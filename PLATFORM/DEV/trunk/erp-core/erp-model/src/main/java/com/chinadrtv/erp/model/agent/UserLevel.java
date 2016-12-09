/**
 * 
 */
package com.chinadrtv.erp.model.agent;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "USRLEVEL", schema = "IAGENT")
public class UserLevel implements Serializable {

     /**
	 * 
	 */
	private static final long serialVersionUID = 3867960144222292507L;

	@Id
    @GeneratedValue(generator="USER_LEVEL_GENERATOR")
    @GenericGenerator(name="USER_LEVEL_GENERATOR", strategy="assigned")
	@Column(name = "USRID", nullable = false, length = 10)
	private String userId;
	
	@Column(name = "LEVELID", nullable = true, length = 10)
	private String levelId;
	
	@Column(name = "LEVELID2", nullable = true, length = 10)
	private String levelId2;
	
	@Column(name = "LEVELID3", nullable = true, length = 10)
	private String levelId3;
	
	@Column(name = "MDDT", nullable = true)
	private Date modifyTime;
	
	@Column(name = "VALID", nullable = false, length = 2)
	private String valid;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getLevelId2() {
		return levelId2;
	}

	public void setLevelId2(String levelId2) {
		this.levelId2 = levelId2;
	}

	public String getLevelId3() {
		return levelId3;
	}

	public void setLevelId3(String levelId3) {
		this.levelId3 = levelId3;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
}
