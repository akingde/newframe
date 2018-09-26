package com.newframe.common.exception;

import com.newframe.enums.CodeStatus;

/**
 * @author WangBin
 */
public class MobileException extends RuntimeException {

    private CodeStatus codeStatus;

    public CodeStatus getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(CodeStatus codeStatus) {
        this.codeStatus = codeStatus;
    }

    public MobileException(CodeStatus codeStatus) {
        this.codeStatus = codeStatus;
    }

    public MobileException() {
    }
}
