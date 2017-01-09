package cn.easyproject.easyee.sm.base.pagination;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * MyBatis PageBean Demo: <br>
 * 
 * <pre><code>
 * **DAO interface:**
 * public List pagination(PageBean pb);
 * 
 * **SQL Mapper:**
 *	&lt;select id="pagination" resultType="Account"&gt;
 *		${autoSQL}
 *	&lt;/select&gt;
 *
 * // **Service:**
 * // EasyCriteria
 * AccountCriteria accountCriteria=new AccountCriteria();
 * accountCriteria.setAccountName("1");
 * 
 * PageBean pb=new PageBean();
 * // FROM Clause; optional; default is *
 * pb.setSelect("*"); 
 * // Table Name; rquired
 * pb.setFrom("Account account");
 * // WHERE Clause; optional; default is ''
 * pb.setCondition(" and account.qxid>=10");
 * // Append where clause condition; optional; default is ''
 * // * pb.addCondition(""); 
 * // Append EasyCriteria condition
 * pb.setEasyCriteria(accountCriteria);
 * // SortName; optional; default is ''
 * pb.setSort("account.accountid");
 * // SortOrder; optional; default is 'asc'
 * pb.setSortOrder("desc");
 * // Page Number; optional; default is 1
 * pb.setPageNo(1);
 * // Rows per page; optional; default is 10
 * pb.setRowsPerPage(4);
 * 
 * accountDAO.pagination(pb)
 * // Pagination data
 * System.out.println(pb.getData());
 * System.out.println(pb.getPageTotal());
 * System.out.println(pb.getPageNo());
 * System.out.println(pb.getRowsPerPage());
 * System.out.println(pb.getRowsCount());
 * 
 * // **Direct set pagination SQL**
 * PageBean pb=new PageBean();
 * pb.setSql("select * from Account where accountName like #{accountName} limit 0,5");
 * pb.setCountSQL("select count(*) from Account where accountName like #{accountName}");
 * // SQL Parameter Values
 * Map&lt;String, Object&gt; values=new HashMap<String,Object>();
 * values.put("accountName", "%1%");
 * pb.setSqlParameterValues(values);
 * 
 * accountDAO.pagination(pb)
 * 
 * // Pagination data
 * System.out.println(pb.getData());
 * System.out.println(pb.getPageTotal());
 * System.out.println(pb.getPageNo());
 * System.out.println(pb.getRowsPerPage());
 * System.out.println(pb.getRowsCount());
 * 
 * </code></pre>
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since 1.0.0
 * @see https://github.com/ushelp/EasyMyBatisPagination
 */
@SuppressWarnings("unused")
public class PageBean<T> implements Serializable {

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	private static final long serialVersionUID = 5229229913348492552L;

	/**数据库类型（使用ORACLE，SQLSERVER，MYSQL常量标识）**/
	private int dialect=-1;  
	
	/**ORACLE数据库**/
	public final static int ORACLE_DIALECT=1; 
	public final static int ORACLE_12C_DIALECT=10; 
	/**SQLSERVER数据库**/
	public final static int SQLSERVER_DIALECT=2; 
	public final static int SQLSERVER_2012_DIALECT=20; 
	/**MYSQL数据库**/
	public final static int MYSQL_DIALECT=3;  
	
