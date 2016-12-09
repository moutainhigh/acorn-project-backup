/**
 * KeywordServiceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ctc.ctcoss.webservice.service.impl;

public class KeywordServiceImplServiceLocator extends org.apache.axis.client.Service implements com.ctc.ctcoss.webservice.service.impl.KeywordServiceImplService {

    public KeywordServiceImplServiceLocator() {
    }


    public KeywordServiceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public KeywordServiceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for KeywordServiceImplPort
    private java.lang.String KeywordServiceImplPort_address = "http://3tong.cn:8086/ds/services/getByKw";

    public java.lang.String getKeywordServiceImplPortAddress() {
        return KeywordServiceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String KeywordServiceImplPortWSDDServiceName = "KeywordServiceImplPort";

    public java.lang.String getKeywordServiceImplPortWSDDServiceName() {
        return KeywordServiceImplPortWSDDServiceName;
    }

    public void setKeywordServiceImplPortWSDDServiceName(java.lang.String name) {
        KeywordServiceImplPortWSDDServiceName = name;
    }

    public com.ctc.ctcoss.webservice.service.IKeywordService getKeywordServiceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(KeywordServiceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getKeywordServiceImplPort(endpoint);
    }

    public com.ctc.ctcoss.webservice.service.IKeywordService getKeywordServiceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ctc.ctcoss.webservice.service.impl.KeywordServiceImplServiceSoapBindingStub _stub = new com.ctc.ctcoss.webservice.service.impl.KeywordServiceImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getKeywordServiceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setKeywordServiceImplPortEndpointAddress(java.lang.String address) {
        KeywordServiceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ctc.ctcoss.webservice.service.IKeywordService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ctc.ctcoss.webservice.service.impl.KeywordServiceImplServiceSoapBindingStub _stub = new com.ctc.ctcoss.webservice.service.impl.KeywordServiceImplServiceSoapBindingStub(new java.net.URL(KeywordServiceImplPort_address), this);
                _stub.setPortName(getKeywordServiceImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("KeywordServiceImplPort".equals(inputPortName)) {
            return getKeywordServiceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://impl.service.webservice.ctcoss.ctc.com/", "KeywordServiceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://impl.service.webservice.ctcoss.ctc.com/", "KeywordServiceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("KeywordServiceImplPort".equals(portName)) {
            setKeywordServiceImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
