package ustc.sce.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户：注册  登录
 * @author 秋色天堂
 *
 */
public class User {
	
	private int id;
	private String userName;
	private String userPassword;
	
	//用户和角色之间多对多的关联  只在用户这一方进行维护
	private Set<Role> roles = new HashSet<Role>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	

}
