<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
request.setAttribute("rnd", new java.util.Random().nextInt());
%>
<script type=text/javascript src="${ctx}/static/js/datagrid-dnd.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/ueditor/ueditor.config.js">
</script>
<script type=text/javascript src="${ctx}/static/js/ajaxfileupload.js"></script>
<script type=text/javascript src="${ctx}/static/ueditor/ueditor.all.js"></script>
<script type=text/javascript src="${ctx}/static/js/knowledge/knowledgeArticle.js"></script>
<script type=text/javascript src="${ctx}/static/js/common-ui.js"></script>

<script type=text/javascript>
	var tag = "${knowledge.id}"
	var types = "${knowledge.type}"
	var categoryId = "${knowledge.categoryId}"
	var fstatus = "${knowledgeFavoriteArticle.status}"	
	var tempId = "";
	if ($('#searchTab').val()!=null||$('#searchTab').val()!=undefined) {
		var searchTab = $('#searchTab').tabs('getSelected'); 
		searchTab.panel('options').tab.attr("id",tag);
		tempId = searchTab.panel('options').tab[0].id;
	}

	function edit() {	
		if ($('#searchTab').val()!=null||$('#searchTab').val()!=undefined) {
			var searchTab = $('#searchTab').tabs('getSelected');  
			tempId = searchTab.panel('options').tab[0].id;
		}		
		$('#iframe1'+tempId).hide();
		$('#iframe2'+tempId).hide();
		$('#save'+tempId).show();
		$('#edit'+tempId).hide();
		$('#myEditor2'+tempId).show();
		$('#tags'+tempId).show();	
		var editor2 = new UE.ui.Editor();
		var temp = "myEditor2"+tempId;
		editor2.render('myEditor2'+tempId);
		if (types == '2') {
			$('#title').spinner({
			    required:true,
			    width:200,
			    increment:30
			});		
			$('#title').attr("disabled",false);
		}else {
			$('#myEditor'+tempId).show();
			var editor = new UE.ui.Editor();
			editor.render('myEditor'+tempId);		
		}
	} 
	if (types == '1') {
		if (tag=="") {
			$('#views1'+tempId).hide();
			$('#views2'+tempId).hide();
			$('#views3'+tempId).hide();
			$('#addfav'+tempId).hide();
			$('#removefav'+tempId).hide();			
			$('#edits'+tempId).show();
			$('#tags'+tempId).show();	
			edit();
		}else {
			$('#id_attachment_td'+tempId).show();
			count($('#id'+tempId).val());
			$('#iframe1'+tempId).show();
			$('#iframe2'+tempId).show();
			$('#views1'+tempId).show();
			$('#views2'+tempId).show();
			$('#views3'+tempId).show();
			$('#myEditor'+tempId).hide();
			$('#myEditor2'+tempId).hide();
			$('#edits').hide();
			if (fstatus == 1) {
				$('#addfav'+tempId).hide();
				$('#removefav'+tempId).show();	
			} else if (fstatus == 2) {
				$('#removefav'+tempId).hide();
				$('#addfav'+tempId).show();	
			}else {
				$('#removefav'+tempId).hide();
				$('#addfav'+tempId).show();	
			}			
		}
	}else {				
		$('#views1'+tempId).hide();
		$('#views2'+tempId).hide();
		$('#views3'+tempId).hide();
		$('#myEditor'+tempId).hide();
		$('#iframe2'+tempId).show();
		$('#qa').attr("title","问题与分析");
		if (tag=="") {
			$('#title'+tempId).spinner({
			    required:true,
			    width:200,
			    increment:30
			});	
			$('#title'+tempId).attr("disabled",false);
			$('#addfav'+tempId).hide();
			$('#removefav'+tempId).hide();	
			$('#tags'+tempId).show();	
			edit();
		}else {
			$('#id_attachment_td'+tempId).show();
			count($('#id'+tempId).val());					
			if (fstatus == 1) {
				$('#addfav'+tempId).hide();
				$('#removefav'+tempId).show();	
			} else if (fstatus == 2) {
				$('#removefav'+tempId).hide();
				$('#addfav'+tempId).show();	
			}else {
				$('#removefav'+tempId).hide();
				$('#addfav'+tempId).show();	
			}		
			$('#myEditor2'+tempId).hide();
		}
	}
    function showTags() {
    	var tempId = "";
		if ($('#searchTab').val()!=null||$('#searchTab').val()!=undefined) {
			var searchTab = $('#searchTab').tabs('getSelected');  
			tempId = searchTab.panel('options').tab[0].id;
		}
        var ids = $('#relationshipIds'+tempId).val();
        url = "${ctx}/knowledgeTag/getLists?ids="+ids; 
        $('#tagTable'+tempId).datagrid('options').url= url;
        $('#tagTable'+tempId).datagrid('reload');
        $('#tagTable'+tempId).datagrid('resize');
        $('#knowledgeTable').datagrid('resize');
        $('#tabsWindow'+tempId).window('open');
    }       
