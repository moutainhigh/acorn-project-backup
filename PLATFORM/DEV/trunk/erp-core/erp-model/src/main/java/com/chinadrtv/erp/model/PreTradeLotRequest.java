package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * User: liuhaidong
 * Date: 12-12-14
 */
@Entity
@Table(name = "PRE_TRADE_LOT_REQUEST", schema = "ACOAPP_OMS")
public class PreTradeLotRequest implements java.io.Serializable {

    private Long id;

    //@see com.chinadrtv.erp.constant.TradeSourceConstants
    private Long sourceId;

    /**
     * The request parameters
     */
    private String parameter;

    /**
     * The request url address
     */
    private String urlAddress;

    /**
     * The request is success or not
     */
    private boolean isSuccess;


    /**
     * if the request is failed, isSuccess=false, the errMsg is the error response message
     */
    private String errMsg;

    /**
     * file name, if The request has a file
     */
    private String fileName;

    /**
     * file path in server, if The request has a file
     */
    private String filePath;


    /**
     * the date time of request
     */
    private Date createDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_TRADE_LOT_REQUEST_SEQ")
    @SequenceGenerator(name = "PRE_TRADE_LOT_REQUEST_SEQ", sequenceName = "ACOAPP_OMS.PRE_TRADE_LOT_REQUEST_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SOURCE_ID")
    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "PARAMETER")
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Column(name = "URL_ADDRESS")
    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    @Column(name = "IS_SUCCESS")
    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean success) {
        isSuccess = success;
    }

    @Column(name = "FILE_NAME")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "FILE_PATH")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", length = 7)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "ERR_MSG", length = 2000)
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
