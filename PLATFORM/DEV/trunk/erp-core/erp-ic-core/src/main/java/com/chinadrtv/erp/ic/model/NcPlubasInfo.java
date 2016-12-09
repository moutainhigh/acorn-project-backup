package com.chinadrtv.erp.ic.model;

/**
 * NC商品信息（搜索）
 * User: gaodejian
 * Date: 13-5-29
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
public class NcPlubasInfo implements java.io.Serializable {
    /**
     * NC代码
     */
    private String nccode;
    /**
     * NC代码
     */
    private String ncname;
    /**
     *  产品简码
     */
    private String spellcode;
    /**
     * NC产品名称
     */
    private String spellname;

    /**
     * 渠道价格
     */
    private Double groupPrice;
    /**
     * 商品原价
     */
    private Double listPrice;
    /**
     * 最低售价
     */
    private Double minPrice;
    /**
     * 最高价格
     */
    private Double maxPrice;

    public String getNccode() {
        return nccode;
    }

    public void setNccode(String nccode) {
        this.nccode = nccode;
    }

    public String getNcname() {
        return ncname;
    }

    public void setNcname(String ncname) {
        this.ncname = ncname;
    }

    public String getSpellcode() {
        return spellcode;
    }

    public void setSpellcode(String spellcode) {
        this.spellcode = spellcode;
    }

    public String getSpellname() {
        return spellname;
    }

    public void setSpellname(String spellname) {
        this.spellname = spellname;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(Double groupPrice) {
        this.groupPrice = groupPrice;
    }
}
