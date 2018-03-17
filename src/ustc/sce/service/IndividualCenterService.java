package ustc.sce.service;

import ustc.sce.domain.Page;
import ustc.sce.domain.User;

public interface IndividualCenterService {

	Page getForPage(User user, int currentPage, int pageSize);

}
