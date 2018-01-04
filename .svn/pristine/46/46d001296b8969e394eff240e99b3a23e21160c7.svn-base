package cn.nvinfo.service;

import java.util.List;

import cn.nvinfo.domain.HotProduct;
import cn.nvinfo.utils.Pager;

/**
 * 用于参加热销抢购系统
 * @author yangli	2017-12-25
 *
 */
public interface HotProductServcie {
	//查询抢购产品的所有信息返回给前台	2017-12-25
	List<HotProduct> getProduct();
	//通过id查询抢购产品的详细	2017-12-25	杨立
	HotProduct getHotProductById(Integer id);
	//根据random_no查出原本的oldUrl	yangli	2017-12-25
	List<String> getOldUrl(String random_no);
	//修改图片路径	2017-12-25	杨立
	int updateUrl(String random_no, String newUrl);
	// 分页查询接口	杨立	2017-12-26
	Pager<HotProduct> getPager(Integer pageIndex, Integer pageSize);
	//添加抢购产品，除路径外的其他信息	杨立	2017-12-26	
	int add(HotProduct hotProduct);
	//先添加产品随机数，就相当于添加了一个产品，然后在对这个产品进行修改url
	int addRandom_no(String random_no);
	//先查询数据库中是否存在这个随机数，若存在，则不进行添加随机数
	List<java.lang.String> getRandom_no(java.lang.String random_no);
	 //删除抢购产品	杨立	2017-12-27
	int delete(Integer id);

}
