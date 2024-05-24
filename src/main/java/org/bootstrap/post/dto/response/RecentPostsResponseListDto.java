package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record RecentPostsResponseListDto(
        List<RecentPostsResponseDto> recentPostsResponseDtoList
) {
    public static RecentPostsResponseListDto of(List<RecentPostsResponseDto> recentPostsResponseDtoList){
        return RecentPostsResponseListDto.builder()
                .recentPostsResponseDtoList(recentPostsResponseDtoList)
                .build();
    }
}
