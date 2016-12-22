/**
EasyEE
*********************************************************************
**/


create sequence seq_module_dept;
create sequence seq_module_emp;
create sequence seq_sys_log;
create sequence seq_sys_menu;
create sequence seq_sys_operation;
create sequence seq_sys_role;
create sequence seq_sys_user;

/**Table structure for table module_dept **/

CREATE TABLE module_dept (
  deptno number NOT NULL ,
  dname varchar2(30) NOT NULL,
  loc varchar2(255) DEFAULT NULL,
  PRIMARY KEY (deptno)
);


/**Table structure for table module_emp **/

CREATE TABLE module_emp (
  empno number NOT NULL ,
  ename varchar2(30) DEFAULT NULL,
  job varchar2(30) DEFAULT NULL,
  deptno number DEFAULT NULL,
  PRIMARY KEY (empno),
  CONSTRAINT module_emp_fk FOREIGN KEY (deptno) REFERENCES module_dept (deptno)
);


/**Table structure for table sys_log **/

CREATE TABLE sys_log (
  LOG_ID number NOT NULL ,
  ACTION clob DEFAULT NULL,
  PARAMETERS clob,
  RES clob DEFAULT NULL,
  ACCOUNT varchar2(200) DEFAULT NULL,
  IP varchar2(200) DEFAULT NULL,
  LOG_TIME date DEFAULT SYSDATE,
  PRIMARY KEY (LOG_ID)
);

/**Table structure for table sys_menu **/

CREATE TABLE sys_menu (
  MENU_PERMISSION_ID number NOT NULL ,
  NAME varchar2(50) NOT NULL,
  ACTION varchar2(500) DEFAULT NULL,
  PARENT_ID number DEFAULT NULL,
  SORT_NUM number DEFAULT NULL,
  ICON varchar2(50) DEFAULT NULL,
  REMARK varchar2(500) DEFAULT NULL,
  PRIMARY KEY (MENU_PERMISSION_ID),
  CONSTRAINT sys_menu_fk FOREIGN KEY (PARENT_ID) REFERENCES sys_menu (MENU_PERMISSION_ID)
);


/**Table structure for table sys_operation **/

CREATE TABLE sys_operation (
  OPERATION_PERMISSION_ID number NOT NULL ,
  MENU_PERMISSION_ID number DEFAULT NULL,
  NAME varchar2(50) NOT NULL,
  ACTION varchar2(500) DEFAULT NULL,
  REMARK varchar2(500) DEFAULT NULL,
  PRIMARY KEY (OPERATION_PERMISSION_ID),
  CONSTRAINT sys_oper_fk FOREIGN KEY (MENU_PERMISSION_ID) REFERENCES sys_menu (MENU_PERMISSION_ID)
);


/**Table structure for table sys_role **/

CREATE TABLE sys_role (
  ROLE_ID number NOT NULL ,
  NAME varchar2(50) NOT NULL,
  STATUS number DEFAULT '0',
  REMARK varchar2(500) DEFAULT NULL,
  PRIMARY KEY (ROLE_ID)
);


/**Table structure for table sys_role_menu **/

CREATE TABLE sys_role_menu (
  ROLE_ID number NOT NULL,
  MENU_PERMISSION_ID number NOT NULL,
  PRIMARY KEY (ROLE_ID,MENU_PERMISSION_ID),
  CONSTRAINT sys_role_menu_fk FOREIGN KEY (ROLE_ID) REFERENCES sys_role (ROLE_ID),
  CONSTRAINT sys_role_menu_fk2 FOREIGN KEY (MENU_PERMISSION_ID) REFERENCES sys_menu (MENU_PERMISSION_ID)
) ;


/**Table structure for table sys_role_operation **/

CREATE TABLE sys_role_operation (
  ROLE_ID number NOT NULL,
  OPERATION_PERMISSION_ID number NOT NULL,
  PRIMARY KEY (ROLE_ID,OPERATION_PERMISSION_ID),
  CONSTRAINT sys_role_oper_fk FOREIGN KEY (ROLE_ID) REFERENCES sys_role (ROLE_ID),
  CONSTRAINT sys_role_oper_fk2 FOREIGN KEY (OPERATION_PERMISSION_ID) REFERENCES sys_operation (OPERATION_PERMISSION_ID)
) ;


/**Table structure for table sys_user **/

CREATE TABLE sys_user (
  USER_ID number NOT NULL ,
  NAME varchar2(50) NOT NULL UNIQUE,
  PASSWORD varchar2(100) NOT NULL,
  STATUS number DEFAULT '0',
  REAL_NAME varchar2(50) DEFAULT NULL,
  PRIMARY KEY (USER_ID)
);

/**Table structure for table sys_user_role **/

CREATE TABLE sys_user_role (
  USER_ID number NOT NULL,
  ROLE_ID number NOT NULL,
  PRIMARY KEY (USER_ID,ROLE_ID),
  CONSTRAINT sys_user_role_fk FOREIGN KEY (USER_ID) REFERENCES sys_user (USER_ID),
  CONSTRAINT sys_user_role_fk2 FOREIGN KEY (ROLE_ID) REFERENCES sys_role (ROLE_ID)
) ;


