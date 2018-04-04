package ustc.sce.service;

import ustc.sce.domain.Page;
import ustc.sce.domain.User;

public interface MyPaperService {

	Page myPaperList(User user, int currentPage, int pageSize);

	Page myPpaperSearch(User user, String keyWords, int currentPage, int pageSize);

}
