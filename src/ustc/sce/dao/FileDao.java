package ustc.sce.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;

public interface FileDao {

	void FileSave(FileEntity fileUpload);

	boolean fileDelete(int fileId, HttpServletRequest request);

	boolean fileDelete(String fileName);

	List<FileEntity> fileList();

	String fileShow(String fileName);

	boolean fileDelete(FileEntity fileEntity);

	FileEntity getFile(String fileName);

	FileEntity fileShow(int fileId);

	Page getForPage(int currentPage, int pageSize);

	FileEntity getFile(int fileId);

//	Long getAllRowCount();
//
//	List<FileEntity> getForPage(int offset, int pageSize);

}
