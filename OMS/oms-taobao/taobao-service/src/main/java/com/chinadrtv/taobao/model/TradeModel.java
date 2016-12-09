package com.chinadrtv.taobao.model;

import com.chinadrtv.taobao.model.TaobaoOrderConfig;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-3
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
public class TradeModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private TaobaoOrderConfig taobaoOrderConfig;
    private String startModified;
    private String endModified;
    private String status;


    public TaobaoOrderConfig getTaobaoOrderConfig() {
        return taobaoOrderConfig;
    }

    public void setTaobaoOrderConfig(TaobaoOrderConfig taobaoOrderConfig) {
        this.taobaoOrderConfig = taobaoOrderConfig;
    }

    public String getStartModified() {
        return startModified;
    }

    /**
     * yyyyMMdd HHmmss
     * @param startModified
     */
    public void setStartModified(String startModified) {
        this.startModified = startModified;
    }

    public String getEndModified() {
        return endModified;
    }

    /**
     *  yyyyMMdd HHmmss
     * @param endModified
     */
    public void setEndModified(String endModified) {
        this.endModified = endModified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
