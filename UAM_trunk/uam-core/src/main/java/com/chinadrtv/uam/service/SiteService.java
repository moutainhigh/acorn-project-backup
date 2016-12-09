package com.chinadrtv.uam.service;

import com.chinadrtv.uam.model.cas.Site;

/**
 * 
 * @author dengqianyong
 *
 */
public interface SiteService extends ServiceSupport {

    Site getSiteByName(String siteName);
}
