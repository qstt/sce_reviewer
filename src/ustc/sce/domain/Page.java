package ustc.sce.domain;

import java.util.List;

public class Page<T> {

	// 每页显示的数据
	private List<T> list;
	// 总记录数
	private int totalRecords;
	// 每页显示的记录条数
	private int pageSize;
	// 当前页
	private int pageNo;

	/**
	 * 调用getTotalPage() 获取到总页数 JavaBean的属性规定：totalPage是JavaBean是属性
	 * ${pageBean.totalPage}
	 * 
	 * @return
	 */
	public int getTotalPage() {
		// 计算
		int totalPage = totalRecords / pageSize;
		// 说明整除
		if (totalRecords % pageSize == 0) {
			return totalPage;
		} else {
			return totalPage + 1;
		}
	}
	
	/**
	 * 计算当前页从哪条记录开始
	 * @param currentPage  当前第几页
	 * @param pageSize   每页记录数
	 * @return  当前页开始记录号
	 */
	public int countOffset(int currentPage,int pageSize) {
		return pageSize * (currentPage - 1);
	}
	
	/**
	 * @return   首页
	 */
	public int getTopPageNo() {
		return 1;
	}
	
	/**
	 * @return  上一页
	 */
	public int getPrevionsPageNo() {
		if(pageNo <= 1) {
			return 1;
		}
		return pageNo - 1;
	}
	
	/**
	 * @return   下一页
	 */
	public int getNextPageNo() {
		if(pageNo >= getBottomPageNo()) {
			return getBottomPageNo();
		}
		return pageNo + 1;
	}

	/**
	 * @return   尾页
	 */
	public int getBottomPageNo() {
		return getTotalPage();
	}
	

	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
