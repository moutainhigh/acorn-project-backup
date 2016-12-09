package com.chinadrtv.service.oms.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.chinadrtv.model.oms.dto.PreTradeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.oms.dao.PreTradeDetailDao;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.service.oms.PriceReconService;

/**
 * User: liuhaidong
 * Date: 12-12-21
 */
@Service("priceRconService")
public class PriceReconServiceImpl implements PriceReconService {
	
	private final static Logger logger = Logger.getLogger(PriceReconServiceImpl.class.getName());
	
	//@Autowired
	//private PreTradeDetailDao preTradeDetailDao;
	

    private void setDefaultValue(PreTradeDto preTrade) {
        Double payment = preTrade.getPayment();
        preTrade.setPayment(payment == null ? 0 : payment);
        Double shippingFee = preTrade.getShippingFee();
        preTrade.setShippingFee(shippingFee == null ? 0 : shippingFee);
        
        List<PreTradeDetail> preTradeDetails = preTrade.getPreTradeDetails();
        
        for (Iterator<PreTradeDetail> iterator = preTradeDetails.iterator(); iterator.hasNext(); ) {
            PreTradeDetail preTradeDetail =  iterator.next();
            Integer qty = preTradeDetail.getQty();
            qty = qty == null ? 0 : qty;

            preTradeDetail.setQty(qty);
            Double price = preTradeDetail.getPrice();
            preTradeDetail.setPrice(price == null ? 0 : price);
            Double upPrice = preTradeDetail.getUpPrice();
            preTradeDetail.setUpPrice(upPrice == null ? 0 : upPrice);

        }
    }

