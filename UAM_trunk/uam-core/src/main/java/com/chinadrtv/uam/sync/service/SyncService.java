package com.chinadrtv.uam.sync.service;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-28
 * Time: 上午10:14
 * To change this template use File | Settings | File Templates.
 */
public interface SyncService {
    /**
     * 同步部门 组 用户
     */
    public void syncLdapInfo();

    /**
     * 同步角色  前提：同步用户
     */
    public void syncLdapRoles();

    /**
     * 同步用户及工作组
     * @param userId  ldap用户Id
     * @param groupCode  用户目标组
     */
    public void  syncUser(String userId,String groupCode);
}
