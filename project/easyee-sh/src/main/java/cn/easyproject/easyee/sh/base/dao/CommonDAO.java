package cn.easyproject.easyee.sh.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import cn.easyproject.easyee.sh.base.util.EasyCriteria;
import cn.easyproject.easyee.sh.base.util.PageBean;


/**
 * Hibernate CommonDAO 
 * 
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since 1.0.0
 * @see https://github.com/ushelp/EasyCommonDAO
 *
 */
@SuppressWarnings("rawtypes")
public interface CommonDAO {
	/**
	 */
	String DEFAULT_QUERY_CACHE_REGION = "org.hibernate.cache.internal.StandardQueryCache";
	
	/**
	 * 使用JDBC执行原生的批量SQL，单参数语句值列表
	 * @param sql SQL语句
	 * @param values 单参数语句值列表
	 */
	public void batchUpdateSQL(String sql, Object[] values);
	/**
	 * 使用JDBC执行原生的批量SQL，多参数语句值列表
	 * @param sql SQL语句
	 * @param values 多参数语句值列表
	 */
	public void batchUpdateSQL(String sql, Object[][] values);
	/**
	 * 删除持久对象
	 * @param cls 类型
	 * @param id OID
	 */
	public void remove(Class cls, Serializable id);
	/**
	 * 删除持久对象
	 * @param o 实体对象
	 */
	public void remove(Object o);
	
	/**
* 使用 Query 语句批量删除，不支持级联
	 * @param cls 类型ls 类型
	 * @param fieldName 删除条件字段
	 * @param values 语句参数值列表List 要删除的字段列表，使用,分隔
	 * @return affected rows count
	 */
	 public Integer deleteByValues(Class cls, String fieldName, Object[] values);
	 
	 /**
	* 使用 Query 语句批量删除，支持级联
	  * @param cls 类型
	  * @param fieldName 删除条件字段
	  * @param values 集合值列表
	  */
	 public void deleteCascadeByValues(Class cls, String fieldName, Object[] values);
	 
	/**
	 * 清除二级缓存中所有的c对象
	 * @param cls 类型
	 */
	public void evict(Class cls);
	
	/**
	 * 清除二级缓存中OID为id的c对象
	 * @param cls 类型
	 * @param id OID
	 */
	public void evict(Class cls,Serializable id);
	/**
	 * 清除二级缓存中所有对象
	 */
	public void evictAll();
	
	/**
	 * 清除二级缓存中指定类的集合属性
	 * @param collectionRegion 查询缓存区域
	 */
	public void evictCollectionRegion(String collectionRegion);
	
	/**
	 * 清除二级缓存中指定id类的集合属性
	 * @param collectionRegion 查询缓存区域
	 * @param id OID
	 */
	public void evictCollectionRegion(String collectionRegion,Serializable id);
    /**
	 * 清除默认区域的查询缓存策略，如果默认为空，则清除所有标准区域查询缓存策略
	 */
	public void evictQueries();
    /**
	 * 清除指定区域策略的查询缓存
	 * @param queryCacheRegion 指定查询缓存区域
	 */
	public void evictQueries(String queryCacheRegion);

 
    /**
	 * 通过Id查询
	 * @param cls 类型
	 * @param id OID
	 * @return Object
	 */
	public <T> T find(Class cls, Serializable id);

	/**
	 * 按条件根据jpql查询
	 * @param jpql jpql语句 jpql
	 * @param easyCriteria EasyCriteria条件对象
	 * @return List
	 */
	public List find(String jpql,EasyCriteria easyCriteria);
	
	/**
	 * 根据jpql查询
	 * @param jpql jpql语句 jpql
	 * @param values 语句参数值列表 参数列表
	 * @return List
	 */
	public List find(String jpql, Object... values);
	
	/**
	 * 查询所有
	 * @param cls 类型ls
	 * @return List
	 */
	public List findAll(Class cls);
	
