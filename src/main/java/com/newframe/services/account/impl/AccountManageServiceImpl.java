package com.newframe.services.account.impl;

import com.google.common.collect.Lists;
import com.newframe.dto.OperationResult;
import com.newframe.dto.account.*;
import com.newframe.entity.account.*;
import com.newframe.entity.user.UserAddress;
import com.newframe.entity.user.UserBaseInfo;
import com.newframe.entity.user.UserPwd;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.enums.BizErrorCode;
import com.newframe.enums.account.AccountTypeEnum;
import com.newframe.enums.account.DealTypeEnum;
import com.newframe.enums.account.OrderTypeEnum;
import com.newframe.enums.account.WithholdEnum;
import com.newframe.enums.order.PayStatusEnum;
import com.newframe.services.account.AccountManageService;
import com.newframe.services.account.AccountService;
import com.newframe.services.userbase.UserAddressService;
import com.newframe.services.userbase.UserBaseInfoService;
import com.newframe.services.userbase.UserPwdService;
import com.newframe.services.userbase.UserRentMerchantService;
import com.newframe.utils.TimeUtils;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:wangdong
 * @description:
 */
@Service
public class AccountManageServiceImpl implements AccountManageService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserBaseInfoService userBaseInfoService;

    @Autowired
    private UserPwdService userPwdService;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private UserRentMerchantService userRentMerchantService;

    @Autowired
    private IdGlobalGenerator idGlobal;

    private final BigDecimal overdueRate = BigDecimal.valueOf(0.2);

    private final BigDecimal depositRate = BigDecimal.valueOf(0.15);

    /**
     * 租赁商获取账户信息
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<RenterAccountInfo> getRenterAccountInfo(Long uid) {
        if (null == uid) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        RenterBaseInfo renterBaseInfo = new RenterBaseInfo();
        List<RenterAddress> renterAddresses = Lists.newArrayList();
        RenterAuthorization renterAuthorization = new RenterAuthorization();

        UserBaseInfo userBaseInfo = userBaseInfoService.findOne(uid);
        if (null != userBaseInfo && StringUtils.isNotEmpty(userBaseInfo.getPhoneNumber())) {
            renterBaseInfo.setMobile(userBaseInfo.getPhoneNumber());
        }
        UserPwd userPwd = userPwdService.findByUid(uid);
        if (StringUtils.isNotEmpty(userPwd.getLoginPwd())) {
            renterBaseInfo.setHasPassword(true);
        }
        renterBaseInfo.setHasPassword(false);

        List<UserAddress> userAddresses = userAddressService.findUserAddressList(uid);
        if (CollectionUtils.isNotEmpty(userAddresses)) {
            userAddresses.forEach(userAddress -> {
                RenterAddress renterAddress = new RenterAddress();
                renterAddress.setCountry("中国");
                renterAddress.setProvince(userAddress.getProvinceName());
                renterAddress.setCity(userAddress.getCityName());
                renterAddress.setRegion(userAddress.getCountyName());
                renterAddress.setDetail(userAddress.getConsigneeAddress());
                renterAddress.setMobile(userAddress.getMobile());
                renterAddress.setName(userAddress.getConsigneeName());
                renterAddresses.add(renterAddress);
            });
        }
        UserRentMerchant userRentMerchant = userRentMerchantService.findOne(uid);
        if (null != userRentMerchant) {
            renterAuthorization.setBusinessLicense(userRentMerchant.getBusinessLicenseNumber());
            renterAuthorization.setBusinessLicenseUrl(userRentMerchant.getBusinessLicenseFile());
            renterAuthorization.setAcademicDiplomasUrl(userRentMerchant.getHighestDegreeDiplomaFile());
            renterAuthorization.setDrivingLicenseUrl(userRentMerchant.getDrivingLicenseFile());
            renterAuthorization.setHouseContractUrl(userRentMerchant.getHouseProprietaryCertificateFile());
            renterBaseInfo.setLegalName(userRentMerchant.getLegalEntity());
            renterBaseInfo.setIdNumber(userRentMerchant.getLegalEntityIdNumber());
        }
        List<AccountRenterAppointSupplier> accountRenterAppointSuppliers = accountService.listAccountRenterAppointSupplier(uid);

        return new OperationResult<>(new RenterAccountInfo(renterBaseInfo, renterAddresses, renterAuthorization, accountRenterAppointSuppliers));
    }

    /**
     * 获取租赁商的账户资产
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<Account> getRenterAssetAccount(Long uid) {
        if (null == uid) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }

        Account accountRenter = accountService.getAccountRenter(uid);

        List<AccountRenterRent> accountRenterRents = accountService.listAccountRenterRent(uid);
        if (CollectionUtils.isEmpty(accountRenterRents)){
            return new OperationResult<>(accountRenter);
        }
        //计算待收金额
        BigDecimal dueAmount = accountRenterRents.stream().map(AccountRenterRent::getDueInAccount).reduce(BigDecimal.ZERO,BigDecimal::add);

        //获取本月最后一天的23：59：59
        Integer lastDayOfMonth = TimeUtils.getLastDayOfMonth();
        List<AccountRenterRepay> accountRenterRepays = accountService.listAccountRenterRepay(uid,lastDayOfMonth, WithholdEnum.NO);
        if (CollectionUtils.isEmpty(accountRenterRepays)){
            return new OperationResult<>(accountRenter);
        }
        //计算本月应收
        BigDecimal currentMonthPayment = accountRenterRepays.stream().map(AccountRenterRepay::getOrderAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
        //计算本月保证金返还
        BigDecimal marginAdvances = accountRenterRepays.stream().map(AccountRenterRepay::getCashDeposit).reduce(BigDecimal.ZERO,BigDecimal::add);
        accountRenter.setAccount(dueAmount,currentMonthPayment,marginAdvances);
        accountService.updateAccount(accountRenter);

        return new OperationResult<>(accountRenter);
    }

    /**
     * @param uid
     * @param associatedOrderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public OperationResult<AccountRenterRentInfo> listRenterOrderRent(Long uid, String associatedOrderStatus, Integer currentPage, Integer pageSize) {
        if (null == uid) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == currentPage || null == pageSize) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        AccountRenterRentInfo accountRenterRentInfo = new AccountRenterRentInfo();
        Page<AccountRenterRent> accountRenterRentPage = accountService.getAccountRenterRent(uid, associatedOrderStatus, currentPage, pageSize);
        List<AccountRenterRent> accountRenterRents = accountRenterRentPage.getContent();
        accountRenterRentInfo.setList(accountRenterRents);
        accountRenterRentInfo.setTotal(accountRenterRentPage.getTotalElements());
        return new OperationResult<>(accountRenterRentInfo);
    }

    /**
     * 租赁商获取订单融资账户
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<AccountRenterFinancingMachine> getRenterOrderFinanceAccount(Long uid) {
        if (null == uid) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        //在调用这个接口的时候，去查询一次，然后将查询的接口写入到数据库
        //AccountRenterFinancing
        //计算订单融资金额、已经结清的融资本息、未结清的融资本息、本月应还
        RenterFinanceStatistics renterFinanceStatistics = accountService.getorderFinancing(uid);
        //BigDecimal orderFinancing = renterFinanceStatistic;

        //获取本月初的时间戳
        Integer firstDayOfMonth = TimeUtils.getFirstDayOfMonth();
        Integer lastDayOfMonth = TimeUtils.getLastDayOfMonth();
        List<AccountRenterRepay> accountRenterRepays = accountService.listAccountRenterRepay(uid, firstDayOfMonth, lastDayOfMonth);
        //计算本月应还
        BigDecimal monthShouldRepay = BigDecimal.ZERO;
        if (CollectionUtils.isNotEmpty(accountRenterRepays)) {
            monthShouldRepay = accountRenterRepays.stream().map(AccountRenterRepay::getOrderAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        //先查询，没有的话，往数据库插入一条数据库
        AccountRenterFinancingMachine accountRenterFinancingMachine = accountService.getAccountRenterFinancingMachine(uid);
        if (null == accountRenterFinancingMachine) {
            accountService.saveAccountRenterFinancingMachine(uid);
        }
        //更新最新的这条数据
        AccountRenterFinancingMachine financingMachine = new AccountRenterFinancingMachine();
        if (null == renterFinanceStatistics) {
            financingMachine.setAccountRenterFinancingMachine(uid, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, monthShouldRepay);
        } else {
            financingMachine.setAccountRenterFinancingMachine(uid, renterFinanceStatistics.getOrderFinancing(), renterFinanceStatistics.getSettleFinancing(), renterFinanceStatistics.getUnsettledFinancing(), monthShouldRepay);
        }
        AccountRenterFinancingMachine machine = accountService.updateAccountRenterFinancingMachine(financingMachine);

        //查处最新的一条数据
        AccountRenterFinancingMachine renterFinancingMachine = accountService.getAccountRenterFinancingMachine(uid);

        return new OperationResult<>(renterFinancingMachine);
    }

    /**
     * 我是租赁商订单融资账户订单融资列表
     *
     * @param uid
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public OperationResult<RenterOrderFinanceInfo> listRenterOrderFinance(Long uid, Integer repaymentStatus, Integer orderStatus, Integer currentPage, Integer pageSize) {

        if (null == uid) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == currentPage || null == pageSize) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        RenterOrderFinanceInfo renterOrderFinanceInfo = new RenterOrderFinanceInfo();
        Page<AccountRenterFinancing> accountRenterFinancings = accountService.getAccountRenterFinancing(uid, repaymentStatus, orderStatus, currentPage, pageSize);
        List<AccountRenterFinancing> accountRenterFinancingList = accountRenterFinancings.getContent();

        renterOrderFinanceInfo.setList(accountRenterFinancingList);
        renterOrderFinanceInfo.setTotal(accountRenterFinancings.getTotalElements());
        return new OperationResult<>(renterOrderFinanceInfo);
    }

    /**
     * 我是租赁商订单融资账户订单融资列表查看订单详情
     *
     * @param orderId
     * @return
     */
    @Override
    public OperationResult<List<AccountRenterRepay>> getRenterOrderFinanceDetail(Long orderId) {
        if (null == orderId) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        List<AccountRenterRepay> accountRenterRepay = accountService.listAccountRenterRepay(orderId);

        return new OperationResult<>(accountRenterRepay);
    }

    /**
     * 9.获取租赁商租赁账户
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<AccountRenterRentMachine> getRenterOrderRentAccount(Long uid) {
        if (null == uid) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }

        AccountRenterRentMachine accountRenterRentMachine = accountService.getAccountRenterRentMachine(uid);
        //如果没有数据，就初始化一条数据
        if (null == accountRenterRentMachine) {
            AccountRenterRentMachine machine = new AccountRenterRentMachine();
            machine.setAccountRenterRentMachine(uid, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
            accountService.saveAccountRenterRentMachine(machine);
        }
        //如果不为空，则执行更新的操作，计算
        RentMachineStatistics rentMachineStatistics = accountService.getRentMachineStatistics(uid);
        accountRenterRentMachine.setAccountRenterRentMachine(uid, rentMachineStatistics);

        //将最新的结果更新到数据库
        accountService.updateAccountRenterRentMachine(accountRenterRentMachine);

        return new OperationResult<>(accountRenterRentMachine);
    }

    /**
     * 10.我是租赁商租赁账户租赁明细列表
     *
     * @param uid
     * @param payStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public OperationResult<RenterOrderRentDetailInfo> listRenterOrderRentAccount(Long uid, Integer payStatus, Integer currentPage, Integer pageSize) {
        if (null == uid) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == currentPage || null == pageSize) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        RenterOrderRentDetailInfo renterOrderRentDetailInfo = new RenterOrderRentDetailInfo();
        Page<AccountRenterRentDetail> accountRenterRentDetails = accountService.getAccountRenterRentDetail(uid, payStatus, currentPage, pageSize);
        List<AccountRenterRentDetail> accountRenterRentDetailList = accountRenterRentDetails.getContent();

        renterOrderRentDetailInfo.setList(accountRenterRentDetailList);
        renterOrderRentDetailInfo.setTotal(accountRenterRentDetails.getTotalElements());
        return new OperationResult<>(renterOrderRentDetailInfo);
    }

    /**
     * 12.获取租赁商订单逾期账户
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<AccountRenterOverdueAsset> getAccountRenterOverdueAsset(Long uid) {
        if (null == uid) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }

        AccountRenterOverdueAsset accountRenterOverdueAsset = accountService.getAccountRenterOverdueAsset(uid);
        //如果没有，需要初始化一条数据
        if (null == accountRenterOverdueAsset) {
            AccountRenterOverdueAsset asset = new AccountRenterOverdueAsset();
            asset.setAccountRenterOverdueAsset(uid, BigDecimal.ZERO, 0, BigDecimal.ZERO, BigDecimal.ZERO);
            accountService.saveAccountRenterOverdueAsset(asset);
        }
        AccountRenterOverdueAsset overdueAsset = accountService.getAccountRenterOverdueAsset(uid);
        //查询逾期的订单
        List<AccountRenterOverdueDetail> renterOverdueDetails = accountService.listAccountRenterOverdueDetail(uid, 2);
        if (CollectionUtils.isEmpty(renterOverdueDetails)) {
            overdueAsset.setAccountRenterOverdueAsset(uid, BigDecimal.ZERO, 0, BigDecimal.ZERO, BigDecimal.ZERO);
        }
        //计算逾期金额合计
        BigDecimal totalOverdueAccount = renterOverdueDetails.stream().map(AccountRenterOverdueDetail::getInvestAccount).reduce(BigDecimal.ZERO, BigDecimal::add);
        //计算逾期笔数
        Integer overdueNumber = renterOverdueDetails.size();
        //计算逾期率
        //获取融资列表总订单
        List<AccountRenterFinancing> accountRenterFinancingList = accountService.listAccountRenterFinancing(uid, PayStatusEnum.NORMAL);
        List<AccountRenterRentDetail> accountRenterRentDetailList = accountService.listAccountRenterRentDetail(uid, PayStatusEnum.NORMAL);
        //正常订单的笔数
        Integer normalNumber = accountRenterFinancingList.size() + accountRenterRentDetailList.size();
        //除数不能为0
        if (normalNumber.equals(0)){
            return new OperationResult<>(overdueAsset);
        }
        BigDecimal overdueRate = new BigDecimal(overdueNumber).divide(new BigDecimal(normalNumber), 2, RoundingMode.HALF_UP);
        overdueAsset.setAccountRenterOverdueAsset(uid, totalOverdueAccount, overdueNumber, overdueRate, BigDecimal.ZERO);
        //更新一下
        accountService.updateAccountRenterOverdueAsset(overdueAsset);
        return new OperationResult<>(overdueAsset);
    }

    /**
     * 13.我是租赁商订单逾期账户下租赁明细列表
     *
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public OperationResult<RenterOrderOverdueDetailInfo> listRenterOrderOverdue(Long uid, Integer currentPage, Integer pageSize) {

        if (null == uid) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == currentPage || null == pageSize) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        //用uid和逾期状态去查租机的订单
        List<AccountRenterRentDetail> accountRenterRentDetailList = accountService.listAccountRenterRentDetail(uid, PayStatusEnum.OVERDUE);
        //用uid和逾期状态去查融资订单的订单
        List<AccountRenterFinancing> accountRenterFinancingList = accountService.listAccountRenterFinancing(uid, PayStatusEnum.OVERDUE);

        //保存租机逾期的订单
        if (CollectionUtils.isNotEmpty(accountRenterRentDetailList)) {
            List<AccountRenterOverdueDetail> rentMachins = Lists.newArrayList();
            accountRenterRentDetailList.forEach(accountRenterRentDetail -> {
                AccountRenterOverdueDetail detail = new AccountRenterOverdueDetail();
                detail.setAccountRenterOverdueDetail(uid, accountRenterRentDetail);
                detail.setId(idGlobal.getSeqId(AccountRenterOverdueDetail.class));
                rentMachins.add(detail);
            });
            accountService.saveAccountRenterOverdueDetails(rentMachins);
        }

        //保存融资购机的逾期订单
        if (CollectionUtils.isNotEmpty(accountRenterFinancingList)) {
            List<AccountRenterOverdueDetail> rentMachins = Lists.newArrayList();
            accountRenterFinancingList.forEach(accountRenterFinancing -> {
                AccountRenterOverdueDetail detail = new AccountRenterOverdueDetail();
                detail.setAccountRenterFinancing(uid, accountRenterFinancing);
                detail.setId(idGlobal.getSeqId(AccountRenterOverdueDetail.class));
                rentMachins.add(detail);
            });

            accountService.saveAccountRenterOverdueDetails(rentMachins);
        }

        RenterOrderOverdueDetailInfo renterOrderRentDetailInfo = new RenterOrderOverdueDetailInfo();
        Page<AccountRenterOverdueDetail> accountRenterOverdueDetails = accountService.getAccountRenterOverdueDetail(uid, currentPage, pageSize);
        List<AccountRenterOverdueDetail> accountRenterOverdueDetailList = accountRenterOverdueDetails.getContent();

        renterOrderRentDetailInfo.setList(accountRenterOverdueDetailList);
        renterOrderRentDetailInfo.setTotal(accountRenterOverdueDetails.getTotalElements());
        return new OperationResult<>(renterOrderRentDetailInfo);
    }

    /**
     * 这个是用户模块在创建用户的时候，需要默认一个创建账户
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccount(Long uid) {
        if (null == uid) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        Account account = new Account();
        account.setAccount(uid);
        Account result = accountService.saveAccount(account);
        if (null == result) {
            return new OperationResult<>(false);
        }
        return new OperationResult<>(true);
    }

    /**
     * 租赁商账户资产
     * 租赁明细
     * 由订单中心那边，调用，将相关信息插入到这张表AccountRenterRent
     *
     * @param uid
     * @param orderId
     * @param relevanceOrderId
     * @param receivableAccount 应收
     * @param receivedAccount   已收
     * @param dueInAccount      待收金额
     * @param residueTime       剩余期数
     * @param collectMoney      收款账户
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountRenterRent(Long uid, Long orderId, String relevanceOrderId, BigDecimal receivableAccount, BigDecimal receivedAccount, BigDecimal dueInAccount, Integer residueTime, String collectMoney) {
        if (null == uid || null == orderId || null == relevanceOrderId || null == receivableAccount || null == receivedAccount || null == dueInAccount) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        AccountRenterRent accountRenterRent = new AccountRenterRent();
        accountRenterRent.setAccountRenterRent(uid, orderId, relevanceOrderId, receivableAccount, receivedAccount, dueInAccount, residueTime, collectMoney);
        AccountRenterRent result = accountService.saveAccountRenterRent(accountRenterRent);
        if (null == result) {
            return new OperationResult<>(false);
        }
        return new OperationResult<>(true);
    }

    /**
     * 租赁商账户资产
     * 租机账户
     * 由订单中心那边，调用，将相关信息插入到这张表AccountRenterRent
     *
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
     * @param accidentInsurance
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountRenterRentDetail(Long uid, Long orderId, String associatedOrderId, String productBrand, String productModel, String productColour, String productStorage, String productMemory,
                                                                BigDecimal totalRentAccount, Integer monthNumber, BigDecimal payedAccount, BigDecimal unpayedAccount, Integer residueTime, String collectMoney, BigDecimal accidentInsurance) {
        if (null == uid || null == orderId || null == associatedOrderId || StringUtils.isEmpty(productBrand) || StringUtils.isEmpty(productModel) ||
                StringUtils.isEmpty(productColour) || StringUtils.isEmpty(productStorage) || StringUtils.isEmpty(productMemory) || null == totalRentAccount || null == monthNumber || null == payedAccount || null == unpayedAccount) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        AccountRenterRentDetail accountRenterRentDetail = new AccountRenterRentDetail();
        accountRenterRentDetail.setAccountRenterRentDetail(uid, orderId, associatedOrderId, productBrand, productModel, productColour, productStorage, productMemory, totalRentAccount, monthNumber, payedAccount, unpayedAccount);
        AccountRenterRentDetail result = accountService.saveAccountRenterRentDetail(accountRenterRentDetail);
        OperationResult<Boolean> renterRent = saveAccountRenterRent(uid, orderId, associatedOrderId, totalRentAccount, payedAccount, unpayedAccount, residueTime, collectMoney);
        OperationResult<Boolean> renterRepay = saveAccountRenterRepay(orderId, uid, monthNumber, accidentInsurance, totalRentAccount, OrderTypeEnum.RENT, BigDecimal.ZERO);
        if (null == result || !renterRent.getEntity() || !renterRepay.getEntity()) {
            return new OperationResult<>(false);
        }
        return new OperationResult<>(true);
    }

    /**
     * 租赁商账户资产
     * 订单融资
     * 能传的字段，按照接口要求传，不能提供的请传一个0
     *
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
     * @param accidentInsurance
     * @param cashDeposit
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountRenterFinancing(Long uid, Long orderId, String associatedOrderId, BigDecimal financingAmount, Integer financingMaturity, BigDecimal financingPrincipalInterest,
                                                               BigDecimal financingInterest, BigDecimal settlePrincipalInterest, BigDecimal settleInterest, BigDecimal unsettlePrincipalInterest,
                                                               BigDecimal unsettleInterest, BigDecimal accidentInsurance, BigDecimal cashDeposit) {
        if (null == uid || null == orderId || null == associatedOrderId || null == financingAmount || null == financingMaturity ||
                null == financingPrincipalInterest || null == financingInterest || null == settlePrincipalInterest || null == settleInterest || null == unsettlePrincipalInterest || null == unsettleInterest) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        AccountRenterFinancing accountRenterFinancing = new AccountRenterFinancing();
        accountRenterFinancing.setAccountRenterFinancing(uid, orderId, associatedOrderId, financingAmount, financingMaturity,
                financingPrincipalInterest, financingInterest, settlePrincipalInterest, settleInterest, unsettlePrincipalInterest, unsettleInterest);
        AccountRenterFinancing result = accountService.saveAccountRenterFinancing(accountRenterFinancing);
        OperationResult<Boolean> renterRepay = saveAccountRenterRepay(orderId, uid, financingMaturity, accidentInsurance, financingAmount, OrderTypeEnum.FINANCING, cashDeposit);
        if (null == result || null == result || !renterRepay.getEntity()) {
            return new OperationResult<>(false);
        }
        return new OperationResult<>(true);
    }

    /**
     * 生成还款计划表
     *
     * @param orderId           订单的ID
     * @param uid
     * @param totalPeriods      一共几期
     * @param accidentInsurance
     * @param totalAccount      总金额
     * @param orderTypeEnum
     * @param cashDeposit
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountRenterRepay(Long orderId, Long uid, Integer totalPeriods, BigDecimal accidentInsurance, BigDecimal totalAccount, OrderTypeEnum orderTypeEnum, BigDecimal cashDeposit) {
        if (null == orderId || null == uid || null == totalAccount || null == totalPeriods) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        List<AccountRenterRepay> accountRenterRepays = new ArrayList<>(totalPeriods);
        BigDecimal orderAmount = totalAccount.divide(BigDecimal.valueOf(totalPeriods), 2, RoundingMode.HALF_UP);

        for (int i = 1; i <= totalPeriods; i++) {
            AccountRenterRepay accountRenterRepay = new AccountRenterRepay();
            accountRenterRepay.setId(idGlobal.getSeqId(AccountRenterRepay.class));
            accountRenterRepay.setNumberPeriods(i);
            accountRenterRepay.setOrderAmount(orderAmount);
            accountRenterRepay.setOrderId(orderId);
            accountRenterRepay.setUid(uid);
            accountRenterRepay.setWithhold(1);
            accountRenterRepay.setOrderStatus(1);
            accountRenterRepay.setOrderType(orderTypeEnum.getCode());
            accountRenterRepay.setCashDeposit(cashDeposit);
            Long uixTime = LocalDate.now().plus(i - 1, ChronoUnit.MONTHS).atStartOfDay().toEpochSecond(ZoneOffset.of("+8"));
            accountRenterRepay.setPayTime(Math.toIntExact(uixTime));
            accountRenterRepays.add(accountRenterRepay);
        }
        //第一期是已扣款
        AccountRenterRepay renterRepay = accountRenterRepays.get(0);
        if (null != renterRepay) {
            renterRepay.setWithhold(2);
            renterRepay.setOrderAmount(renterRepay.getOrderAmount().add(accidentInsurance));
        }
        List<AccountRenterRepay> result = accountService.saveAccountRenterRepay(accountRenterRepays);
        if (CollectionUtils.isEmpty(result)) {
            return new OperationResult<>(false);
        }
        return new OperationResult<>(true);
    }

    /**
     * 操作账户的接口
     * 具体字段什么意思
     * 在AccountStatement实体类有详细的描述
     *
     * @param uid
     * @param dealType    交易的类型
     * @param accountType 操作账户的类型
     * @param dealAmount  交易的金额
     * @param extraAmount 额外的金额
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountStatement(Long uid, DealTypeEnum dealType, AccountTypeEnum accountType, BigDecimal dealAmount, BigDecimal extraAmount) {
        if (null == uid || null == dealType || null == accountType || null == dealAmount || null == extraAmount) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setAccountStatement(uid, dealType.getCode(), accountType.getCode(), dealAmount, extraAmount);
        AccountStatement result = accountService.saveAccountStatement(accountStatement);

        OperationResult<Boolean> res = updateAccount(uid, accountType, dealAmount);
        if (null == result || !res.getSucc() || !res.getEntity()) {
            return new OperationResult<>(false);
        }
        return new OperationResult<>(true);
    }

    /**
     * 对指定的账户做操作
     *
     * @param uid
     * @param accountTypeEnum
     * @param dealAmount
     * @return
     */
    @Override
    public OperationResult<Boolean> updateAccount(Long uid, AccountTypeEnum accountTypeEnum, BigDecimal dealAmount) {
        if (null == uid || null == accountTypeEnum || null == dealAmount) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        Account account = accountService.getAccount(uid);
        if (null == account) {
            return new OperationResult<>(BizErrorCode.ACCOUNT_NOTEXIST);
        }
        BigDecimal amount;
        if (accountTypeEnum.equals(AccountTypeEnum.TOTALASSETS)) {
            amount = account.getTotalAssets().add(dealAmount);
            account.setTotalAssets(amount);
        } else if (accountTypeEnum.equals(AccountTypeEnum.USEABLEASSETS)) {
            amount = account.getUseableAmount().add(dealAmount);
            account.setUseableAmount(amount);
            account.setTotalAssets(account.getTotalAssets().add(dealAmount));
        } else if (accountTypeEnum.equals(AccountTypeEnum.FROZENASSETS)) {
            amount = account.getFrozenAssets().add(dealAmount);
            account.setFrozenAssets(amount);
            account.setTotalAssets(account.getTotalAssets().add(dealAmount));
        } else if (accountTypeEnum.equals(AccountTypeEnum.MARGINASSETS)) {
            amount = account.getMarginBalance().add(dealAmount);
            account.setMarginBalance(amount);
            account.setTotalAssets(account.getTotalAssets().add(dealAmount));
        } else {
            return new OperationResult<>(BizErrorCode.ACCOUNTTYPE_NOTEXIST);
        }

        Account result = accountService.updateAccount(account);
        if (null == result) {
            return new OperationResult<>(false);
        }
        return new OperationResult<>(true);
    }

    /**
     * 融资购机还款
     *
     * @param id
     * @return
     */
    @Override
    public OperationResult<Boolean> financeRepayment(Long id) {
        if (null == id) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        Boolean finallyPeriod = false;
        AccountRenterRepay accountRenterRepay = accountService.getAccountRenterRepay(id);
        if (null == accountRenterRepay) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        BigDecimal extraAmount = BigDecimal.valueOf(0);
        BigDecimal dealAmount = accountRenterRepay.getOrderAmount();
        Long orderId = accountRenterRepay.getOrderId();
        List<AccountRenterRepay> accountRenterRepays = accountService.listAccountRenterRepay(orderId);
        //获取该笔订单的最后一期
        AccountRenterRepay acc = accountRenterRepays.get(accountRenterRepays.size() - 1);
        //判断目前正要付款的订单是否是最后一期
        if (id.equals(acc.getId())) {
            finallyPeriod = true;
        }
        //如果逾期
        if (accountRenterRepay.getOrderStatus().equals(2)) {
            //这个利息先不加
            //extraAmount = dealAmount.multiply(overdueRate);
            dealAmount = dealAmount.add(extraAmount);
            AccountRenterOverdueDetail detail = accountService.getAccountRenterOverdueDetail(orderId);
            if (null != detail) {
                detail.setPayedAccount(detail.getPayedAccount().add(dealAmount));
                detail.setUnpayedAccount(detail.getUnpayedAccount().subtract(dealAmount));
                accountService.updateAccountRenterOverdueDetail(detail);

            }
        }
        AccountRenterFinancing accountRenterFinancing = accountService.getAccountRenterFinancing(orderId);
        AccountFundingFinanceAsset accountFundingFinanceAsset = accountService.getAccountFundingFinanceAsset(orderId);
        if (null == accountRenterFinancing) {
            return new OperationResult<>(BizErrorCode.ACCOUNT_NOTEXIST);
        }
        if (null == accountFundingFinanceAsset) {
            return new OperationResult<>(BizErrorCode.ACCOUNT_NOTEXIST);
        }
        Long renterUid = accountRenterFinancing.getUid();
        Long funderUid = accountFundingFinanceAsset.getUid();
        //需要先校验余额是否充足
        Account account = accountService.getAccount(renterUid);
        BigDecimal useableAmount = account.getUseableAmount();
        //a.compareTo(b),a>b 1,a<b -1
        if (useableAmount.subtract(dealAmount).compareTo(new BigDecimal(0)) == -1) {
            return new OperationResult<>(BizErrorCode.NOT_SUFFICIENT_FUNDS);
        }
        //操作租赁商的账户
        OperationResult<Boolean> result = saveAccountStatement(renterUid, DealTypeEnum.FINANCING, AccountTypeEnum.USEABLEASSETS, dealAmount.multiply(new BigDecimal(-1)), extraAmount);
        //操作资金方的账户
        OperationResult<Boolean> result1 = saveAccountStatement(funderUid, DealTypeEnum.FINANCING, AccountTypeEnum.USEABLEASSETS, dealAmount, extraAmount);
        //如果是最后一期。需要将保证金退还

        if (accountRenterRepay.getOrderType().equals(OrderTypeEnum.FINANCING)){
            BigDecimal cashDeposit = accountRenterRepay.getCashDeposit();
            //减保证金
            saveAccountStatement(renterUid, DealTypeEnum.ACCOUNTTRANSFER, AccountTypeEnum.MARGINASSETS, cashDeposit.multiply(new BigDecimal(-1)), extraAmount);
            //加可用余额
            saveAccountStatement(renterUid, DealTypeEnum.ACCOUNTTRANSFER, AccountTypeEnum.USEABLEASSETS, cashDeposit, extraAmount);

        }


        //更新还款状态
        accountRenterRepay.setOrderStatus(1);
        accountRenterRepay.setWithhold(2);
        accountService.updateAccountRenterRepay(accountRenterRepay);

        //更新租赁商和资金方的状态
        //该笔订单是否全部还清,初始值为false,只判断最后一期即可
        //逾期还款后去更改订单状态，这个还需要判断是否所有逾期的都已经还了，才能去更新
        if (acc.getWithhold().equals(2) || acc.getWithhold().equals(4)) {
            accountRenterFinancing.setRepaymentStatus(1);
            accountRenterFinancing.setOrderStatus(1);
            accountService.updateAccountRenterFinancing(accountRenterFinancing);
            accountFundingFinanceAsset.setOrderStatus(1);
            accountService.updateAccountFundingFinanceAsset(accountFundingFinanceAsset);
        }
        //更新租赁商已清偿金额和待清偿金额，利息暂时不考虑
        accountRenterFinancing.setSettlePrincipalInterest(accountRenterFinancing.getSettlePrincipalInterest().add(dealAmount));
        accountRenterFinancing.setUnsettlePrincipalInterest(accountRenterFinancing.getUnsettlePrincipalInterest().subtract(dealAmount));
        accountService.updateAccountRenterFinancing(accountRenterFinancing);

        if (!result.getSucc() || !result.getEntity() || !result1.getSucc() || !result1.getEntity()) {
            return new OperationResult<>(BizErrorCode.SAVE_INFO_ERROR);
        }
        return new OperationResult<>(true);
    }

    /**
     * 租赁商租机还款
     *
     * @param id
     * @return
     */
    @Override
    public OperationResult<Boolean> rentRepayment(Long id) {
        if (null == id) {
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        AccountRenterRepay accountRenterRepay = accountService.getAccountRenterRepay(id);
        if (null == accountRenterRepay) {
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        BigDecimal extraAmount = BigDecimal.valueOf(0);
        BigDecimal dealAmount = accountRenterRepay.getOrderAmount();
        Long orderId = accountRenterRepay.getOrderId();
        Boolean finallyPeriod = false;
        List<AccountRenterRepay> accountRenterRepays = accountService.listAccountRenterRepay(orderId);
        //获取该笔订单的最后一期
        AccountRenterRepay acc = accountRenterRepays.get(accountRenterRepays.size() - 1);
        //判断目前正要付款的订单是否是最后一期
        if (id.equals(acc.getId())) {
            finallyPeriod = true;
        }

        //如果逾期
        if (accountRenterRepay.getOrderStatus().equals(2)) {
            //这个逾期利息暂时先不加
            //extraAmount = dealAmount.multiply(overdueRate);
            dealAmount = dealAmount.add(extraAmount);
            AccountRenterOverdueDetail detail = accountService.getAccountRenterOverdueDetail(orderId);
            if (null != detail) {
                detail.setPayedAccount(detail.getPayedAccount().add(dealAmount));
                detail.setUnpayedAccount(detail.getUnpayedAccount().subtract(dealAmount));
                accountService.updateAccountRenterOverdueDetail(detail);

            }
        }
        AccountRenterRent accountRenterRent = accountService.getAccountRenterRent(orderId);
        AccountLessorMatterAsset accountLessorMatterAsset = accountService.getAccountLessorMatterAsset(orderId);

        Long renterUid = accountRenterRent.getUid();
        Long lessorUid = accountLessorMatterAsset.getUid();
        //需要先校验余额是否充足
        Account account = accountService.getAccount(renterUid);
        BigDecimal useableAmount = account.getUseableAmount();
        //a.compareTo(b),a>b 1,a<b -1
        if (useableAmount.subtract(dealAmount).compareTo(new BigDecimal(0)) == -1) {
            return new OperationResult<>(BizErrorCode.NOT_SUFFICIENT_FUNDS);
        }
        //操作租赁商的账户
        OperationResult<Boolean> result = saveAccountStatement(renterUid, DealTypeEnum.FINANCING, AccountTypeEnum.USEABLEASSETS, dealAmount.multiply(new BigDecimal(-1)), extraAmount);
        OperationResult<Boolean> result1 = saveAccountStatement(lessorUid, DealTypeEnum.FINANCING, AccountTypeEnum.USEABLEASSETS, dealAmount, extraAmount);

        //更新还款状态
        accountRenterRepay.setOrderStatus(1);
        accountRenterRepay.setWithhold(2);
        AccountRenterRepay renterRepay = accountService.updateAccountRenterRepay(accountRenterRepay);

        //更新租赁商和资金方的状态
        //该笔订单是否全部还清,初始值为false,只判断最后一期即可
        //逾期还款后去更改订单状态，这个还需要判断是否所有逾期的都已经还了，才能去更新
        if (acc.getWithhold().equals(2) || acc.getWithhold().equals(4)) {
            accountLessorMatterAsset.setOrderStatus(1);
            accountService.updateAccountLessorMatterAsset(accountLessorMatterAsset);
        }

        //更新租赁商已清偿金额和待清偿金额，利息暂时不考虑
        accountRenterRent.setReceivedAccount(accountRenterRent.getReceivedAccount().add(dealAmount));
        accountRenterRent.setDueInAccount(accountRenterRent.getDueInAccount().subtract(dealAmount));
        accountRenterRent.setResidueTime(accountRenterRent.getResidueTime() - 1);
        accountService.updateAccountRenterRent(accountRenterRent);
        //更新AccountRenterRentDetail租机明细
        AccountRenterRentDetail accountRenterRentDetail = accountService.getAccountRenterRentDetail(orderId);
        accountRenterRentDetail.setPayedAccount(accountRenterRentDetail.getPayedAccount().add(dealAmount));
        accountRenterRentDetail.setUnpayedAccount(accountRenterRentDetail.getUnpayedAccount().subtract(dealAmount));
        accountService.updateAccountRenterRentDetail(accountRenterRentDetail);

        if (!result.getSucc() || !result.getEntity() || !result1.getSucc() || !result1.getEntity()) {
            return new OperationResult<>(BizErrorCode.SAVE_INFO_ERROR);
        }
        return new OperationResult<>(true);
    }

    /**
     * 账户资产的操作接口
     *
     * @return
     */


}
