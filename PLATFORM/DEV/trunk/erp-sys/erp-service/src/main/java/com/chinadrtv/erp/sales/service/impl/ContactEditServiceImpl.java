package com.chinadrtv.erp.sales.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.*;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.sales.dto.CustomerPhoneFrontDto;
import com.chinadrtv.erp.sales.service.ContactEditService;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dao.ContactChangeDao;
import com.chinadrtv.erp.uc.dao.ContactDao;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.ContactDto;
import com.chinadrtv.erp.uc.service.*;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.PojoUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class ContactEditServiceImpl implements ContactEditService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ContactEditServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private ContactChangeDao contactChangeDao;
    @Autowired
    ContactService contactService;
    @Autowired
    ChangeRequestService changeRequestService;
    @Autowired
    SysMessageService sysMessageService;
    @Autowired
    PhoneService phoneService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressExtService addressExtService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CountyAllService countyAllService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private UserBpmTaskService userBpmTaskService;

    public void createEditNameAndSexBatchAndFlow(String name, String sex, String customerId, String lastBatchId) throws MarketingException {
        AgentUser user = SecurityHelper.getLoginUser();
        Contact contact = contactService.get(customerId);
        String batchId = changeRequestService.createChangeRequest(createUserBpm(user, customerId));
        ContactDto contactDto = (ContactDto) PojoUtils.convertPojo2Dto(contact, ContactDto.class);
        contactDto.setName(name);
        contactDto.setSex(sex);
        applyUpdateRequest(contactDto, "修改姓名性别", user.getUserId(), user.getDepartment(), batchId);
        if (StringUtils.isNotBlank(lastBatchId)) {
            //关闭流程
            changeRequestService.closeChangeRequestStatus(lastBatchId);
            //同步批次
            changeRequestService.syncBatchStatus(lastBatchId);
            //处理消息
            UserBpm ub = changeRequestService.queryApprovingTaskById(lastBatchId);
            MessageType mType = MessageType.MODIFY_CONTACT_REJECT;
            sysMessageService.handleMessage(mType, ub.getContactID(), ub.getCreateDate());
        }
        createAndSaveMessage(customerId, user);
    }

    private UserBpm createUserBpm(AgentUser user, String customerId) {
        UserBpm userBpm = new UserBpm();
        userBpm.setContactID(customerId);
        userBpm.setBusiType("" + AuditTaskType.CONTACTCHANGE.getIndex());
        userBpm.setCreateDate(new Date());
        userBpm.setCreateUser(user.getUserId());
        userBpm.setDepartment(user.getDepartment());
        userBpm.setWorkGrp(user.getWorkGrp());
        return userBpm;
    }

    private void createAndSaveMessage(String customerId, AgentUser user) {
        try {
            SysMessage sysMessage = new SysMessage();
            sysMessage.setSourceTypeId(MessageType.MODIFY_CONTACT.getIndex() + "");
            sysMessage.setContent(customerId);
            sysMessage.setCreateUser(user.getUserId());
            sysMessage.setCreateDate(new Date());
            sysMessage.setRecivierGroup(user.getWorkGrp());
            sysMessage.setDepartmentId(user.getDepartment());
            sysMessageService.addMessage(sysMessage);
        } catch (Exception e) {
            logger.error("修改客户审批流程消息创建失败，" + e.getMessage());
        }
    }

    public Map createEditPhoneAndAddressBatchAndFlow(String customerId, String lastBatchId, List<CustomerPhoneFrontDto> newPhones, List<AddressDto> newAddresss) throws MarketingException {
        AgentUser user = SecurityHelper.getLoginUser();
        String batchId = changeRequestService.createChangeRequest(createUserBpm(user, customerId));
        for (CustomerPhoneFrontDto newPhoneFrontDto : newPhones) {
            if (StringUtils.isBlank(newPhoneFrontDto.getCustomerPhoneId())) {
                Phone phone = new Phone();
                BeanUtils.copyProperties(newPhoneFrontDto, phone);
                phone.setContactid(customerId);
                phoneService.applyAddRequest(phone, "新增客户电话", user.getUserId(), user.getDepartment(), batchId);
                continue;
            }
        }
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(100);
        try {
            List<AddressDto> oldAddresss = (List<AddressDto>) addressService.queryAddressPageList(dataGridModel, customerId).get("rows");
            for (AddressDto newAddressDto : newAddresss) {
                if (StringUtils.isBlank(newAddressDto.getAddressid())) {
                    Map result = resetModelProperties(newAddressDto);
                    Address address = (Address) result.get("address");
                    AddressExt addressExt = (AddressExt) result.get("addressExt");
                    addressService.applyAddRequest(customerId, address, addressExt, "新增客户地址", user.getUserId(), user.getDepartment(), batchId);
                    continue;
                }
                for (AddressDto oldAddressDto : oldAddresss) {
                    if (StringUtils.equals(newAddressDto.getAddressid(), oldAddressDto.getAddressid()) &&
                            !compareAddress(newAddressDto, oldAddressDto)) {
                        Map result = resetModelProperties(newAddressDto);
                        Address address = (Address) result.get("address");
                        AddressExt addressExt = (AddressExt) result.get("addressExt");
                        addressService.applyUpdateRequest(address, addressExt, "修改客户地址", user.getUserId(), user.getDepartment(), batchId);
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("地址流程申请出错", e);
            throw new MarketingException(e);
        }
        try {
            if (StringUtils.isNotBlank(lastBatchId)) {
                //关闭流程
                changeRequestService.closeChangeRequestStatus(lastBatchId);
                //同步批次
                changeRequestService.syncBatchStatus(lastBatchId);
                //处理消息
                UserBpm ub = changeRequestService.queryApprovingTaskById(lastBatchId);
                MessageType mType = MessageType.MODIFY_CONTACT_REJECT;
                sysMessageService.handleMessage(mType, ub.getContactID(), ub.getCreateDate());
            }
        } catch (Exception e) {
            logger.error("关闭上次申请批次出错", e);
            throw new MarketingException(e);
        }
        createAndSaveMessage(customerId, user);
        Map map = new HashMap();
        map.put("msg", "修改客户电话地址信息流程申请成功。");
        return map;
    }

    private Map resetModelProperties(AddressDto newAddressDto) {
        Address address = new Address();
        AddressExt addressExt = new AddressExt();
        if (StringUtils.isNotBlank(newAddressDto.getAddressid())) {
            address = addressService.get(newAddressDto.getAddressid());
            addressExt = addressExtService.get(newAddressDto.getAddressid());
        }
        Province province = provinceService.get(newAddressDto.getState());
        CityAll city = cityService.get(newAddressDto.getCityId());
        CountyAll county = countyAllService.queryByCountyId(newAddressDto.getCountyId());
        AreaAll area = areaService.get(newAddressDto.getAreaid());
        address.setIsdefault(newAddressDto.getIsdefault());
        address.setAddress(newAddressDto.getAddress());
        address.setArea(area.getAreaid().toString());
        address.setAreaid(county.getSpellid());
        address.setCity(city.getCode());
        address.setState(province.getProvinceid());
        address.setZip(newAddressDto.getZip());

        addressExt.setAddressDesc(newAddressDto.getAddress());
        addressExt.setProvince(province);
        addressExt.setCity(city);
        addressExt.setCounty(county);
        addressExt.setArea(area);
        addressExt.setAreaName(area.getAreaname());
        Map result = new HashMap();
        result.put("address", address);
        result.put("addressExt", addressExt);
        return result;
    }

    private boolean compareAddress(AddressDto newAddressDto, AddressDto oldAddressDto) {
        return compareAddressDetail(newAddressDto, oldAddressDto) &&
                compareAddressZip(newAddressDto, oldAddressDto) &&
                compareAddressFourLevelAddress(newAddressDto, oldAddressDto);
    }

    private boolean compareAddressDetail(AddressDto newAddressDto, AddressDto oldAddressDto) {
        return StringUtils.equals(newAddressDto.getAddress(), oldAddressDto.getAddress());
    }

    private boolean compareAddressZip(AddressDto newAddressDto, AddressDto oldAddressDto) {
        return StringUtils.equals(newAddressDto.getZip(), oldAddressDto.getZip());
    }

    private boolean compareAddressFourLevelAddress(AddressDto newAddressDto, AddressDto oldAddressDto) {
        return StringUtils.equals(newAddressDto.getState(), oldAddressDto.getState()) &&
                StringUtils.equals(newAddressDto.getCityId() == null ? "" : newAddressDto.getCityId().toString(), oldAddressDto.getCityId() == null ? "" : oldAddressDto.getCityId().toString()) &&
                StringUtils.equals(newAddressDto.getCountyId() == null ? "" : newAddressDto.getCountyId().toString(), oldAddressDto.getCountyId() == null ? "" : oldAddressDto.getCountyId().toString()) &&
                StringUtils.equals(newAddressDto.getAreaid() == null ? "" : newAddressDto.getAreaid().toString(), oldAddressDto.getAreaid() == null ? "" : oldAddressDto.getAreaid().toString());
    }

    /**
     * <p>Title: applyUpdateRequest</p>
     * <p>Description: 联系人修改审批</p>
     * @param contactDto
     * @param remark
     * @param userId
     * @param deptId
     * @return Long
     */
    public void applyUpdateRequest(ContactDto contactDto, String remark, String userId, String deptId, String batchId) {
        String contactId = contactDto.getContactid();

        UserBpmTask userBpmTask = new UserBpmTask();
        userBpmTask.setChangeObjID(contactId);
        userBpmTask.setUpdateDate(new Date());
        userBpmTask.setUpdateUser(userId);
        userBpmTask.setRemark(remark);
        userBpmTask.setBatchID(batchId);
        Integer bizType = UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex();
        userBpmTask.setBusiType(bizType.toString());

        String instanceId = userBpmTaskService.createApprovingTask(userBpmTask);

        Contact originalContact = contactDao.get(contactId);
        originalContact.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
        contactDto.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
        contactDao.saveOrUpdate(originalContact);

        Session session = sessionFactory.getCurrentSession();
        session.evict(originalContact);

        ContactDto originalDto = new ContactDto();
        BeanUtils.copyProperties(originalContact, originalDto);

        if(originalDto.equals(contactDto)){
            throw new BizException("当前客户与修改的客户没有任何差异");
        }

        List<PropertyDescriptor> propDescList = PojoUtils.compare(originalDto, contactDto);

        ContactChange contactChange = new ContactChange();
        contactChange.setContactid(contactDto.getContactid());
        PojoUtils.invoke(contactDto, contactChange, propDescList);

        contactChange.setCreateDate(new Date());
        contactChange.setCreateUser(userId);
        contactChange.setProcInstId(instanceId);

        contactChangeDao.save(contactChange);
    }
}
