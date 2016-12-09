package com.chinadrtv.erp.uc.dao;

import java.util.List;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.uc.dto.AddressDto;


/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-3 下午3:13:31 
 *
 */
public interface AddressExtDao extends GenericDao<AddressExt, String>{


	/**
	 * <p>分页查询客户地址</p>
	 * @param contactId
	 * @return int
	 */
	public int queryAddressPageCount(String contactId);


	/**
	 * <p>分页查询客户地址</p>
	 * @param contactId
	 * @return List<AddressDto>
	 */
	public List<AddressDto> queryAddressPageList(DataGridModel dataGridModel, String contactId);
	
	/**
	 * <p>查询客户所有地址</p>
	 * @param contactId
	 * @return
	 */
	public List<AddressExt> queryAllAddressByContact(String contactId);
}
