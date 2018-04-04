package ustc.sce.domain;

/**
 * 存放token信息
 * @author 秋色天堂
 *
 */
public class Token {
	
	private int id;
	//通过UUID产生的token
	private String token;
	//token 和该用户的用户名进行关联
	private String userName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
