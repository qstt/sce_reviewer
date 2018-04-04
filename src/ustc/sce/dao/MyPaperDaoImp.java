package ustc.sce.dao;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.Page;
import ustc.sce.domain.User;
import ustc.sce.utils.PageUtil;

public class MyPaperDaoImp extends HibernateDaoSupport implements MyPaperDao {
	
	private PageUtil pageUtil;
	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}
	

	@Override
	public Page myPaperList(User user, int currentPage, int pageSize) {
		String paperOwner = user.getUserName();
		String hql1 = "SELECT COUNT(*) FROM Paper where paperOwner ='" + paperOwner + "'";
		String hql2 = "from Paper where paperOwner ='" + paperOwner + "'";

		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}


	@Override
	public Page myPpaperSearch(User user, String keyWords, int currentPage, int pageSize) {
		Page page = new Page();

		String userName = user.getUserName();
		String hql1 = "SELECT COUNT(*) from Paper as paper where paper.paperOwner='" + userName + "'"
				+ "and paperTitle like '" + "%" + keyWords + "%" + "'";
		String hql2 = "from Paper as paper where paper.paperOwner='" + userName + "'"
				+ "and paperTitle like '" + "%" + keyWords + "%" + "'";
		page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);

		return page;
	}

}
