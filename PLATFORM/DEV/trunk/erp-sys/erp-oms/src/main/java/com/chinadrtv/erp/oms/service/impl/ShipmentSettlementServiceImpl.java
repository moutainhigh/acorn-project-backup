package com.chinadrtv.erp.oms.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.oms.dto.ShipmentSettlementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.model.trade.ShipmentSettlement;
import com.chinadrtv.erp.oms.dao.ShipmentSettlementDao;
import com.chinadrtv.erp.oms.service.ShipmentSettlementService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * 结算单
 * User: gaodejian
 * Date: 13-4-9
 * Time: 上午10:22
 * To change this template use File | Settings | File Templates.
 */
@Service("shipmentSettlementService")
public class ShipmentSettlementServiceImpl extends GenericServiceImpl<ShipmentSettlement, Long> implements ShipmentSettlementService {

    @Autowired
    private ShipmentSettlementDao shipmentSettlementDao;

    @Override
    protected GenericDao<ShipmentSettlement, Long> getGenericDao() {
        return shipmentSettlementDao;
    }

    public Long getSettlementCount(Map<String, Object> params) {
        return shipmentSettlementDao.getSettlementCount(params);
    }

    public List<ShipmentSettlement> getSettlements(Map<String, Object> params, int index, int size) {
        return shipmentSettlementDao.getSettlements(params, index, size);
    }

    public List<ShipmentSettlement> getSettlements(Long[] settlementIds){
        return shipmentSettlementDao.getSettlements(settlementIds);
    }

	public ShipmentSettlement getById(Long id) {
		return shipmentSettlementDao.get(id);
	}

    /**
     * 通过ShipmentHeader查找ShipmentSettlement
     * @param shipmentHeader
     * @return
     */
	public List<ShipmentSettlement> getShipmentHeader(ShipmentHeader shipmentHeader) {
		return shipmentSettlementDao.findByShipmentHeader(shipmentHeader);
	}

    /**
     * 对指定的ShipmentHeader进行结算操作
     * @param sh
     * @param cc 如果是“上海EMS则允许误差上下浮动1元”
     */
	public void createShipmentSettlement(ShipmentHeader sh, CompanyContract cc) {
		
		if(sh==null){
			return;
		}
		
		AgentUser agentUser = SecurityHelper.getLoginUser();
		List<ShipmentSettlement> ssList = shipmentSettlementDao.findByShipmentHeader(sh);

		if(ssList!=null && ssList.size()>0){
			for(ShipmentSettlement ss : ssList){
				createShipmentSettlement(sh, cc, ss, agentUser);
			}
		}else{
			ShipmentSettlement ss = new ShipmentSettlement();
			createShipmentSettlement(sh, cc, ss, agentUser);
		}

	}
	
	/**
	 * 
	 * @param sh
	 * @param cc
	 * @param ss
	 * @param agentUser
	 */
	private void createShipmentSettlement(ShipmentHeader sh, CompanyContract cc, ShipmentSettlement ss, AgentUser agentUser){
		
		if(sh==null){
			return;
		}
		
		ss.setCreateUser(agentUser.getUserId());
		
		if(sh.getOrderId()!=null){
			ss.setOrderId(Long.parseLong(sh.getOrderId()));
		}
		
		ss.setNccompanyId(cc.getNccompanyId());
		
		if(sh.getEntityId()!=null){
			ss.setCompanyId(Long.parseLong(sh.getEntityId()));
		}
		
		if(sh.getAccountType()!=null){
			ss.setType(Long.parseLong(sh.getAccountType()));
		}
		
		BigDecimal apAmount = new BigDecimal(0);
        /*
		if(sh.getProdPrice()!=null){
			apAmount.add(sh.getProdPrice());
		}
		if(sh.getMailPrice()!=null){
			apAmount.add(sh.getMailPrice());
		}
		*/

        BigDecimal arAmount = new BigDecimal(0) ;
        if (sh.getProdPrice() != null){
            arAmount = arAmount.add(sh.getProdPrice()) ;
        }
        if (sh.getMailPrice() != null ){
            arAmount = arAmount.add(sh.getMailPrice()) ;
        }
        arAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        ss.setArAmount(arAmount.doubleValue()) ;
        /*
        if("1".equals(sh.getAccountType()) && sh.getProdPrice()!=null){
            ss.setArAmount(sh.getProdPrice().doubleValue());
        }
        else if("2".equals(sh.getAccountType()) && sh.getProdPrice()!=null){
			ss.setArAmount(sh.getProdPrice().doubleValue());
		}else if("3".equals(sh.getAccountType()) && sh.getMailPrice()!=null){
			ss.setArAmount(sh.getMailPrice().doubleValue());
		}
		*/
		
		ss.setIsSettled("0");
		
		ss.setApAmount(apAmount.doubleValue());
		ss.setShipmentHeader(sh);
		ss.setCreateDate(new Date());
		this.saveOrUpdate(ss);
	}

    /**
     * 获取结算单信息
     * @param params
     * @param index
     * @param size
     * @return
     */
    public List<ShipmentSettlementDto> getShipmentSettlementDtoInfo(Map<String, Object> params, int index, int size) {
        List objList = shipmentSettlementDao.getShipmentSettlementInfo(params,index,size);
        List<ShipmentSettlementDto> shipmentSettlementDtoList = new ArrayList<ShipmentSettlementDto>();
        Object[] obj = null;
        ShipmentSettlementDto shipmentSettlementDto = null;
        if(objList.size() > 0){
           for(int i=0;i<objList.size();i++){
               obj = (Object[])objList.get(i);
               shipmentSettlementDto = new ShipmentSettlementDto();
               shipmentSettlementDto.setId(Long.parseLong(obj[0].toString().trim()));
               shipmentSettlementDto.setCreateDate(obj[1] != null ? obj[1].toString() : "");
               shipmentSettlementDto.setRfoutdat(obj[2] != null ? obj[2].toString() : "");
               shipmentSettlementDto.setShipmentId(obj[3] != null ? obj[3].toString() : "");
               shipmentSettlementDto.setMailId(obj[4] != null ? obj[4].toString() : "");
               shipmentSettlementDto.setArAmount(obj[5] != null ? Double.parseDouble(obj[5].toString()) : 0.0);
               shipmentSettlementDto.setSettled(obj[6] != null ? obj[6].toString() : "");
               shipmentSettlementDto.setCompanyId(obj[7] != null ? obj[7].toString() : "");
               shipmentSettlementDto.setPaymentDate(obj[8] != null ? obj[8].toString() : "");
               shipmentSettlementDtoList.add(shipmentSettlementDto);
           }
        }

        return shipmentSettlementDtoList;
    }
}
