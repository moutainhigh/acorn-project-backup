package com.chinadrtv.erp.report.core.repository.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.chinadrtv.erp.report.core.orm.entity.BaseEntity;

/**
 * Spring data jpa dao 代理扩展接口实现类
 * @author zhangguosheng
 * @param <T>
 * @param <PK>
 */
@NoRepositoryBean
public class BaseRepositoryPlus<T extends BaseEntity<T>, PK extends Serializable> extends SimpleJpaRepository<T, PK> implements BaseRepository<T, PK>{

	private final EntityManager entityManager;   
	
	/**
	 * 
	 * @param entityInformation
	 * @param entityManager
	 */
	public BaseRepositoryPlus(final JpaEntityInformation<T, PK> entityInformation, final EntityManager entityManager) { 
	    super(entityInformation, entityManager);   
	    this.entityManager = entityManager;   
	}

	/**
	 * 删除实体
	 * @param BaseEntity
	 */
    @Override
    public T get(Class<T> entityClass, PK id){
    	return entityManager.find(entityClass, id);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <E extends BaseEntity<E>> Page<E> nativeQuery(Class<E> entityClass, String sql, Object[] values, Pageable pageable){
    	Page<E> page = new PageImpl<E>(new ArrayList<E>(), pageable, 0);
    	
    	if(entityClass!=null && sql!=null && values!=null){
    		//计算数量
			String singleStr = removeSelect(removeOrders(sql));
			Query queryCount = entityManager.createNativeQuery(" select count(1) " + singleStr);
    		for (int i = 0; values != null && i < values.length; i++) {
    			queryCount.setParameter(i+1, values[i]);
    		}
			List<BigDecimal> totals = queryCount.getResultList();
			Long total = 0L;
			for (BigDecimal element : totals) {
				total += element == null ? 0 : element.longValue();
			}
    		
    		//查询数据
    		Query query = entityManager.createNativeQuery(sql, entityClass);
    		for (int i = 0; values != null && i < values.length; i++) {
    			query.setParameter(i+1, values[i]);
    		}
        	query.setFirstResult(pageable.getOffset());
        	query.setMaxResults(pageable.getPageSize());
        	
    		List<E> content = total > pageable.getOffset() ? query.getResultList() : Collections.<E> emptyList();
    		page = new PageImpl<E>(content, pageable, total);
    	}	
    	return page;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> nativeQuery(String sql, Object[] values){
    	List<Object[]> list = new ArrayList<Object[]>();
    	if(sql!=null && values!=null){
    		Query query = entityManager.createNativeQuery(sql);
    		for (int i = 0; values != null && i < values.length; i++) {
    			query.setParameter(i+1, values[i]);
    		}
    		list = query.getResultList();
    	}
    	return list;
    }
    
	/**
	 * 删除select
	 * @param
	 * @return
	 */
	private static String removeSelect(String sql) {
		int beginPos = sql.toLowerCase().indexOf("from");
		return sql.substring(beginPos);
	}
	
	/**
	 * 移除order by
	 * @param
	 * @return
	 */
	private static String removeOrders(String sql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

}
