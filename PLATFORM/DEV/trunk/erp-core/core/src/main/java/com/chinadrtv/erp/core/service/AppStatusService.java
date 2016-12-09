package com.chinadrtv.erp.core.service;

import com.chinadrtv.erp.exception.AppStatusException;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 13-3-7
 * Time: 下午1:27
 * To change this template use File | Settings | File Templates.
 */
public interface AppStatusService {

    /**
     * 检查数据库连接是否有效，实现方法中调用每个数据库连接的一个DAO方法，DAO方法只能返回1条数据
     * @return  true: 正常连接，false:连接异常
     * @throws AppStatusException  不能连接数据库的jdbc url
     */
    boolean checkDbConnection() throws AppStatusException;

    /**
     * 检查所需文件夹路径是否有效
     * @return
     * @throws AppStatusException  不能访问的文件夹路径
     */
    boolean checkFilePath() throws AppStatusException;

    /**
     * 检查所需网络连接，包括外网HTTP，FTP, SMTP
     * @return
     * @throws AppStatusException  不能访问的外网资源地址
     */
    boolean checkHttpConnection() throws AppStatusException;

}
