package org.bootstrap.post.mapper;

import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.dto.response.*;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.vo.CompositionCategoryPostVo;
import org.bootstrap.post.vo.PostCategoryInfoVo;
import org.bootstrap.post.vo.PostDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {
    public Post toEntity(PostRequestDto requestDto, Long memberId, String thumbnail) {
        return Post.createPost(requestDto, memberId, thumbnail);
    }

    public CreatePostResponseDto toCreatePostResponseDto(Post post) {
        return CreatePostResponseDto.of(post);
    }

    public PostDetailResponseDto toPostDetailResponseDto(Post post) {
        return PostDetailResponseDto.of(post);
    }

    public PostsResponseDto toPostsResponseDto(Page<PostDetailVo> postDetailVos) {
        return PostsResponseDto.of(postDetailVos);
    }

    public PostsCategoryResponseDto toPostsCategoryResponseDto(Page<PostCategoryInfoVo> postCategoryInfoVos) {
        return PostsCategoryResponseDto.of(postCategoryInfoVos);
    }

    public CompositionCategoryPostResponseDto toCompositionCategoryPostResponseDto(List<CompositionCategoryPostVo> postInfo,
                                                                                   Long count) {
        return CompositionCategoryPostResponseDto.of(postInfo, count);
    }
}
