package ustc.sce.service;

import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.User;

/**
 * 公开论文业务层接口   列表  查找  收藏
 * @author 秋色天堂
 *
 */
public interface PublicPaperService {


	/**
	 * 公开论文 根据论文题目进行查找
	 * @param keyWords 查找关键字
	 * @param currentPage 当前页面
	 * @param pageSize 每页记录条数
	 * @return List<Paper>
	 */
	Page publicPaperSearch(String keyWords,int currentPage, int pageSize);

	/**
	 * 收藏公开论文
	 * @param paperId 收藏论文id
	 * @param request
	 * @return 论文信息
	 */
	Paper publicPaperCollect(User user, int paperId);

}
