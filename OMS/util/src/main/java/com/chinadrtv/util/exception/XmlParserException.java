package com.chinadrtv.util.exception;

public class XmlParserException extends RuntimeException {

    private static final long serialVersionUID = -7819438297028774461L;

    public XmlParserException(String message) {
        super(message);
    }

    public XmlParserException(Throwable cause) {
        super(cause);
    }

    public XmlParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
