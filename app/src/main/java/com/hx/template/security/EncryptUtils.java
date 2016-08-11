package com.hx.template.security;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES对称加解密工具
 * Created by huangx on 2016/7/15.
 */
public class EncryptUtils {
    private static final String TAG = "EncryptUtils";

    /**
     * 默认AES CBC 加解密(使用随机算法生成密钥)
     *
     * @param mode       加密、解密
     * @param encryptPwd 密码
     * @param iv         偏移量
     * @param data       加解密数据
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] defaultAESCBCEncryptDecrypt(int mode, String encryptPwd, String iv, byte[] data) throws NoSuchProviderException, NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] seed = encryptPwd.getBytes("UTF-8");
        byte[] rawKey = getRawKey(seed);
        SecretKeySpec secretKeySpec = getSecretKeySpec(rawKey, "AES");
        Cipher cipher = getCipher("AES", "CBC", "PKCS5Padding");
        int blockCount = cipher.getBlockSize();
        byte[] ivData = iv.getBytes("UTF-8");
        if (ivData.length < blockCount) {
            throw new IllegalArgumentException("iv length must more than " + blockCount);
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivData, 0, blockCount);
        cipher.init(mode, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(data);
    }

    /**
     * 简单AES加解密 (不使用随机算法生成密钥)
     *
     * @param mode
     * @param encryptPwd
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] simpleAESECBEncryptDecrypt(int mode, String encryptPwd, byte[] data) throws UnsupportedEncodingException, NoSuchProviderException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] seed = encryptPwd.getBytes("UTF-8");
        byte[] rawKey = seed;
        SecretKeySpec secretKeySpec = getSecretKeySpec(rawKey, "AES");
        Cipher cipher = getCipher("AES", null, null);
        cipher.init(mode, secretKeySpec);
        return cipher.doFinal(data);
    }


    /**
     * 默认AES ECB 加解密(使用随机算法生成密钥)
     *
     * @param mode       加密、解密
     * @param encryptPwd 密码
     * @param data       加解密数据
     * @return
     * @throws Exception
     */
    public static byte[] defaultAESECBEncryptDecrypt(int mode, String encryptPwd, byte[] data) throws UnsupportedEncodingException, NoSuchProviderException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] seed = encryptPwd.getBytes("UTF-8");
        byte[] rawKey = getRawKey(seed);
        SecretKeySpec secretKeySpec = getSecretKeySpec(rawKey, "AES");
        Cipher cipher = getCipher("AES", null, null);
        cipher.init(mode, secretKeySpec);
        return cipher.doFinal(data);
    }

    /**
     * 使用随机算法生成密钥
     *
     * @param seed
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] getRawKey(byte[] seed) throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyGenerator keyGenerator = getKeyGenerator("AES", null);
        SecureRandom secureRandom = getSecureRandom("SHA1PRNG", null);
        secureRandom.setSeed(seed);
        keyGenerator.init(128, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 实例化一个密钥生成器
     *
     * @param algorithm 加密算法 eg:AES/DES
     * @param provider  包提供程序
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static KeyGenerator getKeyGenerator(String algorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator keyGenerator = null;
        if (TextUtils.isEmpty(provider)) {
            keyGenerator = KeyGenerator.getInstance(algorithm);
        } else {
            keyGenerator = KeyGenerator.getInstance(algorithm, provider);
        }
        return keyGenerator;
    }

    /**
     * 实例化一个强随机数生成器
     *
     * @param algorithm 随机算法 默认SHA1PRNG
     * @param provider  包提供程序 eg:SUN/Crypto
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static SecureRandom getSecureRandom(String algorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom secureRandom = null;
        if (TextUtils.isEmpty(provider)) {
            secureRandom = SecureRandom.getInstance(algorithm);
        } else {
            secureRandom = SecureRandom.getInstance(algorithm, provider);
        }
        return secureRandom;
    }

    /**
     * 实例化一个密钥生成规范
     *
     * @param key       密钥
     * @param algorithm 加密算法 eg:AES
     * @return
     */
    public static SecretKeySpec getSecretKeySpec(byte[] key, String algorithm) {
        SecretKeySpec secretKeySpec = null;
        secretKeySpec = new SecretKeySpec(key, algorithm);
        return secretKeySpec;
    }

    /**
     * 实例化一个密码器，
     *
     * @param algorithm 加密算法 eg:AES
     * @param mode      加密模式 eg:ECB/CBC
     * @param padding   填充方式 eg:pkcs5padding/pkcs7padding
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    public static Cipher getCipher(String algorithm, String mode, String padding) throws NoSuchPaddingException, NoSuchAlgorithmException {
        String transformation = algorithm;
        if (!TextUtils.isEmpty(mode) && !TextUtils.isEmpty(padding)) {
            transformation = algorithm + "/" + mode + "/" + padding;
        }
        return Cipher.getInstance(transformation);
    }
}
