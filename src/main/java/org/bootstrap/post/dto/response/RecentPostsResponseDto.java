package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;


@Builder(access = AccessLevel.PRIVATE)
public record RecentPostsResponseDto(
        String title,
        String lastModifiedDate
) {
    public static RecentPostsResponseDto of(String title, String lastModifiedDate){
        return RecentPostsResponseDto.builder()
                .title(title)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }
}