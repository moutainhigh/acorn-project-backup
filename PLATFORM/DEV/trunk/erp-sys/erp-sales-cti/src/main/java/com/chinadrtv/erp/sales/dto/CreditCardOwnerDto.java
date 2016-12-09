package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 联系人信用卡列表
 * User: gaodejian
 * Date: 13-6-19
 * Time: 上午11:38
 * To change this template use File | Settings | File Templates.
 */
public class CreditCardOwnerDto implements Serializable {
    private String contactId;
    private String contactName;
    private List<CreditCardDto> cards;

    public CreditCardOwnerDto(String contactId, String contactName, List<CreditCardDto> cards){
        this.contactId = contactId;
        this.contactName = contactName;
        this.cards = cards;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public List<CreditCardDto> getCards() {
        return cards;
    }

    public void setCards(List<CreditCardDto> cards) {
        this.cards = cards;
    }


}
