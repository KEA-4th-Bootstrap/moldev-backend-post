package org.bootstrap.post.mapper;

import org.bootstrap.post.common.PageInfo;
import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.dto.response.*;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.entity.PostImage;
import org.bootstrap.post.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    public Post toEntity(PostRequestDto requestDto, Long memberId) {
        return Post.createPost(requestDto, memberId);
    }

    public CreatePostResponseDto toCreatePostResponseDto(Post post, PostImage postImage) {
        return CreatePostResponseDto.of(post, postImage);
    }

    public PostDetailResponseDto toPostDetailResponseDto(Post post) {
        return PostDetailResponseDto.of(post);
    }

    public PostsResponseDto toPostsResponseDto(Page<PostDetailVo> postDetailVos) {
        return PostsResponseDto.of(postDetailVos);
    }

    public PostsCategoryResponseDto toPostsCategoryResponseDto(List<PostCategoryInfoWithRedisVo> postCategoryResponseDto, PageInfo pageInfo) {
        return PostsCategoryResponseDto.of(postCategoryResponseDto, pageInfo);
    }

    public TrendingPostsResponseDto toTrendingPostsResponseDto(List<PostDetailWithRedisVo> postList) {
        return TrendingPostsResponseDto.of(postList);
    }

    public CompositionCategoryPostResponseDto toCompositionCategoryPostResponseDto(List<CompositionPostWithViewCountResponseDto> postInfo,
                                                                                   Long count) {
        return CompositionCategoryPostResponseDto.of(postInfo, count);
    }

    public SameCategoryPostsResponseDto toSameCategoryPostsResponseDto(List<PostTitleAndDateVo> postList, Long preCount, Long postCount) {
        return SameCategoryPostsResponseDto.of(postList, (int) Math.ceil((double) (preCount - 2) / 5), (int) Math.ceil((double) (postCount - 2) / 5));
    }

    public CompositionPostWithViewCountResponseDto toCompositionPostWithViewCountResponseDto(CompositionCategoryPostVo postInfo,
                                                                                             Integer redisViewCount) {
        return CompositionPostWithViewCountResponseDto.of(postInfo, redisViewCount);
    }

    public PostImage toPostImage(Post post, PostRequestDto postRequestDto) {
        return PostImage.createPostImage(post, postRequestDto.images());
    }
}
