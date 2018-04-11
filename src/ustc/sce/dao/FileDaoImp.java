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

/**
 * 文件持久层 保存文件 删除文件 根据文件id查询文件
 * 
 * @author 秋色天堂
 *
 */
public class FileDaoImp extends HibernateDaoSupport implements FileDao {

	private PageUtil pageUtil;

	public void setPageUtil(PageUtil pageUtil) {
		this.pageUtil = pageUtil;
	}

	/**
	 * 保存文件
	 */
	public void FileSave(FileEntity fileUpload) {
		this.getHibernateTemplate().save(fileUpload);
	}

	/**
	 * 删除文件
	 */
	public boolean fileDelete(int fileId, HttpServletRequest request) {
		String hql = "from FileEntity as file where file.id='" + fileId + "'";
		List<FileEntity> list = (List<FileEntity>) getHibernateTemplate().find(hql);
		if (!list.isEmpty()) {
			FileEntity fileEntity = list.get(0);
			String path = fileEntity.getFilePath();
			// path只是文件存储路径的后半部分 加上前面的才是完整的路径
			path = request.getSession().getServletContext().getRealPath("\\") + path; // 相对路径用
			File file = new File(path);
			this.getHibernateTemplate().delete(fileEntity);
			if (file.exists()) {
				file.delete();
			}
			return true;
		}
		return false;
	}

	/**
	 * 根据文件id查询文件
	 */
	public FileEntity getFile(int fileId) {
		String hql = "from FileEntity as file where file.id='" + fileId + "'";
		List<FileEntity> list = (List<FileEntity>) getHibernateTemplate().find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 分页显示该用户上传的文件
	 */
	public Page getForPage(String keyWords, int currentPage, int pageSize, User user) {
		int userId = user.getId();
		Page page = new Page();
		// 列表
		String hql1 = "SELECT COUNT(*) from FileEntity as file inner join file.user as u where u.id='" + userId + "'";
		String hql2 = "from FileEntity as file inner join fetch file.user as u where u.id='" + userId + "'";
		// 查询
		String hql3 = "SELECT COUNT(*) from FileEntity as file inner join file.user as u where u.id='" + userId + "'"
				+ "and fileName like'" + "%" + keyWords + "%" + "'";
		String hql4 = "from FileEntity as file inner join fetch file.user as u where u.id='" + userId + "'"
				+ "and fileName like'" + "%" + keyWords + "%" + "'";

		if (keyWords == null) {
			page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		} else {
			page = pageUtil.getForPage(hql3, hql4, currentPage, pageSize);
		}
		return page;
	}

	/**
	 * 不分页显示该用户上传的文件
	 */
	public List<FileEntity> fileNoPageList(User user) {
		int userId = user.getId();
		String hql = "from FileEntity as file inner join file.user as u where u.id='" + userId + "'";
		List<FileEntity> list = (List<FileEntity>) this.getHibernateTemplate().find(hql);
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}

	/**
	 * 分页显示所有文件列表
	 */
	public Page getForPage(int currentPage, int pageSize) {

		String hql1 = "SELECT COUNT(*) FROM FileEntity";
		String hql2 = "from FileEntity";
		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}

	/**
	 * 按文件名查找所有文件 分页显示
	 */
	public Page getForPage(int currentPage, int pageSize, String keyWords) {

		String hql1 = "SELECT COUNT(*) FROM FileEntity where fileName like '" + "%" + keyWords + "%" + "'";
		String hql2 = "from FileEntity where fileName like '" + "%" + keyWords + "%" + "'";

		Page page = pageUtil.getForPage(hql1, hql2, currentPage, pageSize);
		return page;
	}

	/**
	 * 按文件名查找该用户上传文件 不用分页显示
	 */
	public List<FileEntity> fileNoPageList(User user, String keyWords) {
		int userId = user.getId();
		String hql = "from FileEntity as file inner join file.user as u where u.id='" + userId + "'"
				+ "and fileName like '" + "%" + keyWords + "%" + "'";
		List<FileEntity> list = (List<FileEntity>) this.getHibernateTemplate().find(hql);
		if (list.isEmpty()) {
			return null;
		}

		return list;
	}

	/**
	 * 根据文件名 获得该文件信息
	 */
	public FileEntity getFile(String fileName) {
		String hql = "from FileEntity as file where file.fileName='" + fileName + "'";
		List<FileEntity> list = (List<FileEntity>) getHibernateTemplate().find(hql);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	// @Override
	// public Long getAllRowCount() {
	// //COUNT(*) 函数返回表中的记录数
	// String hql = "SELECT COUNT(*) FROM FileEntity";
	// Session session =
	// getHibernateTemplate().getSessionFactory().getCurrentSession();
	// Query query =session.createQuery(hql);
	// return Long.parseLong(query.uniqueResult().toString());
	// }
	//
	// @Override
	// public List<FileEntity> getForPage(int offset, int pageSize) {
	// List<FileEntity> fileEntityList = null;
	// String hql="from FileEntity";
	// Session session =
	// getHibernateTemplate().getSessionFactory().getCurrentSession();
	// Query query =session.createQuery(hql);
	// query.setFirstResult(offset);
	// query.setMaxResults(pageSize);
	// fileEntityList = query.list();
	// return fileEntityList;
	// }

}
