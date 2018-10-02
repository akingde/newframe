package com.newframe.services.order.impl;

import com.newframe.dto.OperationResult;
import com.newframe.entity.account.Account;
import com.newframe.entity.order.OrderFunder;
import com.newframe.entity.order.OrderHirer;
import com.newframe.entity.order.OrderRenter;
import com.newframe.entity.order.OrderSupplier;
import com.newframe.enums.account.AccountTypeEnum;
import com.newframe.enums.account.DealTypeEnum;
import com.newframe.enums.order.OrderResultEnum;
import com.newframe.enums.order.PatternPaymentEnum;
import com.newframe.services.account.AccountManageService;
import com.newframe.services.account.AccountService;
import com.newframe.services.order.OrderAccountOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    @Value("${residual.value.protection.scheme}")
    private BigDecimal residualValue;

    /**
     * 融资购机
     * 租赁账户可用余额中扣除保证金------>保证金 =（手机供应价-用户租机首付）×15%
     * 冻结账户可用余额中的融资首付，资金方付款之后直接解冻到资金方账户，作为首付还款
     * @return
     */
    @Override
    public OperationResult<Boolean> financing(OrderRenter orderRenter, OrderFunder orderFunder) {
        // 查询租赁商账户
        Account account = accountService.getAccount(orderRenter.getRenterId());
        if (account != null) {
            // 判断租赁商账户余额是否大于保证金金额
            BigDecimal frozenAmount = orderFunder.getMonthlyPayment();
            // 保证金 + （融资金额/融资期限）+ 意外保障金额
            BigDecimal usableAmount = orderFunder.getDeposit().add(frozenAmount);
            if (account.getUseableAmount().compareTo(usableAmount) >= 0) {
                // 账户间操作，将账户余额划转到保证金
                // 保证金增加
                accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                        DealTypeEnum.ACCOUNTTRANSFER,
                        AccountTypeEnum.MARGINASSETS,
                        orderFunder.getDeposit(),
                        new BigDecimal("0"));
                // 冻结金额增加
                // 将融资首付扣除到冻结金额，融资被拒绝解冻，融资成功直接支付融资购机还款首付
                accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                        DealTypeEnum.ACCOUNTTRANSFER,
                        AccountTypeEnum.FROZENASSETS,
                        frozenAmount,
                        new BigDecimal("0"));
                // 可用余额减少
                accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                        DealTypeEnum.ACCOUNTTRANSFER,
                        AccountTypeEnum.USEABLEASSETS,
                        usableAmount.multiply(new BigDecimal(-1)),
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
        BigDecimal frozenAmount = orderFunder.getMonthlyPayment();
        BigDecimal usableAmount = frozenAmount.add(orderFunder.getDeposit());
        // 保证金减少
        accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                DealTypeEnum.ACCOUNTTRANSFER,
                AccountTypeEnum.MARGINASSETS,
                orderFunder.getDeposit().multiply(new BigDecimal("-1")),
                new BigDecimal("0"));
        // 冻结金额减少
        accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                DealTypeEnum.ACCOUNTTRANSFER,
                AccountTypeEnum.FROZENASSETS,
                frozenAmount.multiply(new BigDecimal("-1")),
                new BigDecimal("0"));
        // 可用余额增加
        accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                DealTypeEnum.ACCOUNTTRANSFER,
                AccountTypeEnum.USEABLEASSETS,
                usableAmount,
                new BigDecimal("0"));
        // 判断租赁商账户余额是否大于保证金金额
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
            // 计算融资首付
            BigDecimal frozenAmount = orderFunder.getMonthlyPayment();
            // 计算要从租赁商账户扣除的钱：用户租机首付（作为融资购机的一部分给供应商）+ 残值保障计划（直接扣除到平台）
            BigDecimal renterUsableAmount = orderRenter.getDownPayment();
            if(Integer.valueOf(1).equals(orderFunder.getResidualScheme())){
                renterUsableAmount = renterUsableAmount.add(residualValue);
            }
            // 租赁商融资时要扣除用户租机首付
            if(renterAccount.getUseableAmount().compareTo(renterUsableAmount) < 0){
                return new OperationResult<>(OrderResultEnum.RENTER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT,false);
            }
            if(funderAccount.getUseableAmount().compareTo(orderFunder.getFinancingAmount()) < 0){
                return new OperationResult<>(OrderResultEnum.FUNDER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT,false);
            }
            // 不同账户间操作金额
            // 从租赁商账户余额扣除用户租机意外保险，给供应商，作为购买价款的一部分，剩下的由出租方出
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.USEABLEASSETS,
                    orderRenter.getAccidentBenefit().multiply(new BigDecimal(-1)),
                    new BigDecimal(0));
            // 扣除残值保障计划，直接扣除，给平台
            if(Integer.valueOf(1).equals(orderFunder.getResidualScheme())){
                accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                        DealTypeEnum.FINANCING,
                        AccountTypeEnum.USEABLEASSETS,
                        residualValue.multiply(new BigDecimal(-1)),
                        new BigDecimal(0));
            }

            // 从租赁商账户冻结金额中扣除融资还款首付
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.FROZENASSETS,
                    frozenAmount.multiply(new BigDecimal("-1")),
                    BigDecimal.ZERO);
            // 从资金方账户可用余额扣除融资金额
            accountManageService.saveAccountStatement(orderFunder.getFunderId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.USEABLEASSETS,
                    orderFunder.getFinancingAmount().multiply(new BigDecimal(-1)),
                    new BigDecimal(0));
            BigDecimal productPrice = orderRenter.getAccidentBenefit().add(orderFunder.getFinancingAmount());
            // 用户还首付
            accountManageService.saveAccountStatement(orderFunder.getFunderId(),
                    DealTypeEnum.NORMALPAY,
                    AccountTypeEnum.USEABLEASSETS,
                    frozenAmount,
                    BigDecimal.ZERO);
            // 将机器购买金额转入供应商商账户，用户租机意外保险作为 额外金额
            accountManageService.saveAccountStatement(orderSupplier.getSupplierId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.USEABLEASSETS,
                    productPrice,
                    orderRenter.getAccidentBenefit());
            // 按月返还用户保证金
            BigDecimal monthlyDeposit = orderFunder.getDeposit().divide(BigDecimal.valueOf(orderFunder.getNumberOfPeriods()),2,RoundingMode.HALF_UP);
            accountManageService.saveAccountStatement(
                    orderRenter.getRenterId(),
                    DealTypeEnum.NORMALPAY,
                    AccountTypeEnum.MARGINASSETS,
                    monthlyDeposit.multiply(new BigDecimal("-1")),
                    BigDecimal.ZERO
                    );
            accountManageService.saveAccountStatement(
                    orderRenter.getRenterId(),
                    DealTypeEnum.NORMALPAY,
                    AccountTypeEnum.USEABLEASSETS,
                    monthlyDeposit,
                    BigDecimal.ZERO
            );
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
    public OperationResult<Boolean> offlineLoan(OrderRenter orderRenter, OrderSupplier orderSupplier,OrderFunder orderFunder) {
        Account renterAccount = accountService.getAccount(orderRenter.getRenterId());
        Account supplierAccount = accountService.getAccount(orderSupplier.getSupplierId());
        if(renterAccount != null && supplierAccount != null){
            // 计算要从租赁商账户扣除的钱：用户租机意外保险（作为融资购机的一部分给供应商）+ 残值保障计划（直接扣除到平台）
            BigDecimal renterUsableAmount = orderRenter.getAccidentBenefit();
            if(Integer.valueOf(1).equals(orderFunder.getResidualScheme())){
                renterUsableAmount = renterUsableAmount.add(residualValue);
            }
            if(renterAccount.getUseableAmount().compareTo(renterUsableAmount) < 0){
                return new OperationResult<>(OrderResultEnum.RENTER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT,false);
            }
            // 从租赁商账户扣除用户租机意外保险
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.USEABLEASSETS,
                    orderRenter.getAccidentBenefit().multiply(new BigDecimal(-1)),
                    new BigDecimal(0));
            // 扣除残值保障金额
            if(Integer.valueOf(1).equals(orderFunder.getResidualScheme())){
                accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                        DealTypeEnum.FINANCING,
                        AccountTypeEnum.USEABLEASSETS,
                        residualValue.multiply(new BigDecimal(-1)),
                        new BigDecimal(0));
            }
            // 将租赁商意外保险金额打入供应商账户
            accountManageService.saveAccountStatement(orderSupplier.getSupplierId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.USEABLEASSETS,
                    orderRenter.getAccidentBenefit(),
                    new BigDecimal(0));
            // 从租赁商账户冻结金额中扣除融资首付
            BigDecimal frozenAmount = orderFunder.getMonthlyPayment();
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.FINANCING,
                    AccountTypeEnum.FROZENASSETS,
                    frozenAmount.multiply(new BigDecimal("-1")),
                    BigDecimal.ZERO);
            // 用户还首付
            accountManageService.saveAccountStatement(orderFunder.getFunderId(),
                    DealTypeEnum.NORMALPAY,
                    AccountTypeEnum.USEABLEASSETS,
                    frozenAmount,
                    BigDecimal.ZERO);
            // 按月返还用户保证金
            BigDecimal monthlyDeposit = orderFunder.getDeposit().divide(BigDecimal.valueOf(orderFunder.getNumberOfPeriods()),2,RoundingMode.HALF_UP);
            accountManageService.saveAccountStatement(
                    orderRenter.getRenterId(),
                    DealTypeEnum.NORMALPAY,
                    AccountTypeEnum.MARGINASSETS,
                    monthlyDeposit.multiply(new BigDecimal("-1")),
                    BigDecimal.ZERO
            );
            accountManageService.saveAccountStatement(
                    orderRenter.getRenterId(),
                    DealTypeEnum.NORMALPAY,
                    AccountTypeEnum.USEABLEASSETS,
                    monthlyDeposit,
                    BigDecimal.ZERO
            );
            return new OperationResult<>(true);
        }
        return new OperationResult<>(OrderResultEnum.ACCOUNT_NO_EXIST,false);
    }

    /**
     * 冻结租赁商租机首付
     * @param orderRenter
     * @param orderHirer
     * @return
     */
    @Override
    public OperationResult<Boolean> frozenRentDownPayment(OrderRenter orderRenter, OrderHirer orderHirer){
        Account renterAccount = accountService.getAccount(orderRenter.getRenterId());
        if(renterAccount == null){
            return new OperationResult<>(OrderResultEnum.ACCOUNT_NO_EXIST,false);
        }
        // 分期支付，冻结 月租金 + 意外保险
        if(PatternPaymentEnum.INSTALMENT_PAYMENT.getValue().equals(orderHirer.getPatternPayment())){
            BigDecimal frozenAmount = orderHirer.getMonthlyPayment().add(orderHirer.getAccidentBenefit());
            if(renterAccount.getUseableAmount().compareTo(frozenAmount) < 0){
                return new OperationResult<>(OrderResultEnum.RENTER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT,false);
            }
            // 租赁商账户可用余额扣除
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.ACCOUNTTRANSFER,
                    AccountTypeEnum.USEABLEASSETS,
                    frozenAmount.multiply(new BigDecimal("-1")),
                    orderHirer.getAccidentBenefit());
            // 租赁商账户可用余额增加
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.ACCOUNTTRANSFER,
                    AccountTypeEnum.FROZENASSETS,
                    frozenAmount,
                    orderHirer.getAccidentBenefit());
            return new OperationResult<>(true);
        }
        // 全款支付，冻结金额 = 订单金额 + 意外保险
        if(PatternPaymentEnum.FULL_PAYMENT.getValue().equals(orderHirer.getPatternPayment())){
            BigDecimal frozenAmount = orderHirer.getOrderAmount().add(orderHirer.getAccidentBenefit());
            if(renterAccount.getUseableAmount().compareTo(frozenAmount) < 0){
                return new OperationResult<>(OrderResultEnum.RENTER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT,false);
            }
            // 租赁商账户可用余额扣除
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.ACCOUNTTRANSFER,
                    AccountTypeEnum.USEABLEASSETS,
                    frozenAmount.multiply(new BigDecimal("-1")),
                    orderHirer.getAccidentBenefit());
            // 租赁商账户可用余额减少
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.ACCOUNTTRANSFER,
                    AccountTypeEnum.FROZENASSETS,
                    frozenAmount,
                    orderHirer.getAccidentBenefit());
            return new OperationResult<>(true);
        }
        return new OperationResult<>(OrderResultEnum.ACCOUNT_NO_EXIST,false);
    }

    /**
     * 解冻用户租机首付
     * 出租方拒绝订单之后需要退还冻结金额
     * @param orderRenter 租赁商订单
     * @param orderHirer 出租方订单
     * @return 操作结果
     */
    @Override
    public OperationResult<Boolean> unfrozenRentDownPayment(OrderRenter orderRenter, OrderHirer orderHirer){
        Account renterAccount = accountService.getAccount(orderRenter.getRenterId());
        if(renterAccount == null){
            return new OperationResult<>(OrderResultEnum.ACCOUNT_NO_EXIST,false);
        }
        // 分期支付，冻结 月租金 + 意外保险
        if(PatternPaymentEnum.INSTALMENT_PAYMENT.getValue().equals(orderHirer.getPatternPayment())){
            BigDecimal frozenAmount = orderHirer.getMonthlyPayment().add(orderHirer.getAccidentBenefit());
            if(renterAccount.getUseableAmount().compareTo(frozenAmount) < 0){
                return new OperationResult<>(OrderResultEnum.RENTER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT,false);
            }
            // 租赁商账户可用余额增加
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.ACCOUNTTRANSFER,
                    AccountTypeEnum.USEABLEASSETS,
                    frozenAmount,
                    orderHirer.getAccidentBenefit());
            // 租赁商账户冻结金额减少
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.ACCOUNTTRANSFER,
                    AccountTypeEnum.FROZENASSETS,
                    frozenAmount.multiply(new BigDecimal("-1")),
                    orderHirer.getAccidentBenefit());
            return new OperationResult<>(true);
        }
        // 全款支付，冻结金额 = 订单金额 + 意外保险
        if(PatternPaymentEnum.FULL_PAYMENT.getValue().equals(orderHirer.getPatternPayment())){
            BigDecimal frozenAmount = orderHirer.getOrderAmount().add(orderHirer.getAccidentBenefit());
            if(renterAccount.getUseableAmount().compareTo(frozenAmount) < 0){
                return new OperationResult<>(OrderResultEnum.RENTER_ACCOUNT_USABLE_AMOUNT_INSUFFICIENT,false);
            }
            // 租赁商账户可用余额增加
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.ACCOUNTTRANSFER,
                    AccountTypeEnum.USEABLEASSETS,
                    frozenAmount,
                    orderHirer.getAccidentBenefit());
            // 租赁商账户可用余额减少
            accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                    DealTypeEnum.ACCOUNTTRANSFER,
                    AccountTypeEnum.FROZENASSETS,
                    frozenAmount.multiply(new BigDecimal("-1")),
                    orderHirer.getAccidentBenefit());
            return new OperationResult<>(true);
        }
        return new OperationResult<>(OrderResultEnum.ACCOUNT_NO_EXIST,false);
    }

    @Override
    public OperationResult<Boolean> payDownPayment(OrderRenter orderRenter,OrderHirer orderHirer){
        Account renterAccount = accountService.getAccount(orderRenter.getRenterId());
        Account hirerAmount = accountService.getAccount(orderHirer.getLessorId());
        if(renterAccount != null && hirerAmount != null){
            if(PatternPaymentEnum.INSTALMENT_PAYMENT.getValue().equals(orderHirer.getPatternPayment())) {
                // 解冻租赁商冻结金额
                BigDecimal frozenAmount = orderHirer.getMonthlyPayment().add(orderHirer.getAccidentBenefit());
                accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                        DealTypeEnum.NORMALPAY,
                        AccountTypeEnum.FROZENASSETS,
                        frozenAmount.multiply(new BigDecimal("-1")),
                        orderHirer.getAccidentBenefit()
                        );
                // 付租机首付
                accountManageService.saveAccountStatement(orderHirer.getLessorId(),
                        DealTypeEnum.NORMALPAY,
                        AccountTypeEnum.USEABLEASSETS,
                        frozenAmount,
                        orderHirer.getAccidentBenefit());
                return new OperationResult<>(true);
            }
            if(PatternPaymentEnum.FULL_PAYMENT.getValue().equals(orderHirer.getPatternPayment())) {
                // 解冻租赁商冻结金额
                BigDecimal frozenAmount = orderHirer.getOrderAmount().add(orderHirer.getAccidentBenefit());
                accountManageService.saveAccountStatement(orderRenter.getRenterId(),
                        DealTypeEnum.NORMALPAY,
                        AccountTypeEnum.FROZENASSETS,
                        frozenAmount.multiply(new BigDecimal("-1")),
                        orderHirer.getAccidentBenefit()
                );
                // 付租机首付
                accountManageService.saveAccountStatement(orderHirer.getLessorId(),
                        DealTypeEnum.NORMALPAY,
                        AccountTypeEnum.USEABLEASSETS,
                        frozenAmount,
                        orderHirer.getAccidentBenefit());
                return new OperationResult<>(true);
            }
        }
        return new OperationResult<>(OrderResultEnum.ACCOUNT_NO_EXIST,false);
    }

    /**
     * 获取用户账户
     * @param uid 用户uid
     * @return 返回结果
     */
    @Override
    public Account getAccount(Long uid){
        if(uid == null){
            return null;
        }
        return accountService.getAccount(uid);
    }

}
