function _handler(){
       $('#tabs').tabs('resize',{width:'100%'});
       $('#dgCase').datagrid('resize',{width:'100%'});
}

$(function(){

    $("#cbCaseType").combobox({
        url:'/case/type/lookup',
        valueField: 'casetypeid',
        textField: 'dsc',
        onSelect:function(r){
            $.post("/case/category/lookup",{ category: r.category }, function(data){
                $("#cbCategory").combobox({
                    data: data,
                    valueField: 'categoryId',
                    textField: 'item'
                });
            });
        }
    });

    $("#cbCaseState").combobox({
        url:'/case/state/lookup',
        valueField: 'casestatid',
        textField: 'dsc'
    });

    $("#cbSolution").combobox({
        url:'/case/solution/lookup',
        valueField: 'tmpId',
        textField: 'dsc'
    });

    $("#cbCaseSource").combobox({
        url:'/case/source/lookup',
        valueField: 'tmpId',
        textField: 'dsc'
    });

    $("#cbBuyChannel").combobox({
        url:'/case/channel/lookup',
        valueField: 'tmpId',
        textField: 'dsc'
    });

    $("#cbSatisfaction").combobox({
        url:'/case/satisfaction/lookup',
        valueField: 'tmpId',
        textField: 'dsc'
    });

    $("#cbReason").combobox({
        url:'/case/reason/lookup',
        valueField: 'tmpId',
        textField: 'dsc'
    });

    $("#cbPriority").combobox({
        url:'/case/priority/lookup',
        valueField: 'priorityId',
        textField: 'dsc'
    });


    $('#dgCase').datagrid({
        width : '100%',
        height : 410,
        scrollbarSize:0,
        nowrap:false,
        fitColumns : false,
        idField: "caseid",
        url: "/case/search",
        columns : [ [
            { field : 'caseid', title : '事件编号', width: 130, align: 'center',
                formatter:function(val,row,index){
                    return "<a href=\"javascript:addEventTab('"+val+"','"+row.contactid+"','"+row.contactname+"');\">"+val+"</a>";
                }
            },
            { field : 'dsc1', title : '事件类型', width: 130, align:'center' },
            { field : 'contactname', title : '姓名', align: 'center' },
            { field : 'orderid', align:'center', title : '订单编号' },
            { field : 'scode', title : '产品', align: 'center' },
            { field : 'probdsc', title : '问题描述' },
            { field : 'dsc3', title : '解决方案', align:'center', width : 130 },
            { field : 'external', title : '处理描述', align: 'center' },
            { field : 'crusr', title : '员工号', width: 100,  align: 'center' } ,
            { field : 'contactaddr', title : '地址', width: 100, align: 'center' },
            { field : 'contactid', title : '客户编号', width: 100, align: 'center' }
        ] ],
        remoteSort : false,
        singleSelect : true,
        pagination : true,
        onDblClickRow: function(rowIndex, rowData){
            addEventTab(rowData.caseid,rowData.contactid, rowData.contactname);
        }
    });

    var p = $('#dgCase').datagrid('getPager');
    $(p).pagination({
        pageSize : 10,
        pageList : [ 5, 10, 15 ],
        beforePageText : '第',
        afterPageText : '页    共 {pages} 页',
        displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh : function() {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });

    $("#lnkClear").click(function(){
        $("#fmSearch").form("clear");
    });

    $('#lnkSearch').click(function() {
        var validate = $('#fmSearch').form('validate');
        if(validate){
            var a = $('#fmSearch').serializeArray();
            if(a.length > 0){
                var o = {
                    page: 1
                };
                $.each(a, function() {
                    if (o[this.name] !== undefined) {
                        if (!o[this.name].push) {
                            o[this.name] = [o[this.name]];
                        }
                        o[this.name].push(this.value || '');
                    } else {
                        o[this.name] = this.value || '';
                    }
                });
                o.begindate = o.begindate + " 00:00:00";
                o.enddate = o.enddate + " 23:59:59";
                var bcrdt = new Date(o.begindate.replace(/-/g,"/"));
                var ecrdt = new Date(o.enddate.replace(/-/g,"/"));
                var day = (ecrdt.getTime()-bcrdt.getTime())/(1000*60*60*24);
                if(day > 7){
                    window.parent.alertWin("警告","查询范围不能7天!");
                }
                else {
                    $('#dgCase').datagrid('load',o);
                }
            } else {
                window.parent.alertWin("警告","请至少选择一个查询条件");
            }
        }
    });

    $("#lnkExport").click(function(){
        var validate = $('#fmSearch').form('validate');
        if(validate){
            var a = $('#fmSearch').serializeArray();
            if(a.length > 0){
                var o = {};
                $.each(a, function() {
                    if (o[this.name] !== undefined) {
                        if (!o[this.name].push) {
                            o[this.name] = [o[this.name]];
                        }
                        o[this.name].push(this.value || '');
                    } else {
                        o[this.name] = this.value || '';
                    }
                });
                o.begindate = o.begindate + " 00:00:00";
                o.enddate = o.enddate + " 23:59:59";
                var bcrdt = new Date(o.begindate.replace(/-/g,"/"));
                var ecrdt = new Date(o.enddate.replace(/-/g,"/"));
                var day = (ecrdt.getTime()-bcrdt.getTime())/(1000*60*60*24);
                if(day > 7){
                    window.parent.alertWin("警告","查询范围不能7天!");
                } else {
                    //alert('/case/export?'+$.param(o));
                    document.location.href='/case/export?'+$.param(o);
                }
            } else {
                window.parent.alertWin("警告","请至少选择一个查询条件");
            }
        }
    });
});


function addEventTab(caseid,contactid,contactname){
    if(contactid){
        window.parent.addTab('E'+contactid, contactname+"-事件管理", false, "/case/index?caseId="+caseid+"&contactId="+contactid+"&contactName="+encodeURIComponent(contactname),"",true);
    } else {
        window.parent.alertWin("警告","客户编号不能为空!");
    }
}

function dateFormatter(val, row){
	if(null!=val){
		return new Date(val).format('yyyy-MM-dd hh:mm:ss');
	}
}

Date.prototype.format = function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(),    //day
	"h+" : this.getHours(),   //hour
 	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
 	"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
 	"S" : this.getMilliseconds() //millisecond
	};

	if(/(y+)/.test(format)) {
		format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}
	
 	for(var k in o){
		if(new RegExp("("+ k +")").test(format)){
 			format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		}
	}
	return format;
};