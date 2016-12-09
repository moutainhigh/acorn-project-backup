package com.chinadrtv.yhd.service;

import com.chinadrtv.yhd.model.YhdOrderConfig;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-3-21
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
public interface YhdFeedbackService {
    void orderFeedback(List<YhdOrderConfig> yhdOrderConfigList);
}
