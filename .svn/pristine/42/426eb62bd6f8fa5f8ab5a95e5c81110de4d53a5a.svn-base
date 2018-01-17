package cn.nvinfo.dao.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import cn.nvinfo.dao.ViewDao;
import cn.nvinfo.domain.User;
import cn.nvinfo.domain.ViewMessage;
/**
 *  景区管理    
 * @author 杨立   2017-09-19
 *
 */

@Repository
public class ViewDaoImp implements ViewDao {

	@Resource
	private SqlSessionTemplate template;

	/*
	 * 从字典表中获得景区分类	 杨立   2017-09-19
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getViewType()
	 */
	public List<String> getViewType() {
		
		return template.selectList("view.getViewType");
	}

	/*
	 * 从字典表中获得景区等级	 杨立   2017-09-19
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getViewType()
	 */
	public List<String> getViewLevel() {
		return template.selectList("view.getViewLevel");
	}

	/*
	 * 从字典中获得省份集合	 杨立   2017-09-19
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getViewProvince()
	 */
	public List<String> getViewProvince() {
		return template.selectList("view.getViewProvince");
	}

	/*
	 * 从字典中获得城市集合	 杨立   2017-09-19
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getViewCity()
	 */
	public List<String> getViewCity() {
		return template.selectList("view.getViewCity");
	}

	/*
	 * 获得业务员的编号和姓名      杨立     2017-09-20
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getStaff()
	 */
	public List<User> getStaff() {
		List<User> list = template.selectList("view.getStaff");
		return list;
	}

	/*
	 * 添加景区信息	杨立   2017-09-20  
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#add(cn.nvinfo.domain.ViewMessage, org.springframework.web.multipart.MultipartFile)
	 */
	public int add(ViewMessage view) {
		return template.insert("view.add", view);
	}

	/*
	 *删除 	杨立   2017-09-20  
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#delete(java.lang.Integer)
	 */
	public int delete(Integer id) {
		return  template.delete("view.delete", id);
	}

	/*
	 * 修改数据回显  	杨立	2017-09-21
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#getById(java.lang.Integer)
	 */
	public ViewMessage getById(Integer id) {
		return template.selectOne("view.getById", id);
	}

	/*
	 * 修改  	杨立	2017-09-21
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#getById(java.lang.Integer)
	 */
	public int edit(ViewMessage view) {
		return template.update("view.edit", view);
	}

	/*
	 * * 获得总记录数  	杨立	2017-09-21
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#getAllCount()
	 */
	public int getAllCount(Integer viewId,Integer logic , String staffName, String viewName, String level, String viewType, String province, String city) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("viewId", viewId);
		map.put("logic", logic);
		map.put("staffName", staffName);
		map.put("viewName", viewName);
		map.put("level", level);
		map.put("viewType", viewType);
		map.put("province", province);
		map.put("city", city);
		return template.selectOne("view.getAllCount",map);
	}

	/*
	 * 当页数据  	杨立	2017-09-21
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#getPageDate(java.lang.Integer, java.lang.Integer)
	 */
	public List<ViewMessage> getPageDate(Integer pageIndex, Integer pageSize, Integer viewId,Integer logic ,String staffName,
			String viewName, String level, String viewType, String province, String city) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pageIndex", (pageIndex-1)*pageSize);
		map.put("pageSize", pageSize);
		map.put("viewId", viewId);
		map.put("logic", logic);
		map.put("staffName", staffName);
		map.put("viewName", viewName);
		map.put("level", level);
		map.put("viewType", viewType);
		map.put("province", province);
		map.put("city", city);
		List<ViewMessage> list = template.selectList("view.getPageDate", map);
		return list;
	}

	/*
	 * 查询每个景区所对应的产品数量
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#getViewNum(int)
	 */
	public int getViewNum(int id) {
		
		return template.selectOne("view.getViewNum",id);
	}

	/*
	 *  再删除景区之前，先查询该景区是否有产品，若有，则返回删除失败	yangli 	2017-10-16(non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#getProduct(java.lang.Integer)
	 */
	public int getProduct(Integer id) {
		return template.selectOne("view.getProduct", id);
	}

	/*
	 * 分页查询  普通管理员	获得总记录数	yangli	2017-10-(non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#getAllCount(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public int getAllCount(Integer viewId, Integer logic, String staffName,
			String viewName, String level, String viewType, String province,
			String city, Integer staff_id) {
		Map<String,Object> map=new HashMap<String,Object>();
		
		map.put("viewId", viewId);
		map.put("logic", logic);
		map.put("staffName", staffName);
		map.put("viewName", viewName);
		map.put("level", level);
		map.put("viewType", viewType);
		map.put("province", province);
		map.put("city", city);
		map.put("staff_id", staff_id);
		return template.selectOne("view.getAllCount2",map);
	}

	/*
	 * 分页查询 	 普通管理员	获得数据	yangli	2017-10-19(non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#getPageDate(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public List<ViewMessage> getPageDate(Integer pageIndex, Integer pageSize,
			Integer viewId, Integer logic, String staffName, String viewName,
			String level, String viewType, String province, String city,
			Integer staff_id) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("pageIndex", (pageIndex-1)*pageSize);
		map.put("pageSize", pageSize);
		map.put("viewId", viewId);
		map.put("logic", logic);
		map.put("staffName", staffName);
		map.put("viewName", viewName);
		map.put("level", level);
		map.put("viewType", viewType);
		map.put("province", province);
		map.put("city", city);
		map.put("staff_id", staff_id);		
		return template.selectList("view.getPageDate2", map);
	}

	/*
	 * 根据staffId查名字	杨立	2017-10-26(non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getByStaffId()
	 */
	public String getByStaffId(Integer staffId) {
		return template.selectOne("view.getByStaffId",staffId);
	}

	/*
	 * 将view表中的picture表为空	yangli	2017-10-27(non-Javadoc)
	 * @see cn.nvinfo.dao.ViewDao#delPicture(java.lang.Integer)
	 */
	public int delPicture(Integer id) {
		return template.update("view.delPicture",id);
	}
	//先查询数据库中是否存在这个随机数，若存在，则不进行添加随机数 2018-01-12
	public List<String> getRandom_no(String random_no) {
		return template.selectList("view.getRandom_no", random_no);
	}
	//先添加产品随机数，就相当于添加了一个产品，然后在对这个产品进行修改url 2018-01-12
	public int addRandom_no(String random_no) {
		return template.insert("view.addRandom_no", random_no);
	}
	//根据random_no查出原本的oldUrl	yangli	2018-01-12
	public List<String> getOldUrl(String random_no) {
		return template.selectList("view.getOldUrl", random_no);
	}
	//修改图片路径	2018-01-12	杨立
	public int updateUrl(String random_no, String newUrl) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("random_no", random_no);
		map.put("newUrl", newUrl);
		return template.update("view.updateUrl", map);
	}
	
	

}
