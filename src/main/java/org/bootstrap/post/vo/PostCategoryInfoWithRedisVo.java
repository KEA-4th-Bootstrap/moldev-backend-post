package org.bootstrap.post.vo;

import lombok.Builder;

@Builder
public record PostCategoryInfoWithRedisVo(
    PostCategoryInfoVo postInfo,
    Integer redisViewCount
) {
}
