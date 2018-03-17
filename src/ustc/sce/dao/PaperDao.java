package ustc.sce.dao;

import ustc.sce.domain.Paper;

public interface PaperDao {

	Paper createPaper(String paperTitle, String paperAuthor, String paperOwner, boolean ispublic, int fileId);

	Paper addPDF(int paperId, int fileId);

}
