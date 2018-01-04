package cn.nvinfo.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {

	/**
	 * MD5加密后转大写
	 * 
	 * @param str需要加密的字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException
	 */
	public static String Md5Encode(String str) {
		StringBuilder sign = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(str.getBytes());

			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(bytes[i] & 0xFF);
				if (hex.length() == 1) {
					sign.append("0");
				}
				sign.append(hex.toUpperCase());
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sign.toString();
	}

	/**
	 * MD5加密后转小写
	 * 
	 * @param str需要加密的字符串
	 * @return 加密后的字符串
	 * @throws NoSuchAlgorithmException
	 */
	public static String Md5EncodeLower(String str) {
		StringBuilder sign = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(str.getBytes());

			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(bytes[i] & 0xFF);
				if (hex.length() == 1) {
					sign.append("0");
				}
				sign.append(hex.toLowerCase());
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sign.toString();
	}
	/**
	 * 随机生成数字验证码
	 * 
	 * @return 生成后的验证码
	 */
	public static String getRandomString(int c) {
		char[] chars = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= c; i++) {
			sb.append(chars[random.nextInt(chars.length)]);
		}
		return sb.toString();
	}
/**
 * 获取post请求传过来的参数
 * @param request
 * @return
 * @throws IOException
 */
	public static String receivePost(HttpServletRequest request) throws IOException{
		request.setCharacterEncoding("UTF-8");

		// 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}
}
