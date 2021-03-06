package cn.nvinfo.juntu.servlet;

import java.io.IOException;

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

public class TicketChange extends HttpServlet {
	private static Logger log=Logger.getLogger(TicketChange.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			
			log.info("=====================================TicketChange=====================================");
			log.info("=====================================门票信息变更通知开始=====================================");
			
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
					 * productId	integer	required	产品ID
					 * status	integer	required	产品状态 0 产品下架 ，1 产品上架 ，2 产品信息变化 ，4 产品的价格日历变化
					 */
					com.alibaba.fastjson.JSONObject jsonData1=com.alibaba.fastjson.JSONObject.parseObject(resdata);
					Integer status_no=(Integer)jsonData1.get("status");//审核结果状态 1 同意退款 2 拒绝退款
					Integer juntuPId=(Integer)jsonData1.get("productId");
					if(status_no==0||status_no==1||status_no==2){
						//重新触发获得产品信息的接口
						req.getRequestDispatcher("/getProductServlet").forward(req, resp);
					}else{
						//触发获得价格日历的接口
						req.getRequestDispatcher("/getStockServlet").forward(req, resp);
					}
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
			log.info("=====================================门票信息变更通知结束=====================================");
	}

}
