package ustc.sce.domain;

import java.util.HashSet;
import java.util.Set;

public class Collection {
	
	private int id;
	private Set<User> collectUsers = new HashSet<User>();
	private Set<Paper> collectPapers = new HashSet<Paper>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set<User> getCollectUsers() {
		return collectUsers;
	}
	public void setCollectUsers(Set<User> collectUsers) {
		this.collectUsers = collectUsers;
	}
	public Set<Paper> getCollectPapers() {
		return collectPapers;
	}
	public void setCollectPapers(Set<Paper> collectPapers) {
		this.collectPapers = collectPapers;
	}
	
	
	

}
