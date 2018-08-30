package com.newframe.services.account.impl;

import com.newframe.controllers.JsonResult;
import com.newframe.controllers.PageJsonResult;
import com.newframe.dto.account.response.AccountFundingDTO;
import com.newframe.dto.account.response.AccountFundingFinanceAssetDTO;
import com.newframe.dto.account.response.AccountFundingFinanceAssetListDTO;
import com.newframe.dto.account.response.AccountOrderFundingDTO;
import com.newframe.entity.account.AccountFunding;
import com.newframe.entity.account.AccountFundingFinanceAsset;
import com.newframe.entity.order.OrderFunder;
import com.newframe.enums.SystemCode;
import com.newframe.repositories.dataQuery.account.AccountFundingFinanceAssetQuery;
import com.newframe.repositories.dataQuery.order.OrderFunderQuery;
import com.newframe.repositories.dataSlave.account.*;
import com.newframe.repositories.dataSlave.order.OrderFunderSlave;
import com.newframe.services.account.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    public JsonResult recharge(BigDecimal amount) {
        return null;
    }

    public JsonResult withdrawDeposit() {
        return null;
    }

    public JsonResult getRenterAccountInfo() {
        return null;
    }

    public JsonResult getRenterAssetAccount() {
        return null;
    }

    public JsonResult getRenterRentDetail(Integer currentPage, Integer pageSize) {
        return null;
    }

    public JsonResult getRenterOrderFinanceAccount() {
        return null;
    }

    public JsonResult listRenterOrderFinance(Integer currentPage, Integer pageSize) {
        return null;
    }

    public JsonResult getRenterOrderFinanceDetail(Long orderId) {
        return null;
    }

    public JsonResult getRenterOrderRentAccount() {
        return null;
    }

    public JsonResult listRenterOrderRentAccount(Integer currentPage, Integer pageSize) {
        return null;
    }

    public JsonResult getRenterOrderRentDetail(Long orderId) {
        return null;
    }

    public JsonResult getRenterOrderOverdueAccount() {
        return null;
    }

    public JsonResult listRenterOrderOverdue(Integer currentPage, Integer pageSize) {
        return null;
    }

    public JsonResult getRenterOrderOverdueDetail(Long orderId) {
        return null;
    }

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

    public JsonResult getFunderOrderFinancialAssets(Long uid) {
        AccountFundingFinanceAsset entity = accountFundingFinanceAssetSlave.findOne(uid);
        if (null != entity) {
            AccountFundingFinanceAssetDTO dto = new AccountFundingFinanceAssetDTO();
            BeanUtils.copyProperties(entity, dto);
            return new JsonResult(SystemCode.SUCCESS, dto);
        }
        return new JsonResult(SystemCode.SUCCESS, null);
    }

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

    public JsonResult getFunderOrderOverdueAssets() {
        return null;
    }

    public JsonResult listFunderOrderOverdue(Integer currentPage, Integer pageSize) {
        return null;
    }

    public JsonResult getFunderOrderOverdueDetail(Long orderId) {
        return null;
    }

    public JsonResult getSupplierAssetAccount() {
        return null;
    }

    public JsonResult getSupplierOrderSellAssets() {
        return null;
    }

    public JsonResult listSupplierOrderSell(Integer currentPage, Integer pageSize) {
        return null;
    }

    public JsonResult getHirerAssetAccount() {
        return null;
    }

    public JsonResult getHirerOrderOverdueAssets() {
        return null;
    }

    public JsonResult listHirerOrderOverdue(Integer currentPage, Integer pageSize) {
        return null;
    }

    public JsonResult getHirerOrderOverdueDetail(Long orderId) {
        return null;
    }

    public JsonResult getHirerOrderMaterialAssets() {
        return null;
    }

    public JsonResult listHirerOrderMaterial(Integer currentPage, Integer pageSize) {
        return null;
    }

    public JsonResult getHirerOrderMaterialDetail(Long orderId) {
        return null;
    }
}
