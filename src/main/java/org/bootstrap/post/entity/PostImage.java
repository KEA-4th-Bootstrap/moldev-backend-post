package org.bootstrap.post.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

import static org.bootstrap.post.utils.EntityUpdateValueUtils.updateValue;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
@Document(collection = "post_image")
public class PostImage {
    @Id
    @Field(name = "_id")
    private String id;

    @Field(name = "post_id")
    private Long postId;

    @Field(name = "images")
    private List<String> images;

    public static PostImage createPostImage(Post post, List<String> images) {
        return PostImage.builder()
                .postId(post.getId())
                .images(images)
                .build();
    }

    public void updateImages(List<String> images) {
        this.images = updateValue(this.images, images);
    }
}
