package com.chinadrtv.erp.tc.core.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-31
 */
@Component("com.chinadrtv.erp.tc.core.constant.SchemaNames")
public class SchemaNames {

    @Value("${agentSchema}")
    private String agentSchema;

    public String getAgentSchema()
    {
        if(agentSchema==null||"".equals(agentSchema))
        {
            return "";
        }
        else
        {
            return agentSchema + ".";
        }
    }
}
