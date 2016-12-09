package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.cntrpbank.AcdGroup;
import com.chinadrtv.erp.uc.dao.AcdGroupDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ACD组数据访问
 * User: gaodejian
 * Date: 13-12-13
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AcdGroupDaoImpl extends GenericDaoHibernate<AcdGroup, String> implements AcdGroupDao {

	public AcdGroupDaoImpl() {
		super(AcdGroup.class);
	}

	public List<AcdGroup> getAcdGroupsByDeptNo(String deptNo, Boolean ivr) {
		Session session = getSession();
		Query query = session.createQuery("from AcdGroup c where c.deptNo = :deptNo and c.ivr = :ivr");
        query.setString("deptNo", deptNo);
        query.setBoolean("ivr", ivr);
        return query.list();
	}

    public List<AcdGroup> getAllAcdGroupsByDeptNo(Boolean ivr) {
        Session session = getSession();
        Query query = session.createQuery("from AcdGroup c where c.ivr = :ivr");
        query.setBoolean("ivr", ivr);
        return query.list();
    }
}
