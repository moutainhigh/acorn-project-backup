package com.chinadrtv.erp.core.controller.impl;

import com.chinadrtv.erp.core.controller.GenericController;
import com.chinadrtv.erp.core.controller.Util.Constants;
import com.chinadrtv.erp.core.controller.Util.RequestUtil;
import com.chinadrtv.erp.core.dao.query.Criteria;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.model.client.EntityGridView;
import com.chinadrtv.erp.core.model.client.EntityView;
import com.chinadrtv.erp.core.service.EntityViewService;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.core.ui.GridView;
import com.chinadrtv.erp.core.ui.jquery.GridViewImpl;
import com.chinadrtv.erp.exception.PageException;
import com.chinadrtv.erp.util.JsonBinder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: liuhaidong
 * Date: 12-11-12
 */
public abstract class GenericControllerImpl<T, PK extends Serializable> implements GenericController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GenericControllerImpl.class);

    @Autowired
    private EntityViewService entityViewService;

    protected static JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

    protected abstract GenericService<T,PK> getGenericService();

    protected abstract String getEntityViewName();

    public ModelAndView doQueryModel(HttpServletRequest request,
                                   HttpServletResponse response) throws JSONException, IOException {
        EntityView entityView = null;
        entityView = getEntityView();
        Map<String, Parameter > parameters = RequestUtil.getQueryParameters(request,entityView.getSearch());
        Map<String, Criteria > criterias = RequestUtil.getQueryCriterias(request,entityView.getSearch());

        List<T> auditLogList = getGenericService().findPageList(entityView,parameters,criterias);
        int totalRecords = getGenericService().findPageCount(entityView, parameters, criterias);
        response.setContentType("text/json; charset=UTF-8");
        String jsonData = "{\"rows\":" +jsonBinder.toJson(auditLogList) + ",\"total\":" + totalRecords + " }";
        response.getWriter().print(jsonData);
        response.setHeader("Cache-Control", "no-cache");
        return null;
    }
    protected ModelAndView doInitPage(HttpServletRequest request,
                         HttpServletResponse response) throws JSONException, IOException {
        EntityGridView entityView = (EntityGridView)this.getEntityView();
        GridView gridView = new GridViewImpl(entityView);
        JSONObject jsonData = new JSONObject();
        jsonData.put("initScript",gridView.getJs());
        response.setContentType("text/json; charset=UTF-8");
        response.getWriter().print(jsonData.toString());
        response.setHeader("Cache-Control", "no-cache");
        return null;
    }
    protected EntityView getEntityView() {
        EntityView entityView;
        entityView = entityViewService.findByName(getEntityViewName());
        return entityView;
    }

    public ModelAndView doStartView(HttpServletRequest request){
        EntityView entityView = getEntityView();
        return new ModelAndView(entityView.getViewUrl());
    }

    public ModelAndView doSaveModel(T model,HttpServletRequest request) throws JSONException {
        getGenericService().save(model);
        return null;
    }

    public ModelAndView doDelModel(@RequestParam(required = true) PK id, HttpServletRequest request) throws JSONException {
        getGenericService().delete(id);
        return null;
    }

    private ModelAndView errorModelAndView(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exception/handling");
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }

    @ExceptionHandler({PageException.class})
    public ModelAndView handleExceptionArray(Exception ex,HttpServletRequest request) {
        logger.info("Uncaught exception: " + ex.getClass().getSimpleName());
        logger.info("exception: " + ex.getMessage());
        return errorModelAndView(ex);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


}
