package com.chinadrtv.erp.ic.test.api;

import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.ic.test.ErpIcContextTests;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-3
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class PlubasInfoControllerTests extends ErpIcContextTests {
    private static final Logger LOG = LoggerFactory.getLogger(PlubasInfoControllerTests.class);

    @Autowired
    private RestTemplate template;

    //@Test
    public void getList()
    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("nccode",  "1130306000");
        List<Map<String, Object>> infos = template.postForObject("http://localhost:8080/product/getList", params, List.class);
        Assert.assertNotNull(infos);
        for(Map<String, Object> info : infos){
            Assert.assertEquals("1130306000", info.get("nccode"));
        }
    }

    @Test
    public void getNccodeFuzzyList()
    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("nccode",  "1130306000");
        List<Map<String, Object>> infos = template.postForObject("http://localhost:8080/product/getFuzzyList", params, List.class);
        Assert.assertNotNull(infos);
        for(Map<String, Object> info : infos){
            Assert.assertEquals("1130306000", info.get("nccode"));
        }
    }

    @Test
    public void getSpellcodeFuzzyList()
    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("spellcode",  "YYWJYDT3");
        List<Map<String, Object>> infos = template.postForObject("http://localhost:8080/product/getFuzzyList", params, List.class);
        Assert.assertNotNull(infos);
        for(Map<String, Object> info : infos){
            Assert.assertEquals("YYWJYDT3", info.get("spellcode"));
        }
    }

    @Test
    public void getSpellcodeFuzzyList2()
    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("spellcode",  "YYWJY%");
        List<Map<String, Object>> infos = template.postForObject("http://localhost:8080/product/getFuzzyList", params, List.class);
        Assert.assertNotNull(infos);
        for(Map<String, Object> info : infos){
            String spellcode = (String)info.get("spellcode");
            Assert.assertNotNull(spellcode);
            Assert.assertTrue(spellcode.startsWith("YYWJY"));
        }
    }

    @Test
    public void getPlunameFuzzyList()
    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pluname",  "%可贝尔%");
        List<Map<String, Object>> infos = template.postForObject("http://localhost:8080/product/getFuzzyList", params, List.class);
        Assert.assertNotNull(infos);
    }
}
