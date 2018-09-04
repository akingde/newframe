package com.newframe.services.userbase;

import com.newframe.entity.user.MerchantAppoint;

import java.util.List;

/**
 * @author WangBin
 */
public interface MerchantAppointService {

    /**
     * 根据租赁商id获取
     * @param rentMerchantUid
     * @return
     */
    List<MerchantAppoint> findAll(Long rentMerchantUid);

    /**
     * 根据uid 和 供应商uid数组查询
     * @param rentMerchantUid
     * @param supplierUids
     * @return
     */
    List<MerchantAppoint> findAll(Long rentMerchantUid, List<Long> supplierUids);

    /**
     * 批量添加制定关系
     * @param merchantAppoints
     * @return
     */
    List<MerchantAppoint> batchInsert(List<MerchantAppoint> merchantAppoints);

    /**
     * 根据指定id去删除
     * @param merchantAppoints
     */
    void remove(List<MerchantAppoint> merchantAppoints);
}
