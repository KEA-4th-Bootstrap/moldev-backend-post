package org.bootstrap.post.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
public record PostDetailWithRedisVo(
    PostDetailVo postInfo,
    Integer redisViewCount
) {
}
