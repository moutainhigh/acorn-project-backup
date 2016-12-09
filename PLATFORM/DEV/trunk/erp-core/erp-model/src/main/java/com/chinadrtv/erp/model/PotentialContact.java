package com.chinadrtv.erp.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-7
 * Time: 涓嬪崍4:15
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "POTENTIAL_CONTACT", schema = "ACOAPP_MARKETING")
@Entity
public class PotentialContact implements java.io.Serializable {

	private static final long serialVersionUID = 5773826180938610335L;

	private Long id;  //娼滃缂栧彿
    
    private java.lang.String name;  //濮撳悕
    
    private java.lang.String gender;  //鎬у埆
    
    private String comments;//澶囨敞淇℃伅

    private Long call_Length;

    private Long contactId;
    
    private Long productId;

    private java.util.Date birthday;  //鐢熸棩

    private java.lang.String contacttype;  //瀹㈡埛绫诲瀷

    private java.lang.String customersource; //娼滃鏉ユ簮

    private java.lang.String crusr;  //璁板綍鍒涘缓浜�

    private java.util.Date crdt;  //璁板綍鍒涘缓鏃ユ湡
    
    private Set<PotentialContactPhone> potentialContactPhones;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POTENTIAL_CONTACT")
    @SequenceGenerator(name = "SEQ_POTENTIAL_CONTACT", sequenceName = "ACOAPP_MARKETING.SEQ_POTENTIAL_CONTACT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "GENDER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "CONTACT_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
    public String getContacttype() {
        return contacttype;
    }

    public void setContacttype(String contacttype) {
        this.contacttype = contacttype;
    }

    @Column(name = "CREATE_DATE", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @Column(name = "CREATE_USER", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    @Column(name = "BIRTHDAY", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    @Column(name = "CUSTOMER_SOURCE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getCustomersource() {
        return customersource;
    }

    public void setCustomersource(String customersource) {
        this.customersource = customersource;
    }

    @Column(name = "COMMENTS", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Column(name = "CALL_LENGTH", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
    public Long getCall_Length() {
        return call_Length;
    }

    public void setCall_Length(Long call_Length) {
        this.call_Length = call_Length;
    }

    @Column(name = "CONTACT_ID", length = 19)
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    @Column(name = "PRODUCT_ID", length = 19)
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @OneToMany(mappedBy="potentialContact", orphanRemoval=true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public Set<PotentialContactPhone> getPotentialContactPhones() {
		return potentialContactPhones;
	}

	public void setPotentialContactPhones(Set<PotentialContactPhone> potentialContactPhones) {
		this.potentialContactPhones = potentialContactPhones;
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
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((call_Length == null) ? 0 : call_Length.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
		result = prime * result + ((contacttype == null) ? 0 : contacttype.hashCode());
		result = prime * result + ((crdt == null) ? 0 : crdt.hashCode());
		result = prime * result + ((crusr == null) ? 0 : crusr.hashCode());
		result = prime * result + ((customersource == null) ? 0 : customersource.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		PotentialContact other = (PotentialContact) obj;
		if (birthday == null) {
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
		if (contacttype == null) {
			if (other.contacttype != null)
				return false;
		} else if (!contacttype.equals(other.contacttype))
			return false;
		if (crdt == null) {
			if (other.crdt != null)
				return false;
		} else if (!crdt.equals(other.crdt))
			return false;
		if (crusr == null) {
			if (other.crusr != null)
				return false;
		} else if (!crusr.equals(other.crusr))
			return false;
		if (customersource == null) {
			if (other.customersource != null)
				return false;
		} else if (!customersource.equals(other.customersource))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (potentialContactPhones == null) {
			if (other.potentialContactPhones != null)
				return false;
		} else if (!potentialContactPhones.equals(other.potentialContactPhones))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}
}
