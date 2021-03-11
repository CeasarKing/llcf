CREATE TABLE `llcf`.`llcf_cfg_property` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `definition_id` BIGINT(20) NOT NULL,
  `cfg_type` VARCHAR(200) NOT NULL COMMENT '动态配置字典表',
  `property_key` VARCHAR(45) NOT NULL,
  `property_value` TEXT NULL,
  `property_value_desc` VARCHAR(200) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `llcf`.`llcf_cfg_dict` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `definition_id` BIGINT(20) NOT NULL,
  `dict_id` tinyint(4) NOT NULL COMMENT '动态配置字典表',
  `dict_code` VARCHAR(45) NOT NULL,
  `dict_desc` VARCHAR(200) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
  );

CREATE TABLE `llcf`.`llcf_cfg_definition_obj` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `project_id` BIGINT(20) NOT NULL,
  `module_id` BIGINT(20) NOT NULL,
  `obj_key` VARCHAR(40) NOT NULL,
  `obj_name` VARCHAR(40) NOT NULL,
  `obj_desc` VARCHAR(40) NOT NULL,
  `parent_definition_id` BIGINT(20) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
  );

CREATE TABLE `llcf`.`llcf_cfg_definition_obj_field` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `obj_id` BIGINT(20) NOT NULL COMMENT '对象id',
  `field_key` VARCHAR(40) NOT NULL COMMENT '字段键值',
  `field_name` VARCHAR(40) NOT NULL COMMENT '字段名称',
  `field_desc` VARCHAR(200) NOT NULL COMMENT '字段描述',
  `field_type` TINYINT(3) NOT NULL COMMENT '字段类型',
  `value_range_rel_type` TINYINT(3) NOT NULL COMMENT '字段关联类型',
  `value_range_definition_id` BIGINT(20) NOT NULL COMMENT '字段取值范围关联定义id',
  `value_range_values` TEXT NOT NULL COMMENT '字段关联取值',
  `input_type` TINYINT(3) NOT NULL COMMENT '输入类型',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`));

CREATE TABLE `llcf`.`llcf_project` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `project_key` VARCHAR(40) NOT NULL,
  `project_name` VARCHAR(45) NOT NULL,
  `project_desc` VARCHAR(200) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));
ALTER TABLE `llcf`.`llcf_project`
  ADD UNIQUE INDEX `project_key_UNIQUE` (`project_key` ASC) VISIBLE;


CREATE TABLE `llcf`.`llcf_module` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `project_id` BIGINT(20) NOT NULL,
  `module_key` VARCHAR(40) NOT NULL,
  `module_name` VARCHAR(40) NOT NULL,
  `module_desc` VARCHAR(200) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `llcf`.`llcf_cfg_dict_definition` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `project_id` BIGINT NOT NULL COMMENT '项目id',
  `module_id` BIGINT NOT NULL COMMENT '模块id',
  `dict_key` VARCHAR(40) NOT NULL COMMENT '枚举键',
  `dict_name` VARCHAR(40) NOT NULL COMMENT '枚举值',
  `dict_desc` VARCHAR(40) NOT NULL COMMENT '枚举描述',
  `create_time` VARCHAR(40) NOT NULL COMMENT '创建时间',
  `update_time` VARCHAR(40) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`));
ALTER TABLE `llcf`.`llcf_cfg_dict_definition`
ADD UNIQUE INDEX `unq_index` (`project_id` ASC, `module_id` ASC, `dict_key` ASC) VISIBLE;

CREATE TABLE `llcf`.`llcf_cfg_property_definition` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `project_id` BIGINT NOT NULL,
  `module_id` BIGINT NOT NULL,
  `property_key` VARCHAR(40) NOT NULL,
  `property_name` VARCHAR(40) NOT NULL,
  `property_desc` VARCHAR(200) NOT NULL,
  `create_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`));
ALTER TABLE `llcf`.`llcf_cfg_property_definition`
ADD UNIQUE INDEX `uni_index` (`project_id` ASC, `module_id` ASC, `property_key` ASC) VISIBLE;
;

INSERT INTO `llcf`.`llcf_project`(`id`,`project_key`,`project_name`,`project_desc`,`create_time`,`update_time`)VALUES(1,'hell', 'vava娃娃', '', '2021-03-05 21:19:49', '2021-03-05 21:19:50');
INSERT INTO `llcf`.`llcf_module`(`id`,`project_id`,`module_key`,`module_name`,`module_desc`,`create_time`,`update_time`,`app_key`)VALUES(1, 1, 'hell', 'vava娃娃', '', '2021-03-05 21:39:10', '2021-03-05 21:39:10', 'qweva'), (2, 1, 'hell0 ', 'vava娃娃', '', '2021-03-05 21:39:10', '2021-03-05 21:39:10', '4gqwfqv');
INSERT INTO `llcf`.`llcf_cfg_property_definition`(`id`,`project_id`,`module_id`,`property_key`,`property_name`,`property_desc`,`create_time`,`update_time`)VALUES(1,1,1,'worldwide.settlement', '全球结算配置', '全球结算配置描述', '2021-03-06 17:36:00','2021-03-10 14:51:43');
INSERT INTO `llcf`.`llcf_cfg_property`(`id`,`definition_id`,`cfg_type`,`property_key`,`property_value`,`property_value_desc`,`create_time`,`update_time`)VALUES(2,1,1,'version','1','第一个版本','2021-03-06 18:04:40.293','2021-03-10 17:52:59'),(3,1,3,'toporg','cccckkkk','头部上家cccckkkk','2021-03-06 18:04:40.293','2021-03-10 17:52:59'),(7,1,5,'test.strlist','["a","b","c","d"]','特殊字符串列表','2021-03-06 18:04:40.293','2021-03-10 17:52:59'),(8,1,6,'test.intlist','[1,2,3]','测试输入json的数据','2021-03-06 18:04:40.293','2021-03-10 17:52:59');
