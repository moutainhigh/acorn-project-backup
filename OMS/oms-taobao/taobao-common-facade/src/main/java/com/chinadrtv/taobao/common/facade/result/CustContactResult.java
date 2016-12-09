package com.chinadrtv.taobao.common.facade.result;

import java.util.List;

import com.chinadrtv.taobao.common.facade.abs.AbstractResponse;
import com.chinadrtv.taobao.common.facade.bean.contact.CustomerContactRes;

/**
 *  联系人结果集
 * @author panyan
 * @version $Id: CustBaseResponse.java, v 0.1 2013-7-30 上午10:11:01 panyan Exp $
 */
public class CustContactResult extends AbstractResponse {

    /** versionId */
    private static final long serialVersionUID = 6385554695280222106L;

    /**返回的结果对象*/
    private List<CustomerContactRes> contactRes;

    /**
     * @param string
     * @param object2
     */
    public CustContactResult( List<CustomerContactRes>  contactRes) {
        this.contactRes = contactRes;
    }
    
    public CustContactResult(){
        
    }
    
    /**
     * Getter method for property <tt>contactRes</tt>.
     * 
     * @return property value of contactRes
     */
    public List<CustomerContactRes> getContactRes() {
        return contactRes;
    }

    /**
     * Setter method for property <tt>contactRes</tt>.
     * 
     * @param contactRes value to be assigned to property contactRes
     */
    public void setContactRes(List<CustomerContactRes> contactRes) {
        this.contactRes = contactRes;
    }
}
