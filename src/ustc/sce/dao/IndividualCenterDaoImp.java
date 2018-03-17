package ustc.sce.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.Collection;
import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.User;
import ustc.sce.utils.PageUtil;

public class IndividualCenterDaoImp extends HibernateDaoSupport implements IndividualCenterDao {
	
	private PageUtil pageUtil;
	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	public Page getForPage(User user, int currentPage, int pageSize) {
		
		Page page = new Page();
		
		int userId = user.getId();
		String hql="from Collection as collection inner join fetch collection.collectUsers as c where c.id='"+userId+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<Collection> list = query.list();
        
        for(int i = 0;i < list.size();i ++) {
        	Collection collection = list.get(i);
        	Set<Paper> papers = collection.getCollectPapers();
        	Iterator<Paper> iterator = papers.iterator();
        	while(iterator.hasNext()) {
        		Paper paper = iterator.next();
        		int paperId = paper.getId();
        		
        		String hql1 = "SELECT COUNT(*) FROM Paper where id ='"+paperId+"'";
        		String hql2="from Paper where id ='"+paperId+"'";
        		
        		page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
        	}
        }
		return page;
	}

}
