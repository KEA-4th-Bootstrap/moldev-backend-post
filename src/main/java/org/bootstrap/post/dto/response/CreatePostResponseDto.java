package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.entity.Post;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record CreatePostResponseDto(
        Long id,
        String title,
        String thumbnail,
        String content,
        String frontUrl,
        LocalDateTime createdDate
) {
    public static CreatePostResponseDto of(Post post) {
        return CreatePostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .thumbnail(post.getThumbnail())
                .content(post.getProfileContent())
                .frontUrl(post.getFrontUrl())
                .createdDate(post.getCreateDate())
                .build();
    }
}
