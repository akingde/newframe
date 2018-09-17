package com.newframe.entity.user;

import com.newframe.dto.user.request.BankDTO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author WangBin
 */
@Data
@Entity
@Table(name = "user_bank")
public class UserBank {
    /**
     * 用户id
     * uid
     */
    @Id
    @Column(name = "uid")
    private Long uid;
    /**
     * 银行名称
     * bank_name
     */
    @Column(name = "bank_name")
    private String bankName;
    /**
     * 银行详细名称
     * bank_detailed_name
     */
    @Column(name = "bank_detailed_name")
    private String bankDetailedName;
    /**
     * 银行卡号
     * bank_number
     */
    @Column(name = "bank_number")
    private String bankNumber;
    /**
     * 用户名称
     * bank_user_name
     */
    @Column(name = "bank_user_name")
    private String bankUserName;
    /**
     * 手机号
     * bank_phone_number
     */
    @Column(name = "bank_phone_number")
    private String bankPhoneNumber;
    /**
     * ctime
     * ctime
     */
    @Column(name = "ctime")
    private Integer ctime;
    /**
     * utime
     * utime
     */
    @Column(name = "utime")
    private Integer utime;

    public UserBank() {
    }

    public UserBank(Long uid, BankDTO bankDTO, String phoneNumber) {
        this.uid = uid;
        this.bankName = bankDTO.getBankName();
        this.bankDetailedName = bankDTO.getBankDetailedName();
        this.bankNumber = bankDTO.getBankNumber();
        this.bankUserName = bankDTO.getUserBankName();
        this.bankPhoneNumber = phoneNumber;
    }
}