    public void reconPrice(PreTradeDto preTrade) {
        try {
            setDefaultValue(preTrade);
            
            String tradeId = preTrade.getTradeId();
            List<PreTradeDetail> preTradeDetails = preTrade.getPreTradeDetails();
            
            if (preTradeDetails.size() > 0) {
                //计算优惠金额（订单头与明细SUM(单价*数量-折扣))
                BigDecimal diff = checkSumPrice(preTrade);
                //不管diff是否为0， 都需要更新uprice
                assignDiff1(diff, preTrade);

                diff = checkSumPrice2(preTrade);
                BigDecimal zero = new BigDecimal(0);
                if (diff.compareTo(zero) != 0) {
                    boolean oddFound = false;
                    for (PreTradeDetail preTradeDetail : preTradeDetails) {
                        Integer qty = preTradeDetail.getQty();
                        if (qty == 1) {
                            oddFound = true;
                            Set<PreTradeDetail> assignedDetails = assignDiff2(diff, preTradeDetail);
                            //检查逻辑
                            preTrade.getPreTradeDetails().addAll(assignedDetails);
                            //this.savePreTradeDetails(preTrade, assignedDetails);
                            break;
                        }
                    }
                    //no odd qty found, use the first tradeDetail to assign diff
                    if (!oddFound && !preTradeDetails.isEmpty()) {
                        PreTradeDetail preTradeDetail = (PreTradeDetail) preTradeDetails.toArray()[0];
                        Set<PreTradeDetail> assignedDetails = assignDiff2(diff, preTradeDetail);
                        
                        //检查逻辑
                        preTrade.getPreTradeDetails().addAll(assignedDetails);
                        //this.savePreTradeDetails(preTrade, assignedDetails);
                    }
                }
            } else {
                String errMsg = preTrade.getErrMsg();
                errMsg = errMsg == null ? "" : errMsg;
                errMsg += " 无法进行价格匹配计算";
                preTrade.setIsValid(false);
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    /**
	 * <p></p>
	 * @param preTrade
	 * @param assignedDetails
	 */
	/*private void savePreTradeDetails(PreTrade preTrade, Set<PreTradeDetail> assignedDetails) {
		for (PreTradeDetail pd : assignedDetails) {
			pd.setTradeId(preTrade.getTradeId());
			pd.setPreTradeId(preTrade.getId());
			//TODO:
			// preTradeDetailDao.insertData(pd);
		}
	}*/

	/**
     * check the Payment - sum(Price * Qty)
     *
     * @param preTrade the different amount
     * @return difference in BigDecimal, 0 if no difference
     */
    public BigDecimal checkSumPrice(PreTradeDto preTrade) {
        //付款金额
        BigDecimal payment = new BigDecimal(preTrade.getPayment().toString());
        //运费
        BigDecimal shippingFee = new BigDecimal(preTrade.getShippingFee().toString());
        //明细汇总金额
        BigDecimal sumPayment = new BigDecimal(0);
        
        List<PreTradeDetail> preTradeDetails = preTrade.getPreTradeDetails();
        if(preTradeDetails==null)
            return sumPayment;
        for (PreTradeDetail preTradeDetail : preTradeDetails) {
            BigDecimal price = new BigDecimal(preTradeDetail.getPrice().toString());
            BigDecimal qty = new BigDecimal(preTradeDetail.getQty().toString());
            BigDecimal discountFee = new BigDecimal(0);
            if (preTradeDetail.getDiscountFee() != null){
                discountFee = new BigDecimal(preTradeDetail.getDiscountFee().toString());
            }
            sumPayment = sumPayment.add(price.multiply(qty).subtract(discountFee));
        }
        return payment.subtract(shippingFee).subtract(sumPayment);
    }

    public BigDecimal checkSumPrice2(PreTradeDto preTrade) {
        BigDecimal payment = new BigDecimal(preTrade.getPayment().toString());
        BigDecimal shippingFee = new BigDecimal(preTrade.getShippingFee().toString());
        BigDecimal sumPayment = new BigDecimal(0);
      
        List<PreTradeDetail> preTradeDetails = preTrade.getPreTradeDetails();// preTradeDetailDao.queryDetailByTradeId(preTrade.getTradeId());
        for (PreTradeDetail preTradeDetail : preTradeDetails) {
            //最后成交单价
            BigDecimal price = new BigDecimal(preTradeDetail.getUpPrice().toString());
            BigDecimal qty = new BigDecimal(preTradeDetail.getQty().toString());
            sumPayment = sumPayment.add(price.multiply(qty));
        }
        return payment.subtract(shippingFee).subtract(sumPayment);
    }

    public void assignDiff1(BigDecimal diff, PreTradeDto preTrade) {
        BigDecimal payment = new BigDecimal(0);
        
        List<PreTradeDetail> preTradeDetails = preTrade.getPreTradeDetails();// preTradeDetailDao.queryDetailByTradeId(preTrade.getTradeId());
        if(preTradeDetails==null)
            return;
        for (PreTradeDetail preTradeDetail : preTradeDetails) {
            BigDecimal price = new BigDecimal(preTradeDetail.getPrice().toString());
            BigDecimal qty = new BigDecimal(preTradeDetail.getQty().toString());
            BigDecimal discountFee = new BigDecimal(0);
            if (preTradeDetail.getDiscountFee() != null){
                discountFee = new BigDecimal(preTradeDetail.getDiscountFee().toString());
            }
            payment = payment.add(price.multiply(qty).subtract(discountFee));
        }
        BigDecimal sumPayment = new BigDecimal(0);
        
        for (PreTradeDetail preTradeDetail : preTradeDetails) {
            if (preTradeDetail.getQty().compareTo(0) > 0) {
                BigDecimal price = new BigDecimal(preTradeDetail.getPrice().toString());
                BigDecimal qty = new BigDecimal(preTradeDetail.getQty().toString());
                BigDecimal discountFee = new BigDecimal(0);
                if (preTradeDetail.getDiscountFee() != null){
                    discountFee = new BigDecimal(preTradeDetail.getDiscountFee().toString());
                }
                sumPayment = price.multiply(qty).subtract(discountFee);
                //BigDecimal priceDiff = diff.multiply(sumPayment.divide(payment, 8, BigDecimal.ROUND_HALF_UP)).divide(qty, 2, BigDecimal.ROUND_HALF_UP);
                //BigDecimal upPrice = price.add(priceDiff);
                BigDecimal amtDiff = diff.multiply(sumPayment.divide(payment, 8, BigDecimal.ROUND_HALF_UP));
                BigDecimal upPrice = sumPayment.add(amtDiff).divide(qty, 2, BigDecimal.ROUND_HALF_UP) ;
                upPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                //更新最后交易单价
                preTradeDetail.setUpPrice(upPrice.doubleValue());
                preTradeDetail.setIsActive(true);
            }
        }
    }

    /**
     * If sum(Price * Qty) has difference, assign the difference to new created PreTradeDetail.
     * The original PreTradeDetail's active set to false.
     *
     * @param diff           the different amount
     * @param preTradeDetail the target preTradeDetail need be recon for the difference
     * @return set of PreTradeDetail for new assigned PreTradeDetails
     */
    public Set<PreTradeDetail> assignDiff2(BigDecimal diff, PreTradeDetail preTradeDetail) {
        Set<PreTradeDetail> preTradeDetails = new HashSet<PreTradeDetail>(0);
        PreTradeDetail firstTradeDetail = new PreTradeDetail();
        BeanUtils.copyProperties(preTradeDetail, firstTradeDetail);
        firstTradeDetail.setIsActive(true);
        if (preTradeDetail.getQty().compareTo(1)== 0) {
            BigDecimal newPrice = new BigDecimal(preTradeDetail.getUpPrice().toString()).add(diff);
            firstTradeDetail.setUpPrice(newPrice.doubleValue());
            preTradeDetails.add(firstTradeDetail);
        } else if (preTradeDetail.getQty().compareTo(1)> 0){
            BigDecimal newPrice = diff.add(new BigDecimal(preTradeDetail.getUpPrice().toString()));
            firstTradeDetail.setQty(1);
            firstTradeDetail.setUpPrice(newPrice.doubleValue());
            preTradeDetails.add(firstTradeDetail);

            PreTradeDetail remainTradeDetail = new PreTradeDetail();
            BeanUtils.copyProperties(preTradeDetail, remainTradeDetail);
            remainTradeDetail.setQty(preTradeDetail.getQty() - 1);
            remainTradeDetail.setIsActive(true);
            preTradeDetails.add(remainTradeDetail);
        }
        preTradeDetail.setIsActive(false);
        return preTradeDetails;
    }
}
