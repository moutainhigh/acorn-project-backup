package com.chinadrtv.erp.ic.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.ic.dao.ItemInventoryDao;
import com.chinadrtv.erp.ic.model.NcRealTimeStockItem;
import com.chinadrtv.erp.ic.model.RealTimeStockItem;
import com.chinadrtv.erp.ic.model.SalesStockItem;
import com.chinadrtv.erp.ic.model.WmsRealTimeStockItem;
import com.chinadrtv.erp.model.inventory.ItemInventoryChannel;
import com.google.code.ssm.api.ParameterDataUpdateContent;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.UpdateSingleCache;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author cuiming
 * @version 1.0
 * @since 2013-1-28 下午6:17:40
 *
 * gaodejian 2013-2-22 重构
 */
@Repository
public class ItemInventoryDaoImpl extends
		GenericDaoHibernateBase<ItemInventoryChannel, Long> implements
		ItemInventoryDao {

    private static final String CACHE_KEY=  "com.chinadrtv.erp.ic.dao.hibernate.ItemInventoryDaoImpl";

	public ItemInventoryDaoImpl() {
		super(ItemInventoryChannel.class);
	}

	/*
	 * 
	 * nc查询 (非 Javadoc) <p>Title: getItemInventoryChannelList</p>
	 * <p>Description: </p>
	 * 
	 * @param chanel
	 * 
	 * @param ncfree
	 * 
	 * @param spellcode
	 * 
	 * @param warehouses
	 * 
	 * @param locationType
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.ic.dao.ItemNcInventoryDao#getItemInventoryChannelList
	 * (java.lang.String, java.util.List, java.util.List, java.lang.String,
	 * java.lang.String)
	 */

	public List<ItemInventoryChannel> getItemNcInventoryChannel(String channel,
			List nccode, List spellcode, String warehouses, String locationType) {
		StringBuilder sqlBuilder = new StringBuilder(
				"select  {i.*}  from iagent.plubasInfo p , ACOAPP_OMS.ITEM_INVENTORY_CHANNEL i where  "
						+ "    p.ruid=i.product_Id ");
		Map map = new HashMap();
		if (channel != null && !("").equals(channel)
				&& !("null").equals(channel)) {
			sqlBuilder.append(" and i.channel =:channel");
			map.put("channel", channel);
		}
		if (nccode != null && !nccode.isEmpty()) {
			ArrayList ids = new ArrayList<String>();
			for (int i = 0; i < nccode.size(); i++) {
				ids.add(nccode.size());
			}
			sqlBuilder.append(" and p.nccode in (:nccode)");
			map.put("nccode", nccode);
		}
		if (spellcode != null && !spellcode.isEmpty()) {
			ArrayList ids = new ArrayList<String>();
			for (int i = 0; i < spellcode.size(); i++) {
				ids.add(spellcode.get(i).toString());
			}
			sqlBuilder.append(" and p.spellcode in (:spellcode)");
			map.put("spellcode", ids);
		}
		// 多个仓库编码 用,逗号分割
		if (warehouses != null) {
			String[] warehousess = warehouses.split(",");
			ArrayList<String> ids = new ArrayList<String>();
			for (int i = 0; i < warehousess.length; i++) {
				ids.add(warehousess[i]);
			}
			sqlBuilder.append(" and i.warehouse in (:warehouse)");
			map.put("warehouse", ids);
		}
		if (locationType != null && !("").equals(locationType)) {
			String[] locationTypes = locationType.split(",");
			ArrayList<String> ids = new ArrayList<String>();
			for (int i = 0; i < locationTypes.length; i++) {
				ids.add(locationTypes[i]);
			}
			sqlBuilder.append(" and i.location_type in (:location_type)");
			map.put("location_type", ids);
		}
		// 查询
		Query query = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlBuilder.toString())
				.addEntity("i", ItemInventoryChannel.class);
		query = getParams(map, query);
		List<ItemInventoryChannel> result = query.list();

		return result;
	}

	/**
	 * query 参数过滤
	 * 
	 * @Description: TODO
	 * @param map
	 * @param query
	 * @return
	 * @return Query
	 * @throws
	 */
	public Query getParams(Map map, Query query) {
		if (map.containsKey("channel")) {
			query.setParameter("channel", map.get("channel"));
		}
		if (map.containsKey("nccode")) {
			query.setParameterList("nccode", (List) map.get("nccode"));
		}
		if (map.containsKey("product_Id")) {
			query.setParameterList("product_Id", (List) map.get("product_Id"));
		}
		if (map.containsKey("plucode")) {
			query.setParameterList("plucode", (List) map.get("plucode"));
		}
		if (map.containsKey("spellcode")) {
			query.setParameterList("spellcode", (List) map.get("spellcode"));
		}
		if (map.containsKey("warehouse")) {
			query.setParameterList("warehouse", (List) map.get("warehouse"));
		}
		if (map.containsKey("location_type")) {
			query.setParameterList("location_type",
					(List) map.get("location_type"));
		}
		return query;
	}

	/**
	 * sku 查询
	 */
	public List<ItemInventoryChannel> getItemSkuInventoryChannel(
			String channel, List productId, List plucode, List spellcode,
			String warehouses, String locationType) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		StringBuilder sqlBuilder = new StringBuilder(
				"select  {i.*}  from iagent.plubasInfo p , ACOAPP_OMS.ITEM_INVENTORY_CHANNEL i where  "
						+ "    p.ruid=i.product_Id ");

		if (channel != null && !("").equals(channel)
				&& !("null").equals(channel)) {
			sqlBuilder.append(" and i.channel =:channel");
			map.put("channel", channel);
			// query.setParameter("channel", channel);
		}
		if (productId != null && !productId.isEmpty()) {
			ArrayList ids = new ArrayList<String>();
			for (int i = 0; i < productId.size(); i++) {
				ids.add(Long.valueOf(productId.get(i).toString()));
			}
			sqlBuilder.append(" and i.product_Id in (:product_Id)");
			map.put("product_Id", ids);
		}
		if (plucode != null && !plucode.isEmpty()) {
			ArrayList ids = new ArrayList<String>();
			for (int i = 0; i < plucode.size(); i++) {
				ids.add(plucode.get(i).toString());
			}
			sqlBuilder.append(" and p.plucode in (:plucode)");
			map.put("plucode", ids);
		}
		if (spellcode != null && !spellcode.isEmpty()) {
			ArrayList ids = new ArrayList<String>();
			for (int i = 0; i < spellcode.size(); i++) {
				ids.add(spellcode.get(i).toString());
			}
			sqlBuilder.append(" and p.spellcode in (:spellcode)");
			map.put("spellcode", ids);
		}
		// 多个仓库编码 用,逗号分割
		if (warehouses != null) {
			String[] warehousess = warehouses.split(",");
			ArrayList<String> ids = new ArrayList<String>();
			for (int i = 0; i < warehousess.length; i++) {
				ids.add(warehousess[i]);
			}
			sqlBuilder.append(" and i.warehouse in (:warehouse)");
			map.put("warehouse", ids);
		}
		if (locationType != null && !("").equals(locationType)) {
			String[] locationTypes = locationType.split(",");
			ArrayList<String> ids = new ArrayList<String>();
			for (int i = 0; i < locationTypes.length; i++) {
				ids.add(locationTypes[i]);
			}
			sqlBuilder.append(" and i.location_type in (:location_type)");
			map.put("location_type", ids);
		}
		// 查询
		Query query = this.hibernateTemplate.getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlBuilder.toString())
				.addEntity("i", ItemInventoryChannel.class);
		query = getParams(map, query);
		List<ItemInventoryChannel> result = query.list();
		return result;
	}

    public List<ItemInventoryChannel> getAllItemInventories() {
        return getAll();
    }

    /**
     * 获取缓存中的库存项,如果不存在查询数据库建立缓存
     * @param channel
     * @param warehouse
     * @param locationType
     * @param productId
     * @return
     */
    @ReadThroughSingleCache(namespace = CACHE_KEY, expiration = 300)
    public ItemInventoryChannel getCacheItemInventory(
            @ParameterValueKeyProvider(order = 0) final String channel,
            @ParameterValueKeyProvider(order = 1) final String warehouse,
            @ParameterValueKeyProvider(order = 2) final String locationType,
            @ParameterValueKeyProvider(order = 3) final Long productId)
    {
        Session session = getSession();
        String hql = "from ItemInventoryChannel where channel=:channel and warehouse =:warehouse and  locationType=:locationType  and productId =:productId ";
        ItemInventoryChannel itemInventoryChannel = null;
        Query query = session.createQuery(hql);
        query.setParameter("channel", channel);
        query.setParameter("warehouse", warehouse);
        query.setParameter("locationType", locationType);
        query.setParameter("productId", productId);
        List list = query.list();
        if (list != null && !list.isEmpty()) {
            itemInventoryChannel = (ItemInventoryChannel) query.list().get(0);
            //必须让对象脱离Session,否则对象可能被变更提交
            session.evict(itemInventoryChannel);
        }
        return itemInventoryChannel;
    }

    /**
     * 保存库存项到缓存
     * @param channel
     * @param warehouse
     * @param locationType
     * @param productId
     * @param itemInventoryChannel
     */
    @UpdateSingleCache(namespace = CACHE_KEY, expiration = 300)
    public void saveCacheItemInventory(
            @ParameterValueKeyProvider(order = 0) final String channel,
            @ParameterValueKeyProvider(order = 1) final String warehouse,
            @ParameterValueKeyProvider(order = 2) final String locationType,
            @ParameterValueKeyProvider(order = 3) final Long productId,
            @ParameterDataUpdateContent ItemInventoryChannel itemInventoryChannel) {
       /* do nothing */
    }

	public ItemInventoryChannel getDbItemInventory(String channel,
			String warehouse, String locationType, Long productId) {
		Session session = getSession();
		Query query = session
				.createQuery("from ItemInventoryChannel a "
						+ "where a.channel = :channel and a.warehouse = :warehouse and "
						+ "a.locationType = :locationType and a.productId = :productId");
		query.setString("channel", channel);
		query.setString("warehouse", warehouse);
		query.setString("locationType", locationType);
		query.setParameter("productId", productId);
		List list = query.list();
		return list == null || list.isEmpty() ? null : (ItemInventoryChannel) query.list().get(0);
	}

    public List<RealTimeStockItem> getDbRealTimeStock(Map<String, Object> params){
            Session session = getSession();
            Query q = session.createSQLQuery(
                    "select m.ruid as productId, m.plucode as productCode, m.pluname as productName, m.nccode, m.ncfree, m.ncfreename, m.spellCode, m.spellname,\n" +
                     (params.containsKey("grpid") ? "(select LPRICE from IAGENT.productgrplimit c where c.prodid=m.nccode and c.grpId = :grpid and c.status='-1' and c.startdt <= sysdate and c.enddt > sysdate and rownum < 2)":"''") + " as groupPrice," +
                    "  nvl(m.SLPRC,0) as listPrice, nvl(m.HSLPRC,0) as maxPrice, nvl(m.LSLPRC,0) as minPrice, \n" +
                    "  decode(m.status,'1','0','2','-1','3','0','4','-1','5','-1','6','-1','7','-1','8','-1','9','0','-1') as status,\n" +
                    "  n.onHandQty,n.inTransitQty,n.availableQty from iagent.plubasinfo m \n" +
                    "inner join (\n" +
                    "  --单品库存(带规格)\n" +
                    "  select b.plucode,\n" +
                    "    sum(nvl(a.on_hand_qty,0)) as onHandQty,\n" +
                    "    sum(nvl(a.in_transit_purchase_qty,0) + nvl(a.in_transit_movement_qty, 0) + nvl(a.in_transit_other_qty,0)) as inTransitQty,\n" +
                    "    sum(nvl(a.on_hand_qty,0) - nvl(a.frozen_qty, 0) - nvl(a.allocated_qty,0) - nvl(a.allocated_distribution_qty,0) - nvl(a.allocated_other_qty,0)) as availableQty \n" +
                    "  from acoapp_oms.item_inventory_channel a \n" +
                    "  right join iagent.plubasinfo b on a.product_id = b.ruid and b.NCFREESTATUS=1 \n" +
                    "  where a.location_type='1' \n" +
                    (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where b.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where b.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                    (params.containsKey("nccode") ? "and b.nccode = :nccode " : "") +
                    (params.containsKey("plucode") ? "and b.plucode = :plucode " : "") +
                    (params.containsKey("pluname") ? "and b.pluname = :pluname " : "") +
                    (params.containsKey("spellcode") ? "and b.spellcode = :spellcode " : "") +
                    (params.containsKey("spellname") ? "and b.spellname = :spellname " : "") +
                    (params.containsKey("ncfree") ? "and b.ncfree = :ncfree " : "") +
                    (params.containsKey("ncfreename") ? "and b.ncfreename = :ncfreename " : "") +
                    "  group by b.plucode\n" +
                    "  union all\n" +
                    "  --套装库存(带规格)\n" +
                    "  select a.prodsuitescmid,\n" +
                    "         nvl(min(a.onHandQty),0) as onHandQty,\n" +
                    "         decode(sign(min(a.inTransitQty)), 1, min(a.inTransitQty), -1, 0, 0) as inTransitQty,\n" +
                    "         decode(sign(min(a.availableQty)), 1, min(a.availableQty), -1, 0, 0) as availableQty\n" +
                    "  from \n" +
                    "  (--多套装NC库存(带规格) \n" +
                    "     select aa.prodsuitescmid, aa.prodsuiteid, aa.prodsuitetype, aa.ncCode, aa.product_id, aa.product_code,       \n" +
                    "            aa.pluname, aa.spellCode, aa.spellname,\n" +
                    "            min(aa.onHandQty) as onHandQty,  min(aa.inTransitQty) as inTransitQty, min(availableQty) as availableQty\n" +
                    "     from iagent.plubasinfo bb\n" +
                    "     inner join \n" +
                    "     (\n" +
                    "          --获取NC对应多套装库存(带规格)\n" +
                    "          select ddd.prodsuitescmid, ddd.prodsuitetype, ddd.prodsuiteid, bbb.ncCode, aaa.product_id, aaa.product_code, bbb.pluname, bbb.spellCode, bbb.spellname,        \n" +
                    "          floor(sum(nvl(aaa.on_hand_qty,0))/avg(ddd.prodnum)) as onHandQty,            \n" +
                    "          floor(sum(nvl(aaa.in_transit_purchase_qty,0) + nvl(aaa.in_transit_movement_qty, 0) + nvl(aaa.in_transit_other_qty,0))/avg(ddd.prodnum)) as inTransitQty, \n" +
                    "          floor(sum(nvl(aaa.on_hand_qty,0) - nvl(aaa.frozen_qty, 0) - nvl(aaa.allocated_qty,0) - nvl(aaa.allocated_distribution_qty,0) - nvl(aaa.allocated_other_qty,0))/avg(ddd.prodnum)) as availableQty\n" +
                    "          from acoapp_oms.item_inventory_channel aaa \n" +
                    "          right join iagent.plubasinfo bbb on aaa.product_id = bbb.ruid\n" +
                    "          left join iagent.productsuitetype ddd on bbb.nccode = ddd.prodid and bbb.plucode=ddd.PRODSCMID \n" +
                    "          where aaa.location_type='1' and ddd.prodnum > 0 " +
                               (params.containsKey("nccode") ? "and ddd.prodsuiteid = :nccode " : "") +
                               (params.containsKey("ncfreename") ? "and ddd.prodsuitetype = :ncfreename " : "") +
                    "          group by ddd.prodsuitescmid, ddd.prodsuitetype, ddd.prodsuiteid, bbb.ncCode, aaa.product_id, aaa.product_code, bbb.pluname, bbb.spellCode, bbb.spellname\n" +
                    "      ) aa on aa.prodsuitescmid = bb.plucode\n" +
                    "      where bb.NCFREESTATUS=1 \n" +
                            (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where bb.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where bb.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                            (params.containsKey("nccode") ? "and bb.nccode = :nccode " : "") +
                            (params.containsKey("plucode") ? "and bb.plucode = :plucode " : "") +
                            (params.containsKey("pluname") ? "and bb.pluname = :pluname " : "") +
                            (params.containsKey("spellcode") ? "and bb.spellcode = :spellcode " : "") +
                            (params.containsKey("spellname") ? "and bb.spellname = :spellname " : "") +
                            (params.containsKey("ncfree") ? "and bb.ncfree = :ncfree " : "") +
                            (params.containsKey("ncfreename") ? "and bb.ncfreename = :ncfreename " : "") +
                    "      group by aa.prodsuitescmid, aa.prodsuiteid, aa.prodsuitetype, aa.ncCode, aa.product_id, aa.product_code, aa.pluname, aa.spellCode, aa.spellname\n" +
                    "   ) a group by a.prodsuitescmid \n" +
                    ") n on m.plucode=n.plucode order by m.plucode asc"
            )
            .addScalar("ncCode", StringType.INSTANCE)
            .addScalar("productId", LongType.INSTANCE)
            .addScalar("productCode", StringType.INSTANCE)
            .addScalar("productName", StringType.INSTANCE)
            .addScalar("ncfree", StringType.INSTANCE)
            .addScalar("ncfreeName", StringType.INSTANCE)
            .addScalar("spellCode", StringType.INSTANCE)
            .addScalar("spellName", StringType.INSTANCE)
            .addScalar("onHandQty", DoubleType.INSTANCE)
            .addScalar("inTransitQty", DoubleType.INSTANCE)
            .addScalar("availableQty", DoubleType.INSTANCE)
            .addScalar("groupPrice", DoubleType.INSTANCE)
            .addScalar("listPrice", DoubleType.INSTANCE)
            .addScalar("maxPrice", DoubleType.INSTANCE)
            .addScalar("minPrice", DoubleType.INSTANCE)
            .addScalar("status", IntegerType.INSTANCE)
            .setResultTransformer(Transformers.aliasToBean(RealTimeStockItem.class));

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)) {
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }

            return q.list();
    }

    public List<RealTimeStockItem> getDbRealTimeAllStock(Map<String, Object> params){
        Session session = getSession();
        Query q = session.createSQLQuery(
                "select m.ruid as productId, m.plucode as productCode, m.pluname as productName, m.nccode, m.ncfree, m.ncfreename, m.spellCode, m.spellname,\n" +
                        (params.containsKey("grpid") ? "(select LPRICE from IAGENT.productgrplimit c where c.prodid=m.nccode and c.grpId = :grpid and c.status='-1' and c.startdt <= sysdate and c.enddt > sysdate and rownum < 2)":"''") + " as groupPrice," +
                        "  nvl(m.SLPRC,0) as listPrice, nvl(m.HSLPRC,0) as maxPrice, nvl(m.LSLPRC,0) as minPrice, \n" +
                        "  decode(m.status,'1','0','2','-1','3','0','4','-1','5','-1','6','-1','7','-1','8','-1','9','0','-1') as status,\n" +
                        "  n.onHandQty,n.inTransitQty,n.availableQty from iagent.plubasinfo m \n" +
                        "inner join (\n" +
                        "  --单品库存(带规格)\n" +
                        "  select b.plucode,\n" +
                        "    sum(nvl(a.on_hand_qty,0)) as onHandQty,\n" +
                        "    sum(nvl(a.in_transit_purchase_qty,0) + nvl(a.in_transit_movement_qty, 0) + nvl(a.in_transit_other_qty,0)) as inTransitQty,\n" +
                        "    sum(nvl(a.on_hand_qty,0) - nvl(a.frozen_qty, 0) - nvl(a.allocated_qty,0) - nvl(a.allocated_distribution_qty,0) - nvl(a.allocated_other_qty,0)) as availableQty \n" +
                        "  from acoapp_oms.item_inventory_channel a \n" +
                        "  right join iagent.plubasinfo b on a.product_id = b.ruid and b.NCFREESTATUS in (0,1) \n" +
                        "  where a.location_type='1' \n" +
                        (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where b.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where b.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                        (params.containsKey("nccode") ? "and b.nccode = :nccode " : "") +
                        (params.containsKey("plucode") ? "and b.plucode = :plucode " : "") +
                        (params.containsKey("pluname") ? "and b.pluname = :pluname " : "") +
                        (params.containsKey("spellcode") ? "and b.spellcode = :spellcode " : "") +
                        (params.containsKey("spellname") ? "and b.spellname = :spellname " : "") +
                        (params.containsKey("ncfree") ? "and b.ncfree = :ncfree " : "") +
                        (params.containsKey("ncfreename") ? "and b.ncfreename = :ncfreename " : "") +
                        "  group by b.plucode\n" +
                        "  union all\n" +
                        "  --套装库存(带规格)\n" +
                        "  select a.prodsuitescmid,\n" +
                        "         nvl(min(a.onHandQty),0) as onHandQty,\n" +
                        "         decode(sign(min(a.inTransitQty)), 1, min(a.inTransitQty), -1, 0, 0) as inTransitQty,\n" +
                        "         decode(sign(min(a.availableQty)), 1, min(a.availableQty), -1, 0, 0) as availableQty\n" +
                        "  from \n" +
                        "  (--多套装NC库存(带规格) \n" +
                        "     select aa.prodsuitescmid, aa.prodsuiteid, aa.prodsuitetype, aa.ncCode, aa.product_id, aa.product_code,       \n" +
                        "            aa.pluname, aa.spellCode, aa.spellname,\n" +
                        "            min(aa.onHandQty) as onHandQty,  min(aa.inTransitQty) as inTransitQty, min(availableQty) as availableQty\n" +
                        "     from iagent.plubasinfo bb\n" +
                        "     inner join \n" +
                        "     (\n" +
                        "          --获取NC对应多套装库存(带规格)\n" +
                        "          select ddd.prodsuitescmid, ddd.prodsuitetype, ddd.prodsuiteid, bbb.ncCode, aaa.product_id, aaa.product_code, bbb.pluname, bbb.spellCode, bbb.spellname,        \n" +
                        "          floor(sum(nvl(aaa.on_hand_qty,0))/avg(ddd.prodnum)) as onHandQty,            \n" +
                        "          floor(sum(nvl(aaa.in_transit_purchase_qty,0) + nvl(aaa.in_transit_movement_qty, 0) + nvl(aaa.in_transit_other_qty,0))/avg(ddd.prodnum)) as inTransitQty, \n" +
                        "          floor(sum(nvl(aaa.on_hand_qty,0) - nvl(aaa.frozen_qty, 0) - nvl(aaa.allocated_qty,0) - nvl(aaa.allocated_distribution_qty,0) - nvl(aaa.allocated_other_qty,0))/avg(ddd.prodnum)) as availableQty\n" +
                        "          from acoapp_oms.item_inventory_channel aaa \n" +
                        "          right join iagent.plubasinfo bbb on aaa.product_id = bbb.ruid\n" +
                        "          left join iagent.productsuitetype ddd on bbb.nccode = ddd.prodid and bbb.plucode=ddd.PRODSCMID \n" +
                        "          where aaa.location_type='1' and ddd.prodnum > 0 " +
                        (params.containsKey("nccode") ? "and ddd.prodsuiteid = :nccode " : "") +
                        (params.containsKey("ncfreename") ? "and ddd.prodsuitetype = :ncfreename " : "") +
                        "          group by ddd.prodsuitescmid, ddd.prodsuitetype, ddd.prodsuiteid, bbb.ncCode, aaa.product_id, aaa.product_code, bbb.pluname, bbb.spellCode, bbb.spellname\n" +
                        "      ) aa on aa.prodsuitescmid = bb.plucode\n" +
                        "      where bb.NCFREESTATUS in (0,1) \n" +
                        (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where bb.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where bb.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                        (params.containsKey("nccode") ? "and bb.nccode = :nccode " : "") +
                        (params.containsKey("plucode") ? "and bb.plucode = :plucode " : "") +
                        (params.containsKey("pluname") ? "and bb.pluname = :pluname " : "") +
                        (params.containsKey("spellcode") ? "and bb.spellcode = :spellcode " : "") +
                        (params.containsKey("spellname") ? "and bb.spellname = :spellname " : "") +
                        (params.containsKey("ncfree") ? "and bb.ncfree = :ncfree " : "") +
                        (params.containsKey("ncfreename") ? "and bb.ncfreename = :ncfreename " : "") +
                        "      group by aa.prodsuitescmid, aa.prodsuiteid, aa.prodsuitetype, aa.ncCode, aa.product_id, aa.product_code, aa.pluname, aa.spellCode, aa.spellname\n" +
                        "   ) a group by a.prodsuitescmid \n" +
                        ") n on m.plucode=n.plucode order by m.plucode asc"
        )
                .addScalar("ncCode", StringType.INSTANCE)
                .addScalar("productId", LongType.INSTANCE)
                .addScalar("productCode", StringType.INSTANCE)
                .addScalar("productName", StringType.INSTANCE)
                .addScalar("ncfree", StringType.INSTANCE)
                .addScalar("ncfreeName", StringType.INSTANCE)
                .addScalar("spellCode", StringType.INSTANCE)
                .addScalar("spellName", StringType.INSTANCE)
                .addScalar("onHandQty", DoubleType.INSTANCE)
                .addScalar("inTransitQty", DoubleType.INSTANCE)
                .addScalar("availableQty", DoubleType.INSTANCE)
                .addScalar("groupPrice", DoubleType.INSTANCE)
                .addScalar("listPrice", DoubleType.INSTANCE)
                .addScalar("maxPrice", DoubleType.INSTANCE)
                .addScalar("minPrice", DoubleType.INSTANCE)
                .addScalar("status", IntegerType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(RealTimeStockItem.class));

        List<String> parameters = Arrays.asList(q.getNamedParameters());
        for(String parameterName : parameters) {
            if(params.containsKey(parameterName)) {
                q.setParameter(parameterName, params.get(parameterName));
            }
        }

        return q.list();
    }

    public List<NcRealTimeStockItem> getNcRealTimeStock(Map<String, Object> params){
        if(params.size() > 0){
            Session session = getSession();
            //-1:受控 0:不受控
            Query q = session.createSQLQuery(
                    "select m.ncCode, m.ncName, m.spellCode, m.spellname, m.status,\n" +
                    (params.containsKey("grpid") ? "(select LPRICE from IAGENT.productgrplimit c where c.prodid=m.nccode and c.grpId = :grpid and c.status='-1' and c.startdt <= sysdate and c.enddt > sysdate and rownum < 2)":"''") + " as groupPrice," +
                    "       m.listPrice, m.maxPrice, m.minPrice, \n" +
                    "       nvl(n.onHandQty,0) as onHandQty,\n" +
                    "       nvl(n.inTransitQty,0) as inTransitQty,\n" +
                    "       nvl(n.availableQty,0) as availableQty from (\n" +
                    "  select b.ncCode, replace(b.pluname,b.ncfreename, '') as ncName, b.spellCode, b.spellname, \n" +
                    "         min(nvl(b.SLPRC,0)) as listPrice, min(nvl(b.HSLPRC,0)) as maxPrice, max(nvl(b.LSLPRC,0)) as minPrice, \n" +
                    "         min(decode(b.status,'1','0','2','-1','3','0','4','-1','5','-1','6','-1','7','-1','8','-1','9','0','-1')) as status\n" +
                    "  from iagent.plubasinfo b \n" +
                    "  where b.NCFREESTATUS=1 \n" +
                    (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where b.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where b.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                    (params.containsKey("nccode") ? "and b.nccode = :nccode " : "") +
                    (params.containsKey("spellcode") ? "and b.spellcode = :spellcode " : "") +
                    (params.containsKey("spellname") ? "and b.spellname = :spellname " : "") +
                    (params.containsKey("ncfree") ? "and b.ncfree = :ncfree " : "") +
                    (params.containsKey("ncfreename") ? "and b.ncfreename = :ncfreename " : "") +
                    "  group by b.ncCode, b.spellCode, b.spellname, replace(b.pluname,b.ncfreename, '')  \n" +
                    ") m left join (\n" +
                    "  --单品库存(无规格)\n" +
                    "  select b.nccode, \n" +
                    "    sum(nvl(a.on_hand_qty,0)) as onHandQty,\n" +
                    "    sum(nvl(a.in_transit_purchase_qty,0) + nvl(a.in_transit_movement_qty, 0) + nvl(a.in_transit_other_qty,0)) as inTransitQty, \n" +
                    "    sum(nvl(a.on_hand_qty,0) - nvl(a.frozen_qty, 0) - nvl(a.allocated_qty,0) - nvl(a.allocated_distribution_qty,0) - nvl(a.allocated_other_qty,0)) as availableQty \n" +
                    "  from acoapp_oms.item_inventory_channel a \n" +
                    "  right join iagent.plubasinfo b on a.product_id = b.ruid and b.NCFREESTATUS=1 \n" +
                    "  where a.location_type='1' and b.NCFREESTATUS=1 \n" +
                    (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where b.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where b.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                    (params.containsKey("nccode") ? "and b.nccode = :nccode " : "") +
                    (params.containsKey("spellcode") ? "and b.spellcode = :spellcode " : "") +
                    (params.containsKey("spellname") ? "and b.spellname = :spellname " : "") +
                    (params.containsKey("ncfree") ? "and b.ncfree = :ncfree " : "") +
                    (params.containsKey("ncfreename") ? "and b.ncfreename = :ncfreename " : "") +
                    "  group by b.nccode\n" +
                    "  union all\n" +
                    "  --套装库存(无规格)\n" +
                    "  select a.prodsuiteid as nccode,\n" +
                    "         nvl(min(a.onHandQty),0) as onHandQty,\n" +
                    "         decode(sign(min(a.inTransitQty)), 1, min(a.inTransitQty), -1, 0, 0) as inTransitQty,\n" +
                    "         decode(sign(min(a.availableQty)), 1, min(a.availableQty), -1, 0, 0) as availableQty\n" +
                    "  from \n" +
                    "  (\n" +
                    "     --多套装NC库存(无规格)\n" +
                    "     select aa.prodsuiteid, aa.ncCode, aa.product_id as productId, aa.product_code,       \n" +
                    "            aa.pluname, aa.spellCode, aa.spellname,\n" +
                    "            min(aa.onHandQty) as onHandQty, min(aa.inTransitQty) as inTransitQty, min(availableQty) as availableQty\n" +
                    "     from (\n" +
                    "            --获取NC对应多套装库存(无规格)\n" +
                    "            select ddd.prodsuitescmid, ddd.prodsuiteid, bbb.ncCode, aaa.product_id, aaa.product_code, bbb.pluname, bbb.spellCode, bbb.spellname,        \n" +
                    "            floor(sum(nvl(aaa.on_hand_qty,0))/avg(ddd.prodnum)) as onHandQty,            \n" +
                    "            floor(sum(nvl(aaa.in_transit_purchase_qty,0) + nvl(aaa.in_transit_movement_qty, 0) + nvl(aaa.in_transit_other_qty,0))/avg(ddd.prodnum)) as inTransitQty, \n" +
                    "            floor(sum(nvl(aaa.on_hand_qty,0) - nvl(aaa.frozen_qty, 0) - nvl(aaa.allocated_qty,0) - nvl(aaa.allocated_distribution_qty,0) - nvl(aaa.allocated_other_qty,0))/avg(ddd.prodnum)) as availableQty\n" +
                    "            from acoapp_oms.item_inventory_channel aaa \n" +
                    "            right join iagent.plubasinfo bbb on aaa.product_id = bbb.ruid\n" +
                    "            left join iagent.productsuitetype ddd on bbb.nccode = ddd.prodid and bbb.plucode=ddd.PRODSCMID \n" +
                    "            inner join iagent.plubasinfo eee on ddd.prodsuitescmid = eee.plucode \n" +
                    "            where aaa.location_type='1' and ddd.prodnum > 0 and eee.ncfreestatus=1 \n" +
                                (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where eee.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where eee.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                                (params.containsKey("nccode") ? "and eee.nccode = :nccode " : "") +
                                (params.containsKey("spellcode") ? "and eee.spellcode = :spellcode " : "") +
                                (params.containsKey("spellname") ? "and eee.spellname = :spellname " : "") +
                                (params.containsKey("ncfree") ? "and eee.ncfree = :ncfree " : "") +
                                (params.containsKey("ncfreename") ? "and eee.ncfreename = :ncfreename " : "") +
                    "            group by ddd.prodsuitescmid, ddd.prodsuiteid, bbb.ncCode, aaa.product_id, aaa.product_code, bbb.pluname, bbb.spellCode, bbb.spellname\n" +
                    "        ) aa group by aa.prodsuiteid, aa.ncCode, aa.product_id, aa.product_code, aa.pluname, aa.spellCode, aa.spellname\n" +
                    "    ) a group by a.prodsuiteid \n" +
                    ") n on m.nccode=n.nccode "
            )
            .addScalar("ncCode", StringType.INSTANCE)
            .addScalar("ncName", StringType.INSTANCE)
            .addScalar("spellCode", StringType.INSTANCE)
            .addScalar("spellName", StringType.INSTANCE)
            .addScalar("onHandQty", DoubleType.INSTANCE)
            .addScalar("inTransitQty", DoubleType.INSTANCE)
            .addScalar("availableQty", DoubleType.INSTANCE)
            .addScalar("groupPrice", DoubleType.INSTANCE)
            .addScalar("listPrice", DoubleType.INSTANCE)
            .addScalar("maxPrice", DoubleType.INSTANCE)
            .addScalar("minPrice", DoubleType.INSTANCE)
            .addScalar("status", IntegerType.INSTANCE)
            .setResultTransformer(Transformers.aliasToBean(NcRealTimeStockItem.class));

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)) {
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return q.list();
        } else {
            return new ArrayList<NcRealTimeStockItem>();
        }
    }

    public List<NcRealTimeStockItem> getNcRealTimeAllStock(Map<String, Object> params){
        if(params.size() > 0){
            Session session = getSession();
            //-1:受控 0:不受控
            Query q = session.createSQLQuery(
                    "select m.ncCode, m.ncName, m.spellCode, m.spellname, m.status,\n" +
                            (params.containsKey("grpid") ? "(select LPRICE from IAGENT.productgrplimit c where c.prodid=m.nccode and c.grpId = :grpid and c.status='-1' and c.startdt <= sysdate and c.enddt > sysdate and rownum < 2)":"''") + " as groupPrice," +
                            "       m.listPrice, m.maxPrice, m.minPrice, \n" +
                            "       nvl(n.onHandQty,0) as onHandQty,\n" +
                            "       nvl(n.inTransitQty,0) as inTransitQty,\n" +
                            "       nvl(n.availableQty,0) as availableQty from (\n" +
                            "  select b.ncCode, replace(b.pluname,b.ncfreename, '') as ncName, b.spellCode, b.spellname, \n" +
                            "         min(nvl(b.SLPRC,0)) as listPrice, min(nvl(b.HSLPRC,0)) as maxPrice, max(nvl(b.LSLPRC,0)) as minPrice, \n" +
                            "         min(decode(b.status,'1','0','2','-1','3','0','4','-1','5','-1','6','-1','7','-1','8','-1','9','0','-1')) as status\n" +
                            "  from iagent.plubasinfo b \n" +
                            "  where b.NCFREESTATUS in (0,1) \n" +
                            (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where b.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where b.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                            (params.containsKey("nccode") ? "and b.nccode = :nccode " : "") +
                            (params.containsKey("spellcode") ? "and b.spellcode = :spellcode " : "") +
                            (params.containsKey("spellname") ? "and b.spellname = :spellname " : "") +
                            (params.containsKey("ncfree") ? "and b.ncfree = :ncfree " : "") +
                            (params.containsKey("ncfreename") ? "and b.ncfreename = :ncfreename " : "") +
                            "  group by b.ncCode, b.spellCode, b.spellname, replace(b.pluname,b.ncfreename, '')  \n" +
                            ") m left join (\n" +
                            "  --单品库存(无规格)\n" +
                            "  select b.nccode, \n" +
                            "    sum(nvl(a.on_hand_qty,0)) as onHandQty,\n" +
                            "    sum(nvl(a.in_transit_purchase_qty,0) + nvl(a.in_transit_movement_qty, 0) + nvl(a.in_transit_other_qty,0)) as inTransitQty, \n" +
                            "    sum(nvl(a.on_hand_qty,0) - nvl(a.frozen_qty, 0) - nvl(a.allocated_qty,0) - nvl(a.allocated_distribution_qty,0) - nvl(a.allocated_other_qty,0)) as availableQty \n" +
                            "  from acoapp_oms.item_inventory_channel a \n" +
                            "  right join iagent.plubasinfo b on a.product_id = b.ruid and b.NCFREESTATUS in (0,1) \n" +
                            "  where a.location_type='1' and b.NCFREESTATUS in (0,1) \n" +
                            (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where b.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where b.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                            (params.containsKey("nccode") ? "and b.nccode = :nccode " : "") +
                            (params.containsKey("spellcode") ? "and b.spellcode = :spellcode " : "") +
                            (params.containsKey("spellname") ? "and b.spellname = :spellname " : "") +
                            (params.containsKey("ncfree") ? "and b.ncfree = :ncfree " : "") +
                            (params.containsKey("ncfreename") ? "and b.ncfreename = :ncfreename " : "") +
                            "  group by b.nccode\n" +
                            "  union all\n" +
                            "  --套装库存(无规格)\n" +
                            "  select a.prodsuiteid as nccode,\n" +
                            "         nvl(min(a.onHandQty),0) as onHandQty,\n" +
                            "         decode(sign(min(a.inTransitQty)), 1, min(a.inTransitQty), -1, 0, 0) as inTransitQty,\n" +
                            "         decode(sign(min(a.availableQty)), 1, min(a.availableQty), -1, 0, 0) as availableQty\n" +
                            "  from \n" +
                            "  (\n" +
                            "     --多套装NC库存(无规格)\n" +
                            "     select aa.prodsuiteid, aa.ncCode, aa.product_id as productId, aa.product_code,       \n" +
                            "            aa.pluname, aa.spellCode, aa.spellname,\n" +
                            "            min(aa.onHandQty) as onHandQty, min(aa.inTransitQty) as inTransitQty, min(availableQty) as availableQty\n" +
                            "     from (\n" +
                            "            --获取NC对应多套装库存(无规格)\n" +
                            "            select ddd.prodsuitescmid, ddd.prodsuiteid, bbb.ncCode, aaa.product_id, aaa.product_code, bbb.pluname, bbb.spellCode, bbb.spellname,        \n" +
                            "            floor(sum(nvl(aaa.on_hand_qty,0))/avg(ddd.prodnum)) as onHandQty,            \n" +
                            "            floor(sum(nvl(aaa.in_transit_purchase_qty,0) + nvl(aaa.in_transit_movement_qty, 0) + nvl(aaa.in_transit_other_qty,0))/avg(ddd.prodnum)) as inTransitQty, \n" +
                            "            floor(sum(nvl(aaa.on_hand_qty,0) - nvl(aaa.frozen_qty, 0) - nvl(aaa.allocated_qty,0) - nvl(aaa.allocated_distribution_qty,0) - nvl(aaa.allocated_other_qty,0))/avg(ddd.prodnum)) as availableQty\n" +
                            "            from acoapp_oms.item_inventory_channel aaa \n" +
                            "            right join iagent.plubasinfo bbb on aaa.product_id = bbb.ruid\n" +
                            "            left join iagent.productsuitetype ddd on bbb.nccode = ddd.prodid and bbb.plucode=ddd.PRODSCMID \n" +
                            "            inner join iagent.plubasinfo eee on ddd.prodsuitescmid = eee.plucode \n" +
                            "            where aaa.location_type='1' and ddd.prodnum > 0 and eee.ncfreestatus in (0,1) \n" +
                            (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where eee.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where eee.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                            (params.containsKey("nccode") ? "and eee.nccode = :nccode " : "") +
                            (params.containsKey("spellcode") ? "and eee.spellcode = :spellcode " : "") +
                            (params.containsKey("spellname") ? "and eee.spellname = :spellname " : "") +
                            (params.containsKey("ncfree") ? "and eee.ncfree = :ncfree " : "") +
                            (params.containsKey("ncfreename") ? "and eee.ncfreename = :ncfreename " : "") +
                            "            group by ddd.prodsuitescmid, ddd.prodsuiteid, bbb.ncCode, aaa.product_id, aaa.product_code, bbb.pluname, bbb.spellCode, bbb.spellname\n" +
                            "        ) aa group by aa.prodsuiteid, aa.ncCode, aa.product_id, aa.product_code, aa.pluname, aa.spellCode, aa.spellname\n" +
                            "    ) a group by a.prodsuiteid \n" +
                            ") n on m.nccode=n.nccode "
            )
                    .addScalar("ncCode", StringType.INSTANCE)
                    .addScalar("ncName", StringType.INSTANCE)
                    .addScalar("spellCode", StringType.INSTANCE)
                    .addScalar("spellName", StringType.INSTANCE)
                    .addScalar("onHandQty", DoubleType.INSTANCE)
                    .addScalar("inTransitQty", DoubleType.INSTANCE)
                    .addScalar("availableQty", DoubleType.INSTANCE)
                    .addScalar("groupPrice", DoubleType.INSTANCE)
                    .addScalar("listPrice", DoubleType.INSTANCE)
                    .addScalar("maxPrice", DoubleType.INSTANCE)
                    .addScalar("minPrice", DoubleType.INSTANCE)
                    .addScalar("status", IntegerType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(NcRealTimeStockItem.class));

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)) {
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }
            return q.list();
        } else {
            return new ArrayList<NcRealTimeStockItem>();
        }
    }

    public List<WmsRealTimeStockItem> getWmsRealTimeStock(Map<String, Object> params)
    {
        if(params.size() > 0){
            Session session = getSession();
            Query q = session.createSQLQuery(
                    "select b.ncCode, a.product_id as productId, a.product_code as productCode," +
                    "       b.pluname as productName, b.spellCode, b.spellName, a.warehouse, c.warehouse_name as warehouseName," +
                    "       b.ncfree, b.ncfreeName,"+
                    "       sum(nvl(a.on_hand_qty,0)) as onHandQty," +
                    "       sum(nvl(a.in_transit_purchase_qty,0) + nvl(a.in_transit_movement_qty, 0) + nvl(a.in_transit_other_qty,0)) as inTransitQty, \n" +
                    "       sum(nvl(a.on_hand_qty,0) - nvl(a.frozen_qty, 0) - nvl(a.allocated_qty,0) - nvl(a.allocated_distribution_qty,0) - nvl(a.allocated_other_qty,0)) as availableQty," +
                    "       min(nvl(b.SLPRC,0)) as listPrice, min(nvl(b.HSLPRC,0)) as maxPrice, max(nvl(b.LSLPRC,0)) as minPrice," +
                    "       min(decode(b.status,'1','0','2','-1','3','0','4','-1','5','-1','6','-1','7','-1','8','-1','9','0','-1')) as status " +
                    "from acoapp_oms.item_inventory_channel a " +
                    "right join iagent.plubasinfo b on a.product_id = b.ruid " +
                    "left join acoapp_oms.warehouse c on a.warehouse = c.warehouse_code " +
                    "where a.location_type='1' " +
                    (params.containsKey("grpid") ? "and (exists(select pd.* from IAGENT.PRODUCT pd where b.nccode=pd.prodid and pd.status='-1') or exists(select pgc.* from iagent.PRODUCTGRPCHANNEL pgc where b.nccode=pgc.prodId and pgc.grpId = :grpid and pgc.status='-1')) " : "") +
                    (params.containsKey("nccode") ? "and ((b.nccode = :nccode and b.NCFREESTATUS=1) or exists(select pst.* from iagent.productsuitetype pst,iagent.plubasinfo pbi where pst.PRODSUITESCMID=pbi.plucode and pbi.NCFREESTATUS=1 and pst.prodscmid=b.plucode and pst.prodsuiteid=:nccode)) " : "") +
                    (params.containsKey("plucode") ? "and b.plucode = :plucode " : "") +
                    (params.containsKey("pluname") ? "and b.pluname = :pluname " : "") +
                    (params.containsKey("spellcode") ? "and b.spellcode = :spellcode " : "") +
                    (params.containsKey("spellname") ? "and b.spellname = :spellname " : "") +
                    //(params.containsKey("ncfree") ? "and b.ncfree = :ncfree " : "") +
                    (params.containsKey("nccode") && params.containsKey("ncfreename") ? "and (b.ncfreename = :ncfreename or exists(select pst.* from iagent.productsuitetype pst where pst.prodscmid=b.plucode and pst.prodsuiteid=:nccode and pst.PRODSUITETYPE=:ncfreename))" : "") +
                    "group by b.ncCode, a.product_id, a.product_code, b.pluname, b.spellCode, b.spellname,b.ncfree, b.ncfreeName, a.warehouse, c.warehouse_name " +
                    "order by a.product_code"
            )
            .addScalar("ncCode", StringType.INSTANCE)
            .addScalar("productId", LongType.INSTANCE)
            .addScalar("productCode", StringType.INSTANCE)
            .addScalar("productName", StringType.INSTANCE)
            .addScalar("spellCode", StringType.INSTANCE)
            .addScalar("spellName", StringType.INSTANCE)
            .addScalar("warehouse", StringType.INSTANCE)
            .addScalar("warehouseName", StringType.INSTANCE)
            .addScalar("ncfree", StringType.INSTANCE)
            .addScalar("ncfreeName", StringType.INSTANCE)
            .addScalar("onHandQty", DoubleType.INSTANCE)
            .addScalar("inTransitQty", DoubleType.INSTANCE)
            .addScalar("availableQty", DoubleType.INSTANCE)
            .addScalar("listPrice", DoubleType.INSTANCE)
            .addScalar("maxPrice", DoubleType.INSTANCE)
            .addScalar("minPrice", DoubleType.INSTANCE)
            .addScalar("status", IntegerType.INSTANCE)
            .setResultTransformer(Transformers.aliasToBean(WmsRealTimeStockItem.class));

            List<String> parameters = Arrays.asList(q.getNamedParameters());
            for(String parameterName : parameters) {
                if(params.containsKey(parameterName)) {
                    q.setParameter(parameterName, params.get(parameterName));
                }
            }

            return q.list();
        } else {
            return new ArrayList<WmsRealTimeStockItem>();
        }
    }

    /**
     * 获取销售库存统计信息
     * @param params
     * @return
     */
    public List<SalesStockItem> getSalesStockItem(Map<String, Object> params)
    {
        return new ArrayList<SalesStockItem>();
    }

	public void saveOrUpdateItemInventory(
			ItemInventoryChannel itemInventoryChannel) {
		saveOrUpdate(itemInventoryChannel);
	}

	/*
	 * @see com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase#
	 * setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}


}
