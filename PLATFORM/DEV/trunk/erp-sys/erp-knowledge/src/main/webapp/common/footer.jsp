<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<div id="footer">
      <script type="text/javascript">
      $.ajaxSetup({
	    	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	    	complete : function(xhr, textStatus) {
	    	    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	    	    var curWwwPath=window.document.location.href;
	    	    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	    	    var pathName=window.document.location.pathname;
	    	    var pos=curWwwPath.indexOf(pathName);
	    	    //获取主机地址，如： http://localhost:8083
	    	    var localhostPaht=curWwwPath.substring(0,pos);
	    	    //获取带"/"的项目名，如：/uimcardprj
	    	    //var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	    	    var projectName = "<%=request.getContextPath()%>";
	    		//session timeout
	    		if (xhr.status == 911) {
	    			window.location = localhostPaht+projectName;//返回应用首页
	    			return;
	    		}
	    	}
	    }); 
      </script>
<sec:authentication property="principal" var="authentication"/>
<table>
    <tbody><tr>
        <td class="noborder">
            <div >  座席工号：<sec:authentication property="name"/> | 座席姓名：${authentication.displayName } | 角色：   <sec:authentication property="authorities"/> |  <label>最后更新时间:</label>&nbsp;&nbsp;<span><spring:message code="systemTimes"/></span>
            </div>
        </td>
      
       


    </tr></tbody></table>
</div>