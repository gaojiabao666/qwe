/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.web;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.xsqwe.web.enums.CommonCodeEnum;

import lombok.Data;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年10月31日 下午4:44:52
 * @version 1.0.0
 */

@Data
public class FastResponse<E> implements Serializable {

	private static final long serialVersionUID = 7010064721473780599L;

	private String code;
	private String message;
	private String subMessage;

	private E result;

	public FastResponse() {
		super();
	}

	public FastResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public FastResponse(String code, String message, String subMessage) {
		super();
		this.code = code;
		this.message = message;
		this.subMessage = subMessage;
	}

	public FastResponse(String code, String message, E result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public FastResponse(String code, String message, String subMessage, E result) {
		super();
		this.code = code;
		this.message = message;
		this.subMessage = subMessage;
		this.result = result;
	}

	public boolean isSuccess() {
		if (CommonCodeEnum.SUCCESS.getCode().equals(code)) {
			return true;
		}
		return false;
	}

	public boolean fail() {
		if (!isSuccess()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "FastResponse [code=" + code + ", message=" + message + ", subMessage=" + subMessage + ", result="
				+ JSON.toJSONString(result) + "]";
	}

}
