package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.exception.service.ServiceTransactionException;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.test.AppTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title: ContactTest
 * Description:
 * User: xieguoqiang
 *
 * @version 1.0
 */
public class ContactTest extends AppTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private PotentialContactService potentialContactService;

    @Test
//    @Rollback(false)
    public void testSaveCompleteContact() throws ServiceTransactionException {
        CustomerDto customerDto = new CustomerDto();
        AddressDto address = new AddressDto();
        address.setAddress("上海浦东新区");
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
        List<AddressDto> addressDtoList = new ArrayList<AddressDto>();
        addressDtoList.add(address);

        customerDto.setName("XGQ TEST");
        customerDto.setSex("1");
        customerDto.setBirthday("1987-12-12");
        customerDto.setPhoneNum("12334434");
        customerDto.setContactType("T");

        Phone phone = new Phone();
        phone.setPhonetypid("2");
        phone.setPhn1("021");
        phone.setPhn2("23244232");
        phone.setPrmphn("Y");
        List<Phone> phoneList = new ArrayList<Phone>();
        phoneList.add(phone);
        Map<String, Object> resultMap = contactService.saveCustomer(customerDto, addressDtoList, phoneList);
        Assert.assertTrue("保存失败", resultMap.get("contactId") != null);
        Assert.assertTrue("保存失败", resultMap.get("potentialContactId") == null);
    }

    @Test
//    @Rollback(false)
    public void testSaveCompletePotentialContact() throws ServiceTransactionException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("XGQ TEST");
        customerDto.setSex("1");
        customerDto.setBirthday("1987-12-12");
        customerDto.setPhone1("0721");
        customerDto.setPhone2("85756436");
        customerDto.setContactType("T");

        Map<String, Object> resultMap = contactService.saveCustomer(customerDto, null, null);
        Assert.assertTrue("保存失败", resultMap.get("contactId") == null);
        Assert.assertTrue("保存失败", resultMap.get("potentialContactId") != null);
    }

    @Test
//    @Rollback(false)
    public void testUpdatePotentialContact() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("test2");
        customerDto.setSex("1");
        customerDto.setPotentialContactId("8150");
        contactService.updateCustomer(customerDto);
        PotentialContact potentialContact = potentialContactService.queryById(8150L);
        Assert.assertTrue("更新失败", potentialContact.getName().equals("test2"));
        Assert.assertTrue("更新失败", potentialContact.getGender().equals("1"));
    }
}
