package ustc.sce.service;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.PaperReviewDao;
import ustc.sce.domain.PaperReview;
import ustc.sce.domain.User;

@Transactional
public class PaperReviewServiceImp implements PaperReviewService {

	private PaperReviewDao paperReviewDao;
	public void setPaperReviewDao(PaperReviewDao paperReviewDao) {
		this.paperReviewDao = paperReviewDao;
	}

	public PaperReview submitPaper(int paperId, int teacherId) {
		return paperReviewDao.submitPaper(paperId,teacherId);
	}

}
