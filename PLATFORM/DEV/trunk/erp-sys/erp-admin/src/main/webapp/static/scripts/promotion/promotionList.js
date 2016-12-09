$(document).ready(function() {
    $(function () {
    	//var v_channelType = eval('('+$('#v_channelType').html().replace("=",":")+')'); //反馈结果
        $('#addNewpromotion').bind('click', function(event) {
            window.location.href = "/admin/promotion";
        });

        $('#query_promotion_btn').bind('click', function(event) {
   
            queryPromotionData();
        });

        $('#deletepromotionsThread').bind('click', function(event) {
            deletepromotionThread();
        });

        $('#updatepromotionsThread').bind('click', function(event) {
            updatePromotionThread();
        });

        $('#decExecOrder').bind('click', function(event) {
            incExecOrderThread();
        });

        $('#incExecOrder').bind('click', function(event) {
            decExecOrderThread();
        });
        
        
        $('#startpromotion').bind('click', function(event) {
        	startpromotion();
        });
        $('#stoppromotion').bind('click', function(event) {
        	stoppromotion();
        });
        
        $('#lookpromotionInfo').bind('click', function(event) {
        	lookpromotionInfo();
        });

        $('#listPromotionTable').datagrid({
            title:'',
            iconCls:'icon-edit',
            width: 700,
            height: 400,
            nowrap: false,
            striped: true,
            border: true,
            collapsible:false,
            fit: true,
            url:'/admin/promotionsJson',
            columns:[[
                {field:'promotionid',title:'ID',width:60},
                {field:'channel',title:'渠道',width:100,  
                	formatter: function(obj) {
                    	var json =eval('('+obj+')');
                    return json != null ? json.name : "";
                	}
                },
                {field:'name',title:'促销规则',width:100},
                {field:'startDate',title:'开始时间',width:100},
                {field:'endDate',title:'结束时间',width:100},
                //{field:"pprodcuts",title:'限制产品',width:100},
                {field:'description',title:'描述',width:100},
                {field:'active',title:'状态',width:60,formatter: function(obj) {
                    	
                    return obj  ? "开始" : "停止";
                	}},
                {field:'maxuse',title:'使用次数',width:100},
                {field:'cumulative',title:'可累积',width:60},
          
                //{field:'execOrder',title:'执行次序',width:100},
                {field:'calcRound',title:'计算批次',width:100}
            ]],
            remoteSort:false,
            idField:'id',
            singleSelect:false,
            pagination:true,
            rownumbers:true,
            frozenColumns:[[
                {field:'ck',checkbox:true}
            ]],
            queryParams: {
                promotionName: $("#promotionName").val(),
                active: $("#active").val()
            }
        });

        var p = $('#listPromotionTable').datagrid('getPager');
        $(p).pagination({
            pageSize: 10,
            pageList: [5,10,15],
            beforePageText: '',
            afterPageText: '页    共 {pages}页',
            displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
            /*onBeforeRefresh:function(){
             $(this).pagination('loading');
             alert('before refresh');
             $(this).pagination('loaded');
             }*/
        });

    });
    
    

});


var queryPromotionData = function () {
    $('#listPromotionTable').datagrid('load',{
        promotionName: $("#promotionName").val(),
        active: $("#active").val()
    });
};

var getSelectIds = function () {
    var ids = [];
    var rows = $('#listPromotionTable').datagrid('getSelections');
    for(var i=0; i<rows.length; i++){
        ids.push(rows[i].promotionid);
    }
    return(ids.join(','));
}


var deletepromotionThread = function () {
    var ids = getSelectIds();
     //已经激活的不允许编辑
    if (ids != "" && confirm('确定要删除吗?')) {
        window.location.href = "/admin/promotionDelete/"+ids;
    }
};

var updatePromotionThread = function () {
    var ids = getSelectIds();
    var rows = $('#listPromotionTable').datagrid('getSelections');
    if (rows.length != 1){
        confirm('请选择一条数据?');
        return;
    }
    
	var now=new Date();
    var st = Date.parse(rows[0].startDate.replace(/-/g,"/"));
    //系统时间是大于开始时间
    if( now > st){
  	  alert("系统时间 大于 开始时间 ");
  	  return;
    }else{
    	window.location.href = '/admin/promotion?promotionId=' + ids;	
    }
    
    
};

var incExecOrderThread = function () {
    var ids = getSelectIds();
    var rows = $('#listPromotionTable').datagrid('getSelections');
    if (rows.length != 1){
        confirm('请选择一条数据?');
        return;
    }
    window.location.href = "/admin/promotionExecOrder?promotionId="+ids+"&direction=1";

};

var decExecOrderThread = function (p_oEvent) {
    //call the delete action.
    var ids = getSelectIds();
    var rows = $('#listPromotionTable').datagrid('getSelections');
    if (rows.length != 1){
        confirm('请选择一条数据?');
        return;
    }
    window.location.href = "/admin/promotionExecOrder?promotionId="+ids+"&direction=-1";

};

var startpromotion=function(p_oEvent){
    var ids = getSelectIds();
    var rows = $('#listPromotionTable').datagrid('getSelections');
    if (rows.length != 1){
        confirm('请选择一条数据!');
        return;
    }
    if(rows[0].active){
    	alert("此条促销规则已经开始");
    	return;
    }
    window.location.href = "/admin/promotionActive?promotionId="+ids+"&active=1";
}

var stoppromotion=function(p_oEvent){
    var ids = getSelectIds();
    var rows = $('#listPromotionTable').datagrid('getSelections');
    if (rows.length != 1){
        confirm('请选择一条数据!');
        return;
    }
    
    if(! rows[0].active){
    	alert("此条促销规则已经停止");
    	return;
    }
    window.location.href = "/admin/promotionActive?promotionId="+ids+"&active=0";
}

var lookpromotionInfo=function(p_oEvent){
    var ids = getSelectIds();
    var rows = $('#listPromotionTable').datagrid('getSelections');
    if (rows.length != 1){
        confirm('请选择一条数据!');
        return;
    }
    window.location.href ='/admin/promotionInfo?promotionId=' + ids;
}






