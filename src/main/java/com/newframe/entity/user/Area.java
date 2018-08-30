package com.newframe.entity.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 *  地址表
 *
 * This class corresponds to the database table area
 *
 * @mbggenerated do_not_delete_during_merge
 */
@Data
@Entity
@Table(name = "area")
public class Area {
    /**
     * 地区Id
     * area_id
     */
    @Id
    @Column(name = "area_id")
    private Integer areaId;

    /**
     * 地区编码
     * area_code
     */
    @Column(name = "area_code")
    private Integer areaCode;

    /**
     * 地区名
     * area_name
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 地区级别（1:省份province,2:市city,3:区县district,4:街道street）
     * area_level
     */
    @Column(name = "area_level")
    private Integer areaLevel;

    /**
     * 城市编码
     * city_code
     */
    @Column(name = "city_code")
    private String cityCode;

    /**
     * 城市中心点（即：经纬度坐标）
     * center
     */
    @Column(name = "center")
    private String center;

    /**
     * 地区父节点
     * parent_id
     */
    @Column(name = "parent_id")
    private Integer parentId;
}