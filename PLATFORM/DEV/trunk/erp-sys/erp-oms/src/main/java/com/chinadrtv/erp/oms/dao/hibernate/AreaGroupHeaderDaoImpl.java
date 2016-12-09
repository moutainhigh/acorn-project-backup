package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.AreaGroup;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dao.AreaGroupHeaderDao;
import com.chinadrtv.erp.oms.dto.AddressDto;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-14
 * Time: 下午3:58
 * To change this template use File | Settings | File Templates.
 */
@Repository("AreaGroupHeaderDao")
public class AreaGroupHeaderDaoImpl extends GenericDaoHibernate<AreaGroup,Long> implements AreaGroupHeaderDao {
    public AreaGroupHeaderDaoImpl()
    {
        super(AreaGroup.class);
    }

   //分页查询
    public List<AddressDto> getAddressDtoList(AddressDto addressDto,DataGridModel dataModel) {
        StringBuilder sb = new StringBuilder();
        sb.append("select t2.areagroup_id,t2.areagroup_name,t2.channel_name,t2.name,");
        sb.append("t2.warehouse_name,t2.priority,t2.crdt,t2.cruser,t2.isactive from (");
        sb.append("select rownum r,a.areagroup_id,a.areagroup_name,d.channel_name,");
        sb.append("c.name,b.warehouse_name,b.priority,a.crdt,a.cruser,a.isactive ");
        sb.append("from oms_area_group_header a left join oms_delivery_regulation b ");
        sb.append("on a.areagroup_id = b.area_group_id and a.channel_id = b.channel_id");
        sb.append(" left join acoapp_oms.company c on b.company_id = c.companyid");
        sb.append(" left join acoapp_oms.oms_channel d on a.channel_id = d.channel_id");
        sb.append(" where 1=1");
        this.genSql(sb,addressDto);  //查询条件sql语句拼接
        sb.append(") t2 where t2.r >:page and r <=:rows");

        SQLQuery sqlQuery = this.getSession().createSQLQuery(sb.toString());

        if(!StringUtils.isEmpty(addressDto.getAreagroupId()))
        {   //地址组id
            sqlQuery.setInteger("Areagroupid",Integer.parseInt(addressDto.getAreagroupId()));
        }
        if(!StringUtils.isEmpty(addressDto.getAreagroupName()))
        {   //地址组名称
            sqlQuery.setString("Areagroupname", addressDto.getAreagroupName());
        }
        if(!StringUtils.isEmpty(addressDto.getPriority()))
        {   //优先级别
            sqlQuery.setInteger("Priority",Integer.parseInt(addressDto.getPriority()));
        }
        if(!StringUtils.isEmpty(addressDto.getCommonCarrier()))
        {   //承运商
            sqlQuery.setInteger("CompanyId", Integer.parseInt(addressDto.getCommonCarrier()));
        }
        if(!StringUtils.isEmpty(addressDto.getChannelId()))
        {   //渠道id
            sqlQuery.setInteger("Channelid",Integer.parseInt(addressDto.getChannelId()));
        }
        sqlQuery.setInteger("page", dataModel.getStartRow());
        sqlQuery.setInteger("rows", dataModel.getStartRow()+dataModel.getRows());

        List objList = sqlQuery.list();
        List<AddressDto> result = new ArrayList<AddressDto>();
        Object[] obj = null;
        AddressDto ad = null;
        for(int i=0;i<objList.size();i++)
        {
            obj = (Object[])objList.get(i);
            ad = new AddressDto();
            ad.setAreagroupId(obj[0] != null ? obj[0].toString() : null);
            ad.setAreagroupName(obj[1] != null ? obj[1].toString() : null);
            ad.setChannelId(obj[2] != null ? obj[2].toString() : null);
            ad.setCommonCarrier(obj[3] != null ? obj[3].toString() : null);
            ad.setWarehouseName(obj[4] != null ? obj[4].toString() : null);
            ad.setPriority(obj[5] != null ? obj[5].toString() : null);
            ad.setCrdt(obj[6] != null ? obj[6].toString() : null);
            ad.setCruser(obj[7] != null ? obj[7].toString() : null);
            ad.setStatus(obj[8] != null ? obj[8].toString() : null);
            result.add(ad);
        }
        return result;
    }

