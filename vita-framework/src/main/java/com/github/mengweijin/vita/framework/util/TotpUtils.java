package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.codec.binary.Base32;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.crypto.digest.otp.TOTP;
import cn.hutool.v7.swing.img.ImgUtil;
import cn.hutool.v7.swing.qrcode.QrCodeUtil;
import cn.hutool.v7.swing.qrcode.QrConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

/**
 * 手机客户端可以使用：Microsoft Authenticator
 * @author mengweijin
 * @since 2023/4/16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TotpUtils {

    private static final QrConfig QR_CONFIG = new QrConfig();

    public static String generateSecretKey() {
        return TOTP.generateSecretKey(16);
    }

    /**
     *
     * @param secretKey 共享密钥。由 generateSecretKey() 生成共享密钥的Base32表示形式。
     * @param label 可以填写用户的名字、或登录名。可以在客户端可以清楚的标识用户信息。
     * @param issuer 代表应用名称，系统名称、代号等，比如 Google。
     * @return 图片 Base64 编码字符串
     */
    public static String generateQrCode(String secretKey, String label, String issuer) {
        String qrCodeContent = StrUtil.format("otpauth://totp/{}?secret={}&issuer={}", label, secretKey, issuer);
        return QrCodeUtil.generateAsBase64DataUri(qrCodeContent, QR_CONFIG, ImgUtil.IMAGE_TYPE_JPG);
    }

    public static boolean validate(String secretKey, int code) {
        TOTP instance = new TOTP(Duration.ofSeconds(30), Base32.decode(secretKey));
        return instance.validate(Instant.now(), 0, code);
    }

}
