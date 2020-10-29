/**
 *  www.meditrusthealth.com Copyright © MediTrust Health 2017
 */
package com.xsqwe.admin.utils;

import javax.net.ssl.X509TrustManager;
import java.math.BigInteger;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * <p>
 * </p >
 *
 * @author yongfei.xie
 * @date 2017年12月8日 上午10:26:02
 * @version 1.0.0
 */
public class MyX509TrustManager implements X509TrustManager {

	public MyX509TrustManager() {
	}

	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		if (null != chain) {
			for (int k = 0; k < chain.length; ++k) {
				X509Certificate cer = chain[k];
				this.print(cer);
			}
		}

		System.out.println("check client trusted. authType=" + authType);
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		if (null != chain) {
			for (int k = 0; k < chain.length; ++k) {
				X509Certificate cer = chain[k];
				this.print(cer);
			}
		}

		System.out.println("check servlet trusted. authType=" + authType);
	}

	public X509Certificate[] getAcceptedIssuers() {
		System.out.println("get acceptedissuer");
		return null;
	}

	private void print(X509Certificate cer) {
		int version = cer.getVersion();
		String sinname = cer.getSigAlgName();
		String type = cer.getType();
		String algorname = cer.getPublicKey().getAlgorithm();
		BigInteger serialnum = cer.getSerialNumber();
		Principal principal = cer.getIssuerDN();
		String principalname = principal.getName();
		System.out.println("version=" + version + ", sinname=" + sinname + ", type=" + type + ", algorname=" + algorname
				+ ", serialnum=" + serialnum + ", principalname=" + principalname);
	}
}
