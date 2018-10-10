package com.newframe.entity.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 【用户密码表】实体类
*
 * @version
 * @author wangdong 2018年04月23日 PM16:26:42
 */
@Data
@Entity
@Table(name = "user_pwd")
public class UserPwd {

	/**
	 * 用户ID
	 */
	@Id
	@Column(name = "uid")
	private Long uid;

	/**
	 * 登陆密码
	 */
	@Column(name = "login_pwd")
	private String loginPwd;

	/**
	 * 支付密码
	 */
	@Column(name = "pay_pwd")
	private String payPwd;

	/**
	 * 创建时间
	 */
	@Column(name = "ctime")
	private Integer ctime;

	/**
	 * 更新时间
	 */
	@Column(name = "utime")
	private Integer utime;

}

