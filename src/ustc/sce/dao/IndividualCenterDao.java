package ustc.sce.dao;

import ustc.sce.domain.Page;
import ustc.sce.domain.User;

/**
 * 个人中心持久层接口   我的论文  我的批注  我的收藏
 * @author 秋色天堂
 *
 */
public interface IndividualCenterDao {

	/**
	 * 论文收藏  列表显示
	 * @param user 用户
	 * @param currentPage 当前页面
	 * @param pageSize 每页记录条数
	 * @return 当前页面论文信息
	 */
	Page getForPage(User user, int currentPage, int pageSize);

}
