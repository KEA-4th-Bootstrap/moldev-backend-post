package org.bootstrap.post.dto.request;

public record PostRequestDto(
        String title,
        String content,
        Integer category,
        String moldevId
) {
}
