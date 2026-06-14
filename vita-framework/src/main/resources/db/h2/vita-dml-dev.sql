--liquibase formatted sql
--changeset admin:9 splitStatements:true context:dev

-- 开发环境：登录时不启用验证码
update VT_CONFIG set CONFIG_VALUE='false' where CONFIG_KEY='vita.login-captcha-enabled';

-- 开发环境：改为一天未修改密码则发系统提醒消息
update VT_CONFIG set CONFIG_VALUE='1' where CONFIG_KEY='vita.user.password-change-interval';

-- 开发环境：每 5 分钟执行一次清理系统日志的任务
update VT_SCHEDULING_TASK set CRON='0 0/10 * * * ?', ARGS='{"days": 1}' where ID=1 AND BEAN_NAME='systemLogCleanTask';
