package com.chinadrtv.util.test;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("configCenters")
public class ConfigCenters {
   // @XStreamOmitField
    private String       responseCode;
    @XStreamImplicit(itemFieldName = "config")
    private List<Config> configs;
    

    /**
     * Getter method for property <tt>responseCode</tt>.
     * 
     * @return property value of responseCode
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * Setter method for property <tt>responseCode</tt>.
     * 
     * @param responseCode value to be assigned to property responseCode
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Getter method for property <tt>configCenters</tt>.
     * 
     * @return property value of configCenters
     */
    public List<Config> getConfigCenters() {
        return configs;
    }

    /**
     * Setter method for property <tt>configCenters</tt>.
     * 
     * @param configCenters value to be assigned to property configCenters
     */
    public void setConfigCenters(List<Config> configs) {
        this.configs = configs;
    }

}
