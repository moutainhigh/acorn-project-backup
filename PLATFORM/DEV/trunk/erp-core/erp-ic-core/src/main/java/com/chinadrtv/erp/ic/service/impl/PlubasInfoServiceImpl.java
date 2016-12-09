package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.ic.dao.PlubasInfoDao;
import com.chinadrtv.erp.ic.model.NcPlubasInfo;
import com.chinadrtv.erp.ic.model.NcPlubasInfoAttribute;
import com.chinadrtv.erp.ic.service.PlubasInfoService;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 产品服务
 * User: gaodejian
 * Date: 13-5-3
 * Time: 下午1:08
 * To change this template use File | Settings | File Templates.
 */
@Service("plubasInfoService")
public class PlubasInfoServiceImpl implements PlubasInfoService {

    @Autowired
    private PlubasInfoDao plubasInfoDao;
    /**
     * 商品精确查询
     * @param nccode
     * @return
     */
    public List<PlubasInfo> getPlubasInfosByNC(String nccode){
        return plubasInfoDao.getPlubasInfosByNC(nccode);
    }
    /**
     * 渠道商品精确查询
     * @param params
     * @return
     */
    public List<PlubasInfo> getExactPlubasInfosByNC(Map<String, Object> params, int index, int size) {
       return plubasInfoDao.getExactPlubasInfosByNC(params, index, size);
    }


    /**
     * 获取制定产品数量
     * @param params
     * @return
     */
    public Long getPlubasInfoCountByNC(Map<String, Object> params)
    {
        return plubasInfoDao.getPlubasInfoCountByNC(params);
    }
    /**
     * 商品模糊查询
     * @param params
     * @return
     */
    public List<PlubasInfo> getPlubasInfosByNC(Map<String, Object> params, int index, int size){
        return plubasInfoDao.getPlubasInfosByNC(params, index, size);
    }
    /**
     * 根据套装商品编号获得套装明细
     * @param suiteId
     * @return
     */
    public List<PlubasInfo> getPlubasInfosBySuiteId(String suiteId){
        return plubasInfoDao.getPlubasInfosBySuiteId(suiteId);
    }

    /**
     * 产品精确查询
     * @return
     */
    public List<PlubasInfo> getPlubasInfos(String nccode, String pluname, String spellcode, String ncfreename){
        Map<String, Object> params = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(nccode)){
            params.put("nccode", nccode);
        }
        if(StringUtils.isNotBlank(pluname)){
            params.put("pluname", pluname);
        }
        if(StringUtils.isNotBlank(spellcode)){
            params.put("spellcode", spellcode);
        }
        if(StringUtils.isNotBlank(ncfreename)){
            params.put("ncfreename", ncfreename);
        }
        return plubasInfoDao.getPlubasInfos(params);
    }

    /**
     * 根据商品编码获取商品
     * @param plucode
     * @return
     */
    public PlubasInfo getPlubasInfo(String plucode){
        return plubasInfoDao.getPlubasInfo(plucode);
    }
    /**
     * 根据商品ID获取商品
     * @param ruid
     * @return
     */
    public PlubasInfo getPlubasInfoByplid(Long ruid){
        return plubasInfoDao.getPlubasInfoByplid(ruid);
    }

    /**
     * 商品搜索(keyword可能是nccode,spellcode,pluname)
     * @param keyword
     * @return
     */
    public List<PlubasInfo> getPlubasInfos(String grpid, String keyword, Integer limit){
        return plubasInfoDao.getPlubasInfos(grpid, keyword, limit);
    }

    public List<NcPlubasInfo> getNcPlubasInfos(String grpid, String keyword, Integer index, Integer limit){
        return plubasInfoDao.getNcPlubasInfos(grpid, keyword, index, limit);
    }
    /**
     * 商品规格(NC自由项)
     * @param nccode
     * @return
     */
    public List<NcPlubasInfoAttribute> getNcAttributes(String nccode){
        List<NcPlubasInfoAttribute> attributes = plubasInfoDao.getNcAttributes(nccode);
        for(NcPlubasInfoAttribute attribute : new ArrayList<NcPlubasInfoAttribute>(attributes))
        {
             if(StringUtils.isBlank(attribute.getNcfree()) &&
                StringUtils.isBlank(attribute.getNcfreename())){
                 attributes.remove(attribute);
             }
        }
        return attributes;
    }

    public List<NcPlubasInfoAttribute> getNcAllAttributes(String nccode) {
        List<NcPlubasInfoAttribute> attributes = plubasInfoDao.getNcAllAttributes(nccode);
        for(NcPlubasInfoAttribute attribute : new ArrayList<NcPlubasInfoAttribute>(attributes))
        {
            if(StringUtils.isBlank(attribute.getNcfree()) &&
                    StringUtils.isBlank(attribute.getNcfreename())){
                attributes.remove(attribute);
            }
        }
        return attributes;
    }

    /**
     * 获取NC商品
     * @param nccode
     * @return
     */
    public NcPlubasInfo getNcPlubasInfo(String nccode){
        return plubasInfoDao.getNcPlubasInfo(nccode);
    }


    public List<PlubasInfo> getAllPlubasInfos(String catcode,String pluname,int index ,int size) {
        return plubasInfoDao.getAllPlubasInfos(catcode,pluname,index ,size);  //To change body of implemented methods use File | Settings | File Templates.
    }

    /* (non-Javadoc)
     * @see com.chinadrtv.erp.admin.service.PlubasInfoService#getAllPlubasInfosCount()
     */
    public int getAllPlubasInfosCount(String catcode,String pluname) {
        // TODO Auto-generated method stub
        return plubasInfoDao.getAllPlubasInfosCount(catcode,pluname);
    }

    /* (non-Javadoc)
     * @see com.chinadrtv.erp.admin.service.PlubasInfoService#getPlubasInfosByIds(java.lang.String)
     */
    public Set<PlubasInfo> getPlubasInfosByIds(String ids) {
        // TODO Auto-generated method stub
        Set<PlubasInfo> set = new HashSet();
        for(String id : ids.split(",")){
            set.add(plubasInfoDao.get(Long.valueOf(id)));
        }

        return set;
    }


}
