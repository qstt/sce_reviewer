package ustc.sce.domain;

/**
 * 存放token信息
 * @author 秋色天堂
 *
 */
public class Token {
	
	private int id;
	private String token;
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
