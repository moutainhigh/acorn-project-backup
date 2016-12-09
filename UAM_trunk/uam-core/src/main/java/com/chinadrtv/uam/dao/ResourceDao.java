package com.chinadrtv.uam.dao;

import java.util.List;

import com.chinadrtv.uam.model.auth.res.ResAction;

public interface ResourceDao {

	/**
	 * 
	 * @return
	 */
	List<ResAction> loadUamResources();
}
