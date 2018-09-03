package com.newframe.services.account;

import com.newframe.controllers.JsonResult;
import com.newframe.entity.account.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:zww 31个接口
 * @description:账户相关模块的接口
 */
public interface AccountService {


    /**
     * 充值
     * 这里应该有一个公共的账户系统
     * 直接往里面充值人民币
     *
     * @return
     */
    JsonResult recharge(BigDecimal amount);

    /**
     * 提现
     * 这里提现的金额只允许可用金额提现
     *
     * @return
     */
    JsonResult withdrawDeposit();


    /**
     * 租赁方相关的接口
     */


    /**
     * 租赁商获取账户信息
     * 1、用户的手机号码
     * 2、设置过密码
     * 3、收货地址
     * 4、授权申请信息
     *
     * @return
     */
    JsonResult getRenterAccountInfo();

    /**
     * 租赁商获取账户资产
     * 1、可用余额
     * 2、资产总额
     * 3、冻结资产
     * 4、保证金余额
     * 5、保证金垫付金额
     * 6、代收金额
     * 7、本月应收
     *
     * @return
     */
    JsonResult getRenterAssetAccount();

    /**
     * 获取租赁商租赁明细
     * 涉及到分页
     *
     * @return
     */
    JsonResult getRenterRentDetail(Integer currentPage, Integer pageSize);

    /**
     * 获取租赁商订单融资账户
     * 涉及到
     * 1、订单融资余额
     * 2、已结清融资本息
     * 3、未结清融资本息
     * 4、本月应还
     *
     * @return
     */
    JsonResult getRenterOrderFinanceAccount();

    /**
     * 订单融资列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    JsonResult listRenterOrderFinance(Integer currentPage, Integer pageSize);

    /**
     * 租赁商订单融资列列表
     * 根据订单的ID去查询订单融资的金额
     *
     * @param orderId
     * @return
     */
    JsonResult getRenterOrderFinanceDetail(Long orderId);

    /**
     * 获取租赁商租赁账户
     * 涉及到
     * 1、租赁总额
     * 2、累计应付租金
     * 3、已付租金
     * 4、待付租金
     *
     * @return
     */
    JsonResult getRenterOrderRentAccount();

    /**
     * 租赁商租赁账户下
     * 租赁明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    JsonResult listRenterOrderRentAccount(Integer currentPage, Integer pageSize);

    /**
     * 租赁商租赁账户下
     * 租赁明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    JsonResult getRenterOrderRentDetail(Long orderId);


    /**
     * 获取租赁商订单逾期账户
     * 涉及到
     * 1、逾期金额合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    JsonResult getRenterOrderOverdueAccount();

    /**
     * 租赁商订单逾期账户下
     * 租赁明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    JsonResult listRenterOrderOverdue(Integer currentPage, Integer pageSize);

    /**
     * 租赁商订单逾期账户下
     * 逾期订单租赁明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    JsonResult getRenterOrderOverdueDetail(Long orderId);

    /**
     * 资金方相关的接口
     */

    /**
     * 15.资金方获取账户资产
     * 1、可用余额
     * 2、资产总额
     * 3、冻结资产
     * 4、保证金余额
     * 5、保证金垫付金额
     * 6、代收金额
     * 7、本月应收
     *
     * @return
     */
    JsonResult getFunderAssetAccount(Long uid);


    /**
     * 16.获取资金方金融资产账户
     * 涉及到
     * 1、投资回报率
     * 2、市场平均投资回报率
     *
     * @return
     */
    JsonResult getFunderOrderFinancialAssets(Long uid);

    /**
     * 17.获取资金方金融资产下
     * 投资明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    JsonResult listFunderOrderInvestment(Long uid, Integer currentPage, Integer pageSize);

    /**
     * 18.获取资金方金融资产下
     * 获取资金方金融资产下
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    JsonResult getFunderOrderInvestmentDetail(Long uid, Long orderId);

    /**
     * 19.获取资金方逾期资产账户
     * 涉及到
     * 1、逾期金融合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    JsonResult getFunderOrderOverdueAssets(Long uid);

    /**
     * 20.获取资金方逾期资产
     * 逾期明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    JsonResult listFunderOrderOverdue(Long uid, Integer currentPage, Integer pageSize);

    /**
     * 21.获取资金方逾期资产
     * 逾期明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    JsonResult getFunderOrderOverdueDetail(Long uid, Long orderId);


    /**
     * 供应商相关账户接口
     */

