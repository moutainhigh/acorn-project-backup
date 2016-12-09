package com.chinadrtv.erp.task.core.job;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Mapper注解
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Task {

	String name() default "";
	String description() default "";
	
}
