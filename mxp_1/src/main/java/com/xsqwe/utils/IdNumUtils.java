/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <p>
 * 身份证工具<br/>
 * 15位身份证号码：第7、8位为出生年份(两位数)，第9、10位为出生月份，第11、12位代表出生日期，第15位代表性别，奇数为男，偶数为女。 <br/>
 * 18位身份证号码 第7、8、9、10位为出生年份(四位数)，第11、第12位为出生月份，第13、14位代表出生日期，第17位代表性别，奇数为男，偶数为女。
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2018年1月10日 下午5:01:31
 * @version 1.0.0
 */
public final class IdNumUtils {

	private static final Map<Integer, String> AREA_CODE = new HashMap<>();

	private static final int[] PARITYBIT = new int[] { 49, 48, 88, 57, 56, 55, 54, 53, 52, 51, 50 };

	private static final int[] POWER_LIST = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	private static final char[] VERIFY_BIT = new char[] { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

	static {
		AREA_CODE.put(Integer.valueOf(11), "北京");
		AREA_CODE.put(Integer.valueOf(12), "天津");
		AREA_CODE.put(Integer.valueOf(13), "河北");
		AREA_CODE.put(Integer.valueOf(14), "山西");
		AREA_CODE.put(Integer.valueOf(15), "内蒙古");
		AREA_CODE.put(Integer.valueOf(21), "辽宁");
		AREA_CODE.put(Integer.valueOf(22), "吉林");
		AREA_CODE.put(Integer.valueOf(23), "黑龙江");
		AREA_CODE.put(Integer.valueOf(31), "上海");
		AREA_CODE.put(Integer.valueOf(32), "江苏");
		AREA_CODE.put(Integer.valueOf(33), "浙江");
		AREA_CODE.put(Integer.valueOf(34), "安徽");
		AREA_CODE.put(Integer.valueOf(35), "福建");
		AREA_CODE.put(Integer.valueOf(36), "江西");
		AREA_CODE.put(Integer.valueOf(37), "山东");
		AREA_CODE.put(Integer.valueOf(41), "河南");
		AREA_CODE.put(Integer.valueOf(42), "湖北");
		AREA_CODE.put(Integer.valueOf(43), "湖南");
		AREA_CODE.put(Integer.valueOf(44), "广东");
		AREA_CODE.put(Integer.valueOf(45), "广西");
		AREA_CODE.put(Integer.valueOf(46), "海南");
		AREA_CODE.put(Integer.valueOf(50), "重庆");
		AREA_CODE.put(Integer.valueOf(51), "四川");
		AREA_CODE.put(Integer.valueOf(52), "贵州");
		AREA_CODE.put(Integer.valueOf(53), "云南");
		AREA_CODE.put(Integer.valueOf(54), "西藏");
		AREA_CODE.put(Integer.valueOf(61), "陕西");
		AREA_CODE.put(Integer.valueOf(62), "甘肃");
		AREA_CODE.put(Integer.valueOf(63), "青海");
		AREA_CODE.put(Integer.valueOf(64), "宁夏");
		AREA_CODE.put(Integer.valueOf(65), "新疆");
		AREA_CODE.put(Integer.valueOf(71), "台湾");
		AREA_CODE.put(Integer.valueOf(81), "香港");
		AREA_CODE.put(Integer.valueOf(82), "澳门");
		AREA_CODE.put(Integer.valueOf(91), "外国");
	}

	public static String conver15To18(String idNum) {
		idNum = idNum.trim();
		StringBuffer idCard18 = new StringBuffer(idNum);
		int sum = 0;
		if (idNum != null && idNum.length() == 15) {
			idCard18.insert(6, "19");

			int indexOfCheckBit;
			for (indexOfCheckBit = 0; indexOfCheckBit < idCard18.length(); ++indexOfCheckBit) {
				char c = idCard18.charAt(indexOfCheckBit);
				int ai = Integer.parseInt((new Character(c)).toString());
				int Wi = (int) Math.pow(2.0D, (double) (idCard18.length() - indexOfCheckBit)) % 11;
				sum += ai * Wi;
			}

			indexOfCheckBit = sum % 11;
			idCard18.append(VERIFY_BIT[indexOfCheckBit]);
		}

		return idCard18.toString();
	}

	public static boolean isIdNum(String idNum) {
		if (idNum == null || idNum.length() != 15 && idNum.length() != 18) {
			return false;
		} else {
			char[] cs = idNum.toUpperCase().toCharArray();
			int power = 0;

			for (int i = 0; i < cs.length && (i != cs.length - 1 || cs[i] != 'X'); ++i) {
				if (cs[i] < '0' || cs[i] > '9') {
					return false;
				}

				if (i < cs.length - 1) {
					power += (cs[i] - 48) * POWER_LIST[i];
				}
			}

			if (!AREA_CODE.containsKey(Integer.valueOf(idNum.substring(0, 2)))) {
				return false;
			} else {
				String year = idNum.length() == 15 ? getIdNumCalendar() + idNum.substring(6, 8) : idNum.substring(6, 10);
				int iyear = Integer.parseInt(year);
				if (iyear >= 1900 && iyear <= Calendar.getInstance().get(1)) {
					String month = idNum.length() == 15 ? idNum.substring(8, 10) : idNum.substring(10, 12);
					int imonth = Integer.parseInt(month);
					if (imonth >= 1 && imonth <= 12) {
						String day = idNum.length() == 15 ? idNum.substring(10, 12) : idNum.substring(12, 14);
						int iday = Integer.parseInt(day);
						if (iday >= 1 && iday <= 31) {
							if (idNum.length() == 15) {
								return true;
							} else {
								return cs[cs.length - 1] == PARITYBIT[power % 11];
							}
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
	}

	private static int getIdNumCalendar() {
		GregorianCalendar curDay = new GregorianCalendar();
		int curYear = curDay.get(1);
		int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));
		return year2bit;
	}

	public static int getAgeByIdNum(String idNum) {
		int iAge = 0;
		if(idNum == null || idNum.trim().length() == 0) {
			return iAge;
		}
		if (idNum.length() == 15) {
			idNum = conver15To18(idNum);
		}

		String year = idNum.substring(6, 10);
		Calendar cal = Calendar.getInstance();
		int iCurrYear = cal.get(1);
		iAge = iCurrYear - Integer.valueOf(year).intValue();
		return iAge;
	}

	public static String getBirthByIdNum(String idNum) {
		Integer len = idNum.length();
		if (len.intValue() < 15) {
			return null;
		} else {
			if (len.intValue() == 15) {
				idNum = conver15To18(idNum);
			}
			return idNum.substring(6, 14);
		}
	}

	public static String getSexByIdNum(String idNum) {
		String sex = "n";
		if (idNum.length() == 15) {
			idNum = conver15To18(idNum);
		}
		String sCardNum = idNum.substring(16, 17);
		if (Integer.parseInt(sCardNum) % 2 != 0) {
			sex = "m";
		} else {
			sex = "f";
		}
		return sex;
	}

	public static int hoursDiff(Date startTime, Date endTime) {
		if (startTime != null && endTime != null) {
			long millisecond = endTime.getTime() - startTime.getTime();
			long hoursInterval = millisecond / 3600000L;
			return Integer.parseInt(String.valueOf(hoursInterval));
		} else {
			return 0;
		}
	}

	public static String getPinYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; ++i) {
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t2[0] = t2[0].substring(0, 1).toUpperCase() + t2[0].substring(1, t2[0].length());
					t4 = t4 + t2[0];
				} else {
					t4 = t4 + Character.toString(t1[i]);
				}
			}

			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination var7) {
			var7.printStackTrace();
			return t4;
		}
	}

