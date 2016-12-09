package com.chinadrtv.erp.uc.service.impl;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.AddressChange;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.AddressExtChange;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.uc.constants.AddressConstant;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dao.AddressChangeDao;
import com.chinadrtv.erp.uc.dao.AddressDao;
import com.chinadrtv.erp.uc.dao.AddressExtChangeDao;
import com.chinadrtv.erp.uc.dao.AddressExtDao;
import com.chinadrtv.erp.uc.dao.AreaDao;
import com.chinadrtv.erp.uc.dao.CityDao;
import com.chinadrtv.erp.uc.dao.CountyDao;
import com.chinadrtv.erp.uc.dao.OrderDao;
import com.chinadrtv.erp.uc.dao.ProvinceDao;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.util.PojoUtils;

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
 * @since 2013-5-3 下午1:55:00
 */
@Service
public class AddressServiceImpl extends GenericServiceImpl<Address, String> implements AddressService {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private AddressExtDao addressExtDao;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private CountyDao countyDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserBpmTaskService userBpmTaskService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private AddressChangeDao addressChangeDao;
    @Autowired
    private AddressExtChangeDao addressExtChangeDao;


    @Override
    protected GenericDao<Address, String> getGenericDao() {
        return addressDao;
    }

    @Override
    public Address get(String id) {
        return addressDao.get(id);
    }

    /**
     * <p>Title: queryAddress</p>
     * <p>Description: 查询地址</p>
     *
     * @param addressId
     * @return AddressDto
     * @see com.chinadrtv.erp.uc.service.AddressService#queryAddress(java.lang.String)
     */
    public AddressDto queryAddress(String addressId) {
        Address address = addressDao.get(addressId);
        AddressExt addressExt = addressExtDao.get(addressId);
        AddressDto addressDto = (AddressDto) PojoUtils.convertPojo2Dto(address, AddressDto.class);
        addressDto.setContactid(addressExt.getContactId());
        try {
            addressDto.setCountyId(null == addressExt.getCounty() ? null : addressExt.getCounty().getCountyid());
            addressDto.setCityId(null == addressExt.getCity() ? null : addressExt.getCity().getCityid());
            addressDto.setAreaid(null == addressExt.getArea() ? null : addressExt.getArea().getAreaid());

            addressDto.setProvinceName(null == addressExt.getProvince() ? null : addressExt.getProvince().getChinese());
            addressDto.setCityName(null == addressExt.getCity() ? null : addressExt.getCity().getCityname());
            addressDto.setCountyName(null == addressExt.getCounty() ? null : addressExt.getCounty().getCountyname());
            addressDto.setCountySpellid(null == addressExt.getCounty() ? null : addressExt.getCounty().getSpellid());
            addressDto.setAreaName(null == addressExt.getArea() ? null : addressExt.getArea().getAreaname());
            addressDto.setAreaSpellid(null == addressExt.getArea() ? null : addressExt.getArea().getSpellid());
        } catch (Exception e) {
            logger.error("四级地址配置表中没有对应的数据。");
        }
        addressDto.setAddressDesc(addressExt.getAddressDesc());
        addressDto.setReceiveAddress(addressConcordancyNoAddressInfo(addressDto));
        addressDto.setAuditState(addressExt.getAuditState());
        return addressDto;
    }


