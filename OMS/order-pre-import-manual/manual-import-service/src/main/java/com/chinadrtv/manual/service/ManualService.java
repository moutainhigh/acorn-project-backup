package com.chinadrtv.manual.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-5
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public interface ManualService {

    //解析Excel文件
    HSSFWorkbook doProcessXLS(InputStream inputStream);
    //解析CSV文件
    void doProcessCSV();
}
