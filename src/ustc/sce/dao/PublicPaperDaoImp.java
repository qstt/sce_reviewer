package ustc.sce.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.Collection;
import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.User;
import ustc.sce.utils.PageUtil;

public class PublicPaperDaoImp extends HibernateDaoSupport implements PublicPaperDao {
	
	private PageUtil pageUtil;
	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}


	public Page getForPage(int currentPage, int pageSize) {
		
		String hql1 = "SELECT COUNT(*) FROM Paper where ispublic ='"+1+"'";
		String hql2="from Paper where ispublic ='"+1+"'";
		
		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}


	@Override
	public Page publicPaperSearch(String keyWords, int currentPage, int pageSize) {
		String hql1 = "SELECT COUNT(*) FROM Paper where paperTitle like '"+ "%" + keyWords + "%" +"'";
		String hql2="from Paper where paperTitle like '"+ "%" + keyWords + "%" +"'";
		
		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}


	@Override
	public Collection publicPaperCollect(User user, int collectPaperId) {
		
		Collection collection = new Collection();
		
		String hql="from Paper as paper where paper.id='"+collectPaperId+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<Paper> list = query.list();
        
        if(list.isEmpty()) {
        	return null;
        }
        Paper paper = list.get(0);
        
        collection.getCollectUsers().add(user);
        collection.getCollectPapers().add(paper);
        
        this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(collection);
		
		return collection;
	}

}
