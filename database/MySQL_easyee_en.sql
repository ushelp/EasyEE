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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `module_dept` */

insert  into `module_dept`(`deptno`,`dname`,`loc`) values (1,'Dev','Beijing');
insert  into `module_dept`(`deptno`,`dname`,`loc`) values (2,'Test','Shanghai');
insert  into `module_dept`(`deptno`,`dname`,`loc`) values (3,'Research','上海');

/*Table structure for table `module_emp` */

CREATE TABLE `module_emp` (
  `empno` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(30) DEFAULT NULL,
  `job` varchar(30) DEFAULT NULL,
  `deptno` int(11) DEFAULT NULL,
  PRIMARY KEY (`empno`),
  KEY `fk_emp_deptno` (`deptno`) USING BTREE,
  CONSTRAINT `module_emp_ibfk_1` FOREIGN KEY (`deptno`) REFERENCES `module_dept` (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `module_emp` */

insert  into `module_emp`(`empno`,`ename`,`job`,`deptno`) values (1,'Jack','Manger',1);
insert  into `module_emp`(`empno`,`ename`,`job`,`deptno`) values (2,'Smith','Sales',1);
insert  into `module_emp`(`empno`,`ename`,`job`,`deptno`) values (3,'Jone','Manger',3);
insert  into `module_emp`(`empno`,`ename`,`job`,`deptno`) values (4,'Green','Sales',1);
insert  into `module_emp`(`empno`,`ename`,`job`,`deptno`) values (5,'James','Sales',2);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_log` */

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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (1,'EasyEE - SSH','',NULL,0,'icon-home2','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (2,'Management',NULL,1,0,'icon-application_view_tile','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (3,'Users','SysUser/page',2,0,'icon-user',NULL);
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (4,'Roles','SysRole/page',2,1,'icon-grade',NULL);
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (5,'Menus','SysMenuPermission/page',2,2,'icon-menu',NULL);
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (6,'Operations','SysOperationPermission/page',2,3,'icon-rights',NULL);
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (7,'HR',NULL,1,1,'icon-report','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (8,'Depts','Dept/page',7,1,'icon-group','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (9,'Emps','Emp/page',7,2,'icon-id','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (10,'Reports',NULL,1,3,'icon-chart_bar','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (11,'reports','toReports',10,0,'icon-chart_curve','');
insert  into `sys_menu`(`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`PARENT_ID`,`SORT_NUM`,`ICON`,`REMARK`) values (12,'Logs','SysLog/page',2,5,'icon-book','');

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
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

/*Data for the table `sys_operation` */

insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (1,5,'Menu list','SysMenuPermission/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (2,5,'Edit menu','SysMenuPermission/update','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (3,5,'Delete menu','SysMenuPermission/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (4,5,'Move menu','SysMenuPermission/move','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (5,5,'Add menu','SysMenuPermission/save','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (6,6,'Menu list','SysMenuPermission/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (7,6,'Operations list','SysOperationPermission/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (8,6,'Add operation','SysOperationPermission/save','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (9,6,'Edit operation','SysOperationPermission/update','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (10,6,'Delete operation','SysOperationPermission/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (11,4,'Role list','SysRole/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (12,4,'Add role','SysRole/save,SysMenuPermission/listAllForSysRole','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (13,4,'Edit role','SysRole/update#SysRole/getAllPermissionsId,SysMenuPermission/listAllForSysRole','修改角色需要获得用户的相关角色权限');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (14,4,'Delete role','SysRole/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (15,3,'User list','SysUser/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (16,3,'Add user','SysUser/save,SysRole/all','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (17,3,'Edit user','SysUser/update,SysRole/all','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (18,3,'Delete user','SysUser/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (19,3,'Show add user Button','SysUserAddBtn','Show permission');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (20,3,'Show delete user Button','SysUserDelBtn','Show permission');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (21,3,'Show edit user button','SysUserUpdateBtn','Show permission');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (22,3,'Show real name columen','showRealNameColumn','Show permission');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (23,8,'Add dept','Dept/save','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (24,8,'Edit dept','Dept/update','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (25,8,'Delete dept','Dept/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (26,8,'Dept list','Dept/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (27,8,'Show delete dept','deptDeleteShow','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (28,9,'Add Emp','Emp/save','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (29,9,'Edit Emp','Emp/update,Emp/allDept','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (30,9,'Delete Emp','Emp/delete','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (31,9,'Emp list','Emp/list,Emp/allDept','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (32,12,'Log list','SysLog/list','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (33,1,'Management Center','toMain','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (34,3,'All role list','SysRole/all','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (35,4,'Get Menus, Operations','SysRole/getAllPermissionsId,sysMenuPermission/listAll,sysMenuPermission/listAllForSysRole','');
insert  into `sys_operation`(`OPERATION_PERMISSION_ID`,`MENU_PERMISSION_ID`,`NAME`,`ACTION`,`REMARK`) values (36,1,'Change password','SysUser/changePwd','');

/*Table structure for table `sys_role` */

CREATE TABLE `sys_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `STATUS` int(11) DEFAULT '0',
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`ROLE_ID`,`NAME`,`STATUS`,`REMARK`) values (1,'SuperAdmin',0,'All permissions');
insert  into `sys_role`(`ROLE_ID`,`NAME`,`STATUS`,`REMARK`) values (2,'Admin',0,'can not delete user');
insert  into `sys_role`(`ROLE_ID`,`NAME`,`STATUS`,`REMARK`) values (3,'HR',0,'Hr');
insert  into `sys_role`(`ROLE_ID`,`NAME`,`STATUS`,`REMARK`) values (4,'Manager',0,'Reports');
insert  into `sys_role`(`ROLE_ID`,`NAME`,`STATUS`,`REMARK`) values (5,'Demo',0,'');

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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`USER_ID`,`NAME`,`PASSWORD`,`STATUS`,`REAL_NAME`) values (1,'admin','172eee54aa664e9dd0536b063796e54e',0,'');
insert  into `sys_user`(`USER_ID`,`NAME`,`PASSWORD`,`STATUS`,`REAL_NAME`) values (2,'user','37cf6e1a4cd5a940ae416392ac26768d',0,'');
insert  into `sys_user`(`USER_ID`,`NAME`,`PASSWORD`,`STATUS`,`REAL_NAME`) values (3,'hr','16f60453bf87e1625f811c295e1a34fc',0,'');
insert  into `sys_user`(`USER_ID`,`NAME`,`PASSWORD`,`STATUS`,`REAL_NAME`) values (4,'manager','357d5b7e9946c6bfb8d91140c31f7074',0,'');
insert  into `sys_user`(`USER_ID`,`NAME`,`PASSWORD`,`STATUS`,`REAL_NAME`) values (5,'demo','ae41281904e75830f26c0465f53772bb',0,'');

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
insert  into `sys_user_role`(`USER_ID`,`ROLE_ID`) values (3,3);
insert  into `sys_user_role`(`USER_ID`,`ROLE_ID`) values (4,4);
insert  into `sys_user_role`(`USER_ID`,`ROLE_ID`) values (5,5);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
