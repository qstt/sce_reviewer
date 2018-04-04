package ustc.sce.service;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.MyPaperDao;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

@Transactional
public class MyPaperServiceImp implements MyPaperService {
	
	private MyPaperDao myPaperDao;
	public void setMyPaperDao(MyPaperDao myPaperDao) {
		this.myPaperDao = myPaperDao;
	}


	public Page myPaperList(User user, int currentPage, int pageSize) {
		return myPaperDao.myPaperList(user,currentPage,pageSize);
	}


	@Override
	public Page myPpaperSearch(User user, String keyWords, int currentPage, int pageSize) {
		return myPaperDao.myPpaperSearch(user,keyWords,currentPage,pageSize);
	}

}
