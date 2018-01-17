package cn.nvinfo.service.imp;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.nvinfo.dao.ViewDao;
import cn.nvinfo.domain.User;
import cn.nvinfo.domain.ViewMessage;
import cn.nvinfo.service.ViewService;
import cn.nvinfo.utils.Pager;

/**
 *  景区管理    
 * @author 杨立   2017-09-19
 *
 */
@Service
@Transactional
public class ViewServiceImp implements ViewService {

	@Resource
	private ViewDao viewDao;


	/*
	 * 从字典表中获得景区分类   杨立   2017-09-19
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getViewType()
	 */
	public List<String> getViewType() {

		return viewDao.getViewType();
	}

	/*
	 * 从字典表中获得景区等级   杨立   2017-09-19
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getViewType()
	 */
	public List<String> getViewLevel() {
		return viewDao.getViewLevel();
	}

	/*
	 * 从字典中获得省份集合	 杨立   2017-09-19
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getViewProvince()
	 */
	public List<String> getViewProvince() {

		return viewDao.getViewProvince();
	}

	/*
	 * 从字典中获得城市集合	 杨立   2017-09-19
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getViewCity()
	 */
	public List<String> getViewCity() {
		return viewDao.getViewCity();
	}

	/*
	 * 获得业务员的编号和姓名      杨立     2017-09-20
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getStaff()
	 */
	public List<User> getStaff() {
		return viewDao.getStaff();
	}

	/*
	 * 添加景区信息	杨立   2017-09-20  
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#add(cn.nvinfo.domain.ViewMessage, org.springframework.web.multipart.MultipartFile)
	 */
	public int add(ViewMessage view) {

		return viewDao.add(view);
	}

	/*
	 * 删除	杨立   2017-09-20  
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#delete(java.lang.Integer)
	 */
	public int delete(Integer id) {
		return viewDao.delete(id);
	}

	/*
	 * 修改数据回显   杨立 	2017-09-21
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getById(java.lang.Integer)
	 */
	public ViewMessage getById(Integer id) {
		return viewDao.getById(id);
	}

	/*
	 * 修改   杨立 	2017-09-21
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getById(java.lang.Integer)
	 */
	public int edit(ViewMessage view) {
		return viewDao.edit(view);
	}

	/*
	 * 分页查询   杨立 	2017-09-21
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.TicketTypeService#getPager(java.lang.Integer, java.lang.Integer)
	 */
	public Pager<ViewMessage> getPager(Integer pageIndex, Integer pageSize, Integer viewId, Integer logic, String staffName, 
			String viewName, String level, String viewType, String province, String city) {
		Pager<ViewMessage> pager =new Pager<ViewMessage>();
		pager.setCurrPage(pageIndex);
		pager.setPageSize(pageSize);
		pager.setAllCount(viewDao.getAllCount(viewId,logic,staffName,viewName,level,viewType,province,city));
		pager.setDatas(viewDao.getPageDate(pageIndex,pageSize,viewId,logic,staffName,viewName,level,viewType,province,city));
		pager.setPageCount();
		return pager;
	}

	/*
	 * 查询每个景区所对应的产品数量
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getViewNum(int)
	 */
	public int getViewNum(int id) {
		return viewDao.getViewNum(id);
	}

	/*
	 *  再删除景区之前，先查询该景区是否有产品，若有，则返回删除失败	yangli 	2017-10-16(non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getProduct(java.lang.Integer)
	 */
	public int getProduct(Integer id) {
		return viewDao.getProduct(id);
	}

	/*
	 * 普通管理员	yangli	2017-10-19(non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getPager(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public Pager<ViewMessage> getPager(Integer pageIndex, Integer pageSize,
			Integer viewId, Integer logic, String staffName, String viewName,
			String level, String viewType, String province, String city,
			Integer staff_Id) {
		Pager<ViewMessage> pager =new Pager<ViewMessage>();
		pager.setCurrPage(pageIndex);
		pager.setPageSize(pageSize);
		pager.setAllCount(viewDao.getAllCount(viewId,logic,staffName,viewName,level,viewType,province,city,staff_Id));
		pager.setDatas(viewDao.getPageDate(pageIndex,pageSize,viewId,logic,staffName,viewName,level,viewType,province,city,staff_Id));
		pager.setPageCount();
		return pager;
	}
	/*
	 * 根据staffId查名字	杨立	2017-10-26(non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#getByStaffId()
	 */
	public String getByStaffId(Integer staffId) {
		return viewDao.getByStaffId(staffId);
	}

	/*
	 * 将view表中的picture表为空	yangli	2017-10-27(non-Javadoc)
	 * @see cn.nvinfo.service.ViewService#delPicture(java.lang.Integer)
	 */
	public int delPicture(Integer id) {
		return viewDao.delPicture(id);
	}
	//先查询数据库中是否存在这个随机数，若存在，则不进行添加随机数 2018-01-12
	public List<String> getRandom_no(String random_no) {
		return viewDao.getRandom_no(random_no);
	}
	//先添加产品随机数，就相当于添加了一个产品，然后在对这个产品进行修改url 2018-01-12
	public int addRandom_no(String random_no) {
		return viewDao.addRandom_no(random_no);
	}
	//根据random_no查出原本的oldUrl	yangli	2018-01-12
	public List<String> getOldUrl(String random_no) {
		return viewDao.getOldUrl(random_no);
	}
	//修改图片路径	2018-01-12	杨立
	public int updateUrl(String random_no, String newUrl) {
		return viewDao.updateUrl(random_no,newUrl);
	}



}