	/**
	 * 带查询缓存查询，
	 * 查询缓存，使用DEFAULT_QUERY_CACHE_REGION指定区域的缓存，如DEFAULT_QUERY_CACHE_REGION==null或=="",则使用默认标准区域缓存策略
	 * @param jpql jpql语句
	 * @param easyCriteria EasyCriteria条件对象 
	 * @return List
	 */
	public List findByCache(String jpql, EasyCriteria easyCriteria);
	/**
	 * 带查询缓存查询，
	 * 查询缓存，使用DEFAULT_QUERY_CACHE_REGION指定区域的缓存，如DEFAULT_QUERY_CACHE_REGION==null或=="",则使用默认标准区域缓存策略
	 * @param jpql jpql语句
	 * @param values 语句参数值列表
	 * @return List
	 */
	public List findByCache(String jpql, Object... values);
	
	/**
	 * 查询缓存,需要指定具体的缓存策略区域
	 * @param jpql jpql语句
	 * @param queryCacheRegion 指定查询缓存区域
	 * @param easyCriteria EasyCriteria条件对象 
	 * @return List
	 */
	public List findByCache(String jpql, String queryCacheRegion, EasyCriteria easyCriteria);
	/**
	 * 查询缓存,需要指定具体的缓存策略区域
	 * @param jpql jpql语句
	 * @param queryCacheRegion 指定查询缓存区域
	 * @param values 语句参数值列表
	 * @return List
	 */
	public List findByCache(String jpql, String queryCacheRegion, Object... values);
	
	/**
	 * 分页查询
	 * @param pageBean pageBean
	 */
	public void findByPage(PageBean pageBean);
	/**
	 * 分页查询
	 * @param pageBean pageBean
	 * @param easyCriteria 按条件分页查询方法
	 */
	public void findByPage(PageBean pageBean,EasyCriteria easyCriteria);
	
	/**
	 * 分页查询
	 * @param pageBean pageBean
	 * @param values 语句参数值列表
	 */
	public void findByPage(PageBean pageBean,List values);  
	/**
	 * 简单查询，按照字段查询
	 * @param cls 类型ls
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return List
	 */
    public List findByProperty(Class cls, String propertyName, Object value);  
	
	/**
	 * 按照字段查询，不区分大小写
	 * @param cls 类型ls
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return List
	 */
    public List findByPropertyIgnoreCase(Class cls, String propertyName, String value);
	
	/**
	 * 执行Native SQL，返回封装的 Map 对象集合
	 * @param sql SQL语句
	 * @param resultClass 类型 封装的类型
	 * @param easyCriteria EasyCriteria条件对象 
	 * @return 查询的结果封装为transformers指定的类型
	 */
	public List findBySQL(String sql, Class resultClass, EasyCriteria easyCriteria);
	
	/**
	 * 执行Native SQL，返回封装的对象集合
	 * @param sql SQL语句
	 * @param resultClass 类型 封装的类型
	 * @param values 语句参数值列表
	 * @return 查询的结果封装为c指定的实体
	 */
	public List findBySQL(String sql, Class resultClass, Object... values);
	
	/**
	 * 执行Native SQL，返回封装的对象集合
	 * @param sql SQL语句
	 * @param easyCriteria EasyCriteria条件对象 
	 * @return 查询的结果，多列时转换为Object[]
	 */
	public List findBySQL(String sql,EasyCriteria easyCriteria);

	
	/**
	 * 执行Native SQL，返回封装的对象集合
	 * @param sql SQL语句
	 * @param values 语句参数值列表
	 * @return 查询的结果，多列时转换为Object[]
	 */
	public List findBySQL(String sql,Object... values);
	
	
	
	/**
	 * 通过jpql语句查询总条数
	 * @param jpql jpql语句
	 * @param easyCriteria EasyCriteria条件对象 
	 * @return 总条数
	 */
	public int findCount(String jpql, EasyCriteria easyCriteria);  
	
	/**
	 * 通过jpql语句查询总条数
	 * @param jpql jpql语句
	 * @param values 语句参数值列表
	 * @return 总条数
	 */
	public int findCount(String jpql, Object... values);  
	