</script>
<LINK rel=stylesheet href="${ctx}/static/ueditor/themes/default/css/ueditor.css"/>
<div class="easyui-panel" fit="true" border="false" style="padding:10px" >
    <div  class="easyui-layout" fit="true"  >
    
            <sec:authorize access="hasAnyRole('INBOUND_MANAGER,OUTBOUND_MANAGER,COMPLAIN_MANAGER')">
            <div data-options="region:'south',split:false,border:false" style="height:60px;">
                <div class="bottomWrap">
                <a href="#"   id="edit${knowledge.id}" class="easyui-linkbutton" data-options="" style=""
                   onclick="edit()">编辑</a>
                <a href="#"  id="save${knowledge.id}" class="easyui-linkbutton" data-options="" style="display: none" onclick="saveArticle()">保存</a>
                </div>
            </div>            
        </sec:authorize> 
        <div region="center"  split="true" border="false" title="" style="padding-right:10px"  >


        <div class="easyui-layout" fit="true" >

            <div   data-options="region:'north',border:false">
                <p class="pageTitle nui-btn"><span class="title-content">${knowledge.title}${knowledge.productName}</span>
                    <span class="nui-btn-text" id="nui-component2-text" onclick="toHome()"><a class="icon-back"></a>&nbsp;返回</span>
                </p>
                <form name="myform" method="post" id="myform${knowledge.id}">
                <div class="pro_info">
                    <p>&nbsp;</p>
                    <table width="98%">
                        <tr>
                            <c:if test="${knowledge.productName!=''}">
                                <td id="views1${knowledge.id}"><div><label>商品名称</label><div class="t_con">${knowledge.productName}</div></div></td>
                                <td id="views2${knowledge.id}"><label>商品编号</label><div class="t_con">${knowledge.productCode}</div></td>
                                <td id="views3${knowledge.id}"><label>商品简码</label><div class="t_con">${knowledge.shortPinyin}</div></td>
                              
                            </c:if>
                            <input type="hidden" id="categoryId${knowledge.id}" value="${knowledge.categoryId}"/>
                            <input type="hidden" id="type${knowledge.id}" value="${knowledge.type}"/>
                            <input type="hidden" id="id${knowledge.id}" value="${knowledge.id}"/>
                            <input type="hidden" id="productCode${knowledge.id}" value="${knowledge.productCode}"/>
                            <input type="hidden" id="productName${knowledge.id}" value="${knowledge.productName}"/>
                            <input type="hidden" id="shortPinyin${knowledge.id}"  value="${knowledge.shortPinyin}"/>
                            <input type="hidden" id="relationshipIds${knowledge.id}"  value="${knowledge.relationshipIds}"/>
                            <input type="hidden" id="isExit${knowledge.id}"  />
                             <input type="hidden" id="flag${knowledge.id}"  />
                            <td id="edits${knowledge.id}" style="display: none">
                                <label class="newpro">产品</label>
                                <input class="easyui-combogrid" style="width:150px;"
                                       id="product${knowledge.id}"
                                       name="product"
                                       width="200"
                                       value="${knowledge.productName}"
                                       data-options="
								url:'${ctx }/dict/getProduct',
								idField:'prodid',
								textField:'prodname',
								panelWidth:600,
								panelHeight:340,
								pagination:true,
								queryParams: {
									productName: '${knowledge.productName}',
									productId: '${knowledge.productCode}'
								},
								rownumbers:true,
								required:true,
								editable:false,
								toolbar:'#ttt',
								fitColumns:true,
								scrollbarSize:-1,
								columns:[[
									{field:'prodid',title:'产品ID',width:200},
									{field:'prodname',title:'产品名称',width:200},
									{field:'scode',title:'产品简码',width:200}
								]],
								onLoadSuccess:function(data){
									$('#searchText1').val('${knowledge.productName}')
								},onChange:function(data){
									$.post('${ctx }/knowledge/queryByCode', {
											'productCode' : data
										},function(datas) {
										if (datas!=null&&datas!='') {
											if (datas.flag=='1'&&'${knowledge.productCode}'!=data) {
													alerts('该产品知识点已存在');
													$('#flag').attr('value','true') ;
												}
										}
										},'json');
								}
						"/>	
							<input  type="hidden" id="relationshipIdss${knowledge.id}"/>
                            </td>                     
                            <c:if test="${knowledge.type=='2'}">
                                <td><span>标题：</span><input  id="title" value="${knowledge.title}" type="text" disabled="disabled"/></td>
                            </c:if>                                                    
                            <td></td>
                            <td></td>
                                <td style="vertical-align: middle" rowspan="2"><input id="addfav${knowledge.id}" type="button" class="_text_sketch" onclick="addFavorite()" value="收藏">
                               <input id="removefav${knowledge.id}" type="button" class="_text_sketch" onclick="removeFavorite()" style="display: none" value="取消收藏">
                             </td>

                        </tr>
                        <tr>
                            <c:if test="${knowledge.id != null }">
                                <c:choose>
                                    <c:when test="${knowledge.updateUser!=null}">
                                        <td><label>最后更新</label> <div class="t_con">${knowledge.updateDate}</div></td>
                                        <td><label>更新人</label> <div class="t_con">${knowledge.updateUser}</div></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><label>最后更新</label> <span>${knowledge.createDate}</span></td>
                                        <td><label>更新人</label> <span>${knowledge.createUser}</span></td>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <sec:authorize access="hasAnyRole('INBOUND_MANAGER,OUTBOUND_MANAGER,COMPLAIN_MANAGER')">
                            <td id="id_attachment_td${knowledge.id}" style="display: none">
                                <a class="attachment"  href="javascript: void(0)" onclick="showDownloads()">
                                	<span class="attachment-button fl"></span>附件
                                	<span id="attachmentCount${knowledge.id}" class="attachment-tip">0</span>
                                </a>
                            </td>
                            </sec:authorize>
                            <!--<td><label>浏览次数</label> <span>1458</span></td>-->
                        </tr>
                    </table>    <p>&nbsp;</p>
                </div>
    </form>
