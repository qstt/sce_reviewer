package ustc.sce.service;

import java.util.List;

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

	@Override
	public List<User> getTeachers() {
		return paperReviewDao.getTeachers();
	}

	@Override
	public PaperReview reviewing(int paperId, int teacherStatus) {
		return paperReviewDao.reviewing(paperId,teacherStatus);
	}

	@Override
	public PaperReview finalVersion(int paperId) {
		return paperReviewDao.finalVersion(paperId);
	}

}
