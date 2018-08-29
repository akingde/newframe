package com.newframe.services.userbase.impl;

import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.UserRoleApply;
import com.newframe.repositories.dataMaster.user.UserRoleApplyMaster;
import com.newframe.repositories.dataQuery.user.UserRoleApplyQuery;
import com.newframe.repositories.dataSlave.user.UserRoleApplySlave;
import com.newframe.services.userbase.UserRoleApplyService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        /*UserRoleApplyQuery query = new UserRoleApplyQuery();
        query.setId(roleApplyId);
        if(uid != null){
            query.setUid(uid);
        }*/
        return userRoleApplySlave.findOne(uid);
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
        if(userRoleApply.getApplyStatus() == null){
            updateFields.add("applyStatus");
        }
        if(userRoleApply.getMerchantName() == null){
            updateFields.add("merchantName");
        }
        if(userRoleApply.getLegalEntity() == null){
            updateFields.add("legalEntity");
        }
        if(userRoleApply.getLegalEntityIdNumber() == null){
            updateFields.add("legalEntityIdNumber");
        }
        if(userRoleApply.getTopContacts() == null){
            updateFields.add("topContacts");
        }
        if(userRoleApply.getTopContactsPhoneNumber() == null){
            updateFields.add("topContactsPhoneNumber");
        }
        if(userRoleApply.getRelationship() == null){
            updateFields.add("relationship");
        }
        if(userRoleApply.getBusinessLicenseNumber() == null){
            updateFields.add("businessLicenseNumber");
        }
        if(userRoleApply.getBusinessLicenseFile() == null){
            updateFields.add("businessLicenseFile");
        }
        if(userRoleApply.getLetterOfAttorneyFile() == null){
            updateFields.add("letterOfAttorneyFile");
        }
        if(userRoleApply.getBusinessQualificationFile() == null){
            updateFields.add("businessQualificationFile");
        }
        if(userRoleApply.getDrivingLicenseFile() == null){
            updateFields.add("drivingLicenseFile");
        }
        if(userRoleApply.getHouseProprietaryCertificateFile() == null){
            updateFields.add("houseProprietaryCertificateFile");
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
        query.setUid(uid);
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
            return new UserRoleApply();
        }
        return pageContent.get(0);
    }
}