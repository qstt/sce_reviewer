package ustc.sce.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Paper;

public class PaperDaoImp extends HibernateDaoSupport implements PaperDao {

	@Override
	public Paper createPaper(String paperTitle, String paperAuthor, String paperOwner, boolean ispublic,
			int fileId) {
		
		Paper paper = new Paper();
		FileEntity fileEntity = new FileEntity();
		
		paper.setPaperTitle(paperTitle);
		paper.setPaperAuthor(paperAuthor);
		paper.setPaperOwner(paperOwner);
		paper.setIspublic(ispublic);
		
		
		
		if(fileId != -1) {
			String hql="from FileEntity as file where file.id='"+fileId+"'";
			Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
	        Query query =session.createQuery(hql);
	        List<FileEntity> list = query.list();
	        fileEntity = list.get(0);
			
	       // fileEntity.getPapers().add(paper);
	     //   this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(fileEntity);
		}
		
		paper.setFileEntity(fileEntity);
		fileEntity.getPapers().add(paper);
		
		
		
		
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(fileEntity);
        return paper;
	}

	@Override
	public Paper addPDF(int paperId, int fileId) {
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

        fileEntity.getPapers().add(paper);
    	
    	
    	this.getHibernateTemplate().getSessionFactory().getCurrentSession().update(fileEntity);
    	
    	return paper;
		
	}

}
