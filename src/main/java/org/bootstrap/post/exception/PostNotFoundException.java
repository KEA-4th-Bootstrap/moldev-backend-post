package org.bootstrap.post.exception;

import org.bootstrap.post.common.error.BaseErrorException;

public class PostNotFoundException extends BaseErrorException {
    public static final PostNotFoundException EXCEPTION = new PostNotFoundException();

    public PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }

}
