package com.chinadrtv.erp.task.core.repository.jpa;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

import com.chinadrtv.erp.task.core.orm.entity.BaseEntity;

/**
 * spring data jpa 代理工厂
 * @author zhangguosheng
 * @param <T>
 * @param <PK>
 */
public class BaseRepositoryFactory<T extends BaseEntity, PK extends Serializable> extends JpaRepositoryFactory{
	
    public BaseRepositoryFactory(EntityManager entityManager) {   
        super(entityManager);   
    }
    
	@Override
	@SuppressWarnings("unchecked")
    protected JpaRepository<T, PK> getTargetRepository(RepositoryMetadata metadata, EntityManager em){
        JpaEntityInformation<T, PK> entityMetadata = mock(JpaEntityInformation.class);   
        when(entityMetadata.getJavaType()).thenReturn((Class<T>) metadata.getDomainType());  
        return new BaseRepositoryPlus<T, PK>(entityMetadata, em);   
    }   
 
    @Override  
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {   
        return BaseRepositoryPlus.class;   
    } 

}
