package com.chinadrtv.erp.admin.dao.hibernate;

import com.chinadrtv.erp.admin.dao.MediaNumDao;
import com.chinadrtv.erp.admin.model.MediaNum;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 12-11-13
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class MediaNumDaoImpl extends GenericDaoHibernate<MediaNum, Long> implements MediaNumDao{
    public MediaNumDaoImpl() {
        super(MediaNum.class);
    }

    private Query initQuery(String mediaNum, StringBuilder sb) {
        if (mediaNum != null && !mediaNum.equals("")) {
            sb.append(" and  a.n400  = :mediaNum ");
        }

        Query q = getSession().createQuery(sb.toString());

        if (mediaNum != null && !mediaNum.equals("")) {
            q.setString("mediaNum", mediaNum);
        }

        return q;
    }

    public int getMediaNumCountByNum(String mediaNum) {
        //String hql = "from AuditLog a where ";
        // Query qTest = getSession().createQuery(hql);
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(a.ruid) from MediaNum a  where 1=1  ");
        Query q = initQuery(mediaNum, sb);
        int count = Integer.valueOf(q.list().get(0).toString());
        return count;
    }
}
