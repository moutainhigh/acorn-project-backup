package com.chinadrtv.erp.task.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.core.service.BaseServiceImpl;
import com.chinadrtv.erp.task.dao.oms.PreTradeDao;
import com.chinadrtv.erp.task.entity.PreTrade;
import com.chinadrtv.erp.task.service.PreTradeService;

@Service
public class PreTradeServiceImpl extends BaseServiceImpl<PreTrade, Long> implements PreTradeService{

	@Autowired
	private PreTradeDao preTradeDao;
	
	@Override
	public BaseRepository<PreTrade, Long> getDao() {
		return preTradeDao;
	}
	
	@Override
	public List<Object[]> getAllPreTrade(Map<String, Object> parms, Integer state, Integer refundStatus, Integer refundStatusConfirm, Integer pageNo, Integer pageSize) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT A.ID,A.SOURCE_ID FROM PRE_TRADE A WHERE 1=1 ");
		sb.append(parms.containsKey("sourceId") ? " and  A.SOURCE_ID  = :sourceId " : "");

		// 待审核 impStatus 为 null 或0
		if (state == 1) sb.append(" and nvl(TO_NUMBER(A.IMP_STATUS),0) = 0 ");
		// 审核失败 impStatus !=13
		if (state == 2) sb.append(" and TO_NUMBER(nvl(A.IMP_STATUS,0)) != 13 and TO_NUMBER(nvl(A.IMP_STATUS,0)) != 14 and TO_NUMBER(nvl(A.IMP_STATUS,0)) != 17 and nvl(TO_NUMBER(A.FEEDBACK_STATUS),0) != 2 and TO_NUMBER(nvl(A.IMP_STATUS,0)) != 99  and nvl(TO_NUMBER(A.IMP_STATUS),0) != 0  ");
		// 已审核未发货 impStatus ==13 并且 feedbackStatus =="0" ,select
		// t.customizestatus from orderhist t where val(customizestatus,0) == 0
		if (state == 3) sb.append(" and (TO_NUMBER(nvl(A.IMP_STATUS,0)) = 13 or TO_NUMBER(nvl(A.IMP_STATUS,0)) = 14 or TO_NUMBER(nvl(A.IMP_STATUS,0)) = 17) and nvl(TO_NUMBER(A.FEEDBACK_STATUS),0) = 0");
		// 已发货待反馈 impStatus ==13 ,feedbackStatus =="0" || "4",
		// shipmentId(通过iagentID 到) select t.customizestatus from orderhist t
		// where customizestatus == 2
		// if(state == 4 )
		// sb.append(" and nvl(A.IMP_STATUS,0)=13 and (nvl(A.FEEDBACK_STATUS) = 0 or A.FEEDBACK_STATUS=4 )"
		// );
		// 标记完成
		if (state == 4) sb.append(" and TO_NUMBER(nvl(A.FEEDBACK_STATUS,0)) = 2 )");
		// 完成订单 impStatus ==13,feedbackStatus =="1"
		// 反馈失败
		if (state == 5) sb.append(" and TO_NUMBER(nvl(A.FEEDBACK_STATUS,0)) = 4 ");
		// 前置压单 impStatus == 99
		if (state == 6) sb.append(" and TO_NUMBER(nvl(A.IMP_STATUS,0)) = 99 ");

		// 有反馈
		if (refundStatus == 7) sb.append(" and nvl(TO_NUMBER(a.refundStatus),-1) != -1 ");
		// 无反馈
		if (refundStatus == 8) sb.append(" and nvl(TO_NUMBER(a.refundStatus),-1) = -1 ");

//		拒绝退款
//		if(refundStatus == 9 )
//			sb.append(" and (a.refundStatus = 3  or (  a.refundStatus = 1 and A.REFUND_STATUS_CONFIRM = 1 ))"
//		);
		// 收有可以审核的订单
		if (state == 10) sb.append(" AND TO_NUMBER(nvl(A.IMP_STATUS,0)) != 13 and TO_NUMBER(nvl(A.IMP_STATUS,0)) != 14 and TO_NUMBER(nvl(A.IMP_STATUS,0)) != 17 and nvl(TO_NUMBER(A.FEEDBACK_STATUS),0) != 2 and TO_NUMBER(nvl(A.IMP_STATUS,0)) != 99 ");

		if (refundStatusConfirm == -1) sb.append(" and nvl(TO_NUMBER(A.REFUND_STATUS_CONFIRM),-1) = -1 ");
		if (refundStatusConfirm == 1) sb.append(" and TO_NUMBER(nvl(A.REFUND_STATUS_CONFIRM,0)) = 1 ");
		if (refundStatusConfirm == 2) sb.append(" and TO_NUMBER(nvl(A.REFUND_STATUS_CONFIRM,0)) = 2 ");
		
		
		List<Object[]> list = preTradeDao.nativeQuery(sb.toString(), parms, pageNo, pageSize);

//		PageRequest pageable = new PageRequest(startPosition, maxResult) ;
//		Map<String, Object> values = new HashMap<String, Object>();
//		Page<PreTrade> page = preTradeDao.nativeQuery(PreTrade.class, "SELECT A.ID FROM PRE_TRADE A", values, pageable);
		return list;
	}

	@Override
	public void savePreTrade(PreTrade preTrade) {
		preTradeDao.save(preTrade);
	}

}
