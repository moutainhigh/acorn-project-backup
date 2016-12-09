/*
 * @(#)CustomerSqlSource.java 1.0 2013-1-21下午2:09:48
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.model.bi;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * 客户群管理——客户标签</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-21 下午2:09:48
 * 
 */
@Table(name = "IDL_CRM_USR_FLAG_FT",schema="acorn_dm")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class IdlCrmUsrFlag {

	private String contactid;
	private String names;
	private String gender;
	private String id_num;
	private String birthday;
	private String province;
	private String city;
	private String county;
	private String area;
	private String city_lev;
	private Date fst_paid_ordr_date;
	private String fst_paid_prod;
	private String fst_paid_prod_cat;
	private String fst_paid_media_co;
	private String fst_paid_media_prod;
	private Integer fst_paid_ordr_amt;
	private String intst_cat;
	private String mbr_type;
	private String contact_lev;
	private String is_bind_old_custm;
	private Date lst_paid_ordr_date;
	private Date lst_ordr_date;
	private String lst_paid_prod;
	private String lst_paid_prod_cat;
	private String lst_paid_media_co;
	private String lst_paid_media_prod;
	private Integer lst_paid_ordr_amt;
	private Date lst_callout_date;
	private Integer acc_paid_ordr_qty;
	private Integer acc_paid_ordr_amt;
	private Date lst_inb_paid_mobile_date;
	private Date lst_inb_paid_clctn_date;
	private Date lst_inb_paid_dgtl_date;
	private Date lst_inb_paid_sport_date;
	private Date lst_inb_paid_watch_date;
	private Integer acc_2011_paid_mobile_qty;
	private Integer acc_2011_paid_clctn_qty;
	private Integer acc_2011_paid_dgtl_qty;
	private Integer acc_2011_paid_sport_qty;
	private Integer acc_2011_paid_watch_qty;
	private Integer acc_paid_mobile_qty;
	private Integer acc_paid_clctn_qty;
	private Integer acc_paid_dgtl_qty;
	private Integer acc_paid_sport_qty;
	private Integer acc_paid_wathc_qty;
	private Date data_date;
	private String sync_flag;
	private Date import_date;

	/**
	 * @return the contactId
	 */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "assigned")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "CONTACTID")
	public String getContactid() {
		return contactid;
	}

	/**
	 * @param contactid
	 *            the contactid to set
	 */
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}

	/**
	 * @return the names
	 */
	@Column(name = "names")
	public String getNames() {
		return names;
	}

	/**
	 * @param names
	 *            the names to set
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * @return the gender
	 */
	@Column(name = "gender")
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the id_num
	 */
	@Column(name = "id_num")
	public String getId_num() {
		return id_num;
	}

	/**
	 * @param id_num
	 *            the id_num to set
	 */
	public void setId_num(String id_num) {
		this.id_num = id_num;
	}

	/**
	 * @return the birthday
	 */
	@Column(name = "birthday")
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the province
	 */
	@Column(name = "province")
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	@Column(name = "city")
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the county
	 */
	@Column(name = "county")
	public String getCounty() {
		return county;
	}

	/**
	 * @param county
	 *            the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @return the area
	 */
	@Column(name = "area")
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the city_lev
	 */
	@Column(name = "city_lev")
	public String getCity_lev() {
		return city_lev;
	}

	/**
	 * @param city_lev
	 *            the city_lev to set
	 */
	public void setCity_lev(String city_lev) {
		this.city_lev = city_lev;
	}

	/**
	 * @return the fst_paid_ordr_date
	 */
	@Column(name = "fst_paid_ordr_date")
	public Date getFst_paid_ordr_date() {
		return fst_paid_ordr_date;
	}

	/**
	 * @param fst_paid_ordr_date
	 *            the fst_paid_ordr_date to set
	 */
	public void setFst_paid_ordr_date(Date fst_paid_ordr_date) {
		this.fst_paid_ordr_date = fst_paid_ordr_date;
	}

	/**
	 * @return the fst_paid_prod
	 */
	@Column(name = "fst_paid_prod")
	public String getFst_paid_prod() {
		return fst_paid_prod;
	}

	/**
	 * @param fst_paid_prod
	 *            the fst_paid_prod to set
	 */
	public void setFst_paid_prod(String fst_paid_prod) {
		this.fst_paid_prod = fst_paid_prod;
	}

	/**
	 * @return the fst_paid_prod_cat
	 */
	@Column(name = "fst_paid_prod_cat")
	public String getFst_paid_prod_cat() {
		return fst_paid_prod_cat;
	}

	/**
	 * @param fst_paid_prod_cat
	 *            the fst_paid_prod_cat to set
	 */
	public void setFst_paid_prod_cat(String fst_paid_prod_cat) {
		this.fst_paid_prod_cat = fst_paid_prod_cat;
	}

	/**
	 * @return the fst_paid_media_co
	 */
	@Column(name = "fst_paid_media_co")
	public String getFst_paid_media_co() {
		return fst_paid_media_co;
	}

	/**
	 * @param fst_paid_media_co
	 *            the fst_paid_media_co to set
	 */
	public void setFst_paid_media_co(String fst_paid_media_co) {
		this.fst_paid_media_co = fst_paid_media_co;
	}

	/**
	 * @return the fst_paid_media_prod
	 */
	@Column(name = "fst_paid_media_prod")
	public String getFst_paid_media_prod() {
		return fst_paid_media_prod;
	}

	/**
	 * @param fst_paid_media_prod
	 *            the fst_paid_media_prod to set
	 */
	public void setFst_paid_media_prod(String fst_paid_media_prod) {
		this.fst_paid_media_prod = fst_paid_media_prod;
	}

	/**
	 * @return the fst_paid_ordr_amt
	 */
	@Column(name = "fst_paid_ordr_amt")
	public Integer getFst_paid_ordr_amt() {
		return fst_paid_ordr_amt;
	}

	/**
	 * @param fst_paid_ordr_amt
	 *            the fst_paid_ordr_amt to set
	 */
	public void setFst_paid_ordr_amt(Integer fst_paid_ordr_amt) {
		this.fst_paid_ordr_amt = fst_paid_ordr_amt;
	}

	/**
	 * @return the intst_cat
	 */
	@Column(name = "intst_cat")
	public String getIntst_cat() {
		return intst_cat;
	}

	/**
	 * @param intst_cat
	 *            the intst_cat to set
	 */
	public void setIntst_cat(String intst_cat) {
		this.intst_cat = intst_cat;
	}

	/**
	 * @return the mbr_type
	 */
	@Column(name = "mbr_type")
	public String getMbr_type() {
		return mbr_type;
	}

	/**
	 * @param mbr_type
	 *            the mbr_type to set
	 */
	public void setMbr_type(String mbr_type) {
		this.mbr_type = mbr_type;
	}

	/**
	 * @return the contact_lev
	 */
	@Column(name = "contact_lev")
	public String getContact_lev() {
		return contact_lev;
	}

	/**
	 * @param contact_lev
	 *            the contact_lev to set
	 */
	public void setContact_lev(String contact_lev) {
		this.contact_lev = contact_lev;
	}

	/**
	 * @return the is_bind_old_custm
	 */
	@Column(name = "is_bind_old_custm")
	public String getIs_bind_old_custm() {
		return is_bind_old_custm;
	}

	/**
	 * @param is_bind_old_custm
	 *            the is_bind_old_custm to set
	 */
	public void setIs_bind_old_custm(String is_bind_old_custm) {
		this.is_bind_old_custm = is_bind_old_custm;
	}

	/**
	 * @return the lst_paid_ordr_date
	 */
	@Column(name = "lst_paid_ordr_date")
	public Date getLst_paid_ordr_date() {
		return lst_paid_ordr_date;
	}

	/**
	 * @param lst_paid_ordr_date
	 *            the lst_paid_ordr_date to set
	 */
	public void setLst_paid_ordr_date(Date lst_paid_ordr_date) {
		this.lst_paid_ordr_date = lst_paid_ordr_date;
	}

	/**
	 * @return the lst_ordr_date
	 */
	@Column(name = "lst_ordr_date")
	public Date getLst_ordr_date() {
		return lst_ordr_date;
	}

	/**
	 * @param lst_ordr_date
	 *            the lst_ordr_date to set
	 */
	public void setLst_ordr_date(Date lst_ordr_date) {
		this.lst_ordr_date = lst_ordr_date;
	}

	/**
	 * @return the lst_paid_prod
	 */
	@Column(name = "lst_paid_prod")
	public String getLst_paid_prod() {
		return lst_paid_prod;
	}

	/**
	 * @param lst_paid_prod
	 *            the lst_paid_prod to set
	 */
	public void setLst_paid_prod(String lst_paid_prod) {
		this.lst_paid_prod = lst_paid_prod;
	}

	/**
	 * @return the lst_paid_prod_cat
	 */
	@Column(name = "lst_paid_prod_cat")
	public String getLst_paid_prod_cat() {
		return lst_paid_prod_cat;
	}

	/**
	 * @param lst_paid_prod_cat
	 *            the lst_paid_prod_cat to set
	 */
	public void setLst_paid_prod_cat(String lst_paid_prod_cat) {
		this.lst_paid_prod_cat = lst_paid_prod_cat;
	}

	/**
	 * @return the lst_paid_media_co
	 */
	@Column(name = "lst_paid_media_co")
	public String getLst_paid_media_co() {
		return lst_paid_media_co;
	}

	/**
	 * @param lst_paid_media_co
	 *            the lst_paid_media_co to set
	 */
	public void setLst_paid_media_co(String lst_paid_media_co) {
		this.lst_paid_media_co = lst_paid_media_co;
	}

	/**
	 * @return the lst_paid_media_prod
	 */
	@Column(name = "lst_paid_media_prod")
	public String getLst_paid_media_prod() {
		return lst_paid_media_prod;
	}

	/**
	 * @param lst_paid_media_prod
	 *            the lst_paid_media_prod to set
	 */
	public void setLst_paid_media_prod(String lst_paid_media_prod) {
		this.lst_paid_media_prod = lst_paid_media_prod;
	}

	/**
	 * @return the lst_paid_ordr_amt
	 */
	@Column(name = "lst_paid_ordr_amt")
	public Integer getLst_paid_ordr_amt() {
		return lst_paid_ordr_amt;
	}

	/**
	 * @param lst_paid_ordr_amt
	 *            the lst_paid_ordr_amt to set
	 */
	public void setLst_paid_ordr_amt(Integer lst_paid_ordr_amt) {
		this.lst_paid_ordr_amt = lst_paid_ordr_amt;
	}

	/**
	 * @return the lst_callout_date
	 */
	@Column(name = "lst_callout_date")
	public Date getLst_callout_date() {
		return lst_callout_date;
	}

	/**
	 * @param lst_callout_date
	 *            the lst_callout_date to set
	 */
	public void setLst_callout_date(Date lst_callout_date) {
		this.lst_callout_date = lst_callout_date;
	}

	/**
	 * @return the acc_paid_ordr_qty
	 */
	@Column(name = "acc_paid_ordr_qty")
	public Integer getAcc_paid_ordr_qty() {
		return acc_paid_ordr_qty;
	}

	/**
	 * @param acc_paid_ordr_qty
	 *            the acc_paid_ordr_qty to set
	 */
	public void setAcc_paid_ordr_qty(Integer acc_paid_ordr_qty) {
		this.acc_paid_ordr_qty = acc_paid_ordr_qty;
	}

	/**
	 * @return the acc_paid_ordr_amt
	 */
	@Column(name = "acc_paid_ordr_amt")
	public Integer getAcc_paid_ordr_amt() {
		return acc_paid_ordr_amt;
	}

	/**
	 * @param acc_paid_ordr_amt
	 *            the acc_paid_ordr_amt to set
	 */
	public void setAcc_paid_ordr_amt(Integer acc_paid_ordr_amt) {
		this.acc_paid_ordr_amt = acc_paid_ordr_amt;
	}

	/**
	 * @return the lst_inb_paid_mobile_date
	 */
	@Column(name = "lst_inb_paid_mobile_date")
	public Date getLst_inb_paid_mobile_date() {
		return lst_inb_paid_mobile_date;
	}

	/**
	 * @param lst_inb_paid_mobile_date
	 *            the lst_inb_paid_mobile_date to set
	 */
	public void setLst_inb_paid_mobile_date(Date lst_inb_paid_mobile_date) {
		this.lst_inb_paid_mobile_date = lst_inb_paid_mobile_date;
	}

	/**
	 * @return the lst_inb_paid_clctn_date
	 */
	@Column(name = "lst_inb_paid_clctn_date")
	public Date getLst_inb_paid_clctn_date() {
		return lst_inb_paid_clctn_date;
	}

	/**
	 * @param lst_inb_paid_clctn_date
	 *            the lst_inb_paid_clctn_date to set
	 */
	public void setLst_inb_paid_clctn_date(Date lst_inb_paid_clctn_date) {
		this.lst_inb_paid_clctn_date = lst_inb_paid_clctn_date;
	}

	/**
	 * @return the lst_inb_paid_dgtl_date
	 */
	@Column(name = "lst_inb_paid_dgtl_date")
	public Date getLst_inb_paid_dgtl_date() {
		return lst_inb_paid_dgtl_date;
	}

	/**
	 * @param lst_inb_paid_dgtl_date
	 *            the lst_inb_paid_dgtl_date to set
	 */
	public void setLst_inb_paid_dgtl_date(Date lst_inb_paid_dgtl_date) {
		this.lst_inb_paid_dgtl_date = lst_inb_paid_dgtl_date;
	}

	/**
	 * @return the lst_inb_paid_sport_date
	 */
	@Column(name = "lst_inb_paid_sport_date")
	public Date getLst_inb_paid_sport_date() {
		return lst_inb_paid_sport_date;
	}

	/**
	 * @param lst_inb_paid_sport_date
	 *            the lst_inb_paid_sport_date to set
	 */
	public void setLst_inb_paid_sport_date(Date lst_inb_paid_sport_date) {
		this.lst_inb_paid_sport_date = lst_inb_paid_sport_date;
	}

	/**
	 * @return the lst_inb_paid_watch_date
	 */
	@Column(name = "lst_inb_paid_watch_date")
	public Date getLst_inb_paid_watch_date() {
		return lst_inb_paid_watch_date;
	}

	/**
	 * @param lst_inb_paid_watch_date
	 *            the lst_inb_paid_watch_date to set
	 */
	public void setLst_inb_paid_watch_date(Date lst_inb_paid_watch_date) {
		this.lst_inb_paid_watch_date = lst_inb_paid_watch_date;
	}

	/**
	 * @return the acc_2011_paid_mobile_qty
	 */
	@Column(name = "acc_2011_paid_mobile_qty")
	public Integer getAcc_2011_paid_mobile_qty() {
		return acc_2011_paid_mobile_qty;
	}

	/**
	 * @param acc_2011_paid_mobile_qty
	 *            the acc_2011_paid_mobile_qty to set
	 */
	public void setAcc_2011_paid_mobile_qty(Integer acc_2011_paid_mobile_qty) {
		this.acc_2011_paid_mobile_qty = acc_2011_paid_mobile_qty;
	}

	/**
	 * @return the acc_2011_paid_clctn_qty
	 */
	@Column(name = "acc_2011_paid_clctn_qty")
	public Integer getAcc_2011_paid_clctn_qty() {
		return acc_2011_paid_clctn_qty;
	}

	/**
	 * @param acc_2011_paid_clctn_qty
	 *            the acc_2011_paid_clctn_qty to set
	 */
	public void setAcc_2011_paid_clctn_qty(Integer acc_2011_paid_clctn_qty) {
		this.acc_2011_paid_clctn_qty = acc_2011_paid_clctn_qty;
	}

	/**
	 * @return the acc_2011_paid_dgtl_qty
	 */
	@Column(name = "acc_2011_paid_dgtl_qty")
	public Integer getAcc_2011_paid_dgtl_qty() {
		return acc_2011_paid_dgtl_qty;
	}

	/**
	 * @param acc_2011_paid_dgtl_qty
	 *            the acc_2011_paid_dgtl_qty to set
	 */
	public void setAcc_2011_paid_dgtl_qty(Integer acc_2011_paid_dgtl_qty) {
		this.acc_2011_paid_dgtl_qty = acc_2011_paid_dgtl_qty;
	}

	/**
	 * @return the acc_2011_paid_sport_qty
	 */
	@Column(name = "acc_2011_paid_sport_qty")
	public Integer getAcc_2011_paid_sport_qty() {
		return acc_2011_paid_sport_qty;
	}

	/**
	 * @param acc_2011_paid_sport_qty
	 *            the acc_2011_paid_sport_qty to set
	 */
	public void setAcc_2011_paid_sport_qty(Integer acc_2011_paid_sport_qty) {
		this.acc_2011_paid_sport_qty = acc_2011_paid_sport_qty;
	}

	/**
	 * @return the acc_2011_paid_watch_qty
	 */
	@Column(name = "acc_2011_paid_watch_qty")
	public Integer getAcc_2011_paid_watch_qty() {
		return acc_2011_paid_watch_qty;
	}

	/**
	 * @param acc_2011_paid_watch_qty
	 *            the acc_2011_paid_watch_qty to set
	 */
	public void setAcc_2011_paid_watch_qty(Integer acc_2011_paid_watch_qty) {
		this.acc_2011_paid_watch_qty = acc_2011_paid_watch_qty;
	}

	/**
	 * @return the acc_paid_mobile_qty
	 */
	@Column(name = "acc_paid_mobile_qty")
	public Integer getAcc_paid_mobile_qty() {
		return acc_paid_mobile_qty;
	}

	/**
	 * @param acc_paid_mobile_qty
	 *            the acc_paid_mobile_qty to set
	 */
	public void setAcc_paid_mobile_qty(Integer acc_paid_mobile_qty) {
		this.acc_paid_mobile_qty = acc_paid_mobile_qty;
	}

	/**
	 * @return the acc_paid_clctn_qty
	 */
	@Column(name = "acc_paid_clctn_qty")
	public Integer getAcc_paid_clctn_qty() {
		return acc_paid_clctn_qty;
	}

	/**
	 * @param acc_paid_clctn_qty
	 *            the acc_paid_clctn_qty to set
	 */
	public void setAcc_paid_clctn_qty(Integer acc_paid_clctn_qty) {
		this.acc_paid_clctn_qty = acc_paid_clctn_qty;
	}

	/**
	 * @return the acc_paid_dgtl_qty
	 */
	@Column(name = "acc_paid_dgtl_qty")
	public Integer getAcc_paid_dgtl_qty() {
		return acc_paid_dgtl_qty;
	}

	/**
	 * @param acc_paid_dgtl_qty
	 *            the acc_paid_dgtl_qty to set
	 */
	public void setAcc_paid_dgtl_qty(Integer acc_paid_dgtl_qty) {
		this.acc_paid_dgtl_qty = acc_paid_dgtl_qty;
	}

	/**
	 * @return the acc_paid_sport_qty
	 */
	@Column(name = "acc_paid_sport_qty")
	public Integer getAcc_paid_sport_qty() {
		return acc_paid_sport_qty;
	}

	/**
	 * @param acc_paid_sport_qty
	 *            the acc_paid_sport_qty to set
	 */
	public void setAcc_paid_sport_qty(Integer acc_paid_sport_qty) {
		this.acc_paid_sport_qty = acc_paid_sport_qty;
	}

	/**
	 * @return the acc_paid_wathc_qty
	 */
	@Column(name = "acc_paid_wathc_qty")
	public Integer getAcc_paid_wathc_qty() {
		return acc_paid_wathc_qty;
	}

	/**
	 * @param acc_paid_wathc_qty
	 *            the acc_paid_wathc_qty to set
	 */
	public void setAcc_paid_wathc_qty(Integer acc_paid_wathc_qty) {
		this.acc_paid_wathc_qty = acc_paid_wathc_qty;
	}

	/**
	 * @return the data_date
	 */
	@Column(name = "data_date")
	public Date getData_date() {
		return data_date;
	}

	/**
	 * @param data_date
	 *            the data_date to set
	 */
	public void setData_date(Date data_date) {
		this.data_date = data_date;
	}

	/**
	 * @return the sync_flag
	 */
	@Column(name = "sync_flag")
	public String getSync_flag() {
		return sync_flag;
	}

	/**
	 * @param sync_flag
	 *            the sync_flag to set
	 */
	public void setSync_flag(String sync_flag) {
		this.sync_flag = sync_flag;
	}

	/**
	 * @return the import_date
	 */
	@Column(name = "import_date")
	public Date getImport_date() {
		return import_date;
	}

	/**
	 * @param import_date
	 *            the import_date to set
	 */
	public void setImport_date(Date import_date) {
		this.import_date = import_date;
	}

	/* (非 Javadoc)
	* <p>Title: hashCode</p>
	* <p>Description: </p>
	* @return
	* @see java.lang.Object#hashCode()
	*/ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((acc_2011_paid_clctn_qty == null) ? 0
						: acc_2011_paid_clctn_qty.hashCode());
		result = prime
				* result
				+ ((acc_2011_paid_dgtl_qty == null) ? 0
						: acc_2011_paid_dgtl_qty.hashCode());
		result = prime
				* result
				+ ((acc_2011_paid_mobile_qty == null) ? 0
						: acc_2011_paid_mobile_qty.hashCode());
		result = prime
				* result
				+ ((acc_2011_paid_sport_qty == null) ? 0
						: acc_2011_paid_sport_qty.hashCode());
		result = prime
				* result
				+ ((acc_2011_paid_watch_qty == null) ? 0
						: acc_2011_paid_watch_qty.hashCode());
		result = prime
				* result
				+ ((acc_paid_clctn_qty == null) ? 0 : acc_paid_clctn_qty
						.hashCode());
		result = prime
				* result
				+ ((acc_paid_dgtl_qty == null) ? 0 : acc_paid_dgtl_qty
						.hashCode());
		result = prime
				* result
				+ ((acc_paid_mobile_qty == null) ? 0 : acc_paid_mobile_qty
						.hashCode());
		result = prime
				* result
				+ ((acc_paid_ordr_amt == null) ? 0 : acc_paid_ordr_amt
						.hashCode());
		result = prime
				* result
				+ ((acc_paid_ordr_qty == null) ? 0 : acc_paid_ordr_qty
						.hashCode());
		result = prime
				* result
				+ ((acc_paid_sport_qty == null) ? 0 : acc_paid_sport_qty
						.hashCode());
		result = prime
				* result
				+ ((acc_paid_wathc_qty == null) ? 0 : acc_paid_wathc_qty
						.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((city_lev == null) ? 0 : city_lev.hashCode());
		result = prime * result
				+ ((contact_lev == null) ? 0 : contact_lev.hashCode());
		result = prime * result
				+ ((contactid == null) ? 0 : contactid.hashCode());
		result = prime * result + ((county == null) ? 0 : county.hashCode());
		result = prime * result
				+ ((data_date == null) ? 0 : data_date.hashCode());
		result = prime
				* result
				+ ((fst_paid_media_co == null) ? 0 : fst_paid_media_co
						.hashCode());
		result = prime
				* result
				+ ((fst_paid_media_prod == null) ? 0 : fst_paid_media_prod
						.hashCode());
		result = prime
				* result
				+ ((fst_paid_ordr_amt == null) ? 0 : fst_paid_ordr_amt
						.hashCode());
		result = prime
				* result
				+ ((fst_paid_ordr_date == null) ? 0 : fst_paid_ordr_date
						.hashCode());
		result = prime * result
				+ ((fst_paid_prod == null) ? 0 : fst_paid_prod.hashCode());
		result = prime
				* result
				+ ((fst_paid_prod_cat == null) ? 0 : fst_paid_prod_cat
						.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id_num == null) ? 0 : id_num.hashCode());
		result = prime * result
				+ ((import_date == null) ? 0 : import_date.hashCode());
		result = prime * result
				+ ((intst_cat == null) ? 0 : intst_cat.hashCode());
		result = prime
				* result
				+ ((is_bind_old_custm == null) ? 0 : is_bind_old_custm
						.hashCode());
		result = prime
				* result
				+ ((lst_callout_date == null) ? 0 : lst_callout_date.hashCode());
		result = prime
				* result
				+ ((lst_inb_paid_clctn_date == null) ? 0
						: lst_inb_paid_clctn_date.hashCode());
		result = prime
				* result
				+ ((lst_inb_paid_dgtl_date == null) ? 0
						: lst_inb_paid_dgtl_date.hashCode());
		result = prime
				* result
				+ ((lst_inb_paid_mobile_date == null) ? 0
						: lst_inb_paid_mobile_date.hashCode());
		result = prime
				* result
				+ ((lst_inb_paid_sport_date == null) ? 0
						: lst_inb_paid_sport_date.hashCode());
		result = prime
				* result
				+ ((lst_inb_paid_watch_date == null) ? 0
						: lst_inb_paid_watch_date.hashCode());
		result = prime * result
				+ ((lst_ordr_date == null) ? 0 : lst_ordr_date.hashCode());
		result = prime
				* result
				+ ((lst_paid_media_co == null) ? 0 : lst_paid_media_co
						.hashCode());
		result = prime
				* result
				+ ((lst_paid_media_prod == null) ? 0 : lst_paid_media_prod
						.hashCode());
		result = prime
				* result
				+ ((lst_paid_ordr_amt == null) ? 0 : lst_paid_ordr_amt
						.hashCode());
		result = prime
				* result
				+ ((lst_paid_ordr_date == null) ? 0 : lst_paid_ordr_date
						.hashCode());
		result = prime * result
				+ ((lst_paid_prod == null) ? 0 : lst_paid_prod.hashCode());
		result = prime
				* result
				+ ((lst_paid_prod_cat == null) ? 0 : lst_paid_prod_cat
						.hashCode());
		result = prime * result
				+ ((mbr_type == null) ? 0 : mbr_type.hashCode());
		result = prime * result + ((names == null) ? 0 : names.hashCode());
		result = prime * result
				+ ((province == null) ? 0 : province.hashCode());
		result = prime * result
				+ ((sync_flag == null) ? 0 : sync_flag.hashCode());
		return result;
	}

	/* (非 Javadoc)
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
		IdlCrmUsrFlag other = (IdlCrmUsrFlag) obj;
		if (acc_2011_paid_clctn_qty == null) {
			if (other.acc_2011_paid_clctn_qty != null)
				return false;
		} else if (!acc_2011_paid_clctn_qty
				.equals(other.acc_2011_paid_clctn_qty))
			return false;
		if (acc_2011_paid_dgtl_qty == null) {
			if (other.acc_2011_paid_dgtl_qty != null)
				return false;
		} else if (!acc_2011_paid_dgtl_qty.equals(other.acc_2011_paid_dgtl_qty))
			return false;
		if (acc_2011_paid_mobile_qty == null) {
			if (other.acc_2011_paid_mobile_qty != null)
				return false;
		} else if (!acc_2011_paid_mobile_qty
				.equals(other.acc_2011_paid_mobile_qty))
			return false;
		if (acc_2011_paid_sport_qty == null) {
			if (other.acc_2011_paid_sport_qty != null)
				return false;
		} else if (!acc_2011_paid_sport_qty
				.equals(other.acc_2011_paid_sport_qty))
			return false;
		if (acc_2011_paid_watch_qty == null) {
			if (other.acc_2011_paid_watch_qty != null)
				return false;
		} else if (!acc_2011_paid_watch_qty
				.equals(other.acc_2011_paid_watch_qty))
			return false;
		if (acc_paid_clctn_qty == null) {
			if (other.acc_paid_clctn_qty != null)
				return false;
		} else if (!acc_paid_clctn_qty.equals(other.acc_paid_clctn_qty))
			return false;
		if (acc_paid_dgtl_qty == null) {
			if (other.acc_paid_dgtl_qty != null)
				return false;
		} else if (!acc_paid_dgtl_qty.equals(other.acc_paid_dgtl_qty))
			return false;
		if (acc_paid_mobile_qty == null) {
			if (other.acc_paid_mobile_qty != null)
				return false;
		} else if (!acc_paid_mobile_qty.equals(other.acc_paid_mobile_qty))
			return false;
		if (acc_paid_ordr_amt == null) {
			if (other.acc_paid_ordr_amt != null)
				return false;
		} else if (!acc_paid_ordr_amt.equals(other.acc_paid_ordr_amt))
			return false;
		if (acc_paid_ordr_qty == null) {
			if (other.acc_paid_ordr_qty != null)
				return false;
		} else if (!acc_paid_ordr_qty.equals(other.acc_paid_ordr_qty))
			return false;
		if (acc_paid_sport_qty == null) {
			if (other.acc_paid_sport_qty != null)
				return false;
		} else if (!acc_paid_sport_qty.equals(other.acc_paid_sport_qty))
			return false;
		if (acc_paid_wathc_qty == null) {
			if (other.acc_paid_wathc_qty != null)
				return false;
		} else if (!acc_paid_wathc_qty.equals(other.acc_paid_wathc_qty))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (city_lev == null) {
			if (other.city_lev != null)
				return false;
		} else if (!city_lev.equals(other.city_lev))
			return false;
		if (contact_lev == null) {
			if (other.contact_lev != null)
				return false;
		} else if (!contact_lev.equals(other.contact_lev))
			return false;
		if (contactid == null) {
			if (other.contactid != null)
				return false;
		} else if (!contactid.equals(other.contactid))
			return false;
		if (county == null) {
			if (other.county != null)
				return false;
		} else if (!county.equals(other.county))
			return false;
		if (data_date == null) {
			if (other.data_date != null)
				return false;
		} else if (!data_date.equals(other.data_date))
			return false;
		if (fst_paid_media_co == null) {
			if (other.fst_paid_media_co != null)
				return false;
		} else if (!fst_paid_media_co.equals(other.fst_paid_media_co))
			return false;
		if (fst_paid_media_prod == null) {
			if (other.fst_paid_media_prod != null)
				return false;
		} else if (!fst_paid_media_prod.equals(other.fst_paid_media_prod))
			return false;
		if (fst_paid_ordr_amt == null) {
			if (other.fst_paid_ordr_amt != null)
				return false;
		} else if (!fst_paid_ordr_amt.equals(other.fst_paid_ordr_amt))
			return false;
		if (fst_paid_ordr_date == null) {
			if (other.fst_paid_ordr_date != null)
				return false;
		} else if (!fst_paid_ordr_date.equals(other.fst_paid_ordr_date))
			return false;
		if (fst_paid_prod == null) {
			if (other.fst_paid_prod != null)
				return false;
		} else if (!fst_paid_prod.equals(other.fst_paid_prod))
			return false;
		if (fst_paid_prod_cat == null) {
			if (other.fst_paid_prod_cat != null)
				return false;
		} else if (!fst_paid_prod_cat.equals(other.fst_paid_prod_cat))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id_num == null) {
			if (other.id_num != null)
				return false;
		} else if (!id_num.equals(other.id_num))
			return false;
		if (import_date == null) {
			if (other.import_date != null)
				return false;
		} else if (!import_date.equals(other.import_date))
			return false;
		if (intst_cat == null) {
			if (other.intst_cat != null)
				return false;
		} else if (!intst_cat.equals(other.intst_cat))
			return false;
		if (is_bind_old_custm == null) {
			if (other.is_bind_old_custm != null)
				return false;
		} else if (!is_bind_old_custm.equals(other.is_bind_old_custm))
			return false;
		if (lst_callout_date == null) {
			if (other.lst_callout_date != null)
				return false;
		} else if (!lst_callout_date.equals(other.lst_callout_date))
			return false;
		if (lst_inb_paid_clctn_date == null) {
			if (other.lst_inb_paid_clctn_date != null)
				return false;
		} else if (!lst_inb_paid_clctn_date
				.equals(other.lst_inb_paid_clctn_date))
			return false;
		if (lst_inb_paid_dgtl_date == null) {
			if (other.lst_inb_paid_dgtl_date != null)
				return false;
		} else if (!lst_inb_paid_dgtl_date.equals(other.lst_inb_paid_dgtl_date))
			return false;
		if (lst_inb_paid_mobile_date == null) {
			if (other.lst_inb_paid_mobile_date != null)
				return false;
		} else if (!lst_inb_paid_mobile_date
				.equals(other.lst_inb_paid_mobile_date))
			return false;
		if (lst_inb_paid_sport_date == null) {
			if (other.lst_inb_paid_sport_date != null)
				return false;
		} else if (!lst_inb_paid_sport_date
				.equals(other.lst_inb_paid_sport_date))
			return false;
		if (lst_inb_paid_watch_date == null) {
			if (other.lst_inb_paid_watch_date != null)
				return false;
		} else if (!lst_inb_paid_watch_date
				.equals(other.lst_inb_paid_watch_date))
			return false;
		if (lst_ordr_date == null) {
			if (other.lst_ordr_date != null)
				return false;
		} else if (!lst_ordr_date.equals(other.lst_ordr_date))
			return false;
		if (lst_paid_media_co == null) {
			if (other.lst_paid_media_co != null)
				return false;
		} else if (!lst_paid_media_co.equals(other.lst_paid_media_co))
			return false;
		if (lst_paid_media_prod == null) {
			if (other.lst_paid_media_prod != null)
				return false;
		} else if (!lst_paid_media_prod.equals(other.lst_paid_media_prod))
			return false;
		if (lst_paid_ordr_amt == null) {
			if (other.lst_paid_ordr_amt != null)
				return false;
		} else if (!lst_paid_ordr_amt.equals(other.lst_paid_ordr_amt))
			return false;
		if (lst_paid_ordr_date == null) {
			if (other.lst_paid_ordr_date != null)
				return false;
		} else if (!lst_paid_ordr_date.equals(other.lst_paid_ordr_date))
			return false;
		if (lst_paid_prod == null) {
			if (other.lst_paid_prod != null)
				return false;
		} else if (!lst_paid_prod.equals(other.lst_paid_prod))
			return false;
		if (lst_paid_prod_cat == null) {
			if (other.lst_paid_prod_cat != null)
				return false;
		} else if (!lst_paid_prod_cat.equals(other.lst_paid_prod_cat))
			return false;
		if (mbr_type == null) {
			if (other.mbr_type != null)
				return false;
		} else if (!mbr_type.equals(other.mbr_type))
			return false;
		if (names == null) {
			if (other.names != null)
				return false;
		} else if (!names.equals(other.names))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (sync_flag == null) {
			if (other.sync_flag != null)
				return false;
		} else if (!sync_flag.equals(other.sync_flag))
			return false;
		return true;
	}

	
	
}
