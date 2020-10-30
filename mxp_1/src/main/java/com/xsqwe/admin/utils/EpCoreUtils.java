package com.xsqwe.admin.utils;

import com.xsqwe.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
public class EpCoreUtils {
	
	public static final int LIMIT_DEFAULT = 1028;

	/**
	 * 将网络图片编码为base64
	 *
	 * @param url
	 * @return
	 * @throws BusinessException
	 */
	public static String encodeImageToBase64(URL url) throws Exception {
		//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		System.out.println("图片的路径为:" + url.toString());
		//打开链接
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			//设置请求方式为"GET"
			conn.setRequestMethod("GET");
			//超时响应时间为5秒
			conn.setConnectTimeout(5 * 1000);
			//通过输入流获取图片数据
			InputStream inStream = conn.getInputStream();
			//得到图片的二进制数据，以二进制封装得到数据，具有通用性
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			//创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			//每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			//使用一个输入流从buffer里把数据读取出来
			while ((len = inStream.read(buffer)) != -1) {
				//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			//关闭输入流
			inStream.close();
			byte[] data = outStream.toByteArray();
			//对字节数组Base64编码
			BASE64Encoder encoder = new BASE64Encoder();
			String base64 = encoder.encode(data);
			System.out.println("网络文件[{}]编码成base64字符串:[{}]"+url.toString()+base64);
			return base64;//返回Base64编码过的字节数组字符串
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("图片上传失败,请联系客服!");
		}
	}

	/**
	 * 限制字符串最大长度
	 * @param str
	 * @param limit
	 * @return
	 */
	public static String limitLen(String str) {
		return limitLen(str, LIMIT_DEFAULT);
	}

	/**
	 * 限制字符串最大长度
	 * @param str
	 * @param limit
	 * @return
	 */
	public static String limitLen(String str, int limit) {
		if (!Tools.isBlank(str) && str.length() > limit) {
			return str.substring(0, limit);
		}
		return str;
	}

	/**
	 * 当值为空时填充默认字符
	 * @return
	 */
	public static String getBlankVal(String str) {
		if (Tools.isBlank(str)) {
			return "*";
		}
		return str;
	}
	
	/**
	 * 获取时间字符串
	 * @param date
	 * @return
	 */
	public static String toDateTimeStr(Date date) {
		if (date == null) {
			return "";
		} else {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		}
	}
	
	/**
	 * 根据时间字符串获取时间
	 * @param str
	 * @param formatStr
	 * @return
	 */
	public static Date toDateTime(String str, String formatStr) {
		if (Tools.isBlank(str)) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			return sdf.parse(str);
		} catch (ParseException e) {
			log.error("日期格式转换错误", e);
			return null;
		}
	}
	
	public static Date addHours(Date date, int days) {
		if (date == null) {
			if (log.isInfoEnabled()) {
				log.info("日期date转换出错，字符串为空");
			}
			return null;
		} else {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR_OF_DAY, days);
			return calendar.getTime();
		}
	}
	
	public static final String toXml(Document document) {
		OutputFormat format = new OutputFormat();
		format.setEncoding("UTF-8");
		// format.setExpandEmptyElements(true);
		StringWriter out = new StringWriter();
		XMLWriter writer = new XMLWriter(out, format);
		try {
			writer.write(document);
			writer.flush();
		} catch (IOException e) {
			log.error("XML报文转字符串异常", e);
		}
		return out.toString();
	}

	/**
	 * 当值为空时填充默认值0
	 * @param <T>
	 * @return
	 */
	public static Long getBlankLongVal(Long num) {
		if(num ==null){
			return 0L;
		}
		return num;
	}
	public static String getBlankStrVal(Long num) {
		if(num == null){
			return "0";
		}
		return num.toString();
	}

	public static boolean isEmpty(String s) {
		boolean flg = true;
		return s != null && s.trim().length() > 0 ? false : flg;
	}
}