	/**
	 * 执行Native SQL，返回封装的 Map 对象集合
	 * @param sql SQL语句
	 * @param easyCriteria EasyCriteria条件对象 
	 * @return 查询的结果封装为transformers指定的类型
	 */
	public List findMapResultBySQL(String sql,EasyCriteria easyCriteria);  
	/**
	 * 执行Native SQL，返回封装的 Map 对象集合
	 * @param sql SQL语句
	 * @param values 语句参数值列表
	 * @return 查询的结果封装为transformers指定的类型
	 */
	public List findMapResultBySQL(String sql, Object... values);  
	/**
	 * 查询数据条数，并根据每页条数计算总页数
	 * @param jpql 查询条数的语句
	 * @param rowsPerPage 每页显示条数
	 * @param easyCriteria EasyCriteria条件对象 
	 * @return 总页数
	 */
	public int findMaxPage(String jpql, int rowsPerPage, EasyCriteria easyCriteria);  
	 
	/**
	 * 查询数据条数，并根据每页条数计算总页数
	 * @param jpql 查询条数的语句
	 * @param rowsPerPage 每页显示条数
	 * @param values 语句参数值列表
	 * @return 总页数
	 */
	public int findMaxPage(String jpql, int rowsPerPage, Object... values);  
	
	/**
     * 执行命名查询
     * @param name queryName
     * @param values 语句参数值列表
     * @return List
     */
    public List findNamedQuery(String name, Object... values) ; 
	/**
     * 执行命名查询
     * @param name queryName
     * @param queryCacheRegion 指定查询缓存区域
     * @param values 语句参数值列表
     * @return List
     */
    public List findNamedQueryByCache(String name, String queryCacheRegion, Object... values) ;
	
	/**
	 * 查询前topCount条数据
	 * @param jpql jpql语句
	 * @param topCount count
	 * @param easyCriteria EasyCriteria条件对象 
	 * @return List
	 */
	public List findTop(String jpql,int topCount,  EasyCriteria easyCriteria);
	
	
	
	/**
	 * 查询前topCount条数据
	 * @param jpql jpql语句
	 * @param topCount count
	 * @param values 语句参数值列表
	 * @return List
	 */
	public List findTop(String jpql,int topCount, Object... values); 
	/**
	 * 查询单个（单行单列）数据，如最大值，最小值等等
	 * @param jpql 单个数据查询语句
	 * @param easyCriteria EasyCriteria条件对象 
	 * @return 查询到的单个值
	 */
	public <T> T findVal(String jpql,  EasyCriteria easyCriteria);
	
	/**
	 * 查询单个（单行单列）数据，如最大值，最小值等等
	 * @param jpql 单个数据查询语句
	 * @param values 语句占位符对应的值
	 * @return 查询到的单个值
	 */
	public <T> T findVal(String jpql, Object... values);

	/**
	 * 获取EntityManager
	 * @return EntityManager
	 */
	public EntityManager getCurrentEntityManager();
	
	/**
	 * 获取EntityManagerFactory，主要用来手动清除缓存
	 * @return EntityManagerFactory
	 */
	public EntityManagerFactory getEntityManagerFactory();
	
	/**
	 * 初始化关联的对象
	 * @param proxy 对象
	 */
	public void initialize(Object proxy) ;
	
	/**
	 * 将集合中的对象深度初始化
	 * @param collection Collection
	 */
	public void initializeDeep(Collection collection);
	/**
	 * 修改对象
	 * @param o 实体对象
	 */
	public void merge(Object o);
	/**
	 * 新增
	 * @param o 实体对象
	 */
	public void persist(Object o); 
	/**
	 * 通过jpql语句进行批量修改（更新，删除）
	 * @param jpql jpql语句
	 * @param values 语句参数值列表
	 */
	public void updateByJpql(String jpql,Object... values);
	/**
	 * 使用JDBC执行原生的增删改SQL语句
	 * @param sql SQL语句
	 * @param values 语句参数值列表
	 */
	public void updateBySQL(String sql,Object...values);
	
}
