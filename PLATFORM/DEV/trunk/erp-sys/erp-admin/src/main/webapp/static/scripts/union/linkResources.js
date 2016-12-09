var imgUrl;

$.extend($.fn.datagrid.defaults.editors, {
    uploadEditor : {
        init: function(container, options)
        {
            var editorContainer = $('<div/>');

            var button = $("<a href='javascript:void(0)'>Image</a>")
                .linkbutton({plain:true, iconCls:"icon-remove"});
            button.bind('click', function(){
                popUpload();
            });
            editorContainer.append(button);
            editorContainer.appendTo(container);
            return button;
        },
        getValue: function(target)
        {
            var target = $(target).text();
            return imgUrl;//
        },
        setValue: function(target, value)
        {
            $(target).text(value);
        },
        resize: function(target, width)
        {
            var span = $(target);
            if ($.boxModel == true){
                span.width(width - (span.outerWidth() - span.width()) - 10);
            } else {
                span.width(width - 10);
            }
        }
    }
});



$(document).ready(function() {
    var linkData;
    $.ajax({
        url: '/admin/getLinks',
        type: 'POST',
        dataType: 'json',
        async:false,
        success: function(data){
            linkData = data;
        }
    });

    $('#linkName').combobox({
        data:linkData,
        id:'linkNameHiddenId',
        valueField:'id',
        textField:'name'
    });

    $('#linkName').combobox('setValue',null);

    function formatImgUrl(val,row){
        return '<img src="'+val+'" width="32px/">';
    }

    $(function () {
        $('#listLinkTable').edatagrid({
            title:'',
            iconCls:'icon-edit',
            width: 500,
            height: 'auto',
            nowrap: false,
            striped: true,
            border: true,
            collapsible:false,
            fit: true,
            url:'/admin/queryLinkResources',
            saveUrl: '/admin/saveLinkResource',
            updateUrl: '/admin/saveLinkResource',
            destroyUrl: '/admin/delLinkResource',
            //sortName: 'code',
            //sortOrder: 'desc',
            columns:[[
                {field:'id',title:'资源ID',width:60},
                {field:'name',title:'名称',width:100,editor:'text'},
                {field:'content',title:'文字内容',width:100,editor:'text'},
                {field:'imgUrl',title:'图片链接',width:100,editor:'uploadEditor',formatter:function(value){
                    return '<img src="'+value+'">';
                }},
                {field:'width',title:'图片宽度',width:100,editor:'numberbox'},
                {field:'height',title:'图片高度',width:100,editor:'numberbox'},
                {field:'linkId',title:'LINK',width:100,
                    formatter:function(value){
                        for(var i=0; i<linkData.length; i++){
                            if (linkData[i].id == value) return linkData[i].name;
                        }
                        return value;
                    },
                    editor:{
                        type:'combobox',
                        options:{
                            valueField:'id',
                            textField:'name',
                            data:linkData,
                            required:true
                        }
                    }
                },
                {field:'isActive',title:'激活',width:50,align:'center',
                    editor:{
                        type:'checkbox',
                        options:{
                            on: 'True',
                            off: 'False'
                        }
                    }
                }
            ]],
            remoteSort:false,
            idField:'id',
            singleSelect:false,
            pagination:true,
            rownumbers:true,
            frozenColumns:[[

            ]],
            queryParams: {
            	linkId: document.getElementsByName("linkName")[0].value
            }
        });

        var p = $('#listLinkResourceTable').datagrid('getPager');
        $(p).pagination({
            pageSize: 10,
            pageList: [5,10,15],
            beforePageText: '',
            afterPageText: '页    共 {pages}页',
            displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
        });

    });
    $("#uploadImg").click(function() {
        popUpload();
    });


});

var popUpload = function(){
    var files_selector = "/admin/files/selector";
    var returnVal = window.showModalDialog(files_selector, '', 'resizable:yes;scroll:auto;status:no;dialogWidth=1000px;dialogHeight=600px;center=yes;help=no');
    if (returnVal!=null) {
        var selected_files = eval(returnVal);
        for (var i = 0; i < selected_files.length; i++) {
            // var index = myDataTable_img_real.getRecordSet().getLength();
            // myDataTable_img_real.addRow(selected_files[i], index);
            var row = $('#listLinkTable').datagrid('getSelected');
            if (row){
                alert(selected_files[i].imageUrl);
                imgUrl = selected_files[i].imageUrl;
            }
        }
    }
};

var queryOrderData = function () {
   $('#listLinkTable').datagrid('load',{
	   linkId: document.getElementsByName("linkName")[0].value
    });
};






