package ustc.sce.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.PaperDao;
import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Paper;
import ustc.sce.domain.PaperReview;

@Transactional
public class PaperServiceImp implements PaperService {
	
	private PaperDao paperDao;
	public void setPaperDao(PaperDao paperDao) {
		this.paperDao = paperDao;
	}

	@Override
	public FileEntity addPDF(int paperId, int fileId) {
		return paperDao.addPDF(paperId,fileId);
	}

	@Override
	public PaperReview createPaper(String paperTitle, String paperAuthor, String paperOwner, boolean ispublic,
			int fileId) {
		return paperDao.createPaper(paperTitle, paperAuthor, paperOwner, ispublic, fileId);
	}

	@Override
	public boolean paperDelete(int paperId, HttpServletRequest request) {
		return paperDao.paperDelete(paperId,request);
	}

}
