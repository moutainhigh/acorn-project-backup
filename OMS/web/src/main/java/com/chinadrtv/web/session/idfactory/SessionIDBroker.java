package com.chinadrtv.web.session.idfactory;

//import org.apache.commons.configuration.Configuration;



public interface SessionIDBroker {
    String SESSION_ID_LENGTH         = "length";
    int    SESSION_ID_LENGTH_DEFAULT = 32;
   
    /**
     *
     */
    void init();

    /**
     *
     */
    String generateSessionID();
}
