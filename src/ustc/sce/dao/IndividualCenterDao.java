package ustc.sce.dao;

import ustc.sce.domain.Page;
import ustc.sce.domain.User;

public interface IndividualCenterDao {

	Page getForPage(User user, int currentPage, int pageSize);

}
