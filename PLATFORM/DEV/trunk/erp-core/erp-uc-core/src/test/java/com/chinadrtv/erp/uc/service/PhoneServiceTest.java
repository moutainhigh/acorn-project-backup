package com.chinadrtv.erp.uc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.test.SpringTest;
import com.chinadrtv.erp.uc.dao.PhoneDao;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.uc.dto.UpdateLastCallDateDto;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-7
 * Time: 上午10:59
 * To change this template use File | Settings | File Templates.
 */

public class PhoneServiceTest extends SpringTest {

	@Autowired
	private SessionFactory sessionFactory;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private ChangeRequestService changeRequestService;
    @Autowired
    private PhoneDao phoneDao;

    @Test
    public void testSetPrimePhone1() throws Exception {
        String contactId = "27934911";
        Long phoneId = 2045882685L;
        Phone phone = phoneService.get(phoneId);
        Assert.assertEquals("电话初始化是主电话", "N", phone.getPrmphn());
        phoneService.setPrimePhone(contactId, phoneId.toString());
    }

    @Test
    public void testAddSuccess() throws ServiceTransactionException {
        Phone phone = new Phone();
        phone.setContactid("1122");
        phone = phoneService.addPhone(phone);
        Assert.assertTrue("增加客户电话失败", phone != null);
    }

    @Test
//    @Rollback(false)
    public void testAddPhoneWithProcessPrm() throws ServiceTransactionException {
        Phone phone = new Phone();
        phone.setContactid("27934583");
        phone.setPhn2("18699966652");
        phone.setPhonetypid("4");
        phone.setPrmphn("Y");
        phone = phoneService.addPhoneWithProcessPrm(phone);
        Assert.assertTrue("增加客户电话失败", phone != null);
    }

    @Test
    public void testAddPhoneListSuccess() throws ServiceTransactionException {
        Phone phone1 = new Phone();
        phone1.setContactid("910399224");
        phone1.setPhonetypid("4");
        phone1.setPhn2("13222224356");
        Phone phone2 = new Phone();
        phone2.setContactid("910399224");
        phone2.setPhonetypid("4");
        phone2.setPhn2("13222224356");
        List<Phone> phoneList = new ArrayList<Phone>();
        phoneList.add(phone1);
        phoneList.add(phone2);
        phoneService.addPhoneList(phoneList);
    }

    @Test
    public void testUpdate() throws ServiceTransactionException {
        Phone phone = new Phone();
        phone.setPhoneid(2045880301L);
        phone.setPrmphn("Y");
        phone.setPhn1("010");
        phone.setPhn2("13345674");
        phone.setPhn3("2222");
        phone.setPhonetypid("3");
        phoneService.updatePhone(phone);
        Assert.assertTrue("更新客户电话失败", phone != null);
    }

    @Test
    public void testUpdateList() throws ServiceTransactionException {
        Phone phone = new Phone();
        phone.setPhoneid(2045880301L);
        phone.setPrmphn("Y");
        phone.setPhn1("010");
        phone.setPhn2("13345674");
        phone.setPhn3("2222");
        phone.setPhonetypid("3");
        Phone phone2 = new Phone();
        phone2.setPhoneid(2045878302L);
        phone2.setPrmphn("N");
        phone2.setPhn1("020");
        phone2.setPhn2("133445674");
        phone2.setPhn3("2222");
        phone2.setPhonetypid("4");
        List<Phone> phoneList = new ArrayList<Phone>();
        phoneList.add(phone);
        phoneList.add(phone2);
        phoneService.updatePhoneList(phoneList);
    }

    @Test
    public void testDelete() throws ServiceTransactionException {
        Phone phone = new Phone();
        phone.setContactid("27934904");
        phone.setPhoneid(2045882673L);
        phoneService.deletePhone(phone);
    }

    @Test
    public void testDeleteList() throws ServiceTransactionException {
        Phone phone = new Phone();
        phone.setContactid("27934904");
        phone.setPhoneid(2045882673L);

        Phone phone2 = new Phone();
        phone2.setContactid("27934911");
        phone2.setPhoneid(2045882685L);

        List<Phone> phoneList = new ArrayList<Phone>();
        phoneList.add(phone);
        phoneList.add(phone2);
        phoneService.deletePhoneList(phoneList);
    }

    @Test
    public void testFindByContactId() {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        Map map = phoneService.findByContactId("910698284", dataGridModel);
        Assert.assertEquals("分页查找数目错误", "2", map.get("total").toString());
    }


