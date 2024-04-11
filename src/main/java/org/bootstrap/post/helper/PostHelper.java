package org.bootstrap.post.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.repository.PostRepository;
import org.bootstrap.post.utils.S3Provider;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class PostHelper {
    private final static String IMAGE_TYPE = "thumbnail";
    private final PostRepository postRepository;
    private final S3Provider s3Provider;

    public String createStringThumbnail(MultipartFile multipartFile) {
        return s3Provider.uploadFile(multipartFile, IMAGE_TYPE);
    }

    public Post savePost (Post post) {
        return postRepository.save(post);
    }
}
