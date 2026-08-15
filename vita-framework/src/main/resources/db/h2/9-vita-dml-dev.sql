--liquibase formatted sql
--changeset admin:9 splitStatements:true context:dev

-- 开发环境：登录时不启用验证码
update VT_CONFIG set CONFIG_VALUE='false' where CONFIG_KEY='vita.login-captcha-enabled';
