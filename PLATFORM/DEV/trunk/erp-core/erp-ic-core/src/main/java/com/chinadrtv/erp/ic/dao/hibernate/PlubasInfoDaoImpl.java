package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.PlubasInfoDao;
import com.chinadrtv.erp.ic.model.NcPlubasInfo;
import com.chinadrtv.erp.ic.model.NcPlubasInfoAttribute;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 产品档案
 * User: gaodejian
 * Date: 13-2-1
 * Time: 下午1:01 To change this template use
 * File | Settings | File Templates.
 */
@Repository
public class PlubasInfoDaoImpl extends
        GenericDaoHibernateBase<PlubasInfo, Long> implements PlubasInfoDao {

    private static final String CACHE_KEY=  "com.chinadrtv.erp.ic.dao.hibernate.PlubasInfoDaoImpl";

    public PlubasInfoDaoImpl() {
        super(PlubasInfo.class);
    }

    /**
     * 产品精确查询
     * @param nccode
     * @return
     */
    public List<PlubasInfo> getPlubasInfosByNC(String nccode) {
        Session session = getSession();
        Query q = session
                .createQuery("from PlubasInfo a where a.nccode = :nccode");
        q.setString("nccode", nccode);
        return q.list();
    }


    public List<PlubasInfo> getPlubasInfos(String grpid, String keyword, Integer limit){

        Session session = getSession();
        Query q = session.createQuery(
                "from PlubasInfo a where exists(select b.prodId,b.grpId from ProductGrpChannel b where a.nccode=b.prodId and b.grpId = :grpid) " +
                "and (a.nccode like :keyword or a.spellcode like :keyword or (" +
                 reduceLike("lower(a.pluname) like '%s'", keyword.trim().replace("'","")) +
                ")) order by a.nccode asc"
        );
        q.setParameter("grpid", grpid);
        q.setParameter("keyword", keyword+"%");

        q.setFirstResult(0);
        q.setMaxResults(limit);
        return q.list();
    }

    private String reduceLike(String format, String keyword){
        String result = "";
        for(String key : keyword.split("&&")) {
            if(StringUtils.isNotBlank(result)) {
                result += " and ";
            }
            result += String.format(format, "%" + key.trim().toLowerCase() + "%");
        }
        if(StringUtils.isBlank(result)) {
            result = "0=1";
        }
        return result;
    }
    /**
     * 产品精确查询
     * @return
     */
    @Deprecated
    public List<PlubasInfo> getPlubasInfos(String nccode, String pluname, String spellcode, String ncfreename){
        Map<String, Object> params = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }
        if(StringUtils.isNotBlank(pluname)){
            params.put("pluname", pluname);
        }
        if(StringUtils.isNotBlank(spellcode)){
            params.put("spellcode", spellcode);
        }
        if(StringUtils.isNotBlank(ncfreename)){
            params.put("ncfreename", ncfreename);
        }
        return getPlubasInfos(params);
    }
    /**
     * 产品精确查询
     * @param params
     * @return
     */
    public List<PlubasInfo> getPlubasInfos(Map<String, Object> params) {
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery(
                    "from PlubasInfo a where a.ruid > 0 " +
                            (params.containsKey("grpid") ? "and exists(select b.prodId,b.grpId from ProductGrpChannel b where a.nccode=b.prodId and b.grpId = :grpid and b.status='-1') " : "") +
                            (params.containsKey("nccode") ? "and a.nccode = :nccode " : "") +
                            (params.containsKey("plucode") ? "and a.plucode = :plucode " : "") +
                            (params.containsKey("pluname") ? "and a.pluname = :pluname " : "") +
                            (params.containsKey("spellcode") ? "and a.spellcode = :spellcode " : "") +
                            (params.containsKey("spellname") ? "and a.spellname = :spellname " : "") +
                            (params.containsKey("ncfree") ? "and a.ncfree = :ncfree " : "") +
                            (params.containsKey("ncfreename") ? "and a.ncfreename = :ncfreename " : "") +
                            "order by a.nccode asc"
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return q.list();
        } else {
            return new ArrayList<PlubasInfo>();
        }
    }
    /**
     * 渠道商品精确查询
     * @param params
     * @return
     */
    public Long getExactPlubasInfoCountByNC(Map<String, Object> params){
        if(params.size() > 0) {
            Session session = getSession();
            Query q = session.createQuery("select count(a.ruid) from PlubasInfo a where a.ruid > 0 " +
                    (params.containsKey("grpid") ? "and exists(select b.prodId,b.grpId from ProductGrpChannel b where b.prodId=a.nccode and b.grpId = :grpid) " : "") +
                    (params.containsKey("nccode") ? "and a.nccode = :nccode " : " ") +
                    "order by a.nccode asc"
            );
            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return Long.parseLong(q.uniqueResult().toString());
        } else{
            return 0L;
        }
    }
    /**
     * 渠道商品精确查询
     * @param params
     * @param index
     * @param size
     * @return
     */
    public List<PlubasInfo> getExactPlubasInfosByNC(Map<String, Object> params, int index, int size){
        if(params.size() > 0) {
            Session session = getSession();
            Query q = session.createQuery("from PlubasInfo a where a.ruid > 0 " +
                    (params.containsKey("grpid") ? "and exists(select b.prodId,b.grpId from ProductGrpChannel b where b.prodId=a.nccode and b.grpId = :grpid) " : "") +
                    (params.containsKey("nccode") ? "and a.nccode = :nccode " : "") +
                    "order by a.nccode asc"
            );
            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            q.setFirstResult(index);
            q.setMaxResults(size);
            return q.list();
        }
        else{
            return new ArrayList<PlubasInfo>();
        }
    }

    public Long getPlubasInfoCountByNC(Map<String, Object> params){
        if(params.size() > 0) {
            Session session = getSession();
            Query q = session.createQuery(
                    "select count(a.ruid) from PlubasInfo a where a.ruid > 0 " +
                            (params.containsKey("grpid") ? "and exists(select b.prodId,b.grpId from ProductGrpChannel b where b.prodId=a.nccode and b.grpId = :grpid) " : "") +
                            (params.containsKey("nccode") ? "and a.nccode like :nccode " : "") +
                            (params.containsKey("spellcode") ? "and a.spellcode like :spellcode " : "") +
                            (params.containsKey("pluname") ? "and a.pluname like :pluname " : "") +
                            "order by a.nccode asc"
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return Long.parseLong(q.uniqueResult().toString());
        } else {
            return 0L;
        }

    }
    /**
     * 产品模糊查询
     * @param params
     * @return
     */
    public List<PlubasInfo> getPlubasInfosByNC(Map<String, Object> params, int index, int size) {
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createQuery(
                    "from PlubasInfo a where a.ruid > 0 " +
                            (params.containsKey("grpid") ? "and exists(select b.prodId,b.grpId from ProductGrpChannel b where a.nccode=b.prodId and b.grpId = :grpid) " : "") +
                            (params.containsKey("nccode") ? "and a.nccode like :nccode " : "") +
                            (params.containsKey("spellcode") ? "and a.spellcode like :spellcode " : "") +
                            (params.containsKey("pluname") ? "and a.pluname like :pluname " : "") +
                            "order by a.nccode asc"
            );

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)){
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }

            q.setFirstResult(index);
            q.setMaxResults(size);

            return q.list();
        } else {
            return new ArrayList<PlubasInfo>();
        }
    }

    public PlubasInfo getPlubasInfo(String plucode) {
        Session session = getSession();
        Query q = session
                .createQuery("from PlubasInfo a where a.plucode = :plucode");
        q.setString("plucode", plucode);
        List result = q.list();
        return result.size() > 0 ? (PlubasInfo) result.get(0) : null;
    }

    public PlubasInfo getPlubasInfoByplid(Long ruid) {
        Session session = getSession();
        Query q = session.createQuery("from PlubasInfo a where a.ruid = :ruid");
        q.setLong("ruid", ruid);
        List result = q.list();
        return result.size() > 0 ? (PlubasInfo) result.get(0) : null;

    }

    /**
     * 根据套装商品编号获得套装明细
     * @param suiteId
     * @return
     */
    public List<PlubasInfo> getPlubasInfosBySuiteId(String suiteId){
        Session session = getSession();
        //Query q = session.createQuery("select a.suites from PlubasInfo a where a.plucode=:suiteId");
        Query q = session.createSQLQuery("select {a.*} from IAGENT.PLUBASINFO a,IAGENT.PRODUCTSUITETYPE b where a.PLUCODE=b.PRODSCMID and b.PRODSUITESCMID=:suiteId")
                .addEntity("a",PlubasInfo.class);
        q.setString("suiteId", suiteId);
        return q.list();
    }

    @ReadThroughSingleCache(namespace = CACHE_KEY, expiration = 300)
    public List<NcPlubasInfo> getNcPlubasInfos(
            @ParameterValueKeyProvider(order = 0) String grpid,
            @ParameterValueKeyProvider(order = 1) String keyword,
            @ParameterValueKeyProvider(order = 2) Integer index,
            @ParameterValueKeyProvider(order = 3) Integer limit){
        Session session = getSession();
        Query q = session.createSQLQuery(
                "select a.nccode, a.spellcode, replace(a.pluname,a.ncfreename, '') as spellname," +
                "(select LPRICE from IAGENT.productgrplimit c where c.prodid=a.nccode and c.grpId = :grpid and c.status='-1' and c.startdt <= sysdate and c.enddt > sysdate and rownum < 2) as groupPrice," +
                "min(nvl(a.SLPRC,0)) as listPrice, min(nvl(a.HSLPRC,0)) as maxPrice, max(nvl(a.LSLPRC,0)) as minPrice from IAGENT.PLUBASINFO a " +
                "where " +
                "(exists(select b.* from IAGENT.PRODUCT b where a.nccode=b.prodid and b.status='-1') or" +
                " exists(select b.* from IAGENT.PRODUCTGRPCHANNEL b where a.nccode=b.prodid and b.grpId = :grpid and b.status='-1' and b.startdt <= sysdate and b.enddt > sysdate)) " +
                "and (a.nccode like :keyword1 or lower(a.spellcode) like lower(:keyword1) or (" +
                 reduceLike("lower(a.pluname) like '%s'", keyword.replaceAll("'","")) +
                 ")) group by a.nccode,a.spellcode,replace(a.pluname,a.ncfreename, '') order by a.nccode asc"
        )
                .addScalar("nccode", StringType.INSTANCE)
                .addScalar("spellcode", StringType.INSTANCE)
                .addScalar("spellname", StringType.INSTANCE)
                .addScalar("groupPrice", DoubleType.INSTANCE)
                .addScalar("listPrice", DoubleType.INSTANCE)
                .addScalar("maxPrice", DoubleType.INSTANCE)
                .addScalar("minPrice", DoubleType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(NcPlubasInfo.class));

        q.setParameter("grpid", grpid);
        q.setParameter("keyword1", keyword.trim()+"%");
        //q.setParameter("keyword2", "%"+keyword+"%");
        q.setFirstResult(index);
        q.setMaxResults(limit);
        return q.list();
    }

    public List<NcPlubasInfoAttribute> getNcAttributes(String nccode){
        Session session = getSession();
        Query q = session.createSQLQuery("select ncfree,ncfreename from IAGENT.PLUBASINFO where NCFREESTATUS = '1' and nccode=:nccode")
                .addScalar("ncfree", Hibernate.STRING)
                .addScalar("ncfreename", Hibernate.STRING)
                .setResultTransformer(Transformers.aliasToBean(NcPlubasInfoAttribute.class));
        q.setString("nccode", nccode);
        return q.list();
    }

    public List<NcPlubasInfoAttribute> getNcAllAttributes(String nccode) {
        Session session = getSession();
        Query q = session.createSQLQuery("select ncfree,ncfreename from IAGENT.PLUBASINFO where  nccode=:nccode")
                .addScalar("ncfree", Hibernate.STRING)
                .addScalar("ncfreename", Hibernate.STRING)
                .setResultTransformer(Transformers.aliasToBean(NcPlubasInfoAttribute.class));
        q.setString("nccode", nccode);
        return q.list();
    }

    public NcPlubasInfo getNcPlubasInfo(String nccode){
        Session session = getSession();

        Query q = session.createSQLQuery("select distinct a.nccode,replace(a.pluname,a.ncfreename, '') as ncname,a.spellcode, replace(a.pluname,a.ncfreename, '') as spellname from IAGENT.PLUBASINFO a " +
                "where a.nccode = :nccode order by nccode asc")
                .addScalar("nccode", Hibernate.STRING)
                .addScalar("ncname", Hibernate.STRING)
                .addScalar("spellcode", Hibernate.STRING)
                .addScalar("spellname", Hibernate.STRING)
                .setResultTransformer(Transformers.aliasToBean(NcPlubasInfo.class));

        q.setParameter("nccode", nccode);
        List<NcPlubasInfo> list = q.list();
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }




    public List<PlubasInfo> getAllPlubasInfos(String catcode,String pluname,int index ,int size ) {

        StringBuffer sb = new StringBuffer();
        sb.append(" from PlubasInfo a where 1=1 ");
        Query q = initQuery(catcode,pluname,sb);
        q.setFirstResult(index * size);
        q.setMaxResults(size);
        List<PlubasInfo> list = null;
        try{
            list= q.list();
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return list;
    }


    public int getAllPlubasInfosCount(String catcode,String pluname) {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer();
        sb.append("select count(a.ruid) from PlubasInfo a where 1=1 ");
        Query q= initQuery(catcode,pluname,sb);
        List list = null;
        try{
            list = q.list();
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return Integer.valueOf(list.get(0).toString());
    }

    private Query initQuery(String catcode,String pluname,StringBuffer sb){
        if(StringUtils.isNotBlank(catcode)){
            sb.append(" and a.plucode like :catcode");
        }
        if(StringUtils.isNotBlank(pluname)){
            sb.append(" and a.pluname like :pluname");
        }

        Query q = getSession().createQuery(sb.toString());

        if(StringUtils.isNotBlank(catcode)){
            q.setString("catcode", "%"+catcode+"%");
        }
        if(StringUtils.isNotBlank(pluname)){
            q.setString("pluname", "%"+pluname+"%");
        }
        return q;

    }
}
