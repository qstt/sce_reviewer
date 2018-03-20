package ustc.sce.dao;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Paper;
import ustc.sce.domain.PaperReview;

public class PaperDaoImp extends HibernateDaoSupport implements PaperDao {

	@Override
	public PaperReview createPaper(String paperTitle, String paperAuthor, String paperOwner, boolean ispublic,
			int fileId) {
		
		Paper paper = new Paper();
		FileEntity fileEntity = new FileEntity();
		PaperReview paperReview = new PaperReview();
		
		//保存论文
		paper.setPaperTitle(paperTitle);
		paper.setPaperAuthor(paperAuthor);
		paper.setPaperOwner(paperOwner);
		paper.setIspublic(ispublic);
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(paper);
		
		//将论文关联到文件中
		if(fileId != -1) {
			String hql="from FileEntity as file where file.id='"+fileId+"'";
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
	        Query query =session.createQuery(hql);
	        List<FileEntity> list = query.list();
	        fileEntity = list.get(0);
	        fileEntity.setPaper(paper);
	        this.getHibernateTemplate().getSessionFactory().getCurrentSession().update(fileEntity);
		}
		
		//将论文关联到论文评阅中
		paperReview.setPaperStatus(0);
		paperReview.setPaper(paper);
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(paperReview);
        
		return paperReview;
	}

	public FileEntity addPDF(int paperId, int fileId) {
		String hql="from Paper as paper where paper.id='"+paperId+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<Paper> list = query.list();
        
        String hql1="from FileEntity as file where file.id='"+fileId+"'";
		Session session1 = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query1 =session1.createQuery(hql1);
        List<FileEntity> list1 = query1.list();
        
        if(list.isEmpty()) {
        	return null;
        }
        if(list1.isEmpty()) {
    		return null;
    	}
        
        Paper paper = list.get(0);
        FileEntity fileEntity = list1.get(0);

        fileEntity.setPaper(paper);
    	
    	this.getHibernateTemplate().getSessionFactory().getCurrentSession().update(fileEntity);
    	
    	return fileEntity;
		
	}

	//删除论文    方法有待优化
	public boolean paperDelete(int paperId, HttpServletRequest request) {
		
		FileEntity fileEntity = new FileEntity();
		
		String hql = "from FileEntity as file inner join fetch file.paper as p where p.id='"+paperId+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<FileEntity> list = query.list();
        
        //删除文件
        for(int i = 0;i < list.size();i ++) {
        	fileEntity = list.get(i);
        	String path = fileEntity.getFilePath();
        	//path只是文件存储路径的后半部分  加上前面的才是完整的路径
        	path = request.getSession().getServletContext().getRealPath("\\") + path;  //相对路径用
        	File file = new File(path);
        	this.getHibernateTemplate().getSessionFactory().getCurrentSession().delete(fileEntity);
        	if(file.exists()){
    			file.delete();
    		}
        }
		
        //删除状态 paperReview
        String hql3 = "from PaperReview as review inner join fetch review.paper as p where p.id='"+paperId+"'";
		Session session3 = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query3 =session3.createQuery(hql3);
        List<PaperReview> list3 = query3.list();
        PaperReview paperReview = list3.get(0);
        this.getHibernateTemplate().getSessionFactory().getCurrentSession().delete(paperReview);
        
        //删除论文
        String hql1="from Paper as paper where paper.id='"+paperId+"'";
		Session session1 = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query1 =session1.createQuery(hql1);
        List<Paper> list1 = query1.list();
        Paper paper = list1.get(0);
       
        this.getHibernateTemplate().getSessionFactory().getCurrentSession().delete(paper);
        
        //查询数据库是否正确将paper删除
        String hql2="from Paper as paper where paper.id='"+paperId+"'";
		Session session2 = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query2 =session2.createQuery(hql2);
        List<Paper> list2 = query2.list();
        
        if(list2.isEmpty()) {
        	return true;
        }else {
        	 return false;
        }
       
	}


}
