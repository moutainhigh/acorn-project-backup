package com.chinadrtv.erp.uc.service.impl;

import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.*;

import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.ContactChange;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.PotentialContactPhone;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.smsapi.util.StringUtil;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dao.AddressExtDao;
import com.chinadrtv.erp.uc.dao.ContactChangeDao;
import com.chinadrtv.erp.uc.dao.ContactDao;
import com.chinadrtv.erp.uc.dao.PhoneDao;
import com.chinadrtv.erp.uc.dao.PotentialContactDao;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.ContactDto;
import com.chinadrtv.erp.uc.dto.CustomerBaseSearchDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.OldContactexService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.uc.service.PotentialContactPhoneService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.PojoUtils;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-3
 * Time: 下午4:31
 * To change this template use File | Settings | File Templates.
 */

@Service
public class ContactServiceImpl extends GenericServiceImpl<Contact, String> implements ContactService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ContactServiceImpl.class);
    @Autowired
    private ContactDao contactDao;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private PotentialContactDao potentialContactDao;

    @Autowired
    private PotentialContactPhoneService potentialContactPhoneService;

    @Autowired
    private AddressService addressService;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private UserBpmTaskService userBpmTaskService;
    @Autowired
    private ChangeRequestService changeRequestService;
    @Autowired
    private ContactChangeDao contactChangeDao;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private LeadService leadService;
    @Autowired
    private OldContactexService oldContactexService;
    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressExtDao addressExtDao;

    @Override
    protected GenericDao<Contact, String> getGenericDao() {
        return contactDao;
    }

    public Contact get(String contactId){
        return contactDao.get(contactId);
    }

    public Contact findById(String contactId) {
        return contactDao.get(contactId);
    }

    public Map<String, Object> findByBaseCondition(CustomerBaseSearchDto customerBaseSearchDto, DataGridModel dataGridModel) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(customerBaseSearchDto.getPhone())) {
            PhoneAddressDto phoneAddressDto = phoneService.splicePhone(customerBaseSearchDto.getPhone());
            customerBaseSearchDto.setPhone(phoneAddressDto.getPhn2());
            if (StringUtils.isNotBlank(phoneAddressDto.getPhn1()) && !phoneAddressDto.getPhonetypid().equals("4")) {
                int count = contactDao.findCountByFixedPhone(phoneAddressDto.getPhn1(), phoneAddressDto.getPhn2());
                if (count > 0) {
                    pageMap.put("total", count);
                    pageMap.put("rows", contactDao.findByFixedPhone(phoneAddressDto.getPhn1(), phoneAddressDto.getPhn2(), dataGridModel.getStartRow(), dataGridModel.getRows()));
                    refindCustomerLevel((List<CustomerDto>) pageMap.get("rows"));
                    return pageMap;
                }
            }
        }
        //此处划分条件影响到sql中的关联表个数,从而影响到sql的性能
        if (onlyPhone(customerBaseSearchDto)) {
            //1 需要查询正式客户表和关联电话表 union 潜客表关联潜客电话表
            pageMap.put("total", contactDao.findCountByPhone(customerBaseSearchDto.getPhone()));
            pageMap.put("rows", contactDao.findByPhone(customerBaseSearchDto.getPhone(), dataGridModel.getStartRow(), dataGridModel.getRows()));
        } else if (onlyName(customerBaseSearchDto)) {
            //2 查询正式客户表 union 潜客表
            pageMap.put("total", contactDao.findCountByName(customerBaseSearchDto.getName()));
            pageMap.put("rows", contactDao.findByName(customerBaseSearchDto.getName(), dataGridModel.getStartRow(), dataGridModel.getRows()));
        } else if (onlyPhoneAndName(customerBaseSearchDto)) {
            //3 同分支1
            pageMap.put("total", contactDao.findCountByPhoneAndName(customerBaseSearchDto.getPhone(), customerBaseSearchDto.getName()));
            pageMap.put("rows", contactDao.findByPhoneAndName(customerBaseSearchDto.getPhone(), customerBaseSearchDto.getName(), dataGridModel.getStartRow(), dataGridModel.getRows()));
        } else if (onlyNameAndAddress(customerBaseSearchDto)) {
            //4 查询正式客户表关联地址表
            pageMap.put("total", contactDao.findCountByNameAndAddress(customerBaseSearchDto));
            pageMap.put("rows", contactDao.findByNameAndAddress(customerBaseSearchDto, dataGridModel.getStartRow(), dataGridModel.getRows()));
        } else if (onlyPhoneAndAddress(customerBaseSearchDto)) {
            //5 查询正式客户表关联地址表关联电话表
            pageMap.put("total", contactDao.findCountByPhoneAndNameAndAddress(customerBaseSearchDto));
            pageMap.put("rows", contactDao.findByPhoneAndNameAndAddress(customerBaseSearchDto, dataGridModel.getStartRow(), dataGridModel.getRows()));
        } else {
            //6 所有条件都存在，同分支5
            pageMap.put("total", contactDao.findCountByPhoneAndNameAndAddress(customerBaseSearchDto));
            pageMap.put("rows", contactDao.findByPhoneAndNameAndAddress(customerBaseSearchDto, dataGridModel.getStartRow(), dataGridModel.getRows()));
        }
        refindCustomerLevel((List<CustomerDto>) pageMap.get("rows"));
        return pageMap;
    }

    public Map<String, Object> findByBaseConditionForDistribution(CustomerBaseSearchDto customerBaseSearchDto, DataGridModel dataGridModel) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(customerBaseSearchDto.getPhone())) {
            PhoneAddressDto phoneAddressDto = phoneService.splicePhone(customerBaseSearchDto.getPhone());
            customerBaseSearchDto.setPhone(phoneAddressDto.getPhn2());
            if (StringUtils.isNotBlank(phoneAddressDto.getPhn1()) && !phoneAddressDto.getPhonetypid().equals("4")) {
                int count = contactDao.findCountByFixedPhone(phoneAddressDto.getPhn1(), phoneAddressDto.getPhn2());
                if (count > 0) {
                    pageMap.put("total", count);
                    pageMap.put("rows", contactDao.findByFixedPhone(phoneAddressDto.getPhn1(), phoneAddressDto.getPhn2(), dataGridModel.getStartRow(), dataGridModel.getRows()));
                    refindCustomerLevel((List<CustomerDto>) pageMap.get("rows"));
                    return pageMap;
                } else {
                    pageMap.put("total", 0);
                    pageMap.put("rows", new ArrayList<CustomerDto>());
                    return pageMap;
                }
            }
        }
        //此处划分条件影响到sql中的关联表个数,从而影响到sql的性能
        if (onlyPhone(customerBaseSearchDto)) {
            //1 需要查询正式客户表和关联电话表 union 潜客表关联潜客电话表
            pageMap.put("total", contactDao.findCountByPhone(customerBaseSearchDto.getPhone()));
            pageMap.put("rows", contactDao.findByPhone(customerBaseSearchDto.getPhone(), dataGridModel.getStartRow(), dataGridModel.getRows()));
        } else if (onlyName(customerBaseSearchDto)) {
            //2 查询正式客户表 union 潜客表
            pageMap.put("total", contactDao.findCountByName(customerBaseSearchDto.getName()));
            pageMap.put("rows", contactDao.findByName(customerBaseSearchDto.getName(), dataGridModel.getStartRow(), dataGridModel.getRows()));
        } else if (onlyPhoneAndName(customerBaseSearchDto)) {
            //3 同分支1
            pageMap.put("total", contactDao.findCountByPhoneAndName(customerBaseSearchDto.getPhone(), customerBaseSearchDto.getName()));
            pageMap.put("rows", contactDao.findByPhoneAndName(customerBaseSearchDto.getPhone(), customerBaseSearchDto.getName(), dataGridModel.getStartRow(), dataGridModel.getRows()));
        } else if (onlyNameAndAddress(customerBaseSearchDto)) {
            //4 查询正式客户表关联地址表
            pageMap.put("total", contactDao.findCountByNameAndAddress(customerBaseSearchDto));
            pageMap.put("rows", contactDao.findByNameAndAddress(customerBaseSearchDto, dataGridModel.getStartRow(), dataGridModel.getRows()));
        } else if (onlyPhoneAndAddress(customerBaseSearchDto)) {
            //5 查询正式客户表关联地址表关联电话表
            pageMap.put("total", contactDao.findCountByPhoneAndNameAndAddress(customerBaseSearchDto));
            pageMap.put("rows", contactDao.findByPhoneAndNameAndAddress(customerBaseSearchDto, dataGridModel.getStartRow(), dataGridModel.getRows()));
        } else {
            //6 所有条件都存在，同分支5
            pageMap.put("total", contactDao.findCountByPhoneAndNameAndAddress(customerBaseSearchDto));
            pageMap.put("rows", contactDao.findByPhoneAndNameAndAddress(customerBaseSearchDto, dataGridModel.getStartRow(), dataGridModel.getRows()));
        }
        refindCustomerLevel((List<CustomerDto>) pageMap.get("rows"));
        return pageMap;
    }

    private void refindCustomerLevel(List<CustomerDto> customerDtos) {
        for (CustomerDto customerDto : customerDtos) {
            if ("1".equals(customerDto.getContactType())) {
                customerDto.setLevel(findLevelByContactId(customerDto.getContactId()));
            }
        }
    }

    private boolean onlyPhoneAndAddress(CustomerBaseSearchDto customerBaseSearchDto) {
        return StringUtils.isNotBlank(customerBaseSearchDto.getPhone())
                && (StringUtils.isNotBlank(customerBaseSearchDto.getProvinceId())
                || StringUtils.isNotBlank(customerBaseSearchDto.getCityId())
                || StringUtils.isNotBlank(customerBaseSearchDto.getCountyId())
                || StringUtils.isNotBlank(customerBaseSearchDto.getAreaId()))
                && StringUtils.isBlank(customerBaseSearchDto.getName());
    }

    private boolean onlyNameAndAddress(CustomerBaseSearchDto customerBaseSearchDto) {
        return StringUtils.isNotBlank(customerBaseSearchDto.getName())
                && (StringUtils.isNotBlank(customerBaseSearchDto.getProvinceId())
                || StringUtils.isNotBlank(customerBaseSearchDto.getCityId())
                || StringUtils.isNotBlank(customerBaseSearchDto.getCountyId())
                || StringUtils.isNotBlank(customerBaseSearchDto.getAreaId()))
                && StringUtils.isBlank(customerBaseSearchDto.getPhone());
    }

    private boolean onlyPhoneAndName(CustomerBaseSearchDto customerBaseSearchDto) {
        return StringUtils.isNotBlank(customerBaseSearchDto.getPhone())
                && StringUtils.isNotBlank(customerBaseSearchDto.getName())
                && StringUtils.isBlank(customerBaseSearchDto.getProvinceId())
                && StringUtils.isBlank(customerBaseSearchDto.getCityId())
                && StringUtils.isBlank(customerBaseSearchDto.getCountyId())
                && StringUtils.isBlank(customerBaseSearchDto.getAreaId());
    }

    private boolean onlyName(CustomerBaseSearchDto customerBaseSearchDto) {
        return StringUtils.isNotBlank(customerBaseSearchDto.getName())
                && StringUtils.isBlank(customerBaseSearchDto.getProvinceId())
                && StringUtils.isBlank(customerBaseSearchDto.getCityId())
                && StringUtils.isBlank(customerBaseSearchDto.getCountyId())
                && StringUtils.isBlank(customerBaseSearchDto.getAreaId())
                && StringUtils.isBlank(customerBaseSearchDto.getPhone());
    }

    private boolean onlyPhone(CustomerBaseSearchDto customerBaseSearchDto) {
        return StringUtils.isNotBlank(customerBaseSearchDto.getPhone())
                && StringUtils.isBlank(customerBaseSearchDto.getProvinceId())
                && StringUtils.isBlank(customerBaseSearchDto.getCityId())
                && StringUtils.isBlank(customerBaseSearchDto.getCountyId())
                && StringUtils.isBlank(customerBaseSearchDto.getAreaId())
                && StringUtils.isBlank(customerBaseSearchDto.getName());
    }

    public Contact findByOrderId(String orderId) {
        return contactDao.findByOrderId(orderId);
    }

    public Contact findByShipmentId(String shipmentId) {
        return contactDao.findByShipmentId(shipmentId);
    }

    public Double findJiFenByContactId(String contactId) throws ServiceException {
        Double result = contactDao.findJiFenByContactId(contactId);
        if(result==null){
            throw new ServiceException(ExceptionConstant.SERVICE_USER_QUERY_EXCEPTION, "查询用户可用积分失败");
        }
        return result;
    }

    public Map<String, Object> saveCustomer(CustomerDto customer) throws ServiceTransactionException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("potentialContactId", null);
        resultMap.put("contactId", null);
        if (customer.getAddressDto() == null) {
            Long potentialContactId = potentialContactDao.savePotentialContact(createPotentialContact(customer));
            if(StringUtils.isNotBlank(customer.getPhone2())){
                potentialContactPhoneService.addPotentialContactPhone(createPotentialContactPhone(potentialContactId, customer));
            }
            resultMap.put("potentialContactId", potentialContactId);
        } else {
            String contactId = contactDao.save(createContact(customer)).getContactid();
            AddressDto addressDto = customer.getAddressDto();
            addressDto.setContactid(contactId);
            addressService.addAddress(addressDto);
            if(! StringUtil.isNullOrBank(customer.getPhoneNum())){
                phoneDao.save(createPhone(contactId, customer));
            }
            resultMap.put("contactId", contactId);
        }
        return resultMap;
    }

    public Map<String, Object> saveCustomer(CustomerDto customer, List<AddressDto> addressDtos, List<Phone> phones) throws ServiceTransactionException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("potentialContactId", null);
        resultMap.put("contactId", null);
        if (addressDtos == null || addressDtos.size() == 0) {
            Long potentialContactId = potentialContactDao.savePotentialContact(createPotentialContact(customer));
            if(!StringUtil.isNullOrBank(customer.getPhone2())){
                potentialContactPhoneService.addPotentialContactPhone(createPotentialContactPhone(potentialContactId, customer));
            }
            resultMap.put("potentialContactId", potentialContactId);
        } else {
            String contactId = contactDao.save(createContact(customer)).getContactid();
            savePhones(contactId, phones);
            saveAddresss(contactId, addressDtos);
            resultMap.put("contactId", contactId);
        }
        return resultMap;
    }


    public   Map<String, Object> addFormalCustomerWithFlow(CustomerDto customerDto, List<AddressDto> listAddresses, List<Phone> listphone, AgentUser user, String source) throws ServiceException {

        Map<String, Object> map = new HashMap();
        //是否走流程
        try {
            if(!StringUtils.equals(source, "shoppingCart") && !StringUtils.equals(source, "editOrder") &&
                    userService.getGroupType(user.getUserId()).equals(CustomerConstant.OUTBOUNR_TYPE) && !user.hasRole(SecurityConstants.ROLE_APPROVE_MANAGER)) {
                ContactDto contactDto = new ContactDto();
                contactDto.setName(customerDto.getName());
                contactDto.setSex(customerDto.getSex());
                contactDto.setCrusr(customerDto.getCrusr());
                contactDto.setCrdt(new Date());
                map = applyAddRequest(contactDto,"新增客户走流程",user.getUserId(),user.getDepartment());
                if(StringUtil.isNullOrBank((String) map.get("contactId"))) {
                    //新增客户失败
                    map.put("contactId","");
                }else{
                    List<Phone> listp = new ArrayList<Phone>();
                    for(Phone phone : listphone){
                        phone.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                        listp.add(phone);
                    }
                    List<AddressDto> lista = new ArrayList<AddressDto>();
                    for(AddressDto addressDto : listAddresses){
                        addressDto.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                        lista.add(addressDto);
                    }
                    savePhones(map.get("contactId").toString(), listp);
                    saveAddresss(map.get("contactId").toString(), lista);
                }
            }else{
                map= saveCustomer(customerDto, listAddresses, listphone);
                if (StringUtils.equals(source, "shoppingCart") || StringUtils.equals(source, "editOrder")) setModelsState(map);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            // logger.info("outboound新增客户("+contactId+")启动流程失败:"+e.getMessage());
            if (e.getMessage().contains("已经存在")) {
                throw new ServiceException("客户姓名与手机号码已经存在，不支持重复添加。");
            }
        }
        return map;
    }

    @Override
    public Integer findContactCountByFixPhone(String phoneNum) {
        return contactDao.findContactCountByFixPhone(phoneNum);
    }

    @Override
    public Map<String, Object> findContactByFixPhone(String phoneNum, DataGridModel dataGridModel) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("total", contactDao.findContactCountByFixPhone(phoneNum));
        pageMap.put("rows", contactDao.findContactByFixPhone(phoneNum, dataGridModel.getStartRow(), dataGridModel.getRows()));
        refindCustomerLevel((List<CustomerDto>) pageMap.get("rows"));
        return pageMap;
    }

    private void setModelsState(Map<String, Object> map) {
        String contactId = (String) map.get("contactId");
        Contact contact = get(contactId);
        contact.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_UNAUDITED);
        contactDao.saveOrUpdate(contact);
        List<AddressExt> addressExts = addressExtDao.queryAllAddressByContact(contactId);
        for (AddressExt addressExt : addressExts) {
            addressExt.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_UNAUDITED);
            addressExtDao.saveOrUpdate(addressExt);
        }
        List<Phone> phones = phoneDao.findByContactId(contactId, 0, 1000);
        for (Phone phone : phones) {
            phone.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_UNAUDITED);
            phoneDao.updatePhone(phone);
        }
    }

    private void savePhones(String contactId, List<Phone> phones) throws ServiceTransactionException {
        for(Phone phone : phones) {
            phone.setContactid(contactId);
            phoneService.addPhone(phone);
        }
    }

    private void saveAddresss(String contactId, List<AddressDto> addressDtos) {
        for(AddressDto addressDto : addressDtos) {
            addressDto.setContactid(contactId);
            addressService.addAddress(addressDto);
        }
    }

    public void updateCustomer(CustomerDto customerDto) {
        if (StringUtils.isNotBlank(customerDto.getContactId())) {
            updateContact(customerDto);
        } else {
            updatePotentialContact(customerDto);
        }
    }

    private void updatePotentialContact(CustomerDto customerDto) {
        PotentialContact potentialContactInDB = potentialContactDao.get(Long.valueOf(customerDto.getPotentialContactId()));
        potentialContactInDB.setName(customerDto.getName());
        potentialContactInDB.setBirthday(customerDto.getBirthDayDate());
        potentialContactInDB.setGender(customerDto.getSex());
        potentialContactInDB.setContacttype(customerDto.getContactType());
        potentialContactDao.saveOrUpdate(potentialContactInDB);
    }

    private void updateContact(CustomerDto customerDto) {
        Contact contactInDB = contactDao.get(customerDto.getContactId());
        contactInDB.setName(customerDto.getName());
        contactInDB.setBirthday(customerDto.getBirthDayDate());
        contactInDB.setSex(customerDto.getSex());
        contactInDB.setContacttype(customerDto.getContactType());
        contactDao.saveOrUpdate(contactInDB);
    }

    private Contact createContact(CustomerDto customer) {
        Contact contact = new Contact();
        contact.setName(customer.getName());
        contact.setBirthday(customer.getBirthDayDate());
        contact.setSex(customer.getSex());
        contact.setContacttype(customer.getContactType());
        contact.setCrusr(customer.getCrusr());
        contact.setCrdt(new Date());
        contact.setCrtm(new Date());
        return contact;
    }

    private PotentialContactPhone createPotentialContactPhone(Long potentialContactId, CustomerDto customerDto) {
        PotentialContactPhone potentialContactPhone = new PotentialContactPhone();
        potentialContactPhone.setPotentialContactId(potentialContactId.toString());
        potentialContactPhone.setPhone1(customerDto.getPhone1());
        potentialContactPhone.setPhone2(customerDto.getPhone2());
        potentialContactPhone.setPhone3(customerDto.getPhone3());
        potentialContactPhone.setPhoneTypeId(customerDto.getPhoneType());
        potentialContactPhone.setPrmphn("Y");
        return potentialContactPhone;
    }

    private Phone createPhone(String contactId, CustomerDto customerDto) {
        Phone phone = new Phone();
        phone.setContactid(contactId);
        phone.setPhn2(customerDto.getPhoneNum());
        return phone;
    }


    private PotentialContact createPotentialContact(CustomerDto customer) {
        PotentialContact potentialContact = new PotentialContact();
        potentialContact.setName(customer.getName());
        potentialContact.setBirthday(customer.getBirthDayDate());
        potentialContact.setGender(customer.getSex());
        potentialContact.setContacttype(customer.getContactType());
        potentialContact.setCrusr(customer.getCrusr());
        potentialContact.setCrdt(new Date());
        return potentialContact;
    }

    public String findLevelByContactId(String contactId) {
        String levelName = "";
        try {
            levelName = contactDao.findLevelByContactId(contactId);
        } catch (Exception e) {
            logger.error("查询会员等级出错，会员ID为："+contactId, e);
        }
        return levelName;
    }

    public Contact findByMailId(String mailId) {
        return contactDao.findByMailId(mailId);
    }
    
    
    
    
    
    
    
    /*===================================Separator==============================================*/


    /**
     * <p>Title: applyAddRequest</p>
     * <p>Description: </p>
     * @param contactDto
     * @param remark
     * @param userId
     * @param deptId
     * @return String contactId
     * @throws Exception
     * @see com.chinadrtv.erp.uc.service.ContactService#applyAddRequest(com.chinadrtv.erp.uc.dto.ContactDto, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Object> applyAddRequest(ContactDto contactDto, String remark, String userId, String deptId)
            throws Exception {
        AgentUser user = SecurityHelper.getLoginUser();

        Contact originalContact = (Contact) PojoUtils.convertDto2Pojo(contactDto, Contact.class);
        originalContact.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
        originalContact = contactDao.save(originalContact);

        String contactId = originalContact.getContactid();

        UserBpm userBpm = new UserBpm();
        userBpm.setContactID(contactId);
        userBpm.setBusiType("" + AuditTaskType.CONTACTADD.getIndex());
        userBpm.setCreateDate(new Date());
        userBpm.setCreateUser(user.getUserId());
        userBpm.setDepartment(user.getDepartment());
        userBpm.setWorkGrp(user.getWorkGrp());
        String batchId = changeRequestService.createChangeRequest(userBpm);

        UserBpmTask userBpmTask = new UserBpmTask();
        userBpmTask.setChangeObjID(contactId);
        userBpmTask.setUpdateDate(new Date());
        userBpmTask.setUpdateUser(userId);
        userBpmTask.setRemark(remark);
        userBpmTask.setBatchID(batchId);
        Integer bizType = UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex();
        userBpmTask.setBusiType(bizType.toString());

        String instanceId = userBpmTaskService.createApprovingTask(userBpmTask);

        List<PropertyDescriptor> propDescList = PojoUtils.compare(new ContactDto(), contactDto);

        ContactChange contactChange = new ContactChange();
        contactChange.setContactid(contactId);
        PojoUtils.invoke(contactDto, contactChange, propDescList);

        contactChange.setCreateDate(new Date());
        contactChange.setCreateUser(userId);
        contactChange.setProcInstId(instanceId);

        contactChangeDao.save(contactChange);

        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("contactId", contactId);
        rsMap.put("batchId", batchId);

        return rsMap;
    }

    /**
     * <p>Title: finishAddRequest</p>
     * <p>Description: </p>
     * @param contactId
     * @param remark
     * @param applyUser
     * @param approveUser
     * @param instId
     * @throws Exception
     * @see com.chinadrtv.erp.uc.service.ContactService#finishAddRequest(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void finishAddRequest(String contactId, String remark, String applyUser, String approveUser, String instId)
            throws Exception {
        Contact contact = contactDao.get(contactId);

        userBpmTaskService.approveChangeRequest(applyUser, approveUser, instId, remark);

        contact.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);

        contactDao.saveOrUpdate(contact);
    }

    /**
     * <p>Title: finishAddRequestState</p>
     * <p>Description: </p>
     * @param contactId
     * @throws Exception
     * @see com.chinadrtv.erp.uc.service.ContactService#finishAddRequestState(java.lang.String)
     */
    @Override
    public void finishAddRequestState(String contactId, int state) throws Exception{
        Contact contact = contactDao.get(contactId);
        contact.setState(state);
        contactDao.saveOrUpdate(contact);

        List<Phone> pList = phoneDao.findByContactId(contactId, 0, Integer.MAX_VALUE);
        for(Phone p : pList){
            p.setState(state);
            phoneDao.updatePhone(p);
        }

        List<AddressExt> addressList = addressExtDao.queryAllAddressByContact(contactId);
        for(AddressExt addressExt : addressList){
            addressExt.setAuditState(state);
            addressExtDao.saveOrUpdate(addressExt);
        }
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

    /**
     * <p>Title: finishUpdateRequest</p>
     * <p>Description: 结束审批流程</p>
     * @param contactId
     * @param remark
     * @param applyUser
     * @param approveUser
     * @param instId
     * @throws MarketingException
     * @see com.chinadrtv.erp.uc.service.ContactService#finishUpdateRequest(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void finishUpdateRequest(String contactId, String remark, String applyUser, String approveUser, String instId) throws MarketingException {

        Contact contact = contactDao.get(contactId);

        userBpmTaskService.approveChangeRequest(applyUser, approveUser, instId, remark);

        ContactChange contactChange = contactChangeDao.queryByTaskId(contactId, instId);

        List<PropertyDescriptor> propDescList =  PojoUtils.getNotNullPropertyDescriptor(contactChange);

        PojoUtils.invoke(contactChange, contact, propDescList);

        contact.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
        contactDao.saveOrUpdate(contact);
    }

    /**
     * <p>Title: queryAuditPageList</p>
     * <p>Description: </p>
     * @param customerDto
     * @return
     * @throws MarketingException
     */
    public Map<String, Object> queryAuditPageList(DataGridModel dataGrid, ObCustomerDto customerDto) throws MarketingException {
        Map<String, Object> pageMap = new HashMap<String, Object>();

        AgentUser user = SecurityHelper.getLoginUser();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = customerDto.getBeginDate();
        Date endDate = customerDto.getEndDate();
        ApprovingTaskDto taskDto = new ApprovingTaskDto();
        taskDto.setContactID(customerDto.getContactid());
        taskDto.setStartDate(null==beginDate ? "" : sdf.format(beginDate));
        taskDto.setEndDate(null==endDate ? "" : sdf.format(endDate));
        taskDto.setAppliedUserID(user.getUserId());
        taskDto.setDepartment(user.getDepartment());

        List<String> taskTypes = new ArrayList<String>();
        if (StringUtils.isNotBlank(customerDto.getAuditTaskType())) {
            taskTypes.add(AuditTaskType.valueOf(customerDto.getAuditTaskType()).getIndex()+"");
        } else {
            taskTypes.add(AuditTaskType.CONTACTCHANGE.getIndex()+"");
            taskTypes.add(AuditTaskType.CONTACTADD.getIndex()+"");
        }

        taskDto.setTaskTypes(taskTypes);
        taskDto.setContactName(customerDto.getName());
        taskDto.setTaskStatus(customerDto.getInstStatus());
        taskDto.setAppliedUserID(customerDto.getAgentId());

        if(null!=customerDto.getInstStatus() && !"".equals(customerDto.getInstStatus())){
            taskDto.setTaskStatus(customerDto.getInstStatus());
        }
        pageMap = changeRequestService.queryChangeReqeust(taskDto, dataGrid);
        return pageMap;
    }

    /**
     * <p>Title: createTask</p>
     * <p>Description: </p>
     * @param contactId
     * @param dto
     * @throws ServiceException
     */
    public String createTask(String contactId, CampaignDto dto) throws ServiceException {
        AgentUser user = SecurityHelper.getLoginUser();
        String userId = user.getUserId();
        Lead lead = leadService.getLastestAliveLead(userId, contactId, dto.getId().toString());
        if (lead == null) {
            lead = new Lead();
            lead.setContactId(contactId);
            lead.setCampaignId(dto.getId());
            lead.setDnis(dto.getDnis());
            lead.setAni(dto.getTollFreeNum());
            lead.setCallDirection(1L);
            lead.setOwner(userId);
            lead.setUserGroup(user.getWorkGrp());
            lead.setCreateUser(userId);
            lead.setStatus(0L);
            String groupType = userService.getGroupType(userId);
            if("IN".equalsIgnoreCase(groupType)){
                lead.setOutbound(false);
            }
            else if("OUT".equalsIgnoreCase(groupType)){
                lead.setOutbound(true);
            }

            lead.setTaskSourcType(CampaignTaskSourceType.SELFCREATE.getIndex());
        }
        LeadTask leadTask = leadService.saveLead(lead);
        campaignBPMTaskService.updateTaskStatus(leadTask.getBpmInstanceId(), ""+CampaignTaskStatus.ACTIVE.getIndex(), null, null);
        return leadTask.getBpmInstanceId();
    }

    /**
     * <p>Title: havePermissionCreateTask</p>
     * <p>Description: </p>
     * @param userId
     * @param contactId
     * @return Boolean
     * @see com.chinadrtv.erp.uc.service.ContactService#havePermissionCreateTask(java.lang.String, java.lang.String)
     */
    @Override
    public Boolean havePermissionCreateTask(String userId, String contactId) {

        AgentUser old_user = oldContactexService.queryBindAgentUser(contactId);
        if(null!=old_user){
            if(old_user.getUserId().equals(userId)){
                return true;
            }else{
                return false;
            }
        }
        return true;
    }





    public List<Contact> getContactFromIds(List<String> contactIdList)
    {
        return contactDao.getContactFromIds(contactIdList);
    }

    @Override
    public Map<String, Object> findContactByPhone(String phoneNum, DataGridModel dataGridModel) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("total", contactDao.findContactCountByPhone(phoneNum));
        pageMap.put("rows", contactDao.findContactByPhone(phoneNum, dataGridModel.getStartRow(), dataGridModel.getRows()));
        refindCustomerLevel((List<CustomerDto>) pageMap.get("rows"));
        return pageMap;
    }



    @Override
    public Integer findContactCountByPhone(String phoneNum) {
        return contactDao.findContactCountByPhone(phoneNum);
    }

    public Integer findContactCountByPhoneList(List<String> phoneNumList) {
        Integer count = contactDao.findContactCountByPhoneList(phoneNumList);
        return count;
    }
    public Map<String, Object> findContactByPhoneList(List<String> phoneNumList, DataGridModel dataGridModel) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("total", contactDao.findContactCountByPhoneList(phoneNumList));
        pageMap.put("rows", contactDao.findContactByPhoneList(phoneNumList, dataGridModel.getStartRow(), dataGridModel.getRows()));
        return pageMap;
    }

}