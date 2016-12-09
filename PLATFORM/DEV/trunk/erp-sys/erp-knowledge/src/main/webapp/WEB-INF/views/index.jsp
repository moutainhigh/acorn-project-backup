<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>

</head>
<body>
<input type="hidden" id="isHome" value="1"/>
<div id="main-layout-panel" class="easyui-panel" fit="true"  border="false" style="padding:10px">
<p class="pageTitle">首页</p>

<div class="searchWarp"><input class="easyui-searchbox" data-options="prompt:'关键字/产品名称/产品编码/产品简码',searcher:doSearch"
                               style="width:400px;height:30px;"></input></div>
<script>
    function doSearch(value){
    	if (value.trim()!="") {
    		a=encodeURI("/knowledge/initList?keyWord="+value);
    		$("#main-layout")
            .layout('panel','center').panel('refresh',encodeURI(a));	
    	}
    }
</script>
<div class="c_content">
<div class="c_block">
<P  id="0"  class="title">最新发布    <a class="more" href="javascript:void(0)" onclick="articleList('')"> 更多>></a></P>
<ul class="newest">
<c:forEach items="${newAddsList}" var="news">
   <li><a href="javascript:void(0)" onclick="convert('${news.id}');">  
       <span>[<fmt:formatDate value="${news.createDate}"  pattern="yyyy-MM-dd"/>]</span>
   <c:choose>
   <c:when test="${news.productName!=''&&news.productName!=null}">
  	 	${news.productName}
   </c:when>
   <c:otherwise>
   		${news.title}
   </c:otherwise> 
   </c:choose>
   </a>  
   </li>
</c:forEach>

</ul>
</div>
<div class="c_block">
    <P  id="1" class="title" style="margin-bottom: 0">我的收藏<span class="tip">（按照加收藏时间显示10条记录）</span><a class="more" href="javascript:void(0)" onclick="favoriteList()"> 更多>></a></P>
    <div >
    <table id="myFavorite">
    
    </table>
    </div>
</div>
<div class="c_block">
    <P id="2" class="title"  style="margin-bottom: 0">收藏热门<span class="tip">（按照加收藏次数显示10条记录）</span></P>
    <div>
        <table id="hot" ></table>
    </div>
</div>
</div>
<script type="text/javascript" src="${ctx}/static/js/home.js"></script>
</body>
</html>