package cn.nvinfo.utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Log {
	/**
	 * 写入文件方法
	 * @param content  写入的内容
	 * @param wjjname  文件夹名称
	 * @param filename 文件名称
	 */
	public static void write(String content) {


		try {  
			//兑换成功日志
			SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd");
			String time = sm.format(new Date()).toString();
			String pathName="/home/log/XinLangchao/";  
			String fileName ="20170010_"+time+"_log.txt";  
			File path = new File(pathName);  
			if(!path.exists()) {  
				path.mkdir();  
			}  
			File file = new File(path,fileName);  
			if(!file.exists()) {  
				file.createNewFile();  
			}  
			// 打开一个随机访问文件流，按读写方式  
            RandomAccessFile randomFile = new RandomAccessFile(pathName+fileName, "rw");  
            // 文件长度，字节数  
            long fileLength = randomFile.length();  
            // 将写文件指针移到文件尾。  
            randomFile.seek(fileLength); 
            SimpleDateFormat sm1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String time1 = sm1.format(new Date()).toString();
            randomFile.write(("时间:"+time1+"------"+content+"\r\n").getBytes("gb2312"));  
            randomFile.close(); 
		} catch(Exception e) {  
			e.printStackTrace();  
		}  
	}
}
