package ustc.sce.dao;


import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.Annotation;
import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;
import ustc.sce.utils.PageUtil;

/**
 * 批注持久层
 * @author 秋色天堂
 *
 */
public class AnnotationDaoImp extends HibernateDaoSupport implements AnnotationDao {
	
	private PageUtil pageUtil;
	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	/**
	 * 保存批注
	 */
	public Annotation saveAnnotation(Annotation annotation) {
		
		int fileId = annotation.getFile().getId();
		
		String hql="from FileEntity as file where file.id='"+fileId+"'";
        List<FileEntity> list = (List<FileEntity>) this.getHibernateTemplate().find(hql);
        FileEntity fileEntity = list.get(0);
        annotation.setFile(fileEntity);
        
		this.getHibernateTemplate().save(annotation);
		return annotation;
	}

	/**
	 * 删除该用户批注
	 */
	public boolean deleteAnnotation(User user, int annotationId) {
		
		String userName = user.getUserName();
		String hql="from Annotation where id='"+annotationId+"'"+"and annotator='"+userName+"'";
		List<Annotation> list = (List<Annotation>) this.getHibernateTemplate().find(hql);
		Annotation annotation = list.get(0);
		this.getHibernateTemplate().delete(annotation);
		
		List<Annotation> list1 = (List<Annotation>) this.getHibernateTemplate().find(hql);
		
		if(list1.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 分页显示该用户批注
	 */
	public Page getForPage(User user, int currentPage, int pageSize) {
		
		String userName = user.getUserName();
		String hql1 = "SELECT COUNT(*) from Annotation where annotator='"+userName+"'";
		String hql2="from Annotation where annotator='"+userName+"'";
		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}

	/**
	 * 分页显示该用户批注查找
	 */
	public Page userAnnotationSearch(User user, String keyWords1, int currentPage, int pageSize) {
		String userName = user.getUserName();
		String hql1 = "SELECT COUNT(*) from Annotation where annotator='"+userName+"'" +"and annotationSelect like '"+"%" +keyWords1+"%"+"'";
		String hql2="from Annotation where annotator='"+userName+"'"+"and annotationSelect like '"+"%" +keyWords1+"%"+"'";
		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}

	/**
	 * 不分页显示改文件所有批注
	 */
	public List<Annotation> fileAnnotationList(int fileId) {

		String hql="from Annotation as anno inner join fetch anno.file as f where f.id='"+fileId+"'";
		List<Annotation> list = (List<Annotation>) this.getHibernateTemplate().find(hql);
		
		return list;
	}

	/**
	 * 不分页查找该文件中的批注
	 */
	public List<Annotation> fileAnnotationSearch(int fileId, String keyWords) {
		String hql="from Annotation as anno inner join fetch anno.file as f where f.id='"+fileId+"'" +"and annotationSelect like '" + "%"+keyWords+"%" +"'";
		List<Annotation> list = (List<Annotation>) this.getHibernateTemplate().find(hql);
		
		return list;
	}

}
