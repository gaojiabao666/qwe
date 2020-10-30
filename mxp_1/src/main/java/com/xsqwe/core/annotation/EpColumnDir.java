/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.core.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * 对象转目录
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年6月12日 下午6:25:28
 * @version 1.0.0
 */

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface EpColumnDir {

	// 目录英文名称
	String value() default "";

	// 目录英文名称
	String name() default "";

}
