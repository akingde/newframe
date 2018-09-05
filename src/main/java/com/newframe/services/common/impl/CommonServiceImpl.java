package com.newframe.services.common.impl;

import com.newframe.dto.OperationResult;
import com.newframe.dto.common.ExpressInfo;
import com.newframe.dto.message.UserMessageInfo;
import com.newframe.entity.message.UserMessage;
import com.newframe.enums.BizErrorCode;
import com.newframe.repositories.dataQuery.message.UserMessageQuery;
import com.newframe.repositories.dataSlave.message.UserMessageSlave;
import com.newframe.services.common.CommonService;
import com.newframe.utils.KdniaoTrackQueryAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author:wangdong
 * @description:
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private UserMessageSlave userMessageSlave;


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

    /**
     * 首页查询用户的消息列表
     *
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public OperationResult<UserMessageInfo> listUserMessage(Long uid, Integer currentPage, Integer pageSize) {
        if (null == uid){
            return new OperationResult<>(BizErrorCode.NOT_LOGIN);
        }
        if (null == pageSize || null == currentPage){
            return new OperationResult<>(BizErrorCode.PARAM_INFO_ERROR);
        }
        UserMessageInfo userMessageInfo = new UserMessageInfo();
        Sort sort =new Sort(Sort.Direction.DESC,"ctime");
        PageRequest pageRequest = PageRequest.of(currentPage-1,pageSize,sort);
        UserMessageQuery query = new UserMessageQuery();
        query.setUid(uid);
        Page<UserMessage> userMessagePage = userMessageSlave.findAll(query,pageRequest);
        userMessageInfo.setList(userMessagePage.getContent());
        userMessageInfo.setTotal(userMessagePage.getTotalElements());
        return new OperationResult<>(userMessageInfo);
    }
}
