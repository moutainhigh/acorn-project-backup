package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.ContactChange;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.uc.dto.*;
import com.chinadrtv.erp.uc.test.AppTest;
import com.chinadrtv.erp.util.PojoUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.annotation.Rollback;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xieguoqiang
 * Date: 13-5-3
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public class ContactServiceTest extends AppTest {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ChangeRequestService changeRequestService;

    @Test
    public void service_should_not_null_when_autowired() {
        Assert.assertNotNull(contactService);
    }

     @Test
     public void testSaveContact() throws ServiceTransactionException {
        CustomerDto customerDto = new CustomerDto();
        AddressDto address = new AddressDto();
         address.setAddress("上海");
         address.setState("03");
         address.setCityId(5);
         address.setAreaid(44211);
         address.setZip("201909");
         address.setContactid("910298214");
         address.setAddrtypid("2");
         address.setAddconfirmation("");
         address.setAddrconfirm("-1");
         address.setFlag("");
         address.setCountyId(3132);
         address.setIsdefault("0");

        customerDto.setAddressDto(address);
        customerDto.setName("XGQ TEST");
        customerDto.setSex("1");
        customerDto.setBirthday("1987-12-12");
        customerDto.setPhoneNum("12334434");
        customerDto.setContactType("T");

        Map<String, Object> resultMap = contactService.saveCustomer(customerDto);
         Assert.assertTrue("保存失败", resultMap.get("contactId") != null);
         Assert.assertTrue("保存失败", resultMap.get("potentialContactId") == null);
     }

    @Test
    public void testSavePotentialContact() throws ServiceTransactionException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("ACORN TEST");
        customerDto.setSex("0");
        customerDto.setBirthday("1989-12-12");
        customerDto.setPhoneNum("123324434");
        customerDto.setContactType("J");

        Map<String, Object> resultMap = contactService.saveCustomer(customerDto);
        Assert.assertTrue("保存失败", resultMap.get("contactId") == null);
        Assert.assertTrue("保存失败", resultMap.get("potentialContactId") != null);
    }

    @Test
    public void testUpdateContact() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setContactId("27935289");
        contactService.updateCustomer(customerDto);
    }

    @Test
    public void testUpdateContact2() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("test");
        customerDto.setContactId("27935289");
        contactService.updateCustomer(customerDto);
    }

    @Test
    public void testUpdatePotentialContact() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setPotentialContactId("2050");
        contactService.updateCustomer(customerDto);
    }

    @Test
    public void testUpdatePotentialContact2() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("test");
        customerDto.setPotentialContactId("2050");
        contactService.updateCustomer(customerDto);
    }

    @Test
    public void should_get_contact_when_contactId_exist() {
        String contactId = "910698284";
        Contact contact = null;
        try {
            contact = contactService.get(contactId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(contact);
        Assert.assertEquals("客户编号不符合", "910698284", contact.getContactid());
        Assert.assertEquals("名称不符合", "杨先花", contact.getName());
        Assert.assertEquals("生日不符合", "2011-06-25 00:00:00.0", contact.getBirthday().toString());
        Assert.assertEquals("性别不符合", "2", contact.getSex());
        Assert.assertEquals("客户类型不符合", "C", contact.getContacttype());
        Assert.assertEquals("DataType不符合", "A", contact.getDatatype());
        Assert.assertEquals("客户来源不符合", "WWW", contact.getCustomersource());
        Assert.assertEquals("创建日期不符合", "2011-06-25 16:06:20.0", contact.getCrdt().toString());
        Assert.assertEquals("创建时间不符合", "2011-06-25 16:06:20.0", contact.getCrtm().toString());
        Assert.assertEquals("创建人不符合", "20295", contact.getCrusr());
    }

    @Test(expected = ObjectRetrievalFailureException.class)
    public void should_throw_exception_when_contactId_not_exist() throws Exception {
        String contactId = "xxyyzz";
        contactService.get(contactId);
    }

    @Test
    public void should_get_contact_when_orderId_exist() {
        String orderId = "908455232";
        Contact contact = contactService.findByOrderId(orderId);
        Assert.assertNotNull(contact);
        Assert.assertEquals("客户编号不符合", "910698284", contact.getContactid());
        Assert.assertEquals("名称不符合", "杨先花", contact.getName());
        Assert.assertEquals("生日不符合", "2011-06-25 00:00:00.0", contact.getBirthday().toString());
        Assert.assertEquals("性别不符合", "2", contact.getSex());
        Assert.assertEquals("客户类型不符合", "C", contact.getContacttype());
        Assert.assertEquals("DataType不符合", "A", contact.getDatatype());
        Assert.assertEquals("客户来源不符合", "WWW", contact.getCustomersource());
        Assert.assertEquals("创建日期不符合", "2011-06-25 16:06:20.0", contact.getCrdt().toString());
        Assert.assertEquals("创建时间不符合", "2011-06-25 16:06:20.0", contact.getCrtm().toString());
        Assert.assertEquals("创建人不符合", "20295", contact.getCrusr());
    }

    @Test
    public void should_get_null_when_orderId_not_exist() {
        String orderId = "908455232ddfdf";
        Contact contact = contactService.findByOrderId(orderId);
        Assert.assertTrue("根据不存在的orderId查找客户有问题", contact == null);
    }

    @Test
    public void should_get_contact_when_shipmentId_exist() {
        String shipmentId = "1";
        Contact contact = contactService.findByShipmentId(shipmentId);
        Assert.assertNotNull(contact);
        Assert.assertEquals("客户编号不符合", "910698284", contact.getContactid());
        Assert.assertEquals("名称不符合", "杨先花", contact.getName());
        Assert.assertEquals("生日不符合", "2011-06-25 00:00:00.0", contact.getBirthday().toString());
        Assert.assertEquals("性别不符合", "2", contact.getSex());
        Assert.assertEquals("客户类型不符合", "C", contact.getContacttype());
        Assert.assertEquals("DataType不符合", "A", contact.getDatatype());
        Assert.assertEquals("客户来源不符合", "WWW", contact.getCustomersource());
        Assert.assertEquals("创建日期不符合", "2011-06-25 16:06:20.0", contact.getCrdt().toString());
        Assert.assertEquals("创建时间不符合", "2011-06-25 16:06:20.0", contact.getCrtm().toString());
        Assert.assertEquals("创建人不符合", "20295", contact.getCrusr());
    }

    @Test
    public void should_get_null_when_shipmentId_not_exist() {
        String shipmentId = "12dd";
        Contact contact = contactService.findByShipmentId(shipmentId);
        Assert.assertTrue("根据不存在的shipmentId查找客户有问题", contact == null);
    }

    @Test
    public void should_get_contact_when_mailId_exist() {
        String mailId = "1";
        Contact contact = contactService.findByMailId(mailId);
        Assert.assertNotNull(contact);
        Assert.assertEquals("客户编号不符合", "910698284", contact.getContactid());
        Assert.assertEquals("名称不符合", "杨先花", contact.getName());
        Assert.assertEquals("生日不符合", "2011-06-25 00:00:00.0", contact.getBirthday().toString());
        Assert.assertEquals("性别不符合", "2", contact.getSex());
        Assert.assertEquals("客户类型不符合", "C", contact.getContacttype());
        Assert.assertEquals("DataType不符合", "A", contact.getDatatype());
        Assert.assertEquals("客户来源不符合", "WWW", contact.getCustomersource());
        Assert.assertEquals("创建日期不符合", "2011-06-25 16:06:20.0", contact.getCrdt().toString());
        Assert.assertEquals("创建时间不符合", "2011-06-25 16:06:20.0", contact.getCrtm().toString());
        Assert.assertEquals("创建人不符合", "20295", contact.getCrusr());
    }

    @Test
    public void should_get_null_when_mailId_not_exist() {
        String mailId = "12dd";
        Contact contact = contactService.findByMailId(mailId);
        Assert.assertTrue("根据不存在的mailId查找客户有问题", contact == null);
    }

    @Test
    public void should_get_contact_when_phoneAndNameAndAddress_exist() {
        CustomerBaseSearchDto searchDto = new CustomerBaseSearchDto();
        searchDto.setPhone("18917765588");
        searchDto.setName("杨先花");
        searchDto.setProvinceId("15");
        searchDto.setCityId("145");
        searchDto.setCountyId("1232");
        searchDto.setAreaId("46226");
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        Map<String, Object> resultMap = contactService.findByBaseCondition(searchDto, dataGridModel);
        List<CustomerDto> result = (List<CustomerDto>) resultMap.get("rows");
        Assert.assertEquals("实际条数不是1", "1", resultMap.get("total").toString());
        assertResults(result.get(0));
    }

    @Test
    public void should_get_contact_when_nameAndAddress_exist() {
        CustomerBaseSearchDto searchDto = new CustomerBaseSearchDto();
        searchDto.setPhone("");
        searchDto.setName("杨先花");
        searchDto.setProvinceId("15");
        searchDto.setCityId("145");
        searchDto.setCountyId("1232");
        searchDto.setAreaId("46226");
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        Map<String, Object> resultMap = contactService.findByBaseCondition(searchDto, dataGridModel);
        List<CustomerDto> result = (List<CustomerDto>) resultMap.get("rows");
        Assert.assertEquals("实际条数不是1", "1", resultMap.get("total").toString());
        assertResults(result.get(0));
    }

    @Test
    public void should_get_contact_when_only_phone_exist() {
        CustomerBaseSearchDto searchDto = new CustomerBaseSearchDto();
        searchDto.setPhone("18917765588");
        searchDto.setName("");
        searchDto.setProvinceId("");
        searchDto.setCityId("");
        searchDto.setCountyId("");
        searchDto.setAreaId("");
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        Map<String, Object> resultMap = contactService.findByBaseCondition(searchDto, dataGridModel);
        List<CustomerDto> result = (List<CustomerDto>) resultMap.get("rows");
        Assert.assertEquals("实际条数不是1", "1", resultMap.get("total").toString());
        assertResults(result.get(0));
    }

    @Test
    public void should_get_contact_when_only_phoneAndName_exist() {
        CustomerBaseSearchDto searchDto = new CustomerBaseSearchDto();
        searchDto.setPhone("18917765588");
        searchDto.setName("杨先花");
        searchDto.setProvinceId("");
        searchDto.setCityId("");
        searchDto.setCountyId("");
        searchDto.setAreaId("");
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        Map<String, Object> resultMap = contactService.findByBaseCondition(searchDto, dataGridModel);
        List<CustomerDto> result = (List<CustomerDto>) resultMap.get("rows");
        Assert.assertEquals("实际条数不是1", "1", resultMap.get("total").toString());
        assertResults(result.get(0));
    }

    private void assertResults(CustomerDto customerDto) {
        Assert.assertNotNull(customerDto);
        Assert.assertEquals("客户编号不符合", "910698284", customerDto.getContactId());
        Assert.assertEquals("名称不符合", "杨先花", customerDto.getName());
        Assert.assertEquals("生日不符合", "2011-06-25 00:00:00.0", customerDto.getBirthday().toString());
        Assert.assertEquals("性别不符合", "2", customerDto.getSex());
        Assert.assertEquals("客户类型不符合", "C", customerDto.getContactType());
        Assert.assertEquals("DataType不符合", "A", customerDto.getDataType());
        Assert.assertEquals("客户来源不符合", "WWW", customerDto.getCustomerSource());
        Assert.assertEquals("创建日期不符合", "2011-06-25 16:06:20.0", customerDto.getCrdt().toString());
        Assert.assertEquals("创建时间不符合", "2011-06-25 16:06:20.0", customerDto.getCrtm().toString());
        Assert.assertEquals("创建人不符合", "20295", customerDto.getCrusr());
    }

    @Test
    public void should_get_empty_result_when_address_not_exist() {
        CustomerBaseSearchDto searchDto = new CustomerBaseSearchDto();
        searchDto.setPhone("18917765588");
        searchDto.setName("杨先花");
        searchDto.setProvinceId("15");
        searchDto.setCityId("145");
        searchDto.setCountyId("1232");
        searchDto.setAreaId("3333");
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        Map<String, Object> resultMap = contactService.findByBaseCondition(searchDto, dataGridModel);
        List<CustomerDto> result = (List<CustomerDto>) resultMap.get("rows");
        Assert.assertTrue(result.size() == 0);
    }

    @Test
    public void testFindLevelByContactId() {
        String contactId = "14309038";
        String level = contactService.findLevelByContactId(contactId);
        Assert.assertTrue("查找有等级的会员的等级错误", "普通会员".equals(level));
    }

    @Test
    public void testFindLevelByContactId2() {
        String contactId = "143090382";
        String level = contactService.findLevelByContactId(contactId);
        Assert.assertTrue("查找没有等级的会员的等级错误", level == null);
    }
    
    @Test
    public void testPojoUtils() throws Exception{
    	CustomerDto contact1 = new CustomerDto();
    	CustomerDto contact2 = new CustomerDto();
    	String contactId = "1233442";
    	contact1.setContactId(contactId);
    	contact1.setName("张明");
    	
    	contact2.setContactId(contactId);
    	contact2.setName("张小明");
    	
    	ContactChange contactChange = new ContactChange();
    	
    	try {
			List<PropertyDescriptor> propDescList = PojoUtils.compare(contact1, contact2);
			PojoUtils.invoke(contact2 ,contactChange, propDescList);
			
			System.out.println("ContactChange.name: "+ contactChange.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
    
    @Test
    @Rollback(false)
    public void applyUpdateRequest() throws Exception{
    	String contactId = "910392274";
    	String userId = "27427";
    	String remark = "创建流程";
    	String deptId= "22";
    	
    	UserBpm userBpm = new UserBpm();
    	Integer type = AuditTaskType.CONTACTCHANGE.getIndex();
    	userBpm.setBusiType(type.toString());
    	userBpm.setContactID(contactId);
    	userBpm.setCreateDate(new Date());
    	userBpm.setCreateUser(userId);
    	userBpm.setDepartment(deptId);
    	
    	String batchId = changeRequestService.createChangeRequest(userBpm);

    	Contact contact = contactService.get(contactId);
    	ContactDto contactDto = (ContactDto) PojoUtils.convertPojo2Dto(contact, ContactDto.class);
    	//ContactDto contactDto = new ContactDto();
    	//contactDto.setContactid(contactId);
    	contactDto.setName("张XXX");
    	contactService.applyUpdateRequest(contactDto, remark, userId, deptId, batchId);
    }
    
    /**
     * <p>结束修改请求，合并数据</p>
     * @throws MarketingException
     */
    @Test
    public void finishUpdateRequest() throws MarketingException{
    	String taskId = "";
    	String contactId = "910392274";
    	String userId = "17353";
    	String remark = "创建流程";
    	contactService.finishUpdateRequest(contactId, remark, userId, userId, taskId);
    }

    @Test
    public void testQueryAuditPageList() throws Exception {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        ObCustomerDto obCustomerDto = new ObCustomerDto();
        contactService.queryAuditPageList(dataGridModel, obCustomerDto);
    }

    @Test
    public void testQueryAuditPageList2() throws Exception {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(1);
        dataGridModel.setRows(10);
        ObCustomerDto obCustomerDto = new ObCustomerDto();
        obCustomerDto.setBeginDate(new Date());
        contactService.queryAuditPageList(dataGridModel, obCustomerDto);
    }
}
