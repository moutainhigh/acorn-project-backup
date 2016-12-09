/*
 * Copyright (c) 2012 Acorn International.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http:www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

YAHOO.namespace("pingmob");

YAHOO.util.Event.onDOMReady(function () {
    YAHOO.pingmob.promotionEdit = function () {

        // Put a LogReader on your page
        //this.myLogReader = new YAHOO.widget.LogReader();

        var Dom = YAHOO.util.Dom,
            Event = YAHOO.util.Event;

        var myConfig = {
            height:'600px',
            width:'100%',
            animate:true,
            dompath:true,
            focusAtStart:false
        };

        var state = 'off';
        YAHOO.log('Set state to off..', 'info', 'promotionEdit');

        var getTableDataSourceArray = function(datatable){
            var rs = datatable.getRecordSet(),
                len = rs.getLength(),
                data = [];
            for (var index=0; index < len; index++) {
                data.push(rs.getRecord(index).getData());
            }
            return YAHOO.lang.JSON.stringify(data);
        };

        var getTagArray = function(){
            var existTagIds = $('[name="tagCheckBox"]').map(
                function(){ return $(this).val(); }
            ).toArray();
            return YAHOO.lang.JSON.stringify(existTagIds);
        };

        var getRuleOptionArray = function(){
            var formId = "form" + $('#rule').val()
            var $inputs = $('#frmRuleOption :input');
             var values = {};
            $inputs.each(function() {
                if (this.id.length >0){
                    if (this.className && this.className.indexOf('easyui-date')>=0){
                        values[this.id] = $(this).datebox('getValue');
                    }else if (this.type == 'checkbox'){
                        values[this.id] = $('#'+this.id).is(':checked');
                    }else{
                        values[this.id] = $(this).val();
                    }
                }
            });
            return YAHOO.lang.JSON.stringify(values);
        }

        var parsepromotionContent = function parsepromotionContent() {
            Dom.get("options_json").value = getTableDataSourceArray(myDataTable_opt);
             return true;
        };

        //YAHOO.util.Event.addListener("promotionForm","submit",parsepromotionContent);

        var returnpromotionList = function (p_oEvent) {
            window.location.href = "/admin/promotions";
        };

        var refreshpromotionEdit = function (p_oEvent) {
            window.location.reload();
        };

        var updatePromotion = function (p_oEvent) {
            var isPValid =  document.forms["promotionForm"].onsubmit();
            var isRValid = document.forms["frmRuleOption"].onsubmit();
            var isStartDateValid = document.getElementsByName('startDate')[0].value;
            if (!isStartDateValid){
                alert(请输入开始时间);
            }
            var isEndDateValid =  document.getElementsByName('endDate')[0].value;
            if (!isEndDateValid){
                alert(请输入结束时间);
            }
            if (isPValid && isRValid && isStartDateValid && isEndDateValid){
                document.getElementById("products_json").value = getTableDataSourceArray(myDataTable_product);
                document.getElementById("coupons_json").value = getTableDataSourceArray(myDataTable_coupon);
                document.getElementById("tags_json").value =  getTagArray();
                document.getElementById("ruleOptions_json").value =  getRuleOptionArray();
                document.forms["promotionForm"].submit();
            }
        };

        var deletepromotion = function (p_oEvent) {
            debugger;
            var promotionId = document.forms["promotionForm"]["promotionid"].value;
            if (promotionId != "") {
                YAHOO.util.Connect.asyncRequest('GET', '/admin/promotions/delete/' + promotionId,
                    {success:function (o) {
                       // alert("Deleted promotion successfully.");
                        window.location.href = "/admin/promotions";
                    }, failure:function (o) {
                        alert(o.statusText);
                    }, scope:this
                    });
            }
        };

        //button.
        var returnpromotionListBtn = new YAHOO.widget.Button("returnpromotionListBtn", { onclick:{ fn:returnpromotionList } });
        var refreshpromotionEditBtn = new YAHOO.widget.Button("refreshpromotionEditBtn", { onclick:{ fn:refreshpromotionEdit } });
        var save_promotion_btn = new YAHOO.widget.Button("save_promotion_btn", { onclick:{ fn:updatePromotion } });
        debugger;
        var delete_promotion_btn = new YAHOO.widget.Button("delete_promotion_btn", { onclick:{ fn:deletepromotion } });

        //promotion options table.
        var myColumnDefs_opt = [
            {key:"Select", label:messages["select"], formatter:"checkbox", className:"table-checkbox"},
            {key:"optionKey", label:messages["promotion.option.key"], sortable:true, editor: new YAHOO.widget.TextboxCellEditor({disableBtns:true})},
            {key:"optionValue", label:messages["promotion.option.value"], sortable:true, editor: new YAHOO.widget.TextboxCellEditor({disableBtns:true})}
        ];

        var myDataSource_opt = new YAHOO.util.DataSource(YAHOO.util.Dom.get("promo_opp_table"));
        myDataSource_opt.responseType = YAHOO.util.DataSource.TYPE_HTMLTABLE;
        myDataSource_opt.responseSchema = {
            fields:[
                {key:"optionId"},
                {key:"optionKey"},
                {key:"optionValue"}
            ]
        };

        var myDataTable_opt = new YAHOO.widget.DataTable("promo_opp_table_div", myColumnDefs_opt, myDataSource_opt,
            { sortedBy:{key:"optionKey", dir:"asc"},
                MSG_EMPTY:messages["js.no.records.found"]
            }
        );
        // Set up editing flow
        var highlightEditableCell = function(oArgs) {
            var elCell = oArgs.target;
            if(YAHOO.util.Dom.hasClass(elCell, "yui-dt-editable")) {
                this.highlightCell(elCell);
            }
        };
        myDataTable_opt.subscribe("cellMouseoverEvent", highlightEditableCell);
        myDataTable_opt.subscribe("cellMouseoutEvent", myDataTable_opt.onEventUnhighlightCell);
        myDataTable_opt.subscribe("cellClickEvent", myDataTable_opt.onEventShowCellEditor);

        var deleteProdAttrThread = function (p_oEvent) {
            //call the delete action.
            var oRows = myDataTable_opt.getSelectedRows();
            for (var i = 0; i < oRows.length; i++) {
                myDataTable_opt.deleteRow(oRows[i]);
            }
        };

        myDataTable_opt.subscribe('checkboxClickEvent', function (oArgs) {
            var elCheckbox = oArgs.target;
            var newValue = elCheckbox.checked;
            var record = this.getRecord(elCheckbox);
            var column = this.getColumn(elCheckbox);
            record.setData(column.key, newValue);

            var allChecked = true;
            var oRS = myDataTable_opt.getRecordSet();
            var start_index = 0;
            for (var i = start_index; i < oRS.getLength(); i++) {
                var oRec = oRS.getRecord(i);
                if (!oRec.getData('Select')) {
                    allChecked = false;
                    break;
                }
            }
            if (newValue) {
                this.selectRow(record);
            } else {
                this.unselectRow(record);
            }
            if (myDataTable_opt.getSelectedRows().length > 0) {
                deleteProdAttrThread_btn.set("disabled", false);
            } else {
                deleteProdAttrThread_btn.set("disabled", true);
            }
            return false;
        });

        var addProdAttrThread=function (p_oEvent) {
            var index = myDataTable_opt.getRecordSet().getLength();
            myDataTable_opt.addRow({optionId:null, optionKey:null, optionValue:null}, index);
        };

        var savePromotionOption=function () {
            document.getElementById("options_json").value = getTableDataSourceArray(myDataTable_opt);
            return true;
        };
        var deleteProdAttrThread_btn = new YAHOO.widget.Button("deleteProdAttrThread", { onclick:{ fn:deleteProdAttrThread } });

        var addProdAttrThread_btn = new YAHOO.widget.Button("add-attr-edit", { onclick:{ fn:addProdAttrThread } });

         //products table.
        var myColumnDefs_product = [
            {key:"Select", label:messages["select"], formatter:"checkbox", className:"table-checkbox"},
            {key:"productName", label:messages["product.name"], sortable:true},
            {key:"productId", label:"", hidden:true},
            {key:"productSellPrice", sortable:true, label:messages["product.price.sell"]},
            {key:"productBrand", sortable:true, label:messages["product.brand"]},
            {key:"supplier", sortable:true,  label:messages["product.supplier"]}
        ];

        var myDataSource_product = new YAHOO.util.DataSource(YAHOO.util.Dom.get("prod_product_table"));
        myDataSource_product.responseType = YAHOO.util.DataSource.TYPE_HTMLTABLE;
        myDataSource_product.responseSchema = {
            fields:[
                {key:"productId"},
                {key:"productName"},
                {key:"productSellPrice"},
                {key:"productBrand"},
                {key:"supplier"}
            ]
        };

        var myDataTable_product = new YAHOO.widget.DataTable("promo_product_table_div", myColumnDefs_product, myDataSource_product,
            { sortedBy:{key:"productName", dir:"asc"},
                MSG_EMPTY:messages["js.no.records.found"]
            }
        );

        var deletePromoproductThread = function (p_oEvent) {
            //call the delete action.
            var oRows = myDataTable_product.getSelectedRows();
            for (var i = 0; i < oRows.length; i++) {
                myDataTable_product.deleteRow(oRows[i]);
            }
        };

        myDataTable_product.subscribe('checkboxClickEvent', function (oArgs) {
            var currentPage = History.getCurrentState("page");
            var elCheckbox = oArgs.target;
            var newValue = elCheckbox.checked;
            var record = this.getRecord(elCheckbox);
            var column = this.getColumn(elCheckbox);
            record.setData(column.key, newValue);

            var allChecked = true;
            var oRS = myDataTable_product.getRecordSet();
            var start_index = (currentPage - 1) * number_per_page_default;
            for (var i = start_index; i < oRS.getLength(); i++) {
                var oRec = oRS.getRecord(i);
                if (!oRec.getData('Select')) {
                    allChecked = false;
                    break;
                }
            }
            if (newValue) {
                this.selectRow(record);
            } else {
                this.unselectRow(record);
            }
            if (myDataTable_product.getSelectedRows().length > 0) {
                deletePromoproductThread_btn.set("disabled", false);
            } else {
                deletePromoproductThread_btn.set("disabled", true);
            }
            return false;
        });

        var deletePromoproductThread_btn = new YAHOO.widget.Button("deletePromoproductThread", { onclick:{ fn:deletePromoproductThread } });

        //product list table.
        var number_per_page_default = 10;

        var myPaginator = new YAHOO.widget.Paginator({
            containers:['products-thread-paging'],
            rowsPerPage:number_per_page_default,
            previousPageLinkLabel:"&lsaquo;&lsaquo; Pre",
            nextPageLinkLabel:"Next &rsaquo;&rsaquo;",
            previousPageLinkClass:"yui-pg-previous gc-inbox-prev",
            nextPageLinkClass:"yui-pg-next gc-inbox-next"//,
            //template           : "<strong>{CurrentPageReport}</strong> {PreviousPageLink} {NextPageLink}",
            //pageReportTemplate : "{startIndex}-{endIndex} of {totalRecords}"
        });
        var myTableConfig = {
            paginator:myPaginator,
            MSG_EMPTY:messages["js.no.records.found"],
            dynamicData:true,
            initialLoad:false
        };

        var myColumnDefs = [
            {key:"Select", label:"", formatter:"checkbox", className:"table-checkbox"},
            {key:"productName", label:messages["product.name"]},
            {key:"productBrand", label:messages["product.brand"]},
            {key:"supplier", label:messages["product.supplier"]},
            {key:"productCategory", label:messages["product.category"]},
            {key:"productSubCategory", label:messages["product.category.second"]},
            {key:"productSellPrice", label:messages["product.price.sell"]}

        ];
        var productsDataSource = new YAHOO.util.DataSource('/products/json?');
        productsDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        productsDataSource.responseSchema = {
            resultsList:'records',
            metaFields:{
                totalRecords:'totalRecords',
                paginationRecordOffset:"startIndex",
                paginationRowsPerPage:"pageSize"
            }
        };
        var productsDataTable = new YAHOO.widget.DataTable("products-thread-list", myColumnDefs, productsDataSource, myTableConfig);

        // Show loading message while page is being rendered
        productsDataTable.showTableMessage(productsDataTable.get("MSG_LOADING"), YAHOO.widget.DataTable.CLASS_LOADING);

        // Integrate with Browser History Manager
        var History = YAHOO.util.History;

        // Define a custom function to route pagination through the Browser History Manager
        var handlePagination = function (state) {
            // The next state will reflect the new pagination values
            var newState = state.page;

            // Pass the state along to the Browser History Manager
            History.navigate("page", newState + "");  //navigate method only accept String parameter.
        };

        // First we must unhook the built-in mechanism...
        myPaginator.unsubscribe("changeRequest", productsDataTable.onPaginatorChangeRequest);
        // ...then we hook up our custom function
        myPaginator.subscribe("changeRequest", handlePagination, productsDataTable, true);

        // Update totalRecords on the fly with values from server
        productsDataTable.doBeforeLoadData = function (oRequest, oResponse, oPayload) {
            var meta = oResponse.meta;
            oPayload.totalRecords = meta.totalRecords || oPayload.totalRecords;
            oPayload.pagination = {
                rowsPerPage:meta.paginationRowsPerPage || number_per_page_default,
                recordOffset:meta.paginationRecordOffset || 0
            };
            return true;
        };

        var generateRequest = function (page) {
            /*
             var productName = encodeURIComponent(Dom.get("productName").value());
             var productSellPrice = encodeURIComponent(Dom.get("productSellPrice").value());
             var skuCode = encodeURIComponent(Dom.get("skuCode").value());
             */
            page = page || 1;
            if(parseInt(page)!=page)
            {
                page = 1;
            }
            var start_index = (page - 1) * number_per_page_default;
            //return "start_index=" + start_index + "&num_per_page=" + number_per_page_default + "&name="+productName+ "&price="+productSellPrice+ "&sku="+skuCode;
            return "start_index=" + start_index + "&num_per_page=" + number_per_page_default;
        };

        // Called by Browser History Manager to trigger a new state
        var handleHistoryNavigation = function (page) {
            //show the "loading" message.
            productsDataTable.getTbodyEl().style.display = "none";
            productsDataTable.showTableMessage(messages["js.loading"]);

            // Sends a new request to the DataSource
            productsDataSource.sendRequest(generateRequest(page), {
                success:function () {
                    productsDataTable.getTbodyEl().style.display = "";
                    productsDataTable.hideTableMessage();
                    productsDataTable.onDataReturnReplaceRows.apply(this, arguments);
                },
                failure:function () {
                    productsDataTable.getTbodyEl().style.display = "";
                    productsDataTable.hideTableMessage(YAHOO.widget.DataTable.MSG_ERROR);
                    productsDataTable.onDataReturnReplaceRows.apply(this, arguments);
                },
                scope:productsDataTable,
                argument:{} // Pass in container for population at runtime via doBeforeLoadData
            });
        };

        // Calculate the first request
        var initialRequest = History.getBookmarkedState("page") || "1";

        // Register the module
        History.register("page", initialRequest, handleHistoryNavigation);

        // Render the first view
        History.onReady(function () {
            renderPage();
        });

        var renderPage = function () {
            var currentState = History.getCurrentState("page");
            handleHistoryNavigation(currentState);
        };

        // Initialize the Browser History Manager.
        YAHOO.util.History.initialize("yui-history-field", "yui-history-iframe");


        //event.

        var refreshProductThread = function (p_oEvent) {
            renderPage();
        };

        //button.
        var query_btn = new YAHOO.widget.Button("query_products_btn", {label:messages["button.search"],onclick:{ fn:handleHistoryNavigation } });

        productsDataTable.subscribe('checkboxClickEvent', function (oArgs) {
            var currentPage = History.getCurrentState("page");
            var elCheckbox = oArgs.target;
            var newValue = elCheckbox.checked;
            var record = this.getRecord(elCheckbox);
            var column = this.getColumn(elCheckbox);
            record.setData(column.key, newValue);

            var allChecked = true;
            var oRS = productsDataTable.getRecordSet();
            var start_index = (currentPage - 1) * number_per_page_default;
            for (var i = start_index; i < oRS.getLength(); i++) {
                var oRec = oRS.getRecord(i);
                if (!oRec.getData('Select')) {
                    allChecked = false;
                    break;
                }
            }
            if (newValue) {
                this.selectRow(record);
            } else {
                this.unselectRow(record);
            }
            return false;
        });

        var handleSubmit = function () {
            //call the delete action.
            var currentPage = History.getCurrentState("page");
            var oRows = productsDataTable.getSelectedRows();
            var oRS = productsDataTable.getRecordSet();
            if (oRows.length < 1) {
                alert(messages["product.validator.sku.choose"]);
            } else {
                var start_index = (currentPage - 1) * number_per_page_default;
                for (var i = start_index; i < oRS.getLength(); i++) {
                    var oRec = oRS.getRecord(i);
                    if (oRec.getData("Select")) {
                        var productId = oRec.getData("productId");
                        var productName = oRec.getData("productName");
                        var productSellPrice = oRec.getData("productSellPrice");
                        var productBrand = oRec.getData("productBrand");
                        var supplier = oRec.getData("supplier");
                        var index = myDataTable_product.getRecordSet().getLength();
                        myDataTable_product.addRow({productId:productId, productName:productName, productSellPrice:productSellPrice, productBrand:productBrand, supplier:supplier}, index);
                    }
                }
                YAHOO.pingmob.container.panel_product.hide();
            }
        };

        var handleCancel = function() {
            this.cancel();
        };

        var panel_product = new YAHOO.widget.Dialog("panel_product",
            {   width:"700px",
                visible:false,
                constraintoviewport:true,
                fixedcenter:true,
                underlay:"shadow",
                buttons : [
                    { text:messages["submit"], handler:handleSubmit, isDefault:true },
                    { text:messages["cancel"], handler:handleCancel }
                ]
            });
        panel_product.render();

        var show_product_panel = function(){
            Dom.setStyle("panel_product", "display", "");
            panel_product.show();
        };

        var addProdThread = function () {
            //show modal dialog.
            var products_selector = "/admin/products/selector";
            var returnVal = window.showModalDialog(products_selector, '', 'resizable:yes;scroll:auto;status:no;dialogWidth=1000px;dialogHeight=600px;center=yes;help=no');
            var oRS = myDataTable_product.getRecordSet();
            var record_length = oRS.getLength();
            if (returnVal!=null) {
                var selected_products = YAHOO.lang.JSON.parse(returnVal);
                for (var i = 0; i < selected_products.length; i++) {
                    var product =  selected_products[i];
                    //product["order"] = record_length+i;
                    //myDataTable_gift.addRow(product, index);
                    var productId = product.productId;
                    var productName = product.productName;
                    var productSellPrice = product.productSellPrice;
                    var productBrand = product.productBrand;
                    var supplier = product.supplier;
                    var index = myDataTable_product.getRecordSet().getLength();
                    myDataTable_product.addRow({productId:productId, productName:productName, productSellPrice:productSellPrice, productBrand:productBrand, supplier:supplier}, index);

                }
            }
        };

        var add_product_btn = new YAHOO.widget.Button("add-product-edit");
        var addProdThread_btn = new YAHOO.widget.Button("add-product-edit", { onclick:{ fn:addProdThread } });
        //YAHOO.util.Event.addListener("add-product-edit", "click", show_product_panel, panel_product, true);
        //coupons table.

        var CouponHistory = YAHOO.util.History;
        var myColumnDefs_coupon = [
            {key:"Select", label:messages["select"], formatter:"checkbox", className:"table-checkbox"},
            {key:"couponCode", label:messages["coupon.code"]},
            {key:"couponId", label:"", hidden:true},
            {key:"name", label:messages["coupon.name"]},
            {key:"description", label:messages["coupon.description"]},
            {key:"maxUse", label:messages["coupon.maxUse"]},
            {key:"timesUsed",  label:messages["coupon.timesUsed"]}
        ];

        var myDataSource_coupon = new YAHOO.util.DataSource(YAHOO.util.Dom.get("prod_coupon_table"));
        myDataSource_coupon.responseType = YAHOO.util.DataSource.TYPE_HTMLTABLE;
        myDataSource_coupon.responseSchema = {
            fields:[
                {key:"couponId"},
                {key:"couponCode"},
                {key:"name"},
                {key:"description"},
                {key:"maxUse"},
                {key:"timesUsed"}
            ]
        };

        var myDataTable_coupon = new YAHOO.widget.DataTable("promo_coupon_table_div", myColumnDefs_coupon, myDataSource_coupon,
            {
                MSG_EMPTY:messages["js.no.records.found"]
            }
        );

        var deletePromoCouponThread = function (p_oEvent) {
            //call the delete action.
            var oRows = myDataTable_coupon.getSelectedRows();
            for (var i = 0; i < oRows.length; i++) {
                myDataTable_coupon.deleteRow(oRows[i]);
            }
        };

        myDataTable_coupon.subscribe('checkboxClickEvent', function (oArgs) {
            var currentPage = History.getCurrentState("page");
            var elCheckbox = oArgs.target;
            var newValue = elCheckbox.checked;
            var record = this.getRecord(elCheckbox);
            var column = this.getColumn(elCheckbox);
            record.setData(column.key, newValue);

            var allChecked = true;
            var oRS = myDataTable_coupon.getRecordSet();
            var start_index = (currentPage - 1) * number_per_page_default;
            for (var i = start_index; i < oRS.getLength(); i++) {
                var oRec = oRS.getRecord(i);
                if (!oRec.getData('Select')) {
                    allChecked = false;
                    break;
                }
            }
            if (newValue) {
                this.selectRow(record);
            } else {
                this.unselectRow(record);
            }
            if (myDataTable_coupon.getSelectedRows().length > 0) {
                deletePromoCouponThread_btn.set("disabled", false);
            } else {
                deletePromoCouponThread_btn.set("disabled", true);
            }
            return false;
        });

        var deletePromoCouponThread_btn = new YAHOO.widget.Button("deletePromoCouponThread", { onclick:{ fn:deletePromoCouponThread } });

        //coupon list table.




        // Show loading message while page is being rendered
       // couponsDataTable.showTableMessage(couponsDataTable.get("MSG_LOADING"), YAHOO.widget.DataTable.CLASS_LOADING);

        // Update totalRecords on the fly with values from server


        var generateCouponRequest = function(oState, oSelf) {
            // Get states or use defaults
            oState = oState || { pagination: null, sortedBy: null };
           // var sort = (oState.sortedBy) ? oState.sortedBy.key : "id";
          //  var dir = (oState.sortedBy && oState.sortedBy.dir === YAHOO.widget.DataTable.CLASS_DESC) ? "desc" : "asc";
            var startIndex = (oState.pagination) ? oState.pagination.recordOffset : 0;
            var results = (oState.pagination) ? oState.pagination.rowsPerPage : 25;
            if(parseInt(startIndex)!=startIndex)
            {
                startIndex = 1;
            }
            //return "start_index=" + start_index + "&num_per_page=" + number_per_page_default + "&name="+couponName+ "&price="+couponSellPrice+ "&sku="+skuCode;
            return "start_index=" + startIndex + "&num_per_page=" + number_per_page_default;
        };

        // Called by Browser History Manager to trigger a new state
        var queryCouponData = function () {
            $('#listCouponTable').datagrid('load',{
                couponCode: $("#qCouponCode").val(),
                couponName: $("#qCouponName").val()
            });
        };


        //button.
        var query_coupon_btn = new YAHOO.widget.Button("query_coupons_btn", {label:messages["button.search"],onclick:{ fn:queryCouponData } });



        var handleCouponSubmit = function () {
            var selected = $('#listCouponTable').datagrid("getSelections");
            if (selected.length < 1) {
                alert(messages["coupon.validator.sku.choose"]);
            } else {
                $.each(selected,function() {
                    var index = myDataTable_coupon.getRecordSet().getLength();
                    myDataTable_coupon.addRow({couponId:this.couponId, couponCode:this.couponCode, name:this.name,
                        description:this.description, maxUse:this.maxUse, timesUsed:this.timesUsed}, index);
                 });
            }
        };

        var handleCancel = function() {
            this.cancel();
        };

        var panel_coupon = new YAHOO.widget.Dialog("panel_coupon",
            {   width:"700px",
                visible:false,
                constraintoviewport:true,
                fixedcenter:true,
                underlay:"shadow",
                buttons : [
                    { text:messages["submit"], handler:handleCouponSubmit, isDefault:true },
                    { text:messages["cancel"], handler:handleCancel }
                ]
            });
        panel_coupon.render();

        var show_coupon_panel = function(){
            Dom.setStyle("panel_coupon", "display", "");
            panel_coupon.show();
        };

        var add_coupon_btn = new YAHOO.widget.Button("add-coupon-edit");
        YAHOO.util.Event.addListener("add-coupon-edit", "click", show_coupon_panel, panel_coupon, true);


        //tag list

        var deletePromoTagThread = function (p_oEvent) {
            //call the delete action.
            var checkboxValues = $('[name="tagCheckBox"]:checked').map(
                function(){ return $(this).val(); }
            ).toArray();
            $.each(checkboxValues,function() {
                var id = this;
                $("#promotion-tag"+id).remove();
            } );
        };
        var checkTagExist = function(id){
            var nId = id.toString();
            var existTagIds = $('[name="tagCheckBox"]').map(
                function(){ return $(this).val(); }
            ).toArray();
            return  $.inArray(nId,existTagIds) != -1;
        }

        var isCanDelTag = function(){
            var checkboxValues = $('[name="tagCheckBox"]:checked').map(
                function(){ return $(this).val(); }
            ).toArray();
            return  checkboxValues.length > 0;
        }
        var tagCheckChanged = function(){
            var disabled = !isCanDelTag()
            deletePromoTagBtn.set("disabled", disabled);

        }
        var handleTagSubmit = function () {
            var checkboxValues = $('[name="qTagCheckBox"]:checked').map(
                function(){ return $(this).val(); }
            ).toArray();
            $.each(checkboxValues,function() {
                var id = this;
                if (!checkTagExist(id)){
                    var labeName = '#L-'+id.toString();
                   var name =  $(labeName).text();
                   var tDiv =   $(document.createElement("div"));
                    tDiv.className='promotion-tag';
                    tDiv.id="promotion-tag"+id;
                   var container =  $('#promo_tag_div').append( tDiv);
                   var html = '<input type="checkbox" id="'+id + '" value="'+id+ '" name="tagCheckBox" /> <label name="tl'+id+'">'+name+'</label>';
                   tDiv.append($(html));
                }
        });
        }

        var panel_tag = new YAHOO.widget.Dialog("panel_tag",
            {   width:"700px",
                visible:false,
                constraintoviewport:true,
                fixedcenter:true,
                underlay:"shadow",
                buttons : [
                    { text:messages["submit"], handler:handleTagSubmit, isDefault:true },
                    { text:messages["cancel"], handler:handleCancel }
                ]
            });
        panel_tag.render();

        var show_tag_panel = function(){
            Dom.setStyle("panel_tag", "display", "");
            queryTag();
            panel_tag.show();
        };

        var queryTag = function(){
            $.post("/admin/tags/jsonByName",{tagName:$("#qTagName").val()},function(data){
                var container = $('#tags-thread-list');
                $('#tags-thread-list').empty();
                var records = $.parseJSON(data).records;
                $.each(records,function() {
                    var tDiv =   $(document.createElement("div"));
                    tDiv.className='promotion-tag';
                    container.append(tDiv);
                    var html = '<input type="checkbox" id=q"'+this.id + '" value="'+this.id+ '" name="qTagCheckBox" /> <label id="L-'+this.id+'">'+this.tagName+'</label>';
                    tDiv.append($(html));
                    //alert(result.d.join(" | "));
                });
            });

        }
        var deletePromoTagBtn = new YAHOO.widget.Button("deletePromoTagThread", { onclick:{ fn:deletePromoTagThread } });
        var add_tag_btn = new YAHOO.widget.Button("add-tag-edit");
        YAHOO.util.Event.addListener("add-tag-edit", "click", show_tag_panel, panel_tag, true);
        $('[name="tagCheckBox"]').click(function() {
            tagCheckChanged();
        })
        var query_tags_btn = new YAHOO.widget.Button("query_tags_btn", { onclick:{ fn:queryTag } });

        var renderOptionUI = function(){
            $.get('/ruleOptionUI/'+$('#rule').val(), function(data) {
                var dataObj = $.parseJSON(data);
                $('#p_ruleOptions').html(dataObj.html);
                $.globalEval(dataObj.initScript);
                $.globalEval(dataObj.validateScript);
                initOptionUIValue();
            });
        };
        renderOptionUI();

        var initOptionUIValue = function(){
            $.get('/admin/promotionOption?promotionId='+$('#promotionid').val(), function(data) {
                var ruleId = $('#rule').val();
                for(var i= 0;i<data.length;i++){
                   var op = data[i];
                   var key = op.optionKey + "-" + ruleId;
                    if ( key.indexOf('date')>=0  || key.indexOf('Date')>=0){
                        $('#'+key).datebox('setValue', op.optionValue);
                   }else if ($('#'+key).attr("type") == 'checkbox'){
                        if (op.optionValue == 'true') {
                            $('#'+key).attr("checked", true);
                        } else {
                            $('#'+key).attr("checked", false);
                        }
                   }else {
                     $('#'+key).val(op.optionValue);
                   }
                }
            });
        }
        $(document).ready(function() {
            $('#rule').change(function(){
                renderOptionUI();
            });
            $('#promo_opp_table_div').hide();
        });
        return {
            de:myEditor,
            oDS_opt:myDataSource_opt,
            oDT_opt:myDataTable_opt,
            oDS_prod:productsDataSource,
            oDT_prod:productsDataTable,
            oDS_myProd:myDataSource_product,
            oDT_myProd:myDataTable_product,

            oDS_coupon:couponsDataSource,
            oDT_coupon:couponsDataTable,
            oDS_myCoupon:myDataSource_coupon,
            oDT_myCoupon:myDataTable_coupon
        };



    }();
});