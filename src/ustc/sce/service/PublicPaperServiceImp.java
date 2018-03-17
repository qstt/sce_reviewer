package ustc.sce.service;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.PublicPaperDao;
import ustc.sce.domain.Collection;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

@Transactional
public class PublicPaperServiceImp implements PublicPaperService {
	
	private PublicPaperDao publicPaperDao;
	public void setPublicPaperDao(PublicPaperDao publicPaperDao) {
		this.publicPaperDao = publicPaperDao;
	}


	public Page getForPage(int currentPage, int pageSize) {
		return publicPaperDao.getForPage(currentPage,pageSize);
	}


	@Override
	public Page publicPaperSearch(String keyWords,int currentPage, int pageSize) {
		return publicPaperDao.publicPaperSearch(keyWords,currentPage,pageSize);
	}


	@Override
	public Collection publicPaperCollect(User user, int collectPaperId) {
		return publicPaperDao.publicPaperCollect(user,collectPaperId);
	}

}
