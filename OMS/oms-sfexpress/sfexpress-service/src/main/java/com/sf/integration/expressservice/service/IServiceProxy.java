package com.sf.integration.expressservice.service;

public class IServiceProxy implements com.sf.integration.expressservice.service.IService {
  private String _endpoint = null;
  private com.sf.integration.expressservice.service.IService iService = null;
  
  public IServiceProxy() {
    _initIServiceProxy();
  }
  
  public IServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIServiceProxy();
  }
  
  private void _initIServiceProxy() {
    try {
      iService = (new com.sf.integration.expressservice.service.CommonServiceServiceLocator()).getCommonServicePort();
      if (iService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iService != null)
      ((javax.xml.rpc.Stub)iService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.sf.integration.expressservice.service.IService getIService() {
    if (iService == null)
      _initIServiceProxy();
    return iService;
  }
  
  public java.lang.String sfexpressService(java.lang.String arg0) throws java.rmi.RemoteException{
    if (iService == null)
      _initIServiceProxy();
    return iService.sfexpressService(arg0);
  }
  
  
}