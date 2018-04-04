package ustc.sce.service;

import java.util.List;

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
	public PaperReview createPaper(Paper paper, Integer fileId) {
		return paperDao.createPaper(paper,fileId);
	}
	
	/**
	 * 给论文增加文件
	 */
	public Paper addPDF(int paperId, int fileId) {
		return paperDao.addPDF(paperId,fileId);
	}

	/**
	 * 删除论文 将论文中所有文件都删除
	 */
	public boolean paperDelete(int paperId, HttpServletRequest request) {
		return paperDao.paperDelete(paperId,request);
	}

	/**
	 * 获取该论文所有文件
	 */
	public List<FileEntity> paperFile(int paperId) {
		return paperDao.paperFile(paperId);
	}


	

}
