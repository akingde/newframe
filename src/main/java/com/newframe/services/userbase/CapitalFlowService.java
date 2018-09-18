package com.newframe.services.userbase;

import com.newframe.dto.after.request.DrawAssetSearchDTO;
import com.newframe.entity.user.CapitalFlow;
import org.springframework.data.domain.Page;

/**
 * @author WangBin
 */
public interface CapitalFlowService {

    Page<CapitalFlow> findAll(Long uid, DrawAssetSearchDTO drawAssetSearchDTO, Integer type);

    CapitalFlow findOne(Long orderId);

    CapitalFlow findOne(CapitalFlow capitalFlow);

    CapitalFlow insert(CapitalFlow capitalFlow);

    int update(CapitalFlow capitalFlow);
}
