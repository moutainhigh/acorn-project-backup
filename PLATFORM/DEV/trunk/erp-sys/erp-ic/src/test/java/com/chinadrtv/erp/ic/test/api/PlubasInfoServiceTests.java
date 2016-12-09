package com.chinadrtv.erp.ic.test.api;

import com.chinadrtv.erp.ic.model.NcPlubasInfoAttribute;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.ic.test.ErpIcContextTests;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-3
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
public class PlubasInfoServiceTests extends ErpIcContextTests {

    @Autowired
    private PlubasInfoService plubasInfoService;


    @Test
    public void getExactList()
    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("grpid",  "outdialb24");
        List<PlubasInfo> infos = plubasInfoService.getExactPlubasInfosByNC(params, 1, 100);
        Assert.assertNotNull(infos);

    }

    @Test
    public void getExactList2()
    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("grpid",  "outdialb24");
        params.put("nccode",  "1060107210");
        List<PlubasInfo> infos = plubasInfoService.getExactPlubasInfosByNC(params, 1, 20);
        Assert.assertNotNull(infos);
        for(PlubasInfo info : infos){
            Assert.assertEquals("1060107210", info.getNccode());
        }
    }

    @Test
    public void getNccodeFuzzyCount()
    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("grpid",  "outdialb24");
        params.put("nccode",  "1060107210");
        Long count = plubasInfoService.getPlubasInfoCountByNC(params);
        Assert.assertTrue("NC代码1130306000数量为0", count > 0L);
    }

    @Test
    public void getNccodeFuzzyList()
    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("grpid",  "outdialb24");
        params.put("nccode",  "1060107210");
        List<PlubasInfo> infos = plubasInfoService.getPlubasInfosByNC(params, 1, 20);
        Assert.assertNotNull(infos);
        for(PlubasInfo info : infos) {
            Assert.assertEquals("1060107210", info.getNccode());
        }

        HashMap<String, Object> params2 = new HashMap<String, Object>();
        params2.put("nccode",  "1060107%");
        List<PlubasInfo> infos2 = plubasInfoService.getPlubasInfosByNC(params2, 1, 20);
        Assert.assertNotNull(infos2);
        for(PlubasInfo info : infos2){
            Assert.assertTrue(info.getNccode().startsWith("1060107"));
        }

        HashMap<String, Object> params3 = new HashMap<String, Object>();
        params3.put("nccode",  "%107210");
        List<PlubasInfo> infos3 = plubasInfoService.getPlubasInfosByNC(params3, 1, 20);
        Assert.assertNotNull(infos3);
        for(PlubasInfo info : infos3){
            Assert.assertTrue(info.getNccode().endsWith("107210"));
        }

        HashMap<String, Object> params4 = new HashMap<String, Object>();
        params4.put("nccode",  "%0721%");
        List<PlubasInfo> infos4 = plubasInfoService.getPlubasInfosByNC(params4, 1, 20);
        Assert.assertNotNull(infos4);
        for(PlubasInfo info : infos4){
            Assert.assertTrue(info.getNccode().contains("0721"));
        }
    }

    @Test
    public void getSpellcodeFuzzyList()
    {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("spellcode",  "YYWJYDT3");
        List<PlubasInfo> infos = plubasInfoService.getPlubasInfosByNC(params, 1, 20);
        Assert.assertNotNull(infos);
        for(PlubasInfo info : infos){
            Assert.assertEquals("YYWJYDT3", info.getSpellcode());
        }

        HashMap<String, Object> params2 = new HashMap<String, Object>();
        params2.put("spellcode",  "YYWJY%");
        List<PlubasInfo> infos2 = plubasInfoService.getPlubasInfosByNC(params2, 1, 20);
        Assert.assertNotNull(infos2);
        for(PlubasInfo info : infos2) {
            Assert.assertTrue(info.getSpellcode().startsWith("YYWJY"));
        }

        HashMap<String, Object> params3 = new HashMap<String, Object>();
        params3.put("spellcode",  "%JYDT3");
        List<PlubasInfo> infos3 = plubasInfoService.getPlubasInfosByNC(params3, 1, 20);
        Assert.assertNotNull(infos3);
        for(PlubasInfo info : infos3) {
            Assert.assertTrue(info.getSpellcode().endsWith("JYDT3"));
        }

        HashMap<String, Object> params4 = new HashMap<String, Object>();
        params4.put("spellcode",  "%YWJYD%");
        List<PlubasInfo> infos4 = plubasInfoService.getPlubasInfosByNC(params4, 1, 20);
        Assert.assertNotNull(infos4);
        for(PlubasInfo info : infos4){
            Assert.assertTrue(info.getSpellcode().contains("YWJYD"));
        }
    }

    @Test
    public void getPlunameFuzzyList()
    {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pluname",  "米奴仙仿缎加长礼服裙");
        List<PlubasInfo> infos = plubasInfoService.getPlubasInfosByNC(params, 1, 20);
        Assert.assertNotNull(infos);
        for(PlubasInfo info : infos){
            Assert.assertEquals("米奴仙仿缎加长礼服裙", info.getPluname());
        }

        HashMap<String, Object> params2 = new HashMap<String, Object>();
        params2.put("pluname",  "紫环%");
        List<PlubasInfo> infos2 = plubasInfoService.getPlubasInfosByNC(params2, 1, 20);
        Assert.assertNotNull(infos2);
        for(PlubasInfo info : infos2){
            Assert.assertTrue(info.getPluname().startsWith("紫环"));
        }

        HashMap<String, Object> params3 = new HashMap<String, Object>();
        params3.put("spellcode",  "%连衣裙");
        List<PlubasInfo> infos3 = plubasInfoService.getPlubasInfosByNC(params3, 1, 20);
        Assert.assertNotNull(infos3);
        for(PlubasInfo info : infos3){
            Assert.assertTrue(info.getPluname().endsWith("连衣裙"));
        }

        HashMap<String, Object> params4 = new HashMap<String, Object>();
        params4.put("pluname",  "%可贝尔%");
        List<PlubasInfo> infos4 = plubasInfoService.getPlubasInfosByNC(params4, 1, 20);
        Assert.assertNotNull(infos4);
        for(PlubasInfo info : infos4) {
            Assert.assertTrue(info.getPluname().contains("可贝尔"));
        }
    }

    @Test
    public void getPlubasInfosBySuiteId() {
        List<PlubasInfo> infos = plubasInfoService.getPlubasInfosBySuiteId("106053490100");
        Assert.assertNotNull(infos);
    }

    @Test
    public void getNcAttributes() {
        List<NcPlubasInfoAttribute> list = plubasInfoService.getNcAttributes("1130124801");
        Assert.assertNotNull(list);
    }

}
