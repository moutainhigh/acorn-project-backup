package com.chinadrtv.erp.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinadrtv.erp.exception.PromotionException;
import com.chinadrtv.erp.model.PromotionRuleResult;
import com.chinadrtv.erp.model.PromotionRuleResult_FreeProduct;

/**
 * User: liuhaidong
 * Date: 12-8-14
 */
public class MvelUtil {
    public static boolean isContain(Set<String> sets, String key){
        return sets.contains(key);

    }
    public static Object getMapObj(String key,Map map){
                   return map.get(key);
    }

    public static Object splitString(String s){
        return s.split(",");
    }

    public  static Double perAmount(Double amount,Double perAmount,Double ratio) {
        if (amount != null){
            BigDecimal bdAmount = new BigDecimal(amount.toString());
            BigDecimal bdPerAmount = new BigDecimal(perAmount.toString());
            BigDecimal times = new BigDecimal(bdAmount.divide(bdPerAmount,0,RoundingMode.DOWN).intValue());
            BigDecimal bdRation = new BigDecimal(ratio.toString());
            Double result = times.multiply(bdRation).doubleValue();
            return result;
        }else{
            return 0d;
        }
    }

    public static String getProductJson(String productId,String skuCode,String productName,Double qty){
        JsonBinder jsonBinder = JsonBinder.buildNonNullBinder();
        Map<String,String> prodMap = new HashMap<String, String>();
        prodMap.put("productName",productName);
        prodMap.put("productId",productId);
        prodMap.put("skuCode",skuCode);
        prodMap.put("unitNumber",qty.toString());
        return jsonBinder.toJson(prodMap);
    }
    
    

    public static String getSetString(String value){
        if (value.length() >1){
            value = value.substring(0, value.length() -1);
        }
       return "[" + value + "]";
    }

    public static  void getJsonObject(String jsonString){
       // String s = "[{\"productName\":\"Free\",\"skuCode\":\"abc\",\"productId\":\"123\"}]" ;
        JsonBinder jsonBinder = JsonBinder.buildNonNullBinder();
        jsonBinder.fromJson(jsonString, Set.class);
    }

/*    private static boolean isTagProduct(Product product,String tag){
      boolean isTagMached = false;
      for (ProductTag pTag : product.getProductTags()){
          if (pTag.getTagName().equals(tag)){
              isTagMached = true;
              break;
          }
      }
      return isTagMached;
    }*/
  /*  public static boolean matchProductTagAndAmound(List<UserBasketProduct> userBasketProducts,Set<String> tags,Double amount){
        if (tags.size() == 0){
            return true;
        }
        BigDecimal bdAmount = new BigDecimal(amount.toString());
        BigDecimal tagAmount = new BigDecimal(0);
        for (UserBasketProduct basketProduct : userBasketProducts){
            boolean isFound = false;
            for (String tag : tags){
                if (isTagProduct(basketProduct.getProduct(),tag)){
                    BigDecimal pAmount = new BigDecimal(basketProduct.getAmountPrice().toString());
                    tagAmount = tagAmount.add(pAmount);
                    if (basketProduct.getPackagePrice() != null){
                        BigDecimal paPrice = new BigDecimal(basketProduct.getPackagePrice().toString());
                        tagAmount = tagAmount.add(paPrice);
                    }
                    break;
                }
            }
        }
        if (tagAmount.compareTo(bdAmount) >= 0){
            return true;
        }else {
            return false;
        }
    }*/
    
    public static List getProductList(Integer group,String prod,String sku,String skuName,String qty,String PromotionName, PromotionRuleResult freeProdResult){
    	List list = new ArrayList();
    	String prodArry[] = prod.split(",");
    	String skuArry[] = sku.split(",");
    	String skuNameArry[] = skuName.split(",");
    	String qtyArry[] = qty.split(",");
    	//判断格式是否正确
    	if(prodArry.length ==qtyArry.length){//
    		for(int i=0;i< prodArry.length;i++){
    			PromotionRuleResult_FreeProduct pf = new PromotionRuleResult_FreeProduct(PromotionName,"","","",0);
    		     pf.setParentResult(freeProdResult);
    			 pf.setProductId(prodArry[i].trim());
    			 pf.setSkuCode(skuArry[i].trim());
    			 pf.setProductName(skuNameArry[i].trim());
    			 pf.setUnitNumber(Integer.valueOf(qtyArry[i]));
    			 pf.setGroup(group);
    			 list.add(pf);
    		}
    	}else{
    		try {
				throw new PromotionException(" UnitNumber is error");
			} catch (PromotionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	return list;
    }
    
    public static Double getBigValue(Double m1,Double m2,Double m3,Double m4,Double m5,Double sumAount){
    	m1 = m1 == null ? 0:m1;
    	m2 = m2 == null ? 0:m2;
    	m3 = m3 == null ? 0:m3;
    	m4 = m4 == null ? 0:m4;
    	m5 = m5 == null ? 0:m5;
        double Darry2[] = {m1, m2, m3, m4, m5,sumAount};
        double Darry[] = new double[6];
        Arrays.sort(Darry2);
        int m =0;
     	for (int i=Darry2.length-1;i>=0 ;i-- )
       {   
     		
            Darry[m]=Darry2[i];
            m++;
        }
        int j = -1;
    	for(int i =0 ; i<Darry.length-1;i++){
    		if(Darry[i]==sumAount){
    			j=i; 	
    			break;
    		}
    	}
    	if(j==-1){
    		return null;
    	}else{
    		return  Darry[j+1];
    	}
    	
    }
}
