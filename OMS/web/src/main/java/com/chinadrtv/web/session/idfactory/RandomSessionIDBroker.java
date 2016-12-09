package com.chinadrtv.web.session.idfactory;

import java.security.SecureRandom;
import java.util.Random;

//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.configuration.Configuration;

import com.chinadrtv.web.exception.PaffInitializationException;


public class RandomSessionIDBroker implements SessionIDBroker {
    private int    length;
    private Random rnd;

    /**
     *
     */
    public void init() throws PaffInitializationException {
        length = SESSION_ID_LENGTH_DEFAULT;

        try {
            rnd = new SecureRandom();
        } catch (Throwable e) {
            rnd = new Random();
        }
    }

    public String generateSessionID() {
        byte[] bytes = new byte[((length + 3) / 4) * 3];
        rnd.nextBytes(bytes);
        return new sun.misc.BASE64Encoder().encode(bytes);
    }
}
