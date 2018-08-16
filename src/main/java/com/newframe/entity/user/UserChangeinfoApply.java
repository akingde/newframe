package com.newframe.entity.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 *  用户信息修改申请
 *
 * This class corresponds to the database table user_changeinfo_apply
 *
 * @mbggenerated do_not_delete_during_merge
 */
@Data
@Entity
@Table(name = "user_changeinfo_apply")
public class UserChangeinfoApply {
    /**
     * 用户id
     * uid
     */
    @Id
    @Column(name = "uid")
    private Long uid;

    /**
     * 修改的内容：1、手机，2、邮箱
     * change_type
     */
    @Column(name = "change_type")
    private Integer changeType;

    /**
     * 旧的信息
     * oldinfo
     */
    @Column(name = "oldinfo")
    private String oldinfo;

    /**
     * 新的信息
     * newinfo
     */
    @Column(name = "newinfo")
    private String newinfo;

    /**
     * 常用的联系电话
     * phone_number
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 修改的内容：1、待审核，2、通过，3、拒绝
     * apply_statue
     */
    @Column(name = "apply_statue")
    private Integer applyStatue;

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