--liquibase formatted sql
--changeset admin:202303 splitStatements:true

-- 用户
insert into VT_USER (ID,DEPT_ID,USERNAME,NICKNAME,PASSWORD,SALT,PASSWORD_LEVEL,PASSWORD_CHANGE_TIME,ID_CARD,GENDER,EMAIL,MOBILE,TOTP,DISABLED,DELETED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1,1,'admin','管理员','eb6e8f8eb569eba878f1ac8c19082c3a663bce3ab300e0210b00d205292a1cc1','89D92F225B2218F589D92F225B2218F5','EASY',current_timestamp(),null,'female','mwjwork@qq.com','18700000000',null,'N','N',null,1,current_timestamp(),1,current_timestamp());
insert into VT_USER (ID,DEPT_ID,USERNAME,NICKNAME,PASSWORD,SALT,PASSWORD_LEVEL,PASSWORD_CHANGE_TIME,ID_CARD,GENDER,EMAIL,MOBILE,TOTP,DISABLED,DELETED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (2,1004,'vita','微塔','37918f4f819a45d0aadf0a858c3530f29f1b18c713e80295d0839db0c6099c17','89D92F225B2218F589D92F225B2218F5','EASY',current_timestamp(),null,'male','mwjwork@qq.com','18700000000',null,'N','N',null,1,current_timestamp(),1,current_timestamp());
insert into VT_USER (ID,DEPT_ID,USERNAME,NICKNAME,PASSWORD,SALT,PASSWORD_LEVEL,PASSWORD_CHANGE_TIME,ID_CARD,GENDER,EMAIL,MOBILE,TOTP,DISABLED,DELETED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (3,1005,'guest','游客','846f92f8290746a36515875ed334889cf70498a70fd90e12e8ebe302ce73828e','89D92F225B2218F589D92F225B2218F5','EASY',current_timestamp(),null,'male','mwjwork@qq.com','18700000000',null,'N','N',null,1,current_timestamp(),1,current_timestamp());

-- 角色
insert into VT_ROLE (ID, NAME, CODE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1, '管理员', 'admin', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_ROLE (ID, NAME, CODE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (2, '普通用户', 'common', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_ROLE (ID, NAME, CODE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (3, '游客', 'guest', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());

-- 部门
insert into VT_DEPT (ID, PARENT_ID, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1,           0,        'Vita（微塔）科技',   1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1001,     1,     '财务部',     1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1002,     1,     '研发部',   2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1003,     1,     '销售部',     3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1004,     1,     'IT 运维部',   4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1005,     1,     '游击队',   5, 'N', null, 1, current_timestamp(), 1, current_timestamp());

-- 分类
INSERT INTO VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (1,      0,   'vt_address',                 '地址区域分类','地址区域分类',1,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP());
INSERT INTO VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (1001,   1,   'vt_address_shaanxi',         '陕西省',      NULL,         1,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP());
INSERT INTO VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (1001001,1001,'vt_address_shaanxi_xian',    '西安市',      NULL,         1,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP());
INSERT INTO VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (1001002,1001,'vt_address_shaanxi_xianyang','咸阳市',      NULL,         2,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP());
INSERT INTO VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (1001003,1001,'vt_address_shaanxi_hanzhong','汉中市',      NULL,         3,'Y',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP());
INSERT INTO VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (1002,1,      'vt_address_sichuan',         '四川省',      NULL,         2,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP());
INSERT INTO VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (1002001,1002,'vt_address_sichuan_chengdou','成都市',      NULL,         1,'N',1,CURRENT_TIMESTAMP(),1,CURRENT_TIMESTAMP());

-- 岗位
insert into VT_POST (ID, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1, '董事长', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (2, '人力资源', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (3, '开发工程师', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());

-- 字典类型
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1, '停用/启用', 'vt_disabled', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (2, '是/否', 'vt_yes_no', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (3, '菜单类型', 'vt_menu_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (4, '用户性别', 'vt_user_gender', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (5, '成功/失败', 'vt_succeeded', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (6, '登录类型', 'vt_login_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (7, '密码强度', 'vt_password_level', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (8, '操作日志类型', 'vt_operation_log_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (9, 'HTTP请求类型', 'vt_http_request_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10, '日志级别', 'vt_log_level', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (11, '已发布/未发布', 'vt_released', null, 1, current_timestamp(), 1, current_timestamp());

-- 字典：停用/启用
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10001, 'vt_disabled', 'N', '启用', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10002, 'vt_disabled', 'Y', '停用', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：是/否
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (20001, 'vt_yes_no', 'Y', '是', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (20002, 'vt_yes_no', 'N', '否', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：成功/失败
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (30001, 'vt_succeeded', 'Y', '成功', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (30002, 'vt_succeeded', 'N', '失败', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：用户性别
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (40001, 'vt_user_gender', 'male', '男', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (40002, 'vt_user_gender', 'female', '女', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：登录类型
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (50001, 'vt_login_type', 'LOGIN', '登入', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (50002, 'vt_login_type', 'LOGOUT', '注销', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (50003, 'vt_login_type', 'KICK_OUT', '被踢下线', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (50004, 'vt_login_type', 'REPLACED', '被顶下线', 'warning', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：菜单类型
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60001, 'vt_menu_type', 'DIR', '目录', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60002, 'vt_menu_type', 'MENU', '菜单', 'primary', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60003, 'vt_menu_type', 'BTN', '按钮', 'success', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60004, 'vt_menu_type', 'IFRAME', '内嵌页面', 'warning', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60005, 'vt_menu_type', 'URL', '外链', 'danger', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：密码强度
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70001, 'vt_password_level', 'EASY', '简单', 'danger', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70002, 'vt_password_level', 'MEDIUM', '中', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70003, 'vt_password_level', 'STRONG', '强', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70004, 'vt_password_level', 'VERY_STRONG', '很强', 'primary', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70005, 'vt_password_level', 'EXTREMELY_STRONG', '非常强', 'success', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：操作日志类型
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80001, 'vt_operation_log_type', 'SELECT', '查询', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80002, 'vt_operation_log_type', 'INSERT', '新增', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80003, 'vt_operation_log_type', 'UPDATE', '更新', 'warning', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80004, 'vt_operation_log_type', 'DELETE', '删除', 'danger', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80005, 'vt_operation_log_type', 'IMPORT', '导入', 'success', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80006, 'vt_operation_log_type', 'EXPORT', '导出', 'warning', 6, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80007, 'vt_operation_log_type', 'UPLOAD', '上传', 'success', 7, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80008, 'vt_operation_log_type', 'DOWNLOAD', '下载', 'warning', 8, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80009, 'vt_operation_log_type', 'OTHER', '其它', 'info', 9, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：http 请求方式
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90001, 'vt_http_request_type', 'GET', 'GET', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90002, 'vt_http_request_type', 'POST', 'POST', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90003, 'vt_http_request_type', 'PUT', 'PUT', 'warning', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90004, 'vt_http_request_type', 'DELETE', 'DELETE', 'danger', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90005, 'vt_http_request_type', 'HEAD', 'HEAD', 'info', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90006, 'vt_http_request_type', 'PATCH', 'PATCH', 'info', 6, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90007, 'vt_http_request_type', 'OPTIONS', 'OPTIONS', 'info', 7, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90008, 'vt_http_request_type', 'TRACE', 'TRACE', 'info', 8, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：日志级别
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100001, 'vt_log_level', 'TRACE', 'TRACE', 'info', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100002, 'vt_log_level', 'DEBUG', 'DEBUG', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100003, 'vt_log_level', 'INFO', 'INFO',   'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100004, 'vt_log_level', 'WARN', 'WARN',   'warning', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100005, 'vt_log_level', 'ERROR', 'ERROR', 'danger', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：已发布/未发布
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (110001, 'vt_released', 'Y', '已发布', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG_STYLE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (110002, 'vt_released', 'N', '未发布', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());


-- 配置
insert into VT_CONFIG (ID, NAME, CODE, VAL, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1, '用户登录是否启用验证码', 'vt_captcha_enabled', 'true', '用户登录-验证码启用/停用', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, NAME, CODE, VAL, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (2, '用户初始密码', 'vt_user_init_password', 'aday.fun', '用户管理-初始密码', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, NAME, CODE, VAL, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (3, '修改密码的时间间隔', 'vt_user_password_change_interval', '90', '提醒用户长时间未修改密码的时间间隔。单位：天。0 表示没有启用该限制。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, NAME, CODE, VAL, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (4, '系统管理员角色编码', 'vt_system_admin_role_code', 'admin', '系统管理员角色的角色编码。用以接收系统维护相关消息，告警等', 1, current_timestamp(), 1, current_timestamp());


-- 菜单
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001,0,'MENU','通知公告','system:notice:view','SystemNotice','/system/notice','system/notice/NoticeList.vue',1001,'ep:bell-filled','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001001,1001,'BTN','通知公告-查询','system:notice:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001002,1001,'BTN','通知公告-创建','system:notice:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001003,1001,'BTN','通知公告-更新','system:notice:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001004,1001,'BTN','通知公告-删除','system:notice:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002,0,'MENU','我的消息','system:message:view','SystemMessage','/system/message', 'system/message/MessageList.vue',1002,'ep:chat-dot-round','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002001,1002,'BTN','我的消息-查询','system:message:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002002,1002,'BTN','我的消息-创建','system:message:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002003,1002,'BTN','我的消息-更新','system:message:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002004,1002,'BTN','我的消息-删除','system:message:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011,0,'DIR','系统管理','system:manage:view','System','/system',null,10011,'ri:list-settings-fill','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011001,10011,'MENU','菜单管理','system:menu:view','SystemMenu','/system/menu','system/menu/MenuList.vue',10011001,'ri:menu-2-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011001001,10011001,'BTN','菜单管理-查询','system:menu:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011001002,10011001,'BTN','菜单管理-创建','system:menu:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011001003,10011001,'BTN','菜单管理-更新','system:menu:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011001004,10011001,'BTN','菜单管理-删除','system:menu:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011002,10011,'MENU','部门管理','system:dept:view','SystemDept','/system/dept','system/dept/DeptList.vue',10011002,'ep:office-building','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011002001,10011002,'BTN','部门管理-查询','system:dept:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011002002,10011002,'BTN','部门管理-创建','system:dept:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011002003,10011002,'BTN','部门管理-更新','system:dept:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011002004,10011002,'BTN','部门管理-删除','system:dept:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011003,10011,'MENU','岗位管理','system:post:view','SystemPost','/system/post','system/post/PostList.vue',10011003,'ep:avatar','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011003001,10011003,'BTN','岗位管理-查询','system:post:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011003002,10011003,'BTN','岗位管理-创建','system:post:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011003003,10011003,'BTN','岗位管理-更新','system:post:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011003004,10011003,'BTN','岗位管理-删除','system:post:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004,10011,'MENU','用户管理','system:user:view','SystemUser','/system/user','system/user/UserList.vue',10011004,'ep:user-filled','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004001,10011004,'BTN','用户管理-查询','system:user:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004002,10011004,'BTN','用户管理-创建','system:user:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004003,10011004,'BTN','用户管理-更新','system:user:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004004,10011004,'BTN','用户管理-删除','system:user:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005,10011,'MENU','角色管理','system:role:view','SystemRole','/system/role','system/role/RoleList.vue',10011005,'ri:group-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005001,10011005,'BTN','角色管理-查询','system:role:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005002,10011005,'BTN','角色管理-创建','system:role:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005003,10011005,'BTN','角色管理-更新','system:role:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005004,10011005,'BTN','角色管理-删除','system:role:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011006,10011,'MENU','分类管理','system:category:view','SystemCategory','/system/category','system/category/CategoryList.vue',10011006,'ri:node-tree','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011006001,10011006,'BTN','分类管理-查询','system:category:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011006002,10011006,'BTN','分类管理-创建','system:category:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011006003,10011006,'BTN','分类管理-更新','system:category:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011006004,10011006,'BTN','分类管理-删除','system:category:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007,10011,'MENU','字典管理','system:dict:view','SystemDict','/system/dict','system/dict/DictList.vue',10011007,'ep:notebook','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007001,10011007,'BTN','字典类型-查询','system:dictType:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007002,10011007,'BTN','字典类型-创建','system:dictType:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007003,10011007,'BTN','字典类型-更新','system:dictType:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007004,10011007,'BTN','字典类型-删除','system:dictType:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007005,10011007,'BTN','字典数据-查询','system:dictData:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007006,10011007,'BTN','字典数据-创建','system:dictData:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007007,10011007,'BTN','字典数据-更新','system:dictData:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007008,10011007,'BTN','字典数据-删除','system:dictData:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008,10011,'MENU','配置管理','system:config:view','SystemConfig','/system/config','system/config/ConfigList.vue',10011008,'ep:set-up','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008001,10011008,'BTN','配置管理-查询','system:config:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008002,10011008,'BTN','配置管理-创建','system:config:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008003,10011008,'BTN','配置管理-更新','system:config:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008004,10011008,'BTN','配置管理-删除','system:config:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009,10011,'MENU','文件管理','system:oss:view','SystemOss','/system/oss','system/oss/OssList.vue',10011009,'ep:folder-opened','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009001,10011009,'BTN','文件管理-查询','system:oss:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009002,10011009,'BTN','文件管理-创建','system:oss:create',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009003,10011009,'BTN','文件管理-更新','system:oss:update',null,null,null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009004,10011009,'BTN','文件管理-删除','system:oss:delete',null,null,null,4,null,'N',1,current_timestamp(),1,current_timestamp());



insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021,0,'DIR','系统监控','system:monitor:view','Monitor','/monitor',null,10021,'ep:monitor','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001,10021,'MENU','应用监控','system:application:query','MonitorApplication','/monitor/application','monitor/application/MonitorApplication.vue',10021001,'ep:data-line','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021002,10021,'MENU','缓存监控','system:cache:query','MonitorCache','/monitor/cache','monitor/cache/MonitorCache.vue',10021002,'ep:data-analysis','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021003,10021,'MENU','在线用户','system:userOnline:view','MonitorUserOnline','/monitor/user-online','monitor/user-online/MonitorUserOnline.vue',10021003,'ri:user-voice-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021003001,10021003,'BTN','在线用户-查询','system:userOnline:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021003004,10021003,'BTN','在线用户-下线','system:userOnline:kickOut',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021004,10021,'MENU','登录日志','system:logLogin:view','MonitorLogLogin','/monitor/log-login','monitor/log-login/MonitorLogLogin.vue',10021004,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021004001,10021004,'BTN','登录日志-查询','system:logLogin:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021004004,10021004,'BTN','登录日志-删除','system:logLogin:delete',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021005,10021,'MENU','操作日志','system:logLogin:view','MonitorLogOperation','/monitor/log-operation','monitor/log-operation/LogOperation.vue',10021005,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021005001,10021005,'BTN','操作日志-查询','system:logOperation:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021005004,10021005,'BTN','操作日志-删除','system:logOperation:delete',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021006,10021,'MENU','系统日志','system:logSystem:view','MonitorLogSystem','/monitor/log-system','monitor/log-system/LogSystem.vue',10021006,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021006001,10021006,'BTN','系统日志-查询','system:logSystem:query',null,null,null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021006004,10021006,'BTN','系统日志-删除','system:logSystem:delete',null,null,null,2,null,'N',1,current_timestamp(),1,current_timestamp());


insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10081,0,'DIR','开发工具','system:tools:view','Tool','/tool',null,10081,'ri:tools-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10081001,10081,'MENU','代码生成器','system:generator:view','ToolGenerator','/tool/generator','tool/generator/ToolGenerator.vue',10081001,'ri:ai-generate-text','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10081002,10081,'IFRAME','接口文档','system:api:view',null,null,'doc.html',10081002,'ep:document','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10091,0,'URL','个人博客','aday:fun:view',null,null,'https://aday.fun',10091,'ep:link','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10092,0,'URL','Vita Admin','aday:fun:view',null,null,'https://vita.aday.fun',10092,'ep:link','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10093,0,'URL','Vitality（Vue 版）','aday:fun:view',null,null,'https://vue.aday.fun',10093,'ep:link','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10094,0,'URL','Vitality（Layui 版）','aday:fun:view',null,null,'https://layui.aday.fun',10094,'ep:link','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10095,0,'URL','GitHub','aday:fun:view',null,null,'https://github.com/mengweijin/vita',10095,'ri:github-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,ROUTE_NAME,ROUTE_PATH,COMPONENT,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10096,0,'URL','Gitee','aday:fun:view',null,null,'https://gitee.com/mengweijin/vita',10096,'simple-icons:gitee','N',1,current_timestamp(),1,current_timestamp());


-- 用户-角色
INSERT INTO VT_USER_ROLE (ID,USER_ID,ROLE_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES
	 (1876458459928420354,3,3,1,current_timestamp(),1,current_timestamp());

-- 角色-权限
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES
	 (1876458392433680385,3,1001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392433680386,3,1002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392496594945,3,10081,1,current_timestamp(),1,current_timestamp()),
	 (1876458392496594946,3,10091,1,current_timestamp(),1,current_timestamp()),
	 (1876458392496594947,3,10092,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703810,3,10093,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703811,3,10094,1,current_timestamp(),1,current_timestamp()),
	 (1876458342563703811,3,10095,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703812,3,1001001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703813,3,1002001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703814,3,10021001,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES
	 (1876458392563703815,3,10021002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703816,3,10081001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703817,3,10081002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703818,3,1001001001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703819,3,1001001002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618369,3,1001001003,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618370,3,1001001004,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618371,3,1002001001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618372,3,1002001002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618373,3,1002001003,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES
	 (1876458392626618374,3,1002001004,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618375,3,10011001001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618376,3,10011001002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618377,3,10011002001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618378,3,10011002002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618379,3,10011003001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618380,3,10011003002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618381,3,10011004001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618382,3,10011004002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618383,3,10011004003,1,current_timestamp(),1,current_timestamp()),
     (1876345392612618381,3,10011004011,1,current_timestamp(),1,current_timestamp()),
	 (1876345392612618382,3,10011004012,1,current_timestamp(),1,current_timestamp()),
	 (1876345392612618383,3,10011004013,1,current_timestamp(),1,current_timestamp()),
	 (1876345392612618384,3,10011004014,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES
	 (1876458392626618384,3,10011005001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727234,3,10011005002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727235,3,10011005003,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727236,3,10011006001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727237,3,10011006002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727238,3,10011007001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727239,3,10011007002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727240,3,10011007005,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727241,3,10011007006,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727242,3,10011008001,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES
	 (1876458392693727243,3,10011008002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727244,3,10011009001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727245,3,10011009002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727246,3,10021003001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727247,3,10021004001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727248,3,10021005001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727249,3,10021006001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727250,3,10011001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727251,3,10011,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727252,3,10011002,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES
	 (1876458392760836097,3,10011003,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836098,3,10011004,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836099,3,10011005,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836100,3,10011006,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836101,3,10011007,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836102,3,10011008,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836103,3,10011009,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836104,3,10021,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836105,3,10021003,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836106,3,10021004,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES
	 (1876458392760836107,3,10021005,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836108,3,10021006,1,current_timestamp(),1,current_timestamp());
