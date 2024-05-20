package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.vo.CompositionCategoryPostVo;

@Builder(access = AccessLevel.PRIVATE)
public record CompositionPostWithViewCountResponseDto(
        Long id,
        String title,
        String content,
        String thumbnail,
        CategoryType category,
        Integer viewCount
) {
    public static CompositionPostWithViewCountResponseDto of(CompositionCategoryPostVo postInfo,
                                                             Integer redisCount) {
        return CompositionPostWithViewCountResponseDto.builder()
                .id(postInfo.id())
                .title(postInfo.title())
                .content(postInfo.content())
                .thumbnail(postInfo.thumbnail())
                .category(postInfo.category())
                .viewCount(postInfo.viewCount() + redisCount)
                .build();
    }
}
