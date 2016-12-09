package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.uc.dto.IvrGrpNumCti;
import com.chinadrtv.erp.uc.dto.IvrDeptRate;
import com.chinadrtv.erp.uc.dto.IvrUser;
import com.chinadrtv.erp.uc.dto.Ivrdist;
import com.chinadrtv.erp.uc.dto.WilcomCallDetail;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.user.model.AgentUser;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-8
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
public interface IvrcallbackDao {
    List<IvrGrpNumCti> getIvrGrpNumCtis(String deptId);
    List<IvrGrpNumCti> getIvrGrpNumAllCtis();
    List<IvrDeptRate> getIvrDeptRates(String ivrType);
    IvrUser getIvrUser(String userId);
    Ivrdist getIvrdist(String caseid);
    HashMap<String, Long> findAllocatedNumbers(CallbackSpecification specification);
    List<Ivrdist> findAllocatedIvrdists(CallbackSpecification specification);
    void CreateIvrdist(AgentUser user, WilcomCallDetail callDetail, String owner, String grpid);
}
