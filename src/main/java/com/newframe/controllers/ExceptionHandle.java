package com.newframe.controllers;

import com.newframe.common.exception.AccountOperationException;
import com.newframe.common.exception.MobileException;
import com.newframe.dto.OperationResult;
import com.newframe.entity.user.MerchantAppoint;
import com.newframe.enums.merchant.MerchantResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kfm
 * @date 2018.09.15 14:26
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JsonResult handleBindException(MethodArgumentNotValidException e) {
        log.error("参数校验异常：" + e);
        Map<String, String> map = new HashMap<>(16);
        for (FieldError errors : e.getBindingResult().getFieldErrors()) {
            log.error(errors.getField() + ":" + errors.getDefaultMessage());
            map.put(errors.getField(), errors.getDefaultMessage());
        }
        return new JsonResult(MerchantResult.VALID_EXCEPTION, map);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public JsonResult handleBindException(BindException e) {
        log.error("参数校验异常：" + e);
        Map<String, String> map = new HashMap<>(16);
        for (FieldError errors : e.getBindingResult().getFieldErrors()) {
            log.error(errors.getField() + ":" + errors.getDefaultMessage());
            map.put(errors.getField(), errors.getDefaultMessage());
        }
        return new JsonResult(MerchantResult.VALID_EXCEPTION, map);
    }

    @ExceptionHandler(AccountOperationException.class)
    @ResponseBody
    public JsonResult handleAccountOperateException(AccountOperationException e) {
        log.error(e.getMessage());
        OperationResult result = e.getOperationResult();
        return new JsonResult(result.getErrorCode(),false);
    }

    @ExceptionHandler
    @ResponseBody
    public JsonResult handleMobileException(MobileException e){
        return new JsonResult(e.getCodeStatus());
    }
}
