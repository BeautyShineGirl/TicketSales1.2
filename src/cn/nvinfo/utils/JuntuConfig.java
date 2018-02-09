package cn.nvinfo.utils;

import java.io.InputStream;
import java.util.Properties;
/**
 * 骏图公共参数
 * @author admin
 *
 */
public class JuntuConfig {
	//骏景宝提供给合作商商唯一标识
    public static String partnerId ;
    //骏景宝提供给合作商密钥
    public static String partnerKey;
   
    
    static{
        Properties prop = new Properties();   
        InputStream in = JuntuConfig.class.getResourceAsStream("/juntu.properties");   
        try {   
            prop.load(in);   
            partnerId = prop.getProperty("partnerId").trim();   
            partnerKey = prop.getProperty("partnerKey").trim();   
        } catch (Exception e) {   
            e.printStackTrace();   
        } 
    }
}
