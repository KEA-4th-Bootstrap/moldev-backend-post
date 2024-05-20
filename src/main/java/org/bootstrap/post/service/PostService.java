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
import org.bootstrap.post.helper.PostHelper;
import org.bootstrap.post.mapper.PostMapper;
import org.bootstrap.post.utils.CookieUtils;
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

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final static long CRITERIA_CATEGORY_POST_COUNT = 2;
    private final static String POST_VIEW_COUNT = "post_view_count";
    private final PostMapper postMapper;
    private final PostHelper postHelper;
    private final RedisUtils redisUtils;

    public SameCategoryPostsResponseDto getSameCategoryPosts(Long postId, CategoryType type) {
        Long preCount = postHelper.countPostsBeforeCurrentId(postId, type);
        Long postCount = postHelper.countPostsAfterCurrentId(postId, type);
        List<PostTitleAndDateVo> postsBeforeCurrentId = getPostsBeforeCurrentId(postId, type, postCount);
        List<PostTitleAndDateVo> postsAfterCurrentId = getPostsAfterCurrentId(postId, type, preCount);
        PostTitleAndDateVo currentPost = postHelper.findPostTitleAndDateVoCurrentId(postId, type);
        postsBeforeCurrentId.add(currentPost);
        postsBeforeCurrentId.addAll(postsAfterCurrentId);
        postsBeforeCurrentId.sort(Comparator.comparingLong(PostTitleAndDateVo::id));
        return postMapper.toSameCategoryPostsResponseDto(postsBeforeCurrentId, preCount, postCount);
    }

    public PostsCategoryResponseDto getPostForCategory(String moldevId, CategoryType type, Pageable pageable) {
        Page<PostCategoryInfoVo> postCategoryInfoVos = postHelper.findPostCategoryInfoVos(moldevId, type, pageable);
        List<PostCategoryInfoWithRedisVo> postCategoryInfoWithRedisVos = createPostCategoryInfoWithRedisVos(postCategoryInfoVos);

        PageInfo pageInfo = PageInfo.of(postCategoryInfoVos);
        return postMapper.toPostsCategoryResponseDto(postCategoryInfoWithRedisVos, pageInfo);
    }

    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postHelper.findPostOrThrow(postId);
        return postMapper.toPostDetailResponseDto(post);
    }

    public PostsResponseDto getPosts(Long memberId, Pageable pageable) {
        Page<PostDetailVo> postDetailVos = postHelper.findPostDetailVos(memberId, pageable);
        return postMapper.toPostsResponseDto(postDetailVos);
    }

    public CreatePostResponseDto createPost(Long memberId, PostRequestDto requestDto, MultipartFile thumbnail) {
        String thumbnailString = postHelper.createStringThumbnail(thumbnail);
        Post post = createPostAndSave(memberId, requestDto, thumbnailString);
        return postMapper.toCreatePostResponseDto(post);
    }

    private Post createPostAndSave(Long memberId, PostRequestDto requestDto, String thumbnail) {
        Post post = postMapper.toEntity(requestDto, memberId, thumbnail);
        return postHelper.savePost(post);
    }

    public void updatePost(Long postId, PostRequestDto requestDto, MultipartFile thumbnail) {
        Post post = postHelper.findPostOrThrow(postId);
        post.updatePost(requestDto);
        checkThumbnailAndUpdate(thumbnail, post);
    }

    private List<PostTitleAndDateVo> getPostsBeforeCurrentId(Long postId, CategoryType type, Long postCount) {
        long requestBeforeCount = CRITERIA_CATEGORY_POST_COUNT;
        if (postCount < CRITERIA_CATEGORY_POST_COUNT)
            requestBeforeCount = CRITERIA_CATEGORY_POST_COUNT + (CRITERIA_CATEGORY_POST_COUNT - postCount);
        return postHelper.findPostsBeforeCurrentId(postId, type, requestBeforeCount);
    }

    private List<PostTitleAndDateVo> getPostsAfterCurrentId(Long postId, CategoryType type, Long preCount) {
        long requestAfterCount = CRITERIA_CATEGORY_POST_COUNT;
        if (preCount < CRITERIA_CATEGORY_POST_COUNT)
            requestAfterCount = CRITERIA_CATEGORY_POST_COUNT + (CRITERIA_CATEGORY_POST_COUNT - preCount);
        return postHelper.findPostsAfterCurrentId(postId, type, requestAfterCount);
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

    private void checkThumbnailAndUpdate(MultipartFile thumbnail, Post post) {
        if (!Objects.isNull(thumbnail)) {
            String thumbnailString = postHelper.createStringThumbnail(thumbnail);
            post.updateThumbnail(thumbnailString);
        }
    }

    public PostDetailResponseDto getPost(Long postId) {
        Post post = postHelper.findPostOrThrow(postId);
        return postMapper.toPostDetailResponseDto(post);
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
                    if (Objects.isNull(viewCount)) {
                        viewCount = 0.0;
                    }
                    return new PostCategoryInfoWithRedisVo(postCategoryInfoVo, viewCount.intValue());
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
}
