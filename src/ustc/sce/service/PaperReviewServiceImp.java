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


	public PaperReview notReview(int paperStatus, int paperId,User user) {
		
		PaperReview paperReview = paperReviewDao.notReview(paperStatus,paperId,user);
		
		return paperReview;
	}


	public PaperReview changeReview(int paperStatus, String paperTitle) {

		PaperReview paperReview = paperReviewDao.changeReview(paperStatus,paperTitle);
		
		return paperReview;
	}

}
