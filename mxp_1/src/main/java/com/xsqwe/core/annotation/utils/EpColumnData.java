/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.core.annotation.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年6月12日 下午3:35:08
 * @version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EpColumnData {

	/**
	 * 目录名称 对应对象名
	 */
	private String dirName;

	/**
	 * 列英文名 对应对象属性名成
	 */
	private String name;

	/**
	 * 列描述 对应对象属性名成
	 */
	private String desc;

	/**
	 * 对象属性下拉多选对应的数据字典
	 */
	private String dictCode;

	/**
	 * 对应对象属性用户输入的列值
	 */
	private Object value;

	/**
	 * 是否转换为列属性
	 */
	private boolean flag;

	/**
	 * 字段值信息是否安全加密
	 */
	private boolean security;

	/**
	 * 列属性前端页面是否渲染显示
	 */
	private boolean display;

	/**
	 * 列属性前端页面是否必填
	 */
	private boolean required;

}
