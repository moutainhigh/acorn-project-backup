package com.chinadrtv.erp.user.service;

import com.chinadrtv.erp.model.agent.Grp;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 13-6-26
 * Time: 上午10:43
 * To change this template use File | Settings | File Templates.
 */
public interface GrpService {
    /**
     * 获取当前登录用户Grp集合
     * @return
     */
    public  List<Grp> getGrpList();

    List<Grp> getGrpList(List<String> grpIdList);
}
