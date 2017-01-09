package cn.easyproject.easyee.sh.base.util;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * PageBean Demo: <br>
 * 
 * <pre><code>
 * // FROM Clause; optional
 * // If use EclipseLink is required!
 * pb.setSelect(" select new com.company.ssh.entity.Account(ac.accountid, ac.qxname) ");
 * // FROM Clause Entity Name; rquired
 * pb.setEntityName("Account ac");
 * // Page Number; optional; default is 1
 * pb.setPageNo(1);
 * // Rows per page; optional; default is 10
 * pb.setRowsPerPage(4);
 * // WHERE Clause; optional; default is ''
 * pb.setCondition(" and ac.accountid &gt; 2");
 * // Append where clause condition; optional; default is ''
 * // pb.addCondition(" and name='A'");
 * // SortName; optional; default is ''
 * pb.setSort("ac.accountid");
 * // SortOrder; optional; default is 'asc'
 * pb.setSortOrder("desc");
 * 
 * // Immediate use this query
 * // data query
 * // pb.setQuery("select ac from Account ac where ac.accountid &gt; =10 and ac.accountid &lt; 1000");
 * // total query
 * // pb.setCountQuery("select count(1) from Account ac where ac.accountid &gt; =10 and ac.accountid &lt; 1000");
 * 
 * 
 * // Execute pagination quries
 * commonDAO.findByPage(pb);
 * 
 * // Pagination data
 * System.out.println(pb.getData());
 * System.out.println(pb.getPageNo());
 * System.out.println(pb.getRowsPerPage());
 * System.out.println(pb.getRowsCount());
 * System.out.println(pb.getPageTotal());
 * </code></pre>
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class PageBean<T> implements Serializable {

	private static final long serialVersionUID = 5229229913348492552L;
	/**
	 *
	 * 前置条件， 可写 主要用于查询组合列，以select开头，如select new User(u.name,u.sex)
	 */
	private String select = "";
	/**
	 * 当前是第几页，可写
	 */
	private int pageNo = 1;
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
	private int rowsPerPage = 10;
	/**
	 * 本页中显示的数据集合，可读
	 */
	private List<T> data;
	/**
	 * 排序方式，可写
	 */
	private String sortOrder = "asc";
	/**
	 * 排序字段，可写，必须
	 */
	private String sort = "";
	/**
	 * 要查询的实体名，可写，必须
	 */
	private String entityName = "";
	/**
	 * 查询条件，无需使用where开始，通过setCondition,addCondition可写, getCondition读取
	 */
	private StringBuffer conditions = new StringBuffer();

	/**
	 * jpql语句最后的附加排序，可写 例如语句后的第二个排序条件，jqpl+",time desc"
	 */
	private String lastSort = "";
	/**
	 * 直接指定分页 语句，覆盖getAutoJpql()自动生成的 JPQL 语句，可写
	 */
	private String query;
	/**
	 * 直接指定分页总数查询语句
	 */
	private String countQuery;
	/**
	 * 要排除的行数，内部使用
	 */
	private int rowStart;

	

	

	public String getAutoQuery() {if(isNotNullOrEmpty(query)){return query;}
		String jpql = select + " from " + entityName + " where 1=1 ";
		if (isNotNullOrEmpty(conditions.toString())) {
			jpql += conditions.toString();
		}
		if (isNotNullOrEmpty(sort)) {
			jpql += " order by " + sort;
			if (isNotNullOrEmpty(sortOrder)) {
				jpql += " " + sortOrder;
			}
		}

		if (isNotNullOrEmpty(sort)) {
			if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
				jpql += "," + lastSort;
			}
		} else {
			if (isNotNullOrEmpty(lastSort) && lastSort.trim().length() != 0) {
				jpql += " order by " + lastSort;
			}
		}

		return jpql;
	}
	
	/**
	 * 获得统计条数的Query语句
	 * @return 总条数query语句
	 */
	public String getAutoCountQuery(){
		
		if(isNotNullOrEmpty(countQuery)){
			return countQuery;
		}
		
		String hql=getAutoQuery();
		String queryString = "";
		int end = hql.length();
		if (hql.indexOf("order by") != -1) {
			end = hql.indexOf("order by");
		}
		if (hql.toUpperCase().indexOf("SELECT") != -1) {
			int i = hql.toUpperCase().indexOf("FROM");
			queryString = "select count(1) " + hql.substring(i, end);
		} else {
			queryString = "select count(1) " + hql.substring(0, end);
		}
		// 去掉ORDER BY 的部分
		int j = queryString.toUpperCase().lastIndexOf("ORDER");
		if (j != -1) {
			queryString = queryString.substring(0, j);
		}
		return queryString;
	}

	public boolean isNotNullOrEmpty(String s) {
		if (null == s || s.trim().equals("")) {
			return false;
		}
		return true;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
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
		this.pageNo = pageNo < 1 ? 1 : pageNo;
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
	 * @param pageTotal
	 *            the maxPage to set
	 */
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	/**
	 * @return the maxRow
	 */
	public int getRowsCount() {
		return rowsCount;
	}

	/**
* @param rowsCount
	 *            the maxRow to set
	 */
	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	/**
	 * 得到每页显示多少条记录
	 * 
	 * @return the rowsPerPage
	 */
	public int getRowsPerPage() {
		return rowsPerPage <= 0 ? 5 : rowsPerPage;
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
	 * @param data
	 *            the data to set
	 */
	public void setData(List<T> data) {
		this.data = data;
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

	public String getQuery() {
		return query;
	}
	public String getCountQuery() {
		return countQuery;
	}

	public void setCountQuery(String countQuery) {
		this.countQuery = countQuery;
	}
}
