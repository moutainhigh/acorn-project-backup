package com.chinadrtv.erp.uc.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.PhoneChange;
import com.chinadrtv.erp.model.agent.CityAll;
import com.chinadrtv.erp.model.agent.MpZone;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dao.PhoneChangeDao;
import com.chinadrtv.erp.uc.dao.PhoneDao;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.uc.dto.UpdateLastCallDateDto;
import com.chinadrtv.erp.uc.service.*;
import com.chinadrtv.erp.user.aop.Mask;
import com.chinadrtv.erp.user.model.DepartmentInfo;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.PojoUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-7
 * Time: 上午10:33
 * To change this template use File | Settings | File Templates.
 */
@Service("phoneService")
public class PhoneServiceImpl extends GenericServiceImpl<Phone, Long> implements PhoneService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PhoneServiceImpl.class);

    private static String mobileRegexp = "(1[3458]{1})([0-9]{9})";
    private static String mobile01Regexp = "(01[3458]{1})([0-9]{9})";
    private static String mobile86Regexp = "(861[3458]{1})([0-9]{9})";
    private static String mobileA86Regexp = "(\\+861[3458]{1})([0-9]{9})";
    private static String local7FixedPhoneRegexp = "([0-9]{7})";
    private static String local8FixedPhoneRegexp = "([0-9]{8})";
    private static String remote3AreaCode8FixedPhoneRegexp = "(0[12]{1}[^6]{1})([0-9]{8})";
    private static String remote4AreaCode8FixedPhoneRegexp = "(0[^012]{1}[0-9]{2})([0-9]{8})";
    private static String remote4AreaCode7FixedPhoneRegexp = "(0[^012]{1}[0-9]{2})([0-9]{7})";
    private static String begin8PhoneRegexp = "(8[0-9]{7,})";

    private static Pattern mobilePattern = Pattern.compile(mobileRegexp);
    private static Pattern mobile01Pattern = Pattern.compile(mobile01Regexp);
    private static Pattern mobile86Pattern = Pattern.compile(mobile86Regexp);
    private static Pattern mobileA86Pattern = Pattern.compile(mobileA86Regexp);
    private static Pattern local7FixedPhonePattern = Pattern.compile(local7FixedPhoneRegexp);
    private static Pattern local8FixedPhonePattern = Pattern.compile(local8FixedPhoneRegexp);
    private static Pattern remote3AreaCode8FixedPhonePattern = Pattern.compile(remote3AreaCode8FixedPhoneRegexp);
    private static Pattern remote4AreaCode8FixedPhonePattern = Pattern.compile(remote4AreaCode8FixedPhoneRegexp);
    private static Pattern remote4AreaCode7FixedPhonePattern = Pattern.compile(remote4AreaCode7FixedPhoneRegexp);
    private static Pattern begin8PhonePattern = Pattern.compile(begin8PhoneRegexp);

    private final static int errorType = 0;
    private final static int mobileType = 11;
    private final static int mobile86Type = 12;
    private final static int mobileA86Type = 13;
    private final static int mobile01Type = 14;
    private final static int local7FixedPhoneType = 21;
    private final static int local8FixedPhoneType = 22;
    private final static int remote3AreaCode8FixedPhoneType = 23;
    private final static int remote4AreaCode8FixedPhoneType = 24;
    private final static int remote4AreaCode7FixedPhoneType = 25;
    private final static int begin8PhoneType = 31;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private UserBpmTaskService userBpmTaskService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private PhoneChangeDao phoneChangeDao;

    @Autowired
    private MpZoneService mpZoneService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private UserService userService;

    @Override
    protected GenericDao<Phone, Long> getGenericDao() {
        return phoneDao;
    }

    public Phone get(Long phoneId) {
        return phoneDao.get(phoneId);
    }

    public Phone addPhone(Phone phone) throws ServiceTransactionException {
        Contact contact = contactService.get(phone.getContactid());
        if (checkExistSameNameAndPhone(phone.getPhoneid(), contact.getName(), phone.getPhn1(), phone.getPhn2(), phone.getPhn3())) {
            throw new ServiceTransactionException("电话号码" + phone.getPhn2() + "已绑定在相同客户名的不同客户编号身上。");
        }
        
        phone.setPhoneNum(splicePhoneNum(phone));
        return phoneDao.save(phone);
    }

    public Phone addPhoneWithCkeckSelfExist(Phone phone) throws ServiceTransactionException {
        if (checkExistPhone(phone)) {
            throw new ServiceTransactionException("该客户已经存在电话号码：" + phone.getPhn2());
        }
        return phoneDao.save(phone);
    }

    public Phone addPhoneWithCkeckSelfExistForService(Phone phone) {
        if (checkExistPhone(phone)) {
            logger.error("该客户已经存在电话号码：" + phone.getPhn2());
            return new Phone();
        }
        return phoneDao.save(phone);
    }


    public Phone addPhoneWithProcessPrm(Phone phone) throws ServiceTransactionException {
        if (checkExistPhone(phone)) {
            throw new ServiceTransactionException("该客户已经存在电话号码：" + phone.getPhn2());
        }
        if (StringUtils.equalsIgnoreCase("Y", phone.getPrmphn())) {
            phone.setPrmphn("N");
            phone = phoneDao.save(phone);
            setPrimePhone(phone.getContactid(), phone.getPhoneid().toString());

        }else if (StringUtils.equalsIgnoreCase("B", phone.getPrmphn())) {
            phone.setPrmphn("N");
            phone = phoneDao.save(phone);
            setBackupPhone(phone.getContactid(), phone.getPhoneid().toString());

        } else phone = phoneDao.save(phone);
        return phone;
    }

    private String splicePhoneNum(Phone phone) {
        StringBuffer phoneNum = new StringBuffer();
        if (StringUtils.isNotBlank(phone.getPhn1())) phoneNum.append(phone.getPhn1() + "-");
        if (StringUtils.isNotBlank(phone.getPhn2())) phoneNum.append(phone.getPhn2());
        if (StringUtils.isNotBlank(phone.getPhn3())) phoneNum.append("-" + phone.getPhn3());
        return phoneNum.toString();
    }

    public void addPhoneList(List<Phone> phones) throws ServiceTransactionException {
        for (Phone phone : phones) {
            addPhone(phone);
        }
    }

    public void updatePhone(Phone phone) throws ServiceTransactionException {
        Contact contact = contactService.get(phone.getContactid());

        boolean isInValidByName = checkExistSameNameAndPhone(phone.getPhoneid(), contact.getName(), phone.getPhn1(), phone.getPhn2(), phone.getPhn3());
        boolean isInValidByContactId = this.checkExistSameNameAndPhone(phone.getPhoneid(), contact.getName(), 
        		phone.getPhn2(), contact.getContactid(), phone.getPhonetypid(), phone.getPhn1(), phone.getPhn3()); 

        //如果根据Name和contactId都无效，则是同一个contact 拥有相同的号码，不执行任何操作
        //Continue, do nothing
        //如果根据Name无效，根据contactId有效，则是同一号码绑定在相同姓名的不同客户身上，此种情况（一般是一个客户手机号码废弃被SP回收）大多是同一个客户录了两次，则应该报错
        if(isInValidByName && !isInValidByContactId) {
        	throw new ServiceTransactionException("电话号码已绑定在相同客户名的不同客户编号身上，请确认是否将一个客户录了两次");
        //如果根据Name和contactId都是有效的，则将该记录录入
        }else if(!isInValidByName && !isInValidByContactId) {
        	phone.setPhoneNum(splicePhoneNum(phone));
            phoneDao.updatePhone(phone);
            if ("Y".equalsIgnoreCase(phone.getPrmphn())) {
                this.setPrimePhone(phone.getContactid(), phone.getPhoneid().toString());
            }
        }
    }

    public void updatePhoneList(List<Phone> phones) throws ServiceTransactionException {
        for (Phone phone : phones) {
            updatePhone(phone);
        }
    }

    public List<Phone> findByPhoneFullNum(String areaCode, String phoneNum, String childNum) {
        return phoneDao.findByPhoneFullNum(areaCode, phoneNum, childNum);
    }

    public Phone findByPhoneFullNumAndContactId(String areaCode, String phoneNum, String childNum, String contactId) {
        List<Phone> phoneList = phoneDao.findByPhoneFullNumAndContactId(areaCode, phoneNum, childNum, contactId);
        return phoneList == null || phoneList.size() == 0 ? new Phone() : phoneList.get(0);
    }

    public void deletePhone(Phone phone) throws ServiceTransactionException {
        if (StringUtils.isNotBlank(phone.getContactid())) {
            Phone primePhone = phoneDao.findPrimePhone(phone.getContactid());
            if (primePhone.getPhoneid().equals(phone.getPhoneid())) {
                throw new ServiceTransactionException("phone=" + primePhone.getPhn2() + "为主电话，无法删除，请先重置主电话。");
            }
        }
        phoneDao.remove(phone.getPhoneid());
    }

    public void deletePhoneList(List<Phone> phoneList) throws ServiceTransactionException {
        for (Phone phone : phoneList) {
            deletePhone(phone);
        }
    }

    @Mask(depth = 1)
    public Map<String, Object> findByContactId(String contactId, DataGridModel dataGridModel) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("total", phoneDao.findCountByContactId(contactId));
        List<Phone> contactPhoneList = phoneDao.findByContactId(contactId, dataGridModel.getStartRow(), dataGridModel.getRows());
        for (Phone phone : contactPhoneList) {
            phone.setPhn2(processMobileNum(phone));
            phone.setState(phone.getState() == null ? 4 : phone.getState());
            saveOrUpdate(phone);
        }
        pageMap.put("rows", contactPhoneList);
        return pageMap;
    }

    private String processMobileNum(Phone phone) {
        if (StringUtils.equals(phone.getPhonetypid(), "4") && phone.getPhn2().startsWith("0"))
            return phone.getPhn2().substring(1);
        return phone.getPhn2();
    }

    public void setPrimePhone(String contactId, String phoneId) {
        phoneDao.clearPrimePhone(contactId);
        phoneDao.setPrimePhone(contactId, phoneId);
    }

    public void setBackupPhone(String contactId, String phoneId) {
        List<Phone> backupPhones = phoneDao.findBackupPhoneByContactId(contactId, phoneId);
        if (backupPhones.size() > 1) {
            phoneDao.unsetBackupPhone(backupPhones.get(0).getPhoneid().toString());
        }
        phoneDao.setBackupPhone(phoneId);
    }

    public void unsetBackupPhone(String phoneId) {
        phoneDao.unsetBackupPhone(phoneId);
    }


    /**
     * <p>Title: applyAddRequest</p>
     * <p>Description: 新增地址审批流程</p>
     *
     * @param phone
     * @param remark
     * @param userId
     * @param deptId
     * @param batchId
     * @see com.chinadrtv.erp.uc.service.PhoneService#applyAddRequest(com.chinadrtv.erp.model.Phone, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void applyAddRequest(Phone phone, String remark, String userId, String deptId, String batchId) {
        sessionFactory.getCurrentSession().evict(phone);
        Phone emptyPhone = new Phone();

        emptyPhone = phoneDao.save(emptyPhone);
        Long phoneId = emptyPhone.getPhoneid();

        UserBpmTask userBpmTask = new UserBpmTask();
        userBpmTask.setChangeObjID(phoneId.toString());
        userBpmTask.setUpdateDate(new Date());
        userBpmTask.setUpdateUser(userId);
        userBpmTask.setRemark(remark);
        userBpmTask.setBatchID(batchId);
        Integer bizType = UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex();
        userBpmTask.setBusiType(bizType.toString());

        String instanceId = userBpmTaskService.createApprovingTask(userBpmTask);

        List<PropertyDescriptor> propDescList = PojoUtils.compare(new Phone(), phone);

        PhoneChange phoneChange = new PhoneChange();
        phoneChange.setPhoneid(phoneId);
        phoneChange.setCreateDate(new Date());
        phoneChange.setCreateUser(userId);
        PojoUtils.invoke(phone, phoneChange, propDescList);

        phoneChange.setProcInstId(instanceId);

        phoneChangeDao.save(phoneChange);

    }


    /**
     * <p>Title: finishAddRequest</p>
     * <p>Description: 新增地址审批流程结束</p>
     *
     * @param phoneId
     * @param remark
     * @param userId
     * @param instId
     * @throws Exception
     * @see com.chinadrtv.erp.uc.service.PhoneService#finishAddRequest(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void finishAddRequest(Long phoneId, String remark, String applyUser, String approveUser, String instId) throws Exception {
        Phone phone = new Phone();

        userBpmTaskService.approveChangeRequest(applyUser, approveUser, instId, remark);

        PhoneChange phoneChange = phoneChangeDao.queryByTaskId(phoneId, instId);

        List<PropertyDescriptor> propDescList = PojoUtils.getNotNullPropertyDescriptor(phoneChange);
        PojoUtils.invoke(phoneChange, phone, propDescList);

        phone.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);

        String contactId = phone.getContactid();
        if (phone.getPrmphn().equals("Y")) {
            phoneDao.clearPrimePhone(contactId);
        } else if (phone.getPrmphn().equals("B")) {
            this.setBackupPhone(contactId, phone.getPhoneid() + "");
        }

        this.updatePhone(phone);
    }


    /**
     * <p>Title: applyUpdateRequest</p>
     * <p>Description: </p>
     *
     * @param phone
     * @param remark
     * @param userId
     * @param deptId
     * @param batchId
     * @see com.chinadrtv.erp.uc.service.PhoneService#applyUpdateRequest(com.chinadrtv.erp.model.Phone, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Deprecated
    public void applyUpdateRequest(Phone phone, String remark, String userId, String deptId, String batchId) {
        Long phoneId = phone.getPhoneid();

        UserBpmTask userBpmTask = new UserBpmTask();
        userBpmTask.setChangeObjID(phoneId.toString());
        userBpmTask.setUpdateDate(new Date());
        userBpmTask.setUpdateUser(userId);
        userBpmTask.setRemark(remark);
        userBpmTask.setBatchID(batchId);
        Integer bizType = UserBpmTaskType.CONTACT_PHONE_CHANGE.getIndex();
        userBpmTask.setBusiType(bizType.toString());

        String instanceId = userBpmTaskService.createApprovingTask(userBpmTask);

        Phone rawPhone = phoneDao.get(phoneId);
        rawPhone.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
        phoneDao.saveOrUpdate(rawPhone);
        Session session = sessionFactory.getCurrentSession();
        session.evict(rawPhone);

        if (phone.equals(rawPhone)) {
            throw new BizException("当前电话与修改的电话没有任何差异");
        }

        List<PropertyDescriptor> propDescList = PojoUtils.compare(rawPhone, phone);

        PhoneChange phoneChange = new PhoneChange();
        phoneChange.setPhoneid(phoneId);
        phoneChange.setCreateDate(new Date());
        phoneChange.setCreateUser(userId);
        PojoUtils.invoke(phone, phoneChange, propDescList);

        phoneChange.setProcInstId(instanceId);

        phoneChangeDao.save(phoneChange);

    }

    /**
     * <p>Title: finishUpdateRequest</p>
     * <p>Description: </p>
     *
     * @param phoneId
     * @param remark
     * @param userId
     * @param instId
     * @throws Exception
     * @see com.chinadrtv.erp.uc.service.PhoneService#finishUpdateRequest(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
     */
    @Deprecated
    public void finishUpdateRequest(Long phoneId, String remark, String applyUser, String approveUser, String instId) throws Exception {
        Phone phone = phoneDao.get(phoneId);

        userBpmTaskService.approveChangeRequest(applyUser, approveUser, instId, remark);

        PhoneChange phoneChange = phoneChangeDao.queryByTaskId(phoneId, instId);

        List<PropertyDescriptor> propDescList = PojoUtils.getNotNullPropertyDescriptor(phoneChange);
        PojoUtils.invoke(phoneChange, phone, propDescList);

        phone.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED);
        this.updatePhone(phone);
    }

    @Override
    public void updateLastCallDate(UpdateLastCallDateDto updateLastCallDateDto) {
        phoneDao.updateLastCallDate(updateLastCallDateDto);
    }

    private boolean checkExistSameNameAndPhone(Long phoneId, String name, String phn1, String phn2, String phn3) {
        return phoneDao.checkExistSameNameAndPhone(phoneId, name, phn1, phn2, phn3);
    }

    private boolean checkExistSameNameAndPhone(Long phoneId, String name, String phn2, String contactId, String phoneType, String phn1, String phn3) {
        return phoneDao.checkExistSameNameAndPhone(phoneId, name, phn2, contactId, phoneType, phn1, phn3);
    }

    @Override
    public boolean checkExistSameNameAndPhone(Phone phone) {
        try {
            Contact contact = contactService.get(phone.getContactid());
            return checkExistSameNameAndPhone(phone.getPhoneid(), contact.getName(), phone.getPhn1(), phone.getPhn2(), phone.getPhn3());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean checkExistPhone(Phone phone) {
        try {
            return phoneDao.checkExistPhone(phone);
        } catch (Exception e) {
            return false;
        }
    }

    private int checkPhoneType(String phone) {
        if (mobilePattern.matcher(phone).matches()) {
            return mobileType;
        }
        if (mobile01Pattern.matcher(phone).matches()) {
            return mobile01Type;
        }
        if (mobile86Pattern.matcher(phone).matches()) {
            return mobile86Type;
        }
        if (mobileA86Pattern.matcher(phone).matches()) {
            return mobileA86Type;
        }
        if (local7FixedPhonePattern.matcher(phone).matches()) {
            return local7FixedPhoneType;
        }
        if (local8FixedPhonePattern.matcher(phone).matches()) {
            return local8FixedPhoneType;
        }
        if (remote3AreaCode8FixedPhonePattern.matcher(phone).matches()) {
            return remote3AreaCode8FixedPhoneType;
        }
        if (remote4AreaCode8FixedPhonePattern.matcher(phone).matches()) {
            return remote4AreaCode8FixedPhoneType;
        }
        if (remote4AreaCode7FixedPhonePattern.matcher(phone).matches()) {
            return remote4AreaCode7FixedPhoneType;
        }
        if (begin8PhonePattern.matcher(phone).matches()) {
            return begin8PhoneType;
        }
        return errorType;
    }

    @Override
    public PhoneAddressDto splicePhone(String phone) {
        switch (checkPhoneType(phone)) {
            case mobileType:
                return createMobileTypeDto(phone.substring(0, 7), phone);
            case mobile01Type:
                phone = phone.substring(1, phone.length());
                return createMobileTypeDto(phone.substring(0, 7), phone);
            case mobile86Type:
                return createMobileTypeDto(phone.substring(2, 9), phone.substring(2, 13));
            case mobileA86Type:
                return createMobileTypeDto(phone.substring(3, 10), phone.substring(3, 14));
            case local7FixedPhoneType:
                try {
                    DepartmentInfo departmentInfo = userService.getDepartmentInfo(SecurityHelper.getLoginUser().getDepartment());
                    return createFixedPhoneTypeDto(departmentInfo.getAreaCode(), phone);
                } catch (ServiceException e) {
                    logger.error( "解析本地电话时，获取当前坐席的部门信息出错，无法获取本地区号。", e);
                    return new PhoneAddressDto();
                }
            case local8FixedPhoneType:
                try {
                    DepartmentInfo departmentInfo = userService.getDepartmentInfo(SecurityHelper.getLoginUser().getDepartment());
                    return createFixedPhoneTypeDto(departmentInfo.getAreaCode(), phone);
                } catch (ServiceException e) {
                    logger.error( "解析本地电话时，获取当前坐席的部门信息出错，无法获取本地区号。", e);
                    return new PhoneAddressDto();
                }
            case remote3AreaCode8FixedPhoneType:
                return createFixedPhoneTypeDto(phone.substring(0, 3), phone.substring(3, 11));
            case remote4AreaCode8FixedPhoneType:
                return createFixedPhoneTypeDto(phone.substring(0, 4), phone.substring(4, 12));
            case remote4AreaCode7FixedPhoneType:
                return createFixedPhoneTypeDto(phone.substring(0, 4), phone.substring(4, 11));
            case begin8PhoneType:
                return splicePhone(phone.substring(1, phone.length()));
            default:
                return createErrorPhoneTypeDto(phone);
        }
    }

    private PhoneAddressDto createMobileTypeDto(String subPhone, String phone2) {
        PhoneAddressDto phoneAddressDto = new PhoneAddressDto();
        try {
            MpZone mpZone = mpZoneService.queryByPhoneNo(subPhone);
            CityAll city = cityService.getByAreaCode(mpZone.getAreacode());
            phoneAddressDto.setProvid(city.getProvid());
            phoneAddressDto.setCityid(city.getCityid());
            phoneAddressDto.setProvName(mpZone.getProvince());
            phoneAddressDto.setCityName(mpZone.getCity());
        } catch (Exception e) {
            logger.error( "解析手机号码出错。电话为:" + phone2, e);
        }
        phoneAddressDto.setPhonetypid("4");
        phoneAddressDto.setPhn2(phone2);
        return phoneAddressDto;
    }

    private PhoneAddressDto createErrorPhoneTypeDto(String phone) {
        PhoneAddressDto phoneAddressDto = new PhoneAddressDto();
        phoneAddressDto.setPhonetypid("6"); // 6代表不正常号码
        phoneAddressDto.setPhn2(phone);
        return phoneAddressDto;
    }

    private PhoneAddressDto createFixedPhoneTypeDto(String phone1, String phone2) {
        PhoneAddressDto phoneAddressDto = new PhoneAddressDto();
        try {
            CityAll cityAll = cityService.getByAreaCode(phone1);
            phoneAddressDto.setProvid(cityAll.getProvid());
            phoneAddressDto.setCityid(cityAll.getCityid());
            phoneAddressDto.setCityName(cityAll.getCityname());
            phoneAddressDto.setProvName(provinceService.get(cityAll.getProvid()).getChinese());
        } catch (Exception e) {
            logger.error( "解析固定电话出错。电话为:" + phone1 + phone2, e);
        }
        phoneAddressDto.setPhonetypid("1");
        phoneAddressDto.setPhn1(phone1);
        phoneAddressDto.setPhn2(phone2);
        return phoneAddressDto;
    }

    /**
     * <p>Title: queryMobilePhoneByContact</p>
     * <p>Description: </p>
     *
     * @param contactId
     * @return List<Phone>
     * @see com.chinadrtv.erp.uc.service.PhoneService#queryMobilePhoneByContact(java.lang.String)
     */
    @Override
    public List<Phone> queryMobilePhoneByContact(String contactId) {
        return phoneDao.queryMobilePhoneByContact(contactId);
    }

    @Override
    public void addToBlackList(Long phoneId) {
        phoneDao.addToBlackList(phoneId);
    }

    @Override
    public void removeFromBlackList(Long phoneId) {
        phoneDao.removeFromBlackList(phoneId);
    }

    public void addOrUpdateNewPhone(Phone phone) throws ServiceTransactionException {

        if (phone.getPrmphn().equals("Y")) {
            phoneDao.clearPrimePhone(phone.getContactid());
        } else if (phone.getPrmphn().equals("B")) {
            this.setBackupPhone(phone.getContactid(), phone.getPhoneid() + "");
        }
        if (phone.getPhoneid() == null) {
            this.addPhone(phone);
        } else {
            this.updatePhone(phone);
        }
    }

    /* (非 Javadoc)
    * <p>Title: getPhonesByContactId</p>
	* <p>Description: </p>
	* @param contactId
	* @return
	* @see com.chinadrtv.erp.uc.service.PhoneService#getPhonesByContactId(java.lang.String)
	*/
    @Override
    @Mask
    public List<Phone> getPhonesByContactId(String contactId) {
        List<Phone> phones = phoneDao.findByContactId(contactId, 0, Integer.MAX_VALUE);
        return phones;
    }

    public void updatePhoneDirect(Phone phone) {
        phoneDao.updatePhone(phone);
    }
}
