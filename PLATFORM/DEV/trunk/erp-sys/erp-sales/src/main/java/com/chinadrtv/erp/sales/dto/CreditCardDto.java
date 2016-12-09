package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.model.Amortisation;
import com.chinadrtv.erp.model.Cardtype;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.agent.Card;
import org.apache.commons.lang.StringUtils;
import org.compass.core.mapping.ContractMapping;

import java.io.Serializable;
import java.util.List;

/**
 * 信用卡类型
 * User: gaodejian
 * Date: 13-6-6
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class CreditCardDto implements Serializable {

    private Contact contact;
    private Card card;
    private Cardtype cardtype;
    private List<Amortisation> amortisations;
    private Integer lastStatus;

    public CreditCardDto(Contact contact, Card card, Cardtype cardtype, List<Amortisation> amortisations) {
        super();
        this.contact = contact;
        this.card = card;
        this.cardtype = cardtype;
        this.amortisations = amortisations;
        if(!amortisations.isEmpty()) {
            lastStatus = amortisations.get(0).getAmortisation();
        }
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Cardtype getCardtype() {
        return cardtype;
    }

    public void setCardtype(Cardtype cardtype) {
        this.cardtype = cardtype;
    }

    public List<Amortisation> getAmortisations() {
        return amortisations;
    }

    public void setAmortisations(List<Amortisation> amortisations) {
        this.amortisations = amortisations;
    }

    public Long getCardId() {
        return card.getCardId();
    }

    public void setCardId(Long cardId) {
        this.card.setCardId(cardId);
    }

    public String getType() {
        return card.getType();
    }

    public void setType(String type) {
        this.card.setType(type);
    }

    public String getTypeName() {
        return cardtype.getCardname();
    }

    public void setTypeName(String typeName) {
        this.cardtype.setCardname(typeName);
    }

    public String getBankCode() {
        return cardtype.getBankCode();
    }

    public void setBankCode(String bankCode) {
        this.cardtype.setBankCode(bankCode);
    }

    public String getBankName() {
        return cardtype.getBankName();
    }

    public void setBankName(String bankName) {
        this.cardtype.setBankName(bankName);
    }

    public String getShowextracode() {
        return cardtype.getShowextracode();
    }

    public void setShowextracode(String showextracode) {
        this.cardtype.setShowextracode(showextracode);
    }

    public String getContactId() {
        return card.getContactId();
    }

    public void setContactId(String contactId) {
        this.card.setContactId(contactId);
    }

    public String getContactName() {
        return contact.getName();
    }

    public void setContactName(String contactId) {
        this.contact.setName(contactId);
    }

    public String getCardNumber(){
        String cardNumber = card.getCardNumber();
        if(cardNumber != null){
            if(cardNumber.length() > 8){
                String prefix = cardNumber.substring(0, 4);
                String suffix = cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
                cardNumber = StringUtils.rightPad(prefix, cardNumber.length() - 8, "*") +suffix;
            } else if(cardNumber.length() > 4) {
                String prefix = cardNumber.substring(0, 4);
                cardNumber = StringUtils.rightPad(prefix, cardNumber.length() - 4, "*");
            }
        }
        return cardNumber;
    }

    public void setCardNumber(String cardNumber){
        this.card.setCardNumber(cardNumber);
    }

    public String getExtraCode() {
        String extraCode =  card.getExtraCode();
        if(extraCode != null && extraCode.length() > 1){
            String prefix = extraCode.substring(0, 1);
            extraCode = StringUtils.rightPad(prefix, extraCode.length(), "*");
        }
        return extraCode;
    }

    public void setExtraCode(String extraCode) {
        this.card.setExtraCode(extraCode);
    }

    public String getValidDate(){
        return card.getValidDate();
    }

    public void setValidDate(String validDate){
        this.card.setValidDate(validDate);
    }

    public Integer getLastStatus(){
        return lastStatus;
    }

    public void setValidDate(Integer lastStatus){
        this.lastStatus = lastStatus;
    }


    public Integer getState() {
        return card.getState();
    }

    public void setState(Integer state) {
        card.setState(state);
    }
}
