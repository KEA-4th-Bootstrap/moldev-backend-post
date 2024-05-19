package org.bootstrap.post.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.exception.PostNotFoundException;
import org.bootstrap.post.repository.PostRepository;
import org.bootstrap.post.utils.S3Provider;
import org.bootstrap.post.vo.CompositionCategoryPostVo;
import org.bootstrap.post.vo.PostCategoryInfoVo;
import org.bootstrap.post.vo.PostDetailVo;
import org.bootstrap.post.vo.PostTitleAndDateVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class PostHelper {
    private final static String IMAGE_TYPE = "thumbnail";
    private final PostRepository postRepository;
    private final S3Provider s3Provider;

    public String createStringThumbnail(MultipartFile multipartFile) {
        return s3Provider.uploadFile(multipartFile, IMAGE_TYPE);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Page<PostDetailVo> findPostDetailVos(Long memberId, Pageable pageable) {
        return postRepository.findPostDetailVos(memberId, pageable);
    }

    public Post findPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    public Page<PostCategoryInfoVo> findPostCategoryInfoVos(String moldevId, CategoryType type, Pageable pageable) {
        return postRepository.findPostCategoryInfoVos(moldevId, type, pageable);
    }

    public List<PostDetailVo> findTrendingPostDetailVos(Set<Long> postIds) {
        return postRepository.findTrendingPostDetailVos(postIds);
    }

    public List<CompositionCategoryPostVo> findCompositionCategoryPostVos(String moldevId, CategoryType type) {
        return postRepository.findCompositionCategoryPostVo(moldevId, type);
    }

    public Long findPostCountForUserAndCategory(String moldevId, CategoryType type) {
        return postRepository.findPostCountForUserAndCategory(moldevId, type);
    }

    public PostTitleAndDateVo findPostTitleAndDateVoCurrentId(Long currentId, CategoryType type) {
        return postRepository.findPostTitleAndDateVo(currentId, type)
                .orElseThrow(() -> PostNotFoundException.EXCEPTION);
    }

    public List<PostTitleAndDateVo> findPostsAfterCurrentId(Long currentId, CategoryType type, long beforeSize) {
        return postRepository.findPostsAfterCurrentId(currentId, type, beforeSize);
    }

    public List<PostTitleAndDateVo> findPostsBeforeCurrentId(Long currentId, CategoryType type, long beforeSize) {
        return postRepository.findPostsBeforeCurrentId(currentId, type, beforeSize);
    }

    public long countPostsAfterCurrentId(Long currentId, CategoryType type) {
        return postRepository.countPostsAfterCurrentId(currentId, type);
    }

    public long countPostsBeforeCurrentId(Long currentId, CategoryType type) {
        return postRepository.countPostsBeforeCurrentId(currentId, type);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
