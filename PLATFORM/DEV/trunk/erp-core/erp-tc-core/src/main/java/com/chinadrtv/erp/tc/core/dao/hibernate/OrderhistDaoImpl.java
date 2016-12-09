package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.model.agent.MediaDnis;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.tc.core.dto.OrderAuditExtDto;
import com.chinadrtv.erp.tc.core.dto.OrderAuditQueryDto;
import com.chinadrtv.erp.tc.core.dto.OrderQueryDto;
import com.chinadrtv.erp.tc.core.model.CompanyAssignQuantity;
import com.chinadrtv.erp.tc.core.utils.OrderAddressChecker;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.utils.OrderhistValidChecker;

/**
 * Created with IntelliJ IDEA.
 * Date: 13-1-24
 */
@Repository
public class OrderhistDaoImpl extends GenericDaoHibernateBase<Order, Long> implements OrderhistDao {

    public OrderhistDaoImpl()
    {
        super(Order.class);
    }

    @Autowired
    private SchemaNames schemaNames;

	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}
	
    public String getOrderId(){
        Query q = getSession().createSQLQuery("select "+ schemaNames.getAgentSchema()+"seqorderhist.nextval from dual");
        return  q.list().get(0).toString();
    }

    /*
    * <p>Title: 根据业务订单号获取订单</p>
    * <p>Description: </p>
    * @param orderId
    * @return
    * @see com.chinadrtv.erp.tc.dao.OrderhistDao#getOrderHistByOrderid(java.lang.String)
     */
    public Order getOrderHistByOrderid(String orderId) {
    	String hql = "select oh from com.chinadrtv.erp.model.agent.Order oh where oh.orderid=:orderid";
    	Parameter<String> orderid = new ParameterString("orderid", orderId);
    	Order orderhist = this.find(hql, orderid);
    	return orderhist;
    }

    /**
     * 郝雷涛
     */
	public int updateOrderhist(String setStr, String treadid) throws Exception {
		String hql = "update Order "+setStr+ " where orderid= "+treadid;
		return this.execUpdate(hql);
		 
	}

	/* 
	 * 获取非套装产品信息
	* <p>Title: queryProdNonSuiteInfo</p>
	* <p>Description: </p>
	* @param orderdet
	* @return
	* @see com.chinadrtv.erp.tc.dao.OrderhistDao#queryProdNonSuiteInfo(com.chinadrtv.erp.model.agent.OrderDetail)
	*/ 
	public Map<String, Object> queryProdNonSuiteInfo(OrderDetail orderdet) {
		/*
		String prodId = orderdet.getProdid();
		String orderId = orderdet.getOrderid();
		String sql = "select e.ruid, e.plucode, c.dsc, e.issuiteplu " +
				" from orderhist a join orderdet b on a.orderid = b.orderid " +
				" join producttype c on b.producttype = c.recid " +
				" join plubasinfo e on b.prodid = e.nccode and nvl(c.dsc, '-1') = nvl(e.ncfreename, '-1') " +
				" where b.orderid='"+orderId+"' " +
				" and b.prodid='"+prodId+"' ";*/
        String sql="select e.ruid, e.plucode, c.dsc, e.issuiteplu, e.status " +
                " from "+schemaNames.getAgentSchema()+"producttype c " +//on b.producttype = c.recid
                " join "+schemaNames.getAgentSchema()+"plubasinfo e on nvl(c.dsc, '-1') = nvl(e.ncfreename, '-1')"+  //on b.prodid = e.nccode
                " where c.recid='"+orderdet.getProducttype()+"' "+
                " and e.nccode='"+orderdet.getProdid()+"'";

		Query query = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(sql)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		List<Map<String, Object>> resultList = query.list();
		if(null!= resultList && resultList.size()>0){
			Map<String, Object> result = resultList.get(0);
            if(result!=null)
            {
                Map<String, Object> mapRet=new HashMap<String,Object>();
                for(Map.Entry<String,Object> entry:result.entrySet())
                {
                    if(entry.getKey().equalsIgnoreCase("STATUS"))
                    {
                        mapRet.put("status",entry.getValue());
                    }
                    else
                    {
                        mapRet.put(entry.getKey(),entry.getValue());
                    }
                }
                return mapRet;
            }
			return result;
		}
		return null;
	}

    public Order getOrderHistByMailId(String mailId){
        return this.find("from Order a where a.mailid = :mailId",new ParameterString("mailId",mailId));
    }

	@Override
	public void saveOrUpdate(Order object) {
		String error = OrderhistValidChecker.checkOrderhist(object);
		if (StringUtils.isNotEmpty(error)) {
			throw new HibernateException(error);
		}
        //
        object.setMddt(new Date());
        if(StringUtils.isBlank(object.getMdusr()))
        {
            object.setMdusr(object.getCrusr());
        }
        if(StringUtils.isEmpty(object.getIsassign()))
        {
            object.setIsassign(null);
        }
		super.saveOrUpdate(object);
	}

    @Override
    public Order save(Order orderhist)
    {
        orderhist.setMddt(new Date());
        if(StringUtils.isBlank(orderhist.getMdusr()))
        {
            orderhist.setMdusr(orderhist.getCrusr());
        }
        return super.save(orderhist);
    }

    public List<CompanyAssignQuantity> getCurrentCompanyAssignQuantity()
    {
        List<CompanyAssignQuantity> companyAssignQuantityList=new ArrayList<CompanyAssignQuantity>();

        Date dtNow = new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String strTime = simpleDateFormat.format(dtNow);

        String sql="select entityid, SUM(totalprice), COUNT(1) from iagent.orderhist a where senddt >= trunc(SYSDATE)  " +
                " AND status in ('2', '5', '6') and entityid IS NOT NULL GROUP BY a.entityid ";
        SQLQuery query=this.getSession().createSQLQuery(sql);
        List<Object[]> list=query.list();
        if(list!=null)
        {
            for(Object[] objs : list)
            {
                if(objs[0]!=null)
                {
                    Long companyId=Long.parseLong(objs[0].toString());
                    BigDecimal price=new BigDecimal(objs[1].toString());
                    CompanyAssignQuantity companyAssignQuantityMatch=null;
                    for(CompanyAssignQuantity companyAssignQuantity:companyAssignQuantityList)
                    {
                        if(companyAssignQuantity.getCompanyId().equals(companyId))
                        {
                            companyAssignQuantityMatch=companyAssignQuantity;
                            break;
                        }
                    }
                    if(companyAssignQuantityMatch==null)
                    {
                        companyAssignQuantityMatch=new CompanyAssignQuantity();
                        companyAssignQuantityMatch.setCompanyId(companyId);
                        companyAssignQuantityMatch.setQuantity(1L);
                        companyAssignQuantityMatch.setTotalPrice(price);
                        companyAssignQuantityMatch.setMdDate(new Date());
                        companyAssignQuantityList.add(companyAssignQuantityMatch);
                    }
                    else
                    {
                        companyAssignQuantityMatch.setQuantity(companyAssignQuantityMatch.getQuantity()+1);
                        companyAssignQuantityMatch.setTotalPrice(companyAssignQuantityMatch.getTotalPrice().add(price));
                    }
                }
            }
        }

        /*try
        {
            Date dt=simpleDateFormat.parse(strTime);
            dtNow=dt;
        }catch (Exception exp)
        {

        }
        int index=0;
        final int batchSize=1000;
        int readSize=0;
        String hql = "select entityid,totalprice,status from Order where senddt>to_date(:senddt,'yyyy-MM-dd') and entityid is not null and status<>'0'";
        do{
            Query query = this.getSession().createQuery(hql);
            query.setParameter("senddt",strTime);
            query.setFirstResult(index);
            query.setMaxResults(batchSize);


            List<Object[]> list=query.list();
            if(list!=null)
            {
                readSize=list.size();
                for(Object[] objs : list)
                {
                    if(objs[0]!=null)
                    {
                        Long companyId=Long.parseLong(objs[0].toString());
                        BigDecimal price=new BigDecimal(objs[1].toString());
                        CompanyAssignQuantity companyAssignQuantityMatch=null;
                        for(CompanyAssignQuantity companyAssignQuantity:companyAssignQuantityList)
                        {
                            if(companyAssignQuantity.getCompanyId().equals(companyId))
                            {
                                companyAssignQuantityMatch=companyAssignQuantity;
                                break;
                            }
                        }
                        if(companyAssignQuantityMatch==null)
                        {
                            companyAssignQuantityMatch=new CompanyAssignQuantity();
                            companyAssignQuantityMatch.setCompanyId(companyId);
                            companyAssignQuantityMatch.setQuantity(1L);
                            companyAssignQuantityMatch.setTotalPrice(price);
                            companyAssignQuantityMatch.setMdDate(new Date());
                            companyAssignQuantityList.add(companyAssignQuantityMatch);
                        }
                        else
                        {
                            companyAssignQuantityMatch.setQuantity(companyAssignQuantityMatch.getQuantity()+1);
                            companyAssignQuantityMatch.setTotalPrice(companyAssignQuantityMatch.getTotalPrice().add(price));
                        }
                    }
                }
            }
            index+=batchSize;
        }while (readSize==batchSize);*/

        return companyAssignQuantityList;
    }

    @Value("${com.chinadrtv.erp.sales.core.OrderQueryDays}")
    private Integer orderQueryDays;
    public List<Order> findCorrelativeOrders(Order order)
    {
        //添加时间限制
        Map<String,Parameter> parmMap=new Hashtable<String, Parameter>();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("from Order where crdt>SYSDATE-:days");
        parmMap.put("days",new ParameterInteger("days",orderQueryDays));
        if((order.getAddress()!=null&&order.getAddress().getAddressId()!=null)
            &&(order.getGetcontactid()!=null))
        {
            stringBuilder.append(" and (address.addressId=:addressId or getcontactid=:getcontactid)");
            stringBuilder.append(" order by crdt desc");
            parmMap.put("addressId",new ParameterString("addressId",order.getAddress().getAddressId()));
            parmMap.put("getcontactid",new ParameterString("getcontactid",order.getGetcontactid()));

            return this.findList(stringBuilder.toString(),parmMap);
        }
        else if((order.getAddress()!=null&&order.getAddress().getAddressId()!=null)
                &&(order.getGetcontactid()==null))
        {
            stringBuilder.append(" and address.addressId=:addressId");
            stringBuilder.append(" order by crdt desc");
            parmMap.put("addressId",new ParameterString("addressId",order.getAddress().getAddressId()));

            return this.findList(stringBuilder.toString(),parmMap);
        }
        else if((order.getAddress()==null||order.getAddress().getAddressId()==null)
                &&(order.getGetcontactid()!=null))
        {
            stringBuilder.append(" and getcontactid=:getcontactid");
            stringBuilder.append(" order by crdt desc");
            parmMap.put("getcontactid",new ParameterString("getcontactid",order.getGetcontactid()));

            return this.findList(stringBuilder.toString(),parmMap);
        }
        else
        {
            return null;
        }
    }



    //******************************

    public List<Order> queryOrder(OrderQueryDto orderQueryDto)
    {
        //如果订单地址没有完全提供，那么在订单取出之后进行进行，而不连接多表
        //首先查找订单
        //然后查找相关运单
        DetachedCriteria criteria = getQueryCriteriaFromOrder(orderQueryDto);

        return this.hibernateTemplate.findByCriteria(criteria,orderQueryDto.getStartPos(),orderQueryDto.getPageSize());
    }

    public int getTotalCount(OrderQueryDto orderQueryDto)
    {
        DetachedCriteria criteria = getQueryCriteriaFromOrder(orderQueryDto);
        Long l= (Long)this.getHibernateTemplate().findByCriteria(criteria.setProjection(Projections.rowCount())).get(0);
        return l.intValue();
    }

    private DetachedCriteria getQueryCriteriaFromOrder(OrderQueryDto orderQueryDto) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Order.class);
        if(StringUtils.isNotEmpty(orderQueryDto.getOrderId()))
        {
            criteria.add(Restrictions.eq("orderid", orderQueryDto.getOrderId()));
        }
        if(StringUtils.isNotEmpty(orderQueryDto.getMailId()))
        {
            criteria.add(Restrictions.eq("mailid", orderQueryDto.getMailId()));
        }
        if(StringUtils.isNotEmpty(orderQueryDto.getParentOrderId()))
        {
            criteria.add(Restrictions.eq("parentid",orderQueryDto.getParentOrderId()));
        }
        if(StringUtils.isNotEmpty(orderQueryDto.getConfirm()))
        {
            if(orderQueryDto.getConfirm().equals("-1"))
            {
                criteria.add(Restrictions.eq("confirm","-1"));
            }
            else
            {
                criteria.add((Restrictions.isNull("confirm")));
            }
        }
        boolean bHaveTrack=StringUtils.isNotEmpty(orderQueryDto.getTrackUsr());
        if(StringUtils.isNotEmpty(orderQueryDto.getCrUsr())&&StringUtils.isNotEmpty(orderQueryDto.getTrackUsr()))
        {
            criteria.add(Restrictions.or(Restrictions.eq("crusr",orderQueryDto.getCrUsr()),
                    Restrictions.eq("trackUsr",orderQueryDto.getCrUsr())));
        }
        else if(StringUtils.isNotEmpty(orderQueryDto.getCrUsr())&&StringUtils.isEmpty(orderQueryDto.getTrackUsr()))
        {
            criteria.add(Restrictions.eq("crusr",orderQueryDto.getCrUsr()));
        }
        else if(StringUtils.isEmpty(orderQueryDto.getCrUsr())&&StringUtils.isNotEmpty(orderQueryDto.getTrackUsr()))
        {
            criteria.add(Restrictions.eq("trackUsr",orderQueryDto.getCrUsr()));
        }

        if(StringUtils.isNotEmpty(orderQueryDto.getStatus()))
        {
            criteria.add(Restrictions.eq("status",orderQueryDto.getStatus()));
        }
        Criterion criterionDate = getCriterionFromDate("crdt",orderQueryDto.getBeginCrdt(),orderQueryDto.getEndCrdt(),bHaveTrack);
        if(criterionDate!=null)
            criteria.add(criterionDate);
        criterionDate = getCriterionFromDate("rfoutdat",orderQueryDto.getBeginOutdt(),orderQueryDto.getEndOutdt(),bHaveTrack);
        if(criterionDate!=null)
            criteria.add(criterionDate);

        List<String> paytypeList=getPayTypeListFromDto(orderQueryDto);
        if(paytypeList!=null&&paytypeList.size()>0)
        {
            criteria.add(getCriterionFromList("paytype",paytypeList));
        }
        if(StringUtils.isNotEmpty(orderQueryDto.getProdId()))
        {
            criteria.createAlias("orderdets","OrderDetail");
            criteria.add(Restrictions.or(
                    Restrictions.or(
                            Restrictions.eq("OrderDetail.prodid",orderQueryDto.getProdId()),
                            Restrictions.eq("OrderDetail.prodname",orderQueryDto.getProdId())),
                    Restrictions.eq("OrderDetail.prodscode",orderQueryDto.getProdId())
            ));//Restrictions.eq("OrderDetail.prodid",orderQueryDto.getProdId())
        }
        if(orderQueryDto.getContactIdList()!=null&&orderQueryDto.getContactIdList().size()>0)
        {
            criteria.add(getCriterionFromList("contactid",orderQueryDto.getContactIdList()));
        }

        if(orderQueryDto.getGetContactIdList()!=null&&orderQueryDto.getGetContactIdList().size()>0)
        {
            criteria.add(getCriterionFromList("getcontactid",orderQueryDto.getGetContactIdList()));
        }

        if(orderQueryDto.getCheckResult()!=null)
        {
            //审批通过的，是否包含从来没有审批的订单？？？
            if(orderQueryDto.getCheckResult().intValue()==2)
                criteria.add(Restrictions.or(Restrictions.isNull("checkResult"),Restrictions.eq("checkResult",orderQueryDto.getCheckResult())));
            else
                criteria.add(Restrictions.eq("checkResult",orderQueryDto.getCheckResult()));

        }

        if(orderQueryDto.getCheckResultList()!=null&&orderQueryDto.getCheckResultList().size()>0)
        {
            //
            List<Criterion> criterionList=new ArrayList<Criterion>();
            for(Integer checkFlag:orderQueryDto.getCheckResultList())
            {
                if(checkFlag!=null)
                {
                    criterionList.add(Restrictions.eq("checkResult",checkFlag));
                }
                else
                {
                    criterionList.add(Restrictions.isNull("checkResult"));
                }
            }

            Criterion criterionTemp=criterionList.get(0);
            for(int i=1;i<criterionList.size();i++)
            {
                criterionTemp=Restrictions.or(criterionTemp,criterionList.get(i));
            }

            criteria.add(criterionTemp);
        }

        if(orderQueryDto.getAddressExt()!=null)
        {
            criteria.createAlias("address","AddressExt");
            if(StringUtils.isNotEmpty(orderQueryDto.getAddressExt().getAddressId()))
            {
                criteria.add(Restrictions.eq("AddressExt.addressId",orderQueryDto.getAddressExt().getAddressId()));
            }
            else
            {
                if(orderQueryDto.getAddressExt().getProvince()!=null&&orderQueryDto.getAddressExt().getProvince().getProvinceid()!=null)
                {
                    criteria.createAlias("AddressExt.province","Province");
                    criteria.add(Restrictions.eq("Province.provinceid",orderQueryDto.getAddressExt().getProvince().getProvinceid()));
                }
                if(orderQueryDto.getAddressExt().getCity()!=null&&orderQueryDto.getAddressExt().getCity().getCityid()!=null)
                {
                    criteria.createAlias("AddressExt.city","City");
                    criteria.add(Restrictions.eq("City.cityid",orderQueryDto.getAddressExt().getCity().getCityid()));
                }
                if(orderQueryDto.getAddressExt().getCounty()!=null&&orderQueryDto.getAddressExt().getCounty().getCountyid()!=null)
                {
                    criteria.createAlias("AddressExt.county","County");
                    criteria.add(Restrictions.eq("County.countyid",orderQueryDto.getAddressExt().getCounty().getCountyid()));
                }
                if(orderQueryDto.getAddressExt().getArea()!=null&&orderQueryDto.getAddressExt().getArea().getAreaid()!=null)
                {
                    criteria.createAlias("AddressExt.area","Area");
                    criteria.add(Restrictions.eq("Area.areaid",orderQueryDto.getAddressExt().getArea().getAreaid()));
                }
            }
        }

        if(orderQueryDto.getSortCrDate()!=null)
        {
            if(orderQueryDto.getSortCrDate().intValue()>0)
            {
                criteria.addOrder(org.hibernate.criterion.Order.asc("crdt"));
                //criteria.add()
            }
            else if(orderQueryDto.getSortCrDate().intValue()<0)
            {
                criteria.addOrder(org.hibernate.criterion.Order.desc("crdt"));
            }
        }
        return criteria;
    }


    private List<String> getPayTypeListFromDto(OrderQueryDto orderQueryDto)
    {
        if(StringUtils.isNotEmpty(orderQueryDto.getPayType()))
        {
            if(orderQueryDto.getPaytypeList()!=null&&orderQueryDto.getPaytypeList().size()>0)
            {
                List<String> paytypeList=orderQueryDto.getPaytypeList();
                if(!paytypeList.contains(orderQueryDto.getPayType()))
                {
                    paytypeList.add(orderQueryDto.getPayType());
                }

                return paytypeList;
            }
            else
            {
                List<String> list=new ArrayList<String>();
                list.add(orderQueryDto.getPayType());
                return list;
            }
        }
        else
        {
            if(orderQueryDto.getPaytypeList()!=null&&orderQueryDto.getPaytypeList().size()>0)
            {
                return orderQueryDto.getPaytypeList();
            }
            else
            {
                return null;
            }
        }
    }


    private SimpleDateFormat simpleDateFormatQuery=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Criterion getCriterionFromDate(String fldName, Date beginDate, Date endDate, boolean bHaveTrack)
    {
        if(beginDate!=null&&endDate!=null)
        {
            if(bHaveTrack)
            return Restrictions.sqlRestriction(String.format("({alias}.%s>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and {alias}.%s<=to_date(?,'yyyy-MM-dd hh24:mi:ss'))", fldName,fldName),
                    new Object[]{simpleDateFormatQuery.format(beginDate),simpleDateFormatQuery.format(endDate)},new Type[]{StringType.INSTANCE,StringType.INSTANCE});
            else
            return Restrictions.and(
                    Restrictions.gt(fldName,beginDate),
                    Restrictions.lt(fldName,endDate));
        }
        else
        {
            if(beginDate!=null)
                if(bHaveTrack)
                return Restrictions.sqlRestriction(String.format("{alias}.%s>=to_date(?,'yyyy-MM-dd hh24:mi:ss')",fldName),
                        new Object[]{simpleDateFormatQuery.format(beginDate)},new Type[]{StringType.INSTANCE});
                else
                    return Restrictions.gt(fldName,beginDate);
            if(endDate!=null)
                if(bHaveTrack)
                return Restrictions.sqlRestriction(String.format("{alias}.%s<=to_date(?,'yyyy-MM-dd hh24:mi:ss')",fldName),
                        new Object[]{simpleDateFormatQuery.format(endDate)},new Type[]{StringType.INSTANCE});
                else
                return Restrictions.lt(fldName,endDate);

        }
        return null;
    }

    private Criterion getCriterionFromList(String fldName, List<String> fldValueList)
    {
        Criterion criterion=Restrictions.eq(fldName,fldValueList.get(0));
        for(int i=1;i<fldValueList.size();i++)
        {
            criterion=Restrictions.or(criterion,Restrictions.eq(fldName,fldValueList.get(i)));
        }
        return criterion;
    }

    public List<OrderAuditExtDto> queryAuditOrder(OrderAuditQueryDto orderAuditQueryDto)
    {
        StringBuilder stringBuilder;
        if(StringUtils.isNotEmpty(orderAuditQueryDto.getProdId()))
        {
            stringBuilder=new StringBuilder("from Order a,UserBpm b inner join fetch  a.orderdets c where ");
        }
        else
        {
            stringBuilder=new StringBuilder("from Order a,UserBpm b where ");
        }
        Query query = getQueryFromAuditQuery(stringBuilder, orderAuditQueryDto);

        query.setFirstResult(orderAuditQueryDto.getStartPos());
        query.setMaxResults(orderAuditQueryDto.getPageSize());

        List<OrderAuditExtDto> orderAuditExtDtoList=new ArrayList<OrderAuditExtDto>();
        List<Object[]> objsList=(List<Object[]>)query.list();
        for(Object[] objs:objsList)
        {
            OrderAuditExtDto orderAuditExtDto=new OrderAuditExtDto();
            orderAuditExtDto.setOrder((Order)objs[0]);
            orderAuditExtDto.setUserBpm((UserBpm)objs[1]);
            orderAuditExtDtoList.add(orderAuditExtDto);
        }
        return orderAuditExtDtoList;
    }

    private Query getQueryFromAuditQuery(StringBuilder stringBuilder, OrderAuditQueryDto orderAuditQueryDto) {
        //从流程表和订单表联合查询

        //orderhistDao.findList();
        Map<String,Object> mapParms=new HashMap<String, Object>();
        boolean bFirst=true;
        if(StringUtils.isNotEmpty(orderAuditQueryDto.getOrderId()))
        {
            if(bFirst==true)
            {
                bFirst=false;
            }
            else
            {
                stringBuilder.append(" and ");
            }
            stringBuilder.append(" a.orderid=:orderid");
            mapParms.put("orderid",orderAuditQueryDto.getOrderId());
        }
        if(orderAuditQueryDto.getBeginCrdt()!=null)
        {
            if(bFirst==true)
            {
                bFirst=false;
            }
            else
            {
                stringBuilder.append(" and ");
            }
            stringBuilder.append(" a.crdt>=:beginCrdt");
            mapParms.put("beginCrdt",orderAuditQueryDto.getBeginCrdt());
        }
        if(orderAuditQueryDto.getEndCrdt()!=null)
        {
            if(bFirst==true)
            {
                bFirst=false;
            }
            else
            {
                stringBuilder.append(" and ");
            }
            stringBuilder.append(" a.crdt<=:endCrdt");
            mapParms.put("endCrdt",orderAuditQueryDto.getEndCrdt());
        }
        if(orderAuditQueryDto.getContactIdList()!=null&&orderAuditQueryDto.getContactIdList().size()>0)
        {
            int index=0;
            for(String contactId:orderAuditQueryDto.getContactIdList())
            {
                if(index==0)
                {
                    if(bFirst==true)
                    {
                        bFirst=false;
                    }
                    else
                    {
                        stringBuilder.append(" and ");
                    }
                    stringBuilder.append(" (");
                }
                else
                {
                    stringBuilder.append(" or ");
                }
                String parmName="contactid"+index;
                stringBuilder.append("a.contactid=:"+parmName);
                mapParms.put(parmName,contactId);
                index++;
            }
            stringBuilder.append(")");
        }
        /*if(StringUtils.isNotEmpty(orderAuditQueryDto.getCrUsr()))
        {
            if(bFirst==true)
            {
                bFirst=false;
            }
            else
            {
                stringBuilder.append(" and ");
            }
            stringBuilder.append(" a.crusr=:crusr");
            mapParms.put("crusr",orderAuditQueryDto.getCrUsr());
        }*/
        if(StringUtils.isNotEmpty(orderAuditQueryDto.getCrUsr()))
        {
            if(bFirst==true)
            {
                bFirst=false;
            }
            else
            {
                stringBuilder.append(" and ");
            }
            stringBuilder.append(" b.createUser=:crusr");
            mapParms.put("crusr",orderAuditQueryDto.getCrUsr());
        }

        if(StringUtils.isNotEmpty(orderAuditQueryDto.getPayType()))
        {
            if(bFirst==true)
            {
                bFirst=false;
            }
            else
            {
                stringBuilder.append(" and ");
            }
            stringBuilder.append(" a.paytype=:paytype");
            mapParms.put("paytype",orderAuditQueryDto.getPayType());
        }
        //busiType
        if(StringUtils.isNotEmpty(orderAuditQueryDto.getBusiType()))
        {
            if(bFirst==true)
            {
                bFirst=false;
            }
            else
            {
                stringBuilder.append(" and ");
            }
            stringBuilder.append(" b.busiType=:busiType");
            mapParms.put("busiType",orderAuditQueryDto.getBusiType());
        }


        if(bFirst==true)
        {
            bFirst=false;
        }
        else
        {
            stringBuilder.append(" and ");
        }
        stringBuilder.append("a.orderid=b.orderID");
        if(StringUtils.isNotEmpty(orderAuditQueryDto.getAuditStatus()))
        {
            stringBuilder.append(" and b.status=:status");
            mapParms.put("status",orderAuditQueryDto.getAuditStatus());
        }
        if(StringUtils.isNotEmpty(orderAuditQueryDto.getProdId()))
        {
            stringBuilder.append(" and (c.prodid=:prodid or c.prodscode=:prodscode or c.prodname=:prodname)");
            mapParms.put("prodid",orderAuditQueryDto.getProdId());
            mapParms.put("prodscode",orderAuditQueryDto.getProdId());
            mapParms.put("prodname",orderAuditQueryDto.getProdId());
            //stringBuilder.append("c.");
        }
        if(orderAuditQueryDto.getSortCrDate()!=null)
        {
            if(orderAuditQueryDto.getSortCrDate().intValue()>0)
            {
                stringBuilder.append(" order by b.createDate");
            }
            else if(orderAuditQueryDto.getSortCrDate().intValue()<0)
            {
                stringBuilder.append(" order by b.createDate desc");
            }
        }
        Query query = this.getSession().createQuery(stringBuilder.toString());
        for(Map.Entry<String,Object> entry:mapParms.entrySet())
        {
            query.setParameter(entry.getKey(),entry.getValue());
        }
        return query;
    }

    public int getAuditTotalCount(OrderAuditQueryDto orderAuditQueryDto)
    {
        StringBuilder stringBuilder;
        if(StringUtils.isNotEmpty(orderAuditQueryDto.getProdId()))
        {
            stringBuilder=new StringBuilder("select count(a.orderid) from Order a,UserBpm b inner join a.orderdets c where ");
        }
        else
        {
            stringBuilder=new StringBuilder("select count(a.orderid) from Order a,UserBpm b where ");

        }

        Query query=  getQueryFromAuditQuery(stringBuilder, orderAuditQueryDto);
        return Integer.parseInt(query.uniqueResult().toString());
    }

    public void flush()
    {
        this.getSession().flush();
    }

    @Override
    public List<String> queryN400ByDnis(String dnis) {
        Query query = getSession().createQuery("from MediaDnis m  where m.startdt <= sysdate  and m.enddt >=sysdate  and   m.dnis =:dnis");
        query.setParameter("dnis", dnis);

        List<MediaDnis> mediaDnisList = query.list();

        List<String> n400List = new ArrayList<String>();
        if (mediaDnisList == null || mediaDnisList.isEmpty()) {
            Query query2 = getSession().createQuery("from MediaDnis m  where    m.dnis =:dnis " +
                    " and m.enddt  =(select max(m2.enddt) from MediaDnis m2 where m2.dnis =:dnis ) ");
            query2.setParameter("dnis", dnis);
            mediaDnisList = query2.list();
        }
        if (mediaDnisList != null) {
            for (MediaDnis mediaDnis : mediaDnisList) {
                String n400 = mediaDnis.getN400();
                if (!n400List.contains(n400)) {
                    n400List.add(n400);
                }
            }
        }

        return n400List;
    }

    @Override
    public List<MediaDnis> queryOrderType(List<String> n400List, String area) {

        Query query = getSession().createQuery("from MediaDnis m  " +
                " where m.startdt <= sysdate  and m.enddt >=sysdate  and  m.n400 in (:n400List) and m.area =:area");
        query.setParameterList("n400List",n400List);
        query.setParameter("area",area);

        List mediaDnisList = query.list();

        if(mediaDnisList ==null || mediaDnisList.isEmpty()){
            Query query2 = getSession().createQuery("from MediaDnis m  " +
                    " where   m.n400 in (:n400List) and m.area =:area  " +
                    " and m.enddt  =(select max(m2.enddt) from MediaDnis m2 where m2.n400 in (:n400List) and m2.area =:area ) ");
            query2.setParameterList("n400List",n400List);
            query2.setParameter("area",area);

            mediaDnisList = query2.list();
        }
        return  mediaDnisList;
    }
}
