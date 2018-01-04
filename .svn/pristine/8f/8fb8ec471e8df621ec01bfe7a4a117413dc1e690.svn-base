package cn.nvinfo.dao;

import java.util.List;

import cn.nvinfo.domain.Priority;


/**
 * 基础设置   优先级设置
 * @author yangli 	2017.09.19
 *
 */
public interface PriorityDao {

	/*
	 * 获得总记录数
	 */
	int getAllCount();

	/*
	 * 获得当前页的数据
	 * 
	 */
	List<Priority> getPageDate(Integer pageIndex, Integer pageSize);

	/*
	 * 添加
	 */
	int add(Priority priority);
	
	/*
	 * 修改
	 */
	int edit(Priority priority);

	/*
	 * 根据id查询数据
	 */
	Priority getById(Integer id);

	/*
	 * 删除
	 */
	int delete(Integer id);
	
	/*
	 * 属性获得priorty的集合
	 */
	List<Integer> getPriority();


}
