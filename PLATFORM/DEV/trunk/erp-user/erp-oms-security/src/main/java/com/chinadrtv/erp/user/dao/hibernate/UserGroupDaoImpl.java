package com.chinadrtv.erp.user.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Criteria;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.user.dao.UserGroupDao;
import com.chinadrtv.erp.user.model.UserGroup;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository("userGroupDao")
public class UserGroupDaoImpl extends GenericDaoHibernate<UserGroup, String>
  implements UserGroupDao
{
  public UserGroupDaoImpl()
  {
    super(UserGroup.class);
  }

  public List<UserGroup> getAll()
  {
    return null;
  }

  public List<UserGroup> getAllDistinct()
  {
    return null;
  }

  public UserGroup get(String id)
  {
    return null;
  }

  public boolean exists(String id)
  {
    return false;
  }

  public UserGroup save(UserGroup object)
  {
    return null;
  }

  public void remove(String id)
  {
  }

  public List<UserGroup> findByNamedQuery(String queryName, Map<String, Object> queryParams)
  {
    return null;
  }

  public void saveOrUpdate(UserGroup object)
  {
  }

  public int findPageCount(String hql, Map<String, Parameter> parameters)
  {
    return 0;
  }

  public int findPageCount(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias)
  {
    return 0;
  }

  public List<UserGroup> findPageList(String hql, Map<String, Parameter> parameters)
  {
    return null;
  }

  public List<UserGroup> findPageList(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias)
  {
    return null;
  }

  public List<UserGroup> findList(String hql, Map<String, Parameter> parameters)
  {
    return null;
  }

  public List<UserGroup> findList(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias)
  {
    return null;
  }

  public UserGroup find(String hql, Map<String, Parameter> parameters)
  {
    return null;
  }

  public UserGroup find(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias)
  {
    return null;
  }

  public int execUpdate(String hql, Map<String, Parameter> parameters)
  {
    return 0;
  }

  public int execUpdate(String hql, Map<String, Parameter> parameters, Map<String, Criteria> criterias)
  {
    return 0;
  }

  public UserGroup find(String hql, Parameter[] parameters)
  {
    return null;
  }

  public List<UserGroup> findList(String hql, Parameter[] parametersArray)
  {
    return null;
  }

  public int execUpdate(String hql, Parameter[] parametersArray)
  {
    return 0;
  }

  public List<UserGroup> getUserGroupsByUserId(String userId)
  {
    List list = null;
    try {
      String hql = "from UserGroup ug where ug.usrId =:usrId ";
      Query q = getSession().createQuery(hql);
      q.setString("usrId", userId);
      list = q.list();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }

    return list;
  }
}