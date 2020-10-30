/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.web;



import com.xsqwe.web.enums.FastEnum;
import com.xsqwe.web.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月13日 下午4:17:30
 * @version 1.0.0
 */
@Slf4j
public final class ExceptionUtils {

	public static CommonException create(FastResponse<?> fastResponse) {
		log.info("FastResponse: {}", fastResponse);
		return new CommonException(fastResponse.getCode(), fastResponse.getMessage(), fastResponse.getSubMessage());
	}

	public static CommonException create(FastEnum fastEnum) {
		log.info("FastEnum: {}", fastEnum.toString());
		return new CommonException(fastEnum.getCode(), fastEnum.getMessage());
	}

	public static CommonException create(FastEnum fastEnum, Throwable throwable) {
		log.info("FastEnum: {}", fastEnum.toString());
		return new CommonException(fastEnum.getCode(), fastEnum.getMessage(), throwable);
	}

	public static CommonException create(FastEnum fastEnum, String subMessage) {
		log.info("FastEnum: {}, SubMessage: {}", fastEnum.toString(), subMessage);
		return new CommonException(fastEnum.getCode(), fastEnum.getMessage(), subMessage);
	}

	public static CommonException create(FastEnum fastEnum, String subMessage, Throwable throwable) {
		log.info("FastEnum: {}, SubMessage: {}", fastEnum.toString(), subMessage);
		return new CommonException(fastEnum.getCode(), fastEnum.getMessage(), subMessage, throwable);
	}
}
