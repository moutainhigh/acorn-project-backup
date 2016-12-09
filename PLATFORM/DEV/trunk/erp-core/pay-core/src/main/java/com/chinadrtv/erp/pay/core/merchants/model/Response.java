
package com.chinadrtv.erp.pay.core.merchants.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errMsg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hostErrCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="result" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resultElements" type="{http://beans.oaas.creditcard.cmbchina.com}ResultElement" maxOccurs="unbounded"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Response", namespace = "http://beans.oaas.creditcard.cmbchina.com", propOrder = {
    "errCode",
    "errMsg",
    "hostErrCode",
    "result",
    "resultElements",
    "token"
})
public class Response {
    @XmlElement(namespace = "http://beans.oaas.creditcard.cmbchina.com", required = true, nillable = true)
    protected int errCode;
    @XmlElement(namespace = "http://beans.oaas.creditcard.cmbchina.com", required = true, nillable = true)
    protected String errMsg;
    @XmlElement(namespace = "http://beans.oaas.creditcard.cmbchina.com", required = true, nillable = true)
    protected String hostErrCode;
    @XmlElement(namespace = "http://beans.oaas.creditcard.cmbchina.com", required = true, nillable = true)
    protected String result;
    @XmlElement(namespace = "http://beans.oaas.creditcard.cmbchina.com", required = true, nillable = true)
    protected List<ResultElement> resultElements;
    @XmlElement(namespace = "http://beans.oaas.creditcard.cmbchina.com", required = true, nillable = true)
    protected String token;

    /**
     * Gets the value of the errCode property.
     * 
     */
    public int getErrCode() {
        return errCode;
    }

    /**
     * Sets the value of the errCode property.
     * 
     */
    public void setErrCode(int value) {
        this.errCode = value;
    }

    /**
     * Gets the value of the errMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * Sets the value of the errMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrMsg(String value) {
        this.errMsg = value;
    }

    /**
     * Gets the value of the hostErrCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostErrCode() {
        return hostErrCode;
    }

    /**
     * Sets the value of the hostErrCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostErrCode(String value) {
        this.hostErrCode = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResult(String value) {
        this.result = value;
    }

    /**
     * Gets the value of the resultElements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resultElements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultElements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResultElement }
     * 
     * 
     */
    public List<ResultElement> getResultElements() {
        if (resultElements == null) {
            resultElements = new ArrayList<ResultElement>();
        }
        return this.resultElements;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

}
