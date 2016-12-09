package com.chinadrtv.util.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.chinadrtv.util.XmlUtil;


public class XmlUtilTest {
    @Test
    public void testXmlUtil() {
        Config c1 = new Config();
        c1.setConfigKey("logFormat");
        c1.setConfigValue("1");
        c1.setStatus("N");

        Config c2 = new Config();
        c2.setConfigKey("logFormat");
        c2.setConfigValue("1");
        c2.setStatus("N");
        Config c3 = new Config();
        c3.setConfigKey("logFormat");
        c3.setConfigValue("1");
        c3.setStatus("N");

        ConfigCenters configs = new ConfigCenters();

        List<Config> configCenters = new ArrayList<Config>();
        configCenters.add(c3);
        configCenters.add(c2);

        configCenters.add(c1);
        configs.setConfigCenters(configCenters);
        configs.setResponseCode("100");
        String xml = XmlUtil.toXmlByAnnotation(configs);
        ConfigCenters con = XmlUtil.toObjectByAnnotation(xml, ConfigCenters.class);
        Assert.assertEquals(configs.getResponseCode(), con.getResponseCode());
        Assert.assertEquals(configs.getConfigCenters().size(), con.getConfigCenters().size());
        for (int i = 0; i < configs.getConfigCenters().size(); i++) {
            Assert.assertEquals(configs.getConfigCenters().get(i).getConfigKey(), con
                .getConfigCenters().get(i).getConfigKey());
            Assert.assertEquals(configs.getConfigCenters().get(i).getConfigValue(), con
                .getConfigCenters().get(i).getConfigValue());
            Assert.assertEquals(configs.getConfigCenters().get(i).getStatus(), con
                .getConfigCenters().get(i).getStatus());
        }

    }

}
