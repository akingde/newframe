package com.newframe.services.userbase.impl;

import com.newframe.entity.user.MerchantAppoint;
import com.newframe.repositories.dataMaster.user.MerchantAppointMaster;
import com.newframe.repositories.dataSlave.user.MerchantAppointSlave;
import com.newframe.services.userbase.MerchantAppointService;
import com.newframe.utils.cache.IdGlobalGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author WangBin
 */
public class MerchantAppointServiceImpl implements MerchantAppointService {

    @Autowired
    private MerchantAppointMaster merchantAppointMaster;
    @Autowired
    private MerchantAppointSlave merchantAppointSlave;
    @Autowired
    private IdGlobalGenerator idGlobalGenerator;

    /**
     * 根据租赁商id获取
     *
     * @param rentMerchantUid
     * @return
     */
    @Override
    public List<MerchantAppoint> findAll(Long rentMerchantUid) {
        return null;
    }

    /**
     * 根据uid 和 供应商uid数组查询
     *
     * @param rentMerchantUid
     * @param supplierUids
     * @return
     */
    @Override
    public List<MerchantAppoint> findAll(Long rentMerchantUid, Long[] supplierUids) {
        return null;
    }

    /**
     * 批量添加制定关系
     *
     * @param merchantAppoints
     * @return
     */
    @Override
    public List<MerchantAppoint> batchInsert(List<MerchantAppoint> merchantAppoints) {
        return null;
    }

    /**
     * 根据指定id去删除
     *
     * @param appointIds
     */
    @Override
    public void remove(List<Long> appointIds) {

    }
}
