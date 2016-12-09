package com.chinadrtv.uam.dao;

import com.chinadrtv.uam.model.cas.Site;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-4-10
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
public interface SiteDao {

    public Site getSiteByName(String siteName);
}
