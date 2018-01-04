package cn.nvinfo.utils;

import java.util.List;

/**
 * 用于封装分页的模型
 * @author Administrator
 * @param <E>
 *
 */
public class Pager<E> {
	
	/**
	 * 总记录数
	 */
	private int allCount;
	
	/**
	 * 每页显示的记录数
	 */
	private int pageSize;
	
	/**
	 * 总页数
	 */
	private int pageCount;
	
	/**
	 * 当前页的页码
	 */
	private int currPage;
	
	/**
	 * 当前页显示的数据
	 */
	private List<E> datas;

	public int getAllCount() {
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	public int getPageCount() {
		return pageCount;
	}
    
	/**
	 * 获得总页数的方法
	 */
	public void setPageCount() {
		this.pageCount = this.allCount%this.pageSize==0?this.allCount/this.pageSize:(this.allCount/this.pageSize+1);
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<E> getDatas() {
		return datas;
	}

	public void setDatas(List<E> datas) {
		this.datas = datas;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	

}
