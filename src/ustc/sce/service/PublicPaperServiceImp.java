package ustc.sce.service;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.PublicPaperDao;
import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.User;

@Transactional
public class PublicPaperServiceImp implements PublicPaperService {
	
	private PublicPaperDao publicPaperDao;
	public void setPublicPaperDao(PublicPaperDao publicPaperDao) {
		this.publicPaperDao = publicPaperDao;
	}

	/**
	 * 公开论文列表
	 */
	public Page getForPage(int currentPage, int pageSize) {
		return publicPaperDao.getForPage(currentPage,pageSize);
	}

	/**
	 * 公开论文 根据论文题目进行查找
	 */
	public Page publicPaperSearch(String keyWords,int currentPage, int pageSize) {
		return publicPaperDao.publicPaperSearch(keyWords,currentPage,pageSize);
	}


	/**
	 * 收藏公开论文
	 */
	public Paper publicPaperCollect(User user, int paperId) {
		return publicPaperDao.publicPaperCollect(user,paperId);
	}

}
