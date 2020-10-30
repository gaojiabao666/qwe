/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */

package com.xsqwe.admin.request;

import lombok.Data;

import java.util.List;

/**
 * <p> 
 * </p>
 *
 * @author  herendong
 * @date  2018年7月9日 上午11:25:43
 * @version  1.0.0
 */
@Data
public class EpCoreFileCopyRequest {
	/**
	 * 申请用户编号
	 */
	private String userId;
	/**
	 * 申请订单编号
	 */
	private String orderNo;
	/**
	 * 文件源路径
	 */
	private String source;
	/**
	 * 文件目标路径
	 */
	private String target;
	/**
	 * 文件原始文件名
	 */
	private String originalFileName;
	/**
	 * 字段名
	 */
	private String fieldName;
	/**
	 * 签名字段
	 */
	private List<TextEditerRequest> textEditers;
}
