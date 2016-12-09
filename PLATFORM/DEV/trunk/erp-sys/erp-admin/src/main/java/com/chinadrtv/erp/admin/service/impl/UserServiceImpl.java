package com.chinadrtv.erp.admin.service.impl;

import com.chinadrtv.erp.admin.dao.UserDao;
import com.chinadrtv.erp.admin.model.User;
import com.chinadrtv.erp.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao dao;

    public User findById(String id){
        return dao.get(id);
    }

    public List<User> getAllUser() {
        return dao.getAllUser();  //To change body of implemented methods use File | Settings | File Templates.
    }



}
