package ustc.sce.dao;

import javax.servlet.http.HttpServletRequest;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.PaperReview;

public interface PaperDao {

	FileEntity addPDF(int paperId, int fileId);

	PaperReview createPaper(String paperTitle, String paperAuthor, String paperOwner, boolean ispublic, int fileId);

	boolean paperDelete(int paperId, HttpServletRequest request);

}
