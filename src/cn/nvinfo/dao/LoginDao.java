package cn.nvinfo.dao;

import cn.nvinfo.domain.Staff;


/**
 *  登陆
 * @author 杨立   2017-10-09
 *
 */
public interface LoginDao {
	/*
	 * 查询用户	杨立	2017-10-09
	 */
	Staff getStaff(String loginName, String password);
	/*
	 *  修改密码  杨立  2017-10-09
	 */
	int editPassword(Integer id, String password);
	/*
	 * 重置密码 	杨立	2017-10-09
	 */
	int initPassword(Integer id);


	
}
