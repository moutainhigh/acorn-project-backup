package com.chinadrtv.erp.marketing.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.dao.BaseCallInfoDao;
import com.chinadrtv.erp.model.marketing.BaseCallInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-24
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BaseCallInfoDaoImpl extends
        GenericDaoHibernate<BaseCallInfo, Long> implements
        BaseCallInfoDao {

    public BaseCallInfoDaoImpl() {
        super(BaseCallInfo.class);
    }

    public List<BaseCallInfo> getActiveBaseCalls(){
        return findList("from BaseCallInfo a where a.active=:active", new ParameterInteger("active", 1));
    }

    public void excludeCallNumber(String callerId){
        BaseCallInfo info = new BaseCallInfo();
        info.setInclusion(".*");
        info.setExclusion(callerId);
        info.setActive(true);
        saveOrUpdate(info);
        /*
        execUpdate("insert into BaseCallInfo(inclusion,exclusion,active) values(:inclusion, :exclusion, 1)",
                new ParameterString("inclusion", "*"),
                new ParameterString("exclusion", callerId)
                );
        */
    }
}
