/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.utils;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年1月25日 上午10:51:17
 * @version 1.0.0
 */

@Slf4j
public final class SignatureUtils {

	public static String gen(String... arr) {
		if (StringUtils.isAnyEmpty(arr)) {
			throw new IllegalArgumentException("非法请求参数，有部分参数为空 : " + Arrays.toString(arr));
		}

		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for (String a : arr) {
			sb.append(a);
		}
		return DigestUtils.sha1Hex(sb.toString());
	}

	public static String genWithAmple(String... arr) {
		if (StringUtils.isAnyEmpty(arr)) {
			throw new IllegalArgumentException("非法请求参数，有部分参数为空 : " + Arrays.toString(arr));
		}

		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			String a = arr[i];
			sb.append(a);
			if (i != arr.length - 1) {
				sb.append('&');
			}
		}
		return DigestUtils.sha1Hex(sb.toString());
	}

	public static boolean checkSignature(String signature, String... arr) {
		try {
			return signature.equals(SignatureUtils.genWithAmple(arr));
		} catch (Exception e) {
			log.error("signature fail", e);
		}
		return false;
	}

}
