package ustc.sce.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 以后所有的DAO的接口都需要继承BaseDao接口
 * 自定义泛型接口
 * @author Administrator
 */
public interface BaseDao<T> {
	
	public void save(T t);
	
	public void delete(T t);
	
	public void update(T t);
	
	public T findById(int id);
	
	public T findByName(String userName);
	
	public List<T> findAll();
	
//	public PageBean<T> findByPage(Integer pageCode, Integer pageSize, DetachedCriteria criteria);
	
}
