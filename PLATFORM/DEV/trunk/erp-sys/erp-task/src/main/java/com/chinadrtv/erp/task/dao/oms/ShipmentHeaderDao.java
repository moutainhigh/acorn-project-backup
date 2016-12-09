package com.chinadrtv.erp.task.dao.oms;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.entity.ShipmentHeader;

@Repository
public interface ShipmentHeaderDao extends BaseRepository<ShipmentHeader, Long> {
	
}
