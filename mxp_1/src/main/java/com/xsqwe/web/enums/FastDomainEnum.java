/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.web.enums;

/**
 * <p>
 * {@link CommonCodeEnum} <br/>
 * 2位系统编号: <br/>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月17日 下午4:34:00
 * @version 1.0.0
 */
public enum FastDomainEnum {

	common("00", "fast-common"),

	// ==========微信===========
	wechat_service("AA", "fast-wechat-service"),

	// ==========网关===========
	//弃用
	wechat_gateway("WG", "fast-wechat-gateway"),

	app_gateway("AG", "fast-app-gateway"),
	
	wxmp_gateway("BG", "fast-wxmp-gateway"),
	
    wxma_gateway("DG", "fast-wxma-gateway"),
    
    web_gateway("EG", "fast-web-gateway"),

	universal_gateway("UG", "fast-universal-gateway"),

	// ==========OSS===========
	oss("OA", "fast-oss"),

	oss_service("OS", "fast-oss-service"),

	// ==========基础配置===========
	config_service("B2", "fast-config-service"),

	// ==========数据中心===========
	data_provider("D1", "fast-data-provider"),

	data_service("D2", "fast-data-service"),

	data_admin("D3", "fast-data-admin"),

	// ==========产品===========
	product_provider("E1", "fast-product-provider"),

	product_service("E2", "fast-product-service"),

	product_admin("E3", "fast-product-admin"),

	// ==========风控===========
	risk_provider("G1", "fast-risk-provider"),

	risk_service("G2", "fast-risk-service"),

	risk_admin("G3", "fast-risk-admin"),

	// ==========合作伙伴===========
	partner_provider("H1", "fast-partner-provider"),

	partner_service("H2", "fast-partner-service"),

	partner_admin("H3", "fast-partner-admin"),

	// ==========主数据===========
	maindata_provider("H4", "fast-maindata-provider"),

	maindata_service("H5", "fast-maindata-service"),

	maindata_admin("H6", "fast-maindata-admin"),

	// ==========账户===========
	account_provider("J1", "fast-account-provider"),

	account_service("J2", "fast-account-service"),

	account_admin("J3", "fast-account-admin"),

	// ==========交易===========
	trans_provider("K1", "fast-trans-provider"),

	trans_provider_nouser_order("82", "fast-trans-provider"),

	trans_provider_member_order("83", "fast-trans-provider"),

	trans_provider_home_order("80", "fast-trans-provider"),

	trans_provider_pick_order("81", "fast-trans-provider"),

	trans_service_home_order("84", "fast-trans-service"),

	trans_service_pick_order("85", "fast-trans-service"),

	trans_service_nouser_order("86", "fast-trans-service"),

	trans_service_member_order("87", "fast-trans-service"),

	trans_service("K2", "fast-trans-service"),

	trans_admin("K3", "fast-trans-admin"),

	fund_shbank_service("K4", "fast-fund-shbank-service"),

	// ==========会员===========
	member_provider("M1", "fast-member-provider"),

	member_service("M2", "fast-member-service"),

	member_admin("M3", "fast-member-admin"),

	member_provider_ascii("771", "fast-member-provider"),

	member_service_ascii("772", "fast-member-service"),

	member_admin_ascii("773", "fast-member-admin"),

	// ==========第三方===========
	openapi_provider("N1", "fast-openapi-provider"),

	thirdparty_service("N2", "fast-thirdparty-service"),

	thirdparty_admin("N3", "fast-thirdparty-admin"),

	// ==========批量服务===========
	batch_service("P1", "fast-batch-service"),

	// ==========BI===========
	bi_service("Q1", "fast-bi-service"),

	bi_admin("Q2", "fast-bi-admin"),
	
	bi_post_service("Q3", "fast-bi-post-service"),

	// ==========保险===========
	insurance_provider("U1", "fast-insurance-provider"),

	insurance_service("U2", "fast-insurance-service"),

	insurance_admin("U3", "fast-insurance-admin"),

	// ==========Ep药企开发平台服务===========

	ep_core("X1", "fast-ep-core"),

	ep_core_admin("X2", "fast-ep-core-admin"),

	// ==========market服务===========
	market_service("MA", "fast-market-service"),

	market_provider_ascii("781", "fast-market-provider"),

	market_service_ascii("782", "fast-market-service"),

	market_admin_ascii("783", "fast-market-admin"),

	;

	private String code;
	private String message;

	private FastDomainEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static FastDomainEnum getEnumByCode(String code) {
		for (FastDomainEnum fastDomainEnum : FastDomainEnum.values()) {
			if (fastDomainEnum.getCode().equals(code)) {
				return fastDomainEnum;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("FastDomainEnum name = %s code = %s message = %s", this.name(), code, message);
	}
}
