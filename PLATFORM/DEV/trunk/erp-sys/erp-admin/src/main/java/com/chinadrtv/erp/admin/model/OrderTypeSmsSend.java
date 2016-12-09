package com.chinadrtv.erp.admin.model;

import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 12-11-13
 * Time: 上午10:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "OrderTypeSmsSend", schema = "IAGENT")
//短信配置表的信息
public class OrderTypeSmsSend implements java.io.Serializable {
   private Integer id ;
   private String smstype;
    private OrderType orderType;
    private String smsname;
    private PayType paytype;
   private Date setdate;
    private User user ;
    private Double senddelytime;
    private String smscontent;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_OrderTypeSmsSend")
    @SequenceGenerator(name = "seq_OrderTypeSmsSend", sequenceName = "IAGENT.AUDIT_LOG_SEQ")
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "ORDERTYPE")
    @org.hibernate.annotations.Fetch(value = FetchMode.SELECT) //Join不加Where过滤,bug?
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    @ManyToOne
    @JoinColumn(name = "PAYTYPE")
    @org.hibernate.annotations.Fetch(value = FetchMode.SELECT) //Join不加Where过滤,bug?
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public PayType getPaytype() {
        return paytype;
    }

    public void setPaytype(PayType payType) {
        this.paytype = payType;
    }
    @Column(name = "SMSTYPE")
    public String getSmsType() {
        return smstype;
    }

    public void setSmsType(String smsType) {
        this.smstype = smsType;
    }

    @Column(name = "SMSNAME")
    public String getSmsName() {
        return smsname;
    }

    public void setSmsName(String smsName) {
        this.smsname = smsName;
    }
    @Column(name = "SETDATE")
    public Date getSetDate() {
        return setdate;
    }

    public void setSetDate(Date setDate) {
        this.setdate = setDate;
    }

    @ManyToOne
    @JoinColumn(name = "SETPROID")
    @org.hibernate.annotations.Fetch(value = FetchMode.SELECT) //Join不加Where过滤,bug?
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Column(name = "SENDDELYTIME")
    public Double getSendDelyTime() {
        return senddelytime;
    }

    public void setSendDelyTime(Double sendDelyTime) {
        this.senddelytime = sendDelyTime;
    }

    @Column(name = "SMSCONTENT")
    public String getSmscontent() {
        return smscontent;
    }

    public void setSmscontent(String smscontent) {
        this.smscontent = smscontent;
    }
}
