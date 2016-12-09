package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.AddressExt;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none
 * </dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>none
 * </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-3 下午3:15:53
 */
public interface AddressExtService extends GenericService<AddressExt, String> {

    public AddressExt get(String id);

}
