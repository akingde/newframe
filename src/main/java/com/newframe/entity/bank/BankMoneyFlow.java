package com.newframe.entity.bank;

import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 银行流水
 * </p>
 *
 * @author zww
 * @since 2018-07-13
 */
@Data
@Entity
public class BankMoneyFlow implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    /**
     * 类型 0代表提现 1代表充值
     */
    private Integer type;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 交易编号
     */
    private String transactionNo;
    /**
     * 银行交易流水编号
     */
    private String bankTransactionNo;
    /**
     * 提现,充值的金额
     */
    private BigDecimal amount;
    /**
     * 银行卡号
     */
    private String bankCard;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 支行名称
     */
    private String subBankName;
    /**
     * 转账类型
     */
    private Integer transferType;
    /**
     * 银行返回序列id
     */
    private String serialNumber;
    /**
     * 交易流水hash
     */
    private String cashFlowHash;
    /**
     * 签名
     */
    private String sign;
    /**
     * 标识性 0代表上链成功 1上链失败
     */
    private Integer chainStatus;
    /**
     * 审核人id
     */
    private String auditor;
    /**
     * 审核人名字
     */
    private String auditorName;
    /**
     * 审核时间
     */
    private Long auditorTime;
    /**
     * 申请时间
     */
    private Long applyTime;
    /**
     * 完成时间
     */
    private Long finishTime;
    /**
     * 备注信息
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Long ctime;
    /**
     * 更新时间
     */
    private Long utime;
    /**
     * 版本控制（乐观锁）
     */
    private Integer version;


    /**
     * 提款上链
     *
     * @return
     */
    public MultiValueMap convertWithdrawMap() {
        MultiValueMap params = new LinkedMultiValueMap();
        params.add("id", transactionNo);
        params.add("applyDate", applyTime);
        params.add("amount", amount);
        params.add("state", status);
        params.add("privateKey", "");
        return params;
    }

    /**
     * 充值上链
     *
     * @return
     */
    public MultiValueMap convertRechargeMap() {
        MultiValueMap params = new LinkedMultiValueMap();
        params.add("id", transactionNo);
        params.add("applyDate", applyTime);
        params.add("amount", amount);
        params.add("account", bankCard);
        params.add("name", bankName);
        params.add("completeDate", finishTime);
        params.add("privateKey", "");
        return params;
    }

}
