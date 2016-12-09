package com.chinadrtv.companymail.service;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-22
 * Time: 上午10:07
 * To change this template use File | Settings | File Templates.
 * 承运商邮件发送数据模板
 */
public interface GenerateExcelService {
    //模板
    public byte[] createExcelTemplate(Integer internalLoadNum, Integer mailType, String company) throws Exception;
}
