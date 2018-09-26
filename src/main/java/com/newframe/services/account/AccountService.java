package com.newframe.services.account;

import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
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
    JsonResult listFunderOrderInvestment(Long uid, Integer currentPage, Integer pageSize, Integer orderStatus);

    /**
     * 18.查看资金方分期收款计划
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
    JsonResult listFunderOrderOverdue(Long uid, Integer currentPage, Integer pageSize, Integer orderStatus);

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
    JsonResult listSupplierOrderSell(Long uid, Integer currentPage, Integer pageSize, Integer orderStatus);

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
    JsonResult listHirerOrderMaterial(Long uid, Integer currentPage, Integer pageSize, Integer orderStatus);

    /**
     * 28.查看出租方分期收款计划
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
    JsonResult listHirerOrderOverdue(Long uid, Integer currentPage, Integer pageSize, Integer orderStatus);

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
    Account getAccountRenter(Long uid);

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
     *
     * @param uid
     * @return
     */
    AccountRenterFinancingMachine getAccountRenterFinancingMachine(Long uid);

    /**
     * 用户注册时，订单融资初始化
     * @return
     */
    AccountRenterFinancingMachine saveAccountRenterFinancingMachine(Long uid);

    /**
     * 更新订单融资的统计信息
     * @param machine
     * @return
     */
    AccountRenterFinancingMachine updateAccountRenterFinancingMachine(AccountRenterFinancingMachine machine);

    /**
     * 我是租赁商订单融资账户订单融资列表
     *
     * @param uid
     * @param orderStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<AccountRenterFinancing> getAccountRenterFinancing(Long uid, Integer repaymentStatus, Integer orderStatus, Integer currentPage, Integer pageSize);

    /**
     * 我是租赁商订单融资账户订单融资列表查看订单详情
     *
     * @param orderId
     * @return
     */
    List<AccountRenterRepay> listAccountRenterRepay(Long orderId);

    /**
     * 获取租赁商租赁账户
     *
     * @param uid
     * @return
     */
    AccountRenterRentMachine getAccountRenterRentMachine(Long uid);

    /**
     * 10.我是租赁商租赁账户租赁明细列表
     *
     * @param uid
     * @param payStatus
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<AccountRenterRentDetail> getAccountRenterRentDetail(Long uid, Integer payStatus, Integer currentPage, Integer pageSize);

    /**
     * 12.获取租赁商订单逾期账户
     *
     * @param uid
     * @return
     */
    AccountRenterOverdueAsset getAccountRenterOverdueAsset(Long uid);

    /**
     * 13.我是租赁商订单逾期账户下租赁明细列表
     *
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<AccountRenterOverdueDetail> getAccountRenterOverdueDetail(Long uid, Integer currentPage, Integer pageSize);

    /**
     * 保存账户的接口
     * @param account
     * @return
     */
    Account saveAccount(Account account);

    /**
     * 保存租赁商的账户资产下的租赁明细
     * @param accountRenterRent
     * @return
     */
    AccountRenterRent saveAccountRenterRent(AccountRenterRent accountRenterRent);

    /**
     * 保存accountRenterRentDetail
     * @param accountRenterRentDetail
     * @return
     */
    AccountRenterRentDetail saveAccountRenterRentDetail(AccountRenterRentDetail accountRenterRentDetail);

    /**
     * 保存AccountRenterRepay
     * @param accountRenterRepays
     * @return
     */
    List<AccountRenterRepay> saveAccountRenterRepay(List<AccountRenterRepay> accountRenterRepays);

    /**
     * 操作账户的数据
     * @param accountStatement
     * @return
     */
    AccountStatement saveAccountStatement(AccountStatement accountStatement);


    /**
     * 出租方(租户)账户
     * 由订单中心那边，调用，将相关信息插入到表account_renter_rent和account_lessor_matter_asset
     *
     * @return
     */
    OperationResult<Boolean> saveAccountLessorMatterAssetDetail(Long uid, Long orderId, Long orderTime, Long renterId, String renterName, String associatedOrderId,
                                                                String productBrand, String productModel, String productColour, String productStorage, String productMemory,
                                                                BigDecimal totalRentAccount, Integer monthNumber);
    /**
     * 资金方账户
     * 由订单中心那边，调用，将相关信息插入到表account_renter_rent和account_funding_finance_asset
     *
     * @return
     */
    OperationResult<Boolean> saveAccountFundingFinanceAssetDetail(Long uid, Long orderId, Long orderTime, Long renterId, String renterName, String relevanceOrderId, BigDecimal totalRentAccount, Integer monthNumber);

    /**
     * 根据UID查询到账户
     * @param uid
     * @return
     */
    Account getAccount(Long uid);

    /**
     * 更新Account
     * @param acc
     * @return
     */
    Account updateAccount(Account acc);

    /**
     * 保存AccountRenterFinancing
     * @param accountRenterFinancing
     * @return
     */
    AccountRenterFinancing saveAccountRenterFinancing(AccountRenterFinancing accountRenterFinancing);

    /**
     * 根据主键查询AccountRenterRepay
     * @param id
     * @return
     */
    AccountRenterRepay getAccountRenterRepay(Long id);

    /**
     * 根据订单Id去租赁商订单表查询信息
     * @param orderId
     * @return
     */
    AccountRenterFinancing getAccountRenterFinancing(Long orderId);

    /**
     * 根据订单Id,查询资金方的订单信息
     * @param orderId
     * @return
     */
    AccountFundingFinanceAsset getAccountFundingFinanceAsset(Long orderId);

    /**
     * 还款后更新状态呀
     * @param accountRenterFinancing
     * @return
     */
    AccountRenterFinancing updateAccountRenterFinancing(AccountRenterFinancing accountRenterFinancing);

    /**
     * 还款后更新状态
     * @param accountFundingFinanceAsset
     * @return
     */
    AccountFundingFinanceAsset updateAccountFundingFinanceAsset(AccountFundingFinanceAsset accountFundingFinanceAsset);

    /**
     * 更新还款后的状态
     * @param accountRenterRepay
     * @return
     */
    AccountRenterRepay updateAccountRenterRepay(AccountRenterRepay accountRenterRepay);

    /**
     * 操作出租方的账户
     * @param orderId
     * @return
     */
    AccountLessorMatterAsset getAccountLessorMatterAsset(Long orderId);

    /**
     * 更新AccountLessorMatterAsset
     * @param accountLessorMatterAsset
     * @return
     */
    AccountLessorMatterAsset updateAccountLessorMatterAsset(AccountLessorMatterAsset accountLessorMatterAsset);

    /**
     * 根据这个用户uid，计算出所有他的融资金额
     * @param uid
     * @return
     */
    BigDecimal getorderFinancing(Long uid);

    /**
     * 根据用户的Uid,查询本月应还的金额
     * @param uid
     * @param firstDayOfMonth
     * @param lastDayOfMonth
     * @return
     */
    List<AccountRenterRepay> listAccountRenterRepay(Long uid, Integer firstDayOfMonth, Integer lastDayOfMonth);

    /**
     * 保存AccountRenterRentMachine操作
     * @param machine
     * @return
     */
    AccountRenterRentMachine saveAccountRenterRentMachine(AccountRenterRentMachine machine);

    /**
     * 根据订单的ID,获取租机订单
     * @param orderId
     * @return
     */
    AccountRenterRent getAccountRenterRent(Long orderId);
}
