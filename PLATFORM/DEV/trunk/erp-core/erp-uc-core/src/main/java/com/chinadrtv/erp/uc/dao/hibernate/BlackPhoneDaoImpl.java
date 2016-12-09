package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.BlackPhone;
import com.chinadrtv.erp.uc.dao.BlackPhoneDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title: BlackPhoneDaoImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Repository
public class BlackPhoneDaoImpl extends GenericDaoHibernateBase<BlackPhone, Long> implements BlackPhoneDao {

    @Autowired
    private JdbcTemplate ctiJdbcTemplate;

    public BlackPhoneDaoImpl() {
        super(BlackPhone.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    @Override
    public BlackPhone findByCustomer(String phoneNum) {
        String hql = "from BlackPhone b where b.phoneNum=:phoneNum and b.status<>3";
        Query query = this.getSession().createQuery(hql.toString());
        query.setString("phoneNum", phoneNum);
        return (BlackPhone) query.uniqueResult();
    }

    @Override
    public List<BlackPhone> queryAll() {
        String hql = "from BlackPhone";
        Query query = this.getSession().createQuery(hql.toString());
        return query.list();
    }

    public void saveBlackPhoneToCti(final BlackPhone blackPhone) {
        ctiJdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                String sql = "insert into BLACKLIST (BLACKDN,BLACKPHONEID,CREATEDATE) values(?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, blackPhone.getPhoneNum());
                ps.setLong(2, blackPhone.getId());
                ps.setDate(3, new Date(new java.util.Date().getTime()));
                return ps;
            }
        }
        );
    }

    public void deleteBlackPhoneFromCti(BlackPhone blackPhone) {
        ctiJdbcTemplate.update("delete from BLACKLIST where BLACKPHONEID=?", new Object[]{blackPhone.getId()},
                new int[]{java.sql.Types.LONGVARCHAR});
    }

    @Override
    public List<BlackPhone> queryPageList(String phoneNum, Integer addTimes, Integer status, int startRow, int rows) {
        StringBuilder hql = new StringBuilder();
        hql.append(" from BlackPhone b where b.status<>3 ");
        if (StringUtils.isNotBlank(phoneNum)) hql.append(" and b.phoneNum=:phoneNum ");
        if (addTimes != null) hql.append(" and b.addTimes=:addTimes ");
        if (status != null) hql.append(" and b.status=:status ");
        Query hqlQuery = this.getSession().createQuery(hql.toString());
        hqlQuery.setFirstResult(startRow);
        hqlQuery.setMaxResults(rows);
        if (StringUtils.isNotBlank(phoneNum)) hqlQuery.setParameter("phoneNum", phoneNum);
        if (addTimes != null) hqlQuery.setParameter("addTimes", addTimes);
        if (status != null) hqlQuery.setParameter("status", status);
        return hqlQuery.list();
    }

    @Override
    public Integer queryCountPageList(String phoneNum, Integer addTimes, Integer status) {
        StringBuilder hql = new StringBuilder();
        hql.append("select count(*) from BlackPhone b where b.status<>3 ");
        if (StringUtils.isNotBlank(phoneNum)) hql.append(" and b.phoneNum=:phoneNum ");
        if (addTimes != null) hql.append(" and b.addTimes=:addTimes ");
        if (status != null) hql.append(" and b.status=:status ");
        Query hqlQuery = this.getSession().createQuery(hql.toString());
        if (StringUtils.isNotBlank(phoneNum)) hqlQuery.setParameter("phoneNum", phoneNum);
        if (addTimes != null) hqlQuery.setParameter("addTimes", addTimes);
        if (status != null) hqlQuery.setParameter("status", status);
        return Integer.valueOf(hqlQuery.uniqueResult().toString());
    }
}
