package com.chinadrtv.erp.sales.filter;

import com.chinadrtv.erp.user.handle.SalesSessionRegistry;
import com.chinadrtv.erp.user.service.SalesSessionService;
import com.chinadrtv.erp.util.SpringUtil;
import com.chinadrtv.erp.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-10-23
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */


public class SessionInvalidation implements Filter {


    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SessionInvalidation.class);

    @Autowired
    private SalesSessionRegistry sessionRegistry;

    @Autowired
    private SalesSessionService salesSessionService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.

        sessionRegistry = (SalesSessionRegistry)SpringUtil.getBean("sessionRegistry");
        salesSessionService = (SalesSessionService)SpringUtil.getBean("salesSessionService");
        logger.info("sessionRegistry:::::::"+sessionRegistry);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        boolean sessionInvalid=false;
        if(session!=null){
        	String msgUrl = request.getRequestURI();
        	long maxInactive = session.getMaxInactiveInterval();
        	long currentTime = System.currentTimeMillis();
        	Object lastAccessTime = session.getAttribute("lastAccessTime");
        	if(lastAccessTime ==null || msgUrl.indexOf("/message/get") < 0 || msgUrl.indexOf("/task/queryCampaignTask") <= 0){
        		session.setAttribute("lastAccessTime", currentTime);
        		logger.info("maxInactive:"+maxInactive+";lastTime:"+currentTime+";");
        	}else if(msgUrl.indexOf("/message/get") >= 0 || msgUrl.indexOf("/task/queryCampaignTask") >= 0){
        		long lastTime = (Long)lastAccessTime;
        		logger.info("maxInactive:"+maxInactive+";lastTime:"+currentTime+";url="+msgUrl);
        		if(currentTime-lastTime>=maxInactive){
        			session.invalidate();
        			sessionInvalid = true;
        		}
        	}
        }



        String xmlhttprequest = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(xmlhttprequest)) { // ajax request

            // 判断是否是session过期，如果是执行下边的代码

        	logger.info("IP:"+StringUtil.getIpAddr(request) + "; Session Id:"+ request.getRequestedSessionId()
        			+"; Request URL: " + request.getRequestURL()+"; Cookie:" + request.getHeader("Cookie") );
            try {

                boolean ismark = true;
                boolean ismark2 = true;// 登陆的用户是否在   getAllPrincipals 中，不在为TRUE ,在为FALSE
                boolean ismark3 = true;//登陆的用户是否在　getSalesprincipals　中，不在为TRUE ,在为FALSE
                String username="";
                if(sessionRegistry != null){

                    if(!sessionInvalid){
                         if(request.getSession().getAttribute("SPRING_SECURITY_CONTEXT") == null){
                             logger.info("SessionMangeer........");
                             ismark=false;

                             for(Object obj :sessionRegistry.getAllPrincipals()){
                                 List<SessionInformation> sessions = sessionRegistry.getAllSessions(obj, false);
                                 for(SessionInformation ses : sessions){
                                     logger.debug("==="+ses.getSessionId());
                                 }
                             }

                        }else{
                            String sessionId = request.getSession().getId();
                            logger.info("+++++++++++++++++++++++++++++++++++" + request.getRequestURI());
                            username= getUserName(request.getSession().getAttribute("SPRING_SECURITY_CONTEXT"));

                            logger.info("+++++++++++++++++++++++++++++++++++userName:"+(StringUtil.isNullOrBank(username) ? "":username));
                            Object principal = null;
                            try {
                                principal = salesSessionService.getSalesSession(sessionId);
                            }catch (Exception e){
                                logger.info("salesSession is null"+e.getMessage());
                            }
                             //如果存在

                            if(principal != null){

                                if(((UserDetails)principal).getUsername().equals(username) ){
                                    for(Object principall: sessionRegistry.getAllPrincipals()){
                                        if(((UserDetails)principall).getUsername().equals(username)){
                                            ismark2=false;
                                        }
                                    }
                                    for(Map map: sessionRegistry.getSalesprincipals()){
                                        if(map.get("uid").toString().equals(username)){
                                            ismark3 = false;
                                            if(StringUtil.isNullOrBank(map.get("server") ==null ? "":map.get("server").toString())){
                                                sessionRegistry.setIpToPrincal(StringUtil.getIpAddr(request),request.getLocalAddr(),new Date(),String.valueOf(request.getLocalPort()),principal);
                                            }
                                        }
                                    }

                                    if(ismark2){
                                        logger.info("registerNewSession>>>>>");
                                       // sessionRegistry.setIpToPrincal(StringUtil.getIpAddr(request),request.getLocalAddr(),new Date(),String.valueOf(request.getLocalPort()),principal);
                                        sessionRegistry.registerNewSession(sessionId,principal);
                                    }
                                    if(ismark3){
                                        sessionRegistry.setIpToPrincal(StringUtil.getIpAddr(request),request.getLocalAddr(),new Date(),String.valueOf(request.getLocalPort()),principal);

                                    }
                                    ismark=false;
                                }else{
                                    logger.info("GoGoGoGoGoGoGoGoGoGoGo...");
                                }
                            }else{
                               if(salesSessionService == null) ismark=false;
                            }
                        }

                        //logger.info(username+"------------"+request.getSession().getAttribute("SPRING_SECURITY_CONTEXT").toString());
                        logger.info("Security sessions:"+sessionRegistry.getAllPrincipals().size() + ";"+sessionRegistry.getSalesprincipals().size());

                        if(ismark){
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType("text/json");
                            response.setDateHeader("Expires", 0);
                            PrintWriter out = response.getWriter();
                            logger.error("sessionTimeout:true");
                            out.println("{\"sessionTimeout\": true}");

                            out.flush();
                            out.close();

                            return;
                        }

                    }

                }
            }catch (IOException e) {
                logger.error("",e);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getUserName(Object object){

        String username = "";
        Authentication au = null;
        if((SecurityContext)object instanceof SecurityContext ){
            au=((SecurityContextImpl)object).getAuthentication();
            logger.info(au.toString());
            logger.info(au.getPrincipal().toString());
        }
        if(au.getPrincipal()   instanceof   UserDetails){
            logger.info("123");
            username   =   ((UserDetails) au.getPrincipal()).getUsername();
        }

        return username;
    }
}
