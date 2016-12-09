/**
 * CommonServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sf.integration.expressservice.service;

public class CommonServiceServiceLocator extends org.apache.axis.client.Service implements com.sf.integration.expressservice.service.CommonServiceService {

    public CommonServiceServiceLocator() {
    }


    public CommonServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CommonServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CommonServicePort
    private java.lang.String CommonServicePort_address = "http://bsp-test.sf-express.com:9090/bsp-ois/ws/expressService";

    public java.lang.String getCommonServicePortAddress() {
        return CommonServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CommonServicePortWSDDServiceName = "CommonServicePort";

    public java.lang.String getCommonServicePortWSDDServiceName() {
        return CommonServicePortWSDDServiceName;
    }

    public void setCommonServicePortWSDDServiceName(java.lang.String name) {
        CommonServicePortWSDDServiceName = name;
    }

    public com.sf.integration.expressservice.service.IService getCommonServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CommonServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCommonServicePort(endpoint);
    }

    public com.sf.integration.expressservice.service.IService getCommonServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.sf.integration.expressservice.service.CommonServiceServiceSoapBindingStub _stub = new com.sf.integration.expressservice.service.CommonServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCommonServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCommonServicePortEndpointAddress(java.lang.String address) {
        CommonServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.sf.integration.expressservice.service.IService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.sf.integration.expressservice.service.CommonServiceServiceSoapBindingStub _stub = new com.sf.integration.expressservice.service.CommonServiceServiceSoapBindingStub(new java.net.URL(CommonServicePort_address), this);
                _stub.setPortName(getCommonServicePortWSDDServiceName());
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
        if ("CommonServicePort".equals(inputPortName)) {
            return getCommonServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.expressservice.integration.sf.com/", "CommonServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.expressservice.integration.sf.com/", "CommonServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CommonServicePort".equals(portName)) {
            setCommonServicePortEndpointAddress(address);
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
