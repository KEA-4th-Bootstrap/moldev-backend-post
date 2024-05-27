package org.bootstrap.post.dto.request;

import org.bootstrap.post.entity.CategoryType;

import java.util.List;

public record PostRequestDto(
        String title,
        String content,
        String profileContent,
        String thumbnail,
        CategoryType category,
        String moldevId,
        List<String> images
) {
}
