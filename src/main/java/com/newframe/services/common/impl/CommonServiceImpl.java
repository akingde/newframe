package com.newframe.services.common.impl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.common.ExpressInfo;
import com.newframe.enums.BizErrorCode;
import com.newframe.services.common.CommonService;
import com.newframe.utils.KdniaoTrackQueryAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author:wangdong
 * @description:
 */
@Service
public class CommonServiceImpl implements CommonService {


    /**
     * 根据快递公司和快递单号
     * 查询快递信息
     *
     * @param expCode 快递公司
     * @param expNo 快递单号
     * @return
     */
    @Override
    public OperationResult<ExpressInfo> getExpressMessage(String expCode, String expNo) {

        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
        ExpressInfo expressInfo = new ExpressInfo();
        expressInfo.setExpCompany(expCode);
        expressInfo.setExpNo(expNo);
        try {
            String result= api.getOrderTracesByJson(expCode, expNo);
            String expstatus = StringUtils.substringBetween(result,"[","]");
            if (StringUtils.isNotEmpty(expstatus)){
                expressInfo.setExpStatus(expstatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        return new OperationResult<>(expressInfo);
    }
}
