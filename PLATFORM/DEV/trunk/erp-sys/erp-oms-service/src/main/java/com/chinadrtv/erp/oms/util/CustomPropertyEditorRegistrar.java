package com.chinadrtv.erp.oms.util;

import java.util.Date;

import com.chinadrtv.erp.oms.util.DateTypeEditor;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author jason
 * @version 1.0, 2012-10-27
 * @since sfa
 * 
 */

public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.PropertyEditorRegistrar#registerCustomEditors
	 * (org.springframework.beans.PropertyEditorRegistry)
	 */
	@InitBinder
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(Date.class, new DateTypeEditor());

	}

}
