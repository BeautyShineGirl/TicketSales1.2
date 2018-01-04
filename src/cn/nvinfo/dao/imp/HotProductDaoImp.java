package cn.nvinfo.dao.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import cn.nvinfo.dao.HotProductDao;
import cn.nvinfo.domain.HotProduct;
/**
 * 用于参加热销抢购系统
 * @author yangli	2017-12-25
 *
 */
@Repository
public class HotProductDaoImp implements HotProductDao{

	@Resource
	private SqlSessionTemplate template;
	//查询抢购产品的所有信息返回给前台	2017-12-25
	public List<HotProduct> getProduct() {
		return template.selectList("hotProduct.getProduct");
	}
	
	//通过id查询抢购产品的详细	2017-12-25	杨立
	public HotProduct getHotProductById(Integer id) {
		return template.selectOne("hotProduct.getHotProductById", id);
	}
	//根据random_no查出原本的oldUrl	yangli	2017-12-25
	public List<String> getOldUrl(String random_no) {
		return template.selectList("hotProduct.getOldUrl", random_no);
	}
	//修改图片路径	2017-12-25	杨立
	public int updateUrl(String random_no, String newUrl) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("random_no", random_no);
		map.put("newUrl", newUrl);
		return template.update("hotProduct.updateUrl", map);
	}
	//分页查询，获得总记录数
	public int getAllCount() {
		return template.selectOne("hotProduct.getAllCount");
	}
	//分页查询，获得所有数据
	public List<HotProduct> getPageDate(Integer pageIndex, Integer pageSize) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("pageIndex", (pageIndex-1)*pageSize);
		map.put("pageSize", pageSize);
		return template.selectList("hotProduct.getPageDate",map);
	}
	//添加抢购产品，除路径外的其他信息	杨立	2017-12-26	
	public int add(HotProduct hotProduct) {
		return template.update("hotProduct.add", hotProduct);
	}
	//先添加产品随机数，就相当于添加了一个产品，然后在对这个产品进行修改url
	public int addRandom_no(String random_no) {
		return template.insert("hotProduct.addRandom_no", random_no);
	}
	//先查询数据库中是否存在这个随机数，若存在，则不进行添加随机数
	public List<String> getRandom_no(String random_no) {
		return  template.selectList("hotProduct.getRandom_no", random_no);
	}
	//删除抢购产品	杨立	2017-12-27
	public int delete(Integer id) {
		return template.delete("hotProduct.delete", id);
	}
}
