package org.bootstrap.post.mapper;

import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.dto.response.FrontUrlResponseDto;
import org.bootstrap.post.dto.response.PostDetailResponseDto;
import org.bootstrap.post.dto.response.PostsCategoryResponseDto;
import org.bootstrap.post.dto.response.PostsResponseDto;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.vo.PostCategoryInfoVo;
import org.bootstrap.post.vo.PostDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post toEntity(PostRequestDto requestDto, Long memberId, String thumbnail) {
        return Post.createPost(requestDto, memberId, thumbnail);
    }

    public FrontUrlResponseDto toFrontUrlResponseDto(String frontUrl) {
        return FrontUrlResponseDto.of(frontUrl);
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
}
