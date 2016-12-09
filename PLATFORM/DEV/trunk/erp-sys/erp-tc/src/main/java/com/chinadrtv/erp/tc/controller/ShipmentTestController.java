package com.chinadrtv.erp.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.model.AreaGroup;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.service.AreaGroupService;
import com.chinadrtv.erp.tc.service.ShipmentHeaderService;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-6
 */
@Controller
@RequestMapping("/shipment1")
public class ShipmentTestController extends TCBaseController{

    @Autowired
    private ShipmentHeaderService shipmentHeaderService;

    @Autowired
    private AreaGroupService areaGroupService;

    @RequestMapping(value = "/testCache",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public String testCache(@RequestBody String areaId)
    {
        AreaGroup areaGroup=new AreaGroup();
        Long id=1L;
        try
        {
            id=Long.parseLong(areaId);
        } catch (Exception e){

        }
        areaGroup.setId(id);
        System.out.println("test");
        areaGroupService.getAreaGroupDetails(areaGroup);
        return "succ";
    }

    @RequestMapping(value = "/test",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public String justTest(@RequestBody String shipmentId)
    {
        try
        {
            if(shipmentId!=null&&!"".equals(shipmentId))
            {
                Long id=Long.parseLong(shipmentId);
                if(shipmentHeaderService!=null)
                {
                    ShipmentHeader shipmentHeader= shipmentHeaderService.getShipmentHeader(id);
                    if(shipmentHeader==null)
                    {
                        System.out.println("shipmentHeader is null");
                    }
                    else
                    {
                        if(shipmentHeader.getEntityId()!=null)
                        {
                            Long entityId=Long.parseLong(shipmentHeader.getEntityId());
                            entityId++;
                            shipmentHeader.setEntityId(entityId.toString());
                            shipmentHeaderService.updateShipmentHeader(shipmentHeader);
                        }
                        else
                        {
                            System.out.println("shipmentHeader's EntityId is null");
                        }
                    }
                }
                else
                {
                    System.out.println("shipmentHeaderService is null");
                }
            }
        }
        catch(Exception exp)
        {
             return "error: "+exp.getMessage();
        }
        return "just test";
    }
}
