package com.chinadrtv.amazon.common.facade.abs;

import java.io.Serializable;

public abstract class AbstractRequest implements Serializable {

    /**  */
    private static final long serialVersionUID = -2077559673137218189L;
    
    private String            channel;                                //渠道(D-移动终端 W-web)
    private String            version;                                //版本号
    /**
     * Getter method for property <tt>channel</tt>.
     * 
     * @return property value of channel
     */
    public String getChannel() {
        return channel;
    }
    /**
     * Setter method for property <tt>channel</tt>.
     * 
     * @param channel value to be assigned to property channel
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }
    /**
     * Getter method for property <tt>version</tt>.
     * 
     * @return property value of version
     */
    public String getVersion() {
        return version;
    }
    /**
     * Setter method for property <tt>version</tt>.
     * 
     * @param version value to be assigned to property version
     */
    public void setVersion(String version) {
        this.version = version;
    }

}
