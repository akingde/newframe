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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
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
}
