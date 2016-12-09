<!--
http://union.yihaodian.com/passport/viewBankInfo.do
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<script>
function view(id,name){
	var pp = $('#searchTab');
	if (!pp.tabs('exists', name)){
		if (id !=""&&id!=null) {
	        var url = '/knowledge/initw?id='+id;
	        addPanel(url,name,id);
	    }
	}else{
        pp.tabs('select', name);
    }
}
function addPanel(url,name,id){
    $('#searchTab').tabs('add',{
        title: name,
        href:url,
        id:id,
        closable: true
    });
}
</script>
<div class="easyui-layout" fit="true" >
            <div style="padding-left: 10px" data-options="region:'north',border:false">
                <div class="desqa mod">
                    <div class="hd">
                        <h2 class="nostyle">关联知识点
                            <a class="attachment fr tagpos"  href="javascript: void(0)" onclick="showTags()">
                                 <span class="attachment-button label-button fl"></span>标签[<span id="attachmentCount" class="red">0</span>]
                            </a>
                        </h2>
                    </div>
                </div>
            </div>
            <div style="margin-top:10px" data-options="region:'center',border:false">
                <div class="desqa mod">
                    <div class="bd">
                        <ul>
                        <c:if test="${flag == '1' }">
                         <c:forEach var="know" items="${knowledgeDto}">
 							<li><i class="comment_12"></i><a href="javascript:void(0)" onclick="view('${know.id }','${know.title }')"
                                                             title="">
                                		${know.title }</a></li>                       
                          </c:forEach>                           
                        </c:if>
                         
                        </ul>
                    </div>
                </div>
            </div>


