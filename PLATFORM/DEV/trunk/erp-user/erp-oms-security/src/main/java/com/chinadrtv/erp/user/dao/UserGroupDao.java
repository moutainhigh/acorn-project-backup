package com.chinadrtv.erp.user.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.user.model.UserGroup;
import java.util.List;

public abstract interface UserGroupDao extends GenericDao<UserGroup, String>
{
  public abstract List<UserGroup> getUserGroupsByUserId(String paramString);
}