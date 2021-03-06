package com.chinadrtv.erp.task.core.repository.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * spring data jpa 代理工厂
 * @author zhangguosheng
 * @param <T>
 */
public class BaseRepositoryFactoryBean<T extends JpaRepository<Object, Serializable>> extends JpaRepositoryFactoryBean<T, Object, Serializable>  {

	@Override
	@SuppressWarnings("rawtypes")
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {  
      return new BaseRepositoryFactory(em);
    }  
	
}
