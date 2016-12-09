package com.chinadrtv.model.oms;

public class CountyMapping {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.COUNTY_MAPPING.COUNTYID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private Integer countyid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.COUNTY_MAPPING.COUNTYNAME
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private String countyname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.COUNTY_MAPPING.MAPPING_COUNTYID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private Integer mappingCountyid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACOAPP_OMS.COUNTY_MAPPING.REMARK
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.COUNTY_MAPPING.COUNTYID
     *
     * @return the value of ACOAPP_OMS.COUNTY_MAPPING.COUNTYID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public Integer getCountyid() {
        return countyid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.COUNTY_MAPPING.COUNTYID
     *
     * @param countyid the value for ACOAPP_OMS.COUNTY_MAPPING.COUNTYID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setCountyid(Integer countyid) {
        this.countyid = countyid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.COUNTY_MAPPING.COUNTYNAME
     *
     * @return the value of ACOAPP_OMS.COUNTY_MAPPING.COUNTYNAME
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public String getCountyname() {
        return countyname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.COUNTY_MAPPING.COUNTYNAME
     *
     * @param countyname the value for ACOAPP_OMS.COUNTY_MAPPING.COUNTYNAME
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setCountyname(String countyname) {
        this.countyname = countyname == null ? null : countyname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.COUNTY_MAPPING.MAPPING_COUNTYID
     *
     * @return the value of ACOAPP_OMS.COUNTY_MAPPING.MAPPING_COUNTYID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public Integer getMappingCountyid() {
        return mappingCountyid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.COUNTY_MAPPING.MAPPING_COUNTYID
     *
     * @param mappingCountyid the value for ACOAPP_OMS.COUNTY_MAPPING.MAPPING_COUNTYID
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setMappingCountyid(Integer mappingCountyid) {
        this.mappingCountyid = mappingCountyid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACOAPP_OMS.COUNTY_MAPPING.REMARK
     *
     * @return the value of ACOAPP_OMS.COUNTY_MAPPING.REMARK
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACOAPP_OMS.COUNTY_MAPPING.REMARK
     *
     * @param remark the value for ACOAPP_OMS.COUNTY_MAPPING.REMARK
     *
     * @mbggenerated Wed Oct 30 13:34:08 CST 2013
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}