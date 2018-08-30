package com.newframe.entity.order;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kfm
 * @date 2018.08.24 11:34
 */
@Table(name = "order_supplier")
@Data
@Entity
public class OrderSupplier {


    public static final Integer NO_DELETE_STATUS = 0;

    @Id
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "partner_order_id")
    private String partnerOrderId;
    @Column(name = "partner_id")
    private Integer partnerId;
    @Column(name = "supplier_id")
    private Long supplierId;
    @Column(name = "merchant_id")
    private Long merchantId;
    @Column(name = "merchant_name")
    private String merchantName;
    @Column(name = "uid")
    private Long uid;
    @Column(name = "receiver_name")
    private String receiverName;
    @Column(name = "receiver_mobile")
    private String receiverMobile;
    @Column(name = "receiver_address")
    private String receiverAddress;
    @Column(name = "product_brand")
    private String produceBrand;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_color")
    private String productColor;
    @Column(name = "product_random_memory")
    private Integer productRandomMemory;
    @Column(name = "product_storage")
    private Integer productStorage;
    @Column(name = "express_company")
    private String expressCompany;
    @Column(name = "express_number")
    private String expressNumber;
    @Column(name = "express_time")
    private Long expressTime;
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "order_status")
    private Integer orderStatus;
    @Column(name = "delete_status")
    private Integer deleteStatus;
    @Column(name = "ctime")
    private Integer ctime;
    @Column(name = "utime")
    private Integer utime;

}
