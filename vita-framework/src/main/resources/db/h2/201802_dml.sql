--liquibase formatted sql
--changeset admin:2 splitStatements:true

-- 用户
insert into VT_USER (ID,DEPT_ID,USERNAME,NICKNAME,PASSWORD,SALT,PASSWORD_LEVEL,PASSWORD_CHANGE_TIME,CITIZEN_ID,GENDER,EMAIL,MOBILE,TOTP,DISABLED,DELETED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1,1,'admin','管理员','$2a$10$aO4vR1PTzmn2Y5UaDt/RWeqqBhUVsUT6iq7vazlBeKNebsNOJtiGm','$2a$10$v/3HEiNTDK8ww2yAMLa3y.','EASY',current_timestamp(),null,'male','aday.fun@outlook.com','18700000000',null,'N','N',null,1,current_timestamp(),1,current_timestamp());
insert into VT_USER (ID,DEPT_ID,USERNAME,NICKNAME,PASSWORD,SALT,PASSWORD_LEVEL,PASSWORD_CHANGE_TIME,CITIZEN_ID,GENDER,EMAIL,MOBILE,TOTP,DISABLED,DELETED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (2,1,'vita','微塔','$2a$10$aO4vR1PTzmn2Y5UaDt/RWeqqBhUVsUT6iq7vazlBeKNebsNOJtiGm','$2a$10$v/3HEiNTDK8ww2yAMLa3y.','EASY',current_timestamp(),null,'female','aday.fun@outlook.com','18700000000',null,'N','N',null,1,current_timestamp(),1,current_timestamp());
insert into VT_USER (ID,DEPT_ID,USERNAME,NICKNAME,PASSWORD,SALT,PASSWORD_LEVEL,PASSWORD_CHANGE_TIME,CITIZEN_ID,GENDER,EMAIL,MOBILE,TOTP,DISABLED,DELETED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (3,1,'guest','游客','$2a$10$aO4vR1PTzmn2Y5UaDt/RWeqqBhUVsUT6iq7vazlBeKNebsNOJtiGm','$2a$10$v/3HEiNTDK8ww2yAMLa3y.','EASY',current_timestamp(),null,'female','aday.fun@outlook.com','18700000000',null,'N','N',null,1,current_timestamp(),1,current_timestamp());

-- 角色
insert into VT_ROLE (ID, NAME, CODE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1, '管理员', 'admin', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_ROLE (ID, NAME, CODE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (2, '普通用户', 'general', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_ROLE (ID, NAME, CODE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (3, '游客', 'guest', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());

-- 部门
insert into VT_DEPT (ID, PARENT_ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1,     null, 'company',      '微塔科技',   1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1001,     1, 'dept_finance',      '财务部',     1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1002,     1, 'dept_development',  '研发部',   2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1003,     1, 'dept_sales',        '销售部',     3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1004,     1, 'dept_hr',           '人力资源部',   4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1005,     1, 'dept_legal',        '法务部',   5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1006,     1, 'dept_compensation', '薪酬管理部',   6, 'N', null, 1, current_timestamp(), 1, current_timestamp());


-- 岗位
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1, 'CEO',           '执行总裁', 1, 'N', 'Chief Executive Officer', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (2, 'GM',            '总经理', 2, 'N', 'General Manager', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (3, 'VP',            '副总裁', 3, 'N', 'Vice President', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (4, 'CFO',           '财务总监', 4, 'N', 'Chief Financial Officer', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (5, 'HRD',           '人力资源总监', 5, 'N', 'Human Resource Director', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (6, 'CTO',           '技术总监', 6, 'N', 'Chief Technology Officer', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (7, 'CCO',           '首席文化官', 7, 'N', 'Chief Cultural Officer', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (8, 'CPO',           '公关总监', 8, 'N', 'Chief Public relation Officer', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (9, 'CQO',           '质量总监', 9, 'N', 'Chief Quality Officer', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10, 'CSO',           '销售总监', 10, 'N', 'Chief Sales Officer', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (11, 'MD',            '市场总监', 11, 'N', 'Marketing Director', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (12, 'OD',            '运营总监', 12, 'N', 'Operations Director', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (13, 'OM',            '运营经理', 13, 'N', 'Operations Manager', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (14, 'Production Manager', '生产经理', 14, 'Y', 'Production Manager', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (15, 'Product Manager', '产品经理', 15, 'N', 'Product Manager', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (16, 'Project Manager', '项目经理', 16, 'N', 'Project Manager', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (17, 'RM',            '大区经理', 17, 'N', 'Regional Manager', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (18, 'DM',            '地区经理', 18, 'N', 'District Manager', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (19, 'BM',            '部门经理', 19, 'N', 'Branch Manager', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (20, 'CM',            '客户经理', 20, 'N', 'Costomer Manager', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (21, 'SA',            '销售助理', 21, 'N', 'Sales Assistant', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (22, 'AVP',           '副总裁助理', 22, 'N', 'Assistant Vice President', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (23, 'Accountant',    '会计', 23, 'N', 'Accountant', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (24, 'Cashier',       '出纳', 24, 'N', 'Cashier', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (25, 'Recruitment Officer',      '招聘专员', 25, 'N', 'Recruitment Officer', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (26, 'Training Coordinator',     '培训专员', 26, 'N', 'Training Coordinator', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (27, 'Administrative Assistant', '行政助理', 27, 'N', 'Administrative Assistant', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (28, 'DE',            '开发工程师', 28, 'N', 'Developmental Engineer', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (29, 'TE',            '测试工程师', 29, 'N', 'Test Engineer', 1, current_timestamp(), 1, current_timestamp());
insert into VT_POST (ID, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (30, 'Production Engineer', '生产工程师', 30, 'Y', 'Production Engineer', 1, current_timestamp(), 1, current_timestamp());


-- 分类
insert into VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1,      null,'vt_address',                 '地址区域分类','地址区域分类',1,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001,      1,'vt_address_shaanxi',         '陕西省',      null,         1,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001001,1001,'vt_address_shaanxi_xian',    '西安市',      null,         1,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001002,1001,'vt_address_shaanxi_xianyang','咸阳市',      null,         2,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001003,1001,'vt_address_shaanxi_hanzhong','汉中市',      null,         3,'Y',1,current_timestamp(),1,current_timestamp());
insert into VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002,      1,'vt_address_sichuan',         '四川省',      null,         2,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_CATEGORY (ID,PARENT_ID,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002001,1002,'vt_address_sichuan_chengdou','成都市',      null,         1,'N',1,current_timestamp(),1,current_timestamp());


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
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10, '系统日志记录级别', 'vt_log_level', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (11, '已发布/未发布', 'vt_released', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (12, '字典标签样式', 'vt_dict_tag_style', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (13, '调度任务执行状态', 'vt_scheduling_task_status', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (14, '系统消息类别', 'vt_message_category', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (15, '已读/未读', 'vt_message_viewed_status', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (16, '表单组件类型', 'vt_ext_prop_form_types', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10001, '数据级别', 'vt_gbt_data_level', '参考：标准号：GB/T 43697-2024。中文标准名称：数据安全技术 数据分类分级规则。说明：GB: 强制性国家标准；GB/T：推荐性国家标准；GB/Z: 指导性技术文件。', 1, current_timestamp(), 1, current_timestamp());

-- 字典：停用/启用
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10001, 'vt_disabled', 'N', '启用', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10002, 'vt_disabled', 'Y', '停用', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：是/否
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (20001, 'vt_yes_no', 'Y', '是', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (20002, 'vt_yes_no', 'N', '否', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：成功/失败
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (30001, 'vt_succeeded', 'Y', '成功', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (30002, 'vt_succeeded', 'N', '失败', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：用户性别
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (40001, 'vt_user_gender', 'male', '男', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (40002, 'vt_user_gender', 'female', '女', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：登录类型
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (50001, 'vt_login_type', 'LOGIN', '登入', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (50002, 'vt_login_type', 'LOGOUT', '注销', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (50003, 'vt_login_type', 'KICK_OUT', '被踢下线', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (50004, 'vt_login_type', 'REPLACED', '被顶下线', 'warning', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：菜单类型
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60001, 'vt_menu_type', 'DIR', '目录', 'info', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60002, 'vt_menu_type', 'MENU', '菜单', 'primary', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60003, 'vt_menu_type', 'BTN', '按钮', 'warning', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60005, 'vt_menu_type', 'URL', '外链', 'danger', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：密码强度
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70001, 'vt_password_level', 'EASY', '简单', 'danger', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70002, 'vt_password_level', 'MEDIUM', '中', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70003, 'vt_password_level', 'STRONG', '强', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70004, 'vt_password_level', 'VERY_STRONG', '很强', 'primary', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70005, 'vt_password_level', 'EXTREMELY_STRONG', '非常强', 'success', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：操作日志类型
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80001, 'vt_operation_log_type', 'SELECT', '查询', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80002, 'vt_operation_log_type', 'INSERT', '新增', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80003, 'vt_operation_log_type', 'UPDATE', '更新', 'warning', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80004, 'vt_operation_log_type', 'REMOVE', '删除', 'danger', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80005, 'vt_operation_log_type', 'IMPORT', '导入', 'success', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80006, 'vt_operation_log_type', 'EXPORT', '导出', 'warning', 6, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80007, 'vt_operation_log_type', 'UPLOAD', '上传', 'success', 7, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80008, 'vt_operation_log_type', 'DOWNLOAD', '下载', 'warning', 8, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80009, 'vt_operation_log_type', 'OFFLINE', '强制下线', 'danger', 9, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80010, 'vt_operation_log_type', 'OTHER', '其它', 'info', 10, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：http 请求方式
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90001, 'vt_http_request_type', 'GET', 'GET', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90002, 'vt_http_request_type', 'POST', 'POST', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90003, 'vt_http_request_type', 'PUT', 'PUT', 'warning', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90004, 'vt_http_request_type', 'DELETE', 'DELETE', 'danger', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90005, 'vt_http_request_type', 'HEAD', 'HEAD', 'info', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90006, 'vt_http_request_type', 'PATCH', 'PATCH', 'info', 6, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90007, 'vt_http_request_type', 'OPTIONS', 'OPTIONS', 'info', 7, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90008, 'vt_http_request_type', 'TRACE', 'TRACE', 'info', 8, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：日志级别
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100001, 'vt_log_level', 'TRACE', 'TRACE', 'info', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100002, 'vt_log_level', 'DEBUG', 'DEBUG', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100003, 'vt_log_level', 'INFO', 'INFO',   'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100004, 'vt_log_level', 'WARN', 'WARN',   'warning', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100005, 'vt_log_level', 'ERROR', 'ERROR', 'danger', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：已发布/未发布
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (110001, 'vt_released', 'Y', '已发布', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (110002, 'vt_released', 'N', '未发布', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：字典标签样式
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (120001, 'vt_dict_tag_style', 'primary', 'primary', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (120002, 'vt_dict_tag_style', 'success', 'success', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (120003, 'vt_dict_tag_style', 'info', 'info', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (120004, 'vt_dict_tag_style', 'warning', 'warning', 'warning', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (120005, 'vt_dict_tag_style', 'danger', 'danger', 'danger', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：调度任务执行状态
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (130001, 'vt_scheduling_task_status', 'not_started', '未开始', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (130002, 'vt_scheduling_task_status', 'running', '执行中', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (130003, 'vt_scheduling_task_status', 'finished', '已完成', 'success', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：系统消息类别
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (140001, 'vt_message_category', 'system', '系统', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (140002, 'vt_message_category', 'security', '安全', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (140003, 'vt_message_category', 'warning', '警告', 'warning', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (140004, 'vt_message_category', 'user', '用户', 'primary', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (140005, 'vt_message_category', 'other', '其它', 'info', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：已发布/未发布
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (150001, 'vt_message_viewed_status', 'Y', '已读', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (150002, 'vt_message_viewed_status', 'N', '未读', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：表单组件类型
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160001, 'vt_ext_prop_form_types', 'input', '输入框', 'info', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160002, 'vt_ext_prop_form_types', 'input-number', '数字输入框', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160003, 'vt_ext_prop_form_types', 'textarea', '文本域', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160004, 'vt_ext_prop_form_types', 'richtext', '富文本', 'info', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160005, 'vt_ext_prop_form_types', 'select', '下拉框', 'info', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160006, 'vt_ext_prop_form_types', 'select-multiple', '多选下拉框', 'info', 6, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160007, 'vt_ext_prop_form_types', 'switch', '开关', 'info', 7, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160008, 'vt_ext_prop_form_types', 'radio', '单选框', 'info', 8, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160009, 'vt_ext_prop_form_types', 'checkbox', '多选框', 'info', 9, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160010, 'vt_ext_prop_form_types', 'year', '年选择器', 'info', 10, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160011, 'vt_ext_prop_form_types', 'month', '月选择器', 'info', 11, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160012, 'vt_ext_prop_form_types', 'date', '日期选择器', 'info', 12, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160013, 'vt_ext_prop_form_types', 'datetime', '日期时间选择器', 'info', 13, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160014, 'vt_ext_prop_form_types', 'week', '周选择器', 'info', 14, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160015, 'vt_ext_prop_form_types', 'file', '文件选择器', 'info', 15, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160016, 'vt_ext_prop_form_types', 'color', '颜色选择器', 'info', 16, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160017, 'vt_ext_prop_form_types', 'icon', '图标选择器', 'info', 17, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160018, 'vt_ext_prop_form_types', 'category', '分类选择器', 'info', 18, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160019, 'vt_ext_prop_form_types', 'dept', '部门选择器', 'info', 19, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160020, 'vt_ext_prop_form_types', 'post', '岗位选择器', 'info', 20, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160021, 'vt_ext_prop_form_types', 'role', '角色选择器', 'info', 21, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160022, 'vt_ext_prop_form_types', 'user', '用户选择器', 'info', 22, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 数据级别
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100010001, 'vt_gbt_data_level', 'core', '核心', 'danger', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100010002, 'vt_gbt_data_level', 'key', '重要', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, CODE, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100010003, 'vt_gbt_data_level', 'general', '一般', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());


-- 配置
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1, 'vita.log-record-level', 'INFO', '系统日志记录到数据库的最低日志级别（不区分大小写）。可选值：[ALL, TRACE, DEBUG, INFO, WARN, ERROR, OFF]。注意：请勿设置过低，否则极度影响性能！', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (2, 'vita.login-captcha-enabled', 'true', '用户登录是否启用验证码。可选值：[ true, false ]。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (3, 'vita.otp-enabled', 'false', '是否启用系统动态口令二次验证。可选值：[ true, false ]。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (4, 'vita.user.default-password', 'aday.fun', '用户初始密码。密码长度应该在 8-18 位之间，并且为数字、字母、符号的至少任意两种的组合。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (5, 'vita.user.password-change-interval', '90', '修改密码的时间间隔。单位：天。若用户 90 天未修改密码，则通过系统消息提醒用户修改密码。0 表示没有启用该限制。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (6, 'vita.user.default-role-code', 'general', '用户默认角色编码。创建用户时，用户所拥有的默认的角色编码，用户拥有该角色，即拥有对应的菜单权限。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (7, 'vita.role-code-for-admin', 'admin', '系统管理员角色编码。用以接收系统维护、告警等相关消息的角色编码。', 1, current_timestamp(), 1, current_timestamp());


-- 菜单
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (100,null,'MENU','首页', 'system:home:view','/home',0,'ant-design:home-filled','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001,null,'MENU','系统公告','system:notice:view','/system/notice',1,'ep:bell-filled','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001001,1001,'BTN','系统公告-查询','system:notice:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001002,1001,'BTN','系统公告-创建','system:notice:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001003,1001,'BTN','系统公告-更新','system:notice:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001004,1001,'BTN','系统公告-删除','system:notice:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001005,1001,'BTN','系统公告-发布','system:notice:release',null,5,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1001006,1001,'BTN','系统公告-撤回','system:notice:revoke',null,6,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002,null,'MENU','消息管理','system:message:view','/system/message',2,'ep:chat-dot-round','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002001,1002,'BTN','消息管理-查询','system:message:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002002,1002,'BTN','消息管理-创建','system:message:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002003,1002,'BTN','消息管理-更新','system:message:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1002004,1002,'BTN','消息管理-删除','system:message:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011,null,'DIR','系统管理','system:manage:view',null,5,'ri:list-settings-fill','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011001,10011,'MENU','菜单管理','system:menu:view','/system/menu',1,'ri:menu-2-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011001001,10011001,'BTN','菜单管理-查询','system:menu:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011001002,10011001,'BTN','菜单管理-创建','system:menu:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011001003,10011001,'BTN','菜单管理-更新','system:menu:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011001004,10011001,'BTN','菜单管理-删除','system:menu:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011002,10011,'MENU','部门管理','system:dept:view','/system/dept',2,'ep:office-building','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011002001,10011002,'BTN','部门管理-查询','system:dept:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011002002,10011002,'BTN','部门管理-创建','system:dept:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011002003,10011002,'BTN','部门管理-更新','system:dept:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011002004,10011002,'BTN','部门管理-删除','system:dept:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011003,10011,'MENU','岗位管理','system:post:view','/system/post',3,'ep:avatar','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011003001,10011003,'BTN','岗位管理-查询','system:post:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011003002,10011003,'BTN','岗位管理-创建','system:post:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011003003,10011003,'BTN','岗位管理-更新','system:post:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011003004,10011003,'BTN','岗位管理-删除','system:post:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004,10011,'MENU','用户管理','system:user:view','/system/user',4,'ep:user-filled','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004001,10011004,'BTN','用户管理-查询','system:user:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004002,10011004,'BTN','用户管理-创建','system:user:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004003,10011004,'BTN','用户管理-更新','system:user:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004004,10011004,'BTN','用户管理-删除','system:user:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004005,10011004,'BTN','用户管理-敏感信息查询','system:user:sensitive',null,5,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004006,10011004,'BTN','用户管理-设置角色','system:user:setRoles',null,6,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004007,10011004,'BTN','用户管理-修改密码','system:user:changePassword',null,7,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004008,10011004,'BTN','用户管理-重置密码','system:user:resetPassword',null,8,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005,10011,'MENU','角色管理','system:role:view','/system/role',5,'ri:group-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005001,10011005,'BTN','角色管理-查询','system:role:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005002,10011005,'BTN','角色管理-创建','system:role:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005003,10011005,'BTN','角色管理-更新','system:role:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005004,10011005,'BTN','角色管理-删除','system:role:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005005,10011005,'BTN','角色管理-分配用户','system:role:assignUser',null,5,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011006,10011,'MENU','分类管理','system:category:view','/system/category',6,'ri:node-tree','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011006001,10011006,'BTN','分类管理-查询','system:category:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011006002,10011006,'BTN','分类管理-创建','system:category:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011006003,10011006,'BTN','分类管理-更新','system:category:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011006004,10011006,'BTN','分类管理-删除','system:category:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007,10011,'MENU','字典管理','system:dict:view','/system/dict',7,'ep:notebook','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007001,10011007,'BTN','字典类型-查询','system:dictType:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007002,10011007,'BTN','字典类型-创建','system:dictType:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007003,10011007,'BTN','字典类型-更新','system:dictType:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007004,10011007,'BTN','字典类型-删除','system:dictType:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007005,10011007,'BTN','字典数据-查询','system:dictData:select',null,11,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007006,10011007,'BTN','字典数据-创建','system:dictData:create',null,12,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007007,10011007,'BTN','字典数据-更新','system:dictData:update',null,13,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011007008,10011007,'BTN','字典数据-删除','system:dictData:remove',null,14,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008,10011,'MENU','配置管理','system:config:view','/system/config',8,'ep:set-up','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008001,10011008,'BTN','配置管理-查询','system:config:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008002,10011008,'BTN','配置管理-创建','system:config:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008003,10011008,'BTN','配置管理-更新','system:config:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008004,10011008,'BTN','配置管理-删除','system:config:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009,10011,'MENU','文件管理','system:file:view','/system/file',9,'ep:folder-opened','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009001,10011009,'BTN','文件管理-查询','system:file:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009002,10011009,'BTN','文件管理-创建','system:file:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009003,10011009,'BTN','文件管理-更新','system:file:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009004,10011009,'BTN','文件管理-删除','system:file:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());



insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021,null,'DIR','系统监控','monitor:system:view',null,21,'ep:monitor','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001,10021,'MENU','调度任务','monitor:schedulingTask:view','/monitor/scheduling',1,'ep:clock','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001001,10021001,'BTN','调度任务-查询','monitor:schedulingTask:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001002,10021001,'BTN','调度任务-创建','monitor:schedulingTask:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001003,10021001,'BTN','调度任务-更新','monitor:schedulingTask:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001004,10021001,'BTN','调度任务-删除','monitor:schedulingTask:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001005,10021001,'BTN','调度任务-执行','monitor:schedulingTask:run',null,5,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (100210010011,10021001,'BTN','调度任务日志-查询','monitor:schedulingTaskLog:select',null,11,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (100210010012,10021001,'BTN','调度任务日志-创建','monitor:schedulingTaskLog:create',null,12,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (100210010013,10021001,'BTN','调度任务日志-更新','monitor:schedulingTaskLog:update',null,13,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (100210010014,10021001,'BTN','调度任务日志-删除','monitor:schedulingTaskLog:remove',null,14,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021002,10021,'MENU','应用监控','monitor:server:view','/monitor/server',2,'ep:data-line','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021003,10021,'MENU','本地缓存','monitor:cache:view','/monitor/cache-local',3,'ep:data-analysis','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021003001,10021003,'BTN','本地缓存-删除','monitor:cache:remove',null,1,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021004,10021,'MENU','在线用户','monitor:userOnline:view','/monitor/user-online',4,'ri:user-voice-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021004001,10021004,'BTN','在线用户-查询','monitor:userOnline:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021004004,10021004,'BTN','在线用户-下线','monitor:userOnline:kickOut',null,2,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021005,10021,'MENU','登录日志','monitor:logLogin:view','/monitor/log-login',5,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021005001,10021005,'BTN','登录日志-查询','monitor:logLogin:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021005004,10021005,'BTN','登录日志-删除','monitor:logLogin:remove',null,2,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021006,10021,'MENU','操作日志','monitor:logLogin:view','/monitor/log-operation',6,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021006001,10021006,'BTN','操作日志-查询','monitor:logOperation:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021006004,10021006,'BTN','操作日志-删除','monitor:logOperation:remove',null,2,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021007,10021,'MENU','系统日志','monitor:logSystem:view','/monitor/log-system',7,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021007001,10021007,'BTN','系统日志-查询','monitor:logSystem:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021007004,10021007,'BTN','系统日志-删除','monitor:logSystem:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021008,10021,'MENU','数据变动日志','monitor:logDataChange:view','/monitor/log-data-change',8,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021008001,10021008,'BTN','数据变动日志-查询','monitor:logDataChange:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021008004,10021008,'BTN','数据变动日志-删除','monitor:logDataChange:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10081,null,'DIR','开发工具','tools:menu:view',null,81,'ri:tools-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10081001,10081,'MENU','代码生成器','tools:generator:view','/tool/generator',1,'ri:ai-generate-text','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10081002,10081,'MENU','接口文档','tools:swagger-ui:view','/tool/swagger-ui',2,'ep:document','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10090,null,'DIR','演示页面','demo:page:view',null,90,'ep:star-filled','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10090001,10090,'MENU','IFRAME 页面','demo:page:view','/demo/iframe',1,'ep:pointer','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10090002,10090,'DIR','错误页面','demo:page:view', null,2,'ri:error-warning-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10090002001,10090002,'MENU','403','demo:page:view', '/error/403',1,'ri:error-warning-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10090002002,10090002,'MENU','404','demo:page:view', '/error/404',1,'ri:error-warning-fill','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10091,null,'URL','个人博客','aday:fun:view','https://aday.fun',91,'ep:link','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10092,null,'URL','Vita Admin','aday:fun:view','https://vita.aday.fun',92,'ep:link','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10093,null,'URL','Vitality（Layui 版）','aday:fun:view','https://layui.aday.fun',94,'ep:link','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10094,null,'URL','GitHub','aday:fun:view','https://github.com/mengweijin/vita',95,'ri:github-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10095,null,'URL','Gitee','aday:fun:view','https://gitee.com/mengweijin/vita',96,'simple-icons:gitee','N',1,current_timestamp(),1,current_timestamp());


-- 调度任务
insert into VT_SCHEDULING_TASK (ID,NAME,CRON,BEAN_NAME,ARGS,DISABLED,EXECUTE_AFTER_STARTED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1,'系统日志清理','0 0 3 1 * ?','systemLogCleanTask','{ "days": 365 }','N','Y','days：系统日志保留时长，单位：天。超过该配置时间的系统日志将被调度任务清理。',1,current_timestamp(),1,current_timestamp());
insert into VT_SCHEDULING_TASK (ID,NAME,CRON,BEAN_NAME,ARGS,DISABLED,EXECUTE_AFTER_STARTED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (2,'临时目录清理','0 10 3 * * ?','multipartLocationCleanTask','{ "hours": 24 }','N','Y','hours：临时文件保留时长，单位：小时。超过该配置时间的临时文件将被调度任务清理。',1,current_timestamp(),1,current_timestamp());
insert into VT_SCHEDULING_TASK (ID,NAME,CRON,BEAN_NAME,ARGS,DISABLED,EXECUTE_AFTER_STARTED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (3,'文件上传存储路径下的空文件夹清理','0 20 3 ? * 1','fileUploadEmptyDirectoryCleanTask',null,'N','Y','文件上传存储路径下的空文件夹清理任务，防止文件删除后，遗留的空文件夹太多。',1,current_timestamp(),1,current_timestamp());


-- 用户-角色
insert into VT_USER_ROLE (ID,USER_ID,ROLE_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1876458459928420354,3,3,1,current_timestamp(),1,current_timestamp());


-- 角色-权限
insert into VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values
	 (1876458392433680385,3,1001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392433680386,3,1002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392496594945,3,10081,1,current_timestamp(),1,current_timestamp()),
	 (1876458392496594946,3,10091,1,current_timestamp(),1,current_timestamp()),
	 (1876458392496594947,3,10092,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703810,3,10093,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703811,3,10094,1,current_timestamp(),1,current_timestamp()),
	 (1876458342563703811,3,10095,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703813,3,1001001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703814,3,1002001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703815,3,10021001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703816,3,10021002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703817,3,10081001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703818,3,10081002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703819,3,1001001001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392563703820,3,1001001002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618369,3,1001001003,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618370,3,1001001004,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618371,3,1002001001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618372,3,1002001002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618373,3,1002001003,1,current_timestamp(),1,current_timestamp()),
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
	 (1876345392612618384,3,10011004014,1,current_timestamp(),1,current_timestamp()),
	 (1876458392626618384,3,10011005001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727234,3,10011005002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727235,3,10011005003,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727236,3,10011006001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727237,3,10011006002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727238,3,10011007001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727239,3,10011007002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727240,3,10011007005,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727241,3,10011007006,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727242,3,10011008001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727243,3,10011008002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727244,3,10011009001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727245,3,10011009002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727246,3,10021003001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727247,3,10021004001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727248,3,10021005001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727249,3,10021006001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727250,3,10011001,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727251,3,10011,1,current_timestamp(),1,current_timestamp()),
	 (1876458392693727252,3,10011002,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836097,3,10011003,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836098,3,10011004,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836099,3,10011005,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836100,3,10011006,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836101,3,10011007,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836102,3,10011008,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836103,3,10011009,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836104,3,10021,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836105,3,10021003,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836106,3,10021004,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836107,3,10021005,1,current_timestamp(),1,current_timestamp()),
	 (1876458392760836108,3,10021006,1,current_timestamp(),1,current_timestamp());


-- 系统公告
INSERT INTO VT_NOTICE (ID,TITLE,DESCRIPTION,RELEASED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (1948579666135564289,'欢迎使用 Vita（微塔）管理系统！','<p style="text-align: left;"><strong>Vita（中文名：微塔）</strong>：是一款<strong>轻量级快速开发平台应用系统</strong>。</p><p style="text-align: left;">基于 SpringBoot 3、sa-token、mybatis-plus、vite、vue 3、element-plus、javascript 等技术，不依赖任何第三方服务。</p><p style="text-align: left;">有时候我们就想做一个简单的东西，采用已有的开源框架却要依赖一大堆东西，和很复杂的配置文件，自己从零搭建又太耗费时间，<strong>真的太麻烦了！</strong></p><p style="text-align: left;">于是，就有了 <strong>Vita</strong>，它可以帮你节省很多时间和精力，非常适合一个人即一个团队的工作环境。</p><h3 style="text-align: left;">在线演示</h3><table style="width: auto;table-layout: fixed;height:110.83332824707031"><colgroup contentEditable="false"><col width=317></col><col width=557></col></colgroup><tbody><tr><th colspan="1" rowspan="1" width="auto" style="text-align: left;">版本</th><th colspan="1" rowspan="1" width="auto" style="text-align: left;">演示链接</th></tr><tr><td colspan="1" rowspan="1" width="auto" style="border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204);">Vita（开发中）</td><td colspan="1" rowspan="1" width="auto" style="border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204);"><a href="https://vita.aday.fun" target="_blank">https://vita.aday.fun</a></td></tr><tr><td colspan="1" rowspan="1" width="auto" style="border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204);">Vitality Vue 版</td><td colspan="1" rowspan="1" width="auto" style="border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204);">已下线</td></tr><tr><td colspan="1" rowspan="1" width="auto" style="border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204);">Vitality Layui 版</td><td colspan="1" rowspan="1" width="auto" style="border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204);"><a href="https://layui.aday.fun" target="_blank">https://layui.aday.fun</a></td></tr></tbody></table><p><br></p>','Y',1,current_timestamp(),1,current_timestamp());
