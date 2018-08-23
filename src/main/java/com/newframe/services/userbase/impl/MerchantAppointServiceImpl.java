package com.newframe.services.userbase.impl;

import com.newframe.entity.user.MerchantAppoint;
import com.newframe.repositories.dataMaster.user.MerchantAppointMaster;
import com.newframe.repositories.dataQuery.user.MerchantAppointQuery;
import com.newframe.repositories.dataSlave.user.MerchantAppointSlave;
import com.newframe.services.userbase.MerchantAppointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangBin
 */
@Service
public class MerchantAppointServiceImpl implements MerchantAppointService {

    @Autowired
    private MerchantAppointMaster merchantAppointMaster;
    @Autowired
    private MerchantAppointSlave merchantAppointSlave;

    /**
     * 根据租赁商id获取
     *
     * @param rentMerchantUid
     * @return
     */
    @Override
    public List<MerchantAppoint> findAll(Long rentMerchantUid) {
        MerchantAppointQuery query = new MerchantAppointQuery();
        query.setRentMerchantUid(rentMerchantUid);
        return merchantAppointSlave.findAll(query);
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
        MerchantAppointQuery query = new MerchantAppointQuery();
        query.setRentMerchantUid(rentMerchantUid);
        query.setSupplierUids(supplierUids);
        return merchantAppointSlave.findAll(query);
    }

    /**
     * 批量添加制定关系
     *
     * @param merchantAppoints
     * @return
     */
    @Override
    public List<MerchantAppoint> batchInsert(List<MerchantAppoint> merchantAppoints) {
        return merchantAppointMaster.saveAll(merchantAppoints);
    }

    /**
     * 根据指定id去删除
     *
     * @param merchantAppoints
     */
    @Override
    public void remove(List<MerchantAppoint> merchantAppoints) {
        merchantAppointMaster.deleteAll(merchantAppoints);
    }
}
