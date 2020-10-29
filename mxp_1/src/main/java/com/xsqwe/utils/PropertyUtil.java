package com.xsqwe.utils;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class PropertyUtil {
	// private static final boolean THROW_ON_LOAD_FAILURE = true;
	// private static final boolean LOAD_AS_RESOURCE_BUNDLE = false;
	// private static final String SUFFIX = ".properties";
	// private static final String PROPERT_FILE_NAME = "yiyao";
	private static Properties props;

	public PropertyUtil() {
	}

	public static Properties loadProperties(String name, ClassLoader loader) {
		if (name == null) {
			throw new IllegalArgumentException("null input: name");
		} else {
			if (name.startsWith("/")) {
				name = name.substring(1);
			}

			if (name.endsWith(".properties")) {
				name = name.substring(0, name.length() - ".properties".length());
			}

			Properties result = null;
			InputStream in = null;

			try {
				if (loader == null) {
					loader = ClassLoader.getSystemClassLoader();
				}

				name = name.replace('.', '/');
				if (!name.endsWith(".properties")) {
					name = name.concat(".properties");
				}

				in = loader.getResourceAsStream(name);
				if (in != null) {
					result = new Properties();
					result.load(in);
				}
			} catch (Exception var13) {
				result = null;
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Throwable var12) {
						;
					}
				}

			}

			if (result == null) {
				throw new IllegalArgumentException("could not load [" + name + "] as " + "a classloader resource");
			} else {
				return result;
			}
		}
	}

	public static Properties loadProperties(String name) {
		return loadProperties(name, Thread.currentThread().getContextClassLoader());
	}

	public static final void load() {
		props = loadProperties("yiyao");
	}

	public static final void load(String fileName) {
		props = loadProperties(fileName);
	}

	public static final String getStringProperty(String key) {
		return props.getProperty(key);
	}

	public static final int getIntProperty(String key) {
		String value = getStringProperty(key);
		return Integer.parseInt(value);
	}

	public static final boolean getBooleanProperty(String key) {
		String value = getStringProperty(key);
		return Boolean.valueOf(value).booleanValue();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap getProps2Map() {
		HashMap map = new HashMap();
		Iterator it = props.entrySet().iterator();

		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			map.put(key, value);
		}

		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map toMap(Object object)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map dataMap = new HashMap();
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(object);
		PropertyDescriptor[] var4 = propertyDescriptors;
		int var5 = propertyDescriptors.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			PropertyDescriptor propertyDescriptor = var4[var6];
			String name = propertyDescriptor.getName();
			Object value = PropertyUtils.getProperty(object, name);
			if (value instanceof Timestamp) {
				value = DateFormatUtils.format(((Timestamp) value).getTime(), "yyyy-MM-dd hh:mm:ss");
			} else if (value instanceof Date) {
				value = new java.sql.Date(((Date) value).getTime());
				value = String.valueOf(value);
			}

			dataMap.put(name, value);
		}

		return dataMap;
	}

	@SuppressWarnings("rawtypes")
	public static Object fromMap(Object obj, Map map) throws IllegalAccessException {
		Class<?> clazz = obj.getClass();
		copyProperty(obj, map, clazz);

		while (clazz != Object.class) {
			copyProperty(obj, map, clazz);
			clazz = clazz.getSuperclass();
		}

		return obj;
	}

	@SuppressWarnings("rawtypes")
	public static void copyProperty(Object obj, Map map, Class<?> clazz) throws IllegalAccessException {
		Field[] fields = clazz.getDeclaredFields();
		Field[] var4 = fields;
		int var5 = fields.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			Field field = var4[var6];
			Object value = map.get(field.getName());
			if (value != null) {
				field.setAccessible(true);
				field.set(obj, TypeConverter.convert(field.getType(), value));
			}
		}

	}
}
