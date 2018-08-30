package com.newframe.dto.account;

import lombok.Data;

import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
@Data
public class RenterAccountInfo {

    private RenterBaseInfo renterBaseInfo;

    private List<RenterAddress> renterAddresses;

    private RenterAuthorization renterAuthorization;

    public RenterAccountInfo(RenterBaseInfo renterBaseInfo, List<RenterAddress> renterAddresses, RenterAuthorization renterAuthorization){
        this.renterBaseInfo = renterBaseInfo;
        this.renterAddresses = renterAddresses;
        this.renterAuthorization=renterAuthorization;
    }
}
