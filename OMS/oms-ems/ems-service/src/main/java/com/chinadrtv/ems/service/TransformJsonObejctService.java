package com.chinadrtv.ems.service;


import java.util.List;

import com.chinadrtv.erp.orderfeedback.model.MailStatusHis;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-10-28
 * Time: 下午12:51
 * To change this template use File | Settings | File Templates.
 */
public interface TransformJsonObejctService {
    public List<MailStatusHis> transXmlToList(String strJson) throws Exception;

    public String checkAuthenticateAndVersion(String authenticate,String version);
}
