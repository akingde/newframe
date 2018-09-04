package com.newframe.services.account;

import com.newframe.dto.OperationResult;
import com.newframe.dto.account.*;
import com.newframe.entity.account.*;

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
    OperationResult<AccountRenter> getRenterAssetAccount(Long uid);

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
}
