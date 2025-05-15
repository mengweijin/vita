package com.github.mengweijin.vita.framework.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.io.IoUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author mengweijin
 */
@Slf4j
class EncryptUtilsTest {

    private static final String FILE_PATH = "D:\\Download\\Oracle19c.zip";
    private static final String FILE_PATH_ENCRYPTED = "D:\\Download\\Oracle19c_encrypted.zip";
    private static final String FILE_PATH_DECRYPTED = "D:\\Download\\Oracle19c_decrypted.zip";

    private static final File FILE = FileUtil.file(FILE_PATH);

    private static final String FiLE_SIZE = FileUtil.readableFileSize(FILE);

    private static final String MD5 = "1858bd0d281c60f4ddabd87b1c214a4f";

    @Test
    @SneakyThrows
    void encrypt() {
        log.debug("File size = {}", FiLE_SIZE);
        long start = System.currentTimeMillis();

        byte[] encrypt = AESUtils.getAES().encrypt(IoUtil.toStream(FILE));

        long end = System.currentTimeMillis();
        log.info("加密执行时长：{} 毫秒", (end - start));
        log.info("加密执行时长：{} 秒", (end - start) / 1000);

        Assertions.assertNotNull(encrypt);
    }

    @Test
    @SneakyThrows
    void encryptBigFile() {
        File encryptedFile = FileUtil.file(FILE_PATH_ENCRYPTED);
        FileUtil.del(encryptedFile);

        long start = System.currentTimeMillis();

        byte[] encrypt = AESUtils.getAES().encrypt(IoUtil.toStream(FILE));

        long end = System.currentTimeMillis();
        log.info("加密大文件执行时长：{} 毫秒", (end - start));
        log.info("加密大文件执行时长：{} 秒", (end - start) / 1000);

        String md5 = SecureUtil.md5(FileUtil.file(FILE_PATH_ENCRYPTED));
        log.info("MD5 = {}", md5);
        Assertions.assertNotNull(encrypt);
    }

    @Test
    @SneakyThrows
    void decryptBigFile() {
        File decryptedFile = FileUtil.file(FILE_PATH_DECRYPTED);
        FileUtil.del(decryptedFile);

        long start = System.currentTimeMillis();

        byte[] decrypt = AESUtils.getAES().decrypt(IoUtil.toStream(FileUtil.file(FILE_PATH_ENCRYPTED)));

        long end = System.currentTimeMillis();
        log.info("解密执行时长：{} 毫秒", (end - start));
        log.info("解密执行时长：{} 秒", (end - start) / 1000);

        Assertions.assertNotNull(decrypt);

        String md5 = SecureUtil.md5(FileUtil.file(FILE_PATH_DECRYPTED));
        Assertions.assertEquals(MD5, md5);
    }

    @Test
    void md5() {
        long start = System.currentTimeMillis();

        String md5 = SecureUtil.md5(FileUtil.file(FILE));

        long end = System.currentTimeMillis();
        log.info("MD5计算执行时长：{} 毫秒", (end - start));
        log.info("MD5计算执行时长：{} 秒", (end - start) / 1000);

        Assertions.assertEquals(MD5, md5);
    }
}