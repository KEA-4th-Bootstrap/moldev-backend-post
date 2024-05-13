package org.bootstrap.post.repository;

import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.vo.CompositionCategoryPostVo;
import org.bootstrap.post.vo.PostCategoryInfoVo;
import org.bootstrap.post.vo.PostDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostQueryRepository {
    Page<PostDetailVo> findPostDetailVos(Long memberId, Pageable pageable);
    Page<PostCategoryInfoVo> findPostCategoryInfoVos(String moldevId, CategoryType type, Pageable pageable);
    List<CompositionCategoryPostVo> findCompositionCategoryPostVo(String moldevId, CategoryType type);
    Long findPostCountForUserAndCategory(String moldevId, CategoryType type);
}
