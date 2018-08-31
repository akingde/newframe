package com.newframe.services.userbase.impl;

import com.newframe.dto.after.request.FunderSearchDTO;
import com.newframe.entity.user.UserFunder;
import com.newframe.enums.user.RoleStatusEnum;
import com.newframe.repositories.dataMaster.user.UserFunderMaster;
import com.newframe.repositories.dataQuery.user.UserFunderQuery;
import com.newframe.repositories.dataSlave.user.UserFunderSlave;
import com.newframe.services.userbase.UserFunderService;
import org.apache.commons.lang3.StringUtils;
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
 *  资金方
 *
 * This class corresponds to the database table user_funder
 *
 * app用户的token
 * @mbggenerated do_not_delete_during_merge
 */
@Service
public class UserFunderServiceImpl implements UserFunderService {

    @Autowired
    private UserFunderMaster userFunderMaster;
    @Autowired
    private UserFunderSlave userFunderSlave;

    /**
     * 查询
     *
     * @param uid
     * @return
     */
    @Override
    public UserFunder findOne(Long uid) {
        if (null == uid){
            return null;
        }
        return userFunderSlave.findOne(uid);
    }

    /**
     * 根据uid获取在白名单中的资金方
     *
     * @param funderUid
     * @return
     */
    @Override
    public List<UserFunder> findAll(List<Long> funderUid) {
        UserFunderQuery query = new UserFunderQuery();
        query.setUids(funderUid);
        query.setIsWhite(false);
        return userFunderSlave.findAll(query);
    }

    /**
     * 获取所有的非白名单资金方
     *
     * @return
     */
    @Override
    public List<UserFunder> findAll() {
        UserFunderQuery query = new UserFunderQuery();
        query.setStatus(RoleStatusEnum.NORMAL.getRoleStatue());
        query.setIsWhite(false);
        return userFunderSlave.findAll(query);
    }

    /**
     * 获取所有白名单的资金方
     *
     * @param condition
     * @return
     */
    @Override
    public Page<UserFunder> findAll(FunderSearchDTO condition) {
        UserFunderQuery query = new UserFunderQuery();
        query.setIsWhite(true);
        if (StringUtils.isNotEmpty(condition.getMerchantName())){
            query.setMerchantName("%" + condition.getMerchantName() +"%");
        }
        if (StringUtils.isNotEmpty(condition.getPhoneNumber())){
            query.setPhoneNumber(condition.getPhoneNumber());
        }
        if (StringUtils.isNotEmpty(condition.getLegalEntity())){
            query.setLegalEntity(condition.getLegalEntity());
        }
        if (StringUtils.isNotEmpty(condition.getLegalEntityIdNumber())){
            query.setLegalEntityIdNumber(condition.getLegalEntityIdNumber());
        }
        if (condition.getStartTime() != null){
            query.setStartTime(condition.getStartTime());
        }
        if(condition.getEndTime() != null){
            query.setEndTime(condition.getEndTime());
        }
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        Pageable pageable = PageRequest.of(condition.getCurrentPage() - 1, condition.getPageSize(), sort);
        return userFunderSlave.findAll(query, pageable);
    }

    /**
     * 添加
     *
     * @param userFunder
     * @return
     */
    @Override
    public UserFunder insert(UserFunder userFunder) {
        return userFunder == null ? null : userFunderMaster.save(userFunder);
    }

    /**
     * 更新
     *
     * @param userFunder
     * @return
     */
    @Override
    public int update(UserFunder userFunder) {
        List<String> updateFields = new ArrayList();
        UserFunderQuery query = new UserFunderQuery();
        query.setUid(userFunder.getUid());
        if(StringUtils.isNotEmpty(userFunder.getMerchantName())){
            updateFields.add("merchantName");
        }
        if(StringUtils.isNotEmpty(userFunder.getLegalEntity())){
            updateFields.add("legalEntity");
        }
        if(StringUtils.isNotEmpty(userFunder.getLegalEntityIdNumber())){
            updateFields.add("legalEntityIdNumber");
        }
        if(StringUtils.isNotEmpty(userFunder.getTopContacts())){
            updateFields.add("topContacts");
        }
        if(StringUtils.isNotEmpty(userFunder.getTopContactsPhoneNumber())){
            updateFields.add("topContactsPhoneNumber");
        }
        if(userFunder.getRelationship() != null){
            updateFields.add("relationship");
        }
        if(StringUtils.isNotEmpty(userFunder.getBusinessLicenseNumber())){
            updateFields.add("businessLicenseNumber");
        }
        if(StringUtils.isNotEmpty(userFunder.getBusinessLicenseFile())){
            updateFields.add("businessLicenseFile");
        }
        if(StringUtils.isNotEmpty(userFunder.getLetterOfAttorneyFile())){
            updateFields.add("letterOfAttorneyFile");
        }
        if(StringUtils.isNotEmpty(userFunder.getBusinessQualificationFile())){
            updateFields.add("businessQualificationFile");
        }
        if(userFunder.getRoleStatus() != null){
            updateFields.add("roleStatus");
        }
        String[] array = new String[updateFields.size()];
        updateFields.toArray(array);
        return userFunderMaster.update(userFunder, query, array);
    }

    /**
     * 将指定的资金方添加/删除白名单
     *
     * @param isWhite
     * @param funderUid
     */
    @Override
    public int update(Boolean isWhite, List<Long> funderUid) {
        UserFunderQuery query = new UserFunderQuery();
        query.setUids(funderUid);
        UserFunder userFunder = new UserFunder();
        userFunder.setIsWhite(isWhite);
        return userFunderMaster.update(userFunder, query ,"isWhite");
    }

    /**
     * 将指定的资金方添加/删除到白名单
     *
     * @param isWhite
     * @param funderUid
     * @return
     */
    @Override
    public int update(Boolean isWhite, Long funderUid) {
        UserFunderQuery query = new UserFunderQuery();
        query.setUid(funderUid);
        UserFunder userFunder = new UserFunder();
        userFunder.setIsWhite(isWhite);
        return userFunderMaster.update(userFunder, query ,"isWhite");
    }

    /**
     * 删除
     *
     * @param uid
     */
    @Override
    public void delete(Long uid) {
        userFunderMaster.deleteById(uid);
    }
}