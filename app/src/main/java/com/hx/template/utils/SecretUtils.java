package com.hx.template.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecretUtils {
    /*
     * 基于口令的对称加密
     */
    public static byte[] secretEncrypt(String keyword, byte[] data) {

        // 将要加密的数据传递进去，返回加密后的数据
        byte[] results = null;
        try {
            // 实例化工具
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            // 使用该工具将基于密码的形式生成Key
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
                    .generateSecret(new PBEKeySpec(keyword.toCharArray()));
            PBEParameterSpec parameterspec = new PBEParameterSpec(new byte[]{
                    1, 2, 3, 4, 5, 6, 7, 8}, 1000);
            // 初始化加密操作，同时传递加密的算法
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterspec);
            // 先对数据进行base64加密
            byte[] base64Data = Base64.encode(data, Base64.DEFAULT);
            results = cipher.doFinal(base64Data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /*
     * 基于口令的对称解密
     */
    public static byte[] secretDecrypt(String keyword, byte[] base64Data) {
        byte[] result = null;
        try {
            // 实例化工具
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            // 使用该工具将基于密码的形式生成Key
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
                    .generateSecret(new PBEKeySpec(keyword.toCharArray()));
            PBEParameterSpec parameterspec = new PBEParameterSpec(new byte[]{
                    1, 2, 3, 4, 5, 6, 7, 8}, 1000);
            // 初始化解密操作，同时传递解密的算法
            cipher.init(Cipher.DECRYPT_MODE, key, parameterspec);
            result = cipher.doFinal(base64Data);
            // 对数据进行base64解密
            result = Base64.decode(result, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 加密
     *
     * @param seed
     * @param cleartext
     * @return
     * @throws Exception
     */
    public static String encrypt(String seed, String cleartext)
            throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes("UTF-8"));
        byte[] result = encrypt(rawKey, cleartext.getBytes("UTF-8"));
        return toHex(result);
    }

    /**
     * 解密
     *
     * @param seed
     * @param encrypted
     * @return
     * @throws Exception
     */
    public static String decrypt(String seed, String encrypted)
            throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes("UTF-8"));
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result, "UTF-8");
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        return skey.getEncoded();
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/ZeroBytePadding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/ZeroBytePadding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encrypted);
    }

    public static String toHex(String txt) throws UnsupportedEncodingException {
        return toHex(txt.getBytes("UTF-8"));
    }

    public static String fromHex(String hex) throws UnsupportedEncodingException {
        return new String(toByte(hex), "UTF-8");
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        }
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
