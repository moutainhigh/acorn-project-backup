<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<div id="footer">
<table>
    <tbody><tr>
        <td class="noborder">
            <div >
                <label>当前登陆用户：</label>&nbsp;<span>${currentUser.name}(${currentUser.userId})&nbsp;&nbsp;所在工作组：${currentUser.workGrp}</span>
                <label>最后更新时间：</label>&nbsp;<span><spring:message code="systemTimes"/></span>
            </div>
        </td>
    </tr></tbody>
</table>
</div>