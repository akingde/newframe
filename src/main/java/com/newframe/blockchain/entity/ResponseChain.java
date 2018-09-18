package com.newframe.blockchain.entity;

import lombok.Data;

/**
 * @author wangtao
 * @date 2018/8/25 14:58
 */
@Data
public class ResponseChain {
    private boolean success;
    private String error;
    private String hash;
    private Long height;
}
