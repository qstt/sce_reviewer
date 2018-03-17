package ustc.sce.service;

import ustc.sce.domain.Collection;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

public interface PublicPaperService {

	Page getForPage(int currentPage, int pageSize);

	Page publicPaperSearch(String keyWords,int currentPage, int pageSize);

	Collection publicPaperCollect(User user, int collectPaperId);

}
