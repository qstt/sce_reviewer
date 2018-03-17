package ustc.sce.service;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.PaperDao;
import ustc.sce.domain.Paper;

@Transactional
public class PaperServiceImp implements PaperService {
	
	private PaperDao paperDao;
	public void setPaperDao(PaperDao paperDao) {
		this.paperDao = paperDao;
	}

	public Paper create(String paperTitle, String paperAuthor, String paperOwner, boolean ispublic, int fileId) {
		return paperDao.createPaper(paperTitle,paperAuthor,paperOwner,ispublic,fileId);
	}

	@Override
	public Paper addPDF(int paperId, int fileId) {
		return paperDao.addPDF(paperId,fileId);
	}

}
