package org.bootstrap.post.kafka.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.entity.Post;

@Builder(access = AccessLevel.PRIVATE)
public record KafkaMessageDto(
        String content,
        Long memberId,
        Long postId
) {
    public static KafkaMessageDto create(Post post, Long memberId) {
        return KafkaMessageDto.builder()
                .content(post.getProfileContent())
                .memberId(memberId)
                .postId(post.getId())
                .build();
    }

    public static KafkaMessageDto update(Post post, Long memberId) {
        return KafkaMessageDto.builder()
                .memberId(memberId)
                .postId(post.getId())
                .build();
    }
}
