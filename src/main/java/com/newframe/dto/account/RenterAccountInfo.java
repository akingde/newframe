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

    private List<RenterAddresses> renterAddresses;

    private RenterAuthorization renterAuthorization;
}
