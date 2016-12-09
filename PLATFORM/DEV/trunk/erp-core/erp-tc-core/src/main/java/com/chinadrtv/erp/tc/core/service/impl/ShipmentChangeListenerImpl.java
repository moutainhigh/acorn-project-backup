package com.chinadrtv.erp.tc.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.EntityMode;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.impl.SessionFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.dao.ShipmentChangeHisDao;
import com.chinadrtv.erp.tc.core.service.ShipmentChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-6
 */
@Service
public class ShipmentChangeListenerImpl implements ShipmentChangeListener,PostUpdateEventListener/*,PreUpdateEventListener*/ {
    private static final Logger logger = LoggerFactory.getLogger(ShipmentChangeListenerImpl.class);

    @Autowired(required = false)
    @Qualifier("sessionFactory")
    private SessionFactoryImpl sessionFactoryImpl;

    @Autowired
    private ShipmentChangeHisDao shipmentChangeHisDao;

    /**
     * 目前先放置在构造中，以后如果固定，可以放置在配置文件中
     */
    @PostConstruct
    public void installAsEventListener()
    {
         if(sessionFactoryImpl==null)
         {
             logger.info("sessionFactoryImpl is null");
             //System.out.println("just test");
         }
         else
         {
             //sessionFactoryImpl.getEventListeners().setPostUpdateEventListeners();
             if(sessionFactoryImpl.getEventListeners()==null)
             {
                 logger.info("sessionFactoryImpl event is null");
                 //System.out.println("just test event null");
             }
             else
             {
                 PostUpdateEventListener[] postUpdateEventListeners= sessionFactoryImpl.getEventListeners().getPostUpdateEventListeners();
                 List<PostUpdateEventListener> postUpdateEventListenerList=new ArrayList<PostUpdateEventListener>();
                 if(postUpdateEventListeners!=null)
                 {
                     for(PostUpdateEventListener postUpdateEventListener:postUpdateEventListeners)
                     {
                         postUpdateEventListenerList.add(postUpdateEventListener);
                     }
                 }
                 postUpdateEventListenerList.add(this);

                postUpdateEventListeners=postUpdateEventListenerList.toArray(new PostUpdateEventListener[postUpdateEventListenerList.size()]);

                 sessionFactoryImpl.getEventListeners().setPostUpdateEventListeners(postUpdateEventListeners);

                 logger.info("add post update event is succ!");
                 //System.out.println("it is auto wired");
             }
         }
    }

    public void onPostUpdate(PostUpdateEvent event)
    {
        if (event.getEntity() instanceof ShipmentHeader)
        {
            //保存修改日志
            //获取更新前值
            logger.info("begin shipment post update handle");
            int[] dirtyProperties= event.getPersister().findDirty(event.getState(),event.getOldState(),event.getEntity(),event.getSession());
            if(dirtyProperties!=null&&dirtyProperties.length>0)
            {
                logger.info("begin post update handle");

                ShipmentHeader newShipmentHeader=(ShipmentHeader)event.getEntity();
                ShipmentHeader oldShipmentHeader=new ShipmentHeader();


                BeanUtils.copyProperties(newShipmentHeader, oldShipmentHeader);

                for(int i=0;i<dirtyProperties.length;i++)
                {
                    int index=dirtyProperties[i];

                    event.getPersister().setPropertyValue(oldShipmentHeader,index,event.getOldState()[index], EntityMode.POJO);
                }

                //this.addShipmentChangeHisFromUpdate(event.getSession(),newShipmentHeader, oldShipmentHeader);

                if(this.shipmentChangeHisDao!=null)
                {
                    logger.info("begin shipmentChangeHisDao update");
                    this.shipmentChangeHisDao.addShipmentChangeHisFromUpdate(newShipmentHeader,oldShipmentHeader);
                    logger.info("end shipmentChangeHisDao update");
                }
                else
                {
                    logger.info("shipmentChangeHisDao is null");

                }

                logger.info("end post update handle");
                    //this.addShipmentChangeHisFromUpdate(event.getSession(), newShipmentHeader, oldShipmentHeader);
                //shipmentChangeHisDao.addShipmentChangeHisFromUpdate(newShipmentHeader,oldShipmentHeader);
            }

        }
    }

