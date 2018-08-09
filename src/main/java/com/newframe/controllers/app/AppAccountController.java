package com.newframe.controllers.app;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:wangdong
 * @description:账户相关模块的Controller
 */
@RestController
@RequestMapping("/app/account/")
public class AppAccountController extends BaseController {


    /**
     * 租赁商获取账户信息
     * 1、用户的手机号码
     * 2、设置过密码
     * 3、收货地址
     * 4、授权申请信息
     * @return
     */
    @RequestMapping("getRenterAccountInfo")
    public JsonResult getRenterAccountInfo() {
        return null;
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
     * @return
     */
    @RequestMapping("getRenterAssetAccount")
    public JsonResult getRenterAssetAccount() {
        return null;
    }

    /**
     * 获取租赁商租赁明细
     * 涉及到分页
     * @return
     */
    @RequestMapping("getRenterRentDetail")
    public JsonResult getRenterRentDetail(Integer currentPage, Integer pageSize) {
        return null;
    }

    /**
     * 获取租赁商订单融资账户
     * 涉及到
     * 1、订单融资余额
     * 2、已结清融资本息
     * 3、未结清融资本息
     * 4、本月应还
     * @return
     */
    @RequestMapping("getRenterOrderFinanceAccount")
    public JsonResult getRenterOrderFinanceAccount() {
        return null;
    }

    /**
     * 订单融资列表
     * 涉及到分页
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("getRenterOrderFinanceList")
    public JsonResult getRenterOrderFinanceList(Integer currentPage, Integer pageSize) {
        return null;
    }
}
