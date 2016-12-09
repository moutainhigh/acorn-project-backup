package com.chinadrtv.erp.admin.service;


import com.chinadrtv.erp.model.ChannelType;

import java.util.*;

/**
 * 渠道类型服务
 * User: gaodejian
 * Date: 12-8-10
 */
public interface ChannelTypeService {
    ChannelType findById(String id);
    List<ChannelType> getAllChannelTypes();
}
