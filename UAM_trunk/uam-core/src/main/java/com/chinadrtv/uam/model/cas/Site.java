package com.chinadrtv.uam.model.cas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.IndexColumn;

import com.chinadrtv.uam.model.BaseEntity;

/**
 * 可被信任的系统平台，如oms，sales等等
 * 
 * @author dengqianyong
 *
 */
@Entity
@Table(name = "CAS_REGISTERED_SERVICE", schema = "ACOAPP_UAM")
public class Site extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5065607253753347801L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	/**
	 * 有ant,regex两种 默认使用ant
	 */
	@JsonIgnore
	@Column(name = "EXPRESSION_TYPE", nullable = false, length = 15)
	private String expressionType = "ant";
	
	/**
	 * 对应可返回的属性
	 */
	@JsonIgnore
	@ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @JoinTable(
		name = "CAS_RS_ATTRIBUTES", 
		schema = "ACOAPP_UAM",
		joinColumns = @JoinColumn(name = "REGISTERED_SERVICE_ID")
    )
    @Column(name = "A_NAME", nullable = false)
    @IndexColumn(name = "A_ID")
    private List<String> allowedAttributes = new ArrayList<String>();

	/**
	 * 排序号
	 */
	@Column(name = "EVALUATION_ORDER", nullable = false)
    private Integer evaluationOrder;

    /**
     * Name of the user attribute that this service expects as the value of the username payload in the
     * validate responses.
     */
	@JsonIgnore
    @Column(name = "USERNAME_ATTR", nullable = true, length = 256)
    private String usernameAttribute = null;
    
    /**
     * 描述
     */
    @Column(name = "DESCRIPTION", nullable = true, length = 255)
    private String description;

    /**
     * 服务平台的ID，通过对应服务平台的url根路径http://www.xxx.com
     */
    @Column(name = "SERVICEID", nullable = true)
    private String serviceId;

    /**
     * 服务平台名称
     */
    @Column(name = "NAME", nullable = true)
    private String name;

    @JsonIgnore
    @Column(name = "THEME", nullable = true)
    private String theme;

    /**
     * 是否可以代理
     */
    @Column(name = "ALLOWEDTOPROXY", nullable = false)
    private boolean allowedToProxy = true;

    /**
     * 是否可用
     */
    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;

    /**
     * 是否使用SSO,如不使用, 将强制要求登录一次
     */
    @Column(name = "SSOENABLED", nullable = false)
    private boolean ssoEnabled = true;

    /**
     * 可否匿名登录
     */
    @Column(name = "ANONYMOUSACCESS", nullable = false)
    private boolean anonymousAccess = false;

    /**
     * 是否忽略设置的属性
     */
    @JsonIgnore
    @Column(name = "IGNOREATTRIBUTES", nullable = false)
    private boolean ignoreAttributes = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(String expressionType) {
		this.expressionType = expressionType;
	}

	public List<String> getAllowedAttributes() {
		return allowedAttributes;
	}

	public void setAllowedAttributes(List<String> allowedAttributes) {
		this.allowedAttributes = allowedAttributes;
	}

	public Integer getEvaluationOrder() {
		return evaluationOrder;
	}

	public void setEvaluationOrder(Integer evaluationOrder) {
		this.evaluationOrder = evaluationOrder;
	}

	public String getUsernameAttribute() {
		return usernameAttribute;
	}

	public void setUsernameAttribute(String usernameAttribute) {
		this.usernameAttribute = usernameAttribute;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public boolean isAllowedToProxy() {
		return allowedToProxy;
	}

	public void setAllowedToProxy(boolean allowedToProxy) {
		this.allowedToProxy = allowedToProxy;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isSsoEnabled() {
		return ssoEnabled;
	}

	public void setSsoEnabled(boolean ssoEnabled) {
		this.ssoEnabled = ssoEnabled;
	}

	public boolean isAnonymousAccess() {
		return anonymousAccess;
	}

	public void setAnonymousAccess(boolean anonymousAccess) {
		this.anonymousAccess = anonymousAccess;
	}

	public boolean isIgnoreAttributes() {
		return ignoreAttributes;
	}

	public void setIgnoreAttributes(boolean ignoreAttributes) {
		this.ignoreAttributes = ignoreAttributes;
	}
    
}
