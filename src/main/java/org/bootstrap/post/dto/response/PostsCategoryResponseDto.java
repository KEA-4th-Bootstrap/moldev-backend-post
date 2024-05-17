package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.common.PageInfo;
import org.bootstrap.post.vo.PostCategoryInfoWithRedisVo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record PostsCategoryResponseDto(
        List<PostCategoryInfoWithRedisVo> postList,
        PageInfo pageInfo
) {
    public static PostsCategoryResponseDto of(List<PostCategoryInfoWithRedisVo> postList, PageInfo pageInfo) {
        return PostsCategoryResponseDto.builder()
                .postList(postList)
                .pageInfo(pageInfo)
                .build();
    }
}
