package cn.easyproject.easyee.ssh.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.hibernate.jpa.QueryHints;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.easyproject.easyee.ssh.base.dao.CommonDAO;
import cn.easyproject.easyee.ssh.base.util.EasyCriteria;
import cn.easyproject.easyee.ssh.base.util.PageBean;

/**
 * JPA CommonDAO implement used spring.
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since 1.0.0
 * 
 */
@Repository("commonDAO")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CommonDAOSpringImpl implements CommonDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void batchUpdateSQL(final String sql, final Object[] values) {
		entityManager.unwrap(Session.class).doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				PreparedStatement pstmt = null;
				try {
					connection.setAutoCommit(false);// 手动提交
					pstmt = connection.prepareStatement(sql);// 预编译
					for (int i = 0; i < values.length; i++) {
						pstmt.setObject(1, values[i]);
						pstmt.addBatch();
					}
					pstmt.executeBatch();// 将参数加入到批处理
					connection.commit();// 提交
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (pstmt != null) {
						try {
							pstmt.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					if (connection != null && (!connection.isClosed())) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void batchUpdateSQL(final String sql, final Object[][] values) {

		entityManager.unwrap(Session.class).doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				PreparedStatement pstmt = connection.prepareStatement(sql);// 预编译
				for (Object[] row : values) {
					for (int i = 0; i < row.length; i++) {
						pstmt.setObject(i + 1, row[i]);
					}
					pstmt.addBatch();
				}
				pstmt.executeBatch();// 将参数加入到批处理
			}
		});
	}

	/**
	 * CommonDAO 内部方法，NamedQuery
	 * 
	 * @param entityManager
	 *            entityManager
	 * @param name
	 *            name
	 * @param cacheable
	 *            cacheable
	 * @param cacheRegion
	 *            cacheRegion
	 * @param values
	 *            values
	 * @return Query对象
	 */
	private Query createNamedQuery(EntityManager entityManager, String name, boolean cacheable, String cacheRegion,
			Object... values) {
		Query query = entityManager.createNamedQuery(name.trim());
		if (cacheable) {
			query.setHint(QueryHints.HINT_CACHEABLE, "true");
			if (cacheRegion != null && (!cacheRegion.equals(""))) {
				query.setHint(QueryHints.HINT_CACHE_REGION, cacheRegion);
			}
		}
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		return query;
	}

	/**
	 * CommonDAO 内部方法, NativeQuery
	 * 
	 * @param entityManager
	 *            entityManager
	 * @param sql
	 *            sql
	 * @param values
	 *            values
	 * @return Query对象
	 */
	@SuppressWarnings("deprecation")
	private Query createNativeQuery(EntityManager entityManager, String sql, Class resultClass, boolean mapResult,
			Object... values) {
		Query query = null;
		if (resultClass != null) {
			query = entityManager.createNativeQuery(sql, resultClass);
		} else {
			query = entityManager.createNativeQuery(sql);
		}
		// 封装为Map结果
		if (mapResult) {
			query.unwrap(org.hibernate.query.Query.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}

		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		return query;
	}

	/**
	 * CommonDAO 内部方法，Query
	 * 
	 * @param entityManager
	 *            entityManager
	 * @param jpql
	 *            jqpl
	 * @param cacheable
	 *            cacheable
	 * @param cacheRegion
	 *            cacheRegion
	 * @param values
	 *            values
	 * @return Query对象
	 */
	private Query createQuery(EntityManager entityManager, String jpql, boolean cacheable, String cacheRegion,
			Object... values) {
		Query query = entityManager.createQuery(jpql);
		if (cacheable) {
			query.setHint(QueryHints.HINT_CACHEABLE, "true");
			if (cacheRegion != null && (!cacheRegion.equals(""))) {
				query.setHint(QueryHints.HINT_CACHE_REGION, cacheRegion);
			}
		}
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	@Override
	public void remove(Class cls, Serializable id) {
		entityManager.remove(entityManager.find(cls, id));
	}

	@Override
	public void remove(Object o) {
		entityManager.remove(o);
	}

	@Override
	public Integer deleteByValues(Class cls, String fieldName, Object[] values) {
		StringBuilder ins = new StringBuilder();
		ins.append("'");
		for (Object v : values) {
			ins.append(v).append("','");
		}

		String jpql = "delete from " + cls.getSimpleName() + " where " + fieldName + " in("
				+ ins.substring(0, ins.length() - 2) + ")";
		return entityManager.createQuery(jpql).executeUpdate();
	}

	@Override
	public void deleteCascadeByValues(Class cls, String fieldName, Object[] values) {

		StringBuilder ins = new StringBuilder();
		ins.append("'");
		for (Object v : values) {
			ins.append(v).append("','");
		}

		String jpql = "from " + cls.getSimpleName() + " where " + fieldName + " in("
				+ ins.substring(0, ins.length() - 2) + ")";
		for (Object o : entityManager.createQuery(jpql).getResultList()) {
			entityManager.remove(o);
		}

	}

	@Override
	public void evict(Class cls) {
		entityManager.getEntityManagerFactory().getCache().evict(cls);
	}

	@Override
	public void evict(Class cls, Serializable id) {
		entityManager.getEntityManagerFactory().getCache().evict(cls, id);
	}

	@Override
	public void evictAll() {
		entityManager.getEntityManagerFactory().getCache().evictAll();
	}

	@Override
	public void evictCollectionRegion(String collectionRegion) {
		entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getCache()
				.evictCollectionRegion(collectionRegion);
	}

	@Override
	public void evictCollectionRegion(String collectionRegion, Serializable id) {
		entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getCache()
				.evictCollection(collectionRegion, id);

	}

	@Override
	public void evictQueries() {
		if (null != DEFAULT_QUERY_CACHE_REGION || (!"".equals(DEFAULT_QUERY_CACHE_REGION))) {
			entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getCache()
					.evictQueryRegion(DEFAULT_QUERY_CACHE_REGION);
		} else {
			entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getCache().evictQueryRegions();
		}
	}

	@Override
	public void evictQueries(String queryCacheRegion) {
		entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getCache()
				.evictQueryRegion(queryCacheRegion);
	}

	@Override
	public List find(String jpql, EasyCriteria easyCriteria) {
		if (easyCriteria != null) {
			return find(jpql + " " + easyCriteria.getCondition(), easyCriteria.getValues().toArray());
		} else {
			return find(jpql);
		}

	}

	@Override
	public List find(String jpql, Object... values) {
		Query q = createQuery(entityManager, jpql, false, null, values);
		List list = q.getResultList();
		return list;
	}

	@Override
	public List findAll(Class cls) {
		String queryString = "from " + cls.getName();
		List list = entityManager.createQuery(queryString).getResultList();
		initialize(list);
		return list;
	}

	@Override
	public List findByCache(String jpql, EasyCriteria easyCriteria) {

		if (easyCriteria != null) {
			return findByCache(jpql + " " + easyCriteria.getCondition(), easyCriteria.getValues().toArray());
		} else {
			return findByCache(jpql);
		}

	}

	@Override
	public List findByCache(String jpql, Object... values) {

		String cacheRegion = null;
		if (null != DEFAULT_QUERY_CACHE_REGION || (!"".equals(DEFAULT_QUERY_CACHE_REGION))) {
			cacheRegion = DEFAULT_QUERY_CACHE_REGION;
		}
		Query q = createQuery(entityManager, jpql, true, cacheRegion, values);
		List list = q.getResultList();
		return list;
	}

	@Override
	public List findByCache(String jpql, String queryCacheRegion, EasyCriteria easyCriteria) {

		if (easyCriteria != null) {
			return findByCache(jpql + " " + easyCriteria.getCondition(), queryCacheRegion,
					easyCriteria.getValues().toArray());
		} else {
			return findByCache(jpql, queryCacheRegion);
		}
	}

	@Override
	public List findByCache(String jpql, String queryCacheRegion, Object... values) {

		Query q = createQuery(entityManager, jpql, true, queryCacheRegion, values);
		List list = q.getResultList();
		return list;
	}

	@Override
	public void findByPage(PageBean pageBean) {
		this.findByPage(pageBean, new ArrayList());
	}

	@Override
	public void findByPage(PageBean pageBean, EasyCriteria easyCriteria) {
		if (easyCriteria != null) {
			pageBean.setCondition(easyCriteria.getCondition());
			this.findByPage(pageBean, easyCriteria.getValues());
		} else {
			this.findByPage(pageBean);
		}
	}

	@Override
	public void findByPage(PageBean pageBean, List values) {
		String jpql = pageBean.getAutoQuery();
	
		Query query = createQuery(entityManager, jpql, false, null, values.toArray());
		query.setFirstResult(pageBean.getRowStart());
		query.setMaxResults(pageBean.getRowsPerPage());

		pageBean.setData(query.getResultList());

		Query cquery = createQuery(entityManager, pageBean.getAutoCountQuery(), false, null, values.toArray());

		int maxRow = (Integer.valueOf(cquery.getSingleResult().toString())).intValue();
		pageBean.setRowsCount(maxRow);

	}

	@Override
	public List findByProperty(Class cls, String propertyName, Object value) {
		String queryString = "from " + cls.getName() + " model where model." + propertyName + "= ?";
		return find(queryString, value);
	}

	@Override
	public List findByPropertyIgnoreCase(Class cls, String propertyName, String value) {
		String queryString = "from " + cls.getName() + " model where lower(model." + propertyName + ")= ?";
		return find(queryString, value.toLowerCase());
	}

	@Override
	public List findBySQL(String sql, Class resultClass, EasyCriteria easyCriteria) {
		if (easyCriteria != null) {
			return findBySQL(sql + " " + easyCriteria.getCondition(), resultClass, easyCriteria.getValues().toArray());
		} else {
			return findBySQL(sql, resultClass);
		}
	}

	@Override
	public List findBySQL(String sql, Class resultClass, Object... values) {

		Query q = createNativeQuery(entityManager, sql, resultClass, false, values);
		List list = q.getResultList();
		return list;

	}

	@Override
	public List findBySQL(String sql, EasyCriteria easyCriteria) {
		if (easyCriteria != null) {
			return findBySQL(sql + " " + easyCriteria.getCondition(), easyCriteria.getValues().toArray());
		} else {
			return findBySQL(sql);
		}
	}

	@Override
	public List findBySQL(String sql, Object... values) {

		Query q = createNativeQuery(entityManager, sql, null, false, values);
		List list = q.getResultList();
		return list;
	}

	@Override
	public int findCount(String jpql, EasyCriteria easyCriteria) {
		if (easyCriteria != null) {
			return findCount(jpql + " " + easyCriteria.getCondition(), easyCriteria.getValues().toArray());
		} else {
			return findCount(jpql);
		}
	}

	@Override
	public int findCount(String jpql, Object... values) {

		Query q = createQuery(entityManager, jpql, false, null, values);
		return Integer.valueOf(q.getSingleResult().toString());

	}

	public List findMapResultBySQL(String sql, EasyCriteria easyCriteria) {
		if (easyCriteria != null) {
			return findMapResultBySQL(sql + " " + easyCriteria.getCondition(), easyCriteria.getValues().toArray());
		} else {
			return findMapResultBySQL(sql);
		}
	}

	public List findMapResultBySQL(String sql, Object... values) {
		Query q = createNativeQuery(entityManager, sql, null, true, values);
		List list = q.getResultList();
		return list;
	}

	@Override
	public int findMaxPage(String jpql, int rowsPerPage, EasyCriteria easyCriteria) {
		return (this.findCount(jpql, easyCriteria) - 1) / rowsPerPage + 1;
	}

	@Override
	public int findMaxPage(String jpql, int rowsPerPage, Object... values) {
		return (this.findCount(jpql, values) - 1) / rowsPerPage + 1;
	}

	@Override
	public List findNamedQuery(String name, Object... values) {

		Query q = createNamedQuery(entityManager, name, false, null, values);
		return q.getResultList();
	}

	@Override
	public List findNamedQueryByCache(String name, String queryCacheRegion, Object... values) {
		Query q = createNamedQuery(entityManager, name, true, queryCacheRegion, values);
		return q.getResultList();
	}

	@Override
	public List findTop(String jpql, int topCount, EasyCriteria easyCriteria) {

		if (easyCriteria != null) {
			return findTop(jpql + " " + easyCriteria.getCondition(), topCount, easyCriteria.getValues().toArray());
		} else {
			return findTop(jpql, topCount);

		}
	}

	@Override
	public List findTop(String jpql, int topCount, Object... values) {

		Query q = entityManager.createQuery(jpql);
		q.setFirstResult(0);
		q.setMaxResults(topCount);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				q.setParameter(i, values[i]);
			}
		}

		List list = q.getResultList();
		return list;
	}

	@Override
	public <T> T findVal(String jpql, EasyCriteria easyCriteria) {
		List list = this.find(jpql, easyCriteria);
		return (T) (list.size() > 0 ? list.get(0) : null);
	}

	@Override
	public <T> T findVal(String jpql, Object... values) {
		List list = this.find(jpql, values);
		return (T) (list.size() > 0 ? list.get(0) : null);
	}

	@Override
	public <T> T find(Class cls, Serializable id) {
		return (T) entityManager.find(cls, id);
	}

	@Override
	public EntityManager getCurrentEntityManager() {
		return entityManager;
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManager.getEntityManagerFactory();
	}

	@Override
	public void initialize(Object proxy) {
		if (!Hibernate.isInitialized(proxy)) {
			Hibernate.initialize(proxy);
		}
	}

	@Override
	public void initializeDeep(Collection collection) {

		if (collection == null) {
			return;
		}

		for (Object obj : collection) {
			Method[] methods = obj.getClass().getMethods();
			if (methods != null) {
				for (int j = 0; j < methods.length; j++) {
					String getName = methods[j].getName();
					if (getName.length() > 3 && getName.startsWith("get")) {
						String getFix = getName.substring(3, getName.length());
						for (int k = 0; k < methods.length; k++) {
							String setName = methods[k].getName();
							if (setName.length() > 3 && setName.startsWith("set")) {
								String setFix = setName.substring(3, setName.length());
								if (getFix.equals(setFix)) {
									try {
										Object o = methods[j].invoke(obj, new Object[0]);
										if (o != null) {
											Hibernate.initialize(o);
											methods[k].invoke(obj, o);
										}

									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}

				}
			}

		}
	}

	@Override
	public void merge(Object o) {
		entityManager.merge(o);
	}

	@Override
	public void persist(Object o) {
		entityManager.persist(o);
	}

	@Override
	public void updateByJpql(String jpql, Object... values) {
		Query query = entityManager.createQuery(jpql);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		query.executeUpdate();
	}

	@Override
	public void updateBySQL(String sql, Object... values) {
		Query query = entityManager.createNativeQuery(sql);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		query.executeUpdate();
	}

}
