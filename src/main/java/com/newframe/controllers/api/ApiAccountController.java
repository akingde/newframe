package com.newframe.controllers.api;

import com.newframe.common.anony.Anonymous;
import com.newframe.common.anony.UserType;
import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.account.*;
import com.newframe.entity.account.*;
import com.newframe.enums.TypeEnum;
import com.newframe.services.account.AccountManageService;
import com.newframe.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:wangdong 31个接口
 * @description:账户相关模块的Controller
 */
@RestController
@RequestMapping("/rent/api/account/")
public class ApiAccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountManageService accountManageService;

    private Long getUid() {
        return 3436672695388700980L;
    }

    /**
     * 充值
     * 这里应该有一个公共的账户系统
     * 直接往里面充值人民币
     *
     * @return
     */
    @RequestMapping("recharge")
    public JsonResult recharge(BigDecimal amount) {
        return null;
    }

    /**
     * 提现
     * 这里提现的金额只允许可用金额提现
     *
     * @return
     */
    @RequestMapping("withdrawDeposit")
    public JsonResult withdrawDeposit() {
        return null;
    }


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
    @RequestMapping("getRenterAccountInfo")
    public JsonResult getRenterAccountInfo(Long uid) {

        uid = 10001L;

        OperationResult<RenterAccountInfo> result = accountManageService.getRenterAccountInfo(uid);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

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
    @RequestMapping("getRenterAssetAccount")
    public JsonResult getRenterAssetAccount(Long uid) {

        uid = 10001L;

        OperationResult<AccountRenter> result = accountManageService.getRenterAssetAccount(uid);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

    /**
     * 2.我是租赁商租赁明细列表
     * 涉及到分页
     *
     * @return
     */
    @RequestMapping("listRenterOrderRent")
    public JsonResult listRenterOrderRent(Long uid, Integer orderStatus, Integer currentPage, Integer pageSize) {

        uid = 10001L;

        OperationResult<AccountRenterRentInfo> result = accountManageService.listRenterOrderRent(uid, orderStatus, currentPage, pageSize);
        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

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
    @RequestMapping("getRenterOrderFinanceAccount")
    public JsonResult getRenterOrderFinanceAccount(Long uid) {

        uid = 10001L;

        OperationResult<AccountRenterFinancingMachine> result = accountManageService.getRenterOrderFinanceAccount(uid);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

    /**
     * 订单融资列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("listRenterOrderFinance")
    public JsonResult listRenterOrderFinance(Long uid,Integer orderStatus,Integer currentPage, Integer pageSize) {

        uid = 10001L;

        OperationResult<RenterOrderFinanceInfo> result = accountManageService.listRenterOrderFinance(uid,orderStatus,currentPage,pageSize);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

    /**
     * 租赁商订单融资列列表
     * 根据订单的ID去查询订单融资的金额
     *
     * @param orderId
     * @return
     */
    @RequestMapping("getRenterOrderFinanceDetail")
    public JsonResult getRenterOrderFinanceDetail(Long orderId) {

        orderId = 10089L;

        OperationResult<List<AccountRenterRepay>> result = accountManageService.getRenterOrderFinanceDetail(orderId);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

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
    @RequestMapping("getRenterOrderRentAccount")
    public JsonResult getRenterOrderRentAccount(Long uid) {

        uid = 10001L;

        OperationResult<AccountRenterRentMachine> result = accountManageService.getRenterOrderRentAccount(uid);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

    /**
     * 租赁商租赁账户下
     * 租机明细
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("listRenterOrderRentAccount")
    public JsonResult listRenterOrderRentAccount(Long uid,Integer payStatus,Integer currentPage, Integer pageSize) {

        uid = 10001L;

        OperationResult<RenterOrderRentDetailInfo> result = accountManageService.listRenterOrderRentAccount(uid,payStatus,currentPage,pageSize);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

    /**
     * 租赁商租赁账户下
     * 租赁明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @RequestMapping("getRenterOrderRentDetail")
    public JsonResult getRenterOrderRentDetail(Long orderId) {

        orderId = 10089L;

        OperationResult<List<AccountRenterRepay>> result = accountManageService.getRenterOrderFinanceDetail(orderId);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }


    /**
     * 获取租赁商订单逾期账户
     * 涉及到
     * 1、逾期金额合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    @RequestMapping("getRenterOrderOverdueAccount")
    public JsonResult getRenterOrderOverdueAccount(Long uid) {

        uid = 10001L;

        OperationResult<AccountRenterOverdueAsset> result = accountManageService.getAccountRenterOverdueAsset(uid);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

    /**
     * 租赁商订单逾期账户下
     * 租赁明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("listRenterOrderOverdue")
    public JsonResult listRenterOrderOverdue(Long uid,Integer currentPage, Integer pageSize) {

        uid = 10001L;

        OperationResult<RenterOrderOverdueDetailInfo> result = accountManageService.listRenterOrderOverdue(uid,currentPage,pageSize);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

    /**
     * 租赁商订单逾期账户下
     * 逾期订单租赁明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @RequestMapping("getRenterOrderOverdueDetail")
    public JsonResult getRenterOrderOverdueDetail(Long orderId) {

        orderId = 10089L;

        OperationResult<List<AccountRenterRepay>> result = accountManageService.getRenterOrderFinanceDetail(orderId);

        if (result.getSucc()) {
            return success(result.getEntity());
        }
        return error(result.getCode(), result.getMessage());
    }

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
    @Anonymous(true)
    @UserType(type = TypeEnum.app)
    @RequestMapping(value = "getFunderAssetAccount", method = RequestMethod.POST)
    public JsonResult getFunderAssetAccount(Long uid) {
        return accountService.getFunderAssetAccount(getUid());
    }


    /**
     * 16.获取资金方金融资产账户
     * 涉及到
     * 1、投资回报率
     * 2、市场平均投资回报率
     *
     * @return
     */
    @Anonymous(true)
    @UserType(type = TypeEnum.app)
    @RequestMapping(value = "getFunderOrderFinancialAssets", method = RequestMethod.POST)
    public JsonResult getFunderOrderFinancialAssets() {
        return accountService.getFunderOrderFinancialAssets(getUid());
    }

    /**
     * 17.获取资金方金融资产下
     * 投资明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Anonymous(true)
    @UserType(type = TypeEnum.app)
    @RequestMapping(value = "listFunderOrderInvestment", method = RequestMethod.POST)
    public JsonResult listFunderOrderInvestment(Integer currentPage, Integer pageSize) {
        return accountService.listFunderOrderInvestment(getUid(), currentPage, pageSize);
    }

    /**
     * 18.获取资金方金融资产下
     * 获取资金方金融资产下
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @Anonymous(true)
    @UserType(type = TypeEnum.app)
    @RequestMapping(value = "getFunderOrderInvestmentDetail", method = RequestMethod.POST)
    public JsonResult getFunderOrderInvestmentDetail(Long orderId) {
        return accountService.getFunderOrderInvestmentDetail(getUid(), orderId);
    }

    /**
     * 19.获取资金方逾期资产账户
     * 涉及到
     * 1、逾期金融合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    @Anonymous(true)
    @UserType(type = TypeEnum.app)
    @RequestMapping(value = "getFunderOrderOverdueAssets", method = RequestMethod.POST)
    public JsonResult getFunderOrderOverdueAssets() {
        return accountService.getFunderOrderOverdueAssets(getUid());
    }

    /**
     * 20.获取资金方逾期资产
     * 逾期明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Anonymous(true)
    @UserType(type = TypeEnum.app)
    @RequestMapping(value = "listFunderOrderOverdue", method = RequestMethod.POST)
    public JsonResult listFunderOrderOverdue(Integer currentPage, Integer pageSize) {
        return accountService.listFunderOrderOverdue(getUid(), currentPage, pageSize);
    }

    /**
     * 21.获取资金方逾期资产
     * 逾期明细列表
     * 根据订单的Id,去查看详情
     *
     * @param orderId
     * @return
     */
    @Anonymous(true)
    @UserType(type = TypeEnum.app)
    @RequestMapping(value = "getFunderOrderOverdueDetail", method = RequestMethod.POST)
    public JsonResult getFunderOrderOverdueDetail(Long orderId) {
        return accountService.getFunderOrderOverdueDetail(getUid(), orderId);
    }

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
    @RequestMapping(value = "getSupplierAssetAccount", method = RequestMethod.POST)
    public JsonResult getSupplierAssetAccount() {
        return accountService.getSupplierAssetAccount(getUid());
    }


    /**
     * 23.获取供应商销售账户
     * 涉及到
     * 1、累计营收
     * 2、累计销售数量
     * 3、待发货数量
     *
     * @return
     */
    @RequestMapping(value = "getSupplierOrderSellAssets", method = RequestMethod.POST)
    public JsonResult getSupplierOrderSellAssets() {
        return accountService.getSupplierOrderSellAssets(getUid());
    }

    /**
     * 24.获取供应商销售账户下
     * 销售明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "listSupplierOrderSell", method = RequestMethod.POST)
    public JsonResult listSupplierOrderSell(Integer currentPage, Integer pageSize) {
        return accountService.listSupplierOrderSell(getUid(), currentPage, pageSize);
    }

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
    @RequestMapping(value = "getHirerAssetAccount", method = RequestMethod.POST)
    public JsonResult getHirerAssetAccount() {
        return accountService.getHirerAssetAccount(getUid());
    }

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
    @RequestMapping(value = "getHirerOrderMaterialAssets", method = RequestMethod.POST)
    public JsonResult getHirerOrderMaterialAssets() {
        return accountService.getHirerOrderMaterialAssets(getUid());
    }

    /**
     * 27.获取出租方实物资产账户下
     * 实物明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "listHirerOrderMaterial", method = RequestMethod.POST)
    public JsonResult listHirerOrderMaterial(Integer currentPage, Integer pageSize) {
        return accountService.listHirerOrderMaterial(getUid(), currentPage, pageSize);
    }

    /**
     * 28.获取出租方实物资产账户下
     * 实物明细列表
     *
     * @return
     */
    @RequestMapping(value = "getHirerOrderMaterialDetail", method = RequestMethod.POST)
    public JsonResult getHirerOrderMaterialDetail(Long orderId) {
        return accountService.getHirerOrderMaterialDetail(getUid(), orderId);
    }

    /**
     * 29.获取出租方逾期资产账户
     * 涉及到
     * 1、逾期金额合计
     * 2、逾期笔数
     * 3、逾期率
     *
     * @return
     */
    @RequestMapping(value = "getHirerOrderOverdueAssets", method = RequestMethod.POST)
    public JsonResult getHirerOrderOverdueAssets() {
        return accountService.getHirerOrderOverdueAssets(getUid());
    }

    /**
     * 30.获取出租方逾期资产账户下
     * 逾期明细列表
     * 涉及到分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "listHirerOrderOverdue", method = RequestMethod.POST)
    public JsonResult listHirerOrderOverdue(Integer currentPage, Integer pageSize) {
        return accountService.listHirerOrderOverdue(getUid(), currentPage, pageSize);
    }

    /**
     * 31.获取出租方逾期资产账户下
     * 逾期明细列表
     *
     * @return
     */
    @RequestMapping(value = "getHirerOrderOverdueDetail", method = RequestMethod.POST)
    public JsonResult getHirerOrderOverdueDetail(Long orderId) {
        return accountService.getHirerOrderOverdueDetail(getUid(), orderId);
    }
}
