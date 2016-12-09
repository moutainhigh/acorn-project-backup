<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="form-tabs">
    <div class="baseInfo" style="margin: 0">
        <table class="" border="0" cellspacing="0" cellpadding="0"
               width="100%">
            <tr>
                <td>客户姓名</td>
                <td>        <input class="fl"  type="text" maxlength="20" value="${name}" />
                </td>
                <td >回访日期</td>
                <td><input  type="text" id="recalldt" value="${caseRecall.recalldt}"  required="true"   name="recalldt" class="easyui-datebox"  style="width:130px"/></td>
                <td>回访类型</td>
                <td>
                    <select id="recalltype" name="recalltype" class="easyui-combobox" required="true" style="width:134px" >
                    </select>

                </td>
            </tr>
            <tr>
                <td>回访状态</td>
                <td><select id="status" name="status" class="easyui-combobox" required="true" style="width:134px" >
                </select>
                <input  type="hidden" id="contactid"  name="contactid"  value="${contactid}">
                <input  type="hidden" id="recid"  name="recid"  value="${recid}">
                <input  type="hidden" id="crdt"  name="crdt" value="${caseRecall.crdt}">
                </td>
                <td >
                    订单编号
                </td>
                <td>
                    <input class="fl" id="orderid" name="orderid"  value="${orderid}" disabled="disabled" type="text" maxlength="20" />
                </td>
                <td>事件编号</td>
                <td>
                    <input class="fl"  id="caseid" name="caseid"  value="${caseid}" type="text" disabled="disabled"  maxlength="20" />
                </td>
            </tr>
            <tr class="remarks">
                <td valign="top">备注:</td>
                <td colspan="5"><textarea style="width:558px;" id="dsc" name="dsc" value="">${caseRecall.dsc}</textarea>
                </td>
            </tr>
        </table>
    </div>

    <p class="textC mt10"><a href="javascript:void(0);" id="save" onclick="saves()" class="window_btn ml10">保存</a>
        <a href="javascript:void(0);" id="exit" onclick="exit()" class="window_btn ml10">取消</a></p>
</div>
<script>


        if ("${caseRecall.recalldt}"=="") {
            $("#recalldt").attr("value", '${nowDate}');
        }

    $("#recalltype").combobox({
        url:'${ctx }/caseRecall/caseRecallType',
        valueField: 'tmpId',
        textField: 'dsc',
        onLoadSuccess:function() {
        if ("${caseRecall.recalltype}" == "") {
            $(this).combobox('setValue', '1');
        }else {
            $(this).combobox('setValue', '${caseRecall.recalltype}');
        }
    }
    });
    $("#status").combobox({
        url:'${ctx }/caseRecall/caseRecallstat',
        valueField: 'tmpId',
        textField: 'dsc',
        onLoadSuccess:function() {
            if ("${caseRecall.status}" == "") {
                $(this).combobox('setValue', '2');
            }else {
                $(this).combobox('setValue', '${caseRecall.status}');
            }
        }
    });



    function saves () {
        var url = "${ctx }/caseRecall/saveRecall";
        var contactid = $("#contactid").val();
        var orderid = $("#orderid").val();
        var caseid = $("#caseid").val();
        var status = $("#status").combobox('getValue');
        var recalltype = $("#recalltype").combobox('getValue');
        var dsc = $("#dsc").val();
        var recalldts = $("#recalldt").datebox("getValue");
        var recid = $("#recid").val();
        var crdt = $("#crdt").val();
        var dsc = $("#dsc").val();
        $.post(url, {
            "caseid":caseid,
            "status":status,
            "orderid":orderid,
            "contactid":contactid,
            "recalltype":recalltype,
            "recalldts":recalldts ,
            "recid":recid,
            "dsc":dsc,
            "crdt":crdt
        }, function(data) {
            if (data.result=="1") {
                window.parent.alertWin('系统提示','保存成功');
            }else {
                window.parent.alertWin('系统提示', '保存失败');
            }
            $('#revisitDialog').window('close');
            if ($("#eventReturn")!=null){
                $("#eventReturn").datagrid("load");
            }


        }, 'json');

    }

    function exit () {
        $('#revisitDialog').window('close');
    }

    /**
     * 时间对象的格式化;
     */
    Date.prototype.format = function(format) {
        /*
         * eg:format="yyyy-MM-dd hh:mm:ss";
         */
        var o = {
            "M+" : this.getMonth() + 1, // month
            "d+" : this.getDate(), // day
            "h+" : this.getHours(), // hour
            "m+" : this.getMinutes(), // minute
            "s+" : this.getSeconds(), // second
            "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
            "S" : this.getMilliseconds()
            // millisecond
        };

        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
                    - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1
                        ? o[k]
                        : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    };

</script>

