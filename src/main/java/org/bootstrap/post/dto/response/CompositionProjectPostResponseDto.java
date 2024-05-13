package org.bootstrap.post.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.bootstrap.post.vo.CompositionCategoryPostVo;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record CompositionProjectPostResponseDto(
        List<CompositionCategoryPostVo> postInfo,
        Integer count
) {
}
