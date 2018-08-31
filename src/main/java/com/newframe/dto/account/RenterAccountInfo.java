package com.newframe.dto.account;

import com.newframe.entity.account.AccountRenterAppointSupplier;
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

    private List<AccountRenterAppointSupplier> accountRenterAppointSuppliers;

    public RenterAccountInfo(RenterBaseInfo renterBaseInfo, List<RenterAddress> renterAddresses, RenterAuthorization renterAuthorization,List<AccountRenterAppointSupplier> accountRenterAppointSuppliers){
        this.renterBaseInfo = renterBaseInfo;
        this.renterAddresses = renterAddresses;
        this.renterAuthorization=renterAuthorization;
        this.accountRenterAppointSuppliers=accountRenterAppointSuppliers;
    }
}
