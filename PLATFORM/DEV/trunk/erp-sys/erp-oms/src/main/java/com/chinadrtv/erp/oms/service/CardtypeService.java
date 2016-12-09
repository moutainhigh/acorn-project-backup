package com.chinadrtv.erp.oms.service;
import com.chinadrtv.erp.model.Cardtype;
import java.util.*;
public interface CardtypeService{
    Cardtype getCardtypeById(String cardtypeId);
    List getAllCardtype();
    List<String> getCreditCardNames();
    List<Cardtype> getAllCardtype(int index, int size);
    int getCardtypeCount();
    void saveCardtype(Cardtype cardtype);
    void addCardtype(Cardtype cardtype);
    void removeCardtype(Cardtype cardtype);
}
