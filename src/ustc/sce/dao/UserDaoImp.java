package ustc.sce.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ustc.sce.domain.Role;
import ustc.sce.domain.Token;
import ustc.sce.domain.User;
import ustc.sce.utils.MD5Utils;

public class UserDaoImp extends HibernateDaoSupport implements UserDao {

	@Override
	public User login(String userName, String userPassword) {
		String hql="from User as user where user.userName='"+userName+"' and user.userPassword = '"+MD5Utils.md5(userPassword)+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<User> list = query.list();
        
        return list.get(0);
	}

	@Override
	public boolean register(String userName, String userPassword, String roleName) {
		User user = new User();
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		
		String hql="from Role as role where role.roleName='"+roleName+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<Role> list = query.list();
        Role role = list.get(0);
		
		user.setRole(role);
		
		Serializable save = this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(user);
		 if(save == null){
	            return false;
	        }
	            return true;
	}

	/**
	 * 检测用户是否已经注册
	 */
	public User checkUser(String userName) {
		String hql="from User as user where user.userName='"+userName+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<User> list = query.list();
        if(list.isEmpty()){
        	return null;
        }
            return list.get(0);
	}

	public boolean exit(String userName) {
		
		String hql="from Token as token where token.userName='"+userName+"'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<Token> list = query.list();
        
        Token token = list.get(0);
        this.getHibernateTemplate().getSessionFactory().getCurrentSession().delete(token);
        
        Query query1 =session.createQuery(hql);
        List<Token> list1 = query1.list();
        
        if(list1.isEmpty()) {
        	return true;
        }
		return false;
	}

	@Override
	public List<Role> getRole() {

		String hql="from Role";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query =session.createQuery(hql);
        List<Role> list = query.list();
		
		return list;
	}

}
