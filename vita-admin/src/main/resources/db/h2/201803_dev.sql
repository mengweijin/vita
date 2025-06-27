--liquibase formatted sql
--changeset admin:3 splitStatements:true context:dev

update VT_CONFIG set VAL='false' where CODE='vt_login_captcha_enabled';

-- 30s 执行一次
update VT_SCHEDULING_TASK set CRON='0/30 * * * * ?', ARGS='{"logRetainedDays": 1}' where ID=1 AND BEAN_NAME='systemLogCleanTask';
