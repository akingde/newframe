package com.newframe.services.userbase.impl;

import com.google.common.collect.Lists;
import com.newframe.dto.after.request.DrawAssetSearchDTO;
import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.CapitalFlow;
import com.newframe.enums.user.AssetTypeEnum;
import com.newframe.repositories.dataMaster.user.CapitalFlowMaster;
import com.newframe.repositories.dataQuery.user.CapitalFlowQuery;
import com.newframe.repositories.dataSlave.user.CapitalFlowSlave;
import com.newframe.services.userbase.CapitalFlowService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangBin
 */
@Service
public class CapitalFlowServiceImpl implements CapitalFlowService {

    @Autowired
    private CapitalFlowMaster capitalFlowMaster;
    @Autowired
    private CapitalFlowSlave capitalFlowSlave;
    @Autowired
    private IdGlobalGenerator idGlobalGenerator;

    @Override
    public Page<CapitalFlow> findAll(Long uid, DrawAssetSearchDTO drawAssetSearchDTO, Integer type) {
        CapitalFlowQuery query = new CapitalFlowQuery();
        query.setUid(uid);
        if(drawAssetSearchDTO.getOrderId() != null) {
            query.setOrderId(drawAssetSearchDTO.getOrderId());
        }
        if(drawAssetSearchDTO.getOrderStatus() != null){
            query.setOrderStatus(drawAssetSearchDTO.getOrderStatus());
        }
        if(StringUtils.isNotEmpty(drawAssetSearchDTO.getMerchantName())){
            query.setMerchantName(drawAssetSearchDTO.getMerchantName());
        }
        if(StringUtils.isNotEmpty(drawAssetSearchDTO.getPhoneNumber())){
            query.setPhoneNumber(drawAssetSearchDTO.getPhoneNumber());
        }
        if(drawAssetSearchDTO.getStartTime() != null){
            query.setStartTime(drawAssetSearchDTO.getStartTime());
        }
        if(drawAssetSearchDTO.getEndTime() != null){
            query.setEndTime(drawAssetSearchDTO.getEndTime());
        }
        if(!AssetTypeEnum.isEmpty(type)){
            query.setType(type);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        Pageable page = PageRequest.of(drawAssetSearchDTO.getCurrentPage() - 1, drawAssetSearchDTO.getPageSize(), sort);
        return capitalFlowSlave.findAll(query, page);
    }

    @Override
    public CapitalFlow findOne(Long orderId) {
        CapitalFlowQuery query = new CapitalFlowQuery();
        query.setOrderId(orderId);
        return capitalFlowSlave.findOne(query);
    }

    @Override
    public CapitalFlow findOne(CapitalFlow capitalFlow) {
        if(capitalFlow == null || capitalFlow.getBankMoneyFlowId() == null){
            return null;
        }
        CapitalFlowQuery query = new CapitalFlowQuery();
        if(capitalFlow.getBankMoneyFlowId() != null){
            query.setBankMoneyFlowId(capitalFlow.getBankMoneyFlowId());
        }
        return capitalFlowSlave.findOne(query);
    }

    @Override
    public CapitalFlow insert(CapitalFlow capitalFlow) {
        if(capitalFlow == null){
            return null;
        }
        capitalFlow.setOrderId(idGlobalGenerator.getSeqId(CapitalFlow.class));
        return capitalFlowMaster.save(capitalFlow);
    }

    @Override
    public int update(CapitalFlow capitalFlow) {
        if(capitalFlow == null || capitalFlow.getOrderId() == null){
            return 0;
        }
        CapitalFlowQuery query = new CapitalFlowQuery();
        query.setOrderId(capitalFlow.getOrderId());
        List<String> updateFields = Lists.newArrayList();
        if(capitalFlow.getOrderStatus() != null){
            updateFields.add("orderStatus");
        }
        if(capitalFlow.getCheckUid() != null){
            updateFields.add("checkUid");
        }
        if(StringUtils.isNotEmpty(capitalFlow.getCheckName())){
            updateFields.add("checkName");
        }
        if(capitalFlow.getBankMoneyFlowId() != null){
            updateFields.add("bankMoneyFlowId");
        }
        String[] array =new String[updateFields.size()];
        updateFields.toArray(array);
        return capitalFlowMaster.update(capitalFlow, query, array);
    }

    @Override
    public Page<CapitalFlow> findAll(Long uid, PageSearchDTO condition, Integer type) {
        CapitalFlowQuery query = new CapitalFlowQuery();
        query.setUid(uid);
        if(!AssetTypeEnum.isEmpty(type)){
            query.setType(type);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "ctime");
        Pageable page = PageRequest.of(condition.getCurrentPage() - 1, condition.getPageSize(), sort);
        return capitalFlowSlave.findAll(query, page);
    }
}
