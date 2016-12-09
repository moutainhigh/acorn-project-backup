package com.chinadrtv.erp.core.controller;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * User: liuhaidong
 * Date: 12-11-12
 */
public interface GenericController<T, PK extends Serializable> {
    public ModelAndView startView(HttpServletRequest request);

    public ModelAndView queryModel(HttpServletRequest request,
                                   HttpServletResponse response) throws JSONException, IOException;

    public ModelAndView saveModel(T model, HttpServletRequest request) throws JSONException;

    public ModelAndView delModel(@RequestParam(required = true) Long id, HttpServletRequest request) throws JSONException;
}
