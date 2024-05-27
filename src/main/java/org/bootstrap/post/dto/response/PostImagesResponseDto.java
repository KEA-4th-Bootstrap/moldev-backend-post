package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.entity.PostImage;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record PostImagesResponseDto(
        List<String> images
) {
    public static PostImagesResponseDto of(PostImage postImage) {
        return PostImagesResponseDto.builder()
                .images(postImage.getImages())
                .build();
    }
}
