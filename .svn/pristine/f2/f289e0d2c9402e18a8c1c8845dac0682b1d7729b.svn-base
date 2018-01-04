package cn.nvinfo.utils;



import java.io.Serializable;

/**
 * Created by yangli 
 */
public class Result implements Serializable {

    /**
     * 程序执行是否成功
     */
    private Integer code = null;

    /**
     * 程序执行结果的提示信息
     */
    private String msg = "" ;

    /**
     * 用来封装业务数据
     */
    private Object data = null;


    /**
     * ʹ使用默认构造器初始化
     * success = true;
     * msg = "";
     * data = null;
     */
    public Result() {
        super();
    }

    /**
     * success = false;
     * msg = msg;
     * data = null;
     */
    public Result(Integer code,String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    /**
     * success = success;
     * msg = "";
     * data = data;
     */
    public Result(Integer code,String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
