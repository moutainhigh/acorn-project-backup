/**
 * 
 */
package com.chinadrtv.uam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.uam.dao.SiteDao;
import com.chinadrtv.uam.model.cas.Site;
import com.chinadrtv.uam.service.SiteService;

/**
 * @author dengqianyong
 *
 */
@Service
public class SiteServiceImpl extends ServiceSupportImpl implements SiteService {
    @Autowired
    private SiteDao siteDao;

    @Override
    public Site getSiteByName(String siteName) {
        return  siteDao.getSiteByName(siteName);
    }
}
