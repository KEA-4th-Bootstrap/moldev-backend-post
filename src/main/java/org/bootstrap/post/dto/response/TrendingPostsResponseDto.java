package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.vo.PostDetailWithRedisVo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record TrendingPostsResponseDto(
    List<PostDetailWithRedisVo> postList
) {
    public static TrendingPostsResponseDto of(List<PostDetailWithRedisVo> postList) {
        return TrendingPostsResponseDto.builder()
            .postList(postList)
            .build();
    }
}
