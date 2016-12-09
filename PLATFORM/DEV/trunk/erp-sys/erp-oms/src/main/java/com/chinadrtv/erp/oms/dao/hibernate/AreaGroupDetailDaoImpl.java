package com.chinadrtv.erp.oms.dao.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.model.AreaGroupDetail;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dao.AreaGroupDetailDao;
import com.chinadrtv.erp.oms.dto.AreaGroupDetailDto;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-20
 * Time: 下午2:14
 * To change this template use File | Settings | File Templates.
 */
@Repository("areaGroupDetailDao")
public class AreaGroupDetailDaoImpl extends GenericDaoHibernate<AreaGroupDetail,Long> implements AreaGroupDetailDao {
    public AreaGroupDetailDaoImpl()
    {
        super(AreaGroupDetail.class);
    }

    /**
     * 数据导入
     * @param areaGroupDetail
     */
    @InvalidateSingleCache(namespace = "com.chinadrtv.erp.model.AreaGroupDetail")
    public void saveAreaGroupDetail(@ParameterValueKeyProvider AreaGroupDetail areaGroupDetail) {

        this.getSession().saveOrUpdate(areaGroupDetail);
    }

    /**
     * 分页显示地址组明细
     * @param areaGroupId
     * @param dataModel
     * @return
     */
    public List<AreaGroupDetailDto> getAreaGroupDetail(Long areaGroupId, DataGridModel dataModel) {
        StringBuilder sql = new StringBuilder();
        sql.append("select t.area_group_id,t.provience_id, t.chinese,t.city_id,t.cityname,t.county_id,");
        sql.append("t.countyname,t.area_id,t.areaname,t.user_def1 from (");
        sql.append("select rownum r,a.area_group_id,a.provience_id, p.chinese,a.city_id,c.cityname,");
        sql.append("a.county_id,cou.countyname,a.area_id,ar.areaname,a.user_def1");
        sql.append(" from acoapp_oms.oms_areagroup_detail a,iagent.province p,iagent.city_all c,iagent.county_all cou,iagent.area_all ar");
        sql.append(" where a.provience_id = p.provinceid and a.city_id = c.cityid and a.county_id = cou.countyid and a.area_id = ar.areaid");
        sql.append(" and a.area_group_id = :ID");
        sql.append(") t where t.r >:page and t.r <=:rows");

        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("ID",areaGroupId);
        sqlQuery.setParameter("page",dataModel.getStartRow());
        sqlQuery.setParameter("rows",dataModel.getStartRow()+dataModel.getRows());

        List objList = sqlQuery.list();
        List<AreaGroupDetailDto> result = new ArrayList<AreaGroupDetailDto>();
        Object[] obj = null;
        AreaGroupDetailDto agd = null;
        for(int i=0;i<objList.size();i++)
        {
            obj = (Object[])objList.get(i);
            agd = new AreaGroupDetailDto();
            agd.setAreaGroupId(obj[0] != null ? obj[0].toString() : null);
            agd.setProvinceId(obj[1] != null ? obj[1].toString() : null);
            agd.setProvinceName(obj[2] != null ? obj[2].toString() : null);
            agd.setCityId(obj[3] != null ? obj[3].toString() : null);
            agd.setCityName(obj[4] != null ? obj[4].toString() : null);
            agd.setCountyId(obj[5] != null ? obj[5].toString() : null);
            agd.setCountyName(obj[6] != null ? obj[6].toString() : null);
            agd.setAreaId(obj[7] != null ? obj[7].toString() : null);
            agd.setAreaName(obj[8] != null ? obj[8].toString() : null);
            agd.setSendTime(obj[9] != null ? obj[9].toString() : null);
            result.add(agd);
        }

        return result;
    }

    /**
     * 获取信息总条数
     * @return
     */
    public Integer queryCount(Long aId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(a.area_group_id) from acoapp_oms.oms_areagroup_detail a");
        sql.append("  where a.area_group_id = :ID");
        Query sqlQuery = getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("ID",aId);

        List list = sqlQuery.list();
        Integer result = ((BigDecimal)list.get(0)).intValue();
        return result;
    }

    /**
     * 地址组明细导出
     * @param areaGroupId
     * @return
     */
    public List getAreaGroupDetail(Long areaGroupId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select a.area_group_id,a.provience_id, p.chinese,a.city_id,c.cityname,");
        sql.append("a.county_id,cou.countyname,a.area_id,ar.areaname,a.user_def1");
        sql.append(" from acoapp_oms.oms_areagroup_detail a,iagent.province p,iagent.city_all c,iagent.county_all cou,iagent.area_all ar");
        sql.append(" where a.provience_id = p.provinceid and a.city_id = c.cityid and a.county_id = cou.countyid and a.area_id = ar.areaid");
        sql.append(" and a.area_group_id = :ID");

        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("ID",areaGroupId);

        List objList = sqlQuery.list();

        return objList;
    }

	/* 
	 * 根据AreaGroupID 获取 AreaGroupDetail List
	* <p>Title: getDetailListByGroupId</p>
	* <p>Description: </p>
	* @param areaGroupId
	* @return
	* @see com.chinadrtv.erp.oms.dao.AreaGroupDetailDao#getDetailListByGroupId(java.lang.Long)
	*/ 
	public List<AreaGroupDetail> getDetailListByGroupId(Long areaGroupId) {
		String hql = "select agd from AreaGroupDetail agd where agd.areaGroup.id=:areaGroupId";
		return this.findList(hql, new ParameterLong("areaGroupId", areaGroupId));
	}
    /**
     * 删除明细
     * @param areId
     * @return
     */
    public boolean deleteByAreaGroupId(Long areId) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete acoapp_oms.oms_areagroup_detail a where a.area_group_id = :AreId");
        Query sqlQuery = getSession().createSQLQuery(sql.toString());
        sqlQuery.setParameter("AreId",areId);
        int i = sqlQuery.executeUpdate();
        if(i>0)
        {
            return true;
        }
        return false;
    }
}
