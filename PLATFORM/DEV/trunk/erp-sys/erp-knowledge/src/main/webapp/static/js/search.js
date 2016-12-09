function doSearch (value,name) {
    if (value==null||value =="") {
        $.messager.alert('信息提示框',"请填写查询内容");
        return;
    }
    var pageIndex = 1; //页面索引初始值
    var pageSize = 10; //每页显示条数初始化
    initTable(1,pageSize,value);
    $('#pp').pagination({
        pageSize: 10,
        pageNumber:1,
        pageList: [10],
        onSelectPage: function (pageNumber, pageSize) {
            $(this).pagination('loading');
            $(this).pagination('loaded');
            initTable(pageNumber, pageSize,value);
        }
    });

}

function view(id,name){
	var pp = $('#searchTab');
	if (!pp.tabs('exists', name)){
		if (id !=""&&id!=null) {
	        var url = '/knowledge/initw?id='+id;
	        addPanel(url,name,id);
	    }
	}else{
        pp.tabs('select', name,id);
    }
}

function delHtmlTag(str)
{
	return str.replace(/<[^>]+>/g,"");//去掉所有的html标记
}
function initTable (pageNumber, pageSize,value){
    var html = "";
    var searchType = $('#searchType').combobox('getValue');
    var content = "";
    var discouse = "";
    var total = 0;
    var value =
        $.post("search/searchList", {
            "filed":value,
            "pageSize":pageSize,
            "currentPage":pageNumber,
            "searchType":searchType
        },function(data) {
            if (data.total==0||data.total=="0"){
                $('#pp').hide();
                $("#TmpContent").html("无结果").addClass("noResult");
                return
            }
            if (searchType==1) {
                for (var i=0;i<data.size;i++) {
                    discourse = data.rows[i].discouse;
                    content = data.rows[i].content;
                    if (content ==null||content =='null') {
                        content = discourse;
                    }
                    html = html +" <table class='result' id='4' srcid='1528' tpl='se_com_default'   data-click='{&quot;rsv_bdr&quot;:&quot;0&quot;,&quot;p5&quot;:4}'> "
                        + " <td class='c-default'>   <h3 class='t'><a href='javascript:view("+data.rows[i].id+",\""+delHtmlTag(data.rows[i].productName)+"\")' target='_blank' >"+data.rows[i].productName+"  </a> "
                        + " </h3> <p class='f13 m'>最后更新时间: "+data.rows[i].createDate+"</p>   <div class='c-abstract'><span class='m' ></span>"+content
                        +"</div> </td> </tr> </tbody> </table>";
                }
            }else {
                for (var i=0;i<data.size;i++) {
                    html = html +" <table class='result' id='4' srcid='1528' tpl='se_com_default'   data-click='{&quot;rsv_bdr&quot;:&quot;0&quot;,&quot;p5&quot;:4}'> "
                        + " <td class='c-default'>   <h3 class='t'><a href='javascript:view("+data.rows[i].id+","+delHtmlTag(data.rows[i].productName)+")' target='_blank' >"+data.rows[i].title+"  </a> "
                        + " </h3> <p class='f13 m'>最后更新时间: "+data.rows[i].createDate+"</p>   <div class='c-abstract'><span class='m' ></span>"+data.rows[i].discouse
                        +"</div> </td> </tr> </tbody> </table>";
                }
            }
            $('#pp').show();
            $('#pp').pagination({ total: data.total});
            $("#TmpContent").html(html);
        },'json');
}
function addPanel(url,name,id){
    $('#searchTab').tabs('add',{
        title: name,
        href:url,
        closable: true
    });
}