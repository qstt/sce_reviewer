package ustc.sce.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ustc.sce.dao.AnnotationDao;
import ustc.sce.domain.Annotation;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;

/**
 * 批注业务层
 * @author 秋色天堂
 *
 */
@Transactional
public class AnnotationServiceImp implements AnnotationService {
	
	private AnnotationDao annotationDao;
	public void setAnnotationDao(AnnotationDao annotationDao) {
		this.annotationDao = annotationDao;
	}

	/**
	 * 保存批注
	 */
	public Annotation saveAnnotation(Annotation annotation) {
		return annotationDao.saveAnnotation(annotation);
	}

	/**
	 * 删除该用户的批注
	 */
	public boolean deleteAnnotation(User user, int annotationId) {
		return annotationDao.deleteAnnotation(user,annotationId);
	}

	/**
	 * 分页显示该用户批注
	 */
	public Page getForPage(User user, int currentPage, int pageSize) {
		return annotationDao.getForPage(user,currentPage,pageSize);
	}

	/**
	 * 分页显示该用户批注查找
	 */
	public Page userAnnotationSearch(User user, String keyWords1, int currentPage, int pageSize) {
		return annotationDao.userAnnotationSearch(user,keyWords1,currentPage,pageSize);
	}

	/**
	 * 不分页显示改文件所有批注
	 */
	public List<Annotation> fileAnnotationList(int fileId) {
		return annotationDao.fileAnnotationList(fileId);
	}

	/**
	 * 不分页查找该文件中的批注
	 */
	public List<Annotation> fileAnnotationSearch(int fileId, String keyWords) {
		return annotationDao.fileAnnotationSearch(fileId,keyWords);
	}

}
