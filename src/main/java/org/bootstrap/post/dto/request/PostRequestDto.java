package org.bootstrap.post.dto.request;

import org.bootstrap.post.entity.CategoryType;

public record PostRequestDto(
        String title,
        String content,
        String profileContent,
        CategoryType category,
        String moldevId
) {
}
