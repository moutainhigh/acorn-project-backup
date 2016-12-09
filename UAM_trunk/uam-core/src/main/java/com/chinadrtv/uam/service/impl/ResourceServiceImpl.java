package com.chinadrtv.uam.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinadrtv.uam.dao.ResourceDao;
import com.chinadrtv.uam.model.auth.res.ResAction;
import com.chinadrtv.uam.service.ResourceService;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-20
 * Time: 上午11:09
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ResourceServiceImpl extends ServiceSupportImpl implements ResourceService {

	@Resource
	private ResourceDao resourceDao;
	
	@Override
	public List<ResAction> loadUamResources() {
		return resourceDao.loadUamResources();
	}
}
