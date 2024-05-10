package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.common.PageInfo;
import org.bootstrap.post.vo.PostCategoryInfoVo;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record PostsCategoryResponseDto(
        List<PostCategoryInfoVo> postList,
        PageInfo pageInfo
) {
    public static PostsCategoryResponseDto of(Page<PostCategoryInfoVo> postList) {
        return PostsCategoryResponseDto.builder()
                .postList(postList.getContent())
                .pageInfo(PageInfo.of(postList))
                .build();
    }
}
