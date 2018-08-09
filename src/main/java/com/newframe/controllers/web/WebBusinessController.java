package com.newframe.controllers.web;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author:wangdong
 * @description:商家商户产品相关模块的Controller
 */
@RestController
@RequestMapping("/app/business/")
public class WebBusinessController extends BaseController {

    /**
     * @Description 获取产品列表
     * @Author WangBin
     * @Return
     * @Date 2018/8/8 18:11
     */
    @PostMapping("getProductList")
    public JsonResult getProductList() {
        return null;
    }

    /**
     * @Description 获取产品详情
     * @Author WangBin
     * @Param productId 产品id
     * @Return
     * @Date 2018/8/8 18:12
     */
    @PostMapping("getProductInfo")
    public JsonResult getProductInfo(Long productId) {
        return null;
    }

    /**
     * @Description 添加产品
     * @Author WangBin
     * @Param brand 品牌
     * @Param model 型号
     * @Param specification 容量 规格
     * @Param color 颜色
     * @Param guidePrice 市场指导价
     * @Param supplyPrice 供应价
     * @Param sotck 库存
     * @Return
     * @Date 2018/8/9 18:06
     */
    @PostMapping("addProduct")
    public JsonResult addProduct(String brand, String model, String specification, String color, BigDecimal guidePrice,
                                 BigDecimal supplyPrice, Integer sotck) {
        return null;
    }

    /**
     * @Description 修改产品信息
     * @Author WangBin
     * @Param productId 产品id
     * @Param brand 品牌
     * @Param model 型号
     * @Param specification 容量 规格
     * @Param color 颜色
     * @Param guidePrice 市场指导价
     * @Param supplyPrice 供应价
     * @Param sotck 库存
     * @Return
     * @Date 2018/8/9 18:08
     */
    @PostMapping("modifyProduct")
    public JsonResult modifyProduct(Long productId, String brand, String model, String specification, String color,
                                    BigDecimal guidePrice, BigDecimal supplyPrice, Integer sotck) {

        return null;
    }

    /**
     * @Description 下架产品
     * @Author WangBin
     * @Param productId 产品id
     * @Return
     * @Date 2018/8/8 18:13
     */
    @PostMapping("removeProduct")
    public JsonResult removeProduct(Long productId) {
        return null;
    }
}
