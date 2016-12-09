/**
 * CommonServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sf.integration.expressservice.service;

public interface CommonServiceService extends javax.xml.rpc.Service {
    public java.lang.String getCommonServicePortAddress();

    public com.sf.integration.expressservice.service.IService getCommonServicePort() throws javax.xml.rpc.ServiceException;

    public com.sf.integration.expressservice.service.IService getCommonServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
