package com.newframe.services.account.impl;

import com.newframe.controllers.JsonResult;
import com.newframe.controllers.PageJsonResult;
import com.newframe.dto.account.response.AccountFundingDTO;
import com.newframe.dto.account.response.AccountFundingFinanceAssetDTO;
import com.newframe.dto.account.response.AccountFundingFinanceAssetListDTO;
import com.newframe.dto.account.response.AccountOrderFundingDTO;
import com.newframe.entity.account.AccountFunding;
import com.newframe.entity.account.AccountFundingFinanceAsset;
import com.newframe.entity.account.AccountRenter;
import com.newframe.entity.account.AccountRenterRent;
import com.newframe.entity.order.OrderFunder;
import com.newframe.enums.SystemCode;
import com.newframe.repositories.dataQuery.account.AccountFundingFinanceAssetQuery;
import com.newframe.repositories.dataQuery.account.AccountRenterRentQuery;
import com.newframe.repositories.dataQuery.order.OrderFunderQuery;
import com.newframe.repositories.dataSlave.account.*;
import com.newframe.repositories.dataSlave.order.OrderFunderSlave;
import com.newframe.services.account.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author:zww 31个接口
 * @description:账户相关模块的接口实现
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountFundingSlave accountFundingSlave;
    @Autowired
    AccountFundingFinanceAssetSlave accountFundingFinanceAssetSlave;
    @Autowired
    AccountFundingOverdueAssetSlave accountFundingOverdueAssetSlave;

    @Autowired
    AccountLessorMatterAssetSlave accountLessorMatterAssetSlave;
    @Autowired
    AccountLessorOverdueAssetSlave accountLessorOverdueAssetSlave;
    @Autowired
    AccountLessorSlave accountLessorSlave;


    @Autowired
    OrderFunderSlave orderFunderSlave;

    @Autowired
    private AccountRenterSlave accountRenterSlave;

    @Autowired
    private AccountRenterRentSlave accountRenterRentSlave;

    @Override
    public JsonResult recharge(BigDecimal amount) {
        return null;
    }

    @Override
    public JsonResult withdrawDeposit() {
        return null;
    }

    @Override
    public JsonResult getRenterAccountInfo() {
        return null;
    }

    @Override
    public JsonResult getRenterAssetAccount() {
        return null;
    }

    @Override
    public JsonResult getRenterRentDetail(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getRenterOrderFinanceAccount() {
        return null;
    }

    @Override
    public JsonResult listRenterOrderFinance(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getRenterOrderFinanceDetail(Long orderId) {
        return null;
    }

    /**
     * 获取租赁商租赁账户
     * 涉及到
     * 1、租赁总额
     * 2、累计应付租金
     * 3、已付租金
     * 4、待付租金
     *
     * @return
     */
    @Override
    public JsonResult getRenterOrderRentAccount() {
        return null;
    }

    /**
     * 租赁商租赁账户下
     * 租赁明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listRenterOrderRentAccount(Integer currentPage, Integer pageSize) {
        return null;
    }

    /**
     * 租赁商租赁账户下
     * 租赁明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @Override
    public JsonResult getRenterOrderRentDetail(Long orderId) {
        return null;
    }

    /**
     * 获取租赁商订单逾期账户
     * 涉及到
     * 1、逾期金额合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    @Override
    public JsonResult getRenterOrderOverdueAccount() {
        return null;
    }

    /**
     * 租赁商订单逾期账户下
     * 租赁明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listRenterOrderOverdue(Integer currentPage, Integer pageSize) {
        return null;
    }

    /**
     * 租赁商订单逾期账户下
     * 逾期订单租赁明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @Override
    public JsonResult getRenterOrderOverdueDetail(Long orderId) {
        return null;
    }


    @Override
    public JsonResult getFunderAssetAccount(Long uid) {
        AccountFunding entity = accountFundingSlave.findOne(uid);
        if (null != entity) {
            //DueAmount 转化成 DueInAmount
            AccountFundingDTO dto = new AccountFundingDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setDueInAmount(entity.getDueAmount());
            return new JsonResult(SystemCode.SUCCESS, dto);
        }
        return new JsonResult(SystemCode.SUCCESS, null);
    }

    @Override
    public JsonResult getFunderOrderFinancialAssets(Long uid) {
        AccountFundingFinanceAsset entity = accountFundingFinanceAssetSlave.findOne(uid);
        if (null != entity) {
            AccountFundingFinanceAssetDTO dto = new AccountFundingFinanceAssetDTO();
            BeanUtils.copyProperties(entity, dto);
            return new JsonResult(SystemCode.SUCCESS, dto);
        }
        return new JsonResult(SystemCode.SUCCESS, null);
    }

    @Override
    public JsonResult listFunderOrderInvestment(Long uid, Integer currentPage, Integer pageSize) {
        currentPage--;
        Pageable pageable = new PageRequest(currentPage, pageSize);
        AccountFundingFinanceAssetQuery query = new AccountFundingFinanceAssetQuery();
        query.setUid(uid);
        Page<AccountFundingFinanceAsset> page = accountFundingFinanceAssetSlave.findAll(query, pageable);

        List<AccountFundingFinanceAssetListDTO> dtoList = new ArrayList<>();
        for (AccountFundingFinanceAsset entity : page.getContent()) {
            AccountFundingFinanceAssetListDTO dto = new AccountFundingFinanceAssetListDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setInvestMonth(entity.getInvestDeadline());
            dto.setEarningsRate(entity.getYieldRate());
            dto.setOrderStatus(1);
            dtoList.add(dto);
        }
        return new PageJsonResult(SystemCode.SUCCESS, dtoList, page.getTotalElements());
    }

    @Override
    public JsonResult getFunderOrderInvestmentDetail(Long uid, Long orderId) {
        OrderFunderQuery query = new OrderFunderQuery();
        query.setFunderId(uid);
        query.setOrderId(orderId);
        query.setDeleteStatus(OrderFunder.NO_DELETE_STATUS);
        OrderFunder entity = orderFunderSlave.findOne(query);
        AccountOrderFundingDTO dto = new AccountOrderFundingDTO();
        BeanUtils.copyProperties(entity, dto);
        return new JsonResult(SystemCode.SUCCESS, dto);
    }

    /**
     * 获取资金方逾期资产账户
     * 涉及到
     * 1、逾期金融合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    @Override
    public JsonResult getFunderOrderOverdueAssets() {
        return null;
    }

    /**
     * 获取资金方逾期资产
     * 逾期明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listFunderOrderOverdue(Integer currentPage, Integer pageSize) {
        return null;
    }

    /**
     * 获取资金方逾期资产
     * 逾期明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @Override
    public JsonResult getFunderOrderOverdueDetail(Long orderId) {
        return null;
    }

    /**
     * 供应商获取账户资产
     * 1、可用余额
     * 2、资产总额
     * 3、冻结资产
     *
     * @return
     */
    @Override
    public JsonResult getSupplierAssetAccount() {
        return null;
    }

    /**
     * 获取供应商销售账户
     * 涉及到
     * 1、累计营收
     * 2、累计销售数量
     * 3、待发货数量
     *
     * @return
     */
    @Override
    public JsonResult getSupplierOrderSellAssets() {
        return null;
    }

    /**
     * 获取供应商销售账户下
     * 销售明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listSupplierOrderSell(Integer currentPage, Integer pageSize) {
        return null;
    }

    /**
     * 出租方获取账户资产
     * 1、可用余额
     * 2、资产总额
     * 3、冻结资产
     * 4、保证金
     * 5、代收金额
     * 6、本月应收
     *
     * @return
     */
    @Override
    public JsonResult getHirerAssetAccount() {
        return null;
    }

    /**
     * 获取出租方逾期资产账户
     * 涉及到
     * 1、逾期金额合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    @Override
    public JsonResult getHirerOrderOverdueAssets() {
        return null;
    }

    /**
     * 获取出租方逾期资产账户下
     * 逾期明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listHirerOrderOverdue(Integer currentPage, Integer pageSize) {
        return null;
    }

    /**
     * 获取出租方逾期资产账户下
     * 逾期明细列表
     *
     * @param orderId
     * @return
     */
    @Override
    public JsonResult getHirerOrderOverdueDetail(Long orderId) {
        return null;
    }

    /**
     * 获取出租方实物资产账户
     * 涉及到
     * 1、租赁总额
     * 2、累计应付租金
     * 3、已付租金
     * 4、待付租金
     * 5、投资回报率
     * 6、市场平均投资回报率
     *
     * @return
     */
    @Override
    public JsonResult getHirerOrderMaterialAssets() {
        return null;
    }

    /**
     * 获取出租方实物资产账户下
     * 实物明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public JsonResult listHirerOrderMaterial(Integer currentPage, Integer pageSize) {
        return null;
    }

    /**
     * 获取出租方实物资产账户下
     * 实物明细列表
     *
     * @param orderId
     * @return
     */
    @Override
    public JsonResult getHirerOrderMaterialDetail(Long orderId) {
        return null;
    }

    /**
     * 获取租赁商账户资产
     *
     * @param uid
     * @return
     */
    @Override
    public AccountRenter getAccountRenter(Long uid) {
        if (null == uid){
            return null;
        }

        Optional<AccountRenter> result = accountRenterSlave.findById(uid);
        if (!result.isPresent()){
            return null;
        }
        return result.get();
    }

    /**
     * 获取租赁商账户资产下的租赁明细表
     *
     * @param uid
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<AccountRenterRent> getAccountRenterRent(Long uid, Integer orderStatus, Integer currentPage, Integer pageSize) {
        if (null == uid || null == currentPage || null == pageSize){
            return null;
        }

        AccountRenterRentQuery query = new AccountRenterRentQuery();
        query.setUid(uid);
        query.setOrderStatus(orderStatus);
        Sort sort = new Sort(Sort.Direction.DESC,"ctime");
        PageRequest pageRequest = PageRequest.of(currentPage-1,pageSize,sort);

        Page<AccountRenterRent> rents = accountRenterRentSlave.findAll(pageRequest);
        return rents;
    }
}