	/*
	 * Default CONSTANT
	 */
	private final static int DEFAULT_PAGE_NO=1;
	private final static int DEFAULT_ROWS_PER_PAGE=10;
	private final static String DEFAULT_SELECT="*";
	private final static String DEFAULT_SORT_ORDER="ASC";
	
	
	/**
	 * 当前是第几页，可写
	 */
	private int pageNo = DEFAULT_PAGE_NO;
	/**
	 * 一共有多少页，可读
	 */
	private int pageTotal;
	/**
	 * 一共有多少行，可读
	 */
	private int rowsCount;
	/**
	 * 每页多少行，可写
	 */
	private int rowsPerPage = DEFAULT_ROWS_PER_PAGE;
	/**
	 * 本页中显示的数据集合，可读
	 */
	private List<T> data;
	/**
	 * 要排除的行数，内部使用
	 */
	private int rowStart;
	
	
	/**
	 *
	 * 前置条件， 可写主要用于查询组合列，如 u.name,u.sex
	 */
	private String select = DEFAULT_SELECT;
	/**
	 * 要查询的表名名，可写，必须
	 */
	private String from = "";
	/**
	 * 使用 Oracle 数据库时设置事实主表的别名，单表可不设置
	 */
	private String primaryTable=""; 
	/**
	 * 查询条件，无需使用where开始，通过setCondition,addCondition可写, getCondition读取
	 */
	private StringBuffer conditions = new StringBuffer();
	/**
	 * 排序方式，可写
	 */
	private String sortOrder = DEFAULT_SORT_ORDER;
	/**
	 * 排序字段，可写，必须
	 */
	private String sort = "";
	/**
	 * 语句最后的附加排序，可写 例如语句后的第二个排序条件，jqpl+",time desc"
	 */
	private String lastSort = "";
	
	
	/**
	 * 直接指定分页 语句，覆盖getAutoSql()自动生成的 SQL 语句，可写
	 */
	private String sql;
	/**
	 * 直接指定分页的总条数查询语句，覆盖getAutoSql()自动生成的 SQL 语句，可写
	 */
	private String countSQL;
	/**
	 * SQL 语句的参数值
	 */
	private Map<String,Object> sqlParameterValues=new HashMap<String, Object>();

	/**
	 * 分页查询条件对象，如果设置了condition，会被覆盖
	 */
	EasyCriteria easyCriteria;

