package com.chinadrtv.uam.controller;

import com.chinadrtv.uam.model.auth.MenuGroup;
import com.chinadrtv.uam.service.MenuGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-21
 * Time: 下午1:15
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/menuGroup")
public class MenuGroupController {
    @Autowired
    private MenuGroupService menuGroupService;

    @RequestMapping(value = "list",method = RequestMethod.POST)
    @ResponseBody
    public List<MenuGroup> list(){
        List<MenuGroup> menuGroupList = menuGroupService.getAll(MenuGroup.class);
        return menuGroupList;
    }


    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public String add(MenuGroup menuGroup) {
        menuGroup.setCreateDate(new Date());

        menuGroupService.save(menuGroup);
        return "success";
    }



}
