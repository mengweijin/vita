--liquibase formatted sql
--changeset admin:2 splitStatements:true

-- 用户
insert into VT_USER (ID,DEPT_ID,USERNAME,NICKNAME,PASSWORD,PASSWORD_LEVEL,PASSWORD_CHANGE_TIME,CITIZEN_ID,GENDER,EMAIL,MOBILE,TOTP,DISABLED,DELETED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1,1,'admin','管理员','$2a$10$iiYJPA.3t09Ry4ECRpcpBuE.tOWJRq0o4M3AEMOadrArXavZg2/Cu','EASY',current_timestamp(),null,'female','aday.fun@outlook.com','18700000000',null,'N','N',null,1,current_timestamp(),1,current_timestamp());
insert into VT_USER (ID,DEPT_ID,USERNAME,NICKNAME,PASSWORD,PASSWORD_LEVEL,PASSWORD_CHANGE_TIME,CITIZEN_ID,GENDER,EMAIL,MOBILE,TOTP,DISABLED,DELETED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (2,1,'vita','微塔','$2a$10$iiYJPA.3t09Ry4ECRpcpBuE.tOWJRq0o4M3AEMOadrArXavZg2/Cu','EASY',current_timestamp(),null,'male','aday.fun@outlook.com','18700000000',null,'N','N',null,1,current_timestamp(),1,current_timestamp());

-- 角色
insert into VT_ROLE (ID, NAME, CODE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1, '管理员', 'admin', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_ROLE (ID, NAME, CODE, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (2, '普通用户', 'general', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());

-- 部门
insert into VT_DEPT (ID, PARENT_ID, ANCESTORS, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1,        null, '/',  'company',           '微塔科技',    1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, ANCESTORS, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1001,     1, '/1/', 'dept_finance',      '财务部',      1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, ANCESTORS, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1002,     1, '/1/', 'dept_development',  '研发部',      2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, ANCESTORS, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1003,     1, '/1/', 'dept_sales',        '销售部',      3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, ANCESTORS, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1004,     1, '/1/', 'dept_hr',           '人力资源部',   4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, ANCESTORS, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1005,     1, '/1/', 'dept_legal',        '法务部',       5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DEPT (ID, PARENT_ID, ANCESTORS, CODE, NAME, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1006,     1, '/1/', 'dept_compensation', '薪酬管理部',   6, 'N', null, 1, current_timestamp(), 1, current_timestamp());


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


-- 流程分类
insert into VT_CATEGORY (ID,PARENT_ID,ANCESTORS,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1,         null,'/','vt_workflow',              '流程类别',             null,         1,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_CATEGORY (ID,PARENT_ID,ANCESTORS,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10001,      1,'/1/','vt_workflow_finance',       '财务类',             null,         1,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_CATEGORY (ID,PARENT_ID,ANCESTORS,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10002,      1,'/1/','vt_workflow_staff',         '人事类',             null,         2,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_CATEGORY (ID,PARENT_ID,ANCESTORS,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10003,      1,'/1/','vt_workflow_rd',            '研发类',             null,         3,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_CATEGORY (ID,PARENT_ID,ANCESTORS,CODE,NAME,REMARK,SEQ,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10999,    1,'/1/','vt_workflow_other',        '其他',             null,         999,'N',1,current_timestamp(),1,current_timestamp());


-- 字典：停用/启用
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1, '停用/启用', 'vt_disabled', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10001, 1, 'N', '启用', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10002, 1, 'Y', '停用', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：是/否
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (2, '是/否', 'vt_yes_no', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (20001, 2, 'Y', '是', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (20002, 2, 'N', '否', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：菜单类型
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (3, '菜单类型', 'vt_menu_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (30001, 3, 'DIR', '目录', 'info', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (30002, 3, 'MENU', '菜单', 'primary', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (30003, 3, 'BTN', '按钮', 'warning', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (30004, 3, 'URL', '外链', 'danger', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：用户性别
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (4, '用户性别', 'vt_user_gender', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (40001, 4, 'male', '男', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (40002, 4, 'female', '女', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：成功/失败
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (5, '成功/失败', 'vt_succeeded', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (50001, 5, 'Y', '成功', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (50002, 5, 'N', '失败', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：登录类型
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (6, '登录类型', 'vt_login_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60001, 6, 'LOGIN', '登入', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60002, 6, 'LOGOUT', '注销', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60003, 6, 'KICK_OUT', '被踢下线', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (60004, 6, 'REPLACED', '被顶下线', 'warning', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：密码强度
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (7, '密码强度', 'vt_password_level', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70001, 7, 'EASY', '简单', 'danger', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70002, 7, 'MEDIUM', '中', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70003, 7, 'STRONG', '强', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70004, 7, 'VERY_STRONG', '很强', 'primary', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (70005, 7, 'EXTREMELY_STRONG', '非常强', 'success', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：操作日志类型
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (8, '操作日志类型', 'vt_operation_log_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80001, 8, 'SELECT', '查询', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80002, 8, 'INSERT', '新增', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80003, 8, 'UPDATE', '更新', 'warning', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80004, 8, 'REMOVE', '删除', 'danger', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80005, 8, 'IMPORT', '导入', 'success', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80006, 8, 'EXPORT', '导出', 'warning', 6, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80007, 8, 'UPLOAD', '上传', 'success', 7, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80008, 8, 'DOWNLOAD', '下载', 'warning', 8, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80009, 8, 'ENABLE', '启用', 'success', 9, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80010, 8, 'DISABLE', '停用', 'danger', 10, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80011, 8, 'OFFLINE', '强制下线', 'danger', 11, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80012, 8, 'COPY', '复制', 'info', 12, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80013, 8, 'PUBLISH', '发布', 'success', 13, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80014, 8, 'UNPUBLISH', '取消发布', 'danger', 14, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (80099, 8, 'OTHER', '其它', 'info', 99, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：http 请求方式
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (9, 'HTTP请求类型', 'vt_http_request_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90001, 9, 'GET', 'GET', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90002, 9, 'POST', 'POST', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90003, 9, 'PUT', 'PUT', 'warning', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90004, 9, 'DELETE', 'DELETE', 'danger', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90005, 9, 'HEAD', 'HEAD', 'info', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90006, 9, 'PATCH', 'PATCH', 'info', 6, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90007, 9, 'OPTIONS', 'OPTIONS', 'info', 7, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90008, 9, 'TRACE', 'TRACE', 'info', 8, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：日志级别
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10, '系统日志记录级别', 'vt_log_level', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100001, 10, 'TRACE', 'TRACE', 'info', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100002, 10, 'DEBUG', 'DEBUG', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100003, 10, 'INFO', 'INFO',   'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100004, 10, 'WARN', 'WARN',   'warning', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (100005, 10, 'ERROR', 'ERROR', 'danger', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：已发布/未发布
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (11, '已发布/未发布', 'vt_released', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (110001, 11, 'Y', '已发布', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (110002, 11, 'N', '未发布', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：字典标签样式
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (12, '字典标签样式', 'vt_dict_tag_style', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (120001, 12, 'primary', 'primary', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (120002, 12, 'success', 'success', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (120003, 12, 'info', 'info', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (120004, 12, 'warning', 'warning', 'warning', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (120005, 12, 'danger', 'danger', 'danger', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：调度任务执行状态
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (13, '调度任务执行状态', 'vt_scheduling_task_status', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (130001, 13, 'not_started', '未开始', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (130002, 13, 'running', '执行中', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (130003, 13, 'finished', '已完成', 'success', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：系统消息类别
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (14, '系统消息类型', 'vt_message_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (140001, 14, 'system', '系统', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (140002, 14, 'security', '安全', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (140003, 14, 'warning', '警告', 'warning', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (140004, 14, 'user', '用户', 'primary', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (140005, 14, 'other', '其它', 'info', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：已发布/未发布
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (15, '已读/未读', 'vt_message_viewed_status', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (150001, 15, 'Y', '已读', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (150002, 15, 'N', '未读', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：表单组件类型
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (16, '表单字段组件类型', 'vt_field_meta_types', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160001, 16, 'input', '输入框', 'info', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160002, 16, 'input-number', '数字输入框', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160003, 16, 'textarea', '文本域', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160004, 16, 'richtext', '富文本', 'info', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160005, 16, 'select', '下拉框', 'info', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160006, 16, 'select-multiple', '多选下拉框', 'info', 6, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160007, 16, 'switch', '开关', 'info', 7, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160008, 16, 'radio', '单选框', 'info', 8, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160009, 16, 'checkbox', '多选框', 'info', 9, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160010, 16, 'year', '年选择器', 'info', 10, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160011, 16, 'month', '月选择器', 'info', 11, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160012, 16, 'date', '日期选择器', 'info', 12, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160013, 16, 'datetime', '日期时间选择器', 'info', 13, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160014, 16, 'week', '周选择器', 'info', 14, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160015, 16, 'file', '文件选择器', 'info', 15, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160016, 16, 'color', '颜色选择器', 'info', 16, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160017, 16, 'icon', '图标选择器', 'info', 17, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160018, 16, 'category', '分类选择器', 'info', 18, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160019, 16, 'dept', '部门选择器', 'info', 19, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160020, 16, 'post', '岗位选择器', 'info', 20, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160021, 16, 'role', '角色选择器', 'info', 21, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (160022, 16, 'user', '用户选择器', 'info', 22, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：差异类型
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (17, '差异类型', 'vt_diff_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (170001, 17, 'ADDED', '新增', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (170002, 17, 'MODIFIED', '修改', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (170003, 17, 'REMOVED', '删除', 'danger', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：二级认证模式
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (18, '二级认证模式', 'vt_safe_mode', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (180001, 18, 'PASSWORD', '密码认证', 'warning', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (180002, 18, 'TOTP', 'TOTP 动态口令', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：请假类型
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (20, '请假类型', 'vt_oa_leave_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200001, 20, 'annual_leave', '年假', 'info', 1, 'N', '带薪年假', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200002, 20, 'personal_leave', '事假', 'info', 2, 'N', '私事无薪假', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200003, 20, 'compensatory_leave', '调休假', 'info', 3, 'N', '加班调休', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200004, 20, 'sick_leave', '病假', 'info', 4, 'N', '生病就医', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200005, 20, 'home_visit_leave', '探亲假', 'info', 5, 'N', '异地探亲', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200006, 20, 'marriage_leave', '婚假', 'info', 6, 'N', '结婚假期', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200007, 20, 'prenatal_leave', '产检假', 'info', 7, 'N', '孕期产检', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200008, 20, 'maternity_leave', '产假', 'info', 8, 'N', '女职工产假', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200009, 20, 'paternity_leave', '陪产假', 'info', 9, 'N', '男方陪护假', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200010, 20, 'lactation_leave', '哺乳假', 'info', 10, 'N', '产后哺乳假', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200011, 20, 'injury_leave', '工伤假', 'info', 11, 'N', '工伤休养', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (200012, 20, 'funeral_leave', '丧假', 'info', 12, 'N', '丧事假期', 1, current_timestamp(), 1, current_timestamp());
-- 字典：Warm-Flow 流程激活状态
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1001, 'Warm-Flow 流程激活状态', 'vt_warmflow_activity_status', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10010001, 1001, '0', '挂起', 'warning', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10010002, 1001, '1', '激活', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：Warm-Flow 流流程图状态
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1002, 'Warm-Flow 流流程图状态', 'vt_warmflow_chart_status', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10020001, 1002, '0', '未办理', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10020002, 1002, '1', '待办理', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10020003, 1002, '2', '已办理', 'success', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：Warm-Flow 流程状态
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1003, 'Warm-Flow 流程状态', 'vt_warmflow_flow_status', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030001, 1003, '0', '待提交', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030002, 1003, '1', '审批中', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030003, 1003, '2', '审批通过', 'success', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030004, 1003, '3', '自动完成', 'success', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030005, 1003, '4', '终止', 'info', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030006, 1003, '5', '作废', 'info', 6, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030007, 1003, '6', '撤销', 'info', 7, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030008, 1003, '7', '取回', 'info', 8, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030009, 1003, '8', '已完成', 'success', 9, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030010, 1003, '9', '已退回', 'danger', 10, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030011, 1003, '10', '失效', 'info', 11, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030012, 1003, '11', '拿回', 'info', 12, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030013, 1003, '12', '重启', 'primary', 13, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10030014, 1003, '13', '暂存', 'primary', 14, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：Warm-Flow 流程是否发布
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1004, 'Warm-Flow 流程是否发布', 'vt_warmflow_publish', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10040001, 1004, '0', '未发布', 'warning', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10040002, 1004, '1', '已发布', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10040009, 1004, '9', '失效', 'info', 9, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：Warm-Flow 审批表单是否自定义
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1005, 'Warm-Flow 审批表单是否自定义', 'vt_warmflow_form_custom', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10050001, 1005, 'Y', '是', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10050002, 1005, 'N', '否', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：Warm-Flow 流程设计器模型
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1006, 'Warm-Flow 流程设计器模型', 'vt_warmflow_designer_model', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10060001, 1006, 'CLASSICS', '经典模型', 'primary', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10060002, 1006, 'MIMIC', '仿钉钉模型', 'success', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：Warm-Flow 流程节点类型
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1007, 'Warm-Flow 流程节点类型', 'vt_warmflow_node_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10070001, 1007, '0', '开始节点', 'info', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10070002, 1007, '1', '中间节点', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10070003, 1007, '2', '结束节点', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10070004, 1007, '3', '互斥网关', 'info', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10070005, 1007, '4', '并行网关', 'info', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：Warm-Flow 流程节点跳转类型
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1008, 'Warm-Flow 流程节点跳转类型', 'vt_warmflow_node_skip_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10080001, 1008, 'PASS', '通过', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10080002, 1008, 'REJECT', '退回', 'danger', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10080003, 1008, 'NONE', '无动作', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：Warm-Flow 流程节点协作方式
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1009, 'Warm-Flow 流程节点协作方式', 'vt_warmflow_node_cooperate_type', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10090001, 1009, '1', '审批', 'info', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10090002, 1009, '2', '转办', 'info', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10090003, 1009, '3', '委派', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10090004, 1009, '4', '会签', 'info', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10090005, 1009, '5', '票签', 'info', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10090006, 1009, '6', '加签', 'info', 6, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (10090007, 1009, '7', '减签', 'info', 7, 'N', null, 1, current_timestamp(), 1, current_timestamp());

-- 字典：人员涉密级别（FL）
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (9001, '人员涉密级别', 'vt_fl_personnel_security_level', '《保守国家秘密法》第 43 条：涉密人员分为：核心涉密人员、重要涉密人员、一般涉密人员。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90010001, 9001, 'core', '核心', 'danger', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90010002, 9001, 'key', '重要', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90010003, 9001, 'general', '一般', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：数据安全级别（GB/T）
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (9002, '数据安全级别', 'vt_gbt_data_security_level', '标准号：GB/T 43697-2024。中文标准名称：数据安全技术 数据分类分级规则。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90020001, 9002, 'core', '核心', 'danger', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90020002, 9002, 'key', '重要', 'warning', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90020003, 9002, 'general', '一般', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
-- 字典：秘密级别（GB/T）
insert into VT_DICT_TYPE (ID, NAME, CODE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (9003, '秘密级别', 'vt_gbt_secret_level', '标准号：GB/T 7156-2003。中文标准名称：文献保密等级代码与标识。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90030001, 9003, 'GK', '公开', 'success', 1, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90030002, 9003, 'XZ', '限制', 'primary', 2, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90030003, 9003, 'MM', '秘密', 'info', 3, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90030004, 9003, 'JM', '机密', 'warning', 4, 'N', null, 1, current_timestamp(), 1, current_timestamp());
insert into VT_DICT_DATA (ID, TYPE_ID, VAL, LABEL, TAG, SEQ, DISABLED, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (90030005, 9003, 'UM', '绝密', 'danger', 5, 'N', null, 1, current_timestamp(), 1, current_timestamp());


-- 配置
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (1, 'vita.log-record-level', 'INFO', '系统日志记录到数据库的最低日志级别（不区分大小写）。可选值：[ALL, TRACE, DEBUG, INFO, WARN, ERROR, OFF]。注意：请勿设置过低，否则极度影响性能！', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (2, 'vita.login-captcha-enabled', 'true', '用户登录是否启用验证码。可选值：[ true, false ]。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (3, 'vita.user.default-password', 'aday.fun', '用户初始密码。密码长度应该在 8-18 位之间，并且为数字、字母、符号的至少任意两种的组合。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (4, 'vita.user.password-change-interval', '90', '修改密码的时间间隔。单位：天。若用户 90 天未修改密码，则通过系统消息提醒用户修改密码。0 表示没有启用该限制。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (5, 'vita.user.default-role-code', 'general', '用户默认角色编码。创建用户时，用户所拥有的默认的角色编码，用户拥有该角色，即拥有对应的菜单权限。', 1, current_timestamp(), 1, current_timestamp());
insert into VT_CONFIG (ID, CONFIG_KEY, CONFIG_VALUE, REMARK, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) values (6, 'vita.role-code-for-admin', 'admin', '系统管理员角色编码。用以接收系统维护、告警等相关消息的角色编码。', 1, current_timestamp(), 1, current_timestamp());


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
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004005,10011004,'BTN','用户管理-设置角色','system:user:setRoles',null,5,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011004006,10011004,'BTN','用户管理-重置密码','system:user:resetPassword',null,6,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005,10011,'MENU','角色管理','system:role:view','/system/role',5,'ri:group-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005001,10011005,'BTN','角色管理-查询','system:role:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005002,10011005,'BTN','角色管理-创建','system:role:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005003,10011005,'BTN','角色管理-更新','system:role:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005004,10011005,'BTN','角色管理-删除','system:role:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005005,10011005,'BTN','角色管理-分配权限','system:role:setPermissions',null,5,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011005006,10011005,'BTN','角色管理-分配用户','system:role:setUsers',null,6,null,'N',1,current_timestamp(),1,current_timestamp());

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
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011008005,10011008,'BTN','配置管理-刷新','system:config:refresh',null,5,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009,10011,'MENU','表单管理','system:form:view','/system/form',9,'ri:input-field','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009001,10011009,'BTN','表单-查询','system:form:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009002,10011009,'BTN','表单-创建','system:form:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009003,10011009,'BTN','表单-更新','system:form:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011009004,10011009,'BTN','表单-删除','system:form:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011010,10011,'MENU','文件管理','system:file:view','/system/file',10,'ep:folder-opened','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011010001,10011010,'BTN','文件管理-查询','system:file:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011010002,10011010,'BTN','文件管理-创建','system:file:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011010003,10011010,'BTN','文件管理-更新','system:file:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10011010004,10011010,'BTN','文件管理-删除','system:file:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());


insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021,null,'DIR','系统监控','monitor:system:view',null,21,'ep:monitor','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001,10021,'MENU','调度任务','monitor:schedulingTask:view','/monitor/scheduling',1,'ep:clock','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001001,10021001,'BTN','调度任务-查询','monitor:schedulingTask:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001002,10021001,'BTN','调度任务-创建','monitor:schedulingTask:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001003,10021001,'BTN','调度任务-更新','monitor:schedulingTask:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001004,10021001,'BTN','调度任务-删除','monitor:schedulingTask:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021001005,10021001,'BTN','调度任务-执行','monitor:schedulingTask:run',null,5,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (100210010011,10021001,'BTN','调度任务日志-查询','monitor:schedulingTaskLog:select',null,11,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (100210010012,10021001,'BTN','调度任务日志-删除','monitor:schedulingTaskLog:remove',null,12,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021002,10021,'MENU','应用监控','monitor:server:view','/monitor/server',2,'ep:data-line','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021003,10021,'MENU','本地缓存','monitor:cacheLocal:view','/monitor/cache-local',3,'ep:data-analysis','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021003001,10021003,'BTN','本地缓存-删除','monitor:cacheLocal:remove',null,1,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021004,10021,'MENU','在线用户','monitor:userOnline:view','/monitor/user-online',4,'ri:user-voice-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021004001,10021004,'BTN','在线用户-查询','monitor:userOnline:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021004004,10021004,'BTN','在线用户-下线','monitor:userOnline:kickOut',null,2,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021005,10021,'MENU','登录日志','monitor:logLogin:view','/monitor/log-login',5,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021005001,10021005,'BTN','登录日志-查询','monitor:logLogin:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021005004,10021005,'BTN','登录日志-删除','monitor:logLogin:remove',null,2,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021006,10021,'MENU','操作日志','monitor:logOperation:view','/monitor/log-operation',6,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021006001,10021006,'BTN','操作日志-查询','monitor:logOperation:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021006004,10021006,'BTN','操作日志-删除','monitor:logOperation:remove',null,2,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021007,10021,'MENU','系统日志','monitor:logSystem:view','/monitor/log-system',7,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021007001,10021007,'BTN','系统日志-查询','monitor:logSystem:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021007004,10021007,'BTN','系统日志-删除','monitor:logSystem:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021008,10021,'MENU','数据变动日志','monitor:logDataChange:view','/monitor/log-data-change',8,'ep:tickets','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021008001,10021008,'BTN','数据变动日志-查询','monitor:logDataChange:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10021008004,10021008,'BTN','数据变动日志-删除','monitor:logDataChange:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());


insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10031,null,'DIR','办公管理','oa:office:view',null,31,'ep:box','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10031001,10031,'MENU','流程中心','oa:workflowCenter:view','/oa/workflow-center',1,'ri:drag-move-line','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10031002,10031,'MENU','我的流程','oa:myWorkflow:view','/oa/my-workflow',2,'ant-design:bars-outlined','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10031003,10031,'MENU','待办任务','oa:backlogTask:view','/oa/backlog-task',3,'ri:calendar-schedule-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10031004,10031,'MENU','已办任务','oa:doneTask:view','/oa/done-task',4,'ri:calendar-check-fill','N',1,current_timestamp(),1,current_timestamp());


insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041,null,'DIR','流程管理','workflow:menu:view',null,41,'ri:flow-chart','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041001,10041,'MENU','流程定义','workflow:definition:view','/workflow/definition',4,'ri:align-item-vertical-center-line','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041001001,10041001,'BTN','流程定义-查询','workflow:definition:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041001002,10041001,'BTN','流程定义-创建','workflow:definition:create',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041001003,10041001,'BTN','流程定义-更新','workflow:definition:update',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041001004,10041001,'BTN','流程定义-删除','workflow:definition:remove',null,4,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041001005,10041001,'BTN','流程定义-复制','workflow:definition:copy',null,5,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041001006,10041001,'BTN','流程定义-发布','workflow:definition:publish',null,6,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041001007,10041001,'BTN','流程定义-取消发布','workflow:definition:unpublish',null,7,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041002,10041,'MENU','流程实例','workflow:instance:view','/workflow/instance',5,'ri:align-item-left-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041002001,10041002,'BTN','流程实例-查询','workflow:instance:select',null,1,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041002002,10041002,'BTN','流程实例-终止','workflow:instance:termination',null,2,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041002003,10041002,'BTN','流程实例-删除','workflow:instance:remove',null,3,null,'N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10041003,10041,'MENU','流程分类','workflow:category:view','/workflow/category',6,'ri:node-tree','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10081,null,'DIR','开发工具','tools:menu:view',null,81,'ri:tools-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10081001,10081,'MENU','代码生成器','tools:generator:view','/tool/generator',1,'ri:ai-generate-text','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10081002,10081,'MENU','接口文档','tools:swagger-ui:view','/tool/swagger-ui',2,'ep:document','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10090,null,'DIR','演示页面','demo:page:view',null,90,'ep:star-filled','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10090001,10090,'MENU','IFRAME 页面','demo:page:view','/demo/iframe',1,'ep:pointer','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10090002,10090,'DIR','错误页面','demo:page:view', null,2,'ri:error-warning-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10090002001,10090002,'MENU','403','demo:page:view', '/error/403',1,'ri:error-warning-fill','N',1,current_timestamp(),1,current_timestamp());
insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10090002002,10090002,'MENU','404','demo:page:view', '/error/404',1,'ri:error-warning-fill','N',1,current_timestamp(),1,current_timestamp());

insert into VT_MENU (ID,PARENT_ID,TYPE,TITLE,PERMISSION,URL,SEQ,ICON,DISABLED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (10091,null,'URL','个人博客','aday:fun:view','https://aday.fun',91,'ep:link','N',1,current_timestamp(),1,current_timestamp());


-- 调度任务
insert into VT_SCHEDULING_TASK (ID,NAME,CRON,BEAN_NAME,ARGS,DISABLED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (1,'系统日志清理','0 0 3 1 * ?','systemLogCleanTask','{ "days": 365 }','N','days：系统日志保留时长，单位：天。超过该配置时间的系统日志将被调度任务清理。',1,current_timestamp(),1,current_timestamp());
insert into VT_SCHEDULING_TASK (ID,NAME,CRON,BEAN_NAME,ARGS,DISABLED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (2,'临时目录清理','0 10 3 * * ?','multipartLocationCleanTask','{ "hours": 24 }','N','hours：临时文件保留时长，单位：小时。超过该配置时间的临时文件将被调度任务清理。',1,current_timestamp(),1,current_timestamp());
insert into VT_SCHEDULING_TASK (ID,NAME,CRON,BEAN_NAME,ARGS,DISABLED,REMARK,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) values (3,'文件上传存储路径下的空文件夹清理','0 20 3 ? * 1','fileUploadEmptyDirectoryCleanTask',null,'N','文件上传存储路径下的空文件夹清理任务，防止文件删除后，遗留的空文件夹太多。',1,current_timestamp(),1,current_timestamp());


-- 系统公告
INSERT INTO VT_NOTICE (ID,TITLE,DESCRIPTION,RELEASED,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (1948579666135564289,'欢迎使用 Vita（微塔）管理系统！','<p style="text-align: left;"><strong>Vita（中文名：微塔）</strong>：是一款<strong>轻量级快速开发平台应用系统</strong>。</p><p style="text-align: left;">基于 SpringBoot 3、sa-token、mybatis-plus、vite、vue 3、element-plus、javascript 等技术，不依赖任何第三方服务。</p><p style="text-align: left;">有时候我们就想做一个简单的东西，采用已有的开源框架却要依赖一大堆东西，和很复杂的配置文件，自己从零搭建又太耗费时间，<strong>真的太麻烦了！</strong></p><p style="text-align: left;">于是，就有了 <strong>Vita</strong>，它可以帮你节省很多时间和精力，非常适合一个人即一个团队的工作环境。</p><p style="text-align: left;">在线演示：<a href="https://vita.aday.fun" target="_blank">https://vita.aday.fun</a></p>','Y',1,current_timestamp(),1,current_timestamp());


-- 用户-角色
INSERT INTO VT_USER_ROLE (ID,USER_ID,ROLE_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2034125907099107330,2,2,1,current_timestamp(),1,current_timestamp());


-- 角色-权限
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065576136705,2,10031001,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065576136706,2,1001006,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065576136707,2,1001005,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065576136708,2,10031003,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065576136709,2,1001004,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065576136710,2,10031002,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065576136711,2,100,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065576136712,2,1001003,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245570,2,1001002,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245571,2,10031004,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245572,2,1001001,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245573,2,10090001,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245574,2,1001,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245575,2,1002,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245576,2,10090,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245577,2,10090002,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245578,2,10091,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245579,2,10031,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245580,2,10090002002,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245581,2,10090002001,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245582,2,1002004,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245583,2,1002003,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245584,2,1002002,1,current_timestamp(),1,current_timestamp());
INSERT INTO VT_ROLE_MENU (ID,ROLE_ID,MENU_ID,CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME) VALUES (2069980065643245585,2,1002001,1,current_timestamp(),1,current_timestamp());
