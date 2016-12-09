package com.chinadrtv.erp.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * User: liuhaidong
 * Date: 12-12-20
 */
public class ModelValidator {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    /**
     * Returns the validation result.  If result.length is >0 ,the validation is failed;
     * otherwise it is success
     *
     * @return the validation message separated by "; "
     */
    public static String validate(Object ob) {
        StringBuilder s = new StringBuilder();
        Set constraintViolations = (Set) validator.validate(ob);
        if (constraintViolations.size() > 0) {

            for (Object cvObj : constraintViolations) {
                ConstraintViolation constraintViolation = (ConstraintViolation) cvObj;
                s.append(constraintViolation.getMessage() + "; ");
            }
        }
        return s.toString();
    }
}
