package ustc.sce.utils;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import ustc.sce.domain.Token;
import ustc.sce.domain.User;

/**
 * 保存token和获得对应的用户
 * 
 * @author 秋色天堂
 *
 */
@Transactional
public class TokenUtil extends HibernateDaoSupport {

	/**
	 * 将用户名和对应的token保存到数据库中
	 * 
	 * @param token
	 * @param userName
	 */
	public Token tokenSave(String token, String userName) {

		Token tk = new Token();

		tk.setToken(token);
		tk.setUserName(userName);
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(tk);

		return tk;

	}

	/**
	 * 用户每次登录都更新token
	 * @param token
	 * @param userName
	 * @return
	 */
	public Token tokenChange(String token, String userName) {
		
		String hql = "from Token where userName='" + userName + "'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<Token> list = query.list();

		Token token2 = list.get(0);
		token2.setToken(token);

		this.getHibernateTemplate().getSessionFactory().getCurrentSession().update(token2);


		return token2;

	}

	/**
	 * 通过token获得用户
	 * 
	 * @param header
	 * @return
	 */
	public User getUser(String header) {

		String hql = "from Token where token = '" + header + "'";
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		List<Token> list = query.list();
		String userName = list.get(0).getUserName();

		String hql1 = "from User where userName = '" + userName + "'";
		Session session1 = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query1 = session1.createQuery(hql1);
		List<User> list1 = query1.list();

		return list1.get(0);

	}

}
