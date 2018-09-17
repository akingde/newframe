package com.newframe.common.exception;

import com.newframe.dto.OperationResult;

/**
 * 自定义异常，用于处理账户操作的一些异常情况
 * @author kfm
 * @date 2018.09.17 15:45
 */
public class AccountOperationException extends Exception {

    private OperationResult operationResult;

    public AccountOperationException(OperationResult operationResult) {
        super(operationResult.getMessage());
        this.operationResult = operationResult;
    }

    public OperationResult getOperationResult() {
        return operationResult;
    }
}
