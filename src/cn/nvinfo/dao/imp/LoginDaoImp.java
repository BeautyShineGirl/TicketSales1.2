package cn.nvinfo.dao.imp;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import cn.nvinfo.dao.LoginDao;
import cn.nvinfo.domain.Staff;
/**
 *  登陆
 * @author 杨立   2017-10-09
 *
 */

@Repository
public class LoginDaoImp implements LoginDao {

	@Resource
	private SqlSessionTemplate template;
	/*
	 * 查询用户	杨立	2017-10-09
	 */
	public Staff getStaff(String loginName, String password) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("loginName", loginName);
		map.put("password", password);
		return template.selectOne("staff.getStaff",map);
	}
	/*
	 *  修改密码  杨立  2017-10-09(non-Javadoc)
	 * @see cn.nvinfo.dao.LoginDao#editPassword(java.lang.Integer, java.lang.String)
	 */
	public int editPassword(Integer id, String password) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("password", password);
		return template.update("staff.editPassword", map);
	}
	/*
	 * 重置密码 	杨立	2017-10-09(non-Javadoc)
	 * @see cn.nvinfo.dao.LoginDao#initPassword(java.lang.Integer)
	 */
	public int initPassword(Integer id) {
		return template.update("staff.initPassword", id);
	}
	
	

}
