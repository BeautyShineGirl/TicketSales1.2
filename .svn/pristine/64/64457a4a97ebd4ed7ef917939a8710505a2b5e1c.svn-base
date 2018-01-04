package cn.nvinfo.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.nvinfo.domain.Staff;
import net.sf.json.JSONObject;


public class LoginIntercetpor extends HandlerInterceptorAdapter{
	@Override
	public void afterCompletion(HttpServletRequest request, 
			HttpServletResponse response, Object arg2, Exception arg3) 
					throws Exception { 
	} 
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, 
			Object arg2, ModelAndView arg3) throws Exception { 
	} 
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, 
			Object arg2) throws Exception { 
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String requestURI = request.getRequestURI();
		JSONObject obj=new JSONObject();
		//说明处在编辑的页面 
		HttpSession session = (HttpSession) request.getSession().getAttribute("staff"); 
		Staff staff = (Staff) session.getAttribute("staff"); 
		if(requestURI.endsWith("login.action")){
			obj.put("code", 1);
			obj.put("message", "验证通过！");
			return true;
		}else if(staff!=null){
			obj.put("code", 1);
			obj.put("message", "验证通过！");
			return true;
		}else{
			obj.put("code", 0);
			obj.put("message", "请进行 登录 ！");
			return false;
		}
	}
}

