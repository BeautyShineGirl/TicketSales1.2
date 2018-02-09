package cn.nvinfo.juntu.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.nvinfo.domain.ViewMessage;
import cn.nvinfo.service.JuntuService;
import cn.nvinfo.tools.MD5;
import cn.nvinfo.utils.DecodeEncode;
import cn.nvinfo.utils.EnDecrypt;
import cn.nvinfo.utils.HttpUtils;
import cn.nvinfo.utils.JuntuConfig;
import cn.nvinfo.utils.StringUtil;

/**
 * 骏图.骏景宝
 * 获取景区信息接口
 * @author 杨立	2018-01-23
 *
 */
@Controller
public class GetPoiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private int total = 0;// 总产品数
	private int pageNum = 20;// 每页显示多少条
	private int currentPage = 1;
	private String url="http://p-api.eticket.juntu.com/ticket/getPoi";
	private static Logger log=Logger.getLogger(GetPoiServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		//获取spring容器中的service
		WebApplicationContext  ctx= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		JuntuService juntuService = (JuntuService) ctx.getBean("juntuService",JuntuService.class);
		Map<String,String> map=new HashMap<String, String>();
		map.put("version", "");//1版本号
		map.put("partnerId", JuntuConfig.partnerId);//2合作商ID
		String timestamp=String.valueOf(System.currentTimeMillis());
		map.put("timestamp", timestamp);//3请求发起的时间戳
		//处理data数据page和pageSize，pageSize=20，page为变量
		JSONObject js=new JSONObject();
		js.put("method","page");
		js.put("page",currentPage);
		js.put("pageSize", pageNum);
		log.info("未加密的json："+js);
		byte[] encrypt = EnDecrypt.encrypt(js.toString().getBytes(), JuntuConfig.partnerKey.getBytes());//AES128加密
		String data = DecodeEncode.encode(encrypt);//对AES加密数据进行一次base64编码
		map.put("data", data);//4业务接口请求数据
		//sign = MD5(partnerId＋ timestamp＋ partnerKey + data)
		String sign = MD5.MD5Encode(JuntuConfig.partnerId+timestamp+JuntuConfig.partnerKey+data);
		map.put("sign",sign);//5请求数据的签名

		String sendPost = HttpUtils.sendPost(url, map);
		log.info("请求发送的数据：url="+url+", map="+map);
		log.info("请求getPoi返回：sendPost="+sendPost);
		if(sendPost!=null||"".equals(sendPost)){
			com.alibaba.fastjson.JSONObject json=com.alibaba.fastjson.JSONObject.parseObject(sendPost);
			Integer status =0;
			String message=null;
			message=json.getString("message");
			String resdata=null;
			resdata=json.getString("data");
			status=Integer.parseInt(json.getString("status"));
			log.info("第一次请求第三方返回的参数（主要得知总数据）：status="+status+", message="+message+", resdata="+resdata);
			com.alibaba.fastjson.JSONObject jsonData1=com.alibaba.fastjson.JSONObject.parseObject(resdata);
			total=(Integer) jsonData1.get("total");
			log.info("请求第三方返回的total="+total);
			if(status==10000){
				if (total <= pageNum) {
					log.info("只有一页数据");
					addView(resdata,juntuService);

				} else {
					if (total % pageNum == 0) {
						for (int i = 1; i < (total / pageNum); i++) {
							++currentPage;
							log.info("当前页" + currentPage);
							sendPost = HttpUtils.sendPost(url, map);
							log.info("继续请求");
							resdata=json.getString("data");
							com.alibaba.fastjson.JSONObject jsonData2=com.alibaba.fastjson.JSONObject.parseObject(resdata);
							resdata=jsonData2.getString("data");
							log.info("获得resdata="+resdata);
							addView(resdata,juntuService);

						}
					} else {
						for (int i = 1; i < (total / pageNum + 1); i++) {
							++currentPage;
							log.info("加1当前页" + currentPage);
							sendPost = HttpUtils.sendPost(url, map);
							log.info("加1继续请求");
							resdata=json.getString("data");
							com.alibaba.fastjson.JSONObject jsonData2=com.alibaba.fastjson.JSONObject.parseObject(resdata);
							resdata=jsonData2.getString("data");
							log.info("加1获得resdata="+resdata);
							addView(resdata,juntuService);
						}
					}
				}
				

			}else{
				log.info("第三方返回的参数2：status="+status+", message="+message+", resdata="+resdata);
			}
		}

	}

	private void addView(String resdata,JuntuService juntuService) {
		JSONArray ja=null;
		try {
			ja = new JSONArray(resdata);
			log.info("获得的resdata转成jsonArray");
			for (int i = 0; i < ja.length(); i++) {
				Integer juntuId=Integer.parseInt((String) ja.getJSONObject(i).get("poiId"));
				ViewMessage view=new ViewMessage();
				view.setJuntuId(juntuId);
				log.info("获得的即将存入数据库的poiId="+juntuId);
				String longitude = ja.getJSONObject(i).getString("longitude");
				if(longitude!=null||!"".equals(longitude)){
					view.setLng(Double.valueOf(longitude));
					log.info("获得的即将存入数据库的longitude="+Double.valueOf(ja.getJSONObject(i).getString("longitude")));
				}
				String latitude = ja.getJSONObject(i).getString("latitude");
				if(latitude!=null||!"".equals(latitude)){
				view.setLat(Double.valueOf(latitude));
				log.info("获得的即将存入数据库的latitude="+Double.valueOf(ja.getJSONObject(i).getString("latitude")));
				}
				
				view.setName(ja.getJSONObject(i).getString("poiName"));
				log.info("获得的即将存入数据库的name="+ja.getJSONObject(i).getString("poiName"));
				
				view.setLevel(ja.getJSONObject(i).getString("level"));
				log.info("获得的即将存入数据库的level="+ja.getJSONObject(i).getString("level"));
				
				view.setAddress(ja.getJSONObject(i).getString("address"));
				log.info("获得的即将存入数据库的address="+ja.getJSONObject(i).getString("address"));
				
				view.setCity(ja.getJSONObject(i).getString("city"));
				log.info("获得的即将存入数据库的city="+ja.getJSONObject(i).getString("city"));
				
				view.setRandom_no(StringUtil.getRandomString(9));
				view.setSort(3);//骏图的景区排序默认为三
				view.setRemark(ja.getJSONObject(i).getString("introduction"));
				log.info("获得的即将存入数据库的introduction="+ja.getJSONObject(i).getString("introduction"));
				//先从数据库中查是否有这个juntuId，如果有，则不存入数据库，如果没有，则存		yangli	2018-01-31
				ViewMessage view_old=juntuService.getJuntuId(juntuId);
				if(view_old!=null){
					//如果在数据库中有景区的话，修改景区信息到最新 		杨立	2018-02-09
					//juntuService.editView(view);
					continue;
				}else{
					log.info("存入数据库操作"+Integer.parseInt((String) ja.getJSONObject(i).get("poiId")));
					juntuService.addView(view);//添加骏图景区到数据库	2018-01-25
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
