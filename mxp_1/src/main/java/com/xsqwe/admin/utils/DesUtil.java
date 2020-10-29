package com.xsqwe.admin.utils;

/**
 * <p>
 * </p>
 *
 * @author weizhen.cao
 * @version 1.0.0
 * @date 2020/2/20 14:42
 */

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

//import com.axatp.online.service.ITCommunalParmenterService;

public class DesUtil {

    private Key key;

//    public DesUtil(ITCommunalParmenterService comService) {
//        String keyVal = comService.queryUtfInfo("claimOnlineKey");
//        setKey(keyVal);
//    }

    public DesUtil(String keyVal) {
        setKey(keyVal);
    }

    /**
     * 加密String明文输入,String密文输出
     */
    public String encrypt(String strMing) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            byteMing = strMing.getBytes("UTF8");
            byteMi = getEncCode(byteMing);
            strMi = base64en.encode(byteMi);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        } finally {
            base64en = null;
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    /**
     * 解密 以String密文输入,String明文输出
     *
     * @param strMi
     * @return
     */
    public String decrypt(String strMi) {
        BASE64Decoder base64De = new BASE64Decoder();
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            byteMi = base64De.decodeBuffer(strMi);
            byteMing = this.getDesCode(byteMi);
            strMing = new String(byteMing, "UTF8");
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        } finally {
            base64De = null;
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     *
     * @param byteS
     * @return
     */
    private byte[] getEncCode(byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, this.key, SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    private byte[] getDesCode(byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, this.key, SecureRandom.getInstance("SHA1PRNG"));
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 根据参数生成KEY
     */
    private void setKey(String strKey) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            this.key = keyFactory.generateSecret(new DESKeySpec(strKey.getBytes("UTF8")));
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }

    private final static String CHARSET = "utf-8";

    public static String encryptBase64(String seed, String content) {
        try {
            byte[] rawKey = getRawKey(seed.getBytes(CHARSET));
            byte[] result = encrypt(rawKey, content.getBytes(CHARSET));
            return new String(Base64.encodeBase64(result));
        } catch (Exception e) {
            // log.error("AES加密base64编码出错: content={}", content, e);
            throw new RuntimeException(e);
        }
    }

    public static String decryptBase64(String seed, String encrypted) {
        try {
            byte[] rawKey = getRawKey(seed.getBytes(CHARSET));
            byte[] enc = Base64.decodeBase64(encrypted.getBytes());
            byte[] result = decrypt(rawKey, enc);
            return new String(result, CHARSET);
        } catch (Exception e) {
            // log.error("AES解密base64出错: encrypted={}", encrypted, e);
            throw new RuntimeException(e);
        }
    }

    private static byte[] getRawKey(byte[] seed) throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        // 192 and 256 bits may not be available
        kgen.init(128, sr);
        SecretKey skey = kgen.generateKey();
        return skey.getEncoded();
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // AES/CBC/PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // AES/CBC/PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encrypted);
    }

    /**
     * 功能: [CBC加密]<br/>
     * 详细说明：应⽤于获取约定的AES加密参数<br/>
     */
    public static String des3EncodeCBC(String aesKey, String dtKey) {
        byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };
        try {
            byte[] key = Base64.decodeBase64(dtKey.getBytes());
            byte[] data = aesKey.getBytes("UTF-8");
            byte[] str5 = des3EncodeCBC(key, keyiv, data);
            return new String(Base64.encodeBase64(str5));
        } catch (Exception e) {
            // log.error("加密AES参数报错", e);
            throw new RuntimeException(e);
        }
    }

    private static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(1, deskey, ips);
        return cipher.doFinal(data);
    }

}

