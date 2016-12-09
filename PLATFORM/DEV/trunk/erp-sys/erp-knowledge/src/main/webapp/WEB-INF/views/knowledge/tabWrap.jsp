<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
request.setAttribute("rnd", new java.util.Random().nextInt());
%>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/ueditor/ueditor.config.js">
</script>
<script type=text/javascript src="${ctx}/static/js/ajaxfileupload.js"></script>
<script type=text/javascript src="${ctx}/static/ueditor/ueditor.all.js"></script> 
<script type=text/javascript src="${ctx}/static/js/knowledge/knowledgeArticle.js?${rnd}"></script>
<script type=text/javascript src="${ctx}/static/js/common-ui.js"></script>

<div class="easyui-tabs" fit="true"  border="false" id="searchTab">
    <div title="${title}"  href="/knowledge/initw?id=${id}">


    </div>
    </div>