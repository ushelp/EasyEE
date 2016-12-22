/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.10 : Database - easyee
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/* DataBase create `easyee` */

CREATE DATABASE IF NOT EXISTS `easyee` DEFAULT CHARACTER SET utf8;

USE `easyee`;


/*Table structure for table `module_dept` */

CREATE TABLE `module_dept` (
  `deptno` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(30) NOT NULL,
  `loc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `module_dept` */

insert  into `module_dept`(`deptno`,`dname`,`loc`) values (1,'开发部','深圳');
insert  into `module_dept`(`deptno`,`dname`,`loc`) values (2,'测试部','北京');
insert  into `module_dept`(`deptno`,`dname`,`loc`) values (3,'研究院','上海');

/*Table structure for table `module_emp` */

CREATE TABLE `module_emp` (
  `empno` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(30) DEFAULT NULL,
  `job` varchar(30) DEFAULT NULL,
  `deptno` int(11) DEFAULT NULL,
  PRIMARY KEY (`empno`),
  KEY `fk_emp_deptno` (`deptno`) USING BTREE,
  CONSTRAINT `module_emp_ibfk_1` FOREIGN KEY (`deptno`) REFERENCES `module_dept` (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `module_emp` */

insert  into `module_emp`(`empno`,`ename`,`job`,`deptno`) values (1,'张三','开发工程师',1);
insert  into `module_emp`(`empno`,`ename`,`job`,`deptno`) values (2,'李四','开发工程师',1);
insert  into `module_emp`(`empno`,`ename`,`job`,`deptno`) values (3,'王五','管理员',3);
insert  into `module_emp`(`empno`,`ename`,`job`,`deptno`) values (4,'孙六','测试主管',1);
insert  into `module_emp`(`empno`,`ename`,`job`,`deptno`) values (5,'钱七','测试工程师',2);

/*Table structure for table `sys_log` */

CREATE TABLE `sys_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTION` text DEFAULT NULL,
  `PARAMETERS` text,
  `RES` text DEFAULT NULL,
  `ACCOUNT` varchar(200) DEFAULT NULL,
  `IP` varchar(200) DEFAULT NULL,
  `LOG_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_menu` */

CREATE TABLE `sys_menu` (
  `MENU_PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `ACTION` varchar(500) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `SORT_NUM` int(11) DEFAULT NULL,
  `ICON` varchar(50) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`MENU_PERMISSION_ID`),
  KEY `FK_FK_SYS_MENU_RIGHTS_ID` (`PARENT_ID`) USING BTREE,
  CONSTRAINT `sys_menu_ibfk_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `sys_menu` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (1,'EasyEE - SSH','',NULL,0,'icon-home2','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (2,'系统管理',NULL,1,0,'icon-application_view_tile','系统维护管理，系统管理员拥有');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (3,'用户管理','SysUser/page',2,0,'icon-user',NULL);
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (4,'角色管理','SysRole/page',2,1,'icon-grade',NULL);
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (5,'菜单权限管理','SysMenuPermission/page',2,2,'icon-menu',NULL);
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (6,'操作权限管理','SysOperationPermission/page',2,3,'icon-rights',NULL);
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (7,'员工管理',NULL,1,1,'icon-report','人事部操作');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (8,'部门信息管理','Dept/page',7,1,'icon-group','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (9,'员工信息管理','Emp/page',7,2,'icon-id','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (10,'报表管理',NULL,1,3,'icon-chart_bar','经理查看');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (11,'统计报表','toReports',10,0,'icon-chart_curve','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (12,'系统日志','SysLog/page',2,5,'icon-book','系统日志查看');

/*Table structure for table `sys_operation` */

CREATE TABLE `sys_operation` (
  `OPERATION_PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_PERMISSION_ID` int(11) DEFAULT NULL,
  `NAME` varchar(50) NOT NULL,
  `ACTION` varchar(500) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`OPERATION_PERMISSION_ID`),
  KEY `FK_FK_SYS_OPERATION_MENU_ID` (`MENU_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_operation_ibfk_1` FOREIGN KEY (`MENU_PERMISSION_ID`) REFERENCES `sys_menu` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

/*Data for the table `sys_operation` */

insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (1,5,'查询菜单列表','SysMenuPermission/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (2,5,'修改菜单','SysMenuPermission/update','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (3,5,'删除菜单','SysMenuPermission/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (4,5,'移动菜单次序','SysMenuPermission/move','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (5,5,'添加菜单','SysMenuPermission/save','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (6,6,'菜单列表查询','SysMenuPermission/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (7,6,'查询菜单对应的操作权限列表','SysOperationPermission/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (8,6,'新增操作权限','SysOperationPermission/save','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (9,6,'修改操作权限','SysOperationPermission/update','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (10,6,'删除操作权限','SysOperationPermission/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (11,4,'查询所有角色','SysRole/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (12,4,'添加角色','SysRole/save,SysMenuPermission/listAllForSysRole','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (13,4,'修改角色','SysRole/update#SysRole/getAllPermissionsId,SysMenuPermission/listAllForSysRole','修改角色需要获得用户的相关角色权限');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (14,4,'删除角色','SysRole/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (15,3,'查询用户列表','SysUser/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (16,3,'添加用户','SysUser/save,SysRole/all','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (17,3,'修改用户','SysUser/update,SysRole/all','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (18,3,'删除用户','SysUser/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (19,3,'显示添加用户按钮','SysUserAddBtn','显示权限');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (20,3,'显示删除用户按钮','SysUserDelBtn','显示权限');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (21,3,'显示修改用户按钮','SysUserUpdateBtn','显示权限');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (22,3,'显示真实姓名列信息','showRealNameColumn','显示权限');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (23,8,'添加新部门','Dept/save','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (24,8,'修改部门','Dept/update','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (25,8,'删除部门','Dept/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (26,8,'查询部门列表','Dept/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (27,8,'显示动作-删除部门','deptDeleteShow','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (28,9,'添加员工','Emp/save','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (29,9,'修改员工','Emp/update,Emp/allDept','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (30,9,'删除员工','Emp/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (31,9,'查看员工列表','Emp/list,Emp/allDept','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (32,12,'查询日志','SysLog/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (33,1,'后台管理中心权限','toMain','登录到后台必须授予');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (34,3,'查询角色列表','SysRole/all','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (35,4,'查询菜单权限和操作权限','SysRole/getAllPermissionsId,sysMenuPermission/listAll,sysMenuPermission/listAllForSysRole','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (36,1,'修改个人密码','SysUser/changePwd','可以执行Change Password功能');

/*Table structure for table `sys_role` */

CREATE TABLE `sys_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `STATUS` int(11) DEFAULT '0',
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`ROLE_ID`,`NAME`,`STATUS`,`REMARK`) values (1,'超级管理员',0,'拥有所有管理权限');
insert  into `sys_role`(`ROLE_ID`,`NAME`,`STATUS`,`REMARK`) values (2,'系统管理员',0,'管理系统用户和权限分配。\r\n不能删除用户，不显示删除按钮；不显示真实姓名');
insert  into `sys_role`(`ROLE_ID`,`NAME`,`STATUS`,`REMARK`) values (3,'HR',0,'员工管理模块');
insert  into `sys_role`(`ROLE_ID`,`NAME`,`STATUS`,`REMARK`) values (4,'经理',0,'报表查看');
insert  into `sys_role`(`ROLE_ID`,`NAME`,`STATUS`,`REMARK`) values (5,'演示用户',0,'展示系统功能');

