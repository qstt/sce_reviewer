package ustc.sce.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import ustc.sce.authorization.TokenManager;
import ustc.sce.domain.Role;
import ustc.sce.domain.Token;
import ustc.sce.domain.User;
import ustc.sce.domain.UserToken;
import ustc.sce.response.Response;
import ustc.sce.service.UserService;

/**
 * 用户控制层 登录 注册
 * 
 * @author 秋色天堂
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "tokenManager")
	private TokenManager tokenManager;
	
	/**
	 * 检测用户是否已经注册
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET,produces="text/html;charset=utf-8")
	public String checkUser(@RequestParam("userName") String userName) {
		
		User user = userService.checkUser(userName);
		if (user == null) {
			return JSON.toJSONString(new Response().success("该用户没有注册..."));
		}
		return JSON.toJSONString(new Response().failure("该用户已经注册..."));
	}
	
	/**
	 * 获得用户角色   默认是学生
	 * @return
	 */
	@RequestMapping(value = "/get_role", method = RequestMethod.GET,produces="text/html;charset=utf-8")
	public String getRole() {
		
		List<Role> roles = userService.getRole();
		
		return JSON.toJSONString(new Response().success(roles));
		
	}
	
	/**
	 * 注册
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST,produces=("application/json;charset=UTF-8"))
	public String register(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {

		String userName = user.getUserName();
		String userPassword = user.getUserPassword();
		String roleName = user.getRole().getRoleName();
		
		String roleName1=new String(roleName.getBytes("iso-8859-1"), "utf-8");
		
		boolean flag = userService.register(userName, userPassword, roleName1);
		if (flag) {
			return JSON.toJSONString(new Response().success("Register Success..."));
		}
		return JSON.toJSONString(new Response().failure("Register Failure..."));
	}
	
	/**
	 * 登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST,produces=("application/json;charset=UTF-8"))
	public String login(@RequestBody User user,
			HttpServletResponse response, HttpServletRequest request) {
		
		String userName = user.getUserName();
		String userPassword = user.getUserPassword();
		
		user = userService.login(userName, userPassword);
		if (user != null) {
			Token token = tokenManager.createToken(userName);
			String token3 = token.getToken();
			Cookie cookie = new Cookie("X-Token", token3);
			response.addCookie(cookie);
			
			UserToken userToken = new UserToken();
			userToken.setTokn(token);
			userToken.setUser(user);
			
			return JSON.toJSONString(new Response().success(userToken));
		}
		return JSON.toJSONString(new Response().failure("Login Failure..."));
	}

	/**
	 * 退出登录   删除数据库中的token
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public String exit(@RequestParam("userName") String userName) {
		
		boolean flag = userService.exit(userName);
		if (flag) {
			return JSON.toJSONString(new Response().success("Exit Success..."));
		}
		return JSON.toJSONString(new Response().failure("Exit Failure..."));
		
	}
	
}
