package ustc.sce.service;

import ustc.sce.domain.Paper;

public interface PaperService {

	Paper create(String paperTitle, String paperAuthor, String paperOwner, boolean ispublic, int fileId);

	Paper addPDF(int paperId, int fileId);

}
