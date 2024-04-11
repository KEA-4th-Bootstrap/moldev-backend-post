package org.bootstrap.post.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bootstrap.post.utils.EnumField;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CategoryType implements EnumField {
    ACTIVITY(1, "대외활동"),
    PROJECT(2, "프로젝트"),
    AWARDS(3, "수상이력"),
    TROUBLE(4, "트러블슈팅"),
    INTRODUCE(5, "자기소개");

    private Integer code;
    private String desc;
}
