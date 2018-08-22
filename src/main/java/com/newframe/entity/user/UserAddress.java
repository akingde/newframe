package com.newframe.entity.user;

import com.newframe.dto.user.request.AddressDTO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 *  用户的地址管理表
 *
 * This class corresponds to the database table user_address
 *
 * @mbggenerated do_not_delete_during_merge
 */
@Data
@Entity
@Table(name = "user_address")
public class UserAddress {
    /**
     * 地址id
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 用户id
     * uid
     */
    @Column(name = "uid")
    private Long uid;

    /**
     * 收件人姓名
     * consignee_name
     */
    @Column(name = "consignee_name")
    private String consigneeName;

    /**
     * 收件人手机号
     * mobile
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 省id
     * province_id
     */
    @Column(name = "province_id")
    private Integer provinceId;

    /**
     * 省名
     * province_name
     */
    @Column(name = "province_name")
    private String provinceName;

    /**
     * 市id
     * city_id
     */
    @Column(name = "city_id")
    private Integer cityId;

    /**
     * 市名
     * city_name
     */
    @Column(name = "city_name")
    private String cityName;

    /**
     * 县id
     * county_id
     */
    @Column(name = "county_id")
    private Integer countyId;

    /**
     * 县名
     * county_name
     */
    @Column(name = "county_name")
    private String countyName;

    /**
     * 收件地址
     * consignee_address
     */
    @Column(name = "consignee_address")
    private String consigneeAddress;

    /**
     * 是否默认
     * default_address
     */
    @Column(name = "default_address")
    private Boolean defaultAddress;

    /**
     * 创建时间
     * ctime
     */
    @Column(name = "ctime")
    private Integer ctime;

    /**
     * 修改时间
     * utime
     */
    @Column(name = "utime")
    private Integer utime;

    public UserAddress() {
    }

    public UserAddress(Long uid, AddressDTO address, List<Area> areas) {
        this.id = address.getAddressId();
        this.uid = uid;
        this.consigneeName = address.getConsigneeName();
        this.mobile = address.getMobile();
        this.provinceId = address.getProvinceId();
        this.cityId = address.getCityId();
        this.countyId = address.getCountyId();
        this.consigneeAddress = address.getConsigneeAddress();
        this.defaultAddress = address.isDefaultAddress();
        for (Area area : areas) {
            if (area.getAreaLevel().equals(1)){
                this.provinceName = area.getAreaName();
            }else if (area.getAreaLevel().equals(2)){
                this.cityName = area.getAreaName();
            }else if (area.getAreaLevel().equals(3)){
                this.countyName = area.getAreaName();
            }
        }
    }
}