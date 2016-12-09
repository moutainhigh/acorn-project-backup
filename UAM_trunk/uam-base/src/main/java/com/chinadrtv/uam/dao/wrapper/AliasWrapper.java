package com.chinadrtv.uam.dao.wrapper;

import org.hibernate.criterion.CriteriaSpecification;

/**
 * 
 * @author Qianyong,Deng
 * @since Oct 8, 2012
 *
 */
public class AliasWrapper {

	private String property;
    private String alias;
    private JoinType joinType;
    
    public AliasWrapper(String property, String alias) {
    	 this.property = property;
         this.alias = alias;
    }

    public AliasWrapper(String property, String alias, JoinType joinType) {
        this(property, alias);
        this.joinType = joinType;
    }

    public String getProperty() {
        return property;
    }

    public String getAlias() {
        return alias;
    }

    public Integer getJoinType() {
        return joinType != null ? joinType.get() : null;
    }
    
    public static enum JoinType {
    	INNER_JOIN(CriteriaSpecification.INNER_JOIN),
    	LEFT_JOIN(CriteriaSpecification.LEFT_JOIN),
    	FULL_JOIN(CriteriaSpecification.FULL_JOIN);
    	private JoinType(Integer value) {
    		this.value = value;
    	}
    	private Integer value;
    	
    	public Integer get() {
    		return this.value;
    	}
    }
}
