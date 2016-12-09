package com.chinadrtv.erp.user.handle;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: haoleitao
 * Date: 13-10-16
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public class SalesPrincipal {

    private String username;
    private String ip;

    public SalesPrincipal(String username, String ip) {
        Assert.notNull(username,
                "username cannot be null (violation of interface contract)");
        Assert.notNull(ip,
                "username cannot be null (violation of interface contract)");
        this.username = username;
        this.ip = ip;
    }

    public SalesPrincipal(Authentication authentication) {
        Assert.notNull(authentication,
                "authentication cannot be null (violation of interface contract)");

        String username = null;

        if (authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal())
                    .getUsername();
        } else {
            username = (String) authentication.getPrincipal();
        }

        String ip = ((WebAuthenticationDetails) authentication
                .getDetails()).getRemoteAddress();
        this.username = username;
        this.ip = ip;
    }



    public boolean equalsIp(SalesPrincipal salesPrincipal) {
        return this.ip.equals(salesPrincipal.ip);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SalesPrincipal) {
            SalesPrincipal salesPrincipal = (SalesPrincipal) obj;

            return username.equals(salesPrincipal.username);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "SmartPrincipal:{username=" + username + ",ip=" + ip + "}";
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
