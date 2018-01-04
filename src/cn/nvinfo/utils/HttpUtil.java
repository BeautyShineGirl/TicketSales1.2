package cn.nvinfo.utils;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * 在java中处理http请求.
 * 
 * @author nagsh
 * 
 */
@SuppressWarnings("deprecation")
public class HttpUtil {
	/**
	 * 处理get请求.
	 * 
	 * @param url
	 *            请求路径
	 * @param json
	 * @return json
	 */
	public static String get(String url) {
		// 实例化httpclient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 实例化get方法
		HttpGet httpget = new HttpGet(url);
		// 请求结果
		CloseableHttpResponse response = null;
		String content = "";
		try {
			// 执行get方法
			response = httpclient.execute(httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
				content = EntityUtils.toString(response.getEntity(), "utf-8");
				System.out.println(content);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 处理post请求.
	 * 
	 * @param url
	 *            请求路径
	 * @param params
	 *            参数
	 * @return xmlstr
	 */

	public static String post(String url, String xml) {
		try {
			byte[] jsonbyte = xml.getBytes("UTF-8");
			URL url1 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setConnectTimeout(5000);// 超时
			conn.setDoOutput(true);// 允许输出
			conn.setDoInput(true);// 允许输入

			conn.setUseCaches(false);// 不使用缓存
			conn.setRequestMethod("POST");// POST方式传输
			conn.setRequestProperty("Charset", "UTF-8");// 设置编码格式
			conn.setRequestProperty("Content-Length", String.valueOf(jsonbyte.length));// 传输的长度
			conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");// 设置数据类型，以及编码格式
			conn.setRequestProperty("X-ClientType", "WXZF");
			conn.getOutputStream().write(jsonbyte);// 输出内容
			conn.getOutputStream().flush();// 关闭流
			conn.getOutputStream().close();// 清空流
			System.out.println("平台返回的状态码：" + conn.getResponseCode());
			if (conn.getResponseCode() != 200)
				System.out.println("请求失败");
			InputStream is = conn.getInputStream();// 获取返回数据
			// 使用输出流来输出字符串
			InputStream isto = is;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = isto.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			String string = out.toString("UTF-8");
			System.out.println("平台返回的数据：" + string);
			out.close();
			return string;
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} catch (ProtocolException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

	/*@SuppressWarnings("deprecation")
	public static String sendPost(String url, String data,Config c) throws Exception {
		//指定读取证书格式为PKCS12
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		//读取本机存放的PKCS12证书文件
		FileInputStream instream = new FileInputStream(new File(c.getCertLocalPath()));
		try {
		//指定PKCS12的密码(商户ID)
		keyStore.load(instream, c.getCertPassword().toCharArray());
		} finally {
		instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom()
		.loadKeyMaterial(keyStore, c.getCertPassword().toCharArray()).build();
		//指定TLS版本
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		sslcontext,new String[] { "TLSv1" },null,
		SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		//设置httpclient的SSLSocketFactory
		CloseableHttpClient httpclient = HttpClients.custom()
		.setSSLSocketFactory(sslsf)
		.build();


		String response = null;
		CloseableHttpResponse httpresponse = null;
		try {
			HttpPost httppost = new HttpPost(url);
			StringEntity stringentity = new StringEntity(data, ContentType.create("text/xml", "UTF-8"));
			httppost.setEntity(stringentity);
			httpresponse = httpclient.execute(httppost);
			if (httpresponse.getStatusLine().getStatusCode() == 200) {
				response = EntityUtils.toString(httpresponse.getEntity(), "utf-8");
				System.out.println(response.toString());
			}else{
				System.out.println("请求失败");
			}

		} finally {
			if (httpclient != null) {
				httpclient.close();
			}
			if (httpresponse != null) {
				httpresponse.close();
			}
		}

		return response;
	}*/
	/**
	 * 获取post请求传过来的数据
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static String receivePost(HttpServletRequest request) throws IOException {
		request.setCharacterEncoding("UTF-8");

		// 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}
}
