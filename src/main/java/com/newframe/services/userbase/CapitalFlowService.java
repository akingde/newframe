package com.newframe.services.userbase;

import com.newframe.dto.after.request.DrawAssetSearchDTO;
import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.entity.user.CapitalFlow;
import org.springframework.data.domain.Page;

/**
 * @author WangBin
 */
public interface CapitalFlowService {

    Page<CapitalFlow> findAll(DrawAssetSearchDTO drawAssetSearchDTO);

    CapitalFlow findOne(Long orderId);

    CapitalFlow findOne(CapitalFlow capitalFlow);

    CapitalFlow insert(CapitalFlow capitalFlow);

    int update(CapitalFlow capitalFlow);

    Page<CapitalFlow> findAll(Long uid, PageSearchDTO condition, Integer status, Integer type);
}