</div>
            <div  data-options="region:'center',border:false">
                <div class="easyui-tabs" fit="true"   data-options="tabWidth:100,tools:'#tab-tools${knowledge.id}'" style="width:700px;height:250px" id="maintabs${knowledge.id}">
                    <c:if test="${knowledge.type=='1' }">
                        <div title="产品知识" >                    
                            
                              <iframe id="iframe1${knowledge.id}" name="iframe1${knowledge.id}" src="${knowledge.path }"   draggable="false" style="margin-bottom: -4px" frameborder="no"  width="100%" height="100%" >
                        
                        	  </iframe>                        	  
                              <textarea name="myEditor${knowledge.id}" id="myEditor${knowledge.id}">${knowledge.content}</textarea>
                        </div>
                    </c:if>
                    <div title="产品话术" id ="qa${knowledge.id}">
                        	<iframe id="iframe2${knowledge.id}" name="iframe1${knowledge.id}"  src="${knowledge.discoursePath }"  draggable="false" style="margin-bottom: -4px" frameborder="no" border="0"  width="100%" height="100%">
                        	</iframe>
                            <textarea name="myEditor2${knowledge.id}" id="myEditor2${knowledge.id}">${knowledge.discouse}</textarea>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <div style="width:220px;" data-options="region:'east',split:true,border:false" >
            <jsp:include page="/common/right.jsp"/>
        </div>
    </div>
        </div>
