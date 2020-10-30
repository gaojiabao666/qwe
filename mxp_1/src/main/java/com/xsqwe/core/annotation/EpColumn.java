/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.core.annotation;

import com.xsqwe.core.annotation.utils.DefaultEpColumnDicAdapter;
import com.xsqwe.core.annotation.utils.IEpColumnDicAdapter;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;



/**
 * <p>
 * 对象属性转列
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年6月12日 下午1:33:15
 * @version 1.0.0
 */

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Inherited
public @interface EpColumn {

	// 列描述
	String value() default "";

	// 列属性名称
	String name() default "";

	// 列描述
	String desc() default "";

	// 列对应数据字典
	String dictCode() default "";

	// 列对应数据字典适配器
	Class<? extends IEpColumnDicAdapter> dataDicAdapter() default DefaultEpColumnDicAdapter.class;

	// 是否转换为列 默认转换
	boolean flag() default true;

	// 列值是否安全加密处理
	boolean security() default false;

	// 列属性前端页面是否渲染显示
	boolean display() default false;

	// 列属性前端页面是否必填
	boolean required() default false;

}
