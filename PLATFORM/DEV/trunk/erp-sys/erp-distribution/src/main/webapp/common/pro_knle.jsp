<!--
http://union.yihaodian.com/passport/viewBankInfo.do
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<div class="c_content">
<input class="easyui-searchbox" data-options="prompt:'请输入内容',searcher:doSearch" style="width:300px"></input>
<script>
    function doSearch(value){
        alert('You input: ' + value);
    }
</script>
<div class="pro_info">
    <table>
        <tr>
            <td><label>商品名称</label> <span>康佳K58</span></td>
            <td><label>商品编码</label> <span>1020146600</span></td>
            <td><label>商品简码</label> <span>KJK58SJ</span></td>
            <td rowspan="2"><input id="u228" type="submit" class="_text_sketch" value="收藏"></td>
        </tr>
        <tr>
            <td><label>最后更新</label> <span>2010-11-04 17:34</span></td>
            <td><label>更新人</label> <span>12650</span></td>
            <td><label>浏览次数</label> <span>1458</span></td>
      </tr>
    </table>

</div>
<div class="pro_info">
    <table>
        <tr>
            <td colspan="3"><label>主  题</label> <span>新增工商银行在线支付功能，请查看业务规则</span></td>
            <td rowspan="2"><input  type="submit" class="_text_sketch" value="收藏"></td>
        </tr>
        <tr>
            <td><label>最后更新</label> <span>2010-11-04 17:34</span></td>
            <td><label>更新人</label> <span>12650</span></td>
            <td><label>浏览次数</label> <span>1458</span></td>
        </tr>
    </table>

</div>
<div class="easyui-tabs" fit="true"  data-options="tabWidth:100,tools:'#tab-tools'" style="width:700px;height:250px">
    <div title="产品知识" style="padding:10px">
        <p>A modem (modulator-demodulator) is a device that modulates an analog carrier signal to encode digital information, and also demodulates such a carrier signal to decode the transmitted information.</p>
    </div>
    <div title="产品话术" style="padding:10px">
        <p>In computing, an image scanner—often abbreviated to just scanner—is a device that optically scans images, printed text, handwriting, or an object, and converts it to a digital image.</p>
    </div>
</div>
<div id="tab-tools">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'" onclick="">预览</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" onclick="">保存</a>
</div>
</div>