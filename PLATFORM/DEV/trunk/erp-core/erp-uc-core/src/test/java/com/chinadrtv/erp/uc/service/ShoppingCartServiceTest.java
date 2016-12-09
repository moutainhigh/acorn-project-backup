package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.model.ScmPromotion;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.model.ShoppingCartProduct;
import com.chinadrtv.erp.model.agent.Product;
import com.chinadrtv.erp.uc.test.AppTest;
import junit.framework.Assert;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
/**
 * 
 * <dl>
 *    <dt><b>Title:购物车测试</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:购物车增查删改测试</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 *
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-13 上午9:36:50 
 *
 */
public class ShoppingCartServiceTest extends AppTest {
	@Autowired
	private ShoppingCartService shoppingCartService;


    @Test
    public void testGetUserdInsureProduct(){
        List<Product> list = shoppingCartService.getUserdInsureProduct("24143295");

        Assert.assertNotNull(list);
    }

    @Test
    public  void testCreateShoppingCart(){
        ShoppingCart shoppingCart = shoppingCartService.createShoppingCart(9299292L, "12000", ShoppingCart.CART);

        Assert.assertNotNull(shoppingCart.getId());
    }

    @Test
    public  void testFindShoppingCart(){
        ShoppingCart createShoppingCart = shoppingCartService.createShoppingCart(9299292L, "12000", ShoppingCart.CART);
        ShoppingCart findShoppingCart = shoppingCartService.findShoppingCartByContactId(9299292L, ShoppingCart.CART);
        ShoppingCart orderShoppingCart = shoppingCartService.findShoppingCartByContactId(9299292L, ShoppingCart.ORDER);

        Assert.assertEquals(findShoppingCart.getId(), createShoppingCart.getId());
        Assert.assertNull(orderShoppingCart.getId());
    }

    @Test
    public void  testAddShoppingCartProduct() throws ErpException {
        long contactId = 9299292L;
        ShoppingCart shoppingCart = shoppingCartService.createShoppingCart(contactId, "12000", ShoppingCart.CART);
        ShoppingCartProduct scp = getShoppingCartproduct("1020140100","诺基亚手机N8");
        shoppingCartService.addShoppingCartProduct(shoppingCart.getId(), "sa", scp);

        ShoppingCart findShoppingCart = shoppingCartService.findShoppingCartByContactId(contactId, ShoppingCart.CART);

        Assert.assertEquals(1,findShoppingCart.getShoppingCartProducts().size());

    }

    @Test
    public void testFindShoppingCartProductById() throws ErpException {
        long contactId = 9299292L;
        ShoppingCart shoppingCart = shoppingCartService.createShoppingCart(contactId, "12000", ShoppingCart.CART);
        ShoppingCartProduct scp = getShoppingCartproduct("1020140100","诺基亚手机N8");
        Map<String, Object> result = shoppingCartService.addShoppingCartProduct(shoppingCart.getId(), "sa", scp);

        ShoppingCart findShoppingCart = shoppingCartService.findShoppingCartByContactId(contactId, ShoppingCart.CART);
         Set<ShoppingCartProduct> scpSet=findShoppingCart.getShoppingCartProducts();
        ShoppingCartProduct newScp= shoppingCartService.findShoppingCartProductById(scpSet.iterator().next().getId());

        Assert.assertEquals("1020140100",newScp.getSkuCode());
    }

    @Test
    public void testDeleteShoppingCartById() throws ErpException {
        long contactId = 9299292L;
        ShoppingCart shoppingCart = shoppingCartService.createShoppingCart(contactId, "12000", ShoppingCart.CART);
        ShoppingCartProduct scp = getShoppingCartproduct("1020140100","诺基亚手机N8");
        shoppingCartService.addShoppingCartProduct(shoppingCart.getId(), "sa", scp);
        shoppingCartService.deleteShoppingCartById(shoppingCart.getId());
        shoppingCart = shoppingCartService.findShoppingCartByContactId(contactId, ShoppingCart.CART);

        Assert.assertNull(shoppingCart);

        ShoppingCart newShoppingCart = shoppingCartService.createShoppingCart(contactId, "12000", ShoppingCart.CART);
        scp.setIsSelected(false);
        shoppingCartService.addShoppingCartProduct(newShoppingCart.getId(), "sa", scp);
        shoppingCartService.deleteShoppingCartById(newShoppingCart.getId());
        newShoppingCart = shoppingCartService.findShoppingCartByContactId(contactId, ShoppingCart.CART);

        Assert.assertEquals(1,newShoppingCart.getShoppingCartProducts().size());

    }


