package com.chinadrtv.erp.user.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.security.DataViewPermission;
import com.chinadrtv.erp.user.dao.DataViewPermissionDao;

/**
 * User: zhaizhanyi
 * Date: 12-11-19
 */

@Repository("dataViewPermissionDao")
public class DataViewPermissionDaoImpl extends GenericDaoHibernate<DataViewPermission,Long> implements DataViewPermissionDao{
	
    public DataViewPermissionDaoImpl() {
        super(DataViewPermission.class);
    }

	/**
	* <p>Title: query</p>
	* <p>Description: </p>
	* @param viewUrl
	* @param roleId
	* @return
	* @see com.chinadrtv.erp.user.dao.DataViewPermissionDao#query(java.lang.String, java.lang.String)
	*/
    @ReadThroughSingleCache(namespace = "com.chinadrtv.erp.model.security.DataViewPermission1", expiration =  60 * 60 * 24)
	public DataViewPermission query(@ParameterValueKeyProvider(order = 0)String viewUrl,
                                    @ParameterValueKeyProvider(order = 1)String roleId) {
		StringBuffer sql = new StringBuffer("from DataViewPermission dvp " +
				"where dvp.dataView.url=:dataViewUrl " +
				"and dvp.rolePermission.role_id=:roleId");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter viewUrlParam = new ParameterString("dataViewUrl", viewUrl);
		Parameter roleIdParam = new ParameterString("roleId", roleId);

		params.put("dataViewUrl", viewUrlParam);
		params.put("roleId", roleIdParam);
		return find(sql.toString(), params);
	}

}
