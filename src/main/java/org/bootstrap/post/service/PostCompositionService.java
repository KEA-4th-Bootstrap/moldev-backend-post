package org.bootstrap.post.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.dto.response.CompositionCategoryPostResponseDto;
import org.bootstrap.post.dto.response.CompositionPostWithViewCountResponseDto;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.helper.PostHelper;
import org.bootstrap.post.mapper.PostMapper;
import org.bootstrap.post.utils.RedisUtils;
import org.bootstrap.post.vo.CompositionCategoryPostVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PostCompositionService {
    private final static String POST_VIEW_COUNT = "post_view_count";
    private final PostHelper postHelper;
    private final PostMapper postMapper;
    private final RedisUtils redisUtils;

    public CompositionCategoryPostResponseDto getPostForCategoryAndMoldevId(String moldevId, CategoryType type) {
        List<CompositionCategoryPostVo> categoryPostVoList = postHelper.findCompositionCategoryPostVos(moldevId, type);
        List<CompositionPostWithViewCountResponseDto> postWithViewCountResponseDtoList = getCompositionPostWithViewCountResponseDto(categoryPostVoList);
        Long count = postHelper.findPostCountForUserAndCategory(moldevId, type);
        return postMapper.toCompositionCategoryPostResponseDto(postWithViewCountResponseDtoList, count);
    }

    private List<CompositionPostWithViewCountResponseDto> getCompositionPostWithViewCountResponseDto(List<CompositionCategoryPostVo> categoryPostVoList) {
        return categoryPostVoList.stream()
                .map(categoryPostVo -> postMapper.toCompositionPostWithViewCountResponseDto(categoryPostVo, getViewCountAtRedis(categoryPostVo.id())))
                .collect(Collectors.toList());
    }


    private Integer getViewCountAtRedis(Long postId) {
        String stringPostId = String.valueOf(postId);
        Double doubleViewCount = redisUtils.getZSetOperations().score(POST_VIEW_COUNT, String.valueOf(stringPostId));
        return Objects.isNull(doubleViewCount) ? 0 : doubleViewCount.intValue();
    }
}
