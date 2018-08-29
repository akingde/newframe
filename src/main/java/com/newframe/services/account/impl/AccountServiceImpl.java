package com.newframe.services.account.impl;

import com.newframe.controllers.JsonResult;
import com.newframe.dto.account.response.AccountFundingDTO;
import com.newframe.entity.account.AccountFunding;
import com.newframe.enums.SystemCode;
import com.newframe.repositories.dataSlave.account.*;
import com.newframe.services.account.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    @Override
    public JsonResult getRenterOrderRentAccount() {
        return null;
    }

    @Override
    public JsonResult listRenterOrderRentAccount(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getRenterOrderRentDetail(Long orderId) {
        return null;
    }

    @Override
    public JsonResult getRenterOrderOverdueAccount() {
        return null;
    }

    @Override
    public JsonResult listRenterOrderOverdue(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getRenterOrderOverdueDetail(Long orderId) {
        return null;
    }

    @Override
    public JsonResult getFunderAssetAccount(Long uid) {
        AccountFunding accountFunding = accountFundingSlave.findOne(uid);
        if (null != accountFunding) {
            //DueAmount 转化成 DueInAmount
            AccountFundingDTO accountFundingDTO = new AccountFundingDTO();
            BeanUtils.copyProperties(accountFunding, accountFundingDTO);
            accountFundingDTO.setDueInAmount(accountFunding.getDueAmount());
            return new JsonResult(SystemCode.SUCCESS, accountFundingDTO);
        }
        return new JsonResult(SystemCode.SUCCESS, null);
    }

    @Override
    public JsonResult getFunderOrderFinancialAssets() {
        return null;
    }

    @Override
    public JsonResult listFunderOrderInvestment(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getFunderOrderInvestmentDetail(Long orderId) {
        return null;
    }

    @Override
    public JsonResult getFunderOrderOverdueAssets() {
        return null;
    }

    @Override
    public JsonResult listFunderOrderOverdue(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getFunderOrderOverdueDetail(Long orderId) {
        return null;
    }

    @Override
    public JsonResult getSupplierAssetAccount() {
        return null;
    }

    @Override
    public JsonResult getSupplierOrderSellAssets() {
        return null;
    }

    @Override
    public JsonResult listSupplierOrderSell(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getHirerAssetAccount() {
        return null;
    }

    @Override
    public JsonResult getHirerOrderOverdueAssets() {
        return null;
    }

    @Override
    public JsonResult listHirerOrderOverdue(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getHirerOrderOverdueDetail(Long orderId) {
        return null;
    }

    @Override
    public JsonResult getHirerOrderMaterialAssets() {
        return null;
    }

    @Override
    public JsonResult listHirerOrderMaterial(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public JsonResult getHirerOrderMaterialDetail(Long orderId) {
        return null;
    }
}
