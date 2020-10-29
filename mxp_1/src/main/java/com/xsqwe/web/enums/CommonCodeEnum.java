/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.web.enums;

/**
 * <p>
 * {@link FastDomainEnum} <br/>
 * 成功响应码: 【2+2位系统编号+3位系统内部异常编码】<br/>
 * 客户端错误响应码: 【4+2位系统编号+3位系统内部异常编码】<br/>
 * 服务端异常响应码: 【5+2位系统编号+3位系统内部异常编码】<br/>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月1日 上午10:52:57
 * @version 1.0.0
 */
public enum CommonCodeEnum implements FastEnum {

	/**
	 * 成功响应码 [200000～300000)
	 */
	SUCCESS("200000", "消息处理成功"),

	/**
	 * 客户端错误响应码 [400000～500000)
	 */
	VALIDATE_ERROR("400000", "数据校验不合法"),

	ACCESS_PERM_DENIED("400001", "对不起,您没有访问权限"),

	CAPTCHA_VALIDATE_FAIL("400002", "图片验证码验证失败"),

	CAPTCHA_EXPIRE("400003", "图片验证码已失效"),

	PHARMACY_NOT_BIND("400004", "药店信息未绑定"),

	PHARMACY_NOT_EXIST("400005", "药店信息不存在"),

	PHONE_OR_PASSWORD_EMPTY("400006", "手机号或密码不能为空"),
	PHONE_OR_PASSWORD_ERROR("400007", "手机号或密码不正确"),

	PHONE_ERROR_FORMAT("400008", "手机号格式不正确"),

    VALIDATE_API_ERROR("400010", "数据校验不合法"),

	/**
	 * 服务端异常响应码 [500000～600000)
	 */
	EXCEPTION("500000", "系统繁忙,请稍后重试"),

	COMMON_EXCEPTION("500001", "消息处理异常"),

	DB_INSERT_EXCEPTION("500002", "数据插入异常"),

	DB_UPDATE_EXCEPTION("500003", "数据更新异常"),

	DB_SELECT_EXCEPTION("500004", "数据查询异常"),

	DB_KEY_DUPLICATE("500011", "主键或唯一性约束冲突");

	private String code;
	private String message;

	private CommonCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static CommonCodeEnum getEnumByCode(String code) {
		for (CommonCodeEnum commonCodeEnum : CommonCodeEnum.values()) {
			if (commonCodeEnum.getCode().equals(code)) {
				return commonCodeEnum;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("CommonCodeEnum name = %s code = %s message = %s", this.name(), code, message);
	}

}
