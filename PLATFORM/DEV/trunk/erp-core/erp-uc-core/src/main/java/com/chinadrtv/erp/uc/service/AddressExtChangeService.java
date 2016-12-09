package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.AddressExtChange;

/**
 * Created with IntelliJ IDEA.
 * Title: ContactChangeService
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public interface AddressExtChangeService extends GenericService<AddressExtChange, Long> {
    /**
     * 根据bpmInstID查找
     *
     * @param bpmInstID
     * @return
     */
    AddressExtChange queryByBpmInstID(String bpmInstID);
}
