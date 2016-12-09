<%--
  Created by IntelliJ IDEA.
  User: haoleitao
  Date: 13-10-11
  Time: 下午2:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
    <title>403</title>
    <script type="text/javascript">
        function countDown(secs,surl){
            //alert(surl);
            var jumpTo = document.getElementById('jumpTo');
            jumpTo.innerHTML=secs;
            if(--secs>0){
                setTimeout("countDown("+secs+",'"+surl+"')",1000);
            }
            else{
                location.href=surl;
            }
        }
    </script>
</head>
<body>
对不起,您没有此页面的访问权限:
<br/>
</a><span id="jumpTo">5</span>秒后自动跳转到:<a href="${ctx}/login">登录页面</a>
<script type="text/javascript">countDown(5,'${ctx}/login');</script>
</body>
</html>