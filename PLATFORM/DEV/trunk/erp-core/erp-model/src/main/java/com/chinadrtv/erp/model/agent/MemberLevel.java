package com.chinadrtv.erp.model.agent;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * <dl>
 *    <dt><b>Title:会员级别</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-23 下午1:29:09 
 *
 */
@Entity
@Table(name = "MEMBERSERVICE", schema = "IAGENT")
public class MemberLevel implements java.io.Serializable {

	private static final long serialVersionUID = -6200019142545849885L;
	private String memberlevelid;
	private String memberlevel;
	private Date crdt;
	private BigDecimal totalprice;
	private BigDecimal kgradeprice;
	private BigDecimal pointcoefficient;
	private String reductionpostfee;
	private String birthdaysms;
	private String birthdaymarketingid1;
	private String birthdaymarketingid2;
	private String flag1;
	private String flag2;
	private String flag3;
	private String flag4;
	private String flag5;

	// Constructors

	/** default constructor */
	public MemberLevel() {
	}

	/** minimal constructor */
	public MemberLevel(String memberlevelid) {
		this.memberlevelid = memberlevelid;
	}

	/** full constructor */
	public MemberLevel(String memberlevelid, String memberlevel, Date crdt,
			BigDecimal totalprice, BigDecimal kgradeprice, BigDecimal pointcoefficient,
			String reductionpostfee, String birthdaysms,
			String birthdaymarketingid1, String birthdaymarketingid2,
			String flag1, String flag2, String flag3, String flag4, String flag5) {
		this.memberlevelid = memberlevelid;
		this.memberlevel = memberlevel;
		this.crdt = crdt;
		this.totalprice = totalprice;
		this.kgradeprice = kgradeprice;
		this.pointcoefficient = pointcoefficient;
		this.reductionpostfee = reductionpostfee;
		this.birthdaysms = birthdaysms;
		this.birthdaymarketingid1 = birthdaymarketingid1;
		this.birthdaymarketingid2 = birthdaymarketingid2;
		this.flag1 = flag1;
		this.flag2 = flag2;
		this.flag3 = flag3;
		this.flag4 = flag4;
		this.flag5 = flag5;
	}

	// Property accessors

	@Id
	@Column(name = "MEMBERLEVELID")
	public String getMemberlevelid() {
		return this.memberlevelid;
	}

	public void setMemberlevelid(String memberlevelid) {
		this.memberlevelid = memberlevelid;
	}

	@Column(name = "MEMBERLEVEL", length = 20)
	public String getMemberlevel() {
		return this.memberlevel;
	}

	public void setMemberlevel(String memberlevel) {
		this.memberlevel = memberlevel;
	}

	@Column(name = "CRDT", length = 7)
	public Date getCrdt() {
		return this.crdt;
	}

	public void setCrdt(Date crdt) {
		this.crdt = crdt;
	}

