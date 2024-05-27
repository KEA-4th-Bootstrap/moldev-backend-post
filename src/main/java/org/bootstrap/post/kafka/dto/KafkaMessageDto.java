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
    public static KafkaMessageDto create(Post post) {
        return KafkaMessageDto.builder()
                .content(post.getProfileContent())
                .memberId(post.getMemberId())
                .postId(post.getId())
                .build();
    }

    public static KafkaMessageDto update(Post post) {
        return KafkaMessageDto.builder()
                .memberId(post.getMemberId())
                .postId(post.getId())
                .build();
    }
}
