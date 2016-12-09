package com.chinadrtv.erp.task.core.repository.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chinadrtv.erp.task.core.orm.entity.BaseEntity;

/**
 * Spring data jpa dao 代理扩展接口
 * @author zhangguosheng
 * @param <T>
 * @param <PK>
 * @author zhangguosheng
 */
public interface BaseRepository<T extends BaseEntity, PK extends Serializable> extends PagingAndSortingRepository<T, PK>{

	/**
	 * 通过主键查询
	 * 注意：sql语句末尾不能带分号
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public T get(Class<T> entityClass, PK id);
	
	/**
	 * 使用sql查询
	 * 注意：sql语句末尾不能带分号
	 * @param entityClass 查询的实体类
	 * @param sql 查询sql
	 * @param values 查询参数-数组
	 * @param pageable 分页参数
	 * @return 对应的分页数据
	 */
	public <E extends BaseEntity> Page<E> nativeQuery(Class<E> entityClass, String sql, Object[] values, Pageable pageable);
	
	/**
	 * 使用sql查询
	 * 注意：sql语句末尾不能带分号
	 * @param entityClass 查询的实体类
	 * @param sql 查询sql
	 * @param values 查询参数-链表
	 * @param pageable 分页参数
	 * @return 对应的分页数据
	 */
	public <E extends BaseEntity> Page<E> nativeQuery(Class<E> entityClass, String sql, List<Object> values, Pageable pageable);
	
	/**
	 * 使用sql查询
	 * @param entityClass 查询的实体类
	 * @param sql 查询sql
	 * @param values 查询参数-map
	 * @param pageable 分页参数
	 * @return 对应的分页数据
	 */
	public <E extends BaseEntity> Page<E> nativeQuery(Class<E> entityClass, String sql, Map<String, Object> values, Pageable pageable);
	
	/**
	 * 通过sql查询
	 * 注意：sql语句末尾不能带分号
	 * @param sql 查询sql
	 * @param values 查询参数-数组
	 * @param pageNo 页码，从1开始
	 * @param pageSize 页大小
	 * @return 结果集
	 */
	public List<Object[]> nativeQuery(String sql, Object[] values, Integer pageNo, Integer pageSize);
	
	/**
	 * 通过sql查询
	 * 注意：sql语句末尾不能带分号
	 * @param sql 查询sql
	 * @param values 查询参数-链表
	 * @param pageNo 页码，从1开始
	 * @param pageSize 页大小
	 * @return 结果集
	 */
	public List<Object[]> nativeQuery(String sql, List<Object> values, Integer pageNo, Integer pageSize);
	
	/**
	 * 通过sql查询
	 * 注意：sql语句末尾不能带分号
	 * @param sql 查询sql
	 * @param values 查询参数 key-value
	 * @param pageNo 页码，从1开始
	 * @param pageSize 页大小
	 * @return 结果集
	 */
	public List<Object[]> nativeQuery(String sql, Map<String, Object> values, Integer pageNo, Integer pageSize);
	
	/**
	 * 通过sql批量更新
	 * 注意：sql语句末尾不能带分号
	 * @param sql 更新sql
	 * @param values 更新参数
	 * @return 状态
	 */
	public int nativeUpdate(String sql, Object[] values);
	
}
