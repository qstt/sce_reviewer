package ustc.sce.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.User;
import ustc.sce.utils.PageUtil;

/**
 * 个人中心持久层 我的论文 我的批注 我的收藏
 * 
 * @author 秋色天堂
 *
 */
public class IndividualCenterDaoImp extends HibernateDaoSupport implements IndividualCenterDao {

	private PageUtil pageUtil;

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	/**
	 * 论文收藏 列表显示
	 */
	public Page getForPage(User user, int currentPage, int pageSize) {

		Page page = new Page();

		int userId = user.getId();
		String hql1 = "SELECT COUNT(*) from Paper as paper inner join paper.users as u where u.id='" + userId + "'"
				+ "and ispublic='" + 1 + "'";
		String hql2 = "from Paper as paper inner join paper.users as u where u.id='" + userId + "'" + "and ispublic='"
				+ 1 + "'";
		page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);

		return page;
	}

	/**
	 * 取消收藏
	 */
	public boolean cancelCollection(User user, int paperId) {

		int userId = user.getId();

		String sql = "delete from collect where paperId='" + paperId + "'" + "and userId='" + userId + "'";
		SQLQuery createSQLQuery = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		int executeUpdate = createSQLQuery.executeUpdate();// executeUpdate更新的条数

		if (executeUpdate == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 收藏论文   根据论文题目进行查找
	 */
	public Page searchCollection(User user, String keyWords, int currentPage, int pageSize) {
		Page page = new Page();

		int userId = user.getId();
		String hql1 = "SELECT COUNT(*) from Paper as paper inner join paper.users as u where u.id='" + userId + "'"
				+ "and ispublic='" + 1 + "'" + "and paperTitle like '" + "%" + keyWords + "%" + "'";
		String hql2 = "from Paper as paper inner join paper.users as u where u.id='" + userId + "'" + "and ispublic='"
				+ 1 + "'" + "and paperTitle like '" + "%" + keyWords + "%" + "'";
		page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);

		return page;
	}

}
