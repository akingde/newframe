package com.newframe.entity.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author wangdong
 * @since 2018-08-29
 * 用户基础表
 */
@Data
@Entity
public class User {
    /**
     * 用户的uid
     */
    @Id
    private Long uid;
    /**
     * 用户的头像
     */
    private String avatar;

    /**
     * 用户的昵称
     */
    private String userName;
    /**
     * 用户的性别
     * 个人角色才有,性别(1-男,2-女),其余性别为null
     */
    private Integer gender;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 电子邮件的地址
     */
    private String emailAddress;
    /**
     * 角色(0,员工,1:餐厅,2公司,3供应商)
     */
    private Integer role;

    /**
     * 审核状态(0-待添加材料,1-待初审,2-初审失败,3-待复审,5-复审成功,6-复审失败)
     */
    private Integer applyStatus;

    /**
     * 用户状态(0-正常,1-冻结)
     */
    private Integer userStatus;

    /**
     * ctime
     */
    private Integer ctime;
    /**
     * utime
     */
    private Integer utime;

}
