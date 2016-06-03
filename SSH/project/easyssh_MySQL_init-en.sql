/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : easyssh

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2015-09-09 00:57:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTION` varchar(500) DEFAULT NULL,
  `PARAMETERS` varchar(500) DEFAULT NULL,
  `RES` varchar(500) DEFAULT NULL,
  `ACCOUNT` varchar(200) DEFAULT NULL,
  `IP` varchar(200) DEFAULT NULL,
  `LOG_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
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
INSERT INTO `sys_menu_permission` VALUES ('1', 'EasySSH', 'toMain.action', null, '0', 'icon-home2', '');
INSERT INTO `sys_menu_permission` VALUES ('2', 'System', null, '1', '0', 'icon-application_view_tile', '系统维护管理，System员拥有');
INSERT INTO `sys_menu_permission` VALUES ('3', 'Users', 'toSysUser.action', '2', '0', 'icon-user', null);
INSERT INTO `sys_menu_permission` VALUES ('4', 'Roles', 'toSysRole.action', '2', '1', 'icon-grade', null);
INSERT INTO `sys_menu_permission` VALUES ('5', 'Menus', 'toSysMenuPermission.action', '2', '2', 'icon-menu', null);
INSERT INTO `sys_menu_permission` VALUES ('6', 'Operations', 'toSysOperationPermission.action', '2', '3', 'icon-rights', null);
INSERT INTO `sys_menu_permission` VALUES ('7', 'EmpManagement', null, '1', '1', 'icon-report', 'Hr');
INSERT INTO `sys_menu_permission` VALUES ('8', 'Dept', 'toDept.action', '7', '1', 'icon-group', '');
INSERT INTO `sys_menu_permission` VALUES ('9', 'Emp', 'toEmp.action', '7', '2', 'icon-id', '');
INSERT INTO `sys_menu_permission` VALUES ('10', 'Report', null, '1', '2', 'icon-chart_bar', 'Manger');
INSERT INTO `sys_menu_permission` VALUES ('11', 'Statistical', 'toReports.action', '10', '0', 'icon-chart_curve', '');
INSERT INTO `sys_menu_permission` VALUES ('13', 'Logs', 'toSysLog.action', '2', '4', 'icon-book', 'Logs');

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
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_operation_permission
-- ----------------------------
INSERT INTO `sys_operation_permission` VALUES ('37', '5', 'list menus', 'sysMenuPermission_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('38', '5', 'edit  menu', 'sysMenuPermission_update.action', '');
INSERT INTO `sys_operation_permission` VALUES ('39', '5', 'delete  menu', 'sysMenuPermission_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('40', '5', 'move  menu次序', 'sysMenuPermission_move.action', '');
INSERT INTO `sys_operation_permission` VALUES ('41', '5', 'add  menu', 'sysMenuPermission_save.action', '');
INSERT INTO `sys_operation_permission` VALUES ('42', '6', ' menu list ', 'sysMenuPermission_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('43', '6', 'query menu operation permissions', 'sysOperationPermission_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('44', '6', 'add operation permissions', 'sysOperationPermission_save.action', '');
INSERT INTO `sys_operation_permission` VALUES ('45', '6', 'edit operation permissions', 'sysOperationPermission_update.action', '');
INSERT INTO `sys_operation_permission` VALUES ('46', '6', 'delete operation permissions', 'sysOperationPermission_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('47', '4', 'list all roles', 'sysRole_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('48', '4', 'add role', 'sysRole_save.action,sysMenuPermission_listAll.action,sysMenuPermission_listAllForSysRole.action', '');
INSERT INTO `sys_operation_permission` VALUES ('49', '4', 'edit role', 'sysRole_update.action#sysRole_getAllPermissionsId.action,sysMenuPermission_listAll.action,sysMenuPermission_listAllForSysRole.action', 'get edit role permissions');
INSERT INTO `sys_operation_permission` VALUES ('50', '4', 'delete role', 'sysRole_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('52', '3', 'user list', 'sysUser_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('53', '3', 'add user', 'sysUser_save.action,sysRole_all.action', '');
INSERT INTO `sys_operation_permission` VALUES ('54', '3', 'edit user', 'sysUser_update.action,sysRole_all.action', '');
INSERT INTO `sys_operation_permission` VALUES ('55', '3', 'delete user', 'sysUser_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('56', '3', 'show add user button', 'sysUserAddBtn', 'show permission');
INSERT INTO `sys_operation_permission` VALUES ('57', '3', 'show delete user button', 'sysUserDelBtn', 'show permission');
INSERT INTO `sys_operation_permission` VALUES ('58', '3', 'show edit user button', 'sysUserUpdateBtn', 'show permission');
INSERT INTO `sys_operation_permission` VALUES ('59', '3', 'show realname', 'showRealNameColumn', 'show permission');
INSERT INTO `sys_operation_permission` VALUES ('60', '8', 'add dept', 'dept_save.action', '');
INSERT INTO `sys_operation_permission` VALUES ('61', '8', 'edit dept', 'dept_update.action', '');
INSERT INTO `sys_operation_permission` VALUES ('62', '8', 'delete dept', 'dept_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('63', '8', 'dept list', 'dept_list.action', '');
INSERT INTO `sys_operation_permission` VALUES ('64', '8', 'show action-delete dept', 'deptDeleteShow', '');
INSERT INTO `sys_operation_permission` VALUES ('65', '9', 'add emp', 'emp_save.action,emp_allDept.action', '');
INSERT INTO `sys_operation_permission` VALUES ('66', '9', 'edit emp', 'emp_update.action,emp_allDept.action', '');
INSERT INTO `sys_operation_permission` VALUES ('67', '9', 'delete emp', 'emp_delete.action', '');
INSERT INTO `sys_operation_permission` VALUES ('68', '9', 'emp list', 'emp_list.action,emp_allDept.action', '');
INSERT INTO `sys_operation_permission` VALUES ('69', '13', 'log list', 'sysLog_list.action', '');

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
INSERT INTO `sys_role` VALUES ('1', 'Super Admin', '0', 'have all permissions');
INSERT INTO `sys_role` VALUES ('23', 'Administrator', '0', 'System Mangement。 \r\nDo not delete user, show user realname');
INSERT INTO `sys_role` VALUES ('24', 'HR', '0', 'Emp Management');
INSERT INTO `sys_role` VALUES ('25', 'Manager', '0', 'Show Reports');

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
INSERT INTO `sys_user` VALUES ('1', 'admin', '111111', '0', 'super admin');
INSERT INTO `sys_user` VALUES ('2', 'user', 'fffffff', '0', 'admin');
INSERT INTO `sys_user` VALUES ('35', 'hr', '111111', '0', 'hr');
INSERT INTO `sys_user` VALUES ('38', 'manager', 'aaaaaaaa', '0', 'manager');

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
