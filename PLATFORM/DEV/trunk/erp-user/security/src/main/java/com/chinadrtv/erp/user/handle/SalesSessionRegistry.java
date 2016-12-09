package com.chinadrtv.erp.user.handle;

import com.chinadrtv.erp.user.service.SalesSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-10-16
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
public class SalesSessionRegistry extends SessionRegistryImpl {

    @Autowired
    private SalesSessionService salesSessionService;

    private CopyOnWriteArrayList<Map> salesprincipals = new CopyOnWriteArrayList<Map>();
    /**
     * 获取某一个IP上，同个帐号登录的个数
     * @param salesPrincipal
     * @return
     */
    public List<Object> getAllPrincipalsWithIp(SalesPrincipal salesPrincipal) {
        List<SessionInformation> sessions = getAllSessions(salesPrincipal, false);

        List<Object> allPrincipals=new ArrayList<Object>();
        for(SessionInformation sessionInformation:sessions) {
            allPrincipals.add(sessionInformation.getPrincipal());
        }
        return allPrincipals;
    }


    public List<Map> getSalesprincipals() {
        //删除session过期的信息
        removeinvalidationUser();
        return salesprincipals;
    }

    public void setIpToPrincal(String ip,String lip,Date lastTime,String lport,Object obj) {
        Map map = new HashMap();
        map.put("ip",ip);
        map.put("principal",obj);
        map.put("server",lip+":"+lport);
        map.put("lastTime",lastTime);
        map.put("uid",((UserDetails)obj).getUsername());
        if(hasnotExist(salesprincipals,map)){
            this.salesprincipals.addIfAbsent(map);
        }


    }

    public boolean hasnotExist(CopyOnWriteArrayList<Map> list,Map map){

        boolean  flag = true;

        for (Map oldMap: list){
            if (oldMap.equals(map)){
              flag = false;
            }
        }

        return flag ;

    }

    public void removeinvalidationUser(){
        if(salesprincipals.size() != 0){
            for(Map map :salesprincipals){
                boolean  isnotequation =true;
                for(Object principal: this.getAllPrincipals()){
                    if(map.get("principal").equals(principal)){
                        isnotequation=false;
                    }
                }

                if(isnotequation){
                    salesprincipals.remove(map);
                }
            }
        }
    }


    public void removeSalesprincipals(String userName){
        if(salesprincipals.size() != 0){
            for(Map map :salesprincipals){
                if(((UserDetails)map.get("principal")).getUsername().equals(userName)){
                    salesprincipals.remove(map);
                }
            }
        }
    }
}
