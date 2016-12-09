package com.chinadrtv.yuantong.common.dal;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.yuantong.common.dal.dao.WmsShipmentHeaderDao;
import com.chinadrtv.yuantong.common.dal.model.WmsShipmentDetail;
import com.chinadrtv.yuantong.common.dal.model.WmsShipmentHeader;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-2-13
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */
public class WmsShipmentHeaderDaoTests {
    private WmsShipmentHeaderDao dao;

    @Before
    public void init() {

        String[] contextFileArr = {"spring/applicationContext-dal.xml", "mybatis-config.xml"};
        String beanName = "wmsShipmentHeaderDao";
        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            dao = (WmsShipmentHeaderDao) appCont.getBean(beanName);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testMok()
    {
        if(dao!=null)
        {
            try{
                //获取推送数据
                List<WmsShipmentHeader> list =dao.findShipmentHeader();
                System.out.println("推送数据条数："+list.size());

                //获取商品明细
                List<WmsShipmentDetail> detailList = dao.findDetails("24555340V01");
                System.out.print("获取商品明细条数："+detailList.size());

                //更新
                /*int result = dao.updateShipmentHeader("24533832V01");
                System.out.println(result);*/


            }catch (Exception e){
                e.printStackTrace();
            }



        }
        else
        {
            System.out.println("it's error!");
        }
    }
}
