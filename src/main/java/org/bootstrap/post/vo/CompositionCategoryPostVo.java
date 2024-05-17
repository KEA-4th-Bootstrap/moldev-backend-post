package org.bootstrap.post.vo;

import lombok.Builder;
import org.bootstrap.post.entity.CategoryType;

@Builder
public record CompositionCategoryPostVo(
        Long id,
        String title,
        String content,
        String thumbnail,
        CategoryType category,
        Integer viewCount
) {
}
