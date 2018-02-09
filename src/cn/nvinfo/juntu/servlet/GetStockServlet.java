package cn.nvinfo.juntu.servlet;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import cn.nvinfo.domain.CalendarPriceTable;
import cn.nvinfo.domain.Product;
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
 * 获取库存价格日历
 * @author 杨立	2018-01-23
 *
 */
@Controller
public class GetStockServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String url="http://p-api.eticket.juntu.com/ticket/getStock";
	private static Logger log=Logger.getLogger(GetStockServlet.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	/*
	 * 获取价格日历需要给第三方传productId
	 * 第一步，从产品表中获取产品的id的集合，遍历
	 * 第二部，请求第三方接口，获取价格日历
	 * 第三部，处理价格日历村日数据库中
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		//获取spring容器中的service
		WebApplicationContext  ctx= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		JuntuService juntuService = (JuntuService) ctx.getBean("juntuService",JuntuService.class);
		DecimalFormat df=new DecimalFormat("#.0");
		//获取从当前日期开始,到两个月后的今天2018-01-29到2018-03-29
		//开始日期
		String startTime=sdf.format(new Date());
		//结束日期,获取两个月后的今天
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 2);
		String endTime=sdf.format(c.getTime());
		//从产品表中获取产品的id的集合	yangli 2018-01-29
		List<Product> idList = juntuService.getJuntuIdList();
		for (int i = 0; i < idList.size(); i++) {
			log.info("----------------------------------------------------------------------------------------------------------------------------");
			Map<String,String> map=new HashMap<String, String>();
			map.put("version", "");//1版本号
			map.put("partnerId", JuntuConfig.partnerId);//2合作商ID
			String timestamp=String.valueOf(System.currentTimeMillis());
			map.put("timestamp", timestamp);//3请求发起的时间戳
			//处理data数据
			JSONObject js=new JSONObject();
			js.put("productId",idList.get(i).getJuntuPId());
			js.put("startTime",startTime);
			js.put("endTime", endTime);
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
				log.info("请求第三方返回的参数（主要得知总数据）：status="+status+", message="+message+", resdata="+resdata);
				if(status==10000){
					com.alibaba.fastjson.JSONObject jsonData1=com.alibaba.fastjson.JSONObject.parseObject(resdata);
					String data_res=jsonData1.getString("data");
					Integer juntuPid = idList.get(i).getJuntuPId();
					/*
					 * 每次请求都获得一个产品在两个月的日期价格，每天为一个对象
					 * {"productId": 2756,	"date": "2018-02-05","salesPrice": 10000,"settlePrice": 5600,"quantity": 5000},其中quantity为可售数量
					 * 销售价和结算价转成元，在进行操作
					 * 当data_res为空的时候，两个月的日期都处理成0
					 * 当data_res不为空时，没数据的日期销售价处理成0
					 */
					//添加该产品所对应的价格日历表
					//获取从当前时间开始的两个月的信息
					Calendar calendar=Calendar.getInstance();
					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+2, calendar.get(Calendar.DATE));
					long next =calendar.getTimeInMillis();
					calendar.add(Calendar.MONTH,-1);  // 这里想要得到当月的月份，这里写0不行？
					List<String> list2 =new ArrayList<String>();
					while(calendar.getTimeInMillis()!=next)
					{
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						String localeString = format.format(calendar.getTime());
						String substring = localeString.substring(0, 10);
						list2.add(substring);
						int k=1;
						calendar.add(Calendar.DAY_OF_MONTH, k++);
					}
					List<String> list =new ArrayList<String>();
					for (String list1 : getDates()) {
						list.add(list1);
					}
					for (String list3 : list2) {
						list.add(list3);
					}
					String datePrice=null;

					JSONArray ja=null;
					try {
						ja = new JSONArray(data_res);
						if(ja==null||ja.length()==0){
							for (String date_old : list) {
								if(datePrice==null||datePrice.equals("")){
									datePrice=date_old+"&0.0";
								}else{
									datePrice=datePrice+"|"+date_old+"&0.0";
								}
							}
							log.info("返回的data数据位空，juntuPid="+juntuPid+", datePrice="+datePrice);
						}else{
							com.alibaba.fastjson.JSONObject jsonData2=com.alibaba.fastjson.JSONObject.parseObject(ja.getString(1));
							//获得Stock对象，进行处理
							Integer juntuPId = jsonData2.getInteger("productId");
							Double salePrice = jsonData2.getInteger("salesPrice")*0.01;
							Double endPrice=jsonData2.getInteger("settlePrice")*0.01;
							Integer num=jsonData2.getInteger("quantity");
							//将第一天的销售价，结算价，数量放入产品表中	yangli	2018-01-31
							juntuService.addPriceNum(juntuPId,df.format(salePrice),endPrice,num);
							log.info("将第一天的销售价，结算价，数量已放入产品表中");

							/*当data_res为空的时候，两个月的日期都处理成0
							 * 当data_res不为空时，没数据的日期销售价处理成0
							 * //将date和salePrice拼在一起
							 */


							com.alibaba.fastjson.JSONObject jsonData3=null;
							for (String date_old : list) {
								String stock=ja.getString(1);//一个stock是一个产品一天的情况
								jsonData3=com.alibaba.fastjson.JSONObject.parseObject(stock);
								String date = jsonData3.getString("date");
								Double salesPrice = jsonData2.getInteger("salesPrice")*0.01;
								if(date_old.equals(date)){
									if(datePrice==null||datePrice.equals("")){
										datePrice=date+"&"+df.format(salesPrice);
									}else{
										datePrice=datePrice+"|"+date+"&"+df.format(salesPrice);
									}
								}else{
									if(datePrice==null||datePrice.equals("")){
										datePrice=date_old+"&0.0";
									}else{
										datePrice=datePrice+"|"+date_old+"&0.0";
									}
								}
							}
							log.info("返回的data数据不位空，juntuPid="+juntuPid+", datePrice="+datePrice);
						}
						//添加价格日期到价格日期表	2018-01-31	yangli
						juntuService.saveCalendar(datePrice,juntuPid);
						log.info("将价格日历存入数据库，juntuPid="+juntuPid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}else{
					log.info("第三方返回的参数2：status="+status+", message="+message+", resdata="+resdata);
				}
			}
		}		
	}

	/*
	 * 获得从当前日期开始一个月的日期
	 */
	public static List<String> getDates() {
		Calendar calendar=Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DATE));
		long next =calendar.getTimeInMillis();
		calendar.add(Calendar.MONTH,-1);  // 这里想要得到当月的月份，这里写0不行？
		List<String> list =new ArrayList<String>();
		while(calendar.getTimeInMillis()!=next)
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String localeString = format.format(calendar.getTime());

			String substring = localeString.substring(0, 10);
			list.add(substring);
			int i=1;
			calendar.add(Calendar.DAY_OF_MONTH, i++);
		}
		return list;
	}
}