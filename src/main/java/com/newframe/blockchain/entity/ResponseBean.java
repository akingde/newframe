package com.newframe.blockchain.entity;

import lombok.Data;

/**
 * 响应bean
 *
 * @author wangtao
 * @date 2018/6/15
 */
@Data
public class ResponseBean<T> {
    private Integer id;
    private T result;
    private String error;
}
