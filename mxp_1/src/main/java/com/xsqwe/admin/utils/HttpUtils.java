/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.admin.utils;

import com.alibaba.fastjson.JSONObject;
import com.xsqwe.web.enums.CommonCodeEnum;
import com.xsqwe.web.exception.CommonException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p >
 *
 * @author yongfei.xie
 * @date 2017年12月8日 上午10:14:56
 * @version 1.0.0
 */
public class HttpUtils {

	private static CloseableHttpClient httpclient = HttpClients.createDefault();

	private static CloseableHttpClient httpsclient;

	public HttpUtils() {
	}

	public static String getHttp(String apiUrl, Map<String, String> paramPairs) throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpGet httpget = new HttpGet(apiUrl);
			List<NameValuePair> params = NameValuePairHelper.convert(paramPairs);
			String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(params));
			httpget.setURI(new URI(apiUrl + (apiUrl.indexOf("?") == -1 ? "?" : "&") + paramsStr));
			httpResponse = httpclient.execute(httpget);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception var11) {
			throw new Exception(var11);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	public static String getHttp(String apiUrl) throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpGet httpget = new HttpGet(apiUrl);
			httpResponse = httpclient.execute(httpget);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception var8) {
			throw new Exception(var8);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	public static String getHttps(String apiUrl) throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpGet httpget = new HttpGet(apiUrl);
			httpResponse = httpsclient.execute(httpget);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception var8) {
			throw new Exception(var8);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	public static String postHttp(String apiUrl, Map<String, String> paramPairs) throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			List<NameValuePair> params = NameValuePairHelper.convert(paramPairs);
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(uefEntity);
			httpResponse = httpclient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception var11) {
			throw new Exception(var11);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	@SuppressWarnings("unused")
	private static String getHttps(String apiUrl, Map<String, String> paramPairs) throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpGet httpget = new HttpGet(apiUrl);
			List<NameValuePair> params = NameValuePairHelper.convert(paramPairs);
			String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(params));
			httpget.setURI(new URI(apiUrl + (apiUrl.indexOf("?") == -1 ? "?" : "&") + paramsStr));
			httpResponse = httpsclient.execute(httpget);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception var11) {
			throw new Exception(var11);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	public static String postHttps(String apiUrl, Map<String, String> paramPairs) throws CommonException {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			List<NameValuePair> params = NameValuePairHelper.convert(paramPairs);
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(uefEntity);
			httpResponse = httpsclient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			throw new CommonException(CommonCodeEnum.COMMON_EXCEPTION.getCode(), e.getMessage());
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					throw new CommonException(CommonCodeEnum.COMMON_EXCEPTION.getCode(), e.getMessage());
				}
			}

		}

		return body;
	}

	public static String postJsonHttps(String apiUrl, Map<String, Object> paramPairs) throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			String jsonString = JSONObject.toJSONString(paramPairs);
			StringEntity paramEntity = new StringEntity(jsonString, "UTF-8");
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(paramEntity);
			httpResponse = httpsclient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception var11) {
			throw new Exception(var11);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	public static String postJsonHttps(String apiUrl, String requestBody) throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			StringEntity paramEntity = new StringEntity(requestBody, "UTF-8");
			httpPost.addHeader("content-type", "application/json");
			httpPost.setEntity(paramEntity);
			httpResponse = httpsclient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception var10) {
			throw new Exception(var10);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	public static String postJson(String apiUrl, String requestBody, Map<String, String> headers) throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			StringEntity paramEntity = new StringEntity(requestBody, "UTF-8");
			httpPost.addHeader("content-type", "application/json");
			if (headers != null) {
				Iterator<String> var7 = headers.keySet().iterator();

				while (var7.hasNext()) {
					String key = (String) var7.next();
					httpPost.addHeader(key, (String) headers.get(key));
				}
			}

			httpPost.setEntity(paramEntity);
			httpResponse = httpclient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception var12) {
			throw new Exception(var12);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	public static String postJsonHttps(String apiUrl, String requestBody, Map<String, String> headers)
			throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			StringEntity paramEntity = new StringEntity(requestBody, "UTF-8");
			httpPost.addHeader("content-type", "application/json");
			if (headers != null) {
				Iterator<String> var7 = headers.keySet().iterator();

				while (var7.hasNext()) {
					String key = (String) var7.next();
					httpPost.addHeader(key, (String) headers.get(key));
				}
			}

			httpPost.setEntity(paramEntity);
			httpResponse = httpsclient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception var12) {
			throw new Exception(var12);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	public static String putXmlHttps(String apiUrl, String requestBody) throws Exception {
		return putXmlHttps(apiUrl, requestBody, null);
	}

	public static String putXmlHttps(String apiUrl, String requestBody, Map<String, String> headers) throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpPut httpPost = new HttpPut(apiUrl);
			StringEntity paramEntity = new StringEntity(requestBody, "UTF-8");
			httpPost.addHeader("content-type", "application/xml");
			if (headers != null) {
				Iterator<String> var7 = headers.keySet().iterator();

				while (var7.hasNext()) {
					String key = (String) var7.next();
					httpPost.addHeader(key, (String) headers.get(key));
				}
			}

			httpPost.setEntity(paramEntity);
			httpResponse = httpsclient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (Exception var12) {
			throw new Exception(var12);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	public static String requestService(String toURL, String data) throws Exception {
		StringBuffer bs = new StringBuffer();
		URL url = new URL(toURL);
		HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
		urlcon.setRequestMethod("POST");
		urlcon.setUseCaches(false);
		urlcon.setConnectTimeout(30000);
		urlcon.setReadTimeout(30000);
		urlcon.setDoInput(true);
		urlcon.setDoOutput(true);
		urlcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		OutputStream out = urlcon.getOutputStream();
		out.write(data.getBytes("UTF-8"));
		out.flush();
		out.close();
		urlcon.connect();
		InputStream is = urlcon.getInputStream();
		BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
		String l = null;

		while ((l = buffer.readLine()) != null) {
			bs.append(l);
		}

		return bs.toString();
	}

	public static String postXmlHttps(String apiUrl, String requestBody) throws Exception {
		return postXmlHttps(apiUrl, requestBody, null);
	}

	public static String postXmlHttps(String apiUrl, String requestBody, Map<String, String> headers) throws Exception {
		String body = null;
		CloseableHttpResponse httpResponse = null;

		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			StringEntity paramEntity = new StringEntity(requestBody, "UTF-8");
			httpPost.addHeader("content-type", "application/xml");
			if (headers != null) {
				Iterator<String> var7 = headers.keySet().iterator();

				while (var7.hasNext()) {
					String key = (String) var7.next();
					httpPost.addHeader(key, (String) headers.get(key));
				}
			}

			httpPost.setEntity(paramEntity);
			httpResponse = httpsclient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity, "UTF-8");
			EntityUtils.consume(entity);
		} catch (Exception var12) {
			throw new Exception(var12);
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}

		return body;
	}

	public static void main(String[] args) {
		try {
			String s = postXmlHttps("https://demo.otms.cn/ws/orderImport", "", null);
			System.out.println("s:" + s);
		} catch (Exception var2) {
			var2.printStackTrace();
		}

	}

	static {
		SSLContext sslctxt;
		try {
			sslctxt = SSLContext.getInstance("TLS");
			sslctxt.init((KeyManager[]) null, new TrustManager[] { new MyX509TrustManager() }, new SecureRandom());
		} catch (Exception var2) {
			throw new RuntimeException("https client初始化错误");
		}

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslctxt);
		httpsclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
}
