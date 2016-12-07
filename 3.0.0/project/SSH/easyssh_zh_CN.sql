-- MySQL dump 10.13  Distrib 5.7.10, for Win64 (x86_64)
--
-- Host: localhost    Database: easyssh
-- ------------------------------------------------------
-- Server version	5.7.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS `easysh_en` DEFAULT CHARACTER SET utf8;

USE `easysh_en`;
--
-- Table structure for table `module_dept`
--

DROP TABLE IF EXISTS `module_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_dept` (
  `deptno` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(30) NOT NULL,
  `loc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_dept`
--

LOCK TABLES `module_dept` WRITE;
/*!40000 ALTER TABLE `module_dept` DISABLE KEYS */;
INSERT INTO `module_dept` VALUES (1,'开发部','深圳'),(2,'测试部','北京'),(3,'研究院','上海');
/*!40000 ALTER TABLE `module_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_emp`
--

DROP TABLE IF EXISTS `module_emp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module_emp` (
  `empno` int(11) NOT NULL AUTO_INCREMENT,
  `ename` varchar(30) DEFAULT NULL,
  `job` varchar(30) DEFAULT NULL,
  `deptno` int(11) DEFAULT NULL,
  PRIMARY KEY (`empno`),
  KEY `fk_emp_deptno` (`deptno`) USING BTREE,
  CONSTRAINT `module_emp_ibfk_1` FOREIGN KEY (`deptno`) REFERENCES `module_dept` (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_emp`
--

LOCK TABLES `module_emp` WRITE;
/*!40000 ALTER TABLE `module_emp` DISABLE KEYS */;
INSERT INTO `module_emp` VALUES (2,'张三','开发工程师',1),(3,'李四','开发工程师',1),(4,'王五','管理员',3),(5,'孙六','测试主管',1),(6,'钱七','测试工程师',2);
/*!40000 ALTER TABLE `module_emp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACTION` varchar(500) DEFAULT NULL,
  `PARAMETERS` text,
  `RES` varchar(500) DEFAULT NULL,
  `ACCOUNT` varchar(200) DEFAULT NULL,
  `IP` varchar(200) DEFAULT NULL,
  `LOG_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2661 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu_permission`
--

DROP TABLE IF EXISTS `sys_menu_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu_permission` (
  `MENU_PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `ACTION` varchar(500) DEFAULT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `SORT_NUM` int(11) DEFAULT NULL,
  `ICON` varchar(50) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`MENU_PERMISSION_ID`),
  KEY `FK_FK_SYS_MENU_RIGHTS_ID` (`PARENT_ID`) USING BTREE,
  CONSTRAINT `sys_menu_permission_ibfk_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `sys_menu_permission` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu_permission`
--

LOCK TABLES `sys_menu_permission` WRITE;
/*!40000 ALTER TABLE `sys_menu_permission` DISABLE KEYS */;
INSERT INTO `sys_menu_permission` VALUES (1,'EasyEE - SSH','',NULL,0,'icon-home2',''),(2,'系统管理',NULL,1,0,'icon-application_view_tile','系统维护管理，系统管理员拥有'),(3,'用户管理','toSysUser.action',2,0,'icon-user',NULL),(4,'角色管理','toSysRole.action',2,1,'icon-grade',NULL),(5,'菜单权限管理','toSysMenuPermission.action',2,2,'icon-menu',NULL),(6,'操作权限管理','toSysOperationPermission.action',2,3,'icon-rights',NULL),(7,'员工管理',NULL,1,1,'icon-report','人事部操作'),(8,'部门信息管理','Dept/page.action',7,1,'icon-group',''),(9,'员工信息管理','Emp/page.action',7,2,'icon-id',''),(10,'报表管理',NULL,1,3,'icon-chart_bar','经理查看'),(11,'统计报表','toReports.action',10,0,'icon-chart_curve',''),(13,'系统日志','toSysLog.action',2,4,'icon-book','系统日志查看');
/*!40000 ALTER TABLE `sys_menu_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_operation_permission`
--

DROP TABLE IF EXISTS `sys_operation_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_operation_permission` (
  `OPERATION_PERMISSION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_PERMISSION_ID` int(11) DEFAULT NULL,
  `NAME` varchar(50) NOT NULL,
  `ACTION` varchar(500) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`OPERATION_PERMISSION_ID`),
  KEY `FK_FK_SYS_OPERATION_MENU_ID` (`MENU_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_operation_permission_ibfk_1` FOREIGN KEY (`MENU_PERMISSION_ID`) REFERENCES `sys_menu_permission` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_operation_permission`
--

LOCK TABLES `sys_operation_permission` WRITE;
/*!40000 ALTER TABLE `sys_operation_permission` DISABLE KEYS */;
INSERT INTO `sys_operation_permission` VALUES (37,5,'查询菜单列表','sysMenuPermission_list.action',''),(38,5,'修改菜单','sysMenuPermission_update.action',''),(39,5,'删除菜单','sysMenuPermission_delete.action',''),(40,5,'移动菜单次序','sysMenuPermission_move.action',''),(41,5,'添加菜单','sysMenuPermission_save.action',''),(42,6,'菜单列表查询','sysMenuPermission_list.action',''),(43,6,'查询菜单对应的操作权限列表','sysOperationPermission_list.action',''),(44,6,'新增操作权限','sysOperationPermission_save.action',''),(45,6,'修改操作权限','sysOperationPermission_update.action',''),(46,6,'删除操作权限','sysOperationPermission_delete.action',''),(47,4,'查询所有角色','sysRole_list.action',''),(48,4,'添加角色','sysRole_save.action,sysMenuPermission_listAll.action,sysMenuPermission_listAllForSysRole.action',''),(49,4,'修改角色','sysRole_update.action#sysRole_getAllPermissionsId.action,sysMenuPermission_listAll.action,sysMenuPermission_listAllForSysRole.action','修改角色需要获得用户的相关角色权限'),(50,4,'删除角色','sysRole_delete.action',''),(52,3,'查询用户列表','sysUser_list.action',''),(53,3,'添加用户','sysUser_save.action,sysRole_all.action',''),(54,3,'修改用户','sysUser_update.action,sysRole_all.action',''),(55,3,'删除用户','sysUser_delete.action',''),(56,3,'显示添加用户按钮','sysUserAddBtn','显示权限'),(57,3,'显示删除用户按钮','sysUserDelBtn','显示权限'),(58,3,'显示修改用户按钮','sysUserUpdateBtn','显示权限'),(59,3,'显示真实姓名列信息','showRealNameColumn','显示权限'),(60,8,'添加新部门','Dept/save.action',''),(61,8,'修改部门','Dept/update.action',''),(62,8,'删除部门','Dept/delete.action',''),(63,8,'查询部门列表','Dept/list.action',''),(64,8,'显示动作-删除部门','deptDeleteShow',''),(65,9,'添加员工','Emp/save.acton',''),(66,9,'修改员工','Emp/update.action,Emp/allDept.action',''),(67,9,'删除员工','Emp/delete.action',''),(68,9,'查看员工列表','Emp/list.action,Emp/allDept.action',''),(69,13,'查询日志','sysLog_list.action',''),(70,1,'后台管理中心权限','toMain.action','登录到后台必须授予'),(71,3,'查询角色列表','sysRole_all.action',''),(72,4,'查询菜单权限和操作权限','sysRole_getAllPermissionsId.action,sysMenuPermission_listAll.action,sysMenuPermission_listAllForSysRole.action',''),(73,1,'修改个人密码','sysUser_changePwd.action','可以执行Change Password功能');
/*!40000 ALTER TABLE `sys_operation_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `STATUS` int(11) DEFAULT '0',
  `REMARK` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员',0,'拥有所有管理权限'),(23,'系统管理员',0,'管理系统用户和权限分配。\r\n不能删除用户，不显示删除按钮；不显示真实姓名'),(24,'HR',0,'员工管理模块'),(25,'经理',0,'报表查看'),(26,'演示用户',0,'展示系统功能');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu_permission`
--

DROP TABLE IF EXISTS `sys_role_menu_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_menu_permission` (
  `ROLE_ID` int(11) NOT NULL,
  `MENU_PERMISSION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`MENU_PERMISSION_ID`),
  KEY `FK_FK_SYS_ROLE_RIGHTS_RIGHTS_ID` (`MENU_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_role_menu_permission_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `sys_role_menu_permission_ibfk_2` FOREIGN KEY (`MENU_PERMISSION_ID`) REFERENCES `sys_menu_permission` (`MENU_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu_permission`
--

LOCK TABLES `sys_role_menu_permission` WRITE;
/*!40000 ALTER TABLE `sys_role_menu_permission` DISABLE KEYS */;
INSERT INTO `sys_role_menu_permission` VALUES (1,1),(23,1),(24,1),(25,1),(26,1),(1,2),(23,2),(26,2),(1,3),(23,3),(26,3),(1,4),(23,4),(26,4),(1,5),(23,5),(26,5),(1,6),(23,6),(26,6),(1,7),(24,7),(26,7),(1,8),(24,8),(26,8),(1,9),(24,9),(26,9),(1,10),(25,10),(26,10),(1,11),(25,11),(26,11),(1,13),(23,13),(26,13);
/*!40000 ALTER TABLE `sys_role_menu_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_operation_permission`
--

DROP TABLE IF EXISTS `sys_role_operation_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_operation_permission` (
  `ROLE_ID` int(11) NOT NULL,
  `OPERATION_PERMISSION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`OPERATION_PERMISSION_ID`),
  KEY `FK_FK_SYS_ROLE_OPERATION_RIGHTS_OPERATION_RIGHTS_ID` (`OPERATION_PERMISSION_ID`) USING BTREE,
  CONSTRAINT `sys_role_operation_permission_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `sys_role_operation_permission_ibfk_2` FOREIGN KEY (`OPERATION_PERMISSION_ID`) REFERENCES `sys_operation_permission` (`OPERATION_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_operation_permission`
--

LOCK TABLES `sys_role_operation_permission` WRITE;
/*!40000 ALTER TABLE `sys_role_operation_permission` DISABLE KEYS */;
INSERT INTO `sys_role_operation_permission` VALUES (1,37),(23,37),(26,37),(1,38),(23,38),(1,39),(1,40),(23,40),(1,41),(23,41),(1,42),(23,42),(26,42),(1,43),(23,43),(26,43),(1,44),(23,44),(1,45),(23,45),(1,46),(23,46),(1,47),(23,47),(26,47),(1,48),(23,48),(1,49),(23,49),(1,50),(1,52),(23,52),(26,52),(1,53),(23,53),(1,54),(23,54),(1,55),(1,56),(23,56),(26,56),(1,57),(26,57),(1,58),(23,58),(26,58),(1,59),(23,59),(26,59),(1,60),(24,60),(26,60),(1,61),(24,61),(26,61),(1,62),(24,62),(26,62),(1,63),(24,63),(26,63),(1,64),(26,64),(1,65),(24,65),(26,65),(1,66),(24,66),(26,66),(1,67),(24,67),(26,67),(1,68),(24,68),(26,68),(1,69),(23,69),(26,69),(1,70),(23,70),(24,70),(25,70),(26,70),(1,71),(26,71),(1,72),(26,72),(1,73);
/*!40000 ALTER TABLE `sys_role_operation_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `STATUS` int(11) DEFAULT '0',
  `REAL_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UQ_SYS_USER_NAME` (`NAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'admin','172eee54aa664e9dd0536b063796e54e',0,'晁关'),(2,'user','37cf6e1a4cd5a940ae416392ac26768d',0,'蒲关'),(35,'hr','16f60453bf87e1625f811c295e1a34fc',0,'任立'),(38,'manager','357d5b7e9946c6bfb8d91140c31f7074',0,'荆力'),(40,'demo','ae41281904e75830f26c0465f53772bb',0,'戴谋');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_role` (
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`),
  KEY `FK_FK_SYS_USER_ROLE_ROLE_ID` (`ROLE_ID`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `sys_user` (`USER_ID`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,23),(35,24),(38,25),(40,26);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-07 18:05:23
