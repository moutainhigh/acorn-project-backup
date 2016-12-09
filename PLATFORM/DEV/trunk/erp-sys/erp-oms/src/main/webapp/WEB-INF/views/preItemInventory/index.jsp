<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <script type="text/javascript" src="/static/scripts/json.min.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.edatagrid.js"></script>
    <script type="text/javascript" src="/static/scripts/preItemInventory/preItemInventory.js"></script>
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
			<label class="labelTitle" for="ti">渠道商品号:</label> 
			<input id="ti" class="inputStyle" type="text" name="skuId" size="8"> 
			 <label class="labelTitle ml10" style="width:auto" for="si">商品编号:</label>
		<input id="si" class="inputStyle" type="text" name="outSkuId" size="20">
			</div>
				<div>
			
			<label class="labelTitle" for="sd">开始时间:</label>
			<input style="width:130px;" id="sd" class="easyui-datebox datebox-f combo-f"  name="startDate">
			<label class="ml10" for="ed">结束时间:</label>
			<input type="text" style="width:130px;" id="ed" class="easyui-datebox datebox-f combo-f"  name="endDate">
			<button class="Btn ml10" value="查找" type="button" id="btnSearch">查找</button>
				</div>
      
    
</div>

<div region="center" >
<div id="toolbar">
    <!--
    <a href="#" id="lbNew" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="float:left;">新增</a>
    <a href="#" id="lbEdit" class="easyui-linkbutton" iconCls="icon-edit" plain="true" style="float:left;">编辑</a>
    <a href="#" id="lbDelete" class="easyui-linkbutton" iconCls="icon-remove" plain="true" style="float:left;">删除</a>
    <a href="#" id="lbShow" class="easyui-linkbutton" iconCls="icon-search" plain="true" style="float:left;">查看</a>
    -->
</div>
<div class="container" >
    <table id="dg" title="同步库存" 
            url="${url}"
            saveUrl="${saveUrl}"
            updateUrl="${updateUrl}"
            destroyUrl="${destroyUrl}"
            cellspacing="0" cellpadding="0" data-options="" ></table>
</div>
</div>


</body>
</html>
