package ustc.sce.dao;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.Paper;
import ustc.sce.domain.PaperReview;
import ustc.sce.utils.PageUtil;

/**
 * 论文持久层 创建论文
 * 
 * @author 秋色天堂
 *
 */
public class PaperDaoImp extends HibernateDaoSupport implements PaperDao {

	private PageUtil pageUtil;

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}


	/**
	 * 创建论文
	 */
	public PaperReview createPaper(Paper paper, Integer fileId) {
		PaperReview paperReview = new PaperReview();
		FileEntity fileEntity = new FileEntity();

		// 将论文关联到文件中
		if (fileId != -1) {
			String hql = "from FileEntity as file where file.id='" + fileId + "'";
			List<FileEntity> list = (List<FileEntity>) getHibernateTemplate().find(hql);
			if( !list.isEmpty()) {
				fileEntity = list.get(0);
				fileEntity.setPaper(paper);
				this.getHibernateTemplate().update(fileEntity);
				paper.getFileEntitys().add(fileEntity);
			}
		}

		this.getHibernateTemplate().save(paper);
		
		// 将论文关联到论文评阅中
		paperReview.setPaperStatus(0);
		paperReview.setTeacherStatus(0);
		paperReview.setPaper(paper);
		this.getHibernateTemplate().save(paperReview);

		return paperReview;
	}
	
	/**
	 * 给论文增加文件
	 */
	public Paper addPDF(int paperId, int fileId) {
		String hql = "from Paper as paper where paper.id='" + paperId + "'";
		List<Paper> list = (List<Paper>) getHibernateTemplate().find(hql);

		String hql1 = "from FileEntity as file where file.id='" + fileId + "'";
		List<FileEntity> list1 = (List<FileEntity>) getHibernateTemplate().find(hql1);

		if (list.isEmpty()) {
			return null;
		}
		if (list1.isEmpty()) {
			return null;
		}

		Paper paper = list.get(0);
		FileEntity fileEntity = list1.get(0);

		fileEntity.setPaper(paper);
		paper.getFileEntitys().add(fileEntity);

		this.getHibernateTemplate().update(fileEntity);
		this.getHibernateTemplate().update(paper);

		return paper;

	}

	/**
	 * 删除论文 将论文中所有文件都删除
	 */
	// 删除论文 方法有待优化
	public boolean paperDelete(int paperId, HttpServletRequest request) {

		FileEntity fileEntity = new FileEntity();

		String hql = "from FileEntity as file inner join fetch file.paper as p where p.id='" + paperId + "'";
		List<FileEntity> list = (List<FileEntity>) getHibernateTemplate().find(hql);

		// 删除文件
		for (int i = 0; i < list.size(); i++) {
			fileEntity = list.get(i);
			String path = fileEntity.getFilePath();
			// path只是文件存储路径的后半部分 加上前面的才是完整的路径
			path = request.getSession().getServletContext().getRealPath("\\") + path; // 相对路径用
			File file = new File(path);
			this.getHibernateTemplate().delete(fileEntity);
			if (file.exists()) {
				file.delete();
			}
		}

		// 删除状态 paperReview
		String hql3 = "from PaperReview as review inner join fetch review.paper as p where p.id='" + paperId + "'";
		List<PaperReview> list3 = (List<PaperReview>) getHibernateTemplate().find(hql3);
		PaperReview paperReview = list3.get(0);
		this.getHibernateTemplate().delete(paperReview);

		// 删除论文
		String hql1 = "from Paper as paper where paper.id='" + paperId + "'";
		List<Paper> list1 = (List<Paper>) getHibernateTemplate().find(hql1);
		Paper paper = list1.get(0);

		this.getHibernateTemplate().delete(paper);

		// 查询数据库是否正确将paper删除
		String hql2 = "from Paper as paper where paper.id='" + paperId + "'";
		List<Paper> list2 = (List<Paper>) getHibernateTemplate().find(hql2);

		if (list2.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 获取该论文所有文件
	 */
	public List<FileEntity> paperFile(int paperId) {
		String hql = "from FileEntity as file inner join fetch file.paper as p where p.id='" + paperId + "'" +"order by file.createTime desc";
		List<FileEntity> list = (List<FileEntity>) getHibernateTemplate().find(hql);
		
		if(list.isEmpty()) {
			return null;
		}
		return list;
	}



	

}
