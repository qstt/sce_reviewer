package ustc.sce.service;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.IndividualCenterDao;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

@Transactional
public class IndividualCenterServiceImp implements IndividualCenterService {
	
	private IndividualCenterDao individualCenterDao;
	public void setIndividualCenterDao(IndividualCenterDao individualCenterDao) {
		this.individualCenterDao = individualCenterDao;
	}

	public Page getForPage(User user, int currentPage, int pageSize) {
		return individualCenterDao.getForPage(user,currentPage, pageSize);
	}

}
