package com.chinadrtv.service.iagent.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.iagent.dao.MailStatusHisDao;
import com.chinadrtv.model.iagent.MailStatusHis;
import com.chinadrtv.service.iagent.MailStatusHisService;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-18
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
@Service("mailStatusHisService")
public class MailStatusHisServiceImpl implements MailStatusHisService<MailStatusHis> {

    private static final Log logger =  LogFactory.getLog(MailStatusHisServiceImpl.class);

    @Autowired
    private MailStatusHisDao mailStatusHisDao;


   /* //状态校验
    private Boolean validStatus(String CurStatus, String AfterStatus) {
        Boolean result = true;

        if (CurStatus.equals("2")) {
            //发货
            result = true;
        } else if (CurStatus.equals("11") || CurStatus.equals("17")) {
            //“总站收货11”、“转站17”
            if (AfterStatus.equals("12") 派送点收件
                    || AfterStatus.equals("13")  派件
                    || AfterStatus.equals("7")   问题单
                    || AfterStatus.equals("18")  二次配送
                    || AfterStatus.equals("19")  三次配送
                    || AfterStatus.equals("5")   客户签收
                    || AfterStatus.equals("14")  部分签收
                    || AfterStatus.equals("6")   拒收未入库
                    || AfterStatus.equals("15")  丢失
                    || AfterStatus.equals("16")  退回
                    ) {
                result = true;
            } else {
                result = false;
            }
        } else if (CurStatus.equals("12")) {
            //派送点收件
            if (AfterStatus.equals("13")  派件
                    || AfterStatus.equals("7")   问题单
                    || AfterStatus.equals("18")  二次配送
                    || AfterStatus.equals("19")  三次配送
                    || AfterStatus.equals("5")   客户签收
                    || AfterStatus.equals("14")  部分签收
                    || AfterStatus.equals("6")   拒收未入库
                    || AfterStatus.equals("15")  丢失
                    || AfterStatus.equals("16")  退回
                    || AfterStatus.equals("17")  转站
                    ) {
                result = true;
            } else {
                result = false;
            }
        } else if (CurStatus.equals("13")) {
            //派件
            if (AfterStatus.equals("7")   问题单
                    || AfterStatus.equals("18")  二次配送
                    || AfterStatus.equals("19")  三次配送
                    || AfterStatus.equals("5")   客户签收
                    || AfterStatus.equals("14")  部分签收
                    || AfterStatus.equals("6")   拒收未入库
                    || AfterStatus.equals("15")  丢失
                    || AfterStatus.equals("16")  退回
                    ) {
                result = true;
            } else {
                result = false;
            }
        } else if (CurStatus.equals("18") || CurStatus.equals("7")) {
            //“二次派送18”、“问题单7”
            if (AfterStatus.equals("7")   问题单
                    || AfterStatus.equals("19")  三次配送
                    || AfterStatus.equals("5")   客户签收
                    || AfterStatus.equals("14")  部分签收
                    || AfterStatus.equals("6")   拒收未入库
                    || AfterStatus.equals("15")  丢失
                    || AfterStatus.equals("16")  退回
                    ) {
                result = true;
            } else {
                result = false;
            }
        } else if (CurStatus.equals("19")) {
            //“三次派送19”
            if (AfterStatus.equals("7")   问题单
                    || AfterStatus.equals("5")   客户签收
                    || AfterStatus.equals("14")  部分签收
                    || AfterStatus.equals("6")   拒收未入库
                    || AfterStatus.equals("15")  丢失
                    || AfterStatus.equals("16")  退回
                    ) {
                result = true;
            } else {
                result = false;
            }
        } else if (CurStatus.equals("14") || CurStatus.equals("6")) {
            //“部分签收14”，“拒收未入库6”
            if (AfterStatus.equals("16")  退回) {
                result = true;
            } else {
                result = false;
            }
        } else if (CurStatus.equals("5")) {
            //“签收” 则不能修改
            result = false;
        }
        return result;
    }
*/
    
