package com.chinadrtv.erp.admin.service.impl;

import com.chinadrtv.erp.admin.dao.*;
import com.chinadrtv.erp.admin.service.*;
import com.chinadrtv.erp.model.ChannelType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: gaodejian
 * Date: 12-8-10
 */
@Service("ChannelTypeService")
public class ChannelTypeServiceImpl implements ChannelTypeService {
    @Autowired
    private ChannelTypeDao dao;

    public ChannelType findById(String id){
        return dao.getNativeById(id);
    }

    public List<ChannelType> getAllChannelTypes() {
        return dao.getAllChannelTypes();
    }


}
