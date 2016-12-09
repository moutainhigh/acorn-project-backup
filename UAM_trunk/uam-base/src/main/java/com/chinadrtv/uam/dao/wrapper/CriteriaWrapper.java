package com.chinadrtv.uam.dao.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.transform.ResultTransformer;

/**
 * 
 * @author Qianyong,Deng
 * @since Oct 6, 2012
 * 
 */
public class CriteriaWrapper {
	
	private Projection projection;
	
	private RowBounds rowBounds;

	private ResultTransformer resultTransformer;
	
	private List<Criterion> criterions;

	private List<Order> orders;

	private List<FetchModeWrapper> fetchModeWrappers;
	
	private List<AliasWrapper> aliasWrappers;
	
	public CriteriaWrapper() {
		criterions = new ArrayList<Criterion>();
		orders = new ArrayList<Order>();
		fetchModeWrappers = new ArrayList<FetchModeWrapper>();
		aliasWrappers = new ArrayList<AliasWrapper>();
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public List<Criterion> getCriterions() {
		return criterions;
	}

	public void setCriterions(List<Criterion> criterions) {
		this.criterions = criterions;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<FetchModeWrapper> getFetchModeWrappers() {
		return fetchModeWrappers;
	}

	public void setFetchModeWrappers(List<FetchModeWrapper> fetchModeWrapper) {
		this.fetchModeWrappers = fetchModeWrapper;
	}

	public RowBounds getRowBounds() {
		return rowBounds;
	}

	public void setRowBounds(RowBounds rowBounds) {
		this.rowBounds = rowBounds;
	}

	public List<AliasWrapper> getAliasWrappers() {
		return aliasWrappers;
	}

	public void setAliasWrappers(List<AliasWrapper> aliasWraps) {
		this.aliasWrappers = aliasWraps;
	}

	public ResultTransformer getResultTransformer() {
		return resultTransformer;
	}

	public void setResultTransformer(ResultTransformer resultTransformer) {
		this.resultTransformer = resultTransformer;
	}

	public void addCriterion(Criterion c) {
		this.criterions.add(c);
	}
	
	public void addOrder(Order o) {
		this.orders.add(o);
	}
	
	public void addFetchModeWrapper(FetchModeWrapper fw) {
		this.fetchModeWrappers.add(fw);
	}
	
	public void addAliasWrapper(AliasWrapper aw) {
		this.aliasWrappers.add(aw);
	}
	
}
