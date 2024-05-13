package org.bootstrap.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.vo.CompositionCategoryPostVo;
import org.bootstrap.post.vo.PostCategoryInfoVo;
import org.bootstrap.post.vo.PostDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static org.bootstrap.post.entity.QPost.post;

@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostDetailVo> findPostDetailVos(Long memberId, Pageable pageable) {
        List<PostDetailVo> contents = jpaQueryFactory
                .select(Projections.constructor(PostDetailVo.class,
                        post.id,
                        post.title,
                        post.content,
                        post.thumbnail,
                        post.category
                ))
                .from(post)
                .where(
                        eqMemberId(memberId)
                )
                .orderBy(post.id.desc())
                .fetch();

        JPAQuery<Post> countQuery = jpaQueryFactory
                .selectFrom(post)
                .where(
                        eqMemberId(memberId)
                );

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<PostCategoryInfoVo> findPostCategoryInfoVos(String moldevId, CategoryType type, Pageable pageable) {
        List<PostCategoryInfoVo> contents = jpaQueryFactory
                .select(Projections.constructor(PostCategoryInfoVo.class,
                        post.id,
                        post.title,
                        post.content,
                        post.thumbnail
                ))
                .from(post)
                .where(
                        eqMoldevId(moldevId),
                        eqCategoryType(type)
                )
                .orderBy(post.id.desc())
                .fetch();

        JPAQuery<Post> countQuery = jpaQueryFactory
                .selectFrom(post)
                .where(
                        eqMoldevId(moldevId),
                        eqCategoryType(type)
                );

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
    }

    @Override
    public List<CompositionCategoryPostVo> findCompositionCategoryPostVo(String moldevId, CategoryType type) {
        return jpaQueryFactory
                .select(Projections.constructor(CompositionCategoryPostVo.class,
                        post.id,
                        post.moldevId,
                        post.title,
                        post.content,
                        post.thumbnail,
                        post.category
                ))
                .from(post)
                .where(
                        eqMoldevId(moldevId),
                        eqCategoryType(type)
                )
                .orderBy(post.id.desc())
                .limit(2)
                .fetch();
    }

    @Override
    public Long findPostCountForUserAndCategory(String moldevId, CategoryType type) {
        return jpaQueryFactory
                .selectFrom(post)
                .where(
                        eqMoldevId(moldevId),
                        eqCategoryType(type)
                )
                .fetchCount();
    }

    private BooleanExpression eqMemberId(Long memberId) {
        return memberId == null ? null : post.memberId.eq(memberId);
    }

    private BooleanExpression eqCategoryType(CategoryType type) {
        return type == null ? null : post.category.eq(type);
    }

    private BooleanExpression eqMoldevId(String modevId) {
        return modevId == null ? null : post.moldevId.eq(modevId);
    }
}
