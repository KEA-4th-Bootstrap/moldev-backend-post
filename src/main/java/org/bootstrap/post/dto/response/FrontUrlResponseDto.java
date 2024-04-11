package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record FrontUrlResponseDto(
        String url
) {
    public static FrontUrlResponseDto of (String frontUrl) {
        return FrontUrlResponseDto.builder()
                .url(frontUrl)
                .build();
    }
}
