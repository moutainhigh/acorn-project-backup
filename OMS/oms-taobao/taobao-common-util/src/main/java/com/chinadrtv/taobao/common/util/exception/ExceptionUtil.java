package com.chinadrtv.taobao.common.util.exception;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.taobao.common.facade.abs.AbstractResponse;
import com.chinadrtv.taobao.common.util.constant.CipResCodeConstants;
import com.chinadrtv.common.log.LOG_TYPE;

public class ExceptionUtil implements Serializable {

    /**VersionId  */
    private static final long serialVersionUID = 3163940210046507658L;
    /**日志*/
    //private static Logger     logger           = LoggerFactory
    //                                               .getLogger(LOG_TYPE.PAFF_SERVICE.val);

    /**
     * 用在biz层，将下层抛出的异常转换为resp返回码
     * 
     * @param e  Exception
     * @param resp  AbstractResponse
     * @return
     */
    public static AbstractResponse handlerException4biz(Exception e, AbstractResponse resp) {
        if (!(e instanceof Exception) || !(resp instanceof AbstractResponse)) {
            return null;
        }
        try {
            if (e instanceof CustDaoException) {
                ((AbstractResponse) resp).setRespCode(((CustDaoException) e).getErrorCode());
                ((AbstractResponse) resp).setMemo(((CustDaoException) e).getErrorMessage());
            } else if (e instanceof CustServiceException) {
                ((AbstractResponse) resp).setRespCode(((CustServiceException) e).getErrorCode());
                ((AbstractResponse) resp).setMemo(((CustServiceException) e).getErrorMessage());
            } else if (e instanceof CustBizException) {
                ((AbstractResponse) resp).setRespCode(((CustBizException) e).getErrorCode());
                ((AbstractResponse) resp).setMemo(((CustBizException) e).getErrorMessage());
            } else if (e instanceof RuntimeException) {
                ((AbstractResponse) resp).setRespCode(CipResCodeConstants.RUNTIME_EXCEPTION
                    .getCode());
                ((AbstractResponse) resp).setMemo(CipResCodeConstants.RUNTIME_EXCEPTION
                    .getMsg());
            } else if (e instanceof Exception) {
                ((AbstractResponse) resp).setRespCode(CipResCodeConstants.SYSTEM_ERROR.getCode());
                ((AbstractResponse) resp).setMemo(CipResCodeConstants.SYSTEM_ERROR.getMsg());
            }
            //logger.info("ExceptionUtil.handlerException4biz,Exception=" + ",respCode="
            //            + resp.getRespCode() + ",memo=" + resp.getMemo());
        } catch (Exception ex) {
            //logger.error("ExceptionUtil.handlerException处理异常,Exception=" + ex.getMessage());
        }
        return resp;
    }

    /**
     * 用在service层，将本层及下层抛出的异常做类型转换
     * 
     * @param e  Exception
     * @param resp  AbstractResponse
     * @throws Exception 
     */
    public static Exception handlerException4Service(Exception e) {
        Exception ex = null;
        try {
            if (!(e instanceof Exception)) {
                ex = new Exception();
            } else if (e instanceof CustDaoException) {
                ex = e;
            } else if (e instanceof CustServiceException) {
                ex = e;
            } else if (e instanceof CustBizException) {
                ex = e;
            } else if (e instanceof RuntimeException) {
                ex = new CustServiceException(CipResCodeConstants.RUNTIME_EXCEPTION.getCode(),
                    CipResCodeConstants.RUNTIME_EXCEPTION.getMsg());
            } else if (e instanceof Exception) {
                ex = new CustServiceException(CipResCodeConstants.SERVICE_EXCEPTION.getCode(),
                    CipResCodeConstants.SERVICE_EXCEPTION.getMsg());
            }
            //logger.info("Exception,msg=" + e.getMessage());
        } catch (Exception ek) {
            //logger.error("ExceptionUtil.handlerException4Service处理异常,Exception=" + ek.getMessage());
        }
        return ex;
    }
}
