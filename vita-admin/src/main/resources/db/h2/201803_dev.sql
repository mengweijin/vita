--liquibase formatted sql
--changeset admin:3 splitStatements:true context:dev

-- 开发环境：登录时不启用验证码
update VT_CONFIG set VAL='false' where CODE='vt_login_captcha_enabled';

-- 开发环境：改为一天未修改密码则发系统提醒消息
update VT_CONFIG set VAL='1' where CODE='vt_user_password_change_interval';

-- 开发环境：每秒执行一次清理系统日志的任务
update VT_SCHEDULING_TASK set CRON='0 * * * * ?', ARGS='{"days": 1}' where ID=1 AND BEAN_NAME='systemLogCleanTask';
