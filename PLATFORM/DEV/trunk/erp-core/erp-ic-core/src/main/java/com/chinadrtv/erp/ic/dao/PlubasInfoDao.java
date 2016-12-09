package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.ic.model.NcPlubasInfo;
import com.chinadrtv.erp.ic.model.NcPlubasInfoAttribute;
import com.chinadrtv.erp.model.inventory.PlubasInfo;

import java.util.List;
import java.util.Map;

/**
 * 结算单数据访问接口
 * User: gaodejian
 * Date: 13-1-11
 * Time: 上午11:22 To change this
 * template use File | Settings | File Templates.
 */
public interface PlubasInfoDao extends GenericDao<PlubasInfo, Long> {
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

    Long getExactPlubasInfoCountByNC(Map<String, Object> params);
    /**
     * 渠道商品精确查询
     * @param params
     * @return
     */
    List<PlubasInfo> getExactPlubasInfosByNC(Map<String, Object> params, int index, int size);
    /**
     * 商品模糊数量
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
     * 商品搜索(keyword可能是nccode,spellcode,pluname)
     * @param keyword
     * @return
     */
    List<PlubasInfo> getPlubasInfos(String grpid, String keyword, Integer limit);
    List<PlubasInfo> getPlubasInfos(Map<String, Object> params);
    List<PlubasInfo> getPlubasInfos(String nccode, String pluname, String spellcode, String ncfreename);
    /**
     * 获取NC商品信息
     * @param nccode
     * @return
     */
    NcPlubasInfo getNcPlubasInfo(String nccode);
    /**
     * NC商品搜索
     * @param grpid
     * @param keyword
     * @param limit
     * @return
     */
    List<NcPlubasInfo> getNcPlubasInfos(String grpid, String keyword,  Integer index, Integer limit);
    /**
     * 获取NC商品规格
     * @param nccode
     * @return
     */
    List<NcPlubasInfoAttribute> getNcAttributes(String nccode);

    /**
     * 获取NC商品所有规格
     * @param nccode
     * @return
     */
    List<NcPlubasInfoAttribute> getNcAllAttributes(String nccode);

    public List<PlubasInfo> getAllPlubasInfos(String catcode,String pluname,int index ,int size);
    public int getAllPlubasInfosCount(String catcode,String pluname);


}
