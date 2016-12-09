<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<link href="/static/style/integral.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/js/integral/integral.js"></script>

</head>
<body>
<div style="padding:15px">
<div class="baseInfo" style="margin:0">
                  <table class="" border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td><STRONG>客户姓名 : ${name}</lable></td>
                        <td>
                        
                        <input name="name" id="name" type="hidden" value="${name}" maxlength="255" />
                        </td>
                        
                        <td><STRONG>客户编号:${customerId}<STRONG></td>
                        <td><input name="customerId" id="customerId" type="hidden" value="${customerId}" maxlength="255" /></td>
                        <!--  
                       <td> <div class="submitBtns" style=" text-align:right"><a href="javascript:void(0)" onclick="javascript:findIntegral();">查找</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="javascript:cancleIntegral();">清空</a></div></td>
                       -->
                      </tr>           
                </table>
                
               
             </div>
             </div>
<div id="integralTabs" class="easyui-tabs" style=" height:auto">
           		<div title="积分获得详情">
                	<p class="mt10 ml10 fs16" style="color:#1797C0">当前可用积分:<span style="color:red;font-size:22px" id="availableIntegral"></span></p>
                      <div class="receiptInfo tabs-form">
                           <table id="getIntegralTable">  
                            </table>  
                      </div>
                      
                       
                      
                	
                </div>
                <div title="积分使用详情">
                		<p class="mt10 ml10 fs16" style="color:#1797C0">已扣减积分:<span style="color:red;font-size:22px" id="subtractionIntegral"></span></p>
                      <div class="receiptInfo tabs-form">
                           <table   id="useIntegralTable">  
                            </table>  
                      </div>
                </div>
               
              </div>
</html>