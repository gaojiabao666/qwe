/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.utils;


import com.xsqwe.web.FastResponse;
import com.xsqwe.web.enums.CommonCodeEnum;
import com.xsqwe.web.enums.FastEnum;

/**
 * <p>
 * {@link FastResponse}工具类
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月12日 下午3:03:36
 * @version 1.0.0
 */
public final class ResponseUtils {

	public static FastResponse<Void> success() {
		return create(CommonCodeEnum.SUCCESS);
	}

	public static FastResponse<Void> success(String subMessage) {
		return create(CommonCodeEnum.SUCCESS, subMessage);
	}

	public static <E> FastResponse<E> success(E result) {
		return create(CommonCodeEnum.SUCCESS, result);
	}

	public static <E> FastResponse<E> success(String subMessage, E result) {
		return create(CommonCodeEnum.SUCCESS, subMessage, result);
	}

	public static FastResponse<Void> exception() {
		return create(CommonCodeEnum.EXCEPTION);
	}

	public static FastResponse<Void> exception(String subMessage) {
		return create(CommonCodeEnum.EXCEPTION, subMessage);
	}

	public static <E> FastResponse<E> exception(String subMessage, E result) {
		return create(CommonCodeEnum.EXCEPTION, subMessage, result);
	}

	public static <E> FastResponse<E> exception(String message, String subMessage, E result) {
		return create(CommonCodeEnum.EXCEPTION.getCode(), message, subMessage, result);
	}

	public static FastResponse<Void> create(FastEnum fastEnum) {
		return create(fastEnum.getCode(), fastEnum.getMessage());
	}

	public static FastResponse<Void> create(FastEnum fastEnum, String subMessage) {
		return create(fastEnum.getCode(), fastEnum.getMessage(), subMessage);
	}

	public static FastResponse<Void> create(String code, String message) {
		return new FastResponse<Void>(code, message);
	}

	public static FastResponse<Void> create(String code, String message, String subMessage) {
		return new FastResponse<Void>(code, message, subMessage);
	}

	public static <E> FastResponse<E> create(String code, String message, String subMessage, E result) {
		return new FastResponse<E>(code, message, subMessage, result);
	}

	public static <E> FastResponse<E> create(FastEnum fastEnum, E result) {
		return new FastResponse<E>(fastEnum.getCode(), fastEnum.getMessage(), result);
	}

	public static <E> FastResponse<E> create(FastEnum fastEnum, String subMessage, E result) {
		return new FastResponse<E>(fastEnum.getCode(), fastEnum.getMessage(), subMessage, result);
	}
}
