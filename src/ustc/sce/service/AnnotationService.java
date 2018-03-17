package ustc.sce.service;

import ustc.sce.domain.Annotation;

public interface AnnotationService {

	Annotation saveAnnotation(String annotationSelect, String annotationContent, int fileId);

}
