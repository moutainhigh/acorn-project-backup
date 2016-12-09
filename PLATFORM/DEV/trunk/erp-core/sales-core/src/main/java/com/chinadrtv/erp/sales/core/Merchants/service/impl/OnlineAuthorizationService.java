
package com.chinadrtv.erp.sales.core.Merchants.service.impl;

import com.chinadrtv.erp.sales.core.Merchants.service.OnlineAuthorization;

import java.net.*;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "OnlineAuthorizationService", targetNamespace = "http://oaas.creditcard.cmbchina.com")
public class OnlineAuthorizationService
    extends Service
{
    public OnlineAuthorizationService(URL wsdlLocation) {
        super(wsdlLocation, new QName("http://oaas.creditcard.cmbchina.com", "OnlineAuthorizationService"));
    }

    /*public OnlineAuthorizationService() {
        super(ONLINEAUTHORIZATIONSERVICE_WSDL_LOCATION, new QName("http://oaas.creditcard.cmbchina.com", "OnlineAuthorizationService"));
    }*/

    /**
     * 
     * @return
     *     returns OnlineAuthorization
     */
    @WebEndpoint(name = "OnlineAuthorization")
    public OnlineAuthorization getOnlineAuthorization() {
        return super.getPort(new QName("http://oaas.creditcard.cmbchina.com", "OnlineAuthorization"), OnlineAuthorization.class);
    }

}
