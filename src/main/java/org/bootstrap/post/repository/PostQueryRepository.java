package org.bootstrap.post.repository;

import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.vo.CompositionCategoryPostVo;
import org.bootstrap.post.vo.PostCategoryInfoVo;
import org.bootstrap.post.vo.PostDetailVo;
import org.bootstrap.post.vo.PostTitleAndDateVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface PostQueryRepository {
    Page<PostDetailVo> findPostDetailVos(Long memberId, Pageable pageable);
    List<PostDetailVo> findTrendingPostDetailVos(Set<Long> postIds);
    Page<PostCategoryInfoVo> findPostCategoryInfoVos(String moldevId, CategoryType type, Pageable pageable);
    List<CompositionCategoryPostVo> findCompositionCategoryPostVo(String moldevId, CategoryType type);
    Long findPostCountForUserAndCategory(String moldevId, CategoryType type);
    List<PostTitleAndDateVo> findPostsBeforeCurrentId(Long postId, String moldevId, CategoryType type, long beforeSize);
    List<PostTitleAndDateVo> findPostsAfterCurrentId(Long postId, String moldevId, CategoryType type, long afterSize);
    Optional<PostTitleAndDateVo> findPostTitleAndDateVo(Long postId, String moldevId, CategoryType type);
    long countPostsBeforeCurrentId(Long currentId, String moldevId, CategoryType type);
    long countPostsAfterCurrentId(Long currentId, String moldevId, CategoryType type);
}
