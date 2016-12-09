package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * User: zhaizhanyi
 * Date: 12-12-28
 */
@javax.persistence.Table(name = "ORDERHIST_ASSIGNLOG", schema = "ACOAPP_OMS")
@Entity
public class OrderhistAssignLog  implements java.io.Serializable{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer logId;
	private String  orderId;
	private String  userId;
	private String  status;
	private Date   	mdDate;
	private String  orig_companyId ;
	private Integer orig_warehouseId;
	private String  curr_companyId ;
	private Integer curr_warehouseId;
	private String  remark;


	/**
	 * 日志id
	 * @return
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQORDERCHOOSELOG")
    @SequenceGenerator(name = "SEQORDERCHOOSELOG", sequenceName = "ACOAPP_OMS.SEQ_MAILSTAT_HIS_RECID")
    @Column(name = "LOGID")
	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	/**
	 * 订单id
	 * @return
	 */
	@javax.persistence.Column(name = "ORDERID")
	@Basic
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 用户id
	 * @return
	 */
	@javax.persistence.Column(name = "USERID")
	@Basic
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 操作结果
	 * @return
	 */
	@javax.persistence.Column(name = "STATUS")
	@Basic
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 操作时间
	 * @return
	 */
	@javax.persistence.Column(name = "MDDATE")
	@Basic
	public Date getMdDate() {
		return mdDate;
	}

	public void setMdDate(Date mdDate) {
		this.mdDate = mdDate;
	}

	/**
	 * 原始承运商id
	 * @return
	 */
	@javax.persistence.Column(name = "ORIG_COMPANYID")
	@Basic
	public String getOrig_companyId() {
		return orig_companyId;
	}

	public void setOrig_companyId(String orig_companyId) {
		this.orig_companyId = orig_companyId;
	}

	/**
	 * 原始承运商仓库id
	 * @return
	 */
	@javax.persistence.Column(name = "ORIG_WAREHOUSEID")
	@Basic
	public Integer getOrig_warehouseId() {
		return orig_warehouseId;
	}

	public void setOrig_warehouseId(Integer orig_warehouseId) {
		this.orig_warehouseId = orig_warehouseId;
	}

	/**
	 * 修改后的承运商id
	 * @return
	 */
	@javax.persistence.Column(name = "CURR_COMPANYID")
	@Basic
	public String getCurr_companyId() {
		return curr_companyId;
	}

	public void setCurr_companyId(String curr_companyId) {
		this.curr_companyId = curr_companyId;
	}

	/**
	 * 修改后的承运商仓库id
	 * @return
	 */
	@javax.persistence.Column(name = "CURR_WAREHOUSEID")
	@Basic
	public Integer getCurr_warehouseId() {
		return curr_warehouseId;
	}

	public void setCurr_warehouseId(Integer curr_warehouseId) {
		this.curr_warehouseId = curr_warehouseId;
	}

	/**
	 * 备注
	 * @return
	 */
	@javax.persistence.Column(name = "REMARK")
	@Basic
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderhistAssignLog log = (OrderhistAssignLog) o;

        if (!logId.equals(log.logId)) return false;
        if (orderId != null ? !orderId.equals(log.orderId) : log.orderId != null) return false;
        if (userId != null ? !userId.equals(log.userId) : log.userId != null) return false;
        if (status != null ? !status.equals(log.status) : log.status != null) return false;
        if (remark != null ? !remark.equals(log.remark) : log.remark != null) return false;
        if (mdDate != null ? !mdDate.equals(log.mdDate) : log.mdDate != null) return false;
        if (curr_companyId != null ? !curr_companyId.equals(log.curr_companyId) : log.curr_companyId != null) return false;
        if (orig_companyId != null ? !orig_companyId.equals(log.orig_companyId) : log.orig_companyId != null) return false;
        if (!curr_warehouseId.equals(log.curr_warehouseId)) return false;
        if (!orig_warehouseId.equals(log.orig_warehouseId)) return false;
        return true;
    }
	
	@Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + logId;
        result = 31 * result + (curr_companyId != null ? curr_companyId.hashCode() : 0);
        result = 31 * result + (orig_companyId != null ? orig_companyId.hashCode() : 0);
        result = 31 * result + (mdDate != null ? mdDate.hashCode() : 0);
        result = 31 * result + curr_warehouseId;
        result = 31 * result + orig_warehouseId;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
