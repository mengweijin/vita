package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.io.file.FileUtil;
import cn.hutool.v7.swing.captcha.AbstractCaptcha;
import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.generator.MathGenerator;
import com.github.mengweijin.vita.framework.constant.Const;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

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
        captcha.setGenerator(new MathGenerator(1, false));
        captcha.createCode();

        String path = Const.PROJECT_DIR + "target/captcha.png";
        captcha.write(path);

        File file = FileUtil.file(path);
        Assertions.assertTrue(file.exists());
    }
}