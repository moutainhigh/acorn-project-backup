<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/orderSettlement/orderSettlement.js"></script>
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
    </style>
</head>
<body>


<div id="condition">
    <div>
			<label for="ti" class="labelTitle">外部单号:</label> 
			<input size="8" name="tradeId" type="text" class="inputStyle" id="ti"> 
			 <label for="si" class="labelTitle ml10" style="width:70px;">Agent单号:</label>
		<input size="20" name="shipmentId" type="text" class="inputStyle" id="si">
			</div>
				<div>
			
			<label for="sd" class="labelTitle">开始时间:</label>
			<input size="8" class="easyui-datebox datebox-f combo-f" id="sd"  name="startDate" style="display: none;"/>
			<label for="ed" class="ml10">结束时间:</label>
			<input type="text" size="8" name="endDate" class="easyui-datebox datebox-f combo-f" id="ed" style="display: none;"/>
			<button id="btnSearch" type="button" value='查找' class="Btn ml10">查找</button>
				</div>
      
    
</div>
<div region="center">
<div id="toolbar">
    <!--
    <a href="#" id="lbNew" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="float:left;">新增</a>
    <a href="#" id="lbEdit" class="easyui-linkbutton" iconCls="icon-edit" plain="true" style="float:left;">编辑</a>
    <a href="#" id="lbShow" class="easyui-linkbutton" iconCls="icon-search" plain="true" style="float:left;">查看</a>
    -->
    <a href="#" id="lbDelete" class="easyui-linkbutton" iconCls="icon-remove" plain="true" style="float:left;">删除</a>
    <a href="#" id="lbApprove" class="easyui-linkbutton" iconCls="icon-edit" plain="true" style="float:left;">确认</a>
</div>
<div class="container" >
    <table id="dg" title="结算单" 
            url="${url}"
            saveUrl="${saveUrl}"
            updateUrl="${updateUrl}"
            destroyUrl="${destroyUrl}"
            approveUrl="${approveUrl}"
            cellspacing="0" cellpadding="0" width="100%" data-options="" ></table>
</div>
</div>


</body>
</html>
