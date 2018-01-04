package cn.nvinfo.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.nvinfo.dao.TicketTypeDao;
import cn.nvinfo.domain.TicketType;
import cn.nvinfo.service.TicketTypeService;
import cn.nvinfo.utils.Pager;
/**
 * 基础管理    票型类别设置
 * @author 杨立   2017-09-19
 *
 */
@Service
@Transactional
public class TicketTypeServiceImp implements TicketTypeService {

	@Resource
	private TicketTypeDao ticketTypeDao;
	
	/*
	 * 分页查询
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.TicketTypeService#getPager(java.lang.Integer, java.lang.Integer)
	 */
	public Pager<TicketType> getPager(Integer pageIndex, Integer pageSize) {
		Pager<TicketType> pager =new Pager<TicketType>();
		pager.setCurrPage(pageIndex);
		pager.setPageSize(pageSize);
		pager.setAllCount(ticketTypeDao.getAllCount());
		pager.setDatas(ticketTypeDao.getPageDate(pageIndex,pageSize));
		pager.setPageCount();
		return pager;
	}

	/*
	 * 添加
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.TicketTypeService#add(cn.nvinfo.domain.TicketType)
	 */
	public int add(TicketType ticketType) {
		
		return ticketTypeDao.add(ticketType);
	}

	/*
	 * 根据id获取数据
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.TicketTypeService#getById(java.lang.Integer)
	 */
	public TicketType getById(Integer id) {
		return ticketTypeDao.getById(id);
	}

	/*
	 * 修改
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.TicketTypeService#edit(cn.nvinfo.domain.TicketType)
	 */
	public int edit(TicketType ticketType) {
		return ticketTypeDao.edit(ticketType);
	}

	/*
	 * 删除
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.TicketTypeService#delete(java.lang.Integer)
	 */
	public int delete(Integer id) {
		return ticketTypeDao.delete(id);
	}

	/*
	 * 向票型表中添加票型名称
	 * (non-Javadoc)
	 * @see cn.nvinfo.service.TicketTypeService#addDic(java.lang.String)
	 
	public int addDic(String name) {
		
		return ticketTypeDao.add(name);
	}
	 */
	

}
