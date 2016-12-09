package com.chinadrtv.erp.sales.core.model;

import com.chinadrtv.erp.ic.model.NcPlubasInfo;
import com.chinadrtv.erp.ic.model.NcRealTimeStockItem;
import com.chinadrtv.erp.ic.model.RealTimeStockItem;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 已售订单统计
 * Date: 13-7-5
 * Time: 下午1:47
 * To change this template use File | Settings | File Templates.
 */
public class OrderSoldDto implements Serializable {

    private String ncCode;
    private String ncName;
    private String spellCode;
    private String spellName;
    private String ncfreeName;
    private Double onHandQty;
    private Double availableQty;
    private Double inTransitQty;
    private Double weekAvgSoldQty;     //一周平均销量
    private Double yesterdaySoldQty;   //昨天销售量
    private Long favoriteId;
    private Double eta;
    private Boolean dummy;

    public OrderSoldDto(NcPlubasInfo item, String ncfreeName, long favoriteId){
        super();
        this.ncCode = item.getNccode();
        this.ncName = item.getNcname();
        this.spellCode = item.getSpellcode();
        this.spellName = item.getSpellname();
        this.ncfreeName = ncfreeName;
        this.inTransitQty = 0D;
        this.onHandQty = 0D;
        this.availableQty = 0D;
        this.weekAvgSoldQty = 0D;
        this.yesterdaySoldQty = 0D;
        this.favoriteId = favoriteId;
        this.eta = 0D;
        this.dummy = true;
    }

    public OrderSoldDto(NcRealTimeStockItem item, String ncfreeName, double weekAvgSoldQty, double yesterdaySoldQty, long favoriteId, double eta){
        super();
        this.ncCode = item.getNcCode();
        this.ncName = item.getNcName();
        this.spellCode = item.getSpellCode();
        this.spellName = item.getSpellName();
        this.ncfreeName = ncfreeName;
        this.inTransitQty = item.getInTransitQty();
        this.onHandQty = item.getOnHandQty();
        this.availableQty = item.getAvailableQty();
        this.weekAvgSoldQty = weekAvgSoldQty;
        this.yesterdaySoldQty = yesterdaySoldQty;
        this.favoriteId = favoriteId;
        this.eta = eta;
        this.dummy = false;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getNcCode() {
        return this.ncCode;
    }

    public void setNcCode(String ncCode) {
        this.ncCode = ncCode;
    }

    public String getNcName() {
        return this.ncName;
    }

    public void setNcName(String ncName) {
        this.ncName = ncName;
    }

    public String getNcfreeName() {
        return this.ncfreeName;
    }

    public void setNcfreeName(String ncfreeName) {
        this.ncfreeName = ncfreeName;
    }

    public String getSpellCode() {
        return this.spellCode;
    }

    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
    }

    public String getSpellName() {
        return this.spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public Double getOnHandQty() {
        return this.onHandQty;
    }

    public void setOnHandQty(Double onHandQty) {
        this.onHandQty = onHandQty;
    }

    public Double getAvailableQty() {
        return this.availableQty;
    }

    public void setAvailableQty(Double availableQty) {
        this.availableQty = availableQty;
    }

    public Double getInTransitQty() {
        return this.inTransitQty;
    }

    public void setInTransitQty(Double inTransitQty) {
        this.inTransitQty = inTransitQty;
    }

    public Double getWeekAvgSoldQty() {
        return weekAvgSoldQty;
    }

    public void setWeekAvgSoldQty(Double weekAvgSoldQty) {
        this.weekAvgSoldQty = weekAvgSoldQty;
    }

    public Double getYesterdaySoldQty() {
        return yesterdaySoldQty;
    }

    public void setYesterdaySoldQty(Double yesterdaySoldQty) {
        this.yesterdaySoldQty = yesterdaySoldQty;
    }

    public Double getEta() {
        return eta;
    }

    public void setEta(Double eta) {
        this.eta = eta;
    }

    public Boolean getDummy() {
        return dummy;
    }

    public void setDummy(Boolean dummy) {
        this.dummy = dummy;
    }
}