	public String getAutoSQL() {
		
		if(isNotNullOrEmpty(sql)){
			return parameterHandler(sql);
		}
		
		int start=(pageNo-1)*rowsPerPage;  //开始量
		int end=start+rowsPerPage; //结束量
		
		StringBuilder sql=new StringBuilder("");
		
		if(dialect==MYSQL_DIALECT){
			/*
			 * SELECT c,c2 FROM tablename
			 * WHERE 1=1
			 * ORDER BY col
			 * LIMIT 10,5 
			 */
			sql
			.append("SELECT ")
			.append(select)
			.append(" FROM ")
			.append(from)
			.append(" WHERE 1=1 ");
			
			if (isNotNullOrEmpty(conditions.toString())) {
				sql.append(conditions);
			}
			if(easyCriteria!=null){
				sql.append(easyCriteria.getCondition());
			}
			
			if (isNotNullOrEmpty(sort)) {
				sql.append(" ORDER BY ").append(sort);
				if (isNotNullOrEmpty(sortOrder)) {
					sql.append( " " + sortOrder);
				}
				
				if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
					sql.append("," + lastSort);
				}
			}else{
				if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
					sql.append(" ORDER BY " + lastSort);
				}else{
					//
				}
			}
			sql.append(" LIMIT "+start+","+rowsPerPage);//分页语句
			
		}else if(dialect==SQLSERVER_DIALECT){
			/*
			 * SELECT TOP 5 c,c2 FROM tablename 
			 * WHERE id not in(SELECT TOP 10 id FROM tablename WHERE 1=1 ORDER BY id)
			 */
			
			sql
			.append("SELECT TOP ")
			.append(rowsPerPage)
			.append(select)
			.append(" FROM ")
			.append(from)
			.append(" WHERE ")
			.append(sortOrder)
			.append("  NOT IN(SELECT TOP ")
			.append(start+" "+sortOrder+" FROM "+from+" WHERE 1=1");
			
			if (isNotNullOrEmpty(conditions.toString())) {
				sql.append(conditions);
			}
			if(easyCriteria!=null){
				sql.append(easyCriteria.getCondition());
			}
			sql.append(" ORDER BY "+sort+" "+sortOrder);
			
			if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
				sql.append("," + lastSort);
			}
			
			sql.append(")");
		
		}else if(dialect==SQLSERVER_2012_DIALECT){
			/*
			 * SELECT c,c2 FROM tablename
			 * WHERE 1=1
			 * ORDER BY col
			 * OFFSET 10 
			 * ROWS FETCH NEXT 5 ROWS ONLY
			 */
			sql
			.append("SELECT ")
			.append(rowsPerPage)
			.append(select)
			.append(" FROM ")
			.append(from)
			.append(" WHERE 1=1 ");
			
			if (isNotNullOrEmpty(conditions.toString())) {
				sql.append(conditions);
			}
			if(easyCriteria!=null){
				sql.append(easyCriteria.getCondition());
			}
		
			if (isNotNullOrEmpty(sort)) {
				sql.append(" ORDER BY ").append(sort);
				if (isNotNullOrEmpty(sortOrder)) {
					sql.append( " " + sortOrder);
				}
				
				if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
					sql.append("," + lastSort);
				}
			}else{
				if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
					sql.append(" ORDER BY " + lastSort);
				}else{
					//
				}
			}
			
			sql.append(" OFFSET ")
			.append(start)
			.append(" ROWS FETCH NEXT ")
			.append(rowsPerPage)
			.append(" ROWS ONLY")
			;
			
		}else if(dialect==ORACLE_DIALECT){
			
			/*
			 * SELECT B.* FROM (
			 * 		SELECT A.*,rownum r FROM
			 * 			(SELECT col,col2 FROM tableName WHERE 1=1 ORDER BY col,primaryTable.rowid) A
			 * 		WHERE rownum<=10	
			 * ) B 
			 * WHERE B.r>5
			 * 
			 */
			
			sql
			.append("SELECT B.* FROM ( SELECT A.*,rownum r FROM (")
			.append(" SELECT ")
			.append(select)
			.append(" FROM ")
			.append(from)
			.append(" WHERE 1=1 ");
			
			if (isNotNullOrEmpty(conditions.toString())) {
				sql.append(conditions);
			}

			if(easyCriteria!=null){
				sql.append(easyCriteria.getCondition());
			}
			
			if (isNotNullOrEmpty(sort)) {
				sql.append(" ORDER BY ").append(sort);
				if (isNotNullOrEmpty(sortOrder)) {
					sql.append( " " + sortOrder);
				}
				
				if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
					sql.append("," + lastSort);
				}
				
				if(isNotNullOrEmpty(primaryTable)){
					sql.append(","+primaryTable+".ROWID");
				}else{
					sql.append(",ROWID");
				}
				
			}else{
				if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
					sql.append(" ORDER BY " + lastSort);
					if(isNotNullOrEmpty(primaryTable)){
						sql.append(","+primaryTable+".ROWID");
					}else{
						sql.append(",ROWID");
					}
				}else{
					if(isNotNullOrEmpty(primaryTable)){
						sql.append(" ORDER BY "+primaryTable+".ROWID");
					}else{
						sql.append(" ORDER BY ROWID");
					}
				}
			}
			
			
			sql.append(") A  WHERE  ROWNUM<="+end+"  ) B WHERE  B.r>"+start);
			
		}else if(dialect==ORACLE_12C_DIALECT){
			/*
			 * SELECT col,col2 FROM tablename WHERE 1=1 ORDER BY col
			 * OFFSET 10 ROWS FETCH FIRST 5 ROWS ONLY;
			 */
			sql
			.append("SELECT ")
			.append(select)
			.append(" FROM ")
			.append(from)
			.append(" WHERE 1=1 ");
			
			if (isNotNullOrEmpty(conditions.toString())) {
				sql.append(conditions);
			}

			if(easyCriteria!=null){
				sql.append(easyCriteria.getCondition());
			}
			
			if (isNotNullOrEmpty(sort)) {
				sql.append(" ORDER BY ").append(sort);
				if (isNotNullOrEmpty(sortOrder)) {
					sql.append( " " + sortOrder);
				}
				
				if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
					sql.append("," + lastSort);
				}
				
				if(isNotNullOrEmpty(primaryTable)){
					sql.append(","+primaryTable+".ROWID");
				}else{
					sql.append(",ROWID");
				}
				
			}else{
				if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
					sql.append(" ORDER BY " + lastSort);
					if(isNotNullOrEmpty(primaryTable)){
						sql.append(","+primaryTable+".ROWID");
					}else{
						sql.append(",ROWID");
					}
				}else{
					if(isNotNullOrEmpty(primaryTable)){
						sql.append(" ORDER BY "+primaryTable+".ROWID");
					}else{
						sql.append(" ORDER BY ROWID");
					}
				}
			}
			sql.append("OFFSET "+start+" ROWS FETCH FIRST "+rowsPerPage+" ROWS ONLY");
		}
		
		if(easyCriteria!=null){
			sqlParameterValues=easyCriteria.getValues();
		}
		return parameterHandler(sql.toString());
	
	}
	
	/**
	 * 获得查询数据总条数的SQL语句
	 * @return 返回根据表信息和条件自动生成当前dbType对应的数据库的查询总条数SQL语句
	 */
	public String getAutoCountSQL(){
		if(isNotNullOrEmpty(countSQL)){
			return parameterHandler(countSQL);
		}
		
		String countSQL2="select count(*) from "+from+" where 1=1 "; //计算总数量语句
		if (isNotNullOrEmpty(conditions.toString())) {
			countSQL2+=conditions;
		}
		
		if(easyCriteria!=null){
			countSQL2+=easyCriteria.getCondition();
		}
		
		if(easyCriteria!=null){
			sqlParameterValues=easyCriteria.getValues();
		}
		return parameterHandler(countSQL2);
		
	}
	
	public String getCountSQL() {
		return countSQL;
	}

	public void setCountSQL(String countSQL) {
		this.countSQL = countSQL;
	}

	/**
	 * 
	 * @param sql
	 * @return
	 */
	private String parameterHandler(String sql){
		return sql.replaceAll("\\#\\{[\\ ]*(\\S*)[\\ ]*\\}", "\\#\\{sqlParameterValues\\.$1\\}");
	}

	private boolean isNotNullOrEmpty(String s) {
		if (null == s || s.trim().equals("")) {
			return false;
		}
		return true;
	}

	

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 
	 */
	public PageBean() {
		super();
	}

	/**
	 * @return the curPage
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the page number to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo < 1 ? DEFAULT_PAGE_NO : pageNo;
	}

	/**
	 * 得到总共有多少页
	 * 
	 * @return the maxPage
	 */
	public int getPageTotal() {
		return pageTotal = (this.rowsCount - 1) / this.getRowsPerPage() + 1;
	}


	/**
	 * @return the maxRow
	 */
	public int getRowsCount() {
		return rowsCount;
	}



	/**
	 * 得到每页显示多少条记录
	 * 
	 * @return the rowsPerPage
	 */
	public int getRowsPerPage() {
		return rowsPerPage <= 0 ? DEFAULT_ROWS_PER_PAGE : rowsPerPage;
	}

	/**
	 * @param rowsPerPage
	 *            the rowsPerPage to set
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}



	/**
	 * 得到分页前要排除的行数
	 * 
	 * @return the rowStart
	 */
	public int getRowStart() {
		int ret = (this.pageNo - 1) * this.getRowsPerPage();
		return ret < 1 ? 0 : ret;
	}

	/**
	 * @param rowStart
	 *            the rowStart to set
	 */
	private void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * 获得查询条件语句
	 * 
	 * @return 查询条件语句
	 */
	public String getCondition() {
		return conditions.toString();
	}

	/**
	 * 设置查询条件
	 * 
	 * @param condition 条件
	 *            查询条件语句
	 */
	public void setCondition(String condition) {
		this.conditions = new StringBuffer(condition);
	}

	/**
	 * 追加条件
	 * 
	 * @param condition 条件
	 */
	public void addCondition(String condition) {
		conditions.append(condition);
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getLastSort() {
		return lastSort;
	}

	public void setLastSort(String lastSort) {
		this.lastSort = lastSort;
	}

	public EasyCriteria getEasyCriteria() {
		return easyCriteria;
	}

	public void setEasyCriteria(EasyCriteria easyCriteria) {
		this.easyCriteria = easyCriteria;
	}

	public Map<String, Object> getSqlParameterValues() {
		return sqlParameterValues;
	}

	public void setSqlParameterValues(Map<String, Object> sqlParameterValues) {
		this.sqlParameterValues = sqlParameterValues;
	}

	public String getPrimaryTable() {
		return primaryTable;
	}

	public void setPrimaryTable(String primaryTable) {
		this.primaryTable = primaryTable;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getDialect() {
		return dialect;
	}

	public void setDialect(int dialect) {
		this.dialect = dialect;
	}



}
