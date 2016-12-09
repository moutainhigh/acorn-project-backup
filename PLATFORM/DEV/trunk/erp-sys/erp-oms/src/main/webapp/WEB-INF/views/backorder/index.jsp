<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/backorder/backorder.js"></script>
    <link href="/static/style/oms_layout.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            color:#666;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
            padding:5px 0;
        }
        .err
        {
            color: #FF0000;
            font-weight: 700;
        }
        .time
        {
            width:100px;
            padding: 4px;
            text-align: right;
        }

    </style>
</head>
<body>



<div id="condition">
    <div>
			<label for="oi" class="labelTitle">订单编号:</label> 
			<input id="oi" size="8" name="orderId" type="text" class="inputStyle" > 
			 <label for="pi" class="labelTitle ml10" style="width:auto">产品编号:</label>
		<input id="pi" size="20" type="text" name="prodId" class="inputStyle" >
			</div>
				<div>
			
			<label for="sd" class="labelTitle">开始时间:</label>
			<input  class="easyui-datebox datebox-f combo-f" id="sd" style="width:130px;" name="startDate">
			<label for="ed" class="ml10">结束时间:</label>
			<input  class="easyui-datebox datebox-f combo-f" id="ed" style="width:130px;" name="endDate">
			
			
				</div>
<div><label for="st" class="labelTitle">处理状态:</label>	 <select  class="inputStyle"  name="status" id="st" style="width:130px;">
                        <option value="">全部</option>
                        <option value="0">未处理</option>
                        <option value="1">自动取消</option>
                        <option value="2">坐席处理</option>
                    </select><button id="btnSearch" type="button" value="查找" class="Btn ml10">查找</button></div>
    
</div>

<div region="center" >
<div id="toolbar">
    <a href="#" id="lbDelete" class="easyui-linkbutton" iconCls="icon-remove" plain="true" style="float:left;">删除</a>
    <a href="#" id="lbDefer" class="easyui-linkbutton" iconCls="icon-search" plain="true" style="float:left;">延期</a>
</div>
<div class="container" >
    <table id="dg" title="压单取消" 
            url="${url}"
            saveUrl="${saveUrl}"
            updateUrl="${updateUrl}"
            destroyUrl="${destroyUrl}"
            cellspacing="0" cellpadding="0" data-options=""></table>
</div>
</div>


</body>
</html>
