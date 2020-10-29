package com.xsqwe.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class TypeConverter {
	public TypeConverter() {
	}

	@SuppressWarnings("rawtypes")
	public static Object convert(Class clazz, Object o) {
		Object ret = o;
		if (clazz.equals(String.class)) {
			ret = StringUtils.toString(o);
		} else if (!clazz.equals(Integer.TYPE) && !clazz.equals(Integer.class)) {
			if (clazz.equals(BigDecimal.class)) {
				ret = NumberUtils.toBigDecimal(o);
			} else if (!clazz.equals(Float.class) && !clazz.equals(Float.TYPE)) {
				if (!clazz.equals(Double.class) && !clazz.equals(Double.TYPE)) {
					if (!clazz.equals(Long.class) && !clazz.equals(Long.TYPE)) {
						if (clazz.equals(Date.class)) {
							ret = DateUtils.toDate(StringUtils.toString(o));
						} else if (clazz.equals(java.sql.Date.class)) {
							ret = java.sql.Date.valueOf(StringUtils.toString(o));
						} else if (clazz.equals(Timestamp.class)) {
							ret = DateUtils.toTimestamp(StringUtils.toString(o));
						}
					} else {
						ret = NumberUtils.toLong(o);
					}
				} else {
					ret = NumberUtils.toFloat(o);
				}
			} else {
				ret = NumberUtils.toFloat(o);
			}
		} else {
			ret = NumberUtils.toInteger(o);
		}

		return ret;
	}
}
