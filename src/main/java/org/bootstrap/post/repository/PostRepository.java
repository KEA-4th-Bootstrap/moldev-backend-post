package org.bootstrap.post.repository;

import org.bootstrap.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {
    List<Post> findTop3ByMoldevIdOrderByLastModifiedDateDesc(String moldevId);
}
