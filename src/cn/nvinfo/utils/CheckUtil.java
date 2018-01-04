package cn.nvinfo.utils;


/**
 * Created by yangli
 */
public class CheckUtil {

    /**
     * 检查参数是否为空或null，是则抛出异常
     * @param args	变长参数
     */
    public static String checkArgsNotNull(Object... args){
        String str = "";
        for(int i=0;i<args.length;i++){
            if(args[i] == null || "".equals(args[i])){
                str = "第"+(i+1)+"个参数不能为空";
                break;
            }
        }
        return str;
    }

    /**
     * 正则验证手机号
     * @return
     */
    public static boolean checkPhone(String phone){
        boolean flag = true;
        String reg = "(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[0-9])[0-9]{8}$";
        if(!phone.matches(reg)){
            flag = false;
        }
        return flag;
    }

}
