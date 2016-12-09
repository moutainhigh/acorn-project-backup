<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type=text/javascript src="${ctx}/static/js/common-ui.js"></script> 
<script type=text/javascript>

$(document).ready(function(){
	var ids = "${knowledgeTag.id}"
		if (ids==""||ids==null) {
			$("#name").attr("disabled",false);
			$("#edit").hide();
		}else {
			$("#save").hide();
		}
});


function  saves () {
	var name = $("#name").val();
	var ids = "${knowledgeTag.id}";
	$.post("knowledgeTag/saves", {
		"name":name,
		"id":ids			
	},function(data) {
		alerts(data.result);
		$("#main-layout")
	    .layout('panel','center').panel('refresh','/knowledgeTag/knowledgeList');	  
		
	},'json');	
}

function edit () {
	$("#edit").hide();
	$("#save").show();
	$("#name").attr("disabled",false);
	
}

</script>

    <div class="ftitle">标签信息</div>
    <form id="fm" method="post">
        <div class="fitem">
            <label>标签名称:</label>
            <input name="name" id="name" class="easyui-validatebox" required="true" title="" value="${knowledgeTag.name}">
        </div>
        <input id="id" name="id" type="hidden" value="${knowledgeTag.id}"/>
    </form>

<%--<div class="easyui-panel" fit="true" border="false" style="padding:10px" >--%>

        <%--<div class="easyui-layout" fit="true" >--%>
            <%--<div   data-options="region:'north',border:false">--%>
                <%--<form name="myform" method="post" id="myform">--%>
                <%--<div class="pro_info">--%>
                    <%--<p>&nbsp;</p>--%>
                    <%--<table width="98%">--%>
                   		<%--<tr>--%>
                   			<%--<td>                         --%>
                               <%--<span>标签名称：</span><input  id="name" name="name" value="${knowledgeRelationships.name}" type="text" disabled="disabled"/></td>--%>
                            	<%--<input id="id" name="id" type="hidden" value="${knowledgeRelationships.id}"/>--%>
                            <%--</td>--%>
                        <%--</tr>                      --%>
                    <%--</table>--%>
                <%--</div>--%>
    <%--</form>--%>
<%--</div>                    --%>

	<%--</div>--%>
	