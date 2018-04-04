package ustc.sce.service;

import ustc.sce.domain.PaperReview;

public interface PaperReviewService {

	PaperReview submitPaper(int paperId, int teacherId);

}
