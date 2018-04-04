package ustc.sce.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 论文：论文包含文件
 * @author 秋色天堂
 */
public class Paper {
	
	private int id;
	//论文题目
	private String paperTitle;
	//论文作者
	private String paperAuthor;
	//论文所有者（创建论文的用户）
	private String paperOwner;
	//论文是否公开     不公开只能论文上传者也就是用户自己看到
	private boolean ispublic;
	//论文的状态    默认状态为0
	private PaperReview paperReview;
	//该论文的收藏者
	private Set<User> users = new HashSet<User>();
	//该论文所关联的文件
	private Set<FileEntity> fileEntitys = new HashSet<FileEntity>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPaperTitle() {
		return paperTitle;
	}
	public void setPaperTitle(String paperTitle) {
		this.paperTitle = paperTitle;
	}
	public String getPaperAuthor() {
		return paperAuthor;
	}
	public void setPaperAuthor(String paperAuthor) {
		this.paperAuthor = paperAuthor;
	}
	public String getPaperOwner() {
		return paperOwner;
	}
	public void setPaperOwner(String paperOwner) {
		this.paperOwner = paperOwner;
	}
	public boolean isIspublic() {
		return ispublic;
	}
	public void setIspublic(boolean ispublic) {
		this.ispublic = ispublic;
	}
	public PaperReview getPaperReview() {
		return paperReview;
	}
	public void setPaperReview(PaperReview paperReview) {
		this.paperReview = paperReview;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<FileEntity> getFileEntitys() {
		return fileEntitys;
	}
	public void setFileEntitys(Set<FileEntity> fileEntitys) {
		this.fileEntitys = fileEntitys;
	}
	
	
	
	
}
