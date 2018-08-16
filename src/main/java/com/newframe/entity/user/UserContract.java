package com.newframe.entity.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 *  用户公私钥
 *
 * This class corresponds to the database table user_contract
 *
 * @mbggenerated do_not_delete_during_merge
 */
@Data
@Entity
@Table(name = "user_contract")
public class UserContract {
    /**
     * 用户ID
     * uid
     */
    @Id
    @Column(name = "uid")
    private Long uid;

    /**
     * 用户私钥
     * privatekey
     */
    @Column(name = "privatekey")
    private String privatekey;

    /**
     * 用户公钥
     * publickey
     */
    @Column(name = "publickey")
    private String publickey;

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