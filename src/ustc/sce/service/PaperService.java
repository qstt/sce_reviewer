package ustc.sce.service;

import javax.servlet.http.HttpServletRequest;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.PaperReview;

/**
 * 论文业务层接口
 * @author 秋色天堂
 *
 */
public interface PaperService {
	
	/**
	 * 创建论文
	 * @param paperTitle 论文题目
	 * @param paperAuthor 论文作者
	 * @param paperOwner 论文所有者
	 * @param ispublic 公开/私有
	 * @param fileId 关联文件id
	 * @return 论文信息
	 */
	PaperReview createPaper(String paperTitle, String paperAuthor, String paperOwner, boolean ispublic, int fileId);

	/**
	 * 给论文增加文件
	 * @param paperId 论文id
	 * @param fileId 文件id
	 * @return 文件信息
	 */
	FileEntity addPDF(int paperId, int fileId);

	/**
	 * 删除论文 将论文中所有文件都删除
	 * @param paperId 论文id
	 * @param request
	 * @return 论文删除成功/失败
	 */
	boolean paperDelete(int paperId, HttpServletRequest request);

	/**
	 * 论文列表
	 * @param currentPage  当前页面
	 * @param pageSize 每页记录条数
	 * @param ispublic 公开 1 /私有 0
	 * @return 当前页面信息
	 */
	Page getForPage(int currentPage, int pageSize, int ispublic);

	/**
	 * 根据论文题目进行查询
	 * @param keyWords 查询关键字
	 * @param pageNo 当前页面 默认为1
	 * @param pageSize 每页记录条数 默认为3
	 * @param ispublic 公开 1/私有 0
	 * @return 当前页面信息
	 */
	Page paperSearch(String keyWords, int currentPage, int pageSize, int ispublic);

	/**
	 * 保存论文
	 * @param paper 论文实体
	 * @return 论文信息
	 */
	PaperReview savePaper(Paper paper);


}
