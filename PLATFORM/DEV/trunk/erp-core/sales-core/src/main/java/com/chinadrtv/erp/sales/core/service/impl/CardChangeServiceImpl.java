package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.model.CardChange;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.sales.core.service.CardChangeService;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.service.CardService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class CardChangeServiceImpl implements CardChangeService {
    @Autowired
    private CardService cardService;

    private List<String> identityTypeList;

    @Value("${com.chinadrtv.erp.sales.core.service.CardChangeService.CardType}")
    private void initIdentityTypes(String types)
    {
        if(StringUtils.isNotEmpty(types))
        {
            identityTypeList=new ArrayList<String>();

            String[] tokens = types.split(",");
            for(String token:tokens)
            {
                if(!identityTypeList.contains(token))
                    identityTypeList.add(token);
            }
        }
    }

    @Override
    public List<CardChange> getCardChangesFromOrder(Order order) {
        List<CardChange> cardChangeList=new ArrayList<CardChange>();
        if(StringUtils.isNotEmpty(order.getCardnumber()))
        {
            Long cardId=Long.parseLong(order.getCardnumber());
            List<Card> cardList= this.getInvalidCards(cardId,order.getPaycontactid());
            if(cardList!=null&&cardList.size()>0)
            {
                for(Card card:cardList)
                {
                    CardChange cardChange = new CardChange();
                    BeanUtils.copyProperties(card, cardChange);
                    card.setState(CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                    try{
                        cardService.updateCardState(card.getCardId(),CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITING);
                        //cardService.merxge(card);
                    }catch (Exception exp)
                    {

                    }

                    cardChangeList.add(cardChange);
                }
            }
        }
        return cardChangeList;
    }

    @Override
    public boolean isOrderCardValid(Order order) {
        if(StringUtils.isNotEmpty(order.getCardnumber()))
        {
            Long cardId=Long.parseLong(order.getCardnumber());
            List<Card> cardList=this.getInvalidCards(cardId,order.getPaycontactid());
            if(cardList!=null&&cardList.size()>0)
                return false;
        }
        return true;
    }

    private List<Card> getInvalidCards(Long cardId, String contactId)
    {
        List<Card> cardList=new ArrayList<Card>();
        Card card = cardService.getCard(cardId);
        if(!this.isValid(card.getState()))
            cardList.add(card);
        //此时注意检查身份证
        if(identityTypeList!=null)
        {
            String[] types=new String[identityTypeList.size()];
            identityTypeList.toArray(types);
            List<Card> identityCardList = cardService.getCardsByContactIdIncludeCardTypes(contactId,types);
            if(identityCardList!=null)
            {
                for(Card cardIdentity:identityCardList)
                {
                    if(!this.isValid(cardIdentity.getState()))
                        cardList.add(cardIdentity);
                }
            }
        }

        return cardList;
    }

    private boolean isValid(Integer state)
    {
        if(state==null)
            return true;
        if(state.intValue() == CustomerConstant.CUSTOMER_AUDIT_STATUS_AUDITED)
        {
            return true;
        }

        return false;
    }
}
