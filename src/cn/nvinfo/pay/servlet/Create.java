
package cn.nvinfo.pay.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.nvinfo.controller.UserAction;
import cn.nvinfo.utils.HttpUtils;
import cn.nvinfo.utils.Log;

public class Create {

	/**
	 * 新浪潮创建订单
	 * 
	 * @param request
	 * @param response
	 */
	public static Map<String, Object> xlcCreateOrder(String travel_date, Integer info_id, String order_source_id,
			Integer num, String link_man, String link_phone, String link_credit_no) {
		String CUSTID = "700803";
		String APIKEY = "8394662C92F35912D5762D115B71350E";
		String URL = "http://b2b.029ly.cn/api/order.jsp";
		/*
		 * String travel_date = (String) request.getAttribute("travel_date");
		 * Integer info_id = (Integer) request.getAttribute("info_id"); String
		 * order_source_id = (String) request.getAttribute("order_source_id");
		 * Integer num = (Integer) request.getAttribute("num"); String link_man
		 * = (String) request.getAttribute("link_man"); String link_phone =
		 * (String) request.getAttribute("link_phone"); String link_credit_no =
		 * (String) request.getAttribute("link_credit_no");
		 */
		/*
		 * Log.write("出游日期：" + travelDate + "产品号:" + infoId + "订单号：" +
		 * orderSourceId + "数量：" + num + "联系人：" + linkMan + "联系方式：" +
		 * linkPhone+"证件号码："+linkCreditNo);
		 */
		 Log.write("出游日期：" + travel_date + "产品号:" + info_id + "订单号：" + order_source_id + "数量：" + num + "联系人：" + link_man
				+ "联系方式：" + link_phone + "证件号码：" + link_credit_no);
		Map<String, String> map = new HashMap<String, String>();
		map.put("custId", CUSTID);
		map.put("apikey", APIKEY);
		map.put("param",
				"<order><travel_date>" + travel_date + "</travel_date><info_id>" + info_id + "</info_id><cust_id>"
						+ CUSTID + "</cust_id><order_source_id>" + order_source_id + "</order_source_id><num>" + num
						+ "</num><link_man>" + link_man + "</link_man><link_phone>" + link_phone
						+ "</link_phone><link_credit_type>0</link_credit_type><link_credit_no>" + link_credit_no
						+ "</link_credit_no></order>");
		String result = HttpUtils.sendGet(URL, map);
		 Log.write("新浪潮下单返回的数据:"+result);
		Document doc;
		Map<String, Object> info = null;
		try {
			doc = DocumentHelper.parseText(result);
			Element root = doc.getRootElement();
			Element status = root.element("status");
			String statusValue = status.getTextTrim();
			if (statusValue.equals("1")) {// 下单成功
				Log.write("下单成功");
				info = new HashMap<String, Object>();
				info.put("status", Integer.parseInt(statusValue));
				info.put("msg", root.element("msg").getTextTrim());
				info.put("order_id", root.element("order_id").getTextTrim());
				info.put("order_money", Double.parseDouble(root.element("order_money").getTextTrim()));
				info.put("order_state", Integer.parseInt(root.element("order_state").getTextTrim()));
			} else {// 下单失败
				Log.write("下单失败");
				info = new HashMap<String, Object>();
				info.put("status", Integer.parseInt(statusValue));
				info.put("msg", root.element("msg").getTextTrim());
				info.put("error_state", root.element("error_state").getTextTrim());
				info.put("error_msg", root.element("error_msg").getTextTrim());
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return info;
	}

}
