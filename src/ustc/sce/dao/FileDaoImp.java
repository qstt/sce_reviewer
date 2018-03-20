package ustc.sce.dao;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import ustc.sce.domain.FileEntity;
import ustc.sce.domain.Page;
import ustc.sce.domain.User;
import ustc.sce.utils.PageUtil;

public class FileDaoImp extends HibernateDaoSupport implements FileDao {
	
	private PageUtil pageUtil;
	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	public void FileSave(FileEntity fileUpload) {
//		this.getHibernateTemplate().save(fileUpload);  运行时会报错说找不到sessionFaction  不知道为什么会报错？？？
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(fileUpload);
	}

	public boolean fileDelete(int fileId,HttpServletRequest request) {
		String hql="from FileEntity as file where file.id='"+fileId+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<FileEntity> list = query.list();
        if(!list.isEmpty()){
        	FileEntity fileEntity = list.get(0);
        	String path = fileEntity.getFilePath();
        	//path只是文件存储路径的后半部分  加上前面的才是完整的路径
        	//path = "J:\\eclipse\\apache-tomacat-7.0.47\\webapps\\upload\\" + path;   //绝对路径用
        	path = request.getSession().getServletContext().getRealPath("\\") + path;  //相对路径用
        	File file = new File(path);
        	this.getHibernateTemplate().getSessionFactory().getCurrentSession().delete(fileEntity);
        	file.delete();
            return true;
        }
            return false;
	}
	
	public FileEntity getFile(String fileName) {
		String hql="from FileEntity as file where file.fileName='"+fileName+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<FileEntity> list = query.list();
        if(!list.isEmpty()){
        	return list.get(0);
        }
            return null;
	}

	@Override
	public FileEntity fileShow(int fileId) {
		String hql="from FileEntity as file where file.id='"+fileId+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<FileEntity> list = query.list();
		return list.get(0);
	}

	@Override
	public Page getForPage(int currentPage, int pageSize) {

		String hql1 = "SELECT COUNT(*) FROM FileEntity";
		String hql2="from FileEntity";
		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}

	@Override
	public FileEntity getFile(int fileId) {
		String hql="from FileEntity as file where file.id='"+fileId+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<FileEntity> list = query.list();
        if(!list.isEmpty()){
        	return list.get(0);
        }
        return null;
	}

	@Override
	public Page getForPage(int currentPage, int pageSize, User user) {
		int userId = user.getId();
		String hql1 = "SELECT COUNT(*) from FileEntity as file inner join file.user as u where u.id='"+userId+"'";
		String hql2="from FileEntity";
		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}

//	@Override
//	public Long getAllRowCount() {
//		//COUNT(*) 函数返回表中的记录数
//		String hql = "SELECT COUNT(*) FROM FileEntity";
//		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
//        Query query =session.createQuery(hql);
//		return Long.parseLong(query.uniqueResult().toString());
//	}
//
//	@Override
//	public List<FileEntity> getForPage(int offset, int pageSize) {
//		List<FileEntity> fileEntityList = null;
//		String hql="from FileEntity";
//		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
//        Query query =session.createQuery(hql);
//        query.setFirstResult(offset);
//        query.setMaxResults(pageSize);
//        fileEntityList = query.list();
//		return fileEntityList;
//	}

	

}
