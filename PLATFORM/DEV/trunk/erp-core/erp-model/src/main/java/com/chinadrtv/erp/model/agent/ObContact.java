package com.chinadrtv.erp.model.agent;

import javax.persistence.*;
import java.util.*;

/**
 * 
 * 
 * @author zhaizhanyi
 * @date 2013-2-27 下午2:07:11 橡果国际-系统集成部 Copyright (c) 2012-2013 ACORN, Inc. All
 *       rights reserved.
 */
@Entity
@Table(name = "OB_CONTACT", schema = "IAGENT")
public class ObContact implements java.io.Serializable {

	private String contactid;
	private String queueid;
	private String batchid;
	private String prequeueid;
	private String datasrcid;
	private Date createtime;
	private Date updatetime;
	private String assignagent;
	private Date assigntime;
	private String resultid;
	private String dialed;
	private Integer curqueuedialcnt;
	private Date startdate;
	private Date enddate;
	private String pd_customerid;
	private String pd_curstatus;
	private Integer pd_dialcnt;
	private String pd_calllist;
	private String pd_ccc;
	private String pd_agentid;
	private String pd_phnnum;
	private String pd_startdt;
	private String pd_connectdt;
	private String pd_dropdt;
	private String pd_releasedt;
	private String pd_caseid;
	private String pd_jobcaseid;
	private Integer pd_phntype;
	private String pd_dt2d;
	private String pd_device;
	private String pd_recalltime;
	private String pd_campaignid;
	private String pd_sort;
	private Integer pd_prio;
	private String pd_dialnum;
	private String pd_channel;
	private String pd_login_caseid;
	private String pd_jobid;
	private String pd_followagent;
	private String pd_dialmode;
	private String pd_updatetime;
	private String pd_updatedt;
	private String pd_talktime;
	private String pd_acwtime;
	private String pd_referenceid;
	private String pd_other01;
	private String pd_other02;
	private String pd_other03;
	private String pd_other04;
	private String pd_other05;
	private String pd_other06;
	private String pd_other07;
	private String pd_other08;
	private String pd_other09;
	private String pd_other10;
	private String contactinfo;
	private String statisinfo;
	private String status;
	private Long priority;
	private Long campaignId;
	/**
	 * @return the pd_customerid
	 */
	@Id
	@Column(name = "pd_customerid")
	public String getPd_customerid() {
		return pd_customerid;
	}
	/**
	 * @param pd_customerid the pd_customerid to set
	 */
	public void setPd_customerid(String pd_customerid) {
		this.pd_customerid = pd_customerid;
	}
	
