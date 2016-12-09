package com.chinadrtv.erp.admin.dao;


import java.util.List;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.admin.model.FreightLine;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-8-10
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public interface FreightLineDao extends GenericDao<FreightLine, Long> {
    List<FreightLine> getFreightLines(String companyId);
    List<FreightLine> getLegFreightLines(Long legId);
    List<FreightLine> getQuotaFreightLines(Long quotaId);
    List<FreightLine> getVersionFreightLines(Long versionId);
}
