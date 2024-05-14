package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.vo.PostTitleAndDateVo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record SameCategoryPostsResponseDto(
        List<PostTitleAndDateVo> postList,
        Integer prevCount,
        Integer postCount
) {
    public static SameCategoryPostsResponseDto of(List<PostTitleAndDateVo> postList,
                                                  Integer prevCount,
                                                  Integer postCount) {
        return SameCategoryPostsResponseDto.builder()
                .postList(postList)
                .prevCount(prevCount)
                .postCount(postCount)
                .build();
    }
}