/*Table structure for table `sys_role_menu` */

CREATE TABLE `sys_role_menu` (
  `ROLE_ID` int(11) NOT NULL,
  `MENU_PERMISSION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`MENU_PERMISSION_ID`),
  KEY `FK_FK_SYS_ROLE_RIGHTS_RIGHTS_ID` (`MENU_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_role_menu_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `sys_role_menu_ibfk_2` FOREIGN KEY (`MENU_PERMISSION_ID`) REFERENCES `sys_menu` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,1);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (2,1);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (3,1);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (4,1);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,1);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,2);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (2,2);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,2);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,3);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (2,3);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,3);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,4);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (2,4);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,4);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,5);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (2,5);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,5);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,6);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (2,6);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,6);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,7);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (3,7);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,7);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,8);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (3,8);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,8);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,9);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (3,9);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,9);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,10);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (4,10);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,10);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,11);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (4,11);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,11);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (1,12);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (2,12);
insert  into `sys_role_menu`(`ROLE_ID`,`MENU_PERMISSION_ID`) values (5,12);

/*Table structure for table `sys_role_operation` */

CREATE TABLE `sys_role_operation` (
  `ROLE_ID` int(11) NOT NULL,
  `OPERATION_PERMISSION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`OPERATION_PERMISSION_ID`),
  KEY `FK_FK_SYS_ROLE_OPERATION_RIGHTS_OPERATION_RIGHTS_ID` (`OPERATION_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_role_operation_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `sys_role_operation_ibfk_2` FOREIGN KEY (`OPERATION_PERMISSION_ID`) REFERENCES `sys_operation` (`OPERATION_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_operation` */

insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,1);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,1);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,1);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,2);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,2);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,3);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,4);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,4);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,5);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,5);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,6);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,6);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,6);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,7);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,7);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,7);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,8);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,8);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,9);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,9);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,10);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,10);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,11);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,11);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,11);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,12);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,12);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,13);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,13);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,14);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,15);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,15);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,15);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,16);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,16);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,17);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,17);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,18);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,19);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,19);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,19);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,20);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,20);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,21);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,21);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,21);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,22);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,22);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,22);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,23);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (3,23);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,23);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,24);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (3,24);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,24);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,25);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (3,25);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,25);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,26);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (3,26);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,26);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,27);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,27);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,28);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (3,28);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,28);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,29);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (3,29);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,29);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,30);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (3,30);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,30);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,31);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (3,31);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,31);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,32);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,32);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,32);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,33);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (2,33);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (3,33);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (4,33);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,33);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,34);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,34);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,35);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (5,35);
insert  into `sys_role_operation`(`ROLE_ID`,`OPERATION_PERMISSION_ID`) values (1,36);