    //	 @Test 需要初始化数据
	public void testUpdateShoppingCartProduct() throws ErpException {
	    // 查询
		ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByContactId(9299292L,ShoppingCart.CART);
		Set<ShoppingCartProduct> set = shoppingCart.getShoppingCartProducts();
		ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
		for (ShoppingCartProduct scp : set) {
			try {
				if("1140247309".equals(scp.getSkuCode())){
					BeanUtils.copyProperties(shoppingCartProduct, scp);
				}
			} catch (Exception e) {
				//时间对象为空时报错，关闭栈输出
			}
			break;
		}                               
		shoppingCartProduct.setSkuCode("1020140100");
		shoppingCartProduct.setProductName("诺基亚手机N8");
		shoppingCartProduct.setProductType("银色");
		shoppingCartProduct.setUpdateDate(new Date());
		shoppingCartProduct.setUpdateUser("admin");
		shoppingCartProduct.setShoppingCart(shoppingCart);
		// 更新购物车产品
		try {
			shoppingCartService.updateShoppingCartProduct(shoppingCartProduct,"sa");
		} catch (Exception e) {
			e.printStackTrace();
		}
		shoppingCart = shoppingCartService.findShoppingCartByContactId(9299292L,ShoppingCart.CART);
		Assert.assertEquals(1, shoppingCart.getShoppingCartProducts().size());
		
	}

	
	
	@Test
	public void testFindShoppingCartByContactId() throws ErpException{
		//查询
		ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByContactId(9299292L,ShoppingCart.CART);
		Assert.assertNotNull(shoppingCart);
		for (ShoppingCartProduct shoppingCartProduct:shoppingCart.getShoppingCartProducts()) {
			Assert.assertEquals("admin", shoppingCartProduct.getCreateUser());
		}
	}

	@Test
	public void testDeleteShoppingCartByContactId() throws ErpException {
		// 删除
		long contactId = 9299292L;
		shoppingCartService.deleteShoppingCartById(contactId);

		// 查询
		ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByContactId(contactId,ShoppingCart.CART);
		Assert.assertNull(shoppingCart);
	}
	
	@Test
	public void testDeleteShoppingCartProduct() throws ErpException {
		// 查询
		ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByContactId(9299292L,ShoppingCart.CART);
		Set<ShoppingCartProduct> set = shoppingCart.getShoppingCartProducts();
		for (ShoppingCartProduct scp : set) {
			shoppingCartService.deleteShoppingCartProductById(9299292L,scp.getId(),"sa");
		}
		this.testDeleteShoppingCartByContactId();
		shoppingCart = shoppingCartService.findShoppingCartByContactId(9299292L,ShoppingCart.CART);
		Assert.assertNull(shoppingCart);
	}

	@Test
	public void testGetSCMPromotion(){
		// 该测试依赖iagent2.p_getsalespromotioninfo中数据
		//"1120865000,1140247309,1110401110,1150206505,1140100701",
		ShoppingCart sc = getShoppingCart();
		ShoppingCartProduct scp = getShoppingCartproduct("1110401110","益尔健");
		scp.setSalePrice(new BigDecimal(1038.00));
		scp.setShoppingCart(sc);
		sc.getShoppingCartProducts().add(scp);
		List<ScmPromotion> list = shoppingCartService.getScmPromotions(sc,"sa");
		Assert.assertEquals(true, list.size()>0);
	}

	
	private ShoppingCart getShoppingCart() {
		ShoppingCart sc = new ShoppingCart();
		sc.setContactId(9299292L);
		sc.setCreateDate(new Date());
		sc.setCreateUser("admin");

		Set<ShoppingCartProduct> set = new HashSet<ShoppingCartProduct>();
		ShoppingCartProduct scp = getShoppingCartproduct("1020140100","诺基亚手机N8");
		scp.setShoppingCart(sc);
		set.add(scp);
		sc.setShoppingCartProducts(set);
		return sc;
	}

	private ShoppingCartProduct getShoppingCartproduct(String skuCode,String productname) {
		ShoppingCartProduct scp =new ShoppingCartProduct();
		scp.setCreateDate(new Date());
		scp.setCreateUser("admin");
		scp.setGiftSource("gift");
		scp.setIsGift(2);
		scp.setIsSelected(true);
		scp.setOptionValue("ADD");
		scp.setProductQuantity(1);
		scp.setSkuCode(skuCode);
		scp.setProductImage("/img/1.jpg");
		scp.setProductName(productname);
		scp.setProductType("银色");
		scp.setSalePrice(new BigDecimal(1372.00));
		scp.setGiftSourceDetail("赠品描述");
		return scp;
	}

    @Test
    public  void test_updateShoppingCartContact(){
        Long potentialContactCode =9299292L;
        Long contactCode =1123123L;
        shoppingCartService.updateShoppingCartContact(potentialContactCode,contactCode);

        ShoppingCart result = shoppingCartService.findShoppingCartByContactId(contactCode,ShoppingCart.CART);

        Assert.assertEquals(4,result.getShoppingCartProducts().size());
    }

    @Test
    public void testValidateInsure(){
       List<Product> list =  shoppingCartService.getInsureList("1234","outdialb33",null);

       Assert.assertEquals(1,list.size());
    }
	
 }
