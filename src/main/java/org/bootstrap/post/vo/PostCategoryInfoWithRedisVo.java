package org.bootstrap.post.vo;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record PostCategoryInfoWithRedisVo(
        Long id,
        String title,
        String content,
        String thumbnail,
        Integer viewCount,
        LocalDateTime lastModifiedDate
) {
    public static PostCategoryInfoWithRedisVo of(PostCategoryInfoVo postInfo,
                                                 Integer redisViewCount) {
        return PostCategoryInfoWithRedisVo.builder()
                .id(postInfo.id())
                .title(postInfo.title())
                .content(postInfo.content())
                .thumbnail(postInfo.thumbnail())
                .viewCount(postInfo.viewCount() + redisViewCount)
                .lastModifiedDate(postInfo.lastModifiedDate())
                .build();
    }
}
