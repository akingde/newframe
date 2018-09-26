package com.newframe.services.block;

import com.newframe.blockchain.entity.ResponseChain;
import com.newframe.dto.block.*;

import java.util.List;

/**
 * @author WangBin
 */
public interface BlockChainService {

    /**
     * 资金方申请上链
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    ResponseChain funderApply(Long uid, String pubKey, String merchantName);

    /**
     * 出租方申请成功
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    ResponseChain lessorApply(Long uid, String pubKey, String merchantName);

    /**
     * 供应商申请成功
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    ResponseChain supplierApply(Long uid, String pubKey, String merchantName);

    /**
     * 一级租赁商申请成功
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    ResponseChain t1MerchantApply(Long uid, String pubKey, String merchantName);

    /**
     * 二级租赁商申请成功
     * @param belongTo 隶属的一级租赁商uid
     * @param uid
     * @param pubKey 用户公钥
     * @param merchantName
     * @return
     */
    ResponseChain t2MerchantApply(Long belongTo, Long uid, String pubKey, String merchantName);

    /**
     * 删除小B
     * @param t1MerchantUid 大Buid
     * @param t2MerchantUid 小Buid
     * @return
     */
    ResponseChain rmT2Merchant(Long t1MerchantUid, Long t2MerchantUid);

    /**
     * 融资购机
     * @param financeApplyDTO
     * @return
     */
    ResponseChain financeApply(FinanceApply financeApplyDTO);

    /**
     * 资金方拒绝订单
     * @param orderNum 订单编号
     * @param funderUid 资金方uid
     * @return
     */
    ResponseChain financeRefuse(Long orderNum, Long funderUid);

    /**
     * 资金方放款给供应商
     * @param fundSupplier
     * @return
     */
    ResponseChain fundSupplier(FundSupplier fundSupplier);

    /**
     * 供应商发货
     * @param supplierDeliver
     * @return
     */
    ResponseChain supplierDeliver(SupplierDeliver supplierDeliver);

    /**
     * 融资购机订单确认收货
     * @param orderNum 订单id
     * @param trackingNum 物流单号
     * @param confirmTime 确认收货时间
     * @return
     */
    ResponseChain confirmForFinance(Long orderNum, String trackingNum, Integer confirmTime);

    /**
     * 租赁商向出租方租机
     * @param rentalApply
     * @return
     */
    ResponseChain rentalApply(RentalApply rentalApply);

    /**
     * 出租方拒绝订单
     * @param orderNum 订单号
     * @param lessorUid 出租方uid
     * @return
     */
    ResponseChain rentalRefuse(Long orderNum, Long lessorUid);

    /**
     * 租赁商打款给出租方
     * @param orderNum 订单id
     * @param lessorUid 出租方uid
     * @param merchantUid 租赁商uid
     * @param payTime 打款时间
     * @return
     */
    ResponseChain payLessor(Long orderNum, Long lessorUid, Long merchantUid, Integer payTime);

    /**
     * 出租方发货
     * @param lessorDeliver
     * @return
     */
    ResponseChain lessorDeliver(LessorDeliver lessorDeliver);

    /**
     * 租机订单确认收货
     * @param orderNum 订单id
     * @param trackingNum 物流编号
     * @param confirmTime 确认收货时间
     * @return
     */
    ResponseChain confirmForRental(Long orderNum, String trackingNum, Integer confirmTime);

    /**
     * 添加失信记录
     * @param addDiscreditRecord
     * @return
     */
    ResponseChain addDiscreditRecord(AddDiscreditRecord addDiscreditRecord);

    /**
     * 添加白名单
     * @param uids uid数组
     * @return
     */
    ResponseChain addWhitelists(List<Long> uids);

    /**
     * 删除白名单
     * @param uids uid数组
     * @return
     */
    ResponseChain rmWhitelists(List<Long> uids);

    /**
     * 添加黑名单
     * @param uids uid数组
     * @param roleId 角色id
     * @return
     */
    ResponseChain addBlacklists(List<Long> uids, Integer roleId);
}
