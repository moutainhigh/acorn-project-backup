package com.chinadrtv.erp.uc.dao.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.erp.model.agent.Parameters;
import com.chinadrtv.erp.model.agent.Product;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.model.ShoppingCartProduct;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.uc.dao.ShoppingCartDao;
/**
 * 购物车DAO
 *
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-13 上午9:34:59 
 *
 */
@Repository
public class ShoppingCartDaoImpl extends GenericDaoHibernate<ShoppingCart, Long> implements
        ShoppingCartDao {

    public ShoppingCartDaoImpl() {
        super(ShoppingCart.class);
    }

    public ShoppingCart findShoppingCartById(Long id) {
        Query hqlQuery = getSession().createQuery("from ShoppingCart s where s.id =:id");
        hqlQuery.setParameter("id", id);
        return (ShoppingCart) hqlQuery.uniqueResult();
    }

    public ShoppingCart findShoppingCartByContactId(Long contactId,String cartType) {
        if(StringUtils.isBlank(cartType)){
            cartType =ShoppingCart.CART;
        }
        Query hqlQuery = getSession().createQuery("from ShoppingCart s where s.contactId =:contactId and s.cartType =:cartType");
        hqlQuery.setParameter("contactId", contactId);
        hqlQuery.setParameter("cartType", cartType);
        return (ShoppingCart) hqlQuery.uniqueResult();
    }

    public void deleteShoppingCartById(Long shoppingCartId) {
        ShoppingCart shoppingCart = this.findShoppingCartById(shoppingCartId);
        if(shoppingCart !=null){
            getSession().delete(shoppingCart);
        }
    }

    public Integer deleteShoppingCartSelectProducts(Long shoppingCartId) {
        Query hqlQuery = getSession().createQuery("delete from ShoppingCartProduct p where p.shoppingCart.id =:shoppingCartId and p.isSelected =1 ");
        hqlQuery.setParameter("shoppingCartId",shoppingCartId);
        int deleteCount = hqlQuery.executeUpdate();
        getSession().flush();
        return deleteCount;
    }

    public void addShoppingCartProduct(Long shoppingCartId, ShoppingCartProduct shoppingCartProduct) {
        ShoppingCart shoppingCart =  this.findShoppingCartById(shoppingCartId);
        shoppingCartProduct.setShoppingCart(shoppingCart);
        //重复产品，合并后保存
        boolean tempContain = false;
        for (ShoppingCartProduct scp : shoppingCart.getShoppingCartProducts()) {
            if (scp.getProductCode().equals(shoppingCartProduct.getProductCode()) && scp.getIsGift().equals(shoppingCartProduct.getIsGift())) {
                scp.setProductQuantity(scp.getProductQuantity() + shoppingCartProduct.getProductQuantity());
                shoppingCartProduct.setId(scp.getId());
                tempContain = true;
            }
        }
        if (!tempContain) {
            shoppingCart.getShoppingCartProducts().add(shoppingCartProduct);
        }
        getSession().saveOrUpdate(shoppingCart);
        getSession().flush();

    }

    private ShoppingCart getShoppingCart(Long contactId,
                                         ShoppingCartProduct shoppingCartProduct,String cartType) {
        ShoppingCart shoppingCart = this.findShoppingCartByContactId(contactId,cartType);
        if(shoppingCart == null){
            shoppingCart = new ShoppingCart();
            shoppingCart.setContactId(contactId);
            shoppingCart.setCreateUser(shoppingCartProduct.getCreateUser());
            shoppingCart.setCreateDate(shoppingCartProduct.getCreateDate());
            shoppingCart.setCartType(ShoppingCart.CART);
            this.save(shoppingCart);
        }
        return shoppingCart;
    }

    public ShoppingCartProduct updateShoppingCartProduct(ShoppingCartProduct shoppingCartProduct) {
        ShoppingCartProduct result = (ShoppingCartProduct) getSession().merge(shoppingCartProduct);
        return result;
    }

    public void deleteShoppingCartProductId(Long shoppingCartProductId) {
        Query hqlQuery = getSession().createQuery("delete from ShoppingCartProduct s where s.id =:shoppingCartProductId");
        hqlQuery.setParameter("shoppingCartProductId", shoppingCartProductId);
        hqlQuery.executeUpdate();
    }

    public Long getShoppingCartProductQuantity(Long shoppingCartId) {
        Query hqlQuery = getSession().createQuery("select sum(s.productQuantity) from ShoppingCartProduct s where  s.isSelected=true  and s.shoppingCart.id =:shoppingCartId");
        hqlQuery.setParameter("shoppingCartId", shoppingCartId);
        Long count  = (Long) hqlQuery.uniqueResult();
        if(count==null){
            count =0L;
        }
        return count;
    }

    public void updateShoppingCartContact(Long potentialContactCode, Long contactCode) {
        ShoppingCart shoppingCart = this.findShoppingCartByContactId(potentialContactCode,ShoppingCart.CART);
        if(shoppingCart !=null){
            shoppingCart.setContactId(contactCode);
            this.saveOrUpdate(shoppingCart);
        }
    }

    public ShoppingCartProduct findShoppingCartProductById(Long id) {
        Query hqlQuery = getSession().createQuery("from ShoppingCartProduct s where s.id =:id ");
        hqlQuery.setParameter("id", id);
        return (ShoppingCartProduct) hqlQuery.uniqueResult();
    }

    public Parameters getInsureDays() {
        Query  hqlQuery = getSession().createQuery("from Parameters p where p.paramid =:paramid ");
        hqlQuery.setParameter("paramid","INSUREDAY");
        return (Parameters)hqlQuery.uniqueResult();
    }

    public List<String> getUsedInsure(String contactId, Integer insureDay,String orderId) {

        String queryString = "select distinct p.prodid from iagent.product p where p.catid = '80102' " +
                "      and ( exists (" +
                "       select 1 from iagent.orderhist oh ,iagent.orderdet od " +
                "                where oh.orderid = od.orderid " +
                "                and oh.contactid= :contactId" +
                "                and oh.crdt >= trunc(SYSDATE) - :insureDay" +
                "                and od.orderdt >= trunc(SYSDATE) - :insureDay" +
                "                and od.prodid = p.prodid";
        if (StringUtils.isNotBlank(orderId)) {
            queryString += "                and oh.orderid != :orderId";
        }
        queryString += "  ) OR exists (" +
                "                           select  1   from iagent.contactinsure_ordered f " +
                "                           where f.contactid =:contactId" +
                "                           and sysdate between f.insuredt and f.donateddt + 1 )" +
                "             or exists ( " +
                "                           select 1 from iagent.contactinsure c " +
                "                           where c.contactid =:contactId " +
                "                           and c.insureseccdate  >= trunc(sysdate) - :insureDay  )) ";

        Query hqlQuery = getSession().createSQLQuery(queryString);

        hqlQuery.setParameter("contactId",contactId);
        hqlQuery.setParameter("insureDay",insureDay);
        if(StringUtils.isNotBlank(orderId)) {
            hqlQuery.setParameter("orderId",orderId);
        }
        return hqlQuery.list();
    }

    public List<Product> getInsureList(String grpId){

        String queryString = "SELECT distinct p.prodid, p.prodname, p.unitprice" +
                "    FROM iagent.product p" +
                "    LEFT JOIN iagent.productgrpchannel c" +
                "    ON p.prodid = c.prodid" +
                "    WHERE p.catid = '80102'" +
                "    AND (p.status = '-1' or c.status = -1 and c.enddt >= trunc(SYSDATE)  and c.startdt  <= trunc(SYSDATE) " +
                "    AND     c.grpid = :grpId)";
        Query hqlQuery = getSession().createSQLQuery(queryString);
        hqlQuery.setParameter("grpId", grpId);

        List<Object[]> list = hqlQuery.list();
        List<Product> productList = new ArrayList<Product>();
        if (list != null && !list.isEmpty()) {
            for (Object[] obj : list) {
                Product product =new  Product();
                product.setProdid(obj[0]==null?null:obj[0].toString());
                product.setProdname(obj[1] == null ? null : obj[1].toString());
                product.setUnitprice(obj[2]==null?null:new BigDecimal(obj[2].toString()));
                productList.add(product);
            }
        }
        return productList;
    }

    public List<Product> getUsedInsure(String contactId, Integer insureDay){

        String queryString = "select distinct p.prodid,p.prodname from iagent.product p where p.catid = '80102' " +
                "      and (" +
                "       exists (" +
                "           select 1 from iagent.orderhist oh ,iagent.orderdet od " +
                "           where oh.orderid = od.orderid " +
                "           and oh.contactid= :contactId" +
                "           and oh.crdt >= trunc(sysdate) - :insureDay " +
                "           and od.orderdt >= trunc(sysdate) - :insureDay " +
                "           and od.prodid = p.prodid  ) " +
                "      or exists (" +
                "           select  1   from iagent.contactinsure_ordered f " +
                "           where f.contactid =:contactId " +
                "           and sysdate between f.insuredt and f.donateddt + 1 ) " +
                "      or exists ( " +
                "           select 1 from iagent.contactinsure c " +
                "           where c.contactid =:contactId " +
                "           and c.insureseccdate  >= trunc(sysdate) - :insureDay  )) ";

        Query hqlQuery = getSession().createSQLQuery(queryString);

        hqlQuery.setParameter("contactId",contactId);
        hqlQuery.setParameter("insureDay",insureDay);

        List<Product> productList = new ArrayList<Product>();
        List list = hqlQuery.list();
        if (list != null && !list.isEmpty()) {
            for (Object obj : list) {
                Object[] array = (Object[]) obj;
                Product product = new Product();
                product.setProdid(String.valueOf(array[0]));
                product.setProdname(String.valueOf(array[1]));
                productList.add(product);
            }
        }

        return productList;
    }

}
