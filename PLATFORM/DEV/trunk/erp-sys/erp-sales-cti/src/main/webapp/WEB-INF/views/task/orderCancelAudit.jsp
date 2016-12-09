<%@page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="popWin_wrap">
	<input id="input_busiType" type="hidden" value="${auditTaskType}"/>
	<input id="input_id" type="hidden" value="${id}"/>
	<input id="input_crUser" type="hidden" value="${crUser}"/>
	<input id="input_isManager" type="hidden" value="${isManager}"/>
	
	
<c:forEach items="${userBpmTasks }" var="item" varStatus="indexs">
	<input id="input_instID" type="hidden" value="${item.bpmInstID}"/>

	
 	<table width="100%">
		<tbody>
			<tr>
				<td valign="top">取消原因</td>
				<td><textarea class="ml10 readonly"  style="width:300px" readonly="true" id="textarea_orderCancelRemark">${item.remark}</textarea></td>
			</tr>
			<tr>
				<td valign="top">批注</td>
				<td><textarea rows="3" class="ml10" style="width:300px;" id="textarea_orderCancelComment">${item.approverRemark }</textarea></td>

			</tr>
			<tr>
				<td colspan="2" align="center">

				</td>
			</tr>
		</tbody>
	</table> 
</c:forEach>
</div>
<c:choose>
    <c:when test="${isUnassigned }">
        <p class="winBtnsBar textC">
            <a class="window_btn"  id="a_approve" href="javascript:void(0)" onclick='approve("input_instID", "textarea_orderCancelComment")'>批准</a>
            <a class="window_btn ml10" id="a_reject"  href="javascript:void(0)" onclick="reject('input_instID', 'textarea_orderCancelComment')">驳回</a></p>
    </c:when>
    <c:otherwise>
        <p class="winBtnsBar textC">
        <a class="window_btn" href="javascript:void(0)" id="close"
           onclick=''>关闭</a>
        </p>
    </c:otherwise>
</c:choose>
