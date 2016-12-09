package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.model.inventory.ProductSuiteType;
import com.chinadrtv.erp.uc.dto.ProductSuiteDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-30
 * Time: 下午3:08
 * To change this template use File | Settings | File Templates.
 */
public interface PluSplitDao extends GenericDao<PlubasInfo, Long> {

    //商品定位
    PlubasInfo searchPlubasInfoByNccodeAndNcfreename(String nccode, String ncfreename);

    //查询商品规则类型名称
    String searchProductType(String recId);

    //查询套装商品
    List<ProductSuiteType> searchProductSuiteTypeByProdSuiteScmId(String prodSuiteScmId);

    //查询商品规格名称类型ID
    String getProductTypeId(String nccode, String ncfreename);

    //根据12位SCM编码生成商品信息
    PlubasInfo getIagentProduct(String scmId);

    //根据套装产品组合值查询套装
    List<ProductSuiteDto> searchProductSuiteIdList(List<String> list);
}
