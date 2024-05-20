package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.vo.CompositionCategoryPostVo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record CompositionCategoryPostResponseDto(
        List<CompositionPostWithViewCountResponseDto> postInfo,
        Long count
) {
    public static CompositionCategoryPostResponseDto of(List<CompositionPostWithViewCountResponseDto> postInfo,
                                                        Long count) {
        return CompositionCategoryPostResponseDto.builder()
                .postInfo(postInfo)
                .count(count)
                .build();
    }
}
