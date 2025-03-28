--liquibase formatted sql
--changeset admin:3 splitStatements:true context:dev

update VTL_MENU set IFRAME_SRC='https://vue.aday.fun/doc.html' where ID=10081002 and TITLE='接口文档';
