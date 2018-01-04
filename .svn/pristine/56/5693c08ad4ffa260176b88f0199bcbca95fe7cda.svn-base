package cn.nvinfo.wxpay.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.nvinfo.domain.Order;
import cn.nvinfo.pay.servlet.Cancel;
import cn.nvinfo.service.UserService;
import cn.nvinfo.utils.HttpUtil;
import cn.nvinfo.utils.SMS;
import cn.nvinfo.utils.SwiftpassConfig;
import cn.nvinfo.utils.XmlUtils;
/**
 * 退款结果通知
 * @author 杨立	2017-10-24
 *
 */
public class RefundResult extends HttpServlet {
	private static Logger log=Logger.getLogger(RefundServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			//resp.setHeader("Content-type", "text/html;charset=UTF-8");
			
			log.info("=====================================RefundResult=====================================");
			log.info("=====================================退款通知开始=====================================");
			//获取spring容器中的service
			WebApplicationContext  ctx= WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			UserService userService = (UserService) ctx.getBean("userService",UserService.class);
			try {
				String receivePost = HttpUtil.receivePost(req);
				log.info("微信退款结果通知（原始数据）："+receivePost);
				if(receivePost != null){
					Map<String,String> resultMap = XmlUtils.toMap(receivePost.getBytes(), "utf-8");
					log.info("微信退款结果通知（map）："+resultMap);
					String return_code = resultMap.get("return_code");
					if("SUCCESS".equals(return_code)){
						userService.updateOrderState(resultMap.get("out_trade_no"));//把订单表中的orderState的状态更新为3
						//当退款成功时，需要把产品表里面的num加上orderNumber
						Order order2=userService.getOrderSuccess(resultMap.get("out_trade_no"));
						//得到订单的产品数量，orderNumber ，然后更新产品表中的num		yangli	2017-12-12
						Integer productId=order2.getProductId();
						Integer num2= userService.getById(productId).getNum();
						Integer newNum=order2.getOrderNumber()+num2;
						Integer supplierId=order2.getSupplierId();
						//根据供应商id得到供应商的名字	yangli	2017-12-11
						String supplierName=userService.getSupplierName(supplierId);
						SimpleDateFormat df=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
						if(supplierId==1){
							userService.updateProductNum(newNum,productId);
						}
						if(supplierId==2){
							//向新浪潮退单
							//向新浪潮下单，传入参数，新浪潮的供应商订单号，及供应商编号
						/*	req.setAttribute("orderId",order2.getSupplierOrder());//联系人证件类型
							req.setAttribute("supplierId",supplierId);//联系人证件号码
							req.getRequestDispatcher("/anotherRefundServlet").forward(req, resp);*/
							Map<String, Object> cancel=Cancel.xlcCancelOrder(order2.getSupplierOrder());
							Integer status = (Integer)cancel.get("status");
							log.info("请求退款时返回：status="+status+", msg="+(String)cancel.get("msg")+", error_state="+(String)cancel.get("error_state"));
							if(status==0){
								if("1005".equals((String)cancel.get("error_state"))){
									log.info(df.format(new Date())+"supplierName订单号为"+order2.getSupplierOrder()+"进入退改申请");
								}
								if("1006".equals((String)cancel.get("error_state"))){
									//退款失败，更新SupplierOrderState为0，把产品数量加1	yangli 2017-12-11
									Order order3 = new Order();
									order3.setProductId(order2.getProductId());
									order3.setSupplierOrderState(0);//退款成功
									userService.updateSuplierState(order3);
									//将供应商订单号告诉给运营的人，这个订单退款失败
									//模板：supplierName的供应商订单号为supplierOrder的订单退款失败，请尽快处理
									SMS.toYunYingRefund(supplierName,df.format(new Date()),order2.getSupplierOrder(),(String)cancel.get("msg"),"18991199390");
									SMS.toYunYingRefund(supplierName,df.format(new Date()),order2.getSupplierOrder(),(String)cancel.get("msg"),"15202468686");
									log.info("新浪潮退款失败："+df.format(new Date())+order2.getSupplierOrder()+"退款失败，原因是："+(String)cancel.get("msg"));
								}
								
								
							}else{
								//退款成功，更新SupplierOrderState为3，把产品数量加1	yangli 2017-12-11
								Order order3 = new Order();
								order3.setProductId(order2.getProductId());
								order3.setSupplierOrderState(3);//退款成功
								userService.updateSuplierState(order3);
								userService.updateProductNum(newNum,productId);
								log.info(supplierName+df.format(new Date())+order2.getSupplierOrder()+"退款成功");
							}
						}
						
						log.info("退款通知回传给微信服务期的数据:"+"<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
						resp.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
					}else{
						log.info("退款通知回传给微信服务期的数据:"+"<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>");
						resp.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[FAIL]]></return_msg></xml>");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("=====================================退款通知结束=====================================");
	}
}
