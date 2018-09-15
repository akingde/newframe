package com.newframe.entity.account;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * <p>
 * 线上交易流水表
 * 仅对应于数据库的增减
 * </p>
 *
 * @author wangdong
 * @since 2018-08-29
 */
@Data
@Entity
public class AccountStatement {
    /**
     * uid
     */
    @Id
    private Long id;

    /**
     * 用户的uid
     */
    private Long uid;
    /**
     * 流水类型
     * 1：充值
     * 2：提现
     * 3：融资购机
     * 4：租机
     * 5：还款（正常还款）
     * 6：逾期还款（包括利息）
     * 7：付首期（包括保险）
     * 8：账户间不同种类金额划转（例如将可以余额部分划转到保证金）
     * 例如：付首期700块钱，700中包括200保险
     * dealAmount 700 这个是操作数据库的实际金额
     * extraAmount 这个是200 仅起记录作用，不操作数据库
     */
    private Integer dealType;

    /**
     * 操作的账户类型
     * 1：总金额
     * 2：可用金额
     * 3：冻结金额
     * 4：保证金
     * 增就传正，减就传负
     */
    private Integer accountType;

    /**
     * 交易的总金额
     */
    private BigDecimal dealAmount;

    /**
     * 额外的交易金额
     * 保险啊、利息啊
     */
    private BigDecimal extraAmount;

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;


    public void setAccountStatement(Long uid, Integer dealType, Integer accountType, BigDecimal dealAmount, BigDecimal extraAmount){
        this.uid = uid;
        this.dealType = dealType;
        this.accountType = accountType;
        this.dealAmount = dealAmount;
        this.extraAmount = extraAmount;
    }

}
