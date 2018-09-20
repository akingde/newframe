package com.newframe.dto.after.response;

import com.newframe.entity.user.CapitalFlow;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
@Data
public class DrawAssetDTO {

    private Long uid;
    private Long orderId;
    private String merchantName;
    private String userName;
    private String userPhoneNumber;
    private String userBankNumber;
    private String userBankName;
    private String userBankDetailedName;
    private String platformName;
    private String platformBankNumber;
    private String platformBankName;
    private String platformBankDetailedName;
    private BigDecimal amount;
    private Integer type;
    private Integer orderStatus;
    private Long checkUid;
    private String checkName;
    private String remarks;
    private Integer ctime;

    public DrawAssetDTO(CapitalFlow capitalFlow) {
        this.uid = capitalFlow.getUid();
        this.orderId = capitalFlow.getOrderId();
        this.merchantName = capitalFlow.getMerchantName();
        this.userName = capitalFlow.getUserName();
        this.userPhoneNumber = capitalFlow.getUserPhoneNumber();
        this.userBankNumber = capitalFlow.getUserBankNumber();
        this.userBankName = capitalFlow.getUserBankName();
        this.userBankDetailedName = capitalFlow.getUserBankDetailedName();
        this.platformName = capitalFlow.getPlatformName();
        this.platformBankNumber = capitalFlow.getPlatformBankNumber();
        this.platformBankName = capitalFlow.getPlatformBankName();
        this.platformBankDetailedName = capitalFlow.getPlatformBankDetailedName();
        this.amount = capitalFlow.getAmount();
        this.type = capitalFlow.getType();
        this.orderStatus = capitalFlow.getOrderStatus();
        this.checkUid = capitalFlow.getCheckUid();
        this.checkName = capitalFlow.getCheckName();
        this.remarks = capitalFlow.getRemarks();
        this.ctime = capitalFlow.getCtime();
    }
}
