package com.chinadrtv.erp.admin.dao;

import com.chinadrtv.erp.admin.model.MediaNum;
import com.chinadrtv.erp.core.dao.GenericDao;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-11-13
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 */
public interface MediaNumDao extends GenericDao<MediaNum, Long>{
    public int getMediaNumCountByNum(String mediaNum);
}
