package com.newframe.entity.order;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kfm
 * 资金方放款凭证表
 */
@Data
@Table(name = "order_funder_evidence")
@Entity
public class OrderFunderEvidence {
    @Id
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "renter_id")
    private Long renterId;
    @Column(name = "funder_id")
    private Long funderId;
    @Column(name = "evidence_url")
    private String evidenceUrl;
    @Column(name = "ctime")
    private Integer ctime;
    @Column(name = "utime")
    private Integer utime;
}
