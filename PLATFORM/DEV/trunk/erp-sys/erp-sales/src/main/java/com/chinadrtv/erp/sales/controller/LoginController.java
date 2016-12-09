package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.sales.dto.SessionDto;
import com.chinadrtv.erp.sales.service.SourceConfigure;
import com.chinadrtv.erp.user.handle.SalesSessionRegistry;
import com.chinadrtv.erp.user.service.SalesSessionService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.StringUtil;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * User: liuhaidong
 * Date: 12-11-20
 */
@Controller(value = "loginControl")
public class LoginController extends BaseController {
     private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private SalesSessionRegistry sessionRegistry;

    @Autowired
    private SourceConfigure sourceConfigure;

    @Autowired
    private SalesSessionService salesSessionService;

    Map<Object, Date> lastActivityDates = new HashMap<Object, Date>();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "login/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String toLogout(HttpServletRequest request, HttpServletResponse response,@RequestParam(required = true, defaultValue = "") String name ,Model model) {
        if(SecurityHelper.getLoginUser() != null){
                cullingUserAll(SecurityHelper.getLoginUser().getUserId());
                salesSessionService.removeSalessession(request.getSession().getId());
                logger.info("用户"+SecurityHelper.getLoginUser().getUserId()+"退出");
        }

        return "login/logout";
    }



    @ResponseBody
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.POST)
    public List<SessionDto>  getAllLoginUsers(HttpServletRequest request,
                                              @RequestParam(required = true, defaultValue = "") String uid
                                              ){
     //注入用户登录的Ip
        for(Object principal: sessionRegistry.getAllPrincipals()) {
            if((((UserDetails) principal).getUsername()).equals(uid)) {
                if(lastActivityDates.get(principal) != null )  lastActivityDates.remove(principal);
                sessionRegistry.setIpToPrincal(StringUtil.getIpAddr(request),request.getLocalAddr(),new Date(),String.valueOf(request.getLocalPort()),principal);
            }
        }

     return getActiveUsers();
    }

    @ResponseBody
    @RequestMapping(value = "/cullingUser", method = RequestMethod.GET)
    public void cullingUser(@RequestParam(required = true, defaultValue = "") String name){
        logger.info("剔出用户:"+name);
           List<Object> userList=sessionRegistry.getAllPrincipals();
                for(int i=0; i<userList.size(); i++){
                    UserDetails userTemp=(UserDetails) userList.get(i);
                    if(userTemp.getUsername().equals(name)){
                        List<SessionInformation> sessionInformationList = sessionRegistry.getAllSessions(userTemp, false);
                        if (sessionInformationList!=null && sessionInformationList.size()>0) {
                            for (int j=0; j<sessionInformationList.size(); j++) {
                                sessionInformationList.get(j).expireNow();
                                sessionRegistry.removeSessionInformation(sessionInformationList.get(j).getSessionId());
                                sessionRegistry.removeSalesprincipals(userTemp.getUsername());
                                logger.info("剔出用户:"+sessionInformationList.get(j).getSessionId());
                                salesSessionService.removeSalessession(sessionInformationList.get(j).getSessionId());
                            }
                        }else{
                            sessionRegistry.removeSalesprincipals(userTemp.getUsername());
                        }
                    }
                }

        }

    @ResponseBody
    @RequestMapping(value = "/cullingUserFromServer", method = RequestMethod.GET)
    public void cullingUserFromServer(@RequestParam(required = true, defaultValue = "") String name,
                                      @RequestParam(required = true, defaultValue = "") String server){
        try{
            String json = HttpUtil.getUrl("http://"+server+"/cullingUser?name="+name);
        }catch (Exception e){
            logger.info("请求失败" + e.getMessage());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/cullingUserAll", method = RequestMethod.GET)
    public void cullingUserAll(@RequestParam(required = true, defaultValue = "") String name){


        String tservices[] =  sourceConfigure.getTservices().split(",");

        if(tservices.length !=0){

            for (String tservice : tservices){
                try{
                    String json = HttpUtil.getUrl("http://"+tservice+"/cullingUser?name="+name);
                }catch (Exception e){
                    logger.info("请求失败" + e.getMessage());
                }

            }

        }
    }



    public List<SessionDto> getActiveUsers(){

          List<SessionDto> list= new ArrayList();
       Map<Object,Date> lastActivityDates = new HashMap<Object, Date>();
        for(Map map: sessionRegistry.getSalesprincipals()) {
           Object principal = map.get("principal");
            SessionDto dto = new SessionDto();

        // a principal may have multiple active sessions
        for(SessionInformation session : sessionRegistry.getAllSessions(principal, false))
          {
              dto.setSessionId(session.getSessionId());

      // no last activity stored
          if(lastActivityDates.get(principal) == null) {
           // lastActivityDates.put(principal, session.getLastRequest());
             dto.setLastTime(session.getLastRequest());
          } else {
          // check to see if this session is newer than the last stored
            Date prevLastRequest = lastActivityDates.get(principal);
            if(session.getLastRequest().after(prevLastRequest)) {
                // update if so
                lastActivityDates.put(principal, session.getLastRequest());
                 dto.setLastTime(session.getLastRequest());
              }
            }
          }

            dto.setUid(((UserDetails)principal).getUsername());
            dto.setIp(map.get("ip").toString());

           list.add(dto);
      }
        return list;
    }


    @ResponseBody
    @RequestMapping(value = "/getAU", method = RequestMethod.GET)
    public List<Object>  getAU(HttpServletRequest request){
        return sessionRegistry.getAllPrincipals();
    }
    @ResponseBody
    @RequestMapping(value = "/getAU2", method = RequestMethod.GET)
    public List<Map>  getAU2(HttpServletRequest request){
        return sessionRegistry.getSalesprincipals();
    }


    @ResponseBody
    @RequestMapping(value = "/getAUDTO", method = RequestMethod.GET)
    public List<SessionDto>  getAUDTO(HttpServletRequest request){
        //return getSdtoFromSalesprincipals(sessionRegistry.getSalesprincipals());
        return getSdtoFromSalesprincipalss(sessionRegistry.getAllPrincipals());
    }

    @ResponseBody
    @RequestMapping(value = "/getAU3", method = RequestMethod.POST)
    public List<Map> getAU3(HttpServletRequest request){

        String tservices[] =  sourceConfigure.getTservices().split(",");
        List<Map> list = new ArrayList<Map>();
        Map init = new HashMap();
        init.put("server"," ");
        list.add(init);
        for (String tservice : tservices){
            Map map = new HashMap();

            map.put("server",tservice);
            list.add(map);
        }

        return list;

    }

    @ResponseBody
    @RequestMapping(value = "/getAU4", method = RequestMethod.POST)
    public List<SessionDto>  getAU4(HttpServletRequest request,
                                    @RequestParam(required = true, defaultValue = "") String uid,
                                    @RequestParam(required = true, defaultValue = "") String sdt,
                                    @RequestParam(required = true, defaultValue = "") String edt,
                                    @RequestParam(required = true, defaultValue = "") String service,
                                    @RequestParam(required = true, defaultValue = "") String ip

                                    ){

         List<SessionDto> list = new ArrayList<SessionDto>();
        String tservices[] =  sourceConfigure.getTservices().split(",");

        String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss"};
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));

        if(tservices.length !=0){

            for (String tservice : tservices){
                try{
                    String json = HttpUtil.getUrl("http://"+tservice+"/getAUDTO");
                    if(!StringUtil.isNullOrBank(json)){
                        JSONArray jsonarray = JSONArray.fromObject(json);
                        for(int i = 0; i < jsonarray.size(); i++){//遍历JSONArray
                            JSONObject obj = jsonarray.getJSONObject(i);
                            SessionDto dto = (SessionDto)JSONObject.toBean(obj,SessionDto.class);
                            if(StringUtil.nullToBlank(dto.getUid()).length()>0) list.add(dto);

                        }
                    }
                }catch (Exception e){
                    logger.info("请求失败" + e.getMessage());
                }

            }

        }
        return  filterList(uid,service,ip,filterDoble(list));

    }



   public List<SessionDto> filterDoble(List<SessionDto> list){

       List<SessionDto> result = new ArrayList<SessionDto>();

       if (list != null){
         if(list.size() >1){
             Set<SessionDto> set = new HashSet(list);
             return new ArrayList<SessionDto>(set);
         }else{
               return list;
         }
       }else{
           return null;
       }
   }



    public List<SessionDto> getSdtoFromSalesprincipals(List<Map> listmap){
        List<SessionDto> list = new ArrayList<SessionDto>();

        for(Map map :listmap){
            SessionDto dto = new SessionDto();
            dto.setIp(map.get("ip").toString());
            dto.setServer(map.get("server").toString());
            dto.setLastTime((Date)map.get("lastTime"));
            dto.setUid(map.get("uid").toString());

            list.add(dto);
        }


        return list;

    }


    public List<SessionDto> getSdtoFromSalesprincipalss(List<Object> listmap){
        List<SessionDto> list = new ArrayList<SessionDto>();

        if(listmap.size() > 0){
            for(Object principal :listmap){
                SessionDto dto = new SessionDto();
                // a principal may have multiple active sessions
                for (SessionInformation session : sessionRegistry.getAllSessions(principal, false)) {
                    dto.setSessionId(session.getSessionId());

                    // no last activity stored
                    if (lastActivityDates.get(principal) == null) {
                         lastActivityDates.put(principal, session.getLastRequest());
                        dto.setLastTime(session.getLastRequest());
                    } else {
                        // check to see if this session is newer than the last stored
                        Date prevLastRequest = lastActivityDates.get(principal);
                        if (session.getLastRequest().before(prevLastRequest)) {
                            // update if so
                            lastActivityDates.put(principal, session.getLastRequest());
                            dto.setLastTime(session.getLastRequest());
                        }else{
                            dto.setLastTime(prevLastRequest);
                        }
                    }
                }
                dto.setUid(((UserDetails)principal).getUsername());
                logger.info("sessionRegistry.getSalesprincipals():"+sessionRegistry.getSalesprincipals());

                for(Map map:sessionRegistry.getSalesprincipals()){

                    logger.info("map:"+map.get("principal"));
                    logger.info("principal:"+principal);

                    if(map.get("principal").equals(principal)){
                        dto.setIp(map.get("ip").toString());
                        dto.setServer(map.get("server").toString());
                        dto.setLastTime((Date)map.get("lastTime"));
                        //dto.setUid(map.get("uid").toString());
                    }
                }
                list.add(dto);
            }
        }
        return list;

    }


    /**
     * 通过查询条件过滤List
     * @param uid
     * @param server
     * @param ip
     * @param list
     * @return
     */
    public List<SessionDto> filterList(String uid,String server,String ip,List<SessionDto> list){
        List<SessionDto> newList = new ArrayList<SessionDto>();
        SessionDto querydto = new SessionDto();
        querydto.setIp(ip);
        querydto.setUid(uid);
        querydto.setServer(server);
        for( SessionDto dto:list){
            if(dto.equals2(querydto)){
                newList.add(dto);
            }
        }

        return newList;


    }

}
