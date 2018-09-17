package com.newframe.services.order.impl;

import com.newframe.dto.OperationResult;
import com.newframe.entity.account.Account;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.order.OrderSupplier;
import com.newframe.enums.account.AccountTypeEnum;
import com.newframe.enums.account.DealTypeEnum;
import com.newframe.enums.order.OrderResultEnum;
import com.newframe.services.account.AccountManageService;
import com.newframe.services.account.AccountService;
import com.newframe.services.order.OrderAccountOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author kfm
 * @date 2018.09.17 9:58
 */
@Service
public class OrderAccountOperationServiceImpl implements OrderAccountOperationService {

    @Autowired
    AccountManageService accountManageService;

    @Autowired
    AccountService accountService;

    /**
     * 融资购机
     * 租赁账户可用余额中扣除保证金------>保证金 =（手机供应价-用户租机首付）×15%
     *
     * @return
     */
    @Override
    public OperationResult<Boolean> financing(OrderRenter orderRenter, OrderFunder orderFunder) {
        // 查询租赁商账户
        Account account = accountService.getAccount(orderRenter.getRenterId());
        if (account != null) {
            // 判断租赁商账户余额是否大于保证金金额
            if (account.getUseableAmount().compareTo(orderFunder.getDeposit()) >= 0) {
                // 账户间操作，将账户余额划转到保证金
                // 保证金增加
                accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                        DealTypeEnum.ACCOUNTTRANSFER,
                        AccountTypeEnum.MARGINASSETS,
                        orderFunder.getDeposit(),
                        new BigDecimal("0"));
                // 可用余额减少
                accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                        DealTypeEnum.ACCOUNTTRANSFER,
                        AccountTypeEnum.USEABLEASSETS,
                        orderFunder.getDeposit().multiply(new BigDecimal(-1)),
                        new BigDecimal("0"));
                return new OperationResult<>(OrderResultEnum.SUCCESS,true);
            }
        }
        return new OperationResult<>(OrderResultEnum.ACCOUNT_NO_EXIST,false);
    }

    /**
     * 退还保证金
     * 资金方拒绝审核和租赁商向资金方还款完成后退还保证金
     *
     * @return
     */
    @Override
    public OperationResult<Boolean> releaseMarginBalance(OrderRenter orderRenter, OrderFunder orderFunder) {

        // 账户间操作，将账户余额划转到保证金
        // 保证金减少
        accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                DealTypeEnum.ACCOUNTTRANSFER,
                AccountTypeEnum.MARGINASSETS,
                orderFunder.getDeposit().multiply(new BigDecimal(-1)),
                new BigDecimal("0"));
        // 可用余额增加
        accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                DealTypeEnum.ACCOUNTTRANSFER,
                AccountTypeEnum.USEABLEASSETS,
                orderFunder.getDeposit(),
                new BigDecimal("0"));
        return new OperationResult<>(OrderResultEnum.SUCCESS,true);

    }

    /**
     * 资金方线上放款
     * 租赁商账户可用余额中扣除用户租机首付；
     * 资金方账户扣除融资金额【手机的供应价-用户租机首付】；
     * （扣除金额全部转到供应商账户）
     * @return
     */
    @Override
    public OperationResult<Boolean> onlineLoan(OrderRenter orderRenter, OrderFunder orderFunder, OrderSupplier orderSupplier) {
        Account renterAccount = accountService.getAccount(orderRenter.getRenterId());
        Account funderAccount = accountService.getAccount(orderFunder.getFunderId());
        if(renterAccount != null && funderAccount != null){
            if(renterAccount.getUseableAmount().compareTo(orderRenter.getDownPayment()) < 0){
                return new OperationResult<>(OrderResultEnum.RENTER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT,false);
            }
            if(funderAccount.getUseableAmount().compareTo(orderFunder.getFinancingAmount()) < 0){
                return new OperationResult<>(OrderResultEnum.FUNDER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT,false);
            }
            // 不同账户间操作金额
            // 从租赁商账户余额扣除用户租机首付
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.USEABLEASSETS,
                    orderRenter.getDownPayment().multiply(new BigDecimal(-1)),
                    new BigDecimal(0));
            // 从资金方账户可用余额扣除融资金额
            accountManageService.saveAccountStatement(orderFunder.getFunderId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.USEABLEASSETS,
                    orderFunder.getFinancingAmount().multiply(new BigDecimal(-1)),
                    new BigDecimal(0));
            BigDecimal productPrice = orderRenter.getDownPayment().add(orderFunder.getFinancingAmount());
            // 将机器购买加个转入租赁商账户，用户租机首付作为 额外金额
            accountManageService.saveAccountStatement(orderSupplier.getSupplierId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.USEABLEASSETS,
                    productPrice,
                    orderRenter.getDownPayment());
            return new OperationResult<>(OrderResultEnum.SUCCESS,true);
        }

        return new OperationResult<>(OrderResultEnum.ACCOUNT_NO_EXIST,false);
    }

    /**
     * 资金方线下放款
     * 租赁商账户可用余额中扣除用户租机首付；
     * 资金方线下打款，不操作资金方账户；
     * （租赁商扣除金额转入供应商账户）
     * @return
     */
    @Override
    public OperationResult<Boolean> offlineLoan(OrderRenter orderRenter, OrderSupplier orderSupplier) {
        Account renterAccount = accountService.getAccount(orderRenter.getRenterId());
        Account supplierAccount = accountService.getAccount(orderSupplier.getSupplierId());
        if(renterAccount != null && supplierAccount != null){
            if(renterAccount.getUseableAmount().compareTo(orderRenter.getDownPayment()) < 0){
                return new OperationResult<>(OrderResultEnum.RENTER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT,false);
            }
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.USEABLEASSETS,
                    orderRenter.getDownPayment().multiply(new BigDecimal(-1)),
                    new BigDecimal(0));
            accountManageService.saveAccountStatement(orderSupplier.getSupplierId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.USEABLEASSETS,
                    orderRenter.getDownPayment(),
                    new BigDecimal(0));
        }
        return new OperationResult<>(OrderResultEnum.ACCOUNT_NO_EXIST,false);
    }

}
