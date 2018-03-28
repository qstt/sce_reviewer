package ustc.sce.service;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.IndividualCenterDao;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

/**
 * 个人中心业务层   我的论文  我的批注  我的收藏
 * @author 秋色天堂
 *
 */
@Transactional
public class IndividualCenterServiceImp implements IndividualCenterService {
	
	private IndividualCenterDao individualCenterDao;
	public void setIndividualCenterDao(IndividualCenterDao individualCenterDao) {
		this.individualCenterDao = individualCenterDao;
	}

	/**
	 * 论文收藏  列表显示
	 */
	public Page getForPage(User user, int currentPage, int pageSize) {
		return individualCenterDao.getForPage(user,currentPage, pageSize);
	}

	/**
	 * 取消收藏
	 */
	public boolean cancelCollection(User user, int paperId) {
		return individualCenterDao.cancelCollection(user,paperId);
	}

	/**
	 * 收藏论文   根据论文题目进行查找
	 */
	public Page searchCollection(User user, String keyWords, int currentPage, int pageSize) {
		return individualCenterDao.searchCollection(user,keyWords,currentPage, pageSize);
	}

}
