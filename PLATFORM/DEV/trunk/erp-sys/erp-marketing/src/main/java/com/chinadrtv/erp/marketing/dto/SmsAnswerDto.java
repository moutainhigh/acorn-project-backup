package com.chinadrtv.erp.marketing.dto;

public class SmsAnswerDto {
	private Long id;
	private String startTime;
	private String endTime;
	private String startDate;
	private String endDate;
	private String mobile;
	private String receiveChannel;
	private String smsChildId;
	private String state;
	private String receiveContent;
	private String contactid;
	private String receiveTimes;

	public SmsAnswerDto() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the receiveChannel
	 */
	public String getReceiveChannel() {
		return receiveChannel;
	}

	/**
	 * @param receiveChannel
	 *            the receiveChannel to set
	 */
	public void setReceiveChannel(String receiveChannel) {
		this.receiveChannel = receiveChannel;
	}

	/**
	 * @return the smsChildId
	 */
	public String getSmsChildId() {
		return smsChildId;
	}

	/**
	 * @param smsChildId
	 *            the smsChildId to set
	 */
	public void setSmsChildId(String smsChildId) {
		this.smsChildId = smsChildId;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the receiveContent
	 */
	public String getReceiveContent() {
		return receiveContent;
	}

	/**
	 * @param receiveContent
	 *            the receiveContent to set
	 */
	public void setReceiveContent(String receiveContent) {
		this.receiveContent = receiveContent;
	}

	/**
	 * @return the contactid
	 */
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
	 * @return the receiveTimes
	 */
	public String getReceiveTimes() {
		return receiveTimes;
	}

	/**
	 * @param receiveTimes
	 *            the receiveTimes to set
	 */
	public void setReceiveTimes(String receiveTimes) {
		this.receiveTimes = receiveTimes;
	}

}
