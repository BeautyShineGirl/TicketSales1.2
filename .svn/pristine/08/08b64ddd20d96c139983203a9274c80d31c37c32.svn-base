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
 * 骏图.骏景宝    创建订单
 * * @author yangli	2018-02-01
 *
 */
public class CreateOrder {
	private static Logger log=Logger.getLogger(CreateOrder.class);
	public static Map<String, Object> jtCreateOrder(String partnerOrderId, Integer productId,Integer buyPrice,
			Integer totalPrice, Integer quantity, String travelDate, String buyer,String mobile,List visitors) {
		String url = "http://p-api.eticket.juntu.com/ticket/createOrder";
		
		Map<String,String> map=new HashMap<String, String>();
		map.put("version", "");//1版本号
		map.put("partnerId", JuntuConfig.partnerId);//2合作商ID
		String timestamp=String.valueOf(System.currentTimeMillis());
		map.put("timestamp", timestamp);//3请求发起的时间戳
		//处理data数据
		/*
		 *partnerOrderId	string	required	合作商订单号
		 *productId	integer	required	产品ID
		 *buyPrice	integer	required	门票结算单价，单位：分
		 *totalPrice	integer	required	门票总金额，单位：分
		 *quantity	integer	required	购买数量
		 *travelDate	string	required	出行日期 格式要求：yyyy-MM-dd
		 *buyer	string	required	联系人姓名
		 *mobile	string	required	联系人电话
		 *visitors	array[visitor]	optional	游玩人列表，不需要填写的时候可以为空
		 *visiters出行人需要的字段：
		 * 		name	string	optional	中文姓名
		 * 		pinyin	string	optional	姓名拼音
		 *		mobile	string	optional	游客手机号
		 *		idType	integer	optional	证件类型，参见证件类型对应表
		 *		idNo	string	optional	证件号码
		 * 
		 */
		JSONObject js=new JSONObject();
		js.put("partnerOrderId",partnerOrderId);
		js.put("productId",productId);
		js.put("buyPrice",buyPrice);
		js.put("totalPrice",totalPrice);
		js.put("quantity",quantity);
		js.put("travelDate",travelDate);
		js.put("buyer",buyer);
		js.put("mobile",mobile);
		js.put("visitors",visitors);
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
			log.info("请求骏图创建订单接口返回的参数：status="+status+", message="+message+", resdata="+resdata);
			if(status==10000){
				com.alibaba.fastjson.JSONObject jsonData1=com.alibaba.fastjson.JSONObject.parseObject(resdata);
				String supplierOrder=jsonData1.getString("orderId");//骏图的订单编号
				String orderId=jsonData1.getString("partnerOrderId");//易旅通订单号
				mapReturn.put("supplierOrder", supplierOrder);
				mapReturn.put("orderId", orderId);
				mapReturn.put("status", 1);
			}else{
				mapReturn.put("status", 0);
				log.info("骏图返回的参数2：status="+status+", message="+message+", resdata="+resdata);
			}
		}
		return mapReturn;
	
	}
}
