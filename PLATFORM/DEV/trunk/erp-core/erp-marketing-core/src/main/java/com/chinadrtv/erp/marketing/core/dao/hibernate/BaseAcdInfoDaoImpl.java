package com.chinadrtv.erp.marketing.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.dao.BaseAcdInfoDao;
import com.chinadrtv.erp.model.marketing.BaseAcdInfo;
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
public class BaseAcdInfoDaoImpl extends
        GenericDaoHibernate<BaseAcdInfo, Long> implements
        BaseAcdInfoDao {

    public BaseAcdInfoDaoImpl() {
        super(BaseAcdInfo.class);
    }

    public List<BaseAcdInfo> getAllBaseAcds() {
        return findList("from BaseAcdInfo");
    }
    public List<BaseAcdInfo> getBaseAcdsByArea(String area) {
        return findList("from BaseAcdInfo a where a.area=:area", new ParameterString("area", area));
    }

    public List<BaseAcdInfo> getBaseAcdsByDeptNo(String deptNo) {
        return findList("from BaseAcdInfo a where a.deptNo=:deptNo", new ParameterString("deptNo", deptNo));
    }

}