    /**
     * 22.供应商获取账户资产
     * 1、可用余额
     * 2、资产总额
     * 3、冻结资产
     *
     * @return
     */
    JsonResult getSupplierAssetAccount(Long uid);

    /**
     * 23.获取供应商销售账户
     * 涉及到
     * 1、累计营收
     * 2、累计销售数量
     * 3、待发货数量
     *
     * @return
     */
    JsonResult getSupplierOrderSellAssets(Long uid);

    /**
     * 24.获取供应商销售账户下
     * 销售明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    JsonResult listSupplierOrderSell(Long uid, Integer currentPage, Integer pageSize);

    /**
     * 出租方相关接口
     */

    /**
     * 25.出租方获取账户资产
     * 1、可用余额
     * 2、资产总额
     * 3、冻结资产
     * 4、保证金
     * 5、代收金额
     * 6、本月应收
     *
     * @return
     */
    JsonResult getHirerAssetAccount(Long uid);

    /**
     * 26.获取出租方实物资产账户
     * 涉及到
     * 1、租赁总额
     * 2、累计应付租金
     * 3、已付租金
     * 4、待付租金
     * 5、投资回报率
     * 6、市场平均投资回报率
     *
     * @return
     */
    JsonResult getHirerOrderMaterialAssets(Long uid);

    /**
     * 27.获取出租方实物资产账户下
     * 实物明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    JsonResult listHirerOrderMaterial(Long uid, Integer currentPage, Integer pageSize);

    /**
     * 28.获取出租方实物资产账户下
     * 实物明细列表
     *
     * @return
     */
    JsonResult getHirerOrderMaterialDetail(Long uid, Long orderId);

    /**
     * 29.获取出租方逾期资产账户
     * 涉及到
     * 1、逾期金额合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    JsonResult getHirerOrderOverdueAssets(Long uid);

    /**
     * 30.获取出租方逾期资产账户下
     * 逾期明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    JsonResult listHirerOrderOverdue(Long uid, Integer currentPage, Integer pageSize);

    /**
     * 31.获取出租方逾期资产账户下
     * 逾期明细列表
     *
     * @return
     */
    JsonResult getHirerOrderOverdueDetail(Long uid, Long orderId);

    /**
     * 获取租赁商账户资产
     *
     * @param uid
     * @return
     */
    AccountRenter getAccountRenter(Long uid);

    /**
     * 获取租赁商账户资产下的租赁明细表
     *
     * @param uid
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<AccountRenterRent> getAccountRenterRent(Long uid, Integer orderStatus, Integer currentPage, Integer pageSize);

    /**
     * 查询租赁商关联的供应商
     *
     * @param uid
     * @return
     */
    List<AccountRenterAppointSupplier> listAccountRenterAppointSupplier(Long uid);

    /**
     * 获取租赁商订单融资账户
     * @param uid
     * @return
     */
    AccountRenterFinancingMachine getAccountRenterFinancingMachine(Long uid);

    /**
     * 我是租赁商订单融资账户订单融资列表
     * @param uid
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<AccountRenterFinancing> getAccountRenterFinancing(Long uid, Integer orderStatus, Integer currentPage, Integer pageSize);

    /**
     * 我是租赁商订单融资账户订单融资列表查看订单详情
     * @param orderId
     * @return
     */
    List<AccountRenterRepay> listAccountRenterRepay(Long orderId);

    /**
     * 获取租赁商租赁账户
     * @param uid
     * @return
     */
    AccountRenterRentMachine getAccountRenterRentMachine(Long uid);

    /**
     * 10.我是租赁商租赁账户租赁明细列表
     * @param uid
     * @param payStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<AccountRenterRentDetail> getAccountRenterRentDetail(Long uid, Integer payStatus, Integer currentPage, Integer pageSize);
}
