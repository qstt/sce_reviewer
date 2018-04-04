package ustc.sce.domain;

/**
 * 批注  存放选中的字段、批注内容、批注者
 * @author 秋色天堂
 *
 */
public class Annotation {
	
	private int id;
	//选中字段的内容
	private String annotationSelect;
	//批注、评论的内容
	private String annotationContent;
	//批注者
	private String annotator;
	
	//关联pdf文件   文件和批注是一对多的关系 批注是多方只在多方进行关联
	private FileEntity file;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnnotationSelect() {
		return annotationSelect;
	}

	public void setAnnotationSelect(String annotationSelect) {
		this.annotationSelect = annotationSelect;
	}

	public String getAnnotationContent() {
		return annotationContent;
	}

	public void setAnnotationContent(String annotationContent) {
		this.annotationContent = annotationContent;
	}

	public String getAnnotator() {
		return annotator;
	}

	public void setAnnotator(String annotator) {
		this.annotator = annotator;
	}

	public FileEntity getFile() {
		return file;
	}

	public void setFile(FileEntity file) {
		this.file = file;
	}
	
	


}
