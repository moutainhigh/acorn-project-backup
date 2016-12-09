package com.chinadrtv.uam.dao.impl;

import com.chinadrtv.uam.dao.SiteDao;
import com.chinadrtv.uam.model.cas.Site;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: zhoutaotao Date: 14-4-10 Time: 上午10:29 To
 * change this template use File | Settings | File Templates.
 */
@Repository
public class SiteDaoImpl extends HibernateDaoImpl implements SiteDao {
	@Override
	public Site getSiteByName(String siteName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", siteName);

		return findByHql("from Site s where s.name =:name ", map);
	}
}
