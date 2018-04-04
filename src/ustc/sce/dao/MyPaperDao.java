package ustc.sce.dao;

import ustc.sce.domain.Page;
import ustc.sce.domain.User;

public interface MyPaperDao {

	Page myPaperList(User user, int currentPage, int pageSize);

	Page myPpaperSearch(User user, String keyWords, int currentPage, int pageSize);

}
