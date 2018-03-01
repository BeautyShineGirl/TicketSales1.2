package cn.nvinfo.utils;


import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
/**
 * 发送验证码	
 * @author 杨立	2017-11-07
 *
 */
public class SMS {
	public static void main(String[] args) {
		//sendSms("杨立 ","2017-11-07","lol世界总决赛门票","1","1245678963","123456523"," 北京","18789455937");
		findPassWord("123456","18789455937");
	}
	private static Logger log=Logger.getLogger(SMS.class);
	public static void sendSms(String name,String date,String ticketname,String quantity,
			String orderid,String ticketcode,String scenic,String telephone) throws ClientException {
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 初始化ascClient需要的几个参数
		final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
		// 替换成你的AK
		try {
			final String accessKeyId = "LTAIXQzwKxw2xRnB";// 你的accessKeyId,参考本文档步骤2
			final String accessKeySecret = "kverHP6HY1V3gKEEQECwydFgynXNOT";// 你的accessKeySecret，参考本文档步骤2
			// 初始化ascClient,暂时不支持多region（请勿修改）
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			request.setPhoneNumbers(telephone);// 发送短信手机号
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName("E旅通");
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode("SMS_85790043");// SMS_120411558;
			// SMS_120411557
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败

			JSONObject js = new JSONObject();
			js.put("name", name);//用户姓名
			js.put("date", date);//出发日期
			js.put("ticketname", ticketname);//产品名称 如冰雪大世界成人票
			js.put("quantity",quantity);//票数
			js.put("orderid", orderid);//订单号
			js.put("ticketcode", ticketcode);//入园凭证码
			js.put("scenic", scenic);//景区名称
			request.setTemplateParam(js.toString());
			// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
			// request.setSmsUpExtendCode("90997");
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");
			// 请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			String codee = sendSmsResponse.getCode();
			log.info("发送短信返回的code：" + codee);
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				System.out.println("发送成功");
				log.info("发送成功");
			}else{
				log.info("发送失败 :"+sendSmsResponse.getMessage());
				System.out.println("发送失败 :"+sendSmsResponse.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void toYunYing(String name,String date,String orderid,String message,String telephone) {
		/**
		 * Step 1. 获取主题引用
		 */
		CloudAccount account = new CloudAccount("LTAIXQzwKxw2xRnB", "kverHP6HY1V3gKEEQECwydFgynXNOT",
				"http://1086158643880738.mns.cn-hangzhou.aliyuncs.com/");
		MNSClient client = account.getMNSClient();
		CloudTopic topic = client.getTopicRef("sms.topic-cn-hangzhou");
		/**
		 * Step 2. 设置SMS消息体（必须）
		 *
		 * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
		 */
		RawTopicMessage msg = new RawTopicMessage();
		msg.setMessageBody("sms-message");
		/**
		 * Step 3. 生成SMS消息属性
		 */
		MessageAttributes messageAttributes = new MessageAttributes();
		BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
		// 3.1 设置发送短信的签名（SMSSignName）
		batchSmsAttributes.setFreeSignName("E旅通");
		// 3.2 设置发送短信使用的模板（SMSTempateCode）
		batchSmsAttributes.setTemplateCode("SMS_85790043");
		// 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
		BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
		smsReceiverParams.setParam("name", name);//供应商名字
		smsReceiverParams.setParam("date", date);//通知日期
		smsReceiverParams.setParam("orderid", orderid);//订单号
		smsReceiverParams.setParam("msg", message);//订单支付失败的原因
		// 3.4 增加接收短信的号码
		batchSmsAttributes.addSmsReceiver(telephone, smsReceiverParams);
		messageAttributes.setBatchSmsAttributes(batchSmsAttributes);

		/**
		 * Step 4. 发布SMS消息
		 * 
		 */
		try {
			TopicMessage ret = topic.publishMessage(msg, messageAttributes);
			System.out.println("MessageId: " + ret.getMessageId());
			System.out.println("MessageMD5: " + ret.getMessageBodyMD5());
			log.info("MessageId: " + ret.getMessageId());
			log.info("MessageMD5: " + ret.getMessageBodyMD5());
		} catch (ServiceException se) {
			log.info(se.getErrorCode() + se.getRequestId());	
			log.info(se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		client.close();

	}

	public static void toYunYingRefund(String name,String date,String orderid,String message,String telephone) {
		/**
		 * Step 1. 获取主题引用
		 */
		CloudAccount account = new CloudAccount("LTAIXQzwKxw2xRnB", "kverHP6HY1V3gKEEQECwydFgynXNOT",
				"http://1086158643880738.mns.cn-hangzhou.aliyuncs.com/");
		MNSClient client = account.getMNSClient();
		CloudTopic topic = client.getTopicRef("sms.topic-cn-hangzhou");
		/**
		 * Step 2. 设置SMS消息体（必须）
		 *
		 * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
		 */
		RawTopicMessage msg = new RawTopicMessage();
		msg.setMessageBody("sms-message");
		/**
		 * Step 3. 生成SMS消息属性
		 */
		MessageAttributes messageAttributes = new MessageAttributes();
		BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
		// 3.1 设置发送短信的签名（SMSSignName）
		batchSmsAttributes.setFreeSignName("E旅通");
		// 3.2 设置发送短信使用的模板（SMSTempateCode）
		batchSmsAttributes.setTemplateCode("SMS_85790043");
		// 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
		BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
		smsReceiverParams.setParam("name", name);//供应商名字
		smsReceiverParams.setParam("date", date);//通知日期
		smsReceiverParams.setParam("orderid", orderid);//订单号
		smsReceiverParams.setParam("msg", message);//退款失败的原因
		// 3.4 增加接收短信的号码
		batchSmsAttributes.addSmsReceiver(telephone, smsReceiverParams);
		messageAttributes.setBatchSmsAttributes(batchSmsAttributes);

		/**
		 * Step 4. 发布SMS消息
		 * 
		 */
		try {
			TopicMessage ret = topic.publishMessage(msg, messageAttributes);
			//System.out.println("MessageId: " + ret.getMessageId());
			//System.out.println("MessageMD5: " + ret.getMessageBodyMD5());
			log.info("MessageId: " + ret.getMessageId());
			log.info("MessageMD5: " + ret.getMessageBodyMD5());
		} catch (ServiceException se) {
			log.info(se.getErrorCode() + se.getRequestId());	
			log.info(se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		client.close();

	}

	public static void findPassWord(String code,String telephone) {
		
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 初始化ascClient需要的几个参数
		final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
		// 替换成你的AK
		try {
			final String accessKeyId = "LTAIXQzwKxw2xRnB";// 你的accessKeyId,参考本文档步骤2
			final String accessKeySecret = "kverHP6HY1V3gKEEQECwydFgynXNOT";// 你的accessKeySecret，参考本文档步骤2
			// 初始化ascClient,暂时不支持多region（请勿修改）
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			request.setPhoneNumbers(telephone);// 发送短信手机号
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName("E旅通");
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode("SMS_123667359");// SMS_120411558;
			// SMS_120411557
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败

			JSONObject js = new JSONObject();
			js.put("code", code);//验证码
			
			request.setTemplateParam(js.toString());
			// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
			// request.setSmsUpExtendCode("90997");
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");
			// 请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			String codee = sendSmsResponse.getCode();
			log.info("发送短信返回的code：" + codee);
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				System.out.println("发送成功");
				log.info("发送成功");
			}else{
				log.info("发送失败 :"+sendSmsResponse.getMessage());
				System.out.println("发送失败 :"+sendSmsResponse.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
