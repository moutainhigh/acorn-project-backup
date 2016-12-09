//设置显示所在页面
function setBread(){
    var lev1 ;
    var ind;
    var left_url;
    var right_url;
    var menu_permissions ={
        '用户角色分配':'permissions/item1',
        '角色菜单维护':'permissions/item2',
        '角色控件维护':'permissions/item3',
        '角色数据过滤':'permissions/item4',
        '系统维护':'permissions/sys',
        '用户数据配置':'permissions/user'};
    var menu_param ={
        '配置组':'param/group',
        '配置项':'param/config'
    };
    $('#tab').tabs({
        border:false,
        fit:true,
        onSelect:function(title,index){
            ind = index;
            lev1 =    title;
            if(index=="1"){
              left_url = 'archives/left';
              right_url = 'archives/role';
            }
        },
        onLoad:function(panel){
            panel.panel('panel').find('.breadcrumb strong').text(lev1);
            if(panel.panel('panel').find('.tag_a')[0]){
                 panel.panel('panel').find('.tag_a').layout('panel','west').panel('refresh',left_url).panel('setTitle',lev1);
            }
            if(panel.panel('panel').find('.tag_b')[0]){
                switch (ind){
                    case 2: addTab(panel,menu_permissions); break;
                    case 3: addTab(panel,menu_param); break;
                }
            }

        }
    });
}

//初始化模板template2中左边导航
function addTab(obj,data){
    for(var t in data){
        obj.panel('panel').find('.tag_b').tabs({
            onSelect:function(title){
                obj.panel('panel').find('.breadcrumb span').html('&nbsp;>&nbsp;'+title).show();
            }
        }).tabs('add',{
            title:t,
            href:data[t]
        });
    }
    //设置默认选择项
    obj.panel('panel').find('.tag_b').tabs('select',0);
}


//初始化模板template1中左边导航
function initMemus(a_arr){
    a_arr.each(function(){
          $(this).bind('click',function(){
               $("div[selected][href='archives'] .tag_a").layout('panel','center').panel('refresh',$(this).attr('data-sm'));
               $("div[selected][href='archives'] .breadcrumb span").html('&nbsp;>&nbsp;'+$(this).text()).show();
              return false;

          })
    });
    //设置默认选择项
    a_arr[0].click();

}
$(function(){
    setBread();
});