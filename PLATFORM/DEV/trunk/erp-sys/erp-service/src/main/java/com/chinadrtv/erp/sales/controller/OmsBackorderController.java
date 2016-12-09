package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.sales.core.util.ExceptionMsgUtil;
import com.chinadrtv.erp.service.core.dto.OmsBackorder;
import com.chinadrtv.erp.service.core.service.OmsBackorderService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping("/myorder/backorder")
public class OmsBackorderController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OmsBackorderController.class);

    @Autowired
    private OmsBackorderService omsBackorderService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryBackorders(DataGridModel dataGridModel, Integer countRows, String orderId)
    {
        Map<String, Object> pageMap = new HashMap<String, Object>();

        try
        {
            //Thread.sleep(10000);
            AgentUser agentUser = SecurityHelper.getLoginUser();
            if(agentUser==null)
            {
                logger.error("no login user");
                return pageMap;
            }


            if(isManager(agentUser))
            {
                List<GroupInfo> groupInfoList=userService.getGroupList(agentUser.getDepartment());

                List<String> grpIdList=new ArrayList<String>();
                if(groupInfoList!=null)
                {
                    for(GroupInfo groupInfo:groupInfoList)
                    {
                        grpIdList.add(groupInfo.getId());
                    }
                }
                List<OmsBackorder> omsBackorderList = omsBackorderService.queryBackordersByManager(grpIdList,orderId,dataGridModel);
                if(omsBackorderList==null)
                {
                    pageMap.put("total",0);
                    pageMap.put("rows",new ArrayList<OmsBackorder>());
                    return pageMap;
                }
                else
                {
                    if(countRows==null||countRows.intValue()<0)
                    {
                        countRows=omsBackorderService.queryBackOrdersCountByGrps(grpIdList, orderId);
                    }
                    pageMap.put("total",countRows);
                    pageMap.put("rows",omsBackorderList);
                }
            }
            else
            {
                List<OmsBackorder> omsBackorderList = omsBackorderService.queryBackordersByUsr(agentUser.getUserId(), orderId, dataGridModel);
                if(omsBackorderList==null)
                {
                    pageMap.put("total",0);
                    pageMap.put("rows",new ArrayList<OmsBackorder>());
                    return pageMap;
                }
                else
                {
                    if(countRows==null||countRows.intValue()<0)
                    {
                        countRows=omsBackorderService.queryBackOrdersCountByUsr(agentUser.getUserId(),orderId);
                    }

                    pageMap.put("total",countRows);
                    pageMap.put("rows",omsBackorderList);
                }
            }
        }
        catch (Exception exp)
        {
            logger.error("query backorder error:", exp);

            pageMap.put("total", 0);
            pageMap.put("rows", new ArrayList<OmsBackorder>());
            pageMap.put("err", ExceptionMsgUtil.getMessage(exp));
        }

        return pageMap;

    }

    private boolean isManager(AgentUser agentUser)
    {
        if(agentUser!=null)
        {
            if(agentUser.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER) | agentUser.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER))
                return true;
            if(agentUser.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER) | agentUser.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER))
                return true;
            if(agentUser.hasRole(SecurityConstants.ROLE_COMPLAIN_MANAGER))
                return true;
        }
        return false;
    }
}
