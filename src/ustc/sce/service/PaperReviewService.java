package ustc.sce.service;

import ustc.sce.domain.PaperReview;
import ustc.sce.domain.User;

public interface PaperReviewService {

	PaperReview notReview(int paperStatus, int paperId, User user);

	PaperReview changeReview(int paperStatus, String paperTitle);

}
