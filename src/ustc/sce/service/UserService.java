package ustc.sce.service;

import java.util.List;

import ustc.sce.domain.Role;
import ustc.sce.domain.User;

public interface UserService {

	public User login(String userName, String userPassword);

	public boolean register(String userName, String userPassword, String roleName);

	public User checkUser(String userName);

	public boolean exit(String userName);

	public List<Role> getRole();

}
