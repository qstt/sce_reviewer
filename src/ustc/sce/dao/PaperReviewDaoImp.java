package ustc.sce.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.PaperReview;
import ustc.sce.domain.User;
import ustc.sce.utils.TokenUtil;

public class PaperReviewDaoImp extends HibernateDaoSupport implements PaperReviewDao {

	private TokenUtil tokenUtil = new TokenUtil();
	
	public PaperReview submitPaper(int paperId,int teacherId) {
		
		String hql = "from PaperReview as pr inner join fetch pr.paper as p where p.id='" + paperId + "'";
		List<PaperReview> list = (List<PaperReview>) this.getHibernateTemplate().find(hql);
		String hql1="from User as user where user.id='" + teacherId +"'";
		List<User> list1 = (List<User>) this.getHibernateTemplate().find(hql1);
		
		if(list.isEmpty() || list1.isEmpty()) {
			return null;
		}
		
		PaperReview paperReview = list.get(0);
		User teacher = list1.get(0);
		paperReview.setPaperStatus(1);
		paperReview.setTeacherStatus(1);
		paperReview.setUser(teacher);  
		
		this.getHibernateTemplate().update(paperReview);
		
		return paperReview;
	}

	@Override
	public List<User> getTeachers() {
		String hql = "from User as user inner join fetch user.role as r where r.id='" + 2 + "'";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql);
		if(list.isEmpty()) {
			return null;
		}
		return list;
	}

	@Override
	public PaperReview reviewing(int paperId, int teacherStatus) {
		String hql = "from PaperReview as pr inner join fetch pr.paper as p where p.id='" + paperId + "'";
		List<PaperReview> list = (List<PaperReview>) this.getHibernateTemplate().find(hql);
		if(list.isEmpty()) {
			return null;
		}
		PaperReview paperReview = list.get(0);
		if(teacherStatus == 1) {
			teacherStatus = 2;
		}else if(teacherStatus == 2) {
			teacherStatus = 1;
		}
		paperReview.setTeacherStatus(teacherStatus);
		this.getHibernateTemplate().update(paperReview);
		
		return paperReview;
	}

	@Override
	public PaperReview finalVersion(int paperId) {
		String hql = "from PaperReview as pr inner join fetch pr.paper as p where p.id='" + paperId + "'";
		List<PaperReview> list = (List<PaperReview>) this.getHibernateTemplate().find(hql);
		
		if(list.isEmpty()) {
			return null;
		}
		
		PaperReview paperReview = list.get(0);
		paperReview.setPaperStatus(2);
		paperReview.setTeacherStatus(2);
		
		this.getHibernateTemplate().update(paperReview);
		
		return paperReview;
	}

}
