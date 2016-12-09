package com.chinadrtv.erp.sales.controller;



import com.chinadrtv.erp.sales.dto.CtiLoginDto;
import com.chinadrtv.erp.sales.service.SourceConfigure;
import com.chinadrtv.erp.user.handle.SalesSessionRegistry;
import com.chinadrtv.erp.util.StringUtil;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * User: liuhaidong
 * Date: 12-11-20
 */
@Controller(value = "loginControl")
public class LoginController extends BaseController {
    @Autowired
    private SourceConfigure sourceConfigure;
     private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoginController.class);


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("用户登陆");
        return "login/login";
    }


    @RequestMapping(value = "/do_login", method = RequestMethod.POST)
    public String do_login(@RequestParam(required = true,defaultValue = "") String j_cti_server,
                            @RequestParam(required = false,defaultValue = "") String j_username,
                            @RequestParam(required = false,defaultValue = "") String j_password,
                            @RequestParam(required =true,defaultValue = "") String j_phoneno,
                            @RequestParam(required = false, defaultValue = "") String softphone,
                            HttpServletRequest request, HttpServletResponse response

    ) {
        boolean  v_result=false;
        String msg ="";
           if(! isNumeric(j_username)){
             msg ="用户名无效";
             v_result=true;
           }

           if(! isNumeric(j_phoneno)){
               msg="电话号码无效";
               v_result=true;
           }
           if(j_cti_server.equals("000")){
               msg="请选择cti服务器地址";
               v_result=true;
           }
        if (v_result){
            request.getSession().setAttribute("USER_LOGIN_EXCEPTION_MSG",msg);
            return "login/login";
        }



        CtiLoginDto ctiLoginInfo = new CtiLoginDto();
        ctiLoginInfo.setAgentId(j_username);
        ctiLoginInfo.setAreaCode(j_cti_server);
        ctiLoginInfo.setTelephone(j_phoneno);

        String areacode = StringUtil.nullToBlank(ctiLoginInfo.getAreaCode());


        if (ctiLoginInfo != null){

            if(areacode.equals("010")){
                ctiLoginInfo.setCtiServerIp(sourceConfigure.getCtiServerBeijing().split(":")[0]);
                ctiLoginInfo.setCtiServerPort(sourceConfigure.getCtiServerBeijing().split(":")[1]);
                ctiLoginInfo.setCtiServerIpBack(sourceConfigure.getCtiServerBeijingBack().split(":")[0]);
                ctiLoginInfo.setCtiServerPortBack(sourceConfigure.getCtiServerBeijingBack().split(":")[1]);
            }else if(areacode.equals("021")){
                ctiLoginInfo.setCtiServerIp(sourceConfigure.getCtiServerShanghai().split(":")[0]);
                ctiLoginInfo.setCtiServerPort(sourceConfigure.getCtiServerShanghai().split(":")[1]);
                ctiLoginInfo.setCtiServerIpBack(sourceConfigure.getCtiServerShanghaiBack().split(":")[0]);
                ctiLoginInfo.setCtiServerPortBack(sourceConfigure.getCtiServerShanghaiBack().split(":")[1]);
            }else if(areacode.equals("0510")){
                ctiLoginInfo.setCtiServerIp(sourceConfigure.getCtiServerWuxi().split(":")[0]);
                ctiLoginInfo.setCtiServerPort(sourceConfigure.getCtiServerWuxi().split(":")[1]);
                ctiLoginInfo.setCtiServerIpBack(sourceConfigure.getCtiServerWuxiBack().split(":")[0]);
                ctiLoginInfo.setCtiServerPortBack(sourceConfigure.getCtiServerWuxiBack().split(":")[1]);
            }
        }


        if(request.getSession().getAttribute("isLoadCtiServer") != null)  request.getSession().setAttribute("isLoadCtiServer",null);
        if(request.getSession().getAttribute("cti_webapp") != null)  request.getSession().setAttribute("cti_webapp",null);
        if(request.getSession().getAttribute("cti_agentId") != null)  request.getSession().setAttribute("cti_agentId",null);
        if(request.getSession().getAttribute("cti_dn") != null)  request.getSession().setAttribute("cti_dn",null);
        if(request.getSession().getAttribute("cti_host") != null)  request.getSession().setAttribute("cti_host",null);
        if(request.getSession().getAttribute("cti_port") != null)  request.getSession().setAttribute("cti_port",null);
        if(request.getSession().getAttribute("cti_jreVersion") != null)  request.getSession().setAttribute("cti_jreVersion",null);
        if(request.getSession().getAttribute("cti_phoneType") != null)  request.getSession().setAttribute("cti_phoneType",null);

        if(softphone.equals("2")){
            request.getSession().setAttribute("isLoadCtiServer",false);

        }else{
            logger.info("DN:"+ ctiLoginInfo.getTelephone());

            request.getSession().setAttribute("isLoadCtiServer",true);
            request.getSession().setAttribute("cti_phoneType",ctiLoginInfo.getPhoneType() ==null?"2":ctiLoginInfo.getPhoneType());
            request.getSession().setAttribute("cti_webapp",ctiLoginInfo.getWebapp());
            request.getSession().setAttribute("cti_agentId",ctiLoginInfo.getAgentId());
            request.getSession().setAttribute("cti_dn",ctiLoginInfo.getTelephone());
            request.getSession().setAttribute("cti_host",ctiLoginInfo.getCtiServerIp());
            request.getSession().setAttribute("cti_port",ctiLoginInfo.getCtiServerPort());
            request.getSession().setAttribute("cti_host_back",ctiLoginInfo.getCtiServerIpBack());
            request.getSession().setAttribute("cti_port_back",ctiLoginInfo.getCtiServerPortBack());
            request.getSession().setAttribute("cti_jreVersion",sourceConfigure.getCtiJreVersion());


        }


        return "home/index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String toLogout(HttpServletRequest request, HttpServletResponse response,@RequestParam(required = true, defaultValue = "") String name ,Model model) {

     logger.info("用户退出");
        return "login/logout";
    }




    public  boolean isNumeric(String str){
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return false;
        }
        return true;
    }


}
