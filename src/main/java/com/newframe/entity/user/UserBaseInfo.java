package com.newframe.entity.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 *  用户的基本信息
 *
 * This class corresponds to the database table user_base_info
 *
 * @mbggenerated do_not_delete_during_merge
 */
@Data
@Entity
@Table(name = "user_base_info")
public class UserBaseInfo {
    /**
     * 用户ID
     * uid
     */
    @Id
    @Column(name = "uid")
    private Long uid;

    /**
     * 头像路径
     * avatar
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 用户昵称
     * user_name
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 性别(1-男,2-女)
     * gender
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 电话号码
     * phone_number
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 用户状态(0-正常,1-冻结)
     * user_status
     */
    @Column(name = "user_status")
    private Integer userStatus;

    /**
     * 创建时间
     * ctime
     */
    @Column(name = "ctime")
    private Integer ctime;

    /**
     * 更新时间
     * utime
     */
    @Column(name = "utime")
    private Integer utime;
}