package com.chinadrtv.erp.user.util;

import com.chinadrtv.erp.user.model.AgentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.InetOrgPerson;

public class SecurityHelper
{
  public static AgentUser getLoginUser()
  {
    AgentUser user = null;
    if ((SecurityContextHolder.getContext() == null) || (SecurityContextHolder.getContext().getAuthentication() == null)) {
      return null;
    }
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (((principal instanceof String)) && (principal.equals("anonymousUser"))) {
      return null;
    }
    if ((principal instanceof InetOrgPerson)) {
      user = new AgentUser();
      InetOrgPerson person = (InetOrgPerson)principal;
      user.setDepartment(person.getDepartmentNumber());
      user.setUserId(person.getUid());
      user.setName(person.getUsername());
    } else {
      user = (AgentUser)principal;
    }

    return user;
  }

  public static boolean isLogin()
  {
    return (SecurityContextHolder.getContext() == null) || (SecurityContextHolder.getContext().getAuthentication() == null) || (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null);
  }
}