/*Table structure for table `sys_user` */

CREATE TABLE `sys_user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `STATUS` int(11) DEFAULT '0',
  `REAL_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UQ_SYS_USER_NAME` (`NAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`USER_ID`,`NAME`,`PASSWORD`,`STATUS`,`REAL_NAME`) values (1,'admin','172eee54aa664e9dd0536b063796e54e',0,'晁关');
insert  into `sys_user`(`USER_ID`,`NAME`,`PASSWORD`,`STATUS`,`REAL_NAME`) values (2,'user','37cf6e1a4cd5a940ae416392ac26768d',0,'蒲关');
insert  into `sys_user`(`USER_ID`,`NAME`,`PASSWORD`,`STATUS`,`REAL_NAME`) values (3,'hr','16f60453bf87e1625f811c295e1a34fc',0,'任立');
insert  into `sys_user`(`USER_ID`,`NAME`,`PASSWORD`,`STATUS`,`REAL_NAME`) values (4,'manager','357d5b7e9946c6bfb8d91140c31f7074',0,'荆力');
insert  into `sys_user`(`USER_ID`,`NAME`,`PASSWORD`,`STATUS`,`REAL_NAME`) values (5,'demo','ae41281904e75830f26c0465f53772bb',0,'戴谋');


/*Table structure for table `sys_user_role` */

CREATE TABLE `sys_user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FK_FK_SYS_USER_ROLE_ROLE_ID` (`ROLE_ID`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`USER_ID`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`USER_ID`,`ROLE_ID`) values (1,1);
insert  into `sys_user_role`(`USER_ID`,`ROLE_ID`) values (2,2);
insert  into `sys_user_role`(`USER_ID`,`ROLE_ID`) values (3,2);
insert  into `sys_user_role`(`USER_ID`,`ROLE_ID`) values (4,3);
insert  into `sys_user_role`(`USER_ID`,`ROLE_ID`) values (5,5);


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
