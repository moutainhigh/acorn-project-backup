package com.chinadrtv.erp.marketing.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.bpm.service.BpmProcessService;

/**
 * 手工挑单处理
 * <p/>
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 12-12-20
 * Time: 上午11:34
 */

@Controller
@RequestMapping("/")
public class IndexController {


	@Autowired
	private BpmProcessService bpmProcessService;
	
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView main(@RequestParam(required = false, defaultValue = "") String from, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
    	
//    	String processInstanceId = bpmProcessService.startProcessInstance("escalationExample");
//    	
//    	System.out.println("启动流程实例Id="+processInstanceId);
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    
    public static void main(String arg[]){
    	
//    	String cmd = "sqlldr crmmarketing/crm.2013@testdb control='d:/123.ctl' log='d:/input.log'";
//        try {
//           Process ldr = Runtime.getRuntime().exec(cmd);
//           InputStream stderr = ldr.getInputStream();
//           InputStreamReader isr = new InputStreamReader(stderr);
//           BufferedReader br = new BufferedReader(isr);
//           String line = null;
//           while ((line = br.readLine()) != null)
//              System.out.println("*** " + line);
//           stderr.close();
//           isr.close();
//           br.close();
//           try {
//              ldr.waitFor();
//           } catch (Exception e) {
//              System.out.println("process function:loader wait for != 0");
//           }
//        } catch (Exception ex) {
//           System.out.println("process function:loader execute exception"
//                  + ex.toString());
//        }
    	
    	System.out.println(String.format("%1$03d", 35));
    	System.out.println(String.format("%1$06d", 1024));
    	System.out.println(String.format("%1$09d", 10001099));  
     }

    
    
}