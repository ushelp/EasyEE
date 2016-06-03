# EasyEE-SSH Architecture Manual

EasyEE is a basic framework JaveEE background development. Background for the enterprise provides the infrastructure project development and specifications, and provide common components based on users, roles, rights management system privileges program (support page displays access control) and security management framework, front end uses EasyUI framework.



## EasyEE-SSH
 Based on Struts2,Hibernate4, Spring4 of EasyEE infrastructure development background. Include component: 

   - `SSH`: JAVA framework and related technical background and rights management module
   
   - `EasyUI`: Based on the front-end framework jQuery
   
   - `EasyUIEx`: EasyUI of extensions（[API](http://www.easyproject.cn/easyuiex "EasyUIEx API")）
   
   - `EasyShiro`:  Shiro of extensions（[API](http://www.easyproject.cn/easyshiro "EasyShiro API")）
   
   - `EasyFilter`: Jave Web content filtering requests replacement component, illegal keyword filtering （[API](http://www.easyproject.cn/easyfilter "EasyFilter API")）


## The latest version information

- **EasyEE-SSH version： 2.1.0**

 ```
 <!-- SSH Version: Update on 2016/06/01 -->
 <struts.version>2.5</struts.version>
 <hibernate.version>5.1.0.Final</hibernate.version>
 <spring.version>4.2.6.RELEASE</spring.version>
 <druid.version>1.0.20</druid.version>
 <shiro.version>1.2.5</shiro.version>
 <easyshiro.version>2.3.0-RELEASE</easyshiro.version>
 <easyfilte.version>2.0.1-RELEASE</easyfilte.version>
 ```

- **Notice:**
Struts provides enhanced security access model after 2.5 `Strict Method Invocation (Strict DMI, SMI)`, if you want to call a method by wildcards or DMI must be configured to allow in `struts_easyssh_default.xml` method name:
```
<!-- Allowed Methods for Strict DMI -->
<global-allowed-methods>execute,input,back,cancel,browse,save,delete,list,index,move,update,getAllPermissionsId,changePwd,regex:(list.*),regex:(all.*)</global-allowed-methods>
```


## Framework Manual

1. Project structure 
2. Quick Development Manual 
3. Other configuration instructions





##  Project structure 
```
 EasySSH 
    + src/main/java 
        + cn.easyproject.easyssh 
            + base (Base Common Package)
                + action 
                    + BaseAction.java（All Action extends BaseAction）
                + dao
                    + CommonDAO.java 
                    + impl
                        + CommonDAOHibernateImpl.java 
                + service
                    + BaseService.java (All Service extends BaseService)
                + util 
                    + ... (PageBean, Encryption, Utils....)
            + sys (RBAC Package)
                + action, entity, service, util, criteria... 
            + moudle  (Demo Package)
                + action, entity, service, criteria...
    + src/main/resource 
        + applicationContext_bean_scan.xml (Spring Bean scanner)
        + applicationContext.xml (Spring Core configuration)
        + ApplicationResources.properties (i18n)
        + db.properties (jdbc info) 
        + easyFilter.properties (EasyFilter Web request filter)
        + ehcache.xml (ehcache )
        + log4j.properties (Log4J)
        + log4j2.xml (Log4J2)
        + struts.xml (Struts2 include)
        + struts_easyssh_default.xml (Struts2 default parent package)
        + struts_easyssh_dispatcher.xml (Struts2 dispatcher)
        + struts_easyssh_sys.xml (RBAC)
        + struts.properties (Struts2 properties)
    + src/test/java 
    + src/test/resource 
    + WebRoot
        + easyssh 
            + json (easyssh json: theme, icon)
            + easyssh.main.js (easyssh core js)
            + jquery.cookie.js 
        + easyui (EasyUI )
        + easyuiex (EasyUIEx)
        + echarts (EChars)
        + error (JSP Error Page)
        + images
        + jsp 
            + echarts (ECharts demo)
            + error 
                + notFound.jsp (404 error)
                + permissionDenied.jsp (no permission)
                + serverError.jsp (500 error)
            + VerifyCode.jsp （CAPTCHA JSP）
        + script (project module js, with WEB-INF/content)
            + main
                + sys (RBAC)
                + main.js (main.jsp)
            + login.js (login.jsp) 
        + style 
            + easyssh.main.css (easyssh golbal css)
        + WEB-INF
            + content (project page)
                + dialog (EasyUI Dialog)
                    + sys (RBAC)
                    + module (Demo module)
                + main (EasyUI Core page)
                    + sys (RBAC page)
                    + module (Demo Page)
                    + main.jsp (EasySSH Main Page)
                + login.jsp 
            + lib 
            + web.xml
         + toLogin.action (redirect to WEB-INF/content/login.jsp) 
```


## Quick Development Manual 

1. **Create new package**
   - e.g.:
   
    **cn.easyproject.easyssh.`module`**
    **cn.easyproject.easyssh.module.`entity`**
    **cn.easyproject.easyssh.module.`service`**
    **cn.easyproject.easyssh.module.`action`**
    **cn.easyproject.easyssh.module.`criteria`**
    
2. **Create POJO in entity package**

      - Annotaion
      ```JAVA
      @Entity
      @Table(name = "Emp", catalog = "EASYSSH")
      public class Emp implements java.io.Serializable {
     		//...
      }
      ``` 
      - XML
      ```XML
      <?xml version="1.0" encoding="utf-8"?>
      <!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
      "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
      <hibernate-mapping>
          <class name="cn.easyproject.easyssh.module.entity.Emp" table="module_emp" catalog="easyssh">
              <!-- ... -->
          </class>
      </hibernate-mapping>
      ```

3. **Create Service in service package**
   - Interface
   ```JAVA
     public interface DeptService {
     	public void add(Dept dept);
     
     	public void delete(Integer deptno);
     
     	public void update(Dept dept);
     
     	public Dept get(Integer deptno);
     
     	public void findByPage(PageBean pageBean, DeptCriteria deptCriteria);
     
     	public int findMaxPage(int rowPerPage);
     	
     	public List<Dept> findAll();
     }
      ```
    
   - Implements in `service.impl`
     - extends BaseService
     - direct invoke commonDAO method
     - use @Service
      ```JAVA
       /**
        * extends BaseService
        * direct invoke commonDAO method
        *  
        * @author easyproject.cn
        * @version 1.0
        *
        */
       @Service("deptService")
       public class DeptServiceImpl extends BaseService implements DeptService {
       
       	@Override
       	public void add(Dept dept) {
       		commonDAO.save(dept);
       	}
       
       	@Override
       	public void delete(Integer deptno) {
       		commonDAO.updateByHql("delete from Dept where deptno=?",deptno);
       	}
       
       	@Override
       	public void update(Dept dept) {
       		commonDAO.update(dept);
       	}
       
       	@Override
       	public Dept get(Integer deptno) {
       		return commonDAO.get(Dept.class, deptno);
       	}
       
       	@Override
       	public void findByPage(PageBean pageBean, DeptCriteria deptCriteria) {
       		pageBean.setEntityName("Dept dept");
       		pageBean.setSelect("select dept");
       		
       		// 按条件分页查询
       		commonDAO.findByPage(pageBean,deptCriteria);
       	}
       
       	@Override
       	public int findMaxPage(int rowPerPage) {
       		return (commonDAO.findMaxPage("select count(*) from Dept", rowPerPage)-1)/rowPerPage+1;
       	}
       
       	@SuppressWarnings("unchecked")
       	@Override
       	public List<Dept> findAll() {
       		return commonDAO.find("from Dept");
       	}
       
       }
     ``` 

    - Register Transaction AOP Support for Servie (`applicationContext.xml`)
      ```XML
     	<!-- AOP -->
     	<aop:config>
     		<!--  Transaction AOP -->
     		<aop:advisor advice-ref="txAdvie"
     			pointcut="execution(* cn.easyproject.easyssh.sys.service..*.*(..))" />
     		<!-- Your module.... -->
     		<aop:advisor advice-ref="txAdvie"
     			pointcut="execution(* cn.easyproject.easyssh.module.service..*.*(..))" />
     	</aop:config>
      ```

4. **Create Action Controller**

     ```JAVA
        /**
         * Must extends BaseAction
         * 
         * BaseAction includes:
         * - request, application Servlet API
         * - JSON parameters
         * - Init JSON data method（setJsonMap，setJsonMsgMap，setJsonPaginationMap(PageBean, Object...)）
         * - EasyUI Pagination parameter
         * - JSON constant: result="json" 
         *
         */
       public class DeptAction extends BaseAction {
       	
       	// variable name equals @Service("deptService"), need setter method
       	private DeptService deptService;
       	
       	private Dept dept;
       	
       	/**
       	 * Pagination
       	 * @return
       	 */
       	public String list(){
       		PageBean pb = super.getPageBean(); // get PageBean
       		deptService.findByPage(pb,dept);
       		// Out JSON include PageBean
       		super.setJsonPaginationMap(pb);
       		return JSON;
       	}
       	
       	/**
       	 * CRUD
       	 * @return
       	 */
       	public String save(){
       		// save 
       		try {
       			deptService.add(dept);
       			
       			// Message
       			msg = getText("msg.saveSuccess");
       
       			// if need go to last page
       //			super.page = deptService.findMaxPage(rows);
       		} catch (Exception e) {
       			e.printStackTrace();
       			msg = getText("msg.saveFail");
       			statusCode=StatusCode.ERROR; // Default is OK
       		}
       		
       		/*
       		 * Ajax response info
       		 * {
       		 * statusCode：status code, 
       		 * msg: message,
       		 * callback: callback function,
       		 * locationUrl: go to location
       		 * }
       		 */
       		// JSON out, include Message
       //		super.setJsonMsgMap();
       		// update datagrid info
       		super.setJsonMsgMap("rowData", dept);

       		return JSON;
       	}
       	
       	public String delete(){
       		try {
       			deptService.delete(dept.getDeptno());
       		} catch (Exception e) {
       			e.printStackTrace();
       			statusCode=StatusCode.ERROR;
       		}
       		super.setJsonMsgMap();
       		return JSON;
       	}
       	
       	public String update(){
       		try {
       			deptService.update(dept);
       			msg=getText("msg.updateSuccess");
       			super.refreshPermissions(); //refreshPermissions
       		} catch (Exception e) {
       			e.printStackTrace();
       			msg=getText("msg.updateFail");
       			statusCode=StatusCode.ERROR;
       		}
       		setJsonMsgMap();
       		return JSON;
       	}
       
       	public Dept getDept() {
       		return dept;
       	}
       
       	public void setDept(Dept dept) {
       		this.dept = dept;
       	}
       
       	public void setDeptService(DeptService deptService) {
       		this.deptService = deptService;
       	}
       	
       }
       ```

  - Configuration struts.xml
    
    1. Create struts xml in `src/main/resources/struts_easyssh_module.xml`
    
       ```XML
       <?xml version="1.0" encoding="UTF-8" ?>
       <!DOCTYPE struts PUBLIC "-//Apache Software Foundation//DTD Struts Configuration 2.1//EN" "http://struts.apache.org/dtds/struts-2.1.dtd">
       <struts>
       	<!-- Your Module package -->
       	<!-- Must extends easyssh-default -->
       	<package name="easyssh-module" extends="easyssh-default" namespace="/">
            		<!-- ALL Response JSON -->
            		<action name="dept_*" class="cn.easyproject.easyssh.module.action.DeptAction" method="{1}">
            		</action>		
            		<action name="emp_*" class="cn.easyproject.easyssh.module.action.EmpAction" method="{1}">
            		</action>		
       	</package>
       </struts>    
       ```
    2. include struts xml in`struts.xml``struts_easyssh_module.xml`:
       ```XML
       <?xml version="1.0" encoding="UTF-8" ?>
       <!DOCTYPE struts PUBLIC "-//Apache Software Foundation//DTD Struts Configuration 2.1//EN" "http://struts.apache.org/dtds/struts-2.1.dtd">
       <struts>
            	<!-- easyssh 默认主系统配置模块包 -->
            	<include file="struts_easyssh_default.xml"></include>
            	<!-- 页面转发跳转配置包 -->
            	<include file="struts_easyssh_dispatcher.xml"></include>
            	<!-- EasySSH 权限系统配置包 -->
            	<include file="struts_easyssh_sys.xml"></include>
            	
            	<!-- 自定义模块开发配置文件 - 员工管理 -->
            	<include file="struts_easyssh_module.xml"></include>
       </struts>    
       ```

5. **Page**

   - Create folder：
   
      - `WebRoot/WEB-INF/content/main/module`（EasyUI page）
       
      - `WebRoot/WEB-INF/content/dialog/module`(EasyUI Dialog page)
      
   - Develop Page
      - EasyEE use `EasyUI Tabs` of `href` include page,. 
      
      > EasyUI Two Dynamic Tabs difference between dynamic loading:
      > 
      > **-Use content (iframe frame) the introduction page:**
      >     content : '<iframe scrolling="auto" frameborder="0" src="'+ url + '" style="width:100%;height:100%;"></iframe>';
      >  Exist as a separate window, the page content independent, non-interfering with the current page
      >  Need to introduce independent JS and CSS resources needed
      >  Pop-up content is within the window
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

6. **And configure access permissions**
   1. In `struts_easyssh_dispatcher.xml` configure access under WEB-INF JSP view of the action request
       ```XML
      <?xml version="1.0" encoding="UTF-8" ?>
      <!DOCTYPE struts PUBLIC "-//Apache Software Foundation//DTD Struts Configuration 2.1//EN" "http://struts.apache.org/dtds/struts-2.1.dtd">
      <struts>
      	<!-- easyssh dispatcher /WEB-INF/ page-->
      	<package name="easyssh-dispatcher" extends="easyssh-default" namespace="/">
      		
      		<!-- ... -->
      
      		<action name="toDept">  
      			  <result>/WEB-INF/content/main/module/dept.jsp</result>
      		</action>
      		<action name="toEmp">  
      			  <result>/WEB-INF/content/main/module/emp.jsp</result>
      		</action>
      	</package>
      </struts>    
       ```
   2. User Admin to Add `Menu Permission`(toDept.action, click on the menu to access the action requested JSP views)
   
   3.  User Admin to Add `Operation Permission`(CRUD Permission, Show Controller Permission)
   
   4. Assign `permissions and menu` operations for user
 


## Other configuration instructions
### 1. EasySSH Ajax Response
- EasySSH Ajax Response JSON:
   ```JSON
  {
    statusCode：status code,  
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
  - JSON result constant： ` final String JSON = "json";`
  - JSON parameter(pagination, Ajax response)
  - JSON response method(`setJsonRoot`, `setJsonMap`, `setJsonMsgMap`, `setJsonPaginationMap`)
  - reloadPermissions(): refreshing the user's current permission after to modify permissions
  - utils
   
  
### 3.  CommonDAO

CommonDAO persistence layer provides a common operation, a package of various daily methods of operation, including a process based on PageBean and EasyCriteria paging and search criteria components. 

### 4. PageBean分页和查询条件处理

- EasySSH use PageBean, invoke `findByPage(PageBean)` to pagination: 
   ```JAVA
  // Demo: 
  //		PageBean pb = new PageBean();
  //		pb.setSelect(" select new Account(id,username) ");// select
  //		pb.setEntityName("Account"); // from entityname
  //		pb.setPageNo(1); // 
  //		pb.setRowsPerPage(4);//
  //		pb.setCondition(" and id>2");// 
  //		pb.addCondition(" and name='A'"); //
  //		pb.setSort("id");// 
  //		
  //		commonDAO.findByPage(pb); //execute
  //
  //		System.out.println(pb.getPageNo());// 
  //		System.out.println(pb.getRowsPerPage());// 
  //		System.out.println(pb.getData());// 
  //		System.out.println(pb.getRowsCount());// 
  //		System.out.println(pb.getPageTotal());// 
   ```

- EasySSH use EasyCriteria to defined search criteria `findByPage(PageBean, EasyCriteria)`:

  - **Crate Criteria class(implements getCondition(), return condition string)**
     ```JAVA
    /**
     * SysUser search criteria
     * 
     * @author easyproject.cn
     * @version 1.0
     *
     */
    public class SysUserCriteria extends EasyCriteria implements java.io.Serializable {
    	/*
    	 * 1. condition property
    	 */
    	private String name;
    	private String realName;
    	private Integer status; // user status：0 on；1 off；2 delete
    
   	  /*
    	 * 2. 
    	 */
    	public SysUserCriteria() {
    	}
    
    	public SysUserCriteria(String name, String realName, Integer status) {
    		super();
    		this.name = name;
    		this.realName = realName;
    		this.status = status;
    	}
    	
   	  /*
    	 * 3. You can implements this method
    	 */
    	public String getCondition() {
    		values.clear(); //clear condition
    		StringBuffer condition = new StringBuffer();
    		if (StringUtils.isNotNullAndEmpty(this.getName())) {
    			condition.append(" and name like ?");
    			values.add("%" + this.getName() + "%");
    		}
    		if (StringUtils.isNotNullAndEmpty(this.getRealName())) {
    			condition.append(" and realName like ?");
    			values.add("%" + this.getRealName() + "%");
    		}
    		if (StringUtils.isNotNullAndEmpty(this.getStatus())) {
    			condition.append(" and status=?");
    			values.add(this.getStatus());
    		}
    		return condition.toString();
    	}
   
   	  /*
    	 * 4. Setters & Getters...
    	 */ 
    }
   ```
    
   - Service 
   ```JAVA
  	@Override
  	public void findByPage(PageBean pb,SysUserCriteria sysUserCriteria) {
  		pb.setEntityName("SysUser s");
  		pb.setSelect("select s");
  		
  	 // Use EasyCriteria
  		commonDAO.findByPage(pb, sysUserCriteria);
  	}
    ```

### 5. Rights Profile
- **Access Configuration**

  1. Adding menu rights and operation permissions
  
  2. The menu for the role permissions and rights are allocated
 
  3. Assign roles to users
 
- **Show Controller(e.g. WEB-INF/content/jsp/module/dept.jsp)**

  1. Show Permissions Configuration Permissions action in operation

  2. Include `easyssh-tags` taglig, You will need to display the contents of the definition of control in `shiro:hasPermission` within,` name` specified must have permission to display the name of the action
  
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
  - EasyCommons-imageutils-1.4.jar
  
    Provide a picture compression, picture address extraction, image watermarking tools. 

  - EasyCommons-objectutils-1.7.1.jar
  
    Provided instead of Java Properties object properties file operations component.

  - EasyCommons-propertiesutils-1.4.jar
  
    Provided (`FieldExpression`) field expression object attribute extraction based on object attribute filter, waited in vain for the object attribute is set Obejct object manipulation components.
 
       
### 7. EasyUIEx
#### [EasyUIEx API](http://easyproject.cn/easyuiex "EasyUIEx API")
EasyUIEx use jQuery EasyUI framework for the development of extensible plug-ins, mainly for common functions EasyUI framework encapsulates and expansion EasyUI can greatly increase the ease of use, simplify operational complexity, and provide additional functionality.

Use during project development jQuery EasyUI + EasyUIEx architecture can greatly simplify the complexity EasyUI framework uses, especially in the various aspects of CRUD data grid, he made a high degree of packaging.

In the project, a priority search EasyUIEx API functions can be done to achieve a multiplier effect.
  


## End

[Comments](http://www.easyproject.cn/easyee/en/index.jsp#about 'Comments')

If you have more comments, suggestions or ideas, please contact me.

Email：<inthinkcolor@gmail.com>

<p>
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_blank">
<input type="hidden" name="cmd" value="_xclick">
<input type="hidden" name="business" value="inthinkcolor@gmail.com">
<input type="hidden" name="item_name" value="EasyProject development Donation">
<input type="hidden" name="no_note" value="1">
<input type="hidden" name="tax" value="0">
<input type="image" src="http://www.easyproject.cn/images/paypaldonation5.jpg"  title="PayPal donation"  border="0" name="submit" alt="Make payments with PayPal - it's fast, free and secure!">
</form>
</P>


[http://www.easyproject.cn](http://www.easyproject.cn "EasyProject Home")