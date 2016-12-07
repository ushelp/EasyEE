-- MySQL dump 10.13  Distrib 5.7.10, for Win64 (x86_64)
--
-- Host: localhost    Database: easysm_en
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_dept`
--

LOCK TABLES `module_dept` WRITE;
/*!40000 ALTER TABLE `module_dept` DISABLE KEYS */;
INSERT INTO `module_dept` VALUES (1,'Research','Beijing'),(2,'Sales','Shanghai'),(3,'研究院','上海');
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_emp`
--

LOCK TABLES `module_emp` WRITE;
/*!40000 ALTER TABLE `module_emp` DISABLE KEYS */;
INSERT INTO `module_emp` VALUES (2,'SCOTT','SE',1),(3,'SMITH','SE',1),(4,'ALLEN','MANAGER',3),(5,'JONES','TE',1),(6,'KING','TE',2);
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
) ENGINE=InnoDB AUTO_INCREMENT=2728 DEFAULT CHARSET=utf8;
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
INSERT INTO `sys_menu_permission` VALUES (1,'EasyEE - SSH','',NULL,0,'icon-home2',''),(2,'System',NULL,1,0,'icon-application_view_tile','Management System，System manager'),(3,'User','SysUser/page.do',2,0,'icon-user',NULL),(4,'Role','SysRole/page.do',2,1,'icon-grade',NULL),(5,'Menu','SysMenuPermission/page.do',2,2,'icon-menu',NULL),(6,'Operation','SysOperationPermission/page.do',2,3,'icon-rights',NULL),(7,'Emp',NULL,1,1,'icon-report','HR'),(8,'Dept','Dept/page.do',7,1,'icon-group',''),(9,'Emp','Emp/page.do',7,2,'icon-id',''),(10,'Reports',NULL,1,3,'icon-chart_bar','Manager'),(11,'Chars','toReports.do',10,0,'icon-chart_curve',''),(13,'Logs','SysLog/page.do',2,5,'icon-book','Logs detail');
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
INSERT INTO `sys_operation_permission` VALUES (37,5,'get menus','SysMenuPermission_list.do',''),(38,5,'edit menu','SysMenuPermission_update.do',''),(39,5,'delete menu','SysMenuPermission_delete.do',''),(40,5,'move menu order','SysMenuPermission_move.do',''),(41,5,'add menu','SysMenuPermission_save.do',''),(42,6,'menu list','SysMenuPermission_list.do',''),(43,6,'get operations by menu','SysOperationPermission_list.do',''),(44,6,'add operation permission','SysOperationPermission_save.do',''),(45,6,'edit operation permission','SysOperationPermission_update.do',''),(46,6,'delete operation permission','SysOperationPermission_delete.do',''),(47,4,' role list','SysRole_list.do',''),(48,4,'add role','SysRole_save.do,SysMenuPermission_listAll.do,SysMenuPermission_listAllForSysRole.do',''),(49,4,'edit role','SysRole_update.do#SysRole_getAllPermissionsId.do,SysMenuPermission_listAll.do,SysMenuPermission_listAllForSysRole.do','edit role need user roles'),(50,4,'delete role','SysRole_delete.do',''),(52,3,'user list','SysUser_list.do',''),(53,3,'add user','SysUser_save.do,sysRole_all.do',''),(54,3,'edit user','SysUser_update.do,sysRole_all.do',''),(55,3,'delete user','SysUser_delete.do',''),(56,3,'show add user button ','SysUserAddBtn','show permission'),(57,3,'show delete user button ','SysUserDelBtn','show permission'),(58,3,'show edit user button ','SysUserUpdateBtn','show permission'),(59,3,'show real name column info','showRealNameColumn','show permission'),(60,8,'add dept','Dept/save.do',''),(61,8,'edit dept','Dept/update.do',''),(62,8,'delete dept','Dept/delete.do',''),(63,8,'get depts','Dept/list.do',''),(64,8,'show permission -delete dept','deptDeleteShow',''),(65,9,'add emp','Emp/save.acton',''),(66,9,'edit emp','Emp/update.do,Emp/allDept.do',''),(67,9,'delete emp','Emp/delete.do',''),(68,9,'get emp list','Emp/list.do,Emp/allDept.do',''),(69,13,'log list','SysLog/list.do',''),(70,1,'Main page permission','toMain.do',' must grant to login user'),(71,3,'role list','SysRole/all.do',''),(72,4,'get menu and operation permissions','SysRole/getAllPermissionsId.do,sysMenuPermission_listAll.do,sysMenuPermission/listAllForSysRole.do',''),(73,1,'edit password','SysUser/changePwd.do','Change Password');
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
INSERT INTO `sys_role` VALUES (1,'Super Administrator',0,'all permissions'),(23,'Manager',0,'can not delete user and see user real name.'),(24,'HR',0,'Emp Module'),(25,'Boss',0,'reports'),(26,'Demo',0,'show system');
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
INSERT INTO `sys_user` VALUES (1,'admin','172eee54aa664e9dd0536b063796e54e',0,'SA'),(2,'user','37cf6e1a4cd5a940ae416392ac26768d',0,'admin'),(35,'hr','16f60453bf87e1625f811c295e1a34fc',0,'hr'),(38,'manager','357d5b7e9946c6bfb8d91140c31f7074',0,'manager'),(40,'demo','ae41281904e75830f26c0465f53772bb',0,'demo');
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

-- Dump completed on 2016-12-07 18:05:58
