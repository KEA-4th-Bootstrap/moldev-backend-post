package org.bootstrap.post.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.dto.response.CompositionCategoryPostResponseDto;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.exception.PostNotFoundException;
import org.bootstrap.post.repository.PostRepository;
import org.bootstrap.post.utils.S3Provider;
import org.bootstrap.post.vo.CompositionCategoryPostVo;
import org.bootstrap.post.vo.PostCategoryInfoVo;
import org.bootstrap.post.vo.PostDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

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

    public List<CompositionCategoryPostVo> findCompositionCategoryPostVos(String moldevId, CategoryType type) {
        return postRepository.findCompositionCategoryPostVo(moldevId, type);
    }

    public Long findPostCountForUserAndCategory(String moldevId, CategoryType type) {
        return postRepository.findPostCountForUserAndCategory(moldevId, type);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
