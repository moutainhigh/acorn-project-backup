<%--
  ~ Copyright (c) 2012 Acorn International.
  ~
  ~  Licensed under the Apache License, Version 2.0 (the "License");
  ~  you may not use this file except in compliance with the License.
  ~  You may obtain a copy of the License at
  ~
  ~      http:www.apache.org/licenses/LICENSE-2.0
  ~
  ~  Unless required by applicable law or agreed to in writing, software
  ~  distributed under the License is distributed on an "AS IS" BASIS,
  ~  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
  ~  See the License for the specific language governing permissions and
  ~  limitations under the License.
  --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<html>
<head>
    <%@ include file="/common/meta.jsp" %>


    <link type="text/css" rel="stylesheet" href="/static/style/main.css"/>
    <link type="text/css" rel="stylesheet" href="/static/style/global.css"/>
    <link type="text/css" rel="stylesheet" href="/static/style/layout.css"/>

    <!--DataTable-->


    <script type="text/javascript" src="/static/scripts/messages.js"></script>
    <script type="text/javascript" src="/static/scripts/main.js"></script>

    <decorator:head/>
</head>
<body class="yui-skin-sam"  <decorator:getProperty property="body.id" writeEntireProperty="true"/>>
<input id="yui-history-field" type="hidden">
<div id="doc3" class="yui-t7">
    <div id="hd">
    </div>
    <div id="bd">
        <decorator:body/>
    </div>
    <div id="ft">
    </div>
</div>
</body>
</html>
