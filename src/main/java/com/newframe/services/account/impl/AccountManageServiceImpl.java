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
     * @param receivableAccount
     * @param receivedAccount
     * @param dueInAccount
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountRenterRent(Long uid, Long orderId, String relevanceOrderId, BigDecimal receivableAccount, BigDecimal receivedAccount, BigDecimal dueInAccount) {
        if (null == uid || null == orderId || null == relevanceOrderId || null == receivableAccount || null == receivedAccount || null == dueInAccount){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        AccountRenterRent accountRenterRent = new AccountRenterRent();
        accountRenterRent.setAccountRenterRent(uid,orderId,relevanceOrderId,receivableAccount,receivedAccount,dueInAccount);
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
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountRenterRentDetail(Long uid, Long orderId, String associatedOrderId, String productBrand, String productModel, String productColour, String productStorage, String productMemory, BigDecimal totalRentAccount, Integer monthNumber, BigDecimal payedAccount, BigDecimal unpayedAccount) {
        if (null == uid || null == orderId || null == associatedOrderId || StringUtils.isEmpty(productBrand) || StringUtils.isEmpty(productModel)||
                StringUtils.isEmpty(productColour) || StringUtils.isEmpty(productStorage) || StringUtils.isEmpty(productMemory) ||  null == totalRentAccount || null == monthNumber || null == payedAccount || null == unpayedAccount){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        AccountRenterRentDetail accountRenterRentDetail = new AccountRenterRentDetail();
        accountRenterRentDetail.setAccountRenterRentDetail(uid,orderId,associatedOrderId,productBrand,productModel,productColour,productStorage,productMemory,totalRentAccount,monthNumber,payedAccount,unpayedAccount);
        AccountRenterRentDetail result = accountService.saveAccountRenterRentDetail(accountRenterRentDetail);
        OperationResult<Boolean> renterRent = saveAccountRenterRent(uid, orderId, associatedOrderId, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        OperationResult<Boolean> renterRepay = saveAccountRenterRepay(orderId,totalRentAccount,monthNumber);
        if (null == result || !renterRent.getEntity() || !renterRepay.getEntity()){
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
     * @return
     */
    @Override
    public OperationResult<Boolean> saveAccountRenterRepay(Long orderId, BigDecimal totalAccount, Integer totalPeriods) {
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

            accountRenterRepays.add(accountRenterRepay);
        };
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
    public OperationResult<Boolean> saveAccountStatement(Long uid, Integer dealType, Integer accountType, BigDecimal dealAmount, BigDecimal extraAmount) {
        if (null == uid || null == dealType || null ==accountType || null == dealAmount || null == extraAmount){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }

        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setAccountStatement(uid,dealType,accountType,dealAmount,extraAmount);
        AccountStatement result = accountService.saveAccountStatement(accountStatement);

        if (null == result){
            return new OperationResult<>(false);
        }
        return new OperationResult<>(true);
    }

    /**
     * 账户资产的操作接口
     *
     * @return
     */


}
