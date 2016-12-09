package com.chinadrtv.erp.model;

import javax.persistence.*;

/**
 * 运费规则配置明细
 * User: gaodejian
 * Date: 13-3-21
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "LOGISTICS_FEE_RULE_DETAIL", schema = "ACOAPP_OMS")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "RULE_TYPE", discriminatorType = DiscriminatorType.STRING)
public class LogisticsFeeRuleDetail implements java.io.Serializable {

    private Integer id;
    private String ruleType;
    private LogisticsFeeRule rule;
    private Warehouse warehouse;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGISTICS_FEE_RULE_DETAIL_SEQ")
    @SequenceGenerator(name = "LOGISTICS_FEE_RULE_DETAIL_SEQ", sequenceName = "ACOAPP_OMS.LOGISTICS_FEE_RULE_DETAIL_SEQ")
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOGISTICS_FEE_RULE_ID")
    public LogisticsFeeRule getRule() {
        return rule;
    }

    public void setRule(LogisticsFeeRule rule) {
        this.rule = rule;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "WAREHOUSE_ID")
    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Column(name = "RULE_TYPE", insertable=false, updatable = false)
    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }
}
