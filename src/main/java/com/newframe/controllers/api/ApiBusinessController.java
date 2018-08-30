package com.newframe.controllers.api;

import com.newframe.controllers.BaseController;
import com.newframe.controllers.JsonResult;
import com.newframe.dto.OperationResult;
import com.newframe.dto.RequestUser;
import com.newframe.dto.user.request.PageSearchDTO;
import com.newframe.dto.user.request.ProductModifyDTO;
import com.newframe.dto.user.response.ProductDTO;
import com.newframe.services.user.RoleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:wangdong
 * @description:商家商户产品相关模块的Controller
 */
@RestController
@RequestMapping("/app/business/")
public class ApiBusinessController extends BaseController {

    @Autowired
    private RoleBaseService roleBaseService;

    /**
     * @Description 获取产品列表
     * @Author WangBin
     * @Return
     * @Date 2018/8/8 18:11
     */
    @PostMapping("getProductList")
    public JsonResult getProductList(Integer roleId, PageSearchDTO condition) {
        Long uid = RequestUser.getCurrentUid();
        OperationResult<ProductDTO> product = roleBaseService.getProductList(uid, roleId, condition);
        return success(product.getEntity());
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
    public JsonResult addProduct(Integer roleId, ProductModifyDTO productModifyDTO) {
        Long uid = RequestUser.getCurrentUid();
        OperationResult<Boolean> result = roleBaseService.addProduct(uid, roleId, productModifyDTO);
        if (result.getEntity()){
            return error(result.getErrorCode());
        }
        return success(result.getSucc());
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
    public JsonResult modifyProduct(Integer roleId, ProductModifyDTO productModifyDTO) {
        Long uid = RequestUser.getCurrentUid();
        OperationResult<Boolean> result = roleBaseService.modifyProduct(uid, roleId, productModifyDTO);
        if (result.getEntity()){
            return error(result.getErrorCode());
        }
        return success(result.getSucc());
    }

    /**
     * @Description 下架产品
     * @Author WangBin
     * @Param productId 产品id
     * @Return
     * @Date 2018/8/8 18:13
     */
    @PostMapping("removeProduct")
    public JsonResult removeProduct(Integer roleId, Long productId) {
        Long uid = RequestUser.getCurrentUid();
        OperationResult<Boolean> result = roleBaseService.removeProduct(uid, roleId, productId);
        if (result.getEntity()){
            return error(result.getErrorCode());
        }
        return success(result.getSucc());
    }
}
