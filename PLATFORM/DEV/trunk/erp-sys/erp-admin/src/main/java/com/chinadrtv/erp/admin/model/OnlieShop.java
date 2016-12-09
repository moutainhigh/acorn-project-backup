package com.chinadrtv.erp.admin.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 12-8-9
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "OnlieShop", schema = "IAGENT")
//网店信息对象
public class OnlieShop implements java.io.Serializable {

  private String shopid;//网店编号
    private  String  shopname;//  网店名称
    private String  shopstate;// 网店状态

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDIT_LOG_SEQ")
    @SequenceGenerator(name = "AUDIT_LOG_SEQ", sequenceName = "IAGENT.AUDIT_LOG_SEQ")
    @Column(name = "SHOPID")
    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }
    @Column(name = "SHOPNAME")
    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
    @Column(name = "SHOPSTATE")
    public String getShopstate() {
        return shopstate;
    }

    public void setShopstate(String shopstate) {
        this.shopstate = shopstate;
    }



}
