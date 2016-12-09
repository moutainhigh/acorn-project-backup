package com.chinadrtv.service.iagent;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-18
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public interface MailStatusHisService<MailStatusHis>{
     void saveAll(List<MailStatusHis> mailStatusHisList) throws Exception;
}
