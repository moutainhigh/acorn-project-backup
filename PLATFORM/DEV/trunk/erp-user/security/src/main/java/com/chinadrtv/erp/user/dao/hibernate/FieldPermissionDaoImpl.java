package com.chinadrtv.erp.user.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.FieldPermission;
import com.chinadrtv.erp.user.dao.FieldPermissionDao;

/**
 * User: zhaizhanyi
 * Date: 12-11-19
 */

@Repository("fieldPermissionDao")
public class FieldPermissionDaoImpl extends GenericDaoHibernate<FieldPermission,Long> implements FieldPermissionDao{
	
    public FieldPermissionDaoImpl() {
        super(FieldPermission.class);
    }

	/* (非 Javadoc)
	* <p>Title: getFieldPermission</p>
	* <p>Description: </p>
	* @param roleName
	* @param url
	* @param fieldName
	* @return
	* @see com.chinadrtv.erp.user.dao.FieldPermissionDao#getFieldPermission(java.lang.String, java.lang.String, java.lang.String)
	*/ 
	@Override
	public FieldPermission getFieldPermission(String roleName, String url, String fieldName) {

		String sql = "select f.id,f.field_name,fp.is_visible,fp.is_editable "+
				"  from acoapp_security.role_permission      r,               "+
				"       acoapp_security.data_view            d,               "+
				"       acoapp_security.data_view_permission dp,              "+
				"       acoapp_security.field_permission     fp,              "+
				"       acoapp_security.view_field           f                "+
				" where r.id = dp.role_permission_id                          "+
				"   and dp.data_view_id = d.id                                "+
				"   and fp.data_view_permission_id = dp.id                    "+
				"   and fp.view_field_id = f.id                               "+
				"   and r.role_id = :roleName		                          "+
				"   and d.url = :url				                          "+
				"   and f.field_name=:fieldName                               ";
		
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		
		sqlQuery.setString("roleName", roleName);
		sqlQuery.setString("url", url);
		sqlQuery.setString("fieldName", fieldName);
		
		List objList = sqlQuery.list();
		Object[] obj = null;
		FieldPermission fieldPermission = null;
		if(!objList.isEmpty()){
			obj = (Object[])objList.get(0);
			fieldPermission = new FieldPermission();
			fieldPermission.setId(Long.valueOf(((BigDecimal)obj[0]).toString()));
			fieldPermission.setFieldName((String)obj[1]);
			fieldPermission.setIs_visible(Integer.valueOf(((BigDecimal)obj[2]).toString()));
			fieldPermission.setIs_editable(Integer.valueOf(((BigDecimal)obj[3]).toString()));
		}
        return fieldPermission;
		
	}

}
