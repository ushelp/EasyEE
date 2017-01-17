# EasyEE-SM Platform Development Manual

EasyEE is an open source JaveEE enterprise-class rapid development of the basic platform, provide a variety of technical options options, support Spring Boot, Hibernate, MyBatis, Struts, Shiro and other core framework.

It integrates the widely used framework of JaveEE domain and EasyUI front-end framework. It provides the back-end rights management system, security management framework and common development components based on user, role and permission scheme. Provides infrastructure and specifications for enterprise-level project development.

[EasyEE Auto](https://github.com/ushelp/EasyEE-Auto 'EasyEE Auto') automated code generator is provided.

Latest version:  `4.2.0`

## SM Framework

- **Spring 4+(SpringMVC)**
- **MyBatis 3.4+**
- **Shiro 2+**
- EasyMyBatis-Pagination
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

EasyEE Automated code generator。

[EasyEE Auto](https://github.com/ushelp/EasyEE-Auto 'EasyEE Auto') 




## Framework Manual

1. Project structure 

2. Quick Development Manual 

3. Other configuration instructions



##  Project structure 
```JS
EasySM 
    + src/main/java 
        + cn.easyproject.easyee.sm
            + base (Base Common Package)
                + controller 
                    + BaseController.java(All Controller extends BaseController)
                + pagination (EasyMyBatis-Pagination) 
                + service
                    + BaseService.java (All Service extends BaseService)
                + util 
                    + ... (PageBean, EasyCriteria, Encryption, Utils....)
            + sys (RBAC Package)
                + action, entity, dao, service, util, shiro, criteria... 
            + hr  (Demo Module Package)
                + action, entity, dao, service, criteria...
    + src/main/resource
        + i18n
            + messages.properties (i18n)
            + messages_zh_CN.properties (i18n)
        + mybatis
        	+ mapper
        		+ hr
        		+ sys
        	+ mybatis-config.xml
        + spring
            + ApplicationContext_bean.xml (Spring Bean scanner)
            + ApplicationContext_dao.xml (Spring DAO)
            + ApplicationContext_mvc.xml (Spring MVC)
            + ApplicationContext_shiro.xml (Spring Shiro)
            + ApplicationContext.xml (Spring Core configuration)
        + db.properties (jdbc info) 
        + easyFilter.properties (EasyFilter Web request filter)
        + ehcache.xml (ehcache )
        + log4j.properties (Log4J)
        + log4j2.xml (Log4J2)
    + src/test/java 
    + src/test/resource 
    + WebRoot
        + doc
        + jsp 
            + echarts (ECharts demo)
            + error 
                + notFound.jsp (404 error)
                + permissionDenied.jsp (no permission)
                + serverError.jsp (500 error)
            + VerifyCode.jsp （CAPTCHA JSP）
        + locklogin
            + admin.jsp (EasyShiro LockLogin Management)
        + script (project module js, with WEB-INF/content)
            + main
                + sys (RBAC)
                + main.js (main.jsp)
            + login.js (login.jsp) 
        + staticresources
            + easyee 
                + json (easyssh json: theme, icon)
                + easyee-sh.main.js (easyssh core js)
                + jquery.cookie.js 
            + easyui (EasyUI )
            + easyuiex (EasyUIEx)
            + echarts (EChars)
            + error (JSP Error Page)
            + images
            + style 
                + easyee.main.css (easyssh golbal css)
        + WEB-INF
            + content (project page)
                + dialog (EasyUI Dialog)
                    + sys (RBAC)
                    + hr (Demo module)
                + main (EasyUI Core page)
                    + sys (RBAC page)
                    + hr (Demo Page)
                    + main.jsp (EasySSH Main Page)
                + login.jsp 
            + lib 
            + web.xml
```


## Quick Development Manual 

1. **Create new moudle package**
   
 cn.easyproject.easyssh.`yourmodule`
 cn.easyproject.easyssh.yourmodule.`entity`
 cn.easyproject.easyssh.yourmodule.`dao`
 cn.easyproject.easyssh.yourmodule.`service`
 cn.easyproject.easyssh.yourmodule.`controller`
 cn.easyproject.easyssh.yourmodule.`criteria`
 
2. **Create Entity Class in entity package(Annotaion or XML)**

  ```JAVA
  public class Dept implements java.io.Serializable {
 		//...
  }
  ```
  

3. **Create MyBatis DAO interface and  SQL Mapper file**

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


4. **Create Service in service package**

 - Interface, Use `@Transactional` to declare transaction
 
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
 
 - Implements in `service.impl`
 
    - extends BaseService
    
    - use @Service
 
    ```JAVA
	/**
	 * Must extends BaseService
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
			deptDAO.pagination(pageBean);
		}
	
		@Override
		public List<Dept> findAll() {
			return deptDAO.findAll();
		}
	}
    ``` 

5. **Create a Controller**

 ```JAVA
	/**
	 * Must extends BaseController
	 * 
	 * BaseController: 
	 * - request, application Servlet API 
	 * - JSON result
	 * - JSON response method: setJsonMap,setJsonMsgMap,setJsonPaginationMap(PageBean, Object...) 
	 * - Get PageBean Object：super.getPageBean() 
	 * 
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
		 * Forward page
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
			try {
				deptService.save(dept);
				super.setMsgKey("msg.saveSuccess");
			} catch (Exception e) {
				e.printStackTrace();
				super.setMsgKey("msg.saveFail");
				super.setStatusCode(StatusCode.ERROR); //default is OK
			}
			
			/*
			 * Ajax
			 * {
			 * statusCode: ;  
			 * msg: ;   
			 * callback: ,
			 * locationUrl: 
			 * }
			 */
			// super.setJsonMsgMap();
			
			
			// When add data, to update page row data
			return super.setJsonMsgMap("rowData", dept);
			
			// if you need goto last page
	//		int page = deptService.findMaxPage(rows);
	//		return super.setJsonMsgMap("rowData", dept, "page", page);
		}
		
		/**
		 * Pagination
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		@RequestMapping("list")
		public Map<Object,Object> list(DeptCriteria deptCriteria){
			PageBean pb = super.getPageBean(); // Get PageBean object
			deptService.findByPage(pb,deptCriteria);
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
				super.setStatusCode(StatusCode.ERROR); // Default is OK
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
				super.setStatusCode(StatusCode.ERROR); // Default is OK
			}
			
			return super.setJsonMsgMap();
		}
	}
 ```

6. **Page**

   - Create folder：
   
      - `webapp/WEB-INF/content/main/yourmodule`（EasyUI page）
       
      - `webapp/WEB-INF/content/dialog/yourmodule`(EasyUI Dialog page)
      
   - Develop Page
      - EasyEE use `EasyUI Tabs` of `href` include page,. 
      
      > EasyUI Two Dynamic Tabs difference between dynamic loading:
      > 
      > **-Use content (iframe frame) the introduction page:**
      >     content : '<iframe scrolling="auto" frameborder="0" src="'+ url + '" style="width:100%;height:100%;"></iframe>';
      >  Exist as a separate window, the page content independent, non-interfering with the current page
      >  Need to introduce independent JS and CSS resources needed
      >  Pop-up content is within the window
      > 
      > **-Use href methods:**
      >     href : url,
      >  A piece of content is loaded, the introduction of the contents of the current page and merge
      >  Need to introduce JS and CSS resource page has introduced
      >  Reference page can not have a body, or the contents of the internal loads of grammar JS file execution
      >  Html rendering prompt resolution displays
      >  To prevent inter-page DOM element, naming conflicts of JS (DOM unique name should be the same prefix, JS using namespaces)
      
      -To prevent unique prefix naming conflicts between pages DOM elements, you should use unified DOM element for each page
      - In order to prevent inter-page JavaScript variable naming conflicts should be defined only operating namespace for each page, all registration functions into the namespace
      - Recommended default development is structured as follows:
      ```JSP
        <%-- 1. Page Datagrid initialization related JS --%>
        
        <script type="text/javascript">
        // To avoid naming conflicts, define a unique operating namespace for each page
        // All functions registered into the namespace
        var sysRole = {};
        $(function() {
        	/*
        	 * datagrid init
        	 */
        	// ...

        	/*
        	 * datagrid CRUD
        	 */
        		
        	/*
        	 * datagird search
        	 */
        	
        });
        </script>
        
        <%-- 2. content, don't need Body --%>
        <!--  ... -->
        
        <%-- grid contextmenu --%>
        <!--  ... -->
        
        <%-- 3. Dialog Page--%>
        <!--  ... -->
      ```

7. **And configure access permissions**
   
   1. User Admin to Add `Menu Permission`(`/Dept/page`, click on the menu to access the action requested JSP views)
   
   2.  User Admin to Add `Operation Permission`(CRUD Permission, Show Controller Permission)
   
   3. Assign `permissions and menu` operations for user


## Other configuration instructions

### 1. EasySSH Ajax Response

- EasySSH Ajax Response JSON:

   ```JSON
  {
    statusCode: status code,  
    msg: message,
    callback: callback function,
    locationUrl: goto location,
    //... other data
  }
   ```

- BaseAction out JSON method：

   ```JAVA
   // you can define JSON root data 
   setJsonRoot(Object);
   // JSON include your Object
   setJsonMap(Object...)
   // JSON include your Object and EasySSH Ajax response
   setJsonMsgMap(Object...)
   // JSON include your Object, Pagination data and EasySSH Ajax response
   setJsonPaginationMap(PageBean, Object...)
   ```
  

### 2. BaseAction 

  - Servlet API(request, application )
  - Get PageBean：super.getPageBean() 
  - JSON parameter(pagination, Ajax response)
  - JSON response method(`setJsonRoot`, `setJsonMap`, `setJsonMsgMap`, `setJsonPaginationMap`)
  - reloadPermissions(): refreshing the user's current permission after to modify permissions
  - utils
   
### 3. EasyMyBatis-Pagination

[EasyMyBatis-Pagination](https://github.com/ushelp/EasyMyBatis-Pagination 'EasyMyBatis-Pagination') Is a generic paging plug-in for the MyBaits framework. Provides the PageBean automatic paging data encapsulation, the EasyCriteria paging condition object, and the automated paging SQL that supports databases based on both the `Mappers` interface and `SQLID`.

The EasyEE MyBaits version integrates this plug-in to complete paging.


#### PageBean paging and query condition processing
	
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
   // Query interface
   @Transactional
	public interface EmpService {
		//...
		
		// Pagination
		@Transactional(readOnly=true)
		public void findByPage(PageBean pageBean,EmpCriteria empCriteria); // EmpCriteria parameters are optional
	}
	
	// Query implementation class
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
			
			// Paged by condition query
			empDAO.pagination(pageBean);
		}
	
		//...
	}
   ```
   
- PageBean Query Settings Example 1:

 Setting syntax: `SELECT <select> FROM <from> WHERE <conditions> OREDER BY <order> <sortOrder>,<lastSort>,[primaryTable.ROWID]`

	```JAVA
	PageBean pb=new PageBean();
	// SELECT Statement; optional; default is  *
	pb.setSelect("*"); 
	// From Statement; required
	pb.setFrom("Account account");
	// WHERE Statement; optional; default is  ''
	pb.setCondition(" and account.qxid>=10");
	// Additional WHERE condition; optional; default is  ''
	//pb.addCondition(""); 
	// Column sorting; optional; default is  ''
	pb.setSort("account.accountid");
	// Sorting method; optional; default is  'asc'
	pb.setSortOrder("desc");
	// The current page; optional; default is  1
	pb.setPageNo(1);
	// Page number; optional; default is  10
	pb.setRowsPerPage(4);
	
	// Condition paging query
	xxxDAO.pagination(pageBean);
	```


- PageBean query settings example 2:

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

#### EasyCriteria criteria queries

1. Create **EasyCriteria** Class, must `extends EasyCriteria implements Serializable`

2. create method `getCondition()`

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
	 	 * 1. fields
	 	 */
		private String name;
		private String status;
		
		 /*
	 	 * 2. core method
	 	 */
		public String getCondition() {
			values.clear(); //clear
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


### 5. Rights Profile

- **Access Configuration**

  1. Adding menu rights and operation permissions
  
  2. The menu for the role permissions and rights are allocated
 
  3. Assign roles to users
 
- **Show Controller(e.g. WEB-INF/content/jsp/module/dept.jsp)**

  1. Show Permissions Configuration Permissions action in operation

  2. Include `siro-tags` taglig, You will need to display the contents of the definition of control in `shiro:hasPermission` within,` name` specified must have permission to display the name of the action
  
    ```JSP
   <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
 
 	<shiro:hasPermission name="deptDeleteShow">
 	   	<div onclick="$('#deptDataGrid').edatagrid('destroyRow')" data-options="iconCls:'icon-remove'">Delete</div>
 	</shiro:hasPermission>
   ```

  3. Assign rights for users


### 6. Add-ons:

- **EasyShiro** ([API](http://easyproject.cn/easyshiro/en/index.jsp#readme "EasShiro API"))

 EasyShiro based Shiro is a security extension components. Database rights management and Web URL authorization RBAC (Role Based Access Control) Web-based permission model provides universal support. EasyShiro simplifies security integration, and verification code, auto login, login is locked, an error message management, interceptors, Ajax, etc. in response to a more powerful and comprehensive feature support, only a simple configuration can be.


- **EasyFilter Jave Web Request paramtere filter** ([API](http://easyproject.cn/easyfilter/zh-cn/index.jsp#readme "EasyFilter API"))
 
  EasyFilter is a Jave Web content filtering request replacement of a component, supports the use of properties or xml configuration file to customize the filter configuration, the user can request the following information to filter replacement:

  1. Replace special characters (such as: & lt ;, & gt; special tags, scripts, etc.)
  
  2. Illegal keyword substitution (such as: network situation does not allow China's special keywords)
  
  3. SQL-injection filter (eg:%, *, or, delete, and so on SQL special keyword)

  **In `easyFilter.xml` has predefined default alternative arrangement.**

- **EasyCommons** ([API](http://easyproject.cn/easycommons/zh-cn/index.jsp#readme "EasyCommons API"))

  - EasyCommons-imageutils
  
    Provide a picture compression, picture address extraction, image watermarking tools. 

  - EasyCommons-objectutils
  
    Provided instead of Java Properties object properties file operations component.

  - EasyCommons-propertiesutils
  
    Provided (`FieldExpression`) field expression object attribute extraction based on object attribute filter, waited in vain for the object attribute is set Obejct object manipulation components.
 
       
### 7. EasyUIEx

[EasyUIEx API](http://easyproject.cn/easyuiex "EasyUIEx API") use jQuery EasyUI framework for the development of extensible plug-ins, mainly for common functions EasyUI framework encapsulates and expansion EasyUI can greatly increase the ease of use, simplify operational complexity, and provide additional functionality.

Use during project development jQuery EasyUI + EasyUIEx architecture can greatly simplify the complexity EasyUI framework uses, especially in the various aspects of CRUD data grid, he made a high degree of packaging.

In the project, a priority search EasyUIEx API functions can be done to achieve a multiplier effect.
  

## Demo

[Demo Online](http://www.easyproject.cn/easyee 'Demo')

Username: `demo`

Password: `111111`

![Demo](../images/easyee.png)


## End

[Demo - English](http://www.easyproject.cn/easyee/en/index.jsp#demo 'Demo - English]')


[The official home page](http://www.easyproject.cn/easyee/en/index.jsp 'The official home page')

[Comments](http://www.easyproject.cn/easyee/en/index.jsp#donation 'Comments')

If you have more comments, suggestions or ideas, please contact me.

Contact, Feedback, Custom, Train Email：<inthinkcolor@gmail.com>

[http://www.easyproject.cn](http://www.easyproject.cn "EasyProject Home")



We believe that the contribution of each bit by bit, will be driven to produce more and better free and open source products a big step.

**Thank you donation to support the server running and encourage more community members.**

[![PayPal](http://www.easyproject.cn/images/paypaldonation5.jpg)](https://www.paypal.me/easyproject/10 "Make payments with PayPal - it's fast, free and secure!")








## END
### [The official home page](http://www.easyproject.cn/easyuiex/en/index.jsp 'The official home page')

[Comments](http://www.easyproject.cn/easyuiex/en/index.jsp#donation 'Comments')

If you have more comments, suggestions or ideas, please contact me.


### [官方主页](http://www.easyproject.cn/easyuiex/zh-cn/index.jsp '官方主页')

[留言评论](http://www.easyproject.cn/easyuiex/zh-cn/index.jsp#donation '留言评论')

如果您有更好意见，建议或想法，请联系我。




Email：<inthinkcolor@gmail.com>

[http://www.easyproject.cn](http://www.easyproject.cn "EasyProject Home")





We believe that the contribution of each bit by bit, will be driven to produce more and better free and open source products a big step.

**Thank you donation to support the server running and encourage more community members.**

[![PayPal](http://www.easyproject.cn/images/paypaldonation5.jpg)](https://www.paypal.me/easyproject/10 "Make payments with PayPal - it's fast, free and secure!")

