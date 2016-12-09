package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.constant.BusinessTypeConstants;
import com.chinadrtv.erp.constant.ChannelConstants;
import com.chinadrtv.erp.constant.LocationTypeConstants;
import com.chinadrtv.erp.constant.TransactionSourceConstants;
import com.chinadrtv.erp.ic.dao.*;
import com.chinadrtv.erp.ic.service.ItemInventoryTransactionService;
import com.chinadrtv.erp.model.inventory.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author cuiming
 * @version 1.0
 * @since 2013-1-28 下午3:39:40
 */
@Service(value = "itemInventoryTransactionService")
public class ItemInventoryTransactionServiceImpl implements
		ItemInventoryTransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemInventoryTransactionServiceImpl.class);

	@Autowired
	private ItemInventoryTransactionDao itemInventoryTransactionDao;

	@Autowired
	private ItemInventoryCalcLogDao itemInventoryCalcLogDao;

	@Autowired
	private ItemInventoryDao itemInventoryDao;

	@Autowired
	private WmsReceiptHeaderHisDao wmsReceiptHeaderHisDao;

	@Autowired
	private ScmUploadReceiptHeaderHisDao scmUploadReceiptHeaderHisDao;

    @Autowired
    private ScmItemStatusChangeHisDao scmItemStatusChangeHisDao;

    @Autowired
    private ScmOnhandAdjustmentHisDao scmOnhandAdjustmentHisDao;

    @Autowired
    private PlubasInfoDao plubasInfoDao;

	/*
	 * (非 Javadoc) <p>Title: insertItemInventoryTransaction</p> <p>Description:
	 * </p>
	 * 
	 * @param itemInventoryTransaction
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.ic.sercice.ItemInventoryTransactionService#
	 * insertItemInventoryTransaction(java.lang.Object)
	 */
	public Boolean insertItemInventoryTransaction(ItemInventoryTransaction itemInventoryTransaction) {
		Boolean flag = false;
		try {
            //事物项自身数据完整性填充
            ensureItemTransaction(itemInventoryTransaction);
            // 插入事物表
            itemInventoryTransactionDao.saveOrUpdateItemTransaction(itemInventoryTransaction);
			// 获取缓存,如果不存在从库存表获取并建立缓存
			ItemInventoryChannel itemInventoryChannel = itemInventoryDao
                    .getCacheItemInventory(itemInventoryTransaction.getChannel(),
                            itemInventoryTransaction.getWarehouse(),
                            itemInventoryTransaction.getLocationType(),
                            itemInventoryTransaction.getProductId());
			if (itemInventoryChannel != null)
            {
                // 将本次事物项更新到缓存库存
				updateItemInventory(itemInventoryChannel, itemInventoryTransaction);
				// 更新缓存
                itemInventoryDao.saveCacheItemInventory(
                        itemInventoryTransaction.getChannel(),
                        itemInventoryTransaction.getWarehouse(),
                        itemInventoryTransaction.getLocationType(),
                        itemInventoryTransaction.getProductId(),
                        itemInventoryChannel);
			}

			flag = true;
		} catch (Exception e) {
            LOG.error(e.getMessage());
		}
		return flag;
	}

    /**
     * 生成计算库存
     * @param batchUser
     */
    public void processUnhandledTransactionsEx(String batchUser) {
        Date now = new Date();
        Long batchId = now.getTime();
        ItemInventoryCalcLog log = itemInventoryCalcLogDao.createUnhandledCalcLog(batchId.toString(), batchUser);
        if(log != null && log.getRowsAffected() > 0){
            itemInventoryTransactionDao.executeItemTransactions(
                    Long.parseLong(log.getStartRevesion()),
                    Long.parseLong(log.getEndRevesion()),
                    batchId.toString(),
                    batchUser
                    );
            log.setId(null);
            itemInventoryCalcLogDao.saveLog(log);
        }
    }

	/**
	 * 1、 生成BATCH_ID
     * 2、 记录开始日志到ITEM_INVENTORY_CALC_LOG表 3、
	 * 查询ITEM_INVENTORY_TRANSACTION表中BATCH_ID为空的行 4、
	 * 逐行处理2的行，增减渠道库存表ITEM_INVENTORY_CHANNEL，各种数量的增减逻辑见“业务场景”部分。 5、
	 * 记录完成日志到ITEM_INVENTORY_CALC_LOG表 6、
	 * 回写BATCH_ID、BATCH_DATE、BATCH_BY到ITEM_INVENTORY_TRANSACTION表
	 * @param batchUser
	 */
    @Deprecated
	public void processUnhandledTransactions(String batchUser) {
		List<ItemInventoryTransaction> tanscations = itemInventoryTransactionDao.getUnhandledBatchTransactions();
		if (tanscations != null && tanscations.size() > 0) {
			Date now = new Date();
            Long batchId = now.getTime();

			ItemInventoryCalcLog log = new ItemInventoryCalcLog();
			log.setBatchId(batchId.toString());
			log.setStartRevesion(String.valueOf(tanscations.get(0).getId()));
			log.setEndRevesion(String.valueOf(tanscations.get(
					tanscations.size() - 1).getId()));
			log.setRowsAffected(new Long(tanscations.size()));
			log.setStatusDate(now);
			log.setStatus("1");
			log.setModified(now);
			log.setModifiedBy(batchUser);
			log.setCreated(now);
			log.setCreatedBy(batchUser);

			itemInventoryCalcLogDao.saveLog(log);


			for (ItemInventoryTransaction trans : tanscations) {
				trans.setModified(now);
				trans.setModifiedBy(batchUser);
				trans.setBatchId(log.getBatchId());
				trans.setBatchDate(now);
				trans.setBatchBy(batchUser);
				// 更新库存
				ItemInventoryChannel inventory = getOrCreateItemInventory(trans);
				inventory.setModified(now);
				inventory.setModifiedBy(batchUser);
				inventory.setCreated(now);
				inventory.setCreatedBy(batchUser);
				updateItemInventory(inventory, trans);
				itemInventoryDao.saveOrUpdateItemInventory(inventory);
                //不需要更新缓存,直接更新数据库
                itemInventoryTransactionDao.saveOrUpdateItemTransaction(trans);
			}
		}
	}

    /**
     * 事物交易项数据完整性填充
     * @param transaction
     */
    private void ensureItemTransaction(ItemInventoryTransaction transaction) {
        updateTransactionProduct(transaction);
        updateTransactionQuantity(transaction);
    }


    private void updateTransactionProduct(ItemInventoryTransaction trans) {
        /**
         * ProductId -> ProductCode
         * ProductCode -> ProductId
         */
        if ((trans.getProductId() == null ||
                trans.getProductId() == 0) &&
                trans.getProductCode() != null) {
            PlubasInfo info = plubasInfoDao.getPlubasInfo(trans.getProductCode());
            if(info != null){
                trans.setProductId(info.getRuid());
            }
        }
        else if (trans.getProductId() != null &&
                trans.getProductId() > 0 &&
                (("").equals(trans.getProductCode()) ||
                        trans.getProductCode() == null)) {

            PlubasInfo info = plubasInfoDao.getPlubasInfoByplid(trans.getProductId());
            if(info != null){
                trans.setProductId(info.getRuid());
            }
        }
    }

    /**
     * 内存更新库存
     * @param trans
     */
    private void updateTransactionQuantity(ItemInventoryTransaction trans) {

        // 采购在途
        if (Arrays.asList(BusinessTypeConstants.ORDER_IN_TRANSIT_PROCUREMENTS)
                  .contains(trans.getBusinessType())){
            orderInTransitProcurements(trans);
        }
        // 移动在途
        else if (Arrays.asList(BusinessTypeConstants.ORDER_IN_TRANSIT_MOVEMENTS)
                        .contains(trans.getBusinessType())){
            orderInTransitMovements(trans);
        }
        // 其他在途
        else if (Arrays.asList(BusinessTypeConstants.ORDER_IN_TRANSIT_OTHERS)
                .contains(trans.getBusinessType())){
            orderInTransitOthers(trans);
        }
        // 预分配(直销)
        else if (Arrays.asList(BusinessTypeConstants.ORDER_ALLOCATED_RETAILS)
                .contains(trans.getBusinessType())){
            orderAllocated(trans);
        }
        // 预分配(分销)
        else if (Arrays.asList(BusinessTypeConstants.ORDER_ALLOCATED_DISTRIBUTIONS)
                .contains(trans.getBusinessType())){
            orderAllocatedDistributions(trans);
        }
        // 预分配(其他)
        else if (Arrays.asList(BusinessTypeConstants.ORDER_ALLOCATED_OTHERS)
                        .contains(trans.getBusinessType())) {
            orderAllocatedOthers(trans);
        }
        // 采购入库
        else if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_IN)
                .contains(trans.getBusinessType())){
            inventoryStockIn(trans);
        }
        // 出库
        else if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_OUT)
                .contains(trans.getBusinessType())){
            inventoryStockOut(trans);
        }
        // 盘点调整
        else if (BusinessTypeConstants.INVENTORY_STOCK_RECOUNT
                .equalsIgnoreCase(trans.getBusinessType())) {
            inventoryStockRecount(trans);
        }
        // 变更货主
        else if (BusinessTypeConstants.INVENTORY_STOCK_OWNER_RIGHTS
                .equalsIgnoreCase(trans.getBusinessType())) {
            inventoryStockRecount(trans);
        }
        // 库存移动
        else if (BusinessTypeConstants.INVENTORY_STOCK_MOVEMENT
                .equalsIgnoreCase(trans.getBusinessType())) {
            inventoryStockRecount(trans);
        }
    }

	private void updateItemInventory(ItemInventoryChannel inventory,
			ItemInventoryTransaction trans) {

        inventory.setOnHandQty(inventory.getOnHandQty() + trans.getOnHandQty());
        inventory.setFrozenQty(inventory.getFrozenQty() + trans.getFrozenQty());
        inventory.setAllocatedQty(inventory.getAllocatedQty() + trans.getAllocatedQty());
        inventory.setAllocatedDistributionQty(inventory.getAllocatedDistributionQty() + trans.getAllocatedDistributionQty());
        inventory.setAllocatedOtherQty(inventory.getAllocatedOtherQty() + trans.getAllocatedOtherQty());
        inventory.setInTransitPurchaseQty(inventory.getInTransitPurchaseQty() + trans.getInTransitPurchaseQty());
        inventory.setInTransitMovementQty(inventory.getInTransitMovementQty() + trans.getInTransitMovementQty());
        inventory.setInTransitOtherQty(inventory.getInTransitOtherQty() + trans.getInTransitOtherQty());


        /*
		// 采购在途
		if (Arrays.asList(BusinessTypeConstants.ORDER_IN_TRANSIT_PROCUREMENTS)
                  .contains(trans.getBusinessType())){
			orderInTransitProcurements(trans, inventory);
		}
		// 移动在途
		else if (Arrays.asList(BusinessTypeConstants.ORDER_IN_TRANSIT_MOVEMENTS)
                  .contains(trans.getBusinessType())){
			orderInTransitMovements(trans, inventory);
		}
		// 其他在途
		else if (Arrays.asList(BusinessTypeConstants.ORDER_IN_TRANSIT_OTHERS)
                  .contains(trans.getBusinessType())){
			orderInTransitOthers(trans, inventory);
		}
		// 预分配
		else if (Arrays.asList(BusinessTypeConstants.ORDER_ALLOCATED_SALES)
                  .contains(trans.getBusinessType())){
			orderAllocated(trans, inventory);
		}
		else if (Arrays.asList(BusinessTypeConstants.ORDER_ALLOCATED_DISTRIBUTIONS)
                  .contains(trans.getBusinessType())){
            orderAllocatedDistributions(trans, inventory);
        }
        else if (Arrays.asList(BusinessTypeConstants.ORDER_ALLOCATED_OTHERS)
                  .contains(trans.getBusinessType())){
            orderAllocatedOthers(trans, inventory);
        }
		// 采购入库
		else if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_IN)
                  .contains(trans.getBusinessType())){
			inventoryStockIn(trans, inventory);

		}
		// 出库
		else if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_OUT)
                  .contains(trans.getBusinessType())){
			inventoryStockOut(trans, inventory);
		}
		// 盘点调整
		else if (BusinessTypeConstants.INVENTORY_STOCK_RECOUNT
				.equalsIgnoreCase(trans.getBusinessType())) {
			inventoryStockRecount(trans, inventory);
		}
		// 变更货主
		else if (BusinessTypeConstants.INVENTORY_STOCK_OWNER_RIGHTS
				.equalsIgnoreCase(trans.getBusinessType())) {
			inventoryStockRecount(trans, inventory);
		}
		// 库存移动(状态)
		else if (BusinessTypeConstants.INVENTORY_STOCK_MOVEMENT
				.equalsIgnoreCase(trans.getBusinessType())) {
			inventoryStockRecount(trans, inventory);
		}
		*/
	}

    private void inventoryStockRecount(ItemInventoryTransaction trans) {
        if ("1".equals(trans.getBusinessChildType())) { // 1报损
            if (trans.getQty() != null) {

                if (trans.getOnHandQty() != null) {
                    trans.setOnHandQty(trans.getOnHandQty() - trans.getQty());
                } else {
                    trans.setOnHandQty(-1 * trans.getQty());
                }
            }
        } else if ("2".equals(trans.getBusinessChildType())) { //2报益

            if (trans.getQty() != null) {
                if (trans.getOnHandQty() != null) {
                    trans.setOnHandQty(trans.getOnHandQty() + trans.getQty());
                } else {
                    trans.setOnHandQty(trans.getQty());
                }
            }
        }
    }

    private void inventoryStockOut(ItemInventoryTransaction trans) {

        if (trans.getOnHandQty() != null) {
            trans.setOnHandQty(trans.getOnHandQty() - trans.getQty());
        } else {
            trans.setOnHandQty(trans.getQty() * -1);
        }

        if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_OUT_RETAILS)
                .contains(trans.getBusinessType())) {
            //直销
            if (trans.getAllocatedQty() != null) {
                trans.setAllocatedQty(trans.getAllocatedQty() - trans.getEstimatedQty());
            } else {
                trans.setAllocatedQty(trans.getEstimatedQty() * -1);
            }
        } else if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_OUT_DISTRIBUTIONS)
                .contains(trans.getBusinessType())) {
            // 分销
            if (trans.getAllocatedDistributionQty() != null) {
                trans.setAllocatedDistributionQty(trans.getAllocatedDistributionQty() - trans.getEstimatedQty());
            } else {
                trans.setAllocatedDistributionQty(trans.getEstimatedQty() * -1);
            }
          } else if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_OUT_OTHERS)
                .contains(trans.getBusinessType())) {
            // 其他
            if (trans.getAllocatedOtherQty() != null) {
                trans.setAllocatedOtherQty(trans.getAllocatedOtherQty() - trans.getEstimatedQty());
            } else {
                trans.setAllocatedOtherQty(trans.getEstimatedQty() * -1);
            }
        }
    }

    private void inventoryStockIn(ItemInventoryTransaction trans) {
        // 添加在手量
        if (trans.getQty() != null) {
            if (trans.getOnHandQty() != null) {
                trans.setOnHandQty(trans.getOnHandQty() + trans.getQty());
            } else {
                trans.setOnHandQty(trans.getQty());
            }
        }

        if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_IN_PROCUREMENTS)
                .contains(trans.getBusinessType())) {

            // 减少采购在途量
            if (trans.getEstimatedQty() != null) {
                if (trans.getInTransitPurchaseQty() != null) {
                    trans.setInTransitPurchaseQty(trans
                            .getInTransitPurchaseQty()
                            - trans.getEstimatedQty());
                } else {
                    trans.setInTransitPurchaseQty(trans.getEstimatedQty()
                            * -1);
                }
            }

        } else if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_IN_MOVEMENTS)
                .contains(trans.getBusinessType())) {
            // 减少移动在途量
            if (trans.getEstimatedQty() != null) {
                if (trans.getInTransitMovementQty() != null) {
                    trans.setInTransitMovementQty(trans
                            .getInTransitMovementQty()
                            - trans.getEstimatedQty());
                } else {
                    trans.setInTransitMovementQty(trans.getEstimatedQty()
                            * -1);
                }
            }

        } else if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_IN_OTHERS)
                .contains(trans.getBusinessType())) {

            // 减少其他在途量
            if (trans.getEstimatedQty() != null) {
                if (trans.getInTransitOtherQty() != null) {
                    trans.setInTransitOtherQty(trans
                            .getInTransitOtherQty() - trans.getEstimatedQty());
                } else {
                    trans
                            .setInTransitOtherQty(trans.getEstimatedQty() * -1);
                }
            }
        }
    }

    private void orderAllocated(ItemInventoryTransaction trans) {
        if (trans.getEstimatedQty() != null) {
            if (trans.getAllocatedQty() != null) {
                trans.setAllocatedQty(trans.getAllocatedQty()
                        + trans.getEstimatedQty());
            } else {
                trans.setAllocatedQty(trans.getEstimatedQty());
            }
        }
    }

    private void orderAllocatedDistributions(ItemInventoryTransaction trans) {
        if (trans.getEstimatedQty() != null) {
            if (trans.getAllocatedDistributionQty() != null) {
                trans.setAllocatedDistributionQty(trans.getAllocatedDistributionQty()
                        + trans.getEstimatedQty());
            } else {
                trans.setAllocatedDistributionQty(trans.getEstimatedQty());
            }
        }
    }

    private void orderAllocatedOthers(ItemInventoryTransaction trans) {
        if (trans.getEstimatedQty() != null) {
            if (trans.getAllocatedOtherQty() != null) {
                trans.setAllocatedOtherQty(trans.getAllocatedOtherQty()
                        + trans.getEstimatedQty());
            } else {
                trans.setAllocatedOtherQty(trans.getEstimatedQty());
            }
        }
    }

    private void orderInTransitOthers(ItemInventoryTransaction trans) {
        if (trans.getEstimatedQty() != null) {
            if (trans.getInTransitOtherQty() != null) {
                trans.setInTransitOtherQty(trans.getInTransitOtherQty()
                        + trans.getEstimatedQty());
            } else {
                trans.setInTransitOtherQty(trans.getEstimatedQty());
            }
        }
    }

    private void orderInTransitMovements(ItemInventoryTransaction trans) {
        if (trans.getEstimatedQty() != null) {
            if (trans.getInTransitMovementQty() != null) {
                trans.setInTransitMovementQty(trans
                        .getInTransitMovementQty() + trans.getEstimatedQty());
            } else {
                trans.setInTransitMovementQty(trans.getEstimatedQty());
            }
        }
    }

    private void orderInTransitProcurements(ItemInventoryTransaction trans) {
        if (trans.getEstimatedQty() != null) {
            if (trans.getInTransitPurchaseQty() != null) {
                trans.setInTransitPurchaseQty(trans
                        .getInTransitPurchaseQty() + trans.getEstimatedQty());
            } else {
                trans.setInTransitPurchaseQty(trans.getEstimatedQty());
            }
        }
    }

    /*
    @Deprecated
	private void inventoryStockRecount(ItemInventoryTransaction trans,
			ItemInventoryChannel inventory) {
		if ("1".equals(trans.getBusinessChildType())) { // 1报损，
			if (trans.getQty() != null) {
				if (inventory.getOnHandQty() != null) {
					inventory.setOnHandQty(inventory.getOnHandQty()
							- trans.getQty());
				} else {
					inventory.setOnHandQty(trans.getQty() * -1);
				}
			}
		} else if ("2".equals(trans.getBusinessChildType())) { //2报益
			if (trans.getQty() != null) {
				if (inventory.getOnHandQty() != null) {
					inventory.setOnHandQty(inventory.getOnHandQty()
							+ trans.getQty());
				} else {
					inventory.setOnHandQty(trans.getQty());
				}
			}
		}
	}

    @Deprecated
	private void inventoryStockOut(ItemInventoryTransaction trans,
			ItemInventoryChannel inventory) {

        if (inventory.getAllocatedQty() != null) {
            inventory.setAllocatedQty(inventory.getAllocatedQty() - trans.getQty());
        } else {
            inventory.setAllocatedQty(trans.getQty() * -1);
        }

		if (inventory.getOnHandQty() != null) {
			inventory.setOnHandQty(inventory.getOnHandQty() - trans.getQty());
		} else {
			inventory.setOnHandQty(trans.getQty() * -1);
		}
	}

    @Deprecated
	private void inventoryStockIn(ItemInventoryTransaction trans,
			ItemInventoryChannel inventory) {
		// 添加在手量
		if (trans.getQty() != null) {
			if (inventory.getOnHandQty() != null) {
				inventory.setOnHandQty(inventory.getOnHandQty()
						+ trans.getQty());
			} else {
				inventory.setOnHandQty(trans.getQty());
			}
		}

        if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_IN_PROCUREMENTS)
            .contains(trans.getBusinessType())) {

			// 减少采购在途量
			if (trans.getEstimatedQty() != null) {
				if (inventory.getInTransitPurchaseQty() != null) {
					inventory.setInTransitPurchaseQty(inventory
							.getInTransitPurchaseQty()
							- trans.getEstimatedQty());
				} else {
					inventory.setInTransitPurchaseQty(trans.getEstimatedQty()
							* -1);
				}
			}
		} else if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_IN_MOVEMENTS)
                .contains(trans.getBusinessType())) {

			// 减少移动在途量
			if (trans.getEstimatedQty() != null) {
				if (inventory.getInTransitMovementQty() != null) {
					inventory.setInTransitMovementQty(inventory
							.getInTransitMovementQty()
							- trans.getEstimatedQty());
				} else {
					inventory.setInTransitMovementQty(trans.getEstimatedQty()
							* -1);
				}
			}

        } else if (Arrays.asList(BusinessTypeConstants.INVENTORY_STOCK_IN_OTHERS)
                .contains(trans.getBusinessType())) {

			// 减少其他在途量
			if (trans.getEstimatedQty() != null) {
				if (inventory.getInTransitOtherQty() != null) {
					inventory.setInTransitOtherQty(inventory
							.getInTransitOtherQty() - trans.getEstimatedQty());
				} else {
					inventory
							.setInTransitOtherQty(trans.getEstimatedQty() * -1);
				}
			}
		}
	}



    @Deprecated
    private void orderAllocated(ItemInventoryTransaction trans,
                                ItemInventoryChannel inventory) {
        if (trans.getEstimatedQty() != null) {
            if (inventory.getAllocatedQty() != null) {
                inventory.setAllocatedQty(inventory.getAllocatedQty()
                        + trans.getEstimatedQty());
            } else {
                inventory.setAllocatedQty(trans.getEstimatedQty());
            }
        }
    }

    @Deprecated
	private void orderAllocatedDistributions(ItemInventoryTransaction trans,
			ItemInventoryChannel inventory) {
		if (trans.getEstimatedQty() != null) {
			if (inventory.getAllocatedDistributionQty() != null) {
				inventory.setAllocatedDistributionQty(inventory.getAllocatedDistributionQty()
						+ trans.getEstimatedQty());
			} else {
				inventory.setAllocatedDistributionQty(trans.getEstimatedQty());
			}
		}
	}



    @Deprecated
    private void orderAllocatedOthers(ItemInventoryTransaction trans,
                                ItemInventoryChannel inventory) {
        if (trans.getEstimatedQty() != null) {
            if (inventory.getAllocatedOtherQty() != null) {
                inventory.setAllocatedOtherQty(inventory.getAllocatedOtherQty()
                        + trans.getEstimatedQty());
            } else {
                inventory.setAllocatedOtherQty(trans.getEstimatedQty());
            }
        }
    }



    @Deprecated
	private void orderInTransitOthers(ItemInventoryTransaction trans,
			ItemInventoryChannel inventory) {
		if (trans.getEstimatedQty() != null) {
			if (inventory.getInTransitOtherQty() != null) {
				inventory.setInTransitOtherQty(inventory.getInTransitOtherQty()
						+ trans.getEstimatedQty());
			} else {
				inventory.setInTransitOtherQty(trans.getEstimatedQty());
			}
		}
	}


    @Deprecated
	private void orderInTransitMovements(ItemInventoryTransaction trans,
			ItemInventoryChannel inventory) {
		if (trans.getEstimatedQty() != null) {
			if (inventory.getInTransitMovementQty() != null) {
				inventory.setInTransitMovementQty(inventory
						.getInTransitMovementQty() + trans.getEstimatedQty());
			} else {
				inventory.setInTransitMovementQty(trans.getEstimatedQty());
			}
		}
	}



    @Deprecated
	private void orderInTransitProcurements(ItemInventoryTransaction trans,
			ItemInventoryChannel inventory) {
		if (trans.getEstimatedQty() != null) {
			if (inventory.getInTransitPurchaseQty() != null) {
				inventory.setInTransitPurchaseQty(inventory
						.getInTransitPurchaseQty() + trans.getEstimatedQty());
			} else {
				inventory.setInTransitPurchaseQty(trans.getEstimatedQty());
			}
		}
	}
    */

	private ItemInventoryChannel getOrCreateItemInventory(
			ItemInventoryTransaction trans) {
		ItemInventoryChannel inventoryChannel = itemInventoryDao
				.getDbItemInventory(trans.getChannel(), trans.getWarehouse(),
                        trans.getLocationType(), trans.getProductId());

        if(inventoryChannel == null){
            inventoryChannel = new ItemInventoryChannel(trans.getChannel(),
                    trans.getWarehouse(), trans.getLocationType(),
                    trans.getProductId(), trans.getProductCode());
            inventoryChannel.setOnHandQty(0.0);
            inventoryChannel.setFrozenQty(0.0);
            inventoryChannel.setAllocatedQty(0.0);
            inventoryChannel.setAllocatedDistributionQty(0.0);
            inventoryChannel.setAllocatedOtherQty(0.0);
            inventoryChannel.setInTransitPurchaseQty(0.0);
            inventoryChannel.setInTransitMovementQty(0.0);
            inventoryChannel.setInTransitOtherQty(0.0);
        }
		return inventoryChannel;
	}

	/**
	 * 关联原单计算预估数量,冲掉原通知单数量
	 * @param tran
	 */
	private void cancelEstimatedQty(ItemInventoryTransaction tran) {
		if (wmsReceiptHeaderHisDao != null) {
            tran.setEstimatedQty(wmsReceiptHeaderHisDao.getTotalQty(
                    tran.getWarehouse(),
                    tran.getBusinessNo(),
                    BusinessTypeConstants.BusinessType2ReceiptTypeWMS(tran.getBusinessType()),
                    tran.getProductCode()
            ));
		}
	}

    private boolean isSameTransaction(ItemInventoryTransaction tran1, ItemInventoryTransaction tran2){
         return tran1.getWarehouse().equals(tran2.getWarehouse()) &&
                 tran1.getBusinessNo().equals(tran2.getBusinessNo()) &&
                 tran1.getBusinessType().equals(tran2.getBusinessType()) &&
                 tran1.getProductCode().equals(tran2.getProductCode());
    }

    public void receiptWMS(String batchUser) {

        //重掉所有原来的单据
        List<ScmUploadReceiptHeaderHis> receipts = scmUploadReceiptHeaderHisDao.getUnhandledReceipts();
        if (receipts != null) {
            Date now = new Date();
            Long batchId = now.getTime();

            List<String> deliveredIds = new ArrayList<String>();

            for (ScmUploadReceiptHeaderHis receipt : receipts) {
                try
                {
                    //RECEIPT_TYPE = 4( 全退包，出库转进货由仓库发起，不需要冲减预扣)
                    if(!"4".equals(receipt.getReceiptType())){
                        deliveredIds.add(receipt.getReceiptId());
                    }

                    receipt.setBatchId(batchId.toString());
                    receipt.setBatchDate(now);

                    for (ScmUploadReceiptDetailHis detail : receipt.getDetails()) {

                        detail.setBatchId(batchId.toString());
                        detail.setBatchDate(now);

                        ItemInventoryTransaction trans = new ItemInventoryTransaction();
                        trans.setChannel(ChannelConstants.Default);
                        trans.setWarehouse(receipt.getWarehouse());
                        trans.setSourceId(TransactionSourceConstants.WMS); //收货单
                        trans.setSourceDesc("WMS");
                        trans.setBusinessNo(receipt.getReceiptId());
                        trans.setBusinessDate(receipt.getReceiptDate());
                        trans.setLocationType(LocationTypeConstants.status2LocationType(detail.getItemStatus()));
                        trans.setCreated(now);
                        trans.setModified(now);
                        trans.setCreatedBy(batchUser);
                        trans.setModifiedBy(batchUser);
                        trans.setProductId(0L);
                        trans.setProductCode(detail.getItem());
                        trans.setEstimatedQty(0.0); // 放后面计算
                        if(detail.getTotalQty() != null){
                            trans.setQty(Double.valueOf(detail.getTotalQty()));
                        } else {
                            trans.setQty(0.0);
                        }
                        trans.setBusinessType(BusinessTypeConstants.ReceiptType2BusinessTypeWMS(receipt.getReceiptType()));
                        insertItemInventoryTransaction(trans);
                        //deliveredTrans.add(trans);
                    }
                    scmUploadReceiptHeaderHisDao.batchLog(receipt);
                }
                catch (RuntimeException ex){
                    LOG.error(ex.getMessage());
                    throw ex;
                }
            }

            if(deliveredIds.size() > 0){
                cancelTransitedTransactions(batchUser, deliveredIds, now);
            }
        }
    }

    private void cancelTransitedTransactions(String batchUser, List<String> deliveredIds, Date now){
        /*把相关的通知单冲掉*/
        //List<WmsReceiptHeaderHis> receipts = wmsReceiptHeaderHisDao.getArrivedReceipts(deliveredIds);
        for (String receiptId : deliveredIds) {
            WmsReceiptHeaderHis receipt = wmsReceiptHeaderHisDao.getArrivedReceipt(receiptId);
            if(receipt != null){
                for (WmsReceiptDetailHis detail : receipt.getDetails()) {

                    String businessType = BusinessTypeConstants.ReceiptType2BusinessTypeWMS(receipt.getReceiptType());
                    if(businessType != null)
                    {
                        ItemInventoryTransaction trans = new ItemInventoryTransaction();
                        trans.setChannel(ChannelConstants.Default);
                        trans.setWarehouse(receipt.getWarehouse());
                        trans.setSourceId(TransactionSourceConstants.WMS);  //收货通知单红冲
                        trans.setSourceDesc("WMS");
                        trans.setBusinessNo(receipt.getReceiptId());
                        trans.setBusinessType(businessType);
                        trans.setBusinessDate(receipt.getUpdate());
                        if(BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_CUSTOMER.equals(businessType) ||    //153
                           BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_WHOLESALE.equals(businessType) ||   //155
                           BusinessTypeConstants.INVENTORY_STOCK_IN_FIX_VENDOR.equals(businessType) ||          //158
                           BusinessTypeConstants.INVENTORY_STOCK_IN_LOAN.equals(businessType) ||                 //160
                           BusinessTypeConstants.INVENTORY_STOCK_IN_RETURN_HALF.equals(businessType)            //162
                        ) {
                            trans.setLocationType(LocationTypeConstants.INVENTORY_DEFECTIVE_PRODUCT);
                        } else {
                            trans.setLocationType(LocationTypeConstants.INVENTORY_QUALITY_PRODUCT);
                        }
                        trans.setCreated(now);
                        trans.setModified(now);
                        trans.setCreatedBy(batchUser);
                        trans.setModifiedBy(batchUser);
                        trans.setProductId(0L);
                        trans.setProductCode(detail.getItem());
                        //(编号15*，后续计冲减)
                        trans.setEstimatedQty(Double.valueOf(detail.getTotalQty()));
                        trans.setQty(0.0);
                        insertItemInventoryTransaction(trans);
                    }
                }
            }
        }
    }


    /**
     * 入库操作
     * @param batchUser
     */
    @Deprecated
	public void receiptWMS2(String batchUser) {
		if (scmUploadReceiptHeaderHisDao != null) {
			List<ScmUploadReceiptHeaderHis> receipts = scmUploadReceiptHeaderHisDao.getUnhandledReceipts();
			if (receipts != null) {
				Date now = new Date();
				Long batchId = now.getTime();
				// 合并相同产品WMS收货单生成一条收货事务记录
				List<ItemInventoryTransaction> qualifiedTrans = new ArrayList<ItemInventoryTransaction>();
                List<ItemInventoryTransaction> destroyedTrans = new ArrayList<ItemInventoryTransaction>();

				for (ScmUploadReceiptHeaderHis receipt : receipts) {
                    try
                    {
                        receipt.setBatchId(batchId.toString());
                        receipt.setBatchDate(now);

                        for (ScmUploadReceiptDetailHis detail : receipt.getDetails()) {

                            detail.setBatchId(batchId.toString());
                            detail.setBatchDate(now);

                            ItemInventoryTransaction tran = new ItemInventoryTransaction();
                            tran.setChannel(ChannelConstants.Default);
                            tran.setWarehouse(receipt.getWarehouse());
                            tran.setSourceId(TransactionSourceConstants.WMS); //收货单
                            tran.setSourceDesc("WMS");
                            tran.setBusinessNo(receipt.getReceiptId());
                            tran.setBusinessDate(receipt.getReceiptDate());
                            tran.setLocationType(LocationTypeConstants.status2LocationType(detail.getItemStatus()));
                            tran.setCreated(now);
                            tran.setModified(now);
                            tran.setCreatedBy(batchUser);
                            tran.setModifiedBy(batchUser);
                            tran.setProductId(0L);
                            tran.setProductCode(detail.getItem());
                            tran.setEstimatedQty(0.0); // 放后面计算
                            if(detail.getTotalQty() != null){
                                tran.setQty(Double.valueOf(detail.getTotalQty()));
                            } else {
                                tran.setQty(0.0);
                            }
                            tran.setBusinessType(BusinessTypeConstants.ReceiptType2BusinessTypeWMS(receipt.getReceiptType()));
                            if(LocationTypeConstants.INVENTORY_QUALITY_PRODUCT.equals(tran.getLocationType())){
                                qualifiedTrans.add(tran);
                            } else {
                                destroyedTrans.add(tran);
                            }
                        }
                        scmUploadReceiptHeaderHisDao.batchLog(receipt);

                    }
                    catch (RuntimeException ex){
                        LOG.error(ex.getMessage());
                        throw ex;
                    }
				}

                if(qualifiedTrans.size() > 0 || destroyedTrans.size() > 0){
                    handleItemInventoryTransactionsForDestroy(qualifiedTrans, destroyedTrans);
                }

			}
		}
	}

    /**
     * 计算回冲正品预估量
     * @param qualifiedTrans
     * @param destroyedTrans
     */
    @Deprecated
    private void handleItemInventoryTransactionsForDestroy(List<ItemInventoryTransaction> qualifiedTrans, List<ItemInventoryTransaction> destroyedTrans){

        //回冲正品预估量
        while(qualifiedTrans.size() > 0)
        {
            ItemInventoryTransaction tran = qualifiedTrans.remove(0);
            if (tran.getBusinessType() != null) {
                try
                {
                    //正品回冲预估量
                    cancelEstimatedQty(tran);
                    //正品相同处理
                    for (ItemInventoryTransaction tran1 : new ArrayList<ItemInventoryTransaction>(qualifiedTrans)) {
                         if(isSameTransaction(tran, tran1)){
                             qualifiedTrans.remove(tran1);
                             tran1.setEstimatedQty(tran1.getQty());
                             insertItemInventoryTransaction(tran1);
                             tran.setEstimatedQty(tran.getEstimatedQty() - tran1.getQty());
                         }
                    }
                    //插入事务(根据类型把预估量分配到相关的量上)
                    insertItemInventoryTransaction(tran);
                    //所有残次品已经存在对应正品,直接插入事物并从列表中删除
                    for(ItemInventoryTransaction tran2 : new ArrayList<ItemInventoryTransaction>(destroyedTrans)){
                        if(isSameTransaction(tran, tran2)){
                            destroyedTrans.remove(tran2);
                            insertItemInventoryTransaction(tran2);
                        }
                    }

                }
                catch (Exception ex) {
                    LOG.error(ex.getMessage());
                }
            }
        }
        //全部是残次品,生成一个数量为０正品,预估量为通知单数量的正品事物
        //排除残次品重复情况
        while(destroyedTrans.size() > 0) {
            ItemInventoryTransaction tran = destroyedTrans.remove(0);
            if (tran.getBusinessType() != null) {
                try
                {
                    ItemInventoryTransaction tran1 = new ItemInventoryTransaction();
                    tran1.setLocationType(LocationTypeConstants.INVENTORY_QUALITY_PRODUCT);
                    tran1.setChannel(tran.getChannel());
                    tran1.setWarehouse(tran.getWarehouse());
                    tran1.setSourceId(tran.getSourceId());
                    tran1.setSourceDesc(tran.getSourceDesc());
                    tran1.setBusinessNo(tran.getBusinessNo());
                    tran1.setBusinessDate(tran.getBusinessDate());
                    tran1.setCreated(tran.getCreated());
                    tran1.setModified(tran.getModified());
                    tran1.setCreatedBy(tran.getCreatedBy());
                    tran1.setModifiedBy(tran.getModifiedBy());
                    tran1.setProductId(tran.getProductId());
                    tran1.setBusinessType(tran.getBusinessType());
                    tran1.setProductCode(tran.getProductCode());
                    tran1.setEstimatedQty(0.0); // 放后面计算
                    tran1.setQty(0.0); //数量为0
                    cancelEstimatedQty(tran1);
                    //插入正品
                    insertItemInventoryTransaction(tran1);
                    //插入残次品
                    insertItemInventoryTransaction(tran);
                    //插入残次品重复项
                    for(ItemInventoryTransaction tran2 : new ArrayList<ItemInventoryTransaction>(destroyedTrans)){
                        if(isSameTransaction(tran, tran2)){
                            destroyedTrans.remove(tran2);
                            insertItemInventoryTransaction(tran2);
                        }
                    }
                }
                catch (Exception ex) {
                    LOG.error(ex.getMessage());
                }
            }
        }
    }

    /**
     * 库存入库通知(收货)
     * @param batchUser
     */
    public void receiptSCM(String batchUser) {
        if (wmsReceiptHeaderHisDao != null) {
            List<WmsReceiptHeaderHis> receipts = wmsReceiptHeaderHisDao
                    .getUnhandledReceipts();
            Date now = new Date();
            Long batchId = now.getTime();

            for (WmsReceiptHeaderHis receipt : receipts) {

                receipt.setBatchId(batchId.toString());
                receipt.setBatchDate(now);

                for (WmsReceiptDetailHis detail : receipt.getDetails()) {
                    detail.setBatchId(batchId.toString());
                    detail.setBatchDate(now);

                    String businessType = BusinessTypeConstants.ReceiptType2BusinessTypeSCM(receipt.getReceiptType());
                    if(businessType != null)
                    {
                        ItemInventoryTransaction trans = new ItemInventoryTransaction();
                        trans.setChannel( ChannelConstants.Default);
                        trans.setWarehouse(receipt.getWarehouse());
                        trans.setSourceId(TransactionSourceConstants.SCM);  //收货通知单
                        trans.setSourceDesc("SCM");
                        trans.setBusinessNo(receipt.getReceiptId());
                        trans.setBusinessType(businessType);
                        trans.setBusinessDate(receipt.getUpdate());
                        if(BusinessTypeConstants.ORDER_IN_TRANSIT_SALES_CUSTOMER.equals(businessType) ||  //103
                           BusinessTypeConstants.ORDER_IN_TRANSIT_SALES_WHOLESALE.equals(businessType) || //105
                           BusinessTypeConstants.ORDER_IN_TRANSIT_FIX_VENDOR.equals(businessType) ||       //108
                           BusinessTypeConstants.ORDER_IN_TRANSIT_LOAN.equals(businessType) ||              //110
                           BusinessTypeConstants.ORDER_IN_TRANSIT_RETURN_HALF.equals(businessType)         //112
                           ) {
                            trans.setLocationType(LocationTypeConstants.INVENTORY_DEFECTIVE_PRODUCT);
                        } else {
                            trans.setLocationType(LocationTypeConstants.INVENTORY_QUALITY_PRODUCT);
                        }
                        trans.setCreated(now);
                        trans.setModified(now);
                        trans.setCreatedBy(batchUser);
                        trans.setModifiedBy(batchUser);
                        trans.setProductId(0L);
                        trans.setProductCode(detail.getItem());
                        trans.setEstimatedQty(Double.valueOf(detail.getTotalQty()));
                        trans.setQty(0.0);
                        insertItemInventoryTransaction(trans);
                    }
                }

                wmsReceiptHeaderHisDao.batchLog(receipt);
            }
        }
    }

    /**
     * 调整库存(正残转化)
     * @param batchUser
     */
    @Transactional
    public void recountItemStatus(String batchUser) {
        if(scmItemStatusChangeHisDao != null){
            Date now = new Date();
            Long batchId = now.getTime();
            List<ScmItemStatusChangeHis> items =scmItemStatusChangeHisDao.getStatusChangedItems();
            for (ScmItemStatusChangeHis item : items) {

                try
                {
                    if(item.getChangeId() == null) continue;
                    if(item.getFromStatus() == null) continue;
                    if(item.getToStatus() == null) continue;

                    //源状态记录
                    ItemInventoryTransaction trans = new ItemInventoryTransaction();
                    trans.setChannel( ChannelConstants.Default);
                    trans.setWarehouse(item.getWarehouse());
                    trans.setSourceId(TransactionSourceConstants.WMS);
                    trans.setSourceDesc("WMS");
                    trans.setBusinessNo(String.valueOf(item.getChangeId()));
                    trans.setBusinessDate(item.getDateTimeStamp());
                    trans.setBusinessType(BusinessTypeConstants.INVENTORY_STOCK_MOVEMENT);
                    trans.setBusinessChildType("1"); //报损
                    trans.setLocationType(LocationTypeConstants.status2LocationType(item.getFromStatus()));
                    trans.setCreated(now);
                    trans.setModified(now);
                    trans.setCreatedBy(batchUser);
                    trans.setModifiedBy(batchUser);
                    trans.setProductId(0L);
                    trans.setProductCode(item.getItem());
                    trans.setEstimatedQty(0.0);
                    if(item.getTotalQty() != null){
                        trans.setQty(1.0 * item.getTotalQty());
                    } else {
                        trans.setQty(0.0);
                    }
                    insertItemInventoryTransaction(trans);
                    //目标状态记录
                    trans = new ItemInventoryTransaction();
                    trans.setChannel( ChannelConstants.Default);
                    trans.setWarehouse(item.getWarehouse());
                    trans.setSourceId(TransactionSourceConstants.WMS);
                    trans.setSourceDesc("WMS");
                    trans.setBusinessNo(String.valueOf(item.getChangeId()));
                    trans.setBusinessDate(item.getDateTimeStamp());
                    trans.setBusinessType(BusinessTypeConstants.INVENTORY_STOCK_MOVEMENT);
                    trans.setBusinessChildType("2");  //报溢
                    trans.setLocationType(LocationTypeConstants.status2LocationType(item.getToStatus()));
                    trans.setCreated(now);
                    trans.setModified(now);
                    trans.setCreatedBy(batchUser);
                    trans.setModifiedBy(batchUser);
                    trans.setProductId(0L);
                    trans.setProductCode(item.getItem());
                    trans.setEstimatedQty(0.0);
                    if(item.getTotalQty() != null){
                        trans.setQty(1.0 * item.getTotalQty());
                    } else {
                        trans.setQty(0.0);
                    }
                    insertItemInventoryTransaction(trans);
                    item.setBatchId(batchId.toString());
                    item.setBatchDate(now);
                    scmItemStatusChangeHisDao.batchLog(item);
                }
                catch (RuntimeException ex){
                    LOG.error(ex.getMessage());
                    throw ex;
                }
            }
        }
        //throw new RuntimeException("unchecked异常,分布式事务测试!!!!!");
    }

    /**
     * 库存调整(数量)
     * @param batchUser
     */
    public void recountItemAdjustment(String batchUser) {
        if(scmOnhandAdjustmentHisDao != null){
            Date now = new Date();
            Long batchId = now.getTime();
            List<ScmOnhandAdjustmentHis> items =scmOnhandAdjustmentHisDao.getAdjustedItems();
            for (ScmOnhandAdjustmentHis item : items) {
                try
                {
                    if(item.getAdjustmentId() == null) continue;
                    if(item.getItemStatus() == null) continue;
                    if(item.getAdjustmentType() == null) continue;

                    ItemInventoryTransaction trans = new ItemInventoryTransaction();
                    trans.setChannel( ChannelConstants.Default);
                    trans.setWarehouse(item.getWarehouse());
                    trans.setSourceId(TransactionSourceConstants.WMS);
                    trans.setSourceDesc("WMS");
                    trans.setBusinessNo(String.valueOf(item.getAdjustmentId()));
                    trans.setBusinessDate(item.getDateTimeStamp());
                    trans.setBusinessType(BusinessTypeConstants.INVENTORY_STOCK_RECOUNT);
                    trans.setBusinessChildType(String.valueOf(item.getAdjustmentType()));
                    trans.setLocationType(LocationTypeConstants.status2LocationType(item.getItemStatus()));
                    trans.setCreatedBy(batchUser);
                    trans.setModifiedBy(batchUser);
                    trans.setCreated(now);
                    trans.setModified(now);
                    trans.setProductId(0L);
                    trans.setProductCode(item.getItem());
                    trans.setEstimatedQty(0.0);
                    if(item.getTotalQty() != null){
                        //如果是报溢[2],接口库本身就是负数需要先矫正
                        if(item.getAdjustmentType() == 1L) {
                            trans.setQty(Double.valueOf(item.getTotalQty()));
                        }else if(item.getAdjustmentType() == 2L) {
                            //接口库TotalQty本身为负数
                            trans.setQty(Double.valueOf(item.getTotalQty()) * -1);
                        }else{
                            trans.setQty(0.0);
                        }
                    } else {
                        trans.setQty(0.0);
                    }
                    insertItemInventoryTransaction(trans);
                    item.setBatchId(batchId.toString());
                    item.setBatchDate(now);
                    scmOnhandAdjustmentHisDao.batchLog(item);
                }
                catch (RuntimeException ex){
                    LOG.error(ex.getMessage());
                    throw ex;
                }
            }
        }
    }

}
