package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.ic.dao.CompanyDeliverySpanDao;
import com.chinadrtv.erp.ic.model.CompanyDeliverySpan;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 承运商配送范围与配送时效
 * User: gaodejian
 * Date: 13-5-8
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class CompanyDeliverySpanDaoImpl implements CompanyDeliverySpanDao {

    private HibernateTemplate hibernateTemplate;

    public CompanyDeliverySpan getDeliverySpan(
            String companyId,
            Long orderTypeId,
            Long payTypeId,
            Long provinceId,
            Long cityId,
            Long countyId,
            Long areaId)
    {
        Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        if(session != null){
            Query q = session.createSQLQuery(
                    "select c.companyId, c.name as companyName,   \n" +
                            "b.warehouse_id as warehouseId, b.warehouse_name as warehouseName,       \n" +
                            "a.provience_id as provinceId, d.chinese as provinceName,       \n" +
                            "a.city_id as cityId, e.cityName, a.county_id as countyId, f.countyName,       \n" +
                            "a.area_id as areaId, g.areaName, a.user_def1 as userDef1 \n" +
                            "from acoapp_oms.oms_areagroup_detail a\n" +
                            "left join acoapp_oms.oms_delivery_regulation b on a.area_group_id = b.area_group_id and b.IS_ACTIVE='Y' \n" +
                            "and b.channel_id in (select x.channel_id from acoapp_oms.OMS_ORDER_PAY_TYPE x where x.order_type_id = :orderTypeId and x.pay_type_id= :payTypeId)\n"+
                            "left join iagent.company c on b.company_id = c.companyid\n" +
                            "left join acoapp_oms.province d on a.provience_id = d.provinceid \n" +
                            "left join acoapp_oms.city_all e on a.city_id = e.cityid \n" +
                            "left join acoapp_oms.county_all f on a.county_id = f.countyid \n" +
                            "left join acoapp_oms.area_all g on a.area_id = g.areaid \n" +
                            "where rownum < 2 and c.companyId=:companyId  and a.provience_id =:provinceId and a.city_id =:cityId and a.county_id=:countyId and a.area_id=:areaId\n" +
                            "order by b.priority asc")
                    .addScalar("companyId", StringType.INSTANCE)
                    .addScalar("companyName", StringType.INSTANCE)
                    .addScalar("warehouseId", LongType.INSTANCE)
                    .addScalar("warehouseName", StringType.INSTANCE)
                    .addScalar("provinceId", LongType.INSTANCE)
                    .addScalar("provinceName", StringType.INSTANCE)
                    .addScalar("cityId", LongType.INSTANCE)
                    .addScalar("cityName", StringType.INSTANCE)
                    .addScalar("countyId", LongType.INSTANCE)
                    .addScalar("countyName", StringType.INSTANCE)
                    .addScalar("areaId", LongType.INSTANCE)
                    .addScalar("areaName", StringType.INSTANCE)
                    .addScalar("userDef1", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(CompanyDeliverySpan.class));;

            q.setParameter("companyId", companyId);
            q.setParameter("orderTypeId", orderTypeId != null ? orderTypeId : 0L);
            q.setParameter("payTypeId", payTypeId != null ? payTypeId : 0L);
            q.setParameter("provinceId", provinceId != null ? provinceId : 0L);
            q.setParameter("cityId", cityId != null ? cityId : 0L);
            q.setParameter("countyId", countyId != null ? countyId : 0L);
            q.setParameter("areaId", areaId != null ? areaId : 0L);

            List list = q.list();
            if(list != null && list.size() > 0){
                return (CompanyDeliverySpan)list.get(0);
            }
        }
        return null;
    }

    public List<CompanyDeliverySpan> getDeliveryScope(String companyId)
    {
        Session session =  SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
        if(session != null){
            Query q = session.createSQLQuery(
                    "select a.companyId, a.name as companyName," +
                    "       b.warehouse_id as warehouseId, b.warehouse_name as warehouseName," +
                    "       c.provience_id as provinceId, d.chinese as provinceName," +
                    "       c.city_id as cityId, e.cityName, c.county_id as countyId, f.countyName," +
                    "       c.area_id as areaId, g.areaName, c.user_def1 as userDef1 " +
                    "from iagent.company a " +
                    "left join acoapp_oms.oms_delivery_regulation b on b.company_id = a.companyid " +
                    "left join acoapp_oms.oms_areagroup_detail c on c.area_group_id = b.area_group_id " +
                    "left join acoapp_oms.province d on c.provience_id = d.provinceid " +
                    "left join acoapp_oms.city_all e on c.city_id = e.cityid " +
                    "left join acoapp_oms.county_all f on c.county_id = f.countyid " +
                    "left join acoapp_oms.area_all g on c.area_id = g.areaid " +
                    "where a.companyId=:companyId")
                    .addScalar("companyId", StringType.INSTANCE)
                    .addScalar("companyName", StringType.INSTANCE)
                    .addScalar("warehouseId", LongType.INSTANCE)
                    .addScalar("warehouseName", StringType.INSTANCE)
                    .addScalar("provinceId", StringType.INSTANCE)
                    .addScalar("provinceName", StringType.INSTANCE)
                    .addScalar("cityId", LongType.INSTANCE)
                    .addScalar("cityName", StringType.INSTANCE)
                    .addScalar("countyId", LongType.INSTANCE)
                    .addScalar("countyName", StringType.INSTANCE)
                    .addScalar("areaId", LongType.INSTANCE)
                    .addScalar("areaName", StringType.INSTANCE)
                    .addScalar("userDef1", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(CompanyDeliverySpan.class));;

            q.setParameter("companyId", companyId);
            return q.list();
        } else {
            return new ArrayList<CompanyDeliverySpan>();
        }
    }


    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

}
