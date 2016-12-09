/*
 * @(#)GrpDaoImpl.java 1.0 2013-6-20下午2:08:44
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.Grp;
import com.chinadrtv.erp.user.dao.GrpDao;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-6-20 下午2:08:44 
 * 
 */
@Repository
public class GrpDaoImpl extends GenericDaoHibernate<Grp, String> implements GrpDao {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public GrpDaoImpl() {
		super(Grp.class);
	}

    public List<Grp> getGrpList(List<String> grpIdList)
    {
        if(grpIdList==null||grpIdList.size()==0)
            return new ArrayList<Grp>();
        Map<String,Parameter> mapParms=new Hashtable<String,Parameter>();
        StringBuilder strBld=new StringBuilder("from Grp where ");
        for(int i=0;i<grpIdList.size();i++)
        {
            String name="grp"+i;
            if(i==0)
            {
                strBld.append(String.format("grpid=:%s",name));
            }
            else
            {
                strBld.append(String.format(" or grpid=:%s",name));
            }
            mapParms.put(name,new ParameterString(name,grpIdList.get(i)));
        }
        return this.findList(strBld.toString(),mapParms);
    }
    
    public List<Grp> getGrpList(String departmentNum)
    {
        Map<String,Parameter> mapParms=new Hashtable<String,Parameter>();
        StringBuilder strBld=new StringBuilder("from Grp where dept=:dept");
          mapParms.put("dept",new ParameterString("dept",departmentNum));
        return this.findList(strBld.toString(),mapParms);
    }

	/* (非 Javadoc)
	* <p>Title: checkGrpMangerRole</p>
	* <p>Description: </p>
	* @param groupId
	* @return
	* @see com.chinadrtv.erp.user.dao.GrpDao#checkGrpMangerRole(java.lang.String)
	*/ 
	@Override
	public boolean checkGrpMangerRole(String groupId) {
		 Map<String,Parameter> mapParms=new Hashtable<String,Parameter>();
	     StringBuilder strBld=new StringBuilder("select * from iagent.pglink t where t.grpid=:groupId and t.purviewid='ModifyReqCom' and t.rights='-1'");
	        
	     SQLQuery sqlQuery = this.getSession().createSQLQuery(strBld.toString());
	     sqlQuery.setString("groupId", groupId);
	     
	     List list = sqlQuery.list();
	     if(list.isEmpty()){
	    	 return false;
	     }else{
	    	 return true;
	     }
	}

}
