package ustc.sce.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.UserDao;
import ustc.sce.domain.Role;
import ustc.sce.domain.User;
import ustc.sce.utils.MD5Utils;

@Transactional
public class UserServiceImp implements UserService {

	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	@Override
	public User login(String userName, String userPassword) {
		return userDao.login(userName,userPassword);
	}


	@Override
	public boolean register(String userName, String userPassword, String roleName) {
		userPassword = MD5Utils.md5(userPassword);
		boolean register = userDao.register(userName,userPassword,roleName);
		if (register) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * 检测用户是否已经注册
	 */
	public User checkUser(String userName) {
		return userDao.checkUser(userName);
	}

	public boolean exit(String userName) {
		return userDao.exit(userName);
	}


	@Override
	public List<Role> getRole() {
		return userDao.getRole();
	}

}
