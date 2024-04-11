package org.bootstrap.post.mapper;

import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.dto.response.FrontUrlResponseDto;
import org.bootstrap.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post toEntity (PostRequestDto requestDto, Long memberId, String thumbnail) {
        return Post.createPost(requestDto, memberId, thumbnail);
    }

    public FrontUrlResponseDto toFrontUrlResponseDto (String frontUrl) {
        return FrontUrlResponseDto.of(frontUrl);
    }
}
