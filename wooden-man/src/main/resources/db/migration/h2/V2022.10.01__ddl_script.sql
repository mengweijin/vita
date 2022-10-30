DROP TABLE IF EXISTS QBT_GEN_TEMPLATE;
create TABLE QBT_GEN_TEMPLATE (
  id bigint NOT NULL COMMENT 'id',
  tpl_category varchar(20) NOT NULL COMMENT '模板类别（mybatis/maybatis-plus/jpa/自定义的模板 等）',
  tpl_content text DEFAULT NULL COMMENT '模板内容',
  gen_name varchar(20) NOT NULL COMMENT '生成文件的名称（controller/service/mapper/mapper.xml/entity/vue/layui 等）',
  gen_suffix varchar(20) NOT NULL COMMENT '生成文件的后缀。（.java/.xml/.vue/.html 等）',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QBT_GEN_TEMPLATE';


DROP TABLE IF EXISTS QBT_GEN_DRIVER;
create TABLE QBT_GEN_DRIVER (
  id bigint NOT NULL COMMENT 'id',
  group_id varchar(100) NOT NULL COMMENT 'JDBC Driver groupId',
  artifact_id varchar(100) NOT NULL COMMENT 'JDBC Driver artifactId',
  driver_version varchar(50) NULL COMMENT 'JDBC Driver version',
  driver_path varchar(200) NULL COMMENT 'JDBC Driver path on the disk.',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id),
  UNIQUE KEY QBT_GEN_DRIVER_GROUPID_ARTIFACTID_VERSION_INDEX (group_id, artifact_id, driver_version) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QBT_GEN_DRIVER';


DROP TABLE IF EXISTS QBT_GEN_DATASOURCE;
create TABLE QBT_GEN_DATASOURCE (
  id bigint NOT NULL COMMENT 'id',
  db_type varchar(20) NOT NULL COMMENT 'database type. mysql/h2/oracle/redis/MongoDB etc. Refer to ${@link com.baomidou.mybatisplus.annotation.DbType}',
  url varchar(100) NOT NULL COMMENT 'jdbc url or others(For example: redis=http://host:port). ',
  username varchar(50) NULL COMMENT 'username',
  password varchar(50) NULL COMMENT 'password',
  driver_id bigint NULL COMMENT 'QBT_GEN_DRIVER id',
  create_by varchar(64) NULL COMMENT 'Creator',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'creation time',
  update_by varchar(64) NULL COMMENT 'Revisor',
  update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP COMMENT 'Revisor time',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='QBT_GEN_DATASOURCE';



insert into QBT_GEN_DATASOURCE values (1576744861023870978, 'H2', 'jdbc:h2:file:D:\code\quickboot\h2\quickboot;DB_CLOSE_ON_EXIT=FALSE;MODE=MYSQL', 'sa', null, null, 'admin', CURRENT_TIMESTAMP(), 'admin', CURRENT_TIMESTAMP());

