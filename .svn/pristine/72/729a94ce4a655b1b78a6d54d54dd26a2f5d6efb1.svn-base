package cn.nvinfo.dao.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import cn.nvinfo.dao.CustomTypeDao;
import cn.nvinfo.domain.CustomType;
/**
 * 分销商管理以及基础设置中的分销商类别设置
 * @author yangli 	2017.09.18
 *
 */

@Repository
public class CustomTypeDaoImp implements CustomTypeDao {

	@Resource
	private SqlSessionTemplate template;
	
	/*
	 * 获得总记录数
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#getAllCount()
	 */
	public int getAllCount() {
		int rows=template.selectOne("customType.getAllCount");
		return rows;
	}

	/*
	 * 获得当前页的数据
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#getPageDate(java.lang.Integer, java.lang.Integer)
	 */
	public List<CustomType> getPageDate(Integer pageIndex, Integer pageSize) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pageIndex", (pageIndex-1)*pageSize);
		map.put("pageSize", pageSize);
		List<CustomType> list = template.selectList("customType.getPageDate", map);
		return list;
	}

	/*
	 * 添加
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#add(cn.nvinfo.domain.CustomType)
	 */
	public int add(CustomType customType) {
		int rows=template.insert("customType.save",customType);
		return rows; 
	}

	/*
	 * 修改
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#edit(cn.nvinfo.domain.CustomType)
	 */
	public int edit(CustomType customType) {
		int rows=template.update("customType.edit", customType);
		return rows;
	}

	/*
	 * 根据id获得数据
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#getById(java.lang.Integer)
	 */
	public CustomType getById(Integer id) {
		 
		return template.selectOne("customType.getById", id);
	}

	/*
	 * 删除
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#delete(java.lang.Integer)
	 */
	public int delete(Integer id) {
		
		return template.delete("customType.delete", id);
	}

}
