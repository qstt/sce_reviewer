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
	public Page myPpaperSearch(User user, String keyWords, int currentPage, int pageSize) {
		Page page = new Page();
		String userName = user.getUserName();
		//列表
		String hql1 = "SELECT COUNT(*) FROM Paper where paperOwner ='" + userName + "'";
		String hql2 = "from Paper where paperOwner ='" + userName + "'";
		//查找
		String hql3 = "SELECT COUNT(*) from Paper as paper where paper.paperOwner='" + userName + "'"
				+ "and paperTitle like '" + "%" + keyWords + "%" + "'";
		String hql4 = "from Paper as paper where paper.paperOwner='" + userName + "'"
				+ "and paperTitle like '" + "%" + keyWords + "%" + "'";
		
		if(keyWords == null) {
			page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		}else {
			page = pageUtil.getForPage(hql3, hql4, currentPage, pageSize);
		}

		return page;
	}

}
