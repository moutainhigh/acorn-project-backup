package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.admin.model.User;

import java.util.List;

/**
 * User: taoyawei
 * Date: 12-8-10
 */
public interface UserService {
    User findById(String id);
    List<User> getAllUser();
}
