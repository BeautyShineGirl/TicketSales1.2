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
import cn.nvinfo.utils.SMS;

public class RefundApplyResult extends HttpServlet {
	private static Logger log=Logger.getLogger(RefundApplyResult.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			
			log.info("=====================================RefundApplyResult=====================================");
			log.info("=====================================骏图退款通知开始=====================================");
			
			//获取spring容器中的service
			WebApplicationContext  ctx= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			UserService userService = (UserService) ctx.getBean("userService",UserService.class);
			String receivePost =HttpUtil.receivePost(req);
			log.info("退款通知返回的数据：receivePost="+receivePost);
			
			if(receivePost!=null||"".equals(receivePost)){
				com.alibaba.fastjson.JSONObject json=com.alibaba.fastjson.JSONObject.parseObject(receivePost);
				Integer status =0;
				String message=null;
				message=json.getString("message");
				String resdata=null;
				resdata=json.getString("data");
				status=Integer.parseInt(json.getString("status"));
				log.info("请求骏图查询接口返回的参数（主要得知总数据）：status="+status+", message="+message+", resdata="+resdata);
				if(status==10000){
					/*
					 *orderId	string	required	骏景宝订单号
					 *partnerOrderId	string	required	合作商订单号
					 *refundId	string	required	退款流水号
					 *status	integer	required	审核结果状态 1 同意退款 2 拒绝退款
					 *refundFee	Integer	required	退款手续费
					 *requestTime	string	Required	申请退款时间 格式：2017-10-10 16:29:28
					 *responseTime	string	Required	审核退款时间 格式：2017-10-10 16:59:28
					 */
					com.alibaba.fastjson.JSONObject jsonData1=com.alibaba.fastjson.JSONObject.parseObject(resdata);
					String supplierOrder=jsonData1.getString("orderId");//骏景宝订单号
					String partnerOrderId=jsonData1.getString("partnerOrderId");//合作商订单号
					String refundId=jsonData1.getString("refundId");//退单款流水号
					Integer status_no=(Integer)jsonData1.get("status");//审核结果状态 1 同意退款 2 拒绝退款
					String requestTime=jsonData1.getString("requestTime");//申请退款时间2017-10-10 16:29:28
					String responseTime=jsonData1.getString("responseTime");//审核退款时间
					SimpleDateFormat df=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
					if(status_no==1){
						status_no=3;
					}else{
						status_no=0;
						//发短信告知票务
						//模板：supplierName的供应商订单号为supplierOrder的订单退款失败，请尽快处理
						SMS.toYunYingRefund("骏图",df.format(new Date()),supplierOrder,"拒绝退款","18991199390");
						SMS.toYunYingRefund("骏图",df.format(new Date()),supplierOrder,"拒绝退款","15202468686");
					}
					userService.updateRefund(partnerOrderId,status_no,requestTime,responseTime);//将骏图返回的退款结果存入数据库	2018-02-08	yangli
					JSONObject js_res=new JSONObject();
					js_res.put("status","10000");
					resp.getOutputStream().print(js_res.toString());
					log.info("骏图退款通并存入数据库");
				}else{
					JSONObject js_res=new JSONObject();
					js_res.put("status","10001");
					resp.getOutputStream().print(js_res.toString());
					
					log.info("退款通知接受失败");
				}
			}
			log.info("=====================================退款通知结束=====================================");
	}
}
