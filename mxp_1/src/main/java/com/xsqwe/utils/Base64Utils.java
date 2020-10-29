/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月26日 下午9:19:29
 * @version 1.0.0
 */
public final class Base64Utils {

	private static final String URL_SALT = "URL_FAST_MX_0904";

	public static final byte[] encode2Byte(String data) {
		return Base64.encodeBase64(data.getBytes());
	}

	public static final String encode(String data) {
		return new String(encode2Byte(data));
	}

	public static final String encodeByte(byte[] binaryData) {
		return Base64.encodeBase64String(binaryData);
	}

	public static final byte[] decode2Byte(String data) {
		return Base64.decodeBase64(data.getBytes());
	}

	public static final String decode(String data) {
		return new String(decode2Byte(data));
	}

	public static String encodeBase64URLSafeString(String url, int validSecond) {
		Assert.notNull(url, "File URL");
		validSecond = validSecond > 0 ? validSecond : 120;
		String expireTime = String.valueOf((System.currentTimeMillis() / 1000) + validSecond);
		byte[] md5Byte = DigestUtils.md5(url.concat(URL_SALT).concat(expireTime));
		String encodeValue = Base64.encodeBase64URLSafeString(md5Byte);
		return url.concat("?v=").concat(encodeValue).concat("&t=").concat(expireTime);
	}

}