    /**
     * <p>Title: 保存地址信息</p>
     * <p>Description: </p>
     *
     * @param addressDto
     * @return Address
     * @see com.chinadrtv.erp.uc.service.AddressService#saveAddress(com.chinadrtv.erp.model.Address)
     */
    public Address addAddress(AddressDto addressDto) {

        boolean existsMainAddress = checkMainAddress(addressDto);
        if (existsMainAddress) {
            throw new BizException("编号为[" + addressDto.getContactid() + "]联系人已经存在主地址");
        }

        Address address = (Address) PojoUtils.convertDto2Pojo(addressDto, Address.class);

        AddressExt addressExt = new AddressExt();
        addressExt.setAddressType("0");
        //FIXME address 属性 addressDesc属性混乱
        addressExt.setAddressDesc(addressDto.getAddress());
        Province province = provinceDao.get(addressDto.getState());
        addressExt.setProvince(province);

        CountyAll county = countyDao.get(addressDto.getCountyId());
        addressExt.setCounty(county);

        CityAll city = cityDao.get(new Integer(addressDto.getCityId()));
        address.setCity(city.getCode());
        addressExt.setCity(city);

        Integer areaId = addressDto.getAreaid();
        AreaAll area = null;
        if (null != areaId) {
            try {
                area = areaDao.get(areaId);
                addressExt.setArea(area);
                addressExt.setAreaName(area.getAreaname());
            } catch (ObjectRetrievalFailureException e) {
                logger.error("",e);
            }
            if (null != area && null != area.getSpellid()) {
                address.setArea(area.getSpellid().toString());
            } else {
                address.setArea(null == county.getSpellid() ? null : county.getSpellid().toString());
            }
        } else {
            address.setArea(null == county.getSpellid() ? null : county.getSpellid().toString());
        }

        String fullAddress = this.addressConcordancy(addressDto);
        address.setAddress(fullAddress);

        addressExt.setUptime(new Date());

        //一定要放在设置主地址前面
        addressExt.setContactId(address.getContactid());

        //如果是非主地址
        if (!address.getIsdefault().equals(AddressConstant.CONTACT_MAIN_ADDRESS)) {
            String secondarySeq = addressDao.getContactSecondarySeq();
            address.setContactid(secondarySeq);
        }

        String seq = addressDao.getSequence();
        address.setAddressid(seq);
        address = addressDao.save(address);

        //保存AddressExt
        addressExt.setAddressId(address.getAddressid());
        addressExt.setUptime(new Date());

        addressExt.setAuditState(addressDto.getAuditState());

        addressExtDao.saveOrUpdate(addressExt);

        return address;
    }


    /**
     * <p>Title: addAddress</p>
     * <p>Description: 批量添加地址</p>
     *
     * @param addressDtoList
     * @return Integer
     * @see com.chinadrtv.erp.uc.service.AddressService#addAddress(java.util.List)
     */
    @Override
    public int addAddress(List<AddressDto> addressDtoList) {
        int effected = 0;
        for (AddressDto dto : addressDtoList) {
            this.addAddress(dto);
            effected += 1;
        }
        return effected;
    }

