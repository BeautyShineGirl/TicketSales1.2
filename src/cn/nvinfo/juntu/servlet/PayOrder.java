package cn.nvinfo.juntu.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.nvinfo.tools.MD5;
import cn.nvinfo.utils.DecodeEncode;
import cn.nvinfo.utils.EnDecrypt;
import cn.nvinfo.utils.HttpUtils;
import cn.nvinfo.utils.JuntuConfig;

/**
 * 骏图.骏景宝    支付接口
 * * @author yangli	2018-02-02
 *
 */
public class PayOrder {
	private static Logger log=Logger.getLogger(PayOrder.class);
	
	public static Map<String, Object> jtPayOrder(String partnerOrderId,String supplierOrder) {
		String url = "http://p-api.eticket.juntu.com/ticket/payOrder";
		
		Map<String,String> map=new HashMap<String, String>();
		map.put("version", "");//1版本号
		map.put("partnerId", JuntuConfig.partnerId);//2合作商ID
		String timestamp=String.valueOf(System.currentTimeMillis());
		map.put("timestamp", timestamp);//3请求发起的时间戳
		//处理data数据
		/*
		 *partnerOrderId	string	required	合作商订单号
		 *orderId	string	required	骏景宝订单号
		 * 
		 */
		JSONObject js=new JSONObject();
		js.put("partnerOrderId",partnerOrderId);
		js.put("orderId",supplierOrder);
		log.info("未加密的json："+js);
		byte[] encrypt = EnDecrypt.encrypt(js.toString().getBytes(), JuntuConfig.partnerKey.getBytes());//AES128加密
		String data = DecodeEncode.encode(encrypt);//对AES加密数据进行一次base64编码
		map.put("data", data);//4业务接口请求数据
		String sign = MD5.MD5Encode(JuntuConfig.partnerId+timestamp+JuntuConfig.partnerKey+data);
		map.put("sign",sign);//5请求数据的签名
		String sendPost = HttpUtils.sendPost(url, map);
		log.info("请求发送的数据：url="+url+", map="+map);
		log.info("请求getPoi返回：sendPost="+sendPost);
		Map<String,Object> mapReturn=new HashMap<String, Object>();
		if(sendPost!=null||"".equals(sendPost)){
			com.alibaba.fastjson.JSONObject json=com.alibaba.fastjson.JSONObject.parseObject(sendPost);
			Integer status =0;
			String message=null;
			message=json.getString("message");
			String resdata=null;
			resdata=json.getString("data");
			status=Integer.parseInt(json.getString("status"));
			log.info("请求骏图支付接口返回的参数（主要得知总数据）：status="+status+", message="+message+", resdata="+resdata);
			if(status==10000){
				mapReturn.put("status", 1);
			}else{
				mapReturn.put("status", 0);
				log.info("第三方返回的参数2：status="+status+", message="+message+", resdata="+resdata);
			}
		}
		return mapReturn;
	
	}
}
