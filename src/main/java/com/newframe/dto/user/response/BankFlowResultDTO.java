package com.newframe.dto.user.response;

import com.newframe.entity.user.CapitalFlow;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author WangBin
 */
@Data
public class BankFlowResultDTO {

    private Long orderId;
    private Long uid;
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
    private Integer ctime;

    public BankFlowResultDTO(CapitalFlow capitalFlow) {
        this.orderId = capitalFlow.getOrderId();
        this.uid = capitalFlow.getUid();
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
        this.ctime = capitalFlow.getCtime();
    }
}
