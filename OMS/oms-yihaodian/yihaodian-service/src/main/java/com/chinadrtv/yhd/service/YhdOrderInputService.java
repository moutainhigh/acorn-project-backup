package com.chinadrtv.yhd.service;

import com.chinadrtv.yhd.model.YhdOrderConfig;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-3-20
 * Time: 上午10:36
 * To change this template use File | Settings | File Templates.
 */
public interface YhdOrderInputService {
    void input(List<YhdOrderConfig> yhdOrderConfigList,Date startDate,Date endDate);
}
