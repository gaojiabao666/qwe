/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.admin.enums;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月22日 上午9:40:52
 * @version 1.0.0
 */
public enum WechatMenuEnums {

	mobileBinding("会员注册", "/@/mobileBinding", true, true),

	Menu01("药店查询", "/@/selectPharmacy", true, false),

	// 会员专享
	Menu12("项目介绍", "/@/introduce", true, false),

	Menu13("注册预约", "/@/reg", true, false),
	//暂时放开路由
	Menu14("我的爱心券", "/@/coupon", true, false),

	/*Menu23("项目介绍", "/@/financialStaging", true, false),*/

	Menu31("我的订单", "/@/myOrder", true, true),

	Menu34("400热线", "/@/callTelphone", true, false),


	//暂时放开路由
//	Menu37("我的账户", "/@/myTicket", true, false),
//
//	Menu38("注射教学", "/@/video", true, false),

	//Menu39("早鸟流程", "/@/videoTwo", true, false),

	/*Menu38("金融分期", "/@/comingSoon", true, true),*/


	/*Menu39("我的检测", "/@/dalabs", true, true),

	Menu41("项目介绍", "/@/platformWelfare", true, false),

	Menu42("操作指引", "/@/platformIntroduction", true, true),*/

	OrderWXPayThird("订单支付", "/@/orderWXPayThird", true, true),

	SafeWXPayThird("订单支付", "/@/safeWXPay", true, false);


	private String title;
	private String url;
	private boolean domainPrefix;
	private boolean registed;

	private WechatMenuEnums(String title, String url, boolean domainPrefix, boolean registed) {
		this.title = title;
		this.url = url;
		this.domainPrefix = domainPrefix;
		this.registed = registed;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public boolean isDomainPrefix() {
		return domainPrefix;
	}

	public boolean isRegisted() {
		return registed;
	}

	public static WechatMenuEnums getMenuEnumByUrl(String url) {
		if (url == null) {
			return null;
		}
		url = url.replaceAll("#", "@");
		for (WechatMenuEnums menuEnum : WechatMenuEnums.values()) {
			if (url.contains(menuEnum.getUrl())) {
				return menuEnum;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("MenuEnum name=%s , title=%s , url=%s , domainPrefix=%s , registed=%s", this.name(), title, url, domainPrefix, registed);
	}

}
