//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.xsqwe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FastCodeUtils {
    private static final Logger log = LoggerFactory.getLogger(FastCodeUtils.class);
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final String DEFAULT_KEY = "1MEhD58VjFeFARU7BIbOYXNGCz5uQNp6";
    private static final String DEFAULT_CRYPTO_ALG = "DESede/ECB/PKCS5Padding";
    private static final char MASK_CHAR = '*';
    private static final Key KEY = loadKey();

    private FastCodeUtils() {
    }

    public static String encrypt(String plain) {
        if (StringUtils.isEmpty(plain)) {
            return plain;
        } else {
            try {
                byte[] in = plain.getBytes(CHARSET);
                Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
                cipher.init(1, KEY);
                byte[] out = cipher.doFinal(in);
                return Base64.encodeBase64String(out);
            } catch (Exception var4) {
                log.error("Encrypt error, plain = {}, mode = {}", plain, var4);
                return null;
            }
        }
    }

    public static String decrypt(String base64CipherText) {
        if (StringUtils.isEmpty(base64CipherText)) {
            return base64CipherText;
        } else {
            byte[] bytes = decrypt2Bytes(base64CipherText);
            return bytes == null ? null : new String(bytes, CHARSET);
        }
    }

    private static byte[] decrypt2Bytes(String base64CipherText) {
        try {
            byte[] in = Base64.decodeBase64(base64CipherText);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(2, KEY);
            return cipher.doFinal(in);
        } catch (Exception var3) {
            log.error("Encrypt error, base64CipherText = {}, mode = {}", base64CipherText, var3);
            return null;
        }
    }

    public static String hash(String plain) {
        if (StringUtils.isEmpty(plain)) {
            return plain;
        } else {
            byte[] bytes = DigestUtils.sha1(plain);
            return Base64.encodeBase64String(bytes);
        }
    }

    public static String decryptAndHash(String base64CipherText) {
        if (StringUtils.isEmpty(base64CipherText)) {
            return base64CipherText;
        } else {
            byte[] plain = decrypt2Bytes(base64CipherText);
            byte[] hash = DigestUtils.sha1(plain);
            return Base64.encodeBase64String(hash);
        }
    }

    public static String decryptAndMaskBankCard(String base64CipherBankCard) {
        if (base64CipherBankCard == null) {
            return null;
        } else {
            String plain = decrypt(base64CipherBankCard);
            return plain == null ? null : maskBankCard(plain);
        }
    }

    public static String maskBankCard(String cardNo) {
        return mask(cardNo, 0, 4);
    }

    public static String decryptAndMaskIdCard(String base64CipherIdCard) {
        if (base64CipherIdCard == null) {
            return null;
        } else {
            String plain = decrypt(base64CipherIdCard);
            return plain == null ? null : maskIdCard(plain);
        }
    }

    public static String maskRealName(String realName) {
        if (realName != null && realName.length() >= 2) {
            if (realName.length() == 2) {
                char[] chs = realName.toCharArray();
                chs[1] = '*';
                return new String(chs);
            } else {
                return mask(realName, 0, 1);
            }
        } else {
            return realName;
        }
    }

    public static String maskIdCard(String idCard) {
        return mask(idCard, 1, 1);
    }

    public static String maskMobile(String mobile) {
        return mask(mobile, 3, 2);
    }

    public static String mask(String str, int before, int after) {
        if (StringUtils.isBlank(str)) {
            return str;
        } else if (str.length() <= before + after) {
            return str;
        } else {
            char[] chs = str.toCharArray();
            int i = before;

            for(int k = str.length() - after; i < k; ++i) {
                chs[i] = '*';
            }

            return new String(chs);
        }
    }

    private static Key loadKey() {
        try {
            String key = loadDefaultKey();
            byte[] bytes = Base64.decodeBase64(key);
            DESedeKeySpec spec = new DESedeKeySpec(bytes);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            return factory.generateSecret(spec);
        } catch (Exception var4) {
            log.error("Load key cause error", var4);
            return null;
        }
    }

    private static String loadDefaultKey() {
        return "1MEhD58VjFeFARU7BIbOYXNGCz5uQNp6";
    }

    protected static String loadResource() throws IOException {
        BufferedReader reader = null;

        String line;
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(FastCodeUtils.class.getName());
            reader = new BufferedReader(new InputStreamReader(in));
            line = null;

            while((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() != 0 && !line.startsWith("#")) {
                    String var3 = line;
                    return var3;
                }
            }

            line = null;
        } finally {
            if (reader != null) {
                reader.close();
            }

        }

        return line;
    }
}
