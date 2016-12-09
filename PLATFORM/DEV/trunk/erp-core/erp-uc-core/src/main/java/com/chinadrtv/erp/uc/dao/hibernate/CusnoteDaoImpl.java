package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.service.Cusnote;
import com.chinadrtv.erp.model.service.CusnoteId;
import com.chinadrtv.erp.uc.dao.CusnoteDao;
import com.chinadrtv.erp.uc.dto.CusnoteDto;
import com.chinadrtv.erp.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xieguoqiang on 14-4-23.
 */
@Repository
public class CusnoteDaoImpl extends GenericDaoHibernateBase<Cusnote, CusnoteId> implements CusnoteDao {

    private static final Logger logger = LoggerFactory
            .getLogger(CusnoteDaoImpl.class);

    public CusnoteDaoImpl() {
        super(Cusnote.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public int getOrderCusnoteCountByOrderid(String orderId) {
        String hql = " select count(*) from Cusnote c where (c.gendate > sysdate - 7 or c.isreplay = '0') and c.orderid = :orderId";
        Query hqlQuery = this.getSession().createQuery(hql);
        hqlQuery.setParameter("orderId", orderId);
        return Integer.valueOf(hqlQuery.list().get(0).toString());
    }

    public List<Cusnote> getOrderCusnoteByOrderid(String orderId) {
        String hql = " from Cusnote c where (c.gendate > sysdate - 7 or c.isreplay = '0') and c.orderid = :orderId order by c.isreplay, c.gendate desc";
        Query hqlQuery = this.getSession().createQuery(hql);
        hqlQuery.setParameter("orderId", orderId);
        return hqlQuery.list();
    }

    private List<Cusnote> convertToCusnoteList(List list) {
        List<Cusnote> result = new ArrayList<Cusnote>();
        Object[] obj;
        for (int i = 0; i < list.size(); i++) {
            obj = (Object[]) list.get(i);
            Cusnote cusnote = new Cusnote();
            cusnote.setNotecate(obj[0] != null ? obj[0].toString() : null);
            cusnote.setIsreplay(obj[1] != null ? obj[1].toString() : null);
            cusnote.setGenseat(obj[3] != null ? obj[3].toString() : null);
            cusnote.setNoteremark(obj[4] != null ? obj[4].toString() : null);
            cusnote.setReseat(obj[6] != null ? obj[6].toString() : null);
            cusnote.setRenote(obj[7] != null ? obj[7].toString() : null);
            cusnote.setNoteclass(obj[8] != null ? obj[8].toString() : null);
            cusnote.setOrderid(obj[9] != null ? obj[9].toString() : null);
            cusnote.setCaseid(obj[11] != null ? obj[11].toString() : null);
            try {
                cusnote.setFeatstr(obj[10] != null ? Integer.valueOf(obj[10].toString()) : null);
                cusnote.setGendate(obj[2] != null ? DateUtil.string2Date(obj[2].toString(), "yyyy-MM-dd") : null);
                cusnote.setRedate(obj[5] != null ? DateUtil.string2Date(obj[5].toString(), "yyyy-MM-dd") : null);
            } catch (Exception e) {
                logger.error("客服通知查询转换时出错。" + e.getMessage());
            }
            result.add(cusnote);
        }
        return result;
    }

    @Override
    public List<Cusnote> queryCusnote(CusnoteDto cusnoteDto, DataGridModel dataGrid) {
        StringBuffer sql = new StringBuffer();
        sql.append("select c.notecate,c.isreplay,c.gendate,c.genseat,c.noteremark,c.redate,c.reseat,c.renote,c.noteclass,c.orderid,c.featstr,c.caseid ");
        sql.append(" from iagent.CUSNOTE c left join iagent.ORDERHIST o on c.orderid=o.orderid where c.gendate >= to_date(:gendate_begin,'yyyy-MM-dd') and c.gendate-1 <= to_date(:gendate_end,'yyyy-MM-dd') and o.crusr=:reseat ");
        if (StringUtils.isNotBlank(cusnoteDto.getIsreplay())) sql.append(" and c.isreplay = :isreplay");
        if (StringUtils.isNotBlank(cusnoteDto.getNoteclass())) sql.append(" and c.noteclass = :noteclass");
        if (StringUtils.isNotBlank(cusnoteDto.getGenseat())) sql.append(" and c.genseat = :genseat");
        if (StringUtils.isNotBlank(cusnoteDto.getRedate_begin())) sql.append(" and c.redate >= to_date(:redate_begin,'yyyy-MM-dd')");
        if (StringUtils.isNotBlank(cusnoteDto.getRedate_end())) sql.append(" and c.redate-1 <= to_date(:redate_end,'yyyy-MM-dd')");
        Query hqlQuery = this.getSession().createSQLQuery(sql.toString());
        hqlQuery.setParameter("reseat", cusnoteDto.getReseat());
        hqlQuery.setParameter("gendate_begin", cusnoteDto.getGendate_begin());
        hqlQuery.setParameter("gendate_end", cusnoteDto.getGendate_end());
        if (StringUtils.isNotBlank(cusnoteDto.getIsreplay())) hqlQuery.setParameter("isreplay", cusnoteDto.getIsreplay());
        if (StringUtils.isNotBlank(cusnoteDto.getNoteclass())) hqlQuery.setParameter("noteclass", cusnoteDto.getNoteclass());
        if (StringUtils.isNotBlank(cusnoteDto.getGenseat())) hqlQuery.setParameter("genseat", cusnoteDto.getGenseat());
        if (StringUtils.isNotBlank(cusnoteDto.getRedate_begin())) hqlQuery.setParameter("redate_begin", cusnoteDto.getRedate_begin());
        if (StringUtils.isNotBlank(cusnoteDto.getRedate_end())) hqlQuery.setParameter("redate_end", cusnoteDto.getRedate_end());
        hqlQuery.setFirstResult(dataGrid.getStartRow());
        hqlQuery.setMaxResults(dataGrid.getRows());
        return convertToCusnoteList(hqlQuery.list());
    }

    @Override
    public int queryCusnoteCount(CusnoteDto cusnoteDto) {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) from iagent.CUSNOTE c left join iagent.ORDERHIST o on o.orderid=c.orderid where c.gendate >= to_date(:gendate_begin,'yyyy-MM-dd') and c.gendate-1 <= to_date(:gendate_end,'yyyy-MM-dd') and o.crusr=:reseat ");
        if (StringUtils.isNotBlank(cusnoteDto.getIsreplay())) sql.append(" and c.isreplay = :isreplay");
        if (StringUtils.isNotBlank(cusnoteDto.getNoteclass())) sql.append(" and c.noteclass = :noteclass");
        if (StringUtils.isNotBlank(cusnoteDto.getGenseat())) sql.append(" and c.genseat = :genseat");
        if (StringUtils.isNotBlank(cusnoteDto.getRedate_begin())) sql.append(" and c.redate >= to_date(:redate_begin,'yyyy-MM-dd')");
        if (StringUtils.isNotBlank(cusnoteDto.getRedate_end())) sql.append(" and c.redate-1 <= to_date(:redate_end,'yyyy-MM-dd')");
        Query hqlQuery = this.getSession().createSQLQuery(sql.toString());
        hqlQuery.setParameter("reseat", cusnoteDto.getReseat());
        hqlQuery.setParameter("gendate_begin", cusnoteDto.getGendate_begin());
        hqlQuery.setParameter("gendate_end", cusnoteDto.getGendate_end());
        if (StringUtils.isNotBlank(cusnoteDto.getIsreplay())) hqlQuery.setParameter("isreplay", cusnoteDto.getIsreplay());
        if (StringUtils.isNotBlank(cusnoteDto.getNoteclass())) hqlQuery.setParameter("noteclass", cusnoteDto.getNoteclass());
        if (StringUtils.isNotBlank(cusnoteDto.getGenseat())) hqlQuery.setParameter("genseat", cusnoteDto.getGenseat());
        if (StringUtils.isNotBlank(cusnoteDto.getRedate_begin())) hqlQuery.setParameter("redate_begin", cusnoteDto.getRedate_begin());
        if (StringUtils.isNotBlank(cusnoteDto.getRedate_end())) hqlQuery.setParameter("redate_end", cusnoteDto.getRedate_end());
        return Integer.valueOf(hqlQuery.list().get(0).toString());
    }

    @Override
    public boolean haveNoRepalyNotice(String userId, String customerId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select c.orderid from iagent.CUSNOTE c left join iagent.ORDERHIST o on o.orderid=c.orderid where c.gendate > sysdate-1 and c.isreplay=0 ");
        sql.append(" and o.crusr=:userId ");
        sql.append(" and o.contactid=:contactId ");
        Query hqlQuery = this.getSession().createSQLQuery(sql.toString());
        hqlQuery.setParameter("userId", userId);
        hqlQuery.setParameter("contactId", customerId);
        if (hqlQuery.list().size() > 0) {
            logger.info("坐席对该客户有一天内未回复的客服通知,允许坐席新建任务外拨. " + userId + " " + customerId + " " + new Date());
            return true;
        }
        return false;
    }

    @Override
    public int queryCountNoRepalyNotice(String userId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) from iagent.CUSNOTE c inner join iagent.ORDERHIST o on o.orderid=c.orderid and c.isreplay=0 and c.gendate > sysdate-1 ");
        sql.append(" and o.crusr=:userId ");
        Query hqlQuery = this.getSession().createSQLQuery(sql.toString());
        hqlQuery.setParameter("userId", userId);
        return Integer.valueOf(hqlQuery.list().get(0).toString());
    }
}
