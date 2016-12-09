package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.erp.uc.dto.ProductSuiteDto;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.model.inventory.ProductSuiteType;
import com.chinadrtv.erp.uc.dao.PluSplitDao;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-30
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PluSplitDaoImpl extends GenericDaoHibernate<PlubasInfo, Long> implements PluSplitDao {

    public PluSplitDaoImpl() {
        super(PlubasInfo.class);
    }

    /**
     * 商品定位
     *
     * @param nccode
     * @param ncfreename
     * @return
     */
    public PlubasInfo searchPlubasInfoByNccodeAndNcfreename(String nccode, String ncfreename) {
        if (ncfreename == null) {
            ncfreename = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select a.ruid as ruid,a.plucode as plucode,a.pluname as pluname,a.issuiteplu as issuiteplu from IAGENT.PLUBASINFO a");
        sb.append(" where a.NCCODE = ? and nvl(trim(a.NCFREENAME), '-1') = nvl(trim(?),'-1')");

        Query q = getSession().createSQLQuery(sb.toString())
                .addScalar("ruid",new LongType())
                .addScalar("plucode", new StringType())
                .addScalar("pluname", new StringType())
                .addScalar("issuiteplu", new StringType())
                .setResultTransformer(Transformers.aliasToBean(PlubasInfo.class));
        q.setParameter(0, nccode);
        q.setParameter(1, ncfreename);

        return (PlubasInfo) q.uniqueResult();
    }

    /**
     * 返回产品规则类型信息
     *
     * @param recid
     * @return
     */
    public String searchProductType(String recid) {
        String hql = "select a.dsc from ProductType a where a.recid = :RecId";
        Query q = getSession().createQuery(hql);
        q.setParameter("RecId", recid);
        return (String) q.uniqueResult();
    }

    /**
     * 查询套装商品
     *
     * @param prodSuiteScmId
     * @return
     */
    public List<ProductSuiteType> searchProductSuiteTypeByProdSuiteScmId(String prodSuiteScmId) {
        String hql = " from ProductSuiteType a where a.prodSuiteScmId = :ProdSuiteScmId";
        Query q = getSession().createQuery(hql);
        q.setParameter("ProdSuiteScmId", prodSuiteScmId);
        return q.list();
    }

    /**
     * 根据12位SCM编码生成10编码和自由项
     *
     * @param scmId
     * @return
     */
    public PlubasInfo getIagentProduct(String scmId) {

        String hql = " from PlubasInfo a where a.plucode = :PluCode";
        Query q = getSession().createQuery(hql);
        q.setParameter("PluCode", scmId.trim());

        return (PlubasInfo) q.uniqueResult();
    }


    public List<ProductSuiteDto> searchProductSuiteIdList(List<String> list) {

        String sql ="select prodsuitescmid,total from (" +
                "select p.prodsuitescmid,sum(p.prodscmid*p.prodnum) as total  from IAGENT.PRODUCTSUITETYPE p  group by p.prodsuitescmid )" +
                " where total in (:list)";
        SQLQuery result = getSession().createSQLQuery(sql);
        result.setParameterList("list",list);

        List<Object[]> resultList =result.list() ;
        List<ProductSuiteDto> productSuiteDtoList =new ArrayList<ProductSuiteDto>();
        for(Object[] obj:resultList){
            ProductSuiteDto productSuiteDto = new ProductSuiteDto();
            productSuiteDto.setProdsuitescmid(obj[0].toString());
            productSuiteDto.setTotal(obj[1].toString());
            productSuiteDtoList.add(productSuiteDto);
        }
        return  productSuiteDtoList;
    }

    //查询商品规格名称类型ID
    public String getProductTypeId(String nccode, String ncfreename) {

        //判断商品自由项是否为空
       if (StringUtils.isEmpty(nccode))
       {
           return "none";
       }
        else
       {
           String hql = "select a.recid from ProductType a where a.prodrecid = :NCCODE and a.dsc = :NCFREENAME";
           Query q = getSession().createQuery(hql);
           q.setParameter("NCCODE", nccode);
           q.setParameter("NCFREENAME", ncfreename);

           return  null == q.uniqueResult() ? "none" : q.uniqueResult().toString();
       }
    }
}
