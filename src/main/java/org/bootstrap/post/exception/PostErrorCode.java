package org.bootstrap.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bootstrap.post.common.error.BaseErrorCode;
import org.bootstrap.post.common.error.ErrorReason;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements BaseErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_404_1", "존재하지 않는 게시글입니다.");

    private HttpStatus status;
    private String code;
    private String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.of(status.value(), code, reason);
    }
}
