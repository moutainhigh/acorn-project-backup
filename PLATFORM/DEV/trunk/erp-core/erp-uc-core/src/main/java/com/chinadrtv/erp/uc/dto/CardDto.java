package com.chinadrtv.erp.uc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.model.Amortisation;
import com.chinadrtv.erp.model.agent.Card;

/**
 * Created with IntelliJ IDEA.
 * Title: CardDto
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class CardDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3638092634958186900L;
	
	private Card card; // delegate
	
	private String contactName;
	
	private String cardtypeName;
	
	private String bankName;
	
	private String showextracode;

    private List<Amortisation> amortisations;

    private String cardNumber;

    public CardDto() {
	}
    
    public CardDto(Card card) {
		this.card = card;
	}

	public Long getCardId() {
		return card.getCardId();
	}

	public void setCardId(Long cardId) {
		card.setCardId(cardId);
	}

	public String getContactId() {
		return card.getContactId();
	}

	public void setContactId(String contactId) {
		card.setContactId(contactId);
	}

	public String getEntityId() {
		return card.getEntityId();
	}

	public void setEntityId(String entityId) {
		card.setEntityId(entityId);
	}

	public String getType() {
		return card.getType();
	}

	public void setType(String type) {
		card.setType(type);
	}

	public String getCardNumber() {
		return card.getCardNumber();
	}

	public void setCardNumber(String cardNumber) {
		card.setCardNumber(cardNumber);
	}

	public String getValidDate() {
		return card.getValidDate();
	}

	public void setValidDate(String validDate) {
		card.setValidDate(validDate);
	}

	public String getChecked() {
		return card.getChecked();
	}

	public void setChecked(String checked) {
		card.setChecked(checked);
	}

	public String getExtraCode() {
		return card.getExtraCode();
	}

	public void setExtraCode(String extraCode) {
		card.setExtraCode(extraCode);
	}

	public String getLimit() {
		return card.getLimit();
	}

	public void setLimit(String limit) {
		card.setLimit(limit);
	}

	public Long getLastLockSeqid() {
		return card.getLastLockSeqid();
	}

	public void setLastLockSeqid(Long lastLockSeqid) {
		card.setLastLockSeqid(lastLockSeqid);
	}

	public Long getLastUpdateSeqid() {
		return card.getLastUpdateSeqid();
	}

	public void setLastUpdateSeqid(Long lastUpdateSeqid) {
		card.setLastUpdateSeqid(lastUpdateSeqid);
	}

	public Date getLastUpdateTime() {
		return card.getLastUpdateTime();
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		card.setLastUpdateTime(lastUpdateTime);
	}

	public String getCardtypeName() {
        return cardtypeName;
    }

    public void setCardtypeName(String cardtypeName) {
        this.cardtypeName = cardtypeName;
    }

	public List<Amortisation> getAmortisations() {
		return amortisations;
	}

	public void setAmortisations(List<Amortisation> amortisations) {
		this.amortisations = amortisations;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getShowextracode() {
		return showextracode;
	}

	public void setShowextracode(String showextracode) {
		this.showextracode = showextracode;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * @param card the card to set
	 */
	public void setCard(Card card) {
		this.card = card;
	}

    public Integer getState() {
        return card.getState();
    }
}
