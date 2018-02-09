package cn.nvinfo.juntu.servlet;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.nvinfo.tools.MD5;
import cn.nvinfo.utils.DecodeEncode;
import cn.nvinfo.utils.EnDecrypt;
import cn.nvinfo.utils.HttpUtils;
import cn.nvinfo.utils.JuntuConfig;

/**
 * 骏图.骏景宝   查询订单接口
 * * @author yangli	2018-02-08
 *
 */
public class QueryOrder {
	private static Logger log=Logger.getLogger(QueryOrder.class);
	
	public static Map<String, Object> jtQueryOrder(String partnerOrderId,String supplierId) {
		String url = "http://p-api.eticket.juntu.com/ticket/queryOrder";
		
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
		js.put("orderId",supplierId);
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
			log.info("请求骏图查询接口返回的参数（主要得知总数据）：status="+status+", message="+message+", resdata="+resdata);
			if(status==10000){
				com.alibaba.fastjson.JSONObject jsonData1=com.alibaba.fastjson.JSONObject.parseObject(resdata);
				String orderId=jsonData1.getString("orderId");//支付结果
				String partnerOrderId_res=jsonData1.getString("partnerOrderId");
				String quantity=jsonData1.getString("quantity");
				String refundQuantity=jsonData1.getString("refundQuantity");
				String payStatus=jsonData1.getString("payStatus");//支付状态，1未支付，2已支付
				String refundStatus=jsonData1.getString("refundStatus");//退款状态，1未退款，2退款中，3已部分退款，4 已退款
				String ticketingType=jsonData1.getString("ticketingType");//出票类型，0，未出票，1出票成功，2出票失败，3出票待定     该字段为预留字段，此结果并不代表最终出票结果，出票结果需与客服核实，如因此字段造成的损失由合作商自行承担
				String voucherType=jsonData1.getString("voucherType");
				/*
				 * voucher	string	optional	凭证码
					ticketName	string	optional	门票名称
					quantity	string	optional	与status配合使用，此状态下的凭证代表的数量
					status	integer	optional	凭证状态;0为未使用，1为已使用，3为已作废
				 */
				String voucherList=jsonData1.getString("voucherList");//凭证码类型；0为不回传凭证或无法回传凭证时。1 返回凭证时
				mapReturn.put("orderId", orderId);
				mapReturn.put("partnerOrderId_res", partnerOrderId_res);
				mapReturn.put("quantity", quantity);
				mapReturn.put("refundQuantity", refundQuantity);
				mapReturn.put("payStatus", payStatus);
				mapReturn.put("refundStatus", refundStatus);
				mapReturn.put("ticketingType", ticketingType);
				mapReturn.put("voucherType", voucherType);
			}else{
				log.info("第三方返回的参数2：status="+status+", message="+message+", resdata="+resdata);
			}
		}
		return mapReturn;
	
	}
}
