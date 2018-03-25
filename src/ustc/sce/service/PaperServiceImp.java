package ustc.sce.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.PaperDao;
import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.PaperReview;

/**
 * 论文业务层
 * @author 秋色天堂
 *
 */
@Transactional
public class PaperServiceImp implements PaperService {
	
	private PaperDao paperDao;
	public void setPaperDao(PaperDao paperDao) {
		this.paperDao = paperDao;
	}

	/**
	 * 创建论文
	 */
	public PaperReview createPaper(String paperTitle, String paperAuthor, String paperOwner, boolean ispublic,
			int fileId) {
		return paperDao.createPaper(paperTitle, paperAuthor, paperOwner, ispublic, fileId);
	}
	
	/**
	 * 给论文增加文件
	 */
	public FileEntity addPDF(int paperId, int fileId) {
		return paperDao.addPDF(paperId,fileId);
	}

	/**
	 * 删除论文 将论文中所有文件都删除
	 */
	public boolean paperDelete(int paperId, HttpServletRequest request) {
		return paperDao.paperDelete(paperId,request);
	}

	/**
	 * 论文列表
	 */
	public Page getForPage(int currentPage, int pageSize, int ispublic) {
		return paperDao.getForPage(currentPage,pageSize,ispublic);
	}

	/**
	 * 根据论文题目进行查询
	 */
	public Page paperSearch(String keyWords, int currentPage, int pageSize, int ispublic) {
		return paperDao.paperSearch(keyWords,currentPage,pageSize,ispublic);
	}

	/**
	 * 保存论文
	 */
	public PaperReview savePaper(Paper paper) {
		return paperDao.savePaper(paper);
	}

}
