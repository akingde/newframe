package com.newframe.services.account;

import com.newframe.dto.OperationResult;
import com.newframe.dto.account.*;
import com.newframe.entity.account.*;
import com.newframe.enums.account.AccountTypeEnum;
import com.newframe.enums.account.DealTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
public interface AccountManageService {

    /**
     * 租赁商获取账户信息
     * @param uid
     * @return
     */
    OperationResult<RenterAccountInfo> getRenterAccountInfo(Long uid);

    /**
     * 获取租赁商的账户资产
     * @param uid
     * @return
     */
    OperationResult<Account> getRenterAssetAccount(Long uid);

    /**
     *
     * @param uid
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    OperationResult<AccountRenterRentInfo> listRenterOrderRent(Long uid, Integer orderStatus, Integer currentPage, Integer pageSize);

    /**
     * 租赁商获取订单融资账户
     * @param uid
     * @return
     */
    OperationResult<AccountRenterFinancingMachine> getRenterOrderFinanceAccount(Long uid);

    /**
     * 我是租赁商订单融资账户订单融资列表
     * @param uid
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    OperationResult<RenterOrderFinanceInfo> listRenterOrderFinance(Long uid, Integer repaymentStatus,Integer orderStatus, Integer currentPage, Integer pageSize);

    /**
     * 我是租赁商订单融资账户订单融资列表查看订单详情
     * @param orderId
     * @return
     */
    OperationResult<List<AccountRenterRepay>> getRenterOrderFinanceDetail(Long orderId);

    /**
     * 9.获取租赁商租赁账户
     * @param uid
     * @return
     */
    OperationResult<AccountRenterRentMachine> getRenterOrderRentAccount(Long uid);

    /**
     * 10.我是租赁商租赁账户租赁明细列表
     * @param uid
     * @param payStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    OperationResult<RenterOrderRentDetailInfo> listRenterOrderRentAccount(Long uid, Integer payStatus, Integer currentPage, Integer pageSize);

    /**
     * 12.获取租赁商订单逾期账户
     * @param uid
     * @return
     */
    OperationResult<AccountRenterOverdueAsset> getAccountRenterOverdueAsset(Long uid);

    /**
     * 13.我是租赁商订单逾期账户下租赁明细列表
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    OperationResult<RenterOrderOverdueDetailInfo> listRenterOrderOverdue(Long uid, Integer currentPage, Integer pageSize);

    /**
     * 这个是用户模块在创建用户的时候，需要默认一个创建账户
     * @param uid
     * @return
     */
    OperationResult<Boolean> saveAccount(Long uid);

    /**
     * 租赁商账户资产
     * 租赁明细
     * 由订单中心那边，调用，将相关信息插入到这张表AccountRenterRent
     * @return
     */
    OperationResult<Boolean> saveAccountRenterRent(Long uid, Long orderId, String relevanceOrderId, BigDecimal receivableAccount, BigDecimal receivedAccount, BigDecimal dueInAccount, Integer residueTime, String collectMoney);

    /**
     * 租赁商账户资产
     * 租机账户
     * 由订单中心那边，调用，将相关信息插入到这张表AccountRenterRent
     * @param uid 用户的uid
     * @param orderId 订单的iD
     * @param associatedOrderId 关联订单的id
     * @param productBrand 手机品牌
     * @param productModel 手机型号
     * @param productColour 手机颜色
     * @param productStorage 手机物理内存
     * @param productMemory 手机的运行内存
     * @param totalRentAccount 总的租金
     * @param monthNumber 租期
     * @param payedAccount 已付租金
     * @param unpayedAccount 未付租金
     * @param residueTime 剩余未还期数
     * @param collectMoney 收款账户
     * @return
     */
    OperationResult<Boolean> saveAccountRenterRentDetail(Long uid,Long orderId,String associatedOrderId,String productBrand,String productModel,String productColour,String productStorage,String productMemory,BigDecimal totalRentAccount,Integer monthNumber,BigDecimal payedAccount,BigDecimal unpayedAccount,Integer residueTime, String collectMoney);

    /**
     * 租赁商账户资产
     * 订单融资
     * 能传的字段，按照接口要求传，不能提供的请传一个0
     * @param uid
     * @param orderId
     * @param associatedOrderId
     * @param financingAmount
     * @param financingMaturity
     * @param financingPrincipalInterest
     * @param financingInterest
     * @param settlePrincipalInterest
     * @param settleInterest
     * @param unsettlePrincipalInterest
     * @param unsettleInterest
     * @return
     */
    OperationResult<Boolean> saveAccountRenterFinancing(Long uid,Long orderId,String associatedOrderId,BigDecimal financingAmount,Integer financingMaturity,BigDecimal financingPrincipalInterest,BigDecimal financingInterest,BigDecimal settlePrincipalInterest,BigDecimal settleInterest,BigDecimal unsettlePrincipalInterest,BigDecimal unsettleInterest);

    /**
     * 生成还款计划表
     * @param orderId 订单的ID
     * @param totalAccount 总金额
     * @param totalPeriods 一共几期
     * @return
     */
    OperationResult<Boolean> saveAccountRenterRepay(Long orderId,BigDecimal totalAccount,Integer totalPeriods);

    /**
     * 操作账户的接口
     * 具体字段什么意思
     * 在AccountStatement实体类有详细的描述
     * @param uid
     * @param dealType 交易的类型
     * @param accountType 操作账户的类型
     * @param dealAmount 交易的金额
     * @param extraAmount 额外的金额
     * @return
     */
    OperationResult<Boolean> saveAccountStatement(Long uid, DealTypeEnum dealType, AccountTypeEnum accountType, BigDecimal dealAmount, BigDecimal extraAmount);

    /**
     * 对指定的账户做操作
     * @param uid
     * @param accountTypeEnum
     * @param dealAmount
     * @return
     */
    OperationResult<Boolean> updateAccount(Long uid,AccountTypeEnum accountTypeEnum,BigDecimal dealAmount);

    /**
     * 融资购机还款
     * @param id
     * @param finallyPeriod
     * @return
     */
    OperationResult<Boolean> financeRepayment(Long id);

    /**
     * 租赁商租机还款
     * @param id
     * @return
     */
    OperationResult<Boolean> rentRepayment(Long id);
}
