<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
</head>
<body>
<script type=text/javascript src="${ctx}/static/js/search.js"></script>
<link href="${ctx}/static/style/search.css" rel="stylesheet" type="text/css"/>
<%--<div id="" class="easyui-panel" fit="true"  border="false" style="padding:10px">--%>
<div class="easyui-tabs" fit="true"  border="false" id="searchTab">
    <div title="全文搜索" style="padding:10px">
    <p class="pageTitle nui-btn"><span class="title-content">全文搜索</span>
        <span class="nui-btn-text" id="nui-component2-text" onclick="toHome()"><a class="icon-back"></a>&nbsp;返回</span>
    </p>
    <div class="searchWarp InFull">
<select id="searchType" class=" easyui-combobox" style="width:80px;height: 30px;" data-options="editable:false,onShowPanel:function(){
       $(this).combobox('panel').height('auto');
}"  >
    <option value="1">产品</option>
    <option value="2">常见问答</option>
</select>
<input class="easyui-searchbox" data-options="prompt:'关键字/产品名称/产品编码/产品简码',searcher:doSearch"
                               style="width:400px;height:30px;">
    </div>
    <div class="c_block" style="margin-bottom: 0">
        <p id="2" class="title" style="margin-bottom: 0">搜索结果</p>
    </div>
    <div  id="TmpContent" class="searchContent easyui-panel"> 
	</div>  
    <div class="easyui-pagination" style="border:1px solid #ccc;" id="pp" hidden="true"
    </div>
    <%--</div>--%>
    </div>
    </div>
</body>
</html>