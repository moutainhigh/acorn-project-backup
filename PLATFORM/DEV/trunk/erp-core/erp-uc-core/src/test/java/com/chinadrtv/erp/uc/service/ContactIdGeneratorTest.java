package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.uc.dao.ContactDao;
import com.chinadrtv.erp.uc.test.AppTest;
import com.chinadrtv.erp.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

/**
 * 联系人ID自动
 * User: gaodejian
 * Date: 13-5-21
 * Time: 上午10:01
 * To change this template use File | Settings | File Templates.
 */
public class ContactIdGeneratorTest extends AppTest {

    @Autowired
    private ContactDao contactDao;

    @Test
    //@Rollback(false)
    public void testContactIdGenerator() throws Exception
    {
        Contact contact = new Contact();
        contact.setName("齐深");
        contact.setSex("1");
        contact.setContacttype("C");
        contact.setEmail("clonalman@hotmail.com");
        contact.setCrdt(new Date());
        contact.setCrtm(new Date());
        contact.setCrusr("7970");
        contact.setMdusr("7970");
        contact.setBirthday(DateUtil.string2Date("2011-6-25", "yyyy-MM-dd"));
        contactDao.saveOrUpdate(contact);
        Assert.assertNotNull(contact.getContactid());
        Assert.assertTrue(contact.getContactid().startsWith("9"));
    }

}
