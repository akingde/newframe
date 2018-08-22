package com.newframe.services.user.roleimpl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.user.request.RentMerchantApplyDTO;
import com.newframe.dto.user.request.RoleApplyDTO;
import com.newframe.dto.user.response.UserRoleApplyDTO;
import com.newframe.dto.user.response.UserRoleDTO;
import com.newframe.entity.user.UserRentMerchant;
import com.newframe.entity.user.UserRoleApply;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.RequestResultEnum;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.services.user.RoleService;
import com.newframe.services.userbase.UserRentMerchantService;
import com.newframe.services.userbase.UserRoleApplyService;
import com.newframe.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author WangBin
 */
@Service
public class FirstRentMerchantServiceImpl implements RoleService {

    @Autowired
    private UserRoleApplyService userRoleApplyService;
    @Autowired
    private UserRentMerchantService userRentMerchantService;


    @Override
    public Integer getRoleId() {
        return RoleEnum.FIRST_RENT_MERCHANT.getRoleId();
    }

    /**
     * 角色申请
     *
     * @param uid
     * @param roleApplyDTO
     * @return
     */
    @Override
    public OperationResult<Boolean> roleApply(Long uid, RoleApplyDTO roleApplyDTO) {
        RentMerchantApplyDTO roleApply = (RentMerchantApplyDTO)roleApplyDTO;
        if(roleApply.getHighestDegreeDiploma() == null || roleApply.getHighestDegreeDiploma().length > 2){
            return new OperationResult(RequestResultEnum.FILE_TOO_MUCH, false);
        }
        if(roleApply.getDrivingLicense() == null || roleApply.getDrivingLicense().length > 2){
            return new OperationResult(RequestResultEnum.FILE_TOO_MUCH, false);
        }
        if(roleApply.getHouseProprietaryCertificate() == null || roleApply.getHouseProprietaryCertificate().length > 9){
            return new OperationResult(RequestResultEnum.FILE_TOO_MUCH, false);
        }
        if(FileUtils.checkImage(roleApply.getHighestDegreeDiploma(), roleApply.getDrivingLicense(),
                roleApply.getHouseProprietaryCertificate())){
            return new OperationResult(RequestResultEnum.FILE_FORMAT_ERROR, false);
        }
        UserRoleApply userRoleApply = new UserRoleApply();
        userRoleApply.setUid(uid);
        userRoleApply.setRoleId(getRoleId());
        userRoleApply.setMerchantName(roleApply.getName());
        userRoleApply.setLegalEntity(roleApply.getLegalEntity());
        userRoleApply.setLegalEntityIdNumber(roleApply.getLegalEntityIdNumber());
        userRoleApply.setBusinessLicenseNumber(roleApply.getBusinessListenNumber());
        userRoleApply.setApplyStatus(RoleStatusEnum.UNDER_REVIEW.getRoleStatue());
        UserRoleApply roleResult = userRoleApplyService.insert(userRoleApply);
        return null;
    }

    /**
     * 获取角色申请详细信息
     *
     * @param uid
     * @param roleApplyId
     * @return
     */
    @Override
    public OperationResult<UserRoleApplyDTO> getUserRoleApplyInfo(Long uid, Long roleApplyId) {
        UserRoleApply roleApply = userRoleApplyService.findOne(roleApplyId, uid);
        return new OperationResult(roleApply == null ? new UserRoleDTO() : new UserRoleApplyDTO.RentMechant(roleApply));
    }

    /**
     * 根据uid获取角色信息
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<UserRoleDTO> getUserRoleInfo(Long uid) {
        UserRentMerchant rentMerchant = userRentMerchantService.findOne(uid, getRoleId());
        return new OperationResult(rentMerchant == null ? new UserRoleDTO() : new UserRoleDTO.RentMechant(rentMerchant));
    }

    /**
     * 设置指定供应商开关
     *
     * @param uid
     * @param appoint
     * @return
     */
    @Override
    public OperationResult<Boolean> setAppoint(Long uid, boolean appoint) {
        UserRentMerchant rentMerchant = userRentMerchantService.findOne(uid, getRoleId());
        if (rentMerchant == null){
            return new OperationResult(RequestResultEnum.ROLE_NOT_EXEISTS, false);
        }
        rentMerchant.setAppoint(appoint);
        userRentMerchantService.update(rentMerchant);
        return new OperationResult(true);
    }

    /**
     * 获取指定的供应商
     *
     * @param uid
     * @return
     */
    @Override
    public OperationResult<List<UserRoleDTO.Supplier>> getAppointSupplier(Long uid) {


        return new OperationResult(Collections.emptyList());
    }

    /**
     * 获取所有的供应商
     *
     * @return
     */
    @Override
    public OperationResult<List<UserRoleDTO.Supplier>> getAllSupplier() {
        return new OperationResult(Collections.emptyList());
    }
}
