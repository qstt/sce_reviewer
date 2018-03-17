package ustc.sce.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ustc.sce.utils.CollectionUtil;
import ustc.sce.utils.StringUtil;

/**        
 * Title: 跨域访问处理(跨域资源共享)    
 * Description: 解决前后端分离架构中的跨域问题
 */      
public class CorsFilter implements Filter{

	private String allowOrigin;
	private String allowMethods;
	private String allowCredentials;
	private String allowHeaders;
	private String exposeHeaders;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		allowOrigin = filterConfig.getInitParameter("allowOrigin");
		allowMethods = filterConfig.getInitParameter("allowMethods");
		allowCredentials = filterConfig.getInitParameter("allowCredentials");
		allowHeaders = filterConfig.getInitParameter("allowHeaders");
		exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
	}
	
	
	//通过CORS技术实现AJAX跨域访问,只要将CORS响应头写入response对象中即可
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String currentOrigin = request.getHeader("Origin");
//		if (StringUtil.isNotEmpty(allowOrigin)) {
//			List<String> allowOriginList = Arrays
//					.asList(allowOrigin.split(","));
//			if (CollectionUtil.isNotEmpty(allowOriginList)) {
//				if (allowOriginList.contains(currentOrigin)) {
//					response.setHeader("Access-Control-Allow-Origin",
//							currentOrigin);
//				}
//			}
//		}
		
		
		response.setHeader("Access-Control-Allow-Origin","*");
		
		
		if (StringUtil.isNotEmpty(allowMethods)) {
			response.setHeader("Access-Control-Allow-Methods", allowMethods);
		}
		if (StringUtil.isNotEmpty(allowCredentials)) {
			response.setHeader("Access-Control-Allow-Credentials",
					allowCredentials);
		}
		if (StringUtil.isNotEmpty(allowHeaders)) {
			response.setHeader("Access-Control-Allow-Headers", allowHeaders);
		}
		if (StringUtil.isNotEmpty(exposeHeaders)) {
			response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
		}
		chain.doFilter(req, res);
	}	
	
	public void destroy() {
		
	}


	
	

}
