package com.chinadrtv.erp.admin.dao;


import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.ChannelType;

import java.util.List;

/**s
 * Created with IntelliJ IDEA.
 * User: g
 * Date: 12-8-10
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public interface ChannelTypeDao extends GenericDao<ChannelType, String> {
	ChannelType getNativeById(String id);
    List<ChannelType> getAllChannelTypes();
}
