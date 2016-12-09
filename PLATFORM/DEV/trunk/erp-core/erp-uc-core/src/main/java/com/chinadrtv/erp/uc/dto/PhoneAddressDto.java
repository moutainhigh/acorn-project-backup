package com.chinadrtv.erp.uc.dto;

/**
 * Created with IntelliJ IDEA.
 * Title: PhoneAddressDto
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class PhoneAddressDto implements java.io.Serializable {
    private String phn1;
    private String phn2;
    private String phn3;
    private String ani;
    private String phonetypid;
    private String phoneNum;
    private String provid;
    private Integer cityid;
    private String provName;
    private String cityName;

    public PhoneAddressDto() {
    }

    public String getPhn1() {
        return phn1;
    }

    public void setPhn1(String phn1) {
        this.phn1 = phn1;
    }

    public String getPhn2() {
        return phn2;
    }

    public void setPhn2(String phn2) {
        this.phn2 = phn2;
    }

    public String getPhn3() {
        return phn3;
    }

    public void setPhn3(String phn3) {
        this.phn3 = phn3;
    }

    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    public String getPhonetypid() {
        return phonetypid;
    }

    public void setPhonetypid(String phonetypid) {
        this.phonetypid = phonetypid;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getProvid() {
        return provid;
    }

    public void setProvid(String provid) {
        this.provid = provid;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