/**Identity for the column DEPTNO **/
create or replace trigger tri_module_dept
before insert on module_dept
for each row
begin
   select seq_module_dept.nextval into :NEW.DEPTNO from dual;
end;
/

/**Identity for the column EMPNO **/
create or replace trigger tri_module_emp
before insert on module_emp
for each row
begin
   select seq_module_emp.nextval into :NEW.EMPNO from dual;
end;
/

/**Identity for the column LOG_ID **/
create or replace trigger tri_sys_log
before insert on sys_log
for each row
begin
   select seq_sys_log.nextval into :NEW.LOG_ID from dual;
end;
/

/**Identity for the column MENU_PERMISSION_ID **/
create or replace trigger tri_sys_menu
before insert on sys_menu
for each row
begin
   select seq_sys_menu.nextval into :NEW.MENU_PERMISSION_ID from dual;
end;
/

/**Identity for the column OPERATION_PERMISSION_ID **/
create or replace trigger tri_sys_operation
before insert on sys_operation
for each row
begin
   select seq_sys_operation.nextval into :NEW.OPERATION_PERMISSION_ID from dual;
end;
/

/**Identity for the column ROLE_ID **/
create or replace trigger tri_sys_role
before insert on sys_role
for each row
begin
   select seq_sys_role.nextval into :NEW.ROLE_ID from dual;
end;
/

/**Identity for the column USER_ID **/
create or replace trigger tri_sys_user
before insert on sys_user
for each row
begin
   select seq_sys_user.nextval into :NEW.USER_ID from dual;
end;
/



/**Data for the table module_dept **/

insert  into module_dept(deptno,dname,loc) values (1,'开发部','深圳');
insert  into module_dept(deptno,dname,loc) values (2,'测试部','北京');
insert  into module_dept(deptno,dname,loc) values (3,'研究院','上海');

/**Data for the table module_emp **/

insert  into module_emp(empno,ename,job,deptno) values (1,'张三','开发工程师',1);
insert  into module_emp(empno,ename,job,deptno) values (2,'李四','开发工程师',1);
insert  into module_emp(empno,ename,job,deptno) values (3,'王五','管理员',3);
insert  into module_emp(empno,ename,job,deptno) values (4,'孙六','测试主管',1);
insert  into module_emp(empno,ename,job,deptno) values (5,'钱七','测试工程师',2);

/**Data for the table sys_menu **/

insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (1,'EasyEE - SSH','',NULL,0,'icon-home2','');
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (2,'系统管理',NULL,1,0,'icon-application_view_tile','系统维护管理，系统管理员拥有');
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (3,'用户管理','SysUser/page',2,0,'icon-user',NULL);
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (4,'角色管理','SysRole/page',2,1,'icon-grade',NULL);
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (5,'菜单权限管理','SysMenuPermission/page',2,2,'icon-menu',NULL);
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (6,'操作权限管理','SysOperationPermission/page',2,3,'icon-rights',NULL);
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (7,'员工管理',NULL,1,1,'icon-report','人事部操作');
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (8,'部门信息管理','Dept/page',7,1,'icon-group','');
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (9,'员工信息管理','Emp/page',7,2,'icon-id','');
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (10,'报表管理',NULL,1,3,'icon-chart_bar','经理查看');
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (11,'统计报表','toReports',10,0,'icon-chart_curve','');
insert  into sys_menu(MENU_PERMISSION_ID,NAME,ACTION,PARENT_ID,SORT_NUM,ICON,REMARK) values (12,'系统日志','SysLog/page',2,5,'icon-book','系统日志查看');


/**Data for the table sys_operation **/

insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (1,5,'查询菜单列表','SysMenuPermission/list','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (2,5,'修改菜单','SysMenuPermission/update','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (3,5,'删除菜单','SysMenuPermission/delete','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (4,5,'移动菜单次序','SysMenuPermission/move','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (5,5,'添加菜单','SysMenuPermission/save','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (6,6,'菜单列表查询','SysMenuPermission/list','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (7,6,'查询菜单对应的操作权限列表','SysOperationPermission/list','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (8,6,'新增操作权限','SysOperationPermission/save','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (9,6,'修改操作权限','SysOperationPermission/update','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (10,6,'删除操作权限','SysOperationPermission/delete','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (11,4,'查询所有角色','SysRole/list','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (12,4,'添加角色','SysRole/save,SysMenuPermission/listAllForSysRole','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (13,4,'修改角色','SysRole/update#SysRole/getAllPermissionsId,SysMenuPermission/listAllForSysRole','修改角色需要获得用户的相关角色权限');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (14,4,'删除角色','SysRole/delete','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (15,3,'查询用户列表','SysUser/list','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (16,3,'添加用户','SysUser/save,SysRole/all','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (17,3,'修改用户','SysUser/update,SysRole/all','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (18,3,'删除用户','SysUser/delete','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (19,3,'显示添加用户按钮','SysUserAddBtn','显示权限');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (20,3,'显示删除用户按钮','SysUserDelBtn','显示权限');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (21,3,'显示修改用户按钮','SysUserUpdateBtn','显示权限');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (22,3,'显示真实姓名列信息','showRealNameColumn','显示权限');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (23,8,'添加新部门','Dept/save','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (24,8,'修改部门','Dept/update','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (25,8,'删除部门','Dept/delete','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (26,8,'查询部门列表','Dept/list','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (27,8,'显示动作-删除部门','deptDeleteShow','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (28,9,'添加员工','Emp/save','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (29,9,'修改员工','Emp/update,Emp/allDept','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (30,9,'删除员工','Emp/delete','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (31,9,'查看员工列表','Emp/list,Emp/allDept','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (32,12,'查询日志','SysLog/list','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (33,1,'后台管理中心权限','toMain','登录到后台必须授予');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (34,3,'查询角色列表','SysRole/all','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (35,4,'查询菜单权限和操作权限','SysRole/getAllPermissionsId,sysMenuPermission/listAll,sysMenuPermission/listAllForSysRole','');
insert  into sys_operation(OPERATION_PERMISSION_ID,MENU_PERMISSION_ID,NAME,ACTION,REMARK) values (36,1,'修改个人密码','SysUser/changePwd','可以执行Change Password功能');


/**Data for the table sys_role **/

insert  into sys_role(ROLE_ID,NAME,STATUS,REMARK) values (1,'超级管理员',0,'拥有所有管理权限');
insert  into sys_role(ROLE_ID,NAME,STATUS,REMARK) values (2,'系统管理员',0,'管理系统用户和权限分配。\r\n不能删除用户，不显示删除按钮；不显示真实姓名');
insert  into sys_role(ROLE_ID,NAME,STATUS,REMARK) values (3,'HR',0,'员工管理模块');
insert  into sys_role(ROLE_ID,NAME,STATUS,REMARK) values (4,'经理',0,'报表查看');
insert  into sys_role(ROLE_ID,NAME,STATUS,REMARK) values (5,'演示用户',0,'展示系统功能');

/**Data for the table sys_role_menu **/

insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,1);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (2,1);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (3,1);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (4,1);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,1);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,2);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (2,2);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,2);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,3);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (2,3);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,3);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,4);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (2,4);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,4);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,5);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (2,5);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,5);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,6);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (2,6);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,6);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,7);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (3,7);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,7);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,8);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (3,8);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,8);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,9);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (3,9);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,9);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,10);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (4,10);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,10);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,11);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (4,11);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,11);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (1,12);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (2,12);
insert  into sys_role_menu(ROLE_ID,MENU_PERMISSION_ID) values (5,12);


/**Data for the table sys_role_operation **/

insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,1);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,1);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,1);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,2);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,2);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,3);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,4);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,4);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,5);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,5);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,6);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,6);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,6);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,7);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,7);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,7);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,8);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,8);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,9);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,9);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,10);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,10);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,11);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,11);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,11);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,12);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,12);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,13);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,13);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,14);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,15);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,15);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,15);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,16);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,16);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,17);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,17);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,18);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,19);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,19);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,19);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,20);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,20);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,21);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,21);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,21);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,22);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,22);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,22);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,23);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (3,23);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,23);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,24);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (3,24);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,24);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,25);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (3,25);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,25);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,26);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (3,26);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,26);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,27);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,27);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,28);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (3,28);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,28);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,29);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (3,29);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,29);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,30);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (3,30);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,30);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,31);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (3,31);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,31);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,32);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,32);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,32);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,33);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (2,33);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (3,33);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (4,33);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,33);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,34);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,34);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,35);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (5,35);
insert  into sys_role_operation(ROLE_ID,OPERATION_PERMISSION_ID) values (1,36);

/**Data for the table sys_user **/

insert  into sys_user(USER_ID,NAME,PASSWORD,STATUS,REAL_NAME) values (1,'admin','172eee54aa664e9dd0536b063796e54e',0,'晁关');
insert  into sys_user(USER_ID,NAME,PASSWORD,STATUS,REAL_NAME) values (2,'user','37cf6e1a4cd5a940ae416392ac26768d',0,'蒲关');
insert  into sys_user(USER_ID,NAME,PASSWORD,STATUS,REAL_NAME) values (3,'hr','16f60453bf87e1625f811c295e1a34fc',0,'任立');
insert  into sys_user(USER_ID,NAME,PASSWORD,STATUS,REAL_NAME) values (4,'manager','357d5b7e9946c6bfb8d91140c31f7074',0,'荆力');
insert  into sys_user(USER_ID,NAME,PASSWORD,STATUS,REAL_NAME) values (5,'demo','ae41281904e75830f26c0465f53772bb',0,'戴谋');


/**Data for the table sys_user_role **/

insert  into sys_user_role(USER_ID,ROLE_ID) values (1,1);
insert  into sys_user_role(USER_ID,ROLE_ID) values (2,2);
insert  into sys_user_role(USER_ID,ROLE_ID) values (3,2);
insert  into sys_user_role(USER_ID,ROLE_ID) values (4,3);
insert  into sys_user_role(USER_ID,ROLE_ID) values (5,5);

commit;
