/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : easyssh

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2015-09-30 15:21:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for module_dept
-- ----------------------------
DROP TABLE IF EXISTS `module_dept`;
CREATE TABLE `module_dept` (
  `deptno` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(30) NOT NULL,
  `loc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module_dept
-- ----------------------------
INSERT INTO `module_dept` VALUES ('1', '开发部', '深圳');
INSERT INTO `module_dept` VALUES ('2', '测试部', '北京');
INSERT INTO `module_dept` VALUES ('3', '研究院', '上海');

-- ----------------------------
-- Table structure for module_emp
-- ----------------------------
DROP TABLE IF EXISTS `module_emp`;
CREATE TABLE `module_emp` (
  `empno` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(30) DEFAULT NULL,
  `job` varchar(30) DEFAULT NULL,
  `deptno` int(11) DEFAULT NULL,
  PRIMARY KEY (`empno`),
  KEY `fk_emp_deptno` (`deptno`),
  CONSTRAINT `fk_emp_deptno` FOREIGN KEY (`deptno`) REFERENCES `module_dept` (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module_emp
-- ----------------------------
INSERT INTO `module_emp` VALUES ('2', '张三', '开发工程师', '1');
INSERT INTO `module_emp` VALUES ('3', '李四', '开发工程师', '1');
INSERT INTO `module_emp` VALUES ('4', '王五', '管理员', '3');
INSERT INTO `module_emp` VALUES ('5', '孙六', '测试主管', '2');
INSERT INTO `module_emp` VALUES ('6', '钱七', '测试工程师', '2');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTION` varchar(500) DEFAULT NULL,
  `PARAMETERS` text,
  `RES` varchar(500) DEFAULT NULL,
  `ACCOUNT` varchar(200) DEFAULT NULL,
  `IP` varchar(200) DEFAULT NULL,
  `LOG_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_permission`;
CREATE TABLE `sys_menu_permission` (
  `MENU_PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `ACTION` varchar(500) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `SORT_ORDER` int(11) DEFAULT NULL,
  `ICON` varchar(50) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`MENU_PERMISSION_ID`),
  KEY `FK_FK_SYS_MENU_RIGHTS_ID` (`PARENT_ID`) USING BTREE,
  CONSTRAINT `sys_menu_permission_ibfk_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `sys_menu_permission` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu_permission
-- ----------------------------
INSERT INTO `sys_menu_permission` VALUES ('1', 'EasySSH', '', null, '0', 'icon-home2', '');
INSERT INTO `sys_menu_permission` VALUES ('2', '系统管理', null, '1', '0', 'icon-application_view_tile', '系统维护管理，系统管理员拥有');
INSERT INTO `sys_menu_permission` VALUES ('3', '用户管理', 'toSysUser.action', '2', '0', 'icon-user', null);
INSERT INTO `sys_menu_permission` VALUES ('4', '角色管理', 'toSysRole.action', '2', '1', 'icon-grade', null);
INSERT INTO `sys_menu_permission` VALUES ('5', '菜单权限管理', 'toSysMenuPermission.action', '2', '2', 'icon-menu', null);
INSERT INTO `sys_menu_permission` VALUES ('6', '操作权限管理', 'toSysOperationPermission.action', '2', '3', 'icon-rights', null);
INSERT INTO `sys_menu_permission` VALUES ('7', '员工管理', null, '1', '1', 'icon-report', '人事部操作');
INSERT INTO `sys_menu_permission` VALUES ('8', '部门信息管理', 'toDept.action', '7', '1', 'icon-group', '');
INSERT INTO `sys_menu_permission` VALUES ('9', '员工信息管理', 'toEmp.action', '7', '2', 'icon-id', '');
INSERT INTO `sys_menu_permission` VALUES ('10', '报表管理', null, '1', '2', 'icon-chart_bar', '经理查看');
INSERT INTO `sys_menu_permission` VALUES ('11', '统计报表', 'toReports.action', '10', '0', 'icon-chart_curve', '');
INSERT INTO `sys_menu_permission` VALUES ('13', '系统日志', 'toSysLog.action', '2', '4', 'icon-book', '系统日志查看');

-- ----------------------------
-- Table structure for sys_operation_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_permission`;
CREATE TABLE `sys_operation_permission` (
  `OPERATION_PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_PERMISSION_ID` int(11) DEFAULT NULL,
  `NAME` varchar(50) NOT NULL,
  `ACTION` varchar(500) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`OPERATION_PERMISSION_ID`),
  KEY `FK_FK_SYS_OPERATION_MENU_ID` (`MENU_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_operation_permission_ibfk_1` FOREIGN KEY (`MENU_PERMISSION_ID`) REFERENCES `sys_menu_permission` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_operation_permission
-- ----------------------------
INSERT INTO `sys_operation_permission` VALUES ('37', '5', '查询菜单列表', 'sysMenuPermission_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('38', '5', '修改菜单', 'sysMenuPermission_update.action', '');
INSERT INTO `sys_operation_permission` VALUES ('39', '5', '删除菜单', 'sysMenuPermission_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('40', '5', '移动菜单次序', 'sysMenuPermission_move.action', '');
INSERT INTO `sys_operation_permission` VALUES ('41', '5', '添加菜单', 'sysMenuPermission_save.action', '');
INSERT INTO `sys_operation_permission` VALUES ('42', '6', '菜单列表查询', 'sysMenuPermission_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('43', '6', '查询菜单对应的操作权限列表', 'sysOperationPermission_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('44', '6', '新增操作权限', 'sysOperationPermission_save.action', '');
INSERT INTO `sys_operation_permission` VALUES ('45', '6', '修改操作权限', 'sysOperationPermission_update.action', '');
INSERT INTO `sys_operation_permission` VALUES ('46', '6', '删除操作权限', 'sysOperationPermission_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('47', '4', '查询所有角色', 'sysRole_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('48', '4', '添加角色', 'sysRole_save.action,sysMenuPermission_listAll.action,sysMenuPermission_listAllForSysRole.action', '');
INSERT INTO `sys_operation_permission` VALUES ('49', '4', '修改角色', 'sysRole_update.action#sysRole_getAllPermissionsId.action,sysMenuPermission_listAll.action,sysMenuPermission_listAllForSysRole.action', '修改角色需要获得用户的相关角色权限');
INSERT INTO `sys_operation_permission` VALUES ('50', '4', '删除角色', 'sysRole_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('52', '3', '查询用户列表', 'sysUser_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('53', '3', '添加用户', 'sysUser_save.action,sysRole_all.action', '');
INSERT INTO `sys_operation_permission` VALUES ('54', '3', '修改用户', 'sysUser_update.action,sysRole_all.action', '');
INSERT INTO `sys_operation_permission` VALUES ('55', '3', '删除用户', 'sysUser_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('56', '3', '显示添加用户按钮', 'sysUserAddBtn', '显示权限');
INSERT INTO `sys_operation_permission` VALUES ('57', '3', '显示删除用户按钮', 'sysUserDelBtn', '显示权限');
INSERT INTO `sys_operation_permission` VALUES ('58', '3', '显示修改用户按钮', 'sysUserUpdateBtn', '显示权限');
INSERT INTO `sys_operation_permission` VALUES ('59', '3', '显示真实姓名列信息', 'showRealNameColumn', '显示权限');
INSERT INTO `sys_operation_permission` VALUES ('60', '8', '添加新部门', 'dept_save.action', '');
INSERT INTO `sys_operation_permission` VALUES ('61', '8', '修改部门', 'dept_update.action', '');
INSERT INTO `sys_operation_permission` VALUES ('62', '8', '删除部门', 'dept_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('63', '8', '查询部门列表', 'dept_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('64', '8', '显示动作-删除部门', 'deptDeleteShow', '');
INSERT INTO `sys_operation_permission` VALUES ('65', '9', '添加员工', 'emp_save.action,emp_allDept.action', '');
INSERT INTO `sys_operation_permission` VALUES ('66', '9', '修改员工', 'emp_update.action,emp_allDept.action', '');
INSERT INTO `sys_operation_permission` VALUES ('67', '9', '删除员工', 'emp_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('68', '9', '查看员工列表', 'emp_list.action,emp_allDept.action', '');
INSERT INTO `sys_operation_permission` VALUES ('69', '13', '查询日志', 'sysLog_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('70', '1', '后台管理中心权限', 'toMain.action', '登录到后台必须授予');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `STATUS` int(11) DEFAULT '0',
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '0', '拥有所有管理权限');
INSERT INTO `sys_role` VALUES ('23', '系统管理员', '0', '管理系统用户和权限分配。\r\n不能删除用户，不显示删除按钮；不显示真实姓名');
INSERT INTO `sys_role` VALUES ('24', 'HR', '0', '员工管理模块');
INSERT INTO `sys_role` VALUES ('25', '经理', '0', '报表查看');

-- ----------------------------
-- Table structure for sys_role_menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_permission`;
CREATE TABLE `sys_role_menu_permission` (
  `ROLE_ID` int(11) NOT NULL,
  `MENU_PERMISSION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`MENU_PERMISSION_ID`),
  KEY `FK_FK_SYS_ROLE_RIGHTS_RIGHTS_ID` (`MENU_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_role_menu_permission_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `sys_role_menu_permission_ibfk_2` FOREIGN KEY (`MENU_PERMISSION_ID`) REFERENCES `sys_menu_permission` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu_permission
-- ----------------------------
INSERT INTO `sys_role_menu_permission` VALUES ('1', '1');
INSERT INTO `sys_role_menu_permission` VALUES ('23', '1');
INSERT INTO `sys_role_menu_permission` VALUES ('24', '1');
INSERT INTO `sys_role_menu_permission` VALUES ('25', '1');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '2');
INSERT INTO `sys_role_menu_permission` VALUES ('23', '2');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '3');
INSERT INTO `sys_role_menu_permission` VALUES ('23', '3');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '4');
INSERT INTO `sys_role_menu_permission` VALUES ('23', '4');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '5');
INSERT INTO `sys_role_menu_permission` VALUES ('23', '5');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '6');
INSERT INTO `sys_role_menu_permission` VALUES ('23', '6');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '7');
INSERT INTO `sys_role_menu_permission` VALUES ('24', '7');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '8');
INSERT INTO `sys_role_menu_permission` VALUES ('24', '8');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '9');
INSERT INTO `sys_role_menu_permission` VALUES ('24', '9');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '10');
INSERT INTO `sys_role_menu_permission` VALUES ('25', '10');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '11');
INSERT INTO `sys_role_menu_permission` VALUES ('25', '11');
INSERT INTO `sys_role_menu_permission` VALUES ('1', '13');
INSERT INTO `sys_role_menu_permission` VALUES ('23', '13');

