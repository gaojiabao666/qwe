package com.xsqwe.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RespResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2341465620232928023L;
	private int status;
	private long costTime;
	private String respMsg;
	private JSONObject jsonData;
	private String url;
	/**
	 * 1-ConnectTimeoutException，2-SocketTimeoutException
	 */
	private String errFlag;

}
