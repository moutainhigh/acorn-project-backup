package com.chinadrtv.erp.ic.service;

import com.chinadrtv.erp.ic.model.NcPlubasInfo;
import com.chinadrtv.erp.ic.model.NcPlubasInfoAttribute;
import com.chinadrtv.erp.model.inventory.PlubasInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 13-5-3
 * Time: 下午1:07
 * To change this template use File | Settings | File Templates.
 */
public interface PlubasInfoService {
    /**
     * 根据商品编码获取商品
     * @param plucode
     * @return
     */
    PlubasInfo getPlubasInfo(String plucode);
    /**
     * 根据商品ID获取商品
     * @param ruid
     * @return
     */
    PlubasInfo getPlubasInfoByplid(Long ruid);
    /**
     * 商品精确查询
     * @param nccode
     * @return
     */
    List<PlubasInfo> getPlubasInfosByNC(String nccode);
    /**
     * 渠道商品精确查询
     * @param params
     * @return
     */
    List<PlubasInfo> getExactPlubasInfosByNC(Map<String, Object> params, int index, int size);

    /**
     * 获取指定产品的数量
     * @param params
     * @return
     */
    Long getPlubasInfoCountByNC(Map<String, Object> params);
    /**
     * 商品模糊查询
     * @param params
     * @return
     */
    List<PlubasInfo> getPlubasInfosByNC(Map<String, Object> params, int index, int size);
    /**
     * 根据套装商品编号获得套装明细
     * @param suiteId
     * @return
     */
    List<PlubasInfo> getPlubasInfosBySuiteId(String suiteId);
    /**
     * 查询指定产品
     * @param nccode
     * @param pluname
     * @param spellcode
     * @param ncfreename
     * @return
     */
    List<PlubasInfo> getPlubasInfos(String nccode, String pluname, String spellcode, String ncfreename);

    /**
     * 商品搜索(keyword可能是nccode,spellcode,pluname)
     * @param keyword
     * @return
     */
    List<PlubasInfo> getPlubasInfos(String grpid, String keyword, Integer limit);

    /**
     * 获取NC查询信息
     * @param grpid
     * @param keyword
     * @param limit
     * @return
     */
    List<NcPlubasInfo> getNcPlubasInfos(String grpid, String keyword, Integer index, Integer limit);
    /**
     * 商品规格(NC自由项)
     * @param nccode
     * @return
     */
    List<NcPlubasInfoAttribute> getNcAttributes(String nccode);
    /**
     * 商品所有规格(NC自由项)
     * @param nccode
     * @return
     */
    List<NcPlubasInfoAttribute> getNcAllAttributes(String nccode);
    /**
     * 获取NC商品
     * @param nccode
     * @return
     */
    NcPlubasInfo getNcPlubasInfo(String nccode);

    /**
     * 获取商品
     * @param catcode
     * @param pluname
     * @param index
     * @param size
     * @return
     */
    public List<PlubasInfo> getAllPlubasInfos(String catcode,String pluname,int index ,int size);

    /**
     * 获取商品数量
     * @param catcode
     * @param pluname
     * @return
     */
    public int getAllPlubasInfosCount(String catcode,String pluname);

    /**
     * 根据Id获取商品详细
     * @param ids 商品id
     * @return
     */
    public Set<PlubasInfo> getPlubasInfosByIds(String ids);

}
