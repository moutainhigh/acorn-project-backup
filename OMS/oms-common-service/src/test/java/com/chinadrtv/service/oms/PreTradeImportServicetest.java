package com.chinadrtv.service.oms;

import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.dto.PretradeEvalDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@TransactionConfiguration(defaultRollback = false)
public class PreTradeImportServicetest extends ServiceTest {

    @Autowired
    private PreTradeImportService preTradeImportService;

    @Test
    public void testMok()
    {

    }
    @Test
    public void testImport()  throws Exception
    {
        List<PreTradeDto> preTradeDtoList=new ArrayList<PreTradeDto>();
        PreTradeDto pretradeDto=new PreTradeDto();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

        pretradeDto.setOutCrdt(simpleDateFormat.parse("2013-09-30"));
        pretradeDto.setPayment(199D);
        pretradeDto.setTradeId("4317921328740401");
        pretradeDto.setOpsTradeId(pretradeDto.getTradeId());
        pretradeDto.setTradeType("98");
        pretradeDto.setInvoiceTitle("个人");
        pretradeDto.setBuyerId("严建超");
        pretradeDto.setBuyerNick("严建超");
        pretradeDto.setReceiverName("严建超");
        pretradeDto.setReceiverProvince("湖北");
        pretradeDto.setReceiverCity("鄂州市");
        pretradeDto.setReceiverArea("其他");
        pretradeDto.setReceiverCounty("鄂城区");
        pretradeDto.setReceiverAddress("燕机镇松山村严家湾68号");
        pretradeDto.setReceiverMobile("18727899896");
        pretradeDto.setSourceId(2L);

        PreTradeDetail preTradeDetail=new PreTradeDetail();
        preTradeDetail.setTradeId("43179213287404001");
        preTradeDetail.setOutSkuId("114010200202");
        preTradeDetail.setSkuId(114010200202L);
        preTradeDetail.setIsValid(true);
        preTradeDetail.setQty(1);
        preTradeDetail.setPrice(199D);
        preTradeDetail.setUpPrice(199D);
        //preTradeDetail

        pretradeDto.getPreTradeDetails().add(preTradeDetail);

        preTradeDtoList.add(pretradeDto);

        PreTradeLotDto preTradeLotDto=new PreTradeLotDto();
        preTradeLotDto.setPreTrades(preTradeDtoList);

        /*try
        {
            ObjectMapper objectMapper=new ObjectMapper();

            //PretradeEvalDto pretradeEvalDto=new PretradeEvalDto();

            String str=objectMapper.writeValueAsString(preTradeLotDto.getPreTrades());
            System.out.println(str);
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }*/

        preTradeImportService.importPretrades(preTradeLotDto);
    }

     @Test
    public void  test_SpiltPreTrades(){
         preTradeImportService.splitPreTrades("696926513016581");
    }


}
