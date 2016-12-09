/**
 * 
 */
package com.chinadrtv.erp.knowledge.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 知识话术附件
 * 
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "KNOWLEDGE_ATTACHMENT", schema = "ACOAPP_MARKETING")
public class KnowledgeAttachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8784700860109005915L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTACHMENT_SEQ_GEN")
	@SequenceGenerator(name = "ATTACHMENT_SEQ_GEN", sequenceName = "ACOAPP_MARKETING.SEQ_KNOWLEDGE_ATTACHMENT", allocationSize = 1)
	@Column(name = "ID", length = 11, nullable = false)
	private Long id;
	
	@Column(name = "ARTICLE_ID", length = 20, nullable = true)
	private Long articleId;
	
	@Column(name = "NAME", length = 50, nullable = true)
	private String name;
	
	@Column(name = "URL", length = 200, nullable = true)
	private String url;
	
	@Column(name = "FILE_SIZE", nullable = true)
	private Long fileSize;
	
	@Column(name = "DOWNLOAD", nullable = true)
	private Integer downloadCount;
	
	@Column(name = "STATUS", length = 1, nullable = true)
	private String status;
	
	@Column(name = "CREATE_USER", length = 50, nullable = true)
	private String createUser;
	
	@Column(name = "CREATE_DATE", nullable = true)
	private Date createDate;
	
	@Column(name = "UPDATE_USER", length = 50, nullable = true)
	private String updateUser;
	
	@Column(name = "UPDATE_DATE", nullable = true)
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	} 

}
