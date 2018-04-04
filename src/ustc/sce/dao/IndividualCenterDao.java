package ustc.sce.dao;

import java.io.UnsupportedEncodingException;

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

	/**
	 * 取消收藏
	 * @param paperId 论文id
	 * @param request 获得用户
	 * @return 取消收藏成功/失败
	 */
	boolean cancelCollection(User user, int paperId);

	/**
	 * 收藏论文   根据论文题目进行查找
	 * @param keyWords 查找关键字
	 * @param pageNo 当前页面   默认为1
	 * @param pageSize 每页记录条数  默认为3
	 * @param request  获取用户
	 * @return List<Paper>
	 */
	Page searchCollection(User user, String keyWords, int currentPage, int pageSize);


}
