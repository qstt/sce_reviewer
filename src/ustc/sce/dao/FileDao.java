package ustc.sce.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

public interface FileDao {

	void FileSave(FileEntity fileUpload);

	boolean fileDelete(int fileId, HttpServletRequest request);

	FileEntity getFile(String fileName);

	FileEntity fileShow(int fileId);

	Page getForPage(int currentPage, int pageSize);

	FileEntity getFile(int fileId);

	Page getForPage(int currentPage, int pageSize, User user);

//	Long getAllRowCount();
//
//	List<FileEntity> getForPage(int offset, int pageSize);

}
