package com.newframe.enums.sms;

/**
 * 
 * 【阿里云短信模块】
 * @author yangjh
 */
public enum AliyunSMSTemplateEnum {
	/**
	 * 游戏上架通知用户
	 */
	USER_GAME_APPOINTMENT("SMS_83850008","游戏上架通知预约用户"),

	GENERAL_VCODE("SMS_121852153","通用验证码"),

	TO_BE_LEADER("SMS_99195081", "设为主管"),

	APPLY_PASS_FOR_MEMBER("SMS_100785088", "主管关联员工成功发送给员工"),

	/** 您的手机号码已绑定新喵号${newAccount}，旧喵号${oldAccount}登录App请使用密码方式 **/
	MOBILE_UNBIND_NOTIFY("SMS_122290217", "手机绑定到新喵号"),
	APP_UNBIND_THIRDLOGIN("SMS_122290675","第三方解绑的短信通知"),
	USER_REGISTER("SMS_119525052","用户注册验证码"),
	USER_LOGIN("SMS_119525054","登录确认验证码"),
	USER_CHANGE_INFO("SMS_119525050","信息变更验证码"),
	USER_PROPERTY_CHANGE("SMS_136393159","资产变动验证码")
	;
	private String templateCode;
	private String desc;

	AliyunSMSTemplateEnum(String templateCode, String desc){
		this.templateCode = templateCode;
		this.desc = desc;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static AliyunSMSTemplateEnum getEnum(String templateCode) {
		for (AliyunSMSTemplateEnum aliyunSMSTemplateEnum: AliyunSMSTemplateEnum.values()) {
			if (aliyunSMSTemplateEnum.getTemplateCode().equals(templateCode)) {
				return aliyunSMSTemplateEnum;
			}
		}
		return null;
	}
}
