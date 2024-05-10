package org.bootstrap.post.repository;

import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.vo.PostCategoryInfoVo;
import org.bootstrap.post.vo.PostDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostQueryRepository {
    Page<PostDetailVo> findPostDetailVos(Long memberId, Pageable pageable);
    Page<PostCategoryInfoVo> findPostCategoryInfoVos(Long memberId, CategoryType type, Pageable pageable);
}