	public static String getPinYinHeadChar(String str) {
		String convert = "";

		for (int j = 0; j < str.length(); ++j) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert = convert + pinyinArray[0].charAt(0);
			} else {
				convert = convert + word;
			}
		}

		return convert.toUpperCase();
	}

	public static String replaceSubString(String str, int prefixLength, int postfixLength) {
		new String();
		if (StringUtils.isBlank(str)) {
			return "";
		} else {
			str = str.trim();
			int strLength = str.length();
			String resultString;
			if (strLength >= prefixLength + postfixLength) {
				StringBuffer resultStringBuffer = new StringBuffer();
				if (strLength == prefixLength + postfixLength) {
					--prefixLength;
					--postfixLength;
				}

				resultStringBuffer.append(str.substring(0, prefixLength));
				String middleString = str.substring(prefixLength, strLength - postfixLength);

				for (int i = 0; i < middleString.length(); ++i) {
					resultStringBuffer.append("*");
				}

				resultString = resultStringBuffer.append(str.substring(strLength - postfixLength, strLength)).toString();
			} else {
				resultString = str.replace(str.substring(strLength / 2), "*");
			}

			return resultString;
		}
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static int getAge(Date dateOfBirth) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				throw new IllegalArgumentException("请您检查您的出生日期!");
			}

			age = now.get(1) - born.get(1);
			if (now.get(6) < born.get(6)) {
				--age;
			}
		}

		return age;
	}

	public static String analyzeBinarySyatemToArray(String binarySystem) {
		StringBuilder sb = new StringBuilder("");
		if (StringUtils.isNotBlank(binarySystem)) {
			for (int i = 0; i < binarySystem.length(); ++i) {
				if (binarySystem.substring(i, i + 1).equals("1")) {
					sb.append(i).append(",");
				}
			}
		}
		String result = sb.toString();
		return result.endsWith(",") ? result.substring(0, result.length() - 1) : (StringUtils.isNotBlank(result) ? result : "-1");
	}

}
