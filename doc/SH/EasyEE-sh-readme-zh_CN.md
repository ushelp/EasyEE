# EasyEE-SH(JPA) 平台开发手册

EasyEE 是一个开源 JaveEE 企业级快速开发基础平台，提供多种技术方案选择，支持 Spring Boot, Hibernate, MyBatis, Struts, Shiro 等核心框架。

整合了广泛使用的 JaveEE 领域优秀框架及 EasyUI 前端框架；提供了基于用户、角色、权限方案的后台权限管理系统，安全管理框架及常用开发组件。为企业级项目开发提供了基础架构和规范。

提供 [EasyEE Auto](https://github.com/ushelp/EasyEE-Auto 'EasyEE Auto') 自动化代码生成器。

最新版本： `4.2.0`

## SH Framework

- **Spring 4+(SpringMVC)**
- **Hibernate 5+(JPA)**
- **Shiro 2+**
- EasyCommonDAO
- EasyShiro 
- EasyFilter
- EasyUI 1.4.3
- EasyUIEx 2.2.0


## Maven framework version

```XML
<hibernate.version>5.2.4.Final</hibernate.version>
<spring.version>4.3.4.RELEASE</spring.version>

<slf4j.version>1.7.21</slf4j.version>
<log4j2.version>2.7</log4j2.version>
<mysql.version>5.1.34</mysql.version>
<oracle.version>11.2.0.4</oracle.version>
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

3. 其他配置说明



## 项目结构

```JS
EasySH （项目）
    + src/main/java (源码目录)
        + cn.easyproject.easyee.sh (包前缀)
            + base (项目基础功能公共组件包)
                + controller 
                    + BaseController.java（基础Controller，所有Controller类继承BaseController）
                + dao
                    + CommonDAO.java (通用DAO接口)
                    + impl
                        + CommonDAOSpringImpl.java (通用DAO实现类)
                + service
                    + BaseService.java (基础Service，所有Service类都基础BaseService)
                + util 
                    + ... (PageBean、EasyCriteria, 加密、日期处理等日常开发和项目所需的工具类)
            + sys (权限管理系统实现包)
                + controller, entity, service, util, shiro, criteria... 
            + hr (自定义模块开发包，演示了一个员工管理的Demo)
                + controller, entity, service, criteria...
    + src/main/resource (源码资源目录)
        + i18n
            + messages.properties (i18n)
            + messages_zh_CN.properties (i18n)
        + spring
            + ApplicationContext_bean.xml (Spring Bean scanner)
            + ApplicationContext_dao.xml (Spring DAO)
            + ApplicationContext_shiro.xml (Spring Shiro)
            + ApplicationContext.xml (Spring Core configuration)
        + db.properties (数据库连接参数配置) 
        + easyFilter.properties (EasyFilter Web请求内容过滤替换组件配置文件)
        + ehcache.xml (ehcache 二级缓存配置 )
        + log4j.properties (Log4J)
        + log4j2.xml (Log4J2)
    + src/test/java (测试源码目录)
    + src/test/resource (测试资源目录)
    + WebRoot (Web根目录)
        + doc
        +  jsp （一般JSP页面）
            + echarts (ECharts demo)
            + error (系统错误页面)
                + notFound.jsp (404错误提示页面)
                + permissionDenied.jsp (权限不足提示页面)
                + serverError.jsp (400错误提示页面)
            + VerifyCode.jsp （验证码生成JSP）
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
        + WEB-INF
            + content (项目核心页面)
                + dialog (EasyUI Dialog 相关页面)
                    + sys (权限系统模块相关Dialog页面)
                    + hr (自定义开发模块，员工管理Demo相关Dialog页面)
                + main (EasyUI 核心页面)
                    + sys (权限系统模块相关页面)
                    + hr (自定义开发模块，员工管理Demo相关页面)
                    + main.jsp (EasySSH登录后的主页面)
                + login.jsp (登录页面)
            + lib (系统jar包，包含SSH、druid连接池、easyFilter、开发常用组件等等)
            + web.xml (部署描述符文件)
```


## 快速开发指导手册

1. **创建新模块包结构**

 cn.easyproject.easyee.sh.`yourmodule`
 cn.easyproject.easyee.sh.yourmodule.`entity`
 cn.easyproject.easyee.sh.yourmodule.`service`
 cn.easyproject.easyee.sh.yourmodule.`controller`
 cn.easyproject.easyee.sh.yourmodule.`criteria`

2. **编写POJO实体类代码**

 在`entity`下根据表创建POJO实体类
       
 ```JAVA
 @Entity
 @Table(name = "Dept")
 public class Dept implements java.io.Serializable {
 	//...
 }
 ```

3. **编写Service代码**

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
		public int findMaxPage(int rowPerPage);
		
		@Transactional(readOnly=true)
		public List<Dept> findAll();
	}
    ```
    
 - 在 `service.impl` 下编写业务接口对应的实现类
 
      - 业务实现类统一继承BaseService类
      
      - BaseService中注入了通用DAO，直接调用commonDAO的数据方法方法即可
      
      - 使用@Service声明Bean
      
     ```JAVA
      /**
       * 业务实现类统一继承BaseService类
       * BaseService中注入了通用DAO，直接调用commonDAO的数据方法方法即可
       * 
       * @author easyproject.cn
       * @version 1.0
       *
       */
      @Service("deptService")
		public class DeptServiceImpl extends BaseService implements DeptService {
		
			@Override
			public void save(Dept dept) {
				commonDAO.persist(dept);
			}
		
			@Override
			public void delete(Serializable deptno) {
				commonDAO.updateByJpql("delete from Dept where deptno=?",deptno);
			}
		
			@Override
			public void update(Dept dept) {
				commonDAO.merge(dept);
			}
		
			@Override
			public Dept get(Integer deptno) {
				return commonDAO.find(Dept.class, deptno);
			}
		
			@SuppressWarnings("rawtypes")
			@Override
			public void findByPage(PageBean pageBean, DeptCriteria deptCriteria) {
				pageBean.setEntityName("Dept dept");
				pageBean.setSelect("select dept");
				
				// 按条件分页查询
				commonDAO.findByPage(pageBean,deptCriteria);
			}
		
			@Override
			public int findMaxPage(int rowPerPage) {
				return commonDAO.findMaxPage("select count(*) from Dept", rowPerPage);
			}
		
			@SuppressWarnings("unchecked")
			@Override
			public List<Dept> findAll() {
				return commonDAO.find("from Dept");
			}
		
		}
     ``` 
 
4. **编写  Controller 控制器**
 
 ```JAVA
 /**
 * 所有Controller处理类统一继承BaseController
 * 
 * BaseController 中定义了一下内容：
 * - request, application Servlet API
 * - 请求响应相关的JSON参数（EasyUI框架请求都是通过JSON响应）
 * - 初始化JSON响应数据的方法（setJsonMap，setJsonMsgMap，setJsonPaginationMap(PageBean, Object...)）
 * - 获得分页对象：super.getPageBean() 
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

5. **页面开发**

   - 在主目录下创建存放新模块页面的目录，如：
   
      - `webapp/WEB-INF/content/main/yourmodule`（EasyUI 页面主目录）
       
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

6. **访问和权限配置**

   1. 通过系统管理员, 添加新模块`菜单权限`(`/Dept/page.do`, 点击菜单访问 JSP 视图的 action 请求)
   
   2. 通过系统管理员, 添加新模块`操作权限`(增删改查的 controller 请求, 显示控制的 action 名称)
   
   3. 针对用户角色按需`分配菜单权限和操作权限`
 


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
   
  
### 3. EasyCommonDAO

[EasyCommonDAO](https://github.com/ushelp/EasyCommonDAO 'EasyCommonDAO') 提供了通用的持久层操作类, 封装了各种日常操作方法, 包含了基于 PageBean 和EasyCriteria 的分页及查询条件处理组件。


#### PageBean分页和查询条件处理

- Example

   ```JAVA
   PageBean pb = new PageBean();

	// FROM Clause; optional
	// **If use EclipseLink is required!**
	pb.setSelect(" select new com.company.ssh.entity.Account(ac.accountid, ac.qxname) ");
	// FROM Clause Entity Name; rquired
	pb.setEntityName("Account ac"); 
	// Page Number; optional; default is 1
	pb.setPageNo(1); 
	// Rows per page; optional; default is 10
	pb.setRowsPerPage(4);
	// WHERE Clause; optional; default is ''
	pb.setCondition(" and ac.accountid>2");
	// Append where clause condition; optional; default is ''
	// pb.addCondition(" and name='A'");
	// SortName; optional; default is ''
	pb.setSort("ac.accountid");
	// SortOrder; optional; default is 'asc'
	pb.setSortOrder("desc");
	// Other sorting methods; optional; default is ''
	pb.setLastSort(",time desc");
	
	// Execute pagination quries
	commonDAO.findByPage(pb); 
	
	// Pagination data
	System.out.println(pb.getData());
	System.out.println(pb.getPageNo());
	System.out.println(pb.getRowsPerPage());
	System.out.println(pb.getRowsCount());
	System.out.println(pb.getPageTotal());
   ```

- Example2

   ```JAVA
   // Immediate use this query
	// data sql
	pb.setQuery("select ac from Account ac where ac.accountid>=10 and ac.accountid<1000");
	// total sql
	pb.setCountSQL("select count(1) from Account ac where ac.accountid>=10 and ac.accountid<1000"); 
	// Page Number; optional; default is 1
	pb.setPageNo(1); 
	// Rows per page; optional; default is 10
	pb.setRowsPerPage(4);
	
	// Execute pagination quries
	commonDAO.findByPage(pb); 
   
   // Pagination data...
   ```

#### EasyCriteria 条件查询

1. 创建 EasyCriteria 类, 必须 extends EasyCriteria implements Serializable

2. 编写条件方法 getCondition()

- Example
    
    ```JAVA
    public class SysUserCriteria extends EasyCriteria implements java.io.Serializable {
       // 1. Criteria field
       private String name;
       private String realName;
       private Integer status; // 0 is ON; 1 is OFF; 2 is REMOVED
    
       // 2. Constructor
       public SysUserCriteria() {
       }
    
       public SysUserCriteria(String name, String realName, Integer status) {
           super();
           this.name = name;
           this.realName = realName;
           this.status = status;
       }
    
       // 3. Condition genertator abstract method implements
       public String getCondition() {
           values.clear(); // **Must clear old values**
    
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
    
       // 4. Setters&amp;Getters...
    
    }
    ```

- Usage

    ```JAVA
    PageBean pageBean = new PageBean();
    pageBean.setEntityName("SysUser users");
    pageBean.setSelect("select users");
    
    // EasyCriteria
    SysUserCriteria usersCriteria =new SysUserCriteria();
    usersCriteria.setName("A");
    usersCriteria.setStatus(0);
    
    // Find by EasyCriteria
    commonDAO.findByPage(pageBean, usersCriteria);
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

 EasyShiro 是一个基于 Shiro 的安全扩展组件。为基于数据库权限管理和 Web URL 授权 的RBAC（Role Based Access Control） Web 权限模型，提供通用支持。EasyShiro 能简化安全集成，并提供验证码，自动登录，登录锁定，错误消息管理，拦截器，Ajax 响应等等更强大全面的功能支持，仅需简单配置即可。


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


