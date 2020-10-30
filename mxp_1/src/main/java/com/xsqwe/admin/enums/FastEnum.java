/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.admin.enums;

/**
 * <p>
 * {@link FastDomainEnum} <br/>
 * {@link CommonCodeEnum} <br/>
 * 成功响应码: 【2+2位系统编号+3位系统内部异常编码】<br/>
 * 客户端错误响应码: 【4+2位系统编号+3位系统内部异常编码】<br/>
 * 服务端异常响应码: 【5+2位系统编号+3位系统内部异常编码】<br/>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月1日 上午10:15:51
 * @version 1.0.0
 */
public interface FastEnum {

	/**
	 * 唯一代码
	 * 
	 * @return
	 */
	public String getCode();

	/**
	 * 描述
	 * 
	 * @return
	 */
	public String getMessage();

}
