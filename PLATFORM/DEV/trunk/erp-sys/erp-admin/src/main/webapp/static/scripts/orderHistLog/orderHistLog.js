$(document).ready(function() {

    $(function () {
    $('#listOHistLogTable').edatagrid({
        title:'',
        iconCls:'icon-edit',
        width:'100%',
        height:'100%',
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fit: true,
        fitColumns:true,
        idField:'id',
        url:'/admin/queryOrderTypeSmsLog',
        saveUrl: '',
        updateUrl: '',
        destroyUrl: '',
        columns:[[
            {field:'id',title:'序列号',width:0,hidden:true},
            {field:'smsType',title:'短信类型',width:100,
                formatter: function(value) {
                    if(value=="1"){
                        return "订购短信";
                    }
                    if(value=="2"){
                        return "验证码短信";
                    }
                    if(value=="3"){
                        return "出库短信";
                    }
                }
           },
            {field:'smsName',title:'短信名称',width:120},
            {field:'orderType',title:'订单类型',width:120,sortable:true,
                formatter: function(obj) {
                    return obj != null ? obj.name : "";
                }},
            {field:'paytype',title:'付款方式',width:100,sortable:true,
                formatter: function(obj) {
                return obj != null ? obj.name : "";
            }},
            {field:'setDate',title:'创建时间',width:100,sortable:true},
            {field:'user',title:'创建人',width:70,
                formatter: function(obj) {
                    return obj != null ? obj.usrid : "";
                }},
            {field:'smscontent',title:'短信内容',width:1000},

            {field:'sendDelyTime',title:'延迟时间',width:60}
        ]],
        remoteSort:false,
        singleSelect:true,
        pagination:true,
        rownumbers:true,

        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
    queryParams: {
        v_smstype:$("#smstype").val(),
        v_ordertype:$("#ordertypeId").combo("getValues")+"",
        v_setProName:"",
        v_smsName:$("#smsName").val(),
        usid:$("#LoginName").val(),
        v_paytype:$("#paytype").combo("getValues")+"",
        v_beginDate: $("#beginDate").datebox('getValue'),
        v_endDate: $("#endDate").datebox('getValue')
        }
    });

    var p = $('#listOHistLogTable').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });
    });

    //绑定添加信息内容的订单类型框
      $('#v_ordertype').combobox({
      url:'/admin/OrderTypeLog',
      valueField:'id' ,
      textField:'name',
       editable:false
      });

    //绑定订单类型
   $('#ordertypeId').combobox({
        url:'/admin/OrderTypeLog',
        valueField:'id',
        textField:'name',
       editable:false
    });
        ///绑定快递公司
    $('#v_setProId').combobox({
        url:'/admin/Company/lookup',
        valueField:'companyId' ,
        textField:'companyName',
        editable:false
    });
    //绑定网店名称
    $('#shopName').combobox({
        url:'/admin/OnlieShop/lookup',
        valueField:'shopid' ,
        textField:'shopname',
        editable:false
    });
    ///绑定查询付款方式Cbox
    $('#paytype').combobox({
        url:'/admin/PayType/lookup',
        valueField:'id' ,
        textField:'name',
        editable:false
    });
    ///绑定付款方式Cbox
    $('#v_paytype').combobox({
        url:'/admin/PayType/lookup',
        valueField:'id' ,
        textField:'name',
        editable:false
    });

});




var queryOHistData = function () {
    $('#listOHistLogTable').datagrid('load',{
        v_smstype: $("#smstype").val(),
        v_ordertype: $("#ordertypeId").combo("getValues")+"",
        v_setProName:"",
        v_smsName:$("#smsName").val(),
        usid:$("#LoginName").val(),
        v_paytype:$("#paytype").combo("getValues")+"",
        v_beginDate: $("#beginDate").datebox('getValue'),
        v_endDate: $("#endDate").datebox('getValue')
    });
};
var RefereshOHistData = function () {
    $('#listOHistLogTable').datagrid('load',{
        v_smstype:"",
        v_ordertype:"",
        v_setProName:"",
        v_smsName:"",
        usid:$("#LoginName").val(),
        v_paytype:"",
        v_beginDate: "",
        v_endDate:""
    });
};
//日期转换格式
function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
  //修改内容信息
   function UpdateRow(){
       var row = $('#listOHistLogTable').datagrid('getSelected');
       if(row){
           var id=row.id;
           var v_smsType=row.smsType;
           var v_smsName=row.smsName;
           var v_ordertype=row.orderType;
           var v_paytype=row.paytype;
           var v_setProId=row.user;
           var v_sendDelyTime=row.sendDelyTime;
           var v_smscontent=row.smscontent;
           clearndiv();
           $("#v_id").val(id);//编号
           $("#v_smstype").val(v_smsType);//短信类型
           $("#v_smsName").val(v_smsName);//短信名称
           $("#v_ordertype").combobox( 'setValue',v_ordertype.id);//订单类型
           $("#v_ordertype").combobox("disable"); //('enable');
           $("#v_paytype").combobox( 'setValue',v_paytype.id); //付款方式
           $("#v_paytype").combobox("disable");//付款方式
           $("#v_sendDelyTime").val(v_sendDelyTime);//延迟时间
           $("#v_smscontent").val(v_smscontent);//短信内容
           $("#shopName").val("");//网店名称
           $("#v_setProId").val("");//快递公司
           $('#dlg').dialog('open').dialog('setTitle','修改短信配置');
       }  else{
           alert("请选择一行短信信息进行修改!");
           return;
       }
   }
//清空信息内容
 function clearndiv(){
     $("#v_id").val(0);//编号
     $("#v_smstype").val("");//短信类型
     $("#v_smsName").val("");//短信名称
     $("#v_ordertype").combobox("clear");//订单类型
     $("#v_ordertype").combobox("enable"); //('enable');
     $("#v_paytype").combobox("clear"); //付款方式
     $("#v_paytype").combobox("enable");//付款方式
     $("#v_setProId").combobox("setValue","");//快递公司
     $("#shopName").combobox("setValue","");//快递公司
     $("#v_sendDelyTime").val(0);//延迟时间
     $("#v_smscontent").val("");//短信内容
 }
//新增短信配置
function openNew(){
        $('#dlg').dialog('open').dialog('setTitle','添加短信配置');
         //新增情况并赋默认值
        clearndiv();
}
  //删除内容信息
  function DeleteRow(){
      var row = $('#listOHistLogTable').datagrid('getSelected');
      var id=0;
      if(row){
         id=row.id;
      }
     if(id!=0){
        if(confirm("是否确认删除当前行")){
            $.ajax({
                type:"post", //请求方式
                url:"/admin/DelOrderTypeSmsLog", //发送请求地址
                data:{ //发送给数据库的数据
                    id:id//短信类型
                },
                //请求成功后的回调函数有两个参数
                success:function(data,textStatus){
                    alert("删除成功!");
                    queryOHistData();
                }
            });
        }
     }
  }

var W = screen.width;//取得屏幕分辨率宽度
var H = screen.height;//取得屏幕分辨率高度

function M(id){
    return document.getElementById(id);//用M()方法代替document.getElementById(id)
}
function MC(t){
    return document.createElement(t);//用MC()方法代替document.createElement(t)
};
//判断浏览器是否为IE
function isIE(){
    return (document.all && window.ActiveXObject && !window.opera) ? true : false;
}


//关闭DIV层
function closeinsert(){
    $('#dlg').dialog('close');
    queryOHistData();
}

