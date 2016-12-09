package com.chinadrtv.erp.user.service.impl;

import com.chinadrtv.erp.model.agent.Grp;
import com.chinadrtv.erp.user.dao.GrpDao;
import com.chinadrtv.erp.user.model.AgentRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.GrpService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-6-26
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
@Service("GrpServiceImpl")
public class GrpServiceImpl implements GrpService {
    @Autowired
    private GrpDao grpDao;

    public List<Grp> getGrpList() {
        AgentUser user = SecurityHelper.getLoginUser();
        List<String> grpidList = new ArrayList<String>();
        for(GrantedAuthority grantedAuthority :user.getAuthorities()){
            AgentRole agentRole = (AgentRole) grantedAuthority;
            grpidList.add(agentRole.getGrpId());
        }
        grpidList.add(user.getWorkGrp());
        List<Grp> grpList = grpDao.getGrpList(grpidList);
        return  grpList;
    }

    public List<Grp> getGrpList(List<String> grpIdList)
    {
        return grpDao.getGrpList(grpIdList);
    }
}
