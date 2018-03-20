package ustc.sce.service;

import javax.servlet.http.HttpServletRequest;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

public interface FileService {

	void FileSave(FileEntity fileUpload);

	boolean fileDelete(int fileId, HttpServletRequest request);

	FileEntity getFile(String fileName);

	FileEntity fielShow(int fileId);

	Page getForPage(int currentPage, int pageSize);

	FileEntity getFile(int fileId);

	Page getForPage(int currentPage, int pageSize, User user);
	
	

}
