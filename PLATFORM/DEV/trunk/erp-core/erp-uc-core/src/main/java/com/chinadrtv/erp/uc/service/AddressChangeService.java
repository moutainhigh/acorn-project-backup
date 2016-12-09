package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.AddressChange;

/**
 * Created with IntelliJ IDEA.
 * Title: ContactChangeService
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface AddressChangeService extends GenericService<AddressChange, Long> {
    /**
     * 根据bpmInstID查找
     *
     * @param bpmInstID
     * @return
     */
    AddressChange queryByBpmInstID(String bpmInstID);
}
