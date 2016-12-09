<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <title>促销管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="/static/scripts/promotion/promotionList.js"></script>
    <script type="text/javascript" src="/static/scripts/jquery/plugins/jquery.datagrid.js"></script>
</head>
<body>
 
<div id="v_channelType" style="display:none" >${channelType_json}</div>
<div>
    <fieldset>
        <legend>促销</legend>
        <div>
            <div class="operator-btn">
                <div>
                    <form id="promotionQueryForm" action="" method="post" enctype="multipart/form-data">
                        <label for="promotionName">促销名称:</label>
                        <input id="promotionName" name="promotionName" name="promotionName" type="text" size="30"/>
                        <label for="active">状态:</label>
                        <select id="active" name="active">
                            <option value="2" selected="selected">All</option>
                            <option value="1">开始</option>
                            <option value="0">停止</option>
                        </select>
                        <input type="button" name="query_promotion_btn" value="查询" id="query_promotion_btn"/>
                    </form>
                    <br/>
                </div>
            </div>
        </div>
    </fieldset>
</div>
<div class="yui-g">
    <div style="padding: 10px">
        <input type="button" id="updatepromotionsThread" name="updatepromotionsThread"
               value="编辑">
        <!--       
        <input type="button" id="deletepromotionsThread" name="deletepromotionsThread"
               value="删除">
             
        <input type="button" id="refreshpromotionsThread" name="refreshpromotionsThread"
               value="刷新">
               -->
        <input type="button" id="addNewpromotion" name="addNewpromotion" value="添加">
        <input type="button" id="startpromotion" name="startpromotion" value="开始">
        <input type="button" id="stoppromotion" name="stoppromotion" value="停止">
        <input type="button" id="lookpromotionInfo" name="lookpromotionInfo" value="察看详细">
        <!--  
        <input type="button" id="decExecOrder" name="decExecOrder" value="延后执行次序">
        <input type="button" id="incExecOrder" name="incExecOrder" value="提前执行次序">
         -->
    </div>
    <div style="padding: 3px;height: 350px">
    <table id="listPromotionTable" cellspacing="0" cellpadding="0" >

    </table>

    </div>
</div>

</body>
</html>
