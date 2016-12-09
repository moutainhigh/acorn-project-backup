package com.chinadrtv.erp.core.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * User: liuhaidong
 * Date: 12-9-24
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = NoXssValidator.class)
@Documented
public @interface NoXss {
    String message() default "{validation.NoXss.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
