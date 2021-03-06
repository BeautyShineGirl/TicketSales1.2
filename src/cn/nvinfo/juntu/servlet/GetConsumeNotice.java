package cn.nvinfo.juntu.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.nvinfo.service.UserService;
import cn.nvinfo.utils.HttpUtil;
/**
 * 骏图.骏景宝
 * 核销通知接口
 * @author 杨立	2018-02-26
 */

public class GetConsumeNotice extends HttpServlet {

	private static Logger log=Logger.getLogger(GetConsumeNotice.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			
			log.info("=====================================GetConsumeNotice=====================================");
			log.info("=====================================骏图核销通知开始=====================================");
			
			//获取spring容器中的service
			WebApplicationContext  ctx= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			UserService userService = (UserService) ctx.getBean("userService",UserService.class);
			String receivePost =HttpUtil.receivePost(req);
			log.info("核销通知返回的数据：receivePost="+receivePost);
			
			if(receivePost!=null||"".equals(receivePost)){
				com.alibaba.fastjson.JSONObject json=com.alibaba.fastjson.JSONObject.parseObject(receivePost);
				Integer status =0;
				String resdata=null;
				resdata=json.getString("data");
				status=Integer.parseInt(json.getString("status"));
				if(status==10000){
					/*
					 * orderId	string	required	骏景宝订单号
					 * partnerOrderId	integer	required	合作商订单号
					 */
					com.alibaba.fastjson.JSONObject jsonData1=com.alibaba.fastjson.JSONObject.parseObject(resdata);
					String supplierOrder=jsonData1.getString("orderId");
					String orderId=jsonData1.getString("partnerOrderId");
					//将订单的orderState状态改为2 已核销，并获取当前时间，将时间写入数据库verDate的字段中	2018-02-26	yangli
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
					String verDate=df.format(new Date());
					Integer orderState=2;
					userService.updateVerdate(orderId,verDate,orderState);
					log.info("核销状态及日期写入数据库");
					JSONObject js_res=new JSONObject();
					js_res.put("status","10000");
					resp.getOutputStream().print(js_res.toString());
					log.info("骏图核销通知成功并存入数据库");
				}else{
					JSONObject js_res=new JSONObject();
					js_res.put("status","10001");
					resp.getOutputStream().print(js_res.toString());
					
					log.info("核销通知接受失败");
				}
			}
			log.info("=====================================核销通知结束=====================================");
	}

}