    @Test
    @Rollback(false)
    public void applyAddRequest() {
        String contactId = "910392274";
        String userId = "27427";
        String remark = "创建流程";
        String deptId = "lizhi7";

        UserBpm userBpm = new UserBpm();
        userBpm.setContactID(contactId);
        userBpm.setCreateDate(new Date());
        userBpm.setCreateUser(userId);
        userBpm.setDepartment(deptId);

        String batchId = changeRequestService.createChangeRequest(userBpm);

        Phone phone = new Phone();
        phone.setContactid(contactId);
        phone.setPhn1("021");
        phone.setPhn2("32039282");
        phone.setPhn3("001");
        phone.setPhonetypid("1");
        phone.setPrmphn("Y");
        String phoneNo = phone.getPhn1() + "-" + phone.getPhn2() + "-" + phone.getPhn3();
        phone.setPhoneNum(phoneNo);

        phoneService.applyAddRequest(phone, remark, userId, deptId, batchId);
    }


    @Test
    //@Rollback(false)
    public void applyUpdateRequest() throws Exception {
        String contactId = "910392274";
        Long phoneId = 2045878305L;
        String userId = "27427";
        String remark = "创建流程";
        String deptId = "lizhi7";

        UserBpm userBpm = new UserBpm();
        userBpm.setContactID(contactId);
        userBpm.setCreateDate(new Date());
        userBpm.setCreateUser(userId);
        userBpm.setDepartment(deptId);

        String batchId = changeRequestService.createChangeRequest(userBpm);

        Phone phone = phoneService.get(phoneId);
        phone.setPhn2("32039282");
        String phoneNo = phone.getPhn1() + "-" + phone.getPhn2() + "-" + phone.getPhn3();
        phone.setPhoneNum(phoneNo);

        phoneService.applyUpdateRequest(phone, remark, userId, deptId, batchId);
    }

    @Test
    //@Rollback(false)
    public void finishUpdateRequest() throws Exception {
        String instId = "310501";
        Long phoneId = 2045878305L;
        String userId = "19200";
        String remark = "创建流程";
        phoneService.finishUpdateRequest(phoneId, remark, userId, userId, instId);
    }

    @Test
//    @Rollback(false)
    public void testUpdateLastCallDate() {
        UpdateLastCallDateDto updateLastCallDateDto = new UpdateLastCallDateDto("27934583", "18699966650", new Date());
        phoneService.updateLastCallDate(updateLastCallDateDto);
    }

    @Test
//    @Rollback(false)
    public void testUpdateLastCallDate2() {
        UpdateLastCallDateDto updateLastCallDateDto = new UpdateLastCallDateDto("27934584", 2045878303L, new Date());
        phoneService.updateLastCallDate(updateLastCallDateDto);
    }

