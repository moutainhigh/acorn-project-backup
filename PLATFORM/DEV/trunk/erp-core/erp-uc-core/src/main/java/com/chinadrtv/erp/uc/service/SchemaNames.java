package com.chinadrtv.erp.uc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA. User: xuzk Date: 13-1-31
 */
@Component
public class SchemaNames {

	@Value("${agentSchema}")
	private String agentSchema;

    @Value("${crmmarketingSchema}")
    private String crmmarketingSchema;

	public String getAgentSchema() {
        if (agentSchema == null || "".equals(agentSchema)) {
            return "";
        } else {
            return agentSchema + ".";
        }
    }

    public String getCrmmarketingSchema() {
        if (crmmarketingSchema == null || "".equals(crmmarketingSchema)) {
            return "";
        } else {
            return crmmarketingSchema + ".";
        }
    }
}
