package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.service.Refundsms;
import com.chinadrtv.erp.service.core.dto.RefundsmsDto;
import com.chinadrtv.erp.service.core.service.RefundsmsService;
import com.chinadrtv.erp.uc.dto.AddressDto;
import com.chinadrtv.erp.uc.service.AddressService;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import net.sf.json.JSONSerializer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: cuiming
 * Date: 14-4-22
 * Time: 下午3:57
 * To change this template use File | Settings | File Templates.
 */
@Controller("/refundsms")
public class RefundsmsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger( RefundsmsController.class);
     @Autowired
    private RefundsmsService refundsmsService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private  PhoneService phoneService;


    @Autowired
    private UserService userService;

    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

    /***
     *   保存退款信息
     * @param refundsms
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/refundsms/saves",method = RequestMethod.POST)
    public Map<String,Object> saves (Refundsms refundsms) {
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if (StringUtils.isBlank(refundsms.getId())) {
                  refundsms.setCrusr(SecurityHelper.getLoginUser().getUserId());
            }else {
                refundsms.setMdusr(SecurityHelper.getLoginUser().getUserId());
            }
            refundsmsService.saves(refundsms) ;
            map.put("succ","succ");
            map.put("id",refundsms.getId());
        }catch (Exception exp)
        {
            logger.error("save refund error:",exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }
        return map;
    }

    /**
     * 打开新增窗口
     */
    @RequestMapping(value = "/refundsms/openwindow",method = RequestMethod.GET)
    public ModelAndView openWinCouponView (String contactid,String orderid,String types) {
        ModelAndView modelAndView = new ModelAndView("/event/openwindow");
        List <Refundsms> list  = refundsmsService.getRefundsmsByOrderid(orderid) ;

        //获取地址列表
        List<AddressDto> addressDtoList= getAddress(contactid);


        Refundsms refundsms;
        if (list!=null&&!list.isEmpty()) {
            refundsms = list.get(0);
            /*String mobile =  refundsms.getPhone();
            if(!StringUtil.isNullOrBank(mobile)) {
                mobile = mobile.substring(0,7)+"****";
            }
            refundsms.setMobile(mobile);*/
            if(StringUtils.isBlank(refundsms.getRefundtype()))
            {
                refundsms.setRefundtype("2");
            }
        }else {
            refundsms=new Refundsms();
            refundsms.setOrderid(orderid);
            refundsms.setContactid(contactid);
            Contact contact = contactService.findById(contactid);
            refundsms.setHoldername(contact.getName());
        }


        //获取电话列表
        List<Phone> phoneList=this.getmobile(contactid,refundsms.getPhone());

        //设置本身的电话默认
        if(StringUtils.isNotBlank(refundsms.getPhone()))
        {
            /*boolean bFind=false;
            for(Phone phone:phoneList)
            {
                if(StringUtils.isNotBlank(phone.getPhn2())&&phone.getPhn2().equals(refundsms.getPhone()))
                {
                    bFind=true;
                    break;
                }
            }
            if(bFind==false)
            {
                Phone phone=new Phone();
                phone.setPhn2(refundsms.getPhone());
                phone.setPhone_mask(refundsms.getMobile());

                phoneList.add(phone);
            }*/
        }
        else
        {
            //先找主手机，然后找备份手机
            for(Phone phone:phoneList)
            {
                if("Y".equals(phone.getPrmphn()))
                {
                    refundsms.setPhone(phone.getPhn2());
                    break;
                }
            }
            if(StringUtils.isBlank(refundsms.getPhone()))
            {
                for(Phone phone:phoneList)
                {
                    if("B".equals(phone.getPrmphn()))
                    {
                        refundsms.setPhone(phone.getPhn2());
                        break;
                    }
                }
            }
            if(StringUtils.isBlank(refundsms.getPhone()))
            {
                for(Phone phone:phoneList)
                {
                    refundsms.setPhone(phone.getPhn2());
                    break;
                }
            }
        }

        //设置本身的地址默认
        if(StringUtils.isNotBlank(refundsms.getAddress()))
        {
            boolean bFind=false;
            for(AddressDto addressDto:addressDtoList)
            {
                if(refundsms.getAddress().equals(addressDto.getAddress()))
                {
                    bFind=true;
                    break;
                }
            }
            if(bFind==false)
            {
                AddressDto addressDto=new AddressDto();
                addressDto.setAddress(refundsms.getAddress());

                addressDtoList.add(addressDto);
            }
        }
        else
        {
            //取客户的默认地址
            for(AddressDto addressDto:addressDtoList)
            {
                if("-1".equals(addressDto.getIsdefault()))
                {
                    refundsms.setAddress(addressDto.getAddress());
                    refundsms.setZipcode(addressDto.getZip());
                    break;
                }
            }
        }



        modelAndView.addObject("addressList", JSONSerializer.toJSON(addressDtoList));
        modelAndView.addObject("phoneList",JSONSerializer.toJSON(phoneList));
        modelAndView.addObject("types",types);

        if(StringUtils.isNotBlank(refundsms.getBankcardnum()))
        {
            refundsms.setBankcardnumMap(refundsms.getBankcardnum());
            refundsms=refundsmsService.filterRefundsms(refundsms);
            /*if(refundsms.getBankcardnum().length()>12)
            {
                String str1=refundsms.getBankcardnum().substring(0,4);
                String str2=refundsms.getBankcardnum().substring(12);

                refundsms.setBankcardnumMap(str1+"********"+str2);
            }
            else
                refundsms.setBankcardnumMap(refundsms.getBankcardnum())*/;
        }

        modelAndView.addObject("refundsms",refundsms);
        return  modelAndView;
    }
    @ResponseBody
    @RequestMapping(value = "/refundsms/getAddress",method = RequestMethod.POST)
    public  List<AddressDto> getAddress (String contactid ) {
        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setRows(999);
        Map<String,Object> result  =  addressService.queryAddressPageList(dataGridModel,contactid);
        List<AddressDto> list =  (List<AddressDto>)result.get("rows");
        if (list!=null&&!list.isEmpty()) {
            for (int i=0;i<list.size();i++) {
               list.get(i).setAddress( list.get(i).getReceiveAddress()+list.get(i).getAddress());
            }
        }

        return list;
    }

    /**
     *发送短信
     * @param refundsms
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/refundsms/sendSms",method = RequestMethod.POST)
    public Map<String,Object> sendSms (Refundsms refundsms) {
        refundsms.setMdusr(SecurityHelper.getLoginUser().getUserId());
        refundsms.setMddt(new Date());
        refundsms.setStatus(2l);
        refundsms.setCrusr(refundsms.getMdusr());

        Map<String,Object> map=new HashMap<String, Object>();
        try
        {
            refundsmsService.sendSms(refundsms);
            map.put("succ","succ");
        }
        catch (Exception exp)
        {
            logger.error("send refund sms error:",exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }
        return map;
    }

    public List<Phone> getmobile (String contactid,String phoneNum) {
        List<Phone> phoneList=null;
        if(StringUtils.isNotBlank(phoneNum))
        {
            phoneList=new ArrayList<Phone>();
            Phone phone=new Phone();
            phone.setPhonetypid("4");
            phone.setContactid(contactid);
            phone.setPhn2(phoneNum);
            phone.setPhone_mask(phoneNum);
            phoneList.add(phone);
        }
        List <Phone> list =  phoneService.getRefundPhonesByContactId(contactid,phoneList);
        list =  refundsmsService.iterPhoneList(list);
        List<Phone> retList=new ArrayList<Phone>();
        for(Phone phone:list)
        {
            boolean bFind=false;
            for(Phone contactPhone:retList)
            {
                if(contactPhone.getPhoneMask().equals(phone.getPhoneMask()))
                {
                    bFind=true;
                    break;
                }
            }
            if(bFind==false)
            {
                if(phone.getPhoneMask()==null)
                {
                    phone.setPhone_mask(phone.getPhn2());
                }
                retList.add(phone);
            }
        }
        return  retList;
    }

    /**
     *  分页查询
     * @param dataGridModel
     * @param refundsmsDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/refundsms/queryList",method = RequestMethod.POST)
    public   Map<String,Object> queryList (DataGridModel dataGridModel,RefundsmsDto refundsmsDto) {
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormatFull=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(refundsmsDto.getStartDate()!=null)
            {
                String str=simpleDateFormat.format(refundsmsDto.getStartDate());
                refundsmsDto.setStartDate(simpleDateFormatFull.parse(str+" 00:00:00"));
            }
            if(refundsmsDto.getEndDate()!=null)
            {
                String str=simpleDateFormat.format(refundsmsDto.getEndDate());
                refundsmsDto.setEndDate(simpleDateFormatFull.parse(str+" 23:59:59"));
            }
            result.put("rows", refundsmsService.query(refundsmsDto,dataGridModel));
            result.put("total", refundsmsService.queryCaseRefundsmsCount(refundsmsDto));
        }catch (Exception exp)
        {
            logger.error("query refunds error:",exp);
            result.put("rows", new ArrayList<Refundsms>());
            result.put("total", 0);
        }
        return result;
    }

    /**
     * 取消退款
     * @param refundsms
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/refundsms/cancel",method = RequestMethod.POST)
    public   Map<String,Object> cancel (Refundsms refundsms) {
        Map<String,Object> map=new HashMap<String, Object>();
        try
        {
            refundsms.setMddt(new Date());
            refundsms.setMdusr(SecurityHelper.getLoginUser().getUserId());
            refundsmsService.cancelRefund(refundsms);
            map.put("succ","succ");
        }catch (Exception exp)
        {
            logger.error("cancel refund error:",exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }
        return map;
    }


}
