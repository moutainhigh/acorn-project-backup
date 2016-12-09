package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title: CustomerFrontDto
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class CustomerFrontDto implements Serializable {
    public static String CONTACT = "1";
    public static String POTENTIAL_CONTACT = "2";
    
    private String customerType;
    private String customerId;
    private String name;
    private String sex;
    private Date birthday;
    private String marriage;
    private String income;
    private String occupationStatus;
    private String education;
    private String car;
    private Integer carmoney1;
    private Integer carmoney2;
    private String children;
    private Date childrenage;
    private String isHasElder;
    private Date elderBirthday;
    private String address; //
    private String  detailedAddress;//详细地址

    private String level;
    private String crusr;
    private Date crdt;
    private String crdtStr;
    private String addressid;
    private Integer state;
    private String email;
    private String weixin;

    private String insureStatus;
    private String insureAge;
    private String insureNote;

    public List<Map<String, String>> getInsureProdList() {
        return insureProdList;
    }

    public void setInsureProdList(List<Map<String, String>> insureProdList) {
        this.insureProdList = insureProdList;
    }

    private List<Map<String,String>> insureProdList;

    public List<Map<String, String>> getInsureProdToList() {
        return insureProdToList;
    }

    public void setInsureProdToList(List<Map<String, String>> insureProdToList) {
        this.insureProdToList = insureProdToList;
    }

    private List<Map<String,String>> insureProdToList;


    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getBirthdayStr() {
        if (birthday == null)
            return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(birthday);
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public Integer getCarmoney1() {
        return carmoney1;
    }

    public void setCarmoney1(Integer carmoney1) {
        this.carmoney1 = carmoney1;
    }

    public String getChildrenageStr() {
        if (childrenage == null)
            return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(childrenage);
    }

    public Date getChildrenage() {
        return childrenage;
    }

    public void setChildrenage(Date childrenage) {
        this.childrenage = childrenage;
    }

    public String getHasElder() {
        return isHasElder;
    }

    public void setHasElder(String hasElder) {
        isHasElder = hasElder;
    }

    public String getOccupationStatus() {
        return occupationStatus;
    }

    public void setOccupationStatus(String occupationStatus) {
        this.occupationStatus = occupationStatus;
    }

    public String getElderBirthdayStr() {
        if (elderBirthday == null)
            return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(elderBirthday);
    }

    public Date getElderBirthday() {
        return elderBirthday;
    }

    public void setElderBirthday(Date elderBirthday) {
        this.elderBirthday = elderBirthday;
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCrusr() {
		return crusr;
	}

	public void setCrusr(String crusr) {
		this.crusr = crusr;
	}

    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

	public String getAddressid() {
		return addressid;
	}

	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}


    public Integer getCarmoney2() {
        return carmoney2;
    }

    public void setCarmoney2(Integer carmoney2) {
        this.carmoney2 = carmoney2;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public String getCrdtStr() {
		return crdtStr;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public void setCrdtStr(String crdtStr) {
		this.crdtStr = crdtStr;
	}

    public String getInsureStatus() {
        return insureStatus;
    }

    public void setInsureStatus(String insureStatus) {
        this.insureStatus = insureStatus;
    }

    public String getInsureAge() {
        return insureAge;
    }

    public void setInsureAge(String insureAge) {
        this.insureAge = insureAge;
    }

    public String getInsureNote() {
        return insureNote;
    }

    public void setInsureNote(String insureNote) {
        this.insureNote = insureNote;
    }
}
