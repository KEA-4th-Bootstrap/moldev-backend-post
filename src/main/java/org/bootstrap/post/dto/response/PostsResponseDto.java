package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.common.PageInfo;
import org.bootstrap.post.vo.PostDetailVo;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record PostsResponseDto(
        List<PostDetailVo> postList,
        PageInfo pageInfo
) {
    public static PostsResponseDto of(Page<PostDetailVo> postList) {
        return PostsResponseDto.builder()
                .postList(postList.getContent())
                .pageInfo(PageInfo.of(postList))
                .build();
    }
}
