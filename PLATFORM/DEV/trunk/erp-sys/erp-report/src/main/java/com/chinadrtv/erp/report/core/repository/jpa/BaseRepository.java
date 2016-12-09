package com.chinadrtv.erp.report.core.repository.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chinadrtv.erp.report.core.orm.entity.BaseEntity;

/**
 * Spring data jpa dao 代理扩展接口
 * @author zhangguosheng
 * @param <T>
 * @param <PK>
 */
public interface BaseRepository<T extends BaseEntity<T>, PK extends Serializable> extends PagingAndSortingRepository<T, PK>{

	/**
	 * 通过主键查询
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public T get(Class<T> entityClass, PK id);
	
	/**
	 * 使用sql语句查询，返回转换好的实体类并且分页
	 * @param entityClass
	 * @param sql
	 * @param values
	 * @param pageable
	 * @return
	 */
	public <E extends BaseEntity<E>> Page<E> nativeQuery(Class<E> entityClass, String sql, Object[] values, Pageable pageable);
	
	/**
	 * 原生sql查询接口
	 * @param sql
	 * @param values
	 * @return
	 */
	public List<Object[]> nativeQuery(String sql, Object[] values);
	
}
