package com.newframe.services.account;

import com.newframe.dto.OperationResult;
import com.newframe.dto.account.*;
import com.newframe.entity.account.*;

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
    OperationResult<Boolean> saveAccountRenterRent(Long uid, Long orderId, String relevanceOrderId, BigDecimal receivableAccount, BigDecimal receivedAccount, BigDecimal dueInAccount);

    /**
     * 租赁商账户资产
     * 租机账户
     * 由订单中心那边，调用，将相关信息插入到这张表AccountRenterRent
     * @param uid
     * @param orderId
     * @param associatedOrderId
     * @param productBrand
     * @param productModel
     * @param productColour
     * @param productStorage
     * @param productMemory
     * @param totalRentAccount
     * @param monthNumber
     * @param payedAccount
     * @param unpayedAccount
     * @return
     */
    OperationResult<Boolean> saveAccountRenterRentDetail(Long uid,Long orderId,String associatedOrderId,String productBrand,String productModel,String productColour,String productStorage,String productMemory,BigDecimal totalRentAccount,Integer monthNumber,BigDecimal payedAccount,BigDecimal unpayedAccount);

    /**
     * 生成还款计划表
     * @param orderId 订单的ID
     * @param totalAccount 总金额
     * @param totalPeriods 一共几期
     * @return
     */
    OperationResult<Boolean> saveAccountRenterRepay(Long orderId,BigDecimal totalAccount,Integer totalPeriods);
}
