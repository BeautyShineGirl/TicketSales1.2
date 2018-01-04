package cn.nvinfo.dao.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import cn.nvinfo.dao.TicketTypeDao;
import cn.nvinfo.domain.TicketType;
/**
 * 基础管理    票型类别设置
 * @author 杨立   2017-09-19
 *
 */
@Repository
public class TicketTypeDaoImp implements TicketTypeDao {

	@Resource
	private SqlSessionTemplate template;
	
	/*
	 * 获得总记录数
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#getAllCount()
	 */
	public int getAllCount() {
		return template.selectOne("ticketType.getAllCount");
	}

	/*
	 * 获得当前页的数据
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#getPageDate(java.lang.Integer, java.lang.Integer)
	 */
	public List<TicketType> getPageDate(Integer pageIndex, Integer pageSize) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pageIndex", (pageIndex-1)*pageSize);
		map.put("pageSize", pageSize);
		List<TicketType> list = template.selectList("ticketType.getPageDate", map);
		return list;
	}

	/*
	 * 添加
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#add(cn.nvinfo.domain.CustomType)
	 */
	public int add(TicketType ticketType) {
		return template.insert("ticketType.save",ticketType);
	}

	
	/*
	 * 根据id获得数据
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#getById(java.lang.Integer)
	 */
	public TicketType getById(Integer id) {
		return template.selectOne("ticketType.getById", id);
	}

	/*
	 * 修改
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#edit(cn.nvinfo.domain.CustomType)
	 */
	public int edit(TicketType ticketType) {
		return template.update("ticketType.edit", ticketType);
	}
	
	/*
	 * 删除
	 * (non-Javadoc)
	 * @see cn.nvinfo.dao.CustomTypeDao#delete(java.lang.Integer)
	 */
	public int delete(Integer id) {
		
		return template.delete("ticketType.delete", id);
	}

	/*
	 * 向票型表中添加票型名称
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.TicketTypeService#addDic(java.lang.String)
	 
	public int add(String name) {
		
		return template.insert("ticketType.add",name);
	}
	*/
}
