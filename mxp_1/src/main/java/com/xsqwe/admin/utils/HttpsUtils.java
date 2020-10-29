package com.xsqwe.admin.utils;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 
 * <p> 
 * </p>
 *
 * @author  weizhen.cao
 * @date  2018年9月4日 下午1:52:34
 * @version  1.0.0
 */
public class HttpsUtils {

	private static class TrustAnyTrustManager implements X509TrustManager {
		// 该方法检查客户端的证书，若不信任该证书则抛出异常。由于我们不需要对客户端进行认证，因此我们只需要执行默认的信任管理器的这个方法。
		// JSSE中，默认的信任管理器类为TrustManager。
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		// 该方法检查服务器的证书，若不信任该证书同样抛出异常。通过自己实现该方法，可以使之信任我们指定的任何证书。在实现该方法时，也可以简单的不做任何处理，即一个空的函数体，由于不会抛出异常，它就会信任任何证书。
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		// 返回受信任的X509证书数组。
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * post方式请求服务器(https协议)
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            参数
	 * @param charset
	 *            编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public static byte[] post(String url, String content, String charset)
			throws NoSuchAlgorithmException, KeyManagementException, IOException {
		/*
		 * 类HttpsURLConnection似乎并没有提供方法设置信任管理器。其实，
		 * HttpsURLConnection通过SSLSocket来建立与HTTPS的安全连接，
		 * SSLSocket对象是由SSLSocketFactory生成的。
		 * HttpsURLConnection提供了方法setSSLSocketFactory(SSLSocketFactory)
		 * 设置它使用的SSLSocketFactory对象。
		 * SSLSocketFactory通过SSLContext对象来获得，在初始化SSLContext对象时，可指定信任管理器对象。
		 */
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());

		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.setDoOutput(true);
		// 设置请求头
		conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes(charset));
		// 刷新、关闭
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			return outStream.toByteArray();
		}
		return null;
	}

}
