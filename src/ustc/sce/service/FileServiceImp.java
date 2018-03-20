package ustc.sce.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.FileDao;
import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;

@Transactional
public class FileServiceImp implements FileService {
	
	private FileDao fileDao;
	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}


	public void FileSave(FileEntity fileUpload) {

		fileDao.FileSave(fileUpload);
	}


	public boolean fileDelete(FileEntity fileEntity) {
		return fileDao.fileDelete(fileEntity);
	}


	public boolean fileDelete(String fileName) {
		return fileDao.fileDelete(fileName);
	}

	@Override
	public boolean fileDelete(int fileId,HttpServletRequest request) {
		return fileDao.fileDelete(fileId,request);
	}

	@Override
	public List<FileEntity> fileList() {
		
		return fileDao.fileList();
	}


	@Override
	public String fielShow(String fileName) {
		
		return fileDao.fileShow(fileName);
	}


	@Override
	public FileEntity getFile(String fileName) {
		return fileDao.getFile(fileName);
	}


	@Override
	public FileEntity fielShow(int fileId) {
		return fileDao.fileShow(fileId);
	}


//	@Override
//	public Page getForPage(int currentPage, int pageSize) {
//		
//		Page page = new Page();
//		int allRow = fileDao.getAllRowCount().intValue();
//		int offset = page.countOffset(currentPage, pageSize);
//		List<FileEntity> list = fileDao.getForPage(offset,pageSize);
//		page.setPageNo(currentPage);
//        page.setPageSize(pageSize);
//        page.setTotalRecords(allRow);
//        page.setList(list);
//        return page;
//        
//	}

	public Page getForPage(int currentPage, int pageSize) {
		return fileDao.getForPage(currentPage, pageSize);
	}


	@Override
	public FileEntity getFile(int fileId) {
		return fileDao.getFile(fileId);
	}
	

}
