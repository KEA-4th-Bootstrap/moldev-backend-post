package org.bootstrap.post.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.dto.response.CompositionCategoryPostResponseDto;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.helper.PostHelper;
import org.bootstrap.post.mapper.PostMapper;
import org.bootstrap.post.vo.CompositionCategoryPostVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PostCompositionService {
    private final PostHelper postHelper;
    private final PostMapper postMapper;

    public CompositionCategoryPostResponseDto getPostForCategoryAndMoldevId(String moldevId, CategoryType type) {
        List<CompositionCategoryPostVo> categoryPostVoList = postHelper.findCompositionCategoryPostVos(moldevId, type);
        Long count = postHelper.findPostCountForUserAndCategory(moldevId, type);
        return postMapper.toCompositionCategoryPostResponseDto(categoryPostVoList, count);
    }
}
