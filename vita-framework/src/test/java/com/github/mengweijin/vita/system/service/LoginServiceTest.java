package com.github.mengweijin.vita.system.service;

import com.github.mengweijin.vita.framework.constant.Const;
import org.dromara.hutool.swing.captcha.AbstractCaptcha;
import org.dromara.hutool.swing.captcha.CaptchaUtil;
import org.dromara.hutool.swing.captcha.generator.MathGenerator;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 * @since 2025/4/26
 */
class LoginServiceTest {

    @Test
    void getCaptcha() {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        AbstractCaptcha captcha = CaptchaUtil.ofLineCaptcha(140, 40, 4, 100);
        // 自定义验证码内容为四则运算方式，每个数字的长度为 1 位
        captcha.setGenerator(new MathGenerator(1));
        captcha.createCode();

        String path = Const.PROJECT_DIR + "target/captcha.png";
        captcha.write(path);
    }
}