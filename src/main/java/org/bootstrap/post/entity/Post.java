package org.bootstrap.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bootstrap.post.common.BaseTimeEntity;
import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.entity.converter.CategoryTypeConverter;

import static org.bootstrap.post.utils.EntityUpdateValueUtils.updateValue;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "post")
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private Long memberId;
    private String title;
    private String thumbnail;
    private String content;
    private String profileContent;
    @Convert(converter = CategoryTypeConverter.class)
    private CategoryType category;
    private String moldevId;
    private String frontUrl;
    @Builder.Default
    private Integer viewCount = 0;

    public static Post createPost(PostRequestDto requestDto, Long memberId, String thumbnail) {
        return Post.builder()
                .memberId(memberId)
                .title(requestDto.title())
                .category(requestDto.category())
                .content(requestDto.content())
                .profileContent(requestDto.profileContent())
                .thumbnail(thumbnail)
                .moldevId(requestDto.moldevId())
                .build();
    }

    public void updatePost(PostRequestDto requestDto) {
        this.title = updateValue(this.title, requestDto.title());
        this.content = updateValue(this.content, requestDto.content());
        this.category = updateValue(this.category, requestDto.category());
        this.profileContent = updateValue(this.profileContent, requestDto.profileContent());
    }

    public void updateFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public void updateThumbnail(String thumbnail) {
        this.thumbnail = updateValue(this.thumbnail, thumbnail);
    }

    public void updateViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
