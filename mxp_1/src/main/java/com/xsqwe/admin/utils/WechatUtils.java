/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.admin.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;

import static me.chanjar.weixin.mp.api.WxMpService.CONNECT_OAUTH2_AUTHORIZE_URL;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年5月21日 下午2:38:36
 * @version 1.0.0
 */

@Slf4j
public class WechatUtils {

	public static final String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (Exception e) {
			log.error("URL编码异常", e);
		}
		return url;
	}

	public static final String getAuthUrlBase(String appId, String authRedirectUrl, String state) {
		return String.format(CONNECT_OAUTH2_AUTHORIZE_URL, appId, urlEncode(authRedirectUrl), "snsapi_base", state);
	}

	public static final String getAuthUrlUserInfo(String appId, String authRedirectUrl, String state) {
		return String.format(CONNECT_OAUTH2_AUTHORIZE_URL, appId, urlEncode(authRedirectUrl), "snsapi_userinfo", state);
	}

	public static final String getAuthUrl(String appId, String authRedirectUrl, String scope, String state) {
		return String.format(CONNECT_OAUTH2_AUTHORIZE_URL, appId, urlEncode(authRedirectUrl), scope, state);
	}
}
