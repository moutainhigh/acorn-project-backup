package com.chinadrtv.erp.core.validate;

import org.hibernate.event.PreInsertEvent;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * User: liuhaidong
 * Date: 12-9-25
 */
public class ValidListener implements PreUpdateEventListener, PreInsertEventListener {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ValidListener.class);

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public boolean onPreUpdate(PreUpdateEvent preUpdateEvent) {
        Object ob = preUpdateEvent.getEntity();

        validateObject(ob);
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void validateObject(Object ob) {
        Set constraintViolations = (Set) validator.validate(ob);
        if (constraintViolations.size() > 0) {
            StringBuilder s = new StringBuilder();
            for (Object cvObj : constraintViolations) {
                ConstraintViolation constraintViolation = (ConstraintViolation) cvObj;
                s.append(constraintViolation.getMessage() + "; ");
            }
            log.error("Error: " + ob.getClass().toString() + s);
            throw new javax.validation.ValidationException(s.toString());
        }
    }

    public boolean onPreInsert(PreInsertEvent preInsertEvent) {
        Object ob = preInsertEvent.getEntity();

        validateObject(ob);
        return false;
    }
}
