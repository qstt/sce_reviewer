package ustc.sce.service;

import java.util.List;

import ustc.sce.domain.Role;
import ustc.sce.domain.User;

/**
 * 用户业务层接口 检测用户是否已经注册  获得用户角色  注册  登录  退出登录
 * @author 秋色天堂
 *
 */
public interface UserService {
	
	/** 
	 *检测用户是否已经注册  
	 *@param userName 注册时的用户名  
	 *@return  数据库中查询的用户结果
	 */
	public User checkUser(String userName);
	
	/**
	 * 获得用户角色   默认是学生
	 * @return  数据库中所有的角色
	 */
	public List<Role> getRole();
	
	/**注册
	 * @param userName 用户名
	 * @param userPassword 密码
	 * @param roleName 角色
	 * @return boolean 注册是否成功
	 */
	public boolean register(String userName, String userPassword, String roleName);

	/**登录
	 * @param userName 用户名
	 * @param userPassword 密码
	 * @return user
	 */
	public User login(String userName, String userPassword);

	/**
	 * 退出登录
	 * @param userName 用户名
	 * @return 退出成功或失败
	 */
	public boolean exit(String userName);

	

}
