package ustc.sce.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.User;
import ustc.sce.utils.PageUtil;

/**
 * 公开论文持久层   列表 查找  接口
 * @author 秋色天堂
 *
 */
public class PublicPaperDaoImp extends HibernateDaoSupport implements PublicPaperDao {
	
	private PageUtil pageUtil;
	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	/**
	 * 公开论文列表
	 */
	public Page getForPage(int currentPage, int pageSize) {
		
		String hql1 = "SELECT COUNT(*) FROM Paper where ispublic ='"+1+"'";
		String hql2="from Paper where ispublic ='"+1+"'";
		
		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}


	/**
	 * 公开论文 根据论文题目进行查找
	 */
	public Page publicPaperSearch(String keyWords, int currentPage, int pageSize) {
		String hql1 = "SELECT COUNT(*) FROM Paper where paperTitle like '" + "%" + keyWords + "%" + "'"
				+ "and ispublic ='" + 1 + "'";
		String hql2 = "from Paper where paperTitle like '" + "%" + keyWords + "%" + "'" + "and ispublic ='" + 1
				+ "'";
		
		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}


	/**
	 * 收藏公开论文
	 */
	public Paper publicPaperCollect(User user, int paperId) {
		
		String hql="from Paper as paper where paper.id='"+paperId+"'";
		List<Paper> list = (List<Paper>) getHibernateTemplate().find(hql);
        
        if(list.isEmpty()) {
        	return null;
        }
        Paper paper = list.get(0);
        paper.getUsers().add(user);
        
        this.getHibernateTemplate().save(paper);
		
		return paper;
	}

}
