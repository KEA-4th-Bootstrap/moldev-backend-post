package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.entity.Post;

@Builder(access = AccessLevel.PRIVATE)
public record PostDetailResponseDto(
        Long id,
        Long memberId,
        String title,
        String content,
        String thumbnail,
        String category
) {
    public static PostDetailResponseDto of (Post post) {
        return PostDetailResponseDto.builder()
                .id(post.getId())
                .memberId(post.getMemberId())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .category(post.getCategory().getDesc())
                .build();
    }
}
