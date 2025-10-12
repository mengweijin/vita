--liquibase formatted sql
--changeset admin:202501 splitStatements:true context:aday
update VT_MENU set URL='https://vita.aday.fun/swagger-ui.html' where ID=10081002 and TITLE='接口文档';
