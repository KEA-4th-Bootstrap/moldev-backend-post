package org.bootstrap.post.mongorepository;

import org.bootstrap.post.entity.PostImage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PostMongoRepository extends MongoRepository<PostImage, String>, MongoCustomRepository {
    Optional<PostImage> findByPostId(Long postId);
    void deleteByPostId(Long postId);
}