    //获取信息条数
    public Integer queryCount(AddressDto addressDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(a.areagroup_id)");
        sql.append(" from acoapp_oms.oms_area_group_header a left join acoapp_oms.oms_delivery_regulation b");
        sql.append(" on a.areagroup_id = b.area_group_id and a.channel_id = b.channel_id");
        sql.append(" where 1=1");

        this.genSql(sql,addressDto);   //查询条件sql语句拼接
        Query sqlQuery = this.getSession().createSQLQuery(sql.toString());
        if(!StringUtils.isEmpty(addressDto.getAreagroupId()))
        {   //地址组id
            sqlQuery.setInteger("Areagroupid",Integer.parseInt(addressDto.getAreagroupId()));
        }
        if(!StringUtils.isEmpty(addressDto.getAreagroupName()))
        {   //地址组名称
            sqlQuery.setString("Areagroupname", addressDto.getAreagroupName());
        }
        if(!StringUtils.isEmpty(addressDto.getPriority()))
        {   //优先级别
            sqlQuery.setInteger("Priority",Integer.parseInt(addressDto.getPriority()));
        }
        if(!StringUtils.isEmpty(addressDto.getCommonCarrier()))
        {   //承运商
            sqlQuery.setInteger("CompanyId", Integer.parseInt(addressDto.getCommonCarrier()));
        }
        if(!StringUtils.isEmpty(addressDto.getChannelId()))
        {   //渠道id
            sqlQuery.setInteger("Channelid",Integer.parseInt(addressDto.getChannelId()));
        }

        List list = sqlQuery.list();
        Integer result = ((BigDecimal)list.get(0)).intValue();
        return result;
    }
    private void genSql(StringBuilder sql,AddressDto addressDto)
    {
        if(!StringUtils.isEmpty(addressDto.getAreagroupId()))
        {   //地址组id
            sql.append(" AND a.areagroup_id = :Areagroupid");
        }
        if(!StringUtils.isEmpty(addressDto.getAreagroupName()))
        {   //地址组名称
            sql.append(" AND a.areagroup_name = :Areagroupname");
        }
        if(!StringUtils.isEmpty(addressDto.getPriority()))
        {   //优先级别
            sql.append(" AND b.priority = :Priority");
        }
        if(!StringUtils.isEmpty(addressDto.getCommonCarrier()))
        {   //承运商
            sql.append(" AND b.company_id = :CompanyId");
        }
        if(!StringUtils.isEmpty(addressDto.getChannelId()))
        {  //渠道id
            sql.append(" AND a.channel_id = :Channelid");
        }
    }

    /**
     * 修改启用或停用状态
     * @param id
     * @param status
     */
    public void updateAreaGroupHeader(Long id, String status) {

        Query sqlQuery = getSession().createSQLQuery("update acoapp_oms.oms_area_group_header set isactive = :IsActive where areagroup_id = :ID");
        sqlQuery.setParameter("IsActive",status);
        sqlQuery.setParameter("ID",id);
        sqlQuery.executeUpdate();
    }

    /**
     * 新增
     * @param areaGroupHeader
     */
    public void saveAreaGroupHeader(AreaGroup areaGroupHeader) {

        this.getSession().saveOrUpdate(areaGroupHeader);
    }

    /**
     * 判断areaGroupId是否存在
     * @param areaGroupId
     * @return
     */
    public AreaGroup getIsAreaGroup(Long areaGroupId) {
        Query query = getSession().createQuery(" from AreaGroup a where a.id = :ID");
        query.setParameter("ID",areaGroupId);

        return (AreaGroup)query.uniqueResult();
    }
}
