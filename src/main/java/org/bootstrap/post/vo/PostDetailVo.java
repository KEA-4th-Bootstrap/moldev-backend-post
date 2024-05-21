package org.bootstrap.post.vo;

import lombok.Builder;
import org.bootstrap.post.entity.CategoryType;

import java.time.LocalDateTime;

@Builder
public record PostDetailVo(
        Long id,
        String moldevId,
        String title,
        String content,
        String thumbnail,
        CategoryType category,
        Integer viewCount,
        String lastModifiedDate
) {
}
