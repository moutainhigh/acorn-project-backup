package com.chinadrtv.erp.tc.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.AreaGroup;
import com.chinadrtv.erp.model.OrderAssignRule;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.tc.core.constant.cache.CacheNames;
import com.chinadrtv.erp.tc.core.dao.OrderAssignRuleDao;
import com.google.code.ssm.api.ReadThroughAssignCache;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-25
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderAssignRuleDaoImpl extends GenericDaoHibernateBase<OrderAssignRule,Long> implements OrderAssignRuleDao {

    public OrderAssignRuleDaoImpl()
    {
        super(OrderAssignRule.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")//暂时在同一个数据库中
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }


    public List<OrderAssignRule> findFromPartial(OrderAssignRule partialOrderAssignRule)
    {
        //根据传递进来的数据实例，来获取记录
        /*DetachedCriteria criteria=DetachedCriteria.forClass(OrderAssignRule.class);
        criteria.createAlias("orderPayTypes", "OPTs");
        criteria.createAlias("areaGroup", "GROUP");
        criteria.createAlias("GROUP.areaGroupDetails", "DETAIL");

        criteria.add(Restrictions.eq("GROUP.isActive",true));

        criteria.add(Example.create(partialOrderAssignRule));
        if(partialOrderAssignRule.getOrderChannel()!=null)
        {
            Criterion expressionOrderAll=null;
            for(OrderChannel orderChannel :partialOrderAssignRule.getOrderChannel()))
            {
                Criterion expression=null;
                if(orderPayType.getOrderType()!=null&&orderPayType.getOrderType().getId()!=null)
                {
                    Criterion orderTypeExpr=Restrictions.eq("OPTs.orderType.id",orderPayType.getOrderType().getId());
                    if(expression==null)
                    {
                        expression=orderTypeExpr;
                    }
                    else
                    {
                        expression= Restrictions.and(expression,orderTypeExpr);
                    }
                }

                if(orderPayType.getPayType()!=null&&orderPayType.getPayType().getId()!=null)
                {
                    Criterion payTypeExpr=Restrictions.eq("OPTs.payType.id",orderPayType.getPayType().getId());
                    if(expression==null)
                    {
                        expression=payTypeExpr;
                    }
                    else
                    {
                        expression= Restrictions.and(expression,payTypeExpr);
                    }
                }
                if(expression==null)
                {
                    continue;
                }
                if(expressionOrderAll==null)
                {
                    expressionOrderAll=expression;
                }
                else
                {
                    expressionOrderAll=Restrictions.or(expressionOrderAll,expression);
                }
            }
            if(expressionOrderAll!=null)
                criteria.add(expressionOrderAll);

        }

        if(partialOrderAssignRule.getAreaGroup()!=null&&partialOrderAssignRule.getAreaGroup().getAreaGroupDetails()!=null)
        {
            Criterion expressionAll=null;
            for(AreaGroupDetail areaGroupDetail :partialOrderAssignRule.getAreaGroup().getAreaGroupDetails())
            {
                Criterion expression=null;// Restrictions.and(null,null);

                if(areaGroupDetail.getProvinceId()!=null)
                {
                    Criterion proviceExpr=Restrictions.eq("DETAIL.provinceId",areaGroupDetail.getProvinceId());
                    if(expression==null)
                    {
                        expression=proviceExpr;
                    }
                    else
                    {
                        expression= Restrictions.and(expression,proviceExpr);
                    }
                }
                if(areaGroupDetail.getAreaId()!=null)
                {
                    Criterion areaExpr=Restrictions.eq("DETAIL.areaId",areaGroupDetail.getAreaId());
                    if(expression==null)
                    {
                        expression=areaExpr;
                    }
                    else
                    {
                        expression= Restrictions.and(expression,areaExpr);
                    }
                }
                if(areaGroupDetail.getCityId()!=null)
                {
                    Criterion cityExpr=Restrictions.eq("DETAIL.cityId",areaGroupDetail.getCityId());
                    if(expression==null)
                    {
                        expression=cityExpr;
                    }
                    else
                    {
                        expression= Restrictions.and(expression,cityExpr);
                    }
                }
                if(areaGroupDetail.getCountyId()!=null)
                {
                    Criterion countyExpr=Restrictions.eq("DETAIL.countyId",areaGroupDetail.getCountyId());
                    if(expression==null)
                    {
                        expression=countyExpr;
                    }
                    else
                    {
                        expression= Restrictions.and(expression,countyExpr);
                    }
                }

                if(expression==null)
                {
                    continue;
                }
                if(expressionAll==null)
                {
                    expressionAll=expression;
                }
                else
                {
                    expressionAll=Restrictions.or(expressionAll,expression);
                }
            }
            if(expressionAll!=null)
            {
                criteria.add(expressionAll);
            }
        }

        return this.hibernateTemplate.findByCriteria(criteria);*/
        return new ArrayList<OrderAssignRule>();
    }

    @ReadThroughAssignCache(namespace= CacheNames.CACHE_ORDER_ASSIGN_RULE, assignedKey = CacheNames.All, expiration=3000)
    public List<OrderAssignRule> getAllValidRules()
    {
        //过滤掉无效的
        List<OrderAssignRule> orderAssignRuleList= this.findList("from OrderAssignRule");// a where a.isActive=true
        List<OrderAssignRule> validOrderAssignRuleList =new ArrayList<OrderAssignRule>();
        if(orderAssignRuleList!=null)
        {
            for(OrderAssignRule orderAssignRule:orderAssignRuleList)
            {
                if(orderAssignRule.isActive()!=null&&orderAssignRule.isActive()==true)
                {
                    validOrderAssignRuleList.add(orderAssignRule);
                }
            }
        }

        List<OrderAssignRule> returnOrderAssignRuleList=new ArrayList<OrderAssignRule>();
        if(validOrderAssignRuleList!=null)
        {
            for(OrderAssignRule orderAssignRule:validOrderAssignRuleList)
            {
                OrderAssignRule orderAssignRule1=new OrderAssignRule();
                BeanUtils.copyProperties(orderAssignRule,orderAssignRule1);
                if(orderAssignRule.getAreaGroup()!=null)
                {
                    orderAssignRule1.setAreaGroup(new AreaGroup());
                    orderAssignRule1.getAreaGroup().setId(orderAssignRule.getAreaGroup().getId());
                }
                if(orderAssignRule.getOrderChannel()!=null)
                {
                    orderAssignRule1.setOrderChannel(new OrderChannel());
                    orderAssignRule1.getOrderChannel().setId(orderAssignRule.getOrderChannel().getId());
                }

                returnOrderAssignRuleList.add(orderAssignRule1);
            }
        }
        return returnOrderAssignRuleList;
    }
}
