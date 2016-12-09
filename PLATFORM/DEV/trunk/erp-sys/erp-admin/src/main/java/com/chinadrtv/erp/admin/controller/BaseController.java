/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.chinadrtv.erp.admin.controller;

import com.chinadrtv.erp.exception.PageException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * User: liuhaidong
 * Date: 12-7-24
 */
@Controller
public abstract class BaseController {
    private static final Logger logger = Logger.getLogger(BaseController.class.getName());

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


}
