--liquibase formatted sql
--changeset admin:202304 splitStatements:true context:dev

update VT_CONFIG set VAL='false' where CODE='vt_captcha_enabled';

update VT_MENU set REDIRECT='http://localhost:8080/doc.html' where ID=10081002 and TITLE='接口文档';
