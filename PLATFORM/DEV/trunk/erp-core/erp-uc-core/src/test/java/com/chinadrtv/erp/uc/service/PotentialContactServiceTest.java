package com.chinadrtv.erp.uc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.chinadrtv.erp.uc.dto.ContactAddressDto;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.PotentialContactPhone;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.test.AppTest;
/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:潜客测试</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhoutaotao
 * @version 1.0
 * @since 2013-5-8 下午6:11:29 
 *
 */
public class PotentialContactServiceTest extends AppTest {
	@Autowired
	private PotentialContactService potentialContactService;
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_SavePotentialContact(){
		PotentialContact potentialContact = new PotentialContact();
		potentialContact.setName("李四");
		potentialContact.setGender("男");
		potentialContact.setContacttype("01");
		potentialContact.setCrdt(new Date());
		potentialContact.setCrusr("李四");
		potentialContact.setBirthday(new Date(87, 0, 31));
		potentialContact.setCustomersource("潜客来源");
		potentialContact.setComments("备注");
		potentialContact.setCall_Length(123456L);
		//potentialContact.setContactId(123456L);
		//potentialContact.setProductId(123456L);
		Long potentialContactId = potentialContactService.savePotentialContact(potentialContact);
		
		Assert.assertNotNull(potentialContactId);
	}

    @Test
    public void should_be_success_when_idAndName_exists() {
        List<CustomerDto> result = potentialContactService.findByBaseCondition("2050", "李四", "021-12345678-2233", "021", "12345678", "2233", 0, 10);
        Assert.assertTrue("结果集为空", result.size() > 0);
        CustomerDto customerDto = result.get(0);
        Assert.assertEquals("性别不符合", "男", customerDto.getSex());
        Assert.assertEquals("完整电话不匹配", "021-12345678-2233", customerDto.getPhoneNum());
    }
    
    
    @Test
    //@Rollback(false)
    public void updatePotentialContact(){
    	/*PotentialContact potentialContact = new PotentialContact();
		potentialContact.setName("李小明");
		potentialContact.setGender("男");
		potentialContact.setContacttype("01");
		potentialContact.setCrdt(new Date());
		potentialContact.setCrusr("21323");
		potentialContact.setBirthday(new Date());
		potentialContact.setCustomersource("WWW");
		potentialContact.setComments("大客户");
		potentialContact.setCall_Length(123456L);
		PotentialContactPhone pcp = new PotentialContactPhone();
		pcp.setPhone1("021");
		pcp.setPhone2("89283982");
		pcp.setPhone3("098");
		pcp.setPhoneNum("021-89283982-098");
		pcp.setPhoneTypeId("01");
		pcp.setPotentialContact(potentialContact);
		Long id = potentialContactService.savePotentialContact(potentialContact);*/
		
		PotentialContact createdContact = potentialContactService.queryById(2050L);
		
		Set<PotentialContactPhone> phoneSet = createdContact.getPotentialContactPhones();
		for(PotentialContactPhone phone : phoneSet){
			phone.setPhone2("77887788");
			phone.setPhone3("222");
		}
		String commnets = "只允许周末呼叫";
		createdContact.setComments(commnets);
		potentialContactService.updatePotentialContact(createdContact);
		
		Assert.assertEquals(commnets, createdContact.getComments());
    }

    @Test
    public void test_upgradeToContact() {
        Assert.assertTrue("潜客转化为正式客户不成功", potentialContactService.upgradeToContact(2050L, "18", 181, 1496, 42174, "升级测试地址", "10002"));
    }

    @Test
    public void test_upgradeToContact2() {
        List<ContactAddressDto> addresses = new ArrayList<ContactAddressDto>();
        ContactAddressDto dto = new ContactAddressDto();
        dto.setProvince( "18");
        dto.setCityId(181);
        dto.setCountyId(1496);
        dto.setAreaId(42174);
        dto.setAddress("升级测试地址");
        dto.setZip("10002");
        addresses.add(dto);
        Assert.assertTrue("潜客转化为正式客户不成功", potentialContactService.upgradeToContact(2050L, addresses));
    }
}
