package ustc.sce.service;

import java.util.List;

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
	 * @param paper 论文实体
	 * @param request 获得用户
	 * @param fileId 所关联文件id
	 * @return 论文信息
	 */
	PaperReview createPaper(Paper paper, Integer fileId);
	
	/**
	 * 给论文增加文件
	 * @param paperId 论文id
	 * @param fileId 文件id
	 * @return 文件信息
	 */
	Paper addPDF(int paperId, int fileId);

	/**
	 * 删除论文 将论文中所有文件都删除
	 * @param paperId 论文id
	 * @param request
	 * @return 论文删除成功/失败
	 */
	boolean paperDelete(int paperId, HttpServletRequest request);

	/**
	 * 获取该论文所有文件
	 * @param paperId 论文id
	 * @return List<FileEntity>
	 */
	List<FileEntity> paperFile(int paperId);



}
