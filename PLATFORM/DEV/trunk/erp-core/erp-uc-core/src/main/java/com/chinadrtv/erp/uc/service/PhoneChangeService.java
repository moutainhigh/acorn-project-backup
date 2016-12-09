package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.PhoneChange;

/**
 * Created with IntelliJ IDEA.
 * Title: ContactChangeService
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface PhoneChangeService extends GenericService<PhoneChange, Long> {
    /**
     * 根据bpmInstID查找
     * @param bpmInstID
     * @return
     */
    PhoneChange queryByBpmInstID(String bpmInstID);
}
