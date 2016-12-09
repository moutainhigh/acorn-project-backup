package com.chinadrtv.erp.ic.service.impl;

import com.chinadrtv.erp.ic.dao.ItemInventoryIntransitDao;
import com.chinadrtv.erp.ic.model.NcIntransitItem;
import com.chinadrtv.erp.ic.service.ItemInventoryIntransitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi.gao
 * Date: 13-8-5
 * Time: 下午1:26
 * To change this template use File | Settings | File Templates.
 */
@Service("itemInventoryIntransitService")
public class ItemInventoryIntransitServiceImpl implements ItemInventoryIntransitService {

    private static final Logger LOG = LoggerFactory.getLogger(ItemInventoryIntransitServiceImpl.class);
    @Autowired
    private ItemInventoryIntransitDao itemInventoryIntransitDao;

    @Override
    public List<NcIntransitItem> GetNcIntransitItems(String nccode, String ncfreename) {
        return itemInventoryIntransitDao.GetNcIntransitItems(nccode, ncfreename);
    }
}
