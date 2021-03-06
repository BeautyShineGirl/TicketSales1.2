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

import com.alibaba.fastjson.JSONArray;

import cn.nvinfo.service.UserService;
import cn.nvinfo.utils.HttpUtil;
/**
 * 骏图.骏景宝
 * 出票通知接口
 * @author 杨立	2018-02-27
 */
public class GetTicketNotice extends HttpServlet {

	private static Logger log=Logger.getLogger(GetTicketNotice.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			
			log.info("=====================================GetTicketNotice=====================================");
			log.info("=====================================骏图出票通知开始=====================================");
			
			//获取spring容器中的service
			WebApplicationContext  ctx= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			UserService userService = (UserService) ctx.getBean("userService",UserService.class);
			String receivePost =HttpUtil.receivePost(req);
			log.info("出票通知返回的数据：receivePost="+receivePost);
			
			if(receivePost!=null||"".equals(receivePost)){
				com.alibaba.fastjson.JSONObject json=com.alibaba.fastjson.JSONObject.parseObject(receivePost);
				Integer status =0;
				String resdata=null;
				resdata=json.getString("data");
				status=Integer.parseInt(json.getString("status"));
				if(status==10000){
					/*
					 * orderId	string	required	骏景宝订单号
					 *partnerOrderId	string	required	合作商订单号
					 *ticketingType	integer	required	出票类型，0，未出票，1出票成功，2出票失败，3出票待定
					 *该字段为预留字段，此结果并不代表最终出票结果，出票结果需与客服核实，如因此字段造成的损失由合作商自行承担
					 *voucherType	integer	required	凭证码类型；0为不回传凭证或无法回传凭证时。1 返回凭证时 
					 *vouchers	Array[Voucher]	optional	出票成功时为必传字段；凭证码数组 
					 *		voucher	string	optional	凭证码
					 *		ticketName	string	optional	门票名称
					 *		quantity	string	optional	数量
					 *		status	integer	optional	凭证状态;0为未使用，1为已使用，3为已作废
					 */
					com.alibaba.fastjson.JSONObject jsonData1=com.alibaba.fastjson.JSONObject.parseObject(resdata);
					String supplierOrder=jsonData1.getString("orderId");
					String orderId=jsonData1.getString("partnerOrderId");
					Integer ticketingType=jsonData1.getInteger("ticketingType");
					Integer voucherType=jsonData1.getInteger("voucherType");
					JSONArray vouchers = jsonData1.getJSONArray("vouchers");
					//将出票通知暂时存成字符串，放到订单表中的remark字段，并标明通知时间	yangli	2018-03-01
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
					String noticeDate=df.format(new Date());
					String remark="临时存放：出票通知时间："+noticeDate+"供应商订单号:"+supplierOrder+", 订单号orderId："+orderId+", 出票类型:"+ticketingType+", 凭证码类型:"+voucherType+", 凭证码:"+vouchers;
					userService.saveTicketNotice(orderId,remark);
					log.info("出票通知及日期写入数据库");
					JSONObject js_res=new JSONObject();
					js_res.put("status","10000");
					resp.getOutputStream().print(js_res.toString());
					log.info("骏图出票通知成功并存入数据库");
				}else{
					JSONObject js_res=new JSONObject();
					js_res.put("status","10001");
					resp.getOutputStream().print(js_res.toString());
					
					log.info("骏图出票通知接受失败");
				}
			}
			log.info("=====================================骏图出票通知结束=====================================");
	}

}
