package com.xsqwe.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberUtils {
	private static BigDecimal DefaultBigDecimal = new BigDecimal(0);
	private static Double DefaultDouble = new Double(0.0D);
	private static Float DefaultFloat = new Float(0.0F);
	private static Integer DefaultInteger = new Integer(0);
	private static Long DefaultLong = new Long(0L);
	private static Boolean DefaultBoolean = new Boolean(false);
	private static int Defaultint = 0;
	private static float Defaultfloat = 0.0F;
	private static double Defaultdouble = 0.0D;
	private static long Defaultlong = 0L;
	private static boolean Defaultboolean = false;
	public static boolean useDefault = true;
	public static boolean throwExceptionForce = false;
	public static boolean acceptBlank = true;

	public NumberUtils() {
	}

	public static BigDecimal toBigDecimal(double num) {
		return toBigDecimal(Double.toString(num));
	}

	public static BigDecimal toBigDecimal(float num) {
		return toBigDecimal(Float.toString(num));
	}

	public static BigDecimal toBigDecimal(int num) {
		return toBigDecimal(Integer.toString(num));
	}

	public static BigDecimal toBigDecimal(long num) {
		return toBigDecimal(Long.toString(num));
	}

	public static BigDecimal toBigDecimal(Object obj) {
		return useDefault ? toBigDecimal(obj, DefaultBigDecimal) : toBigDecimalEX(obj);
	}

	public static BigDecimal toBigDecimalEX(Object obj) {
		try {
			return new BigDecimal(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			throw new RuntimeException(var2);
		}
	}

	public static BigDecimal toBigDecimalDef(Object obj) {
		try {
			return new BigDecimal(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			return null;
		}
	}

	public static BigDecimal toBigDecimal(Object obj, BigDecimal defaultBigDecimal) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultBigDecimal);
				}

				return defaultBigDecimal;
			} else {
				return toBigDecimalEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultBigDecimal);
			}

			return defaultBigDecimal;
		} else if (obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		} else if (0 == obj.toString().trim().length()) {
			return defaultBigDecimal;
		} else {
			try {
				return new BigDecimal(obj.toString());
			} catch (NumberFormatException var3) {
				log.error("无法正确解析" + obj + "至BigDecimal, 返回默认值" + defaultBigDecimal);
				return defaultBigDecimal;
			} catch (Exception var4) {
				log.error("解析" + obj + "时出现异常--" + var4 + "返回默认值" + defaultBigDecimal);
				return defaultBigDecimal;
			}
		}
	}

	public static Double toDouble(Object obj) {
		return useDefault ? toDouble(obj, DefaultDouble) : toDoubleEX(obj);
	}

	public static Double toDoubleEX(Object obj) {
		try {
			return Double.valueOf(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(var2));
		}
	}

	public static Double toDoubleDef(Object obj) {
		try {
			return Double.valueOf(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			return null;
		}
	}

	public static Double toDouble(Object obj, Double defaultDouble) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultDouble);
				}

				return defaultDouble;
			} else {
				return toDoubleEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultDouble);
			}

			return defaultDouble;
		} else if (obj instanceof Double) {
			return (Double) obj;
		} else if (0 == obj.toString().trim().length()) {
			return DefaultDouble;
		} else {
			try {
				return Double.valueOf(obj.toString());
			} catch (NumberFormatException var3) {
				log.error("无法正确解析" + obj + "至Double, 返回默认值" + defaultDouble);
				return defaultDouble;
			} catch (Exception var4) {
				log.error("解析" + obj + "时出现异常--" + var4 + "返回默认值" + defaultDouble);
				return defaultDouble;
			}
		}
	}

	public static Float toFloat(Object obj) {
		return useDefault ? toFloat(obj, DefaultFloat) : toFloatEX(obj);
	}

	public static Float toFloatEX(Object obj) {
		try {
			return Float.valueOf(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			return null;
		}
	}

	public static Float toFloatDef(Object obj) {
		try {
			return Float.valueOf(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			return null;
		}
	}

	public static Float toFloat(Object obj, Float defaultFloat) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultFloat);
				}

				return defaultFloat;
			} else {
				return toFloatEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultFloat);
			}

			return defaultFloat;
		} else if (obj instanceof Float) {
			return (Float) obj;
		} else if (0 == obj.toString().trim().length()) {
			return DefaultFloat;
		} else {
			try {
				return Float.valueOf(obj.toString());
			} catch (NumberFormatException var3) {
				log.error("无法正确解析" + obj + "至Float, 返回默认值" + defaultFloat);
				return defaultFloat;
			} catch (Exception var4) {
				log.error("解析" + obj + "时出现异常--" + var4 + "返回默认值" + defaultFloat);
				return defaultFloat;
			}
		}
	}

	public static Integer toInteger(Object obj) {
		return useDefault ? toInteger(obj, DefaultInteger) : toIntegerEX(obj);
	}

	public static Integer toIntegerEX(Object obj) {
		try {
			return Integer.valueOf(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			return null;
		}
	}

	public static Integer toIntegerDef(Object obj) {
		try {
			return Integer.valueOf(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			return null;
		}
	}

	public static Integer toInteger(Object obj, Integer defaultInteger) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultInteger);
				}

				return defaultInteger;
			} else {
				return toIntegerEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultInteger);
			}

			return defaultInteger;
		} else if (obj instanceof Integer) {
			return (Integer) obj;
		} else if (0 == obj.toString().trim().length()) {
			return DefaultInteger;
		} else {
			try {
				return Integer.valueOf(obj.toString());
			} catch (NumberFormatException var3) {
				log.error("无法正确解析" + obj + "至Integer, 返回默认值" + defaultInteger);
				return defaultInteger;
			} catch (Exception var4) {
				log.error("解析" + obj + "时出现异常--" + var4 + "返回默认值" + defaultInteger);
				return defaultInteger;
			}
		}
	}

	public static int toint(Object obj) {
		return useDefault ? toint(obj, Defaultint) : tointEX(obj);
	}

	public static int tointEX(Object obj) {
		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(var2));
		}
	}

	public static int toint(Object obj, int defaultint) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultint);
				}

				return defaultint;
			} else {
				return tointEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultint);
			}

			return defaultint;
		} else if (obj instanceof Integer) {
			return ((Integer) obj).intValue();
		} else if (0 == obj.toString().trim().length()) {
			return Defaultint;
		} else {
			try {
				return Integer.parseInt(obj.toString());
			} catch (NumberFormatException var3) {
				log.error("无法正确解析" + obj + "至int, 返回默认值" + defaultint);
				return defaultint;
			} catch (Exception var4) {
				log.error("解析" + obj + "时出现异常--" + var4 + "返回默认值" + defaultint);
				return defaultint;
			}
		}
	}

	public static float tofloat(Object obj) {
		return useDefault ? tofloat(obj, Defaultfloat) : tofloatEX(obj);
	}

	public static float tofloatEX(Object obj) {
		try {
			return Float.parseFloat(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(var2));
		}
	}

	public static float tofloat(Object obj, float defaultfloat) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultfloat);
				}

				return defaultfloat;
			} else {
				return tofloatEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultfloat);
			}

			return defaultfloat;
		} else if (obj instanceof Float) {
			return ((Float) obj).floatValue();
		} else if (0 == obj.toString().trim().length()) {
			return Defaultfloat;
		} else {
			try {
				return Float.parseFloat(obj.toString());
			} catch (NumberFormatException var3) {
				log.error("无法正确解析" + obj + "至float, 返回默认值" + defaultfloat);
				return defaultfloat;
			} catch (Exception var4) {
				log.error("解析" + obj + "时出现异常--" + var4 + "返回默认值" + defaultfloat);
				return defaultfloat;
			}
		}
	}

	public static double todouble(Object obj) {
		return useDefault ? todouble(obj, Defaultdouble) : todoubleEX(obj);
	}

	public static double todoubleEX(Object obj) {
		try {
			return Double.parseDouble(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(var2));
		}
	}

	public static double todouble(Object obj, double defaultdouble) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultdouble);
				}

				return defaultdouble;
			} else {
				return todoubleEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultdouble);
			}

			return defaultdouble;
		} else if (obj instanceof Double) {
			return ((Double) obj).doubleValue();
		} else if (0 == obj.toString().trim().length()) {
			return Defaultdouble;
		} else {
			try {
				return Double.parseDouble(obj.toString());
			} catch (NumberFormatException var4) {
				log.error("无法正确解析" + obj + "至double, 返回默认值" + defaultdouble);
				return defaultdouble;
			} catch (Exception var5) {
				log.error("解析" + obj + "时出现异常--" + var5 + "返回默认值" + defaultdouble);
				return defaultdouble;
			}
		}
	}

	public static Long toLong(Object obj) {
		return useDefault ? toLong(obj, DefaultLong) : toLongEX(obj);
	}

	public static Long toLongEX(Object obj) {
		try {
			return Long.valueOf(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(var2));
		}
	}

	public static Long toLongDef(Object obj) {
		try {
			return Long.valueOf(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			return null;
		}
	}

	public static Long toLong(Object obj, Long defaultLong) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultLong);
				}

				return defaultLong;
			} else {
				return toLongEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultLong);
			}

			return defaultLong;
		} else if (obj instanceof Long) {
			return (Long) obj;
		} else if (0 == obj.toString().trim().length()) {
			return DefaultLong;
		} else {
			try {
				return Long.valueOf(obj.toString());
			} catch (NumberFormatException var3) {
				log.error("无法正确解析" + obj + "至Long, 返回默认值" + defaultLong);
				return defaultLong;
			} catch (Exception var4) {
				log.error("解析" + obj + "时出现异常--" + var4 + "返回默认值" + defaultLong);
				return defaultLong;
			}
		}
	}

	public static long tolong(Object obj) {
		return useDefault ? tolong(obj, Defaultlong) : tolongEX(obj);
	}

	public static long tolongEX(Object obj) {
		try {
			return Long.parseLong(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(var2));
		}
	}

	public static long tolong(Object obj, long defaultlong) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultlong);
				}

				return defaultlong;
			} else {
				return tolongEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultlong);
			}

			return defaultlong;
		} else if (obj instanceof Long) {
			return ((Long) obj).longValue();
		} else if (0 == obj.toString().trim().length()) {
			return Defaultlong;
		} else {
			try {
				return Long.parseLong(obj.toString());
			} catch (NumberFormatException var4) {
				log.error("无法正确解析" + obj + "至long, 返回默认值" + defaultlong);
				return defaultlong;
			} catch (Exception var5) {
				log.error("解析" + obj + "时出现异常--" + var5 + "返回默认值" + defaultlong);
				return defaultlong;
			}
		}
	}

	public static Boolean toBoolean(Object obj) {
		return useDefault ? toBoolean(obj, DefaultBoolean) : toBooleanEX(obj);
	}

	public static Boolean toBooleanEX(Object obj) {
		try {
			return Boolean.valueOf(obj.toString());
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(var2));
		}
	}

	public static Boolean toBoolean(Object obj, Boolean defaultBoolean) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultBoolean);
				}

				return defaultBoolean;
			} else {
				return toBooleanEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultBoolean);
			}

			return defaultBoolean;
		} else if (obj instanceof Boolean) {
			return (Boolean) obj;
		} else {
			return 0 == obj.toString().trim().length() ? DefaultBoolean : Boolean.valueOf(obj.toString());
		}
	}

	public static boolean toboolean(Object obj) {
		return useDefault ? toboolean(obj, Defaultboolean) : tobooleanEX(obj);
	}

	public static boolean tobooleanEX(Object obj) {
		try {
			return Boolean.valueOf(obj.toString()).booleanValue();
		} catch (Exception var2) {
			log.error("解析" + obj + "时出现异常--" + var2);
			throw new RuntimeException(ExceptionUtils.getRootCauseMessage(var2));
		}
	}

	public static boolean toboolean(Object obj, boolean defaultboolean) {
		if (throwExceptionForce) {
			if (acceptBlank && isBlank(obj)) {
				if (log.isInfoEnabled()) {
					log.info("传入空指针或blank字符串,返回默认值" + defaultboolean);
				}

				return defaultboolean;
			} else {
				return tobooleanEX(obj);
			}
		} else if (obj == null) {
			if (log.isInfoEnabled()) {
				log.info("传入空指针,返回默认值" + defaultboolean);
			}

			return defaultboolean;
		} else if (obj instanceof Boolean) {
			return ((Boolean) obj).booleanValue();
		} else {
			return 0 == obj.toString().trim().length() ? Defaultboolean
					: Boolean.valueOf(obj.toString()).booleanValue();
		}
	}

	public static float roundHalfUp(float num) {
		return (new BigDecimal(String.valueOf(num))).setScale(0, 4).floatValue();
	}

	public static double roundHalfUp(double num) {
		return (new BigDecimal(String.valueOf(num))).setScale(0, 4).doubleValue();
	}

	public static float roundHalfUp(int scale, float num) {
		return (new BigDecimal(String.valueOf(num))).setScale(scale, 4).floatValue();
	}

	public static double roundHalfUp(int scale, double num) {
		return (new BigDecimal(String.valueOf(num))).setScale(scale, 4).doubleValue();
	}

	public static float roundDown(int scale, float num) {
		return (new BigDecimal(String.valueOf(num))).setScale(scale, 1).floatValue();
	}

	public static double roundDown(int scale, double num) {
		return (new BigDecimal(String.valueOf(num))).setScale(scale, 1).doubleValue();
	}

	public static boolean isNumeric(Object obj) {
		return obj != null && (obj instanceof BigDecimal || obj instanceof Integer || obj instanceof Double
				|| obj instanceof Float || obj instanceof Long || obj.getClass().equals(Integer.TYPE)
				|| obj.getClass().equals(Double.TYPE) || obj.getClass().equals(Float.TYPE)
				|| obj.getClass().equals(Long.TYPE));
	}

	private static boolean isBlank(Object obj) {
		if (obj == null) {
			return true;
		} else {
			return StringUtils.isBlank(obj.toString());
		}
	}

	public static List<Integer> parseBinaryNum(int num) {
		List<Integer> list = new ArrayList();
		if (num == 0)
			return list;
		int index = 1;
		int reslut = 0;
		int count = 0;
		while (true) {
			if ((reslut = num & index) != 0) {
				list.add(new Integer(reslut));
				if (num == (count += reslut)) {
					return list;
				}
			}
			index *= 2;
		}
	}
}
