package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.entity.Post;

@Builder(access = AccessLevel.PRIVATE)
public record PostReportDetailResponseDto(
        Long postId,
        String title
) {
    public static PostReportDetailResponseDto of(Post post) {
        return PostReportDetailResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .build();
    }
}
