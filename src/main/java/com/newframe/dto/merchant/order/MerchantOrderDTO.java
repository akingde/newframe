package com.newframe.dto.merchant.order;

import com.newframe.dto.OperationResult;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 第三方平台推送订单数据封装
 * @author kfm
 * @date 2018.09.14 11:50
 */
@Data
public class MerchantOrderDTO {

    @NotBlank(message = "第三方平台订单id不能为空")
    private String partnerOrderId;

    @NotNull(message = "平台id不能为空")
    private Long partnerId;

    /**租赁商身份证号码，通过身份证号码与我们平台的租赁商进行关联*/
    @NotBlank(message = "租赁商身份证号码不能为空")
    private String renterMobile;

    @NotBlank(message = "租赁姓名不能为空")
    private String userRealname;

    @NotBlank(message = "租赁身份证号不能为空")
    private String userIdNumber;

    @NotBlank(message = "租客联系方式不能为空")
    private String userMobile;

    @NotBlank(message = "租客收货地址不能为空")
    private String userAddress;

    @NotNull(message = "租客芝麻信用分数不能为空")
    private Integer userCreditScore;

    /**用户信用评级*/
    private Integer userCreditLine;

    @NotBlank(message = "租赁机器品牌不能为空")
    private String productBrand;

    @NotBlank(message = "租赁机器名称不能为空")
    private String productName;

    @NotBlank(message = "租赁机器颜色不能为空")
    private String productColor;

    @NotNull(message = "租赁机器内存不能为空")
    private Integer productStorage;

    @NotNull(message = "租赁机器运行内存不能为空")
    private Integer productRandomMemory;

    @NotNull(message = "月租金不能为空")
    private BigDecimal monthlyPayment;

    @NotNull(message = "租赁期限不能为空")
    private Integer numberOfPayments;

    @NotNull(message = "首付金额不能为空")
    private BigDecimal downPayment;

    @NotNull(message = "意外保险金额不能为空")
    private BigDecimal accidentBenefit;

}
