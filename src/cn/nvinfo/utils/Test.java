package cn.nvinfo.utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.xmlbeans.impl.store.Locale;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/*
 * 测试分销商管理 的删除    URL	2017-09-28
 */
public class Test {
	/*public static void test(String fileName,String url){
		String[] arr=url.split(",");
		String newUrl=null;
		for (int i = 0; i < arr.length; i++) {
			if(!arr[i].equals("upload/files/"+fileName)){
				if(newUrl==null){
					newUrl=arr[i];
				}else{
					newUrl=newUrl+",\n\t"+arr[i];
				}
			}
		}
		System.out.println(newUrl);
	}

			public static void test1(String url){
				// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt  
				// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分  
				String lastName = url.substring(url.lastIndexOf("\\") + 1);  
				// 得到上传文件的扩展名  
				String fileExtName = lastName.substring(lastName.lastIndexOf(".") + 1); 
				System.out.println(fileExtName);
			}
	public static void main(String[] args) {
		//String fileName="20170928111022分发平台.pptx";
		String url="upload/files/20170928111022分发平台.pptx";
		//test(fileName,url);
		test1(url);
	}*/

	/*public static void main(String[] args) {

		List<Date> list = getAllTheDateOftheMonth(new Date());

		for(Date date: list){
			System.out.println(date.toString());
		}

	}

	private static List<Date> getAllTheDateOftheMonth(Date date) {
		List<Date> list = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);

		int month = cal.get(Calendar.MONTH);
		while(cal.get(Calendar.MONTH) == month){
			list.add(cal.getTime());
			cal.add(Calendar.DATE, 1);
		}
		return list;
	}*/
	/*public static Date[] getDates(String year, String month) {
		int maxDate = 0;
		Date first = null;
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			first = sdf.parse(year + month);
			cal.setTime(first);
			maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date[] dates = new Date[maxDate];
		for (int i = 1; i <= maxDate; i++) {
			dates[i - 1] = new Date(first.getTime());
			first.setDate(first.getDate() + 1);
		}
		return dates;
	}

	public static void main(String[] args) {
		Date[] dates = getDates("2017", "11");
		for (int i = 0; i < dates.length; i++) {
			System.out.println(dates[i]);
		}
	}*/

