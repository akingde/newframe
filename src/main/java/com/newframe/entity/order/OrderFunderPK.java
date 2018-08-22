package com.newframe.entity.order;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class OrderFunderPK implements Serializable {
    /***/
    @Column(name = "order_id")
    private Long orderId;

    /***/
    @Column(name = "funder_id")
    private Long funderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderFunderPK that = (OrderFunderPK) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(funderId, that.funderId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, funderId);
    }
}
