package org.bootstrap.post.repository;

import org.bootstrap.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {

}
