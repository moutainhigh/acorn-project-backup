<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript" src="/static/scripts/pretread/findResult.js"></script>	
<script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
<!--  
<script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.parser.js"> </script>
-->
	 <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
     <div id="v_result" style="display:none" >${orderfeedback_json}</div>
     <div id="v_laststatus" style="display:none" >${frstate_json}</div>
     <div id="v_buytype" style="display:none">${buytype_json}</div>
     <div id="v_paytype" style="display:none">${paytype_json}</div>
     <div id="v_ordertype" style="display:none" >${ordertype_json}</div>
     <div id="v_cardtype" style="display:none" >${cardtype_json}</div>
     <div id="v_saletype" style="display:none" >${saletype_json}</div>
     <div id="v_company" style="display:none" >${company_json}</div>
     <div id="v_prodstate" style="display:none" >${prodstate_json}</div>    
    
	<div id="condition">
	<div>
			<label  ><input for="state" type="radio" name="state" value="0" checked="checked"/>订购日期</label>
			<label ><input for="state" type="radio" name="state" value="1" />交寄日期</label>
		    <label ><input for="state" type="radio" name="state" value="2" />反馈日期</label>
		    <label for="state"><input type="radio" name="state" value="3" />出库日期</label>
		
		&nbsp;&nbsp;
		<label for="beginDate">从:</label>
		<input id="beginDate"  class="easyui-datetimebox"  style="width:180px" /> <label for="endDate">至:</label>
		<input id="endDate" type="text" class="easyui-datetimebox" style="width:180px" />
		<!--  
	    &nbsp;&nbsp;
	    <input type="checkbox" id="autoSort" name="autoSort" >
	    <label for="autoSort">自动分检(邮购)</label>
	    </input>
	    &nbsp;&nbsp;
	    <input type="checkbox" id="isChangeFindbackDate" name="isChangeFindbackDate" >
	    <label for="isChangeFindbackDate">不改变反馈日期</label>
	    </input>
	    -->
		</div>
		<div>
			<label for="paytype">付款方式:</label> 
			<select name="paytype" id="paytype" >
				<option value="">===请选择===</option>
			    <c:forEach var="n" items="${paytype_req}" >
			    <option value="${n.id.id}">${n.dsc} </option>
			    </c:forEach>
		    </select>
		     &nbsp;&nbsp;
		    <label for="ordertype">订单类型:</label> 
			<select name="ordertype" id="ordertype" style="width:150px;" >
				<option value="" >===请选择===</option>  
			    <c:forEach var="n" items="${ordertype_req}" >
			    <option value="${n.id.id}">${n.dsc} </option>
			    </c:forEach>
		    </select>		    		
    &nbsp;&nbsp;
			<label for="companyid">送货公司:</label> 
			<select name="companyid" id="companyid" style="width:150px;" >
				<option value="">===请选择===</option>  
 				<c:forEach var="n" items="${company_req}">
			    <option value="${n.companyid}">${n.name}</option>
			    </c:forEach>
		    </select>
    &nbsp;&nbsp;
		    <label for="mailtype">订购方式:</label> 
			<select name="mailtype" id="mailtype" style="width:150px;" >
				<option value="">===请选择===</option>  
			    <c:forEach var="n" items="${buytype_req}" >
			    <option value="${n.id.id}">${n.dsc} </option>
			    </c:forEach>
		    </select>
		</div>
		<div>
		  <label for="result">反馈结果:</label> 
			<select name="result" id="result" >
				<option value="">===请选择===</option>  
				<!--  
				<c:forEach var="n" items="${orderfeedback_req}" >
			    <option value="${n.id.id}">${n.dsc} </option>
			    </c:forEach>
			    -->
			    <option value="2">完成</option>
			    <option value="3">拒收</option>
		    </select>
		
		   
		       &nbsp;&nbsp;
		          <label for="cityid">城&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市:</label> 
			<select name="cityid" id="cityid" style="width:150px;" >
			  	<option value="">===请选择===</option>
			    <c:forEach var="n" items="${city_req}" >
			    <option value="${n.cityid}">${n.name} </option>
			    </c:forEach>
		    </select>
		    <!--  
		  &nbsp;&nbsp;
		    <label for="paytype">规&nbsp;&nbsp;&nbsp;格:</label> 
			<select name="paytype" id="paytype" > 
			<option value="">==请选择==</option> 
				<option value="">现金</option>
				<option value="99">其他</option>
		    </select>
		    -->
		      &nbsp;&nbsp;
		   <label for="cardtype">信&nbsp;&nbsp;用&nbsp;&nbsp;卡:</label> 
			<select name="cardtype" id="cardtype" style="width:150px;" >
			</select>
		     &nbsp;&nbsp;
		      <label for="status">订单状态:</label> 
			<select name="status" id="status"  style="width:150px;">
				<option value="">===请选择===</option>  
				<c:forEach var="n" items="${frstate_req}" >
			    <option value="${n.id.id}">${n.dsc} </option>
			    </c:forEach>
		    </select>
		 
		</div>
		
		<div>
		<!--  
			 <label for="paytype">销售方式:</label> 
			<select name="paytype" id="paytype" >
			<option value="">==请选择==</option>    
					<c:forEach var="n" items="${saletype_req}" >
			    <option value="${n.id.id}">${n.dsc} </option>
			    </c:forEach>
		    </select>
		      &nbsp;&nbsp;
		      -->
			<label for="childid">订单编号:</label> <input id="orderid" type="text" name="orderid" size="25" />
		   	   &nbsp;&nbsp;
			<label for="mailid">邮件编号:</label> <input id="mailid" type="text" name="mailid" size="26" />
		    <!--  
		     &nbsp;&nbsp;
		    <label for="paytype">包含产品:</label> 
			<select name="paytype" id="paytype" >
			<option value="">===请选择===</option>    
				<option value="">现金</option>
				<option value="99">其他</option>
		    </select>
		    -->
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	 <input type="button" name="queryData" class="Btn" value='查找' id="queryData" onclick="queryData()" />
		</div>
	</div>
	
	<div class="container" >
		<table id="dataTable" cellspacing="0" cellpadding="0" data-options="" >
		</table>
	</div>
  	<div class="container">
		<table id="dataTable2" cellspacing="0" cellpadding="0">
		</table>
	</div>
</body>
</html>
