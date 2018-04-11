package ustc.sce.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.FileDao;
import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

/**
 * 文件业务层  保存文件   删除文件  根据文件id查询文件
 * @author 秋色天堂
 *
 */
@Transactional
public class FileServiceImp implements FileService {
	
	private FileDao fileDao;
	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	/**
	 * 保存文件
	 */
	public void FileSave(FileEntity fileUpload) {
		fileDao.FileSave(fileUpload);
	}

	/**
	 * 删除文件
	 */
	public boolean fileDelete(int fileId,HttpServletRequest request) {
		return fileDao.fileDelete(fileId,request);
	}
	
	/**
	 * 根据文件id查询文件
	 */
	public FileEntity getFile(int fileId) {
		return fileDao.getFile(fileId);
	}
	
	/**
	 * 分页显示该用户上传的文件
	 */
	public Page getForPage(String keyWords,int currentPage, int pageSize, User user) {
		return fileDao.getForPage(keyWords,currentPage, pageSize,user);
	}
	
	/**
	 * 不分页显示该用户上传的文件
	 */
	public List<FileEntity> fileNoPageList(User user) {
		return fileDao.fileNoPageList(user);
	}
	
	/**
	 * 分页显示所有文件列表
	 */
	public Page getForPage(int currentPage, int pageSize) {
		return fileDao.getForPage(currentPage, pageSize);
	}
	
	/**
	 * 按文件名查找所有文件 分页显示
	 */
	public Page getForPage(int currentPage, int pageSize, String keyWords) {
		return fileDao.getForPage(currentPage, pageSize,keyWords);
	}
	
	/**
	 * 按文件名查找该用户上传文件  不用分页显示
	 */
	public List<FileEntity> fileNoPageList(User user, String keyWords) {
		return fileDao.fileNoPageList(user,keyWords);
	}

	/**
	 * 根据文件名 获得该文件信息
	 */
	public FileEntity getFile(String fileName) {
		return fileDao.getFile(fileName);
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


}
