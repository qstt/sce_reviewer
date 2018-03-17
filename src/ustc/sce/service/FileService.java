package ustc.sce.service;

import java.util.List;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;

public interface FileService {

	void FileSave(FileEntity fileUpload);

	boolean fileDelete(int fileId);

	boolean fileDelete(String fileName);

	List<FileEntity> fileList();

	String fielShow(String fileName);

	boolean fileDelete(FileEntity fileEntity);

	FileEntity getFile(String fileName);

	FileEntity fielShow(int fileId);

	Page getForPage(int currentPage, int pageSize);
	
	

}
