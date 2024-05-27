package org.bootstrap.post.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bootstrap.post.common.PageInfo;
import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.dto.response.*;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.entity.PostImage;
import org.bootstrap.post.helper.PostHelper;
import org.bootstrap.post.kafka.KafkaProducer;
import org.bootstrap.post.kafka.dto.KafkaMessageDto;
import org.bootstrap.post.mapper.PostMapper;
import org.bootstrap.post.repository.PostRepository;
import org.bootstrap.post.utils.CookieUtils;
import org.bootstrap.post.utils.FrontUrlGenerator;
import org.bootstrap.post.utils.RedisUtils;
import org.bootstrap.post.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final static String POST_VIEW_COUNT = "post_view_count";
    private final PostMapper postMapper;
    private final PostHelper postHelper;
    private final RedisUtils redisUtils;

    private final KafkaProducer kafkaProducer;
    private final PostRepository postRepository;

    public SameCategoryPostsResponseDto getSameCategoryPosts(Long postId, CategoryType type, Integer preC, Integer postC) {
        Long preCount = postHelper.countPostsBeforeCurrentId(postId, type);
        Long postCount = postHelper.countPostsAfterCurrentId(postId, type);
        List<PostTitleAndDateVo> postsBeforeCurrentId = getPostsBeforeCurrentId(postId, type, postCount, preC);
        List<PostTitleAndDateVo> postsAfterCurrentId = getPostsAfterCurrentId(postId, type, preCount, postC);
        PostTitleAndDateVo currentPost = postHelper.findPostTitleAndDateVoCurrentId(postId, type, preC, postC);
        composePostResponseList(postsBeforeCurrentId, postsAfterCurrentId, currentPost);
        return postMapper.toSameCategoryPostsResponseDto(postsBeforeCurrentId, preCount, postCount);
    }

    public PostsCategoryResponseDto getPostForCategory(String moldevId, CategoryType type, Pageable pageable) {
        Page<PostCategoryInfoVo> postCategoryInfoVos = postHelper.findPostCategoryInfoVos(moldevId, type, pageable);
        List<PostCategoryInfoWithRedisVo> postCategoryInfoWithRedisVos = createPostCategoryInfoWithRedisVos(postCategoryInfoVos);
        PageInfo pageInfo = PageInfo.of(postCategoryInfoVos);
        return postMapper.toPostsCategoryResponseDto(postCategoryInfoWithRedisVos, pageInfo);
    }

    public PostImagesResponseDto getPostImages(Long postId) {
        PostImage postImage = postHelper.findPostImageOrThrow(postId);
        return PostImagesResponseDto.of(postImage);
    }

    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postHelper.findPostOrThrow(postId);
        kafkaProducer.send("update", KafkaMessageDto.update(post));
        return postMapper.toPostDetailResponseDto(post);
    }

    public PostsResponseDto getPosts(Long memberId, Pageable pageable) {
        Page<PostDetailVo> postDetailVos = postHelper.findPostDetailVos(memberId, pageable);
        return postMapper.toPostsResponseDto(postDetailVos);
    }

    public CreatePostResponseDto createPost(Long memberId, PostRequestDto requestDto) {
        Post post = createPostAndSave(memberId, requestDto);
        PostImage postImage = createPostImageAndSave(post, requestDto);
        String frontUrl = FrontUrlGenerator.createFrontUrl(post);
        post.updateFrontUrl(frontUrl);
        return postMapper.toCreatePostResponseDto(post, postImage);
    }

    public String createPostImage(MultipartFile thumbnail) {
        return postHelper.createStringThumbnail(thumbnail);
    }

    public void updatePost(Long postId, PostRequestDto requestDto) {
        Post post = postHelper.findPostOrThrow(postId);
        PostImage postImage = postHelper.findPostImageOrThrow(post.getId());
        post.updatePost(requestDto);
        postImage.updateImages(requestDto.images());
    }

    private Post createPostAndSave(Long memberId, PostRequestDto requestDto) {
        Post post = postMapper.toEntity(requestDto, memberId);
        return postHelper.savePost(post);
    }

    private PostImage createPostImageAndSave(Post post, PostRequestDto postRequestDto) {
        PostImage postImage = postMapper.toPostImage(post, postRequestDto);
        return postHelper.savePostImage(postImage);
    }

    private List<PostTitleAndDateVo> getPostsBeforeCurrentId(Long postId, CategoryType type, Long postCount, Integer preC) {
        if (Objects.isNull(preC) || preC == 0) return new ArrayList<>();
        long requestBeforeCount = preC;
        if (postCount < preC)
            requestBeforeCount = preC + (preC - postCount);
        return postHelper.findPostsBeforeCurrentId(postId, type, requestBeforeCount);
    }

    private List<PostTitleAndDateVo> getPostsAfterCurrentId(Long postId, CategoryType type, Long preCount, Integer postC) {
        if (Objects.isNull(postC) || postC == 0) return new ArrayList<>();
        long requestAfterCount = postC;
        if (preCount < postC)
            requestAfterCount = postC + (postC - preCount);
        return postHelper.findPostsAfterCurrentId(postId, type, requestAfterCount);
    }

    private void composePostResponseList(List<PostTitleAndDateVo> postsBeforeCurrentId,
                                         List<PostTitleAndDateVo> postsAfterCurrentId,
                                         PostTitleAndDateVo currentPost) {
        if (!Objects.isNull(currentPost)) postsBeforeCurrentId.add(currentPost);
        postsBeforeCurrentId.addAll(postsAfterCurrentId);
        postsBeforeCurrentId.sort(Comparator.comparingLong(PostTitleAndDateVo::id));
    }

    public void viewCountUpByCookie(Long postId, HttpServletRequest request, HttpServletResponse response) {
        final String POST_ID = String.valueOf(postId);

        Cookie[] cookies = CookieUtils.getCookies(request);
        Cookie cookie = getViewCountCookieFromCookies(cookies);

        if (!cookie.getValue().contains(POST_ID)) {
            redisUtils.getZSetOperations().incrementScore(POST_VIEW_COUNT, POST_ID, 1);
            cookie.setValue(cookie.getValue() + POST_ID);
        }

        int maxAge = getMaxAge();
        CookieUtils.addCookieWithMaxAge(response, cookie, maxAge);
    }

    public void deletePost(Long postId) {
        postHelper.deletePost(postId);
    }

    private Cookie getViewCountCookieFromCookies(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(POST_VIEW_COUNT))
                .findFirst()
                .orElseGet(() -> CookieUtils.createCookie(POST_VIEW_COUNT, ""));
    }

    private int getMaxAge() {
        long todayEndSecond = LocalDate.now().atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC);
        long currentSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        return (int) (todayEndSecond - currentSecond);
    }

    private List<PostCategoryInfoWithRedisVo> createPostCategoryInfoWithRedisVos(Page<PostCategoryInfoVo> postCategoryInfoVos) {
        return postCategoryInfoVos.stream()
                .map(postCategoryInfoVo -> {
                    Double viewCount = redisUtils.getZSetOperations().score(POST_VIEW_COUNT, String.valueOf(postCategoryInfoVo.id()));
                    Integer integerViewCount = Objects.isNull(viewCount) ? 0 : viewCount.intValue();
                    return PostCategoryInfoWithRedisVo.of(postCategoryInfoVo, integerViewCount);
                })
                .toList();
    }

    public TrendingPostsResponseDto getTrendingPosts() {
        Set<Long> trendingPostIds = redisUtils.getTrendingPostIds(POST_VIEW_COUNT, 18, 0L);
        List<PostDetailVo> postDetailVosByPostIds = postHelper.findTrendingPostDetailVos(trendingPostIds);
        List<PostDetailWithRedisVo> postDetailWithRedisVos = createPostDetailWithRedisVos(postDetailVosByPostIds);
        return postMapper.toTrendingPostsResponseDto(postDetailWithRedisVos);
    }

    private List<PostDetailWithRedisVo> createPostDetailWithRedisVos(List<PostDetailVo> postDetailVos) {
        return postDetailVos.stream()
                .map(postDetailVo -> {
                    Double viewCount = redisUtils.getZSetOperations().score(POST_VIEW_COUNT, String.valueOf(postDetailVo.id()));
                    if (Objects.isNull(viewCount)) {
                        viewCount = 0.0;
                    }
                    return new PostDetailWithRedisVo(postDetailVo, viewCount.intValue());
                })
                .sorted(Comparator.comparing(PostDetailWithRedisVo::redisViewCount).reversed())
                .toList();
    }

    public RecentPostsResponseListDto getRecentsPosts(String moldevId) {
        List<Post> posts = postRepository.findTop3ByMoldevIdOrderByLastModifiedDateDesc(moldevId);
        List<RecentPostsResponseDto> responseDtoList = posts.stream()
                .map(post -> RecentPostsResponseDto.of(post.getTitle(), post.getLastModifiedDate().toString()))
                .collect(Collectors.toList());
        return RecentPostsResponseListDto.of(responseDtoList);
    }
}
