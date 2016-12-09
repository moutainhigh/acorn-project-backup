package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.uc.test.AppTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xieguoqiang on 14-1-7.
 */
public class ToServiceServiceTest extends AppTest {

    @Autowired
    private ToServiceService toServiceService;

    @Test
    public void testQueryComplainPageList() {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        toServiceService.queryComplainPageList("943398257", dataGridModel);
    }

    @Test
    public void testQueryComplainPageList2() {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        toServiceService.queryComplainPageList("943398257L", dataGridModel);
    }
}
