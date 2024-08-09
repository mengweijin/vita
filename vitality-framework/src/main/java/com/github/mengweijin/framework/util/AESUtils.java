package com.github.mengweijin.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.crypto.KeyUtil;
import org.dromara.hutool.crypto.SecureUtil;
import org.dromara.hutool.crypto.symmetric.AES;
import org.dromara.hutool.crypto.symmetric.SymmetricAlgorithm;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author mengweijin
 * @since 2021/12/24
 */
@Slf4j
public class AESUtils {

    private static final AES INSTANCE = new AES();

    public static String encrypt(String value) {
        return INSTANCE.encryptBase64(value);
    }

    public static String decrypt(String value) {
        return INSTANCE.decryptStr(value);
    }

    public static String encryptByKey(String key, String value) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        return aes.encryptBase64(value);
    }

    public static String decryptByKey(String key, String value) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        return aes.decryptStr(value);
    }

    public static byte[] encryptByKey(String key, InputStream inputStream) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        return aes.encrypt(inputStream);
    }

    public static byte[] decryptByKey(String key, InputStream inputStream) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        return aes.decrypt(inputStream);
    }

    public static byte[] encryptByKey(String key, byte[] data) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        return aes.encrypt(data);
    }

    public static byte[] decryptByKey(String key, byte[] data) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        return aes.decrypt(data);
    }

    public static void encryptByKey(String key, FileInputStream in, FileOutputStream out) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        aes.encrypt(in, out, true);
    }

    public static void decryptByKey(String key, FileInputStream in, FileOutputStream out) {
        byte[] secretKey = generateSecretKey(key, 128);
        AES aes = SecureUtil.aes(secretKey);
        aes.decrypt(in, out, true);
    }

    /**
     * 注意：不要使用下面的方式：
     * keygen.init(128, new SecureRandom(encodeRules.getBytes()));
     *
     * 这可能出现 javax.crypto.BadPaddingException: Given final block not properly padded
     * 原因：SecureRandom 实现完全随操作系统本身的內部状态，除非调用方在调用 getInstance 方法，然后调用 setSeed 方法；
     *      该实现在 windows 上每次生成的 key 都相同，但是在 solaris 或部分 linux 系统上则不同。
     *
     * 解决办法：调用getInstance方法
     * SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG") ;
     * secureRandom.setSeed(key.getBytes(StandardCharsets.UTF_8));
     */
    public static byte[] generateSecretKey(String key, int keySize) {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes(StandardCharsets.UTF_8));

            SecretKey secretKey = KeyUtil.generateKey(SymmetricAlgorithm.AES.getValue(), keySize, secureRandom);
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandomKey() {
        return IdUtil.fastSimpleUUID().substring(0, 16);
    }

    /**
     * 使用 generateSecretKey 方法来代替。这里仅做原生的写法展示。
     */
    @Deprecated
    private static byte[] generateSecretKeyByKeyGenerator(String key, int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(SymmetricAlgorithm.AES.getValue());
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG") ;
        secureRandom.setSeed(key.getBytes(StandardCharsets.UTF_8));
        keyGenerator.init(keySize, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }
}
