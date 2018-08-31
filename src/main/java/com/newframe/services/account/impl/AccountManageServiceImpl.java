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
    public OperationResult<AccountRenter> getRenterAssetAccount(Long uid) {
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }

        AccountRenter accountRenter = accountService.getAccountRenter(uid);

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
    public OperationResult<AccountRenterRentInfo> listRenterOrderRentAccount(Long uid, Integer orderStatus, Integer currentPage, Integer pageSize) {
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == orderStatus || null == currentPage || null == pageSize){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        AccountRenterRentInfo accountRenterRentInfo = new AccountRenterRentInfo();
        Page<AccountRenterRent> accountRenterRentPage = accountService.getAccountRenterRent(uid, orderStatus, currentPage, pageSize);
        List<AccountRenterRent> accountRenterRents = accountRenterRentPage.getContent();
        if (CollectionUtils.isEmpty(accountRenterRents)) {
            return new OperationResult<>(accountRenterRentInfo);
        }
        accountRenterRentInfo.setList(accountRenterRents);
        accountRenterRentInfo.setTotal(accountRenterRentPage.getTotalPages());
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
    public OperationResult<RenterOrderFinanceInfo> listRenterOrderFinance(Long uid, Integer orderStatus, Integer currentPage, Integer pageSize) {

        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == orderStatus || null == currentPage || null == pageSize){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        RenterOrderFinanceInfo renterOrderFinanceInfo = new RenterOrderFinanceInfo();
        Page<AccountRenterFinancing> accountRenterFinancings = accountService.getAccountRenterFinancing(uid, orderStatus, currentPage, pageSize);
        List<AccountRenterFinancing> accountRenterFinancingList = accountRenterFinancings.getContent();
        if (CollectionUtils.isEmpty(accountRenterFinancingList)) {
            return new OperationResult<>(renterOrderFinanceInfo);
        }
        renterOrderFinanceInfo.setList(accountRenterFinancingList);
        renterOrderFinanceInfo.setTotal(accountRenterFinancings.getTotalPages());
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
}
