package com.chinadrtv.erp.sales.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinadrtv.erp.sales.service.SourceConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.chinadrtv.erp.sales.dto.CtiLoginDto;
import com.chinadrtv.erp.sales.service.TelephoneService;
import com.chinadrtv.erp.util.StringUtil;

/**
 * 
 * <dl>
 *    <dt><b>Title:获取cti登录信息</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-9-3 下午2:55:03 
 *
 */
@Controller
@RequestMapping(value = "/cti")
public class CtiInfoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CtiInfoController.class);

	@Autowired
	private TelephoneService telephoneService;

    @Autowired
    private SourceConfigure sourceConfigure;

	@RequestMapping(value = "/getCtiInfo/{agentId}", method = RequestMethod.GET)
	@ResponseBody
	public CtiLoginDto getCtiInfo(HttpServletRequest request, @PathVariable("agentId") String agentId)
			throws Exception {

		String ip = StringUtil.getIpAddr(request);
		logger.info("ip is Login:"+ip);
		CtiLoginDto ctiLoginInfo = telephoneService.queryCtiLoginInfo(agentId, ip);

		return ctiLoginInfo;
	}


    @RequestMapping(value = "/saveCtiInfo", method = RequestMethod.POST)
    public void saveCtiInfo(
                            @RequestParam(required = false, defaultValue = "") String agentId,
                            @RequestParam(required = false, defaultValue = "") String areacode,
                            @RequestParam(required = false, defaultValue = "") String softphone,
                            HttpServletRequest request, HttpServletResponse response
                            )
            throws Exception {

        String ip = StringUtil.getIpAddr(request);
        logger.info("ip is Login:"+ip);
        CtiLoginDto ctiLoginInfo = telephoneService.queryCtiLoginInfo(agentId, ip);

        areacode = StringUtil.nullToBlank(ctiLoginInfo.getAreaCode());


        if (ctiLoginInfo != null){
            ctiLoginInfo.setAgentId(agentId);
            ctiLoginInfo.setWebapp(sourceConfigure.getCtiServerWebapp());
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



    }
}