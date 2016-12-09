<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<div class="clearfix"></div>
<h4>商品搜索</h4>
<input id="tbKeyword" class="ac" name="keyword" type="text" maxlength="255" />
<h4>商品名称</h4>
<input id="tbPlucode" name="plucode" type="hidden" />
<div class="ac pro_name"><input id="tbSpellname" class="readonly " name="spellname" readonly="true"  type="text" maxlength="255" /><a id="lnkAddFavorite" title="加入收藏"><span></span></a></div>
 <div id="des_tab" class="easyui-tabs mt10 clearfix" style="">
     <div title="更多产品信息" selected="true"  style="overflow:auto;padding:10px;">
         <h4>产品编号</h4>
         <p><input id="tbNccode" readonly="true" class="acc_readonly bc1" type="text" /></p>
         <h4>产品简码</h4>
         <p><input id="tbSpellcode" readonly="true" class="acc_readonly bc2" type="text"/></p>
         <h4>库存（可用）</h4>
         <p><input id="tbStockItem" readonly="true" class="acc_readonly bc3" type="text" style="width:110px" />&nbsp;<a id="lnkStockItem" class="lnkStockItem fr" href="#">明细<font>&gt;&gt;</font></a></p>
         <h4>原价</h4>
         <p><input id="tbListPrice" readonly="true" class="acc_readonly bc4" type="text"/></p>
         <h4>可售价格</h4>
         <p><input id="tbPriceScope" readonly="true" class="acc_readonly bc5" type="text"   value=""/></p>
         <h4>规格</h4>
         <p style="position: relative;">
             <select id="tbNcfree" name="ncfree" style="width:60px;" class="easyui-combobox"></select>
             <a class="toCart ml10" onclick="addShoppingCartProduct()" ><span>加入购物车</span></a>
         </p>
     </div>
    <div title="产品话术"   style="overflow:hidden;padding:10px;">           <span id="colBtn" class="colBtn"><span></span></span>
        <div id="lblDiscourseInfo" class="info">
        </div>
    </div>

</div>
<script type="text/javascript">
    $(function(){
        var infoWidth =0;
        var tabs = $('#des_tab').tabs().tabs({onSelect:function(name,index){

            switch(index){
                case 0:{ $('#des_tab .tabs-panels').css('border-radius','0 8px 8px 8px');
                    $(this).tabs('tabs')[0].panel('options').tab.css({border:'1px #97d0e3  solid',borderBottom:'none',borderRadius:'10px 10px 0 0'});
                    $(this).tabs('tabs')[1].panel('options').tab.css({border:'1px transparent solid',borderBottom:'none'});
                    break;}
                case 1:{ $('#des_tab .tabs-panels').css('border-radius','8px 0 8px 8px');
                    $(this).tabs('tabs')[1].panel('options').tab.css({border:'1px #97d0e3  solid',borderBottom:'none',borderRadius:'10px 10px 0 0'});
                    $(this).tabs('tabs')[0].panel('options').tab.css({border:'1px transparent solid',borderBottom:'none'});
//                     if($('#lblDiscourseInfo').height()>=253)$('#colBtn').show();
                    break;}
            }
        }}).tabs('tabs');
        for(var i=0; i<tabs.length; i++){
            tabs[i].panel('options').tab.width(100-i*20-2) ;
//            tabs[i].panel('options').tab.unbind().bind('mouseenter',{index:i},function(e){
//                $('#des_tab').tabs('select', e.data.index);
//            });
        }

        function toggleWidth(){
            if($('#des_tab').tabs('tabs')[1].width()<400) {
                $('#colBtn').addClass('ext');
                infoWidth =  $('#des_tab').tabs('tabs')[1].width();
            $('#des_tab').tabs('tabs')[1].width('auto').height($('#des_tab').tabs('tabs')[1].height());
            $('#lblDiscourseInfo').height($('#lblDiscourseInfo').height()) ;
            $('#des_tab').tabs('tabs')[1].animate({width:'400px'},'slow',function(){
            })
            }  else {
                $('#colBtn').removeClass('ext');
                $('#des_tab').tabs('tabs')[1].animate({width:infoWidth},'slow',function(){
                })
            }
        }
        $('#colBtn').click(function(){
            toggleWidth();
        });
        $('#lblDiscourseInfo').dblclick(function(){
            toggleWidth();
        })


    });
</script>
<%--<div class="easyui-accordion clearfix">--%>
    <%--<div title="更多产品信息" style="overflow:auto;padding:10px;">--%>
        <%--<h4>产品编号</h4>--%>
        <%--<p><input id="tbNccode" readonly="readonly" class="acc_readonly bc1" type="text" /></p>--%>
        <%--<h4>产品简码</h4>--%>
        <%--<p><input id="tbSpellcode" readonly="readonly" class="acc_readonly bc2" type="text"/></p>--%>
        <%--<h4>库存（可用）</h4>--%>
        <%--<p><input id="tbStockItem" readonly="readonly" class="acc_readonly bc3" type="text" style="width:110px" />&nbsp;<a id="lnkStockItem" class="lnkStockItem fr" href="#">明细<font>&gt;&gt;</font></a></p>--%>
        <%--<h4>原价</h4>--%>
        <%--<p><input id="tbListPrice" readonly="readonly" class="acc_readonly bc4" type="text"/></p>--%>
        <%--<h4>可售价格</h4>--%>
        <%--<p><input id="tbPriceScope" readonly="readonly" class="acc_readonly bc5" type="text"   value=""/></p>--%>
        <%--<h4>规格</h4>--%>
        <%--<p>--%>
            <%--<select id="tbNcfree" name="ncfree" style="width:60px;" class="easyui-combobox"></select>--%>
            <%--<a class="toCart ml10" onclick="addShoppingCartProduct()" ><span>加入购物车</span></a>--%>
        <%--</p>--%>
    <%--</div>--%>
<%--</div>--%>

<div class="pic_box"><img src="" style="width:170px;height:132px;"  />

</div>

<div id="dlgStockItem" >

        <table id="dgStockItem" title="商品库存明细" cellspacing="0" cellpadding="0" data-options=""></table>

</div>