	/**
	 * @return the contactid
	 */
	@Column(name = "contactid")
	public String getContactid() {
		return contactid;
	}
	/**
	 * @param contactid the contactid to set
	 */
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}
	/**
	 * @return the queueid
	 */
	@Column(name = "queueid")
	public String getQueueid() {
		return queueid;
	}
	/**
	 * @param queueid the queueid to set
	 */
	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}
	/**
	 * @return the batchid
	 */
	@Column(name = "batchid")
	public String getBatchid() {
		return batchid;
	}
	/**
	 * @param batchid the batchid to set
	 */
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
	/**
	 * @return the prequeueid
	 */
	@Column(name = "prequeueid")
	public String getPrequeueid() {
		return prequeueid;
	}
	/**
	 * @param prequeueid the prequeueid to set
	 */
	public void setPrequeueid(String prequeueid) {
		this.prequeueid = prequeueid;
	}
	/**
	 * @return the datasrcid
	 */
	@Column(name = "datasrcid")
	public String getDatasrcid() {
		return datasrcid;
	}
	/**
	 * @param datasrcid the datasrcid to set
	 */
	public void setDatasrcid(String datasrcid) {
		this.datasrcid = datasrcid;
	}
	/**
	 * @return the createtime
	 */
	@Column(name = "createtime")
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * @return the updatetime
	 */
	@Column(name = "updatetime")
	public Date getUpdatetime() {
		return updatetime;
	}
	/**
	 * @param updatetime the updatetime to set
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	/**
	 * @return the assignagent
	 */
	@Column(name = "assignagent")
	public String getAssignagent() {
		return assignagent;
	}
	/**
	 * @param assignagent the assignagent to set
	 */
	public void setAssignagent(String assignagent) {
		this.assignagent = assignagent;
	}
	/**
	 * @return the assigntime
	 */
	@Column(name = "assigntime")
	public Date getAssigntime() {
		return assigntime;
	}
	/**
	 * @param assigntime the assigntime to set
	 */
	public void setAssigntime(Date assigntime) {
		this.assigntime = assigntime;
	}
	/**
	 * @return the resultid
	 */
	@Column(name = "resultid")
	public String getResultid() {
		return resultid;
	}
	/**
	 * @param resultid the resultid to set
	 */
	public void setResultid(String resultid) {
		this.resultid = resultid;
	}
	/**
	 * @return the dialed
	 */
	@Column(name = "dialed")
	public String getDialed() {
		return dialed;
	}
	/**
	 * @param dialed the dialed to set
	 */
	public void setDialed(String dialed) {
		this.dialed = dialed;
	}
	/**
	 * @return the curqueuedialcnt
	 */
	@Column(name = "curqueuedialcnt")
	public Integer getCurqueuedialcnt() {
		return curqueuedialcnt;
	}
	/**
	 * @param curqueuedialcnt the curqueuedialcnt to set
	 */
	public void setCurqueuedialcnt(Integer curqueuedialcnt) {
		this.curqueuedialcnt = curqueuedialcnt;
	}
	/**
	 * @return the startdate
	 */
	@Column(name = "startdate")
	public Date getStartdate() {
		return startdate;
	}
	/**
	 * @param startdate the startdate to set
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	/**
	 * @return the enddate
	 */
	@Column(name = "enddate")
	public Date getEnddate() {
		return enddate;
	}
	/**
	 * @param enddate the enddate to set
	 */
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	/**
	 * @return the pd_curstatus
	 */
	@Column(name = "pd_curstatus")
	public String getPd_curstatus() {
		return pd_curstatus;
	}
	/**
	 * @param pd_curstatus the pd_curstatus to set
	 */
	public void setPd_curstatus(String pd_curstatus) {
		this.pd_curstatus = pd_curstatus;
	}
	/**
	 * @return the pd_dialcnt
	 */
	@Column(name = "pd_dialcnt")
	public Integer getPd_dialcnt() {
		return pd_dialcnt;
	}
	/**
	 * @param pd_dialcnt the pd_dialcnt to set
	 */
	public void setPd_dialcnt(Integer pd_dialcnt) {
		this.pd_dialcnt = pd_dialcnt;
	}
	/**
	 * @return the pd_calllist
	 */
	@Column(name = "pd_calllist")
	public String getPd_calllist() {
		return pd_calllist;
	}
	/**
	 * @param pd_calllist the pd_calllist to set
	 */
	public void setPd_calllist(String pd_calllist) {
		this.pd_calllist = pd_calllist;
	}
	/**
	 * @return the pd_ccc
	 */
	@Column(name = "pd_ccc")
	public String getPd_ccc() {
		return pd_ccc;
	}
	/**
	 * @param pd_ccc the pd_ccc to set
	 */
	public void setPd_ccc(String pd_ccc) {
		this.pd_ccc = pd_ccc;
	}
	/**
	 * @return the pd_agentid
	 */
	@Column(name = "pd_agentid")
	public String getPd_agentid() {
		return pd_agentid;
	}
	/**
	 * @param pd_agentid the pd_agentid to set
	 */
	public void setPd_agentid(String pd_agentid) {
		this.pd_agentid = pd_agentid;
	}
	/**
	 * @return the pd_phnnum
	 */
	@Column(name = "pd_phnnum")
	public String getPd_phnnum() {
		return pd_phnnum;
	}
	/**
	 * @param pd_phnnum the pd_phnnum to set
	 */
	public void setPd_phnnum(String pd_phnnum) {
		this.pd_phnnum = pd_phnnum;
	}
	/**
	 * @return the pd_startdt
	 */
	@Column(name = "pd_startdt")
	public String getPd_startdt() {
		return pd_startdt;
	}
	/**
	 * @param pd_startdt the pd_startdt to set
	 */
	public void setPd_startdt(String pd_startdt) {
		this.pd_startdt = pd_startdt;
	}
	/**
	 * @return the pd_connectdt
	 */
	@Column(name = "pd_connectdt")
	public String getPd_connectdt() {
		return pd_connectdt;
	}
	/**
	 * @param pd_connectdt the pd_connectdt to set
	 */
	public void setPd_connectdt(String pd_connectdt) {
		this.pd_connectdt = pd_connectdt;
	}
	/**
	 * @return the pd_dropdt
	 */
	@Column(name = "pd_dropdt")
	public String getPd_dropdt() {
		return pd_dropdt;
	}
	/**
	 * @param pd_dropdt the pd_dropdt to set
	 */
	public void setPd_dropdt(String pd_dropdt) {
		this.pd_dropdt = pd_dropdt;
	}
	/**
	 * @return the pd_releasedt
	 */
	@Column(name = "pd_releasedt")
	public String getPd_releasedt() {
		return pd_releasedt;
	}
	/**
	 * @param pd_releasedt the pd_releasedt to set
	 */
	public void setPd_releasedt(String pd_releasedt) {
		this.pd_releasedt = pd_releasedt;
	}
	/**
	 * @return the pd_caseid
	 */
	@Column(name = "pd_caseid")
	public String getPd_caseid() {
		return pd_caseid;
	}
	/**
	 * @param pd_caseid the pd_caseid to set
	 */
	public void setPd_caseid(String pd_caseid) {
		this.pd_caseid = pd_caseid;
	}
	/**
	 * @return the pd_jobcaseid
	 */
	@Column(name = "pd_jobcaseid")
	public String getPd_jobcaseid() {
		return pd_jobcaseid;
	}
	/**
	 * @param pd_jobcaseid the pd_jobcaseid to set
	 */
	public void setPd_jobcaseid(String pd_jobcaseid) {
		this.pd_jobcaseid = pd_jobcaseid;
	}
	/**
	 * @return the pd_phntype
	 */
	@Column(name = "pd_phntype")
	public Integer getPd_phntype() {
		return pd_phntype;
	}
	/**
	 * @param pd_phntype the pd_phntype to set
	 */
	public void setPd_phntype(Integer pd_phntype) {
		this.pd_phntype = pd_phntype;
	}
	/**
	 * @return the pd_dt2d
	 */
	@Column(name = "pd_dt2d")
	public String getPd_dt2d() {
		return pd_dt2d;
	}
	/**
	 * @param pd_dt2d the pd_dt2d to set
	 */
	public void setPd_dt2d(String pd_dt2d) {
		this.pd_dt2d = pd_dt2d;
	}
	/**
	 * @return the pd_device
	 */
	@Column(name = "pd_device")
	public String getPd_device() {
		return pd_device;
	}
	/**
	 * @param pd_device the pd_device to set
	 */
	public void setPd_device(String pd_device) {
		this.pd_device = pd_device;
	}
	/**
	 * @return the pd_recalltime
	 */
	@Column(name = "pd_recalltime")
	public String getPd_recalltime() {
		return pd_recalltime;
	}
	/**
	 * @param pd_recalltime the pd_recalltime to set
	 */
	public void setPd_recalltime(String pd_recalltime) {
		this.pd_recalltime = pd_recalltime;
	}
	/**
	 * @return the pd_campaignid
	 */
	@Column(name = "pd_campaignid")
	public String getPd_campaignid() {
		return pd_campaignid;
	}
	/**
	 * @param pd_campaignid the pd_campaignid to set
	 */
	public void setPd_campaignid(String pd_campaignid) {
		this.pd_campaignid = pd_campaignid;
	}
	/**
	 * @return the pd_sort
	 */
	@Column(name = "pd_sort")
	public String getPd_sort() {
		return pd_sort;
	}
	/**
	 * @param pd_sort the pd_sort to set
	 */
	public void setPd_sort(String pd_sort) {
		this.pd_sort = pd_sort;
	}
	/**
	 * @return the pd_prio
	 */
	@Column(name = "pd_prio")
	public Integer getPd_prio() {
		return pd_prio;
	}
	/**
	 * @param pd_prio the pd_prio to set
	 */
	public void setPd_prio(Integer pd_prio) {
		this.pd_prio = pd_prio;
	}
	/**
	 * @return the pd_dialnum
	 */
	@Column(name = "pd_dialnum")
	public String getPd_dialnum() {
		return pd_dialnum;
	}
	/**
	 * @param pd_dialnum the pd_dialnum to set
	 */
	public void setPd_dialnum(String pd_dialnum) {
		this.pd_dialnum = pd_dialnum;
	}
	/**
	 * @return the pd_channel
	 */
	@Column(name = "pd_channel")
	public String getPd_channel() {
		return pd_channel;
	}
	/**
	 * @param pd_channel the pd_channel to set
	 */
	public void setPd_channel(String pd_channel) {
		this.pd_channel = pd_channel;
	}
	/**
	 * @return the pd_login_caseid
	 */
	@Column(name = "pd_login_caseid")
	public String getPd_login_caseid() {
		return pd_login_caseid;
	}
	/**
	 * @param pd_login_caseid the pd_login_caseid to set
	 */
	public void setPd_login_caseid(String pd_login_caseid) {
		this.pd_login_caseid = pd_login_caseid;
	}
	/**
	 * @return the pd_jobid
	 */
	@Column(name = "pd_jobid")
	public String getPd_jobid() {
		return pd_jobid;
	}
	/**
	 * @param pd_jobid the pd_jobid to set
	 */
	public void setPd_jobid(String pd_jobid) {
		this.pd_jobid = pd_jobid;
	}
	/**
	 * @return the pd_followagent
	 */
	@Column(name = "pd_followagent")
	public String getPd_followagent() {
		return pd_followagent;
	}
	/**
	 * @param pd_followagent the pd_followagent to set
	 */
	public void setPd_followagent(String pd_followagent) {
		this.pd_followagent = pd_followagent;
	}
	/**
	 * @return the pd_dialmode
	 */
	@Column(name = "pd_dialmode")
	public String getPd_dialmode() {
		return pd_dialmode;
	}
	/**
	 * @param pd_dialmode the pd_dialmode to set
	 */
	public void setPd_dialmode(String pd_dialmode) {
		this.pd_dialmode = pd_dialmode;
	}
	/**
	 * @return the pd_updatetime
	 */
	@Column(name = "pd_updatetime")
	public String getPd_updatetime() {
		return pd_updatetime;
	}
	/**
	 * @param pd_updatetime the pd_updatetime to set
	 */
	public void setPd_updatetime(String pd_updatetime) {
		this.pd_updatetime = pd_updatetime;
	}
	/**
	 * @return the pd_updatedt
	 */
	@Column(name = "pd_updatedt")
	public String getPd_updatedt() {
		return pd_updatedt;
	}
	/**
	 * @param pd_updatedt the pd_updatedt to set
	 */
	public void setPd_updatedt(String pd_updatedt) {
		this.pd_updatedt = pd_updatedt;
	}
	/**
	 * @return the pd_talktime
	 */
	@Column(name = "pd_talktime")
	public String getPd_talktime() {
		return pd_talktime;
	}
	/**
	 * @param pd_talktime the pd_talktime to set
	 */
	public void setPd_talktime(String pd_talktime) {
		this.pd_talktime = pd_talktime;
	}
	/**
	 * @return the pd_acwtime
	 */
	@Column(name = "pd_acwtime")
	public String getPd_acwtime() {
		return pd_acwtime;
	}
	/**
	 * @param pd_acwtime the pd_acwtime to set
	 */
	public void setPd_acwtime(String pd_acwtime) {
		this.pd_acwtime = pd_acwtime;
	}
	/**
	 * @return the pd_referenceid
	 */
	@Column(name = "pd_referenceid")
	public String getPd_referenceid() {
		return pd_referenceid;
	}
	/**
	 * @param pd_referenceid the pd_referenceid to set
	 */
	public void setPd_referenceid(String pd_referenceid) {
		this.pd_referenceid = pd_referenceid;
	}
	/**
	 * @return the pd_other01
	 */
	@Column(name = "pd_other01")
	public String getPd_other01() {
		return pd_other01;
	}
	/**
	 * @param pd_other01 the pd_other01 to set
	 */
	public void setPd_other01(String pd_other01) {
		this.pd_other01 = pd_other01;
	}
	/**
	 * @return the pd_other02
	 */
	@Column(name = "pd_other02")
	public String getPd_other02() {
		return pd_other02;
	}
	/**
	 * @param pd_other02 the pd_other02 to set
	 */
	public void setPd_other02(String pd_other02) {
		this.pd_other02 = pd_other02;
	}
	/**
	 * @return the pd_other03
	 */
	@Column(name = "pd_other03")
	public String getPd_other03() {
		return pd_other03;
	}
	/**
	 * @param pd_other03 the pd_other03 to set
	 */
	public void setPd_other03(String pd_other03) {
		this.pd_other03 = pd_other03;
	}
	/**
	 * @return the pd_other04
	 */
	@Column(name = "pd_other04")
	public String getPd_other04() {
		return pd_other04;
	}
	/**
	 * @param pd_other04 the pd_other04 to set
	 */
	public void setPd_other04(String pd_other04) {
		this.pd_other04 = pd_other04;
	}
	/**
	 * @return the pd_other05
	 */
	@Column(name = "pd_other05")
	public String getPd_other05() {
		return pd_other05;
	}
	/**
	 * @param pd_other05 the pd_other05 to set
	 */
	public void setPd_other05(String pd_other05) {
		this.pd_other05 = pd_other05;
	}
	/**
	 * @return the pd_other06
	 */
	@Column(name = "pd_other06")
	public String getPd_other06() {
		return pd_other06;
	}
	/**
	 * @param pd_other06 the pd_other06 to set
	 */
	public void setPd_other06(String pd_other06) {
		this.pd_other06 = pd_other06;
	}
	/**
	 * @return the pd_other07
	 */
	@Column(name = "pd_other07")
	public String getPd_other07() {
		return pd_other07;
	}
	/**
	 * @param pd_other07 the pd_other07 to set
	 */
	public void setPd_other07(String pd_other07) {
		this.pd_other07 = pd_other07;
	}
	/**
	 * @return the pd_other08
	 */
	@Column(name = "pd_other08")
	public String getPd_other08() {
		return pd_other08;
	}
	/**
	 * @param pd_other08 the pd_other08 to set
	 */
	public void setPd_other08(String pd_other08) {
		this.pd_other08 = pd_other08;
	}
	/**
	 * @return the pd_other09
	 */
	@Column(name = "pd_other09")
	public String getPd_other09() {
		return pd_other09;
	}
	/**
	 * @param pd_other09 the pd_other09 to set
	 */
	public void setPd_other09(String pd_other09) {
		this.pd_other09 = pd_other09;
	}
	/**
	 * @return the pd_other10
	 */
	@Column(name = "pd_other10")
	public String getPd_other10() {
		return pd_other10;
	}
	/**
	 * @param pd_other10 the pd_other10 to set
	 */
	public void setPd_other10(String pd_other10) {
		this.pd_other10 = pd_other10;
	}
	/**
	 * @return the contactinfo
	 */
	@Column(name = "contactinfo")
	public String getContactinfo() {
		return contactinfo;
	}
	/**
	 * @param contactinfo the contactinfo to set
	 */
	public void setContactinfo(String contactinfo) {
		this.contactinfo = contactinfo;
	}
	/**
	 * @return the statisinfo
	 */
	@Column(name = "statisinfo")
	public String getStatisinfo() {
		return statisinfo;
	}
	/**
	 * @param statisinfo the statisinfo to set
	 */
	public void setStatisinfo(String statisinfo) {
		this.statisinfo = statisinfo;
	}
	/**
	 * @return the status
	 */
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	/**
	 * @return the priority
	 */
	@Column(name = "priority")
	public Long getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	
	
	
	/**
	 * @return the campaignId
	 */
	@Column(name = "campaign_Id")
	public Long getCampaignId() {
		return campaignId;
	}
	/**
	 * @param campaignId the campaignId to set
	 */
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
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
		result = prime * result
				+ ((assignagent == null) ? 0 : assignagent.hashCode());
		result = prime * result
				+ ((assigntime == null) ? 0 : assigntime.hashCode());
		result = prime * result + ((batchid == null) ? 0 : batchid.hashCode());
		result = prime * result
				+ ((contactid == null) ? 0 : contactid.hashCode());
		result = prime * result
				+ ((contactinfo == null) ? 0 : contactinfo.hashCode());
		result = prime * result
				+ ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result
				+ ((curqueuedialcnt == null) ? 0 : curqueuedialcnt.hashCode());
		result = prime * result
				+ ((datasrcid == null) ? 0 : datasrcid.hashCode());
		result = prime * result + ((dialed == null) ? 0 : dialed.hashCode());
		result = prime * result + ((enddate == null) ? 0 : enddate.hashCode());
		result = prime * result
				+ ((pd_acwtime == null) ? 0 : pd_acwtime.hashCode());
		result = prime * result
				+ ((pd_agentid == null) ? 0 : pd_agentid.hashCode());
		result = prime * result
				+ ((pd_calllist == null) ? 0 : pd_calllist.hashCode());
		result = prime * result
				+ ((pd_campaignid == null) ? 0 : pd_campaignid.hashCode());
		result = prime * result
				+ ((pd_caseid == null) ? 0 : pd_caseid.hashCode());
		result = prime * result + ((pd_ccc == null) ? 0 : pd_ccc.hashCode());
		result = prime * result
				+ ((pd_channel == null) ? 0 : pd_channel.hashCode());
		result = prime * result
				+ ((pd_connectdt == null) ? 0 : pd_connectdt.hashCode());
		result = prime * result
				+ ((pd_curstatus == null) ? 0 : pd_curstatus.hashCode());
		result = prime * result
				+ ((pd_customerid == null) ? 0 : pd_customerid.hashCode());
		result = prime * result
				+ ((pd_device == null) ? 0 : pd_device.hashCode());
		result = prime * result
				+ ((pd_dialcnt == null) ? 0 : pd_dialcnt.hashCode());
		result = prime * result
				+ ((pd_dialmode == null) ? 0 : pd_dialmode.hashCode());
		result = prime * result
				+ ((pd_dialnum == null) ? 0 : pd_dialnum.hashCode());
		result = prime * result
				+ ((pd_dropdt == null) ? 0 : pd_dropdt.hashCode());
		result = prime * result + ((pd_dt2d == null) ? 0 : pd_dt2d.hashCode());
		result = prime * result
				+ ((pd_followagent == null) ? 0 : pd_followagent.hashCode());
		result = prime * result
				+ ((pd_jobcaseid == null) ? 0 : pd_jobcaseid.hashCode());
		result = prime * result
				+ ((pd_jobid == null) ? 0 : pd_jobid.hashCode());
		result = prime * result
				+ ((pd_login_caseid == null) ? 0 : pd_login_caseid.hashCode());
		result = prime * result
				+ ((pd_other01 == null) ? 0 : pd_other01.hashCode());
		result = prime * result
				+ ((pd_other02 == null) ? 0 : pd_other02.hashCode());
		result = prime * result
				+ ((pd_other03 == null) ? 0 : pd_other03.hashCode());
		result = prime * result
				+ ((pd_other04 == null) ? 0 : pd_other04.hashCode());
		result = prime * result
				+ ((pd_other05 == null) ? 0 : pd_other05.hashCode());
		result = prime * result
				+ ((pd_other06 == null) ? 0 : pd_other06.hashCode());
		result = prime * result
				+ ((pd_other07 == null) ? 0 : pd_other07.hashCode());
		result = prime * result
				+ ((pd_other08 == null) ? 0 : pd_other08.hashCode());
		result = prime * result
				+ ((pd_other09 == null) ? 0 : pd_other09.hashCode());
		result = prime * result
				+ ((pd_other10 == null) ? 0 : pd_other10.hashCode());
		result = prime * result
				+ ((pd_phnnum == null) ? 0 : pd_phnnum.hashCode());
		result = prime * result
				+ ((pd_phntype == null) ? 0 : pd_phntype.hashCode());
		result = prime * result + ((pd_prio == null) ? 0 : pd_prio.hashCode());
		result = prime * result
				+ ((pd_recalltime == null) ? 0 : pd_recalltime.hashCode());
		result = prime * result
				+ ((pd_referenceid == null) ? 0 : pd_referenceid.hashCode());
		result = prime * result
				+ ((pd_releasedt == null) ? 0 : pd_releasedt.hashCode());
		result = prime * result + ((pd_sort == null) ? 0 : pd_sort.hashCode());
		result = prime * result
				+ ((pd_startdt == null) ? 0 : pd_startdt.hashCode());
		result = prime * result
				+ ((pd_talktime == null) ? 0 : pd_talktime.hashCode());
		result = prime * result
				+ ((pd_updatedt == null) ? 0 : pd_updatedt.hashCode());
		result = prime * result
				+ ((pd_updatetime == null) ? 0 : pd_updatetime.hashCode());
		result = prime * result
				+ ((prequeueid == null) ? 0 : prequeueid.hashCode());
		result = prime * result + ((queueid == null) ? 0 : queueid.hashCode());
		result = prime * result
				+ ((resultid == null) ? 0 : resultid.hashCode());
		result = prime * result
				+ ((startdate == null) ? 0 : startdate.hashCode());
		result = prime * result
				+ ((statisinfo == null) ? 0 : statisinfo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((updatetime == null) ? 0 : updatetime.hashCode());
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
		ObContact other = (ObContact) obj;
		if (assignagent == null) {
			if (other.assignagent != null)
				return false;
		} else if (!assignagent.equals(other.assignagent))
			return false;
		if (assigntime == null) {
			if (other.assigntime != null)
				return false;
		} else if (!assigntime.equals(other.assigntime))
			return false;
		if (batchid == null) {
			if (other.batchid != null)
				return false;
		} else if (!batchid.equals(other.batchid))
			return false;
		if (contactid == null) {
			if (other.contactid != null)
				return false;
		} else if (!contactid.equals(other.contactid))
			return false;
		if (contactinfo == null) {
			if (other.contactinfo != null)
				return false;
		} else if (!contactinfo.equals(other.contactinfo))
			return false;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (curqueuedialcnt == null) {
			if (other.curqueuedialcnt != null)
				return false;
		} else if (!curqueuedialcnt.equals(other.curqueuedialcnt))
			return false;
		if (datasrcid == null) {
			if (other.datasrcid != null)
				return false;
		} else if (!datasrcid.equals(other.datasrcid))
			return false;
		if (dialed == null) {
			if (other.dialed != null)
				return false;
		} else if (!dialed.equals(other.dialed))
			return false;
		if (enddate == null) {
			if (other.enddate != null)
				return false;
		} else if (!enddate.equals(other.enddate))
			return false;
		if (pd_acwtime == null) {
			if (other.pd_acwtime != null)
				return false;
		} else if (!pd_acwtime.equals(other.pd_acwtime))
			return false;
		if (pd_agentid == null) {
			if (other.pd_agentid != null)
				return false;
		} else if (!pd_agentid.equals(other.pd_agentid))
			return false;
		if (pd_calllist == null) {
			if (other.pd_calllist != null)
				return false;
		} else if (!pd_calllist.equals(other.pd_calllist))
			return false;
		if (pd_campaignid == null) {
			if (other.pd_campaignid != null)
				return false;
		} else if (!pd_campaignid.equals(other.pd_campaignid))
			return false;
		if (pd_caseid == null) {
			if (other.pd_caseid != null)
				return false;
		} else if (!pd_caseid.equals(other.pd_caseid))
			return false;
		if (pd_ccc == null) {
			if (other.pd_ccc != null)
				return false;
		} else if (!pd_ccc.equals(other.pd_ccc))
			return false;
		if (pd_channel == null) {
			if (other.pd_channel != null)
				return false;
		} else if (!pd_channel.equals(other.pd_channel))
			return false;
		if (pd_connectdt == null) {
			if (other.pd_connectdt != null)
				return false;
		} else if (!pd_connectdt.equals(other.pd_connectdt))
			return false;
		if (pd_curstatus == null) {
			if (other.pd_curstatus != null)
				return false;
		} else if (!pd_curstatus.equals(other.pd_curstatus))
			return false;
		if (pd_customerid == null) {
			if (other.pd_customerid != null)
				return false;
		} else if (!pd_customerid.equals(other.pd_customerid))
			return false;
		if (pd_device == null) {
			if (other.pd_device != null)
				return false;
		} else if (!pd_device.equals(other.pd_device))
			return false;
		if (pd_dialcnt == null) {
			if (other.pd_dialcnt != null)
				return false;
		} else if (!pd_dialcnt.equals(other.pd_dialcnt))
			return false;
		if (pd_dialmode == null) {
			if (other.pd_dialmode != null)
				return false;
		} else if (!pd_dialmode.equals(other.pd_dialmode))
			return false;
		if (pd_dialnum == null) {
			if (other.pd_dialnum != null)
				return false;
		} else if (!pd_dialnum.equals(other.pd_dialnum))
			return false;
		if (pd_dropdt == null) {
			if (other.pd_dropdt != null)
				return false;
		} else if (!pd_dropdt.equals(other.pd_dropdt))
			return false;
		if (pd_dt2d == null) {
			if (other.pd_dt2d != null)
				return false;
		} else if (!pd_dt2d.equals(other.pd_dt2d))
			return false;
		if (pd_followagent == null) {
			if (other.pd_followagent != null)
				return false;
		} else if (!pd_followagent.equals(other.pd_followagent))
			return false;
		if (pd_jobcaseid == null) {
			if (other.pd_jobcaseid != null)
				return false;
		} else if (!pd_jobcaseid.equals(other.pd_jobcaseid))
			return false;
		if (pd_jobid == null) {
			if (other.pd_jobid != null)
				return false;
		} else if (!pd_jobid.equals(other.pd_jobid))
			return false;
		if (pd_login_caseid == null) {
			if (other.pd_login_caseid != null)
				return false;
		} else if (!pd_login_caseid.equals(other.pd_login_caseid))
			return false;
		if (pd_other01 == null) {
			if (other.pd_other01 != null)
				return false;
		} else if (!pd_other01.equals(other.pd_other01))
			return false;
		if (pd_other02 == null) {
			if (other.pd_other02 != null)
				return false;
		} else if (!pd_other02.equals(other.pd_other02))
			return false;
		if (pd_other03 == null) {
			if (other.pd_other03 != null)
				return false;
		} else if (!pd_other03.equals(other.pd_other03))
			return false;
		if (pd_other04 == null) {
			if (other.pd_other04 != null)
				return false;
		} else if (!pd_other04.equals(other.pd_other04))
			return false;
		if (pd_other05 == null) {
			if (other.pd_other05 != null)
				return false;
		} else if (!pd_other05.equals(other.pd_other05))
			return false;
		if (pd_other06 == null) {
			if (other.pd_other06 != null)
				return false;
		} else if (!pd_other06.equals(other.pd_other06))
			return false;
		if (pd_other07 == null) {
			if (other.pd_other07 != null)
				return false;
		} else if (!pd_other07.equals(other.pd_other07))
			return false;
		if (pd_other08 == null) {
			if (other.pd_other08 != null)
				return false;
		} else if (!pd_other08.equals(other.pd_other08))
			return false;
		if (pd_other09 == null) {
			if (other.pd_other09 != null)
				return false;
		} else if (!pd_other09.equals(other.pd_other09))
			return false;
		if (pd_other10 == null) {
			if (other.pd_other10 != null)
				return false;
		} else if (!pd_other10.equals(other.pd_other10))
			return false;
		if (pd_phnnum == null) {
			if (other.pd_phnnum != null)
				return false;
		} else if (!pd_phnnum.equals(other.pd_phnnum))
			return false;
		if (pd_phntype == null) {
			if (other.pd_phntype != null)
				return false;
		} else if (!pd_phntype.equals(other.pd_phntype))
			return false;
		if (pd_prio == null) {
			if (other.pd_prio != null)
				return false;
		} else if (!pd_prio.equals(other.pd_prio))
			return false;
		if (pd_recalltime == null) {
			if (other.pd_recalltime != null)
				return false;
		} else if (!pd_recalltime.equals(other.pd_recalltime))
			return false;
		if (pd_referenceid == null) {
			if (other.pd_referenceid != null)
				return false;
		} else if (!pd_referenceid.equals(other.pd_referenceid))
			return false;
		if (pd_releasedt == null) {
			if (other.pd_releasedt != null)
				return false;
		} else if (!pd_releasedt.equals(other.pd_releasedt))
			return false;
		if (pd_sort == null) {
			if (other.pd_sort != null)
				return false;
		} else if (!pd_sort.equals(other.pd_sort))
			return false;
		if (pd_startdt == null) {
			if (other.pd_startdt != null)
				return false;
		} else if (!pd_startdt.equals(other.pd_startdt))
			return false;
		if (pd_talktime == null) {
			if (other.pd_talktime != null)
				return false;
		} else if (!pd_talktime.equals(other.pd_talktime))
			return false;
		if (pd_updatedt == null) {
			if (other.pd_updatedt != null)
				return false;
		} else if (!pd_updatedt.equals(other.pd_updatedt))
			return false;
		if (pd_updatetime == null) {
			if (other.pd_updatetime != null)
				return false;
		} else if (!pd_updatetime.equals(other.pd_updatetime))
			return false;
		if (prequeueid == null) {
			if (other.prequeueid != null)
				return false;
		} else if (!prequeueid.equals(other.prequeueid))
			return false;
		if (queueid == null) {
			if (other.queueid != null)
				return false;
		} else if (!queueid.equals(other.queueid))
			return false;
		if (resultid == null) {
			if (other.resultid != null)
				return false;
		} else if (!resultid.equals(other.resultid))
			return false;
		if (startdate == null) {
			if (other.startdate != null)
				return false;
		} else if (!startdate.equals(other.startdate))
			return false;
		if (statisinfo == null) {
			if (other.statisinfo != null)
				return false;
		} else if (!statisinfo.equals(other.statisinfo))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updatetime == null) {
			if (other.updatetime != null)
				return false;
		} else if (!updatetime.equals(other.updatetime))
			return false;
		return true;
	}
	
	
}
