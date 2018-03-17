package ustc.sce.domain;

/**
 * 用户角色：老师  学生
 * @author 秋色天堂
 *
 */
public class Role {
	
	private int id;
	//用户角色：老师  学生
	private String roleName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	

}
