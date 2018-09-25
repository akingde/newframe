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
import com.newframe.services.account.AccountManageService;
import com.newframe.services.account.AccountService;
import com.newframe.services.userbase.UserAddressService;
import com.newframe.services.userbase.UserBaseInfoService;
import com.newframe.services.userbase.UserPwdService;
import com.newframe.services.userbase.UserRentMerchantService;
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
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        RenterBaseInfo renterBaseInfo = new RenterBaseInfo();
        List<RenterAddress> renterAddresses = Lists.newArrayList();
        RenterAuthorization renterAuthorization = new RenterAuthorization();

        UserBaseInfo userBaseInfo = userBaseInfoService.findOne(uid);
        if (null != userBaseInfo && StringUtils.isNotEmpty(userBaseInfo.getPhoneNumber())){
            renterBaseInfo.setMobile(userBaseInfo.getPhoneNumber());
        }
        UserPwd userPwd = userPwdService.findByUid(uid);
        if (StringUtils.isNotEmpty(userPwd.getLoginPwd())){
            renterBaseInfo.setHasPassword(true);
        }
        renterBaseInfo.setHasPassword(false);

        List<UserAddress> userAddresses = userAddressService.findUserAddressList(uid);
        if (CollectionUtils.isNotEmpty(userAddresses)){
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
        if (null != userRentMerchant){
            renterAuthorization.setBusinessLicense(userRentMerchant.getBusinessLicenseNumber());
            renterAuthorization.setBusinessLicenseUrl(userRentMerchant.getBusinessLicenseFile());
            renterAuthorization.setAcademicDiplomasUrl(userRentMerchant.getHighestDegreeDiplomaFile());
            renterAuthorization.setDrivingLicenseUrl(userRentMerchant.getDrivingLicenseFile());
            renterAuthorization.setHouseContractUrl(userRentMerchant.getHouseProprietaryCertificateFile());
            renterBaseInfo.setLegalName(userRentMerchant.getLegalEntity());
            renterBaseInfo.setIdNumber(userRentMerchant.getLegalEntityIdNumber());
        }
        List<AccountRenterAppointSupplier> accountRenterAppointSuppliers = accountService.listAccountRenterAppointSupplier(uid);

        return new OperationResult<>(new RenterAccountInfo(renterBaseInfo,renterAddresses,renterAuthorization,accountRenterAppointSuppliers));
    }

    /**
     * 获取租赁商的账户资产
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<Account> getRenterAssetAccount(Long uid) {
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }

        Account accountRenter = accountService.getAccountRenter(uid);

        return new OperationResult<>(accountRenter);
    }

    /**
     * @param uid
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public OperationResult<AccountRenterRentInfo> listRenterOrderRent(Long uid, Integer orderStatus, Integer currentPage, Integer pageSize) {
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == currentPage || null == pageSize){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        AccountRenterRentInfo accountRenterRentInfo = new AccountRenterRentInfo();
        Page<AccountRenterRent> accountRenterRentPage = accountService.getAccountRenterRent(uid, orderStatus, currentPage, pageSize);
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
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }

        AccountRenterFinancingMachine accountRenterFinancingMachine = accountService.getAccountRenterFinancingMachine(uid);

        return new OperationResult<>(accountRenterFinancingMachine);
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
    public OperationResult<RenterOrderFinanceInfo> listRenterOrderFinance(Long uid,Integer repaymentStatus, Integer orderStatus, Integer currentPage, Integer pageSize) {

        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == currentPage || null == pageSize){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        RenterOrderFinanceInfo renterOrderFinanceInfo = new RenterOrderFinanceInfo();
        Page<AccountRenterFinancing> accountRenterFinancings = accountService.getAccountRenterFinancing(uid, repaymentStatus,orderStatus, currentPage, pageSize);
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
        if (null == orderId){
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
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }

        AccountRenterRentMachine accountRenterRentMachine = accountService.getAccountRenterRentMachine(uid);

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
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == currentPage || null == pageSize){
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
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }

        AccountRenterOverdueAsset accountRenterOverdueAsset = accountService.getAccountRenterOverdueAsset(uid);

        return new OperationResult<>(accountRenterOverdueAsset);
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

        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == currentPage || null == pageSize){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        RenterOrderOverdueDetailInfo renterOrderRentDetailInfo = new RenterOrderOverdueDetailInfo();
        Page<AccountRenterOverdueDetail> accountRenterOverdueDetails = accountService.getAccountRenterOverdueDetail(uid,currentPage, pageSize);
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
        if (null == uid){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        Account account = new Account();
        account.setAccount(uid);
        Account result = accountService.saveAccount(account);
        if (null == result){
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
     * @param receivedAccount 已收
     * @param dueInAccount 待收金额
     * @param residueTime 剩余期数
     * @param collectMoney 收款账户
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountRenterRent(Long uid, Long orderId, String relevanceOrderId, BigDecimal receivableAccount, BigDecimal receivedAccount, BigDecimal dueInAccount, Integer residueTime, String collectMoney) {
        if (null == uid || null == orderId || null == relevanceOrderId || null == receivableAccount || null == receivedAccount || null == dueInAccount){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        AccountRenterRent accountRenterRent = new AccountRenterRent();
        accountRenterRent.setAccountRenterRent(uid,orderId,relevanceOrderId,receivableAccount,receivedAccount,dueInAccount,residueTime,collectMoney);
        AccountRenterRent result = accountService.saveAccountRenterRent(accountRenterRent);
        if (null == result){
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
        if (null == uid || null == orderId || null == associatedOrderId || StringUtils.isEmpty(productBrand) || StringUtils.isEmpty(productModel)||
                StringUtils.isEmpty(productColour) || StringUtils.isEmpty(productStorage) || StringUtils.isEmpty(productMemory) ||  null == totalRentAccount || null == monthNumber || null == payedAccount || null == unpayedAccount){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        AccountRenterRentDetail accountRenterRentDetail = new AccountRenterRentDetail();
        accountRenterRentDetail.setAccountRenterRentDetail(uid,orderId,associatedOrderId,productBrand,productModel,productColour,productStorage,productMemory,totalRentAccount,monthNumber,payedAccount,unpayedAccount);
        AccountRenterRentDetail result = accountService.saveAccountRenterRentDetail(accountRenterRentDetail);
        OperationResult<Boolean> renterRent = saveAccountRenterRent(uid, orderId, associatedOrderId, totalRentAccount, payedAccount, unpayedAccount, residueTime, collectMoney);
        OperationResult<Boolean> renterRepay = saveAccountRenterRepay(orderId,totalRentAccount,monthNumber,accidentInsurance);
        if (null == result || !renterRent.getEntity() || !renterRepay.getEntity()){
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
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountRenterFinancing(Long uid, Long orderId, String associatedOrderId, BigDecimal financingAmount, Integer financingMaturity, BigDecimal financingPrincipalInterest, BigDecimal financingInterest, BigDecimal settlePrincipalInterest, BigDecimal settleInterest, BigDecimal unsettlePrincipalInterest, BigDecimal unsettleInterest, BigDecimal accidentInsurance) {
        if (null == uid || null == orderId || null == associatedOrderId || null == financingAmount || null == financingMaturity||
                null == financingPrincipalInterest || null == financingInterest || null == settlePrincipalInterest ||  null == settleInterest || null == unsettlePrincipalInterest || null == unsettleInterest){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        AccountRenterFinancing accountRenterFinancing = new AccountRenterFinancing();
        accountRenterFinancing.setAccountRenterFinancing(uid,orderId,associatedOrderId,financingAmount,financingMaturity,
                financingPrincipalInterest,financingInterest,settlePrincipalInterest,settleInterest,unsettlePrincipalInterest,unsettleInterest);
        AccountRenterFinancing result = accountService.saveAccountRenterFinancing(accountRenterFinancing);
        OperationResult<Boolean> renterRepay = saveAccountRenterRepay(orderId,financingAmount,financingMaturity,accidentInsurance);
        if (null == result || null == result || !renterRepay.getEntity()){
            return new OperationResult<>(false);
        }
        return new OperationResult<>(true);
    }

    /**
     * 生成还款计划表
     *
     * @param orderId       订单的ID
     * @param totalAccount  总金额
     * @param totalPeriods 一共几期
     * @param accidentInsurance
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountRenterRepay(Long orderId, BigDecimal totalAccount, Integer totalPeriods, BigDecimal accidentInsurance) {
        if (null == orderId || null == totalAccount || null == totalPeriods){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        List<AccountRenterRepay> accountRenterRepays = new ArrayList<>(totalPeriods);
        BigDecimal orderAmount = totalAccount.divide(BigDecimal.valueOf(totalPeriods),2, RoundingMode.HALF_UP);

        for (int i = 1; i <= totalPeriods; i++){
            AccountRenterRepay accountRenterRepay = new AccountRenterRepay();
            accountRenterRepay.setId(idGlobal.getSeqId(AccountRenterRepay.class));
            accountRenterRepay.setNumberPeriods(i);
            accountRenterRepay.setOrderAmount(orderAmount);
            accountRenterRepay.setOrderId(orderId);
            accountRenterRepay.setWithhold(1);
            accountRenterRepay.setOrderStatus(1);
            Long uixTime = LocalDate.now().plus(i-1, ChronoUnit.MONTHS).atStartOfDay().toEpochSecond(ZoneOffset.of("+8"));
            accountRenterRepay.setPayTime(Math.toIntExact(uixTime));
            accountRenterRepays.add(accountRenterRepay);
        };
        //第一期是已扣款
        AccountRenterRepay renterRepay = accountRenterRepays.get(0);
        if (null != renterRepay) {
            renterRepay.setWithhold(2);
            renterRepay.setOrderAmount(renterRepay.getOrderAmount().add(accidentInsurance));
        }
        List<AccountRenterRepay> result = accountService.saveAccountRenterRepay(accountRenterRepays);
        if (CollectionUtils.isEmpty(result)){
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
        if (null == uid || null == dealType || null ==accountType || null == dealAmount || null == extraAmount){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setAccountStatement(uid,dealType.getCode(),accountType.getCode(),dealAmount,extraAmount);
        AccountStatement result = accountService.saveAccountStatement(accountStatement);

        OperationResult<Boolean> res = updateAccount(uid,accountType,dealAmount);
        if (null == result || !res.getSucc() || !res.getEntity()){
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
        if (null == uid || null == accountTypeEnum || null == dealAmount){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        Account account = accountService.getAccount(uid);
        if (null == account){
            return new OperationResult<>(BizErrorCode.ACCOUNT_NOTEXIST);
        }
        BigDecimal amount;
        if (accountTypeEnum.equals(AccountTypeEnum.TOTALASSETS)){
            amount = account.getTotalAssets().add(dealAmount);
            account.setTotalAssets(amount);
        }else if (accountTypeEnum.equals(AccountTypeEnum.USEABLEASSETS)){
            amount = account.getUseableAmount().add(dealAmount);
            account.setUseableAmount(amount);
            account.setTotalAssets(account.getTotalAssets().add(dealAmount));
        }else if (accountTypeEnum.equals(AccountTypeEnum.FROZENASSETS)){
            amount = account.getFrozenAssets().add(dealAmount);
            account.setFrozenAssets(amount);
            account.setTotalAssets(account.getTotalAssets().add(dealAmount));
        }else if (accountTypeEnum.equals(AccountTypeEnum.MARGINASSETS)){
            amount = account.getMarginBalance().add(dealAmount);
            account.setMarginBalance(amount);
            account.setTotalAssets(account.getTotalAssets().add(dealAmount));
        }else {
            return new OperationResult<>(BizErrorCode.ACCOUNTTYPE_NOTEXIST);
        }

        Account result = accountService.updateAccount(account);
        if (null == result){
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
        if (null == id){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        Boolean finallyPeriod = false;
        AccountRenterRepay accountRenterRepay = accountService.getAccountRenterRepay(id);
        if (null == accountRenterRepay){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        BigDecimal extraAmount = BigDecimal.valueOf(0);
        BigDecimal dealAmount = accountRenterRepay.getOrderAmount();
        Long orderId = accountRenterRepay.getOrderId();
        List<AccountRenterRepay> accountRenterRepays = accountService.listAccountRenterRepay(orderId);
        //获取该笔订单的最后一期
        AccountRenterRepay acc = accountRenterRepays.get(accountRenterRepays.size()-1);
        //判断目前正要付款的订单是否是最后一期
        if (id.equals(acc.getId())){
            finallyPeriod = true;
        }
        //如果逾期
        if (accountRenterRepay.getOrderStatus().equals(2)){
            extraAmount = dealAmount.multiply(overdueRate);
            dealAmount = dealAmount.add(extraAmount);
        }
        AccountRenterFinancing accountRenterFinancing = accountService.getAccountRenterFinancing(orderId);
        AccountFundingFinanceAsset accountFundingFinanceAsset = accountService.getAccountFundingFinanceAsset(orderId);
        if (null == accountRenterFinancing){
            return new OperationResult<>(BizErrorCode.ACCOUNT_NOTEXIST);
        }
        if (null == accountFundingFinanceAsset){
            return new OperationResult<>(BizErrorCode.ACCOUNT_NOTEXIST);
        }
        Long renterUid = accountRenterFinancing.getUid();
        Long funderUid = accountFundingFinanceAsset.getUid();
        //需要先校验余额是否充足
        Account account = accountService.getAccount(renterUid);
        BigDecimal useableAmount = account.getUseableAmount();
        //a.compareTo(b),a>b 1,a<b -1
        if (useableAmount.subtract(dealAmount).compareTo(new BigDecimal(0))==-1){
            return new OperationResult<>(BizErrorCode.NOT_SUFFICIENT_FUNDS);
        }
        //操作租赁商的账户
        OperationResult<Boolean> result = saveAccountStatement(renterUid,DealTypeEnum.FINANCING,AccountTypeEnum.USEABLEASSETS,dealAmount.multiply(new BigDecimal(-1)),extraAmount);
        //操作资金方的账户
        OperationResult<Boolean> result1 = saveAccountStatement(funderUid,DealTypeEnum.FINANCING,AccountTypeEnum.USEABLEASSETS,dealAmount,extraAmount);
        //如果是最后一期。需要将保证金退还
        if (finallyPeriod){
            BigDecimal totalAmount = accountRenterFinancing.getFinancingAmount();
            BigDecimal cashDeposit = totalAmount.multiply(depositRate);
            //减保证金
            saveAccountStatement(renterUid,DealTypeEnum.ACCOUNTTRANSFER,AccountTypeEnum.MARGINASSETS,cashDeposit.multiply(new BigDecimal(-1)),extraAmount);
            //加可用余额
            saveAccountStatement(renterUid,DealTypeEnum.ACCOUNTTRANSFER,AccountTypeEnum.USEABLEASSETS,cashDeposit,extraAmount);
        }

        //更新还款状态
        accountRenterRepay.setOrderStatus(1);
        accountRenterRepay.setWithhold(2);
        AccountRenterRepay renterRepay = accountService.updateAccountRenterRepay(accountRenterRepay);

        //更新租赁商和资金方的状态
        //该笔订单是否全部还清,初始值为false,只判断最后一期即可
        //逾期还款后去更改订单状态，这个还需要判断是否所有逾期的都已经还了，才能去更新
        if (acc.getWithhold().equals(2) || acc.getWithhold().equals(4)){
            accountRenterFinancing.setRepaymentStatus(1);
            accountRenterFinancing.setOrderStatus(1);
            accountService.updateAccountRenterFinancing(accountRenterFinancing);
            accountFundingFinanceAsset.setOrderStatus(1);
            accountService.updateAccountFundingFinanceAsset(accountFundingFinanceAsset);
        }

        if (!result.getSucc()|| !result.getEntity() || !result1.getSucc() || !result1.getEntity()){
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
        if (null == id){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        AccountRenterRepay accountRenterRepay = accountService.getAccountRenterRepay(id);
        if (null == accountRenterRepay){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        BigDecimal extraAmount = BigDecimal.valueOf(0);
        BigDecimal dealAmount = accountRenterRepay.getOrderAmount();
        Long orderId = accountRenterRepay.getOrderId();
        Boolean finallyPeriod = false;
        List<AccountRenterRepay> accountRenterRepays = accountService.listAccountRenterRepay(orderId);
        //获取该笔订单的最后一期
        AccountRenterRepay acc = accountRenterRepays.get(accountRenterRepays.size()-1);
        //判断目前正要付款的订单是否是最后一期
        if (id.equals(acc.getId())){
            finallyPeriod = true;
        }

        //如果逾期
        if (accountRenterRepay.getOrderStatus().equals(2)){
            extraAmount = dealAmount.multiply(overdueRate);
            dealAmount = dealAmount.add(extraAmount);
        }
        AccountRenterFinancing accountRenterFinancing = accountService.getAccountRenterFinancing(orderId);
        AccountLessorMatterAsset accountLessorMatterAsset = accountService.getAccountLessorMatterAsset(orderId);

        Long renterUid = accountRenterFinancing.getUid();
        Long lessorUid = accountLessorMatterAsset.getUid();
        //需要先校验余额是否充足
        Account account = accountService.getAccount(renterUid);
        BigDecimal useableAmount = account.getUseableAmount();
        //a.compareTo(b),a>b 1,a<b -1
        if (useableAmount.subtract(dealAmount).compareTo(new BigDecimal(0))==-1){
            return new OperationResult<>(BizErrorCode.NOT_SUFFICIENT_FUNDS);
        }
        //操作租赁商的账户
        OperationResult<Boolean> result = saveAccountStatement(renterUid,DealTypeEnum.FINANCING,AccountTypeEnum.USEABLEASSETS,dealAmount.multiply(new BigDecimal(-1)),extraAmount);
        OperationResult<Boolean> result1 = saveAccountStatement(lessorUid,DealTypeEnum.FINANCING,AccountTypeEnum.USEABLEASSETS,dealAmount,extraAmount);

        if (finallyPeriod){
            BigDecimal totalAmount = accountRenterFinancing.getFinancingAmount();
            BigDecimal cashDeposit = totalAmount.multiply(depositRate);
            //减保证金
            saveAccountStatement(renterUid,DealTypeEnum.ACCOUNTTRANSFER,AccountTypeEnum.MARGINASSETS,cashDeposit.multiply(new BigDecimal(-1)),extraAmount);
            //加可用余额
            saveAccountStatement(renterUid,DealTypeEnum.ACCOUNTTRANSFER,AccountTypeEnum.USEABLEASSETS,cashDeposit,extraAmount);
        }

        //更新还款状态
        accountRenterRepay.setOrderStatus(1);
        accountRenterRepay.setWithhold(2);
        AccountRenterRepay renterRepay = accountService.updateAccountRenterRepay(accountRenterRepay);

        //更新租赁商和资金方的状态
        //该笔订单是否全部还清,初始值为false,只判断最后一期即可
        //逾期还款后去更改订单状态，这个还需要判断是否所有逾期的都已经还了，才能去更新
        if (acc.getWithhold().equals(2) || acc.getWithhold().equals(4)){
            accountRenterFinancing.setRepaymentStatus(1);
            accountRenterFinancing.setOrderStatus(1);
            accountService.updateAccountRenterFinancing(accountRenterFinancing);
            accountLessorMatterAsset.setOrderStatus(1);
            accountService.updateAccountLessorMatterAsset(accountLessorMatterAsset);
        }

        if (!result.getSucc()|| !result.getEntity() || !result1.getSucc() || !result1.getEntity()){
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
