package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.entity.PostImage;

import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record CreatePostResponseDto(
        Long id,
        String title,
        String thumbnail,
        String content,
        String frontUrl,
        List<String> images,
        LocalDateTime createdDate
) {
    public static CreatePostResponseDto of(Post post, PostImage postImage) {
        return CreatePostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .thumbnail(post.getThumbnail())
                .content(post.getProfileContent())
                .frontUrl(post.getFrontUrl())
                .images(postImage.getImages())
                .createdDate(post.getCreateDate())
                .build();
    }
}
