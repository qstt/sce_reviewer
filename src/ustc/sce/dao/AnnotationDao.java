package ustc.sce.dao;

import java.util.List;

import ustc.sce.domain.Annotation;
import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

/**
 * 批注持久层接口
 * @author 秋色天堂
 *
 */
public interface AnnotationDao {

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

	/**
	 * 显示该文件该用户的所有批注
	 * @param user
	 * @param fileId
	 * @return
	 */
	List<Annotation> userAnnotationList(User user, int fileId);

	List<Annotation> userAnnotationSearch(User user, int fileId, String keyWords);

	List<FileEntity> myAnnotationList(User user);

	boolean myAnnotationDelete(User user, int fileId);

}
