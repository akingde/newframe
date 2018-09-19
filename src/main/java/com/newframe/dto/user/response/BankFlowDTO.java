package com.newframe.dto.user.response;

import com.google.common.collect.Lists;
import com.newframe.entity.user.CapitalFlow;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author WangBin
 */
@Data
public class BankFlowDTO {

    private Long total;
    private List<Result> result;

    public BankFlowDTO(Page<CapitalFlow> page) {
        this.total = page.getTotalElements();
        List<Result> list = Lists.newArrayList();
        List<CapitalFlow> content = page.getContent();
        for (CapitalFlow capitalFlow : content) {
            list.add(new Result(capitalFlow));
        }
        this.result = list;
    }

    @Data
    public static class Result{
        private Long orderId;
        private Long uid;
        private BigDecimal amount;
        private Integer type;
        private Integer orderStatus;
        private Integer ctime;

        public Result(CapitalFlow capitalFlow) {
            this.orderId = capitalFlow.getOrderId();
            this.uid = capitalFlow.getUid();
            this.amount = capitalFlow.getAmount();
            this.type = capitalFlow.getType();
            this.orderStatus = capitalFlow.getOrderStatus();
            this.ctime = capitalFlow.getCtime();
        }
    }
}
