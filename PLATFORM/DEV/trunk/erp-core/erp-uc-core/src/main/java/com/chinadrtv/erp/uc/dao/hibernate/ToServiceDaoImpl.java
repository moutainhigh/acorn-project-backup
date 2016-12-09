package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.ToService;
import com.chinadrtv.erp.model.ToserviceId;
import com.chinadrtv.erp.uc.dao.ToServiceDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title: ToServiceDaoImpl
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
@Repository
public class ToServiceDaoImpl extends GenericDaoHibernate<ToService, ToserviceId> implements ToServiceDao {

    public ToServiceDaoImpl() {
        super(ToService.class);
    }

    @Override
    public int findComplainPageCount(String contactId) {
        String hql = "select count(a.contactId) from ToService a where a.contactId=:contactId";
        Map<String, Parameter> param = new HashMap<String, Parameter>();
        param.put("contactId", new ParameterString("contactId", contactId));
        return this.findPageCount(hql, param);
    }

    @Override
    public List<ToService> findComplain(String contactId, DataGridModel dataGridModel) {
        String hql = "select a from ToService a where a.contactId=:contactId order by a.crtm desc";

        Map<String, Parameter> param = new HashMap<String, Parameter>();
        param.put("contactId", new ParameterString("contactId", contactId));

        Parameter page = new ParameterInteger("page", dataGridModel.getStartRow());
        page.setForPageQuery(true);
        param.put("page", page);

        Parameter rows = new ParameterInteger("rows", dataGridModel.getRows());
        rows.setForPageQuery(true);
        param.put("rows", rows);

        return findPageList(hql, param);
    }
}
