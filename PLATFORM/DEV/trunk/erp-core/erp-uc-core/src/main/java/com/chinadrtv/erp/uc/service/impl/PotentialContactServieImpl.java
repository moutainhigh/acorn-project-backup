package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.PotentialContactPhone;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dao.PotentialContactDao;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.ContactAddressDto;
import com.chinadrtv.erp.uc.dto.ContactDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.service.*;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-8 下午5:49:36
 * 
 */
@Service("potentialContactServie")
public class PotentialContactServieImpl extends GenericServiceImpl<PotentialContact, Long> implements PotentialContactService {
	@Autowired
	private PotentialContactDao potentialContactDao;

    @Autowired
    private ContactService contactService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PhoneService phoneService;
    @Autowired
    private LeadService leadService;
    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;
    @Autowired
    private CallHistService callHistService;
    @Autowired
    private ShoppingCartService shoppingCartService;
	@Override
	protected GenericDao<PotentialContact, Long> getGenericDao() {
		return potentialContactDao;
	}

	public Long savePotentialContact(PotentialContact potentialContact) {
		return potentialContactDao.savePotentialContact(potentialContact);

	}

    public List<CustomerDto> findByBaseCondition(String potentialContactId, String name, String phoneNum, String phone1, String phone2, String phone3, int index, int rows) {
        return potentialContactDao.findByBaseCondition(potentialContactId, name, phoneNum, phone1, phone2, phone3, index, rows);
    }

	/** 
	 * <p>Title: API-UC-21.更新潜客 4</p>
	 * <p>Description: </p>
	 * @return PotentialContact
	 */
	public void updatePotentialContact(PotentialContact potentialContact) {
		potentialContactDao.saveOrUpdate(potentialContact);
	}

	/** 
	 * <p>Title: 查询</p>
	 * <p>Description: </p>
	 * @param id
	 * @return PotentialContact
	 * @see com.chinadrtv.erp.uc.service.PotentialContactService#queryById(java.lang.Long)
	 */ 
	public PotentialContact queryById(Long id) {
		return potentialContactDao.get(id);
	}
	
