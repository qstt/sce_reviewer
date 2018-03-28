package ustc.sce.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import ustc.sce.domain.Annotation;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

/**
 * 批注业务层接口
 * @author 秋色天堂
 *
 */
public interface AnnotationService {

	/**
	 * 保存批注
	 * @param annotation
	 * @return 批注信息
	 */
	Annotation saveAnnotation(Annotation annotation);

	/**
	 * 删除该用户的批注
	 * @param annotationId 批注id
	 * @param request 获取该用户
	 * @return 删除成功/失败
	 */
	boolean deleteAnnotation(User user, int annotationId);

	/**
	 * 分页显示该用户批注
	 * @param pageNo 当前页面 默认1
	 * @param pageSize 每页显示记录条数  默认3
	 * @param request 获得该用户请求
	 * @return List<Annotation>
	 */
	Page getForPage(User user, int currentPage, int pageSize);

	/**
	 * 分页显示该用户批注查找
	 * @param keyWords 查找关键字
	 * @param pageNo 当前页面  默认1
	 * @param pageSize 每页显示记录条数 默认3
	 * @param request 获取用户
	 * @return List<Annotation>
	 */
	Page userAnnotationSearch(User user, String keyWords1, int currentPage, int pageSize);

	/**
	 * 不分页显示该文件所有批注
	 * @param fileId 文件id
	 * @return List<Annotation>
	 */
	List<Annotation> fileAnnotationList(int fileId);

	/**
	 * 不分页查找该文件中的批注
	 * @param fileId 文件id
	 * @param keyWords 查找关键字
	 * @return List<Annotation>
	 */
	List<Annotation> fileAnnotationSearch(int fileId, String keyWords);

}
