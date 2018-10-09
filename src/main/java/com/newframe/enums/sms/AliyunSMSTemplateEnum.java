package com.newframe.enums.sms;

/**
 * 
 * 【阿里云短信模块】
 * @author wangdong
 */
public enum AliyunSMSTemplateEnum {

	REGISTER_OR_LOGIN("SMS_147196612","用户注册或者登陆")
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
