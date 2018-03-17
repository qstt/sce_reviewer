package ustc.sce.dao;

import ustc.sce.domain.Annotation;

public interface AnnotationDao {

	Annotation saveAnnotation(String annotationSelect, String annotationContent, int fileId);

}