	/**
	 * <p>Title: 根据ID查询</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * @see com.chinadrtv.erp.uc.service.PotentialContactService#findById(java.lang.Long)
	 */
	public PotentialContact findById(Long id) {
		return potentialContactDao.get(id);
	}
    /**
     * 潜在客户转化为正式客户
     * @param potentialContactId
     * @return
     */
    public Boolean upgradeToContact2(
            Long potentialContactId,
            List<AddressDto> addresses)
    {
        PotentialContact potentialContact = potentialContactDao.get(potentialContactId);
        if(potentialContact != null &&
                potentialContact.getContactId() == null){
            if(StringUtils.isNotBlank(potentialContact.getName()) || //姓名
                    StringUtils.isNotBlank(potentialContact.getGender())  //性别
                    ){
                //电话
                Set<PotentialContactPhone> phones = potentialContact.getPotentialContactPhones();
                if(phones.size() > 0){
                    //String contactId =  contactService.getSequence();
                    Contact contact = new Contact();
                    //contact.setContactid(contactId);
                    contact.setName(potentialContact.getName());
                    contact.setSex(potentialContact.getGender());
                    contact.setCrdt(new Date());
                    contact.setMddt(new Date());

                    AgentUser agentUser = SecurityHelper.getLoginUser();
                    if(agentUser != null)   {
                        contact.setCrusr(agentUser.getUserId());
                        contact.setMdusr(agentUser.getUserId());
                    }

                    contactService.saveOrUpdate(contact);

                    for(AddressDto address : addresses){
                        address.setContactid(contact.getContactid());
                        addressService.addAddress(address);
                    }

                    for(PotentialContactPhone phone : phones){
                        Phone newPhone = new Phone();
                        newPhone.setContactid(contact.getContactid());
                        newPhone.setPhn1(phone.getPhone1());
                        newPhone.setPhn2(phone.getPhone2());
                        newPhone.setPhn3(phone.getPhone3());
                        newPhone.setPrmphn(phone.getPrmphn());
                        newPhone.setPhoneNum(phone.getPhoneNum());
                        newPhone.setPhonetypid(phone.getPhoneTypeId());
                        phoneService.saveOrUpdate(newPhone);
                    }

                    potentialContact.setContactId(Long.parseLong(contact.getContactid()));
                    potentialContactDao.updatePotentialContact(potentialContact);
                    this.updateContactTaskAndLead(contact.getContactid(),String.valueOf(potentialContactId)) ;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 潜在客户转化为正式客户 走审批流程
     * @param potentialContactId
     * @return
     */
    public Boolean upgradeToContactWithFlow(
            Long potentialContactId,
            List<AddressDto> addresses) throws Exception {
        PotentialContact potentialContact = potentialContactDao.get(potentialContactId);
        if(potentialContact != null &&
                potentialContact.getContactId() == null){
            if(StringUtils.isNotBlank(potentialContact.getName()) || //姓名
                    StringUtils.isNotBlank(potentialContact.getGender())  //性别
                    ){
                //电话
                Set<PotentialContactPhone> phones = potentialContact.getPotentialContactPhones();
                if(phones.size() > 0){
                    //String contactId =  contactService.getSequence();
                    ContactDto contactDto = new ContactDto();
                    //contactDto.setContactid(contactId);
                    contactDto.setName(potentialContact.getName());
                    contactDto.setSex(potentialContact.getGender());
                    contactDto.setCrdt(new Date());
                    contactDto.setMddt(new Date());

                    AgentUser agentUser = SecurityHelper.getLoginUser();
                    if(agentUser != null)   {
                        contactDto.setCrusr(agentUser.getUserId());
                        contactDto.setMdusr(agentUser.getUserId());
                    }
                    String contactId = (String)contactService.applyAddRequest(contactDto, "潜客转正式客户", agentUser.getUserId(), agentUser.getDepartment()).get("contactId");

                    for(AddressDto address : addresses){
                        address.setContactid(contactId);
                        address.setAuditState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                        addressService.addAddress(address);
                    }

                    for(PotentialContactPhone phone : phones){
                        Phone newPhone = new Phone();
                        newPhone.setContactid(contactId);
                        newPhone.setPhn1(phone.getPhone1());
                        newPhone.setPhn2(phone.getPhone2());
                        newPhone.setPhn3(phone.getPhone3());
                        newPhone.setPrmphn(phone.getPrmphn());
                        newPhone.setPhoneNum(phone.getPhoneNum());
                        newPhone.setPhonetypid(phone.getPhoneTypeId());
                        newPhone.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                        phoneService.saveOrUpdate(newPhone);
                    }

                    potentialContact.setContactId(Long.parseLong(contactId));
                    potentialContactDao.updatePotentialContact(potentialContact);
                    this.updateContactTaskAndLead(contactId, String.valueOf(potentialContactId)) ;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 潜在客户转化为正式客户
     * @param potentialContactId
     * @return
     */
    public Boolean upgradeToContact(
            Long potentialContactId,
            List<ContactAddressDto> addresses){
        PotentialContact potentialContact = potentialContactDao.get(potentialContactId);
        if(potentialContact != null &&
                potentialContact.getContactId() == null){
            if(StringUtils.isNotBlank(potentialContact.getName()) || //姓名
                    StringUtils.isNotBlank(potentialContact.getGender())  //性别
                    ){
                //电话
                Set<PotentialContactPhone> phones = potentialContact.getPotentialContactPhones();
                if(phones.size() > 0){
                    //String contactId =  contactService.getSequence();
                    Contact contact = new Contact();
                    //contact.setContactid(contactId);
                    contact.setName(potentialContact.getName());
                    contact.setSex(potentialContact.getGender());
                    contact.setCrdt(new Date());
                    contact.setMddt(new Date());

                    AgentUser agentUser = SecurityHelper.getLoginUser();
                    if(agentUser != null)   {
                        contact.setCrusr(agentUser.getUserId());
                        contact.setMdusr(agentUser.getUserId());
                    }

                    contactService.saveOrUpdate(contact);

                    for(ContactAddressDto address : addresses){
                        AddressDto newAddress = new AddressDto();
                        newAddress.setContactid(contact.getContactid());
                        newAddress.setState(address.getProvince());
                        newAddress.setCityId(address.getCityId());
                        newAddress.setAreaid(address.getAreaId());
                        newAddress.setCountyId(address.getCountyId());
                        newAddress.setAddress(address.getAddress());
                        newAddress.setZip(address.getZip());
                        newAddress.setAddrtypid("2");
                        newAddress.setIsdefault("-1");
                        addressService.addAddress(newAddress);
                    }

                    for(PotentialContactPhone phone : phones){
                        Phone newPhone = new Phone();
                        newPhone.setContactid(contact.getContactid());
                        newPhone.setPhn1(phone.getPhone1());
                        newPhone.setPhn2(phone.getPhone2());
                        newPhone.setPhn3(phone.getPhone3());
                        newPhone.setPrmphn(phone.getPrmphn());
                        newPhone.setPhonetypid(phone.getPhoneTypeId());
                        phoneService.saveOrUpdate(newPhone);
                    }

                    potentialContact.setContactId(Long.parseLong(contact.getContactid()));
                    potentialContactDao.updatePotentialContact(potentialContact);
                    this.updateContactTaskAndLead(contact.getContactid(),String.valueOf(potentialContactId)) ;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 潜在客户转化为正式客户
     * @param potentialContactId
     * @return
     */
    public Boolean upgradeToContact(
            Long potentialContactId,
            String province,
            Integer cityId,
            Integer countyId,
            Integer areaId,
            String address,
            String zip){

        PotentialContact potentialContact = potentialContactDao.get(potentialContactId);
        if(potentialContact != null &&
           potentialContact.getContactId() == null){
            if(StringUtils.isNotBlank(potentialContact.getName()) || //姓名
               StringUtils.isNotBlank(potentialContact.getGender())  //性别
               ){
                //电话
                Set<PotentialContactPhone> phones = potentialContact.getPotentialContactPhones();
                if(phones.size() > 0){
                    //String contactId =  contactService.getSequence();
                    Contact contact = new Contact();
                    //contact.setContactid(contactId);
                    contact.setName(potentialContact.getName());
                    contact.setSex(potentialContact.getGender());
                    contact.setCrdt(new Date());
                    contact.setMddt(new Date());

                    AgentUser agentUser = SecurityHelper.getLoginUser();
                    if(agentUser != null)   {
                        contact.setCrusr(agentUser.getUserId());
                        contact.setMdusr(agentUser.getUserId());
                    }

                    contactService.saveOrUpdate(contact);

                    AddressDto newAddress = new AddressDto();
                    newAddress.setContactid(contact.getContactid());
                    newAddress.setState(province);
                    newAddress.setCityId(cityId);
                    newAddress.setAreaid(areaId);
                    newAddress.setCountyId(countyId);
                    newAddress.setAddress(address);
                    newAddress.setIsdefault("-1");
                    newAddress.setZip(zip);
                    newAddress.setAddrtypid("2");
                    addressService.addAddress(newAddress);

                    for(PotentialContactPhone phone : phones){
                        Phone newPhone = new Phone();
                        newPhone.setContactid(contact.getContactid());
                        newPhone.setPhn1(phone.getPhone1());
                        newPhone.setPhn2(phone.getPhone2());
                        newPhone.setPhn3(phone.getPhone3());
                        newPhone.setPrmphn(phone.getPrmphn());
                        newPhone.setPhoneNum(phone.getPhoneNum());
                        newPhone.setPhonetypid(phone.getPhoneTypeId());
                        phoneService.saveOrUpdate(newPhone);
                    }

                    potentialContact.setContactId(Long.parseLong(contact.getContactid()));
                    potentialContactDao.updatePotentialContact(potentialContact);

                    this.updateContactTaskAndLead(contact.getContactid(),String.valueOf(potentialContactId)) ;
                    return true;
                }
            }
        }
        return false;
    }

    private void updateContactTaskAndLead(String contactId, String potentialContactId) {
        leadService.updatePotential2Normal(contactId, potentialContactId);
        campaignBPMTaskService.updatePotential2Normal(contactId, potentialContactId);
        callHistService.updateCallHistContact(contactId, potentialContactId);
        shoppingCartService.updateShoppingCartContact(Long.parseLong(potentialContactId),Long.parseLong(contactId));
    }

}
