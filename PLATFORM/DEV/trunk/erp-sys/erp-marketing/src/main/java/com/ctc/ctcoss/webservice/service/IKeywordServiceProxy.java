package com.ctc.ctcoss.webservice.service;

public class IKeywordServiceProxy implements com.ctc.ctcoss.webservice.service.IKeywordService {
  private String _endpoint = null;
  private com.ctc.ctcoss.webservice.service.IKeywordService iKeywordService = null;
  
  public IKeywordServiceProxy() {
    _initIKeywordServiceProxy();
  }
  
  public IKeywordServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIKeywordServiceProxy();
  }
  
  private void _initIKeywordServiceProxy() {
    try {
      iKeywordService = (new com.ctc.ctcoss.webservice.service.impl.KeywordServiceImplServiceLocator()).getKeywordServiceImplPort();
      if (iKeywordService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iKeywordService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iKeywordService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iKeywordService != null)
      ((javax.xml.rpc.Stub)iKeywordService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.ctc.ctcoss.webservice.service.IKeywordService getIKeywordService() {
    if (iKeywordService == null)
      _initIKeywordServiceProxy();
    return iKeywordService;
  }
  
  public java.lang.String getKeywords(java.lang.String in0) throws java.rmi.RemoteException{
    if (iKeywordService == null)
      _initIKeywordServiceProxy();
    return iKeywordService.getKeywords(in0);
  }
  
  
}