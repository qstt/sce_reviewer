package ustc.sce.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ustc.sce.domain.Annotation;
import ustc.sce.domain.FileEntity;

public class AnnotationDaoImp extends HibernateDaoSupport implements AnnotationDao {

	@Override
	public Annotation saveAnnotation(String annotationSelect, String annotationContent, int fileId) {

		Annotation annotation = new Annotation();

		annotation.setAnnotationSelect(annotationSelect);
		annotation.setAnnotationContent(annotationContent);

		String hql = "from FileEntity as file where file.id='" + fileId + "'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<FileEntity> list = query.list();
		FileEntity fileEntity = list.get(0);

		annotation.setFile(fileEntity);

		this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(annotation);

		return annotation;
	}

}
