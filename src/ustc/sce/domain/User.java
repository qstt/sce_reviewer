package ustc.sce.domain;

/**
 * 用户：注册  登录
 * @author 秋色天堂
 *
 */
public class User {
	
	private int id;
	private String userName;
	private String userPassword;
	
	//用户和角色之间多对一的关联  只在用户这方进行维护
	private Role role;

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	
	

}
