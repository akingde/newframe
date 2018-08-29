package com.newframe.entity.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 出租方发货信息
 * @author kfm
 * @date 2018.08.29 17:45
 */
@Data
@Entity
public class HirerDeliver {
    @Id
    private Long orderId;
    private Long lessorId;
    private String expressName;
    private String expressNumber;
    private Integer expressTime;
    private String serialNumber;
    private Integer ctime;
    private Integer utime;
}