   /* private static Pattern pattern = Pattern.compile("[0-9]*");

    public static boolean isNumeric(String str) {
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }*/

    /**
     * 
     * <p>Title: saveAll</p>
     * <p>Description: 按emsDateTimeStamp, dateTimeStamp, operadate 排除是否有重复记录，如果没有重复则插入</p>
     * @param mailStatusHisList
     * @see com.chinadrtv.service.iagent.MailStatusHisService#saveAll(java.util.List)
     */
    public void saveAll(List<MailStatusHis> mailStatusHisList) throws Exception{
        formatMailStatusHis(mailStatusHisList);
    	List<MailStatusHis> insertList = new ArrayList<MailStatusHis>();
    	for(MailStatusHis msh : mailStatusHisList){
    		Boolean exists = false;
			try {
				exists = mailStatusHisDao.queryExceptDate(msh);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		//如果表中没有该符合条件的记录
    		if(null == exists || !exists){
    			msh.setIsProcessed("N");
    			if(null != msh.getDateTimeStamp()){
    				msh.setDateTimeStamp(new Date());
    			}
    			//如果传入的数据不重复
    			boolean contains = this.contains(insertList, msh);
    			
    			if(!contains){
    				insertList.add(msh);	
    			}else{
    				logger.warn("排除传入列表已存在业务相同的数据：" + msh.toString());
    			}
    		}else{
    			logger.warn("exclude repeat mailid:"+msh.getMailid());
    		}
    	}
    	if(insertList.size() > 0){
    		try {
				mailStatusHisDao.insertBatch(insertList);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("批量插入[iagent.mail_status_his]表失败", e);
			}	
    	}
    }

    private void formatMailStatusHis(List<MailStatusHis> mailStatusHisList)
    {
        if(mailStatusHisList!=null)
        {
            for(MailStatusHis mailStatusHis:mailStatusHisList)
            {
                if(!StringUtils.isEmpty(mailStatusHis.getMailid()))
                {
                    mailStatusHis.setMailid(mailStatusHis.getMailid().toUpperCase());
                }
                if(!StringUtils.hasText(mailStatusHis.getStatusCode()))
                {
                    mailStatusHis.setStatusCode(null);
                }
                if(!StringUtils.hasText(mailStatusHis.getReasonCode()))
                {
                    mailStatusHis.setReasonCode(null);
                }
                if(!StringUtils.hasText(mailStatusHis.getSignResult()))
                {
                    mailStatusHis.setSignResult(null);
                }
                if(!StringUtils.hasText(mailStatusHis.getOperatype()))
                {
                    mailStatusHis.setOperatype(null);
                }
                if(!StringUtils.hasText(mailStatusHis.getStation()))
                {
                    mailStatusHis.setStation(null);
                }
                if(!StringUtils.hasText(mailStatusHis.getOptor()))
                {
                    mailStatusHis.setOptor(null);
                }
                if(!StringUtils.hasText(mailStatusHis.getRefusereason()))
                {
                    mailStatusHis.setRefusereason(null);
                }
                if(!StringUtils.hasText(mailStatusHis.getProblemreason()))
                {
                    mailStatusHis.setProblemreason(null);
                }
                if(!StringUtils.hasText(mailStatusHis.getRemarks()))
                {
                    mailStatusHis.setRemarks(null);
                }
            }
        }
    }



	/**
	 * <p>按emsDateTimeStamp, dateTimeStamp, operadate 排除是否已经存在列表中</p>
	 * @param insertList
	 * @param msh
	 * @return
	 */
	private boolean contains(List<MailStatusHis> insertList, MailStatusHis msh) {
		for(MailStatusHis model : insertList){
			int modelHash = model.bizHashCode();
			int mshHash = msh.bizHashCode();
			if(modelHash == mshHash){
				return true;
			}
		}
		return false;
	}

}