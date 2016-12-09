package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.uc.dao.AddressDao;
import com.chinadrtv.erp.uc.dao.AddressExtDao;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.util.PojoUtils;

/**
 * 
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author andrew
 * @version 1.0
 * @since 2013-5-3 下午3:15:03
 * 
 */
@Repository
public class AddressExtDaoImpl extends GenericDaoHibernate<AddressExt, String> implements AddressExtDao {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddressExtDaoImpl.class);

	@Autowired
	private AddressDao addressDao;
	
	public AddressExtDaoImpl() {
		super(AddressExt.class);
	}

	/** 
	 * <p>Title: queryAddressPageCount</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return int
	 * @see com.chinadrtv.erp.uc.dao.AddressDao#queryAddressPageCount(java.lang.String)
	 */ 
	@SuppressWarnings("rawtypes")
	public int queryAddressPageCount(String contactId) {
		String hql = "select count(a) from AddressExt a where a.contactId=:contactId";
		Map<String, Parameter> param = new HashMap<String, Parameter>();
		param.put("contactId", new ParameterString("contactId", contactId));
		return this.findPageCount(hql, param);
	}

	/** 
	 * <p>Title: queryAddressPageList</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return List<AddressDto>
	 * @see com.chinadrtv.erp.uc.dao.AddressDao#queryAddressPageList(java.lang.String)
	 */ 
	@SuppressWarnings("rawtypes")
    public List<AddressDto> queryAddressPageList(DataGridModel dataGridModel, String contactId) {
        String hql = "select a from AddressExt a where a.contactId=:contactId";

        Map<String, Parameter> param = new HashMap<String, Parameter>();
        param.put("contactId", new ParameterString("contactId", contactId));

        Parameter page = new ParameterInteger("page", dataGridModel.getStartRow());
        page.setForPageQuery(true);
        param.put("page", page);

        Parameter rows = new ParameterInteger("rows", dataGridModel.getRows());
        rows.setForPageQuery(true);
        param.put("rows", rows);

        List<AddressExt> addressExtList =  this.findPageList(hql, param);

        List<AddressDto> addressDtoList = new ArrayList<AddressDto>();
        for(AddressExt ae : addressExtList){
            Address address = addressDao.get(ae.getAddressId());
            AddressDto addressDto = (AddressDto) PojoUtils.convertPojo2Dto(address, AddressDto.class);
            addressDto.setAddress(ae.getAddressDesc());
            addressDto.setAddressContactId(address.getContactid());
            try {
                addressDto.setCountyId(null==ae.getCounty() ? null : ae.getCounty().getCountyid());
                addressDto.setCityId(null==ae.getCounty() ? null : ae.getCity().getCityid());
                addressDto.setAreaid(null==ae.getArea() ? null : ae.getArea().getAreaid());
                addressDto.setProvinceName(null==ae.getProvince() ? null : ae.getProvince().getChinese());
                addressDto.setCityName(null==ae.getCity() ? null : ae.getCity().getCityname());
                addressDto.setCountyName(null==ae.getCounty() ? null : ae.getCounty().getCountyname());
                addressDto.setAreaName(null==ae.getArea() ? null : ae.getArea().getAreaname());
            } catch (Exception e) {
                logger.error("四级地址配置表中没有对应的数据。");
            }
            addressDto.setAuditState(ae.getAuditState());
//            addressDto.setBlack(address.getBlack());
            addressDtoList.add(addressDto);
        }

        return addressDtoList;
    }

	/** 
	 * <p>Title: 查询客户所有地址</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return List<AddressExt>
	 * @see com.chinadrtv.erp.uc.dao.AddressExtDao#queryAllAddressByContact(java.lang.String)
	 */ 
	public List<AddressExt> queryAllAddressByContact(String contactId) {
		String hql = "from AddressExt a where a.contactId=:contactId";
		return this.findList(hql, new ParameterString("contactId", contactId));
	}
}