<!--  
		<div id="tab-tools${knowledge.id}">
			<sec:authorize access="hasAnyRole('INBOUND_MANAGER,OUTBOUND_MANAGER,COMPLAIN_MANAGER')">
				    <a id="edit${knowledge.id}" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="edit()">编辑</a>
				    <a id="save${knowledge.id}" style="display: none"href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'" onclick="saveArticle()">保存</a>
			</sec:authorize>  
		</div>
 -->		
		
	</div>
	<div id="ttt${knowledge.id}">
		<input type="text" id="searchText1"/><a href="#" id="queryProductBtn" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'"></a>
	</div>
	<div id="attachmentsWindow${knowledge.id}" class="easyui-window" title="查看附件" style="width:600px;height:280px;"
	   	data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true">
	   	<table id="attachmentsTable${knowledge.id}" class="easyui-datagrid"></table>
		<input id="uploadAttachment${knowledge.id}" style="display: none;" type="file" name="file" onchange="javascript:upload()"/>
		<div id="removeConfirm${knowledge.id}" class="easyui-dialog" title="删除确认" style="width:200px;height:60px;"
        	data-options="iconCls:'icon-tip',resizable:false,modal:true,closed:true">
        </div>
	</div>
<div id="tabsWindow${knowledge.id}" class="easyui-window" title="查看标签" style="width:1000px;height:510px;padding:10px;"
     data-options="modal:true,shadow:false,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true">
    <div class="easyui-layout" fit=true>
        <%--<div data-options="region:'north'" style="height:50px"></div>--%>
        <%--<div data-options="region:'south',split:true" style="height:50px;"></div>--%>
        <div data-options="region:'east',border:false" style="width:440px;">
            <table id="knowledgeTable" class="easyui-datagrid" cellspacing="0" cellpadding="0" data-options="
            title:'所有标签',
            width : '100%',
            height : 430,
            nowrap : false,
            striped : true,
            border : true,
            fit:true,
            fitColumns:true,
            selectOnCheck:true,
            checkOnSelect:true,
            collapsible : false,
            scrollbarSize:0,
            url : '${ctx}/knowledgeTag/list',
            queryParams:{
                name:'${name}'
             },
          idField : 'id',
          sortName : 'id',
          pagination:true,
          sortOrder : 'desc',
          toolbar:'#tagButtons',
          remoteSort : false,
          rownumbers : true">
                <thead>
                <tr>
                    <th data-options="field:'ck',checkbox:true"></th>
                    <th data-options="field:'id',width:60">ID</th>
                    <th data-options="field:'name',width:150,align:'right'">标签名称</th>
                    <th data-options="field:'createDate',width:180,align:'right',formatter : function(val, rec) {
      var _sdate = new Date (rec.createDate);
      return _sdate.format('yyyy-MM-dd hh:mm:ss');
      }">创建时间
                    </th>
                    <th data-options="field:'createUser',width:80">创建工号</th>
                    <th data-options="field:'updateDate',width:260,align:'center'">最后修改时间</th>
                    <th data-options="field:'updateUser',width:80,align:'center'">修改工号</th>
                </tr>
                </thead>
            </table>
        </div>
        <div data-options="region:'west',border:false" style="width:440px;">

            <table id="tagTable${knowledge.id}" class="easyui-datagrid" data-options=" title : '所属标签',
            width : '100%',
            height : 430,
            nowrap : false,
            striped : true,
            border : true,
            fit:true,
            fitColumns:true,
            selectOnCheck:true,
            checkOnSelect:true,
            collapsible : false,
            scrollbarSize:0,
            url : '${ctx}/knowledgeTag/getLists',       
            queryParams:{
          },
          idField : 'id',
          sortName : 'id',
          sortOrder : 'desc',
          toolbar:'#tagButtons',
          remoteSort : false,
          rownumbers : true
          ">
                <thead>
                <tr>
                    <th data-options="field:'ck',checkbox:true"></th>
                    <th data-options="field:'id',width:60">ID</th>
                    <th data-options="field:'name',width:150,align:'right'">标签名称</th>
                    <th data-options="field:'createDate',width:180,align:'right',formatter : function(val, rec) {
      var _sdate = new Date (rec.createDate);
      return _sdate.format('yyyy-MM-dd hh:mm:ss');
      }">创建时间
                    </th>
                    <th data-options="field:'createUser',width:80">创建工号</th>
                    <th data-options="field:'updateDate',width:260,align:'center'">最后修改时间</th>
                    <th data-options="field:'updateUser',width:80,align:'center'">修改工号</th>
                </tr>
                </thead>
            </table>
        </div>
        <div data-options="region:'center',border:false" style="width:100px;padding:180px 10px 0;text-align: center">
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" style="margin-bottom: 20px"
               onclick="saveTags()"></a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" onclick="removeTag()"></a>
        </div>      
    </div>
</div>
