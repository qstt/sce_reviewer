package ustc.sce.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.UserDao;
import ustc.sce.domain.Role;
import ustc.sce.domain.User;
import ustc.sce.utils.MD5Utils;

/**
 * 用户业务层 检测用户是否已经注册  获得用户角色  注册  登录  退出登录
 * @author 秋色天堂
 *
 */
@Transactional
public class UserServiceImp implements UserService {
	
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * 检测用户是否已经注册
	 */
	public User checkUser(String userName) {
		return userDao.checkUser(userName);
	}
	
	/**
	 * 获得用户角色   默认是学生
	 */
	public List<Role> getRole() {
		return userDao.getRole();
	}
	
	/**
	 * 注册
	 */
	public User register(User user) {
		String userPassword = user.getUserPassword();
		userPassword = MD5Utils.md5(userPassword);
		user.setUserPassword(userPassword);
		return userDao.register(user);
	}

	/**登录
	 * @param userName 用户名
	 * @param userPassword 密码
	 * @return user
	 */
	public User login(String userName, String userPassword) {
		return userDao.login(userName,userPassword);
	}
	
	/**
	 * 退出登录
	 */
	public boolean exit(String userName) {
		return userDao.exit(userName);
	}


	

}
