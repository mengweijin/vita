package com.github.mengweijin.vita.framework.otp;

import com.github.mengweijin.vita.framework.constant.Const;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.codec.binary.Base32;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.crypto.digest.otp.TOTP;
import org.dromara.hutool.swing.qrcode.QrCodeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

/**
 * @author mengweijin
 * @since 2023/4/16
 */
@Slf4j
class TOTPUtilsTest {
    /**
     * 这里仅作测试。生产环境可以取用户登录名。或者系统名称+用户登录名。
     *
     * account 在 Microsoft Authenticator 软件中，表示用户账户名称，仅作标识。
     * StrUtil.format("otpauth://totp/{}?secret={}", account, secretKey);
     * 比如：admin，也可以随意定义，比如加上系统名称：vita_admin
     */
    private static final String LABEL = "admin";
    private static final String ISSUER = "vita";

    /**
     * 这里仅作测试，使用下面代码生产随机的 secretKey。
     * String secretKey = TOTP.generateSecretKey(16);
     * 生产环境：用户的共享密钥，每个用户一个单独的密钥。（生成后保存到数据库SYS_USER.otp_shared_key）
     */
    private static final String SECRET_KEY = "RI2XNPH3CKSFIYK7VIQ4YEGRGE======";

    private static final TOTP instance = new TOTP(Duration.ofSeconds(30), Base32.decode(SECRET_KEY));

    /**
     * 0. 每个用户生成一个随机密钥，保存到数据库，后面用这个密钥。
     * generateSecretKey();
     * <p>
     * 1. 生成客户端二维码（项目路径下/target/qrcode.png）
     * generateQRCode();
     * <p>
     * 2.1. 手机 Microsoft Authenticator 软件中，扫描上面的二维码，查看 6 位数字验证码。
     * 2.2. 验证上面获取到的6位数字验证码，输入到控制台，回车。输出 true 即为验证通过，false 表示验证不通过。
     * verificationCode();
     * <p>
     * 3.1 上面的 verificationCode(); 方法已经可以验证了。如果想自己验证，可以自己在服务端计算当前的验证码来判断：
     * 3.2 控制台每10秒打印一次验证码，用这个和手机 Microsoft Authenticator 软件中获取的验证码比对，看是否一致。
     * calculateVerificationCode();
     */
    @Test
    void mainTest() {
        String qrCodePath = generateQRCode();
        log.debug("二维码路径：{}", qrCodePath);

        boolean validated = verificationCode();
        Assertions.assertTrue(validated);
    }

    @Test
    void generateSecretKey() {
        String secretKey = TOTP.generateSecretKey(16);
        log.debug("SecretKey: {}", secretKey);
        Assertions.assertNotNull(secretKey);
    }

    /**
     * 响应给客户端的二维码
     */
    String generateQRCode() {
        String qrCodeContent = StrUtil.format("otpauth://totp/{}?secret={}&issuer={}", LABEL, SECRET_KEY, ISSUER);
        String filePath = Const.PROJECT_DIR + "target/qrcode.png";
        QrCodeUtil.generate(qrCodeContent, 300, 300, FileUtil.file(filePath));
        return filePath;
    }

    @Test
    void calculateVerificationCode() {
        // 服务端计算出来的一次性密码（后台对比用）
        int generate = instance.generate(Instant.now());
        log.debug("一次性密码（后台对比用）: {}", generate);
        Assertions.assertTrue(generate > 0);
    }

    boolean verificationCode() {
        Scanner sc = new Scanner(System.in);
        int code = sc.nextInt();
        boolean validated = instance.validate(Instant.now(), 0, code);
        sc.close();
        return validated;
    }
}