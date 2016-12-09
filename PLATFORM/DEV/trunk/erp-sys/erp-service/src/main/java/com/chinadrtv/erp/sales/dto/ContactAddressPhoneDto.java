package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.uc.dto.AddressDto;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-6-3
 * Time: 下午4:18
 * 使用包含 address对象
 */
public class ContactAddressPhoneDto  {
    private AddressDto addressDto =new AddressDto();
    private List<Phone> phoneList = new ArrayList<Phone>();
    private String type;//收货类型
    private String contactName; //收货人名称
    private String prefixAddress;//四级地址
    private List<String> phoneStrList = new ArrayList<String>();
    private String phone;
    public  ContactAddressPhoneDto()  {
    }

    public  ContactAddressPhoneDto(AddressDto addressDto,List<Phone> phoneList)  {
        this.addressDto = addressDto;
        this.phoneList = phoneList;
        this.initPhoneData();
    }

    private void initPhoneData() {
        if (phoneList.isEmpty()) {
            return;
        }

        for (Phone phone : phoneList) {
            if ("Y".equalsIgnoreCase(phone.getPrmphn())) {
                phoneList.remove(phone);
                phoneList.add(0, phone);
                break;
            }
        }

        StringBuffer phoneBuffer = new StringBuffer();
        for (int i = 0; i < phoneList.size(); i++) {
            Phone otherPhone = phoneList.get(i);
            String phn2 = otherPhone. getPhoneMask()!=null? otherPhone.getPhoneMask():otherPhone.getPhn2();
            otherPhone.setPhn2(phn2);
            phoneBuffer.append(getPhoneBuffer(otherPhone));
            if (i != phoneList.size() - 1) {
                phoneBuffer.append("/");
            }
            phoneStrList.add(JSONObject.fromObject(otherPhone).toString());
            this.setPhone(phoneBuffer.toString());
        }
    }

    private String getPhoneBuffer(Phone phone) {
        StringBuffer buffer = new StringBuffer();
        if(StringUtils.isNotBlank(phone.getPhn1())){
            buffer.append(phone.getPhn1() + "-");
        }
        buffer.append(phone.getPhn2());
        if(StringUtils.isNotBlank(phone.getPhn3())){
            buffer.append("-" + phone.getPhn3());
        }
        return buffer.toString();
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getPhoneStrList() {
        return phoneStrList;
    }

    public void setPhoneStrList(List<String> phoneStrList) {
        this.phoneStrList = phoneStrList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrefixAddress() {
        if(StringUtils.isNotBlank(prefixAddress)){
            return  prefixAddress;
        }
        return addressDto.getProvinceName()+addressDto.getCityName()+addressDto.getCountyName()+addressDto.getAreaName();
    }

    public void setPrefixAddress(String prefixAddress) {
        this.prefixAddress = prefixAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Integer getCityId() {
        return addressDto.getCityId();
    }

    public Integer getCountyId() {
        return addressDto.getCountyId();
    }

    public String getAdditionalinfo() {
        return addressDto.getAdditionalinfo();
    }

    public String getCityName() {
        return addressDto.getCityName();
    }

    public String getProvinceName() {
        return addressDto.getProvinceName();
    }

    public String getFlag() {
        return addressDto.getFlag();
    }

    public Integer getAreaid() {
        return addressDto.getAreaid();
    }

    public String getContactid() {
        return addressDto.getContactid();
    }

    public void setState(String state) {
        addressDto.setState(state);
    }

    public void setFlag(String flag) {
        addressDto.setFlag(flag);
    }

    public void setContactid(String contactid) {
        addressDto.setContactid(contactid);
    }

    public void setAddressid(String addressid) {
        addressDto.setAddressid(addressid);
    }

    public String getIsdefault() {
        return addressDto.getIsdefault();
    }

    public void setAddrtypid(String addrtypid) {
        addressDto.setAddrtypid(addrtypid);
    }

    public String getState() {
        return addressDto.getState();
    }

    public String getAddress() {
        return addressDto.getAddress();
    }

    public void setAddress(String address) {
        addressDto.setAddress(address);
    }

    public void setArea(String area) {
        addressDto.setArea(area);
    }

    public void setAreaName(String areaName) {
        addressDto.setAreaName(areaName);
    }

    public String getZip() {
        return addressDto.getZip();
    }

    public void setProvinceName(String provinceName) {
        addressDto.setProvinceName(provinceName);
    }

    public void setCity(String city) {
        addressDto.setCity(city);
    }

    public void setZip(String zip) {
        addressDto.setZip(zip);
    }

    public String getAddressid() {
        return addressDto.getAddressid();
    }

    public String getAddrtypid() {
        return addressDto.getAddrtypid();
    }

    public void setIsdefault(String isdefault) {
        addressDto.setIsdefault(isdefault);
    }

    public void setAddrconfirm(String addrconfirm) {
        addressDto.setAddrconfirm(addrconfirm);
    }

    public String getArea() {
        return addressDto.getArea();
    }

    public String getCountyName() {
        return addressDto.getCountyName();
    }

    public void setCountyId(Integer countyId) {
        addressDto.setCountyId(countyId);
    }

    public void setCountyName(String countyName) {
        addressDto.setCountyName(countyName);
    }

    public void setAreaid(Integer areaid) {
        addressDto.setAreaid(areaid);
    }

    public void setAddconfirmation(String addconfirmation) {
        addressDto.setAddconfirmation(addconfirmation);
    }

    public String getAddrconfirm() {
        return addressDto.getAddrconfirm();
    }

    public void setAdditionalinfo(String additionalinfo) {
        addressDto.setAdditionalinfo(additionalinfo);
    }

    public void setCityName(String cityName) {
        addressDto.setCityName(cityName);
    }

    public String getAddconfirmation() {
        return addressDto.getAddconfirmation();
    }

    public String getCity() {
        return addressDto.getCity();
    }

    public void setCityId(Integer cityId) {
        addressDto.setCityId(cityId);
    }

    public String getAreaName() {
        return addressDto.getAreaName();
    }
    
    public String getAddressDesc() {
    	return addressDto.getAddressDesc();
    }
    
    public void setAddressDesc(String adddressDesc) {
    	addressDto.setAddressDesc(adddressDesc);
    }

    public String getReceiveAddress() {
        return addressDto.getReceiveAddress();
    }

    public Integer getAreaSpellid() {
        return addressDto.getAreaSpellid();
    }

    public String getStatus() {
        return addressDto.getStatus();
    }

    public void setStatus(String status) {
        addressDto.setStatus(status);
    }

    public void setInstId(String instId) {
        addressDto.setInstId(instId);
    }

    public void setCountySpellid(Integer countySpellid) {
        addressDto.setCountySpellid(countySpellid);
    }

    public Integer getCountySpellid() {
        return addressDto.getCountySpellid();
    }

    public void setReceiveAddress(String receiveAddress) {
        addressDto.setReceiveAddress(receiveAddress);
    }

    public void setAreaSpellid(Integer areaSpellid) {
        addressDto.setAreaSpellid(areaSpellid);
    }

    public void setComment(String comment) {
        addressDto.setComment(comment);
    }

    public String getComment() {
        return addressDto.getComment();
    }

    public String getInstId() {
        return addressDto.getInstId();
    }

    public String getAddressContactId() {
        return addressDto.getAddressContactId();
    }

    public void setAddressContactId(String addressContactId) {
        addressDto.setAddressContactId(addressContactId);
    }

    public Integer getAuditState() {
        return addressDto.getAuditState();
    }

    public void setAuditState(Integer auditState) {
        addressDto.setAuditState(auditState);
    }
}
