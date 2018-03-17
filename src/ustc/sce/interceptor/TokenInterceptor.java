package ustc.sce.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import ustc.sce.response.Response;

/**
 * 用于验证用户是否登录  检查token
 * @author 秋色天堂
 *
 */
public class TokenInterceptor implements HandlerInterceptor{

	
	public boolean preHandle(HttpServletRequest requset, HttpServletResponse response, Object arg2) throws Exception {
		
		String token = requset.getHeader("X-Token");
		System.out.println("进入拦截器中......");
		
		if(token != null) {
			return true;
		}else {
			response.getWriter().print(JSON.toJSONString(new Response().failure("没有登录...")));
			return false;
		}
		
	}

	
	
	
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	
}
