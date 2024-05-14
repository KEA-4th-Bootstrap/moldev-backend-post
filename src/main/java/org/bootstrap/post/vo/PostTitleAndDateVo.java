package org.bootstrap.post.vo;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostTitleAndDateVo(
        Long id,
        String title,
        LocalDateTime updateDate
) {
}
