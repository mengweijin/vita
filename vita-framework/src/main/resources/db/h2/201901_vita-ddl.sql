--liquibase formatted sql
--changeset admin:1001 splitStatements:true

-- 与 MySQL 区别
-- 1、h2 中，创建表最后面不能添加 COMMENT = '表注释'；
-- 2、h2 中，tinyint/int/bigint 类型不能限制位数，比如：int(4) 会报错，需要去掉；

drop table IF EXISTS VT_NOTICE;
create TABLE VT_NOTICE (
  ID                            bigint NOT NULL comment '主键ID',
  TITLE                         varchar(255) DEFAULT NULL comment '标题',
  DESCRIPTION                   text DEFAULT NULL comment '内容',
  RELEASED                      char(1) DEFAULT 'N' comment '是否已发布。[Y, N]',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_NOTICE is '通知/公告表';


drop table IF EXISTS VT_MESSAGE;
create TABLE VT_MESSAGE (
  ID                            bigint NOT NULL comment '主键ID',
  CATEGORY                      varchar(50) DEFAULT NULL comment '消息分类。{@link EMessageCategory}',
  TITLE                         varchar(255) DEFAULT NULL comment '标题',
  CONTENT                       varchar(4000) DEFAULT NULL comment '内容',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_MESSAGE is '消息管理表';


drop table IF EXISTS VT_MESSAGE_RECEIVER;
create TABLE VT_MESSAGE_RECEIVER (
  ID                            bigint NOT NULL comment '主键ID',
  MESSAGE_ID                    bigint NOT NULL comment '消息ID',
  USER_ID                       bigint DEFAULT NULL comment '消息接收者用户ID',
  VIEWED                        char(1) DEFAULT 'N' NOT NULL comment '是否已查看。[Y, N]',
  VIEWED_TIME                   datetime NULL comment '查看时间',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_MESSAGE_RECEIVER is '系统消息与接收人关系表';


drop table IF EXISTS VT_CONFIG;
create TABLE VT_CONFIG (
  ID                            bigint NOT NULL comment '主键ID',
  CONFIG_KEY                    varchar(255) NOT NULL comment '配置 key。比如：spring.profiles.active',
  CONFIG_VALUE                  varchar(2000) NOT NULL comment '配置 key 对应的值。比如 ${spring.profiles.active} 的值：dev',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_CONFIG is '配置管理表';
create unique index UIDX_VT_CONFIG_KEY on VT_CONFIG(CONFIG_KEY);


drop table IF EXISTS VT_CATEGORY;
create TABLE VT_CATEGORY (
  ID                            bigint NOT NULL comment '主键ID',
  PARENT_ID                     bigint DEFAULT NULL comment 'PARENT ID',
  ANCESTORS 	                varchar(500) NOT NULL DEFAULT '/' comment '祖级列表',
  CODE                          varchar(500) NOT NULL comment '编码',
  NAME                          varchar(255) NOT NULL comment '名称',
  REMARK 	                    varchar(500) comment '备注',
  SEQ 		                    int DEFAULT 1 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否已禁用。[Y, N]',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_CATEGORY is '分类管理表';
create unique index UIDX_VT_CATEGORY_CODE on VT_CATEGORY(CODE);
create index IDX_VT_CATEGORY_ANCESTORS on VT_CATEGORY(ANCESTORS);


drop table IF EXISTS VT_DICT_TYPE;
create TABLE VT_DICT_TYPE (
  ID                            bigint NOT NULL comment '主键ID',
  NAME 		                    varchar(100) NOT NULL comment '字典名称',
  CODE 		                    varchar(100) NOT NULL comment '字典类型编码',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_DICT_TYPE is '字典类型表';
create unique index UIDX_VT_DICT_TYPE_CODE on VT_DICT_TYPE(CODE);


drop table IF EXISTS VT_DICT_DATA;
create TABLE VT_DICT_DATA (
  ID                            bigint NOT NULL comment '主键ID',
  CODE 		                    varchar(100) NOT NULL comment '字典类型编码',
  VAL 		                    varchar(100) NOT NULL comment '字典数据值',
  LABEL 		                varchar(100) NOT NULL comment '字典数据标签名称',
  TAG                           varchar(10) NULL comment '字典数据标签样式。["primary", "success", "info", "warning", "danger"]',
  SEQ 		                    int DEFAULT 1 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否已禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_DICT_DATA is '字典数据表';
create unique index UIDX_VT_DICT_DATA_CODE_VAL on VT_DICT_DATA(CODE, VAL);


drop table IF EXISTS VT_LOG_SYSTEM;
create TABLE VT_LOG_SYSTEM (
  ID                            bigint NOT NULL comment '主键ID',
  LOGGER_LEVEL                  varchar(10) DEFAULT NULL comment '日志级别',
  THREAD_NAME                   varchar(255) DEFAULT NULL comment '线程名称',
  LOGGER_NAME                   varchar(255) DEFAULT NULL comment '日志名称。java 类名',
  FORMATTED_MESSAGE             varchar(3000) DEFAULT NULL comment '格式化后的日志信息',
  STACK_TRACE                   text DEFAULT NULL comment 'stack trace',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_LOG_SYSTEM is '系统日志表';


drop table IF EXISTS VT_LOG_OPERATION;
create TABLE VT_LOG_OPERATION (
  ID                            bigint NOT NULL comment '主键ID',
  TITLE                         varchar(255) DEFAULT NULL comment '操作日志模块标题',
  OPERATION_TYPE                varchar(10) DEFAULT NULL comment '操作类型枚举：EOperationType.java',
  HTTP_METHOD                   varchar(10) DEFAULT NULL comment 'http 请求方式',
  URL                           varchar(255) DEFAULT NULL comment '请求url',
  METHOD_NAME                   varchar(255) DEFAULT NULL comment '请求方法名称',
  REQUEST_DATA                  varchar(3000) DEFAULT NULL comment '请求数据',
  RESPONSE_DATA                 varchar(3000) DEFAULT NULL comment '响应数据',
  COST_TIME                     bigint NOT NULL DEFAULT 0 comment '执行消耗时间（毫秒）',
  SUCCESS                       char(1) DEFAULT 'Y' comment '操作是否成功。[Y, N]',
  ERROR_MSG                     varchar(3000) DEFAULT NULL comment '失败信息',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_LOG_OPERATION is '操作日志表';


drop table IF EXISTS VT_LOG_DATA_CHANGE;
create TABLE VT_LOG_DATA_CHANGE (
  ID                            bigint NOT NULL comment '主键ID',
  TABLE_NAME                    varchar(64) NOT NULL comment '数据库业务表名称',
  BUSINESS_ID                   bigint DEFAULT NULL comment '业务数据主键ID',
  BEFORE_DATA                   varchar DEFAULT NULL comment '变更前的数据。JSON',
  AFTER_DATA                    varchar DEFAULT NULL comment '变更后的数据。JSON',
  CHANGE_DATA                   varchar DEFAULT NULL comment '变更字段数据。List<DataChangeModel> JSON',
  READABLE_MESSAGES             varchar DEFAULT NULL comment '人类可阅读的变更数据信息。List<String> JSON',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_LOG_DATA_CHANGE is '数据变动日志表';
create index UIDX_VT_LDC_TABLE_ID on VT_LOG_DATA_CHANGE(TABLE_NAME, BUSINESS_ID);


drop table IF EXISTS VT_LOG_LOGIN;
create TABLE VT_LOG_LOGIN (
  ID                            bigint NOT NULL comment '主键ID',
  USERNAME                      varchar(50) DEFAULT NULL comment '登录账号',
  LOGIN_TYPE                    varchar(50) DEFAULT NULL comment '登录类型。枚举类 ELoginType.java',
  IP                            varchar(46) DEFAULT NULL comment '登录IP地址',
  IP_LOCATION                   varchar(128) DEFAULT NULL comment 'IP所属位置',
  BROWSER                       varchar(255) DEFAULT NULL comment '浏览器',
  PLATFORM                      varchar(50) DEFAULT NULL comment '设备平台类型',
  OS                            varchar(255) DEFAULT NULL comment '操作系统',
  SUCCESS                       char(1) DEFAULT 'Y' comment '登录是否成功。[Y, N]',
  ERROR_MSG                     varchar(3000) DEFAULT NULL comment '失败信息',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_LOG_LOGIN is '登录日志表';


drop table IF EXISTS VT_FILE;
create TABLE VT_FILE (
  ID                            bigint NOT NULL comment '主键ID',
  NAME                          varchar(255) NOT NULL comment '原始文件名称',
  SUFFIX                        varchar(10) comment '文件后缀',
  STORAGE_PATH                  varchar(500) NOT NULL comment '文件存储路径',
  MD5                           varchar(128) NOT NULL comment 'MD5 码',
  DELETED                       char(1) DEFAULT 'N' NOT NULL comment '逻辑删除。[Y, N]',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_FILE is '文件管理表';
create index IDX_VT_FILE_MD5 on VT_FILE(MD5);

drop table IF EXISTS VT_MENU;
create TABLE VT_MENU (
  ID                            bigint NOT NULL comment '主键ID',
  PARENT_ID              		bigint DEFAULT NULL comment '父菜单ID',
  TYPE 		                    varchar(10) NOT NULL DEFAULT 'BTN' comment '菜单类型。vt_menu_type: { DIR=目录；MENU=菜单; BTN=按钮; IFRAME=内嵌页面；URL=外链页面；}',
  TITLE 		                varchar(50) NOT NULL comment '标题',
  PERMISSION 	                varchar(100) comment '权限字符。[*:*:*]',
  URL                           varchar(255) comment '路由路径（如：/system/user）或者一个完整的 url 地址',
  SEQ 		                    int DEFAULT 1 comment '排序',
  ICON 				            varchar(100) comment '图标',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_MENU is '菜单管理表';


drop table IF EXISTS VT_DEPT;
create TABLE VT_DEPT (
  ID                            bigint NOT NULL comment '主键ID',
  PARENT_ID              		bigint DEFAULT NULL comment '父部门ID',
  ANCESTORS 	                varchar(500) NOT NULL DEFAULT '/' comment '祖级列表',
  CODE 		                    varchar(64) NOT NULL comment '部门编码',
  NAME 		                    varchar(64) NOT NULL comment '部门名称',
  SEQ 		                    int DEFAULT 1 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_DEPT is '部门管理表';
create unique index UIDX_VT_DEPT_CODE on VT_DEPT(CODE);
create index IDX_VT_DEPT_ANCESTORS on VT_DEPT(ANCESTORS);


drop table IF EXISTS VT_ROLE;
create TABLE VT_ROLE (
  ID                            bigint NOT NULL comment '主键ID',
  NAME 		                    varchar(50) NOT NULL comment '角色名称',
  CODE 		                    varchar(50) NOT NULL comment '角色编码',
  SEQ 		                    int DEFAULT 1 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_ROLE is '角色管理表';
create unique index UIDX_VT_ROLE_CODE on VT_ROLE(CODE);


drop table IF EXISTS VT_POST;
create TABLE VT_POST (
  ID                            bigint NOT NULL comment '主键ID',
  CODE 		                    varchar(64) NOT NULL comment '岗位编码',
  NAME 		                    varchar(64) NOT NULL comment '岗位名称',
  SEQ 		                    int DEFAULT 0 comment '展示顺序',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_POST is '岗位管理表';
create unique index UIDX_VT_POST_CODE on VT_POST(CODE);

drop table IF EXISTS VT_USER;
create TABLE VT_USER (
  ID                            bigint NOT NULL comment '主键ID',
  DEPT_ID                       bigint NOT NULL comment '部门ID',
  USERNAME                      varchar(64) NOT NULL comment '用户登录名（字母数字下划线）',
  NICKNAME                      varchar(64) NOT NULL comment '用户昵称',
  PASSWORD                      varchar(64) NOT NULL comment '登录密码',
  SALT                          varchar(32) NOT NULL comment '密码加盐',
  PASSWORD_LEVEL                varchar(30) DEFAULT 'MEDIUM' NOT NULL comment '密码强度。PasswdStrength.java',
  PASSWORD_CHANGE_TIME          datetime NULL DEFAULT CURRENT_TIMESTAMP comment '密码修改时间',
  CITIZEN_ID                    varchar(20) DEFAULT NULL comment '身份证号',
  GENDER                        varchar(6) DEFAULT NULL comment '性别。关联数据字典：vt_user_gender',
  EMAIL                         varchar(128) DEFAULT NULL comment '电子邮箱',
  MOBILE                        varchar(15) DEFAULT NULL comment '移动电话',
  TOTP                          varchar(32) DEFAULT NULL comment 'TOTP 动态口令验证密钥',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  DELETED                       char(1) DEFAULT 'N' NOT NULL comment '逻辑删除。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_USER is '用户管理表';
create unique index UIDX_VT_USER_USERNAME on VT_USER(USERNAME);


drop table IF EXISTS VT_USER_AVATAR;
create TABLE VT_USER_AVATAR (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  AVATAR                        text DEFAULT NULL comment '用户头像，以 Base64 文本存储的大字段。',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_USER_AVATAR is '用户与图像关系表';
create unique index UIDX_VT_USER_AVATAR_UID on VT_USER_AVATAR(USER_ID);


drop table IF EXISTS VT_USER_POST;
create TABLE VT_USER_POST (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  POST_ID                       bigint NOT NULL comment '岗位ID',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_USER_POST is '用户与岗位关系表';
create unique index UIDX_VT_USER_POST_UPID on VT_USER_POST(USER_ID, POST_ID);


drop table IF EXISTS VT_USER_ROLE;
create TABLE VT_USER_ROLE (
  ID                            bigint NOT NULL comment '主键ID',
  USER_ID                       bigint NOT NULL comment '用户ID',
  ROLE_ID                       bigint NOT NULL comment '角色ID',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_USER_ROLE is '用户与角色关系表';
create unique index UIDX_VT_USER_ROLE_URID on VT_USER_ROLE(USER_ID, ROLE_ID);


drop table IF EXISTS VT_ROLE_MENU;
create TABLE VT_ROLE_MENU (
  ID                            bigint NOT NULL comment '主键ID',
  ROLE_ID                       bigint NOT NULL comment '角色ID',
  MENU_ID                       bigint NOT NULL comment '菜单ID',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_ROLE_MENU is '用户与菜单关系表';
create unique index UIDX_VT_ROLE_MENU_RMID on VT_ROLE_MENU(ROLE_ID, MENU_ID);


drop table IF EXISTS VT_SCHEDULING_TASK;
create TABLE VT_SCHEDULING_TASK (
  ID                            bigint NOT NULL comment '主键ID',
  NAME 		                    varchar(255) NOT NULL comment '任务名称',
  CRON 		                    varchar(255) NOT NULL comment 'CRON 表达式',
  BEAN_NAME 		            varchar(255) NOT NULL comment '任务实现类的 Bean 名称（Bean 需要实现 ISchedulingTask 类）',
  ARGS 	                        varchar DEFAULT NULL comment '执行参数。以 JSON 字符串存储',
  DISABLED                      char(1) DEFAULT 'N' NOT NULL comment '是否禁用。[Y, N]',
  REMARK 	                    varchar(500) comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_SCHEDULING_TASK is '调度任务表';
create unique index UIDX_SCHEDULING_TASK_NAME on VT_SCHEDULING_TASK(NAME);
create unique index UIDX_SCHEDULING_TASK_BEAN_NAME on VT_SCHEDULING_TASK(BEAN_NAME);


drop table IF EXISTS VT_SCHEDULING_TASK_LOG;
create TABLE VT_SCHEDULING_TASK_LOG (
  ID                            bigint NOT NULL comment '主键ID',
  SCHEDULING_TASK_ID            bigint NOT NULL comment '调度任务ID',
  STATUS                        varchar(30) DEFAULT NULL comment '任务执行状态。字典：vt_scheduling_task_status',
  SUCCESS                       char(1) DEFAULT 'N' comment '任务是否执行成功。vt_succeeded [Y, N]',
  COST_TIME                     bigint NOT NULL DEFAULT 0 comment '执行消耗时间（毫秒）',
  ARGS 	                        varchar DEFAULT NULL comment '实际执行参数。以 JSON 字符串存储',
  MESSAGE                       varchar(3000) DEFAULT NULL comment '执行成功或失败时的附加信息',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_SCHEDULING_TASK_LOG is '调度任务日志表';


drop table IF EXISTS VT_EXT_PROP_DEFINITION;
create TABLE VT_EXT_PROP_DEFINITION (
  ID                            bigint NOT NULL comment '主键ID',
  TABLE_NAME                    varchar(64) NOT NULL comment '扩展目标表的表名称',
  LABEL_NAME                    varchar(64) NOT NULL comment '标签名称',
  PROP_NAME                     varchar(64) NOT NULL comment '属性字段名称',
  FORM_TYPE                     varchar(64) NOT NULL DEFAULT 'input' comment '表单组件类型。关联字典：vt_ext_prop_form_types',
  MANDATORY                     char(1) NOT NULL DEFAULT 'N' comment '是否必填。[Y, N]',
  MIN                           bigint DEFAULT NULL comment '最小长度（字符串）/最小值（数字类型）',
  MAX                           bigint DEFAULT NULL comment '最大长度（字符串）/最大值（数字类型）',
  REGEXP                        varchar(255) NOT NULL comment '值约束的正则表达式',
  DICT_CODE                     varchar(255) DEFAULT NULL comment '值关联的字典编码。可选项。',
  CATEGORY_CODE                 varchar(255) DEFAULT NULL comment '值关联的分类编码。可选项。',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_EXT_PROP_DEFINITION is '扩展属性定义表';
create unique index UIDX_VEPD_TNPN on VT_EXT_PROP_DEFINITION(TABLE_NAME, PROP_NAME);


------------------------------------------------
-- 表单管理表
------------------------------------------------
drop table IF EXISTS VT_FORM;
create TABLE VT_FORM (
  ID                            bigint NOT NULL comment '主键ID',
  PARENT_ID                     bigint DEFAULT NULL comment 'PARENT ID',
  NAME                          varchar(255) NOT NULL comment '表单名称',
  TYPE                          varchar(64) NOT NULL comment '表单类型（静态表单、动态表单）。关联字典：vt_form_type',
  STATIC_ROUTE                  varchar(225) DEFAULT NULL comment '静态表单路由路径',
  DYNAMIC_ID                    bigint DEFAULT NULL comment '动态表单 ID',
  REMARK                        varchar(500) DEFAULT NULL comment '备注',
  CREATE_BY                     bigint DEFAULT NULL comment '创建者',
  CREATE_TIME                   datetime NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
  UPDATE_BY 	                bigint DEFAULT NULL comment '更新者',
  UPDATE_TIME 	                datetime NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP comment '更新时间',
  PRIMARY KEY (ID)
);
comment on table VT_FORM is '表单管理表';
