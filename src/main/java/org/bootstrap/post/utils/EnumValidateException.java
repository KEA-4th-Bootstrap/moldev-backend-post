package org.bootstrap.post.utils;

import org.bootstrap.post.common.error.BaseErrorException;
import org.bootstrap.post.common.error.GlobalErrorCode;

public class EnumValidateException extends BaseErrorException {
    public static final BaseErrorException EXCEPTION = new EnumValidateException();

    public EnumValidateException() {
        super(GlobalErrorCode.INVALID_ENUM_CODE);
    }

}