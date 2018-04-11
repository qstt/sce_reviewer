package ustc.sce.dao;

import ustc.sce.domain.Page;
import ustc.sce.domain.User;

public interface MyPaperDao {

	Page myPpaperSearch(User user, String keyWords, int currentPage, int pageSize);

}