    /*public boolean onPreUpdate(PreUpdateEvent event)
    {
        if (event.getEntity() instanceof ShipmentHeader)
        {
            //保存修改日志
            //获取更新前值
            if(event.getOldState()!=null&&event.getOldState().length>0)
            {
                ShipmentHeader newShipmentHeader=(ShipmentHeader)event.getEntity();
                ShipmentHeader oldShipmentHeader=new ShipmentHeader();


                BeanUtils.copyProperties(newShipmentHeader, oldShipmentHeader);


                //event.getPersister().setPropertyValue();

                //shipmentHeader=oldShipmentHeader;

                //if(this.shipmentChangeHisService!=null)
                //this.addShipmentChangeHisFromUpdate(event.getSession(),newShipmentHeader, oldShipmentHeader);
                //shipmentChangeHisDao.addShipmentChangeHisFromUpdate(newShipmentHeader,oldShipmentHeader);
            }

        }

        return false;
    }*/

    /*public void addShipmentChangeHisFromUpdate(Session session,ShipmentHeader newShipmentHeader,ShipmentHeader oldShipmentHeader)
    {
        Transaction transaction=session.getTransaction();
        if(transaction.isActive())
        {
            int kk=0;
            kk++;
        }
        //根据更新前和更新后值，来记录日志
        ShipmentChangeHis shipmentChangeHis=new ShipmentChangeHis();

        shipmentChangeHis.setAfterAccountStatusId(newShipmentHeader.getAccountStatusId());
        shipmentChangeHis.setAfterAccountStatusRemark(newShipmentHeader.getAccountStatusRemark());
        shipmentChangeHis.setAfterEntityId(newShipmentHeader.getEntityId());
        shipmentChangeHis.setAfterLogisticsStatusId(newShipmentHeader.getLogisticsStatusId());
        shipmentChangeHis.setAfterLogisticsStatusRemark(newShipmentHeader.getLogisticsStatusRemark());
        shipmentChangeHis.setAfterMailId(newShipmentHeader.getMailId());
        shipmentChangeHis.setAfterWarehouseId(newShipmentHeader.getWarehouseId());

        shipmentChangeHis.setBeforeAccountStatusId(oldShipmentHeader.getAccountStatusId());
        shipmentChangeHis.setBeforeAccountStatusRemark(oldShipmentHeader.getAccountStatusRemark());
        shipmentChangeHis.setBeforeEntityId(oldShipmentHeader.getEntityId());
        shipmentChangeHis.setBeforeLogisticsStatusId(oldShipmentHeader.getLogisticsStatusId());
        shipmentChangeHis.setBeforeLogisticsStatusRemark(oldShipmentHeader.getLogisticsStatusRemark());
        shipmentChangeHis.setBeforeMailId(oldShipmentHeader.getMailId());
        shipmentChangeHis.setBeforeWarehouseId(oldShipmentHeader.getWarehouseId());

        shipmentChangeHis.setShipmentRefId(newShipmentHeader.getId());
        shipmentChangeHis.setDateTimeStamp(new Date());
        //TODO:处理程序名称
        shipmentChangeHis.setProcessStamp("");
        shipmentChangeHis.setShipmentId(newShipmentHeader.getShipmentId());
        shipmentChangeHis.setUserStamp(newShipmentHeader.getMdusr());

        //shipmentChangeHis.setId(550L);
        FlushMode flushMode=session.getFlushMode();
        boolean  bll=session.isDirty();
        session.saveOrUpdate(shipmentChangeHis);
        bll=session.isDirty();
        System.out.println(bll);
        //transaction.commit();
        //session.flush();
        //session.save(shipmentChangeHis);
        //this.save(shipmentChangeHis);

    } */
}
