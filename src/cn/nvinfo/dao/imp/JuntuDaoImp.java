package cn.nvinfo.dao.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import cn.nvinfo.dao.JuntuDao;
import cn.nvinfo.domain.Product;
import cn.nvinfo.domain.ViewMessage;
@Repository
public class JuntuDaoImp implements JuntuDao {
	@Resource
	private SqlSessionTemplate template;
	//添加骏图景区到数据库	2018-01-25
	public int addView(ViewMessage view) {
		return template.insert("juntu.addView", view);
	}
	//添加骏图产品到数据库	2018-01-26
	public int addProduct(Product product) {
		return template.insert("juntu.addProduct", product);
	}
	//从产品表中获取产品的id的集合	yangli 2018-01-29
	public List<Product> getJuntuIdList() {
		return template.selectList("juntu.getJuntuIdList");
	}
	//先从数据库中查是否有这个juntuId，如果有，则不存入数据库，如果没有，则存		yangli	2018-01-31
	public ViewMessage getJuntuId(Integer juntuId) {
		return template.selectOne("juntu.getJuntuId", juntuId);
	}
	//根据juntuPId查询数据库中的信息，若存在，则不存进数据库，若不存在，则存	杨立2018-01-31
	public Product getProduct(int juntuPId) {
		return template.selectOne("juntu.getProduct", juntuPId);
	}
	//将第一天的销售价，结算价，数量放入产品表中	yangli	2018-01-31
	public int addPriceNum(Integer juntuPId, String salePrice, Double endPrice,
			Integer num) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("juntuPId", juntuPId);
		map.put("salePrice", salePrice);
		map.put("endPrice", endPrice);
		map.put("num", num);
		return template.update("juntu.addPriceNum", map);
	}
	//添加价格日期到价格日期表	2018-01-31	yangli
	public int saveCalendar(String datePrice, Integer juntuPid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("datePrice", datePrice);
		map.put("juntuPid", juntuPid);
		return template.update("juntu.saveCalendar", map);
	}
	//将pid存入价格日历表	2018-01-31	杨立
	public int addCalendarPid(Integer pid) {
		return template.insert("juntu.addCalendarPid", pid);
	}
	//修改从 产品表中的信息		yangli 2018-02-09
	public int editProduct(Product product) {
		return template.update("juntu.editProduct", product);
	}
	//如果在数据库中有景区的话，修改景区信息到最新 		杨立	2018-02-09
	public int editView(ViewMessage view) {
		return template.update("juntu.editView", view);
	}
}
