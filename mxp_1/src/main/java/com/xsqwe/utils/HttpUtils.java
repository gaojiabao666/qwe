package com.xsqwe.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class HttpUtils {
	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	public static final String METHOD_GET = "get";

	public static final String METHOD_POST = "post";

	public static final int DEFAULT_TIME_OUT = 10000;

	public static final String DEFAULT_CHARSET = "UTF-8";

	private static final List<String> igoreParams = Arrays.asList(new String[]{});
	public static HttpRequestBase getHttpClient(String url, String type, Integer timeout) {
		return getHttpClient(url, type, timeout,true);
	}
	
	public static HttpRequestBase getHttpClient(String url, String type, Integer timeout,boolean autoRedirect) {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).setRedirectsEnabled(autoRedirect)
				.build();// 设置请求和传输超时时间
		HttpRequestBase httpBase = null;
		if (METHOD_GET.equals(type)) {
			httpBase = new HttpGet(url);
		} else if (METHOD_POST.equals(type)) {
			httpBase = new HttpPost(url);
		} else {
			httpBase = new HttpPost(url);
		}
		httpBase.setConfig(requestConfig);
		return httpBase;
	}

	public static CloseableHttpClient getClient() {
		return HttpClients.custom().setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
	}

	public static <T> List<T> get(String url, Class<T> clazz) throws Exception {
		CloseableHttpClient client = getClient();
		HttpGet httpGet = (HttpGet) HttpUtils.getHttpClient(url, METHOD_GET, DEFAULT_TIME_OUT);
		try {
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity);
			if (entity != null) {
				EntityUtils.consumeQuietly(entity);
			}
			return JSON.parseArray(body, clazz);
		} catch (Exception e) {
			log.error("httputils get to list error : ", e);
			throw e;
		} finally {
			httpGet.abort();
		}
	}

	public static RespResult get(String url) {
		return HttpUtils.doGet(url, null, DEFAULT_TIME_OUT);
	}

	public static RespResult get(String url, boolean needRedirect) {
		return HttpUtils.doGet(url, null, DEFAULT_TIME_OUT);
	}

	public static RespResult get(String url, String encoding) {
		return HttpUtils.doGet(url, null, DEFAULT_TIME_OUT, encoding);
	}

	public static RespResult get(String url, Map<String, String> header) {
		return HttpUtils.doGet(url, header, DEFAULT_TIME_OUT);
	}

	public static RespResult get(String url, Map<String, String> header, int timeOut) {
		return HttpUtils.doGet(url, header, timeOut);
	}

	public static RespResult get(String url, int timeout) {
		return HttpUtils.doGet(url, null, DEFAULT_TIME_OUT);
	}

	public static RespResult post(String url, Map<String, ?> paramsMap) {
		return HttpUtils.doPost(url, null, paramsMap, false, DEFAULT_TIME_OUT);
	}

	public static RespResult post(String url, Map<String, String> header, Map<String, ?> paramsMap,
			boolean stringEntity) {
		return HttpUtils.doPost(url, header, paramsMap, stringEntity, DEFAULT_TIME_OUT);
	}

	public static RespResult post(String url, Map<String, String> header, Map<String, ?> paramsMap,
			boolean stringEntity, int timeout) {
		return HttpUtils.doPost(url, header, paramsMap, stringEntity, timeout);
	}

	public static RespResult post(String url, Map<String, Object> paramsMap, int timeout) {
		return HttpUtils.doPost(url, null, paramsMap, false, timeout);
	}

	private static RespResult doPost(String url, Map<String, String> header, Map<String, ?> paramsMap,
			boolean stringEntity, int timeout) {
		StopWatch stopWatch = new StopWatch();
		List<NameValuePair> params = new ArrayList<>();
		if (paramsMap != null) {
			for (Entry<String, ?> entry : paramsMap.entrySet()) {
				if (entry.getValue() != null) {
					params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
			}
		}
		RespResult rr = new RespResult();
		rr.setUrl(url);
		CloseableHttpClient client = getClient();
		HttpPost post = (HttpPost) HttpUtils.getHttpClient(url, METHOD_POST, timeout);
		if (MapUtils.isNotEmpty(header)) {
			for (Entry<String, String> entry : header.entrySet()) {
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}
		try {
			post.setEntity(stringEntity ? new StringEntity(JSON.toJSONString(paramsMap), "UTF-8")
					: new UrlEncodedFormEntity(params, "UTF-8"));
			stopWatch.start();
			HttpResponse response = client.execute(post);
			stopWatch.stop();
			int status = response.getStatusLine().getStatusCode();
			rr = new RespResult();
			rr.setStatus(status);
			if (status == HttpStatus.SC_MOVED_PERMANENTLY || status == HttpStatus.SC_MOVED_TEMPORARILY) {
				Header locationHeader = response.getFirstHeader("location");
				if (locationHeader != null && StringUtils.isNotEmpty(locationHeader.getValue())) {
					rr = doPost(locationHeader.getValue(), header, paramsMap, stringEntity, timeout);// 用跳转后的页面重新请求
				}
			} else if (status == HttpStatus.SC_OK || status == HttpStatus.SC_REQUEST_TIMEOUT) {
				HttpEntity entity = response.getEntity();
				String body = EntityUtils.toString(entity);
				rr.setRespMsg(body);
			}
			long costTime = stopWatch.getTotalTimeMillis();
			log.info("http client request url: " + url + ",header: " + header + ",params: "
					+ (checkPrintParams(url) ? paramsMap
							: "ignore print params, mobile=" + parseMobileFromMap(url, paramsMap))
					+ "\n" + " response httpcode=" + status + ", response entity: " + rr.getRespMsg() + ", elapsed: "
					+ costTime);
			rr.setCostTime(costTime);
		} catch (Exception e) {
			if (stopWatch.isRunning()) {
				stopWatch.stop();
			}
			rr.setErrFlag(parseHttpTimeoutFlag(e));
			long costTime = stopWatch.getTotalTimeMillis();
			log.error("http请求异常url:" + url + ",params:" + paramsMap + ", elapsed: " + costTime, e);
			rr.setCostTime(costTime);
		} finally {
			post.abort();
		}
		return rr;
	}

	private static String parseMobileFromMap(String url, Map<String, ?> paramsMap) {
//		if (url.indexOf(CUSTOMFSL_OPERATOR_GETOPERATORDATA) > -1) {
//			String bizData = (String) paramsMap.get("biz_data");
//			return parseMobileFromString(url, bizData);
//		}
		return "";
	}

	private static boolean checkPrintParams(String url) {
		for (String ig : igoreParams) {
			if (url.indexOf(ig) > -1) {
				return false;
			}
		}
		return true;
	}

	public static RespResult postByBody(String url, JSONObject json) {
		return postByJson(url, null, json.toJSONString(), DEFAULT_TIME_OUT);
	}

	public static RespResult postByBody(String url, String data) {
		return postByJson(url, null, data, DEFAULT_TIME_OUT);
	}

	public static RespResult postByJson(String url, Map<String, String> header, String data, int timeout) {
		StopWatch stopWatch = new StopWatch();
		RespResult rr = new RespResult();
		CloseableHttpClient httpClient = getClient();
		HttpPost post = (HttpPost) HttpUtils.getHttpClient(url, METHOD_POST, timeout);
		try {
			StringEntity entity = new StringEntity(data, "utf-8");
			entity.setContentType("application/json");
			// entity.setContentEncoding("utf-8");
			post.setEntity(entity);

			post.setHeader("Content-Type", "application/json");
			post.setHeader("Accept", "application/json");
			if (MapUtils.isNotEmpty(header)) {
				for (Entry<String, String> entry : header.entrySet()) {
					post.addHeader(entry.getKey(), entry.getValue());
				}
			}
			stopWatch.start();
			HttpResponse response = httpClient.execute(post);
			stopWatch.stop();
			int status = response.getStatusLine().getStatusCode();
			rr.setStatus(status);
			if (status == HttpStatus.SC_MOVED_PERMANENTLY || status == HttpStatus.SC_MOVED_TEMPORARILY) {
				Header locationHeader = response.getFirstHeader("location");
				if (locationHeader != null && StringUtils.isNotEmpty(locationHeader.getValue())) {
					rr = postByJson(locationHeader.getValue(), header, data, timeout);// 用跳转后的页面重新请求
				}
			} else if (status == HttpStatus.SC_OK || status == HttpStatus.SC_REQUEST_TIMEOUT) {
				HttpEntity respEntity = response.getEntity();
				String body = EntityUtils.toString(respEntity, "UTF-8");
				if (respEntity != null) {
					EntityUtils.consumeQuietly(respEntity);
				}
				rr.setRespMsg(body);
			}
			long costTime = stopWatch.getTotalTimeMillis();
			log.info("http client request url: " + url + ", params: "
					+ (checkPrintParams(url) ? data : "ignore print params, mobile=" + parseMobileFromString(url, data))
					+ "\n" + " response httpcode=" + status + ", response entity: " + rr.getRespMsg() + ", elapsed: "
					+ costTime);
			rr.setCostTime(costTime);
		} catch (Exception e) {
			if (stopWatch.isRunning()) {
				stopWatch.stop();
			}
			rr.setErrFlag(parseHttpTimeoutFlag(e));
			long costTime = stopWatch.getTotalTimeMillis();
			rr.setCostTime(costTime);
			log.error("http请求异常url:" + url + ",params:" + data + ", elapsed: " + costTime, e);
		} finally {
			post.abort();
		}
		return rr;
	}

	public static String parseMobileFromString(String url, String data) {
		try {
			if (StringUtils.isEmpty(data) || data.indexOf("mobile") == -1)
				return "";
			int start = data.indexOf("mobile") + "mobile".length();
			return data.substring(start + 3, start + 14);
		} catch (Exception e) {
			log.error("parseMobileFromString error for url=" + url + " params=" + data + ", errMsg=" + e.getMessage());
		}
		return "";
	}

	public static RespResult postByStringEntity(String url, String stringEntity, String encoding, Map<String, String> header) {
		return postByStringEntity(url, stringEntity, encoding, header, DEFAULT_TIME_OUT);
	}
	public static RespResult postByStringEntity(String url, String stringEntity, String encoding, Map<String, String> header, int timeout) {
		StopWatch stopWatch = new StopWatch();
		RespResult rr = new RespResult();
		CloseableHttpClient client = null;
		if (url != null && url.toLowerCase().startsWith("https")) {
			client = createSSLClientDefault();
		} else {
			client = getClient();
		}
		if (client != null) {
			HttpPost post = (HttpPost) HttpUtils.getHttpClient(url, METHOD_POST, timeout);
			try {
				post.setEntity(new StringEntity(stringEntity, encoding));
//				post.setHeader("Content-Type", "application/x-www-form-urlencoded");
//				post.setHeader("Accept", "application/json");
				if (MapUtils.isNotEmpty(header)) {
					for (Entry<String, String> entry : header.entrySet()) {
						post.addHeader(entry.getKey(), entry.getValue());
					}
				}
				stopWatch.start();
				HttpResponse response = client.execute(post);
				stopWatch.stop();
				int status = response.getStatusLine().getStatusCode();
				rr.setStatus(status);
				if (status == HttpStatus.SC_MOVED_PERMANENTLY || status == HttpStatus.SC_MOVED_TEMPORARILY) {
					Header locationHeader = response.getFirstHeader("location");
					if (locationHeader != null && StringUtils.isNotEmpty(locationHeader.getValue())) {
						rr = postByStringEntity(locationHeader.getValue(), stringEntity, encoding, header, timeout);// 用跳转后的页面重新请求
					}
				} else if (status == HttpStatus.SC_OK || status == HttpStatus.SC_REQUEST_TIMEOUT) {
					HttpEntity entity = response.getEntity();
					String body = EntityUtils.toString(entity, encoding);
					rr.setRespMsg(body);
				}
				long costTime = stopWatch.getTotalTimeMillis();
				log.info("http client request url: " + url + ", params: "
						+ (checkPrintParams(url) ? stringEntity : "ignore print params")
						+ "\n" + " response httpcode=" + status + ", response entity: " + rr.getRespMsg()
						+ ", elapsed: " + costTime);
				rr.setCostTime(costTime);
			} catch (Exception e) {
				if (stopWatch.isRunning()) {
					stopWatch.stop();
				}
				rr.setErrFlag(parseHttpTimeoutFlag(e));
				long costTime = stopWatch.getTotalTimeMillis();
				rr.setCostTime(costTime);
				log.error("http请求异常url:" + url + ",params:" + stringEntity + ", elapsed: " + costTime, e);
			} finally {
				post.abort();
			}
		}
		return rr;
	}

	public static RespResult postByJson(String url, String data) {
		return postByJson(url, null, data, DEFAULT_TIME_OUT);
	}

	public static RespResult postByMap(String url, Map<String, ?> paramsMap, boolean stringEntity, String encoding) {
		StopWatch stopWatch = new StopWatch();
		List<NameValuePair> params = new ArrayList<>();
		for (Entry<String, ?> entry : paramsMap.entrySet()) {
			if (entry.getValue() != null) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
		}
		RespResult rr = new RespResult();
		CloseableHttpClient client = null;
		if (url != null && url.toLowerCase().startsWith("https")) {
			client = createSSLClientDefault();
		} else {
			client = getClient();
		}
		if (client != null) {
			HttpPost post = (HttpPost) HttpUtils.getHttpClient(url, METHOD_POST, DEFAULT_TIME_OUT);
			try {
				post.setEntity(stringEntity ? new StringEntity(JSON.toJSONString(paramsMap), encoding)
						: new UrlEncodedFormEntity(params, encoding));
				post.setHeader("Content-Type", "application/x-www-form-urlencoded");
				post.setHeader("Accept", "application/json");
				stopWatch.start();
				HttpResponse response = client.execute(post);
				stopWatch.stop();
				int status = response.getStatusLine().getStatusCode();
				rr.setStatus(status);
				if (status == HttpStatus.SC_MOVED_PERMANENTLY || status == HttpStatus.SC_MOVED_TEMPORARILY) {
					Header locationHeader = response.getFirstHeader("location");
					if (locationHeader != null && StringUtils.isNotEmpty(locationHeader.getValue())) {
						rr = postByMap(locationHeader.getValue(), paramsMap, stringEntity, encoding);// 用跳转后的页面重新请求
					}
				} else if (status == HttpStatus.SC_OK || status == HttpStatus.SC_REQUEST_TIMEOUT) {
					HttpEntity entity = response.getEntity();
					String body = EntityUtils.toString(entity, "utf-8");
					rr.setRespMsg(body);
				}
				long costTime = stopWatch.getTotalTimeMillis();
				log.info("http client request url: " + url + ", params: "
						+ (checkPrintParams(url) ? paramsMap : "ignore print params, mobile=" + paramsMap.get("mobile"))
						+ "\n" + " response httpcode=" + status + ", response entity: " + rr.getRespMsg()
						+ ", elapsed: " + costTime);
				rr.setCostTime(costTime);
			} catch (Exception e) {
				if (stopWatch.isRunning()) {
					stopWatch.stop();
				}
				rr.setErrFlag(parseHttpTimeoutFlag(e));
				long costTime = stopWatch.getTotalTimeMillis();
				rr.setCostTime(costTime);
				log.error("http请求异常url:" + url + ",params:" + paramsMap + ", elapsed: " + costTime, e);
			} finally {
				post.abort();
			}
		}
		return rr;
	}

	private static RespResult doGet(String url, Map<String, String> header, int timeout, String encoding) {
		StopWatch stopWatch = new StopWatch();
		CloseableHttpClient client = getClient();
		HttpGet httpGet = (HttpGet) HttpUtils.getHttpClient(url, METHOD_GET, timeout,false);
		if (MapUtils.isNotEmpty(header)) {
			for (Entry<String, String> entry : header.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}
		}
		RespResult rr = new RespResult();
		rr.setUrl(url);
		try {
			stopWatch.start();
			HttpResponse response = client.execute(httpGet);
			stopWatch.stop();
			int status = response.getStatusLine().getStatusCode();
			rr.setStatus(status);
			if (status == HttpStatus.SC_MOVED_PERMANENTLY || status == HttpStatus.SC_MOVED_TEMPORARILY) {
				Header locationHeader = response.getFirstHeader("location");
				if (locationHeader != null && StringUtils.isNotEmpty(locationHeader.getValue())) {
					rr = doGet(locationHeader.getValue(), header, timeout, encoding);// 用跳转后的页面重新请求
				}
			}
			if (status == HttpStatus.SC_MOVED_PERMANENTLY || status == HttpStatus.SC_MOVED_TEMPORARILY) {
				Header locationHeader = response.getFirstHeader("location");
				if (locationHeader != null && StringUtils.isNotEmpty(locationHeader.getValue())) {
					rr = doGet(locationHeader.getValue(), header, timeout, encoding);// 用跳转后的页面重新请求
				}
			} else if (status == HttpStatus.SC_OK || status == HttpStatus.SC_REQUEST_TIMEOUT) {
				HttpEntity entity = response.getEntity();
				String body = "";
				if (StringUtils.isNotEmpty(encoding) && !"utf-8".equalsIgnoreCase(encoding)) {
					body = parseStrFromStream(entity.getContent(), encoding);
				} else {
					body = EntityUtils.toString(entity);
				}
				if (null != entity) {
					EntityUtils.consume(entity);
				}
				rr.setRespMsg(body);
			}
			long costTime = stopWatch.getTotalTimeMillis();
			log.info("http client request url: " + url + ", header: " + header + ", params: " + "\n"
					+ " response httpcode=" + status + ", response entity: " + rr.getRespMsg() + ", elapsed: "
					+ costTime);
			rr.setCostTime(costTime);
		} catch (Exception e) {
			if (stopWatch.isRunning()) {
				stopWatch.stop();
			}
			rr.setErrFlag(parseHttpTimeoutFlag(e));
			long costTime = stopWatch.getTotalTimeMillis();
			rr.setCostTime(costTime);
			log.error("http请求异常url:" + url + ", elapsed: " + costTime, e);
		} finally {
			httpGet.abort();
		}
		return rr;
	}

	public static String parseHttpTimeoutFlag(Exception e) {
		if(e instanceof ConnectTimeoutException || e instanceof SSLHandshakeException) {
			return "1";
		} else if(e instanceof SocketTimeoutException || e instanceof SSLException) {
			return "2";
		} else if(e instanceof HttpHostConnectException || e instanceof UnknownHostException) {
			return "3";
		}
		return "";
	}

	public static String parseStrFromStream(InputStream inputStream, String encoding) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, encoding));
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (Exception e) {
			log.error("parseStrFromStream error.", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				log.error("close stream error.", e);
			}
		}
		return "";
	}

	private static RespResult doGet(String url, Map<String, String> header, int timeout) {
		return doGet(url, header, timeout, "utf-8");
	}

	public static String getParamsStr(Map<String, String> params, Boolean encode) {
		if (params == null || params.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : params.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			sb.append("&");
			sb.append(entry.getKey());
			sb.append("=");
			try {
				if (encode) {
					sb.append(URLEncoder.encode(entry.getValue(), DEFAULT_CHARSET));
				} else {
					sb.append(entry.getValue());
				}
			} catch (UnsupportedEncodingException e) {
			}
		}
		return sb.substring(1);
	}

	public static String sortParam(Map<String, String> param) {
		if (null == param || 0 == param.size()) {
			return "";
		}
		// 排序键
		Iterator<String> iterator = param.keySet().iterator();
		String[] arr = new String[param.size()];
		for (int i = 0; iterator.hasNext(); i++) {
			arr[i] = iterator.next();
		}
		Arrays.sort(arr);
		// 生成字符串
		StringBuffer sb = new StringBuffer();
		for (String key : arr) {
			String value = param.get(key);
			if (StringUtils.isNotEmpty(value)) {
				sb.append(key).append("=").append(value).append(",");
			}
		}
		// 检查结果
		if (sb.length() > 0) {
			return sb.substring(0, sb.length() - 1);
		} else {
			return "";
		}
	}

	public static String postByOther(String url, String data) {
		OutputStream out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = getConnection(realUrl);

			Map<String, String> defaultHeaders = new LinkedHashMap<>();
			defaultHeaders.put("accept", "*/*");
			defaultHeaders.put("connection", "Keep-Alive");
			defaultHeaders.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			defaultHeaders.put("Content-Type", "application/json");
			Iterator<String> ite = defaultHeaders.keySet().iterator();
			String key = null;
			while (ite.hasNext()) {
				key = ite.next();
				conn.setRequestProperty(key, defaultHeaders.get(key));
			}
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = conn.getOutputStream();
			if (data != null) {
				// 发送请求参数
				out.write(data.getBytes(DEFAULT_CHARSET));
				// flush输出流的缓冲
				out.flush();
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_CHARSET));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.info("算话请求失败：{}", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static HttpURLConnection getConnection(URL url) {
		HttpURLConnection connection = null;
		try {
			if (url.getProtocol().toUpperCase().startsWith("HTTPS")) {
				SSLContext ctx = SSLContext.getInstance("SSL");
				ctx.init(new KeyManager[0], new TrustManager[] { new X509TrustManager() {
					@Override
					public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					}

					@Override
					public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					}

					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
				} }, new SecureRandom());
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setSSLSocketFactory(ctx.getSocketFactory());
				conn.setConnectTimeout(10000);
				conn.setReadTimeout(10000);
				conn.setHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
				connection = conn;
			} else {
				connection = (HttpURLConnection) url.openConnection();
			}
		} catch (Exception e) {
			log.info("算话获取请求链接失败：{}", e);
		}
		return connection;
	}

	/**
	 * 描述：获取证书访问Https请求
	 * 
	 * @return CloseableHttpClient
	 */
	@SuppressWarnings("all")
	public static CloseableHttpClient createSSLClientDefault() {
		try {
			// 信任所有
			X509TrustManager x509mgr = new X509TrustManager() {
				// 该方法检查客户端的证书，若不信任该证书则抛出异常
				public void checkClientTrusted(X509Certificate[] xcs, String string) {
				}

				// 该方法检查服务端的证书，若不信任该证书则抛出异常
				public void checkServerTrusted(X509Certificate[] xcs, String string) {
				}

				// 返回受信任的X509证书数组。
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { x509mgr }, null);
			// 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return HttpClients.custom().setSSLSocketFactory(sslsf)
					.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
		} catch (Exception e) {
			log.info("忽略证书失败：{}", e);
		}
		// 创建默认的httpClient实例.
		return getClient();

	}
}
