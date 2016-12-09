package com.chinadrtv.erp.task.dao.oms;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.entity.PreTradeDetail;

@Repository
public interface PreTradeDetailDao extends BaseRepository<PreTradeDetail, Long> {

	@Query("from PreTradeDetail where tradeId = :preTradeId and isActive = :isActive and isVaid = :isVaid") 
	List<PreTradeDetail> getPreTradeDetailByPreTradeId(@Param("preTradeId") String preTradeId, @Param("isActive") Boolean isActive, @Param("isVaid") Boolean isVaid);
	
}
