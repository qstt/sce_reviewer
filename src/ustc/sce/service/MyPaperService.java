package ustc.sce.service;

import ustc.sce.domain.Page;
import ustc.sce.domain.User;

public interface MyPaperService {

	Page myPpaperSearch(User user, String keyWords, int currentPage, int pageSize);

}
