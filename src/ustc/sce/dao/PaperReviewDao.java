package ustc.sce.dao;

import ustc.sce.domain.PaperReview;
import ustc.sce.domain.User;

public interface PaperReviewDao {

	PaperReview submitPaper(int paperId, int teacherId);

}
