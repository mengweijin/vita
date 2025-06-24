--liquibase formatted sql
--changeset admin:3 splitStatements:true context:dev

update VT_CONFIG set VAL='false' where CODE='vt_login_captcha_enabled';