-- ----------------------------
-- Table structure for sys_role_operation_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_operation_permission`;
CREATE TABLE `sys_role_operation_permission` (
  `ROLE_ID` int(11) NOT NULL,
  `OPERATION_PERMISSION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`OPERATION_PERMISSION_ID`),
  KEY `FK_FK_SYS_ROLE_OPERATION_RIGHTS_OPERATION_RIGHTS_ID` (`OPERATION_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_role_operation_permission_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `sys_role_operation_permission_ibfk_2` FOREIGN KEY (`OPERATION_PERMISSION_ID`) REFERENCES `sys_operation_permission` (`OPERATION_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_operation_permission
-- ----------------------------
INSERT INTO `sys_role_operation_permission` VALUES ('1', '37');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '37');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '38');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '38');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '39');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '39');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '40');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '40');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '41');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '41');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '42');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '42');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '43');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '43');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '44');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '44');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '45');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '45');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '46');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '46');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '47');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '47');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '48');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '48');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '49');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '49');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '50');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '50');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '52');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '52');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '53');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '53');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '54');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '54');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '55');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '56');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '56');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '57');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '57');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '58');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '58');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '59');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '59');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '60');
INSERT INTO `sys_role_operation_permission` VALUES ('24', '60');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '61');
INSERT INTO `sys_role_operation_permission` VALUES ('24', '61');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '62');
INSERT INTO `sys_role_operation_permission` VALUES ('24', '62');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '63');
INSERT INTO `sys_role_operation_permission` VALUES ('24', '63');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '64');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '65');
INSERT INTO `sys_role_operation_permission` VALUES ('24', '65');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '66');
INSERT INTO `sys_role_operation_permission` VALUES ('24', '66');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '67');
INSERT INTO `sys_role_operation_permission` VALUES ('24', '67');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '68');
INSERT INTO `sys_role_operation_permission` VALUES ('24', '68');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '69');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '69');
INSERT INTO `sys_role_operation_permission` VALUES ('1', '70');
INSERT INTO `sys_role_operation_permission` VALUES ('23', '70');
INSERT INTO `sys_role_operation_permission` VALUES ('24', '70');
INSERT INTO `sys_role_operation_permission` VALUES ('25', '70');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `STATUS` int(11) DEFAULT '0',
  `REAL_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UQ_SYS_USER_NAME` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '111111', '0', '晁关');
INSERT INTO `sys_user` VALUES ('2', 'user', 'fffffff', '0', '蒲关');
INSERT INTO `sys_user` VALUES ('35', 'hr', '111111', '0', '任立');
INSERT INTO `sys_user` VALUES ('38', 'manager', 'aaaaaaaa', '0', '荆力');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FK_FK_SYS_USER_ROLE_ROLE_ID` (`ROLE_ID`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`USER_ID`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '23');
INSERT INTO `sys_user_role` VALUES ('35', '24');
INSERT INTO `sys_user_role` VALUES ('38', '25');
