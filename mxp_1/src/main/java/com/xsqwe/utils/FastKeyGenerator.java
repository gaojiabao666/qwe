/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.utils;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

import com.xsqwe.web.enums.FastDomainEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 唯一序号生成器
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年11月17日 下午4:31:09
 * @version 1.0.0
 */

@Slf4j
public final class FastKeyGenerator {

	private static final char[] CHS = "0123456789abcdefghijkmnpqrstuvwxyz".toCharArray();

	/**
	 * 集群节点环境编号的环境变量名称或者系统参数名称
	 */
	private static final String SYS_ENV_NAME = "FAST_SYS_ENV";

	/**
	 * 默认的环境编号
	 */
	private static final String DEFAULLT_SYS_ENV = "66";

	private static final String DEFAULLT_SYS_DOMAIN = "88";

	/**
	 * 两位的系统环境编号
	 */
	private static final char[] SYS_ENV = getWrapSysEnv().toCharArray();

	private static final AtomicInteger INCR = new AtomicInteger(0);

	private static final int LEN_SEQ = 6;
	private static final int LEN_DOMAIN = 2;
	private static final int LEN_ENV = 2;
	private static final int LEN_YEAR = 4;
	private static final int LEN_MONTH = 2;
	private static final int LEN_DAY = 2;
	private static final int LEN_HOUR = 2;
	private static final int LEN_MINUTE = 2;
	private static final int LEN_SECOND = 2;

	private static final int OFFSET_SEQ = 0;
	private static final int OFFSET_DOMAIN = OFFSET_SEQ + LEN_SEQ;
	private static final int OFFSET_ENV = OFFSET_DOMAIN + LEN_DOMAIN;
	private static final int OFFSET_YEAR = OFFSET_ENV + LEN_ENV;
	private static final int OFFSET_MONTH = OFFSET_YEAR + LEN_YEAR;
	private static final int OFFSET_DAY = OFFSET_MONTH + LEN_MONTH;
	private static final int OFFSET_HOUR = OFFSET_DAY + LEN_DAY;
	private static final int OFFSET_MINUTE = OFFSET_HOUR + LEN_HOUR;
	private static final int OFFSET_SECOND = OFFSET_MINUTE + LEN_MINUTE;

	private static final int COUNT = OFFSET_SECOND + LEN_SECOND;

	private FastKeyGenerator() {
	}

	/**
	 * 生成唯一序列号，总共 24 位，结构：
	 * <p>
	 * [6位顺序号（倒序）][域简称][SYS_ENV][yyyyMMddHHmmss]
	 * </p>
	 * 
	 * @param domain
	 *            域名称
	 * @return 序列号
	 */
	public static String generateKey(FastDomainEnum domain) {
		char[] chs = new char[COUNT];
		appendSequence(chs);
		appendDomain(chs, domain);
		appendSysEnv(chs);
		appendTime(chs);
		return new String(chs);
	}

	private static String getWrapSysEnv() {
		String env = getSysEnv();
		log.info("RISK_SYS_ENV = {}", env);
		return env;
	}

	private static String getSysEnv() {
		String env = System.getProperty(SYS_ENV_NAME);
		if (!StringUtils.isBlank(env)) {
			return env.trim();
		}
		env = System.getenv(SYS_ENV_NAME);
		if (!StringUtils.isBlank(env)) {
			return env.trim();
		}
		return DEFAULLT_SYS_ENV;
	}

	private static void appendSequence(char[] chs) {
		int num = (INCR.getAndIncrement() & Integer.MAX_VALUE);
		for (int i = OFFSET_SEQ; i < LEN_SEQ; i++) {
			chs[i] = CHS[num % CHS.length];
			num /= CHS.length;
		}
	}

	private static void appendDomain(char[] chs, FastDomainEnum domainEnum) {
		String domainVal = DEFAULLT_SYS_DOMAIN;
		if (domainEnum != null) {
			domainVal = domainEnum.getCode();
		}
		char[] domain = domainVal.toCharArray();
		if (domain.length < LEN_DOMAIN) {
			chs[OFFSET_DOMAIN] = '0';
			chs[OFFSET_DOMAIN + 1] = '0';
		} else {
			chs[OFFSET_DOMAIN] = domain[0];
			chs[OFFSET_DOMAIN + 1] = domain[1];
		}
	}

	private static void appendSysEnv(char[] chs) {
		if (chs.length == 1) {
			chs[OFFSET_ENV] = '0';
			chs[OFFSET_ENV + 1] = SYS_ENV[0];
			return;
		}
		chs[OFFSET_ENV] = SYS_ENV[0];
		chs[OFFSET_ENV + 1] = SYS_ENV[1];
	}

	private static void appendTime(char[] chs) {
		Calendar calendar = Calendar.getInstance();
		appendNum(chs, calendar.get(Calendar.YEAR), OFFSET_YEAR, LEN_YEAR);
		appendNum(chs, calendar.get(Calendar.MONTH) + 1, OFFSET_MONTH, LEN_MONTH);
		appendNum(chs, calendar.get(Calendar.DAY_OF_MONTH), OFFSET_DAY, LEN_DAY);
		appendNum(chs, calendar.get(Calendar.HOUR_OF_DAY), OFFSET_HOUR, LEN_HOUR);
		appendNum(chs, calendar.get(Calendar.MINUTE), OFFSET_MINUTE, LEN_MINUTE);
		appendNum(chs, calendar.get(Calendar.SECOND), OFFSET_SECOND, LEN_SECOND);
	}

	private static void appendNum(char[] chs, int num, int offset, int max) {
		for (int i = offset + max - 1; i >= offset; i--) {
			chs[i] = (char) (num % 10 + '0');
			num /= 10;
		}
	}

}
