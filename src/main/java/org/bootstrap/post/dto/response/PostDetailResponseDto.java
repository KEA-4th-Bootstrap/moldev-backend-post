package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.entity.Post;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record PostDetailResponseDto(
        Long id,
        String title,
        String content,
        String thumbnail,
        CategoryType category,
        LocalDateTime lastModifiedDate,
        Integer viewCount
) {
    public static PostDetailResponseDto of(Post post) {
        return PostDetailResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .category(post.getCategory())
                .lastModifiedDate(post.getLastModifiedDate())
                .viewCount(post.getViewCount())
                .build();
    }
}
