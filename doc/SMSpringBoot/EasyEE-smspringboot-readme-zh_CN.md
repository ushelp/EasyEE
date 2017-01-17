# EasyEE-SM-SpringBoot 平台开发手册

EasyEE 是一个开源 JaveEE 企业级快速开发基础平台，提供多种技术方案选择，支持 Spring Boot, Hibernate, MyBatis, Struts, Shiro 等核心框架。

整合了广泛使用的 JaveEE 领域优秀框架及 EasyUI 前端框架；提供了基于用户、角色、权限方案的后台权限管理系统，安全管理框架及常用开发组件。为企业级项目开发提供了基础架构和规范。

提供 [EasyEE Auto](https://github.com/ushelp/EasyEE-Auto 'EasyEE Auto') 自动化代码生成器。

最新版本： `4.2.0`

## SM-Spring Boot Framework

- **Spring Boot 1.4.2**
- **MyBatis 3.4+**
- **Shiro 2+**
- EasyCommonDAO
- EasyShiro 
- EasyFilter
- EasyUI 1.4.3
- EasyUIEx 2.2.0

## Maven framework version

```XML
<java.version>1.7</java.version>
<tomcat.version>8.5.8</tomcat.version>

<spring.version>4.3.4.RELEASE</spring.version>
<springboot.version>1.4.2.RELEASE</springboot.version>
<mybatis.version>3.4.1</mybatis.version>
<org.mybatis.spring.boot.version>1.1.1</org.mybatis.spring.boot.version>
<mysql.version>5.1.34</mysql.version>
<oracle.version>11.2.0.4</oracle.version>

<slf4j.version>1.7.21</slf4j.version>
<log4j2.version>2.7</log4j2.version>
<druid.version>1.0.26</druid.version>
<commonsfileupload.version>1.3.2</commonsfileupload.version>
<gson.version>2.8.0</gson.version>
<jackson.version>2.8.4</jackson.version>
<ognl.version>3.1.12</ognl.version>
<shiro.version>1.2.5</shiro.version>
<easyshiro.version>2.5.0-RELEASE</easyshiro.version>
<easyfilte.version>2.0.1-RELEASE</easyfilte.version>
<jstl.version>1.2</jstl.version>
```


## EasyEE Auto

EasyEE 自动化代码生成器。

[EasyEE Auto](https://github.com/ushelp/EasyEE-Auto 'EasyEE Auto') 




## 文档目录

1. 项目结构

2. 快速开发手册

3. Run with WAR or JAR

4. 其他配置说明



## 项目结构

```JS
EasySM-SpringBoot
    + src/main/java (源码目录)
        + cn.easyproject.easyee.sm (包前缀)
            + base (项目基础功能公共组件包)
            	+ configuration (Spring Boot Configuration)
                + controller 
                    + BaseController.java(基础Controller，所有Controller类继承BaseController)
                + pagination (EasyMyBatis-Pagination)
                + service
                    + BaseService.java (基础Service，所有Service类都基础BaseService)
                + util 
                    + ... (PageBean、EasyCriteria, 加密、日期处理等日常开发和项目所需的工具类)
            + sys (权限管理系统实现包)
                + controller, entity, dao, service, util, shiro, criteria... 
            + hr  (自定义模块开发包，演示了一个员工管理的Demo)
                + controller, entity, dao, service, criteria...
		    + EasyEEJarApplication.java (Run as jar)
		    + EasyEEWarApplication.java (Run as war)
    + src/main/resource (源码资源目录)
        + i18n
            + messages.properties (i18n)
            + messages_zh_CN.properties (i18n)
        + mybatis
        	+ mapper
        		+ hr
        		+ sys
        	+ mybatis-config.xml
        + spring
            + spring_shiro.xml (Spring Shiro)
        + static (web 静态资源...)
        	+ doc
        	+ error
        	+ script (项目开发页面相关JS文件，和WEB-INF/content下的页面一一对应)
	            + main
	                + sys (系统权限模块JS...)
	                + main.js (系统主页面JS)
	            + login.js (登录页面JS) 
        	+ staticresources
	            + easyee (easyssh核心js文件)
	                + json (easyssh所需的JSON文件——主题，图标)
	                + easyee-sh.main.js (easyssh核心JS，全局Ajax请求响应处理)
	                + jquery.cookie.js (cookie处理)
	            + easyui (EasyUI 前端框架)
	            + easyuiex (EasyUIEx 扩展插件)
	            + echarts (EChars 前端图形报表组件)
	            + error (JSP错误页面)
	            + images (图片)
	            + style (css样式表)
	                + easyee.main.css (easyssh 系统全局css)
        + application.properties (Spring Boot properties)
        + banner.txt (Spring Boot Banner) 
        + easyFilter.properties (EasyFilter Web请求内容过滤替换组件配置文件)
        + ehcache.xml (ehcache 二级缓存配置 )
        + log4j.properties (Log4J)
        + log4j2.xml (Log4J2)
        + logback.xml (LogBack)
    + src/test/java (测试源码目录)
    + src/test/resource (测试资源目录)
    + src/main/webapp (JSP dynamic web resources)
        + META-INF
        + WEB-INF
        +  jsp (一般JSP页面)
            + echarts (ECharts demo)
            + error (系统错误页面)
                + notFound.jsp (404错误提示页面)
                + permissionDenied.jsp (权限不足提示页面)
                + serverError.jsp (400错误提示页面)
            + VerifyCode.jsp (验证码生成JSP)
        + locklogin
            + admin.jsp (EasyShiro LockLogin Management)
        + script (项目开发页面相关JS文件，和WEB-INF/content下的页面一一对应)
            + main
                + sys (系统权限模块JS...)
                + main.js (系统主页面JS)
            + login.js (登录页面JS) 
        + staticresources
            + easyee (easyssh核心js文件)
                + json (easyssh所需的JSON文件——主题，图标)
                + easyee-sh.main.js (easyssh核心JS，全局Ajax请求响应处理)
                + jquery.cookie.js (cookie处理)
            + easyui (EasyUI 前端框架)
            + easyuiex (EasyUIEx 扩展插件)
            + echarts (EChars 前端图形报表组件)
            + error (JSP错误页面)
            + images (图片)
            + style (css样式表)
                + easyee.main.css (easyssh 系统全局css)
        + src/main/webapp (JSP 动态 Web 资源)
            + content (项目核心页面)
                + dialog (EasyUI Dialog 相关页面)
                    + sys (权限系统模块相关Dialog页面)
                    + hr (自定义开发模块，员工管理Demo相关Dialog页面)
                + error 
                 	+ controllerError.jsp (Controller error)
	                + notFound.jsp (404 error)
	                + permissionDenied.jsp (no permission)
	                + serverError.jsp (500 error)
                + jsp (一般JSP页面)
		            + echarts (ECharts demo)
		            + VerifyCode.jsp (验证码生成JSP)
		        + locklogin
		            + admin.jsp (EasyShiro LockLogin Management)
                + main (EasyUI 核心页面)
                    + sys (权限系统模块相关页面)
                    + hr (自定义开发模块，员工管理Demo相关页面)
                    + reports
                    + main.jsp (EasySSH登录后的主页面)
                + login.jsp (登录页面)
```


## 快速开发指导手册

1. **创建新模块包结构**
    
 cn.easyproject.easyee.sm.`yourmodule`
 cn.easyproject.easyee.sm.yourmodule.`entity`
 cn.easyproject.easyee.sm.yourmodule.`dao` 
 cn.easyproject.easyee.sm.yourmodule.`service` 
 cn.easyproject.easyee.sm.yourmodule.`controller` 
 cn.easyproject.easyee.sm.yourmodule.`criteria`

2. **编写POJO实体类代码**

 在`entity`下根据表创建 Entity 实体类
       
 ```JAVA
 public class Dept implements java.io.Serializable {
 	//...
 }
 ```

3. **编写  MyBatis DAO 接口和  SQL 映射文件**

 - DAO Mapper interface
  
   ```JAVA
	public interface DeptDAO {
			
		public void save(Dept dept);
		public void delete(Serializable deptno);
		public void update(Dept dept);
		public Dept get(Integer deptno);
		
		public List<Dept> findAll();
		public int findMaxRow();
		
		@SuppressWarnings("rawtypes")
		public List pagination(PageBean pageBean);
	}
   ```
 
 - SQL Mapper XML(src/main/resource/mybatis/mapper/`yourmodule`/DeptDAO.xml)
 
    ```XML
    <?xml version="1.0" encoding="UTF-8"?>
	<!DOCTYPE mapper
	  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="cn.easyproject.easyee.sm.hr.dao.DeptDAO">
	
		<insert id="save">
			insert into module_dept(dname,loc) values(#{dname},#{loc})
		</insert>
		<delete id="delete">
			delete from module_dept where deptno=#{deptno}
		</delete>
		<update id="update">
			update module_dept set dname=#{dname},loc=#{loc} where deptno=#{deptno}
		</update>
		<select id="get" resultType="Dept">
			select * from module_dept where deptno=#{deptno}
		</select>
	
		<select id="findAll" resultType="Dept">
			select * from module_dept
		</select>
		<select id="findMaxRow" resultType="int">
			select count(*) from module_dept
		</select>
		
		<!-- EasyMyBatis-Pagination -->
		<select id="pagination" resultType="Dept">
			${autoSQL}
		</select>
	</mapper>
    ```


4. **编写Service代码**

 - 在 `service` 下编写业务接口， 使用 `@Transactional` 声明事务
 
    ```JAVA
	@Transactional
	public interface DeptService {
		public void save(Dept dept);
		public void delete(Serializable deptno);
		public void update(Dept dept);
		@Transactional(readOnly=true)
		public Dept get(Integer deptno);
	
		@SuppressWarnings("rawtypes")
		@Transactional(readOnly=true)
		public void findByPage(PageBean pageBean, DeptCriteria deptCriteria);
		
		@Transactional(readOnly=true)
		public List<Dept> findAll();
	}
    ```
    
 - 在 `service.impl` 下编写业务接口对应的实现类
 
      - 业务实现类统一继承 BaseService 类
      
      - 使用@Service声明Bean
      
     ```JAVA
	/**
	 * 业务实现类统一继承BaseService类
	 * 
	 * @author easyproject.cn
	 * @version 1.0
	 *
	 */
	@Service("deptService")
	public class DeptServiceImpl extends BaseService implements DeptService {
	
		@Resource
		DeptDAO deptDAO;
		
		@Override
		public void save(Dept dept) {
			deptDAO.save(dept);
		}
	
		@Override
		public void delete(Serializable deptno) {
			deptDAO.delete(deptno);
		}
	
		@Override
		public void update(Dept dept) {
			deptDAO.update(dept);
		}
	
		@Override
		public Dept get(Integer deptno) {
			return deptDAO.get(deptno);
		}
	
		@SuppressWarnings({ "rawtypes"})
		@Override
		public void findByPage(PageBean pageBean, DeptCriteria deptCriteria) {
			pageBean.setFrom("module_dept dept");
			pageBean.setSelect("dept.*");
			pageBean.setEasyCriteria(deptCriteria);
			// 按条件分页查询
			deptDAO.pagination(pageBean);
		}
	
		@Override
		public List<Dept> findAll() {
			return deptDAO.findAll();
		}
	}
     ``` 
 
5. **编写  Controller 控制器**
 
 ```JAVA
	/**
	 * Must extends BaseController
	 * 
	 * BaseController中定义了一下内容：
	 * - request, application Servlet API 
	 * - 请求响应相关的JSON参数(EasyUI框架请求都是通过JSON响应) 
	 * - 初始化JSON响应数据的方法(setJsonMap，setJsonMsgMap，setJsonPaginationMap(PageBean, Object...)) 
	 * - 获得分页对象：super.getPageBean() 
	 * 
	 * @author easyproject.cn
	 * @version 1.0
	 *
	 */
	@RestController
	@RequestMapping("Dept")
	public class DeptController extends BaseController {
	
		@Resource
		private DeptService deptService;
		
		@Resource
		private EmpService empService;
		
		/**
		 * 转向显示页面
		 * @return
		 */
		@RequestMapping("page")
		public ModelAndView page(ModelAndView mv){
			mv.setViewName("main/hr/Dept");
			return mv;
		}
		/**
		 * CRUD
		 * @return
		 */
		@RequestMapping("save")
		public Map<Object,Object> save(Dept dept){
			// 保存
			try {
				deptService.save(dept);
				// 处理成功 消息
				super.setMsgKey("msg.saveSuccess");
			} catch (Exception e) {
				e.printStackTrace();
				super.setMsgKey("msg.saveFail");
				super.setStatusCode(StatusCode.ERROR); //默认为OK
			}
			
			/*
			 * Ajax响应信息
			 * statusCode: 响应状态码;  
			 * msg: 响应消息;   
			 * callback: 执行回调函数,
			 * locationUrl: 跳转页面
			 */
			// EasyUI框架响应结果都是JSON
			// JSON数据初始化，包含EasySSH Ajax响应信息
			// super.setJsonMsgMap();
			
			
			// 添加数据后，使用rowData信息更新行的内容
			// 返回JSON
			return super.setJsonMsgMap("rowData", dept);
			
			// 如果需要刷新，跳转到最后一页
	//		int page = deptService.findMaxPage(rows);
	//		return super.setJsonMsgMap("rowData", dept, "page", page);
		}
		
		/**
		 * 分页
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		@RequestMapping("list")
		public Map<Object,Object> list(DeptCriteria deptCriteria){
			PageBean pb = super.getPageBean(); // 获得分页对
			deptService.findByPage(pb,deptCriteria);
			// EasyUI框架响应结果都是JSON
			// JSON数据初始化，包含EasySSH Ajax响应信息和分页信息
			return super.setJsonPaginationMap(pb);
		}
	
		@RequestMapping("delete")
		public Map<Object,Object> delete(Dept dept){
			try {
				if(empService.findEmpCountByDeptno(dept.getDeptno())==0){
					deptService.delete(dept.getDeptno());
				}else{
					super.setMsgKey("hr.DeptController.empExists");
					super.setStatusCode(StatusCode.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.setStatusCode(StatusCode.ERROR); //默认为OK
			}
			return super.setJsonMsgMap();
		}
		
		@RequestMapping("update")
		public Map<Object,Object> update(Dept dept){
			try {
				deptService.update(dept);
				super.setMsgKey("msg.updateSuccess");
			} catch (Exception e) {
				e.printStackTrace();
				super.setMsgKey("msg.updateFail");
				super.setStatusCode(StatusCode.ERROR); //默认为OK
			}
			
			return super.setJsonMsgMap();
		}
	}
 ```

6. **页面开发**

   - 在主目录下创建存放新模块页面的目录，如：
   
      - `webapp/WEB-INF/content/main/yourmodule`(EasyUI 页面主目录)
       
      - `webapp/WEB-INF/content/dialog/yourmodule`(EasyUI Dialog页面主目录)
      
   - 按照 EasyUI 框架指导和 Demo 开发页面
      - EasyEE 使用 `EasyUI Tabs` 的 `href` 属性引入 tab 子页面, 不是 content(iframe 框架) 属性, 所以各个模块页面是和当前页面合并在一起, 无需在子页面重复引入主页面的 JS, CSS 等. 
      
      > EasyUI Tabs两种动态动态加载方式之间的区别：
      > 
      > **-使用content(iframe框架)引入页面:**
      >     content : '<iframe scrolling="auto" frameborder="0" src="'+ url + '" style="width:100%;height:100%;"></iframe>';
      >   作为独立窗口存在, 页面内容独立, 与当前页面互不干扰
      >   需要独立引入需要的JS和CSS资源
      >   弹出的内容是在内部窗口内
      >
      > **-使用href方法：**
      >     href : url,
      >   内容片段加载, 引入的内容和当前页面合并在一起
      >   不需要引入页面已经引入的JS和CSS资源
      >   引用的页面不能有body, 否则加载的内容内部的JS文件文法执行
      >   会显示html渲染解析的提示
      >   要防止页面间DOM元素, JS的命名冲突(DOM命名要使用统一前缀唯一, JS使用命名空间)
      
      - 为了防止页面间DOM元素命名冲突, 应该为每个页面的DOM元素使用统一的唯一前缀
      - 为了防止页面间JavaScript变量命名冲突, 应该为每个页面定义唯一的操作命名空间, 所有函数注册进命名空间
      - 默认开发推荐结构如下：
      ```JSP
        <%-- 1. 页面Datagrid初始化相关JS --%>
        
        <script type="text/javascript">
        // 为了防止命名冲突, 为每个页面定义唯一的操作命名空间
        // 所有函数注册进命名空间
        var Dept = {};
        $(function() {
        	/*
        	 * datagrid数据格式化
        	 */
        	// ...
        	
        	/*
        	 * 数据表格初始化
        	 */
        	
        	/*
        	 * 数据表格CRUD
        	 */
        		
        	/*
        	 * 搜索
        	 */
        	
        });
        </script>
        
        <%-- 2. 页面内容, 无需Body --%>
        <!--  ... -->
        
        <%-- grid右键菜单 --%>
        <!--  ... -->
        
        <%-- 3. 包含的Dialog页面等其他内容 --%>
        <!--  ... -->
      ```

7. **访问和权限配置**

   1. 通过系统管理员, 添加新模块`菜单权限`(`/Dept/page`, 点击菜单访问 JSP 视图的 action 请求)
   
   2. 通过系统管理员, 添加新模块`操作权限`(增删改查的 controller 请求, 显示控制的 action 名称)
   
   3. 针对用户角色按需`分配菜单权限和操作权限`
 
 
## Run with WAR or JAR

### Run with WAR

1. Use `pom_war.xml` to `pom.xml`

2. Uncomment the comment, Run `EasyEEWarApplication.java` 


### Run with JAR

1. Use `pom_jar.xml` to `pom.xml`

2. Uncomment the comment, Run `EasyEEJarApplication.java` 



## 其他配置开发说明

### 1. EasySSH Ajax响应信息

- EasySSH Ajax 响应消息结构:

  ```JSON
  {
    statusCode: 响应状态码,  
    msg: 响应消息,
    callback: 执行回调函数,
    locationUrl: 跳转页面,
    //... 其他数据
  }
   ```

- BaseAction 中输出 JSON 结果相关的方法：

   ```JAVA
   // 自定义JSON输出根对象   
   setJsonRoot(Object);
   // JSON数据初始化, 包含自定义JSON键值对
   setJsonMap(Object...)
   // JSON数据初始化, 包含自定义JSON键值对, 及EasySSH Ajax响应信息
   setJsonMsgMap(Object...)
   // JSON数据初始化, 包含自定义JSON键值对, 分页信息, 及EasySSH Ajax响应信息
   setJsonPaginationMap(PageBean, Object...)
   ```
  

### 2. BaseAction 

BaseAction 中定义了以下主要内容：

   - Servlet API(request, application )
   - 获得分页对象：getPageBean() 
   - JSON参数(请求响应相关的EasyUI分页参数, EasySSH Ajax消息参数)
   - JSON响应初始化工具方法
   - reloadPermissions()：刷新用户当前的权限，用于修改权限后
   - 工具方法
   
  
#### PageBean分页和查询条件处理
	
- DAO interface:
	```JAVA
	public class EmpDAO{
	  public List pagination(PageBean pageBean);
	  // ...
	}
	```

- SQL Mapper:
	```XML
	<select id="pagination" resultType="Emp">
	        ${autoSQL}
	</select>
	```
- Service:

   ```JAVA
   // 查询接口
   @Transactional
	public interface EmpService {
		//...
		
		// Pagination
		@Transactional(readOnly=true)
		public void findByPage(PageBean pageBean,EmpCriteria empCriteria); // EmpCriteria 参数可选
	}
	
	// 查询实现类
	@Service("empService")
	public class EmpServiceImpl extends BaseService implements EmpService {
		
		@Resource
		EmpDAO empDAO;
		
		@Override
		public void findByPage(PageBean pageBean, EmpCriteria empCriteria) {
			pageBean.setEasyCriteria(empCriteria);
		
			pageBean.setSelect("e.empno, e.ename, e.job, d.deptno, d.dname");
			pageBean.setFrom(" module_emp e, module_dept d ");
			pageBean.addCondition("and e.deptno=d.deptno");
			pageBean.setPrimaryTable("e");
			
			// 按条件分页查询
			empDAO.pagination(pageBean);
		}
	
		//...
	}
   ```
   
- PageBean 查询设置示例 1：

 设置语法： `SELECT <select> FROM <from> WHERE <conditions> OREDER BY <order> <sortOrder>,<lastSort>,[primaryTable.ROWID]`

	```JAVA
	PageBean pb=new PageBean();
	// SELECT 语句; 可选; 默认为 *
	pb.setSelect("*"); 
	// From 语句; 必须
	pb.setFrom("Account account");
	// WHERE 语句; 可选; 默认为 ''
	pb.setCondition(" and account.qxid>=10");
	// 追加 WHERE 条件; 可选; 默认为 ''
	//pb.addCondition(""); 
	// 排序列名; 可选; 默认为 ''
	pb.setSort("account.accountid");
	// 排序方式; 可选; 默认为 'asc'
	pb.setSortOrder("desc");
	// 当前页数; 可选; 默认为 1
	pb.setPageNo(1);
	// 每页条数; 可选; 默认为 10
	pb.setRowsPerPage(4);
	
	// 按条件分页查询
	xxxDAO.pagination(pageBean);
	```


- PageBean 查询设置示例 2：

   ```JAVA
	PageBean pb=new PageBean();
	pb.setPageNo(2);
	pb.setRowsPerPage(5);
	// data sql
	pb.setSql("select * from Emp where empno<=80 and ename like #{ename} limit 10,5"); 
	// total sql
	pb.setCountSQL("select count(*) from Emp where empno<=80 and ename like #{ename}"); 
	
	// Set parameter values
	Map<String, Object> values=new HashMap<String,Object>();
	values.put("ename", "%a%");
	pb.setSqlParameterValues(values);
	
	xxxDAO.pagination(pageBean);
   ```

#### EasyCriteria 条件查询

1. 创建 **EasyCriteria** 类, 必须 `extends EasyCriteria implements Serializable`

2. 编写条件方法 `getCondition()`

- Example
    
    ```JAVA
	/**
	 * 
	 * @author easyproject.cn
	 * @version 1.0
	 *
	 */
	public class SysUserCriteria extends EasyCriteria implements java.io.Serializable {
	
		// Fields
		private static final long serialVersionUID = 1L;
		/*
	 	 * 1. 条件属性
	 	 */
		private String name;
		private String status;
		
		 /*
	 	 * 2. 条件生成抽象方法实现
	 	 */
		public String getCondition() {
			values.clear(); //清除条件数据
			StringBuffer condition = new StringBuffer();
			if (StringUtils.isNotNullAndEmpty(this.getDname())) {
				condition.append(" and dname like #{dname}");
				values.put("dname", "%" + this.getDname() + "%");
			}
			if (StringUtils.isNotNullAndEmpty(this.getLoc())) {
				condition.append(" and loc like #{loc}");
				values.put("loc", "%" + this.getLoc() + "%");
			}
			return condition.toString();
		}
		 /*
	 	 * 3. Setters & Getters...
	 	 */ 
	}
    ```

- Usage

    ```JAVA
    PageBean pb=new PageBean();
    pageBean.setSelect("*");
    pageBean.setFrom("SysUser");
    
    
    // EasyCriteria
    SysUserCriteria usersCriteria =new SysUserCriteria();
    usersCriteria.setName("A");
    usersCriteria.setStatus(0);
    
    pb.setEasyCriteria(usersCriteria);
    
    // Find by EasyCriteria
    xxxDAO.pagination(pageBean);
    ```

### 5. 权限配置

- **访问权限配置**

  1. 添加菜单权限和操作权限
  
  2. 为角色分配菜单权限和操作权限
 
  3. 为用户分配角色 
 
- **显示控制权限配置**

  1. 在操作权限中配置显示权限动作

  2. 在JSP页面引入`siro-tags`标签库, 将需要显示控制的内容定义在`shiro:hasPermission`内, `name`指定必须具有的显示权限动作名称 
  
	   ```JSP
	   <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
	 
	  	<shiro:hasPermission name="deptDeleteShow">
	  	   	<div onclick="$('#deptDataGrid').edatagrid('destroyRow')" data-options="iconCls:'icon-remove'">Delete</div>
	  	</shiro:hasPermission>
	   ```
	   
  3. 按需为用户分配显示权限

### 6. 附加组件：

- **EasyShiro** ([API](http://easyproject.cn/easyshiro/zh-cn/index.jsp#readme "EasShiro API"))

 EasyShiro 是一个基于 Shiro 的安全扩展组件。为基于数据库权限管理和 Web URL 授权 的RBAC(Role Based Access Control) Web 权限模型，提供通用支持。EasyShiro 能简化安全集成，并提供验证码，自动登录，登录锁定，错误消息管理，拦截器，Ajax 响应等等更强大全面的功能支持，仅需简单配置即可。


- **EasyFilter Jave Web请求内容过滤替换组件** ([API](http://easyproject.cn/easyfilter/zh-cn/index.jsp#readme "EasyFilter 请求内容过滤替换组件"))

 EasyFilter是一个Jave Web请求内容过滤替换组件, 支持使用properties或xml配置文件自定义过滤配置, 能够对用户请求中的以下信息进行过滤替换：

  1. 特殊字符替换(如：&lt;, &gt;特殊标记, 脚本等)
  
  2. 非法关键字替换(如：网络系统中国情不允许的特殊关键词)
  
  3. SQL防注入过滤(如：%,*,or,delete,and等等SQL特殊关键字)

  **在 `easyFilter.xml` 中已经预定义了默认的替换配置。**

- **EasyCommons 通用开发组件**([API](http://easyproject.cn/easycommons/zh-cn/index.jsp#readme "EasyCommons 通用开发组件"))

  - EasyCommons-imageutils
  
    提供图片压缩, 图片地址提取, 图片水印等工具类。

  - EasyCommons-objectutils
  
    提供代替Java Properties对象的properties文件操作组件。

  - EasyCommons-propertiesutils
  
    提供基于`字段表达式(FieldExpression)`对象属性抽取, 对象属性过滤, 对象属性置空等Obejct对象操作组件。
 
       
### 7. EasyUIEx

[EasyUIEx](http://easyproject.cn/easyuiex "EasyUIEx API")  是针对使用 `jQuery EasyUI` 框架开发的扩展性插件，主要对`EasyUI`框架的常用功能进行了封装和扩展。着重在CRUD方面进行了封装扩展，能大大提高`EasyUI`的易用性，简化操作的复杂性，并提供附加功能。

在进行项目开发时使用 jQuery EasyUI + EasyUIEx 架构能大大简化 EasyUI 框架使用的复杂性, 尤其在各种数据网格的 CRUD 方面, 做了高度封装. 

在项目中, 优先查询 EasyUIEx API 来完成功能能达到事半功倍的作用. 
  
## Demo

[Demo Online](http://www.easyproject.cn/easyee 'Demo')

Username: `demo`

Password: `111111`

![Demo](../images/easyee.png)


## END
### [官方主页](http://www.easyproject.cn/easyee/zh-cn/index.jsp '官方主页')

[留言评论](http://www.easyproject.cn/easyee/zh-cn/index.jsp#donation '留言评论')

如果您有更好意见，建议或想法，请联系我。

### [The official home page](http://www.easyproject.cn/easyee/en/index.jsp 'The official home page')

[Comments](http://www.easyproject.cn/easyee/en/index.jsp#donation 'Comments')

If you have more comments, suggestions or ideas, please contact me.



Email：<inthinkcolor@gmail.com>

[http://www.easyproject.cn](http://www.easyproject.cn "EasyProject Home")



**支付宝钱包扫一扫捐助：**

我们相信，每个人的点滴贡献，都将是推动产生更多、更好免费开源产品的一大步。

**感谢慷慨捐助，以支持服务器运行和鼓励更多社区成员。**

<img alt="支付宝钱包扫一扫捐助" src="http://www.easyproject.cn/images/s.png"  title="支付宝钱包扫一扫捐助"  height="256" width="256"></img>


We believe that the contribution of each bit by bit, will be driven to produce more and better free and open source products a big step.

**Thank you donation to support the server running and encourage more community members.**

[![PayPal](http://www.easyproject.cn/images/paypaldonation5.jpg)](https://www.paypal.me/easyproject/10 "Make payments with PayPal - it's fast, free and secure!")


