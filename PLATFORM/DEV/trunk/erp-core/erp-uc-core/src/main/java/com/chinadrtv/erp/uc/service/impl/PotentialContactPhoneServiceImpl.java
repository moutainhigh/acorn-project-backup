package com.chinadrtv.erp.uc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.PotentialContactPhone;
import com.chinadrtv.erp.uc.dao.PhoneDao;
import com.chinadrtv.erp.uc.dao.PotentialContactPhoneDao;
import com.chinadrtv.erp.uc.dto.UpdateLastCallDateDto;
import com.chinadrtv.erp.uc.service.PotentialContactPhoneService;
import com.chinadrtv.erp.user.aop.Mask;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-10
 * Time: 下午1:30
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PotentialContactPhoneServiceImpl extends GenericServiceImpl<PotentialContactPhone, Long> implements PotentialContactPhoneService {

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private PotentialContactPhoneDao potentialContactPhoneDao;

    @Override
    protected GenericDao<PotentialContactPhone, Long> getGenericDao() {
        return potentialContactPhoneDao;
    }

    public PotentialContactPhone addPotentialContactPhone(PotentialContactPhone potentialContactPhone) throws ServiceTransactionException {
//        if (notExistPhoneNum(potentialContactPhone.getPhone1(), potentialContactPhone.getPhone2(), potentialContactPhone.getPhone3())) {
            potentialContactPhone.setPhoneNum(splicePhoneNum(potentialContactPhone));
            return potentialContactPhoneDao.save(potentialContactPhone);
//        }
//        return null;
    }

    private String splicePhoneNum(PotentialContactPhone phone) {
        StringBuffer phoneNum = new StringBuffer();
        if (StringUtils.isNotBlank(phone.getPhone1())) phoneNum.append(phone.getPhone1() + "-");
        if (StringUtils.isNotBlank(phone.getPhone2())) phoneNum.append(phone.getPhone2());
        if (StringUtils.isNotBlank(phone.getPhone3())) phoneNum.append("-" + phone.getPhone3());
        return phoneNum.toString();
    }

    private boolean notExistPhoneNum(String areaCode, String phoneNum, String childNum) throws ServiceTransactionException {
        List<Phone> phones = phoneDao.findByPhoneFullNum(areaCode, phoneNum, childNum);
        if (phones == null || phones.isEmpty()) {
            List<PotentialContactPhone> potentialContactPhones = potentialContactPhoneDao.findByPhoneFullNum(areaCode, phoneNum, childNum);
            if (potentialContactPhones == null || potentialContactPhones.isEmpty()) {
                return true;
            }
            throw new ServiceTransactionException("phoneNum=" + phoneNum + "在潜客电话表中已经存在。");
        }
        throw new ServiceTransactionException("phoneNum=" + phoneNum + "在正式客户电话表中已经存在。");
    }

    public void addPotentialContactPhoneList(List<PotentialContactPhone> potentialContactPhones) throws ServiceTransactionException {
        for (PotentialContactPhone potentialContactPhone : potentialContactPhones) {
            addPotentialContactPhone(potentialContactPhone);
        }
    }

    public void setPrimePotentialContactPhone(String potentialContactId, String potentialContactPhoneId) {
        potentialContactPhoneDao.clearPrimePotentialContactPhone(potentialContactId);
        potentialContactPhoneDao.setPrimePotentialContactPhone(potentialContactId, potentialContactPhoneId);
    }

    public void updatePotentialContactPhone(PotentialContactPhone potentialContactPhone) throws ServiceTransactionException {
        PotentialContactPhone phoneInDb = potentialContactPhoneDao.get(potentialContactPhone.getId());
        if (changedPhoneNum(phoneInDb, potentialContactPhone) &&
                !notExistPhoneNum(potentialContactPhone.getPhone1(), potentialContactPhone.getPhone2(), potentialContactPhone.getPhone3())) {
            return;
        }
        phoneInDb.setPhoneTypeId(potentialContactPhone.getPhoneTypeId());
        phoneInDb.setPhone1(potentialContactPhone.getPhone1());
        phoneInDb.setPhone2(potentialContactPhone.getPhone2());
        phoneInDb.setPhone3(potentialContactPhone.getPhone3());
        phoneInDb.setPhoneNum(splicePhoneNum(potentialContactPhone));
        phoneInDb.setPrmphn(potentialContactPhone.getPrmphn());
        potentialContactPhoneDao.saveOrUpdate(phoneInDb);
        if ("Y".equals(potentialContactPhone.getPrmphn())) {
            this.setPrimePotentialContactPhone(phoneInDb.getPotentialContactId(), potentialContactPhone.getId().toString());
        }
    }

    private boolean changedPhoneNum(PotentialContactPhone phoneInDB, PotentialContactPhone phone) {
        return !(StringUtils.equals(phoneInDB.getPhone1(), phone.getPhone1()) &&
                StringUtils.equals(phoneInDB.getPhone2(), phone.getPhone2()) &&
                StringUtils.equals(phoneInDB.getPhone3(), phone.getPhone3()));
    }

    public void updatePotentialContactPhoneList(List<PotentialContactPhone> potentialContactPhones) throws ServiceTransactionException {
        for (PotentialContactPhone potentialContactPhone : potentialContactPhones) {
            updatePotentialContactPhone(potentialContactPhone);
        }
    }

    @Mask(depth=2)
    public List<PotentialContactPhone> getPotentialContactPhones(Long potentialContactId) {
        return potentialContactPhoneDao.getPotentialContactPhones(potentialContactId);
    }

    public List<PotentialContactPhone> findByPhoneFullNum(String areaCode, String phoneNum, String childNum) {
        return potentialContactPhoneDao.findByPhoneFullNum(areaCode, phoneNum, childNum);
    }

    public PotentialContactPhone findByPhoneFullNumAndPotentialContactId(String areaCode, String phoneNum, String childNum, String potentialContactId) {
        List<PotentialContactPhone> phoneList = potentialContactPhoneDao.findByPhoneFullNumAndPotentialContactId(areaCode, phoneNum, childNum, potentialContactId);
        return phoneList == null || phoneList.size() == 0 ? new PotentialContactPhone() : phoneList.get(0);
    }

    public void deletePhone(PotentialContactPhone phone) throws ServiceTransactionException {
        PotentialContactPhone primePhone = potentialContactPhoneDao.findPrimePhone(phone.getPotentialContactId());
        phone = potentialContactPhoneDao.get(phone.getId());
        if (primePhone.getId().equals(phone.getId())) {
            throw new ServiceTransactionException("phone=" + primePhone.getPhone2()+ "为主电话，无法删除，请先重置主电话。");
        }
        primePhone.getPotentialContact().getPotentialContactPhones().remove(phone);
        phone.setPotentialContact(null);
        potentialContactPhoneDao.remove(phone.getId());
    }

    public void deletePhoneList(List<PotentialContactPhone> phoneList) throws ServiceTransactionException {
        for (PotentialContactPhone phone : phoneList) {
            deletePhone(phone);
        }
    }

    public Map<String, Object> findByPotentialContactId(String potentialContactId, DataGridModel dataGridModel) {
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("total", potentialContactPhoneDao.findCountByPotentialContactId(potentialContactId));
        List<PotentialContactPhone> potentialContactPhoneList = potentialContactPhoneDao.findByPotentialContactId(potentialContactId, dataGridModel.getStartRow(), dataGridModel.getRows());
        for (PotentialContactPhone phone : potentialContactPhoneList) {
            phone.setPhone2(processMobileNum(phone));
        }
        pageMap.put("rows", potentialContactPhoneList);
        return pageMap;
    }

    private String processMobileNum(PotentialContactPhone phone) {
        if (StringUtils.equals(phone.getPhoneTypeId(), "4") && phone.getPhone2().startsWith("0"))
            return phone.getPhone2().substring(1);
        return phone.getPhone2();
    }

    public PotentialContactPhone findByPotentialPhoneId(Long potentialPhoneId) {
        
        return potentialContactPhoneDao.get(potentialPhoneId);
    }    

    @Override
    public void updateLastCallDate(UpdateLastCallDateDto updateLastCallDateDto) {
        potentialContactPhoneDao.updateLastCallDate(updateLastCallDateDto);
    }

    @Override
    public void addToBlackList(Long phoneId) {
        potentialContactPhoneDao.addToBlackList(phoneId);
    }

    @Override
    public void removeFromBlackList(Long phoneId) {
        potentialContactPhoneDao.removeFromBlackList(phoneId);
    }

    public void setBackupPotentialContactPhone(String potentialContactId, String potentialContactPhoneId) {
        List<PotentialContactPhone> backupPhones = potentialContactPhoneDao.findBackupPCPhoneByPContactId(potentialContactId, potentialContactPhoneId);
        if (backupPhones.size()>1) {
            potentialContactPhoneDao.unsetBackupPCPhone(backupPhones.get(0).getId().toString());
        }
        potentialContactPhoneDao.setBackupPCPhone(potentialContactPhoneId);
    }

    @Override
    public void unsetBackupPotentialContactPhone(String potentialContactPhoneId) {
        potentialContactPhoneDao.unsetBackupPCPhone(potentialContactPhoneId);
    }
}
