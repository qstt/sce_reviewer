package ustc.sce.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Paper;
import ustc.sce.domain.PaperReview;
import ustc.sce.domain.User;
import ustc.sce.utils.MD5Utils;
import ustc.sce.utils.TokenUtil;

public class PaperReviewDaoImp extends HibernateDaoSupport implements PaperReviewDao {

	private TokenUtil tokenUtil = new TokenUtil();
	
	public PaperReview submitPaper(int paperId,int teacherId) {
		
		String hql = "from PaperReview as pr inner join fetch pr.paper as p where f.id='" + paperId + "'";
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

}
