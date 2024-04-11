package org.bootstrap.post.helper;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.repository.PostRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostHelper {
    private final PostRepository postRepository;

    public Post savePost (Post post) {
        return postRepository.save(post);
    }
}
