package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.service.OrderRuleCheckService;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.uc.service.TransferBlackListService;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("BlackListRuleCheckServiceImpl")
public class BlackListRuleCheckServiceImpl implements OrderRuleCheckService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BlackListRuleCheckServiceImpl.class);
    @Autowired
    private TransferBlackListService transferBlackListService;

    @Value("${com.chinadrtv.erp.sales.NCODPaytypes}")
    private void initNCODPaytypes(String types)
    {
        ncodPaytypeList=new ArrayList<String>();
        String[] values=types.split(",");
        if(values!=null)
        {
            for(String item:values)
            {
                if(!ncodPaytypeList.contains(item))
                {
                    ncodPaytypeList.add(item);
                }
            }
        }
    }

    private List<String> ncodPaytypeList;

    @Autowired
    private UserService userService;

    @Override
    public boolean checkOrder(Order order) {
        //检查是否主管，如果是的，那么不需要
        if(ncodPaytypeList.contains(order.getPaytype()))
            return true;

        try{
            List<AcornRole> roleList = userService.getRoleList(order.getCrusr());
            for (AcornRole acornRole : roleList) {
                if (acornRole.getName().equalsIgnoreCase(SecurityConstants.ROLE_OUTBOUND_MANAGER)
                        || acornRole.getName().equalsIgnoreCase(SecurityConstants.ROLE_INBOUND_MANAGER)
                        || acornRole.getName().equalsIgnoreCase(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER)
                        || acornRole.getName().equalsIgnoreCase(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER)) {
                    return true;
                }
            }
        }catch (Exception exp)
        {
            logger.error("",exp);
        }

        return !transferBlackListService.checkContactBlackList(order.getContactid());
    }

    @Override
    public String getRuleName() {
        return "contact black list";
    }

    @Override
    public String getBPMType() {
        return String.valueOf(UserBpmTaskType.CONTACT_BASE_CHANGE.getIndex());//目前走客户修改流程
    }
}
