package org.bootstrap.post.vo;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostCategoryInfoVo(
        Long id,
        String title,
        String content,
        String thumbnail,
        Integer viewCount,
        LocalDateTime lastModifiedDate
) {
}
