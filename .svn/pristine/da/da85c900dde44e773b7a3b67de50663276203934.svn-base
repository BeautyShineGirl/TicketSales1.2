package cn.nvinfo.service;

import java.util.List;

import cn.nvinfo.domain.User;
import cn.nvinfo.domain.ViewMessage;
import cn.nvinfo.utils.Pager;


/**
 *  景区管理    
 * @author 杨立   2017-09-19
 *
 */
public interface ViewService {

	

	/*
	 * 获得景区分类  杨立   2017-09-19
	 */
	List<String> getViewType();

	/*
	 * 从字典表中获得景区等级 杨立   2017-09-19
	 */
	List<String> getViewLevel();

	/*
	 * 从字典中获得省份集合 杨立   2017-09-19
	 */
	List<String> getViewProvince();

	/*
	 * 从字典中获得城市集合 杨立   2017-09-19
	 */
	List<String> getViewCity();

	/*
	 * 获得业务员的编号和姓名     杨立     2017-09-20
	 */
	List<User> getStaff();

	/*
	 * 添加景区信息	杨立   2017-09-20  
	 */
	int add(ViewMessage view);

	/*
	 * 删除	杨立   2017-09-20  
	 */
	int delete(Integer id);

	/*
	 * 修改数据回显   杨立  2017-09-21
	 */
	ViewMessage getById(Integer id);

	/*
	 * 修改   杨立  2017-09-21
	 */
	int edit(ViewMessage view);


	/*
	 * 分页查询  杨立   2017-09-21  系统管理员
	 */
	Pager<ViewMessage> getPager(Integer pageIndex, Integer pageSize, Integer viewId, Integer logic, String staffName, 
			String viewName, String level, String viewType, String province, String city);

	/*
	 * 查询每个景区所对应的产品数量
	 */
	int getViewNum(int id);
	
	/*
	 * 再删除景区之前，先查询该景区是否有产品，若有，则返回删除失败	yangli 	2017-10-16
	 */
	int getProduct(Integer id);
	/*
	 * 普通管理员	yangli	2017-10-19
	 */
	Pager<ViewMessage> getPager(Integer pageIndex, Integer pageSize,
			Integer viewId, Integer logic, String staffName, String viewName,
			String level, String viewType, String province, String city,
			Integer staff_id);

	String  getByStaffId(Integer staffId);

	/*
	 * 将view表中的picture表为空	yangli	2017-10-27
	 */
	int delPicture(Integer id);
	//先查询数据库中是否存在这个随机数，若存在，则不进行添加随机数	2018-01-12
	List<String> getRandom_no(String random_no);
	//先添加产品随机数，就相当于添加了一个产品，然后在对这个产品进行修改url	2018-01-12
	int addRandom_no(String random_no);
	//根据random_no查出原本的oldUrl	yangli	2018-01-12
	List<String> getOldUrl(String random_no);
	//修改图片路径	2018-01-12	杨立
	int updateUrl(String random_no, String newUrl);

}
