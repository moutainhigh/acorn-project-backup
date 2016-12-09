package com.chinadrtv.uam.service;

import java.util.List;

import com.chinadrtv.uam.model.auth.res.ResAction;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-20
 * Time: 上午11:09
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceService extends ServiceSupport {
	
	/**
	 * 加载UAM资源与角色的对应关系
	 * 
	 * @return
	 */
	List<ResAction> loadUamResources();
}
