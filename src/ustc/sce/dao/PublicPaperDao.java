package ustc.sce.dao;

import ustc.sce.domain.Collection;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

public interface PublicPaperDao {

	Page getForPage(int currentPage, int pageSize);

	Page publicPaperSearch(String keyWords, int currentPage, int pageSize);

	Collection publicPaperCollect(User user, int collectPaperId);

}
