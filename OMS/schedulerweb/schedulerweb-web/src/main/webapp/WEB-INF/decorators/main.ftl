<!DOCTYPE html>
<html>
<head>
<#include "../pages/ftl/includes/header.ftl"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
${head}
</head>
<body>
<div class="layout">
<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="javascript:;"><img src="${staticFileRoot}/img/logo.png"/>&nbsp;Paff</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
             <li class="active"><a href="javascript:;" class="menu m0" data-val="0">首页</a></li>
             <li><a href="javascript:;" class="menu m-1" data-val="-1">Freemarker 使用</a></li>
             <li><a href="javascript:;" class="menu m2" data-val="2">DataGrid</a></li>
             <li><a href="javascript:;" class="menu m3" data-val="3">Form</a></li>
            </ul>
          </div>
          <div id="shortcut-2013">
				<ul class="fr">
					<li class="fore1" id="loginbar"><span>你好</span>，平安</li>
					<li class="fore1"><a href="#"><i class="icon-cog icon-white"></i>设置</a></li>
					<li class="fore1"><a href="logout"><i class="icon-off icon-white"></i>退出系统</a></li>
				</ul>
				<div class="clean"></div>
			</div>
        </div>
      </div>
</div>
<div class="clean"></div>
 <div class="content row">
	
	${body}
</div>
</div>
</body>
</html>