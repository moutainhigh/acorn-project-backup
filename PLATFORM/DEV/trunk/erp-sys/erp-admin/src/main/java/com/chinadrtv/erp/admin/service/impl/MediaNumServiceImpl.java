package com.chinadrtv.erp.admin.service.impl;

import com.chinadrtv.erp.admin.dao.MediaNumDao;
import com.chinadrtv.erp.admin.service.MediaNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-11-13
 * Time: 上午11:13
 * To change this template use File | Settings | File Templates.
 */
@Service("mediaNumService")
public class MediaNumServiceImpl implements MediaNumService {
    @Autowired
    private MediaNumDao mediaNumDao;

    public int getMediaNumCountByNum(String mediaNum) {
        return mediaNumDao.getMediaNumCountByNum(mediaNum);
    }

}
