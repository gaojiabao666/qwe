/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.core.annotation.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.xsqwe.core.annotation.EpColumn;
import com.xsqwe.core.annotation.EpColumnDir;
import com.xsqwe.utils.FastCodeUtils;
import com.xsqwe.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <code>EpColumn</code>注解属性转换为<code>EpColumnData</code>集合对象
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年6月12日 下午3:35:22
 * @version 1.0.0
 */
public final class EpColumnUtils {

	private static final Logger LOG = LoggerFactory.getLogger(EpColumnUtils.class);

	public static final List<EpColumnData> getEpColumnDataList(Object obj) {
		String dirName = null;
		Class<? extends Object> clazz = obj.getClass();
		boolean clazzHasAnnotation = clazz.isAnnotationPresent(EpColumnDir.class);
		if (clazzHasAnnotation) {
			EpColumnDir epColumnDir = clazz.getAnnotation(EpColumnDir.class);
			dirName = epColumnDir.value();
			if (Tools.isBlank(dirName)) {
				dirName = epColumnDir.name();
			}
		}

		Field[] fields = clazz.getDeclaredFields();
		if (Tools.isBlank(fields)) {
			return Collections.emptyList();
		}
		List<EpColumnData> epColumnDataList = new LinkedList<EpColumnData>();
		for (Field field : fields) {
			boolean hasAnnotation = field.isAnnotationPresent(EpColumn.class);
			if (!hasAnnotation) {
				continue;
			}
			EpColumn epColumn = field.getAnnotation(EpColumn.class);
			EpColumnData epColumnData = getEpColumnData(obj, field, epColumn, dirName);
			if (epColumnData == null) {
				continue;
			}
			epColumnDataList.add(epColumnData);

			EpColumnData dicEpColumnData = getDicEpColumnData(epColumn, epColumnData);
			if (dicEpColumnData == null) {
				continue;
			}
			epColumnDataList.add(dicEpColumnData);
		}
		return epColumnDataList;
	}

	private static EpColumnData getDicEpColumnData(EpColumn epColumn, EpColumnData epColumnData) {
		String adapterName = null;
		try {
			Class<? extends IEpColumnDicAdapter> adapter = epColumn.dataDicAdapter();
			if (adapter == null) {
				return null;
			}
			IEpColumnDicAdapter adapterInstance = adapter.newInstance();
			adapterName = adapterInstance.getClass().getName();
			EpColumnData dicEpColumnData = adapterInstance.getDescColumnData(epColumnData);
			if (dicEpColumnData == null) {
				return null;
			}
			LOG.info("描述适配转换成功,描述信息:{},适配类:{}", dicEpColumnData, adapterName);
			return dicEpColumnData;
		} catch (Exception e) {
			LOG.error("描述适配器转换异常,转换源信息:{},适配类:{}", epColumnData, adapterName, e);
			return null;
		}
	}

	private static EpColumnData getEpColumnData(Object obj, Field field, EpColumn epColumn, String dirName) {
		String name = epColumn.name();
		String desc = epColumn.desc();
		if (Tools.isBlank(desc)) {
			desc = epColumn.value();
		}
		if (Tools.isBlank(name)) {
			name = field.getName();
		}
		boolean flag = epColumn.flag();

		// 获取字段值
		Object value = null;
		try {
			field.setAccessible(true);
			value = field.get(obj);
		} catch (Exception e) {
			LOG.error("反射获取字段值异常,name:{},desc:{},flag:{}", name, desc, flag, e);
		}
		if (value == null) {
			return null;
		}

		// 安全属性字段值加密处理
		boolean security = epColumn.security();
		if (security) {
			value = FastCodeUtils.encrypt(String.valueOf(value));
		}

		boolean display = epColumn.display();
		boolean required = epColumn.required();

		String dicCode = epColumn.dictCode();
		// 字段转换成EpColumnData对象
		EpColumnData epColumnData = new EpColumnData(dirName, name, desc, dicCode, value, flag, security, display, required);
		return epColumnData;
	}

}
