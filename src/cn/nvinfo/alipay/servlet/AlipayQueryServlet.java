package cn.nvinfo.alipay.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

import cn.nvinfo.domain.Order;
import cn.nvinfo.pay.servlet.Pay;
import cn.nvinfo.service.UserService;
import cn.nvinfo.utils.AlipayConfig;
import cn.nvinfo.utils.CheckUtil;
import cn.nvinfo.utils.SMS;
/**
 * 查询支付订单
 * @author 杨立	2017-10-30
 *
 */
public class AlipayQueryServlet extends HttpServlet {
	private static Logger log=Logger.getLogger(AlipayQueryServlet.class);
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String out_trade_no=req.getParameter("out_trade_no");//订单号
		String id=out_trade_no;//获得订单的id 用于在本地数据库查询定的的状态
		log.info("=====================================AlipayQueryServlet=====================================");
		log.info("===============================================支付结果查询操作开始===============================================");
		log.info("前台传过来的数据：out_trade_no="+out_trade_no+", 订单在数据库中的id="+id);
		//验证参数
		if(!"".equals(CheckUtil.checkArgsNotNull(id,out_trade_no))){
			
			log.info("alipayQueryServlet返回给前端的参数："+"{\"msg\":\"参数错误\",\"result\":[{}],\"code\":\"\"}");
			resp.getWriter().write("{\"msg\":\"参数错误\",\"result\":[{}],\"code\":\"\"}");
		}
		//在本地数据库中查询，若查询状态不是orderState为0，请求queryServlet查询订单状态
		//获取spring容器中的service
		WebApplicationContext  ctx= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		UserService userService = (UserService) ctx.getBean("userService",UserService.class);
		int state=userService.selectOrderState(id);
		if(state!=0){
			log.info("公共参数："+"https://openapi.alipay.com/gateway.do"+
					", app_id="+AlipayConfig.app_id+System.getProperty("line.separator")+"\t\t\t\t"
					+", private_key="+AlipayConfig.private_key+"json"+"UTF-8"+System.getProperty("line.separator")+"\t\t\t\t"
					+", alipay_public_key="+AlipayConfig.alipay_public_key+"RSA2");
			//公共参数
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
					AlipayConfig.app_id,AlipayConfig.private_key,"json","UTF-8",AlipayConfig.alipay_public_key,"RSA2");
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			AlipayTradeQueryModel model = new AlipayTradeQueryModel();
			request.setBizModel(model);
			model.setOutTradeNo(out_trade_no);//订单号
			log.info("alipayQueryServlet提交给支付宝服务器的参数：out_trade_no="+out_trade_no);
			AlipayTradeQueryResponse response;
			try {
				response = alipayClient.execute(request);
				if(response.isSuccess()){
					log.info("请求alipayQueryServlet支付查询接口成功时支付宝服务器返回的数据："+System.getProperty("line.separator")+"\t\t\t\t"+
							"alipay_trade_query_response{"+System.getProperty("line.separator")+"\t\t\t\t\t"+
					        "code:"+response.getCode()+System.getProperty("line.separator")+"\t\t\t\t\t"+
					        "msg:" +response.getMsg()+System.getProperty("line.separator")+"\t\t\t\t\t"+
					        "out_trade_no:" +response.getOutTradeNo()+System.getProperty("line.separator")+"\t\t\t\t\t"+
					        "trade_status:"+response.getParams().get("trade_status")+System.getProperty("line.separator")+"\t\t\t\t"+
							" }");
					/*
					 * 调用成功
					 */
					//交易创建，等待买家付款
					if("WAIT_BUYER_PAY".equals(response.getTradeStatus())){
						try {
							Thread.sleep(1000);
							doPost(req,resp);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//未付款交易超时关闭，或支付完成后全额退款
					if("TRADE_CLOSED".equals(response.getTradeStatus())){
						//把订单状态改成4 为无效订单
						Order order = new Order();
						order.setOrderState(4);//将订单表中的orderState改为0，证明，订单已支付成功
						order.setTransaction_id("无");//保存微信订单号
						order.setOrderId(response.getOutTradeNo());//商品订单号order表中的id
						order.setReason("未付款交易超时关闭，或支付完成后全额退款");
						userService.updateOrder(order);//
						JSONObject obj=new JSONObject();
						obj.put("result", "success");
						obj.put("code", "1");
						obj.put("message", "未付款交易超时关闭，或支付完成后全额退款");
						log.info("返回前端的数据："+obj.toString());
						resp.getOutputStream().print(obj.toString());
						/*log.info("alipayQueryServlet成功发送给前台的数据："+"{\"msg\":\"未付款交易超时关闭，或支付完成后全额退款\",\"result\":[{}],\"code\":\"\"}");
						resp.getWriter().write("{\"msg\":\"未付款交易超时关闭，或支付完成后全额退款\",\"result\":[{}],\"code\":\"\"}");*/
					}
					//交易支付成功
					if("TRADE_SUCCESS".equals(response.getTradeStatus())){
						//先从订单表中把该支付成功的订单查出来	2017-11-07
						Order order2=userService.getOrderSuccess(out_trade_no);
						//修改状态为0 字符成功
						userService.editState(out_trade_no);
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
						JSONObject obj=new JSONObject();
						obj.put("result", "success");
						obj.put("code", "1");
						obj.put("message", "支付成功");
						log.info("返回前端的数据："+obj.toString());
						resp.getOutputStream().print(obj.toString());
						/*log.info("alipayQueryServlet成功发送给前台的数据："+"{\"msg\":\"订单已支付\",\"result\":[{}],\"code\":\"\"}");
						resp.getWriter().write("{\"msg\":\"订单已支付\",\"result\":[{}],\"code\":\"\"}");*/
					}
					//交易结束，不可退款
					if("TRADE_FINISHED".equals(response.getTradeStatus())){
						JSONObject obj=new JSONObject();
						obj.put("result", "success");
						obj.put("code", "1");
						obj.put("message", "交易结束，不可退款");
						log.info("返回前端的数据："+obj.toString());
						resp.getOutputStream().print(obj.toString());
						/*log.info("alipayQueryServlet发送成功给前台的数据："+"{\"msg\":\"交易结束，不可退款\",\"result\":[{}],\"code\":\"\"}");
						resp.getWriter().write("{\"msg\":\"交易结束，不可退款\",\"result\":[{}],\"code\":\"\"}");*/
					}
				} else {
					/*
					 * 调用失败
					 */
					//网关错误
					if(!response.getCode().equals("10000")){
						JSONObject obj=new JSONObject();
						obj.put("result", false);
						obj.put("code", "0");
						if("ACQ.INVALID_PARAMETER".equals(response.getSubCode())){
							obj.put("message", "系统错误");
						}
						if("ACQ.SYSTEM_ERROR".equals(response.getSubCode())){
							obj.put("message", "系统错误");
						}
						if("ACQ.TRADE_NOT_EXIST".equals(response.getSubCode())){
							obj.put("message", "未支付");
						}
						obj.put("sub_code", response.getCode());
						obj.put("sub_msg", response.getSubCode());
						log.info("返回前端的数据1："+obj.toString());
						resp.getOutputStream().print(obj.toString());
						log.info("{\"msg\":\"返回前端的数据1：网关错误\",\"result\":[{}],\"code\":\""+response.getCode()+"\"}");
						/*log.info("alipayQueryServlet发送失败给前台的数据："+"{\"msg\":\"(网关错误)系统错误\",\"result\":[{}],\"code\":\"\"}");
						resp.getWriter().write("{\"msg\":\"系统错误，请重新下单\",\"result\":[{}],\"code\":\"\"}");*/
					}else{
						
						// 	系统错误
						if("ACQ.SYSTEM_ERROR".equals(response.getSubCode())){
							for (int i = 0; i < 1; i++) {
								doPost(req,resp);
							}
							JSONObject obj=new JSONObject();
							obj.put("result", false);
							obj.put("code", "0");
							obj.put("message", "系统错误");
							log.info("返回前端的数据："+obj.toString());
							resp.getOutputStream().print(obj.toString());
							/*log.info("alipayQueryServlet发送失败给前台的数据："+"{\"msg\":\"系统错误，订单已撤销 \",\"result\":[{}],\"code\":\"\"}");
						resp.getWriter().write("{\"msg\":\"系统错误 \",\"result\":[{}],\"code\":\"\"}");*/
							/*req.setAttribute("out_trade_no", out_trade_no);
						req.getRequestDispatcher("/AlipayCancalOrder").forward(req, resp);*/
						}
						// 	参数无效
						if("ACQ.INVALID_PARAMETER".equals(response.getSubCode())){
							JSONObject obj=new JSONObject();
							obj.put("result", false);
							obj.put("code", "0");
							obj.put("message", "检查请求参数，修改后重新发起请求");
							log.info("返回前端的数据："+obj.toString());
							resp.getOutputStream().print(obj.toString());
							/*log.info("alipayQueryServlet发送失败给前台的数据："+"{\"msg\":\"检查请求参数，修改后重新发起请求 ，订单已撤销 \",\"result\":[{}],\"code\":\"\"}");
						resp.getWriter().write("{\"msg\":\"检查请求参数，修改后重新发起请求 \",\"result\":[{}],\"code\":\"\"}");*/
						}
						//查询的交易不存在
						if("ACQ.TRADE_NOT_EXIST".equals(response.getSubCode())){
							JSONObject obj=new JSONObject();
							obj.put("result", false);
							obj.put("code", "0");
							obj.put("message", "检查请求参数，修改后重新发起请求");
							log.info("返回前端的数据："+obj.toString());
							resp.getOutputStream().print(obj.toString());
							/*log.info("alipayQueryServlet发送失败给前台的数据："+"{\"msg\":\"查询的交易不存在\",\"result\":[{}],\"code\":\"\"}");
						resp.getWriter().write("{\"msg\":\"查询的交易不存在\",\"result\":[{}],\"code\":\"\"}");*/
						}
					}
				}
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//本地查询已支付
			JSONObject obj=new JSONObject();
			obj.put("result", true);
			obj.put("code", "1");
			obj.put("message", "支付成功");
			log.info("返回前端的数据："+obj.toString());
			resp.getOutputStream().print(obj.toString());
			/*log.info("{\"msg\":\"订单已支付\",\"result\":[{}],\"code\":\"\"}");
			resp.getWriter().write("{\"msg\":\"订单已支付\",\"result\":[{}],\"code\":\"\"}");*/
		}
		log.info("===============================================支付结果查询操作结束===============================================");
	}
}
