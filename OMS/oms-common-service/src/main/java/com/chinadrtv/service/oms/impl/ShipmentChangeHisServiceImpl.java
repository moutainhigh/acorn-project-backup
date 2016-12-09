package com.chinadrtv.service.oms.impl;

import com.chinadrtv.dal.oms.dao.ShipmentChangeHisDao;
import com.chinadrtv.model.oms.ShipmentChangeHis;
import com.chinadrtv.model.oms.dto.SequenceDto;
import com.chinadrtv.service.oms.ShipmentChangeHisService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class ShipmentChangeHisServiceImpl implements ShipmentChangeHisService {

    @Value("${env_sync_batch_size:10}")
    public void setStrFetchSize(String strFetchSize)
    {
        try{
            fetchSize=Integer.parseInt(strFetchSize);
        }catch (Exception exp)
        {
            fetchSize=10;
        }
    }
    @Value("${env_sequenceStage:50}")
    public void setStrSeqBatch(String strSeqBatch)
    {
        try
        {
            sequenceBatch=Integer.parseInt(strSeqBatch);
        }catch (Exception exp)
        {
            sequenceBatch=50;
        }
    }

    //@Value("${env_sequenceStage}")
    private Integer sequenceBatch;

    //@Value("${env_sync_batch_size}")
    private Integer fetchSize;//如果序列为递增1，那么设置每次获取序列的数量

    @Autowired
    private ShipmentChangeHisDao shipmentChangeHisDao;

    @Override
    public List<ShipmentChangeHis> findHisFromShipments(List<String> shipmentIdList) {
        //目前底层直接直接选取出了最近变化的记录
        return shipmentChangeHisDao.findHisFromShipments(shipmentIdList);
    }

    @Override
    public void saveShipmentChangeHisList(List<ShipmentChangeHis> shipmentChangeHisList) {
        //1.目前序列乘50，那么只需要取一次
        if(sequenceBatch.intValue()>1)
        {
            if(shipmentChangeHisList.size()>sequenceBatch.intValue())
            {
                throw new RuntimeException("ShipmentChangeHis sequence batch size is not enough!");
            }

            List<SequenceDto> sequenceList = shipmentChangeHisDao.fecthSequence(1);
            Long index = sequenceList.get(0).getSeq()*sequenceBatch;
            for(ShipmentChangeHis shipmentChangeHis:shipmentChangeHisList)
            {
                shipmentChangeHis.setId(index++);
            }
        }
        else
        {
            //后期将改成递增1
            //那么获取序列并赋值
            int index=0;
            List<Long> sequenceList=this.fetchSequences(shipmentChangeHisList.size());
            for(ShipmentChangeHis shipmentChangeHis:shipmentChangeHisList)
            {
                shipmentChangeHis.setId(sequenceList.get(index++));
            }
        }

        for(ShipmentChangeHis shipmentChangeHis:shipmentChangeHisList)
        {
            shipmentChangeHisDao.insertData(shipmentChangeHis);
        }
    }

    private List<Long> fetchSequences(int size)
    {
        int count=size/fetchSize;
        int del=size%fetchSize;
        if(del>0)
            count++;
        List<Long> list=new ArrayList<Long>();
        for(int i=0;i<count;i++)
        {
            List<SequenceDto> sequenceDtoList= shipmentChangeHisDao.fecthSequence(fetchSize);
            for(SequenceDto sequenceDto:sequenceDtoList)
                list.add(sequenceDto.getSeq());
            //list.addAll(shipmentChangeHisDao.fecthSequence(fetchSize));
        }
        return list;
    }
}
