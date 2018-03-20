package ustc.sce.service;

import javax.servlet.http.HttpServletRequest;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.PaperReview;

public interface PaperService {

	FileEntity addPDF(int paperId, int fileId);

	PaperReview createPaper(String paperTitle, String paperAuthor, String paperOwner, boolean ispublic, int fileId);

	boolean paperDelete(int paperId, HttpServletRequest request);

}
