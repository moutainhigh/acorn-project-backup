<%@page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


	
<div class="popWin_wrap">

	<input id=input_problematic_orderId type="hidden" value="${orderId}"/>
	
 	<table width="100%">
		<tbody>
			<tr>
				<td  valign="top">问题类型</td>
				<td  valign="top"><select id="orderProblemTypes" class="mb10 ml10" name="orderProblemTypes" >
									<option value="">--请选择--</option>
									<c:forEach items="${problemTypes}" var="item"
										varStatus="indexs">
										<option value="${item.key }">${item.value }</option>
									</c:forEach>
							</select></td>
			</tr>
			<tr>
				<td valign="top">问题描述</td>
				<td  valign="top"><textarea id="textarea_orderProblemDsc" class="ml10" style="width:300px" rows="3"></textarea></td>

			</tr>
			<tr>
				<td colspan="2" align="center">

				</td>
			</tr>
		</tbody>
	</table>

</div>
<p class="winBtnsBar textC">
    <a class="window_btn" href="javascript:void(0)" id="a_approve"
             onclick='markProblematicOrder("input_problematic_orderId","orderProblemTypes","textarea_orderProblemDsc")'>确定</a>
    <a class="window_btn" href="javascript:void(0)" id="close" onclick=''>关闭</a>
</p>
