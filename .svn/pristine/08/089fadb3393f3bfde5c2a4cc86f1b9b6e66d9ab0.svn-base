package cn.nvinfo.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.nvinfo.dao.HotProductDao;
import cn.nvinfo.domain.HotProduct;
import cn.nvinfo.service.HotProductServcie;
import cn.nvinfo.utils.Pager;
/**
 * 用于参加热销抢购系统
 * @author yangli	2017-12-25
 *
 */
@Service
@Transactional
public class HotProductServcieImp implements HotProductServcie {
	
	@Resource
	private HotProductDao hpd;
	//查询抢购产品的所有信息返回给前台	2017-12-25
	public List<HotProduct> getProduct() {
		return hpd.getProduct();
	}
	//通过id查询抢购产品的详细	2017-12-25	杨立
	public HotProduct getHotProductById(Integer id) {
		return hpd.getHotProductById(id);
	}
	//根据random_no查出原本的oldUrl	yangli	2017-12-25
	public List<String> getOldUrl(String random_no) {
		return hpd.getOldUrl(random_no);
	}
	//修改图片路径	2017-12-25	杨立
	public int updateUrl(String random_no, String newUrl) {
		return hpd.updateUrl(random_no,newUrl);
	}
	// 分页查询接口	杨立	2017-12-26
	public Pager<HotProduct> getPager(Integer pageIndex,Integer pageSize) {
		Pager<HotProduct> pager=new Pager<HotProduct>();
		pager.setCurrPage(pageIndex);
		pager.setPageSize(pageSize);
		pager.setAllCount(hpd.getAllCount());
		pager.setDatas(hpd.getPageDate(pageIndex,pageSize));
		pager.setPageCount();
		return pager;
	}
	//添加抢购产品，除路径外的其他信息	杨立	2017-12-26	
	public int add(HotProduct hotProduct) {
		return hpd.add(hotProduct);
	}
	//先添加产品随机数，就相当于添加了一个产品，然后在对这个产品进行修改url
	public int addRandom_no(String random_no) {
		return hpd.addRandom_no(random_no);
	}
	//先查询数据库中是否存在这个随机数，若存在，则不进行添加随机数
	public List<String> getRandom_no(String random_no) {
		return hpd.getRandom_no(random_no);
	}
	//删除抢购产品	杨立	2017-12-27
	public int delete(Integer id) {
		return hpd.delete(id);
	}

}
