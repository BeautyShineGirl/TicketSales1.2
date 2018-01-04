package cn.nvinfo.dao;

import java.util.List;

import cn.nvinfo.domain.TicketType;

/**
 * 基础管理    票型类别设置
 * @author 杨立   2017-09-19
 *
 */
public interface TicketTypeDao {

	/*
	 * 获得总记录数
	 */
	int getAllCount();

	/*
	 * 获得当前页的数据
	 */
	List<TicketType> getPageDate(Integer pageIndex, Integer pageSize);

	/*
	 * 添加
	 */
	int add(TicketType ticketType);

	/*
	 * 根据id获取数据
	 */
	TicketType getById(Integer id);

	/*
	 * 修改
	 */
	int edit(TicketType ticketType);

	/*
	 * 删除
	 */
	int delete(Integer id);

	/*
	 *  向字典表中添加票型名称
	
	int add(String name);
	 */
	
}
