package com.chinadrtv.erp.model;

import com.chinadrtv.erp.model.inventory.PlubasInfo;

import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Promotion entity.
 */
@Entity
@Table(name = "PROMOT", schema = "ACOAPP_OMS")
public class Promotion implements java.io.Serializable {

    // Fields

    private Long promotionid;
    private ChannelType channel;
    private String description;
    private String name;
    private Boolean active;
    private Boolean requiresCoupon;
    private Date startDate;
    private Date endDate;
    private Date dateAdded;
    private Date lastModified;
    private String updateBy;
    private String createdBy;
    private Integer maxuse;
    private Integer usedTimes;

    private Boolean cumulative=Boolean.TRUE;

    private Boolean isDeleted;

    private String product;
    /**
     * the execution order. the smaller value the execution first
     */
    private Integer execOrder;

    /**added by richard.mao: add many-one relationship to PromotionRule
     *
     */

    // the round sequence for calculate promotion, exp, the user point promotion should be in 2rd round
    private Integer calcRound;


    private PromotionRule promotionRule;



    private Set<PromotionOption> promotionOptions = new HashSet<PromotionOption>(0);


    //限制产品，skucodes
    private Set<PlubasInfo> promotionProdcuts = new HashSet<PlubasInfo>(0);

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "promotion_product", schema = "ACOAPP_OMS",
            joinColumns = {@JoinColumn(name = "promotion_id")},
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    public Set<PlubasInfo> getPromotionProdcuts() {
        return promotionProdcuts;
    }

    public void setPromotionProdcuts(Set<PlubasInfo> promotionProdcuts) {
        this.promotionProdcuts = promotionProdcuts;
    }


    //限制优惠券 coupon codes
   // private Set<String> coupons = new HashSet<String>(0);
    //限制产品标签
    //private Set<String> tags = new HashSet<String>();

    // Constructors

    /** default constructor */
    public Promotion() {
    }

    /** minimal constructor */
    public Promotion(Long promotionid) {
        this.promotionid = promotionid;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Promotion_SEQ")
    @SequenceGenerator(name = "Promotion_SEQ", sequenceName = "ACOAPP_OMS.Promotion_SEQ")
    @Column(name = "PROMOTION_ID")
    public Long getPromotionid() {
        return this.promotionid;
    }

    public void setPromotionid(Long promotionid) {
        this.promotionid = promotionid;
    }

    @NotEmpty
    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ACTIVE")
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * cumulative means the same promotion type(type is determined by promotion rule ID) could co-exist.
     * If multiple promotion instances exist, the first(i.e. with the lowest execOrder) will be selected.
     * @return
     */
    @Column(name = "IS_Cumulative")
    public Boolean getCumulative() {
        return this.cumulative;
    }

    /**
     * cumulative means the same promotion type(type is determined by promotion rule ID) could co-exist.
     * If multiple promotion instances exist, the first(i.e. with the lowest execOrder) will be selected.
     * @param cumulative
     */
    public void setCumulative(Boolean cumulative) {
        this.cumulative = cumulative;
    }

    @Column(name = "REQUIRES_COUPON")
    public Boolean getRequiresCoupon() {
        return this.requiresCoupon;
    }

    public void setRequiresCoupon(Boolean requiresCoupon) {
        this.requiresCoupon = requiresCoupon;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE", length = 7)
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", length = 7)
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_ADDED", length = 7)
    public Date getDateAdded() {
        return this.dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_MODIFIED", length = 7)
    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Column(name = "UPDATE_BY", length = 100)
    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "CREATED_BY", length = 100)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "MAXUSE")
    public Integer getMaxuse() {
        return this.maxuse;
    }

    public void setMaxuse(Integer maxuse) {
        this.maxuse = maxuse;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "promotion")
    public Set<PromotionOption> getPromotionOptions() {
        return this.promotionOptions;
    }

    public void setPromotionOptions(
            Set<PromotionOption> promotionOptions) {
        this.promotionOptions = promotionOptions;
    }

    @ManyToOne()
    @JoinColumn(name = "RULE_ID", referencedColumnName = "RULE_ID", nullable = false)
    public PromotionRule getPromotionRule() {
        return this.promotionRule;
    }

    public void setPromotionRule(PromotionRule promotionRule) {
        this.promotionRule=promotionRule;
    }

    @Column(name = "EXEC_ORDER")
    public Integer getExecOrder() {
        return this.execOrder;
    }

    public void setExecOrder(Integer execOrder) {
        this.execOrder=execOrder;
    }

    @Column(name = "PRODUCT")
    public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String toString(){
        StringBuffer buff=new StringBuffer();
        buff.append("promotionId:").append(promotionid);
        buff.append(",name:").append(name);
        buff.append(",rule:").append(promotionRule.getRuleId());
        buff.append(",description:").append(description);
        buff.append(",active:").append(active);
        buff.append(",execOrder:").append(execOrder);
        buff.append(",maxuse:").append(maxuse);
        buff.append(",requiresCoupon:").append(requiresCoupon);
        buff.append(",start date:").append(startDate);
        buff.append(",end date:").append(endDate);
        buff.append(",cumulative:").append(cumulative);
        buff.append(",promotionOptions:").append(promotionOptions);
        buff.append(",createdBy:").append(createdBy);
        buff.append(",dateAdded:").append(dateAdded);
        buff.append(",updateBy:").append(updateBy);
        buff.append(",lastModified:").append(lastModified);

        return buff.toString();
    }

    public Integer getUsedTimes() {
        return usedTimes;
    }

    @Column(name = "USED_TIMES")
    public void setUsedTimes(Integer usedTimes) {
        this.usedTimes = usedTimes;
    }

    @Column(name = "IS_DELETED")
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getCalcRound() {
        return calcRound;
    }

    @Column(name = "CALCROUND")
    public void setCalcRound(Integer calcRound) {
        this.calcRound = calcRound;
    }

    @ManyToOne
    @JoinColumn(name="CHANNEL")
    @org.hibernate.annotations.Fetch(value = FetchMode.SELECT) //Join不加Where过滤,bug?
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    @org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.REFRESH)
    public ChannelType getChannel() {
        return channel;
    }

    public void setChannel(ChannelType channel) {
        this.channel = channel;
    }
}