    @Test
    public void testSplicePhone() {
        //11位手机
        String mobile = "15000334757";
        PhoneAddressDto mobileDto = phoneService.splicePhone(mobile);
        Assert.assertEquals(mobile, mobileDto.getPhn2());
        Assert.assertEquals("4", mobileDto.getPhonetypid());
        Assert.assertEquals("14", mobileDto.getProvid());
        Assert.assertTrue(128 == mobileDto.getCityid());

        //前置86的手机
        String mobile1 = "8615000334757";
        PhoneAddressDto mobileDto1 = phoneService.splicePhone(mobile1);
        Assert.assertEquals("15000334757", mobileDto1.getPhn2());
        Assert.assertEquals("4", mobileDto1.getPhonetypid());
        Assert.assertEquals("14", mobileDto1.getProvid());
        Assert.assertTrue(128 == mobileDto1.getCityid());

        //前置+86的手机
        String mobile2 = "+8615000334757";
        PhoneAddressDto mobileDto2 = phoneService.splicePhone(mobile2);
        Assert.assertEquals("15000334757", mobileDto2.getPhn2());
        Assert.assertEquals("4", mobileDto2.getPhonetypid());
        Assert.assertEquals("14", mobileDto2.getProvid());
        Assert.assertTrue(128 == mobileDto2.getCityid());

        //前置0的手机
        String mobile33 = "015000334757";
        PhoneAddressDto mobileDto33 = phoneService.splicePhone(mobile33);
        Assert.assertEquals("15000334757", mobileDto33.getPhn2());
        Assert.assertEquals("4", mobileDto33.getPhonetypid());
        Assert.assertEquals("14", mobileDto33.getProvid());
        Assert.assertTrue(128 == mobileDto33.getCityid());

        //3位区号的号码
        String mobile3 = "02145454545";
        PhoneAddressDto mobileDto3 = phoneService.splicePhone(mobile3);
        Assert.assertEquals("021", mobileDto3.getPhn1());
        Assert.assertEquals("45454545", mobileDto3.getPhn2());
        Assert.assertEquals("1", mobileDto3.getPhonetypid());
        Assert.assertEquals("14", mobileDto3.getProvid());
        Assert.assertTrue(128 == mobileDto3.getCityid());

        //3位区号的号码
        String mobile31 = "01045454545";
        PhoneAddressDto mobileDto31 = phoneService.splicePhone(mobile31);
        Assert.assertEquals("021", mobileDto31.getPhn1());
        Assert.assertEquals("45454545", mobileDto31.getPhn2());
        Assert.assertEquals("1", mobileDto31.getPhonetypid());
        Assert.assertEquals("14", mobileDto31.getProvid());
        Assert.assertTrue(128 == mobileDto31.getCityid());

        //4位区号7位数字的号码
        String mobile4 = "07317493188";
        PhoneAddressDto mobileDto4 = phoneService.splicePhone(mobile4);
        Assert.assertEquals("0731", mobileDto4.getPhn1());
        Assert.assertEquals("7493188", mobileDto4.getPhn2());
        Assert.assertEquals("1", mobileDto4.getPhonetypid());
        Assert.assertEquals("22", mobileDto4.getProvid());
        Assert.assertTrue(238 == mobileDto4.getCityid());

        //4位区号8位数字的号码
        String mobile5 = "073187493188";
        PhoneAddressDto mobileDto5 = phoneService.splicePhone(mobile5);
        Assert.assertEquals("0731", mobileDto5.getPhn1());
        Assert.assertEquals("87493188", mobileDto5.getPhn2());
        Assert.assertEquals("1", mobileDto5.getPhonetypid());
        Assert.assertEquals("22", mobileDto5.getProvid());
        Assert.assertTrue(238 == mobileDto5.getCityid());

        //7为本地固话
        String mobile51 = "7493188";
        PhoneAddressDto mobileDto51 = phoneService.splicePhone(mobile51);
        Assert.assertEquals("0731", mobileDto51.getPhn1());
        Assert.assertEquals("87493188", mobileDto51.getPhn2());
        Assert.assertEquals("1", mobileDto51.getPhonetypid());
        Assert.assertEquals("22", mobileDto51.getProvid());
        Assert.assertTrue(238 == mobileDto51.getCityid());

        //8为本地固话
        String mobile52 = "87493188";
        PhoneAddressDto mobileDto52 = phoneService.splicePhone(mobile52);
        Assert.assertEquals("0731", mobileDto52.getPhn1());
        Assert.assertEquals("87493188", mobileDto52.getPhn2());
        Assert.assertEquals("1", mobileDto52.getPhonetypid());
        Assert.assertEquals("22", mobileDto52.getProvid());
        Assert.assertTrue(238 == mobileDto52.getCityid());

        //错误号码测试 3位区号7位数字的号码
        String mobile6 = "0213456789";
        PhoneAddressDto mobileDto6 = phoneService.splicePhone(mobile6);
        Assert.assertNull(mobileDto6.getPhn1());
        Assert.assertNull(mobileDto6.getPhn2());
        Assert.assertNull(mobileDto6.getPhonetypid());
        Assert.assertNull(mobileDto6.getProvid());
        Assert.assertNull(mobileDto6.getCityid());

        //错误号码测试 10位手机号
        String mobile7 = "1354565678";
        PhoneAddressDto mobileDto7 = phoneService.splicePhone(mobile7);
        Assert.assertNull(mobileDto7.getPhn1());
        Assert.assertNull(mobileDto7.getPhn2());
        Assert.assertNull(mobileDto7.getPhonetypid());
        Assert.assertNull(mobileDto7.getProvid());
        Assert.assertNull(mobileDto7.getCityid());

        //错误号码测试 12位手机号
        String mobile8 = "135456567888";
        PhoneAddressDto mobileDto8 = phoneService.splicePhone(mobile8);
        Assert.assertNull(mobileDto8.getPhn1());
        Assert.assertNull(mobileDto8.getPhn2());
        Assert.assertNull(mobileDto8.getPhonetypid());
        Assert.assertNull(mobileDto8.getProvid());
        Assert.assertNull(mobileDto8.getCityid());
    }

    @Test
//    @Rollback(false)
    public void testAddToBlackList() {
        phoneService.addToBlackList(2045878300L);
    }

    @Test
//    @Rollback(false)
    public void testRemoveFromBlackList() {
        phoneService.removeFromBlackList(2045878300L);
    }
    
   // @Test
    public void get(){
    	Long phoneId = 13164L;
    	Long begin = System.currentTimeMillis();
    	Phone phone = phoneService.get(phoneId);
    	Long end = System.currentTimeMillis();
    	
    	System.out.println(end-begin);
    }
    
/*    @Test
    @Rollback(false)
    public void update(){
    	Long phoneId = 13164L;
    	Long begin = System.currentTimeMillis();
    	
    	Phone phone = phoneService.get(phoneId);
    	phone.setPhn1("021");
    	phoneDao.saveOrUpdate(phone);
    	Session session = sessionFactory.getCurrentSession();
    	session.flush();
    	
    	Long end = System.currentTimeMillis();
    	System.out.println(end-begin);
    }*/
    
    @Test
    //@Rollback(false)
    public void updatePhone(){
    	Long phoneId = 13164L;
    	Long begin = System.currentTimeMillis();
    	
    	Phone phone = phoneService.get(phoneId);
    	phone.setPhn1("021");
    	phoneDao.updatePhone(phone);
    	Session session = sessionFactory.getCurrentSession();
    	session.flush();
    	
    	Long end = System.currentTimeMillis();
    	System.out.println(end-begin);
    }
}
