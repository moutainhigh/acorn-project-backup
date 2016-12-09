<!--
http://union.yihaodian.com/passport/viewBankInfo.do
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

    <ul class="easyui-tree" data-options="animate:true,dnd:true,
    formatter:function(node){
                    var s = node.text;
                    if (node.children){
                        s += '&nbsp;<span style=\'color:blue\'>(' + node.children.length + ')</span>';
                    }
                    return s;
                },onClick: function(node){
                $(this).tree('beginEdit',node.target);
            }">
        <li>
            <span>My Documents</span>
            <ul>
                <li data-options="state:'closed'">
                    <span>Photos</span>
                    <ul>
                        <li>
                            <span>Friend</span>
                        </li>
                        <li>
                            <span>Wife</span>
                        </li>
                        <li>
                            <span>Company</span>
                        </li>
                    </ul>
                </li>
                <li>
                    <span>Program Files</span>
                    <ul>
                        <li>Intel</li>
                        <li>Java</li>
                        <li>Microsoft Office</li>
                        <li>Games</li>
                    </ul>
                </li>
                <li>index.html</li>
                <li>about.html</li>
                <li>welcome.html</li>
            </ul>
        </li>
    </ul>
