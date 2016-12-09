var bNeedRefresh=false;
$(function(){
    if(isManager==false)
    {
        $('#message_recivier').attr('disabled','disabled');
    }
    $("#message_status").combobox({
        data:messageStatuses,
        valueField: 'id',
        textField: 'dsc'
    });

    $("#message_month").combobox({
        data:messageMonthes,
        valueField: 'id',
        textField: 'dsc'
    });
    $("#message_month").combobox("setValue",0);

    $("#message_type").combobox({
        data:messageTypes,
        valueField: 'id',
        textField: 'dsc'
    });

    var totalMessageCount=-1;
    var parmMessage={};
    $('#messagelist').datagrid({
        title:'',
        iconCls:'icon-edit',
        width : '100%',
        height : 410,
        fitColumns : true,
        striped: true,
        border: true,
        collapsible:true,
        rowStyler: function(index,row)
        {
            if(row.checked==false)
                return 'cursor:pointer;color:#36c';
        },
        fit: true,
        columns:[[
            {field:'id',title:'ID',width:0,editor:'text', hidden: true},
            {field:'sourceTypeId',title:'消息类型',align:'center',width:80,editor:'text',formatter:function (value,row,index){
                for(var i= 0; i<messageTypes.length; i++){
                    if (messageTypes[i].id == value || !value) return messageTypes[i].dsc;
                }
                return value;
            }},
            {field:'content',title:'消息内容',width:140,editor:'text',formatter:function (value,row,index){
                var typeName=null;
                for(var i= 0; i<messageTypes.length; i++){
                    if (messageTypes[i].id == row.sourceTypeId)
                    {
                        typeName= messageTypes[i].dsc;
                        break;
                    }
                }

                //if(row.checked==false)
                {
                    if(row.sourceTypeId=='1')
                    {
                        return '您有<b>'+value+'</b>个'+typeName+'已回复';
                    }
                    else if(row.orgSourceType=='9'||row.orgSourceType=='11')
                    {
                        return '您有<b>'+value+'</b>个'+typeName+'已驳回';
                    }
                    else
                    {
                        return '您有<b>'+value+'</b>个'+typeName+'待处理';
                    }
                }
            }},
            {field:'checked',title:'消息状态',align:'center',width:80,editor:'text',formatter:function (value,row,index){
                var status=null;
                if(value!=null)
                {
                    status=value.toString();
                }
                for(var i= 0; i<messageStatuses.length; i++){
                    if (status && messageStatuses[i].id == status) return messageStatuses[i].dsc;
                }
                return "<span class=''>"+value+"</span>";
            }},
            {field:'createDate',title:'最早发送时间',width:80,editor:'text'},
            {field:'receiverId',title:'接收座席',align:'center',width:80,editor:'text'},
            {field:'recivierGroup',title:'座席组',width:80,editor:'text'}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        queryParams: parmMessage,
        onBeforeLoad:function(data)
        {
            if(data==null||data.receiverId==null)
            {
                return false;
            }
            else
            {
                return true;
            }
        },
        //url:'/message/query',
        onLoadSuccess: function(data){
            if(data.err!=null)
            {
                window.parent.alertWin('系统提示', data.err);
            }
            if(data.total!=null)
            {
                totalMessageCount=data.total;
            }
        },
        onLoadError: function(){
            window.parent.alertWin('系统提示',"加载失败!");

        },
        onSelect:function(rowIndex, rowData)
        {
            if(rowData.checked==true)
            {
                return;
            }

            for(var i=0;i<10;i++)
            {
                var bSucc=true;
                var bCheck=false;
                if(rowData.orgSourceType=='5'||rowData.orgSourceType=='6'||rowData.orgSourceType=='11')
                {
                    if(isManager==false)
                        bCheck=true;
                    bSucc=window.parent.showTabAndCallFunc('myorder','queryFromSysmessage',rowData);
                }
                else if(rowData.orgSourceType=='2'||rowData.orgSourceType=='3'||rowData.orgSourceType=='10')
                {
                    bCheck=true;
                    bSucc=window.parent.showTabAndCallFunc('mytask','queryFromSysmessage',rowData);
                }
                else if(rowData.orgSourceType=='0')
                {
                    //问题单
                    if(isManager==false)
                        bCheck=true;
                    bSucc=window.parent.showTabAndCallFunc('myorder','queryFromSysmessage',rowData);
                }
                else if(rowData.orgSourceType=='1')
                {
                    //催送货
                    if(isManager==false)
                        bCheck=true;
                    bSucc=window.parent.showTabAndCallFunc('myorder','queryFromSysmessage',rowData);
                }
                else if(rowData.orgSourceType=='4'||rowData.orgSourceType=='8')
                {
                    //修改客户
                    bCheck=true;
                    bSucc=window.parent.showTabAndCallFunc('mytask','queryFromSysmessage',rowData);
                }
                else if(rowData.orgSourceType=='7'||rowData.orgSourceType=='9')
                {
                    //修改客户驳回
                    if(isManager==false)
                        bCheck=true;
                    bSucc=window.parent.showTabAndCallFunc('mycustomer','queryFromSysmessage',rowData);
                }

                if(bSucc==true)
                {
                   //update check
                   if(bCheck==true)
                   {
                       $.ajax({
                           type : "post",
                           url : "/message/check",
                           data : rowData,
                           dataType:"json",
                           async : false,
                           success : function(data){
                               bNeedRefresh=true;
                           }
                       });
                   }
                    $.ajax({
                        type : "post",
                        url : "/message/sleep",
                        data : "millisecond=1500",
                        dataType:"json",
                        async : false,
                        success : function(data){
                        }
                    });
                   break;
                }
                else
                {
                    $.ajax({
                        type : "post",
                        url : "/message/sleep",
                        data : "millisecond=1000",
                        dataType:"json",
                        async : false,
                        success : function(data){
                        }
                    });
                }
            }
            console.debug("onSelect ok!"+rowIndex);
        },
        onClickRow:function(rowIndex, rowData)
        {
            console.debug("onClickRow "+rowIndex);
        }
    });

    var bInit=false;
    $('#messagesearch').click(function(){
        if(isManager==true)
        {
            if(!$("#message_recivier").validatebox("isValid"))
            {
                return;
            }
        }

        totalMessageCount=-1;
            parmMessage={
                'checked' : $("#message_status").combobox('getValue'),
                'monthType': $("#message_month").combobox('getValue'),
                'sourceTypeId': $("#message_type").combobox('getValue'),
                'receiverId': $("#message_recivier").val(),
                'messageType':$("#message_type").combobox('getValue'),
                'countRows':totalMessageCount
            };
        if(bInit==false)
        {
        $('#messagelist').datagrid({url:'/message/query'});
            bInit=true;
        }
        $('#messagelist').datagrid('load',parmMessage);
    })

    $('#messagereset').click(function(){
        var currentUsrId=$('#message_recivier').val();
        $('#formmessage').form('clear');
        if(isManager==false)
        {
            $('#message_recivier').val(currentUsrId)
        }
    });
});

function refreshMessage()
{
    if(bNeedRefresh==true)
    {
        bNeedRefresh=false;
        $('#messagelist').datagrid('reload');
    }
}