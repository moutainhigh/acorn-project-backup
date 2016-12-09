/**
 * Created with IntelliJ IDEA.
 * User: fenghuan
 * Date: 13-11-5
 * Time: 上午10:50
 * To change this template use File | Settings | File Templates.
 */
function startEditing() {
	$('#categoryOkBtn').show();
	$('#categoryEditBtn').hide();
	setEditing(true);
	$('#left-panel').panel('setTitle', '知识及话术  (编辑中)');
}
function finishEditing() {
	$('#categoryEditBtn').show();
	$('#categoryOkBtn').hide();
	setEditing(false);
	$('#left-panel').panel('setTitle', '知识及话术');
}
function convert(id){
	if (id !=""&&id!=null) {
			$("#main-layout")
	        .layout('panel','center').panel('refresh','/distribution/init?id='+id);	    
	};      	
//	$('#main-layout').layout('add',{
//        region: 'east',
//        width: 300,
//        split: true,
//        border:false,
//        href:'/common/right.jsp'
//    });
}	
function toHome(index){
    $('#main-layout').layout('remove','east');
    if($('#isHome')[0]){
        var plTop = $("#main-layout-panel").offset().top;
        var scrollTop =  $("#"+index).offset().top;
        if(scrollTop!=plTop){
            $("#main-layout-panel")
                .animate({scrollTop:0},0);
            $("#main-layout-panel")
                .animate({scrollTop:$("#"+index).offset().top-plTop-10},0);
        }
    }else{
        $("#main-layout")
            .layout('panel','center').panel('refresh','/index');

    }


}

function newArticle (id,type){
	if (id !=""&&id!=null&&type!=""&&type!=null) {
		$("#main-layout")
        .layout('panel','center').panel('refresh','/distribution/init?categoryId='+id+'&type='+type);	    
};      		
}
function articleList (id){
	if (id !=""&&id!=null) {
		$("#main-layout")
        .layout('panel','center').panel('refresh','/distribution/initList?categoryId='+id);
}else {
	$("#main-layout")
    .layout('panel','center').panel('refresh','/distribution/initList');
};

//$('#main-layout').layout('add',{
//    region: 'east',
//    width: 300,
//    split: true,
//    border:false,
//    href:'/common/right.jsp'
//});
	
}

function favoriteList() {
	$("#main-layout")
    .layout('panel','center').panel('refresh','/favorite/initList');
}
function deleteArticle(id) {
	$.post("/distribution/deletedistribution", {
		"id":id			
	},function(data) {
		if (data.flag==true) {
			alerts("删除成功");		
		}else {
			alerts("删除失败");	
		}
	},'json');	
	
}

function logout(){

        $.messager.defaults = { ok: "是", cancel: "否"};
//        $.messager.linkbutton({ plain:true});
        $.messager.confirm('提示', '<p style="font-size:14px;height:30px;line-height: 30px;color: #666">确定退出?<p>', function(r){
            if (r){
                window.location.href='/logout';
            }
        });

}


/* loading for page and ajax*/
$(document).ready(function () {
    $(document).ajaxStart( function () {
        $( 'body').addClass('loading' );
    }).ajaxStop(function () {
        $( 'body').removeClass('loading' );
    }).ajaxComplete(function(event, xhr, settings) {
        if(xhr.getResponseHeader("session-status")=="timeout"){
            if(xhr.getResponseHeader("expired-url")){
                $.messager.alert("提示","会话过期，请重新登陆!","error",function(){
                    window.top.location.replace(xhr.getResponseHeader("expired-url"));
                });
            }else{
                $.messager.alert("提示","请求超时请重新登陆 !","error");
            }
        }
    });
});

$(window).load(function () {
    $('body').removeClass( 'loading');
    $('.ajax-loader').bind( "click", function () {
        $( 'body').addClass('loading' );
    });
});