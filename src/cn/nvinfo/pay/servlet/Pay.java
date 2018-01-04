
package cn.nvinfo.pay.servlet;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.nvinfo.utils.HttpUtils;
import cn.nvinfo.utils.Log;

public class Pay {
	public static Map<String, Object> xlcPayOrder(String orderId) {
		String CUSTID = "700803";
		String APIKEY = "8394662C92F35912D5762D115B71350E";
		String URL = "http://b2b.029ly.cn/api/pay.jsp";
		Map<String, String> map = new HashMap<String, String>();
		map.put("custId", CUSTID);
		map.put("apikey", APIKEY);
		map.put("orderId", orderId);
		String result = HttpUtils.sendGet(URL, map);
		Log.write("新浪潮支付订单返回的数据:" + result);
		Map<String, Object> info = null;
		try {
			Document doc = DocumentHelper.parseText(result);
			Element root = doc.getRootElement();
			Element status = root.element("status");
			String statusValue = status.getTextTrim();
			if (statusValue.equals("1")) {
				info = new HashMap<String, Object>();
				info.put("status", Integer.parseInt(statusValue));
				info.put("msg", root.element("msg").getTextTrim());
				info.put("orderId", root.element("orderId").getTextTrim());
				info.put("code", root.element("code").getTextTrim());

			} else {
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
