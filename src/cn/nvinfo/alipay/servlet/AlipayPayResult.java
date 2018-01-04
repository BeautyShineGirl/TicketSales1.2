package cn.nvinfo.alipay.servlet;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.alipay.api.response.AlipayTradePrecreateResponse;

import cn.nvinfo.domain.Order;
import cn.nvinfo.pay.servlet.Pay;
import cn.nvinfo.service.UserService;
import cn.nvinfo.utils.HttpUtil;
import cn.nvinfo.utils.SMS;
import cn.nvinfo.utils.XmlUtils;
/**
 * 支付结果通知
 * @author 杨立	2017-10-30
 *
 */
public class AlipayPayResult extends HttpServlet {
	private static Logger log=Logger.getLogger(AlipayPayResult.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		log.info("=====================================AlipayPayResult=====================================");
		log.info("===============================================异步通知操作开始===============================================");
		//获取spring容器中的service
		WebApplicationContext  ctx= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		UserService userService = (UserService) ctx.getBean("userService",UserService.class);
		String trade_status = req.getParameter("trade_status");
		String out_trade_no = req.getParameter("out_trade_no");
		String trade_no=req.getParameter("trade_no");
		log.info("支付宝支付结果通知（原始数据）：{"+System.getProperty("line.separator")+"\t\t\t\t\t"+
				"trade_status:"+trade_status+System.getProperty("line.separator")+"\t\t\t\t\t"+
				"out_trade_no:" +out_trade_no+System.getProperty("line.separator")+"\t\t\t\t\t"+
				"trade_no:" +trade_no+System.getProperty("line.separator")+"\t\t\t\t\t"+
				"}");
			if("TRADE_CLOSED".equals(trade_status)){
				//交易关闭
				log.info("接收到支付结果回传给支付宝服务器的数据：success");
				resp.getWriter().print("success");
			}else if("TRADE_FINISHED".equals(trade_no)){
				//交易完结
				log.info("接收到支付结果回传给支付宝服务器的数据：success");
				resp.getWriter().print("success");
			}else if("TRADE_SUCCESS".equals(trade_status)){
				//支付成功
				Order order = new Order();
				order.setOrderState(0);//将订单表中的orderState改为0，证明，订单已支付成功
				order.setTransaction_id(trade_status);//保存订单号
				order.setOrderId(out_trade_no);//商品订单号order表中的id
				userService.updateOrder(order);//
				//先从订单表中把该支付成功的订单查出来	2017-11-07
				Order order2=userService.getOrderSuccess(out_trade_no);
				//得到订单的产品数量，orderNumber ，然后更新产品表中的num		yangli	2017-12-12
				Integer productId=order2.getProductId();
				Integer num2= userService.getById(productId).getNum();
				Integer newNum=order2.getOrderNumber()-num2;
				/*
				 * 判断该产品的供应商是否是第三方，若不是，则直接发送短信到用户手机；
				 * 若是第三方产品，则向第三方下单
				 */
				int supplierId = order2.getSupplierId();
				if(supplierId==1){
					//然后将短信通知所需的字段放入短信通知的方法中发送短信2017-11-07
					String userName = order2.getUserName();
					String useDate = order2.getUseDate();
					String productName = order2.getProductName();
					Integer orderNumber = order2.getOrderNumber();
					String num=orderNumber.toString();
					String orderId = order2.getOrderId();
					String orderCode = order2.getOrderCode();
					String viewName = order2.getViewName();
					String userPhone = order2.getUserPhone();
					SMS.sendSms(userName, useDate, productName,num,orderId, orderCode,viewName,userPhone);
					userService.updateProductNum(newNum,productId);
					log.info("自营产品"+orderId+"支付成功,并发送短信至客户");
				}else{
					//根据供应商id得到供应商的名字	yangli	2017-12-11
					String supplierName=userService.getSupplierName(supplierId);
					SimpleDateFormat df=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
					if(supplierId==2){
						//向新浪潮下单，传入参数，新浪潮的供应商订单号，及供应商编号
						/*req.setAttribute("orderId",order2.getSupplierOrder());//联系人证件类型
						req.setAttribute("supplierId",supplierId);//联系人证件号码
						req.getRequestDispatcher("/anotherPayServlet").forward(req, resp);*/
						Map<String, Object> pay = Pay.xlcPayOrder(order2.getSupplierOrder());
						Integer status = (Integer)pay.get("status");
						String code = (String)pay.get("code");
						log.info("请求支付时返回：status="+status+", code="+code+", msg="+(String)pay.get("msg")+", error_state="+(String)pay.get("error_state"));
						if(status==0){
							//把订单状态改成5 为用户下单支付成功，向第三方下单失败，原因是第三方返回的msg	2017-12-08	yangli
							Order order1 = new Order();
							order1.setSupplierOrderState(5);//将订单表中的orderState改为0，证明，订单已支付成功
							order1.setOrderId(order2.getOrderId());//商品订单号order表中的id
							order1.setReason((String)pay.get("msg")+(String)pay.get("error_state"));//支付失败的原因
							userService.updateSupplierOrder(order1);//
							userService.editState(order2.getOrderId());
							//将供应商订单号告诉给运营的人，这个订单支付失败
							//模板：supplierName的供应商订单号为supplierOrder的订单支付失败，请尽快处理
							SMS.toYunYing(supplierName,df.format(new Date()),order2.getSupplierOrder(),(String)pay.get("msg"),"18991199390");
							SMS.toYunYing(supplierName,df.format(new Date()),order2.getSupplierOrder(),(String)pay.get("msg"),"15202468686");
							log.info("新浪潮下单失败："+df.format(new Date())+order2.getSupplierOrder()+"支付失败，原因是："+(String)pay.get("msg"));
						}else{
							//将第三方给客户返回的短信验证码保存到orderCode中	yangli 2017-12-11
							Order order3 = new Order();
							order3.setId(order2.getId());
							order3.setSupplierOrderState(2);//向新浪潮下单成功
							order3.setOrderCode(code);
							userService.insertCode(order3);
							userService.updateProductNum(newNum,productId);
							log.info(supplierName+df.format(new Date())+order2.getSupplierOrder()+"支付成功，code="+code);
						}
					}
				}
				log.info("接收到支付结果回传给支付宝服务器的数据：success");
				resp.getWriter().print("success");
			}else if("WAIT_BUYER_PAY".equals(trade_status)){
				//交易创建
				log.info("接收到支付结果回传给支付宝服务器的数据：success");
				resp.getWriter().print("success");
			}else{
				log.info("接收到支付结果回传给支付宝服务器的数据：fail");
				resp.getWriter().print("fail");
			}
		log.info("===============================================异步通知操作结束===============================================");
	}
}
