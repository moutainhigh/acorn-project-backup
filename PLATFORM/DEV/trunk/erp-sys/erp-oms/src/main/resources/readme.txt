oms 新版本代码修改记录: 
==================================
1.修改bug ,修改没有倒入Iagent的订单提示“重复订单”；
修改人:hlt
修改的 代码: 
  时间 			项目名称                      java代码
2013.5.13 10:50,   erp.oms      erp.oms.controller.PreprocessingController.java
==================================
2.新增前置订单类型: TAOBAOCJ,TAOBAOJ,QQPP
修改人: hlt
修改的代码
时间 			项目名称                      java代码
2013.5.13 17:16   erp.model    erp.constant.TradeSourceConstants.java
2013.5.13 17:15   erp.oms      erp.oms.controller.PreprocessingController.java
2013.5.15 10:33   erp.oms      source.properties
==================================

2. 添加 scm促销借口
  修改人:hlt
添加代码:
时间 			项目名称                      java代码
2013.5.13 17:16    erp.core      util.CollectionUtil.java
2013.5.13 17:16    erp.core      util.StringUtil.java

3. 修改Admin ,为促销添加停止和 开始操作接口
 修改人:hlt
  
修改代码 :
时间 			项目名称                      java代码
2013.5.14 17:16    erp.core      dao.PromotionDao.java
2013.5.14 17:16    erp.core      dao.hibernate.PromotionDaoImpl.java
2013.5.14 17:16    erp.core      service.PromotionAdminService.java
2013.5.14 17:16    erp.admin     *.*

 
=============================================
4.修改Oms 修改订单反馈结果
修改人: htl 
修改代码:
时间 			项目名称                      java代码
2013.5.14 17:16    erp.oms      oms.controller.OrderFreebackController.java
2013.5.14 17:16    erp.oms      oms.service.OrderFeedbackService.java
2013.5.14 17:16    erp.oms      oms.service.impl.OrderFeedbackServiceImpl.java
2013.5.14 17:16    erp.oms      oms.dao.impl.OrderFeedbackServiceImpl.java
2013.5.14 17:16    erp.oms      oms.dao.hibernate.OrderFeedbackServiceImpl.java

2013.5.14 17:16    erp.oms       freebask.js
2013.5.14 17:16    erp.oms      orderFreeBack/index.jsp


5.茶马自动审核添加TC服务支持

修改人: htl 
修改代码:
时间 			项目名称                      java代码
2013.5.14 17:16    erp.oms      oms.quartz.AutoOperator.java
2013.5.14 17:16    erp.oms      oms.quartz.QuartzJob.java




