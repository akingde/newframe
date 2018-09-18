package com.newframe.blockchain.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求bean
 *
 * @author wangtao
 * @date 2018/6/15
 */
@Data
public class RequestBean<T> {
    private String jsonrpc;
    private String method;
    private Object id;
    private List<T> params;

    public RequestBean(T params, String blankChainUrlConst) {
        this.jsonrpc = "2.0";
        this.method = blankChainUrlConst;
        this.id = null;
        this.params = new ArrayList<>();
        this.params.add(params);
    }

}
