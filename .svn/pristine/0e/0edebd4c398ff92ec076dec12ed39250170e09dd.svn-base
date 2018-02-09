package cn.nvinfo.dao;

import java.util.List;

import cn.nvinfo.domain.Product;
import cn.nvinfo.domain.ViewMessage;

public interface JuntuDao {
	//添加骏图景区到数据库	2018-01-25
	int addView(ViewMessage view);
	//添加骏图产品到数据库	2018-01-26
	int addProduct(Product product);
	//从产品表中获取产品的id的集合	yangli 2018-01-29
	List<Product> getJuntuIdList();
	//先从数据库中查是否有这个juntuId，如果有，则不存入数据库，如果没有，则存		yangli	2018-01-31
	ViewMessage getJuntuId(Integer juntuId);
	//根据juntuPId查询数据库中的信息，若存在，则不存进数据库，若不存在，则存	杨立2018-01-31
	Product getProduct(int juntuPId);
	//将第一天的销售价，结算价，数量放入产品表中	yangli	2018-01-31
	int addPriceNum(Integer juntuPId, String salePrice, Double endPrice,
			Integer num);
	//添加价格日期到价格日期表	2018-01-31	yangli
	int saveCalendar(String datePrice, Integer juntuPid);
	//将pid存入价格日历表	2018-01-31	杨立
	int addCalendarPid(Integer pid);
	//修改从 产品表中的信息		yangli 2018-02-09
	int editProduct(Product product);
	//如果在数据库中有景区的话，修改景区信息到最新 		杨立	2018-02-09
	int editView(ViewMessage view);

}