	/*@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		//  String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar calendar=Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
		long next =calendar.getTimeInMillis();
		calendar.add(Calendar.MONTH,-1);  // 这里想要得到当月的月份，这里写0不行？
		List<String> list =new ArrayList<String>();
		while(calendar.getTimeInMillis()!=next)
		{
			//System.out.println(calendar.getTime().toLocaleString());
			String localeString = calendar.getTime().toLocaleString();

			String substring = localeString.substring(0, 10);
			list.add(substring);
			//System.out.println(localeString.substring(0, 9));
			int w = calendar.get(Calendar.DAY_OF_WEEK)-1;
			if (w<0) {
				w=0;
			}
			 System.out.println(weekDays);
			int i=1;
			calendar.add(Calendar.DAY_OF_MONTH, i++);
		}
		String datePrice=null;
		for (String date : list) {
			if(datePrice==null||datePrice.equals("")){
				datePrice=date+"&"+"70";
			}else{
				datePrice=datePrice+"|"+date+"&"+"70";
			}
		}
		System.out.println(datePrice);
	}
	 */
	/*	public static Date[] getDates(String year, String month) {
		int maxDate = 0;
		Date first = null;
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			first = sdf.parse(year + month);
			cal.setTime(first);
			maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date[] dates = new Date[maxDate];
		for (int i = 1; i <= maxDate; i++) {
			dates[i - 1] = new Date(first.getTime());
			first.setDate(first.getDate() + 1);
		}
		return dates;
	}*/
	/*
	 * 获得从当前日期开始一个月的日期
	 */
	public static List<String> getDates() {
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		/*String date = format.format(calendar.getTime());
		String substring2 = date.substring(8);
		System.out.println(substring2);*/
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DATE));
		long next =calendar.getTimeInMillis();
		calendar.add(Calendar.MONTH,-1);  // 这里想要得到当月的月份，这里写0不行？
		List<String> list =new ArrayList<String>();
		while(calendar.getTimeInMillis()!=next)
		{
			String localeString = format.format(calendar.getTime());

			String substring = localeString.substring(0, 10);
			list.add(substring);
			int i=1;
			calendar.add(Calendar.DAY_OF_MONTH, i++);
		}
		return list;
	}

	/*public static void main(String[] args) {
		//Date[] dates = getDates(""+calendar.get(Calendar.YEAR)+"",""+(calendar.get(Calendar.MONTH)+1+1)+"");
		Calendar calendar=Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+2, calendar.get(Calendar.DATE));
		long next =calendar.getTimeInMillis();
		calendar.add(Calendar.MONTH,-1);  // 这里想要得到当月的月份，这里写0不行？
		List<String> list2 =new ArrayList<String>();
		while(calendar.getTimeInMillis()!=next)
		{
			String localeString = calendar.getTime().toLocaleString();

			String substring = localeString.substring(0, 10);
			list2.add(substring);
			int i=1;
			calendar.add(Calendar.DAY_OF_MONTH, i++);
		}
		List<String> list =new ArrayList<String>();
		for (String list1 : getDates()) {
			list.add(list1);
		}
		for (String list3 : list2) {
			list.add(list3);
		}
		String datePrice=null;
		for (String date : list) {
			if(datePrice==null||datePrice.equals("")){
				datePrice=date+"&"+"70";
			}else{
				datePrice=datePrice+"|"+date+"&"+"70";
			}
		}

		for (Date date : dates) {
			String substring = date.toLocaleString().substring(0, 10);
			list.add(substring);
			for (String date1 : list) {
				if(datePrice==null||datePrice.equals("")){
					datePrice=date1+"&"+"70";
				}else{
					datePrice=datePrice+"|"+date1+"&"+"70";
				}
			}
		}
		System.out.println(datePrice);
	}*/
	/**
	 * java 获取当前年份 月份 日期
	 * @param args
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 */
	/* public static void main(String[] args) {
		    Calendar cal = Calendar.getInstance();
		    int day = cal.get(Calendar.DATE);
		    int month = cal.get(Calendar.MONTH) + 1;
		    int year = cal.get(Calendar.YEAR);
		    int dow = cal.get(Calendar.DAY_OF_WEEK);
		    int dom = cal.get(Calendar.DAY_OF_MONTH);
		    int doy = cal.get(Calendar.DAY_OF_YEAR);

		    //System.out.println("Current Date: " + cal.getTime());
		    System.out.println("Day: " + day);
		    System.out.println("Month: " + month);
		    System.out.println("Year: " + year);
		   // System.out.println("Day of Week: " + dow);
		   // System.out.println("Day of Month: " + dom);
		    //System.out.println("Day of Year: " + doy);
		  }*/

	public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
		/*
		 * 如果salePrice等于原来的销售价，且partPrice不等于null时，认为是个别日期的销售价修改，这个时候修改calendar表中的个别日期所对应的销售价
		 * 把原来的datePrice拿到后截开，遍历，partPrice中各组的日期，把对应的日期价格替换掉
		 * [{"date":"2017-12-13","price":"66"},{"date":"2017-12-14","price":"666"},{"date":"2017-12-15","price":"66"},{"date":"2017-12-16","price":"30.0"},
		 * {"date":"2017-12-17","price":"30.0"},{"date":"2017-12-18","price":"30.0"},{"date":"2017-12-19","price":"30.0"},{"date":"2017-12-20","price":"30.0"},]
		 */
		/*String partPrice="[{'date':'2017-12-13','price':'66'},{'date':'2017-12-14','price':'666'},{date':'2017-12-15','price':'66'}]";
		String[] date_price_group=partPrice.split("[");	
		for (String string : date_price_group) {
			System.out.println(string+"=======");
		}*/
		/*	String str = "1510070400";
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");

		  long millionSeconds = sdf.parse(str.toString()).getTime();//毫秒
		  Date startReportDate = sdf.parse(str.toString());
		  System.out.println(startReportDate);*/
		/*String str="20171228100318p12.jpg,20171228100327p19.jpg";
		String[] split = str.split(",");
		String newUrl=null;
		for (int i = 0; i < split.length; i++) {
			if(!split[i].equals("20171228100318p12.jpg")){
				if(newUrl==null){
					newUrl=split[i];
				}else{
					newUrl=newUrl+","+split[i];
				}
			}
		}
		System.out.println(newUrl);*/
		/*System.out.println(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));*/
		//处理data数据page和pageSize，pageSize=20，page为变量
		/*JSONObject js=new JSONObject();
		js.put("method","page");
		js.put("page",1);
		js.put("pageSize", 20);

		byte[] encrypt = EnDecrypt.encrypt(js.toString().getBytes(), JuntuConfig.partnerKey.getBytes());//AES128加密
		System.out.println(new String(encrypt,"utf-8"));*/
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		long millionSeconds = sdf.parse("1510070400").getTime();//毫秒
		System.out.println(millionSeconds);*/

		/*//获取当前系统时间
		 * */
		/*	 
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		//System.out.println(df.format(new Date()));

		//获取两个月后的今天
		Calendar c = Calendar.getInstance();
		System.out.println(df.format(c.getTime()));
		 c.add(Calendar.MONTH, 2);
		  System.out.println(df.format(c.getTime()));*/


		/*//获取从当前时间开始的两个月的信息
		Calendar calendar=Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+2, calendar.get(Calendar.DATE));
		long next =calendar.getTimeInMillis();
		calendar.add(Calendar.MONTH,-1);  // 这里想要得到当月的月份，这里写0不行？
		List<String> list2 =new ArrayList<String>();
		while(calendar.getTimeInMillis()!=next)
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String localeString = format.format(calendar.getTime());
			String substring = localeString.substring(0, 10);
			list2.add(substring);
			int k=1;
			calendar.add(Calendar.DAY_OF_MONTH, k++);
		}
		List<String> list =new ArrayList<String>();
		for (String list1 : getDates()) {
			list.add(list1);
		}
		for (String list3 : list2) {
			list.add(list3);
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}  */

		//获取从当前时间开始的两个月的信息
		/*Calendar calendar=Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+2, calendar.get(Calendar.DATE));
		long next =calendar.getTimeInMillis();
		calendar.add(Calendar.MONTH,-1);  // 这里想要得到当月的月份，这里写0不行？
		List<String> list2 =new ArrayList<String>();
		while(calendar.getTimeInMillis()!=next)
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String localeString = format.format(calendar.getTime());
			String substring = localeString.substring(0, 10);
			list2.add(substring);
			int i=1;
			calendar.add(Calendar.DAY_OF_MONTH, i++);
		}
		List<String> list =new ArrayList<String>();
		for (String list1 : getDates()) {
			list.add(list1);
		}
		for (String list3 : list2) {
			list.add(list3);
		}
		for (String date : list) {
			System.out.println(date);
		}*/
		
		
			System.err.println(cn2Spell("康其行"));
			System.out.println(cn2FirstSpell("杨立"));
	}


	/**  
	 * 获取汉字串拼音首字母，英文字符不变  
	 *  
	 * @param chinese 汉字串  
	 * @return 汉语拼音首字母  
	 */   
	public static String cn2FirstSpell(String chinese) {   
		StringBuffer pybf = new StringBuffer();   
		char[] arr = chinese.toCharArray();   
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
		for (int i = 0; i < arr.length; i++) {   
			if (arr[i] > 128) {   
				try {   
					String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);   
					if (_t != null) {   
						pybf.append(_t[0].charAt(0));   
					}   
				} catch (BadHanyuPinyinOutputFormatCombination e) {   
					e.printStackTrace();   
				}   
			} else {   
				pybf.append(arr[i]);   
			}   
		}   
		return pybf.toString().replaceAll("\\W", "").trim().toUpperCase();   
	}   
	/**  
	 * 获取汉字串拼音，英文字符不变  
	 *  
	 * @param chinese 汉字串  
	 * @return 汉语拼音  
	 */  
	public static String cn2Spell(String chinese) {   
		StringBuffer pybf = new StringBuffer();   
		char[] arr = chinese.toCharArray();   
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
		for (int i = 0; i < arr.length; i++) {   
			if (arr[i] > 128) {   
				try {   
					pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);   
				} catch (BadHanyuPinyinOutputFormatCombination e) {   
					e.printStackTrace();   
				}   
			} else {   
				pybf.append(arr[i]);   
			}   
		}   
		return pybf.toString();   
	}   

	

}
