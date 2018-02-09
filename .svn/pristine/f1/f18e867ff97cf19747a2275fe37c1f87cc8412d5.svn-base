package cn.nvinfo.juntu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

public class TestServlet extends HttpServlet {

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
			List list =new ArrayList();
		/*	//测试创建订单
		 * Map<String, Object> create=CreateOrder.jtCreateOrder("15648465213215468", 2776,Integer.parseInt(String.valueOf(200*100)), Integer.parseInt(String.valueOf(200*100)), 
					1, "2018-02-10","姓名", "15355856698", list);*/
			Map<String, Object> pay = PayOrder.jtPayOrder("12154615615", "5165415615615");
	}
}
