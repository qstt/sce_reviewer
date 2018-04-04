package ustc.sce.dao;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.Role;
import ustc.sce.domain.Token;
import ustc.sce.domain.User;
import ustc.sce.utils.MD5Utils;

public class UserDaoImp extends HibernateDaoSupport implements UserDao {

	/**
	 * 检测用户是否已经注册
	 */
	public User checkUser(String userName) {
		String hql = "from User as user where user.userName='" + userName + "'";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获得用户角色 默认是学生
	 */
	public List<Role> getRole() {
		String hql = "from Role";
		List<Role> list = (List<Role>) this.getHibernateTemplate().find(hql);

		if (list.isEmpty()) {
			return null;
		}
		return list;
	}

	/**
	 * 注册
	 */
	public User register(User user) {
		Role role = new Role();
		
		String roleName = user.getRole().getRoleName();
		try {
			roleName=new String(roleName.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String hql = "from Role as role where role.roleName='" + roleName + "'";
		List<Role> list = (List<Role>) this.getHibernateTemplate().find(hql);
		if(list.isEmpty()) {
			return null;
		}else {
			role = list.get(0);
		}

		user.setRole(role);
		Serializable save = this.getHibernateTemplate().save(user);
		if (save == null) {
			return null;
		}
		return user;
	}

	/**
	 * 登录
	 */
	public User login(String userName, String userPassword) {
		String hql = "from User as user where user.userName='" + userName + "' and user.userPassword = '"
				+ MD5Utils.md5(userPassword) + "'";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 退出登录
	 */
	public boolean exit(String userName) {
		String hql = "from Token as token where token.userName='" + userName + "'";
		List<Token> list = (List<Token>) this.getHibernateTemplate().find(hql);
		if(!list.isEmpty()) {
			Token token = list.get(0);
			this.getHibernateTemplate().delete(token);
		}

		List<Token> list1 = (List<Token>) this.getHibernateTemplate().find(hql);

		if (list1.isEmpty()) {
			return true;
		}
		return false;
	}

}
