/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.admin.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p >
 *
 * @author yongfei.xie
 * @date 2017年12月8日 上午10:17:35
 * @version 1.0.0
 */
public class NameValuePairHelper {

	public NameValuePairHelper() {
	}

	public static List<NameValuePair> convert(Map<String, String> pairs) {
		List<NameValuePair> result = new ArrayList<NameValuePair>();
		Iterator<String> var2 = pairs.keySet().iterator();

		while (var2.hasNext()) {
			String key = (String) var2.next();
			NameValuePair pair = new BasicNameValuePair(key, (String) pairs.get(key));
			result.add(pair);
		}

		return result;
	}
}
