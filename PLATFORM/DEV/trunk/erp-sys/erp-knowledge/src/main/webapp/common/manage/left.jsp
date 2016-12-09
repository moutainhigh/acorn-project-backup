<!--
http://union.yihaodian.com/passport/viewBankInfo.do
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<div class="easyui-accordion"  data-options="fit:true">
    <div title="知识管理" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
        <ul class="pitem">
            <li><a href="javascript:void(0)" onclick="open1('../../easyui/demo/tree/basic.html',this)">Basic Tree</a>
            </li>
            <li><a href="javascript:void(0)" onclick="open1('../../easyui/demo/tree/animation.html',this)">Animation
                Tree</a></li>
            <li><a href="javascript:void(0)" onclick="open1('../../easyui/demo/tree/checkbox.html',this)">CheckBox
                Tree</a></li>
            <li><a href="javascript:void(0)" onclick="open1('../../easyui/demo/tree/lines.html',this)">Tree Lines</a>
            </li>
            <li><a href="javascript:void(0)" onclick="open1('../../easyui/demo/tree/icons.html',this)">Tree Node
                Icons</a></li>
            <li><a href="javascript:void(0)" onclick="open1('../../easyui/demo/tree/actions.html',this)">Tree
                Actions</a></li>
            <li><a href="javascript:void(0)" onclick="open1('../../easyui/demo/tree/contextmenu.html',this)">Tree
                Context Menu</a></li>
            <li><a href="javascript:void(0)" onclick="open1('../../easyui/demo/tree/dnd.html',this)">Drag Drop Tree
                Nodes</a></li>
            <li><a href="javascript:void(0)" onclick="open1('../../easyui/demo/tree/editable.html',this)">Editable
                Tree</a></li>
            <li><a href="javascript:void(0)" onclick="open1('/tutorial/tree/tree2_demo.html',this)">Async Tree</a></li>
            <li><a href="javascript:void(0)" onclick="open1('../../easyui/demo/tree/lazyload.html',this)">Lazy Load Tree
                Nodes</a></li>
            <li><a href="javascript:void(0)" onclick="open1('/../../easyui/demo/tree/formatting.html',this)">Formatting
                Tree Nodes</a></li>
        </ul>
    </div>
    <div title="Help" data-options="iconCls:'icon-help'" style="padding:10px;">
        <p>The accordion allows you to provide multiple panels and display one at a time. Each panel has built-in support for expanding and collapsing. Clicking on a panel header to expand or collapse that panel body. The panel content can be loaded via ajax by specifying a 'href' property. Users can define a panel to be selected. If it is not specified, then the first panel is taken by default.</p>
    </div>
    <div title="TreeMenu" data-options="iconCls:'icon-search'" style="padding:10px;">
        <ul class="easyui-tree">
            <li>
                <span>Foods</span>
                <ul>
                    <li>
                        <span>Fruits</span>
                        <ul>
                            <li>apple</li>
                            <li>orange</li>
                        </ul>
                    </li>
                    <li>
                        <span>Vegetables</span>
                        <ul>
                            <li>tomato</li>
                            <li>carrot</li>
                            <li>cabbage</li>
                            <li>potato</li>
                            <li>lettuce</li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>