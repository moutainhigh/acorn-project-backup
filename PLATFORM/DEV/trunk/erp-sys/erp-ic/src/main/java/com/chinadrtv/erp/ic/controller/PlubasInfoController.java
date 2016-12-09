package com.chinadrtv.erp.ic.controller;

import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.model.inventory.AllocatedEvent;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: gaodejian
 * Date: 13-2-1
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PlubasInfoController {

    @Autowired
    private PlubasInfoService plubasInfoService;

    @RequestMapping(value = "/product/getList",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public List<PlubasInfo> getNcPlubasInfos1(@RequestBody Map<String, Object> params){
        return plubasInfoService.getPlubasInfosByNC((String)params.get("nccode"));
    }

    @RequestMapping(value = "/product/getFuzzyList",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public List<PlubasInfo> getNcPlubasInfos2(
            @RequestBody Map<String, Object> params)
    {
        /*
        if(params.get("nccode")nccode != null && !"".equals(nccode)){
            params.put("nccode", nccode);
        }
        if(spellcode != null && !"".equals(spellcode)){
            params.put("spellcode", spellcode);
        }
        if(pluname != null && !"".equals(pluname)){
            params.put("pluname", pluname);
        }
        */
        return plubasInfoService.getPlubasInfosByNC(params, 1, 20);
    }
}
