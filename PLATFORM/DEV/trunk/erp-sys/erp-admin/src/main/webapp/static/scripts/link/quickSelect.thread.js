$(document).ready(function() {
    $(function () {
    $('#listSelectTable').datagrid({
        title:'',
        iconCls:'icon-edit',
        width: 700,
        height: 'auto',
        nowrap: false,
        striped: true,
        border: true,
        collapsible:false,
        fit: true,
        url:'/union/link/json',
        //sortName: 'code',
        //sortOrder: 'desc',
        remoteSort:false,
        idField:'id',
        singleSelect:false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[[
        {checkbox:false}
    ]],
    queryParams: {
    	linkId: $("#linkSelect").val()
        }
    });

    var p = $('#listSelectTable').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: 'page    total {pages}page',
    displayMsg: 'current {from} - {to} records total {total} records'
      });
    });
    
    
    $("#copy_url_svn").click(function(){
   	 var txt=document.getElementById("picLink").value;
   	 var unionId=document.getElementById("unionId").value;
   	 var linkresourceId=document.getElementById("linkresourceId").value;
   	 var urlOne=document.getElementById("urlOne").value;
   	 var urlTwo=document.getElementById("urlTwo").value;
   	 var urlThd=document.getElementById("urlThd").value;
         if(txt==''){
         	return ;
         }
         //----------------------
         $.ajax({ 
      	   type: "POST", 
       	   url: "/union/link/save", 
       	  data: "urlOne=" + urlOne +"&urlTwo="+urlTwo+"&urlThd="+urlThd+"&unionId="+unionId+"&linkresourceId="+linkresourceId, 
       	   success: function(o){
                    if(o.result){
                   	 alert("成功");
                   	 document.location.reload();
                  }
      	       } 
       	   }); 
         //-------------------------
     
   	});

   $("#copy_url_btn").click(function(){
   	 var txt=document.getElementById("picLink").value;
        var tempval = $("#picLink");
        tempval.focus();
        tempval.select();
        if(window.clipboardData) {
            //window.clipboardData.clearData();
            window.clipboardData.setData("Text", txt);
            alert("广告代码复制成功！");
        } else if(navigator.userAgent.indexOf("Opera") != -1) {
            window.location = txt;
            alert("广告代码复制成功！");
        } else if (window.netscape) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            } catch (e) {
                alert("您使用的浏览器是FireFox!\n复制到粘贴板的操作被浏览器拒绝！您可以直接复制广告代码或使用非fireFox浏览器\n您也可以这样设置您的fireFox\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
            }
            var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
            if (!clip)
                return;
            var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
            if (!trans)
                return;
            trans.addDataFlavor('text/unicode');
            var str = new Object();
            var len = new Object();
            var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
            var copytext = txt;
            str.data = copytext;
            trans.setTransferData("text/unicode",str,copytext.length*2);
            var clipid = Components.interfaces.nsIClipboard;
            if (!clip)
                return false;
            clip.setData(trans,null,clipid.kGlobalClipboard);
            alert("广告代码复制成功！");
        }
    
   	});
});   
  


var queryData = function () {
    $('#listSelectTable').datagrid('load',{
    	linkId:  $("#linkSelect").val()
    });
    
};
