package com.chinadrtv.postprice.wms.service.impl;

import com.chinadrtv.postprice.dal.iagent.model.PostPriceItem;
import com.chinadrtv.postprice.dal.wms.dao.WmsShipmentWeightDao;
import com.chinadrtv.postprice.dal.wms.model.WmsShipmentWeight;
import com.chinadrtv.postprice.util.SplitUtil;
import com.chinadrtv.postprice.wms.service.ShipmentWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class ShipmentWeightServiceImpl implements ShipmentWeightService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ShipmentWeightServiceImpl.class);

    private static final int fetchSize=10;

    @Autowired
    private WmsShipmentWeightDao wmsShipmentWeightDao;

    @Override
    public void fetchOrderWeights(List<PostPriceItem> postPriceItemList) {
        if(postPriceItemList==null||postPriceItemList.size()==0)
            return;
        List<List<PostPriceItem>> allList= SplitUtil.split(postPriceItemList,fetchSize);//splitList(postPriceItemList);
        for(List<PostPriceItem> itemList:allList)
        {
            this.fetchWeights(itemList);
        }
    }

    private void fetchWeights(List<PostPriceItem> postPriceItemList)
    {
        List<WmsShipmentWeight> wmsShipmentWeightList=this.fetchWmsWeights(postPriceItemList);
        if(wmsShipmentWeightList!=null)
        {
            for(PostPriceItem postPriceItem:postPriceItemList)
            {
                postPriceItem.setWeight(BigDecimal.ZERO);
                for(WmsShipmentWeight wmsShipmentWeight:wmsShipmentWeightList)
                {
                    if(wmsShipmentWeight.getOrderId().equals(postPriceItem.getOrderId()))
                    {
                        postPriceItem.setWeight(wmsShipmentWeight.getWeight());
                        break;
                    }
                }
            }
        }
    }

    private List<WmsShipmentWeight> fetchWmsWeights(List<PostPriceItem> postPriceItemList)
    {
        List<WmsShipmentWeight> wmsShipmentWeightList = new ArrayList<WmsShipmentWeight>();
        for(PostPriceItem item:postPriceItemList)
        {
            List<PostPriceItem> itemList=new ArrayList<PostPriceItem>();
            itemList.add(item);
            List<WmsShipmentWeight> weightList=wmsShipmentWeightDao.findShipmentWeight(itemList);
            if(weightList!=null&&weightList.size()>0)
            {
                logger.debug("orderId:"+item.getOrderId()+"-version:"+item.getOrderRefRevision()+"-weight:"+weightList.get(0).getWeight());
                wmsShipmentWeightList.addAll(weightList);
            }
            else
            {
                logger.warn("orderId:"+item.getOrderId()+"-version:"+item.getOrderRefRevision()+"-weight:none");
            }
        }
        return wmsShipmentWeightList;
    }

    /*private List<List<PostPriceItem>> splitList(List<PostPriceItem> list)
    {
        List<List<PostPriceItem>> listList=new ArrayList<List<PostPriceItem>>();

        int count=list.size() / fetchSize ;
        int lessCount=list.size()%fetchSize;

        for(int i=0;i<count;i++)
        {
            List itemList=new ArrayList();
            for(int j=0;j<fetchSize;j++)
            {
                itemList.add(list.get(i*fetchSize+j));
            }
            listList.add(itemList);

        }
        if(lessCount>0)
        {
            List itemList=new ArrayList();
            for(int j=count*fetchSize;j<list.size();j++)
            {
                itemList.add(list.get(j));
            }
            listList.add(itemList);
        }

        return listList;
    }*/

}
