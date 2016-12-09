package com.chinadrtv.erp.model.agent;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-7-15
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "MEDIADNIS", schema = "IAGENT")
public class MediaDnis {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQMEDIADNIS")
    @SequenceGenerator(name = "SEQMEDIADNIS", sequenceName = "IAGENT.SEQMEDIADNIS", allocationSize = 1)
    @Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, length = 30)
    private String id;    //编号
    @Column(name = "DNIS", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    private String dnis; //中继号码
    @Column(name = "N400", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    private String n400;//400/800电话
    @Column(name = "TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    private String type;//中继号类别
    @Column(name = "STARTDT", unique = false, nullable = true, insertable = true, updatable = true)
    private Date startdt;//开始日期
    @Column(name = "ENDDT", unique = false, nullable = true, insertable = true, updatable = true)
    private Date enddt;//结束时间
    @Column(name = "DSC", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    private String dsc;//备注
    @Column(name = "ORDERTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    private String ordertype;//默认订单类型
    @Column(name = "AREA", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    private String area;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    public String getN400() {
        return n400;
    }

    public void setN400(String n400) {
        this.n400 = n400;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartdt() {
        return startdt;
    }

    public void setStartdt(Date startdt) {
        this.startdt = startdt;
    }

    public Date getEnddt() {
        return enddt;
    }

    public void setEnddt(Date enddt) {
        this.enddt = enddt;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
