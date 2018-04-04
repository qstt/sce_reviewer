package ustc.sce.domain;

/**
 * 论文评审：记录论文评审的状态   私有论文不进行提交0  未提交1  正在评审2  已定稿3
 * @author 秋色天堂
 *
 */
public class PaperReview {
	
	private int id;
	//论文状态  未提交0  正在评审1  已定稿2
	private int paperStatus;
	//老师对该论文的评审状态   正在评审1  已经评审2  默认为0
	private int teacherStatus;
	
	//关联论文  论文和论文评审状态是一对一的关系   
	//一对一关系是一对多关系的特殊   把论文评审当成多方进行关联   只在多方进行关联
	private Paper paper;
	
	//关联用户   谁评论的
	//用户和论文评审是一对多的关系   一个用户可以对多个论文进行评审
	//论文是多方  只在多方进行关联
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPaperStatus() {
		return paperStatus;
	}

	public void setPaperStatus(int paperStatus) {
		this.paperStatus = paperStatus;
	}

	public int getTeacherStatus() {
		return teacherStatus;
	}

	public void setTeacherStatus(int teacherStatus) {
		this.teacherStatus = teacherStatus;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

}
