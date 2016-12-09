package com.chinadrtv.uam.dao.impl;

import com.chinadrtv.uam.dao.ResMenuDao;
import com.chinadrtv.uam.dto.ResMenuTreeDto;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ResMenuDaoImpl extends HibernateDaoImpl implements ResMenuDao {


    @Override
    public List<ResMenuTreeDto> getRoleListByUserId(Long userId, String siteName, Long parentId) {
        String sql = "select distinct re.id,re.name,re.description,rm.menu_url,rm.parent_id,rm.sort from ACOAPP_UAM.UAM_resource_menu rm "+
                " join ACOAPP_UAM.UAM_resource re on rm.id=re.id "+
                " join ACOAPP_UAM.UAM_role_resource rr on rr.resource_id=rm.id "+
                " join ACOAPP_UAM.UAM_role r on r.id=rr.role_id "+
                " join ACOAPP_UAM.UAM_USER_ROLE ur on ur.role_id=r.id "+
                " join ACOAPP_UAM.UAM_USER u on u.id=ur.user_id "+
                " join ACOAPP_UAM.CAS_REGISTERED_SERVICE crs on r.site_id=crs.id and re.site_id=crs.id "+
                " where u.id=:userId and crs.name=:siteName ";
        if (parentId == null) sql = sql + " and rm.parent_id is null";
        else sql = sql + " and rm.parent_id=:parentId";
        sql = sql + " order by rm.sort";

        SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);

        sqlQuery.setLong("userId", userId);
        sqlQuery.setString("siteName", siteName);
        if (parentId != null) {
            sqlQuery.setLong("parentId", parentId);
        }

        List<ResMenuTreeDto> result = new ArrayList<ResMenuTreeDto>();
        Object[] obj = null;
        List objList = sqlQuery.list();
        for(int i = 0; i < objList.size(); i++) {
            obj = (Object[])objList.get(i);
            ResMenuTreeDto resMenu = new ResMenuTreeDto();
            resMenu.setId((Long.valueOf(((BigDecimal)obj[0]).toString())));
            resMenu.setName(obj[1].toString());
            resMenu.setDescription(obj[2].toString());
            resMenu.setMenuUrl(obj[3].toString());
            resMenu.setParentId(obj[4] != null ? (Long.valueOf(((BigDecimal)obj[0]).toString())) : null);
            resMenu.setSort((Integer.valueOf(((BigDecimal)obj[0]).toString())));
            result.add(resMenu);
        }
        return result;
    }
}
