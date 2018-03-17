package ustc.sce.utils;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import ustc.sce.domain.Page;

/**
 * 列表展示 分页
 * 
 * @author 秋色天堂
 * @param <T>
 */
@Transactional
public class PageUtil<T> extends HibernateDaoSupport {

	public Page getForPage(String hql1,String hql2,int currentPage, int pageSize) {
		Page page = new Page();
		int allRow = getAllRowCount(hql1).intValue();
		int offset = page.countOffset(currentPage, pageSize);
		List<T> list = (List<T>) getForPageList(hql2,offset, pageSize);
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		page.setTotalRecords(allRow);
		page.setList(list);
		return page;

	}

	/**
	 * 根据hql语句 查询数据库表中有多少条记录
	 * 
	 * @return
	 */
	public Long getAllRowCount(String hql) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return Long.parseLong(query.uniqueResult().toString());
	}

	public List<T> getForPageList(String hql, int offset, int pageSize) {
		List<T> EntityList = null;
		// String hql="from FileEntity";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		EntityList = query.list();
		return EntityList;
	}

}