	@Column(name = "TOTALPRICE", precision = 10)
	public BigDecimal getTotalprice() {
		return this.totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	@Column(name = "KGRADEPRICE", precision = 10)
	public BigDecimal getKgradeprice() {
		return this.kgradeprice;
	}

	public void setKgradeprice(BigDecimal kgradeprice) {
		this.kgradeprice = kgradeprice;
	}

	@Column(name = "POINTCOEFFICIENT", precision = 6)
	public BigDecimal getPointcoefficient() {
		return this.pointcoefficient;
	}

	public void setPointcoefficient(BigDecimal pointcoefficient) {
		this.pointcoefficient = pointcoefficient;
	}

	@Column(name = "REDUCTIONPOSTFEE", length = 4)
	public String getReductionpostfee() {
		return this.reductionpostfee;
	}

	public void setReductionpostfee(String reductionpostfee) {
		this.reductionpostfee = reductionpostfee;
	}

	@Column(name = "BIRTHDAYSMS", length = 4)
	public String getBirthdaysms() {
		return this.birthdaysms;
	}

	public void setBirthdaysms(String birthdaysms) {
		this.birthdaysms = birthdaysms;
	}

	@Column(name = "BIRTHDAYMARKETINGID1", length = 4)
	public String getBirthdaymarketingid1() {
		return this.birthdaymarketingid1;
	}

	public void setBirthdaymarketingid1(String birthdaymarketingid1) {
		this.birthdaymarketingid1 = birthdaymarketingid1;
	}

	@Column(name = "BIRTHDAYMARKETINGID2", length = 4)
	public String getBirthdaymarketingid2() {
		return this.birthdaymarketingid2;
	}

	public void setBirthdaymarketingid2(String birthdaymarketingid2) {
		this.birthdaymarketingid2 = birthdaymarketingid2;
	}

	@Column(name = "FLAG1", length = 4)
	public String getFlag1() {
		return this.flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	@Column(name = "FLAG2", length = 4)
	public String getFlag2() {
		return this.flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	@Column(name = "FLAG3", length = 4)
	public String getFlag3() {
		return this.flag3;
	}

	public void setFlag3(String flag3) {
		this.flag3 = flag3;
	}

	@Column(name = "FLAG4", length = 4)
	public String getFlag4() {
		return this.flag4;
	}

	public void setFlag4(String flag4) {
		this.flag4 = flag4;
	}

	@Column(name = "FLAG5", length = 4)
	public String getFlag5() {
		return this.flag5;
	}

	public void setFlag5(String flag5) {
		this.flag5 = flag5;
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
		result = prime * result + ((birthdaymarketingid1 == null) ? 0 : birthdaymarketingid1.hashCode());
		result = prime * result + ((birthdaymarketingid2 == null) ? 0 : birthdaymarketingid2.hashCode());
		result = prime * result + ((birthdaysms == null) ? 0 : birthdaysms.hashCode());
		result = prime * result + ((crdt == null) ? 0 : crdt.hashCode());
		result = prime * result + ((flag1 == null) ? 0 : flag1.hashCode());
		result = prime * result + ((flag2 == null) ? 0 : flag2.hashCode());
		result = prime * result + ((flag3 == null) ? 0 : flag3.hashCode());
		result = prime * result + ((flag4 == null) ? 0 : flag4.hashCode());
		result = prime * result + ((flag5 == null) ? 0 : flag5.hashCode());
		result = prime * result + ((kgradeprice == null) ? 0 : kgradeprice.hashCode());
		result = prime * result + ((memberlevel == null) ? 0 : memberlevel.hashCode());
		result = prime * result + ((memberlevelid == null) ? 0 : memberlevelid.hashCode());
		result = prime * result + ((pointcoefficient == null) ? 0 : pointcoefficient.hashCode());
		result = prime * result + ((reductionpostfee == null) ? 0 : reductionpostfee.hashCode());
		result = prime * result + ((totalprice == null) ? 0 : totalprice.hashCode());
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
		MemberLevel other = (MemberLevel) obj;
		if (birthdaymarketingid1 == null) {
			if (other.birthdaymarketingid1 != null)
				return false;
		} else if (!birthdaymarketingid1.equals(other.birthdaymarketingid1))
			return false;
		if (birthdaymarketingid2 == null) {
			if (other.birthdaymarketingid2 != null)
				return false;
		} else if (!birthdaymarketingid2.equals(other.birthdaymarketingid2))
			return false;
		if (birthdaysms == null) {
			if (other.birthdaysms != null)
				return false;
		} else if (!birthdaysms.equals(other.birthdaysms))
			return false;
		if (crdt == null) {
			if (other.crdt != null)
				return false;
		} else if (!crdt.equals(other.crdt))
			return false;
		if (flag1 == null) {
			if (other.flag1 != null)
				return false;
		} else if (!flag1.equals(other.flag1))
			return false;
		if (flag2 == null) {
			if (other.flag2 != null)
				return false;
		} else if (!flag2.equals(other.flag2))
			return false;
		if (flag3 == null) {
			if (other.flag3 != null)
				return false;
		} else if (!flag3.equals(other.flag3))
			return false;
		if (flag4 == null) {
			if (other.flag4 != null)
				return false;
		} else if (!flag4.equals(other.flag4))
			return false;
		if (flag5 == null) {
			if (other.flag5 != null)
				return false;
		} else if (!flag5.equals(other.flag5))
			return false;
		if (kgradeprice == null) {
			if (other.kgradeprice != null)
				return false;
		} else if (!kgradeprice.equals(other.kgradeprice))
			return false;
		if (memberlevel == null) {
			if (other.memberlevel != null)
				return false;
		} else if (!memberlevel.equals(other.memberlevel))
			return false;
		if (memberlevelid == null) {
			if (other.memberlevelid != null)
				return false;
		} else if (!memberlevelid.equals(other.memberlevelid))
			return false;
		if (pointcoefficient == null) {
			if (other.pointcoefficient != null)
				return false;
		} else if (!pointcoefficient.equals(other.pointcoefficient))
			return false;
		if (reductionpostfee == null) {
			if (other.reductionpostfee != null)
				return false;
		} else if (!reductionpostfee.equals(other.reductionpostfee))
			return false;
		if (totalprice == null) {
			if (other.totalprice != null)
				return false;
		} else if (!totalprice.equals(other.totalprice))
			return false;
		return true;
	}

	
}