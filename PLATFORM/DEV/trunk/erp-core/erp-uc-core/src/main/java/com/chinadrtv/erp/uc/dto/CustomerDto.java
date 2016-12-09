package com.chinadrtv.erp.uc.dto;

import com.chinadrtv.erp.model.Address;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-6
 * Time: 下午1:40
 * To change this template use File | Settings | File Templates.
 */
public class CustomerDto implements Serializable {
    //正式客户专用
    private String contactId;
    private String contactType;
    private String customerSource;
    private String dataType;
    private String crdt;
    private String crtm;
    private String crusr;
    private AddressDto addressDto;
    private String level;
    //潜客专用
    private String potentialContactId;
    private String productId;
    private String phone1;
    private String phone2;
    private String phone3;
    private Long call_Length;
    private String phoneType;
    private String comments;

    //通用
    private String name;
    private String sex;
    private String phoneNum;
    private String birthday;
    private String customerType; //正式客户 1  潜客 2


    public Date getBirthDayDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(birthday);
        } catch (Exception e) {
            return null;
        }
    }

    public Date getCrdtDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(crdt);
        } catch (Exception e) {
            return null;
        }
    }


	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
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

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(String customerSource) {
        this.customerSource = customerSource;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCrdt() {
        return crdt;
    }

    public void setCrdt(String crdt) {
        this.crdt = crdt;
    }

    public String getCrtm() {
        return crtm;
    }

    public void setCrtm(String crtm) {
        this.crtm = crtm;
    }

    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    public String getPotentialContactId() {
        return potentialContactId;
    }

    public void setPotentialContactId(String potentialContactId) {
        this.potentialContactId = potentialContactId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public Long getCall_Length() {
        return call_Length;
    }

    public void setCall_Length(Long call_Length) {
        this.call_Length = call_Length;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    /**
	 * <p>Title: hashCode</p>
	 * <p>Description: </p>
	 * @return
	 * @see java.lang.Object#hashCode()
	 */ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((addressDto == null) ? 0 : addressDto.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((call_Length == null) ? 0 : call_Length.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
		result = prime * result + ((contactType == null) ? 0 : contactType.hashCode());
		result = prime * result + ((crdt == null) ? 0 : crdt.hashCode());
		result = prime * result + ((crtm == null) ? 0 : crtm.hashCode());
		result = prime * result + ((crusr == null) ? 0 : crusr.hashCode());
		result = prime * result + ((customerSource == null) ? 0 : customerSource.hashCode());
		result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone1 == null) ? 0 : phone1.hashCode());
		result = prime * result + ((phone2 == null) ? 0 : phone2.hashCode());
		result = prime * result + ((phone3 == null) ? 0 : phone3.hashCode());
		result = prime * result + ((phoneNum == null) ? 0 : phoneNum.hashCode());
		result = prime * result + ((phoneType == null) ? 0 : phoneType.hashCode());
		result = prime * result + ((potentialContactId == null) ? 0 : potentialContactId.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		return result;
	}

	/** 
	 * <p>Title: equals</p>
	 * <p>Description: </p>
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */ 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDto other = (CustomerDto) obj;
		/*if (addressDto == null) {
			if (other.addressDto != null)
				return false;
		} else if (!addressDto.equals(other.addressDto))
			return false;*/
		if (level == null) {
			if (other.level != null)
				return false;
		} else if  (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (call_Length == null) {
			if (other.call_Length != null)
				return false;
		} else if (!call_Length.equals(other.call_Length))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (contactId == null) {
			if (other.contactId != null)
				return false;
		} else if (!contactId.equals(other.contactId))
			return false;
		if (contactType == null) {
			if (other.contactType != null)
				return false;
		} else if (!contactType.equals(other.contactType))
			return false;
		if (crdt == null) {
			if (other.crdt != null)
				return false;
		} else if (!crdt.equals(other.crdt))
			return false;
		if (crtm == null) {
			if (other.crtm != null)
				return false;
		} else if (!crtm.equals(other.crtm))
			return false;
		if (crusr == null) {
			if (other.crusr != null)
				return false;
		} else if (!crusr.equals(other.crusr))
			return false;
		if (customerSource == null) {
			if (other.customerSource != null)
				return false;
		} else if (!customerSource.equals(other.customerSource))
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone1 == null) {
			if (other.phone1 != null)
				return false;
		} else if (!phone1.equals(other.phone1))
			return false;
		if (phone2 == null) {
			if (other.phone2 != null)
				return false;
		} else if (!phone2.equals(other.phone2))
			return false;
		if (phone3 == null) {
			if (other.phone3 != null)
				return false;
		} else if (!phone3.equals(other.phone3))
			return false;
		if (phoneNum == null) {
			if (other.phoneNum != null)
				return false;
		} else if (!phoneNum.equals(other.phoneNum))
			return false;
		if (phoneType == null) {
			if (other.phoneType != null)
				return false;
		} else if (!phoneType.equals(other.phoneType))
			return false;
		if (potentialContactId == null) {
			if (other.potentialContactId != null)
				return false;
		} else if (!potentialContactId.equals(other.potentialContactId))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		return true;
	}
}
