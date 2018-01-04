package cn.nvinfo.controller;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.nvinfo.domain.HotProduct;
import cn.nvinfo.domain.Images;
import cn.nvinfo.service.HotProductServcie;
import cn.nvinfo.utils.CheckUtil;
import cn.nvinfo.utils.Pager;
import cn.nvinfo.utils.Result;

/**
 * 用于参加热销抢购系统
 * @author yangli	2017-12-25
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("hotProduct")
public class HotProductAction {
	private static Logger log=Logger.getLogger(HotProductAction.class);
	@Resource
	private HotProductServcie hps;
	private Integer pageSize=10;
	/*
	 * 查询抢购产品的所有信息返回给前台
	 */
	@RequestMapping("/getProduct.action")
	public @ResponseBody Object getProduct(HttpServletRequest request){
		//查询抢购产品的所有信息返回给前台	2017-12-25
		List<HotProduct> list = hps.getProduct();
		if(list.size()!=0){
			for (HotProduct product2 : list) {
				//处理照片
				String picture = product2.getUrl();
				if(picture==null&&"".equals("")){
					product2.setImages(null);
				}else{
					String[] split = picture.split(",");	
					List<Images> list2=new ArrayList<Images>();
					for (String string : split) {
						Images images=new Images();
						images.setName(string);
						//正式服务器
						String path="http://service.nvinfo.cn:8080"+request.getContextPath()+"/upload/images/hotProduct/"+string;
						//String path="http://test.elvmedia.cn:8080"+request.getContextPath()+"/upload/images/hotProduct/"+string;
						//String path="http://192.168.1.109:8080"+request.getContextPath()+"/upload/images/hotProduct/"+string;
						images.setUrl(path);
						list2.add(images);
					}
					product2.setImages(list2);
				}
			}
			log.info(new Result(1,"查询成功",list));
			return new Result(1,"查询成功",list);
		}else{
			log.info(new Result(0,"暂无数据"));
			return new Result(0,"暂无数据");
		}
	}

	/*
	 * 通过id查询抢购产品的详细
	 */
	@RequestMapping("/getProductDetail.action")
	public @ResponseBody Object getProductDetail(Integer id,HttpServletRequest request){
		//验证参数
		if(!"".equals(CheckUtil.checkArgsNotNull(id))){
			log.info(new Result(0,"参数错误"));
			return new Result(0,"参数错误");
		}
		//通过id查询抢购产品的详细	2017-12-25	杨立
		HotProduct HotProduct=hps.getHotProductById(id);
		if(HotProduct==null){
			log.info(new Result(0,"无数据"));
			return new Result(0,"无数据");
		}
		//处理照片
		String picture = HotProduct.getUrl();
		if(picture==null&&"".equals("")){
			HotProduct.setImages(null);
		}else{
			String[] split = picture.split(",");	
			List<Images> list2=new ArrayList<Images>();
			for (String string : split) {
				Images images=new Images();
				images.setName(string);
				//正式服务器
				String path="http://service.nvinfo.cn:8080"+request.getContextPath()+"/upload/images/hotProduct/"+string;
				//String path="http://test.elvmedia.cn:8080"+request.getContextPath()+"/upload/images/"+string;
				//String path="http://192.168.1.109:8080"+request.getContextPath()+"/upload/images/hotProduct/"+string;
				images.setUrl(path);
				list2.add(images);
			}
			HotProduct.setImages(list2);
		}
		log.info(new Result(1,"查询成功",HotProduct));
		return new Result(1,"查询成功",HotProduct);
	}

	/*
	 * 分页查询接口	杨立	2017-12-26
	 */
	@RequestMapping("/findPageData.action")
	public @ResponseBody Object findPageData(Integer pageIndex){
		//验证参数
		if(!"".equals(CheckUtil.checkArgsNotNull(pageIndex))){
			log.info(new Result(0,"参数错误"));
			return new Result(0,"参数错误");
		}
		Pager<HotProduct> pager=hps.getPager(pageIndex,pageSize);
		log.info("分页查询提交条件：pageIndex="+pageIndex);
		if(pager.getDatas().size()!=0){
			for (int i = 0; i < pager.getDatas().size(); i++) {
				Long startTime =Long.parseLong(pager.getDatas().get(i).getStartTime()) ;
				Long endTime =Long.parseLong(pager.getDatas().get(i).getEndTime());
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();  
				date.setTime(startTime);
				pager.getDatas().get(i).setStartTime(sd.format(date));
				Date date1 = new Date();
				date1.setTime(endTime);
				pager.getDatas().get(i).setEndTime(sd.format(date1));
			}
			log.info(new Result(1,"查询成功",pager));
			return new Result(1,"查询成功",pager);
		}else{
			log.info(new Result(0,"暂无数据",pager));
			return new Result(0,"暂无数据",pager);
		}
	}
	/*
	 * 删除抢购产品	杨立	2017-12-27
	 */
	@RequestMapping("delete.action")
	public @ResponseBody Object delete(Integer id,String random_no,HttpServletRequest request){
		//验证参数
		if(!"".equals(CheckUtil.checkArgsNotNull(id))){
			log.info(new Result(0,"参数错误"));
			return new Result(0,"参数错误");
		}
		//查询
		HotProduct ht = hps.getHotProductById(id);
		String[] split = ht.getUrl().split(",");
		for (String string : split) {
			/*
			 * 先删除服务器中的图片，然后更新表中的url
			 */
			String src=request.getSession().getServletContext().getRealPath("upload/images/hotProduct/")+File.separator;
			String path=src+string;
			// 判断目录或文件是否存在  
			File file1 = new File(path); 
			// 路径为文件且不为空则进行删除  
			if (file1.isFile() && file1.exists()) {  
				file1.delete();  
			} 
		}
		int rows=hps.delete(id);
		if(rows>0){
			log.info(new Result(1,"删除成功"));
			return new Result(1,"删除成功");
		}else{
			log.info(new Result(0,"删除失败"));
			return new Result(0,"删除失败");
		}
	}

	/**
	 * 修改数据回显
	 * @param id
	 * @return
	 */
	@RequestMapping("/editUI.action")
	public @ResponseBody Object editUI(Integer id,HttpServletRequest request){
		//验证参数
		if(!"".equals(CheckUtil.checkArgsNotNull(id))){
			log.info(new Result(0,"参数错误"));
			return new Result(0,"参数错误");
		}
		//查询
		HotProduct ht = hps.getHotProductById(id);
		Long startTime =Long.parseLong(ht.getStartTime()) ;
		Long endTime =Long.parseLong(ht.getEndTime());
		ht.setStart(ht.getStartTime());
		ht.setEnd(ht.getEndTime());
		SimpleDateFormat sd=new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();  
		date.setTime(startTime);
		ht.setStartTime(sd.format(date));
		Date date1 = new Date();
		date1.setTime(endTime);
		ht.setEndTime(sd.format(date1));
		if(ht.getUrl()!=null){
			String[] split = ht.getUrl().split(",");	
			List<Images> list2=new ArrayList<Images>();
			for (String string : split) {
				Images images=new Images();
				images.setName(string);
				//正式服务器
				String path="http://service.nvinfo.cn:8080"+request.getContextPath()+"/upload/images/hotProduct/"+string;
				//String path="http://test.elvmedia.cn:8080"+request.getContextPath()+"/upload/images/"+string;
				//String path="http://192.168.1.109:8080"+request.getContextPath()+"/upload/images/hotProduct/"+string;
				images.setUrl(path);
				list2.add(images);
			}
			ht.setImages(list2);
		}
		log.info(new Result(1,"查询成功"));
		return new Result(1,"查询成功",ht);
	}

	/*
	 * 后台管理      添加抢购产品
	 */
	@RequestMapping(value="/add.action")
	public @ResponseBody Object add(HotProduct hotProduct){
		//添加操作	添加抢购产品，除路径外的其他信息	杨立	2017-12-26	
		int rows= hps.add(hotProduct);
		Integer pageIndex=1;
		Pager<HotProduct> pager1=hps.getPager(pageIndex,pageSize);
		Integer pageCount =pager1.getPageCount();
		Pager<HotProduct> pager=hps.getPager(pageCount,pageSize);
		if(rows>0){
			log.info(new Result(1,"添加成功",pager));
			return new Result(1,"添加成功",pager);
		} else {
			log.info(new Result(0,"添加失败"));
			return new Result(0,"添加失败");
		}
	}

	/*
	 * 后台管理   修改产品  删除图片
	 */ 
	@RequestMapping("/deleteImages.action")
	public @ResponseBody Object deleteImages(HttpServletRequest request,String random_no,String deleteURL){
		String newUrl=null;
		/*
		 * 先删除服务器中的图片，然后更新表中的url
		 */
		String src=request.getSession().getServletContext().getRealPath("upload/images/hotProduct/")+File.separator;
		String path=src+deleteURL;
		// 判断目录或文件是否存在  
		File file1 = new File(path); 
		// 路径为文件且不为空则进行删除  
		if (file1.isFile() && file1.exists()) {  
			file1.delete();  
		} 
		
		/*
		 * 更新数据库中的url
		 */
		//根据random_no查出原本的oldUrl	yangli	2017-12-25
		List<String> oldUrl=hps.getOldUrl(random_no);
		if(deleteURL!=null||!"".equals(deleteURL)){
			String editUrl=oldUrl.get(0);
			if(oldUrl.size()!=0){
				String[] split = editUrl.split(",");
				for (int i = 0; i < split.length; i++) {
					if(!split[i].equals(deleteURL)){
						if(newUrl==null){
							newUrl=split[i];
						}else{
							newUrl=newUrl+","+split[i];
						}
					}
				}
			}
		}
		//修改图片路径	2017-12-25	杨立
		int rows= hps.updateUrl(random_no,newUrl);
		if(rows>0){
			log.info(new Result(1,"删除成功"));
			return new Result(1,"删除成功");
		} else {
			log.info(new Result(0,"删除失败"));
			return new Result(0,"删除失败");
		} 
	}

	/*
	 * 后台管理      添加抢购产品的图片上传到服务器
	 */ 
	@RequestMapping("/addImages.action")
	public @ResponseBody Object addImages(MultipartFile file,HttpServletRequest request,String random_no,Integer logic){
		String newUrl=null;
		//先查询数据库中是否存在这个随机数，若存在，则不进行添加随机数
		List<String> random_no_old=hps.getRandom_no(random_no);
		if(random_no_old.size()==0){
			//先添加产品随机数，就相当于添加了一个产品，然后在对这个产品进行修改url
			hps.addRandom_no(random_no);
		}
		String iconName=file.getOriginalFilename();

		//根据random_no查出原本的oldUrl	yangli	2017-12-25
		List<String> oldUrl=hps.getOldUrl(random_no);
		//保存文件  
		saveFile(file,request);  
		if(oldUrl.size()!=0){
			//保存获取文件的路径
			if(oldUrl.get(0)==null||oldUrl.get(0).equals("")){
				newUrl=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+iconName;
			}else{
				newUrl=oldUrl.get(0)+","+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+iconName;
			}
		}
		//修改图片路径	2017-12-25	杨立
		int rows= hps.updateUrl(random_no,newUrl);
		if(rows>0){
			log.info(new Result(1,"上传成功"));
			return new Result(1,"上传成功");
		} else {
			log.info(new Result(0,"上传失败"));
			return new Result(0,"上传失败");
		} 
	}

	public boolean saveFile(MultipartFile file,HttpServletRequest request) { 
		if(file!=null){
			String iconName=file.getOriginalFilename();
			// 校验图片格式  
			// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分  
			String lastName = iconName.substring(iconName.lastIndexOf("\\") + 1);  
			// 得到上传文件的扩展名  
			String fileExtName = lastName.substring(lastName.lastIndexOf(".") + 1);//doc,docx,pdf
			// 校验图片格式 	这段代码没有测试
			if("bmp".equalsIgnoreCase(fileExtName)||"jpg".equalsIgnoreCase(fileExtName)||
					"jpeg".equalsIgnoreCase(fileExtName)||"gif".equalsIgnoreCase(fileExtName)||"png".equalsIgnoreCase(fileExtName)){
				//获取上传文件的路径
				String src=request.getSession().getServletContext().getRealPath("upload/images/hotProduct/")+File.separator;
				String path=src+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+iconName;
				File newFile=new File(path);
				try {
					file.transferTo(newFile);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		return false; 	
	}


}
