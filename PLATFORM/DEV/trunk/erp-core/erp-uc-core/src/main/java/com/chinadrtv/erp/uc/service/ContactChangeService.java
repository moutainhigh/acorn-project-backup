package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.ContactChange;

/**
 * Created with IntelliJ IDEA.
 * Title: ContactChangeService
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface ContactChangeService extends GenericService<ContactChange, Long> {
    /**
     * 根据bpmInstID查找
     *
     * @param bpmInstID
     * @return ContactChange
     */
    ContactChange queryByBpmInstID(String bpmInstID);
}