    /**
     * <p>检查是否存在主地址</p>
     *
     * @param addressDto
     * @return boolean
     */
    private boolean checkMainAddress(AddressDto addressDto) {
        //如果该地址不是主地址
        if (!addressDto.getIsdefault().equals(AddressConstant.CONTACT_MAIN_ADDRESS)) {
            return false;
        }

        Address address = addressDao.getContactMainAddress(addressDto.getContactid());
        sessionFactory.getCurrentSession().evict(address);
        if (null != address) {
            if (null == addressDto.getAddressid() || "".equals(addressDto.getAddressid())) {
                return true;
            } else {
                if (addressDto.getAddressid().equals(address.getAddressid())) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>Title: API-UC-13.更新客户地址</p>
     * <p>Description: </p>
     *
     * @param addressDto
     * @return Address
     * @see com.chinadrtv.erp.uc.service.AddressService#updateAddress(com.chinadrtv.erp.uc.dto.AddressDto)
     */
    public Address updateAddress(AddressDto addressDto) {
        boolean existsMainAddress = checkMainAddress(addressDto);
        if (existsMainAddress) {
            throw new BizException("编号为[" + addressDto.getContactid() + "]联系人已经存在主地址");
        }

        Address address = (Address) PojoUtils.convertDto2Pojo(addressDto, Address.class);
        String addressId = addressDto.getAddressid();

        AddressExt addressExt = addressExtDao.get(addressId);

        addressExt.setAddressDesc(addressDto.getAddress());
        Province province = provinceDao.get(addressDto.getState());
        addressExt.setProvince(province);

        CountyAll county = countyDao.get(addressDto.getCountyId());
        addressExt.setCounty(county);

        CityAll city = cityDao.get(new Integer(addressDto.getCityId()));
        address.setCity(city.getCode());
        addressExt.setCity(city);

        Integer areaId = addressDto.getAreaid();
        AreaAll area = null;
        if (null != areaId) {
            try {
                area = areaDao.get(areaId);
                addressExt.setArea(area);
                addressExt.setAreaName(area.getAreaname());
            } catch (ObjectRetrievalFailureException e) {
                logger.error("",e);
            }
            if (null != area && null != area.getSpellid()) {
                address.setArea(area.getSpellid().toString());
            } else {
                address.setArea(null == county.getSpellid() ? null : county.getSpellid().toString());
            }
        } else {
            address.setArea(null == county.getSpellid() ? null : county.getSpellid().toString());
        }

        String fullAddress = this.addressConcordancy(addressDto);
        address.setAddress(fullAddress);

        addressExt.setUptime(new Date());

        addressDao.saveOrUpdate(address);
        address = addressDao.get(addressId);

        //保存AddressExt
        addressExt.setUptime(new Date());

        addressExtDao.saveOrUpdate(addressExt);

        /**
         * 如果当前地址有未出库订单关联
         */
        List<Order> orderList = orderDao.querySpecialOrder(addressExt.getAddressId());

        if (null != orderList && orderList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("当前地址关联");
            sb.append(orderList.size());
            sb.append("张订单");

            for (int i = 0; i < orderList.size(); i++) {
                sb.append(orderList.get(i).getOrderid());
                if (i < orderList.size() - 1) {
                    sb.append("，");
                }
            }
            sb.append("未出库，无法修改");

            throw new BizException(sb.toString());
        }

        return address;
    }

    /**
     * <p>Title: updateContactMainAddress</p>
     * <p>Description: </p>
     *
     * @param contactId
     * @param addressId
     * @see com.chinadrtv.erp.uc.service.AddressService#updateContactMainAddress(java.lang.String, java.lang.String)
     */
    public void updateContactMainAddress(String contactId, String addressId) {
        //先清除该客户主地址
        Address currMainAddress = addressDao.getAddressByContactId(contactId);
        if (null != currMainAddress) {
            String secondarySeq = addressDao.getContactSecondarySeq();
            currMainAddress.setIsdefault(AddressConstant.CONTACT_EXTRA_ADDRESS);
            currMainAddress.setContactid(secondarySeq);
            addressDao.updateAddress(currMainAddress);

            try {
                AddressExt currMainAddressExt = addressExtDao.get(currMainAddress.getAddressid());
                currMainAddressExt.setAddressType(AddressConstant.CONTACT_EXTRA_ADDRESS);
                addressExtDao.saveOrUpdate(currMainAddressExt);
            } catch (Exception e) {
                logger.error("主地址不全，address不存在对应的addressExt导致擦除原主地址出错。");
            }
        }

        //再设置当前地址为主地址
        Address address = addressDao.get(addressId);
        AddressExt addressExt = addressExtDao.get(addressId);

        address.setContactid(contactId);
        address.setIsdefault(AddressConstant.CONTACT_MAIN_ADDRESS);
        addressDao.saveOrUpdate(address);

        addressExt.setAddressType(AddressConstant.CONTACT_MAIN_ADDRESS);
        addressExtDao.saveOrUpdate(addressExt);
    }

    /**
     * <p>Title: API-UC-15.查询客户地址</p>
     * <p>Description: </p>
     *
     * @param contactId
     * @return Map<String, Object>
     */
    public Map<String, Object> queryAddressPageList(DataGridModel dataGridModel, String contactId) {
        Map<String, Object> pageMap = new HashMap<String, Object>();

        int count = addressExtDao.queryAddressPageCount(contactId);
        List<AddressDto> pageList = addressExtDao.queryAddressPageList(dataGridModel, contactId);
        for (AddressDto addressDto : pageList) {
            addressDto.setReceiveAddress(addressConcordancyNoAddressInfo(addressDto));
            AddressExt addressExt = addressExtDao.get(addressDto.getAddressid());
            if (addressExt != null) {
                addressDto.setContactid(addressExt.getContactId());
                try {
                    addressDto.setProvinceName(null == addressExt.getProvince() ? null : addressExt.getProvince().getChinese());
                    addressDto.setCityId(null == addressExt.getCity() ? null : addressExt.getCity().getCityid());
                    addressDto.setCityName(null == addressExt.getCity() ? null : addressExt.getCity().getCityname());
                    addressDto.setCountyId(null == addressExt.getCounty() ? null : addressExt.getCounty().getCountyid());
                    addressDto.setCountyName(null == addressExt.getCounty() ? null : addressExt.getCounty().getCountyname());
                    addressDto.setCountySpellid(null == addressExt.getCounty() ? null : addressExt.getCounty().getSpellid());
                    addressDto.setAreaName(null == addressExt.getArea() ? null : addressExt.getArea().getAreaname());
                    addressDto.setAreaSpellid(null == addressExt.getArea() ? null : addressExt.getArea().getSpellid());
                } catch (Exception e) {
                    logger.error("四级地址配置表中没有对应的数据。");
                }
                addressDto.setAddressDesc(addressExt.getAddressDesc());
            }
        }

        pageMap.put("total", count);
        pageMap.put("rows", pageList);

        return pageMap;
    }

    /**
     * <p>Title: applyAddRequest</p>
     * <p>Description: 添加客户地址审批流程 </p>
     *
     * @param address
     * @param addressExt
     * @param remark
     * @param userId
     * @param deptId
     * @param batchId
     */
    public void applyAddRequest(String contactId, Address address, AddressExt addressExt, String remark, String userId, String deptId, String batchId) {
        //先手动获取sequence
        String addressId = addressDao.getSequence();
        address.setAddressid(addressId);
        addressExt.setAddressId(addressId);
        addressExt.setContactId(contactId);

        if (address.getIsdefault().equals("-1")) {
            address.setContactid(contactId);
        } else {
            String secondarySeq = addressDao.getContactSecondarySeq();
            address.setContactid(secondarySeq);
        }

        //校验主地址
        /*AddressDto addressDto = (AddressDto) PojoUtils.convertPojo2Dto(address, AddressDto.class);
        boolean existsMainAddress = checkMainAddress(addressDto);
        if (existsMainAddress) {
            throw new BizException("编号为[" + addressDto.getContactid() + "]联系人已经存在主地址");
        }*/

        Session session = sessionFactory.getCurrentSession();
        session.evict(address);
        session.evict(addressExt);

        UserBpmTask userBpmTask = new UserBpmTask();
        userBpmTask.setChangeObjID(addressId);
        userBpmTask.setUpdateDate(new Date());
        userBpmTask.setUpdateUser(userId);
        userBpmTask.setRemark(remark);
        userBpmTask.setBatchID(batchId);
        Integer bizType = UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex();
        userBpmTask.setBusiType(bizType.toString());

        String instanceId = userBpmTaskService.createApprovingTask(userBpmTask);

        List<PropertyDescriptor> addressPropDescList = PojoUtils.compare(new Address(), address);
        List<PropertyDescriptor> addressExtPropDescList = PojoUtils.compare(new AddressExt(), addressExt);

        AddressChange addressChange = new AddressChange();
        addressChange.setAddressid(addressId);
        addressChange.setProcInstId(instanceId);
        addressChange.setCreateDate(new Date());
        addressChange.setCreateUser(userId);
        PojoUtils.invoke(address, addressChange, addressPropDescList);
        addressChangeDao.save(addressChange);

        AddressExtChange addressExtChange = new AddressExtChange();
        addressExtChange.setAddressId(addressId);
        addressExtChange.setProcInstId(instanceId);
        addressExtChange.setCreateDate(new Date());
        addressExtChange.setCreateUser(userId);
        PojoUtils.invoke(addressExt, addressExtChange, addressExtPropDescList);
        addressExtChangeDao.save(addressExtChange);

    }


    /**
     * <p>Title: finishAddRequest</p>
     * <p>Description: </p>
     *
     * @param addressId
     * @param remark
     * @param approveUser
     * @param instId
     * @throws MarketingException
     */
    @SuppressWarnings("unused")
    @Override
    public void finishAddRequest(String addressId, String remark, String applyUser, String approveUser, String instId) throws MarketingException {
        Address address = new Address();
        AddressExt addressExt = new AddressExt();
        address.setAddressid(addressId);
        addressExt.setAddressId(addressId);

        userBpmTaskService.approveChangeRequest(applyUser, approveUser, instId, remark);

        AddressChange addressChange = addressChangeDao.queryByInstId(addressId, instId);
        AddressExtChange addressExtChange = addressExtChangeDao.queryByInstId(addressId, instId);
        //加载关联对象
        Province _province = addressExtChange.getProvince();
        CityAll _city = addressExtChange.getCity();
        AreaAll _areaAll = addressExtChange.getArea();
        CountyAll _countyAll = addressExtChange.getCounty();

        List<PropertyDescriptor> addressPropDescList = PojoUtils.getNotNullPropertyDescriptor(addressChange);
        List<PropertyDescriptor> addressExtPropDescList = PojoUtils.getNotNullPropertyDescriptor(addressExtChange);

        PojoUtils.invoke(addressChange, address, addressPropDescList);
        PojoUtils.invoke(addressExtChange, addressExt, addressExtPropDescList);

        AreaAll area = addressExt.getArea();
        CountyAll county = addressExt.getCounty();
        if (null != area) {
            addressExt.setAreaName(area.getAreaname());
            if (null != area && null != area.getSpellid()) {
                address.setArea(area.getSpellid().toString());
            } else {
                address.setArea(null == county.getSpellid() ? null : county.getSpellid().toString());
            }
        } else {
            address.setArea(null == county.getSpellid() ? null : county.getSpellid().toString());
        }

        String fullAddress = this.addressConcordancy(addressExt.getProvince().getProvinceid(), addressExt.getCity().getCityid(),
                null == county ? null : county.getCountyid(), null == area ? null : area.getAreaid(), address.getAddress());
        address.setAddress(fullAddress);
        addressExt.setUptime(new Date());
        addressExt.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
        
        String contactId = addressExt.getContactId();
        if (AddressConstant.CONTACT_MAIN_ADDRESS.equals(address.getIsdefault())) {
            //先清洗客户原地址
            Address currMainAddress = addressDao.getAddressByContactId(contactId);
            if (null != currMainAddress) {
                String secondarySeq = addressDao.getContactSecondarySeq();
                currMainAddress.setIsdefault(AddressConstant.CONTACT_EXTRA_ADDRESS);
                currMainAddress.setContactid(secondarySeq);
                addressDao.updateAddress(currMainAddress);
            }
        }
        
        addressDao.saveOrUpdate(address);
        addressExtDao.saveOrUpdate(addressExt);
    }

    /**
     * <p>Title: applyUpdateRequest</p>
     * <p>Description: SR1：客户地址修改调用修改审批流程</p>
     *
     * @param address
     * @param addressExt
     * @param remark
     * @param userId
     * @param deptId
     * @param batchId
     */
    public void applyUpdateRequest(Address address, AddressExt addressExt, String remark, String userId, String deptId, String batchId) {
        /**
         * 如果当前地址有未出库订单关联
         */
        List<Order> orderList = orderDao.querySpecialOrder(addressExt.getAddressId());

        if (null != orderList && orderList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("当前地址关联");
            sb.append(orderList.size());
            sb.append("张订单");

            for (int i = 0; i < orderList.size(); i++) {
                sb.append(orderList.get(i).getOrderid());
                if (i < orderList.size() - 1) {
                    sb.append("，");
                }
            }
            sb.append("未出库，无法修改");

            throw new BizException(sb.toString());
        }

        Session session = sessionFactory.getCurrentSession();
        session.evict(address);
        session.evict(addressExt);

        String addressId = address.getAddressid();

        UserBpmTask userBpmTask = new UserBpmTask();
        userBpmTask.setChangeObjID(addressId);
        userBpmTask.setUpdateDate(new Date());
        userBpmTask.setUpdateUser(userId);
        userBpmTask.setRemark(remark);
        userBpmTask.setBatchID(batchId);
        Integer bizType = UserBpmTaskType.CONTACT_ADDRESS_CHANGE.getIndex();
        userBpmTask.setBusiType(bizType.toString());

        String instanceId = userBpmTaskService.createApprovingTask(userBpmTask);

        Address rawAddress = addressDao.get(addressId);
        AddressExt rawAddressExt = addressExtDao.get(addressId);
        //将审批状态设为审批中
        rawAddressExt.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
        addressExt.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
        addressExtDao.saveOrUpdate(rawAddressExt);
        session.evict(rawAddress);
        session.evict(rawAddressExt);

        if (rawAddress.equals(address) && rawAddressExt.equals(addressExt)) {
            throw new BizException("当前地址与修改的地址没有任何差异");
        }

        List<PropertyDescriptor> addressPropDescList = PojoUtils.compare(rawAddress, address);
        List<PropertyDescriptor> addressExtPropDescList = PojoUtils.compare(rawAddressExt, addressExt);

        AddressChange addressChange = new AddressChange();
        addressChange.setAddressid(addressId);
        addressChange.setProcInstId(instanceId);
        addressChange.setCreateDate(new Date());
        addressChange.setCreateUser(userId);
        PojoUtils.invoke(address, addressChange, addressPropDescList);
        addressChangeDao.save(addressChange);

        AddressExtChange addressExtChange = new AddressExtChange();
        addressExtChange.setAddressId(addressId);
        addressExtChange.setProcInstId(instanceId);
        addressExtChange.setCreateDate(new Date());
        addressExtChange.setCreateUser(userId);
        PojoUtils.invoke(addressExt, addressExtChange, addressExtPropDescList);
        addressExtChangeDao.save(addressExtChange);
    }

    /**
     * <p>Title: getContactMainAddress</p>
     * <p>Description: 获取客户主地址</p>
     *
     * @param contactId
     * @return AddressDto
     * @see com.chinadrtv.erp.uc.service.AddressService#getContactMainAddress(java.lang.String)
     */
    public AddressDto getContactMainAddress(String contactId) {
        Address address = addressDao.getContactMainAddress(contactId);
        AddressDto addressDto = null;
        if (address != null) {
            addressDto = (AddressDto) PojoUtils.convertPojo2Dto(address, AddressDto.class);
            AddressExt addressExt = null;
            try {
                addressExt = addressExtDao.get(address.getAddressid());
            } catch (Exception e) {
                logger.error("主地址不全，address不存在对应的addressExt.");
            }
            if (addressExt != null) {
                addressDto.setContactid(addressExt.getContactId());
                addressDto.setAddressDesc(addressExt.getAddressDesc());
                try {
                    addressDto.setProvinceName(null == addressExt.getProvince() ? null : addressExt.getProvince().getChinese());
                    addressDto.setCityName(null == addressExt.getCity() ? null : addressExt.getCity().getCityname());
                    addressDto.setCityId(null == addressExt.getCity() ? null : addressExt.getCity().getCityid());
                    addressDto.setCountyId(null == addressExt.getCounty() ? null : addressExt.getCounty().getCountyid());
                    addressDto.setCountyName(null == addressExt.getCounty() ? null : addressExt.getCounty().getCountyname());
                    addressDto.setCountySpellid(null == addressExt.getCounty() ? null : addressExt.getCounty().getSpellid());
                    addressDto.setAreaid(null == addressExt.getArea() ? null : addressExt.getArea().getAreaid());
                    addressDto.setAreaName(null == addressExt.getArea() ? null : addressExt.getArea().getAreaname());
                    addressDto.setAreaSpellid(null == addressExt.getArea() ? null : addressExt.getArea().getSpellid());
                } catch (Exception e) {
                    logger.error("四级地址配置表中没有对应的数据。");
                }
            }
        }
        return addressDto;
    }

    /**
     * <p>Title: finishUpdateRequest</p>
     * <p>Description: 结束流程，合并数据</p>
     *
     * @param addressId
     * @param remark
     * @param approveUser
     * @param instId
     * @throws MarketingException
     */
    @SuppressWarnings("unused")
    public void finishUpdateRequest(String addressId, String remark, String applyUser, String approveUser, String instId) throws MarketingException {

        Address address = addressDao.get(addressId);
        AddressExt addressExt = addressExtDao.get(addressId);
        addressExt.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);

        userBpmTaskService.approveChangeRequest(applyUser, approveUser, instId, remark);

        AddressChange addressChange = addressChangeDao.queryByInstId(addressId, instId);
        AddressExtChange addressExtChange = addressExtChangeDao.queryByInstId(addressId, instId);
        //加载关联对象
        Province _province = addressExtChange.getProvince();
        CityAll _city = addressExtChange.getCity();
        AreaAll _areaAll = addressExtChange.getArea();
        CountyAll _countyAll = addressExtChange.getCounty();

        List<PropertyDescriptor> addressPropDescList = PojoUtils.getNotNullPropertyDescriptor(addressChange);
        List<PropertyDescriptor> addressExtPropDescList = PojoUtils.getNotNullPropertyDescriptor(addressExtChange);

        PojoUtils.invoke(addressChange, address, addressPropDescList);
        PojoUtils.invoke(addressExtChange, addressExt, addressExtPropDescList);

        AreaAll area = addressExt.getArea();
        CountyAll county = addressExt.getCounty();
        if (null != area) {
            addressExt.setAreaName(area.getAreaname());
            if (null != area && null != area.getSpellid()) {
                address.setArea(area.getSpellid().toString());
            } else {
                address.setArea(null == county.getSpellid() ? null : county.getSpellid().toString());
            }
        } else {
            address.setArea(null == county.getSpellid() ? null : county.getSpellid().toString());
        }

        String fullAddress = this.addressConcordancy(addressExt.getProvince().getProvinceid(), addressExt.getCity().getCityid(),
                null == county ? null : county.getCountyid(), null == area ? null : area.getAreaid(), address.getAddress());
        address.setAddress(fullAddress);
        addressExt.setUptime(new Date());
        
       //如果该地址是主地址，将其主地址清除，并将当前地址设为主地址
        String contactId = addressExt.getContactId();
        if (AddressConstant.CONTACT_MAIN_ADDRESS.equals(address.getIsdefault())) {
            //先清除该客户原地址
            Address currMainAddress = addressDao.getAddressByContactId(contactId);
            if (null != currMainAddress && !address.getAddressid().equals(currMainAddress.getAddressid())) {
                String secondarySeq = addressDao.getContactSecondarySeq();
                currMainAddress.setIsdefault(AddressConstant.CONTACT_EXTRA_ADDRESS);
                currMainAddress.setContactid(secondarySeq);
                addressDao.updateAddress(currMainAddress);
            }

            addressDao.saveOrUpdate(address);
            addressExtDao.saveOrUpdate(addressExt);
        }
    }

    public String addressConcordancy(String stateId, Integer cityId, Integer countyId, Integer areaId, String address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setState(stateId);
        addressDto.setCityId(cityId);
        addressDto.setCountyId(countyId);
        addressDto.setAreaid(areaId);
        addressDto.setAddress(address);
        return addressConcordancy(addressDto);
    }


    /**
     * <p>Title: addressConcordancy</p>
     * <p>Description: 地址整合</p>
     */
    public String addressConcordancy(AddressDto addressDto) {
        StringBuffer sb = new StringBuffer("");

        Province province = addressDto.getState() == null ? null : provinceDao.get(addressDto.getState());
        CityAll city = addressDto.getCityId() == null ? null : cityDao.get(new Integer(addressDto.getCityId()));
        CountyAll county = addressDto.getCountyId() == null ? null : countyDao.get(addressDto.getCountyId());
        AreaAll area = addressDto.getAreaid() == null ? null : areaDao.get(addressDto.getAreaid());

        //直辖市
        if (province != null) {
            if (province.getProvinceid().equals("01") || province.getProvinceid().equals("02") || province.getProvinceid().equals("14") || province.getProvinceid().equals("29")) {
                sb.append("");
            } else if (province.getProvinceid().equals("25")) {
                sb.append(province.getChinese());
                sb.append("壮族自治区");
            } else if (province.getProvinceid().equals("09")) {
                sb.append(province.getChinese());
                sb.append("回族自治区");
            } else if (province.getProvinceid().equals("30")) {
                sb.append(province.getChinese());
                sb.append("自治区");
            } else if (province.getProvinceid().equals("13")) {
                sb.append(province.getChinese());
                sb.append("维吾尔自治区");
            } else if (province.getProvinceid().equals("05")) {
                sb.append(province.getChinese());
                sb.append("自治区");
            } else {
                sb.append(province.getChinese());
                sb.append("省");
            }
        }

        // 市 区
        if (city != null) {
            sb.append(city.getCityname());
        }
        // 县,县级市
        if (county != null) {
            if (!county.getCountyname().equals("其他区") && !county.getCountyname().equals("其他")) {
                sb.append(county.getCountyname());
            }
        }
        // 乡镇
        if (area != null) {
            if (!area.getAreaname().equals("其他") && !area.getAreaname().equals("城区")) {
                sb.append(area.getAreaname());
            }
        }
        // 详细地址
        if (addressDto.getAddress() != null) {
            sb.append(addressDto.getAddress());
        }
        return sb.toString();
    }

    /**
     * <p>Title: addressConcordancy</p>
     * <p>Description: 地址整合</p>
     */
    private String addressConcordancy(Province province, CityAll city, CountyAll county, AreaAll area, String addressDetail) {
        StringBuffer sb = new StringBuffer("");

        //直辖市
        if (province != null) {
            if (province.getProvinceid().equals("01") || province.getProvinceid().equals("02") || province.getProvinceid().equals("14") || province.getProvinceid().equals("29")) {
                sb.append("");
            } else if (province.getProvinceid().equals("25")) {
                sb.append(province.getChinese());
                sb.append("壮族自治区");
            } else if (province.getProvinceid().equals("09")) {
                sb.append(province.getChinese());
                sb.append("回族自治区");
            } else if (province.getProvinceid().equals("30")) {
                sb.append(province.getChinese());
                sb.append("自治区");
            } else if (province.getProvinceid().equals("13")) {
                sb.append(province.getChinese());
                sb.append("维吾尔自治区");
            } else if (province.getProvinceid().equals("05")) {
                sb.append(province.getChinese());
                sb.append("自治区");
            } else {
                sb.append(province.getChinese());
                sb.append("省");
            }
        }

        // 市 区
        if (city != null) {
            sb.append(city.getCityname());
        }
        // 县,县级市
        if (county != null) {
            if (!county.getCountyname().equals("其他区") && !county.getCountyname().equals("其他")) {
                sb.append(county.getCountyname());
            }
        }
        // 乡镇
        if (area != null) {
            if (!area.getAreaname().equals("其他") && !area.getAreaname().equals("城区")) {
                sb.append(area.getAreaname());
            }
        }
        // 详细地址
        sb.append(addressDetail);
        return sb.toString();
    }

    public String addressConcordancyNoAddressInfo(AddressDto addressDto) {
        StringBuffer sb = new StringBuffer("");

        Province province = null;
        CityAll city = null;
        CountyAll county = null;
        AreaAll area = null;
        try {
            province = addressDto.getState() == null ? null : provinceDao.get(addressDto.getState());
            city = addressDto.getCityId() == null ? null : cityDao.get(new Integer(addressDto.getCityId()));
            county = addressDto.getCountyId() == null ? null : countyDao.get(addressDto.getCountyId());
            area = addressDto.getAreaid() == null ? null : areaDao.get(addressDto.getAreaid());
        } catch (Exception e) {
            logger.error("获取地址出错", e);
        }

        //直辖市
        if (province != null) {
            if (province.getProvinceid().equals("01") || province.getProvinceid().equals("02") || province.getProvinceid().equals("14") || province.getProvinceid().equals("29")) {
                sb.append("");
            } else if (province.getProvinceid().equals("25")) {
                sb.append(province.getChinese());
                sb.append("壮族自治区");
            } else if (province.getProvinceid().equals("09")) {
                sb.append(province.getChinese());
                sb.append("回族自治区");
            } else if (province.getProvinceid().equals("30")) {
                sb.append(province.getChinese());
                sb.append("自治区");
            } else if (province.getProvinceid().equals("13")) {
                sb.append(province.getChinese());
                sb.append("维吾尔自治区");
            } else if (province.getProvinceid().equals("05")) {
                sb.append(province.getChinese());
                sb.append("自治区");
            } else {
                sb.append(province.getChinese());
                sb.append("省");
            }
        }

        // 市 区
        if (city != null) {
            sb.append(city.getCityname());
        }
        // 县,县级市
        if (county != null) {
            if (!county.getCountyname().equals("其他区") && !county.getCountyname().equals("其他")) {
                sb.append(county.getCountyname());
            }
        }
        // 乡镇
        if (area != null) {
            if (!area.getAreaname().equals("其他") && !area.getAreaname().equals("城区")) {
                sb.append(area.getAreaname());
            }
        }
        return sb.toString();
    }

//    @Override
//    public void addToBlackList(String addressId) {
//        addressDao.addToBlackList(addressId);
//    }

//    @Override
//    public void removeFromBlackList(String addressId) {
//        addressDao.removeFromBlackList(addressId);
//    }

//    @Override
//    public void checkBlackListContact(String paytype, String contactId) throws ServiceException {
//        if (StringUtils.equals(paytype, "1")) {
//            Address address = get(addressId);
//            if (address.getBlack() != null && address.getBlack() == 1)
//                throw new ServiceException("当前收货地址为黑名单地址，无法使用货到付款的支付方式。");
//        }
//    }

    public Address updateAddressArea(String addressid, Integer areaId) {
        Address address = addressDao.get(addressid);
        AddressExt addressExt = addressExtDao.get(addressid);
        CountyAll county = addressExt.getCounty();
        AreaAll area = null;
        try {
            area = areaDao.get(areaId);
            addressExt.setArea(area);
            addressExt.setAreaName(area.getAreaname());
        } catch (ObjectRetrievalFailureException e) {
            e.printStackTrace();
        }
        if (null != area && null != area.getSpellid()) {
            address.setArea(area.getSpellid().toString());
        } else {
            address.setArea(null == county.getSpellid() ? null : county.getSpellid().toString());
        }

        String fullAddress = addressConcordancy(addressExt.getProvince(), addressExt.getCity(), addressExt.getCounty(), addressExt.getArea(), addressExt.getAddressDesc());
        address.setAddress(fullAddress);

        addressDao.saveOrUpdate(address);
        addressExt.setUptime(new Date());
        addressExtDao.saveOrUpdate(addressExt);
        return address;
    }

    @Override
    public boolean checkEffectAddress(AddressDto addressDto) {
        Province province = null;
        CityAll city = null;
        CountyAll county = null;
        AreaAll area = null;
        try {
            province = addressDto.getState() == null ? null : provinceDao.get(addressDto.getState());
            city = addressDto.getCityId() == null ? null : cityDao.get(new Integer(addressDto.getCityId()));
            county = addressDto.getCountyId() == null ? null : countyDao.get(addressDto.getCountyId());
            area = addressDto.getAreaid() == null ? null : areaDao.get(addressDto.getAreaid());
            if (!area.getProvid().equals(province.getProvinceid()) || area.getCityid()!=city.getCityid() || !area.getCountyid().equals(county.getCountyid()))
                return false;
        } catch (Exception e) {
            logger.error("获取地址出错", e);
            return false;
        }
        return true;
    }


}
