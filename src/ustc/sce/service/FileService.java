package ustc.sce.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

/**
 * 文件业务层接口   保存文件  删除文件 根据文件id查询文件
 * @author 秋色天堂
 *
 */
public interface FileService {

	/**
	 * 保存文件
	 * @param fileUpload 文件实体
	 */
	void FileSave(FileEntity fileUpload);

	/**
	 * 删除文件
	 * @param fileId  文件id
	 * @param request
	 * @return 文件删除成功/失败
	 */
	boolean fileDelete(int fileId, HttpServletRequest request);
	
	/**
	 * 根据文件id查询文件
	 * @param fileId  文件id
	 * @return 数据库中查询到的文件实体
	 */
	FileEntity getFile(int fileId);
	
	/**
	 * 获取该用户当前页面数据
	 * @param currentPage  当前页面
	 * @param pageSize  页面显示记录条数
	 * @param user  用户
	 * @return  当前页面信息
	 */
	Page getForPage(int currentPage, int pageSize, User user);
	
	/**
	 * 不分页显示该用户上传的文件
	 * @param user 用户
	 * @return 该用户上传的所有文件信息
	 */
	List<FileEntity> fileNoPageList(User user);
	
	/**
	 * 分页显示所有文件列表
	 * @param currentPage 当前页面
	 * @param pageSize 页面显示记录条数
	 * @return  当前页面信息
	 */
	Page getForPage(int currentPage, int pageSize);
	
	/**
	 * 按文件名查找所有文件 分页显示
	 * @param currentPage 当前页面
	 * @param pageSize 每页显示记录条数
	 * @param keyWords 关键字
	 * @return  当前页面信息
	 */
	Page getForPage(int currentPage, int pageSize, String keyWords);
	
	/**
	 * 按文件名查找该用户上传文件  不用分页显示
	 * @param user 用户
	 * @param keyWords 关键字
	 * @return 当前页面信息
	 */
	List<FileEntity> fileNoPageList(User user, String keyWords);
	
	/**
	 * 根据文件名 获得该文件信息
	 * @param fileName 文件名
	 * @return 文件信息
	 */
	FileEntity getFile(String fileName);


}
