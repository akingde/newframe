package com.newframe.dto.common;

import com.newframe.dto.order.request.LoanDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UidLoanDTOs {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private Long uid;
    /**
     * 订单id
     */
    @NotNull(message = "订单loanDTOs不能为空")
    private List<LoanDTO> loanDTOs;
}
