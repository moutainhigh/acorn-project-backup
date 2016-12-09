package com.chinadrtv.erp.user.handle;


import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-10-22
 * Time: 下午2:52
 * To change this template use File | Settings | File Templates.
 */
public class SpringSecurityUtils {
    /**
     * 取得当前用户的登录名, 如果当前用户未登录则返回空字符串.
     */
    public static String getCurrentUserName() {
        Authentication authentication = getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            return "";
        }

        return authentication.getName();
    }



    /**
     * 取得Authentication, 如当前SecurityContext为空时返回null.
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null) {
            return null;
        }

        return context.getAuthentication();
    }



    /**
     * 取得当前用户登录IP, 如果当前用户未登录则返回空字符串.
     */
    public static String getCurrentUserIp() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
            return "";
        }

        Object details = authentication.getDetails();
        if (!(details instanceof WebAuthenticationDetails)) {
            return "";
        }

        WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
        return webDetails.getRemoteAddress();
    }


    /**
     * 将UserDetails保存到Security Context.
     *
     * @param userDetails 已初始化好的用户信息.
     * @param request 用于获取用户IP地址信息,可为Null.
     */
    public static void saveUserDetailsToContext(UserDetails userDetails, HttpServletRequest request) {
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails,
                userDetails.getPassword(), userDetails.getAuthorities());

        if (request != null) {
            authentication.setDetails(new WebAuthenticationDetails(request));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
