package com.chinadrtv.erp.uc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.uc.dto.UpdateLastCallDateDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.model.PotentialContactPhone;
import com.chinadrtv.erp.test.SpringTest;
import org.springframework.test.annotation.Rollback;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-10
 * Time: 下午1:40
 * To change this template use File | Settings | File Templates.
 */
public class PotentialContactPhoneServiceTest extends SpringTest {

    @Autowired
    private PotentialContactPhoneService potentialContactPhoneService;

    @Test
    public void testAddPotentialContactPhone() throws ServiceTransactionException {
        PotentialContactPhone potentialContactPhone = new PotentialContactPhone();
        potentialContactPhone.setPhone1("021");
        potentialContactPhone.setPhone2("123456789");
        potentialContactPhone.setPhone3("2233");
        potentialContactPhone.setPhoneNum("021-123456789-2233");
        potentialContactPhone.setPhoneTypeId("1");
        potentialContactPhone.setPrmphn("Y");
        potentialContactPhone = potentialContactPhoneService.addPotentialContactPhone(potentialContactPhone);
        Assert.assertTrue("潜客保存失败", potentialContactPhone != null);
    }

    @Test
    public void testAddPotentialContactPhoneList() throws ServiceTransactionException {
        PotentialContactPhone potentialContactPhone1 = new PotentialContactPhone();
        potentialContactPhone1.setPhone1("021");
        potentialContactPhone1.setPhone2("123456789");
        potentialContactPhone1.setPhone3("2233");
        potentialContactPhone1.setPhoneNum("021-123456789-2233");
        potentialContactPhone1.setPhoneTypeId("1");
        potentialContactPhone1.setPrmphn("Y");
        PotentialContactPhone potentialContactPhone2 = new PotentialContactPhone();
        potentialContactPhone2.setPhone1("0211");
        potentialContactPhone2.setPhone2("1123456789");
        potentialContactPhone2.setPhone3("22331");
        potentialContactPhone2.setPhoneNum("0211-1123456789-22331");
        potentialContactPhone2.setPhoneTypeId("2");
        potentialContactPhone2.setPrmphn("N");
        List<PotentialContactPhone> potentialContactPhoneList = new ArrayList<PotentialContactPhone>();
        potentialContactPhoneList.add(potentialContactPhone1);
        potentialContactPhoneList.add(potentialContactPhone2);
        potentialContactPhoneService.addPotentialContactPhoneList(potentialContactPhoneList);
    }

    @Test
    public void testSetPrimePotentialContactPhone() {
        potentialContactPhoneService.setPrimePotentialContactPhone("2050", "6");
    }

    @Test
    public void testUpdate() throws ServiceTransactionException {
        PotentialContactPhone phone = new PotentialContactPhone();
        phone.setId(6L);
        phone.setPrmphn("Y");
        phone.setPhone1("030");
        phone.setPhone2("13345674");
        phone.setPhone3("2222");
        phone.setPhoneTypeId("3");
        potentialContactPhoneService.updatePotentialContactPhone(phone);
        Assert.assertTrue("更新客户电话失败", phone != null);
    }

    @Test
    public void testUpdateList() throws ServiceTransactionException {
        PotentialContactPhone phone = new PotentialContactPhone();
        phone.setId(6L);
        phone.setPrmphn("Y");
        phone.setPhone1("030");
        phone.setPhone2("13345674");
        phone.setPhone3("2222");
        phone.setPhoneTypeId("3");

        PotentialContactPhone phone2 = new PotentialContactPhone();
        phone2.setId(7L);
        phone2.setPrmphn("Y");
        phone2.setPhone1("040");
        phone2.setPhone2("13345674");
        phone2.setPhone3("2222");
        phone2.setPhoneTypeId("3");

        List<PotentialContactPhone> potentialContactPhoneList = new ArrayList<PotentialContactPhone>();
        potentialContactPhoneList.add(phone);
        potentialContactPhoneList.add(phone2);
        potentialContactPhoneService.updatePotentialContactPhoneList(potentialContactPhoneList);
    }

    @Test
    public void testDelete() throws ServiceTransactionException {
        PotentialContactPhone phone = new PotentialContactPhone();
        phone.setId(7L);
        phone.setPotentialContactId("2050");
        potentialContactPhoneService.deletePhone(phone);
    }

    @Test
    public void testDeleteList() throws ServiceTransactionException {
        PotentialContactPhone phone = new PotentialContactPhone();
        phone.setId(7L);
        phone.setPotentialContactId("2050");

        PotentialContactPhone phone2 = new PotentialContactPhone();
        phone2.setId(8L);
        phone2.setPotentialContactId("2050");

        List<PotentialContactPhone> phoneList = new ArrayList<PotentialContactPhone>();
        phoneList.add(phone);
        phoneList.add(phone2);
        potentialContactPhoneService.deletePhoneList(phoneList);
    }

    @Test
    public void testFindByContactId() {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        Map map = potentialContactPhoneService.findByPotentialContactId("2050", dataGridModel);
        Assert.assertEquals("分页查找数目错误", "3", map.get("total").toString());
    }

    @Test
//    @Rollback(false)
    public void testUpdateLastCallDate() {
        UpdateLastCallDateDto updateLastCallDateDto = new UpdateLastCallDateDto("8300", "12333322123", new Date());
        potentialContactPhoneService.updateLastCallDate(updateLastCallDateDto);
    }

    @Test
//    @Rollback(false)
    public void testUpdateLastCallDate2() {
        UpdateLastCallDateDto updateLastCallDateDto = new UpdateLastCallDateDto("8650", 238L, new Date());
        potentialContactPhoneService.updateLastCallDate(updateLastCallDateDto);
    }

    @Test
//    @Rollback(false)
    public void testAddToBlackList() {
        potentialContactPhoneService.addToBlackList(228L);
    }

    @Test
//    @Rollback(false)
    public void testRemoveFromBlackList() {
        potentialContactPhoneService.removeFromBlackList(228L);
    }
}
