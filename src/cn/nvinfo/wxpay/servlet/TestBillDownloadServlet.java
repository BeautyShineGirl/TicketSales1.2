package cn.nvinfo.wxpay.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.nvinfo.utils.MD5;
import cn.nvinfo.utils.SignUtils;
import cn.nvinfo.utils.SwiftpassConfig;
import cn.nvinfo.utils.XmlUtils;

public class TestBillDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @SuppressWarnings("unchecked")
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        SortedMap<String,String> map = XmlUtils.getParameterMap(req);

        map.put("mch_id", SwiftpassConfig.mch_id);
        map.put("nonce_str", String.valueOf(new Date().getTime()));
        Map<String,String> params = SignUtils.paraFilter(map);
        StringBuilder buf = new StringBuilder((params.size() +1) * 10);
        SignUtils.buildPayParams(buf,params,false);
        String preStr = buf.toString();
        String sign = MD5.sign(preStr, "&key=" + SwiftpassConfig.key, "utf-8");
        map.put("sign", sign);

        String reqUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";


        System.out.println("reqParams:" + XmlUtils.parseXML(map));
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        String res = null;
        try {
        	HttpPost httpPost = new HttpPost(reqUrl);
        	StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map),"utf-8");
        	httpPost.setEntity(entityParams);
        	httpPost.setHeader("Content-Type", "text/xml;charset=utf-8");
        	client = HttpClients.createDefault();
        	response = client.execute(httpPost);
        	if(response != null && response.getEntity() != null){
        		res = new String(EntityUtils.toByteArray(response.getEntity()),"utf-8");
        		System.out.println("请求结果：" + res);


        	}
        } catch (Exception e) {
        	e.printStackTrace();
        	res = "系统异常";
        } finally {
        	if(response != null){
        		response.close();
        	}
        	if(client != null){
        		client.close();
        	}
        }
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().print(res);

    }

}
