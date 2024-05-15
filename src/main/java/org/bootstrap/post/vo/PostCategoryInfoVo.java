package org.bootstrap.post.vo;

import lombok.Builder;

@Builder
public record PostCategoryInfoVo(
        Long id,
        String title,
        String content,
        String thumbnail,
        Integer viewCount
) {
}
