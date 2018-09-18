package com.newframe.dto.user.response;

import com.newframe.entity.user.UserBank;
import lombok.Data;

/**
 * @author WangBin
 */
@Data
public class UserBankDTO {

    private Long uid;
    private String bankName;
    private String bankDetailedName;
    private String bankNumber;
    private String bankUserName;
    private String bankPhoneNumber;

    public UserBankDTO(UserBank userBank) {
        this.uid = userBank.getUid();
        this.bankName = userBank.getBankName();
        this.bankDetailedName = userBank.getBankDetailedName();
        this.bankNumber = userBank.getBankNumber();
        this.bankUserName = userBank.getBankUserName();
        this.bankPhoneNumber = userBank.getBankPhoneNumber();
    }

    public UserBankDTO(){
    }
}
