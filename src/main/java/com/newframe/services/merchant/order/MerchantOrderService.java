package com.newframe.services.merchant.order;

import com.newframe.dto.OperationResult;
import com.newframe.dto.merchant.order.*;

/**
 * @author kfm
 * @date 2018.09.14 11:47
 */
public interface MerchantOrderService {
    /**
     * 推送订单
     * @param merchantOrder 订单信息封装
     * @return 操作结果
     */
    OperationResult<Boolean> pushOrder(MerchantOrderDTO merchantOrder);

    /**
     * 查询订单信息
     * @param merchantInfo 商家信息
     * @return 操作结果
     */
    OperationResult<OrderInfoDTO> getOrderInfo(MerchantInfoDTO merchantInfo);

    /**
     * 续租
     * @param reletDTO 续租时长
     * @return 操作结果
     */
    OperationResult<Boolean> relet(ReletDTO reletDTO);

    /**
     * 续租
     * @param merchantInfo 商家信息
     * @return 操作结果
     */
    OperationResult<AddressDTO> getAddress(MerchantInfoDTO merchantInfo);

    /**
     * 租客还款通知
     * @param repayNotice 还款通知
     * @return 操作结果
     */
    OperationResult<String> repayNotice(RepayNoticeDTO repayNotice);

    /**
     * 第三方平台订单状态同步通知
     * @param dto 请求数据
     * @return 操作结果
     */
    OperationResult<String> pushStatus(PushOrderStatusDTO dto);
}
