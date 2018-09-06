package com.newframe.services.userbase.impl;

import com.newframe.dto.after.request.RoleListSearchDTO;
import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.UserRoleApply;
import com.newframe.enums.RoleEnum;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.repositories.dataMaster.user.UserRoleApplyMaster;
import com.newframe.repositories.dataQuery.user.UserRoleApplyQuery;
import com.newframe.repositories.dataSlave.user.UserRoleApplySlave;
import com.newframe.services.userbase.UserRoleApplyService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *  用户角色申请
 *
 * This class corresponds to the database table user_role_apply
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserRoleApplyServiceImpl implements UserRoleApplyService {

    @Autowired
    private UserRoleApplyMaster userRoleApplyMaster;
    @Autowired
    private UserRoleApplySlave userRoleApplySlave;
    @Autowired
    private IdGlobalGenerator idGlobalGenerator;

    /**
     * 生成用户角色的申请记录
     *
     * @param userRoleApply
     * @return
     */
    @Override
    public UserRoleApply insert(UserRoleApply userRoleApply) {
        if(userRoleApply == null) {
            return null;
        }
        userRoleApply.setId(idGlobalGenerator.getSeqId(UserRoleApply.class));
        return userRoleApplyMaster.save(userRoleApply);
    }

    /**
     * 根据角色申请id和uid找出指定的记录
     *
     * @param roleApplyId
     * @param uid         非必选
     * @return
     */
    @Override
    public UserRoleApply findOne(Long roleApplyId, Long uid) {
        if(roleApplyId == null){
            return null;
        }
        UserRoleApplyQuery query = new UserRoleApplyQuery();
        query.setId(roleApplyId);
        if(uid != null){
            query.setUid(uid);
        }
        return userRoleApplySlave.findOne(query);
    }

    /**
     * 根据用户id找出申请记录，roleId选填
     *
     * @param uid
     * @param roleId
     * @param pageSearchDTO
     * @return
     */
    @Override
    public Page<UserRoleApply> findListByUid(Long uid, Integer roleId, PageSearchDTO pageSearchDTO) {
        if(uid == null || roleId == null){
            return null;
        }
        UserRoleApplyQuery query = new UserRoleApplyQuery();
        query.setUid(uid);
        query.setRoleId(roleId);
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        PageRequest pageRequest = PageRequest.of(pageSearchDTO.getCurrentPage() - 1, pageSearchDTO.getPageSize(), sort);
        return userRoleApplySlave.findAll(query, pageRequest);
    }

    /**
     * 获取所有
     *
     * @param condition
     * @return
     */
    @Override
    public Page<UserRoleApply> findAll(RoleListSearchDTO condition) {
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        Pageable page = PageRequest.of(condition.getCurrentPage() - 1, condition.getPageSize(), sort);
        if(condition == null){
            return userRoleApplySlave.findAll(page);
        }
        UserRoleApplyQuery query = new UserRoleApplyQuery();
        if(condition.getRoleType() != null){
            query.setRoleId(condition.getRoleType());
        }
        if(StringUtils.isNotEmpty(condition.getMerchantName())){
            query.setMerchantName(condition.getMerchantName());
        }
        if(StringUtils.isNotEmpty(condition.getLegalEntity())){
            query.setLegalEntity(condition.getLegalEntity());
        }
        if(StringUtils.isNotEmpty(condition.getPhoneNumber())){
            query.setPhoneNumber(condition.getPhoneNumber());
        }
        if(condition.getRoleStatus() != null){
            query.setApplyStatus(condition.getRoleStatus());
        }
        if(condition.getStartTime() != null){
            query.setStartTime(condition.getStartTime());
        }
        if(condition.getEndTime() != null){
            query.setEndTime(condition.getEndTime());
        }
        return userRoleApplySlave.findAll(query, page);
    }

    /**
     * 根据角色申请id去更新
     *
     * @param userRoleApply
     * @return
     */
    @Override
    public int updateByRoleApplyId(UserRoleApply userRoleApply) {
        if (userRoleApply == null){
            return 0;
        }
        UserRoleApplyQuery query = new UserRoleApplyQuery();
        query.setId(userRoleApply.getId());
        query.setUid(userRoleApply.getUid());
        List<String> updateFields = new ArrayList();
        if(userRoleApply.getApplyStatus() != null){
            updateFields.add("applyStatus");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getMerchantName())){
            updateFields.add("merchantName");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getLegalEntity())){
            updateFields.add("legalEntity");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getLegalEntityIdNumber())){
            updateFields.add("legalEntityIdNumber");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getTopContacts())){
            updateFields.add("topContacts");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getTopContactsPhoneNumber())){
            updateFields.add("topContactsPhoneNumber");
        }
        if(userRoleApply.getRelationship() != null){
            updateFields.add("relationship");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getBusinessLicenseNumber())){
            updateFields.add("businessLicenseNumber");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getBusinessLicenseFile())){
            updateFields.add("businessLicenseFile");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getLetterOfAttorneyFile())){
            updateFields.add("letterOfAttorneyFile");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getBusinessQualificationFile())){
            updateFields.add("businessQualificationFile");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getDrivingLicenseFile())){
            updateFields.add("drivingLicenseFile");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getHouseProprietaryCertificateFile())){
            updateFields.add("houseProprietaryCertificateFile");
        }
        if (userRoleApply.getCheckUid() != null){
            updateFields.add("checkUid");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getCheckPerson())){
            updateFields.add("checkPerson");
        }
        if(StringUtils.isNotEmpty(userRoleApply.getRemarks())){
            updateFields.add("remarks");
        }
        String[] array = new String[updateFields.size()];
        updateFields.toArray(array);
        return userRoleApplyMaster.update(userRoleApply, query, array);
    }

    /**
     * 根据uid获取角色申请列表
     *
     * @param uid
     * @return
     */
    @Override
    public List<UserRoleApply> findApplyList(Long uid) {
        UserRoleApplyQuery query = new UserRoleApplyQuery();
        Integer[] status = new Integer[]{RoleStatusEnum.NORMAL.getRoleStatue(),
                RoleStatusEnum.UNDER_REVIEW.getRoleStatue(), RoleStatusEnum.FREEZE.getRoleStatue()};
        query.setUid(uid);
        query.setStatus(Arrays.asList(status));
        return userRoleApplySlave.findAll(query);
    }

    /**
     * 根据uid找出指定的记录
     *
     * @param uid 非必选
     * @return
     */
    @Override
    public UserRoleApply findOne(Long uid) {
        UserRoleApplyQuery query = new UserRoleApplyQuery();
        query.setUid(uid);
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(0, 1, sort);
        Page<UserRoleApply> page = userRoleApplySlave.findAll(query, pageable);
        List<UserRoleApply> pageContent = page.getContent();
        if (CollectionUtils.isEmpty(pageContent)){
            return null;
        }
        return pageContent.get(0);
    }
}