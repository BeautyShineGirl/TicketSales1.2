package cn.nvinfo.utils;

import java.io.InputStream;
import java.util.Properties;

public class AlipayConfig {
	
    public static String app_id ;
    
    
    public static String private_key;
    
    
   
    public static String alipay_public_key;
    
    public static String notify_url;
    static{
        Properties prop = new Properties();   
        InputStream in = SwiftpassConfig.class.getResourceAsStream("/alipay.properties");   
        try {   
            prop.load(in);   
            app_id = prop.getProperty("app_id").trim();   
            private_key = prop.getProperty("private_key").trim();   
            alipay_public_key = prop.getProperty("alipay_public_key").trim();  
            notify_url = prop.getProperty("notify_url").trim(); 
        } catch (Exception e) {   
            e.printStackTrace();   
        } 
    }
}
