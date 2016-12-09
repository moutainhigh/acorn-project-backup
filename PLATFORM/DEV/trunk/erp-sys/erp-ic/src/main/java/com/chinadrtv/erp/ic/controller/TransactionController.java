package com.chinadrtv.erp.ic.controller;

import com.chinadrtv.erp.ic.service.ItemInventoryTransactionService;
import com.chinadrtv.erp.model.inventory.AllocatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: gaodejian
 * Date: 13-2-1
 * Time: 下午3:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController {


    @RequestMapping(value = "/preassign",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public Integer preassign(@RequestBody AllocatedEvent event){

        return 1;
    }
}
