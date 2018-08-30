package com.newframe.entity.order;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 平台支持的快递公司列表
 * @author kfm
 * @date 2018.08.28 14:53
 */
@Data
@Entity
@Table(name = "express_company")
public class ExpressCompany {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "company_code")
    private String companyCode;
}
