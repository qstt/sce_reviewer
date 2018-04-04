package ustc.sce.service;

import java.util.List;

import ustc.sce.domain.PaperReview;
import ustc.sce.domain.User;

public interface PaperReviewService {

	PaperReview submitPaper(int paperId, int teacherId);

	List<User> getTeachers();

	PaperReview reviewing(int paperId, int teacherStatus);

	PaperReview finalVersion(int paperId);

}
