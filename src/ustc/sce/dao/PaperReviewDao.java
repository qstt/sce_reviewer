package ustc.sce.dao;

import ustc.sce.domain.PaperReview;
import ustc.sce.domain.User;

public interface PaperReviewDao {

	PaperReview notReview(int paperStatus, int paperId, User user);

	PaperReview changeReview(int paperStatus, String paperTitle);

}
